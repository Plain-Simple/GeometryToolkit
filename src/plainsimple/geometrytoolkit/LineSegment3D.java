package plainsimple.geometrytoolkit;

public class LineSegment3D {
  private double x1;
  private double y1;
  private double z1;
  private double x2;
  private double y2;
  private double z2;
  private String name;
  /* initializes line segment using two points */
  public LineSegment3D(Point3D start, Point3D end) {
    x1 = start.x();
    y1 = start.y();
    z1 = start.z();
    x2 = end.x();
    y2 = end.y();
    z2 = end.z();
  }
  /* initializes line segment using vector */
  public LineSegment3D(Vector3D vector) {
    x1 = 0.0;
    y1 = 0.0;
    z1 = 0.0;
    x2 = vector.x();
    y2 = vector.y();
    z2 = vector.z();
  }
  public double x1() { return x1; }
  public double y1() { return y1; }
  public double z1() { return z1; }
  public double x2() { return x2; }
  public double y2() { return y2; }
  public double z2() { return z2; }
  /* returns length of segment */
  public double getLength() {
    return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)
                     + Math.pow((z2 - z1), 2));
  }
  /* returns midpoint */
  public Point3D getMidpoint() {
    return new Point3D((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2);
  }
}
