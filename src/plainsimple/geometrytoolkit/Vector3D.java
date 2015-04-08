package plainsimple.geometrytoolkit;

import c10n.C10N;

public class Vector3D {
  private double x = 0.0;
  private double y = 0.0;
  private double z = 0.0;
  private String name = "";
  /* used to access C10N messages */
  private static final Messages msg = C10N.get(Messages.class);
  public Vector3D() {}
  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Vector3D(String name) {
    x = 0.0;
    y = 0.0;
    z = 0.0;
    this.name = name;
  }
  public Vector3D(Vector3D v1, Vector3D v2) {
      x = v2.x() - v1.x();
      y = v2.x() - v1.x();
      z = v2.x() - v1.x();
  }
  public Vector3D(Vector3D v, Point3D p) {
      x = p.x() - v.x();
      y = p.y() - v.y();
      z = p.z() - v.z();
  }
  /* constructs vector from point_1 to point_2 */
  public Vector3D(Point3D point_1, Point3D point_2) {
    x = point_2.x() - point_1.x();
    y = point_2.y() - point_1.y();
    z = point_2.z() - point_1.z();
  }
  /* constructs vector perpendicular to plane */
  public Vector3D(Plane3D plane) {
    /* will have component form <a, b, c> */
    x = plane.a();
    y = plane.b();
    z = plane.c();
  }
  public void setCoordinates(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public void setName(String new_name) {
    name = new_name;
  }
  /* returns name of vector */
  public String getName() {
    return name;
  }
  /* returns String representation of the vector */
  @Override public String toString() {
    return "<" + x + ", " + y + ", " + z + ">";
  }
  /* returns x value of vector */
  public double x() {
    return x;
  }
  /* returns y value of vector */
  public double y() {
    return y;
  }
  /* returns z value of vector */
  public double z() {
    return z;
  }
  /* gets direction vector */
  public Vector3D getDirection() {
    double magnitude = getMagnitude();
    return new Vector3D(x / magnitude, y / magnitude, z / magnitude);
  }
  /* gets midpoint of vector */
  public Point3D getMidpoint() {
    return new LineSegment3D(this).getMidpoint();
  }
  /* multiplies vector by scalar or by another vector (dot product) */
  public Vector3D multiply(Object multiplier) throws NumberFormatException {
    if(multiplier.getClass() != double.class)
        throw new NumberFormatException(msg.type_error("*", "Vector3D", "Number"));
    else {
        double scalar = (double) multiplier;
        return new Vector3D(scalar * x, scalar * y, scalar * z);
    }
  }
    /* divides vector by scalar */
  public Vector3D divide(Object divisor) throws NumberFormatException {
    if(divisor.getClass() != double.class)
        throw new NumberFormatException(msg.type_error("/", "Number"));
    else {
        double scalar = (double) divisor;
        return new Vector3D(scalar / x, scalar / y, scalar / z);
    }
  }
  /* adds vectors */
  public Vector3D add(Object addend) throws NumberFormatException {
    if(addend.getClass() != Vector3D.class)
        throw new NumberFormatException(msg.type_error("+", "Vector3D"));
    else {
        Vector3D vector_2 = (Vector3D) addend;
        return new Vector3D(x + vector_2.x(), y + vector_2.y(),
                z + vector_2.z());
    }
  }
  /* adds vectors */
  public Vector3D subtract(Object minuend) throws NumberFormatException {
    if(minuend.getClass() != Vector3D.class)
      throw new NumberFormatException(msg.type_error("-", "Vector3D"));
    else {
        Vector3D vector_2 = (Vector3D) minuend;
        return new Vector3D(x + vector_2.x(), y + vector_2.y(),
                z + vector_2.z());
    }
  }
  /* returns dot product of vector and object */
  public double dot(Object object) throws NumberFormatException { // todo: test error message for conflictions with multiply
    if(object.getClass() != Vector3D.class)
        throw new NumberFormatException(msg.type_error("*", "Vector3D", "Number"));
    else {
        Vector3D vector_2 = (Vector3D) object;
        return (x * vector_2.x()) + (y * vector_2.y()) + (z * vector_2.z());
    }
  }
  /* returns cross product of vector and object */
  public Vector3D cross(Object object) throws NumberFormatException {
    if(object.getClass() != Vector3D.class)
        throw new NumberFormatException(msg.type_error("x", "Vector3D"));
    else {
        Vector3D vector_2 = (Vector3D) object;
        return new Vector3D(
                (y * vector_2.z()) - (z * vector_2.y()),
                (z * vector_2.x()) - (x * vector_2.z()),
                (x * vector_2.y()) - (y * vector_2.x()));
    }
  }
  /* returns magnitude of vector */
  public double getMagnitude() {
    return Math.sqrt(dot(this));
  }
  /* returns angle between vector and object */
  public double getAngle(Vector3D vector_2) { // ToDo: angle class??
    return Math.acos((dot(vector_2) /* numerator */
              / getMagnitude()) * vector_2.getMagnitude()); /* denominator */
  }
  /* returns whether this vector is parallel */
  public boolean isParallel(Object object) throws NumberFormatException { // todo: testing
    if(object.getClass() == Vector3D.class) {
        Vector3D vector_2 = (Vector3D) object;
      /* for simplicity and to avoid dividing by zero */
        if ((vector_2.x() == 0 && vector_2.y() == 0 && vector_2.z() == 0) ||
                (x == 0 && y == 0 && z == 0))
            return true;
        try {
            double factor = x / vector_2.x();
            return (((y / vector_2.y()) == factor) && ((z / vector_2.z()) == factor));
        } catch (Exception e) {
            return false;
        }
    } else if(object.getClass() == Line3D.class) {
        Line3D line = (Line3D) object;
        return (line.getDirectionVector().equals(this));
    } else if(object.getClass() == Plane3D.class) {
        Plane3D plane = (Plane3D) object;
        return (new Vector3D(plane.a(), plane.b(), plane.c()).isPerpendicular(this));
    } else if(object.getClass() == LineSegment3D.class) {
        LineSegment3D ls = (LineSegment3D) object;
        return isParallel(new Vector3D(ls.x2() - ls.x1(), ls.y2() - ls.y1(), ls.z2() - ls.z1()));
    } else
        throw new NumberFormatException(msg.type_error("\\", "Vector3D", "Line3D", "Plane3D", "LineSegment3D"));
  }
  /* returns whether this vector is perpendicular */
  public boolean isPerpendicular(Object object) throws NumberFormatException {
    if(object.getClass() == Vector3D.class)
        return (0 == dot(object));
    else if(object.getClass() == Line3D.class)
        return isPerpendicular(((Line3D) object).getDirectionVector());
    else if(object.getClass() == Plane3D.class) {
        Plane3D plane = (Plane3D) object;
        return (new Vector3D(plane.a(), plane.b(), plane.c()).isParallel(this));
    } else if(object.getClass() == LineSegment3D.class) {
        LineSegment3D ls = (LineSegment3D) object;
        return isPerpendicular(new Vector3D(ls.x2() - ls.x1(), ls.y2() - ls.y1(), ls.z2() - ls.z1()));
    } else
        throw new NumberFormatException(msg.type_error("\\", "Vector3D", "Line3D", "Plane3D", "LineSegment3D"));
  }
  /* returns whether this vector is equal */
  @Override public boolean equals(Object o) {
      if (o == null)
          return false;
      else if (o == this)
          return true;
      else if (o.getClass() != Vector3D.class)
          return false;
      else {
          Vector3D v = (Vector3D) o;
          return ((x == v.x()) && (y == v.y())
                  && (z == v.z()));
      }
  }
  /* returns vector projected onto vector_2 */
  public Vector3D getProjection(Vector3D vector_2) {
    /* p = v1((v1 * v2) / (|v2|*|v2|)) */
    double multiplier = dot(vector_2) / (Math.pow((vector_2.getMagnitude()), 2));
    return new Vector3D(x() * multiplier, y() * multiplier,
                        z() * multiplier);
  }
    /* returns a vector perpendicular to this one */
    public Vector3D getPerpendicular() { // todo: learn matrices
        /* dot product must equal zero */
        return new Vector3D(0,0,0);
    }
}
