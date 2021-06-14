package renderer;

import java.util.List;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class VoxelsGrid {

	public class RayVoxels {
		public Ray mainRay;
		private int x, y, z;
		public Geometries voxelGeometries;
		private int stepX = 0, stepY = 0, stepZ = 0;
		private double tMaxX = 0, tMaxY = 0, tMaxZ = 0;
		private double tDeltaX = 0, tDeltaY = 0, tDeltaZ = 0;

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

		public boolean isInCurrentVoxel(Point3D point) {
			List<Integer> index = getVoxelIndex(point);
			return x == index.get(0) && y == index.get(1) && z == index.get(2);
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
			
			if (xV < 0) {
				stepX = -1;
				tMaxX = (((int) ((x - minX) / xDimension)) * xDimension - (x - minX)) / xV;
				tDeltaX = -xDimension / xV;
			}
			else if (xV > 0) {
				stepX = 1;
				tMaxX = (((int) ((x - minX) / xDimension) + 1) * xDimension - (x - minX)) / xV;
				tDeltaX = xDimension / xV;
			}
			
			if (yV < 0) {
				stepY = -1;
				tMaxY = (((int) ((y - minY) / yDimension) + 1) * yDimension - (y - minY)) / yV;
				tDeltaY = -yDimension / yV;
			}
			else if (yV > 0) {
				stepY = 1;
				tMaxY = (((int) ((y - minY) / yDimension) + 1) * yDimension - (y - minY)) / yV;
				tDeltaY = yDimension / yV;
			}
			
			if (zV < 0) {
				stepZ = -1;
				tMaxZ = (((int) ((y - minZ) / zDimension) + 1) * zDimension - (z - minZ)) / zV;
				tDeltaZ = -zDimension / zV;
			}
			else if (xV > 0) {
				stepZ = 1;
				tMaxZ = (((int) ((z - minZ) / zDimension) + 1) * zDimension - (z - minZ)) / zV;
				tDeltaZ = zDimension / zV;
			}

			return head;

		}

		public Boolean nextVoxelGeometries() {
			Geometries geometries = null;
			while (geometries == null) {
				if (tMaxX < tMaxY) {
					if (tMaxX < tMaxZ) {
						x = x + stepX;
						if (x <= justOutX)
							return false; // outside grid
						tMaxX = tMaxX + tDeltaX;
					} else {
						z = z + stepZ;
						if (z <= justOutZ)
							return false;
						tMaxZ = tMaxZ + tDeltaZ;
					}
				} else {
					if (tMaxY < tMaxZ) {
						y = y + stepY;
						if (y <= justOutY)
							return false;
						tMaxY = tMaxY + tDeltaY;
					} else {
						z = z + stepZ;
						if (z <= justOutZ)
							return false;
						tMaxZ = tMaxZ + tDeltaZ;
					}
				}
				geometries = allVoxels[x][y][z];
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

	public VoxelsGrid(Scene scene, double voxelSize) {
		this.voxelSize = voxelSize;
		setGeometriesInVoxel(scene);
	}

	private void setGeometriesInVoxel(Scene scene) {
		List<GeoPoint> sceneMinMax = scene.geometries.getBoxMinMaxVertices();
		int sceneAllBoxLen = sceneMinMax.size();

		setGridSize(voxelSize, sceneMinMax, sceneAllBoxLen);

		allVoxels = new Geometries[(int) (zDimension / voxelSize)][(int) (yDimension / voxelSize)][(int) (xDimension
				/ voxelSize)];
		infiniteGeometries = new Geometries();

		for (int i = 0; i < sceneAllBoxLen; i += 2) {
			Geometry current = sceneMinMax.get(i).geometry;

			if (current instanceof Plane) {
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
				for (int iZ = zMinI; iZ < zMaxI; iZ++)
					for (int iY = yMinI; iY < yMaxI; iY++)
						for (int iX = xMinI; iX < xMaxI; iX++) {
							if (!isSphere || ((abs(iX - xMinI) <= range) || (abs(iY - yMinI) <= range)
									|| (abs(iZ - zMinI) <= range) || (abs(xMaxI - iX) <= range)
									|| (abs(yMaxI - iY) <= range) || (abs(zMaxI - iZ) <= range))) {
								if (allVoxels[iZ][iY][iX] == null)
									allVoxels[iZ][iY][iX] = new Geometries(current);
								else
									allVoxels[iZ][iY][iX].add(current);
							}
						}
			}
		}
	}

	public List<Integer> getVoxelIndex(Point3D point) {
		int x = (int) ((point.getValueOfX() - minX) / voxelSize);
		int y = (int) ((point.getValueOfY() - minY) / voxelSize);
		int z = (int) ((point.getValueOfZ() - minZ) / voxelSize);

		return List.of((x == justOutX ? x - 1 : x), (y == justOutY ? y - 1 : y), (z == justOutZ ? z - 1 : z));
	}

	private double abs(double num) {
		return num < 0 ? -num : num;
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

		double xAdd = ((maxX - minX) % voxelSize) / 2;
		double yAdd = ((maxY - minY) % voxelSize) / 2;
		double zAdd = ((maxZ - minZ) % voxelSize) / 2;
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
