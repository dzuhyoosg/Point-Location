/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

/**
 * This class is used to find out whether a point is on one side of a line or the other
 * and a routine to compute the intersection point of two lines
 */
public class Geometry {
	
	public static enum status {
		COUNTERCLOCKWISE, CLOCKWISE, COLINEAR, INTERSECT;
	}
	
	public static status ccw(Point pStart, Point pEnd, Point p) {
		double dx1 = pEnd.x - pStart.x;
		double dy1 = pEnd.y - pStart.y;
		double dx2 = p.x - pStart.x;
		double dy2 = p.y - pStart.y;
		if (dx1 * dy2 > dy1 * dx2) {
			return status.COUNTERCLOCKWISE;
		} else if (dx1 * dy2 < dy1 * dx2) {
			return status.CLOCKWISE;
		} else if ((dx1 * dx2 < 0) || (dy1 * dy2 < 0)) {
			return status.CLOCKWISE;
		} else if ((dx1 * dx1 + dy1 * dy1) < (dx2 * dx2 + dy2 * dy2)) {
			return status.COUNTERCLOCKWISE;
		} else {
			return status.COLINEAR;
		}
	}
	
	public static Point intersection(Line l1, Line l2) {
		if (l1.k == l2.k) {
			return new Point(-1, -1);
		} else if (l1.k == Double.POSITIVE_INFINITY) {
			return new Point(l1.pStart.x, l2.k * l1.pStart.x + l2.c);
		} else if (l2.k == Double.POSITIVE_INFINITY) {
			return new Point(l2.pStart.x, l1.k * l2.pStart.x + l1.c);
		} else {
			return new Point((l2.c - l1.c) / (l1.k - l2.k), (l1.k * l2.c - l2.k * l1.c) / (l1.k - l2.k));
		}
	}
	
	public static status linePosition(Line root, Line node) {
		if ((ccw(root.pStart, root.pEnd, node.pStart) == status.CLOCKWISE)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.CLOCKWISE)) {
			return status.CLOCKWISE;
		} else if ((ccw(root.pStart, root.pEnd, node.pStart) == status.COUNTERCLOCKWISE)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.COUNTERCLOCKWISE)) {
			return status.COUNTERCLOCKWISE;
		} else if ((ccw(root.pStart, root.pEnd, node.pStart) == status.COLINEAR)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.CLOCKWISE)) {
			return status.CLOCKWISE;
		} else if ((ccw(root.pStart, root.pEnd, node.pStart) == status.COLINEAR)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.COUNTERCLOCKWISE)) {
			return status.COUNTERCLOCKWISE;
		} else if ((ccw(root.pStart, root.pEnd, node.pStart) == status.CLOCKWISE)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.COLINEAR)) {
			return status.CLOCKWISE;
		} else if ((ccw(root.pStart, root.pEnd, node.pStart) == status.COUNTERCLOCKWISE)
				&& (ccw(root.pStart, root.pEnd, node.pEnd) == status.COLINEAR)) {
			return status.COUNTERCLOCKWISE;
		} else {
			return status.INTERSECT;
		}
	}
	
	public static boolean isInSameRegion(Point p1, Point p2) {
		if ((p1.line.equals(p2.line)) && (p1.position.equals(p2.position))) {
			for (Line l : PointLocation.lineList) {
				if (linePosition(l, new Line(p1, p2)) == status.INTERSECT) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static void lineBetweenPoints(Point p1, Point p2) {
		if (p1.position.equals(status.COLINEAR)) {
			System.out.println("Point: " + p1 + " is on line: ");
			System.out.println(p1.line.fuctionToString());
		} else if (p2.position.equals(status.COLINEAR)) {
			System.out.println("Point: " + p2 + " is on line: ");
			System.out.println(p2.line.fuctionToString());
		} else {
			System.out.println("Point: " + p1 + " and ");
			System.out.println("Point: " + p2);
			Line test = new Line(p1, p2);
			System.out.println("They are separated by the line: " + PointLocation.tree.Linelookup(test).fuctionToString());
		}
	}
	
}
