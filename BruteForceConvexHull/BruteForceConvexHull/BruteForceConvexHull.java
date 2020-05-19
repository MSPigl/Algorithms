/*
Brute force convex hull implementation

Jim Teresco
Spring 2011, CSIS 385, Siena College
Updated for Spring 2017

Updated to output a CHM project .gra file for plotting
Further updates to read and write .tmg format files

$Id: BruteForceConvexHull.java 3112 2017-02-02 22:00:22Z terescoj $

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Point {

    // instance variables for label and coordinates
    private String label;
    private double x;
    private double y;

    public Point(String label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return label + " (" + x + "," + y + ")";
    }

    public String toStringTMG() {
        return label + " " + x + " " + y;
    }

    public boolean equals(Object o) {
        Point other = (Point)o;
        return (x == other.x) &&
        (y == other.y) &&
        label.equals(other.label);
    }

    public double squaredDistance(Point other) {

        double dx, dy;
        dx = x-other.x;
        dy = y-other.y;
        return dx*dx + dy*dy;
    }

    /**
    Check if this point is directly in between the two given
    points.  Note: the assumption is that they are colinear.

    @param o1 one of the points
    @param o2 the other point
    @return whether this point is between the two given points
     */
    public boolean isBetween(Point o1, Point o2) {

        double sqDisto1o2 = o1.squaredDistance(o2);
        return (squaredDistance(o1) < sqDisto1o2) &&
        (squaredDistance(o2) < sqDisto1o2);
    }
}

class LineSegment {

    // store the endpoints
    private Point start;
    private Point end;

    public LineSegment(Point a, Point b) {

        start = a;
        end = b;
    }

    public Point getStart() {

        return start;
    }

    public Point getEnd() {

        return end;
    }

    public String toString() {
        return "Segment from " + start + " to " + end;
    }
}

public class BruteForceConvexHull {

    /**
    Read in a list of points:
    label x y
    from the file in args[0] (second line contains number of points)

    The compute convex hull using brute force, print out in
    readable text (args[1].equals("text") or nothing) or as data
    plottable in the TMG data Google Maps viewer (args[1].equals("tmg").
     */

    public static void main(String args[]) {

        int numPoints = 0;
        boolean debug = false;

        if (args.length == 0) {
            System.err.println("Usage: java BruteForceConvexHull filename [type]");
            System.exit(1);
        }

        // create a list of points
        ArrayList<Point> points = new ArrayList<Point>();

        try {
            Scanner s = new Scanner(new File(args[0]));
            // skip header line (would be good to do error checking)
            s.nextLine();
            // second line is number of waypoints and connections
            numPoints = s.nextInt();
            // skip the rest of the line
            s.nextLine();
            // read the lines
            for (int i = 0; i<numPoints; i++) {
                points.add(new Point(s.next(), s.nextDouble(), s.nextDouble()));
            }

        }
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        // we will build the line segments that form the hull in this list
        ArrayList<LineSegment> hull = new ArrayList<LineSegment>();

        // consider each pair of points
        long start = System.currentTimeMillis();
        long checkValsNum = 0;
        for (int i = 0; i<numPoints-1; i++) {

            Point point1 = points.get(i);
            for (int j = i+1; j<numPoints; j++) {

                Point point2 = points.get(j);
                // from here, we need to see if all other points are
                // on the same side of the line connecting point1 and point2
                double a = point2.getY() - point1.getY();
                double b = point1.getX() - point2.getX();
                double c = point1.getX() * point2.getY() - point1.getY() * point2.getX();
                // now check all other points to see if they're on the
                // same side -- stop as soon as we find they're not
                boolean lookingForPositive = false;
                boolean foundProblem = false;
                boolean firstTestPoint = true;

                for (int k = 0; k<numPoints; k++) {

                    Point point3 = points.get(k);

                    if (point1.equals(point3) || point2.equals(point3)) 
                        continue;
                    double checkVal = a * point3.getX() + b * point3.getY() - c;
                    checkValsNum++;
                    if (debug)
                        System.out.println("Checking " + point3 + 
                            " for segment from " + point1 + 
                            " to " + point2);   
                    if (checkVal == 0) {
                        // if in between, continue, otherwise skip this pair
                        // since we'll catch it elsewhere
                        if (point3.isBetween(point1, point2)) {
                            continue;
                        }
                        else {
                            if (debug) 
                                System.out.println("Found colinear point " + 
                                    point3 + " directly between " +
                                    point1 + " and " + point2);
                            foundProblem = true;
                            break;
                        }
                    }
                    if (firstTestPoint) {
                        lookingForPositive = (checkVal > 0);
                        firstTestPoint = false;
                    }
                    else {
                        if ((lookingForPositive && (checkVal < 0) ||
                            (!lookingForPositive && (checkVal > 0)))) {
                            // segment not on hull, jump out of innermost loop
                            if (debug)
                                System.out.println("Found points on opposite sides of line between " +
                                    point1 + " and " + point2);
                            foundProblem = true;
                            break;
                        }   
                    }
                }
                // we didn't find a reason that this segment was not on the
                // hull, so we add it
                if (!foundProblem) hull.add(new LineSegment(point1, point2));
            }
        }
        double time = (System.currentTimeMillis() - start)/ 1000.0;
        // we now have a list of line segments that form the hull
        if (debug) {
            System.out.println("Convex hull is formed from line segments:");
            for (LineSegment l : hull)
                System.out.println(l);
        }

        // we pull out the points and list them in order, repeating the last
        // so we can easily draw the hull
        ArrayList<Point> hullPoints = new ArrayList<Point>();
        // we'll start with the first segment in the list
        LineSegment firstSegment = hull.get(0);
        Point firstHullPoint = firstSegment.getStart();
        hullPoints.add(firstHullPoint);
        Point nextSegmentPoint = firstSegment.getEnd();
        hullPoints.add(nextSegmentPoint);
        hull.remove(firstSegment);
        while (!hull.isEmpty()) {
            for (LineSegment l : hull) {
                if (l.getStart().equals(nextSegmentPoint)) {
                    nextSegmentPoint = l.getEnd();
                    hullPoints.add(nextSegmentPoint);
                    hull.remove(l);
                    break;
                }
                if (l.getEnd().equals(nextSegmentPoint)) {
                    nextSegmentPoint = l.getStart();
                    hullPoints.add(nextSegmentPoint);
                    hull.remove(l);
                    break;
                }
            }
        }

        // print the results
        if ((args.length == 2) && args[1].equals("timings"))
        {
            System.out.println("File name: " + args[0] + "; Time taken: " + time + " seconds; Number of vertices: " + points.size() + "; Number of times checkVal was computed: " + checkValsNum);
        }
        else if ((args.length == 1) || !args[1].equals("tmg")) {
            System.out.println("Convex hull polygon:");
            // all points along the way
            for (Point p : hullPoints) {
                System.out.println(p);
            }
        }
        else {
            // TMG file header
            System.out.println("TMG 1.0 simple");
            System.out.println(hullPoints.size() + " " + hullPoints.size());
            // all points along the way
            for (Point p : hullPoints) {
                System.out.println(p.toStringTMG());
            }
            if ((args.length > 1) && args[1].equals("tmg")) {
                // add in "graph edges", really just connections between 
                // each adjacent point
                for (int i = 0; i < hullPoints.size(); i++) {
                    System.out.print(i + " ");
                    if (i < hullPoints.size()-1) {
                        System.out.print(i+1);
                    }
                    else {
                        System.out.print(0);
                    }
                    System.out.println(" HullSeg" + i);
                }
            }
            System.out.println("Time taken to consider points: " +  time + " seconds");
        }
    }
}
