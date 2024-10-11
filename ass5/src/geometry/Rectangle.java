// Nikita Grebenchuk 337762421

package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a rectangle in 2D space.
 * @author Nikita Grebenchuk
 * @version 2.0
 * @since 11/06/2024 14:00
 */
public class Rectangle {
    private Point upperLeft, downRight;
    private double width, height;

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft
     * @param width
     * @param height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.width = width;
        this.height = height;
        this.upperLeft = upperLeft;
        this.downRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     * @param line
     * @return intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<Point>();
        Point p;

        Line top = new Line(upperLeft.getX(), upperLeft.getY(), downRight.getX(), upperLeft.getY());
        p = line.intersectionWith(top);
        if (p != null) {
            intersectionPoints.add(p);
        }

        Line bottom = new Line(upperLeft.getX(), downRight.getY(), downRight.getX(), downRight.getY());
        p = line.intersectionWith(bottom);
        if (p != null) {
            intersectionPoints.add(p);
        }

        Line left = new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX(), downRight.getY());
        p = line.intersectionWith(left);
        if (p != null) {
            intersectionPoints.add(p);
        }

        Line right = new Line(downRight.getX(), upperLeft.getY(), downRight.getX(), downRight.getY());
        p = line.intersectionWith(right);
        if (p != null) {
            intersectionPoints.add(p);
        }

        return intersectionPoints;
    }

    /**
     * @return p1
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * @param upperLeft
     */
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
        this.downRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * @return p2
     */
    public Point getDownRight() {
        return downRight;
    }

    /**
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }


}
