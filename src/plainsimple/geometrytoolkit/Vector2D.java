package plainsimple.geometrytoolkit;

import c10n.C10N;

public class Vector2D {
  private double x;
  private double y;
  private String name;
  /* used to access C10N messages */
  private static final Messages msg = C10N.get(Messages.class);
  /* simplest constructor, sets all values to zero */
  public Vector2D() {
    x = 0.0;
    y = 0.0;
    name = "";
  }
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public Vector2D(String name) {
    x = 0.0;
    y = 0.0;
    this.name = name;
  }
  public Vector2D(Vector2D v1, Vector2D v2) {
      x = v2.x() - v1.x();
      y = v2.y() - v1.y();
  }
  public Vector2D(Vector2D v, Point2D p) {
      x = p.x() - v.x();
      y = p.y() - v.y();
  }
  public void setCoordinates(double x, double y) {
    this.x = x;
    this.y = y;
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
    return "<" + x + ", " + y + ">";
  }
  /* returns x value of vector */
  public Double x() {
    return x;
  }
  /* returns y value of vector */
  public Double y() { return y; }
  /* gets direction vector */
  public Vector2D getDirection() {
    double magnitude = getMagnitude();
    return new Vector2D(x / magnitude, y / magnitude);
  }
  /* gets midpoint of vector */
  public Point2D getMidpoint() {
    Vector2D this_vector = new Vector2D(x, y);
    return new LineSegment2D(this_vector).getMidpoint();
  }
  /* multiplies vector by scalar */
  public Vector2D multiply(Object multiplier) throws NumberFormatException {
      if(multiplier.getClass() == double.class) {
          double scalar = (double) multiplier;
          return new Vector2D(scalar * x, scalar * y);
      } else
          throw new NumberFormatException(msg.type_error(msg.multiply_sign(), msg.type_double()));
  }
  /* divides vector by scalar */
  public Vector2D divide(Object divisor) throws NumberFormatException {
      if (divisor.getClass() == double.class) {
          double scalar = (double) divisor;
          return new Vector2D(scalar / x, scalar / y);
      } else
          throw new NumberFormatException(msg.type_error(msg.divide_sign(), msg.type_double()));
  }
  /* adds vectors */
  public Vector2D add(Object addend) throws NumberFormatException {
    if(addend.getClass() == Vector2D.class) {
        Vector3D v = (Vector3D) addend;
        return new Vector2D(x + v.x(), y + v.y());
    } else
        throw new NumberFormatException(msg.type_error(msg.plus_sign(), msg.vector_2d()));
  }
  /* subtracts vectors */
  public Vector2D subtract(Object minuend) throws NumberFormatException {
      if(minuend.getClass() == Vector2D.class) {
          Vector3D v = (Vector3D) minuend;
          return new Vector2D(x - v.x(), y - v.y());
      } else
          throw new NumberFormatException(msg.type_error(msg.minus_sign(), msg.vector_2d()));
  }
  /* returns dot product of vector and vector_2 */
  public double dot(Object multiplier) throws NumberFormatException {
    if(multiplier.getClass() == Vector2D.class) {
        Vector2D v = (Vector2D) multiplier;
        return (x * v.x()) + (y * v.y());
    } else
        throw new NumberFormatException(msg.type_error(msg.multiply_sign(), msg.vector_2d()));
  }
  /* returns magnitude of vector */
  public double getMagnitude() {
    Vector2D this_vector = new Vector2D(x, y);
    return Math.sqrt(this_vector.dot(this_vector));
  }
  /* returns angle between two vectors */
  public double getAngle(Vector2D vector_2) { // todo: should be compatible with all 2d geometry objects
    // ToDo: angle class??
    double  numerator = dot(vector_2);
    double denominator = getMagnitude() * vector_2.getMagnitude();
    return Math.acos(numerator / denominator);
  }
  /* returns whether this vector is parallel */
  public boolean isParallel(Object object_2d) throws NumberFormatException { // todo: figure out what to do when dividing by zero
    if(object_2d.getClass() == Vector2D.class) {
        Vector2D vector_2 = (Vector2D) object_2d;
        if ((vector_2.x() == 0 && vector_2.y() == 0) || (x == 0 && y == 0))
            return true;
        try {
            double factor = x / vector_2.x();
            return (((y / vector_2.y()) == factor));
        } catch (Exception e) {
            return false;
        }
    }
    else return false;
  }
  /* returns whether this vector is perpendicular */
  public boolean isPerpendicular(Object object_2d) throws NumberFormatException {
    if(object_2d.getClass() == Vector2D.class) {
        Vector2D v = (Vector2D) object_2d;
        return (0 == dot(v));
    } else return false;
  }
  /* returns whether this vector is equal */
  @Override public boolean equals(Object o) {
      if (o == null)
          return false;
      else if (o == this)
          return true;
      else if (o.getClass() != Vector2D.class)
          return false;
      else {
          Vector2D v = (Vector2D) o;
          return ((x == v.x()) && (y == v.y()));
      }
  }
}
