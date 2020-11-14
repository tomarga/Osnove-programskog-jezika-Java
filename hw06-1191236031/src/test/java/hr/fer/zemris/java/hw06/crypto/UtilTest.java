package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {

	//hextobyte
	@Test
	public void hextobyteEmptyString() {
		byte[] result = Util.hextobyte( "" );
		assertTrue( result.length == 0 );
	}
	@Test
	public void hextobyteInvalidString() {
		assertThrows( IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			byte[] result = Util.hextobyte( "W1" );
		});
	}
	@Test
	public void hextobyteOddSizedString() {
		assertThrows( IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			byte[] result = Util.hextobyte( "1" );
		});
	}
	@Test
	public void hextobyteZero() {
		byte[] result = Util.hextobyte( "00" );
		byte[] expected = {0};
		assertArrayEquals( expected, result );
	}
	@Test 
	public void hextobyteMin() {
		byte[] result = Util.hextobyte( "80" );
		byte[] expected = {-128};
		assertArrayEquals( expected, result );
	}
	@Test
	public void hextobyteMax() {
		byte[] result = Util.hextobyte( "7F" );
		byte[] expected = {127};
		assertArrayEquals( expected, result );
	}
	@Test
	public void hextobyteHwExample() {
		byte[] result = Util.hextobyte( "01aE22" );
		byte[] expected = {1, -82, 34 };
		assertArrayEquals( expected, result );
	}
	
	
	//bytetohex
	@Test
	public void bytetohexZero() {
		String result = Util.bytetohex( new byte[]{0} );
		assertEquals( "00", result );
	}
	@Test
	public void bytetohexMin() {
		String result = Util.bytetohex( new byte[]{-128} );
		assertEquals( "80", result );
	}
	@Test
	public void bytetohexMax() {
		String result = Util.bytetohex( new byte[]{127} );
		assertEquals( "7f", result );
	}
	@Test
	public void bytetohexHwExample() {
		String result = Util.bytetohex( new byte[] {1, -82, 34} );
		assertEquals( "01ae22", result );
	}	
	
}
