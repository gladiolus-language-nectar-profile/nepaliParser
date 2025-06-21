import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.event.*;

public class WebcamPicture {
    public static void main(String[] args) {
        try{
            MarvinVideoInterface videoAdapter = new MarvinJavaCVAdapter();
            videoAdapter.connect(0);
            MarvinImage image = videoAdapter.getFrame();
            MarvinImageIO.saveImage(image, "./res/webcam_picture.jpg");
        } catch(MarvinVideoInterfaceException e){
            e.printStackTrace();
        }
    }
}