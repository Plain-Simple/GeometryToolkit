package plainsimple.geometrytoolkit;

public class Point3D {
  private double x;
  private double y;
  private double z;
  private String name;
  public Point3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  /* returns x */
  public double getX() {
    return x;
  }
  /* returns y */
  public double getY() {
    return y;
  }
  /* returns z */
  public double getZ() {
    return z;
  }
  /* returns String representation of point */
  public String getPointString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }
  /* returns distance between points */
  public double getDistance(Point3D point_2) {
    return Math.sqrt(Math.pow((x - point_2.getX()), 2) +
                     Math.pow((y - point_2.getY()), 2) +
                     Math.pow((z - point_2.getZ()), 2));
  }
}

