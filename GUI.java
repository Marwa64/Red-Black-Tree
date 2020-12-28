import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class nodeGUI {
	Shape circle;
	Color color;
	Font font;
	BasicStroke stroke;
	int x, y, level;
	Node<Integer> node;
	Line2D line1;
	Line2D line2;
	
	public nodeGUI(Node<Integer> node, int x, int y, Color color, int level){
		this.node = node;
		this.x = x;
		this.y = y;
		this.circle = new Ellipse2D.Double(x, y, 41, 41);
		this.color = color;
		this.font = new Font("TimesRoman", Font.PLAIN, 23);
		this.stroke = new BasicStroke(3);
		this.level = level;
		this.line1 = new Line2D.Float(0, 0, 0, 0);
		this.line2 = new Line2D.Float(0, 0, 0, 0);
	}
	
   public void draw(Graphics2D g2, ArrayList<nodeGUI> allNodes) {
	      g2.setColor(color);
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2.setStroke(stroke);
	      g2.draw(circle);
          g2.setFont(font); 
          g2.drawString("" + node.data, x + 12, y + 28);
          g2.setColor(Color.BLACK);
          if (node.left != null || node.right != null) {
              for (nodeGUI current: allNodes) {
                	if (node.left != null) {
                		if (current.node == node.left) {
                			this.line1 = new Line2D.Float(this.x+20, this.y+42, current.x + 20, current.y);
                			continue;
                		}
                	}
                	if (node.right != null) {
                		if (current.node == node.right) {
                			this.line2 = new Line2D.Float(this.x+20, this.y+42, current.x + 20, current.y);
                		}
                	}
                }
          }
          g2.draw(line1);
          g2.draw(line2);
   }
}

public class GUI extends JFrame {
	
    private static final long serialVersionUID = 1;
    
    static Color black = new Color(0,0,0);
    static Color red = new Color(255,17,0);
    static Color background = new Color(176,222,255);
    static int initX = 560, initY = 140;
    static JPanel treePanel;
    
    static RedBlackTree<Integer> tree;
    static ArrayList<nodeGUI> allNodes;
    private JTextField deleteField;
    private JTextField insertField;
   
	public GUI() {

        setSize(new Dimension(1200, 700));
        setResizable(false);
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
                	node.draw(g2, allNodes);
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
					deleteField.requestFocus();


        		}
        	}
        });
        treePanel.setLayout(null);
        treePanel.setBackground(background);
        
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
					insertField.requestFocus();

        		}
        	}
        });
        btnInsert.setBounds(325, 12, 86, 26);
        treePanel.add(btnInsert);


		insertField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				btnInsert.doClick();
			}
		});



		deleteField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				btnDelete.doClick();
			}
		});


		JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		tree.clear(tree.root);
        		tree.root = null;
                generateGUI();
                treePanel.repaint();
        	}
        });
        btnClear.setBounds(850, 12, 86, 26);
        treePanel.add(btnClear);
    	/*tree.insert(30);
		tree.insert(20);
		tree.insert(40);*/
        
    	/*tree.insert(15);
		tree.insert(8);
		tree.insert(30);
		tree.insert(5);
    	tree.insert(10);
    	tree.insert(21);
    	tree.insert(50);
    	tree.insert(3);*/
        generateGUI();
        treePanel.repaint();
    }
    
    public void generateGUI() {
	    if (tree.root == null) {
	    	allNodes.clear();
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
	    		    		  if (level == 1) {
	    		    			  newNode = new nodeGUI(current, node.x - 300, node.y + 90, black, level);
	    		    		  } else {
	    		    			  newNode = new nodeGUI(current, node.x - (260 - (60*level)), node.y + 80, black, level);
	    		    		  }
	    		    		  
	    		    	  }  else {
	    		    		  if (level == 1) {
	    		    			  newNode = new nodeGUI(current, node.x + 300, node.y + 90, black, level);
	    		    		  } else {
	    		    			  newNode = new nodeGUI(current, node.x + (260 - (60*level)), node.y + 80, black, level);
	    		    		  }
	    		    		  
	    		    	  }
	    		    // If the node is red
	    		      } else {
	    		    	  if (current == current.parent.left) {
	    		    		  if (level == 1) {
	    		    			  newNode = new nodeGUI(current, node.x - 300, node.y + 90, red, level);
	    		    		  } else {
	    		    			  newNode = new nodeGUI(current, node.x - (260 - (60*level)), node.y + 80, red, level);
	    		    		  }
	    		    		  
	    		    	  } else {
	    		    		  if (level == 1) {
	    		    			  newNode = new nodeGUI(current, node.x + 300, node.y + 90, red, level);
	    		    		  } else {
	    		    			  newNode = new nodeGUI(current, node.x + (260 - (60*level)), node.y + 80, red, level);
	    		    		  }
	    		    		  
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