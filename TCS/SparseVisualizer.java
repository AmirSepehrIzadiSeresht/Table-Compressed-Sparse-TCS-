import javax.swing.*;
import java.awt.*;

public class SparseVisualizer extends JPanel{
    
    private int[][] matrix;
    private int cellSize = 20; // size of each cell in pixels

    public SparseVisualizer(int[][] matrix, int nonZero, String t) {
        this.matrix = matrix;
        setPreferredSize(new Dimension(matrix[0].length * cellSize,
                                       matrix.length * cellSize));


        JFrame frame = new JFrame("NZ: " + nonZero + t);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    g.setColor(Color.BLACK);  // non-zero elements
                } else {
                    g.setColor(Color.WHITE);  // zero elements
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY); // grid lines
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }


}
