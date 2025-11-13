import javax.swing.*;
import javax.swing.tree.TreeNode;

import java.awt.*;
public class DrawTree<K,V> extends JPanel{
private int NODE_RADIUS = 15; // Circle radius

BalanceableBinaryTree<K,V> tree;
private Iterable<Position<I_Entry<K, V>>> nodes;
private Iterable<Position<I_Entry<K, V>>[]> edges; // each edge as {parentIndex, childIndex}


    public DrawTree(BalanceableBinaryTree<K,V> tree,int radius, String t) {
        this.tree = tree;
        tree.calcCoordinates(tree.getRoot(), 1, 1);
        this.nodes = tree.positions();
        this.edges = tree.makeEdgeListUtil();

    
        NODE_RADIUS = radius;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        JFrame frame = new JFrame(t);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2)); // thicker lines

        // Draw edges (lines between nodes)
        // int count = 1;
       
        // for (Position<T>[] edge : edges) {
        //     System.out.println("pair number: " + count++);
        //     System.out.print("x" + tree.getX(edge[0]));
        //     System.out.print(" ,y" + tree.getY(edge[0]));
        //     System.out.print("  ");
        //     System.out.print("x" + tree.getX(edge[1]));
        //     System.out.print(" ,y" + tree.getY(edge[1]) + "  ||");
        //     System.out.print("par  " + edge[0].getEle());
        //     System.out.print("child  " + edge[1].getEle());

        //     System.out.println();
        // }

        for (Position<I_Entry<K, V>>[] edge : edges) {
            Position<I_Entry<K, V>> parent = edge[0];
            Position<I_Entry<K, V>> child = edge[1];
            g2.drawLine(tree.getX(parent), tree.getY(parent), tree.getX(child), tree.getY(child));
        }

        // Draw nodes (circles with labels)

        for (Position<I_Entry<K, V>> pos : nodes) {
            // BalanceableBinaryTree.BSTNode<I_Entry<K,V>> x = (BalanceableBinaryTree.BSTNode<I_Entry<K,V>>) pos;
            int r = NODE_RADIUS;
            if(tree.validateToNode(pos).isRed()){

                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.lightGray);

            }
            g2.fillOval(tree.getX(pos) - r, tree.getY(pos) - r, 2 * r, 2 * r); // Circle
            g2.setColor(Color.BLACK);
            g2.drawOval(tree.getX(pos) - r, tree.getY(pos) - r, 2 * r, 2 * r);

            // Draw label centered
            FontMetrics fm = g2.getFontMetrics();
            // int textWidth = fm.stringWidth(((pos.getEle() != null) ? pos.getEle().getKey() : "null") + "");
            int textWidth = fm.stringWidth(tree.getAux(pos) +"-"+((pos.getEle() != null) ? pos.getEle().getKey() : "null"));
            int textHeight = fm.getAscent();
            
            // g2.drawString(((pos.getEle() != null) ? pos.getEle().getKey() : "null") + "", tree.getX(pos) - textWidth / 2, tree.getY(pos) + textHeight / 4);
            g2.drawString(tree.getAux(pos) +"-"+((pos.getEle() != null) ? pos.getEle().getKey() : "null")
            , tree.getX(pos) - textWidth / 2, tree.getY(pos) + textHeight / 4);
        }
    }
}
