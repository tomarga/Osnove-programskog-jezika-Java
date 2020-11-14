package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	

	@Test
	public void extra1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getClass() );
	}
	
	@Test
	public void extra2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getClass() );
	}
	
	@Test
	public void extra3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getClass() );
	}
	
	@Test
	public void extra4() {
		String text = readExample(4);
	
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
		
	}
	
	@Test
	public void extra5() {
		String text = readExample(5);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	@Test
	public void extra6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 2, document.numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getClass() );
		assertEquals( EchoNode.class, document.getChild(1).getClass() );
	}
	
	@Test
	public void extra7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 2, document.numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getClass() );
		assertEquals( EchoNode.class, document.getChild(1).getClass() );
	}
	
	@Test
	public void extra8() {
		String text = readExample(8);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	@Test
	public void extra9() {
		String text = readExample(9);
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	//echo
	@Test
	public void echo1() {
		String text = "{$= 0 + * ^ - -9.99 variable varia_b @function \"string\\\"\" $}";
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( EchoNode.class, document.getChild(0).getClass() );
		assertEquals( 10, ( (EchoNode) document.getChild(0) ).getElements().length );
	}
	
	@Test
	public void echo2() {
		String text = "{$= 0 + * ^ - 9.99 variable varia_b @function \"stri\\bng\\\"\" $}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	//for
	
	@Test
	public void for1() {
		String text = "{$FOR variab_33le 12 3 $}";
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( ForLoopNode.class, document.getChild(0).getClass() );
		assertEquals( "variab_33le", ( (ForLoopNode) document.getChild(0)).getVariable().asText() );
		assertEquals( "12", ( (ForLoopNode) document.getChild(0)).getStartExpression().asText() );
		assertEquals( "3", ( (ForLoopNode) document.getChild(0)).getEndExpression().asText() );
	}
	
	@Test
	public void for2() {
		String text = "{$FOR v_ \"b\" b_ -5.5 $}";
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( ForLoopNode.class, document.getChild(0).getClass() );
		assertEquals( "v_", ( (ForLoopNode) document.getChild(0)).getVariable().asText() );
		assertEquals( "b", ( (ForLoopNode) document.getChild(0)).getStartExpression().asText() );
		assertEquals( "b_", ( (ForLoopNode) document.getChild(0)).getEndExpression().asText() );
		assertEquals( "-5.5", ( (ForLoopNode) document.getChild(0)).getStepExpression().asText() );
	}
	
	@Test
	public void for3() {
		String text = "{$FOR variab_33le 12 $}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	@Test
	public void for4() {
		String text = "{$FOR variab_33le 12 4 5 6 $}";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}
	
	@Test
	public void for5() {
		String text = "{$FOR variab_33le 12 4 5 6 $";
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser( text ));
	}

	@Test
	public void for6() {
		String text = "{$FOR variab_33le 1 2 3 $} bla {$END$}";
		SmartScriptParser parser = new SmartScriptParser( text );
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals( 1, document.numberOfChildren() );
		assertEquals( ForLoopNode.class, document.getChild(0).getClass() );
		assertEquals( 1, document.getChild(0).numberOfChildren() );
		assertEquals( TextNode.class, document.getChild(0).getChild(0).getClass() );
		
	}
	
	private String readExample(int n) {
	  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
	    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
	    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt").readAllBytes();
	    String text = new String(data, StandardCharsets.UTF_8);
	    return text;
	  } catch(IOException ex) {
	    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
	  }
	}
	
	

}
