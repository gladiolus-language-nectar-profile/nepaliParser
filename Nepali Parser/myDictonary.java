import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class myDictonary{
  public static void main(String args[])throws IOException{
    Dictionary<Integer,String> dict = new Hashtable<Integer, String>();
    String fn=args[0];
    System.out.println("my dictonary");
    String[][] dicton = new String[1000][3];
    dicton = getTraslation(fn);
  }
  public static String[][] getTraslation(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        String wsc = "hi";
        String[][] twd = new String[1000][3];
        int counterc = -1;
        int ind=0;
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            
            while(line != null){
              counterc++;
              ind = 0;
              StringTokenizer stc = new StringTokenizer(line);
              //System.out.println("check verb vibhakti");
              //for(int i=0;i<3;i++){
                while (stc.hasMoreTokens() && ind <3) {
                  //counterc++;
                  //System.out.println("counterc="+counterc);
                  wsc = stc.nextToken();
                  twd[counterc][ind] = wsc;

                  System.out.println("twd["+counterc+"]["+ind+"]="+twd[counterc][ind]);
                 // counterc++;
                  ind++;
                }
              //}
              line = br.readLine();
            }

        }
        catch(NullPointerException ne){};
        return twd;
    }
  }