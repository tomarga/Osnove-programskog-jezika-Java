package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) {
		
		String filepath = args[0];
		String docBody = "";
		try {
			docBody = new String( Files.readAllBytes(Paths.get(filepath)),
			StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		System.out.println("doc body " + docBody );
		SmartScriptParser parser = null;
//		SmartScriptParser parser2 = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!" + e.getMessage());
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
			
//		try {
//			parser2 = new SmartScriptParser(originalDocumentBody);
//		} catch(SmartScriptParserException e) {
//			System.out.println("Unable to parse document!" + e.getMessage());
//			System.exit(-1);
//		} catch(Exception e) {
//			System.out.println("If this line ever executes, you have failed this class!");
//			System.exit(-1);
//		}
//		
//		DocumentNode document2 = parser2.getDocumentNode();
//		String originalDocumentBody2 = document2.toString();
//		System.out.println(originalDocumentBody2); // should write something like original
//		// content of docBody
//		
//		System.out.println( document.equals(document2));
	}

}
