public class TrainFromData extends JComponent{
    public void train(String fileName) throws Exception
    {
        try
        {
            File file = new File(fileName);
            BufferedImage img = ImageIO.read(file);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(img, 50, 50, 150, 150, null);
            paint(g2d);
            g2d.dispose();
        }

        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    public static void main(String[] args) throws Exception {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final TrainFromData comp = new TrainFromData();
        comp.setPreferredSize(new Dimension(320, 200));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        testFrame.pack();
        testFrame.setVisible(true);
        comp.train("text2.png");
    }
}