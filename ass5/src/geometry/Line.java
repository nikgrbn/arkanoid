// Nikita Grebenchuk 337762421

package geometry;

import java.util.Comparator;
import java.util.List;

/**
 * @author Nikita Grebenchuk
 * @version 1.0
 * @since 5/15/2024 12:00
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Constructor.
     * @param start
     * @param end
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }


    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect
     * @return closest intersection point to the start
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }

        // Sort by distance from starting point
        intersectionPoints.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.distance(start), o2.distance(start));
            }
        });
        return intersectionPoints.get(0);
    }

    /**
     * @return Return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * @return Returns the middle point of the line
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * @return Returns the start point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * @return Returns the end point of the line
     */
    public Point end() {
        return end;
    }

    /**
     * Checks if this line segment intersects with another line segment.
     * @param other The other line segment to check for intersection.
     * @return true if this line segment intersects with the other line segment, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // game.geometry.Line 1 points
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        // game.geometry.Line 2 points
        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        // Check the orientations needed to know if they intersect
        int o1 = orientation(x1, y1, x2, y2, x3, y3);
        int o2 = orientation(x1, y1, x2, y2, x4, y4);
        int o3 = orientation(x3, y3, x4, y4, x1, y1);
        int o4 = orientation(x3, y3, x4, y4, x2, y2);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special cases
        // (x1, y1), (x2, y2) and (x3, y3) are collinear and (x3, y3) lies on segment (x1, y1) to (x2, y2)
        if (o1 == 0 && onSegment(x1, y1, x3, y3, x2, y2)) {
            return true;
        }
        // (x1, y1), (x2, y2) and (x4, y4) are collinear and (x4, y4) lies on segment (x1, y1) to (x2, y2)
        if (o2 == 0 && onSegment(x1, y1, x4, y4, x2, y2)) {
            return true;
        }

        // (x3, y3), (x4, y4) and (x1, y1) are collinear and (x1, y1) lies on segment (x3, y3) to (x4, y4)
        if (o3 == 0 && onSegment(x3, y3, x1, y1, x4, y4)) {
            return true;
        }

        // (x3, y3), (x4, y4) and (x2, y2) are collinear and (x2, y2) lies on segment (x3, y3) to (x4, y4)
        if (o4 == 0 && onSegment(x3, y3, x2, y2, x4, y4)) {
            return true;
        }

        // Otherwise, they don't intersect
        return false;
    }

    /**
     * Calculates the orientation of the triplet of points (px, py), (qx, qy), and (rx, ry).
     * @param px The x-coordinate of the first point.
     * @param py The y-coordinate of the first point.
     * @param qx The x-coordinate of the second point.
     * @param qy The y-coordinate of the second point.
     * @param rx The x-coordinate of the third point.
     * @param ry The y-coordinate of the third point.
     * @return 0 if the points are collinear, 1 if they form a clockwise orientation, 2 if they form a
     * counterclockwise orientation.
     */
    private int orientation(double px, double py, double qx, double qy, double rx, double ry) {
        double val = (qy - py) * (rx - qx) - (qx - px) * (ry - qy);
        if (val == 0) {
            return 0; // collinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    /**
     * Checks if the point (qx, qy) lies on the line segment defined by the points (px, py) and (rx, ry).
     * @param px The x-coordinate of the first endpoint of the segment.
     * @param py The y-coordinate of the first endpoint of the segment.
     * @param qx The x-coordinate of the point to check.
     * @param qy The y-coordinate of the point to check.
     * @param rx The x-coordinate of the second endpoint of the segment.
     * @param ry The y-coordinate of the second endpoint of the segment.
     * @return true if the point (qx, qy) lies on the segment (px, py) to (rx, ry), false otherwise.
     */
    private boolean onSegment(double px, double py, double qx, double qy, double rx, double ry) {
        if (qx <= Math.max(px, rx) && qx >= Math.min(px, rx)
                && qy <= Math.max(py, ry) && qy >= Math.min(py, ry)) {
            return true;
        }
        return false;
    }

    /**
     * @param other1 line1 to check intersection with
     * @param other2 line2 to check intersection with
     * @return Returns true if this 2 lines intersect with this line, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * @param other line to get intersection point with
     * @return Returns the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }

        // game.geometry.Line 1 points
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        // game.geometry.Line 2 points
        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        // Calculate the intersection point using determinant method
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denominator == 0) {
            return null; // Lines are parallel
        }

        double intersectX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
        double intersectY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

        return new Point(intersectX, intersectY);
    }

    /**
     * @param other
     * @return return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end);
    }
}