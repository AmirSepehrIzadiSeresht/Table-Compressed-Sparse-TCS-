import java.util.Random;
import java.util.Stack;

public class TableCompressedSparse{

    protected  AVLTreeMap<Integer,Integer> avl = new AVLTreeMap<>();
    protected  UnsortedListMap<Integer,Integer> nonZeroList = new UnsortedListMap<>();
    protected  Random rand = new Random();
    // protected  int[][] sparse = null;
    protected  int col;
    protected  int row;
   
    protected static final double PROB = 0.30;


    public TableCompressedSparse(int r,int c) {
        row = r;
        col = c;
        // sparse = new int[row][col];
        // initSparse(); //O(m) m = row * col
        // sortedArrToAvl(); // O(m)
    }

    public void initSparse() {
        int sparseOfst;
        for(int i = 0; i < row; ++i) {
            for(int j = 0; j < col; ++j) {
                if(rand.nextDouble() < PROB) {
                    
 
                    // sparse[i][j] = 1;
                    // <key - val>
                    sparseOfst = i * col + j; // key
                    
                    nonZeroList.putUnique(sparseOfst, 1);
                }
            }
        }
    }


    public void VisualizeSparse(int[][] matrix, String title ){
        // for(int i = 0; i < row; ++i) {
        //     for(int j = 0; j < col; ++j) {
        //         System.out.print(sparse[i][j] + " ") ;
        //     }
        // }
        // System.out.println();

        new SparseVisualizer(matrix, nonZeroList.size(),title);
    }

    public void VisualizeAvl(int scale, int radius){
        avl.tree.dscale = scale;
        new DrawTree<>(avl.tree , radius, "enteries: " + avl.size());
    }

    public void sortedArrToAvl() throws IllegalArgumentException {
        if(avl.size() > 1 || nonZeroList.size() == 0) {
            throw new IllegalStateException("tree must only have one root node and sparse shouldn't be empty");
        }
        
        int high = nonZeroList.size()-1;
        int low = 0;
        int[] tuple;
        int mid = (high + low) / 2;
        I_Entry<Integer,Integer> ent;
        int poped = 1;

        Stack<int[]> stack = new Stack<>();
        Position<I_Entry<Integer,Integer>> pos = avl.tree.getRoot();
        ent = new AbstractMap.MapEnt<>(nonZeroList.getKey(mid), nonZeroList.getVal(mid));
        avl.expandExternal(pos, ent);
        stack.push(new int[]{low, high});
        
        while (!stack.isEmpty()) {
            
            //left subtree
            if (low < mid) {

                high = mid - 1;
                mid = ( low + high) / 2;
               ent = new AbstractMap.MapEnt<>(nonZeroList.getKey(mid), nonZeroList.getVal(mid));
                pos = avl.tree.getLeftChild(pos);
                avl.expandExternal(pos, ent);
                
                stack.push(new int[]{low ,  high});
                
            } else if ( high > mid){

                low = mid + 1;
                mid = (low + high) / 2;
  
                ent = new AbstractMap.MapEnt<>(nonZeroList.getKey(mid), nonZeroList.getVal(mid));
                pos = avl.tree.getRightChild(pos);
                avl.expandExternal(pos, ent);
                
                stack.push(new int[]{low ,  high});
            } else {

                while(!avl.tree.isRoot(pos)
                            && pos == avl.tree.getRightChild(avl.tree.getParent(pos))) {
                    stack.pop();
                    
                    if(poped > avl.tree.getAux(pos)) {
                        avl.tree.setAux(pos, poped);
                    }
                    
                    pos = avl.tree.getParent(pos);
                    
                    poped ++;
                }
                
                stack.pop(); 
                
                if(poped > avl.tree.getAux(pos)) {
                    avl.tree.setAux(pos, poped);
                }

                poped++;

                if(!avl.tree.isRoot(pos)) {
                    pos = avl.tree.getParent(pos);
                    
                    avl.tree.setAux(pos, poped); // no check is nedd cause hight of right subtree is not yet calculated

                    poped = 1;
                    
                    tuple = stack.peek();
                    low = tuple[0];
                    high = tuple[1];
                    low = mid = (low + high) / 2; //after pop only right subtree
                }
            }
        }
    } 


    // public static void main(String args[]){
    //     TableCompressedSparse tcs = new TableCompressedSparse(4, 4);
    //     tcs.initSparse();
    //     tcs.VisualizeSparse();
    //     tcs.sortedArrToAvl();
    //     tcs.VisualizeAvl(20, 15);
    // }
}





    // public static int main(String[] args) {
    //     return 0;
    // }
