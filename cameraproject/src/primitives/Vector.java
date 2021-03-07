package primitives;

public class Vector {

	private Point3D head;
	
	public Vector(Point3D _head) {
		super();
		
		if(this.head.equals(new Point3D(0,0,0)))
			throw new IllegalArgumentException ("Can't construct an empty vector");
		
		this.head = _head;
	}
	
	public Vector(double x,double y,double z) {
		Point3D point = new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
		this.head = point;
		
	}
	
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		Point3D point = new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException ("Can't construct an empty vector");
		this.head = point;
	}
	
	public Point3D getHead() {
		return head;
	}



	public Vector add(Vector vector){
		
		return new Vector(vector.getHead().add(this));
	}
	

	public Vector subtract(Vector vector){
		
		return (vector.getHead().subtract(this.head));
	}
	
	public Vector scale(double scalar){
		Point3D scaledCoordinates = new Point3D(this.head.getX_value()*scalar, this.head.getY_value()*scalar,this.head.getZ_value()*scalar);
		
		return new Vector(scaledCoordinates);
	}
	
	public Vector crossProduct(Vector vector){
		Point3D crossproduct = new Point3D(this.head.getY_value()*vector.getHead().getZ_value()-this.head.getZ_value()*vector.getHead().getY_value(),
				-(this.head.getX_value()*vector.getHead().getZ_value()-this.head.getZ_value()*vector.getHead().getX_value()),
				this.head.getY_value()*vector.getHead().getX_value()-this.head.getX_value()*vector.getHead().getY_value()
				);
		return new Vector(crossproduct);
	}
	
	public double dotProduct(Vector vector){
		return this.head.getX_value()*vector.getHead().getX_value()+ this.head.getY_value()*vector.getHead().getY_value()+this.head.getZ_value()*vector.getHead().getZ_value();
	}
	
	public double lengthSquared(){
		return this.head.getX_value()*this.head.getX_value()+this.head.getY_value()*this.head.getY_value()+this.head.getZ_value()*this.head.getZ_value();
	}
	
	public double length(){
		return Math.sqrt(this.lengthSquared());
	}
	
	public Vector normalize(){

		this.head =this.scale(1/this.length()).getHead();
		return this;
	}
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

	@Override
	public String toString() {
		return ""+head;
	}
	
	
}
