package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * A smart script parser.
 * 
 * @author Margarita Tolja
 *
 */
public class SmartScriptParser {
	
	private SmartScriptLexer lexer;
	private DocumentNode documentNode;
	
	/**
	 * Initialises lexer with passed document body.
	 * @param documentBody
	 * 		Body of a document.
	 */
	public SmartScriptParser( String documentBody ) {
		lexer = new SmartScriptLexer( documentBody );	
		documentNode = new DocumentNode();
		parse();		
	}
	
	/**
	 * Parses the text into tree-node model using
	 * smart script lexer.
	 */
	private void parse() {
		//TODO split into smaller methods
		
		ObjectStack tree = new ObjectStack();
		tree.push( documentNode );
		
		try {
			Node newNode = null;
			Token currentToken = lexer.nextToken();
			while( currentToken.getType() != TokenType.EOF ) {
			
				//text state
				
				//text node
				if ( currentToken.getType() == TokenType.TEXT ) {
					newNode = new TextNode( (String) currentToken.getValue() );
					((Node) tree.peek()).addChildNode( newNode ); 
					currentToken = lexer.nextToken();
				}
				//eof
				if ( currentToken.getType() == TokenType.EOF ) {
					break;
				}
				//start tag
				if ( currentToken.getType() == TokenType.STARTTAG ) {
					lexer.setState( LexerState.TAG );
					currentToken = lexer.nextToken();
				}
				
				//tag state
				
				//echo node
				if ( currentToken.getType() == TokenType.EQUALITY ) {
					currentToken = lexer.nextToken();
					
					Element[] elements = new Element[0];
					Element newElement = null;
					while( currentToken.getType() != TokenType.ENDTAG ) {
						
						//variable
						if ( currentToken.getType() == TokenType.NAME ) {
							//add to elements array
							newElement = new ElementVariable( (String) currentToken.getValue() );
							elements = Arrays.copyOf( elements, elements.length + 1 );
							elements[elements.length-1] = newElement;
						}
						
						//String
						if ( currentToken.getType() == TokenType.STRING ) {
							//add to elements array
							newElement = new ElementString( (String) currentToken.getValue() );
							elements = Arrays.copyOf( elements, elements.length + 1 );
							elements[elements.length-1] = newElement;
						}
						
						//integer
						if ( currentToken.getType() == TokenType.INTEGER ) {
							//add to elements array
							Integer number = (Integer) currentToken.getValue();
							newElement = new ElementConstantInteger( number.intValue() );
							elements = Arrays.copyOf( elements, elements.length + 1 );
							elements[elements.length-1] = newElement;
						}
						
						//double
						if ( currentToken.getType() == TokenType.DOUBLE ) {
							//add to elements array
							Double number = (Double) currentToken.getValue();
							newElement = new ElementConstantDouble( number.doubleValue() );
							elements = Arrays.copyOf( elements, elements.length + 1 );
							elements[elements.length-1] = newElement;
						}
						
						//Operator
						if ( currentToken.getType() == TokenType.OPERATOR ) {
							//add to elements array
							newElement = new ElementOperator( (String) currentToken.getValue() );
							elements = Arrays.copyOf( elements, elements.length + 1 );
							elements[elements.length-1] = newElement;
						}
						
						//function
						if ( currentToken.getType() == TokenType.AT ) {
							
							//functions's form is @name
							currentToken = lexer.nextToken();
							if ( currentToken.getType() == TokenType.NAME ) {
								//add to elements array
								newElement = new ElementFunction( (String) currentToken.getValue() );
								elements = Arrays.copyOf( elements, elements.length + 1 );
								elements[elements.length-1] = newElement;
							}
							else {
								throw new SmartScriptParserException( "Invalid function expression." );
							}
						}		
						currentToken = lexer.nextToken();
					}
					//new echo node
					newNode = new EchoNode( elements );
					((Node) tree.peek()).addChildNode( newNode );
								
				}
				
				
				else if ( currentToken.getType() == TokenType.NAME ) {
					String name = (String) currentToken.getValue();
					
					//END node
					if ( name.equalsIgnoreCase( "END" ) ) {
						tree.pop();
						if ( tree.isEmpty() ) {
							throw new SmartScriptParserException( "Document contains more end tags"
									+ " than openned non-empty tags." );
						}
						currentToken = lexer.nextToken();
						if ( currentToken.getType() != TokenType.ENDTAG ) {
							throw new SmartScriptParserException( "$} expected." );
						}
					}
					
					//FOR node
					else if ( name.equalsIgnoreCase( "FOR" ) ) {
						currentToken = lexer.nextToken();
						
						//finding for loop parameters
						ElementVariable variable = null;
						Element start = null;
						Element end = null;
						Element step = null;
						
						//variable
						if ( currentToken.getType() == TokenType.NAME ) {
							variable = new ElementVariable( (String) currentToken.getValue() );	
							currentToken = lexer.nextToken();
						} else {
							throw new SmartScriptParserException( "For loop should start with variable name." );
						}
						//start, end, step
						
						int counter = 1;
						while( currentToken.getType() != TokenType.ENDTAG ) {
							
							if ( counter > 3 ) {
								throw new SmartScriptParserException( 
										"Too many arguments in for-loop expression." );
							}
							
							Element element = null;
							//variable
							if ( currentToken.getType() == TokenType.NAME ) {
								element = new ElementVariable( (String) currentToken.getValue() );	
							}
							//integer
							if ( currentToken.getType() == TokenType.INTEGER ) {
								Integer number = (Integer) currentToken.getValue();
								element = new ElementConstantInteger( number.intValue() );
							}
							//double
							if ( currentToken.getType() == TokenType.DOUBLE ) {
								Double number = (Double) currentToken.getValue();
								element = new ElementConstantDouble( number.doubleValue() );
							}
							//string
							if ( currentToken.getType() == TokenType.STRING ) {
								//add to elements array
								element = new ElementString( (String) currentToken.getValue() );
							}
							
							switch( counter ) {
								case 1:
									start = element;
									break;
								case 2:
									end = element;
									break;
								case 3:
									step = element;
									break;
							}		
							
							currentToken = lexer.nextToken();
							counter++;
						}
						//check if for loop is valid
						if ( start != null && end != null ) {
							newNode = new ForLoopNode( variable, start, end, step );
							((Node) tree.peek()).addChildNode( newNode );
							tree.push( newNode );
						} else {
							throw new SmartScriptParserException( "Too few arguments in for-loop expression." );
						}	
					}
				}
				else {
					throw new SmartScriptParserException( "Tag without name." );
				}	
				
				if ( currentToken.getType() == TokenType.ENDTAG ) {
					lexer.setState( LexerState.TEXT );
				}
				currentToken = lexer.nextToken();
			}
			
		} catch( LexerException e ) {
			throw new SmartScriptParserException( e.getMessage() );
		} catch( EmptyStackException e ) {
			throw new SmartScriptParserException( e.getMessage() );
		}
	}

	/**
	 * Document node getter.
	 * @return
	 * 		The node that represents the whole document.
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

}
