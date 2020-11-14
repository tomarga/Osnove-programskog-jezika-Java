package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

	private Vector2D vector;
	private Vector2D vector2;
	private static double delta = 1E-12;
	
	
	//constructor
	@Test
	public void constructorBasic() {
		vector = new Vector2D( 0, 1 );
		assertEquals( 0, vector.getX() );
		assertEquals( 1, vector.getY() );
	}
	
	
	//translate
	@Test
	public void translate() {
		vector = new Vector2D( 0, 1 );
		vector.translate( new Vector2D( 2, -2 ) );
		assertEquals( 2, vector.getX() );
		assertEquals( -1, vector.getY() );		
	}
	
	
	//translated
	@Test
	public void translated() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.translated( new Vector2D( 2, -2 ) );
		assertEquals( 0, vector.getX() );
		assertEquals( 1, vector.getY() );
		assertEquals( 2, vector2.getX() );
		assertEquals( -1, vector2.getY() );		
	}
	
	
	//rotate
	@Test
	public void rotateZero() {
		vector = new Vector2D( 0, 1 );
		vector.rotate( 0 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test void rotateNullVector() {
		vector = new Vector2D( 0, 0 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePIDividedByTwo1() {
		vector = new Vector2D( 1, 1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo2() {
		vector = new Vector2D( 1, 0 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo3() {
		vector = new Vector2D( 0, 1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo4() {
		vector = new Vector2D( -1, 0 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo5() {
		vector = new Vector2D( 0, -1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePIDividedByTwo6() {
		vector = new Vector2D( -1, 1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo7() {
		vector = new Vector2D( 1, -1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePIDividedByTwo8() {
		vector = new Vector2D( -1, -1 );
		vector.rotate( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePI1() {
		vector = new Vector2D( 1, 1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePI2() {
		vector = new Vector2D( 0, 1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePI3() {
		vector = new Vector2D( 1, 0 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePI4() {
		vector = new Vector2D( 0, -1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePI5() {
		vector = new Vector2D( -1, 0 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotatePI6() {
		vector = new Vector2D( -1, 1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePI7() {
		vector = new Vector2D( 1, -1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotatePI8() {
		vector = new Vector2D( -1, -1 );
		vector.rotate( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotateMinusPIDividedByTwo1() {
		vector = new Vector2D( 1, 1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateMinusPIDividedByTwo2() {
		vector = new Vector2D( 0, 1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotateMinusPIDividedByTwo3() {
		vector = new Vector2D( 1, 0 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateMinusPIDividedByTwo4() {
		vector = new Vector2D( -1, 0 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateMinusPIDividedByTwo5() {
		vector = new Vector2D( 0, -1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotateMinusPIDividedByTwo6() {
		vector = new Vector2D( -1, 1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateMinusPIDividedByTwo7() {
		vector = new Vector2D( 1, -1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateMinusPIDividedByTwo8() {
		vector = new Vector2D( -1, -1 );
		vector.rotate( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole1() {
		vector = new Vector2D( 1, 1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole2() {
		vector = new Vector2D( 0, 1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole3() {
		vector = new Vector2D( 1, 0 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole4() {
		vector = new Vector2D( -1, 0 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole5() {
		vector = new Vector2D( 0, -1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	@Test
	public void rotateWhole6() {
		vector = new Vector2D( -1, 1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotateWhole7() {
		vector = new Vector2D( 1, -1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}	
	@Test
	public void rotateWhole8() {
		vector = new Vector2D( -1, -1 );
		vector.rotate( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
	}
	
	
	//rotated
	@Test
	public void rotatedZero() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.rotated( 0 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}
	@Test void rotatedNullVector() {
		vector = new Vector2D( 0, 0 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedPIDividedByTwo1() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedPIDividedByTwo2() {
		vector = new Vector2D( 1, 0 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPIDividedByTwo3() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPIDividedByTwo4() {
		vector = new Vector2D( -1, 0 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPIDividedByTwo5() {
		vector = new Vector2D( 0, -1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPIDividedByTwo6() {
		vector = new Vector2D( -1, 1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPIDividedByTwo7() {
		vector = new Vector2D( 1, -1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedPIDividedByTwo8() {
		vector = new Vector2D( -1, -1 );
		vector2 = vector.rotated( Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI1() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI2() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedPI3() {
		vector = new Vector2D( 1, 0 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI4() {
		vector = new Vector2D( 0, -1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI5() {
		vector = new Vector2D( -1, 0 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI6() {
		vector = new Vector2D( -1, 1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI7() {
		vector = new Vector2D( 1, -1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedPI8() {
		vector = new Vector2D( -1, -1 );
		vector2 = vector.rotated( Math.PI );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo1() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo2() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedMinusPIDividedByTwo3() {
		vector = new Vector2D( 1, 0 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedMinusPIDividedByTwo4() {
		vector = new Vector2D( -1, 0 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo5() {
		vector = new Vector2D( 0, -1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo6() {
		vector = new Vector2D( -1, 1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo7() {
		vector = new Vector2D( 1, -1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedMinusPIDividedByTwo8() {
		vector = new Vector2D( -1, -1 );
		vector2 = vector.rotated( - Math.PI / 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole1() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole2() {
		vector = new Vector2D( 0, 1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole3() {
		vector = new Vector2D( 1, 0 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole4() {
		vector = new Vector2D( -1, 0 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole5() {
		vector = new Vector2D( 0, -1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( 0 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 0 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedWhole6() {
		vector = new Vector2D( -1, 1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getY()  ) < delta );
	}
	@Test
	public void rotatedWhole7() {
		vector = new Vector2D( 1, -1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( 1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( 1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}	
	@Test
	public void rotatedWhole8() {
		vector = new Vector2D( -1, -1 );
		vector2 = vector.rotated( Math.PI * 2 );
		assertTrue( Math.abs( -1 - vector.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector.getY()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getX()  ) < delta );
		assertTrue( Math.abs( -1 - vector2.getY()  ) < delta );
	}
	
	
	//scale
	@Test
	public void scaleZero() {
		vector = new Vector2D( 1, 1 );
		vector.scale( 0 );
		assertEquals( 0, vector.getX() );
		assertEquals( 0, vector.getY() );
	}	
	@Test
	public void scalePositive() {
		vector = new Vector2D( 1, 1 );
		vector.scale( 2 );
		assertEquals( 2, vector.getX() );
		assertEquals( 2, vector.getY() );
	}	
	@Test
	public void scaleNegative() {
		vector = new Vector2D( 1, 1 );
		vector.scale( -2 );
		assertEquals( -2, vector.getX() );
		assertEquals( -2, vector.getY() );
	}
	
	
	//scaled
	@Test
	public void scaledZero() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.scaled( 0 );
		assertEquals( 1, vector.getX() );
		assertEquals( 1, vector.getY() );
		assertEquals( 0, vector2.getX() );
		assertEquals( 0, vector2.getY() );
	}	
	@Test
	public void scaledPositive() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.scaled( 2 );
		assertEquals( 1, vector.getX() );
		assertEquals( 1, vector.getY() );
		assertEquals( 2, vector2.getX() );
		assertEquals( 2, vector2.getY() );
	}	
	@Test
	public void scaledNegative() {
		vector = new Vector2D( 1, 1 );
		vector2 = vector.scaled( -2 );
		assertEquals( 1, vector.getX() );
		assertEquals( 1, vector.getY() );
		assertEquals( -2, vector2.getX() );
		assertEquals( -2, vector2.getY() );
	}
	
	
	//copy
	@Test
	public void copy() {
		vector = new Vector2D( 1, 3 );
		vector2 = vector.copy();
		assertEquals( vector.getX(), vector2.getX() );
		assertEquals( vector.getY(), vector2.getY() );
	}
		
}
