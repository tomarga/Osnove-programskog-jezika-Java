package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class <code>UniqueNumbers</code> enables users to work 
 * with integer data in binary tree form.
 * 
 * <p>User specifies integers to add in binary tree structure 
 * through keyboard input.</p> 
 * 
 * @author Margarita Tolja
 */
public class UniqueNumbers {
	
	/**
	 * Class that represent one node in binary tree.
	 */
	static class TreeNode {
		TreeNode left, right;
		int value;
	}
	
	/**
	 * Function adds new node with given value
	 * to given binary tree, so that its structure stays as
	 * expected. If there is already a node with the same value, 
	 * nothing happens.
	 * 
	 * @param root
	 * @param newNodeValue
	 * @return TreeNode of binary tree from argument
	 */
	public static TreeNode addNode( TreeNode root, int newNodeValue ) {
		if ( root == null ) {
			root = new TreeNode();
			root.value = newNodeValue;
		}
		else if ( newNodeValue > root.value ) {
			root.right = addNode( root.right, newNodeValue );
		}		
		else if ( newNodeValue < root.value ) {
			root.left = addNode( root.left, newNodeValue );
		}		
		return root;
	}
	
	/**
	 * Function returns number of nodes in a binary tree
	 * with given root.
	 * 
	 * @param root
	 * @return int value length
	 */
	public static int treeSize( TreeNode root ) {				
		if ( root == null ) {
			return 0;
		}
		
		int length = 1;
		if ( root.left != null ) {
			length += treeSize( root.left );
		}
		if ( root.right != null ) {
			length += treeSize( root.right );
		}
		
		return length;
	}
	
	/**
	 * Function checks if there is a node with specified value in 
	 * binary tree with given root.
	 * 
	 * @param root of binary tree where value is searched
	 * @param value 
	 * @return <code>true</code> if there is such node in specified tree;
	 * <code>false</code> otherwise.
	 */
	public static boolean containsValue( TreeNode root, int value ) {
		if ( root == null ) {
			return false;
		}
		if ( root.value == value ) {
			return true;
		}
		if ( root.value < value ) {
			return containsValue( root.right, value );
		}
		if ( root.value > value ) {
			return containsValue( root.left, value );
		}
		return false;
	}
	
	/**
	 * Function outputs values of nodes in binary tree 
	 * with given root in ascending order.
	 * 
	 * @param root
	 */
	public static void outputAscending( TreeNode root ) {
		if ( root == null ) {
			return;
		}
		outputAscending( root.left );
		System.out.print( root.value + " ");
		outputAscending( root.right );
	}
	
	/**
	 * Function outputs values of nodes in binary tree 
	 * with given root in descending order.
	 * 
	 * @param root
	 */
	public static void outputDescending( TreeNode root ) {
		if ( root == null ) {
			return;
		}
		outputDescending( root.right );
		System.out.print( root.value + " ");
		outputDescending( root.left );
		
	}
	
	/**
	 * Function manages user input and prints 
	 * corresponding messages.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) { 
		Scanner sc = new Scanner( System.in );

		TreeNode binaryTree = null;		
		while( true ) {
			System.out.print( "Unesite broj > " );
			if ( sc.hasNextInt() ) {
				
				int newValue = sc.nextInt();
				if ( containsValue( binaryTree, newValue ) ) {
					System.out.println( "Broj već postoji. Preskačem." );
				}
				else {
					binaryTree = addNode( binaryTree, newValue );
					System.out.println( "Dodano." );
				}			
			}
			else {
				String input = sc.next();
				if ( input.compareTo( "kraj" ) == 0 ) {
					break;
				}
				else {
					System.out.println( "'" + input + "' nije cijeli broj." );
				}
			}
		}		
		System.out.print( "Ispis od najmanjeg: " );
		outputAscending( binaryTree );
		System.out.print( "\nIspis od najvećeg: " );
		outputDescending( binaryTree );
			
		sc.close();
	}
}
