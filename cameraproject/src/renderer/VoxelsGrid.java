package renderer;

import java.util.List;
import static primitives.Util.*;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;

/**
 * 
 * @author Eli and nevo
 *
 *         The Voxel grid functions like a rubix cube: the scene is built into
 *         cubes, called voxels, and the idea is to check what geometries are
 *         inside each voxel instead of searching for each geometry in the
 *         scene.
 *
 */
public class VoxelsGrid {
	/**
	 * 
	 * @author Eli and nevo
	 * 
	 *         The voxel rays are designed to traverse through the voxels.
	 */
	public class RayVoxels {
		/**
		 * The ray reaching the scene
		 */
		public Ray mainRay;
		/**
		 * x - The x coordinate of the point that the ray hit the voxelGrid. y - The y
		 * coordinate of the point that the ray hit the voxelGrid. z -The z coordinate
		 * of the point that the ray hit the voxelGrid.
		 */
		private int x, y, z;
		/**
		 * The geometries in the voxels (or the scene)
		 */
		public Geometries voxelGeometries;
		/**
		 * stepX - How much we progress on the X axis stepY - How much we progress on
		 * the Y axis stepZ - How much we progress on the Z axis
		 */
		private int stepX = 1, stepY = 1, stepZ = 1;
		/**
		 * tMaxX - How much we progress on the X axis - inside the grid itself. tMaxY -
		 * How much we progress on the Y axis - inside the grid itself. tMaxZ - How much
		 * we progress on the Z axis - inside the grid itself.
		 */
		private double tMaxX = Double.POSITIVE_INFINITY, tMaxY = Double.POSITIVE_INFINITY,
				tMaxZ = Double.POSITIVE_INFINITY;
		/**
		 * tDeltaX - How much we progress on the X axis - as the ray. t is the t(x,y,z)
		 * of the ray tDeltaY - How much we progress on the Y axis - as the ray. t is
		 * the t(x,y,z) of the ray tDeltaZ - How much we progress on the Z axis - as the
		 * ray. t is the t(x,y,z) of the ray
		 */
		private double tDeltaX = Double.POSITIVE_INFINITY, tDeltaY = Double.POSITIVE_INFINITY,
				tDeltaZ = Double.POSITIVE_INFINITY;

		private int rayID;

		private double maxDistance;

		private List<GeoPoint> oldIntersections;

		/**
		 * Calculating the voxels based on the ray
		 * 
		 * @param ray - the Basic ray
		 */
		public RayVoxels(Ray ray, double maxDistance) {
			mainRay = ray;
			this.maxDistance = maxDistance;
			if (startInFirstVoxel()) {
				Point3D firstIndex = getFirstVoxelIndex(mainRay.getP0());
				x = (int) firstIndex.getValueOfX();
				y = (int) firstIndex.getValueOfY();
				z = (int) firstIndex.getValueOfZ();
				voxelGeometries = allVoxels[z][y][x];
			} else
				mainRay = null;
		}

		/**
		 * A function to check if a point is in a given voxel.
		 * 
		 * @param point - the current point we are checking
		 * @return true if the point is in the voxel, false otherwise
		 */
		private boolean isInCurrentVoxel(Point3D point) {
			double dX = (point.getValueOfX() - minX) / voxelSize;
			double dY = (point.getValueOfY() - minY) / voxelSize;
			double dZ = (point.getValueOfZ() - minZ) / voxelSize;
			return alignZero(dX - x) >= 0 && alignZero(1 + x - dX) >= 0 && alignZero(dY - y) >= 0
					&& alignZero(1 + y - dY) >= 0 && alignZero(dZ - z) >= 0 && alignZero(1 + z - dZ) >= 0;
		}

		/**
		 * Find the GeoIntersections of our rubix cube.
		 * 
		 * @return the closest GeoPoint to the head of the ray ; in the current voxel.
		 */
		public GeoPoint findGeoIntersections() {
			if (voxelGeometries == null)
				return null;

			List<GeoPoint> intersections;
			if(useRayID)
				intersections = voxelGeometries.findGeoIntersections(mainRay, maxDistance, rayID);
			else
				intersections = voxelGeometries.findGeoIntersections(mainRay, maxDistance);
			GeoPoint firstIntersections = null;
			if (intersections != null) {
				if (oldIntersections != null)
					oldIntersections.addAll(intersections);
				else
					oldIntersections = intersections;
			}
			if (oldIntersections != null)
				firstIntersections = mainRay.findClosestGeoPoint(oldIntersections);
			if (firstIntersections != null && isInCurrentVoxel(firstIntersections.point))
				return firstIntersections;
			return null;
		}

		/**
		 * This program is super important. We find the details of the voxel, i.e where
		 * it hits, and how we ought to traverse the rubix cube.
		 * 
		 * So, aside from returning we initialize the 'max' and 'delta' fields, as
		 * written above
		 */
		private boolean startInFirstVoxel() {

			Point3D head = mainRay.getP0();
			double x = head.getValueOfX();
			double y = head.getValueOfY();
			double z = head.getValueOfZ();

			Point3D vHead = mainRay.getDir().head;
			double xV = vHead.getValueOfX();
			double yV = vHead.getValueOfY();
			double zV = vHead.getValueOfZ();

			// minX <= x+t*xV <= maxX

			double tXmin = (minX - x) / xV;
			double tXmax = (maxX - x) / xV;

			double temp;
			if (tXmin > tXmax) {
				temp = tXmin;
				tXmin = tXmax;
				tXmax = temp;
			}

			double tYmin = (minY - y) / yV;
			double tYmax = (maxY - y) / yV;

			if (tYmin > tYmax) {
				temp = tYmin;
				tYmin = tYmax;
				tYmax = temp;
			}

			if ((tXmin > tYmax) || (tYmin > tXmax))
				return false;

			if (tYmin > tXmin)
				tXmin = tYmin;

			if (tYmax < tXmax)
				tXmax = tYmax;

			double tZmin = (minZ - z) / zV;
			double tZmax = (maxZ - z) / zV;

			if (tZmin > tZmax) {
				temp = tZmin;
				tZmin = tZmax;
				tZmax = temp;
			}
			if ((tXmin > tZmax) || (tZmin > tXmax))
				return false;

			if (tZmin > tXmin)
				tXmin = tZmin;

			if (tZmax < tXmax)
				tXmax = tZmax;

			head = mainRay.getPoint(tXmin < 0 ? 0 : tXmin);
			x = head.getValueOfX() - minX;
			y = head.getValueOfY() - minY;
			z = head.getValueOfZ() - minZ;
			mainRay = new Ray(head, mainRay.getDir());

			if (xV < 0) {
				stepX = -1;
				tMaxX = (((int) (x / voxelSize)) * voxelSize - x) / xV;
				tDeltaX = -voxelSize / xV;
			} else if (xV > 0) {
				tMaxX = (((int) (x / voxelSize) + 1) * voxelSize - x) / xV;
				tDeltaX = voxelSize / xV;
			}
			if (isZero(tMaxX))
				tMaxX = tDeltaX;

			if (yV < 0) {
				stepY = -1;
				tMaxY = (((int) (y / voxelSize)) * voxelSize - y) / yV;
				tDeltaY = -voxelSize / yV;
			} else if (yV > 0) {
				tMaxY = (((int) (y / voxelSize) + 1) * voxelSize - y) / yV;
				tDeltaY = voxelSize / yV;
			}
			if (isZero(tMaxY))
				tMaxY = tDeltaY;

			if (zV < 0) {
				stepZ = -1;
				tMaxZ = (((int) (z / voxelSize)) * voxelSize - z) / zV;
				tDeltaZ = -voxelSize / zV;
			} else if (zV > 0) {
				tMaxZ = (((int) (z / voxelSize) + 1) * voxelSize - z) / zV;
				tDeltaZ = voxelSize / zV;
			}
			if (isZero(tMaxZ))
				tMaxZ = tDeltaZ;

			sourceRayID++;
			rayID = sourceRayID;
			return true;

		}

		/**
		 * A function to check when we leave the grid, i.e is there another voxel the
		 * ray hits that needs examining.
		 * 
		 * @return True if there is another voxel to check, False otherwise.
		 */
		public boolean nextVoxel() {
			Geometries geometries = null;
			while (geometries == null) {
				if (tMaxX < tMaxY) {
					if (tMaxX < tMaxZ) {
						x = x + stepX;
						if (x >= justOutX || x < 0)
							return false; // outside grid
						tMaxX = tMaxX + tDeltaX;
					} else {
						z = z + stepZ;
						if (z >= justOutZ || z < 0)
							return false;
						tMaxZ = tMaxZ + tDeltaZ;
					}
				} else {
					if (tMaxY < tMaxZ) {
						y = y + stepY;
						if (y >= justOutY || y < 0)
							return false;
						tMaxY = tMaxY + tDeltaY;
					} else {
						z = z + stepZ;
						if (z >= justOutZ || z < 0)
							return false;
						tMaxZ = tMaxZ + tDeltaZ;
					}
				}
				geometries = allVoxels[z][y][x];
			}
			voxelGeometries = geometries;
			return true;
		}

	}

	/**
	 * As we discussed, the grid of voxels resembles a rubix cube. The information
	 * on each cube is held in a 3 dimentional array, holding the indexes of each
	 * voxel.
	 */
	private Geometries[][][] allVoxels;
	/**
	 * A seperate calculation is needed for any geometries that can't fit into
	 * voxels, a.k.a infinite geometries.
	 */
	public Geometries infiniteGeometries;
	/**
	 * The size of each voxel. i.e if voxel size is 1, each voxel would be 1x1x1 in
	 * size, as they are all cubes.
	 */
	private double voxelSize;
	/**
	 * parameters to check the size of the rubix cube. we get the max and min of
	 * each coordinate.
	 */
	private double minX, minY, minZ, maxX, maxY, maxZ;
	/**
	 * pramaters to store the final coordinate of the rubix cube, to determine when
	 * we left it.
	 */
	private int justOutX, justOutY, justOutZ;
	/**
	 * parameters to store the size of each side. (total)
	 */
	private double xDimension, yDimension, zDimension;
	/**
	 * A distance used to calculate an area in the sphere that is in the sphere, not
	 * near the center.
	 */
	private static final double DISTANCE_K = (2.0 - Math.sqrt(2)) / 2.0;

	private boolean useRayID = false;

	private int sourceRayID = 0;

	/**
	 * ---------------
	 */
	public void setRayID() {
		useRayID = true;
	}

	/**
	 * A constructor that gets the geometries and the designated size of each voxel.
	 * And builds the rubix cube!
	 * 
	 * @param sceneGeometries - All of the geometries in the given scene
	 * @param voxelSize       - The designated size of each voxel. i.e if voxel size
	 *                        is 1, each voxel would be 1x1x1 in size, as they are
	 *                        all cubes.
	 */
	public VoxelsGrid(Geometries sceneGeometries, double voxelSize) {
		this.voxelSize = voxelSize;
		if (voxelSize <= 0)
			throw new IllegalArgumentException("Voxel size must be more then 0");
		allVoxels = setGeometriesInVoxel(sceneGeometries);
	}

	/**
	 * A function to place the geometries inside the voxels.
	 * 
	 * @param sceneGeometries - All of the geometries in the given scene
	 * @return The three dimentional array with each voxel, and what geometries are
	 *         hidden inside it.
	 */
	private Geometries[][][] setGeometriesInVoxel(Geometries sceneGeometries) {
		List<GeoPoint> sceneMinMax = sceneGeometries.getBoxMinMaxVertices();

		int sceneAllBoxLen = sceneMinMax != null ? sceneMinMax.size() : 0;
		for (int i = 0; i < sceneAllBoxLen; i += 1) {
			Geometry current = sceneMinMax.get(i).geometry;
			if (current instanceof Plane) {
				if (infiniteGeometries == null)
					infiniteGeometries = new Geometries();
				infiniteGeometries.add(current);
				sceneMinMax.remove(i);
				sceneAllBoxLen--;
				i--;
			}
		}

		if (sceneAllBoxLen == 0)
			return null;

		setGridSize(voxelSize, sceneMinMax, sceneAllBoxLen);

		Geometries[][][] voxelList = new Geometries[justOutZ][justOutY][justOutX];

		for (int i = 0; i < sceneAllBoxLen; i += 2) {
			Geometry current = sceneMinMax.get(i).geometry;
			Point3D min = sceneMinMax.get(i).point;
			Point3D max = sceneMinMax.get(i + 1).point;

			int xMinI = (int) ((min.getValueOfX() - minX) / voxelSize),
					yMinI = (int) ((min.getValueOfY() - minY) / voxelSize),
					zMinI = (int) ((min.getValueOfZ() - minZ) / voxelSize);
			int xMaxI = (int) ((max.getValueOfX() - minX) / voxelSize),
					yMaxI = (int) ((max.getValueOfY() - minY) / voxelSize),
					zMaxI = (int) ((max.getValueOfZ() - minZ) / voxelSize);

			double d; // 2*d = 2r - The size of the cube inside the sphere = 2r - r*root(2) =(about)
						// 0.6r
			int range = 0;
			boolean isSphere = false;
			if (current instanceof Sphere) {
				d = ((Sphere) current).getRadius() * DISTANCE_K;
				range = (int) (d / voxelSize) + 1;
				isSphere = true;
			}
			for (int iZ = zMinI; iZ <= zMaxI; iZ++)
				for (int iY = yMinI; iY <= yMaxI; iY++)
					for (int iX = xMinI; iX <= xMaxI; iX++) {
						if (!isSphere || ((iX - xMinI <= range) || (iY - yMinI <= range) || (iZ - zMinI <= range)
								|| (xMaxI - iX <= range) || (yMaxI - iY <= range) || (zMaxI - iZ <= range))) {
							if (voxelList[iZ][iY][iX] == null)
								voxelList[iZ][iY][iX] = new Geometries(current);
							else
								voxelList[iZ][iY][iX].add(current);
						}
					}
		}
		return voxelList;
	}

	/**
	 * --------------------------
	 * 
	 * @param mainRay
	 * @return
	 */
	public Point3D getFirstVoxelIndex(Point3D head) {
		int iX = (int) ((head.getValueOfX() - minX) / voxelSize);
		int iY = (int) ((head.getValueOfY() - minY) / voxelSize);
		int iZ = (int) ((head.getValueOfZ() - minZ) / voxelSize);

		return new Point3D((iX == justOutX ? iX - 1 : iX), (iY == justOutY ? iY - 1 : iY),
				(iZ == justOutZ ? iZ - 1 : iZ));
	}

	/**
	 * A function that sets the grid and determines it. Most of the calculations,
	 * the idea is to expand the grid to fit the voxel size.
	 * 
	 * @param voxelSize      - The designated size of each voxel. i.e if voxel size
	 *                       is 1, each voxel would be 1x1x1 in size, as they are
	 *                       all cubes.
	 * @param sceneMinMax    - A list holding the minimum and max points, of each
	 *                       box.
	 * @param sceneAllBoxLen - the length of the list of the last parameter
	 */
	private void setGridSize(double voxelSize, List<GeoPoint> sceneMinMax, int sceneAllBoxLen) {
		minX = Double.POSITIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		minZ = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		maxZ = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < sceneAllBoxLen; i += 2) {
			Point3D min = sceneMinMax.get(i).point;
			double x = min.getValueOfX(), y = min.getValueOfY(), z = min.getValueOfZ();
			if (x < minX)
				minX = x;
			if (y < minY)
				minY = y;
			if (z < minZ)
				minZ = z;
			Point3D max = sceneMinMax.get(i + 1).point;
			x = max.getValueOfX();
			y = max.getValueOfY();
			z = max.getValueOfZ();
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			if (z > maxZ)
				maxZ = z;
		}

		double xAdd = (voxelSize - ((maxX - minX) % voxelSize)) / 2.0;
		double yAdd = (voxelSize - ((maxY - minY) % voxelSize)) / 2.0;
		double zAdd = (voxelSize - ((maxZ - minZ) % voxelSize)) / 2.0;
		minX -= xAdd;
		minY -= yAdd;
		minZ -= zAdd;
		maxX += xAdd;
		maxY += yAdd;
		maxZ += zAdd;

		xDimension = maxX - minX;
		justOutX = (int) (xDimension / voxelSize);
		yDimension = maxY - minY;
		justOutY = (int) (yDimension / voxelSize);
		zDimension = maxZ - minZ;
		justOutZ = (int) (zDimension / voxelSize);
	}

}
