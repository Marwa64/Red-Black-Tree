enum color{RED,BLACK};

class Node{
    int data;
    Node left , right , parent;
    color clr;

    Node(int data){
        this.data=data;
        this.clr= color.RED;
        parent = right = left = null;
    }
}

public class RedBlackTree {
    Node root;




    
    public static void main(String[] args) {

    }





}
