package plainsimple.geometrytoolkit;

public class Line3D {
    /* a 3d line is defined by a point and a direction
    vector, where line = point + t * direction vector */
    private Point3D point;
    private Vector3D direction_vector;
    private String name;
    public Line3D(Point3D p, Vector3D v) {
        point = p;
        direction_vector = v;
    }
    /* constructs line starting from (0,0,0) and perpendicular to plane */
    public Line3D(Plane3D p) {
        point = new Point3D(0,0,0);
        direction_vector = new Vector3D(p);
    }
    /* returns equation of line */
    public String getEquation() { // todo: finish
        return "";
    }
    /* returns name of Line */
    public String getName() { return name; }
    /* sets name of Line */
    public void setName(String s) { name = s; }
    /* returns direction vector of line */
    public Vector3D getDirectionVector() {
        return direction_vector;
    }
    /* returns starting point of line */
    public Point3D getStartPoint() {
        return point;
    }
    /* returns a line perpendicular to the line */
    public Line3D getPerpendicular() { // todo: fix/finish this (need to rotate direction vector
    //todo: 90 degrees - requires matrices). See Vector3D class
        return new Line3D(point, direction_vector);
    }
    /* returns point where line intersects plane */
    public Point3D getPlaneIntersection(Plane3D plane) {
        /* an example:
        3x + 4y + 2z = 13 intersects with (4,3,7) + t<2,1,2)
        3(4 + 2t) + 4(3 + t) + 2(7 + 2t) = 13
        14t = -25
        t = -25 / 14
        intersection = (4,3,7) + -25 / 14 <2,1,2> */
        double t = (plane.d() - (plane.a() * point.x() + plane.b() * point.y()
        + plane.c() * point.z())) / (plane.a() * direction_vector.x() + plane.b() *
        direction_vector.y() + plane.c() * direction_vector.z());
        return new Point3D(point.x() + t * direction_vector.x(), point.y() + t * direction_vector.y(),
                point.z() + t * direction_vector.z());
    }
    /* calculates distances between line and point */
    public double distanceToPoint(Point3D point) {
        Line3D this_line = new Line3D(point, direction_vector);
        /* construct a plane that contains point and is perpendicular to line */
        Plane3D plane = new Plane3D(direction_vector, point);
        return this_line.getPlaneIntersection(plane).getDistance(point);
    }
}
