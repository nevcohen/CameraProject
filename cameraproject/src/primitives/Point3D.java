package primitives;

//import static primitives.Util.isZero;

public class Point3D {

	private Coordinate x;
	private Coordinate y;
	private Coordinate z;
	
	public static Point3D ZERO = new Point3D(0,0,0); 
	
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(double x_value, double y_value, double z_value) {
		super();
		this.x = new Coordinate(x_value);
		this.y = new Coordinate(y_value);
		this.z = new Coordinate(z_value);
	}
	
	public Coordinate getX() {
		return x;
	}
	public Coordinate getY() {
		return y;
	}
	public Coordinate getZ() {
		return z;
	}

	public double getX_value() {
		return x.coord;
	}
	public double getY_value() {
		return y.coord;
	}
	public double getZ_value() {
		return z.coord;
	}

	
	public Point3D add(Vector v){
	return new Point3D(this.x.coord+v.getHead().getX_value(),this.y.coord+v.getHead().getY_value(),this.z.coord+v.getHead().getZ_value());

	}
	public Vector subtract(Point3D point){
		return new Vector(this.x.coord-point.getX_value(),this.y.coord-point.getY_value(),this.z.coord-point.getZ_value());
	}
	public double distanceSquared(Point3D point){
		return ((this.x.coord-point.getX_value())*(this.x.coord-point.getX_value())+ 
				(this.y.coord-point.getY_value())*(this.y.coord-point.getY_value())+
				(this.z.coord-point.getZ_value())*(this.z.coord-point.getZ_value()));
	}
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

	@Override
	public String toString() {
		return "("+x+", "+y+", "+z+")";
	}
	

}
