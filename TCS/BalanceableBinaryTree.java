public class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<I_Entry<K,V>> {

protected static class BSTNode<E> extends Node<E> {
                int aux = 0; // stores hieght of node
               
                public BSTNode(E ele, Node<E> parent, Node<E> leftChild, Node<E> rightChild){
                    super(ele,parent,leftChild, rightChild);
                }

                public int getAux(){return aux;}
                public void setAux(int val){
                    aux = val;
                }
            } // end of BSTNode class

        //positional based methods related to aux field
        public int getAux(Position<I_Entry<K,V>> p){
            return ((BSTNode<I_Entry<K,V>>) p).getAux();
        }
        public void setAux(Position<I_Entry<K,V>> p, int val){
             ((BSTNode<I_Entry<K,V>>) p).setAux(val);
        }




        // override node factory function to produce a BSTNode (rather than a node)
        protected Node<I_Entry<K,V>> createNode(I_Entry<K,V> ele , Node<I_Entry<K,V>> parent,  Node<I_Entry<K,V>> leftChild,  Node<I_Entry<K,V>> rightChild){
            return new BSTNode<>(ele,parent,leftChild,rightChild);
        }

        //relinks a parent node with its oreiented child node
        private void relink(Node<I_Entry<K,V>> parent, Node<I_Entry<K,V>> child, boolean makeLeftChild){
            child.setParent(parent);
            if(makeLeftChild){
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        } 

       //rotates a position p above its parent
       public void rotate(Position<I_Entry<K,V>> p) {
            Node<I_Entry<K,V>> x = validateToNode(p);
            Node<I_Entry<K,V>> y = x.getParent(); // we assume this exists
            Node<I_Entry<K,V>> z = y.getParent(); // grandparent (possibly null)
            
            if(z == null){
                root= x;
                x.setParent(null);
            } else {
                // if y is left child of z, then x will be the new left child of z
                //  ~~~ right child ~~~ right child ~~~
                relink(z, x, y == z.getLeftChild());

            }
            //now rotate x and y including transfer of middle subtree
            if(x == y.getLeftChild()){
                relink(y, x.getRightChild(), true); //connect x's right child to y's leftchild
                relink(x,y,false); //set y as x's right child
            } else {
                 relink(y, x.getLeftChild(), false); //connect x's left child to y's right child
                relink(x,y,true); //set y
            }
       }
       /**performs trinode restructuring of position x with its parent/grandparent */
       public Position<I_Entry<K,V>> restructure(Position<I_Entry<K,V>> x) {
            Position<I_Entry<K,V>> y = getParent(x);
            Position<I_Entry<K,V>> z = getParent(y);

            // if they are aligned only one rotation is needed
            if((x == getLeftChild(y)) == (y == getLeftChild(z))) {
                rotate(y);
                return y; //y will be new root of the subtree previously rooted at z
            } else {
                //double rotation is needed
                rotate(x);
                rotate(x);
                return x; // x will be new root
            }

}
}
 