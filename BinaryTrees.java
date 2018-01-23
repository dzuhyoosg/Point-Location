/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

public class BinaryTrees {
	
	public TreeNode root = null;
	
	public void insert(Line l) {
		if (this.root == null) {
			this.root = new TreeNode(l);
		} else {
			this.root.insert(l);
		}
	}
	
	public Line lookup(Point p) {
		if (this.root == null) {
			System.out.println("Not Found");
			return null;
		} else {
			return this.root.lookUp(p);
		}
	}
	
	public Line Linelookup(Line l) {
		if (this.root == null) {
			return null;
		} else {
			return this.root.lineLookUp(l);
		}
	}
	
	public int numExternalNodes(TreeNode n) {
		if (n == null) {
			return 1;
		} else {
			return numExternalNodes(n.leftChild) + numExternalNodes(n.rightChild);
		}
	}
	
	public int externalPathLength() {
		return extPathLength(root, 0);
	}
	
	private int extPathLength(TreeNode n, int depth) {
		if (n == null) {
			return depth;
		} else {
			return extPathLength(n.leftChild, depth + 1) + extPathLength(n.rightChild, depth + 1);
		}
	}
	
	public int avgPathLength() {
		return this.externalPathLength() / this.numExternalNodes(this.root);
	}
	
}
