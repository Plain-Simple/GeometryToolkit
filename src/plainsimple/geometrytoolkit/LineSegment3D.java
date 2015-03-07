package plainsimple.geometrytoolkit;

public class LineSegment3D {
    double x1;
    double y1;
    double z1;
    double x2;
    double y2;
    double z2;
    String name;
    /* initializes line segment using two points */
    public LineSegment3D(Point3D start, Point3D end) {
        x1 = start.getX();
        y1 = start.getY();
        z1 = start.getZ();
        x2 = end.getX();
        y2 = end.getY();
        z2 = end.getZ();
    }
    /* initializes line segment using vector */
    public LineSegment3D(Vector3D vector) {
        x1 = 0.0;
        y1 = 0.0;
        z1 = 0.0;
        x2 = vector.getX();
        y2 = vector.getY();
        z2 = vector.getZ();
    }
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
