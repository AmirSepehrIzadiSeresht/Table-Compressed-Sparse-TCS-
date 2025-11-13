import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class AVLTreeMap<K,V> extends SortedTreeMap<K,V> {
    // String fileName = "C:\\Users\\Sepehr\\Desktop\\TableSparseCompress(TSC)\\Analysis.txt";
    //     FileWriter fw = new FileWriter(fileName, true);  // 'true' enables append mode
    //          PrintWriter pw = new PrintWriter(fw);

    
    public AVLTreeMap(){
        super();
    }

    public AVLTreeMap(Comparator<K> comp){
        super(comp);
    }

    //return the hight of the give tree position
    protected int height(Position<I_Entry<K,V>> p){
        return tree.getAux(p);
    }

    //recomputes a position's hight based on its children's heights
    protected void recomputeHeight(Position<I_Entry<K,V>> p){
        tree.setAux(p, 1 + Math.max(height(tree.getLeftChild(p)), height(tree.getRightChild(p))));
    }

    //returns whether a position has balance factor between -1 & 1
    protected boolean isBalanced(Position<I_Entry<K,V>> p) {
        return Math.abs(height(tree.getLeftChild(p))- height(tree.getRightChild(p))) <= 1;
    }

    //returns p's child having hight no smaller than its sibling
    protected Position<I_Entry<K,V>> tallerChild(Position<I_Entry<K,V>> p) {
        if(height(tree.getLeftChild(p)) > height(tree.getRightChild(p))) {
            return tree.getLeftChild(p);
        }
        if(height(tree.getLeftChild(p)) < height(tree.getRightChild(p))) {
            return tree.getRightChild(p);
        }

        //if both children have equal height pick one that has the same alignment with its parent
        if(tree.isRoot(p)) {
            //no matter pick which child
            return tree.getRightChild(p);
        }

        if(p == tree.getLeftChild(tree.getParent(p))) {
            return tree.getLeftChild(p);
        } else {
            return tree.getRightChild(p);
        }
    }

    

    //this function rebalances the tree whenever an (insertion or deletion) occurs
    //it marches up the tree T from position p to the root adjusting heights of the nodes 
    //and looking for any unbalanced node
    // if any unbalanced node is detected it performs a trinode restructuring and repeats the algorithm
    protected void rebalance(Position<I_Entry<K,V>> p) {
        int newHeight, oldHeight;
        // BalanceableBinaryTree.BSTNode<I_Entry<K,V>> x = null;
        // boolean didrestructure =false;
        // DrawTree<K,V> draw = null;

        // try {
        //     Thread.sleep(1000);  // 3000 ms = 3 seconds
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // draw = new DrawTree<>(tree,"node inserted"); // show before restructure - Delete
        do {
            oldHeight = height(p);
            if(!isBalanced(p)){
                // didrestructure = true;
                // x= (BalanceableBinaryTree.BSTNode<I_Entry<K,V>>) p;
                // x.setRed(); // DELETE
                // draw = new DrawTree<>(tree, "this node needs to be restructured"); // show before restructure - Delete
                
                
                p = tree.restructure(tallerChild(tallerChild(p))); // p will be set to new root of the subtree
                recomputeHeight(tree.getLeftChild(p));
                recomputeHeight(tree.getRightChild(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = tree.getParent(p);
            // if newHieght and oldHeight differ the node my violate hight-balance property of AVL 
        } while( (newHeight != oldHeight) && p != null);
                // try {
                //     Thread.sleep(1000);  // 3000 ms = 3 seconds
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
        
        // if(didrestructure) {
            
        //     draw = new DrawTree<>(tree, "after restructure"); // show before restructure - Delete
        //     try {
        //             Thread.sleep(1000);  // 3000 ms = 3 seconds
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     x.unsetRed(); // DELETE
        // }
    }

    // is called after a new node is added to tree at position p
    protected void rebalanceInsert(Position<I_Entry<K,V>> p) {
        
        rebalance(p);
    }

    //is called after a node is deleted from the tree
    // position p denoted child of removed node that later promoted to its place
    protected void rebalanceDelete(Position<I_Entry<K,V>> p) {
        if(!tree.isRoot(p)) {
            rebalance(tree.getParent(p));
        }
    }

        // public static void main(String[] args) {
        //     AVLTreeMap<Integer,Integer> avl = new AVLTreeMap<>();
        //     // int[] arr = {2, 4, 6, 8, 10, 20,30,40};
            
        //     for (int i = 1; i <= 20; ++i) {
        //         avl.put(i, i);
        //         // DrawTree<Integer,Integer> draw = new DrawTree<>(avl.tree); // show tree on instantiation
                
        //     }
        //     avl.tree.dscale = 20;
        //     new DrawTree<>(avl.tree, 15, "avl");

        //     return;
        // }
            

    
}
