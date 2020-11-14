package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A class that represents the job of calculating data needed
 * to display Newton-Raphson ftactal.
 * 
 * @author Margarita Tolja
 *
 */
public class ComputingJob implements Callable<Void> {

	double reMin;
	double reMax;
	double imMin;
	double imMax;
	int width;
	int height;
	int yMin;
	int yMax;
	int m;
	short[] data;
	ComplexPolynomial polynomial;
	ComplexPolynomial derived;
	ComplexRootedPolynomial rootedPolynomial;
	double convergenceTreshold;
	double rootTreshold;
	int maxIter;
	
	/**
	 * Constructor.
	 * @param reMin		Minimum real value.
	 * @param reMax		Maximum real value.
	 * @param imMin		Minimum imaginary value.
	 * @param imMax		Maximum imaginary value.
	 * @param width		Width of a display screen.
	 * @param height	Width of a display screen.
	 * @param yMin		Beginning horizontal point of the current stripe.
	 * @param yMax		Ending horizontal point of the current stripe.
	 * @param m			Maximum iterations.
	 * @param data		Array where to put results.
	 * @param rootedPolynomial		Polynomial.
	 */
	public ComputingJob( double reMin, double reMax, double imMin,
			double imMax, int width, int height, int yMin, int yMax, 
			int m, short[] data, ComplexRootedPolynomial rootedPolynomial ) {
		
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.data = data;
		this.rootedPolynomial = rootedPolynomial;
		this.polynomial = rootedPolynomial.toComplexPolynom();
		this.derived = polynomial.derive();
		this.convergenceTreshold = 0.001;
		this.rootTreshold = 0.002;
		this.maxIter = m;
	}
	

	/**
	 * For each point in the area, the method computes the predefined
	 * number of Newton-Raphson's iterations and finds the index of the closest 
	 * root if possible. The index is then saved on the appropriate offset of the
	 * data array.
	 */
	@Override
	public Void call() {
	
		int offset = width*yMin;
		for( int y = yMin; y <= yMax; y++ ) {
			for( int x = 0; x < width; x++ ) {
				
				Complex c = mapToComplexPlain( x, y );
				Complex zn = c;
				int iter = 0;
				double module;
				
				do {
					Complex numerator = polynomial.apply( zn );
					Complex denominator = derived.apply( zn );
					Complex znOld = zn;
					Complex fraction = numerator.divide( denominator );
					zn = zn.sub( fraction );
					module = zn.distance( znOld );
					iter++;
				} while( module > convergenceTreshold && iter < maxIter );
				int index = rootedPolynomial.indexOfClosestRootFor( zn, rootTreshold );
				data[offset++] = (short) (index+1);
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the complex number that represents the (x,y) display point.
	 * @param x		X value.
	 * @param y		Y value.
	 * @return		Complex number.
	 */
	private Complex mapToComplexPlain( double x, double y ) {
		
		double cRe = ( x / ( ( double )width - 1 ) ) * ( reMax - reMin ) + reMin;
		double cIm = ( ( ( double )height - 1 - y ) / ( ( double )height - 1 ) ) * ( imMax - imMin ) + imMin;
		return new Complex( cRe, cIm ); 
	}
}

