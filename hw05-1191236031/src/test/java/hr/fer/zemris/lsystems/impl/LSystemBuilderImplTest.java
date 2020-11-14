package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;

public class LSystemBuilderImplTest {

	private LSystemBuilder lBuilder;
	
	//generate
	@Test
	public void generateZero() {
		lBuilder = new LSystemBuilderImpl()
				.registerProduction( 'F', "F+F--F+F" )
				.setAxiom( "F" );
		
		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F", lSystem.generate( 0 ) );
	}
	@Test
	public void generateOne() {
		lBuilder = new LSystemBuilderImpl()
				.registerProduction( 'F', "F+F--F+F" )
				.setAxiom( "F" );
		
		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F+F--F+F", lSystem.generate( 1 ) );
	}
	@Test
	public void generateTwo() {
		lBuilder = new LSystemBuilderImpl()
				.registerProduction( 'F', "F+F--F+F" )
				.setAxiom( "F" );

		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate( 2 ) );
	}
	
	
	//configureFromText
	@Test
	@Disabled
	public void configureFromTextGenereteZero() {
		String[] data = new String[] {
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		lBuilder = new LSystemBuilderImpl().configureFromText( data );
		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F", lSystem.generate( 0 ) );	
	}
	@Test
	//@Disabled
	public void configureFromTextGenereteOne() {
		String[] data = new String[] {
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"command G color ff0000",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		lBuilder = new LSystemBuilderImpl().configureFromText( data );
		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F+F--F+F", lSystem.generate( 1 ) );	
	}
	@Test
	@Disabled
	public void configureFromTextGenereteTwo() {
		String[] data = new String[] {
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		lBuilder = new LSystemBuilderImpl().configureFromText( data );
		LSystem lSystem = lBuilder.build();
		
		assertEquals( "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate( 2 ) );
	}
	
}
