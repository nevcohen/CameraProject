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

public class VoxelsGrid {

	public class RayVoxels {
		public Ray mainRay;
		private int x, y, z;
		public Geometries voxelGeometries;
		private int stepX = 1, stepY = 1, stepZ = 1;
		private double tMaxX = 0, tMaxY = 0, tMaxZ = 0;
		private double tDeltaX = Double.POSITIVE_INFINITY, tDeltaY = Double.POSITIVE_INFINITY,
				tDeltaZ = Double.POSITIVE_INFINITY;

		public RayVoxels(Ray ray) {
			mainRay = ray;
			Point3D firstPoint = firstVoxel();
			if (firstPoint != null) {
				List<Integer> firstIndex = getVoxelIndex(firstPoint);
				x = firstIndex.get(0);
				y = firstIndex.get(1);
				z = firstIndex.get(2);
				voxelGeometries = allVoxels[z][y][x];
			} else
				mainRay = null;
		}

		private boolean isInCurrentVoxel(Point3D point) {
			Point3D head = mainRay.getP0();
			Point3D dir = mainRay.getDir().head;
			// h + tv = p => t = (p-h)/v
			double tX = (point.getValueOfX() - head.getValueOfX()) / dir.getValueOfX();
			double tY = (point.getValueOfY() - head.getValueOfY()) / dir.getValueOfY();
			double tZ = (point.getValueOfZ() - head.getValueOfZ()) / dir.getValueOfZ();

			return alignZero(tMaxX - tX) >= 0 || alignZero(tMaxY - tY) >= 0 || alignZero(tMaxZ - tZ) >= 0;
		}

		public GeoPoint findGeoIntersections() {
			if (voxelGeometries == null)
				return null;
			List<GeoPoint> intersections = voxelGeometries.findGeoIntersections(mainRay);
			if (infiniteGeometries != null) {
				if (intersections != null)
					intersections.addAll(infiniteGeometries.findGeoIntersections(mainRay));
				else
					intersections = infiniteGeometries.findGeoIntersections(mainRay);
			}
			GeoPoint firstIntersections = intersections == null ? null : mainRay.findClosestGeoPoint(intersections);
			if (firstIntersections != null && isInCurrentVoxel(firstIntersections.point))
				return firstIntersections;
			return null;
		}

		private Point3D firstVoxel() {
			Point3D head = mainRay.getP0();
			double x = head.getValueOfX();
			double y = head.getValueOfY();
			double z = head.getValueOfZ();

			Point3D vHead = mainRay.getDir().head;
			double xV = vHead.getValueOfX();
			double yV = vHead.getValueOfY();
			double zV = vHead.getValueOfZ();

			// minX <= x+t*xV <= maxX
			double ftMinX = xV == 0 ? Double.NEGATIVE_INFINITY : (xV > 0 ? (minX - x) / xV : (maxX - x) / xV);
			double ftMaxX = xV == 0 ? Double.POSITIVE_INFINITY : (xV > 0 ? (maxX - x) / xV : (minX - x) / xV);

			double ftMinY = yV == 0 ? Double.NEGATIVE_INFINITY : (yV > 0 ? (minY - y) / yV : (maxY - y) / yV);
			double ftMaxY = yV == 0 ? Double.POSITIVE_INFINITY : (yV > 0 ? (maxY - y) / yV : (minY - y) / yV);

			double ftMinZ = zV == 0 ? Double.NEGATIVE_INFINITY : (zV > 0 ? (minZ - z) / zV : (maxZ - z) / zV);
			double ftMaxZ = zV == 0 ? Double.POSITIVE_INFINITY : (zV > 0 ? (maxZ - z) / zV : (minZ - z) / zV);

			double minT, maxT;
			if (ftMinX > ftMinZ) {
				if (ftMinX > ftMinY)
					minT = ftMinX;
				else
					minT = ftMinY;
			} else {
				if (ftMinZ > ftMinY)
					minT = ftMinZ;
				else
					minT = ftMinY;
			}
			if (ftMaxX < ftMaxZ) {
				if (ftMaxX < ftMaxY)
					maxT = ftMaxX;
				else
					maxT = ftMaxY;
			} else {
				if (ftMaxZ < ftMaxY)
					maxT = ftMaxZ;
				else
					maxT = ftMaxY;
			}

			if (maxT <= minT)
				return null;

			head = mainRay.getPoint(minT < 0 ? 0 : minT);
			x = head.getValueOfX();
			y = head.getValueOfY();
			z = head.getValueOfZ();
			mainRay = new Ray(head, mainRay.getDir());

			if (xV < 0) {
				stepX = -1;
				tMaxX = (((int) ((x - minX) / voxelSize)) * voxelSize - (x - minX)) / xV;
				tDeltaX = -voxelSize / xV;
			} else if (xV > 0) {
				tMaxX = (((int) ((x - minX) / voxelSize) + 1) * voxelSize - (x - minX)) / xV;
				tDeltaX = voxelSize / xV;
			}

			if (yV < 0) {
				stepY = -1;
				tMaxY = (((int) ((y - minY) / voxelSize)) * voxelSize - (y - minY)) / yV;
				tDeltaY = -voxelSize / yV;
			} else if (yV > 0) {
				tMaxY = (((int) ((y - minY) / voxelSize) + 1) * voxelSize - (y - minY)) / yV;
				tDeltaY = voxelSize / yV;
			}

			if (zV < 0) {
				stepZ = -1;
				tMaxZ = (((int) ((z - minZ) / voxelSize)) * voxelSize - (z - minZ)) / zV;
				tDeltaZ = -voxelSize / zV;
			} else if (zV > 0) {
				tMaxZ = (((int) ((z - minZ) / voxelSize) + 1) * voxelSize - (z - minZ)) / zV;
				tDeltaZ = voxelSize / zV;
			}

			return head;

		}

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

	private Geometries[][][] allVoxels;
	private Geometries infiniteGeometries;
	private double voxelSize;
	private double minX, minY, minZ, maxX, maxY, maxZ;
	private int justOutX, justOutY, justOutZ;
	private double xDimension, yDimension, zDimension;
	private static final double DISTANCE_K = (2.0 - Math.sqrt(2)) / 2.0;

	public VoxelsGrid(Geometries sceneGeometries, double voxelSize) {
		this.voxelSize = voxelSize;
		allVoxels = setGeometriesInVoxel(sceneGeometries);
	}

	private Geometries[][][] setGeometriesInVoxel(Geometries sceneGeometries) {
		List<GeoPoint> sceneMinMax = sceneGeometries.getBoxMinMaxVertices();
		int sceneAllBoxLen = sceneMinMax.size();

		setGridSize(voxelSize, sceneMinMax, sceneAllBoxLen);

		Geometries[][][] voxelList = new Geometries[justOutZ][justOutY][justOutX];

		for (int i = 0; i < sceneAllBoxLen; i += 2) {
			Geometry current = sceneMinMax.get(i).geometry;

			if (current instanceof Plane) {
				if (infiniteGeometries == null)
					infiniteGeometries = new Geometries();
				infiniteGeometries.add(current);
				i -= 1;
			} else {
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
							if (!isSphere || ((iX - xMinI <= range) || (iY - yMinI <= range)
									|| (iZ - zMinI <= range) || (xMaxI - iX <= range)
									|| (yMaxI - iY <= range) || (zMaxI - iZ <= range))) {
								if (voxelList[iZ][iY][iX] == null)
									voxelList[iZ][iY][iX] = new Geometries(current);
								else
									voxelList[iZ][iY][iX].add(current);
							}
						}
			}
		}
		return voxelList;
	}

	public List<Integer> getVoxelIndex(Point3D point) {
		int x = (int) ((point.getValueOfX() - minX) / voxelSize);
		int y = (int) ((point.getValueOfY() - minY) / voxelSize);
		int z = (int) ((point.getValueOfZ() - minZ) / voxelSize);

		return List.of((x == justOutX ? x - 1 : x), (y == justOutY ? y - 1 : y), (z == justOutZ ? z - 1 : z));
	}

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
