package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a complex rooted polynomial.
 * 
 * @author Margarita Tolja
 *
 */
public class ComplexRootedPolynomial {

	private final Complex constant;
	private final List<Complex> roots;
	
	/**
	 * Initialises rooted polynomial's constant and roots. 
	 * @param constant		z0.
	 * @param roots			z1,...,zN, N >= 0.
	 */
	public ComplexRootedPolynomial( Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		for ( Complex root : roots ) {
			this.roots.add( root );
		}
	}

	/**
	 * Computes polynomial value at given point z.
	 * @param z		Complex number.
	 * @return		f(z).
	 */
	public Complex apply( Complex z ) {
		
		Complex result = new Complex( 1, 0 );
		//roots
		for ( Complex root : roots ) {
			result = result.multiply( z.sub( root ) );
		}
		//constant
		result = result.multiply( constant );
		
		return result;
	}
	
	/**
	 * Converts this representation to the non-factorised version.
	 * @return		Complex polynomial object that represents the same polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		int n = roots.size(); 
		Complex[] factors = new Complex[n+1];
		
		for ( int i = n; i > 0; i-- ) {
			
			Complex factor = new Complex();
			
			List<int[]> combinations = generateCombination( n, i );
			for ( int[] combination : combinations ) {
				Complex operand = new Complex( 1, 0 );
				for ( int member : combination ) {
					operand = operand.multiply( roots.get( member ) );
				}
				factor = factor.add( operand );
			}
			
			factor = factor.multiply( constant );
			if ( ( (-1)*i ) % 2 == 1 ) {
				factor = factor.negate();
			}
			
			factors[n-i] = factor;
		}
		
		factors[n] = constant;
		
		return new ComplexPolynomial( factors );
	}
	
	/**
	 * Generates a list of combinations of r numbers 0,...,n without repetition.
	 * @param n		Indicates that members of combinations will be numbers 0,...,n.
	 * @param r		Number of members in a combination.
	 * @return		The list of all combinations.
	 */
	private List<int[]> generateCombination( int n, int r ) {
		
	    List<int[]> combinations = new ArrayList<>();
	    int[] combination = new int[r];
	 
	    // initialize with lowest combination
	    for ( int i = 0; i < r; i++ ) {
	        combination[i] = i;
	    }
	 
	    while ( combination[r - 1] < n ) {
	        combinations.add( combination.clone() );
	 
	         // generate next combination
	        int t = r - 1;
	        while ( t != 0 && combination[t] == n - r + t ) {
	            t--;
	        }
	        combination[t]++;
	        for ( int i = t + 1; i < r; i++ ) {
	            combination[i] = combination[i - 1] + 1;
	        }
	    }
	    return combinations;
	}
	
	/**
	 * Gets the string representation of the rooted polynomial:
	 * "(constant)*(z-(root1))*...*(z-(rootN))".
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append( "(" ).append( constant ).append( ")" );
		
		for ( Complex root : roots ) {
			sb.append( "*" ).append( "(z-(" ).append( root ).append( "))" );
		}
		
		return sb.toString();
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within given treshold.
	 * First root has index 0, second index 1, etc.
	 * @param z				Complex number.
	 * @param treshold		Treshold.
	 * @return				Index of closest root if it is within treshold;
	 * 						-1 if there is no such root.
	 */
	public int indexOfClosestRootFor( Complex z, double treshold ) {
		
		if ( roots.isEmpty() ) {
			return -1;
		}
		
		double minDistance = z.distance( roots.get( 0 ) );
		int closest = 0;
		
		for ( int i = 0, n = roots.size(); i < n; i++  ) {
			double currentDistance = z.distance( roots.get( i ) );
			if ( currentDistance < minDistance ) {
				closest = i;
				minDistance = currentDistance;
			}
		}
		return ( minDistance < treshold ) ? closest : -1;
	}
	
}
