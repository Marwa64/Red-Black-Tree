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

    public void rotateRight(Node<T> node) {
    	
    }
    public void rotateLeft(Node<T> node) {
    	
    }
    public void insert() {
    	
    }
    public Node<T> search(T data) {
    	Node<T> current = root;
    	while (current != null) {
    		if (current.data == data) {
    			return current;
    		} else if (current.data > data) {
    			current = current.left;
    		} else {
    			current = current.right;
    		}
    	}
    	return null;
    }
    public void delete(T data) {
    	Node<T> node = search(data);
    	// If this is a leaf
    	if (node.right == null && node.left == null && node != root) {
    		// Case 1
    		if (node.clr != node.parent.clr) {
    			node.parent.clr = color.BLACK;
    		} else {
    			// Case 3
    			if (node.clr == color.BLACK && node.parent.clr == color.BLACK) {
    				// If this node is the left child
    				if (node == node.parent.left) {
    					// If the sibling is black
    					if (node.parent.right.clr == color.BLACK) {
    						// If the sibling's right child is red (Case 3.2 a iii)
    						if (node.parent.right.right.clr == color.RED) {
    							rotateLeft(node.parent);
    						// If the sibling's left child is red (Case 3.2 a iv)
    						} else if (node.parent.right.left.clr == color.RED) {
    							rotateRight(node.parent.right);
    						}
    					// If the sibling is red (Case 3.2 c iii)
    					} else {
    						rotateLeft(node.parent);
    					}
    				} 
    				// If this node is the right child
    				else {
    					// If the sibling is black
    					if (node.parent.left.clr == color.BLACK) {
    						// If the sibling's right child is red (Case 3.2 a ii)
    						if (node.parent.left.right.clr == color.RED) {
    							rotateLeft(node.parent.left);
    						// If the sibling's left child is red (Case 3.2 a i)
    						} else if (node.parent.left.left.clr == color.RED) {
    							rotateRight(node.parent);
    						}
    					// If the sibling is red (Case 3.2 c i)
    					} else {
    						rotateRight(node.parent);
    					}
    				}
    			}
    		}
    		node = null;
    	}
    	// Case 2
    	else if (node.right != null && node.left == null) {
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
