package plainsimple.geometrytoolkit;

public class Point2D {
  private double x;
  private double y;
  private String name;
    public Point2D() {
        x = 0.0;
        y = 0.0;
    }
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  /* returns name of point */
  public String getName() { return name; }
  /* sets name of point */
  public void setName(String s) { name = s; }
  /* returns x */
  public double x() {
    return x;
  }
  /* returns y */
  public double y() { return y; }
  /* returns String representation of point */
  @Override public String toString() {
    return "(" + x + ", " + y + ")";
  }
  /* returns distance between points */
  public double getDistance(Point2D point_2) {
    return Math.sqrt(Math.pow((x - point_2.x()), 2) +
                     Math.pow((y - point_2.y()), 2));
  }
  @Override public boolean equals(Object o) {
      if (o == null)
          return false;
      else if (o == this)
          return true;
      else if (o.getClass() != Point2D.class)
          return false;
      else {
          Point2D p = (Point2D) o;
          return ((x == p.x()) && (y == p.y()));
      }
  }

}
