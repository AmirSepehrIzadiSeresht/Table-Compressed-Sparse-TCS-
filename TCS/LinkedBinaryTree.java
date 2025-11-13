import java.util.ArrayList;

public class LinkedBinaryTree<T> extends AbsteractBinaryTree<T> {
        protected static class Node<T> implements Position<T>{
            private Node<T> parent;
            private Node<T> leftChild;
            private Node<T> rightChild;
            private T ele;

            private int x = 0;
            private int y = 0;
             boolean red = false; // notices the node which should be restructured

            public Node (T ele, Node<T> parent, Node<T> leftChild, Node<T> rightChild) {
                this.ele = ele;
                this.parent = parent;
                this.leftChild = leftChild;
                this.rightChild = rightChild;
            }

            /** accessor */
            public T getEle() {
            
                return ele;
            }
            public boolean isRed(){
                return red;
            }
            public void setRed(){
                red = true;
            }
            public void unsetRed(){
                red = false;
            }
            public Node<T> getParent(){
                return parent;
            }
            public Node<T> getLeftChild(){
                return leftChild;
            }
            public Node<T> getRightChild(){
                return rightChild;
            }
            
            public int getX() 
            { 
                return x;
            }
            public int getY() 
            { 
                return y;
            }
            /** Update */
            public void setEle(T ele){
                this.ele = ele;
            }
            public void setParent(Node<T> parent){
                this.parent = parent;
            }
            public void setLeftChild(Node<T> left){
                leftChild = left;

            }
            public void  setRightChild(Node<T> right){
                rightChild = right;
            }

            public void setX(int x){
                this.x = x;
            }
            public void setY(int y){
                this.y = y;
            }
        }
     //-- end of Node internal class--

    
    //factory design pattern
    protected Node<T> createNode(T ele, Node<T> parent, Node<T> leftChild, Node<T> rightChild) {
        return new Node<>(ele, parent, leftChild, rightChild);
    }
    
    //fields
    protected Node<T> root = null;
    private int size = 0;
    protected int dscale= 30;

    // //default constructor
    public LinkedBinaryTree() {}

    protected Node<T> validateToNode(Position<T> pos) 
        throws IllegalStateException {
        if(!(pos instanceof Node)) 
            throw new IllegalArgumentException ("doesn t belong to this tree");

        Node<T> node = (Node<T>) pos;

        if(node.getParent() == node) 
            throw new IllegalArgumentException("pos is already removed from the tree");
        return node;
    }

    public int size() { return size; }
    public Node<T> getRoot() { return root; }

    public Position<T> getParent(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getParent();
    }

    public Position<T> getLeftChild(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getLeftChild();
    }

    public Position<T> getRightChild(Position<T> pos) 
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getRightChild();
    }

    public void setX(Position<T> pos, int x) 
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        node.setX(x);
    }
    
    public void setY(Position<T> pos, int y)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        node.setY(y);
    }

    public int getX(Position<T> pos) 
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        return node.getX();
    }
    
    public int getY(Position<T> pos)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        return node.getY();
    }

    public Position<T> addRoot(T ele)
        throws IllegalStateException{
        if(!isEmpty()) throw new IllegalStateException("tree is not empty");
        root = createNode(ele, null, null, null);
        size = 1;
        return root;
    }

    public Position<T> addLeftChild(Position<T> pos, T ele)
        throws IllegalStateException {
        Node<T> parent = validateToNode(pos);

        if(parent.getLeftChild() != null)
            throw new IllegalStateException("already has left child");
        
        Node<T> child = createNode(ele, parent, null, null);
        parent.setLeftChild(child);
        size++;
        return child;
    }
    
    public Position<T> addRightChild(Position<T> pos, T ele)
        throws IllegalStateException {
        Node<T> parent = validateToNode(pos);

        if(parent.getRightChild() != null)
            throw new IllegalStateException("already has right child");
        
        Node<T> child = createNode(ele, parent, null, null);
        parent.setRightChild(child);
        size++;
        return child;
    }

    public T replaceEle(Position<T> pos, T ele)
        throws IllegalArgumentException{
        Node<T> node = validateToNode(pos);
        T temp = node.getEle();
        node.setEle(ele);
        return temp;
    }

    //** Attaches trees t1 & t2 as left and right subtrees of external p */
    public void attach(Position<T> pos, LinkedBinaryTree<T> leftSubTree, LinkedBinaryTree<T> rightSubTree)
        throws IllegalArgumentException{
        Node<T> parent = validateToNode(pos);

        if(isInternal(pos)) 
            throw new IllegalArgumentException("pos must be external");

        size += leftSubTree.size() + rightSubTree.size();

        if(!leftSubTree.isEmpty()) {
            parent.setLeftChild(leftSubTree.getRoot());
            leftSubTree.getRoot().setParent(parent);
            leftSubTree.root = null;
            leftSubTree.size = 0;
        }

        if(!rightSubTree.isEmpty()) {
            parent.setRightChild(rightSubTree.getRoot());
            rightSubTree.getRoot().setParent(parent);
            rightSubTree.root = null;
            rightSubTree.size = 0;
        }
    }

    public T remove (Position<T> pos)
        throws IllegalArgumentException {
        Node<T> node = validateToNode(pos);
        if(numChildren(pos) == 2){
            throw new IllegalArgumentException("pos has two childreb");

        }
        Node<T> child = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();
        if(child != null){
            child.setParent(node.getParent());
        }
        if(node == root){
            root = child;
        } else {
            Node<T> parent = node.getParent();
            if(node == parent.getLeftChild()){
                parent.setLeftChild(child);

            } else {
                parent.setRightChild(child);
            }
        }

        T temp = node.getEle();
        node.setEle(null);
        node.setParent(node);
        node.setLeftChild(null);
        node.setRightChild(null);
        size--;
        return temp;
    }



        // -- start of drawing functionality for binary trees

        /**
         * sets x & y coordinates of proper binary tree based on 
         * an inorder traversal of the nodes
         * x(p) : number of positions visited before p
         * y(p) : depth of p
         */
        public int calcCoordinates(Position<T> pos, int depth, int x){
            Position<T> child = null;
            if ((child = getLeftChild(pos)) != null) {
               x = calcCoordinates(child, depth+1, x);
            }

            setX(pos, (x * dscale));
            x++;
            setY(pos, depth*dscale);

            if((child = getRightChild(pos)) != null){
                x = calcCoordinates(child, depth+1, x);
            }

            return x;
        }

        private void makeEdgeList(Position<T> pos, ArrayList<Position<T>[]> edgeList, Position<T>[] edge) {
            // uses postorder traversal
            edge[1] = pos;
            for(Position<T> c : children(pos)){
                makeEdgeList(c, edgeList, edge);
                edge[0] = pos;

                edgeList.add(edge.clone());
            }
            edge[1] = pos;
        }

        public Iterable<Position<T>[]> makeEdgeListUtil() {
            ArrayList<Position<T>[]> edgeList = new ArrayList<>();
            Position<T>[] edge = (Position<T>[]) new Position[2];
            makeEdgeList(getRoot(), edgeList, edge);
            return edgeList;
        }


        // public static void main(String[] args) {
        //     LinkedBinaryTree<Integer> binaryTree = new LinkedBinaryTree<>();
            
        //     Position<Integer> left;
        //     Position<Integer> right;

        //       // initializing tree
        //     binaryTree.addRoot(10);
        //     left = binaryTree.addLeftChild(binaryTree.getRoot(), 27);
        //     right = binaryTree.addRightChild(binaryTree.getRoot(), 28);
        //     binaryTree.addLeftChild(left, 28);
        //     binaryTree.addRightChild(left, 14);
        //     left = binaryTree.addRightChild(right, 18);
        //     right = binaryTree.addLeftChild(right, 19);
        //     binaryTree.addRightChild(left, 88);
        //     binaryTree.addLeftChild(right, 28);
        //     binaryTree.addRightChild(right, 14);
            
        //     DrawTree<Integer> draw = new DrawTree<>(binaryTree); // show tree on instantiation
        //     return;
        // }

}// -- end of linkedbinarytree --

