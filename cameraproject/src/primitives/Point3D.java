package primitives;

/**
 * Point3D class - Represented by 3 coordinates (x, y, z)
 */
public class Point3D {

	private Coordinate x;
	private Coordinate y;
	private Coordinate z;
	
	public static Point3D ZERO = new Point3D(0,0,0); 
	
	
/**
 * @param x the x coordinate of a given point: (x,y,z)
 * @param y the y coordinate of a given point: (x,y,z)
 * @param z the z coordinate of a given point: (x,y,z)
 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	

	public Point3D(double valueForx, double valueFory, double valueForz) {
		this.x = new Coordinate(valueForx);
		this.y = new Coordinate(valueFory);
		this.z = new Coordinate(valueForz);
	}

	protected double getValueOfX() {
		return x.coord;
	}
	protected double getValueOfY() {
		return y.coord;
	}
	protected double getValueOfZ() {
		return z.coord;
	}
	
	/**
	 * Adding a vector to a point
	 * @param v - The vector to add
	 * @return New point
	 */
	public Point3D add(Vector v){
		return new Point3D(this.x.coord+v.getHead().getValueOfX(),this.y.coord+v.getHead().getValueOfY(),this.z.coord+v.getHead().getValueOfZ());
	}
	
	/**
	 * Vector subtraction
	 * @param point
	 * @return Vector from the second point to the point on which the action is performed, null if the subtraction results in the head of the vector
	 * becoming the point 0
	 */
	public Vector subtract(Point3D point){
		return new Vector(this.x.coord-point.x.coord,this.y.coord-point.y.coord,this.z.coord-point.z.coord);
	}
	
	/**
	 * Distance between two points (squared)
	 * @param point
	 * @return The Distance (squared)
	 */
	public double distanceSquared(Point3D point){
		return ((this.x.coord-point.x.coord)*(this.x.coord-point.x.coord)+ 
				(this.y.coord-point.y.coord)*(this.y.coord-point.y.coord)+
				(this.z.coord-point.z.coord)*(this.z.coord-point.z.coord));
	}
	
	/**
	 * Distance between two points
	 * @param point
	 * @return The Distance
	 */
	public double distance(Point3D point){
		return Math.sqrt(this.distanceSquared(point));
	}
	
	@Override
	public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D)obj;
        return this.x.equals(other.x)&&this.y.equals(other.y)&&this.z.equals(other.z);
	}

	/**
	 * @return The string format of the Point3D, in format -> (x, y, z).
	 */
	@Override
	public String toString() {
		return "("+x+", "+y+", "+z+")";
	}
	

}
