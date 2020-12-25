
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
    int size;

    RedBlackTree(){
        root=null;
        size = 0;
    }

    public void rotateRight(Node<T> node) {
    	System.out.print("ROTATE " + node.data +" RIGHT \n");
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
    	System.out.print("ROTATE " + node.data + " LEFT \n");
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
        while(node!=root && node.parent.clr == color.RED){
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
                    rotateRight(nGrandParent);
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
                    node=node.parent;
                    rotateLeft(node);
                }
                else {
                    node.parent.clr = color.BLACK;
                    nGrandParent.clr = color.RED;
                    rotateLeft(nGrandParent);
                }



            }

        }
        root.clr = color.BLACK;
    }
    public Node<T> search(T data) {
    	Node<T> current = root;
    	while (current != null) {
    		//System.out.print("compare " + current.data + " to " + data + "\n");
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
    			node.parent.left = null;
    		} else {
    			node.parent.right = null;
    		}
    	}
    }
    public void fixDelete(Node<T> node) {
    	
    }
    public void delete(T data) {
    	Node<T> current = search(data);
    	Node<T> sibling = null;
    	if (current != null) {
    		// If this is a leaf
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
        					sibling = current.parent.right;
        					if (current.parent.right.clr == color.BLACK) {
        						// If the sibling's right child is red (Case 3.2 a iii)
        						if (current.parent.right.right != null) {
        							if (current.parent.right.right.clr == color.RED) {
        								rotateLeft(current.parent);
        							}
        						// If the sibling's left child is red (Case 3.2 a iv)
        						} else if (current.parent.right.left != null) {
        							if (current.parent.right.left.clr == color.RED) {
        								rotateRight(current.parent.right);
        							}
        						// If the sibling's children and black or null (Case 3.2 b)
        						} else {
        							current.parent.right.clr = color.RED;
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
        						// If the sibling's children and black or null (Case 3.2 b)
        						} else {
        							current.parent.left.clr = color.RED;
        						}
        					// If the sibling is red (Case 3.2 c i)
        					} else {
        						rotateRight(current.parent);
        					}
        				}
        			}
        		}
        		deleteNode(current);
        	}
        	// Not leaf
        	else if (current.left != null) {
        		Node<T> temp = current.left;
        		while (true) {
        			if (temp.right != null) {
        				temp = temp.right;
        			} else {
        				break;
        			}
        		}
        		current.data = temp.data;
        		if (temp.clr != current.clr) {
        			current.clr = color.BLACK;
        		}
        		deleteNode(temp);
        	} else if (current.right != null) {
        		if (current != root) {
               		boolean black = false;
            		if (current == current.parent.left) {
                		if (current.clr != current.right.clr) {
                			black = true;
                		}
            			current.parent.left = current.right;
            			current.right.parent = current.parent;
            			if (black) {
            				current.parent.left.clr = color.BLACK;
            			}
            		} else {
                		if (current.parent.right.clr != current.right.clr) {
                			black = true;
                		}
            			current.parent.right = current.right;
            			current.right.parent = current.parent;
            			if (black) {
            				current.parent.right.clr = color.BLACK;
            			}
            		}
            		size--;
        		} else {
        			root = current.right;
        			current.right.parent = null;
        			deleteNode(current);
        		}
        	}
    	}
    	
    }
    public void clear() {
    	
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
		tree.insert(1);
    	tree.insert(3);
    	tree.insert(11);
    	tree.insert(20);
    	tree.insert(5);

    	tree.print();
        System.out.println();
        //tree.delete(2);
        //tree.print();
    }

}
