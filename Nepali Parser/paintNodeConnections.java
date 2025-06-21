import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class PaintNodes extends JFrame {
    private NodeLabel node1;
    private NodeLabel node2;
    private JPanel paintPanel;
    public PaintNodes() {
        initComponents();
    }
    private void paintNodeConnections(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        // draw line between right connection point of node1
        // and the left connection point of node2
        Point p1 = node1.getConnectionPoint("RIGHT");
        Point p2 = node2.getConnectionPoint("LEFT");
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        // this just overrides the paintComponent() method 
        // of the created panel.
        paintPanel = new  JPanel() {
           public void paintComponent(Graphics g){
                super.paintComponent(g);
                paintNodeConnections(g);
            }
        };
        paintPanel.setLayout(null);
        node1 = new NodeLabel("Node 1");
        node1.setHorizontalAlignment(SwingConstants.CENTER);
        node1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        paintPanel.add(node1);
        node1.setBounds(54, 52, 56, 28);
        node2 = new NodeLabel("Node 2");
        node2.setHorizontalAlignment(SwingConstants.CENTER);
        node2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        paintPanel.add(node2);
        node2.setBounds(186, 52, 56, 28);
        getContentPane().add(paintPanel, BorderLayout.CENTER);
        pack();
    }
    /** Small class to add some additionial behavior for nodes */
    class NodeLabel extends JLabel {
        Map<String, Point> connectionPoints = new HashMap<String, Point>();
        public NodeLabel(String text) {
            super(text);
            addComponentListener(new java.awt.event.ComponentAdapter(){
                 public void componentResized(java.awt.event.ComponentEvent evt) {
                    mapConnectionPoints();
                }
                public void componentMoved(ComponentEvent e) {
                    mapConnectionPoints();
                }
                 
            });
        }
        
        // updates the mapped positions of the connection points
        // called whenever the component get's resized or moved
        private void mapConnectionPoints(){
            connectionPoints.clear();
            Point point = new Point(getX(),getY()+getHeight()/2);
            connectionPoints.put("LEFT", point);
            point = new Point(getX() + getWidth(), getY() + getHeight()/2);
            connectionPoints.put("RIGHT", point);
            point = new Point(getX() + getWidth()/2, getY());
            connectionPoints.put("TOP", point);
            point = new Point(getX() + getWidth()/2, getY() + getHeight());
            connectionPoints.put("BOTTOM", point);
        }
        public Point getConnectionPoint(String key) {
            return connectionPoints.get(key);
        }
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaintNodes().setVisible(true);
            }
        });
    }
}

