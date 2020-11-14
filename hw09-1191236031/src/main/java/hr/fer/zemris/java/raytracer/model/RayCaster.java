package hr.fer.zemris.java.raytracer.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * A program that displays renderings of simple 3D scenes.
 * 
 * @author Margarita Tolja
 *
 */
public class RayCaster {
	
	/**
	 * Manages the GUI display.
	 * @param args	No argument values expected.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
			new Point3D(10,0,0),
			new Point3D(0,0,0),
			new Point3D(0,0,10),
			20, 20);
	}
	
	/**
	 * @return The ray tracer producer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce( Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer, AtomicBoolean flag ) {
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.difference( view, eye ).normalize();
				viewUp = viewUp.normalize();
				
				Point3D yAxis = viewUp.difference( viewUp, zAxis.scalarMultiply( zAxis.scalarProduct( viewUp ) ) );
				yAxis = yAxis.normalize();
				
				Point3D xAxis = zAxis.vectorProduct( yAxis ).normalize();
				Point3D scaledX = xAxis.scalarMultiply( horizontal / 2 );
				Point3D scaledY = yAxis.scalarMultiply( vertical / 2 );
				Point3D screenCorner = view.sub( scaledX ).add( scaledY ); 
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				
				int offset = 0;
							
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						
						Point3D operand1 = xAxis.scalarMultiply( ( double )horizontal * ( ( double )x / ( double )( width - 1 ) ) );
						Point3D operand2 = yAxis.scalarMultiply( ( double )vertical * ( ( double )y / ( double )( height - 1 ) ) );
						Point3D screenPoint = screenCorner.add( operand1 ).sub( operand2 );
						
						Ray ray = Ray.fromPoints( eye, screenPoint );
						tracer( scene, ray, rgb );
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				
				}
	
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Finds the closest object that the ray goes through and determines the
			 * pixel's colors.
			 * @param scene 	3D scene.
			 * @param ray		Ray starting at eye position.
			 * @param rgb		An array of determined color components of the pixel.
			 */
			private void tracer( Scene scene, Ray ray, short[] rgb ) {
				
				RayIntersection closest = findClosest( ray, scene );
				
				if ( closest == null ) {
					
					//there is no object that intersects with the ray
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
					return;
				}
				
				determineColorFor( scene, ray, closest, rgb );
			}
		
			/**
			 * Determines all three color components of the pixel, based on the light
			 * sources of the scene.
			 * @param scene 	3D scene.
			 * @param ray		Ray starting at eye position.
			 * @param closest 	The closest intersecting point of the ray and all objects in the scene.
			 * @param rgb		An array of determined color components of the pixel.
			 */
			private void determineColorFor( Scene scene, Ray ray, RayIntersection closest, short[] rgb ) {
						
				rgb[0] = 15;
				rgb[1] = 15;
				rgb[2] = 15;
				
				List<LightSource> lights = scene.getLights();
				for ( LightSource light : lights ) {
					
					Ray lightRay = Ray.fromPoints( light.getPoint(), closest.getPoint() );
					RayIntersection lightClosest = findClosest( lightRay, scene );
					if ( lightClosest != null ) {
						
						if ( lightClosest.getDistance() > light.getPoint().sub( closest.getPoint() ).norm() ) {
							continue;
						} else {
							
							Point3D eye = ray.direction;
							Point3D n = closest.getNormal();
							Point3D l = lightRay.direction;
							Point3D r =  n.scalarMultiply(n.scalarProduct(l) * 2).modifySub(l).normalize();
							
							double difusion0 =  Math.abs( closest.getKdr() * ( l.scalarProduct( n ) ) );
							double reflection0 = Math.abs( closest.getKrr() * Math.pow( r.scalarProduct( eye ), closest.getKrn() ) );
							
							double difusion1 =  Math.abs( closest.getKdg() * ( l.scalarProduct( n ) ) );
							double reflection1 = Math.abs( closest.getKrg() * Math.pow( r.scalarProduct( eye ), closest.getKrn() ) );
							
							double difusion2 =  Math.abs( closest.getKdb() * ( l.scalarProduct( n ) ) );
							double reflection2 = Math.abs( closest.getKrb() * Math.pow( r.scalarProduct( eye ), closest.getKrb() ) );

							rgb[0] += (short) (light.getR() * ( difusion0 + reflection0 ) );
							rgb[1] += (short) (light.getG() * ( difusion1 + reflection1 ) );
							rgb[2] += (short) (light.getB() * ( difusion2 + reflection2 ) );
							
						}
					}
				}
			}
			
			/**
			 * Finds the closest intersection of the ray and an object in the scene.
			 * @param ray		Ray.
			 * @param scene		3D scene.
			 * @return			The closest intersection.
			 */
			private RayIntersection findClosest( Ray ray, Scene scene ) {
				
				List<GraphicalObject> objects = scene.getObjects();
				int n = objects.size();
				RayIntersection closest = null;
				
				//find first not null intersection
				int i;
				for ( i = 0; i < n; i++  ) {
					closest = objects.get( i ).findClosestRayIntersection( ray );
					if ( closest != null ) {
						break;
					}
				}
				for ( ;i < n; i++ ) {
					RayIntersection current = objects.get( i ).findClosestRayIntersection( ray );
					if ( current != null && ( closest.getDistance() > current.getDistance() ) ) {
						closest = current;
					}
				}
				return closest;
			}
			
		};
	}
}
