
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Benchmark extends TableCompressedSparse {

    public Benchmark(int r, int c){
        super(r,c);
        initSparse();
    }
    
    // public void allset(){
    //             for(int i = 0; i < row; ++i) {
    //         for(int j = 0; j < col; ++j) {
           
                    
 
    //                 sparse[i][j] = 1;
          
    //             }
    //         }
    //     }

    // private int[][] deepCopyMatrix(int[][] matrix) {
    //     int[][] copy = new int[matrix.length][matrix[0].length];
    //     for (int i = 0; i < matrix.length; i++) {
    //     copy[i] = matrix[i].clone();
    //     }

    //     return copy;
    // }

    
    public void benchmarkAvlMaking(){
        long total= 0;
        
        long startTime = 0;
        long endTime = 0;
        
        startTime = System.nanoTime();
        sortedArrToAvl();
        endTime = System.nanoTime();
        
        total = (endTime - startTime);
        double elapsedMs = total / 1_000_000.0; //ms

        avlMakeBenchToFile(elapsedMs, nonZeroList.size());
    }

    public void benchmarkUpdateOprs() {
        double total= 0;
        //  long median = 0; // to file
         int putHit = 0; // to file
         int lastIndex= (row - 1) * col +  (col - 1);
         int oprNum = 100;
        double median = 0;

         long startTime = 0;
         long endTime = 0;
         int key;
         int removeIndx;

        // perform put
         int keyToRemove;
        //  int r;
        //  int c;
         Integer value;

        // int[][] sprsClone = deepCopyMatrix(sparse);

        // VisualizeSparse(sparse, "before bench");
        // VisualizeAvl(20,20);

        for(int i = 1; i <=oprNum; ++i) {
              
                key = rand.nextInt(lastIndex + 1);
                // r = key / col;
                // c = key % col;
                
                // update original matrix
                // sprsClone[r][c] = 1;
                nonZeroList.put(key,1);
                removeIndx = rand.nextInt(nonZeroList.size());
                
                keyToRemove = nonZeroList.getKey(removeIndx);

                // r = keyToRemove / col;
                // c = keyToRemove % col;
                // sprsClone[r][c] = 0;

                nonZeroList.remove(removeIndx);

                // update map
                startTime = System.nanoTime();
                value = avl.put(key, 1);
                avl.remove(keyToRemove);
                endTime = System.nanoTime();

                total = total + ((endTime - startTime) / 1_000_000.0);

                // key matched when inserting to avl
                if(value != null){
                    putHit++;
                }
        }

        // VisualizeSparse(sprsClone, "after bench");
        // VisualizeAvl(20,15);

         median = total / (oprNum * 2.0); //ms
        

        updateBenchToFile(median, putHit, oprNum * 2);
    }




    private void avlMakeBenchToFile(double time, int nonZero) {
        // File path in current directory
        String fileName = "C:\\Users\\Sepehr\\Desktop\\TableSparseCompress(TSC)\\ExcelAvlMake.txt";

        try (
             FileWriter fw = new FileWriter(fileName, true);  // 'true' enables append mode
             PrintWriter pw = new PrintWriter(fw);

            
             ) {

            // Format line as: median: arg1 | name1: arg2 | name2: arg3
            String line =nonZero + ", " + String.format("%.3f", time);


            // Write the line to the file
            pw.println(line);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void updateBenchToFile(double median, int putHit, int oprNum) {

        // File path in current directory
        String fileName = "C:\\Users\\Sepehr\\Desktop\\TableSparseCompress(TSC)\\AnalysisUpdateBench.txt";
        String fileName2 = "C:\\Users\\Sepehr\\Desktop\\TableSparseCompress(TSC)\\ExcelUpdateBench.txt";

        try (
             FileWriter fw = new FileWriter(fileName, true);  // 'true' enables append mode
             PrintWriter pw = new PrintWriter(fw);
             FileWriter fw2 = new FileWriter(fileName2, true);  // 'true' enables append mode
             PrintWriter pw2 = new PrintWriter(fw2);
            
             ) {

            // Format line as: median: arg1 | name1: arg2 | name2: arg3
            String line = "median(ms): " + String.format("%.3f", median) + " | " + "oprNum: " + oprNum + " | " +"putHit : " + putHit + " | " + "dimen: " + row +"x"+col;
            String line2 =(row*col) +", " + String.format("%.3f", median) ;

            // Write the line to the file
            pw.println(line);
            pw2.println(line2); //suitable for excel

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Benchmark tcsBench = new Benchmark(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        // Benchmark tcsBench = new Benchmark(100, 100);
        
        tcsBench.benchmarkAvlMaking();
        // tcsBench.VisualizeAvl(20,15);

        tcsBench.benchmarkUpdateOprs();

        
    }
    
}
