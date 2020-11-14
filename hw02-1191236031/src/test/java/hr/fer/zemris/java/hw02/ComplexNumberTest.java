package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	private ComplexNumber z;	
	
	//constructor
	@Test
	public void ComplexNumber() {
		z = new ComplexNumber( 0, 0 );
		assertEquals( 0, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	
	//fromReal
	@Test
	public void fromReal() {
		z = ComplexNumber.fromReal( 1.5 );
		assertEquals( 1.5, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	
	//fromImaginary
	@Test
	public void fromImaginary() {
		z = ComplexNumber.fromImaginary( 1.5 );
		assertEquals( 0, z.getReal() );
		assertEquals( 1.5, z.getImaginary() );
	}
	
	//fromMagnitudeAndAngle
	@Test
	public void fromMagnitudeAndAngleZeroZero() {
		z = ComplexNumber.fromMagnitudeAndAngle( 0, 0 );
		assertTrue( Math.abs( z.getReal() - 0 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - 0 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleZeroNonzero() {
		z = ComplexNumber.fromMagnitudeAndAngle( 0, 2 );
		assertTrue( Math.abs( z.getReal() - 0 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - 0 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleNonzeroZero() {
		z = ComplexNumber.fromMagnitudeAndAngle( 2, 0 );
		assertTrue( Math.abs( z.getReal() - 2 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - 0 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngle90() {
		z = ComplexNumber.fromMagnitudeAndAngle( 2, Math.PI/2 );
		assertTrue( Math.abs( z.getReal() - 0 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - 2 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngle180() {
		z = ComplexNumber.fromMagnitudeAndAngle( 2, Math.PI );
		assertTrue( Math.abs( z.getReal() - (-2) ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - 0 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngle270() {
		z = ComplexNumber.fromMagnitudeAndAngle( 2, 3*Math.PI/2 );
		assertTrue( Math.abs( z.getReal() - 0 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - (-2) ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleFirstQuadrant() {
		z = ComplexNumber.fromMagnitudeAndAngle( 1, Math.PI/4 );
		assertTrue( Math.abs( z.getReal() - Math.sqrt( 2 )/2 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - Math.sqrt( 2 )/2 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleSecondQuadrant() {
		z = ComplexNumber.fromMagnitudeAndAngle( 1, 3*Math.PI/4 );
		assertTrue( Math.abs( z.getReal() - (-Math.sqrt( 2 )/2) ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - Math.sqrt( 2 )/2 ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleThirdQuadrant() {
		z = ComplexNumber.fromMagnitudeAndAngle( 1, 5*Math.PI/4 );
		assertTrue( Math.abs( z.getReal() - (-Math.sqrt( 2 )/2) ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - (-Math.sqrt( 2 )/2) ) < 1E-12 );
	}
	@Test
	public void fromMagnitudeAndAngleFourthQuadrant() {
		z = ComplexNumber.fromMagnitudeAndAngle( 1, 7*Math.PI/4 );
		assertTrue( Math.abs( z.getReal() - Math.sqrt( 2 )/2 ) < 1E-12 );
		assertTrue( Math.abs( z.getImaginary() - (-Math.sqrt( 2 )/2) ) < 1E-12 );
	}
	
	//parse
	//parse real
	@Test 
	public void parsePositiveRealOneDigit() {
		z = ComplexNumber.parse( "1" );
		assertEquals( 1, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test
	public void parsePositiveRealMoreDigits() {
		z = ComplexNumber.parse( "3.51" );
		assertEquals( 3.51, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test 
	public void parseSignedPositiveRealOneDigit() {
		z = ComplexNumber.parse( "+1" );
		assertEquals( 1, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test
	public void parseSignedPositiveRealMoreDigits() {
		z = ComplexNumber.parse( "+3.51" );
		assertEquals( 3.51, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealOneDigit() {
		z = ComplexNumber.parse( "-1" );
		assertEquals( -1, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealMoreDigits() {
		z = ComplexNumber.parse( "-3.51" );
		assertEquals( -3.51, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	@Test
	public void parseZero() {
		z = ComplexNumber.parse( "0" );
		assertEquals( 0, z.getReal() );
		assertEquals( 0, z.getImaginary() );
	}
	//parse imaginary
	@Test
	public void parsePositiveImaginaryOneDigit() {
		z = ComplexNumber.parse( "2i" );
		assertEquals( 0, z.getReal() );
		assertEquals( 2, z.getImaginary() );
	}
	@Test
	public void parsePositiveImaginaryMoreDigits() {
		z = ComplexNumber.parse( "3.51i" );
		assertEquals( 0, z.getReal() );
		assertEquals( 3.51, z.getImaginary() );
	}
	@Test
	public void parseSignedPositiveImaginaryOneDigit() {
		z = ComplexNumber.parse( "+2i" );
		assertEquals( 0, z.getReal() );
		assertEquals( 2, z.getImaginary() );
	}
	@Test
	public void parseSignedPositiveImaginaryMoreDigits() {
		z = ComplexNumber.parse( "+3.51i" );
		assertEquals( 0, z.getReal() );
		assertEquals( 3.51, z.getImaginary() );
	}
	@Test
	public void parseNegativeImaginaryOneDigit() {
		z = ComplexNumber.parse( "-2i" );
		assertEquals( 0, z.getReal() );
		assertEquals( -2, z.getImaginary() );
	}
	@Test
	public void parseNegativeImaginaryMoreDigits() {
		z = ComplexNumber.parse( "-3.51i" );
		assertEquals( 0, z.getReal() );
		assertEquals( -3.51, z.getImaginary() );
	}
	@Test
	public void parseI() {
		z = ComplexNumber.parse( "i" );
		assertEquals( 0, z.getReal() );
		assertEquals( 1, z.getImaginary() );
	}
	@Test
	public void parseMinusI() {
		z = ComplexNumber.parse( "-i" );
		assertEquals( 0, z.getReal() );
		assertEquals( -1, z.getImaginary() );
	}
	//parse real + imaginary
	@Test
	public void parsePositiveRealPositiveImaginary1() {
		z = ComplexNumber.parse( ""
				+ "1+2i" );
		assertEquals( 1, z.getReal() );
		assertEquals( 2, z.getImaginary() );
	}
	@Test
	public void parsePositiveRealPositiveImaginary2() {
		z = ComplexNumber.parse( "3.51+2.71i" );
		assertEquals( 3.51, z.getReal() );
		assertEquals( 2.71, z.getImaginary() );
	}
	@Test
	public void parsePositiveRealI() {
		z = ComplexNumber.parse( "1+i" );
		assertEquals( 1, z.getReal() );
		assertEquals( 1, z.getImaginary() );
	}
	@Test
	public void parsePositiveRealMinusI() {
		z = ComplexNumber.parse( "1-i" );
		assertEquals( 1, z.getReal() );
		assertEquals( -1, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealPositiveImaginary1() {
		z = ComplexNumber.parse( "-1+2i" );
		assertEquals( -1, z.getReal() );
		assertEquals( 2, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealPositiveImaginary2() {
		z = ComplexNumber.parse( "-3.51+2.71i" );
		assertEquals( -3.51, z.getReal() );
		assertEquals( 2.71, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealNegativeImaginary1() {
		z = ComplexNumber.parse( "-1-2i" );
		assertEquals( -1, z.getReal() );
		assertEquals( -2, z.getImaginary() );
	}
	@Test
	public void parseNegativeRealNegativeImaginary2() {
		z = ComplexNumber.parse( "-3.51-2.71i" );
		assertEquals( -3.51, z.getReal() );
		assertEquals( -2.71, z.getImaginary() );
	}
	//parse exceptions
	@Test
	public void parseIbeforeMagnitude() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "i3.51" );
		});	
	}
	@Test
	public void parseMultipleSigns1() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "+-3.51" );
		});	
	}
	@Test
	public void parseMultipleSigns2() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "2+-3.51i" );
		});	
	}
	@Test
	public void parseMultipleSigns3() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "2-+3.51i" );
		});	
	}
	@Test
	public void parseMultipleSigns4() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "+-+2-+3.51i" );
		});	
	}
	@Test
	public void parseMultipleSigns5() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "+-i" );
		});	
	}
	@Test
	public void parseEmptyString() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "" );
		});	
	}
	@Test
	public void parseRandomString() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "sdf" );
		});	
	}
	@Test
	public void parseImaginaryImaginary() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "i+2i" );
		});	
	}
	@Test
	public void parseRealReal() {
		assertThrows(NumberFormatException.class, () -> {
			z = ComplexNumber.parse( "3.21+2" );
		});	
	}
	
	//getreal
	@Test
	public void getReal() {
		z = new ComplexNumber( 2, 2 );
		assertEquals( 2, z.getReal() );
	}
	
	//getimaginary
	@Test
	public void getImaginary() {
		z = new ComplexNumber( 2, 2 );
		assertEquals( 2, z.getImaginary() );
	}

	//getMagnitude
	@Test
	public void getMagnitudeZero() {
		z = new ComplexNumber( 0, 0 );
		assertEquals( 0, z.getMagnitude() );
	}
	@Test
	public void getMagnitudeNonZero() {
		z = new ComplexNumber( 1, 1 );
		assertTrue( Math.abs( z.getMagnitude() - Math.sqrt(2)) < 1E-12 );
	}
		
	//getAngle
	@Test
	public void getAngleOne() {
		z = new ComplexNumber( 1, 0 );
		assertEquals( 0, z.getAngle() );
	}
	@Test
	public void getAngleMinusOne() {
		z = new ComplexNumber( -1, 0 );
		assertEquals( Math.PI, z.getAngle() );
	}
	@Test
	public void getAngleI() {
		z = new ComplexNumber( 0, 1 );
		assertEquals( Math.PI/2, z.getAngle() );
	}
	@Test
	public void getAngleMinusI() {
		z = new ComplexNumber( 0, -1 );
		assertEquals( 3*Math.PI/2, z.getAngle() );
	}
	@Test
	public void getAnglePositivePositive() {
		z = new ComplexNumber( 1, 1 );
		assertEquals( Math.PI/4, z.getAngle() );
	}
	@Test
	public void getAngleNegativePositive() {
		z = new ComplexNumber( -1, 1 );
		assertEquals( 3*Math.PI/4, z.getAngle() );
	}
	@Test
	public void getAngleNegativeNegative() {
		z = new ComplexNumber( -1, -1 );
		assertEquals( 5*Math.PI/4, z.getAngle() );
	}
	@Test
	public void getAnglePositiveNegative() {
		z = new ComplexNumber( 1, -1 );
		assertEquals( 7*Math.PI/4, z.getAngle() );
	}
	
	//add
	@Test
	public void add() {
		z = new ComplexNumber( 1, 1 );
		ComplexNumber z2 = new ComplexNumber( 2.5, 2.5 );
		assertEquals( 3.5, z.add(z2).getReal() );
		assertEquals( 3.5, z.add(z2).getImaginary() );		
	}
	
	//sub
	@Test
	public void sub() {
		z = new ComplexNumber( 1, 1 );
		ComplexNumber z2 = new ComplexNumber( 2.5, 2.5 );
		assertEquals( -1.5, z.sub(z2).getReal() );
		assertEquals( -1.5, z.sub(z2).getImaginary() );	
	}
		
	//mul
	@Test
	public void mul() {
		z = new ComplexNumber( 1, 1 );
		ComplexNumber z2 = new ComplexNumber( 2.5, 2.5 );
		assertEquals( 0, z.mul(z2).getReal() );
		assertEquals( 5, z.mul(z2).getImaginary() );		
	}
	
	//div
	@Test
	public void div() {
		z = new ComplexNumber( 1, 1 );
		ComplexNumber z2 = new ComplexNumber( 2.5, 2.5 );
		assertEquals( 0.4, z.div(z2).getReal() );
		assertEquals( 0, z.div(z2).getImaginary() );		
	}
	@Test
	public void divZeroException() {
		z = new ComplexNumber( 1, 1 );
		ComplexNumber z2 = new ComplexNumber( 0, 0 );
		assertThrows(IllegalArgumentException.class, () -> {
			z.div( z2 );
		});			
	}
	
	//power
	@Test
	public void power() {
		z = new ComplexNumber( 1, 1 );
		int n = 3;
		assertTrue( Math.abs( -2 - z.power(n).getReal() ) < 1E-12 );
		assertTrue( Math.abs( 2 - z.power(n).getImaginary() ) < 1E-12 );		
	}
	@Test
	public void powerLessThanZero() {
		z = new ComplexNumber( 1, 1 );
		int n = -1;
		assertThrows(IllegalArgumentException.class, () -> {
			z.power( n );
		});			
	}
	@Test
	public void powerZero() {
		z = new ComplexNumber( 1, 1 );
		int n = 0;
		assertTrue( Math.abs( 1 - z.power(n).getReal() ) < 1E-12 );
		assertTrue( Math.abs( 0 - z.power(n).getImaginary() ) < 1E-12 );			
	}
	
	//root
	@Test
	public void root() {
		z = new ComplexNumber( 1, 1.5 );
		int n = 3;
		double[] expectedReal = { 1.1523393616, -0.9153179115, -0.2370214502 };
		double[] expectedImaginary = { 0.3916146445, 0.8021478387, -1.1937624832 };
		ComplexNumber[] root = z.root( n );
		for ( int i = 0; i < 3; i++ ) {
			assertTrue( Math.abs( root[i].getReal() - expectedReal[i]) < 1E-8 );
			assertTrue( Math.abs( root[i].getImaginary() - expectedImaginary[i]) < 1E-8 );
		}
	}
	@Test
	public void rootLessThanZero() {
		z = new ComplexNumber( 1, 1.5 );
		assertThrows(IllegalArgumentException.class, () -> {
			z.root( -2 );
		});	
	}
	@Test
	public void rootZero() {
		z = new ComplexNumber( 1, 1.5 );
		assertThrows(IllegalArgumentException.class, () -> {
			z.root( 0 );
		});	
	}
	
	//toString
	@Test
	public void toStringReal() {
		z = new ComplexNumber( 2.5, 0 );
		assertEquals( "2.5", z.toString() );
	}
	@Test
	public void toStringImaginary() {
		z = new ComplexNumber( 0, -3.5 );
		assertEquals( "-3.5i", z.toString() );
	}
	@Test
	public void toStringRealImaginary1() {
		z = new ComplexNumber( 2.5, -3.5 );
		assertEquals( "2.5-3.5i", z.toString() );
	}
	@Test
	public void toStringRealImaginary2() {
		z = new ComplexNumber( -2.5, 3.5 );
		assertEquals( "-2.5+3.5i", z.toString() );
	}
		
}
