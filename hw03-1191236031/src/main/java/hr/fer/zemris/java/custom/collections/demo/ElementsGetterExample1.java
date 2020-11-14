package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
//import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterExample1 {
	
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
//		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		}
		
	/*
	 * Ima nepredanih elemenata: true
	 * Jedan element: Ivo
	 * Ima nepredanih elemenata: true
	 * Jedan element: Ana
	 * Ima nepredanih elemenata: true
	 * Jedan element: Jasna
	 * Ima nepredanih elemenata: false
	 * Exception in thread "main" java.util.NoSuchElementException:
	 */
	
}
