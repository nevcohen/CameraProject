package primitives;

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
		
	}
	public Vector subtract(Point3D point){
		
	}
	public double distanceSquared(Point3D point){
		
	}
	public double distance(Point3D point){
		
	}
	@Override
	public boolean equals(Object object){
		
	}
	
	
}
