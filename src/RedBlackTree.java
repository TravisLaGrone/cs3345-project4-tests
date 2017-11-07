// Vincent Luong
// vll150030 
// Project 4, Red Black Tree Simplified, Insertion only
// Purpose: The purpose of the project is to test the understanding of red black trees and how to implement them in code. In this implementation,
// I have my insert method check for any rule breakers in the four requirements of a red black tree, that is: if the root is black, if there are any red leaves, and if the height of black nodes are the same at all points in the tree.
// Overall, this project was difficult and challenging, but I understand red black trees a lot more after doing it.
public class RedBlackTree<E extends Comparable<E>> {
    private static boolean red = false;
    private static boolean black = true;
    private Node<E> root;
    
    public boolean getRed() {
        return red;
    }
    public boolean getBlack() {
        return black;
    }
    public Node getRoot() {
        return root;
    }
    public void setRed(boolean r) {
        this.red = r;
    }
    public void setBlack(boolean b) {
        this.black = b;
    }
    
    public boolean insert(E e) throws NullPointerException { // boolean insert, takes argument Element e, an object
        Node<E> tmp = new Node(e, null, null, null, red);
        
        root = insert(root, tmp);
        if (root == null) {
            return false;
        }
        checkTree(root, tmp);
        return true;
    }
    private Node insert(Node<E> root, Node<E> tmp) {
        if(root == null) { // creates new node and inserts into tree if null
            return tmp;
        }
        int compare = tmp.getElement().compareTo(root.getElement());
        if (compare < 0) { // otherwise recur on left subtree 
            root.setLeftChild(insert(root.getLeftChild(), tmp));
            root.getLeftChild().setParent(root);
        }
        else if(compare > 0) { // opposite, recur on right subtree
            root.setRightChild(insert(root.getRightChild(), tmp));
            root.getRightChild().setParent(root);
        }
        return root; // return root
    }
    public void checkTree(Node<E> root, Node <E> tmp) { /// checks tree for violations, such as red child of red parent, red uncle in the case of rotations, and root rotations
        Node<E> parentTmp; // holds pointer for parent
        Node<E> gndTmp; // holds pointer for grandparent
        
        while(tmp != root && tmp.getParent().getColor() == red && tmp.getColor() != black) {
            parentTmp = tmp.getParent(); // parent pointer 
            gndTmp = tmp.getParent().getParent(); // grandparent pointer
            // PART A
            // CASE 1 RECOLORING OF UNCLE, AKA RIGHT CHILD OF PARENT OF PARENT
            if (parentTmp == gndTmp.getLeftChild()) { // PERTAINS TO THE LEFT SUBTREE, LEFT LEFT OR LEFT RIGHT ROTATIONS
                Node<E> uncleTmp = gndTmp.getRightChild();
                if (uncleTmp != null && uncleTmp.getColor() == red) {
                    gndTmp.setColor(red);
                    parentTmp.setColor(black);
                    uncleTmp.setColor(black);
                    tmp = gndTmp;
                }
                
                else {
                    // CASE 2 LEFT ROTATE, IMBALANCE ON LEFT TREE
                    if (tmp == parentTmp.getRightChild()) {
                        leftRotate(root, parentTmp);
                        tmp = parentTmp;
                    }
                        // CASE 3 RIGHT ROTATE
                    rightRotate(root, gndTmp);
                    parentTmp.setColor(!parentTmp.getColor());
                    gndTmp.setColor(!gndTmp.getColor());
                    tmp = parentTmp;
                    
                    
                }
            }
            // PART B
            else { // PERTAINS TO RIGHT SUBTREE, I.E RIGHT RIGHT, RIGHT LEFT ROTATIONS
                Node<E> uncleTmp = gndTmp.getLeftChild(); // uncle of the child that we are looking at
                
                if (uncleTmp != null && uncleTmp.getColor() == red) {
                    gndTmp.setColor(red);
                    parentTmp.setColor(black);
                    uncleTmp.setColor(black);
                    tmp = gndTmp;
                }
                else { // CASE 2 of PART B
                    if (tmp == parentTmp.getLeftChild()) {
                        rightRotate(root, parentTmp);
                        tmp = parentTmp;
                        parentTmp = tmp.getParent();
                    }
                    // CASE 3
                    // RIGHT RIGHT CASE, we will left rotate and swap the colors
                    leftRotate(root, gndTmp);
                    parentTmp.setColor(!parentTmp.getColor());
                    gndTmp.setColor(!gndTmp.getColor());
                    tmp = parentTmp;
                }
            }
        }
        root.setColor(black);
    }
    
    private void leftRotate(Node<E> root, Node<E> tmp) { // private rotate helper method, accounts for single and double rotation about the right subtree to balance if the right subtree is heavier
        Node<E> rightTmp = tmp.getRightChild(); // holds right child of passed in tmp pointer
        tmp.setRightChild(rightTmp.getLeftChild()); // set right child of passed in tmp pointer to the left child to the right tmp
        
        if (tmp.getRightChild() != null) { // if the right child of tmp is null
            tmp.getRightChild().setParent(tmp); // we perform a rotation, where the parent of the right child is the passed in node
        }
        rightTmp.setParent(tmp.getParent()); // right tmp pointer parent is set to the parent of tmp pointer, aka grandparent is the right pointer parent
        
        if (tmp.getParent() == null) { // rotation about the root, right right case means we need to left rotate
            this.root = rightTmp; // ensures to set the root, not the reference of the root that we passed in
        }
        else if (tmp == tmp.getParent().getLeftChild()) { // otherwise, if the child pointer z that we passed in is the left child of the parent of z
            tmp.getParent().setLeftChild(rightTmp); // we will set the left child of the parent of z as the right child of z
        }
        else { // finally, we have the case where if the z node that we passed in is not the left child of the parent of z
            tmp.getParent().setRightChild(rightTmp); // we set the right child of parent to right pointer from earlier
        }
        rightTmp.setLeftChild(tmp); // set the leftchild of the right pointer to z
        tmp.setParent(rightTmp); // we set the parent of tmp as right parent
    }
    private void rightRotate(Node<E> root, Node<E> tmp) { // private rotate helper method, accounts for single and double rotation about the left subtree to balance if the left subtree is heavier
        Node<E> leftTmp = tmp.getLeftChild(); // holds left child of passed in tmp pointer
        tmp.setLeftChild(leftTmp.getRightChild());
        
        if (tmp.getLeftChild() != null) { // if the left child is null
            tmp.getLeftChild().setParent(tmp); // perform a rotation where the parent of the left child is the passed in node
        }
        leftTmp.setParent(tmp.getParent()); // left tmp pointer is set to the parent of tmp pointer, aka grandparent is the left pointer parent
        
        if (tmp.getParent() == null) { // if parent is null
            this.root = leftTmp; // it is a root rotation, we rotate the root about the left child pointer, since it is a single right rotation
        }
        else if (tmp == tmp.getParent().getLeftChild()) { // otherwise we have to perform a double rotation, as above with the left method
            tmp.getParent().setLeftChild(leftTmp); // we have to set the left child of the parent equal to the left tmp pointer to the node
        }
        else { // otherwise
            tmp.getParent().setRightChild(leftTmp); // we set the right child of the parent equal to the left tmp pointer
        }
        leftTmp.setRightChild(tmp); // sets left pointer's right child to passed in node (AKA NEW NODE)
        tmp.setParent(leftTmp); // sets parent of NEW NODE to left tmp
    }
    
    public boolean contains(E e) { // public method
        return contains(root, e);
    }
    private boolean contains(Node<E> root, E e) { // private helper method
        if (root == null) { // if root is null return false
            return false;
        }
        int compare = e.compareTo(root.getElement()); // create a compare int value so we can determine where to search
        if (compare == 0) { // if it is 0, that is the value we want, return true
            return true;
        }
        if (compare < 0) { // otherwise if it's less than, recur down the left subtree
           return contains(root.getLeftChild(), e);
        }
        else if (compare > 0) { // otherwise if it's greater than, recur down the right subtree
            return contains(root.getRightChild(), e);
        }
        return false; // if not found it will return false;
    }
    @Override
    public String toString() { // takes the element object and converts it into string
        String result = "";
        result = toString(root);
        return result;
    }
    private String toString(Node<E> root) {
        String result = ""; // result of the in order traversal
        
        if (root == null) {
            return "\t"; // return nothing if null
        }
        result += toString(root.getLeftChild());
        if (root.getColor() == red) {
            result += "*" + root.getElement().toString();
        }
        else if (root.getColor() == black) {
            result += root.getElement().toString();
        }
        result += toString(root.getRightChild());
        
        return result;
    }
    static class Node<E extends Comparable<E>> { // nested class, static
        
        private E element;
        private Node leftChild;
        private Node rightChild;
        private Node parent;
        private boolean color;
        
        public Node(E e, Node lc, Node rc, Node p, boolean col) { // parameterized constructor
            this.element = e;
            this.leftChild = lc;
            this.rightChild = rc;
            this.parent = p;
            this.color = col;
        }
        // accessor methods for node class,, allows access to the data fields in the node
        public E getElement() { 
            return element;
        }
        public Node getLeftChild() {
            return leftChild;
        }
        public Node getRightChild() {
            return rightChild;
        }
        public Node getParent() {
            return parent;
        }
        public boolean getColor() {
            return color;
        }
        // mutator methods for node class, allows the values to be changed
        public void setElement(E e) {
            this.element = e;
        }
        public void setLeftChild(Node lc) {
            this.leftChild = lc;
        }
        public void setRightChild(Node rc) {
            this.rightChild = rc;
        }
        public void setParent(Node p) {
            this.parent = p;
        }
        public void setColor(boolean c) {
            this.color = c;
        }
    }
}
