package plainsimple.geometrytoolkit;

public class Vector2D {
  private double x;
  private double y;
  private String name;
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
  public Vector2D multiplyScalar(double scalar) { return new Vector2D(scalar * x, scalar * y); }
  /* divides vector by scalar */
  public Vector2D divideScalar(double scalar) { return new Vector2D(scalar / x, scalar / y); }
  /* adds vectors */
  public Vector2D addVector(Vector2D vector_2) {
    return new Vector2D(x + vector_2.x(), y + vector_2.y());
  }
  /* subtracts vectors */
  public Vector2D subtractVector(Vector2D vector_2) { return new Vector2D(x - vector_2.x(), y - vector_2.y()); }
  /* returns dot product of vector and vector_2 */
  public double dot(Vector2D vector_2) {
    return (x * vector_2.x()) + (y * vector_2.y());
  }
  /* returns magnitude of vector */
  public double getMagnitude() {
    Vector2D this_vector = new Vector2D(x, y);
    return Math.sqrt(this_vector.dot(this_vector));
  }
  /* returns angle between two vectors */
  public double getAngle(Vector2D vector_2) {
    // ToDo: angle class?? Also make sure vectors are tail-to-tail
    double  numerator = dot(vector_2);
    double denominator = getMagnitude() * vector_2.getMagnitude();
    return Math.acos(numerator / denominator);
  }
  /* returns whether this vector is parallel */
  public boolean isParallel(Vector2D
                            vector_2) { // todo: figure out what to do when dividing by zero
    try {
      return (x / vector_2.x()) == (y / vector_2.y());
    } catch(Exception e) {
      System.out.println("Division by zero in Vector2D.isParallel");
      return false; /* is it always false? We need to figure out what to do in this case */
    }
  }
  /* returns whether this vector is perpendicular */
  public boolean isPerpendicular(Vector2D vector_2) {
    return (0 == dot(vector_2));
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
