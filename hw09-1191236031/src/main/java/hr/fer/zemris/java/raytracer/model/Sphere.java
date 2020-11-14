package hr.fer.zemris.java.raytracer.model;

/**
 * A class that represents a sphere in the scene.
 * 
 * @author Margarita Tolja
 *
 */
public class Sphere extends GraphicalObject {
	
	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;
	
	/**
	 * Initialises the sphere.
	 * @param center		Centre of the sphere.
	 * @param radius		Radius of the sphere.
	 * @param kdr			Diffusion coefficient - red.
	 * @param kdg			Diffusion coefficient - green.
	 * @param kdb			Diffusion coefficient - blue.
	 * @param krr			Reflection coefficient - red.
	 * @param krg			Reflection coefficient - green.
	 * @param krb			Reflection coefficient - blue.
	 * @param krn			Shininess coefficient.
	 */
	public Sphere( Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn ) {
	
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Finds the closest point where the ray intersects the sphere.
	 */
	@Override
	public RayIntersection findClosestRayIntersection( Ray ray ) {
		
		Point3D OriginToCenter = center.difference( center, ray.start );
		double OriginCenterDistance = OriginToCenter.scalarProduct( ray.direction );
		
		//if direction is not pointing towards the sphere
		if ( OriginCenterDistance < 0 ) {
			return null;
		}
		
		double RayCenterDistance = OriginToCenter.scalarProduct( OriginToCenter ) - Math.pow( OriginCenterDistance, 2 );;
		RayCenterDistance = Math.sqrt( RayCenterDistance );
		//if there is no intersection
		if ( RayCenterDistance > radius ) {
			return null;
		}
		
		double HalfOfSegmentLength = Math.sqrt( Math.pow( radius, 2 ) - Math.pow( RayCenterDistance, 2 ) );
		
		double lambda1 = RayCenterDistance - HalfOfSegmentLength; 
		double lambda2 = RayCenterDistance + HalfOfSegmentLength;
		
		if ( lambda1 > lambda2) {
			double temp = lambda1;
			lambda1 = lambda2;
			lambda2 = temp;
		}

		if (lambda1 < 0) {
			// if t0 is negative, let's use t1 instead
			lambda1 = lambda2;
		}
		if (lambda1 < 0) {
			// both t0 and t1 are negative
			return null; 
		}
		
		Point3D closest = ray.start.add( ray.direction.scalarMultiply( lambda1 ) );
		double distance = lambda1;
		Point3D normal = closest.difference( closest, center ).normalize();
		
		return new RayIntersectionImpl( closest, distance, true, normal, kdr, kdg, kdb, krr, krg, krb, krn );
	}

}
