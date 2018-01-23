/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

public class TreeNode {
	
	public TreeNode leftChild;
	public TreeNode rightChild;
	public TreeNode parent;
	public Line line;

	public TreeNode(Line x) {
		this.line = x;
	}

	public void insert(Line x) {
		if (Geometry.linePosition(this.line, x) == Geometry.status.CLOCKWISE) {
			if (this.rightChild == null) {
				this.rightChild = new TreeNode(x);
				this.rightChild.parent = this;
			} else {
				this.rightChild.insert(x);
			}
		} else if (Geometry.linePosition(this.line, x) == Geometry.status.COUNTERCLOCKWISE) {
			if (this.leftChild == null) {
				this.leftChild = new TreeNode(x);
				this.leftChild.parent = this;
			} else {
				this.leftChild.insert(x);
			}
		} else if (Geometry.linePosition(this.line, x) == Geometry.status.INTERSECT) {
			Point intersection = Geometry.intersection(this.line, x);
			if (Geometry.ccw(this.line.pStart, this.line.pEnd, x.pStart) == Geometry.status.CLOCKWISE) {
				if (this.rightChild == null) {
					this.rightChild = new TreeNode(new Line(x.pStart, intersection));
					this.rightChild.parent = this;
				} else {
					this.rightChild.insert(new Line(x.pStart, intersection));
				}
				if (this.leftChild == null) {
					this.leftChild = new TreeNode(new Line(intersection, x.pEnd));
					this.leftChild.parent = this;
				} else {
					this.leftChild.insert(new Line(intersection, x.pEnd));
				}
			} else {
				if (this.leftChild == null) {
					this.leftChild = new TreeNode(new Line(x.pStart, intersection));
					this.leftChild.parent = this;
				} else {
					this.leftChild.insert(new Line(intersection, x.pEnd));
				}
				if (this.rightChild == null) {
					this.rightChild = new TreeNode(new Line(intersection, x.pEnd));
					this.rightChild.parent = this;
				} else {
					this.rightChild.insert(new Line(intersection, x.pEnd));
				}
			}
		}
	}

	public Line lookUp(Point p) {
		if (Geometry.ccw(this.line.pStart, this.line.pEnd, p) == Geometry.status.CLOCKWISE) {
			if (this.rightChild == null) {
				return this.line;
			} else {
				return this.rightChild.lookUp(p);
			}
		} else if (Geometry.ccw(this.line.pStart, this.line.pEnd, p) == Geometry.status.COUNTERCLOCKWISE) {
			if (this.leftChild == null) {
				return this.line;
			} else {
				return this.leftChild.lookUp(p);
			}
		} else {
			return this.line;
		}
	}

	public Line lineLookUp(Line l) {
		if (Geometry.linePosition(this.line, l) == Geometry.status.CLOCKWISE) {
			return this.rightChild.lineLookUp(l);
		} else if (Geometry.linePosition(this.line, l) == Geometry.status.COUNTERCLOCKWISE) {
			return this.leftChild.lineLookUp(l);
		} else {
			return this.line;
		}
	}
}
