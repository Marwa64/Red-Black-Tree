enum color{RED,BLACK};

class Node<T>{
    T data;
    Node<T> left , right , parent;
    color clr;

    Node(T data){
        this.data=data;
        this.clr= color.RED;
        parent = right = left = null;
    }
}

public class RedBlackTree<T> {
    Node<T> root;

    public void rotateRight() {
    	
    }
    public void rotateLeft() {
    	
    }
    public void insert() {
    	
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
