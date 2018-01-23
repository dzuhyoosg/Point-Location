/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

public class Point {
	
	public double x, y;
	public boolean isNull; // to tell if the point is out of plane
	public Line line; // the line that separates the points
	public Geometry.status position; // the position of the point respect to the line
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.isNull = (x < 0 || y < 0 || x > 1 || y > 1);
	}
	
	public boolean isNull() {
		return isNull;
	}
	
	public boolean equals(Point p) {
		return ((this.x == p.x) && (this.y == p.y));
	}
	
	public String toString() {
		return "x = " + x + "; y = " + y;
	}
	
}
