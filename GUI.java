import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class nodeGUI {
	Shape shape;
	Color color;
	Font font;
	BasicStroke stroke;
	int x, y, level;
	Node<Integer> node;
	
	public nodeGUI(Node<Integer> node, int x, int y, Color color, int level){
		this.node = node;
		this.x = x;
		this.y = y;
		this.shape = new Ellipse2D.Double(x, y, 65, 65);
		this.color = color;
		this.font = new Font("TimesRoman", Font.PLAIN, 26);
		this.stroke = new BasicStroke(3);
		this.level = level;
	}
	
   public void draw(Graphics2D g2) {
	      g2.setColor(color);
	      g2.setStroke(stroke);
	      g2.draw(shape);
          g2.setFont(font); 
          g2.drawString("" + node.data, x + 23, y + 40);
   }
}

public class GUI extends JFrame {
	
    private static final long serialVersionUID = 1;
    
    static Color black = new Color(0,0,0);
    static Color red = new Color(255,17,0);
    static int initX = 380, initY = 100;
    
    static RedBlackTree<Integer> tree;
    static ArrayList<nodeGUI> allNodes;
    
    @SuppressWarnings("serial")
	public GUI() {

        setSize(new Dimension(900, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Red Black Tree");
        
        allNodes = new ArrayList<nodeGUI>();
    	tree = new RedBlackTree<Integer>();
    	tree.insert(10);
    	tree.insert(2);
    	tree.insert(16);
    	tree.root.left.clr = color.BLACK;
    	tree.root.right.clr = color.BLACK;
    	tree.insert(8);
    	tree.insert(14);
    	tree.insert(9);
    	
        generateGUI();
        JPanel treePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                for (nodeGUI node: allNodes) {
                	node.draw(g2);
                }
            }
        };
        getContentPane().add(treePanel);
    }
    
    public void generateGUI() {
	    if (tree.root == null) {
	    	return; 
	    }
	    Queue<Node<Integer>> q = new LinkedList<Node<Integer>>(); 
	    Node<Integer> current; 
	    nodeGUI newNode = null;
	    q.add(tree.root); 
	    
	    while (!q.isEmpty()) { 
	      current = q.peek(); 
	      q.poll(); 
	      System.out.print(current.data + " ");
	      if (current == tree.root) {
	    	  newNode = new nodeGUI(current, initX + 30, initY + 40, black, 0);
	      } else {
	    	  for (nodeGUI node: allNodes) {
	    		  if (node.node == current.parent) {
	    			  int level = node.level + 1;
	    			  // If the node is red
	    		      if (current.clr == color.BLACK) {
	    		    	  if (current == current.parent.left) {
	    		    		  newNode = new nodeGUI(current, node.x - ((40 + 30*tree.size) - (40*level)), node.y + 80, black, level);
	    		    	  }  else {
	    		    		  newNode = new nodeGUI(current, node.x + ((40 + 30*tree.size) - (40*level)), node.y + 80, black, level);
	    		    	  }
	    		    // If the node is red
	    		      } else {
	    		    	  if (current == current.parent.left) {
	    		    		  newNode = new nodeGUI(current, node.x - ((40 + 30*tree.size) - (40*level)), node.y + 80, red, level);
	    		    	  } else {
	    		    		  newNode = new nodeGUI(current, node.x + ((40 + 30*tree.size) - (40*level)), node.y + 80, red, level);
	    		    	  }
	    		      }
	    			  break;
	    		  }
	    	  }
	      }
	      allNodes.add(newNode);
	      
	      if (current.left != null) 
	        q.add(current.left); 
	      if (current.right != null) 
	        q.add(current.right); 
	    } 
    }
    public static void main(String arg[]) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GUI();
            }
        });
    }

}