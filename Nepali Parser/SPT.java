
import java.io.IOException;
public class SPT
{
	static String[] vbs;
//	Lwg1 lg;
	public static void main(String args[]) throws IOException
	{
		Lwg2 lg = new Lwg2();
		String glyph = "देखि";
		lg.readFile(args[0], args[1],args[2], glyph);
		lg.main(args);
		vbs = lg.getVerbs();
		SPT t = new SPT();
		for(int i=0 ; i < vbs.length ; i++){
			System.out.println("Verbs["+i+"]="+vbs[i]);
			t.getSeed(vbs[i], lg,args[2]);
			//lg.displayString1(args[1], vbs, vbs.length);
		}
		lg.displayString1(args[1], vbs, vbs.length);
		
		
	}
	public void getSeed(String a, Lwg2 l, String arg){
	    // String a = "बिक्रम मेरो नाम हो";
	    // ELEMENT WISE SEGMENTATION
	    String x;
             int strLen = a.length();
             char array[] = new char[strLen];
             String strArray1[] = new String[strLen];
             for (int i=0 ; i< strLen ; i++)
             {
                 array[i] = a.charAt(i);
                 strArray1[i] = Character.toString(a.charAt(i));
                 System.out.println ("Index = " + i + "* Char = " +array[i] + "** String =" +strArray1[i] );
                 l.displayString1(arg, strArray1[i]);

             }
             
             //GLYPH WISE SEGMENTATION
             x = a;
           //  String x = "बिक्रम मेरो नाम हो";
         x=x.replaceAll(" ", ""); // Remove all spaces
         int strLength = x.length();
         String[] letterArray = new String[strLength /2 +1];
         String combined = "";
         for (int i=0, j=0; i < strLength ; i=i+2,j++)
         {
            strArray1[i] = Character.toString(x.charAt(i));
            if (i+1 < strLength)
            {
                strArray1[i+1] = Character.toString(x.charAt(i+1));
                combined = strArray1[i]+strArray1[i+1]; // This line provides the letters.
                           // Assumption is that each letter is 2 unicode characters long.

            }
            else
            {
                combined = strArray1[i];
            }
            letterArray [j] = combined; 
            System.out.println("Split string by letters is : "+combined);
            System.out.println("Split string by letters in array is : "+letterArray [j]);
            l.displayString1(arg, letterArray[j]);
            if(letterArray[j].equals("छु") || letterArray[j].equals("छौँ") || letterArray[j].equals("छन्") || letterArray[j].equals("स्") || letterArray[j].equals("छौ") || letterArray[j].equals("छ") || letterArray[j].equals("छिन्") || letterArray[j].equals("न्") || letterArray[j].equals("छन"))
            {
            	System.out.println("Sentence is in SIMPLE PRESENT TENSE");
            	break;
            }
            else{
            	System.out.println("Sentence is NOT in SIMPLE PRESENT TENSE");            
            }
         }    
        }
}