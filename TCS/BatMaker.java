import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BatMaker {
    public static void main(String[] args) {
                String fileName = "C:\\Users\\Sepehr\\Desktop\\TableSparseCompress(TSC)\\commands.txt";

        try (
             FileWriter fw = new FileWriter(fileName, true);  // 'true' enables append mode
             PrintWriter pw = new PrintWriter(fw);

            
             ) {

            // Format line as: median: arg1 | name1: arg2 | name2: arg3
     


            // Write the line to the file
            // pw.println("@echo off");
            pw.println("javac *.java");
            
            for(int i = 16; i <= 10000; i += 10){
                
                pw.println("java -Xmx8G Benchmark" + " " + i + " " + i);
            }


            pw.println("pause");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
