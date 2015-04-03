package plainsimple.geometrytoolkit;

public class LineSegment2D {
  double x1;
  double y1;
  double x2;
  double y2;
  String name;
  /* initializes line segment using two points */
  public LineSegment2D(Point2D start, Point2D end) {
    x1 = start.x();
    y1 = start.y();
    x2 = end.x();
    y2 = end.y();
  }
  /* initializes line segment using vector */
  public LineSegment2D(Vector2D vector) {
    x1 = 0.0;
    y1 = 0.0;
    x2 = vector.x();
    y2 = vector.y();
  }
  /* returns length of segment */
  public double getLength() {
    return Math.sqrt(Math.pow((x2 - x1), 2) +
                     Math.pow((y2 - y1), 2));
  }
  /* returns midpoint */
  public Point2D getMidpoint() {
    return new Point2D((x1 + x2) / 2,
                       (y1 + y2) / 2);
  }
}
