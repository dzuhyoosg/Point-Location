/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

public class Line {
	
	public Point pStart, pEnd;
	public double k, c;
	
	public Line(Point a, Point b) {
		this.pStart = a;
		this.pEnd = b;
		this.k = this.slope(pStart, pEnd);
		this.c = - (k * pStart.x - pStart.y);
	}
	
	public double slope(Point a, Point b) {
		if (pStart.x == pEnd.x) {
			return Double.POSITIVE_INFINITY;
		} else {
			return (a.y - b.y) / (a.x - b.x);
		}
	}
	
	public boolean equals(Line l) {
		return ((this.pStart.equals(l.pStart)) && (this.pEnd.equals(l.pEnd)));
	}
	
	public String toString() {
		return "Start point: " + pStart + " End Point: " + pEnd + "\n" + "Function: y = " + (0 - k) + "x + " + c + "\n";
	}
	
	public String fuctionToString() {
		return "Function: y = " + (0 - k) + "x + " + c + "\n";
	}
	
}
