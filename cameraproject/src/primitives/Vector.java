package primitives;

/**
 * Vector class - Vector from the beginning of the axes to the given point
 */
public class Vector {

	private Point3D head;
	
	/**
	 * Ctor gets the vector end by Point3D
	 * @param head - Point3D
	 */
	public Vector(Point3D head) {
		this.head = head;
		if(this.head.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
	}

	
/**
 * 
 * @param x the x value of the coordinate, as in: (x,y,z)
 * @param y the y value of the coordinate, as in: (x,y,z)
 * @param z the z value of the coordinate, as in: (x,y,z)
 */
	public Vector(double x,double y,double z) {
		this.head = new Point3D(x,y,z); 
		if(this.head.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");

	}
	
	/**
	 * 
	 * @param x the x value of the coordinate, as in: (x,y,z)
	 * @param y the y value of the coordinate, as in: (x,y,z)
	 * @param z the z value of the coordinate, as in: (x,y,z)
	 */
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		this.head = new Point3D(x,y,z);
		if((this.head).equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
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
		return (this.head.subtract(vector.getHead()));
	}
	
	/**
	 * Vector multiplication by a number - scalar
	 * @param scalar
	 * @return Result vector
	 */
	public Vector scale(double scalar){	
		if(scalar == 0)
			return null;
		return new Vector(new Point3D(this.head.getValueOfX()*scalar, this.head.getValueOfY()*scalar,this.head.getValueOfZ()*scalar));
	}
	
	/**
	 * Vector product
	 * @param vector
	 * @return Result vector
	 */
	public Vector crossProduct(Vector vector){
		return new Vector( new Point3D(this.head.getValueOfY()*vector.getHead().getValueOfZ()-this.head.getValueOfZ()*vector.getHead().getValueOfY(),
				-(this.head.getValueOfX()*vector.getHead().getValueOfZ()-this.head.getValueOfZ()*vector.getHead().getValueOfX()),
				this.head.getValueOfX()*vector.getHead().getValueOfY()-this.head.getValueOfY()*vector.getHead().getValueOfX()
				));
	}
	
	/**
	 * Scalar product
	 * @param vector
	 * @return The result of the product
	 */
	public double dotProduct(Vector vector){
		return this.head.getValueOfX()*vector.getHead().getValueOfX()+ this.head.getValueOfY()*vector.getHead().getValueOfY()+this.head.getValueOfZ()*vector.getHead().getValueOfZ();
	}
	
	/**
	 * Vector length squared
	 * @return The length
	 */
	public double lengthSquared(){
		return this.head.getValueOfX()*this.head.getValueOfX()+this.head.getValueOfY()*this.head.getValueOfY()+this.head.getValueOfZ()*this.head.getValueOfZ();
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
		double scalar = 1/this.length();
		this.head = new Point3D(this.head.getValueOfX()*scalar, this.head.getValueOfY()*scalar,this.head.getValueOfZ()*scalar);
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
