package plainsimple.geometrytoolkit;

public class Plane3D {
  /* ax + by + cz = d */
  private double a;
  private double b;
  private double c;
  private double d;
  private String name;
    /* constructs plane using given values for a, b, c, and d */
    public Plane3D(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    /* constructs plane using three points */
    public Plane3D(Point3D A, Point3D B, Point3D C) {
        Vector3D AB = new Vector3D(A, B);
        Vector3D AC = new Vector3D(A, C);
        /* get vector perpendicular to both ab and ac */
        Vector3D cross = AB.cross(AC);
      a = cross.getX();
      b = cross.getY();
      c = cross.getZ();
        /* use coordinates of Point A to determine equation of plane */
      d = (a * A.getX()) + (b * A.getY()) + (c * A.getZ());
    }
  /* constructs plane using a point and a vector perpendicular to the plane */
  public Plane3D(Vector3D vector, Point3D point) {
    a = vector.getX();
    b = vector.getY();
    c = vector.getZ();
    d = (a * point.getX()) + (b * point.getY()) + (c * point.getZ());
  }
    /* returns a */
    public double getA() {
        return a;
    }
    /* returns b */
    public double getB() {
        return b;
    }
    /* returns c */
    public double getC() {
        return c;
    }
    /* returns d */
    public double getD() {
        return d;
    }
    /* returns name */
    public String getName() {
        return name;
    }
    /* sets name to specified String */
    public void setName(String new_name) {
        name = new_name;
    }
  /* returns whether plane contains vector */
  public boolean containsVector(Vector3D vector) {
    return ((a * vector.getX()) + (b * vector.getY()) + (c * vector.getZ())) == d;
  }
  /* returns whether vector is parallel to plane */
  public boolean isParallel(Vector3D vector) {
    Vector3D plane_as_vector = new Vector3D(a, b, c);
    return plane_as_vector.isPerpendicular(vector);
  }
  /* returns whether vector is perpendicular to plane */
  public boolean isPerpendicual(Vector3D vector) {
    Vector3D plane_as_vector = new Vector3D(a, b, c);
    return plane_as_vector.isParallel(vector);
  }
    /* returns whether plane is parallel to plane_2 */
    public boolean isParallel(Plane3D plane_2) {
        Plane3D this_plane = new Plane3D(a, b, c, d);
        return (new Vector3D(this_plane).isParallel(new Vector3D(plane_2)));
    }
    /* returns whether plane is perpendicular to plane_2 */
    public boolean isPerpendicular(Plane3D plane_2) {
        Plane3D this_plane = new Plane3D(a, b, c, d);
        return (new Vector3D(this_plane).isPerpendicular(new Vector3D(plane_2)));
    }
  /* returns whether plane contains point */
  public boolean containsPoint(Point3D point) {
      return ((a * point.getX()) + (b * point.getY()) + (c * point.getZ())) == d;
  }
    /* returns a Point3D that would be found on the plane */
    public Point3D getPointOnPlane() { //TODO: explain purpose of this
        /* fulfill condition that ax + by + cz = d */
        return new Point3D(0.0, 0.0, d);
    }
    /* returns distance from point to plane */
    public double distanceToPoint(Point3D point) {
        Plane3D this_plane = new Plane3D(a, b, c, d);
        /* create vector perpendicular to plane */
        Vector3D reference_vector = new Vector3D(this_plane);
        /* get reference point on plane */
        Point3D reference_point = getPointOnPlane();
        /* construct diagonal from reference point to point */
        Vector3D diagonal = new Vector3D(reference_point, point);
        /* project this diagonal onto the reference vector */
        Vector3D projection = diagonal.getProjection(reference_vector);
        /* return length of projection */
        return projection.getMagnitude();
    }
    /* returns distance from plane to plane_2 */
    public double distanceToPlane(Plane3D plane_2) { // todo: finish. will need better Line3D class
        Plane3D this_plane = new Plane3D(a, b, c, d);
      return this_plane.isParallel(plane_2) ? 0.0 : 0.0;
    }
    public double distanceToLine(Line3D line) { // todo: finish. will need better Line3D class
        return 0.0;
    }
    /* returns angle between plane and plane_2 */
    public double angleBetweenPlanes(Plane3D plane_2) {
        Plane3D this_plane = new Plane3D(a, b, c, d);
        /* constructs a vector perpendicular to each plane and measures angle
        between them */
        return new Vector3D(this_plane).getAngle(new Vector3D(plane_2));
    }
}
