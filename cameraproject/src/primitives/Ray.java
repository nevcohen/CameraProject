package primitives;

/**
 * Ray class - By using a vector and a starting point
 */
public class Ray {

	private Point3D p0;
	private Vector dir;
	
	/**
	 * Ctor getting normalized vector and a starting point
	 * @param _p0
	 * @param _dir
	 */
	public Ray(Point3D _p0, Vector _dir) {
		super();
		p0 = _p0;
		dir = _dir; //-------
	}
	
	/**
	 * -----
	 * @return starting point
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * @return normalized vector
	 */
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
	
	/**
	 * The string format of the Ray, in format -> t(x,y,z) + (u,v,w)
	 */
	@Override
	public String toString() {
		return "t"+dir+" + "+p0;
	}
	
	
	
}
