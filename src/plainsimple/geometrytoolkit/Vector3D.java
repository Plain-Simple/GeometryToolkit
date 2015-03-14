package plainsimple.geometrytoolkit;

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
    Vector3D this_vector = new Vector3D(x, y, z);
    return new LineSegment3D(this_vector).getMidpoint();
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
    return x * vector_2.getX() + y * vector_2.getY() + z * vector_2.getZ();
  }
  /* returns cross product of vector and vector_2 */
  public Vector3D cross(Vector3D vector_2) {
    double cross_x = y * vector_2.getZ() - z * vector_2.getY();
    double cross_y = z * vector_2.getX() - x * vector_2.getZ();
    double cross_z = x * vector_2.getY() - y * vector_2.getX();
    return new Vector3D(cross_x, cross_y, cross_z);
  }
  /* returns magnitude of vector */
  public double getMagnitude() {
    Vector3D this_vector = new Vector3D(x, y, z);
    return Math.sqrt(this_vector.dot(this_vector));
  }
  /* returns angle between two vectors */
  public double getAngle(Vector3D
                         vector_2) { // ToDo: angle class?? Also make sure vectors are tail-to-tail
    Vector3D this_vector = new Vector3D(x, y,
                                        z); // todo: any way to refer to this vector directly?
    double  numerator = this_vector.dot(vector_2);
    double denominator = this_vector.getMagnitude() * vector_2.getMagnitude();
    return Math.acos(numerator / denominator);
  }
  /* returns whether this vector is parallel */
  public boolean isParallel(Vector3D vector_2) { // todo: avoid dividing by zero
    double factor = x / vector_2.getX();
    return (y / vector_2.getY() == factor && z / vector_2.getZ() == factor);
  }
  /* returns whether this vector is perpendicular */
  public boolean isPerpendicular(Vector3D vector_2) {
    Vector3D this_vector = new Vector3D(x, y, z);
    return (this_vector.dot(vector_2) == 0);
  }
  /* returns whether this vector is equal */
  public boolean equals(Vector3D vector_2) {
    return (x == vector_2.getX() && y == vector_2.getY() && z == vector_2.getZ());
  }
}
