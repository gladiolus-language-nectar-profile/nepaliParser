import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
  
public class SavingAPanel
{
    public static void main(String[] args)
    {
        ImagePanel panel = new ImagePanel();
        Save save = new Save(panel);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(save.getUIPanel(), "North");
        f.getContentPane().add(new JScrollPane(panel));
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
  
class ImagePanel extends JPanel
{
    Rectangle r;
    boolean marking;
    Point start, end;
  
    public ImagePanel()
    {
        marking = false;
        start = new Point();
        end = new Point();
        r = new Rectangle();
        Image[] images = loadImages();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        for(int j = 0; j < images.length; j++)
        {
            gbc.gridwidth = gbc.RELATIVE;
            if((j + 1) % 2 == 0)
                gbc.gridwidth = gbc.REMAINDER;
            add(new JLabel(new ImageIcon(images[j])), gbc);
        }
        PanelMarker marker = new PanelMarker(this);
        addMouseListener(marker);
        addMouseMotionListener(marker);
    }
  
    /**
     * Paints over components.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        if(marking)
        {
            g2.setPaint(Color.red);
            int w = end.x - start.x;
            int h = end.y - start.y;
            r.setRect(start.x, start.y, w, h);
            g2.draw(r);
        }
    }
  
    private Image[] loadImages()
    {
        String[] fileNames = {
            "greathornedowl", "bclynx", "mtngoat", "redfox"
        };
        String path = "images/";
        String ext = ".jpg";
        Image[] images = new Image[fileNames.length];
       /* for(int j = 0; j < fileNames.length; j++)
           try
            {
                URL url = getClass().getResource(path + fileNames[j] + ext);
                images[j] = ImageIO.read(url);
            }
            catch(MalformedURLException mue)
            {
                System.out.println("Bad URL: " + mue.getMessage());
            }
            catch(IOException ioe)
            {
                System.out.println("Read trouble: " + ioe.getMessage());
            }*/
        return images;
    }
}
  
class PanelMarker extends MouseInputAdapter
{
    ImagePanel imagePanel;
  
    public PanelMarker(ImagePanel ip)
    {
        imagePanel = ip;
    }
  
    public void mousePressed(MouseEvent e)
    {
        imagePanel.start = e.getPoint();
    }
  
    public void mouseDragged(MouseEvent e)
    {
        imagePanel.end = e.getPoint();
        imagePanel.repaint();
    }
}
  
class Save
{
    ImagePanel imagePanel;
  
    public Save(ImagePanel ip)
    {
        imagePanel = ip;
    }
  
    private void saveClip()
    {
        Rectangle r = imagePanel.r;
        int w = r.width - 2;
        int h = r.height - 2;
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.translate(-r.x - 1, -r.y - 1);
        imagePanel.paint(g2);
        g2.dispose();
        try
        {
            ImageIO.write(bi, "jpg", new File("clip.jpg"));
        }
        catch(IOException ioe)
        {
            System.out.println("Clip write help: " + ioe.getMessage());
        }
        imagePanel.marking = false;
    }
  
    private void savePanel()
    {
        int w = imagePanel.getWidth();
        int h = imagePanel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        imagePanel.paint(g2);
        g2.dispose();
        try
        {
            ImageIO.write(bi, "jpg", new File("panel.jpg"));
        }
        catch(IOException ioe)
        {
            System.out.println("Panel write help: " + ioe.getMessage());
        }
    }
  
    public JPanel getUIPanel()
    {
        final JButton
            mark = new JButton("mark"),
            saveClip = new JButton("save clip"),
            save = new JButton("save panel");
        ActionListener l = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JButton button = (JButton)e.getSource();
                if(button == mark)
                {
                    save.setEnabled(false);
                    imagePanel.marking = true;
                }
                if(button == saveClip)
                {
                    save.setEnabled(true);
                    saveClip();
                }
                if(button == save)
                {
                    savePanel();
                }
            }
        };
        mark.addActionListener(l);
        saveClip.addActionListener(l);
        save.addActionListener(l);
        JPanel panel = new JPanel();
        panel.add(mark);
        panel.add(saveClip);
        panel.add(save);
        return panel;
    }
}
  