    import java.util.ArrayList;
    public abstract class AbsteractBinaryTree<T> extends AbstractTree<T> 
        implements I_BinaryTree<T> {
        public Position<T> sibling(Position<T> pos) {
            Position<T> parent = getParent(pos);
            if(parent == null) {
                return null;
            }
            return (pos == getLeftChild(parent)) ? getRightChild(parent) : getLeftChild(parent);
        }

        public int numChildren(Position<T> pos) {
            int count = 0;
            if(getLeftChild(pos) != null) count++;
            if(getRightChild(pos) != null) count++;
            return count;
        }

        /** Returns an iterable collection of positions representing p's children */
        public Iterable<Position<T>> children(Position<T> pos) {
            ArrayList<Position<T>> snapshot = new ArrayList<>(2);
            if(getLeftChild(pos) != null) snapshot.add(getLeftChild(pos));
            if(getRightChild(pos) != null) snapshot.add(getRightChild(pos));
            return snapshot;
        }


        /**
         *  overiding positions method to make inorder
         * default traversal scheme for proper binary trees
         * 
          *  in order traversal is specific to proper binary trees
         */

        private void inorderSubtree(Position<T> pos,ArrayList<Position<T>> snapshot){
            if(getLeftChild(pos) != null) 
            inorderSubtree(getLeftChild(pos), snapshot);
            snapshot.add(pos);
            if(getRightChild(pos) != null) 
                inorderSubtree(getRightChild(pos), snapshot);
        }
        
        public Iterable<Position<T>> inorder(){
            ArrayList<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                inorderSubtree(getRoot(), snapshot);
            }
            return snapshot;
        }

        public Iterable<Position<T>> positons() {
            return inorder();
        }

        /**
         * Euler tour adaptation for a binary tree
         */

        // private void eulerTour(Position<T> pos){
        //     //perform invisit action
        // }
        

    } // -- end of abstract binary tree --
