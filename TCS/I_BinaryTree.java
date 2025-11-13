    public interface I_BinaryTree<T> extends I_Tree<T> {
        /** returns position of p's left child (or null) if no child exists*/
        Position<T> getLeftChild(Position<T> pos) // in LinkedBinaryTree
            throws IllegalArgumentException; 

        /** returns postion of p's right child */
        Position<T> getRightChild(Position<T> pos) // in LinkedBinaryTree
            throws IllegalArgumentException;

        /** Returns the position of p's sibling (or null if no sibling exists) */
        Position<T> sibling(Position<T> pos) // in AbsteractBinaryTree
            throws IllegalArgumentException;


    }