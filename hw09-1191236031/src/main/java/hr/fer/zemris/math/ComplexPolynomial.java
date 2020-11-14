package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a complex polynomial.
 * 
 * @author Margarita Tolja
 *
 */
public class ComplexPolynomial {
	
	private final List<Complex> factors;
	
	/**
	 * Initialises complex factors.
	 * @param factors		z0,...zN, N >= 0.
	 */
	public ComplexPolynomial( Complex ... factors ) {
		
		this.factors = new ArrayList<>();
		for ( Complex factor : factors ) {
			this.factors.add( factor );
		}
		if ( this.factors.isEmpty() ) {
			this.factors.add( new Complex() );
		}
	}
	
	/**
	 * Gets the order of the polynomial.
	 * @return		The greatest exponent of the polynomial's variable.
	 */
	public short order() {
		return (short) ( factors.size() - 1 );
	}
	
	/**
	 * Computes a product of this and passed polynomial.
	 * @param p		Other polynomial.
	 * @return 		The product.
	 */
	public ComplexPolynomial multiply( ComplexPolynomial p ) {
		
		int m = factors.size();
		int n = p.factors.size();
		
		Complex[] productFactors = new Complex[m + n - 1]; 
		  
        // initialise the product polynomial 
        for ( int i = 0; i < m + n - 1; i++ ) { 
            productFactors[i] = new Complex(); 
        } 
  
        // multiply two polynomials term by term 
        for ( int i = 0; i < m; i++ ) { 
            for (int j = 0; j < n; j++) { 
                productFactors[i+j] = productFactors[i+j].add( factors.get( i ).multiply( p.factors.get( j ) ) ); 
            } 
        } 
        return new ComplexPolynomial( productFactors ); 
	}
	
	/**
	 * Computes first derivative of this polynomial.
	 * @return		f'(z).
	 */
	public ComplexPolynomial derive() {
		
		int size = factors.size() > 1 ? factors.size()-1 : 1;
		Complex[] derivationFactors = new Complex[size];
		
		for ( int i = 1, n = factors.size(); i < n; i++ ) {
			derivationFactors[i-1] = factors.get( i ).multiply( new Complex( i, 0 ) );
		}
		
		return new ComplexPolynomial( derivationFactors );
	}
	
	/**
	 * Evaluates polynomial in the passed complex point.
	 * @param z		Complex number.
	 * @return		f(z).
	 */
	public Complex apply( Complex z ) {
		
		// initialise result 
        Complex result = factors.get( factors.size() - 1 );   
   
        // evaluate value of polynomial using Horner's method 
        for ( int i = factors.size() - 2; i >= 0; i-- ) {
            result = result.multiply( z ).add( factors.get( i ) );
        }
        return result; 
	}
	
	/**
	 * Gets the string representation of the polynomial:
	 * "(z_n)z^n + ... + (z_0)".
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for ( int i = factors.size() - 1; i > 0; i-- ) {
			sb.append( "(" ).append( factors.get( i ) ).append( ")" );
			sb.append( "*z^" ).append( i ).append( "+" );
		}
		sb.append( "(" ).append( factors.get( 0 ) ).append( ")" );
		
		return sb.toString();
	}
	
}
