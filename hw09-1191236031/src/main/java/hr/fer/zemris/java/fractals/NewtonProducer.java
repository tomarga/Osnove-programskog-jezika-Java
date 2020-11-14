package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A class that represents the producer of Newton fractal computing services.
 * 
 * @author Margarita Tolja
 *
 */
public class NewtonProducer implements IFractalProducer {

	private ExecutorService pool;
	private ComplexRootedPolynomial rootedPolynomial;
	private ComplexPolynomial polynomial;
	
	/**
	 * Constructor. 
	 * @param polynomial	Complex rooted polynomial.
	 */
	public NewtonProducer( ComplexRootedPolynomial polynomial ) {
		
		this.rootedPolynomial = polynomial;
		this.polynomial = polynomial.toComplexPolynom();
		
		ThreadFactory factory = new ThreadFactory() {
			
			@Override
			public Thread newThread( Runnable r ) {
				Thread thread = new Thread( r );
				thread.setDaemon( true );
				return thread;
			}
		};
		pool = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors(), factory );
	}
	
	/**
	 * The range of y-s is divided into 8 * numberOfAvailableProcessors jobs.
	 */
	@Override
	public void produce (double reMin, double reMax, double imMin, double imMax, int width, int height, 
			long requestNo, IFractalResultObserver observer ) {
		
		int m = 16*16*16;
		short[] data = new short[width * height];
		final int numberOfStrips = 8 * Runtime.getRuntime().availableProcessors();
		int yPerStrip = height / numberOfStrips;
		
		List<Future<Void>> results = new ArrayList<>();
		
		for( int i = 0; i < numberOfStrips; i++ ) {
			int yMin = i * yPerStrip;
			int yMax = (i+1) * yPerStrip - 1;
			if( i == numberOfStrips-1 ) {
				yMax = height-1;
			}
			ComputingJob job = new ComputingJob( reMin, reMax, imMin, imMax, width, height,
					yMin, yMax, m, data, rootedPolynomial );
			results.add( pool.submit( job ) );
		}
		
		for( Future<Void> job : results ) {
			try {
				job.get();
			} catch ( InterruptedException | ExecutionException e ) {
			}
		}
		
		pool.shutdown();
		
		observer.acceptResult( data, (short)( polynomial.order()+1), requestNo );
	}
	
}
