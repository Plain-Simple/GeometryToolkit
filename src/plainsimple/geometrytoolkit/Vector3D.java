package plainsimple.geometrytoolkit;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vector3D {
  private double x;
  private double y;
  private double z;
  private String name;
  /* simplest constructor, sets all values to zero */
  public Vector3D() {
    x = 0.0;
    y = 0.0;
    z = 0.0;
    name = "";
  }
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
  /* constructs vector from point_1 to point_2 */
  public Vector3D(Point3D point_1, Point3D point_2) {
    x = point_2.getX() - point_1.getX();
    y = point_2.getY() - point_1.getY();
    z = point_2.getZ() - point_1.getZ();
  }
  /* constructs vector perpendicular to plane */
  public Vector3D(Plane3D plane) {
    /* will have component form <a, b, c> */
    x = plane.getA();
    y = plane.getB();
    z = plane.getC();
  }
  /* constructs Vector3D give String "<x,y,z>" */
  public Vector3D constructFromString(String name, String args) {
    ArrayList<Double> coordinates = new ArrayList<>();
    Pattern parse_vector = Pattern.compile("\\D*(\\d+\\.*\\d*)\\D*");
    Matcher matcher = parse_vector.matcher(args);
    try {
      while (matcher.find()) {
        coordinates.add(Double.parseDouble(matcher.group(1)));
      }
      Vector3D new_vector3d = new Vector3D(name);
      new_vector3d.setCoordinates(coordinates.get(0), coordinates.get(1),
                                  coordinates.get(2));
      return  new_vector3d;
    } catch(Exception e) {
      return null;
    }
  }
  public void setCoordinates(double x_coord, double y_coord, double z_coord) {
    x = x_coord;
    y = y_coord;
    z = z_coord;
  }
  public void setName(String new_name) {
    name = new_name;
  }
  /* returns name of vector */
  public String getName() {
    return name;
  }
  /* returns String representation of the vector */
  public String getComponentForm() {
    return "<" + x + ", " + y + ", " + z + ">";
  }
  /* returns x value of vector */
  public Double getX() {
    return x;
  }
  /* returns y value of vector */
  public Double getY() {
    return y;
  }
  /* returns z value of vector */
  public Double getZ() {
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
  /* multiplies vector by scalar */
  public Vector3D multiplyScalar(double scalar) {
    return new Vector3D(scalar * x, scalar * y, scalar * z);
  }
  /* adds vectors */
  public Vector3D addVector(Vector3D vector_2) {
    return new Vector3D(x + vector_2.getX(), y + vector_2.getY(),
                        z + vector_2.getZ());
  }
  /* returns dot product of vector and vector_2 */
  public double dot(Vector3D vector_2) {
    return (x * vector_2.getX()) + (y * vector_2.getY()) + (z * vector_2.getZ());
  }
  /* returns cross product of vector and vector_2 */
  public Vector3D cross(Vector3D vector_2) {
    return new Vector3D(
             (y * vector_2.getZ()) - (z * vector_2.getY()),
             (z * vector_2.getX()) - (x * vector_2.getZ()),
             (x * vector_2.getY()) - (y * vector_2.getX()));
  }
  /* returns magnitude of vector */
  public double getMagnitude() {
    return Math.sqrt(dot(this));
  }
  /* returns angle between two vectors */
  public double getAngle(Vector3D vector_2) { // ToDo: angle class??
    return Math.acos(
             (dot(vector_2) /* numerator */
              / getMagnitude()) * vector_2.getMagnitude()); /* denominator */
  }
  /* returns whether this vector is parallel */
  public boolean isParallel(Vector3D vector_2) {
    // todo: figure out what to do when dividing by zero
    try {
      double factor = x / vector_2.getX();
      return (((y / vector_2.getY()) == factor) && ((z / vector_2.getZ()) == factor));
    } catch(Exception e) {
      System.out.println("Division by zero in Vector2D.isParallel");
      return false; /* is it always false? We need to figure out what to do in this case */
    }
  }
  /* returns whether this vector is perpendicular */
  public boolean isPerpendicular(Vector3D vector_2) {
    return (0 == dot(vector_2));
  }
  /* returns whether this vector is equal */
  public boolean equals(Vector3D vector_2) {
    return ((x == vector_2.getX()) && (y == vector_2.getY())
            && (z == vector_2.getZ()));
  }
  /* returns vector projected onto vector_2 */
  public Vector3D getProjection(Vector3D vector_2) {
    /* p = v1((v1 * v2) / (|v2|*|v2|)) */
    double multiplier = dot(vector_2) / (Math.pow((vector_2.getMagnitude()), 2));
    return new Vector3D(getX() * multiplier, getY() * multiplier,
                        getZ() * multiplier);
  }
}
