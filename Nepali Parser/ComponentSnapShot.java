import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//gtiwari333@gmail.com
public class ComponentSnapShot {

    public static BufferedImage getScreenShot(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        component.paint(image.getGraphics());
        return image;
    }

    public static void getSaveSnapShot(JPanel component, String fileName) throws Exception {
        BufferedImage img = getScreenShot(component);
        // write the captured image as a PNG
        System.out.println("welcome panel ImageIO");
        ImageIO.write(img, "png", new File(fileName));
    }

    public static void main1() throws Exception {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        System.out.println("welcome panel");
        JButton button = new JButton("Button");
       // panel.add(button);

      //  frame.add(panel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);

        // capture and save image
        getSaveSnapShot(panel, "panel.png");
        System.out.println("welcome panel1");
     //   getSaveSnapShot(button, "button.png");
        frame.setVisible(false);
        System.exit(0);
    }
}


