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

    public void rotateRight(Node node) {
    	
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

    public void search() {
    	
    }
    public void delete(Node<T> node) {
    	// If this is a leaf
    	if (node.right == null && node.left == null) {
    		// Case 1
    		if (node.clr != node.parent.clr) {
    			node.parent.clr = color.BLACK;
    			node = null;
    		} else {
    			// Case 2
    			
    		}
    	}
    	// Case 2
    	if (node.right != null && node.left == null) {
    		node = node.right;
    		node.clr = color.BLACK;
    		node.right = null;
    	} else if (node.left != null && node.right == null) {
    		node = node.left;
    		node.clr = color.BLACK;
    		node.left = null;
    	}
    	// Case 3
    	
    }
    public void clear() {
    	
    }
    public void printInorder() {
    	
    }

    public static void main(String[] args) {
    	
    }

}
