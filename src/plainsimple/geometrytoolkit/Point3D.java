package plainsimple.geometrytoolkit;

public class Point3D {
  private double x;
  private double y;
  private double z;
  private String name;
    public Point3D() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }
  public Point3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
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
  public double y() {
    return y;
  }
  /* returns z */
  public double z() {
    return z;
  }
  /* returns String representation of point */
  @Override public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }
  /* returns distance between points */
  public double getDistance(Point3D point_2) {
    return Math.sqrt(Math.pow((x - point_2.x()), 2) +
                     Math.pow((y - point_2.y()), 2) +
                     Math.pow((z - point_2.z()), 2));
  }
  @Override public boolean equals(Object o) {
      if (o == null)
          return false;
      else if (o == this)
          return true;
      else if (o.getClass() != Point3D.class)
          return false;
      else {
          Point3D p = (Point3D) o;
          return ((x == p.x()) && (y == p.y()) && (z == p.z()));
      }
  }
}

