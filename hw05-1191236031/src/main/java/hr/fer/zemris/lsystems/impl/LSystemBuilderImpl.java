package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * A class used for building LSystem objects used for drawing fractals.
 * 
 * @author Margarita Tolja
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary<Character, Command> commands;
	private Dictionary<Character, String> productions;

	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	/**
	 * Default constructor. Creates a LSystemBuilder with no commands or productions.
	 */
	public LSystemBuilderImpl() {
		
		commands = new Dictionary<>();
		productions = new Dictionary<>();
		
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}
	
	/**
	 * Inner class that represents LSystem object built with this LSystem builder.
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Draws a fractal with passed depth using the painter in argument.
		 */
		@Override
		public void draw(int k, Painter painter) {
			
			Context context = new Context();
			Vector2D direction =  new Vector2D( 1, 0 ).rotated(Math.toRadians( angle ) );
			
			TurtleState state = new TurtleState( origin, direction, Color.BLACK, 1f,
					unitLength * Math.pow( unitLengthDegreeScaler, k ) );
			
			context.pushState( state );
			
			String expression = generate( k );
			for ( Character symbol : expression.toCharArray() ) {
				
				Command command = commands.get( symbol );
				if ( command == null ) {
					continue;
				}
				command.execute( context, painter );				
			}
		}

		/**
		 * Produces the string expression following the production rules
		 * with depth passed as an argument.
		 */
		@Override
		public String generate( int k ) {
			
			String expression = new String( axiom );

			for( int i = 0; i < k; i++ ) {
				
				int index = 0;
				while( index < expression.length() ) {
					String product = productions.get( expression.charAt( index ) );
					//if the symbol is contained in production rules
					if ( product != null ) {
						expression = expression.substring(0,index) + product + expression.substring( index+1 );
						index += product.length();
					} else {
						index++;
					}
				}
			}			
			return expression;
		}	
	}
	
	/**
	 * Returns new LSystem.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Configures LSystemBuilder's properties through directives given as a text parameter.
	 * Returns LSystemBuilder with such properties.
	 */
	@Override
	public LSystemBuilder configureFromText( String[] directives ) {
		
		for ( String line : directives ) {
			
			//empty line
			if ( line.isBlank() ) {
				continue;
			}	
			String[] words = line.split( "\\s+" );
			
			//unitLengthDegreeScaler
			if ( words[0].equals( "unitLengthDegreeScaler" ) ) {
				//no spaces
				if ( words.length == 2 ) {
					String[] operands = words[1].split( "\\/" );
					if ( operands.length == 1 ) {
						double number = isValidDouble( words[1] );
						setUnitLengthDegreeScaler( number );
						continue;
					}
					if ( operands.length == 2 ) {
						double number = isValidDouble( operands[0] ) / isValidDouble( operands[1] );
						setUnitLengthDegreeScaler( number );
						continue;
					}
				}
				//one space
				if ( words.length == 3 ) {
					//remove backslash
					if ( words[1].charAt( words[1].length()- 1 ) == '/' ) {
					 	words[1] = words[1].substring( 0, words[1].length() - 1 );
					} else {
						words[2] = words[2].substring( 1 );
					}
					double number = isValidDouble( words[1] ) / isValidDouble( words[2] );
					setUnitLengthDegreeScaler( number );
					continue;
				}
				if ( words.length == 4 ) {
					//check if '/' is in between
					if ( words[2].equals( "/" ) ) {
						double number = isValidDouble( words[1] ) / isValidDouble( words[3] );
						setUnitLengthDegreeScaler( number );
						continue;
					}
				}
				throw new IllegalArgumentException( "Invalid unit length degree scaler." );		
			}
			
			//other directives with one factor
			if ( words.length == 2 ) {
				//axiom
				if ( words[0].equals( "axiom" ) ) {
					setAxiom( words[1] );
					continue;
				}
				//else second word must be a number
				else {
					double number = isValidDouble( words[1] );
					
					switch ( words[0] ) {
						case "angle" : 
							setAngle( number );
							continue;
						case "unitLength" :
							setUnitLength( number );
							continue;
						default : 
							throw new IllegalArgumentException( "Invalid directive name." );						
					}
				}
			}
			//remaining directives
			switch ( words[0] ) {
				case "origin" :
					if ( words.length == 3 ) {
						setOrigin( isValidDouble( words[1] ), isValidDouble( words[2] ) );
						continue;
					}
					break;
				case "command" :
					if ( words.length >= 3 ) {
						if ( words[1].length() == 1 ) {
							String commandString = new String("");
							
							for ( int i = 2; i < words.length; i++ ) {
								commandString += words[i] + " ";
							}
							registerCommand( words[1].charAt( 0 ), commandString );
							continue;
						}
					}
					break;
				case "production" :
					if ( words.length == 3 ) {
						if ( words[1].length() == 1 ) {
							registerProduction( words[1].charAt( 0 ), words[2] );
							continue;
						}
					}
					break;
				
				default :
					throw new IllegalArgumentException( "Invalid directive name." );
			}
			throw new IllegalArgumentException( "Invalid directive format." );
		}
		return this;
	}
	
	/**
	 * Utility method for parsing number from text.
	 * @param stringDouble
	 * 		Textual representation of a double value.
	 * @throws NumberFormatException if the number representation is invalid.
	 * @return
	 * 		Number.
	 */
	private double isValidDouble( String stringDouble ) {
		double number = 0;
		try {
			number = Double.parseDouble( stringDouble );
		}
		catch ( NumberFormatException e ) {
		}
		return number;
	}

	/**
	 * Adds a new command whose input and output are specified in arguments to a commands dictionary.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder registerCommand( char symbol, String commandString ) {
		
		String[] input = commandString.split("\\s+");
		if ( input.length > 2 ) {
			throw new IllegalArgumentException( "A command consists of at most two words." );
		}
		//check if double value is valid
		double parameter = 0;
		if ( input.length == 2 ) {
			if ( !input[0].equals( "color" ) ) {
				parameter = isValidDouble( input[1] );
			}
			if ( input[0].equals( "put" ) || input[0].contentEquals( "pop" ) ) {
				throw new IllegalArgumentException( "Pop and push commands doesn't need a parameter." );
			}	
		}

		switch ( input[0] ) {
			case "draw" : 
				commands.put( symbol, new DrawCommand( parameter ) );
				break;
			case "skip" :
				commands.put( symbol, new SkipCommand( parameter ) );
				break;
			case "scale" :
				commands.put( symbol, new ScaleCommand( parameter ) );
				break;
			case "rotate" :
				commands.put( symbol, new RotateCommand( parameter ) );
				break;
			case "push" :
				commands.put( symbol, new PushCommand() );
				break;
			case "pop" :
				commands.put( symbol, new PopCommand() );
				break;
			case "color" :
				if ( !input[1].matches( "[0-9a-fA-F]{6}" ) ) {
					throw new IllegalArgumentException( "Invalid color expression." ); 
				}
				commands.put( symbol, new ColorCommand( Color.decode( "#" + input[1] )  ) );
				break;
			
			default:
				throw new IllegalArgumentException( "Invalid command name." ); 			
		}	
		return this;		
	}

	/**
	 * Adds a new production whose input and output are specified in arguments to a productions dictionary.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder registerProduction( char input, String output ) {
		//if there is already a rule with the same input
		if ( productions.get( input ) != null ) {
			throw new IllegalArgumentException( "There is already a rule with the same input symbol." );
		}
		productions.put( input, output );
		return this;
	}

	/**
	 * Updates the angle of a turtle's direction.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder setAngle( double angle ) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets axiom as given in an argument.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder setAxiom( String axiom ) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets origin point as given in arguments.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder setOrigin( double x, double y ) {
		this.origin =new Vector2D( x, y );
		return this;
	}

	/**
	 * Sets the new length of a step.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder setUnitLength( double unitLenght ) {
		this.unitLength = unitLenght;
		return this;
	}

	/**
	 * Sets the new value for a unit lenght degree scaler.
	 * Returns updated LSystemBuilder.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler( double unitLenghtDegreeScaler ) {
		this.unitLengthDegreeScaler = unitLenghtDegreeScaler;
		return this;
	}
	
}
