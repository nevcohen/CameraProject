package primitives;

/**
 * Vector class - Vector from the beginning of the axes to the given point
 */
public class Vector {

	private Point3D head;
	
	/**
	 * Ctor gets the vector end by Point3D
	 * @param _head - Point3D
	 */
	public Vector(Point3D _head) {
		super();
		this.head = _head;
		if(this.head.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
	}
	
	/**
	 * Ctor gets the vector end by point - 3 doubles - (x,y,z)
	 */
	public Vector(double x,double y,double z) {
		Point3D point = new Point3D(x,y,z); //--------
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
		this.head = point;
		
	}
	
	/**
	 * Ctor gets the vector end by point - 3 coordinates - (x,y,z)
	 */
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		Point3D point = new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
		this.head = point;
	}
	
	/**
	 * @return The point of the vector end
	 */
	public Point3D getHead() {
		return head;
	}
	
	/**
	 * Vector connection
	 * @param vector
	 * @return Result vector
	 */
	public Vector add(Vector vector){		
		return new Vector(vector.getHead().add(this));
	}
	
	/**
	 * Vector subtraction
	 * @param vector
	 * @return Result vector
	 */
	public Vector subtract(Vector vector){		
		return (vector.getHead().subtract(this.head));
	}
	
	/**
	 * Vector multiplication by a number - scalar
	 * @param scalar
	 * @return Result vector
	 */
	public Vector scale(double scalar){
		Point3D scaledCoordinates = new Point3D(this.head.getX_value()*scalar, this.head.getY_value()*scalar,this.head.getZ_value()*scalar);		
		return new Vector(scaledCoordinates);
	}
	
	/**
	 * Vector product
	 * @param vector
	 * @return Result vector
	 */
	public Vector crossProduct(Vector vector){
		Point3D crossproduct = new Point3D(this.head.getY_value()*vector.getHead().getZ_value()-this.head.getZ_value()*vector.getHead().getY_value(),
				-(this.head.getX_value()*vector.getHead().getZ_value()-this.head.getZ_value()*vector.getHead().getX_value()),
				this.head.getX_value()*vector.getHead().getY_value()-this.head.getY_value()*vector.getHead().getX_value()
				);
		return new Vector(crossproduct);
	}
	
	/**
	 * Scalar product
	 * @param vector
	 * @return The result of the product
	 */
	public double dotProduct(Vector vector){
		return this.head.getX_value()*vector.getHead().getX_value()+ this.head.getY_value()*vector.getHead().getY_value()+this.head.getZ_value()*vector.getHead().getZ_value();
	}
	
	/**
	 * Vector length squared
	 * @return The length
	 */
	public double lengthSquared(){
		return this.head.getX_value()*this.head.getX_value()+this.head.getY_value()*this.head.getY_value()+this.head.getZ_value()*this.head.getZ_value();
	}
	
	/**
	 * Vector length
	 * @return The length
	 */
	public double length(){
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * Normalize the current vector
	 * @return The vector on which the operation was performed
	 */
	public Vector normalize(){
		this.head = this.scale(1/this.length()).getHead(); //--------------
		return this;
	}
	/**
	 * Vector normalization
	 * @return New vector
	 */
	public Vector normalized(){
		return this.scale(1/this.length());
	}
	
	@Override
	public boolean equals(Object obj){
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Vector)) return false;
	      Vector other = (Vector)obj;
	      return this.head.equals(other.head);

	}

	/**
	 * @return The string format of the Vector -> (x,y,z).
	 */
	@Override
	public String toString() {
		return ""+head;
	}
	
	
}
