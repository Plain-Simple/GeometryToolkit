package plainsimple.geometrytoolkit;

public class Plane3D {
    /* ax + by + cz = d */
    double a;
    double b;
    double c;
    double d;
    String name;
    /* constructs plane using a point and a vector perpendicular to the plane */
    public Plane3D(Vector3D vector, Point3D point) {
        a = vector.getX();
        b = vector.getY();
        c = vector.getZ();
        d = a * point.getX() + b * point.getY() + c * point.getZ();
    }
    /* returns whether plane contains vector */
    public boolean containsVector(Vector3D vector) {
        return (a * vector.getX() + b * vector.getY() + c * vector.getZ() == d);
    }
    /* returns whether vector is parallel to plane */
    public boolean isParallel(Vector3D vector) {
        Vector3D plane_as_vector = new Vector3D(a, b, c);
        return(plane_as_vector.isPerpendicular(vector));
    }
    /* returns whether vector is perpendicular to plane */
    public boolean isPerpendicual(Vector3D vector) {
        Vector3D plane_as_vector = new Vector3D(a, b, c);
        return(plane_as_vector.isParallel(vector));
    }
    /* returns whether plane contains point */
    public boolean containsPoint(Point3D point) {
        return (a * point.getX() + b * point.getY() + c * point.getZ() == d);
    }
}
