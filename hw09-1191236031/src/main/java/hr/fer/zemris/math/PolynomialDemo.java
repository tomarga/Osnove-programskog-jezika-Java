package hr.fer.zemris.math;

public class PolynomialDemo {

	public static void main(String[] args) {
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		System.out.println( cp.apply( new Complex( 2, 1 ) ) );
		System.out.println( crp.apply( new Complex(2, 1 ) ) );
		System.out.println( cp.multiply( cp ));
	}

}
