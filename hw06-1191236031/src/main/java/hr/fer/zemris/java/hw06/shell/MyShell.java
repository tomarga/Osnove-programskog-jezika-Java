package hr.fer.zemris.java.hw06.shell;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * A simple shell emulator command-line program.
 * 
 * @author Margarita Tolja
 *
 */
public class MyShell {
	
	private static Scanner sc;
	
	private static SortedMap<String, ShellCommand> commands;
	
	private static Environment environment = new Environment() {
		
		private Character promptSymbol;
		private Character multilineSymbol;
		private Character morelinesSymbol;
		
		@Override
		public String readLine() throws ShellIOException {
			String line = null;
			try {
				line = sc.nextLine();
			} catch ( NoSuchElementException e ) {
				throw new ShellIOException( "Unable to read line." ); 
			}
			return line;
		}
		@Override
		public void write( String text ) throws ShellIOException {
			if ( text == null ) {
				throw new ShellIOException( "Unable to write null string." );
			}
			System.out.print( text );
		}
		@Override
		public void writeln( String text ) throws ShellIOException {
			if ( text == null ) {
				throw new ShellIOException( "Unable to writeln null string." );
			}
			System.out.println( text );
		}
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return java.util.Collections.unmodifiableSortedMap( commands ); 
		}
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}
		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}
		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}
		
	};

	/**
	 * Manages input and output.
	 * @param args No argument values expected.
	 */
	public static void main(String[] args) {
		
		sc = new Scanner( System.in );
		
		//set environment's special symbols to defaults
		environment.setPromptSymbol( '>' );
		environment.setMorelinesSymbol( '\\' );
		environment.setMultilineSymbol( '|' );
		
		//valid commands
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		
		commands.put("exit", new ExitShellCommand() );
		commands.put( "symbol", new SymbolShellCommand() );
		commands.put( "charsets", new CharsetsShellCommand() );
		commands.put( "cat", new CatShellCommand() );
		commands.put( "ls", new LsShellCommand() );
		commands.put( "tree", new TreeShellCommand() );
		commands.put( "copy", new CopyShellCommand() );
		commands.put( "mkdir", new MkdirShellCommand() );
		commands.put( "hexdump", new HexdumpShellCommand() );
		commands.put( "help", new HelpShellCommand( commands ) );
		
		
		System.out.println( "Welcome to MyShell v 1.0" );
		
		ShellStatus status = null;
		do {
			System.out.print( environment.getPromptSymbol() + " " );
			
			String line = null;
			try {
				line = readLineOrLines();
			} catch ( ShellIOException e ) {
				System.out.println( e.getMessage() );
				break;
			}
			String commandName = line.split( "\\s+" )[0];
			String arguments = line.substring( commandName.length() );
			
			ShellCommand command = commands.get( commandName );
			if ( command == null ) {
				System.out.println( "Invalid command name." );
				continue;
			}
			
			status = command.executeCommand( environment, arguments );
		} while ( status != ShellStatus.TERMINATE );
		
		sc.close();
		
	}
	
	/**
	 * Reads one line or more lines if the command spans across multiple lines.
	 * @return Read lines.
	 * @throws ShellIOException If reading fails.
	 */
	private static String readLineOrLines() throws ShellIOException {
		
		char moreLines = environment.getMorelinesSymbol().charValue();
		char multiLines = environment.getMultilineSymbol().charValue();
		
		String lines = environment.readLine();
		 
		while ( !lines.isEmpty() && lines.charAt( lines.length() - 1 ) == moreLines ) {
			System.out.print( multiLines + " " );
			//the moreLines symbol is not read
			lines = lines.substring( 0, lines.length() - 1 ) + environment.readLine();
		}
		return lines;
	}

}
