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
public class Pngs extends JPanel {

public void paint(Graphics g) {
    Image img = createImage();
    g.drawImage(img, 0, 0, this);
}

public static BufferedImage getScreenShot(
        Component component) {

    BufferedImage image = new BufferedImage(
            component.getWidth(),
            component.getHeight(),
            BufferedImage.TYPE_INT_RGB
    );
// call the Component's paint method, using
    // the Graphics object of the image.
    component.paint(image.getGraphics());
    return image;
}

private static Image createImage() {
    BufferedImage img = null;
    try { 
        img = ImageIO.read(new File("oneImg.png"));
    } catch (IOException e) {
    }
    return img;
}

public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new Pngs());
    frame.setUndecorated(true);
    frame.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(512, 512);
    frame.setVisible(true);
    try { //saves the image
        // retrieve image
        BufferedImage bi = getScreenShot(frame.getContentPane());
        File outputfile = new File("saved.png");
        ImageIO.write(bi, "png", outputfile);
    } catch (Exception e) {
    }
}

}