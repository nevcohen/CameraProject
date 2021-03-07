package primitives;

public class Ray {

	private Point3D p0;
	private Vector dir;
	
	public Ray(Point3D _p0, Vector _dir) {
		super();
		p0 = _p0;
		dir = _dir;
	}
	public Ray(Ray ray) {
		super();
		p0 = ray.getP0();
		dir = ray.getDir();
	}

	public Point3D getP0() {
		return p0;
	}


	public Vector getDir() {
		return dir;
	}
	
	@Override
	public boolean equals(Object obj) {
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Ray)) return false;
	      Ray other = (Ray)obj;
	      return this.p0.equals(other.p0) && this.dir.equals(other.dir);

	}
	
	
	
}
