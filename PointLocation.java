/* NAME: Ziyu Song
 * NET ID: zsong10
 * ASSIGNMENT: Project #3
 * LAB SESSION: TR 615-730pm
 * Collaborator: Sifan Ye (sye8), Qiuyue Sun (qsun15)
 * COURSE: CSC 172
 * TA: Nathaniel Potrepka
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The interface of the project and display messages of lines and points
 */
public class PointLocation {
	
	public static String filename;
	public static int count;
	public static Scanner scan;
	
	public static JFrame frame = new JFrame("POINT LOCATION");
	public static JButton button;
	public static JTextField text;
	public static Canvas canvas = new Canvas();
	public static Graphics g;
	
	public static ArrayList<Line> lineList = new ArrayList<Line>();
	public static ArrayList<Point> pointList = new ArrayList<Point>();
	public static BinaryTrees tree = new BinaryTrees();
	
	public static class Canvas extends JPanel implements MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Canvas() {
			this.setPreferredSize(new Dimension(500, 500));
			this.setVisible(true);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			for (Line l : lineList) {
				g.drawLine((int) (l.pStart.x * 500), (int) (l.pStart.y * 500), (int) (l.pEnd.x * 500), (int) (l.pEnd.y * 500));
			}
			for (Point p : pointList) {
				g.fillOval((int) (p.x * 500) - 2, (int) (p.y * 500) - 2, 4, 4);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int xlocation = e.getX();
			int ylocation = e.getY();
			
			g = getGraphics();
			g.fillOval(xlocation - 3, ylocation - 3, 6, 6);
			
			Point temp = new Point((double) (xlocation - 3) / 500, (double) (ylocation - 3) / 500);
			
			pointList.add(temp);
			
			temp.line = tree.lookup(temp);
			temp.position = Geometry.ccw(temp.line.pStart, temp.line.pEnd, temp);
			
			if (pointList.size() % 2 == 0) {
				if (Geometry.isInSameRegion(pointList.get(pointList.size() - 2), pointList.get(pointList.size() - 1))) {
					System.out.println("Point: " + pointList.get(pointList.size() - 2) + " and");
					System.out.println("Point: " + pointList.get(pointList.size() - 1));
					System.out.println("They are in the same region");
				} else {
					Geometry.lineBetweenPoints(pointList.get(pointList.size() - 2), pointList.get(pointList.size() - 1));
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	protected static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pointList.clear();
			canvas.repaint();
		}
	}

	protected static class TextFieldHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			filename = text.getText();
		}
	}

	public static void main(String[] args) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 700);
		
		// this button will clear all the points in the canvas
		button = new JButton("Clear Points");
		frame.add(button, BorderLayout.NORTH);
		button.addActionListener(new ButtonHandler());
		button.setToolTipText("Click to clear the point in the canvas");
		
		// enter the test file name and press enter
		text = new JTextField();
		frame.add(text, BorderLayout.SOUTH);
		text.addActionListener(new TextFieldHandler());
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setToolTipText("Please enter the file name");

		frame.setLocation((width - 500) / 2, (height - 500) / 2);
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		// let the console sleep while wait for the input from textfield
		while (filename == null) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			scan = new Scanner(new File(filename));
			count = Integer.parseInt(scan.next());
			canvas.addMouseListener(canvas);

			// scan lines into the canvas
			while (scan.hasNext() && count > 0) {
				double x1 = Double.parseDouble(scan.next());
				double y1 = Double.parseDouble(scan.next());
				double x2 = Double.parseDouble(scan.next());
				double y2 = Double.parseDouble(scan.next());
				lineList.add(new Line(new Point(x1, y1), new Point(x2, y2)));
				count--;
			}

			// scan points into the canvas
			while (scan.hasNext()) {
				double x = Double.parseDouble(scan.next());
				double y = Double.parseDouble(scan.next());
				pointList.add(new Point(x, y));
			}
		} catch (FileNotFoundException | NumberFormatException e) {
			System.out.println("Invalid Input");
			e.printStackTrace();
		}
		
		canvas.repaint();
		
		for (Line l : lineList) {
			tree.insert(l);
		}
		
		for (Point p : pointList) {
			p.line = tree.lookup(p);
			p.position = Geometry.ccw(p.line.pStart, p.line.pEnd, p);
		}
		
		for (int i = 0; i < pointList.size() / 2; i++) {
			if (Geometry.isInSameRegion(pointList.get(2 * i), pointList.get(2 * i + 1))) {
				System.out.println("Point: " + pointList.get(2 * i) + " and");
				System.out.println("Point: " + pointList.get(2 * i + 1));
				System.out.println("They are in the same region");
				System.out.println();
			} else {
				Geometry.lineBetweenPoints(pointList.get(2 * i), pointList.get(2 * i + 1));
			}
		}
		
		System.out.println("The number of external nodes in the tree is " + tree.numExternalNodes(tree.root));
		System.out.println("The external path length of the tree is " + tree.externalPathLength());
		System.out.println("The average path length is " + tree.avgPathLength()); // external path length divided by number of external nodes
		System.out.println();
	}

}
