package hr.fer.zemris.java.custom.collections.demo;

//import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterExample4 {
	
	public static void main(String[] args) {
//		Collection col = new ArrayIndexedCollection();
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " +  getter1.getNextElement() );
		System.out.println("Jedan element: " +  getter1.getNextElement() );
		System.out.println("Jedan element: " +  getter2.getNextElement() );
		System.out.println("Jedan element: " +  getter1.getNextElement() );
		System.out.println("Jedan element: " +  getter2.getNextElement() );

		}

	/*
	 *  Jedan element: Ivo
		Jedan element: Ana
		Jedan element: Ivo
		Jedan element: Jasna
		Jedan element: Ana
	 */
}
