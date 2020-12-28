
enum color{RED,BLACK};

class Node<T extends  Comparable<T> >{
    T data;
    Node<T> left , right , parent;
    color clr;
    boolean doubleBlack;
    
    Node(T data){
        this.data=data;
        this.clr= color.RED;
        parent = right = left = null;
        doubleBlack = false;
    }
}

public class RedBlackTree<T extends Comparable<T>> {
    Node<T> root;
    int size;

    RedBlackTree(){
        root=null;
        size = 0;
    }

    public void rotateRight(Node<T> node) {
    	//System.out.print("ROTATE " + node.data +" RIGHT \n");
    	Node<T> temp = node.left;
    	temp.parent = node.parent;
    	if (node != root) {
        	if (node == node.parent.left) {
        		node.parent.left = temp;
        	} else {
        		node.parent.right = temp;
        	}
    	}
    	node.parent = temp;
    	node.left = temp.right;
    	if (temp.right != null) {
    		temp.right.parent = node;
    	}
    	temp.right = node;
    	if (node.parent.parent == null) {
    		root = node.parent;
    	}
    }
    public void rotateLeft(Node<T> node) {
    	//System.out.print("ROTATE " + node.data + " LEFT \n");
    	Node<T> temp = node.right;
    	temp.parent = node.parent;
    	if (node != root) {
        	if (node == node.parent.left) {
        		node.parent.left = temp;
        	} else {
        		node.parent.right = temp;
        	}
    	}
    	node.parent = temp;
    	node.right = temp.left;
    	if (temp.left != null) {
    		temp.left.parent = node;
    	}
    	temp.left = node;
    	if (node.parent.parent == null) {
    		root = node.parent;
    	}
    }

    public void insert(T data) {
        if (root == null) {
            root = new Node<>(data);
            root.clr = color.BLACK;
            size++;
            return;
        }

        Node<T> node = new Node<>(data);
        Node<T> current = this.root;
        Node<T> temp = null;

        while (current != null) {
            temp = current;
            if (current.data.compareTo(node.data) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        node.parent = temp;

        if (temp.data.compareTo(node.data) < 0)
            temp.right = node;
        else temp.left = node;

        node.left = node.right = null;
        node.clr = color.RED;
		size++;
		fixInsert(node);

	}
    public void fixInsert(Node<T> node){

        Node<T> nGrandParent , nUncle;
        while( node !=null && node!=root && node.parent.clr == color.RED){
            nGrandParent=node.parent.parent;
            if(node.parent == nGrandParent.left){
                nUncle= nGrandParent.right;

                if(nUncle !=null && nUncle.clr == color.RED){
                    node.parent.clr = color.BLACK;
                    nUncle.clr= color.BLACK;
                    nGrandParent.clr = color.RED;
                    node=node.parent.parent;
                }
                else if(node == node.parent.right){
                    node=node.parent;
                    rotateLeft(node);
                }
                else {
                    node.parent.clr = color.BLACK;
                    nGrandParent.clr = color.RED;
                    rotateRight(node.parent.parent);
                }


            }
            else {
                nUncle= nGrandParent.left;

                if(nUncle !=null && nUncle.clr == color.RED){
                    node.parent.clr = color.BLACK;
                    nUncle.clr= color.BLACK;
                    nGrandParent.clr = color.RED;
                    node=node.parent.parent;
                }
                else if(node == node.parent.right){
					node.parent.clr = color.BLACK;
					nGrandParent.clr = color.RED;
                    rotateLeft(node.parent.parent);
                }
                else {
					node=node.parent;
                    rotateRight(node);
                }



            }

        }
        root.clr = color.BLACK;
    }
    public Node<T> search(T data) {
    	Node<T> current = root;
    	while (current != null) {
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
    private void deleteNode(Node<T> node) {
    	size--;
    	if (node.parent != null) {
    		if (node == node.parent.left) {
    			if (node.left != null)
    				node.left.parent = node.parent;
    			node.parent.left = node.left;
    			
    		} else {
    			if (node.right != null)
    				node.right.parent = node.parent;
    			node.parent.right = node.right;
    		}
    	}
    	node = null;
    }
    private void blackRedLine(Node<T> sibling, Node<T> child, boolean left) {
		// re-color sibiling's child
		child.clr = color.BLACK;
		sibling.clr = color.RED;
		if (left) {
			rotateRight(sibling.parent);
		} else {
			rotateLeft(sibling.parent);
		}
    }
    private void blackRedTriangle(Node<T> sibling, Node<T> child, boolean left) {
    	// re-color sibling and sibling's child
    	sibling.clr = color.RED;
    	child.clr = color.BLACK;
    	if (left) {
    		rotateLeft(sibling);
    	} else {
    		rotateRight(sibling);
    	}
    	blackRedLine(child, sibling, left);
    }
    private void doubleBlack(Node<T> node, Node<T> sibling, boolean left) {
    	//System.out.print("\nDOUBLE BLACK ON " + sibling.data + "'S SIBLING \n");
		// If the sibling is black
		if (sibling.clr == color.BLACK) {
			if (sibling.right != null && sibling.left != null) {
				// if sibling is right
				if (left) {
					if (sibling.right.clr == color.RED) {
						blackRedLine(sibling, sibling.right, !left);
					}
				// If sibling is left
				} else {
					if (sibling.left.clr == color.RED) {
						blackRedLine(sibling, sibling.left, !left);
					}
				}
			} else {
				// If the sibling's right child is red (Case 3.2 a iii)
				if (sibling.right != null) {
					if (sibling.right.clr == color.RED) {
						// if sibling is right
						if (left) {
							blackRedLine(sibling, sibling.right, !left);
						} else {
							blackRedTriangle(sibling, sibling.right, !left);
						}
					}
				// If the sibling's left child is red (Case 3.2 a iv)
				} else if (sibling.left != null) {
					if (sibling.left.clr == color.RED) {
						// if sibling is right
						if (left) {
							blackRedTriangle(sibling, sibling.left, !left);
						} else {
							blackRedLine(sibling, sibling.left, !left);
						}
					}
				// If the sibling's children and black or null (Case 3.2 b)
				} else {
					sibling.clr = color.RED;
				}
			}
		// If the sibling is red (Case 3.2 c iii)
		} else {
			// re-color sibling and parent
			sibling.clr = color.BLACK;
			if (node.parent.clr == color.BLACK) {
				node.parent.clr = color.RED;
			} else {
				node.parent.clr = color.BLACK;
			}
			if (left) {
				rotateLeft(node.parent);
			} else {
				rotateRight(node.parent);
			}
			if (left) {
				if (node.parent.right.clr == color.BLACK) {
         			if (node.parent.clr == color.RED) {
        				node.parent.clr = color.BLACK;
          			}
					doubleBlack(node, node.parent.right, left);
				}
			} else {
				if (node.parent.left.clr == color.BLACK) {
         			if (node.parent.clr == color.RED) {
        				node.parent.clr = color.BLACK;
          			}
					doubleBlack(node,node.parent.left, left);
				}
			}
		}
    }
    public void delete(T data) {
    	Node<T> current = search(data);
    	if (current != null) {
        	// If this is the only node
        	if (current == root && size == 1) {
        		root = null;
        		size--;
        		return;
        	}
        	Node<T> swapNode = current;
        	// If the node we want to delete has a left child
    		if (current.left != null) {
    			//System.out.println("SWAPNODE: " + swapNode.data + " SWAPNODE PARENT: " + swapNode.parent.data);
           		swapNode = current.left;
        		// If the left child has right children
        		if (swapNode.right != null) {
            		while (true) {
            			if (swapNode.right != null) {
            				swapNode = swapNode.right;
            			} else {
            				break;
            			}
            		}
        		}
        		current.data = swapNode.data;
 
        	// If the node we want to delete has a right child
    		} else if (current.right != null) {
    			swapNode = current.right;
    			current.data = swapNode.data;
        	}
    		// Deleting the swapNode
			if (swapNode.clr == color.BLACK) {
           		if (swapNode.left == null || swapNode.left.clr == color.BLACK) {
          			if (swapNode.parent.clr == color.RED) {
        				swapNode.parent.clr = color.BLACK;
        				
          			}
    				// If this node is the left child
    				if (swapNode == swapNode.parent.left) {
    					doubleBlack(swapNode, swapNode.parent.right, true);
    				} 
    				// If this node is the right child
    				else {
    					doubleBlack(swapNode, swapNode.parent.left, false);
    				}
        		} else {
        			if (swapNode == current.left && swapNode.left != null) {
        				swapNode.left.clr = color.BLACK;
        			}
        			else if (swapNode.parent.clr == color.RED) {
        				swapNode.parent.clr = color.BLACK;
          			}
        		}
			}
    		deleteNode(swapNode);
    	}
    	
    }
    public void clear(Node<T> node) {
        if (node == null) return;  
        
        clear(node.left);  
        clear(node.right);  
          
        deleteNode(node); 
    }
    public void printInorder (Node<T> root, String path) {
        if(root == null)return;

        printInorder(root.left,path+"left ");
        System.out.println(root.data + " " + root.clr +" - " + path+ " -");
        printInorder(root.right , path+"right ");
    }

    public void print(){
        printInorder(root,"root ");
    }

    public static void main(String[] args) {
    	RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
    	tree.insert(10);
		tree.insert(2);
		tree.insert(16);


    	tree.print();
        System.out.println();
    	tree.delete(10);
		tree.delete(2);
		tree.delete(16);
        tree.print();
    }

}
