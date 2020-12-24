import javax.swing.plaf.IconUIResource;
import java.awt.*;

enum color{RED,BLACK};

class Node<T extends  Comparable<T> >{
    T data;
    Node<T> left , right , parent;
    color clr;

    Node(T data){
        this.data=data;
        this.clr= color.RED;
        parent = right = left = null;
    }
}

public class RedBlackTree<T extends Comparable<T>> {
    Node<T> root;

    public void rotateRight(Node<T> node) {
    	
    }
    public void rotateLeft(Node<T> node) {
    	
    }
    public void insert(Node<T> node) {
        Node<T> current=this.root;

        while (current!=null) {
            if (current.data.compareTo(node.data) < 0) {
                current=current.left;
            }
            else {
                current=current.right;
            }
        }
        node.parent=current;

        if(current.data.compareTo(node.data)<0)
            current.right=node;
        else current.left=node;

        node.left=node.right=null;
        node.clr=color.RED;

        //fixing violation;

        Node<T> nGrandParent , nUncle;
        while(node.parent.clr == color.RED){
            nGrandParent=node.parent.parent;
            if(node.parent == nGrandParent.left){
                nUncle= nGrandParent.right;

                if(nUncle.clr == color.RED){
                    //recoloring
                    node.parent.clr = color.BLACK;
                    nUncle.clr= color.BLACK;
                    nGrandParent.clr = color.RED;
                    node=nGrandParent;
                }
                else {
                    if(node == node.parent.right){
                        node=node.parent;
                        rotateLeft(node);
                    }
                    else {
                        node.parent.clr = color.BLACK;
                        nGrandParent.clr = color.RED;
                        rotateRight(nGrandParent);
                    }

                }
            }
            else {
                nUncle= nGrandParent.left;

                if(nUncle.clr == color.RED){
                    node.parent.clr = color.BLACK;
                    nUncle.clr= color.BLACK;
                    nGrandParent.clr = color.RED;
                    node=nGrandParent;
                }
                else {
                    if(node == node.parent.right){
                        node=node.parent;
                        rotateRight(node);
                    }
                    else {
                        node.parent.clr = color.BLACK;
                        nGrandParent.clr = color.RED;
                        rotateLeft(nGrandParent);
                    }

                }

            }

        }
        root.clr = color.BLACK;
    }
    
    public Node<T> search(T data) {
    	Node<T> current = root;
    	while (current != null) {
    		System.out.print("compare " + current.data + " to " + data + "\n");
    		if (current.data == data) {
    			return current;
    		} else if (current.data.compareTo(data) > 0) {
    			current = current.left;
    		} else {
    			current = current.right;
    		}
    	}
    	return null;
    }
    
    public void delete(T data) {
    	Node<T> current = search(data);
    	// If this is a leaf
    	if (current != null) {
        	if (current.right == null && current.left == null && current != root) {
        		// Case 1
        		if (current.clr != current.parent.clr) {
        			current.parent.clr = color.BLACK;
        		} else {
        			// Case 3
        			if (current.clr == color.BLACK && current.parent.clr == color.BLACK) {
        				// If this node is the left child
        				if (current == current.parent.left) {
        					// If the sibling is black
        					if (current.parent.right.clr == color.BLACK) {
        						// If the sibling's right child is red (Case 3.2 a iii)
        						if (current.parent.right.right.clr == color.RED) {
        							rotateLeft(current.parent);
        						// If the sibling's left child is red (Case 3.2 a iv)
        						} else if (current.parent.right.left.clr == color.RED) {
        							rotateRight(current.parent.right);
        						}
        					// If the sibling is red (Case 3.2 c iii)
        					} else {
        						rotateLeft(current.parent);
        					}
        				} 
        				// If this node is the right child
        				else {
        					// If the sibling is black
        					if (current.parent.left.clr == color.BLACK) {
        						// If the sibling's right child is red (Case 3.2 a ii)
        						if (current.parent.left.right.clr == color.RED) {
        							rotateLeft(current.parent.left);
        						// If the sibling's left child is red (Case 3.2 a i)
        						} else if (current.parent.left.left.clr == color.RED) {
        							rotateRight(current.parent);
        						}
        					// If the sibling is red (Case 3.2 c i)
        					} else {
        						rotateRight(current.parent);
        					}
        				}
        			}
        		}
        		current = null;
        	}
        	// Case 2
        	/*else if (node.right != null && node.left == null) {
        		node = node.right;
        		node.clr = color.BLACK;
        		node.right = null;
        	} else if (node.left != null && node.right == null) {
        		node = node.left;
        		node.clr = color.BLACK;
        		node.left = null;
        	}*/
        	// Case 3
    	}
    	
    }
    public void clear() {
    	
    }
    public void printInorder (Node<T> root) {
        if(root == null)return;

        printInorder(root.left);
        System.out.println(root.data + " ");
        printInorder(root.right);

    }

    public void print(){
        printInorder(root);
    }

    public static void main(String[] args) {
    	RedBlackTree<Integer> x = new RedBlackTree<Integer>();
    	x.root = new Node<Integer>(12);
    	x.root.clr = color.BLACK;
    	x.root.right = new Node<Integer>(15);
    	x.root.right.parent = x.root;
    	x.root.left = new Node<Integer>(10);
    	x.root.left.parent = x.root;
    	x.print();
    	x.delete(10);
    	x.print();
    }

}
