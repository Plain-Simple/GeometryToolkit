package plainsimple.geometrytoolkit;

import c10n.C10N;

public class Point3D {
  private double x;
  private double y;
  private double z;
  private String name;
  /* used to access C10N messages */
  private static final Messages msg = C10N.get(Messages.class);
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
  public double getDistance(Object object_3d) throws NumberFormatException { // todo: make compatible with all 2d geometry objects
    if(object_3d.getClass() == Point3D.class) {
        Point3D p = (Point3D) object_3d;
        return Math.sqrt(Math.pow((x - p.x()), 2) +
                Math.pow((y - p.y()), 2) +
                Math.pow((z - p.z()), 2));
    } else
        throw new NumberFormatException(msg.type_error(msg.arrow(), msg.three_d()));
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

