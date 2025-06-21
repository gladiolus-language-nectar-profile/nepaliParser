import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String.*;
import java.util.StringTokenizer;
public class Parser
{
    public  Parser(String s, String f){
        String RegionMatchesDemo = RegionMatchesDemo(s,f);
    }
    public  Parser(){}
   public static void main(String args[])throws IOException 
   {
       Parser p = new Parser();
       p.readFile(args[0]);
      // p.RegionMatchesDemo();
   }
   public void readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String vibhakti1 = "hi";
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            String ln = line;
            System.out.println(ln);
        while (line != null) {
            
            //sb.append(line);
            //sb.append("\n");
            line = br.readLine();
           
            vibhakti1 = line;
            if (line != null)
                RegionMatchesDemo(ln,vibhakti1);
            System.out.println(line);
            //break;
        }
       // return sb.toString();
        } finally {
        br.close();
        }
    }
    public String RegionMatchesDemo(String searchMe, String findMe) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
        int index = 0;
        String g = "Eggs";
        int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
        System.out.println(searchMe);
        System.out.println(findMe);
        
        boolean foundIt = false;
        for(int i = 0; i <= (searchMeLength - findMeLength); i++) {
            //System.out.println("i ="+i+"index="+index);
           if (searchMe.regionMatches(i, findMe, 0, findMeLength)) {
              System.out.println("check");    
              foundIt = true;
              System.out.println(searchMe.substring(i, i + findMeLength));
              index = i;
              g = searchMe.substring(i, i + findMeLength);
              break;
          }
           return searchMe.substring(i, i + findMeLength);
          // break;
        }
        if (!foundIt){
            System.out.println("No match found.");
            return g;
        }
        return g;
    }
}
