package plainsimple.geometrytoolkit;

public class Point2D {
  private double x;
  private double y;
  private String name;
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  /* returns x */
  public double getX() {
    return x;
  }
  /* returns y */
  public double getY() {
    return y;
  }
  /* returns String representation of point */
  public String getPoint() {
    return "(" + x + ", " + y + ")";
  }
  /* returns distance between points */
  public double getDistance(Point3D point_2) {
    return Math.sqrt(Math.pow((x - point_2.getX()), 2) +
                     Math.pow((y - point_2.getY()), 2));
  }
}
