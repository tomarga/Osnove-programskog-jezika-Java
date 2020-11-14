package hr.fer.zemris.java.raytracer.model;

/**
 * A basic implementation of ray intersection interface.
 * 
 * @author Margarita Tolja
 *
 */
public class RayIntersectionImpl extends RayIntersection {
	
	private Point3D point;
	private double distance;
	private boolean outer;
	private Point3D normal;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 *  * Initialises ray intersection with passed values.
	 * @param point			The point of intersection.
	 * @param distance		The distance from origin to intersection.
	 * @param outer			Flag explaining whether the intersection is inner or outer.
	 * @param normal		The normal of intersection.
	 * @param kdr			Diffusion coefficient - red.
	 * @param kdg			Diffusion coefficient - green.
	 * @param kdb			Diffusion coefficient - blue.
	 * @param krr			Reflection coefficient - red.
	 * @param krg			Reflection coefficient - green.
	 * @param krb			Reflection coefficient - blue.
	 * @param krn			Shininess coefficient.
	 */
	protected RayIntersectionImpl( Point3D point, double distance, boolean outer, Point3D normal, 
			 double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn ) {
		super(normal, krn, outer);
		this.krn = krn;
		this.normal = normal;
		this.point = point;
		this.distance = distance;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
	}

	/**
	 * @return the normal.
	 */
	@Override
	public Point3D getNormal() {
		return normal;
	}

	/**
	 * @return the kdr
	 */
	@Override
	public double getKdr() {
		return kdr;
	}

	/**
	 * @return the kdg
	 */
	@Override
	public double getKdg() {
		return kdg;
	}

	/**
	 * @return the kdb
	 */
	@Override
	public double getKdb() {
		return kdb;
	}

	/**
	 * @return the krr
	 */
	@Override
	public double getKrr() {
		return krr;
	}

	/**
	 * @return the krg
	 */
	@Override
	public double getKrg() {
		return krg;
	}

	/**
	 * @return the krb
	 */
	@Override
	public double getKrb() {
		return krb;
	}

	/**
	 * @return the krn
	 */
	@Override
	public double getKrn() {
		return krn;
	}

	/**
	 * @return the point
	 */
	@Override
	public Point3D getPoint() {
		return point;
	}

	/**
	 * @return the distance
	 */
	@Override
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the outer
	 */
	@Override
	public boolean isOuter() {
		return outer;
	}

}
