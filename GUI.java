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

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		this.shape = new Ellipse2D.Double(x, y, 60, 60);
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
          g2.drawString("" + node.data, x + 20, y + 37);
   }
}

public class GUI extends JFrame {
	
    private static final long serialVersionUID = 1;
    
    static Color black = new Color(0,0,0);
    static Color red = new Color(255,17,0);
    static int initX = 560, initY = 140;
    static JPanel treePanel;
    
    static RedBlackTree<Integer> tree;
    static ArrayList<nodeGUI> allNodes;
    private JTextField deleteField;
    private JTextField insertField;
   
	public GUI() {

        setSize(new Dimension(1200, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Red Black Tree");
        
        allNodes = new ArrayList<nodeGUI>();
    	tree = new RedBlackTree<Integer>();
    	
        treePanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                super.paintComponent(g2);
                for (nodeGUI node: allNodes) {
                	node.draw(g2);
                }
            }
        };
        getContentPane().add(treePanel);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(540, 12, 86, 26);
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!deleteField.getText().equalsIgnoreCase("")) {
            		tree.delete(Integer.parseInt(deleteField.getText()));
                    generateGUI();
                    treePanel.repaint();
                    deleteField.setText("");
        		}
        	}
        });
        treePanel.setLayout(null);
        
        deleteField = new JTextField();
        deleteField.setBounds(461, 11, 69, 29);
        treePanel.add(deleteField);
        deleteField.setColumns(10);
        treePanel.add(btnDelete);
        
        insertField = new JTextField();
        insertField.setColumns(10);
        insertField.setBounds(246, 11, 69, 29);
        treePanel.add(insertField);
        
        JButton btnInsert = new JButton("Insert");
        btnInsert.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!insertField.getText().equalsIgnoreCase("")) {
            		tree.insert(Integer.parseInt(insertField.getText()));
                    generateGUI();
                    treePanel.repaint();
                    insertField.setText("");
        		}
        	}
        });
        btnInsert.setBounds(325, 12, 86, 26);
        treePanel.add(btnInsert);
    	/*tree.insert(30);
		tree.insert(20);
		tree.insert(40);*/
        
    	tree.insert(10);
		tree.insert(2);
		tree.insert(16);
		tree.insert(1);
    	tree.insert(3);
    	tree.insert(11);
    	tree.insert(20);
    	tree.insert(5);
        generateGUI();
        treePanel.repaint();
    }
    
    public void generateGUI() {
	    if (tree.root == null) {
	    	return; 
	    }
	    allNodes.clear();
	    Queue<Node<Integer>> q = new LinkedList<Node<Integer>>(); 
	    Node<Integer> current; 
	    nodeGUI newNode = null;
	    q.add(tree.root); 
	    
	    while (!q.isEmpty()) { 
	      current = q.peek(); 
	      q.poll(); 
	      //System.out.print(current.data + " ");
	      if (current == tree.root) {
	    	  newNode = new nodeGUI(current, initX, initY, black, 0);
	      } else {
	    	  for (nodeGUI node: allNodes) {
	    		  if (node.node == current.parent) {
	    			  int level = node.level + 1;
	    			  // If the node is red
	    		      if (current.clr == color.BLACK) {
	    		    	  if (current == current.parent.left) {
	    		    		  newNode = new nodeGUI(current, node.x - (20 * (10-level)), node.y + 80, black, level);
	    		    	  }  else {
	    		    		  newNode = new nodeGUI(current, node.x + (20 * (10-level)), node.y + 80, black, level);
	    		    	  }
	    		    // If the node is red
	    		      } else {
	    		    	  if (current == current.parent.left) {
	    		    		  newNode = new nodeGUI(current, node.x - (20 * (10-level)), node.y + 80, red, level);
	    		    	  } else {
	    		    		  newNode = new nodeGUI(current, node.x + (20 * (10-level)), node.y + 80, red, level);
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
            public void run()
			{
				//to enhance frame and buttons shape
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}

				new GUI();
            }
        });
    }

}