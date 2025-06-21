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
public  class PaintNodes2 extends JFrame {
    private NodeLabel node1;
    private NodeLabel node2;
    private NodeLabel node3;
    private NodeLabel[] node ;
    private JFrame paintFrame;
    private JPanel paintPanel;
   // Picture pict ;
    private final int DELAY = 150;
    private Timer timer;
   // Panel cs = new Panel();
  // ComponentSnapShot cs = new ComponentSnapShot();
   //FullScreenCaptureExample cs = new FullScreenCaptureExample();
    public PaintNodes2(int[][] pm, int rw, int cl, String[] wd, String[] rela, String[] tgs, String fn, String out, String[] words) throws Exception {
    	node = new NodeLabel[cl];
    	//System.out.println("initial rows="+rw+"    col="+cl);
        initComponents(pm, rw, cl, wd, rela, tgs, fn, out, words);
       // pict = new Picture(fn);
       // cs.main1();
    }
    
    public PaintNodes2(int[][] pm, int rw, int cl, String[] wd, String[] rela, String[] tgs, PaintNodes2 pl, String fn, String out, String[] words) throws Exception {
    	node = new NodeLabel[cl];
    	//System.out.println("NL rows="+rw+"    col="+cl);
        nlinitComponents(pm, rw, cl, wd, rela, tgs, pl, fn, out, words);
    }
     public PaintNodes2() {
        initComponents();
    }
    private void paintNodeConnections(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
       // Font font = new Font("Mangal", Font.PLAIN, 48);
        //g2d.setFont(font);
        
        // draw line between right connection point of node1
        // and the left connection point of node2
        Point p1 = node1.getConnectionPoint("RIGHT");
        //Point p2 = node2.getConnectionPoint("LEFT");
        //Point p3 = node3.getConnectionPoint("RIGHT");
        //System.out.println("bad way");
        Point[] p = new Point[3];
        for(int i=0;i<3;i++){
        	if(i%2 == 0)
		         p[i] = node[i].getConnectionPoint("RIGHT");
		else
		         p[i] = node[i].getConnectionPoint("LEFT");
	}
//        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        //g2d.drawLine(p1.x, p1.y, p3.x, p3.y);
        for(int i = 0 ; i < 2 ; i++){
        	g2d.drawLine(p1.x,p1.y,p[i].x,p[i].y);
        	g2d.drawString("my line", 80, 60);
        	g2d.drawLine(p1.x,p1.y,p[i+1].x,p[i+1].y);        	
        }
    }
    
private static BufferedImage renderImage(JPanel panel) {
    //System.out.println(" welcome JFrame");
 JFrame frame = new JFrame();
 frame.setUndecorated(true);
 frame.getContentPane().add(panel);
 frame.pack();
 BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
 Graphics2D graphics = bi.createGraphics();
 panel.print(graphics);
 graphics.dispose();
 frame.dispose();
 return bi;
}


    private void paintNodeConnections(Graphics g2d, int[][] pm1, int rows, int cols, String[] rel, String[] tg, String fn, String[] words) {
    	BufferedImage image = new BufferedImage(600,600, BufferedImage.TYPE_INT_RGB);
       // Graphics2D g2d = image.createGraphics();
        RenderedImage rendImage = null;
       // Graphics2D g2d = (Graphics2D)g;
        Font font = new Font("Mangal", Font.PLAIN, 18);
        g2d.setFont(font);
        // draw line between right connection point of node1
        // and the left connection point of node2
        //Point p1 = node1.getConnectionPoint("RIGHT");
        //Point p2 = node2.getConnectionPoint("LEFT");
        //Point p3 = node3.getConnectionPoint("RIGHT");
      //  int rows = pm1.length;
        //int col = pm1[0].length;
        int count = 0;
        //System.out.println(" 123 HI ITS PaintNodes2.JAVA");
        //System.out.println("rows="+rows+"    col="+cols);
        for(int s = 0 ; s < words.length ; s++){
          if(!words[s].equals("PUNC")){
            if(s < 10)
              g2d.drawString(" "+words[s], 20 + s*100,30);
            else if(s >= 10 && s < 20){
              g2d.drawString(" "+words[s], 20 + (s-10)*100,50);
            }
            else{
              g2d.drawString(" "+words[s], 20 + (s-20)*100,70);
            }
          }
          else
            break;
        }
        int nopoints = cols;
        Point[] p = new Point[nopoints];
        for(int i=0;i<nopoints;i++){
        	if(i%2 == 0)
		         p[i] = node[i].getConnectionPoint("RIGHT");
		else
		         p[i] = node[i].getConnectionPoint("LEFT");
	}
	int a =0;
	int b = 1;
	int rlcount = 0;
//        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        //g2d.drawLine(p1.x, p1.y, p3.x, p3.y);
        for(int i=0;i<rows;i++){
        	count = 0;
        	for(int j = 0 ; j < cols ; j++){
        		if(pm1[i][j] == 1 ){
        			//System.out.println("count="+count+" relation["+j+"]="+rel[j]+"   tg["+j+"]="+tg[j]+"   *tg["+rlcount+"]="+tg[rlcount]+"  rel["+rlcount+"]="+rel[rlcount]);
        			if(count == 0)
	        			a = j;
        			if(count == 1)
	        			b = j;
           /*   if(!rel[j].equals("next")){
                count = 500;
              }*/
	        		//System.out.println("  a="+a+"    b="+b);
              if(rel[rlcount].equals("next")){
                rel[rlcount] = "Verb chunk";
              }
	        		if(count == 1){
	        			g2d.drawLine(p[a].x,p[a].y,p[b].x,p[b].y);
	        			g2d.drawLine(p[a].x,p[a].y,p[b].x,p[b].y);
              }
        			if(j < (cols-1)  ){
        				if(i%2 == 0 && count == 0){
        					//System.out.println("i="+i);
		        			g2d.drawString("        "+rel[rlcount], 300 - i*50, 50+i*100);
		        			g2d.drawString("        "+tg[rlcount], 250 - i*100, 50+i*150);
		        			g2d.drawString("        "+rel[rlcount], 300 - i*50, 50+i*100);
		        			g2d.drawString("        "+tg[rlcount], 250 - i*100, 50+i*150);
		        			rlcount++;
		        		}
		        		else if(count == 0){
		        			//System.out.println("count 0 i="+i);
		        			g2d.drawString("      "+rel[rlcount], 600 -i*50, 50+i*100);
		        			g2d.drawString("       "+tg[rlcount], 550 -i*100, 50+i*150);
		        			g2d.drawString("      "+rel[rlcount], 600 -i*50, 50+i*100);
		        			g2d.drawString("       "+tg[rlcount], 550 -i*100, 50+i*150);
		        			rlcount++;
		        		}
		        		
	        		}
        			count++;
        			
        		}
        		
        	}
        }
        //System.out.println("ayo");
     //   saveImage()
       // String file;
     /*  File file = new File("newimage.png");
         try{
        	//System.out.println("ayo");
        	ImageIO.write(rendImage, "png", file);
	       // getSaveSnapShot(g2d, "panel2.png");//, "panel1.png");
	}
	catch(Exception nl){}*/
       // for(int i = 0 ; i < rows ; i++){
        //	for(int j = 0 ; j < cols ; j++){
        		
	  //      	g2d.drawLine(p1.x,p1.y,p[i].x,p[i].y);
        //		g2d.drawLine(p1.x,p1.y,p[i+1].x,p[i+1].y);        	
        //	}
        //}*/
    
    File file1 = new File("newimage.png");
         try{
            //System.out.println("ayo");
            ImageIO.write( image , "png", file1);
    //        getSaveSnapShot(g2d, "panel2.png");//, "panel1.png");
    }
    catch(Exception nl){}
    }
    
    private void nppaintNodeConnections(Graphics g, int[][] pm1, int rows, int cols, String[] rel, String[] tg, String[] words) {
      
     // Graphics2D g2d = image.createGraphics();
       RenderedImage rendImage = null;
        Graphics2D g2d = (Graphics2D)g;
        // draw line between right connection point of node1
        // and the left connection point of node2
        //Point p1 = node1.getConnectionPoint("RIGHT");
        //Point p2 = node2.getConnectionPoint("LEFT");
        //Point p3 = node3.getConnectionPoint("RIGHT");
      //  int rows = pm1.length;
        //int col = pm1[0].length;
        int count = 0;
        //System.out.println("123  HI ITS NP PaintNodes2.JAVA");
        //System.out.println("rows="+rows+"    col="+cols);
        try{
        for(int s = 0 ; s < words.length ; s++){
          //System.out.println("words["+s+"]="+words[s]);
         // if(words[s].equals("dummy")) words[s] = "NN";
          if(!words[s].equals("VF") || !words[s].equals("VAUX")  || !words[s].equals("PUNC")){
            if(s < 10)
              g2d.drawString(" "+words[s], 20 + s*100,30);
            else if(s >= 10 && s < 20){
              g2d.drawString(" "+words[s], 20 + (s-10)*100,50);
            }
            else{
              g2d.drawString(" "+words[s], 20 + (s-20)*100,70);
            }
          }
          else
            break;
        }
      }
      catch(NullPointerException nl){}
        int nopoints = cols;
        Point[] p = new Point[nopoints];
        try{
        for(int i=0;i<nopoints;i++){
        	if(i%2 == 0)
		         p[i] = node[i].getConnectionPoint("RIGHT");
		else
		         p[i] = node[i].getConnectionPoint("LEFT");
	}
	}
	catch(ArrayIndexOutOfBoundsException ae){
	
	}
	int a =0;
	int b = 1;
	int rlcount = 0;
//        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        //g2d.drawLine(p1.x, p1.y, p3.x, p3.y);
        for(int i=0;i<rows;i++){
        	count = 0;
        	for(int j = 0 ; j <= cols ; j++){
        		if(pm1[i][j] == 1){
        			//System.out.println("count="+count+" relation["+j+"]="+rel[j]+"   tg["+j+"]="+tg[j]+"   *tg["+rlcount+"]="+tg[rlcount]+"  rel["+rlcount+"]="+rel[rlcount]);
        			if(count == 0)
	        			a = j;
        			if(count == 1)
	        			b = j;
	        		//System.out.println("  a="+a+"    b="+b);
	        		if(count == 1)
	        			g2d.drawLine(p[a].x,p[a].y,p[b].x,p[b].y);
        			if(j < (cols+1)){
        				if(i%2 == 0 && count == 0){
        					//System.out.println("even tg["+(rlcount+1)+"]="+tg[rlcount+1]+"  i = "+i);
		        			g2d.drawString("         "+rel[rlcount], 400 - i*50, 70+i*100);
		        			g2d.drawString("         "+tg[rlcount+1], 400 - i*40, 50+i*150);
		        			g2d.drawString("         "+rel[rlcount], 400 - i*50, 70+i*100);
		        			g2d.drawString("         "+tg[rlcount+1], 400 - i*40, 50+i*150);
		        			rlcount++;
		        		}
		        		else if(count == 0){
        					//System.out.println("odd tg["+(rlcount+1)+"]="+tg[rlcount+1]+"  i = "+i);		        		
		        			g2d.drawString("       "+rel[rlcount], 550 -i*50, 70+i*80);
		        			g2d.drawString("        "+tg[rlcount+1], 500 -i*70, 50+i*130);
		        			g2d.drawString("       "+rel[rlcount], 550 -i*50, 70+i*80);
		        			g2d.drawString("        "+tg[rlcount+1], 500 -i*70, 50+i*130);
		        			rlcount++;
		        		}
		        		
	        		}
        			count++;
        			
        		}
        		
        		
        	}
        }
       /* File file1 = new File("newimage.png");
         try{
        	//System.out.println("ayo");
        	ImageIO.write( image , "png", file1);
	//        getSaveSnapShot(g2d, "panel2.png");//, "panel1.png");
	}
	catch(Exception nl){}*/
	
       // String file1;
       
       // for(int i = 0 ; i < rows ; i++){
        //	for(int j = 0 ; j < cols ; j++){
        		
	  //      	g2d.drawLine(p1.x,p1.y,p[i].x,p[i].y);
        //		g2d.drawLine(p1.x,p1.y,p[i+1].x,p[i+1].y);        	
        //	}
        //}*/
    }
    
    private void saveImage( JPanel panel){
	   BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(img.getGraphics());
    try {
        ImageIO.write(img, "png", new File("F://Screen.png"));
        //System.out.println("panel saved as image");

    } catch (Exception e) {
        //System.out.println("panel not saved" + e.getMessage());
    }
	}
    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        
        //int[][] dpm1;
        // this just overrides the paintComponent() method 
        // of the created panel.
        //System.out.println("hi its initcomponents :");
     //   //System.out.println("initial adv rows="+row+"    col="+col);
        paintPanel = new  JPanel() {
//           dmp1 = dpm;
	////System.out.println("adv rows=");
           public void paintComponent(Graphics g){
                super.paintComponent(g);
                //System.out.println("adv rows=");
                paintNodeConnections(g);
               // paintNodeConnections(g, dpm, row, col, relation, tags);
            }
        };
        
        paintPanel.setLayout(null);
        
    }
    private void initComponents(int[][] dpm, int row, int col, String[] words, String[] relation, String[] tags,String filename, String output, String[] word) throws Exception {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 1024));
        setBackground(Color.white);
        int scaledWidth = 1024;
            int scaledHeight = 1024;
            //System.out.println("new exp");
        File inputFile = new File(filename);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
 //System.out.println("new exp1");
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
//System.out.println("new exp2");
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
     //   String outputImagePath = "D:/Logo.png";
     String outputImagePath = output;
       // g2d.dispose();
     //   JFrame dialog = new JFrame();
       // Container contentPane = dialog.getContentPane();
         //((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
         //10, 10, 10, 10));
        //contentPane.add(this);
        //contentPane.setBackground(Color.white);
        //BufferedImage image = new BufferedImage(500,700, BufferedImage.TYPE_INT_RGB);
        //image = getImg(filename);
        //int[][] dpm1;
        // this just overrides the paintComponent() method 
        // of the created panel.
        //System.out.println("hi its initcomponents :");
        //System.out.println("initial adv rows="+row+"    col="+col);
     //   Save save = new Save(panel);
        paintPanel = new  JPanel() {
//           dmp1 = dpm;
            
	
           public void paintComponent(Graphics g2d){
                super.paintComponent(g2d);
                //System.out.println("new exp3");
                //System.out.println("adv rows="+row+"    col="+col);
                
                paintNodeConnections(g2d, dpm, row, col, relation, tags, filename, word);
               
               
            }
        };
        //System.out.println("new exp3.5");
       
        paintPanel.setLayout(null);
      //  outputImage = renderImage(paintPanel);

     
        for(int j =0;j<words.length;j++)
        
	        //System.out.println(" words["+j+"]="+words[j]);
        for(int i = 0 ; i < col ; i++){
        	//if(i==0)
	        	node[i] = new NodeLabel(" "+words[i]);
	       // else
	        //	node[i] = new NodeLabel(" "+words[i-1]);
        	node[i].setHorizontalAlignment(SwingConstants.CENTER);
	        node[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        	paintPanel.add(node[i]);
        	if(i%2 == 0)
		        node[i].setBounds(54 , 100 + i*50, 56, 28);
		else
			node[i].setBounds(186, 100 + i*50, 56, 28);
	            
        }
        setUndecorated(true);
        getContentPane().add(paintPanel, BorderLayout.CENTER);
        pack();
       // savePanel(paintPanel, image);
      //  setVisible(true);


       // frame.pack();
 BufferedImage bi = new BufferedImage(paintPanel.getWidth(), paintPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
 Graphics2D graphics = bi.createGraphics();
 paintPanel.print(graphics);
 graphics.dispose();
 dispose();
       // add(paintPanel);
       // getContentPane().
       // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//System.out.println("new exp5");
//outputImage = renderImage(paintPanel);
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(bi, formatName, new File(outputImagePath));
      //  setSize(400, 300);
      /*  Container cp = frame.getContentPane();
        cp.setBackground(Color.RED);
        cp.setLayout(new FlowLayout());

        cp.add(new JLabel(new ImageIcon(new URL("http://i.stack.imgur.com/BvYxM.png"))));
        cp.add(new JLabel(new ImageIcon(new URL("http://www.axdn.com/redist/axpssp_logo.png"))));

  frame.pack();
  frame.setVisible(true);*/
        //savePanel(paintPanel, image);
      //  paintPanel.repaint();
        //dialog.pack();
  //dialog.setLocationRelativeTo(null);
  //dialog.dispose();
  //GraphicsSupport.saveImage(this, filename);
      //  Rectangle myBox = box;

     //   getContentPane().repaint();
         //Graphics g = image.createGraphics();

        // draw on paintImage using Graphics

      //  g.dispose();
        // repaint panel with new modified paint
      //  repaint();

       
	//makePanelImage(paintPanel, image);
  //pict.show(JFrame );
        
       
    }
    private void savePanel(JPanel imagePanel, BufferedImage bi)
    {
        int w = imagePanel.getWidth();
        int h = imagePanel.getHeight();
        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      //  Graphics2D g2 = bi.createGraphics();
        imagePanel.paint(bi.getGraphics());
       // g2.dispose();
        //System.out.println("hi my panel");
        try
        {
            ImageIO.write(bi, "jpg", new File("F:\\panel.jpg"));
        }
        catch(Exception ioe)
        {
            //System.out.println("Panel write help: " + ioe.getMessage());
        }
    }
  
    public static BufferedImage getScreenShot(
        Component component, JPanel jp) {

    BufferedImage image = new BufferedImage(
            component.getWidth(),
            component.getHeight(),
            BufferedImage.TYPE_INT_RGB
    );
    //System.out.println("now");
// call the Component's paint method, using
    // the Graphics object of the image.
    component.paint(image.getGraphics());
    return image;
}
    private void makePanelImage(Component panel, BufferedImage image)
    {
    	//System.out.println("Are ave to aye");
        Dimension size = panel.getSize();
        //System.out.println("size = "+size);

        // image = new BufferedImage(
          //          size.width, size.height 
            //                  , BufferedImage.TYPE_INT_RGB);
       // Graphics2D g = image.createGraphics();
        //panel.print(g);
         Graphics g2 = image.createGraphics();
        panel.paint(g2);
        //System.out.println("Panel saved as Image 1.");
        try
        {
        	//System.out.println("Panel saved as Image 1.");
            ImageIO.write(image, "png", new File("snapshot.png"));
            //System.out.println("Panel saved as Image 3.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public BufferedImage getImg(String filename){
    	BufferedImage image = null;
        try
        {
          image = ImageIO.read(new File(filename));
        }
        catch (Exception e)
        {
          e.printStackTrace();
          System.exit(1);
        }
        return image;
    }
    public void SaveMe(Graphics g){
       BufferedImage BI = new BufferedImage(800, 700, BufferedImage.TYPE_INT_RGB); 
       BI.createGraphics();
       g.dispose();
       try {
           ImageIO.write(BI,"PNG",new File("/Users/robertbray/Desktop/TestImage.png"));
       }catch(Exception e){
        //System.out.println("File Creation Error "+e);
    }
    }
   /* public  static void getSaveSnapShot(JPanel cmp, String fileName) throws Exception {
       // BufferedImage img = new BufferedImage(); // = getScreenShot(component);
       //System.out.println("welcome panel ImageIO1");
     //  BufferedImage image = new BufferedImage(cmp.getWidth(),cmp.getHeight(), BufferedImage.TYPE_INT_RGB);
        // write the captured image as a PNG
        //System.out.println("welcome panel ImageIO");
        ImageIO.write(image, "png", new File(fileName));
    }*/
    
    private void nlinitComponents(int[][] dpm, int row, int col, String[] words, String[] relation, String[] tags, PaintNodes2 np, String filename, String output, String[] word) throws Exception {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 1024));
        setBackground(Color.white);
        int scaledWidth = 1024;
            int scaledHeight = 1024;
            //System.out.println("np new exp");
        File inputFile = new File(filename);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
 //System.out.println("new exp1");
        // creates output image
        BufferedImage outputImage = inputImage ;/*= new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());*/
//System.out.println("new exp2");
       // Graphics2D g2d = outputImage.createGraphics();
      //  g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        String outputImagePath = "F:/Logo.png";
       // String outputImagePath = output;
      //  image = getImg(filename);
        //int[][] dpm1;
        // this just overrides the paintComponent() method 
        // of the created panel.
        //System.out.println("non projective hi its initcomponents :");
        paintPanel = new  JPanel() {
//           dmp1 = dpm;
           public void paintComponent(Graphics g){
                super.paintComponent(g);
                nppaintNodeConnections(g,  dpm, row, col-1, relation, tags, word);
            }
        };
        paintPanel.setLayout(null);
      //  g.drawString(" "+relation[0], 100*100, 56);
       // node1 = new NodeLabel("Verb");
        //node1.setHorizontalAlignment(SwingConstants.CENTER);
        //node1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        //paintPanel.add(node1);
        //node1.setBounds(100, 52, 56, 28);
        /*node2 = new NodeLabel("Node 2");
        node2.setHorizontalAlignment(SwingConstants.CENTER);
        node2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        paintPanel.add(node2);
        node2.setBounds(186, 52, 56, 28);
        node3 = new NodeLabel("Node 3");
        node3.setHorizontalAlignment(SwingConstants.CENTER);
        node3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        paintPanel.add(node3);
        node3.setBounds(54, 152, 56, 28);*/
       // String[] wds;
        //wds = words;
        for(int j =0;j<words.length;j++)
          //System.out.println(" words["+j+"]="+words[j]+"  tags["+j+"]="+tags[j]);
        for(int i = 0 ; i < (col-1) ; i++){
          //if(i==0)
          /*if(words[i].equals("dummy")){
            //words[i] = np;
            node[i] = new NodeLabel(" "+words[i]);  
          }
          else{*/
            node[i] = new NodeLabel(" "+words[i]);
        //  }
         // else
          //  node[i] = new NodeLabel(" "+words[i-1]);
          node[i].setHorizontalAlignment(SwingConstants.CENTER);
          node[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
          paintPanel.add(node[i]);
          if(i%2 == 0)
            node[i].setBounds(54 + i*10 , 100 + i*40, 56, 28);
    else
      node[i].setBounds(186 + i*20, 100 + i*40, 56, 28);
              
        }
        getContentPane().add(paintPanel, BorderLayout.CENTER);
        pack();

        BufferedImage bi = new BufferedImage(paintPanel.getWidth(), paintPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
 Graphics2D graphics = bi.createGraphics();
 paintPanel.print(graphics);
 graphics.dispose();
 dispose();
       // add(paintPanel);
       // getContentPane().
       // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//System.out.println("new exp5");
//outputImage = renderImage(paintPanel);
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(bi, formatName, new File(outputImagePath));
      //  setSize(400, 300);
      /*  Container cp = frame.getContentPane();
        cp.setBackground(Color.RED);
        cp.setLayout(new FlowLayout());

        cp.add(new JLabel(new ImageIcon(new URL("http://i.stack.imgur.com/BvYxM.png"))));
        cp.add(new JLabel(new ImageIcon(new URL("http://www.axdn.com/redist/axpssp_logo.png"))));

  frame.pack();
  frame.setVisible(true);*/
        //savePanel(paintPanel, image);
      //  paintPanel.repaint();
        //dialog.pack();
  //dialog.setLocationRelativeTo(null);
  //dialog.dispose();
  //GraphicsSupport.saveImage(this, filename);
      //  Rectangle myBox = box;

     //   getContentPane().repaint();
         //Graphics g = image.createGraphics();

        // draw on paintImage using Graphics

      //  g.dispose();
        // repaint panel with new modified paint
      //  repaint();

       
  //makePanelImage(paintPanel, image);
  //pict.show(JFrame );
    //}
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
    
    public void show(BufferedImage image, JPanel label){
	  //frame = new JFrame("HDR View");
	  //label = new JLabel(new ImageIcon(image));
	  getContentPane().add(label);
	 // setLayout(new FlowLayout());
	  pack();
	  setVisible(true);
	}
	
	/*public int show(JFrame frame) {
	  dialog = new JDialog(frame, "Save Changes", true);
	  Container contentPane = dialog.getContentPane();
	  contentPane.setLayout(new BorderLayout());
	  contentPane.add(this, BorderLayout.CENTER);
	  dialog.setResizable(false);
	  dialog.pack();
	  dialog.setLocationRelativeTo(frame);
	  dialog.getRootPane().setDefaultButton(saveButton);
	  dialog.setVisible(true);
	  dialog.dispose();
	  return selectedValue;
	}*/
     public  void main1(int[][] parsedMatrix, int row, int col, String[] sw, String[] rel, String[] tags, String filename, String output, String[] sent) throws Exception {
        
        	PaintNodes2 pn =   new PaintNodes2(parsedMatrix, row, col, sw, rel, tags, filename, output, sent);
           /* public void run() {
            	try{
                 	new PaintNodes2(parsedMatrix, row, col, sw, rel, tags, filename).setVisible(true) ;
                 }
                 catch(Exception nullPointer) {}
                  
            }
        }) ;*/
       // pict.show();
       /* try {
         Thread threadA1 = new Thread();
         threadA1.start();

         Thread.sleep(2500);
         save();
	}
	catch(InterruptedException x){}*/
       // System.exit(1);
       // cs.main1();
        //System.exit(0);
    }
    
    public void save(){
    //	cs.main1();
        System.exit(0);
    }
    
    public JFrame getJframe(Graphics g, int[][] pm1, int rows, int cols, String[] words, String[] rel, String[] tg, String filename){
        JFrame j1 = new JFrame();

        BufferedImage image = new BufferedImage(600,600, BufferedImage.TYPE_INT_RGB);
       // Graphics2D g2d = image.createGraphics();
        RenderedImage rendImage = null;
        Graphics2D g2d = (Graphics2D)g;
        Font font = new Font("Mangal", Font.PLAIN, 18);
        g2d.setFont(font);
        // draw line between right connection point of node1
        // and the left connection point of node2
        //Point p1 = node1.getConnectionPoint("RIGHT");
        //Point p2 = node2.getConnectionPoint("LEFT");
        //Point p3 = node3.getConnectionPoint("RIGHT");
      //  int rows = pm1.length;
        //int col = pm1[0].length;
        int count = 0;
        //System.out.println(" HI ITS getJframe");
        //System.out.println("rows="+rows+"    col="+cols);
        
        int nopoints = cols;
        Point[] p = new Point[nopoints];
        for(int i=0;i<nopoints;i++){
            if(i%2 == 0)
                 p[i] = node[i].getConnectionPoint("RIGHT");
        else
                 p[i] = node[i].getConnectionPoint("LEFT");
    }
    int a =0;
    int b = 1;
    int rlcount = 0;
//        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        //g2d.drawLine(p1.x, p1.y, p3.x, p3.y);
        for(int i=0;i<rows;i++){
            count = 0;
            for(int j = 0 ; j < cols ; j++){
                if(pm1[i][j] == 1){
                    //System.out.println("count="+count+" relation["+j+"]="+rel[j]+"   tg["+j+"]="+tg[j]+"   *tg["+rlcount+"]="+tg[rlcount]+"  rel["+rlcount+"]="+rel[rlcount]);
                    if(count == 0)
                        a = j;
                    if(count == 1)
                        b = j;
                    //System.out.println("  a="+a+"    b="+b);
                    if(count == 1)
                        g2d.drawLine(p[a].x,p[a].y,p[b].x,p[b].y);
                        g2d.drawLine(p[a].x,p[a].y,p[b].x,p[b].y);
                    if(j < (cols-1)){
                        if(i%2 == 0 && count == 0){
                            //System.out.println("i="+i);
                            g2d.drawString("        "+rel[rlcount], 300 - i*50, 50+i*100);
                            g2d.drawString("        "+tg[rlcount], 250 - i*100, 50+i*150);
                            g2d.drawString("        "+rel[rlcount], 300 - i*50, 50+i*100);
                            g2d.drawString("        "+tg[rlcount], 250 - i*100, 50+i*150);
                            rlcount++;
                        }
                        else if(count == 0){
                            //System.out.println("count 0 i="+i);
                            g2d.drawString("      "+rel[rlcount], 600 -i*50, 50+i*100);
                            g2d.drawString("       "+tg[rlcount], 550 -i*100, 50+i*150);
                            g2d.drawString("      "+rel[rlcount], 600 -i*50, 50+i*100);
                            g2d.drawString("       "+tg[rlcount], 550 -i*100, 50+i*150);
                            rlcount++;
                        }
                        
                    }
                    count++;
                    
                }
                
            }
        }


           for(int j =0;j<words.length;j++)
        
            //System.out.println(" words["+j+"]="+words[j]);
        for(int i = 0 ; i < cols ; i++){
            //if(i==0)
                node[i] = new NodeLabel(" "+words[i]);
           // else
            //  node[i] = new NodeLabel(" "+words[i-1]);
            node[i].setHorizontalAlignment(SwingConstants.CENTER);
            node[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            paintPanel.add(node[i]);
            if(i%2 == 0)
                node[i].setBounds(54 , 100 + i*50, 56, 28);
        else
            node[i].setBounds(186, 100 + i*50, 56, 28);
                
        }
        j1.getContentPane().add(paintPanel, BorderLayout.CENTER);
        
       // savePanel(paintPanel, image);
        j1.setVisible(true);
       // add(paintPanel);
       // getContentPane().
       // j1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j1.pack();

     //   saveImage()
       // String file;
     /*  File file = new File("newimage.png");
         try{
            //System.out.println("ayo");
            ImageIO.write(rendImage, "png", file);
           // getSaveSnapShot(g2d, "panel2.png");//, "panel1.png");
    }
    catch(Exception nl){}*/
       // for(int i = 0 ; i < rows ; i++){
        //  for(int j = 0 ; j < cols ; j++){
                
      //        g2d.drawLine(p1.x,p1.y,p[i].x,p[i].y);
        //      g2d.drawLine(p1.x,p1.y,p[i+1].x,p[i+1].y);          
        //  }
        //}*/
    
    File file1 = new File("newimage.png");
         try{
            //System.out.println("ayo");
            ImageIO.write( image , "png", file1);
    //        getSaveSnapShot(g2d, "panel2.png");//, "panel1.png");
    }
    catch(Exception nl){}
        return j1;
    }
    public  void main2(int[][] parsedMatrix, int row, int col, String[] sw, String[] rel, String[] tags, PaintNodes2 pn, String filename, String output, String[] sent) throws Exception{
       PaintNodes2 pn2 = new PaintNodes2(parsedMatrix, row, col, sw, rel, tags, pn, filename, output, sent);
           /* public void run() {
            //pn.setVisible(true);
                new 
            }
        });*/
      //  pict.show(pn);
       /* try {
         Thread threadA1 = new Thread();
         threadA1.start();

         Thread.sleep(2000);
         save();
	}
	catch(InterruptedException x){}*/
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaintNodes2().setVisible(true);
            }
        });
    }
}

