import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.net.*;
import java.applet.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.image.RenderedImage;
import javax.swing.Timer;
public class sample extends JPanel {

public JPanel firstpanel;
public JPanel secondpanel;
JLabel label1, label2;
JButton button1, button2;

public sample() {
    firstpanel = new JPanel();
    firstpanel.setSize(400,300); 
    firstpanel.setBackground(Color.RED);
    secondpanel = new JPanel();
    secondpanel.setBackground(Color.GREEN);
    secondpanel.setSize(400,300); 

    label1 = new JLabel("label1");
    label2 = new JLabel("label2");
    button1 = new JButton("button1");
    button2 = new JButton("button2");

    firstpanel.add(label1);
    firstpanel.add(button1);

    secondpanel.add(label2);
    secondpanel.add(button2);

    saveImage(firstpanel);

    add(firstpanel);

    // add(secondpanel);
}

public static void main(String args[]) {

    JFrame frame = new JFrame();
    sample sam = new sample();
    frame.setContentPane(sam);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

}

private void saveImage(JPanel panel) {
    BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
    panel.paint(img.getGraphics());
    try {
        ImageIO.write(img, "png", new File("F://Screen.png"));
        System.out.println("panel saved as image");

    } catch (Exception e) {
        System.out.println("panel not saved" + e.getMessage());
    }
}
}