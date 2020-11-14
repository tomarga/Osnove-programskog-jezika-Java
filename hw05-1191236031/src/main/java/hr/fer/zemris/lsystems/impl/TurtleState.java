package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.zemris.math.Vector2D;

/**
 * A class that represents the state of a "turtle" that draws a trace while moving through window.
 * 
 * @author Margarita Tolja
 *
 */
public class TurtleState {

	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private float lineWidth;
	private double step;
	
	/**
	 * Creates a new state with passed values.
	 * @param position
	 * 		Current position of a turtle. 
	 * @param direction
	 * 		Current direction of a turtle.
	 * @param color
	 * 		Current colour of a turtle's trace.
	 * @param step
	 * 		Current step length.
	 */
	public TurtleState( Vector2D position, Vector2D direction, Color color, float lineWidth, double step ) {
		this.position = position;
		this.direction = direction;
		this.color = color;		
		this.step = step;
		this.lineWidth = lineWidth;
	}
	
	/**
	 * Creates a copy of this state.
	 * @return
	 * 		Copy of the state.
	 */
	public TurtleState copy() {
		Color colorCopy = new Color( color.getRGB() );
		return new TurtleState( position.copy(), direction.copy(), colorCopy, lineWidth, step );
	}

	/**
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @return the direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * @return the colour
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the colour to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the step
	 */
	public double getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(double step) {
		this.step = step;
	}
	
	/**
	 * @return the lineWidth
	 */
	public float getLineWidth() {
		return lineWidth;
	}

	/**
	 * @param lineWidth the lineWidth to set
	 */
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

}
