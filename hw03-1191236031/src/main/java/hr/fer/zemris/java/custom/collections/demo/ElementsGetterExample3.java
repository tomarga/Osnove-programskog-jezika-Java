package hr.fer.zemris.java.custom.collections.demo;

//import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterExample3 {
	public static void main(String[] args) {
//		Collection col = new ArrayIndexedCollection();
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement() );
		System.out.println("Jedan element: " + getter.getNextElement() );
		System.out.println("Jedan element: " + getter.getNextElement() );
		System.out.println("Jedan element: " + getter.getNextElement() );
		}
	
	/*
	 * Jedan element: Ivo
	 * Jedan element: Ana
	 * Jedan element: Jasna
	 * Exception in thread "main" java.util.NoSuchElementException:
	 */

}
