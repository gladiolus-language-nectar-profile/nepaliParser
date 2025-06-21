import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
//import java.io.OutputStreamWriter;

public class Lwg2
{
    public static int totaltag = 39;
    public static int maxwords = 500;
    public static int maxobswords = 300;
    public static String[] vbs;
   // public static double[][] transitionMatrix = new double[totaltag][totaltag];
   // public static double[][] observationMatrix = new double[totaltag][300];
    public static String[] Words = new String[maxwords];
    public static int noWords = 0;
    public static int noTags = 0;
    public static int MaxSG = 15;
    public static int nKarkas = 8;
    public static int maxKarana = 4;
    public static String filename ;
    public static String thread;
    public static int sentnoWords = 0;
    public static int indexkarana = 0;
    public static int indexkarta = 0;
    public static int noVbkt = 1;
    public static String[] sentWords = new String[maxobswords];
    public static String[] sentTotalwords = new String[2*maxobswords];
    public static String[] OriOutput = new String[3000];
    public static String[] sentwords;
    public static String[] sentwords1;
    public static String[] karana;
    public static String[] karta;
    public static String[][] Presence = new String[7][3]; 
    public static String[][] Karaka = new String[7][3];
    public static String[][] vibh = new String[7][3];
//    public static String[][] chunks = {"N","PR","DM","V","JJ","RB","PSP","CC","RP","QT","RD"};
    public static String[][] N = new String[5][3];    // Maximum 5 noun groups and each group has 3 words are assumed
     public static String[] NP = new String[5]; // Noun Phrase
    public static String[][][] Actualvibhakti;
    public static String[] Actualvibhaktilist;
    public static String Actualvbt;    
    public static int[][][] EDGES;
    public static int[] Deg =  new int[16];
    public static String[] nounTags;

 //   public static int[][] Presence = new int[4][3];        
        
    Tag ftag;
    public  Lwg2(String s, String[] f){
    }
   public  Lwg2(){}
   public static void main(String args[])throws IOException 
   {
       String glyph = "\u0926\u0947\u0916\u093F";
       int nonouns = 0;
   	String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
       //String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM","STOP"};
       for(int i = 0 ; i < totaltag ; i++)
            System.out.println("tagset["+i+"]="+tagset[i]);
       
       Lwg2 t = new Lwg2();
       final String[] tags;
       String[] morphs;
       
       String[] verbs = {"VF","VINF","VAUX","VMNE","VMKO","VMO"};
       String[] Verbs ;
       String[] V;
       String[] nouns = {"NN","NNP","NST"};
       
       
       
       String vframe;
       String[] Nouns;
       String[] Vibhakti = {"\u0932\u0947","\u0932\u093E\u0908"};
//       String[] Vibhakti = {}
       String[][][] vibhakti ;
     //  String[][] N = new String[5][3];
       karana = new String[maxKarana];
       ReadXMLFile xm = new ReadXMLFile();       
      // Chunker ch ;
//Actualvibhaktilist = new String[nKarkas];
       t.readFile(args[0], args[1],args[2], glyph);
       
       nounTags =  new String[noWords/2];
      // tags = t.getTags(sentwords1);
       //Words = new String[tags.length];
       //sentwords = new String[tags.length];
      // sentwords = new String[noWords/2];
   //   System.out.println("*****sentwords1["+0+"]="+sentwords1[0
   	//noWords = 14;
       System.out.println("No of Morphs = "+(noWords/2));
       morphs = new String[noWords/2];
       Actualvibhakti= new String[noWords/2][noWords/2][2];     
//       Actualvibhaktilist = new String[10];

       morphs = t.getMorphs(sentwords);
       tags = t.getTags(sentwords1);
       for(int i = 0 ; i < sentwords1.length ; i++){
       		System.out.println("$****sentwords1["+i+"]="+tags[i]);
       }
       System.out.println("IT IS CHUNKER");
       for(int i = 0 ; i < sentwords1.length ; i++){
       		System.out.println("*****sentwords1["+i+"]="+tags[i]);
       }
       Chunker ch =  new Chunker(noTags, morphs, tags);
       int nnlength = 0;
       int temp = 0;
       int len = 0;
       int[] len1 = new int[5] ; // assuming maximum noun chunks is 5
       
        N = ch.getNounChunks();
        len = ch.getnonounphase();
        
        System.out.println("N.len="+len+"  N["+0+"]["+2+"]="+N[0][2]);
        try{
        	if(N[0][0].equals("null1")){
        		len = 0;;
        	}
        	for(int i = 0 ; i < len ; i++){
        		String sum = "  ";
        		len1[i] = N[i].length;
        		System.out.println("len1["+i+"]="+len1[i]);
        		for(int j = 0 ; j < len1[i] ; j++){
        			if(! N[i][j].equals("n")){
        				sum = sum + N[i][j]+"	"; 
        			}
        			System.out.println("sum="+sum);
//	        		NP[i] =  N[i][j]	
	        	}
	        	NP[i] = sum;
	        	System.out.println("*NP["+i+"]="+NP[i]);
        	}
        	
		
	       
        }catch(NullPointerException nullPointer){}
        nnlength = len;
        System.out.println("nnlength = "+nnlength);
       /* 	System.out.println("N.length="+len+"  N[0][0]="+N[0][0]+"  n["+0+"].LEN="+N[0].length);
        for(int i = 0 ; i < 1 ; i++){
        	for(int j = 0 ; j < N.length ; j++){
	        	System.out.println(" N["+i+"]["+j+"]="+N[i][j]);
	        	try{
		        	if(! N[i][j].equals("n") ){
		        		nnlength = i;
		        		temp = j;
		        		if(len == 3){
		        			System.out.println("hi len = "+len);
		        			for(int k = 0 ; k < len ; k++){
		        				NP[i] = N[i][k]+"	"+N[i][k+1]+"	"+N[i][k+2];
		        				}
		        			break;
		        		}
		        		else if(len == 2){
			        		NP[i] = N[i][len-1] + "   "+ N[i][len] ;
			        		break;
			        	}
			        	else
			        		NP[i] = N[i][j] ;
	        			System.out.println("nnlength="+nnlength);
	        			//break;
	        		} 
	        		else{
	        			break;
	        		}
	        	}
	        	catch (NullPointerException nullPointer){}
	        }
        }*/
        for(int i = 0 ; i < len; i++){
        	System.out.println("	NP["+i+"]="+NP[i]);
        }
        
       Nouns = t.findNouns(tags, sentwords, nouns, Vibhakti);
	
//	Final tags;
       System.out.println("*****sentwords1["+0+"]="+tags[0]);
       System.out.println("No of Morphs = "+(noWords/2));
       
       
       //noVerbs = Verbs.length;
       int decesion = 0;
       System.out.println("WELCOME tags.len = "+tags.length+"   TAGS["+0+"]"+tags[0]);
       for(int l = 0 ; l < tags.length/2 ; l++){
	       //System.out.println("hi");
	       System.out.println("TAGS["+l+"]"+tags[l]);
       		if((tags[l].equals("PSPLE")) || (tags[l].equals("PSPKO")) || (tags[l].equals("PSPLAI"))){
			System.out.println("hi");
       			decesion = 1;
       			//break;
       		}
       		else{
       			//decesion = 0;
       			System.out.println("hi**");
       		}
       }
       Verbs = t.findVerbs(sentwords1, sentwords, verbs, xm);
       V = Verbs;
       vbs = Verbs;
       xm.displayString("vbs="+NP[0]);
       //t.getFrame(Verbs);
        try{
	  	if( decesion == 1 ) 	{
		  	System.out.println("WELCOME1");
			nonouns = Nouns.length;
		       System.out.println("Length of Verbs = "+Verbs.length+"  Length of Nouns="+Nouns.length);
		       int[][][] edges = new int[Verbs.length][Nouns.length][nKarkas];
		       edges = t.findEdges(Verbs,Nouns);
		       EDGES = edges;
	
		      // vibhakti = t.findVibhakti(sentwords1,morphs, Vibhakti, nonouns,nouns);
		       int[][][] subgraphs = new int[MaxSG][Nouns.length][Verbs.length];
		       int[][][] subgraphs3 = new int[MaxSG][Nouns.length][Verbs.length];
		       int nsgrph = 0;
		       int [][][] finalmatching = new int[MaxSG][Nouns.length][Verbs.length];
		       for(int l = 0 ; l < Verbs.length ; l++){
				       subgraphs = t.findsubGraphs(edges, nKarkas, Nouns.length, l, indexkarana);
					finalmatching = t.findfinalmatching(subgraphs, 8, Nouns.length, nKarkas, indexkarta, indexkarana, NP);
		       }
	       
		}
		else{
			System.out.println("Chunk exists ");
			t.getParsedsentence(N, V, NP, nnlength);
		}
	} catch(NullPointerException ne) { };
     //  for(int l1 = 0 ; l1 < Verbs.length ; l1++){
       //		finalmatching = t.findfinalmatching(subgraphs, 2, Nouns.length, nKarkas, indexkarta, indexkarana, Nouns);
       //}
   }
   public String[] getVerbs(){
   	return vbs;
   }
   
  
   
  /* public int[][] checkVerb(int[][][] fg, int karkas){
   		for(int i = 0 ; i < 1 ; i++){
   			for(int j = 0 ; j < karkas ; j++){
   				System.out.print(" 	fg[0][0]["+j+"]="+fg[0][0][j]);
   				if(fg[0][0][0] )
   			}
   			System.out.println();
   		}
   
   }*/
   public String[] separateVbktTAM(String vk){
	String vib;
	String temp;
	StringTokenizer st1 = new StringTokenizer(vk);
	
	String[] vb = new String[st1.countTokens()];   // maximum vibhakti for any verb = 5
	String[] vbt = new String[Math.round(st1.countTokens()/2)+1];
	int count = 0;
	int count1 = 0;
	int t1 = Math.round(st1.countTokens()/2)+1;
	System.out.println("t1="+t1);
	//BufferedReader br = new BufferedReader(, "UTF-8");
	System.out.println("Its a SeparateVbktTAM: ");
	try{
		String ln = vk;
		while(ln != null && count < st1.countTokens()){
			//t++;
			//StringTokenizer st1 = new StringTokenizer(ln);
			//vb = new String[st1.countTokens()];
			while (st1.hasMoreTokens()) {
                            vib = st1.nextToken();
                            vb[count] = vib;
                            if(count % 2 == 0){
	                            vbt[count1] = vib;
	                            System.out.println("0vbt["+count1+"]="+vbt[count1]);
	                            
	                            count1++;
	                            
	                    }else{
	                    	temp = vib;
	                    	
	                    }
                            System.out.println("1vbt["+(count1-1)+"]="+vbt[count1-1]);
                            count++;
                            //count1++;
                        }
		}
	}
	finally {
     		   //br.close();
     	}
     	return vbt;
   }
   
   public void readFile(String fileName, String outputName, String dispfile, String gl) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName, true), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        filename = dispfile;
        String vibhakti1 = "hi";
        try {

            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            System.out.println("hi");
            String[] words = new String[300];// = line.split(" ");//those are your words
            sentwords = new String[300];
            sentwords1 = new String[300];
            int flag = 0, flag1 = 0, flag2 = 0 ;
            int alaram = 0;
            String ws;
            int counter = 0 ;
            String[] sws = new String[2];
            int nowords = 0;
            fout.write("ITS A READFILE IN LWG.JAVA");
            fout.write("\n");
            while (line != null) {
	   	StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                            counter++;
                          //  alaram = 0;
                	   // flag = 0;
                            ws = st.nextToken();
                            System.out.println("CHECK FOR NEW WORD *** ");
                            System.out.println("beforeWS = "+ws+" flag = "+flag +"  counter ="+counter+"  nowords="+nowords);
                            if(flag == 1 && counter%2 == 0){
                            		words[nowords-3] = ws;
		                    	System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                                    sws = RegionMatchesDemo("\u0932\u0947", ws);
	                            System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                            if(sws[0].equals("ab"))
		                            flag = 0;
		                    else
		                            flag = 1;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   
	                   if(flag == 1 ){
	                    	words[nowords] = sws[0];
	                    	System.out.println("wd["+nowords+"]="+words[nowords]);
	                    	System.out.println("beforenowods="+nowords);
	                  //  	if(nowords%2 != 0){
		            //        	words[nowords+1] = ws;
		              //      	System.out.println("wd["+(nowords+1)+"]="+words[nowords+1]);
		           //	}
	                    	words[nowords+2] = sws[1];
	                    	System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
	                    	words[nowords+3] = "PSPLE";
	                    	System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
	                    	System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
	                    	nowords = nowords + 4;
	                    	alaram = 1;
	                    	
	                   }
	                   
	                   System.out.println("CHECK FOR PLAI ");
	                   if(flag1 == 2 && counter%2 == 0){
                            		words[nowords-3] = ws;
		                    	System.out.println("***wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
	                   sws = RegionMatchesDemoLai("\u0932\u093E\u0908", ws);
	                   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                   if(sws[0].equals("ab"))
		                   flag1 = 0;
		           else
		                    flag1 = 2;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   if(flag1 == 2){
	                    	words[nowords] = sws[0];
	                    	System.out.println("beforenowods="+nowords);
	                    //	if(nowords%2 != 0)
		              //      	words[nowords+1] = ws;
	                    	words[nowords+2] = sws[1];
	                    	words[nowords+3] = "PSPLAI";
	                    	System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
	                    	nowords = nowords + 4;
	                    	alaram = 1;
	                   }
	                   
	                   
	                   System.out.println("CHECK FOR PKO ");
	                   if(flag2 == 2 && counter%2 == 0){
                            		words[nowords-3] = ws;
		                    	System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
	                   sws = RegionMatchesDemo("\u0915\u094B", ws);
	                   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                   if(sws[0].equals("ab"))
		                   flag2 = 0;
		           else
		                    flag2 = 2;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   if(flag2 == 2){
	                    	words[nowords] = sws[0];
	                    	System.out.println("beforenowods="+nowords);
	                    //	if(nowords%2 != 0)
		              //      	words[nowords+1] = ws;
	                    	words[nowords+2] = sws[1];
	                    	words[nowords+3] = "PSPKO";
	                    	System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
	                    	nowords = nowords + 4;
	                    	alaram = 1;
	                   }
	                   
	                   System.out.println("flag="+flag+"  flag1 = "+flag1+"   flag2 ="+flag2+"  nowords="+nowords+"  alaram = "+alaram);
	                   
	                    if(flag == 0 && flag1 == 0 && flag2 == 0 && alaram == 0){
	                            words[nowords]=ws;
	                            System.out.println("wd["+(nowords)+"]="+words[nowords]);
        	                    nowords++;
        	            }
        	            else if(flag == 0 && flag1 == 0 && flag2 == 0) {
        	                    alaram = 0;
        	            }
                 }
                 line = br.readLine();
       //     }
       		System.out.println("nowods="+nowords);
           	 for(int i = 0 ; i < nowords; i++){
                	 System.out.println("words["+i+"]="+words[i]);
	                 fout.write(words[i]);
        	         fout.newLine();
            	}
	        fout.newLine();
        	int j = 0;
	        int k = 0;
	        sentwords = new String[nowords];
	        sentwords1 = new String[nowords];
	        Actualvibhaktilist = new String[nowords/2];
	        // sentwords = words and sentwords1 = tags
        	for(int i = 0 ; i < nowords ; i++){
            		if(i %2 == 0){
	            		sentwords[j] = words[i];
        	    		System.out.println("sentwords["+j+"]="+sentwords[j]);
	        	    	fout.write("sentwords["+j+"]="+sentwords[j]);
        	    		fout.newLine();
	        	    	j++;
        	    	}
	            	else{
        	    		sentwords1[k] = words[i];
        	    		
				System.out.println("sentwords1["+k+"]="+sentwords1[k]);
		            	fout.write("sentwords1["+k+"]="+sentwords1[k]);
	        	    	fout.newLine();
	        	    	k++;
		      	}
            	}
	        System.out.println("line= "+line+"  noWords ="+nowords+"    noTags = "+noTags);
	        int cnt = 0;
	        while(cnt < (nowords/2-1)){
//	        int cnt = 0;
	        	Actualvbt = getVbt(sentwords, sentwords1,2*cnt);
		        Actualvibhaktilist[cnt] = Actualvbt;
	        	fout.newLine();
		        fout.write("  Actualvbt ="+Actualvbt+"  Actualvbtlist["+cnt+"]="+Actualvibhaktilist[cnt]);
       		        fout.newLine();
       		        cnt++;
		}

        	Words = words;
	        noWords = nowords;
        	noTags = nowords/2;
	        fout.write(gl);
	        maxobswords = noTags;
//	        Verbs = findVerbs(sentwords1, sentwords, verbs, xm);
	        break;
       	   }
       } finally {
     		   br.close();
  		   fout.close();
        }
    }
    
    public String getVbt(String[] v, String[] tagv, int cnt){
    	String temp = "null";
    	int flag = 0;
	//String[] temp1 = new temp1[]
    	String[] ptag = {"PSPLE","PSPLAI","PSPKO","PSPO"};
    	System.out.println("tagv.length="+tagv.length+"nowords="+noWords);
    	for(int i = cnt ; i < tagv.length/2 ; i++){
    		System.out.println("tagv["+i+"]="+tagv[i]);
    		for(int j=0 ; j < 4; j++){
	    		if(tagv[i].equals(ptag[j]) && flag == 0){
	    			System.out.println("tagv["+i+"]="+tagv[i]+"  ptag["+j+"]="+ptag[j]+"   v["+i+"]="+v[i]);
    				temp = v[i];
    				flag = 1;
    				break;
    			}
    			else{
    				//temp = "null";
	    		}
	    	}
	    	
    		
    	}
    	System.out.println("VIBHAKTI="+temp);
    	return temp;
    
    }
    public String[] getTags(String[] s){
      		for(int i = 0 ; i < noWords/2 ; i++)
	       		System.out.println("Tags["+i+"]="+s[i]);
       		return s;
    }
    public String[] getMorphs(String[] s1){
      		for(int i = 0 ; i < noWords/2 ; i++)
	       		System.out.println("Morphs["+i+"]="+s1[i]);
	       		displayString1(filename,s1,noWords/2);
       		return s1;
    }
    public int getTAM(String vbr, ReadXMLFile f2 ){
    	int ind;
    	f2.displayString(vbr);
//    	int psnverb;
    	String[] vb = new String[2];
	String[][]  prsce = new String[7][3];
	String[][] ktk = new String[7][3];
	String[][] vbkt = new String[7][3];
	vb[0] = vbr;
	//	ReadXMLFile xm = new ReadXMLFile();
    	System.out.println("getTAM");
    	System.out.println("verb="+vbr);
        f2.main1(vb);
        /*for(int i = 0 ; i < vb.length ; i++){
        	f2.displayString(vb[i]);
        }*/
        
        ind = f2.getFrameIndex();
       // ind = 2;  // IN THE CASE OF FRAME DOES NOT EXIST
    	//ind = f2.getRootverb(vbr);
    	System.out.println("index = "+ind);
    	if(ind != -1){
	    	prsce = f2.getPresence(ind);
	    	ktk = f2.getKaraka(ind);
	    	vbkt = f2.getVbhkt(ind);
	    	for(int i = 0 ;i < 7; i++){
	    		for(int j = 0 ; j < 3 ; j++){
			    	System.out.println("index = "+ind+" Presence["+i+"]["+j+"] = "+prsce[i][j]+" karaka["+i+"]["+j+"] = "+ ktk[i][j] +" vbkt["+i+"]["+j+"] = "+vbkt[i][j]);
			}
			System.out.println();
		}
		Presence = prsce;
		Karaka = ktk;
		vibh = vbkt;
		//Actualvibhakti = vibh;
	}
   	return ind;
    }
    public boolean checkTAM(int[][][] tg, int tsg, int trows, int tcol) throws ArrayIndexOutOfBoundsException {
    	boolean flg1=false;
    	String prsk1;
    	String krrk1;
    	String vbtk1;
    	String prsk2;
    	String krrk2;
    	String vbtk2;
    	int count = 0;
    	String prsk4;
    	String krrk4;
    	String vbtk4;
    	String prsk5;
    	String krrk5;
    	String vbtk5;
    	String prsk6;
    	String krrk6;
    	String vbtk6;
    	int flagk1 = 1;
    	int flagk2 = 1;    	
       	int flagk3 = 1;
       	int flagk4 = 1;
       	int flagk5 = 1;
       	int flagk6 = 1;   
       	int[] flgk = new int[6];    	
    	String prsk3;
    	String krrk3;
    	String vbtk3;
    	AsciiString as = new AsciiString();
    	String temp;
    	int[][][] newfg = new int[1][trows][tcol];
    	newfg = tg;
    	int t = 0;
    	
    	String[] tvbt = new String[4]; // Maximum number of vibhakties associated with a verb is 4
    	System.out.println("HI ITS CHECKTAM....");
    	//prsk1 = Presence;
    	for(int i=0;i<1;i++){
    		for(int j=0;j<trows;j++){
    			for(int k = 0 ; k < tcol ; k++){
    				System.out.println("\t "+tg[i][j][k]);
    			}
    			System.out.println();
    		}
    	}
    	int nxt = 0;
    	// Check Karta (K1)
    				prsk1 = Presence[0][0];
    				System.out.println("prsk1="+prsk1);
    				if(prsk1.equals("Mendatory") ){
    					System.out.println("Mendatory");
    					krrk1 = Karaka[0][1];
    					System.out.println("krrk1="+krrk1);
    					if(krrk1.equals("karta(K1)")){
    						System.out.println("karta(k1)");
    						vbtk1 = vibh[0][2];
 					        tvbt = separateVbktTAM(vbtk1);
 					        
 					        for(int i = 0 ; i < tvbt.length ; i++ ){
	 					        temp = as.decode(tvbt[i]);
 					        	System.out.println("Actualvibhakti[0][0][1]="+Actualvibhaktilist[t]+"tvbt["+i+"]="+tvbt[i]+"   temp="+temp);
 					        	/*for(int nx = 0 ; nx < trows ; nx++){
 					        		if(Actualvibhakti[nx][2*nx][1].equals("null") && nxt == 0){ 
 					        			nxt = 0;
 					        			System.out.println("nxt..."+nxt);
 					        			//System.exit(0);
 					        		}
 					        		else{
 					        			nxt = 1;
 					        			System.out.println("**nxt..."+nxt);
 					        			//break;
 					        		}
 					        		if(nxt == 0){
 					        			System.out.println("FRAME DOES NOT EXIST...");
 					        			System.exit(0);
 					        		}
 					        	}*/
 					        	//int t = 0;
 					        	for(int l = 0 ; l < trows ; l++){
 					        		System.out.println("tvbt["+i+"]="+tvbt[i]);
 					        		System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhaktilist[t] + "tvbt["+i+"]="+tvbt[i]);
 					        		System.out.println("Actualvibhakti="+Actualvbt);
 					        		/*if(Actualvibhakti[l][2*l][1].equals("null") && nxt == 0){ 
 					        			nxt = 0;
 					        			System.out.println("nxt..."+nxt);
 					        			//System.exit(0);
 					        		}
 					        		else{
 					        			nxt = 1;
 					        			System.out.println("**nxt..."+nxt);
 					        			//break;
 					        		}
 					        		if(nxt == 0){
 					        			System.out.println("FRAME DOES NOT EXIST...");
 					        			System.exit(0);
 					        		}*/
 					        		//else{
 					        		    try{
		 					        	if(Actualvibhaktilist[t].equals(tvbt[i]) || Actualvbt.equals("\u092C\u093E\u091F")){
		 					        		System.out.println("**********karta(k1)");
 						       // }
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    								            //for(int r = 0 ; r < 4 ; r++){
    								         //   tg[0][0][0]=1;
    								            	System.out.println("tg[0]["+l+"]["+0+"]="+tg[0][l][0]);
	    									if(tg[0][l][0] == 1 ){    // 0th row of the final subgraph
	    										count++;
	    										//if(tvbt[i].equals("null"))
		    									//	count++;
    										
	    									}
	    									else{
    											count = count;
    											
    										}
    									     // }
	    								      System.out.println("karta count ="+ count);
    									      if(count == 1){
    								      		       System.out.println("KARTA(K1) velidated...");
	    								      	       flagk1 = 0;
	    								      	       break;
    									      }
	    								      else if (flagk1 != 0){
    									      		//tg[0][0][0] = 0;
    											System.out.println("KARTA(K1) is Not velidated...");
    											System.out.println("karta count i="+ i);
    											flagk1 = 1;
    											break;
    									      }
 	    								}
		 	    						else{
    									      		if(flagk1 == 0 && i == (tvbt.length-1))
    									      			break;	
    							 	        }
    							            }
    							            catch (NullPointerException nullPointer)
								   {
										//log(causeStr, nullPointer, System.out);
							           }   
							           t++; 
    							 	    
	    							}
	    							//}
//	    							flagk1 = 
	    							/*else{
										System.out.println("FRAME DOES NOT EXIST...");
 					        				System.exit(0);
								}*/
	    					}
	    						
    					}
    				}	
    			//}
    		//}
    	
    	// Check Karma (K2)
    				count = 0;
    				t=0;
    	    			prsk2 = Presence[1][0];
    				System.out.println("prsk2="+prsk2 +"    no of ROWs="+trows +" vibhakti length = "+tvbt.length);
    				if(prsk2.equals("Mendatory") || prsk2.equals("Optional")){
    					System.out.println("Mendatory");
    					krrk2 = Karaka[1][1];
    					System.out.println("krrk2="+krrk2);
    					if(krrk2.equals("karma(K2)")){
    						System.out.println("karma(k2)");
    						vbtk2 = vibh[1][2];
 					        tvbt = separateVbktTAM(vbtk2);
 					        for(int i = 0 ; i < tvbt.length && t < tvbt.length; i++ ){
 					        	System.out.println("Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"   tvbt["+i+"]="+tvbt[i]);
 					        	for(int l = 0 ; l < trows ; l++){
 					        	    System.out.println("tvbt["+i+"]="+tvbt[i]);
 					        	    System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhakti[l][2*l][1] + "   ***tvbt["+i+"]="+tvbt[i]);
 					        	    try{
 					        	    	System.out.println("**********Akarma(k2)");
 					        	    	//System.out.println("Actualvibhakti["+t+"]="+Actualvibhaktilist[t] + "  ***tvbt["+i+"]="+tvbt[i]);
// 					        	    	continue;
						 		System.out.println("flagk2="+flagk2+"    count="+count +"  Actualvibhaktilist[t]"+Actualvibhaktilist[t]);
	 					        	if( count == 1 || Actualvibhakti[l][2*l][1].equals(tvbt[i]) || Actualvibhaktilist[t].equals(tvbt[t])){
 						       			System.out.println("**********karma(k2)");
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    								//    for(int r = 0 ; r < trows ; r++){
    									System.out.println("tg[0]["+l+"]["+1+"]="+tg[0][l][1]);
	    								if(tg[0][l][1] == 1){     // 1st row of the final subgraph
	    									count++;
    										
    									}
	    								else{
    										count = count;
    										
    									}
    								 //     }
	    							      System.out.println("karma count ="+ count);
    								      if(count == 1){
    								      	       System.out.println("KARMA(K2) velidated...");
    								      	       flagk2 = 0;
    								      	       break;
    								      }
    								      else if (flagk2 != 0){
    								      		//tg[0][1][1] = 0;
    										System.out.println("KARMA(K2) is Not velidated...");
    										if( l == (trows-1)){
	    										flagk2 = 1;
	    									}
	    									else{
	    										//continue;
	    									}
    										//break;
	    							      }
		    						
 	    							}
 	    							else{
 	    									System.out.println("HI  and i = "+i+"     flagk2="+flagk2);
 	    									
    								      		if(flagk2 == 0 && i == (tvbt.length-1)){
 	 	    									System.out.println("hi");
    								      			break;	
    								      		}
    								}
    							     }
    							     catch (NullPointerException nullPointer)
							     {
										//log(causeStr, nullPointer, System.out);
							     }  
						//	           t++;  
	    						}
	    						//t++;
	    						 
	    					}
	    					t++;	
    					}
    				}	

    	// Check Karana (K3)
    	
    	
    	    			count = 0;
    	    			t=0;
    	    	    		prsk3 = Presence[2][0];
    				System.out.println("prsk3="+prsk3);
    				
    				if(prsk3.equals("Optional") ){
    					System.out.println("Optional");
    					krrk3 = Karaka[2][1];
    					System.out.println("krrk3="+krrk3);
    					if(krrk3.equals("karana(K3)")){
    						System.out.println("karana(K3)");
    						vbtk3 = vibh[2][2];
 					        tvbt = separateVbktTAM(vbtk3);
 					        for(int i = 0 ; i < tvbt.length ; i++ ){
 					        	System.out.println("Actualvibhakti[2][4][1]="+Actualvbt+"  tvbt["+i+"]="+tvbt[i]);
 					        	for(int l = 0 ; l < trows ; l++){
 					        		System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
 					        		try{
	 					        	if(Actualvibhaktilist[t].equals(tvbt[i])) {
 						       // }
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    									 // int karn ;              // Karana does not exist
//    									 r = l;
		    							 // for(int r = 0 ; r < trows ; r++){
			    							  System.out.println("l="+l);
	    										if(tg[0][l][2] == 1 ){//&& karn != 0){     // 2nd row of the final subgraph
	    											System.out.println("**l="+l);
	    											count++;
    												
		    									}
    											else{
	    											count = count;
    												
			    								}
//    									      }
    									      System.out.println("Karana count ="+ count);
	    								      if(count == 1){
    									      	       System.out.println("Karana(K3) velidated...");
    									      	       flagk3 = 0;
    									      	       break;
    									      }
	    								      else{
    									      		//tg[0][2][3] = 0;
    											System.out.println("Karana(K3) is Not velidated...");
	    										flagk3 = 1;
    									      }
    									}
	    							        else{
    										if(flagk3 == 0 && i == (tvbt.length-1))
    								      			break;	
    									}
								     }
    							            catch (NullPointerException nullPointer)
								   {
										//log(causeStr, nullPointer, System.out);
							           }  
							           t++;  
    							        }
    							}
    						}
    				}	

    	
    	// Check Sampradan (K4)
    				count = 0;
    				t = 0;
    	    	    		prsk4 = Presence[3][0];
    				System.out.println("prsk4="+prsk4);
    				if(prsk4.equals("Mendatory") || prsk2.equals("Optional") ){
    					System.out.println("K4 Optional");
    					krrk4 = Karaka[3][1];
    					System.out.println("krrk4="+krrk4);
    					if(krrk4.equals("Sampradan(K4)")){
    						System.out.println("Sampradan(K4)");
    						vbtk4 = vibh[3][2];
 					        tvbt = separateVbktTAM(vbtk4);
 					        for(int i = 0 ; i < tvbt.length ; i++ ){
 					        	//System.out.println("Actualvibhakti[3][6][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
 					        	for(int l = 0 ; l < trows ; l++){
 					        		System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
 					        		try{
	 					        	if(Actualvibhaktilist[t].equals(tvbt[i])){
 						       // }
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    							
			    						//  for(int r = 0 ; r < trows ; r++){
		    									if(tg[0][l][3] == 1){    // 3nd row of the final subgraph
	    											count++;
    												
		    									}
    											else{
	    											count = count;
    												
			    								}
    									 //     }
    									      System.out.println("Sampradan count ="+ count);
    									      if(count == 1){
    									      	       System.out.println("Sampradan(K4) velidated...");
    									      	       flagk4 = 0;
    									      	       break;
    									      }
		    							      else{
    									      		//tg[0][2][3] = 0;
    											System.out.println("Sampradan(K4) is Not velidated...");
    											flagk4 = 1;
    									      }
    									}
	    							        else{
    										if(flagk4 == 0 && i == (tvbt.length-1))
    								      			break;	
    								      		
    									}
    								    }
    							            catch (NullPointerException nullPointer)
								   {
										//log(causeStr, nullPointer, System.out);
							           }   
							           t++; 	
    							        }
    							}
    						}
    						
    				}	
    				
    	// Check Apadan(K5)
    				count = 0;
    				t = 0;
    	    	    		prsk5 = Presence[4][0];
    				System.out.println("prsk5="+prsk5);
    				if(prsk5.equals("Optional") && t < 4 ){
    					System.out.println("Optional");
    					krrk5 = Karaka[4][1];
    					System.out.println("krrk5="+krrk5);
    					if(krrk5.equals("Apadan(K5)")){
    						System.out.println("Apadan(K5)");
    						vbtk5 = vibh[4][2];
 					        tvbt = separateVbktTAM(vbtk5);
 					        for(int i = 0 ; i < tvbt.length ; i++ ){
 					        	System.out.println("Actualvibhakti[4][8][1]="+Actualvbt+"tvbt["+i+"]="+tvbt[i]);
 					        	for(int l = 0 ; l < trows  && t < trows; l++){
 					        		System.out.println("**Actualvibhakti["+l+"]["+(2*l)+"][l]="+Actualvibhaktilist[t] +"   t="+t);
 					        		try{
		 					        	if(Actualvibhaktilist[t].equals(tvbt[i])){
 						       				System.out.println("Apadan count ="+ count);
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    							
			    						//  for(int r = 0 ; r < trows ; r++){
		    									if(tg[0][l][4] == 1){    // 3nd row of the final subgraph
	    											count++;
	    											System.out.println("hi");
    												
		    									}
    											else{
	    											count = count;
    												
			    								}
    									 //     }
    									      System.out.println("Apadan count ="+ count);
    									      if(count == 1){
    									      	       System.out.println("Apadan(K5) velidated...");
    									      	       flagk5 = 0;
    									      	       break;
    									      }
		    							      else{
//    									      		tg[0][2][3] = 0;
    											System.out.println("Apadan(K5) is Not velidated...");
    											flagk5 = 1;
    									      }
    									}
	    							        else{
    										if(flagk5 == 0 && i == (tvbt.length-1))
    								      			break;	
    								      		
    									}
    								    }
    							            catch (NullPointerException nullPointer)
								   {
										//log(causeStr, nullPointer, System.out);
							           }   
							           t++; 	
    							        }
    							}
    						}
    						
    				}	


    	// Check Sambandh(K6)
    				count = 0;
    				t = 0 ;
    	    	    		prsk6 = Presence[5][0];
    				System.out.println("prsk6="+prsk6);
    				if(prsk6.equals("Optional")  ){
    					System.out.println("Optional");
    					krrk6 = Karaka[5][1];
    					System.out.println("krrk6="+krrk6);
    					if(krrk6.equals("Sambandh(K6)")){
    						System.out.println("Sambandh(K6)");
    						vbtk6 = vibh[5][2];
 					        tvbt = separateVbktTAM(vbtk6);
 					        for(int i = 0 ; i < tvbt.length ; i++ ){
 					        	//System.out.println("Actualvibhakti[5][10][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
 					        	for(int l = 0 ; l < trows && t < trows; l++){
 					        		System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
 					        		try{
		 					        	if(Actualvibhaktilist[t].equals(tvbt[i])){
 						       // }
    							//if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
    							
			    						//  for(int r = 0 ; r < trows ; r++){
		    									if(tg[0][l][5] == 1){    // 4nd row of the final subgraph
	    											count++;
    												
		    									}
    											else{
	    											count = count;
    												
			    								}
    									 //     }
    									      System.out.println("Sambandh count ="+ count);
    									      if(count == 1){
    									      	       System.out.println("Sambandh(K6) velidated...");
    									      	       flagk6 = 0;
    									      	       break;
    									      }
		    							      else{
//    									      		tg[0][2][3] = 0;
    											System.out.println("Sambandh(K6) is Not velidated...");
    											flagk6 = 1;
    									      }
    									}
	    							        else{
    										if(flagk6 == 0 && i == (tvbt.length-1))
    								      			break;	
    								      		
    									}
    								    }
    							            catch (NullPointerException nullPointer)
								   {
										//log(causeStr, nullPointer, System.out);
							           }   
							           t++; 	
    							        }
    							}
    						}
    						
    				}	
    				
    				
    				System.out.println("flagk1= "+flagk1+"   flagk2="+flagk2+"    flagk3= "+flagk3+"  flagk4= "+flagk4 +"      flagk5=  "+flagk5+  "     flagk6="+flagk6+"    trows="+trows);
    				flgk[0] = flagk1;
    				flgk[1] = flagk2;
    				flgk[2] = flagk3;
    				flgk[3] = flagk4;
    				flgk[4] = flagk5;
    				flgk[5] = flagk6;
    				if(trows < 3){
    					flagk3 = 0;
    					flagk4 = 0;
    				}
    				/*if(flagk1 == 0 && flagk2 == 0 && flagk3 == 0 || flagk4 == 0 || flagk5 == 0 || flagk6 == 0 ){
    					flg1 = true;
    				}
    				else{
    					flg1 = false;
    				}*/
    				int ct=0;
    				for(int m = 0 ; m < trows ; m++){
	    				for(int i=0;i<6;i++){
	    				
    						if(tg[0][m][i] == 1){
	    							System.out.println(" ct0="+ct);    						
    						//while(ct < trows){
	    						if(flgk[i] == 0 ){
	    							ct++;
	    							flg1 = true;
	    							System.out.println(" ct="+ct);
	    							if(ct == trows)
	    								break;
	    							
    							//break;
    							}
	    						else{
    								flg1 = false;
    							} 
    						}
    						else 
    							flg1 = false;
    		//			}
    					}
    				}
    				
    				//	break;
    				
    				//}
    				//while(ct <= trows);
//    				break;
    				
	/*			newfg = NewSubgraph(tg,trows,tcol,flagk4);
    				System.out.println("\t  KARTA(k1)\tKARMA(k2)\tKARANA(k3)\tGOL(K2P)");
	   	   		for(int l = 0 ; l < trows ; l++){
	   	   			System.out.println();
   			 		for(int m = 0 ; m < tcol ; m++){
    						System.out.print("  newfg["+0+"]["+l+"]["+m+"]="+newfg[0][l][m]);
	    				}
	    				System.out.println();
    				}*/
    	
    	return flg1;
    
    }
    
 /*   public String getPresence(int inx , ReadXMLFile f3){
    	return f3.getPresence(inx);
    }
    
    public String getKaraka(int inx , ReadXMLFile f3){
    	return f3.getKaraka(inx);
    }
    
    public String getVbkht(int inx , ReadXMLFile f3){
    	return f3.getVbhkt(inx);
    
    }*/
    public static void displayString1(String outputName4, String[] wds, int wdslen){
	try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName4, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        fout.write("Hi its displayString words"+wdslen);
	        fout.write("\n\n");
	   	 for(int l = 0 ; l < wdslen ; l++){
	   	   		fout.write("\n");
	   	   		fout.write(wds[l]);
   		 		//(int m = 0 ; m < krks ; m++){
    			//		fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
	    		//	}
    				fout.write("\n");
    		}
    			fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
    
    public static void displayString1(String outputName4, String wds){
	try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName4, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	       // fout.write("Hi its displayString words"+wdslen);
	        fout.write("\n\n");
	   	// for(int l = 0 ; l < wdslen ; l++){
	   	   		fout.write("\n");
	   	   		fout.write(wds);
   		 		//(int m = 0 ; m < krks ; m++){
    			//		fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
	    		//	}
    				fout.write("\n");
    		//}
    			fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
   // }
   
   public static void displayString1(String outputName5, String[][][] wds, int wdslen){
	try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName5, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        fout.write("Hi its displayString vibhakti"+wdslen);
	        fout.write("\n\n");
	   	 for(int l = 0 ; l < wdslen ; l++){
	   	   		fout.write("\n");
	   	   		for(int m = 0 ; m < (noWords/2) ; m++){
	   	   			for(int n = 0 ; n < 2; n++){
	   	   				System.out.print("\t wds["+l+"]["+m+"]["+n+"]=" +wds[l][m][n]);
			   	   		fout.write("\t wds["+l+"]["+m+"]["+n+"]=" +wds[l][m][n]);	   	   			
	   	   			}
	   	   		}

   		 		//(int m = 0 ; m < krks ; m++){
    			//		fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
	    		//	}
    				fout.write("\n");
    		}
    			fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
    public void displayString(String outputName2, int[][][] fg1, int rows, int krks, String[] nn){
    try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName2, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        fout.write("Hi its displayString FINAL OUTPUT");
	        fout.write("\n\t  KARTA(k1)      \tKARMA(k2)     \tKARANA(k3)     \tSAMPRADAN(K4)   \tAPADAN(K5)  \tSAMBANDH(K6)");
	   	   	for(int l = 0 ; l < rows ; l++){
	   	   		fout.write("\n");
	   	   		fout.write(nn[l]);
   		 		for(int m = 0 ; m < krks ; m++){
    					fout.write("     fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
	    			}
    				fout.write("\n");
    			}
    			fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
    
    public void displayString(String outputName3, String[][][] vb, int rows, int tgl, String ex){
    try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName3, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        fout.write("Hi its displayString Vibhakti and corresponding Noun");
	        fout.write("\n\t  Noun \t \t\tVibhakti");
	        fout.write(" \ntgl="+tgl+"    rows="+rows);
	   	   	for(int l = 0 ; l < rows ; l++){
	   	   		fout.write("\n");
//	   	   		fout.write(nn[l]);
   		 		for(int m = 0 ; m < tgl ; m++){
   		 			for(int n=0 ; n <2 ; n++){
	    					fout.write(" \t vb["+l+"]["+m+"]["+n+"]="+vb[l][m][n]);
	    				}
	    				fout.write("    ex="+ex);
	    			}
    				fout.write("\n");
    			}
    			fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
    public void displayString(String[] s, int number, String outputName1, String[] vbkt) throws IOException {
    	try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName1, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        System.out.println("Hi its displayString");
	        System.out.println("number = "+number+"Vibhakti[0]="+vbkt[0]);
	        boolean b = true;
	        for(int i = 0 ; i < number ; i++){
	        	System.out.println("s["+i+"]="+s[i]+"   s.length= "+s.length+"   number of nouns = "+number);
	        	fout.write("s["+i+"]="+s[i]+"   s.length= "+s.length+"   number of nouns = "+number+"No of Vibhakti = "+noVbkt);
	        	//String a = s[i];
	        	fout.write(s[i]);
	        	fout.newLine();
	        	String string1 = s[i];
	        	fout.write("String1 = "+string1);
	        	//b = string1.endsWith(vbkt[i]); 
	        	for(int l=0;l<noVbkt;l++){
	        		if(string1.contains(vbkt[l])){
	        			System.out.println("Hi its a vibhakti section:  ");
	        			b = true;
	        			break;
	        		}
	        		else
	        			b = false;	
	        		
	        	}
	        	//String string1 = "Madam, I am Adam";
	        	
	        /*	if( b){
	        		fout.newLine();
	        		fout.write(" ");
	        		System.out.println("True");
	        		
	        		if(i != 0 ){
	        			//karana[i] = s[i];
	        			indexkarana = i;
	        			fout.write("karana["+i+"]="+karana[i]+" indexkrana = "+indexkarana);
	        			System.out.println("karana["+i+"]="+karana[i]+" indexkrana = "+indexkarana);
	        			fout.newLine();
	        		}
	        		else{
	        			indexkarta = i;
	        			
	        			//karta[i] = s[i];
	        			fout.write(" indexkarta = "+indexkarta);
	        			System.out.println("indexkarta="+indexkarta);
	        			fout.newLine();
	        		}
	        		fout.write(s[i]);
	        	}*/
	       

		        	fout.newLine();
		        	
		    //    }
		      //  fout.close();
	        }
	        fout.close();
	} finally {	
	       // fout.close();
	}
    
    }
    private String[] findVerbs(String[] tgs, String[] ms , String[] wd, ReadXMLFile f1){
       		System.out.println("HI ITS A VERB SECTION");
    		String[] vbs = tgs;
    		String ind = "NULL";
    		String[] y = new String[10];
   		//for(int i = 0; i < noWords/2 ; i++){
   			//f1.displayString(ms[i]);
   		//}
    		//String[] x = new String[2];
    		int  count = 0;
    		int hint = 0;
    		//String[] tvbs = new String[5];
    		for(int i = 0; i < noWords/2 ; i++){
    			//f1.displayString(ms[count]);
    			for(int j=0;j<6;j++){  // No of verb tags = 6
	    			if(tgs[i].equals(wd[j])){
	    				if (count == 0){
	    				//if(count > 0  && wd[j].equals("VBX")){  	
	    					System.out.println("tgs["+i+"]="+tgs[i]+"   wd["+j+"]="+wd[j]);
    						y[count] = ms[i];
    						//y[count] = vbs[i];
    						//count++;
    						System.out.println("Count="+count);
	    					vbs[count] = ms[i];
    						System.out.println("Verbs["+i+"]="+vbs[i]);
    						f1.displayString(ms[i]);
    						hint = getTAM(vbs[count], f1);
    						if(hint == -1){
    							System.out.println("FRAME DOES NOT EXIST:  ");
	    						System.exit(0);
    						}
    						count++;
    					//	tvbs[k] = vbs[i];	
    					}
    					else{
    						if(count == 1 && wd[j].equals("VAUX")){
    							break;	
    						}
    						else{
    							continue;
    						}
    					}
	    			}
	    			else{
					//if
		    		}
	    				//k++;
	    		}
		    		//x[k] = ind;
    		}
 		String[] z = new String[count];
	    	String b = "null";
    		int m = 0;
    		for(int l = 0 ; l < 10 ; l++){
    			if(b.equals(y[l])) {
    				break;
	    		}
	    		else if(m < count){
	    			System.out.println("y["+l+"]="+y[l]+" m="+m+" Count="+count);
	    			z[m] = y[l];	
			
	    		}
	 	//	System.out.println("z["+m+"]="+z[m]);
	    		m++; 
    		}    				
    		return z;
    }
    
    public void getFrame(String[] verb){
    	//String vibhakti-verb;
    	ReadXMLFile xm = new ReadXMLFile();
    	xm.main1(verb);
    	
    }
    public String[] findNouns(String[] tgs1, String[] ms , String[] nns, String[] vbkts){
    		System.out.println("HI ITS A NOUN SECTION");
    		String[] nbs = ms;
    		int itm = 0;
    		String ind = "NULL";
    		int count = 0;
    		int nt = -1 ;
    		String[] nntg = new String[3];
    		String[] y = new String[16];
    		String[][][] vibhakti = new String[noWords/2][noWords/2][2];
    		System.out.println("***tgs1["+0+"]="+tgs1[0]+"  nns["+1+"]="+nns[1]+" nowords/2 = "+(noWords/2));
    		for(int i = 0; i < noWords/2 ; i++){
    			for(int j=0;j<3;j++){
	    			if(tgs1[i].equals(nns[j])){  
	    				System.out.println("nt="+nt+"   nns["+j+"]="+nns[j]);	
	    				/*if(nt >= 3)
	    					break;
	    				nntg[nt] = nns[j];
    					System.out.println("nounTags["+nt+"]="+nntg[nt]);*/
    					//for(o = 0; o < 1 ; o++){
	    				//	if(ms[i] != optkrks[o]){
	    						//nbs[i] = ms[i];
	    						System.out.println("tgs1["+i+"]="+ tgs1[i]+"   nns["+j+"]="+nns[j]+"   Nouns["+i+"]="+ms[i]);
	    						nbs[itm] = ms[i];
	    						vibhakti[count][2*count][0]=ms[i];
	    						for(int l = 0 ; l < vbkts.length ; l++){
	    							if(vbkts[l].equals(ms[i+1])){
	    								vibhakti[count][2*count][1] = ms[i+1];
	    								System.out.println("vibhakti["+count+"]["+i+"]["+1+"]="+vibhakti[count][i][1]);
	    								break;
	    							}
	    							else{
	    								vibhakti[count][i][1] = "null";
//	    								break;
	    							}
	    						}
	    						
    							System.out.println("Nouns["+i+"]="+nbs[i]);	
		    					y[count] = nbs[i];
    					//char ch1 = nbs[i].charAt(3);
					//System.out.println("Character at 2 index is: "+ch1);
					//String l = Character.toString(ch1);
					//try{
					//	displayString(nbs[i], filename);
					//}catch (IOException ie){ };
					//fout.close();
    							count++;
    							itm++;
    					//	}
    					//}
    				//	ind = nbs[i];
	    			}
    				else{

	    			}
	    		}
	    		nt++;
    		}
    		try{
    			System.out.println("DISPLAY SECTION");
			displayString(nbs, count, filename, vbkts);
			displayString1(filename,vibhakti,count);
//			Actualvibhakti  = vibhakti;
		}catch (IOException ie){ };
		Actualvibhakti = vibhakti;
    		//y.length = count;
    		String[] z = new String[count];
    		String b = "null";
    		int m = 0;
    			for(int l = 0 ; l < 10 ; l++){
    				System.out.println("y["+l+"]="+y[l]+"karana[1]="+karana[1]);
    				if(b.equals(y[l])) {
    					break;
	    			}
	    			else if(m < count && y[l] != karana[1]){
	    				System.out.println("y["+l+"]="+y[l]+" m="+m+" Count="+count);
	    				z[m] = y[l];	
				
	    			}
	    			
	 			//System.out.println("z["+m+"]="+z[m]);
	    			m++; 
    			}
    			
// 		nounTags = nntg;
		nounTags = nns;
    		return z;
    }
    
    public String[] getNounTag(){
    	return nounTags;
    }
     public int[][][] findEdges(String[] v, String[] n){
    	System.out.println("no of nouns = "+n.length+" no of verbs = "+v.length);
    	int[][][] egs = new int[v.length][n.length][8];
    	int karkas = 6 ;   // NO OF KARKAS 1. KARTA 2. KARNA 3. KARMA   4 SAMPRADAN    5 APADAN    6. SAMBANDH  7. K2P (GOAL) 
    	for(int i = 0 ; i < v.length ; i++){
    		for(int k = 0 ; k < n.length ; k++) 
    		{
	    		for(int j = 0 ; j < karkas ; j++){
	    		   try{
	    			if(n[k].equals("null")){
	    				 
	    			}
	    			else{
	    				System.out.println("n["+k+"]="+n[k]);
	    				/*if(j == 2)
		    				egs[i][k][j] = 0; 
		    			else*/
		    				egs[i][k][j] = 1;
	    			}
	    		     }catch(NullPointerException nullPointer){
		    		System.out.println("***egs["+i+"]["+k+"]["+j+"]="+egs[i][k][j]);	
	 		/*	if(j == 2)
	    			 	egs[i][k][j] = 1;
	    			else
 		    			 egs[i][k][j] = 0;*/
	    		     
	    		      }
    			}
    		}
    		
    	}
    	System.out.println("CONSTRAINT GRAPH :");
    	System.out.println();
    	for(int i = 0 ; i < v.length ; i++){
    		for(int k = 0 ; k < n.length ; k++) // For karkas
    		{
	    		for(int j = 0 ; j < karkas ; j++){
	    			System.out.print("    Edges["+i+"]["+k+"]["+j+"]="+egs[i][k][j]);
	    		}
	    		System.out.println();
	    	}
	}
	nKarkas = karkas;
	System.out.println("***nKarkas="+nKarkas);
    	return egs;
    
    }
    
    public int[][][] findsubGraphs3(int[][][] eg1, int krks1, int rows1, int ver, int nsg){
       	int ns = 0;
       	//ver = nsg;
    	System.out.println("nsg = "+nsg+" rows1="+rows1+" krks1="+krks1);
    	int[][][] eg2 = new int[ver][rows1][krks1]; // ver stands for verb index
    	int[][][] teg1  = new int[nsg][rows1][krks1];
    	eg2 = eg1;
    	System.out.println("nsg="+nsg+"  ver="+ver);
    	System.out.println("Hi Its findsubGraph3 method:  ");
    	for(int i = 0 ; i < rows1 ; i++){
    		for(int j = 0 ; j < krks1 ; j++){
    			System.out.print("\t"+eg1[ver][i][j]);
    		}
		System.out.println();    		
    	}
    	System.out.println();
    	int value = nsg;
	int p;

    	for( p = nsg ; p < rows1 ; p++){
    		if(p != 0)
	    		value = (int )(Math.random() * (krks1) + 1);
    		for(int k = value ; k < krks1 ; k++){
    			System.out.println("k="+k+" eg1[0]["+p+"]["+k+"]="+eg1[0][p][k]);
    			if(eg1[0][p][k] == 1){
    				for(int q = 0 ; q < rows1 ; q++){
    					System.out.println("ver = "+ver+" p="+p+" q="+q+" k="+k);
    					if(p == q){
	    					for(int l = 0 ; l < krks1 ; l++){
	    						System.out.println("l= "+l+" ver="+ver+" p="+p);
    							if(k != l)
			    					eg2[ver][p][l] = 0;

			    				//System.out.println("****eg2["+ver+"]["+q+"]["+l+"]="+eg2[ver][q][l]); 
			    			}
		    			}
		    			
	    			}
	    			for(int m = 0; m < krks1 ; m++){
	    				if(k == m){
	    					for(int l = 0 ; l < rows1 ; l++){
	    						if(l != p)
		    						eg2[ver][l][k] = 0;
	    						System.out.println("@@@@eg2["+ver+"]["+l+"]["+k+"]="+eg2[ver][l][k]); 
	    					}
	    				}
	    					
	    			}
    				
    			}
    			System.out.println();
    		}
    	}
    	//ns++;
    	//}
    	int count = 0;
    	int value1 = 0;
    	for(int i1 = 0 ; i1 < rows1 ; i1++){
    		for(int j1 = 0 ; j1 < krks1 ; j1++){
    			System.out.print("\t"+eg2[ver][i1][j1]);
    			if(eg2[ver][i1][j1] == 1 ){
    				count++;
	    		}
    		}
		System.out.println();    		
    	}
    	System.out.println();    
    	System.out.println();    
    	int value2 = 0;
    	int value3 = 0;
    	int temp = 0;
	temp = count - rows1;
    	
    	System.out.println("count = "+count);
    	while(temp != 0){
    		System.out.println("Temp = "+temp);
    		for(int a = 0 ; a < rows1 ; a++){
    			for(int b = 0 ; b < krks1 ; b++){
    				if(eg2[ver][a][b] == 1) {
    					eg2[ver][a][b] = 0;

    				}
    				break;
    			}

    		}
    		temp--;
	  
	    	
	}

    	
    	System.out.println("Final Subgraph :");    
    	for(int i3 = 0 ; i3 < rows1 ; i3++){
		for(int j3 = 0 ; j3 < krks1 ; j3++){
			System.out.print("\t eg2["+ver+"]["+i3+"]["+j3+"]= "+eg2[ver][i3][j3]);
    		}
		System.out.println();
    	}

 
    	return eg2;
    }
    
    public int[][][] findsubGraphs(int[][][] eg, int krks, int rows, int ver, int indkrn){
    	int temp = 0;
    	int temp1 = 0;
    	int r = 0;
    	int c  = 0;
    	int nsgr = 8 ;
    	int maxedges;
    	System.out.println("indexkarana = "+ indkrn);
    	if(indkrn != 0){            // DOES KARANA PRESENT WHICH IS OPTIONAL ?
	    	maxedges = 1;
	}
	else{
		maxedges = 0;
	//	krks = krks - 1;
		System.out.println("krks = "+krks);
	}
	    	
	
    	int[][] ary = new int[rows][krks];
    	System.out.println("sub graphs ="+nsgr+"no of rows = "+rows+"no of cols = "+krks+" verb= "+ver+"nsgr="+nsgr);
    	int[][][] sg = new int[nsgr][rows][krks];
    	int[][][] subtract = new int[nsgr][rows][krks]; 
    	for(int l = 0 ; l < rows ; l++){
    		for(int m = 0 ; m < krks ; m++){
    			sg[0][l][m] = eg[ver][l][m];
    			System.out.print("   eg["+ver+"]["+l+"]["+m+"]="+eg[ver][l][m]);
    		}
    		System.out.println();
    	}
   // 	int i = 0;    	
   // 	int j =0;
   	for(int i =0;i<rows;i++){
    		for(int j=0;j<krks;j++){
    			do{
    				if(sg[0][i][j] == 1){
    					System.out.println("-sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
    					        for(int n = 0 ; n < j ; n++){
    							if(sg[0][i][n] == 1){
    								
    								System.out.println("sg["+0+"]["+i+"]["+n+"]="+sg[0][i][n]);
    								temp = 1;
    								break;
    								
    							}
    							else{
	    							System.out.println("*sg["+0+"]["+i+"]["+n+"]="+sg[0][i][n]);
    								temp = 0;
    							}
    						}
    						System.out.println("new0sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]+" temp="+temp);
    						
    						for(int m = 0 ; m < i ; m++){
    							if(sg[0][m][j] == 1){
    								System.out.println("2sg["+0+"]["+m+"]["+j+"]="+sg[0][m][j]);
    								temp1 = 1;
    								break;
    							}
    							else{
	    							System.out.println("*2sg["+0+"]["+m+"]["+j+"]="+sg[0][m][j]);
    								temp1 = 0;
    							}
    						}
    					//}
    					System.out.println("0sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]+" temp1="+temp1);
    					
    				}
    				else{ 
    					System.out.println("00sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
    				}
    				if(temp == 1 || temp1 == 1){
    					sg[0][i][j] = 0;
    					System.out.println("000sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
    				}
    				else{
    					sg[0][i][j] = eg[ver][i][j];
    					maxedges++;	
    				}
    			}
    			while( maxedges < (krks-1));
    			
    		
    		}
    	} 
    		
    	
    	for(int l = 0 ; l < rows ; l++){
    		for(int m = 0 ; m < krks ; m++){
    			System.out.print("   sg["+0+"]["+l+"]["+m+"]="+sg[0][l][m]);
    			
    		}
    		System.out.println();
    	}
    	
  	if(indkrn != 0)
	    	maxedges = 1;
	else
		maxedges = 0;    	
    	for(int l = 0 ; l < rows ; l++){
    		for(int m = 0 ; m < krks ; m++){
    			subtract[1][l][m] = eg[ver][l][m] - sg[0][l][m];
    			
    			System.out.print("   subtract["+1+"]["+l+"]["+m+"]="+subtract[1][l][m]);
    		}
    		System.out.println();
    		System.out.println();
    	}
    	
    	
    	do{
    	
    		for(r = 0 ; r < rows ; r++){
    			for(c=0;c<krks;c++){
  	  			if (temp == 0 ){
		       			sg[1][r][c] = subtract[1][r][c];
	       				System.out.println("**sg[1]["+r+"]["+c+"]="+sg[1][r][c]+" Maxedges="+maxedges);
	       				temp = 1;
		       		}
		       		else {
		       			if(r != (r-1) && r != 0)
		       				sg[1][r][c] = subtract[1][r][c];
	       			}
	    			for(int s=0;s<=r;s++){
    					do{
    						if(sg[1][s][c] == 0){
    							System.out.println("111sg[0]["+s+"]["+c+"]="+sg[0][s][c]);
      							temp = 0;
      						}
	      					else{
	      						
      							temp = 1;
      							System.out.println(" s = "+s+"c="+c);
      							break;
      						}
	      				}
      					while(temp == 1);
    				}
       			
	       			if(sg[1][r][c] == 1){//{ && maxedges < krks){
	       				System.out.println("maxedges="+maxedges);
       					maxedges++;
       				}
       			
    				System.out.println("sg[1]["+r+"]["+c+"]="+sg[1][r][c]+" Maxedges="+maxedges+"temp="+temp);
	    	
 				System.out.println("maxedges = "+maxedges+"  r="+r+"  c="+c+" krks= "+krks);
 			}
 		   }
    				
	    	} while(maxedges < (krks-1));
 
    		for(int v = 0 ; v < nsgr ; v++){
    			if(indkrn != 0)
    				sg[v][indkrn][2] = 1;
    		}
    		
	    	
//	    	for(int s1 = 0 ; s1 < nsgr ; s1++){
	    		for(int u2 = 0 ; u2 < rows ; u2++){
    				for(int v2 = 0 ;v2 < krks ; v2++){
    					if( sg[0][0][0] == 1 && rows > 2){
    						sg[2][0][0] = sg[0][0][0];
    						sg[2][1][2] = 1;//sg[0][1][1];
    						sg[2][2][1] = 1;//sg[0][2][3];
    						
    					}	
    				
    				}
	    		}
	    		for(int u2 = 0 ; u2 < rows ; u2++){
    				for(int v2 = 0 ;v2 < krks ; v2++){
    					if( sg[0][0][0] == 1 && rows > 2){
    						sg[3][0][0] = sg[0][0][0];
    						sg[3][1][3] = sg[0][1][1];
    						sg[3][2][1] = sg[0][2][2];
    						
    					}
    					    				
    				}
	    		}
	    		
	    		//for(int u2 = 0 ; u2 < rows ; u2++){
    			//	for(int v2 = 0 ;v2 < krks ; v2++){
    					if( sg[0][0][0] == 1 && rows >2 ){
    						sg[5][0][0] = 1;
    						sg[5][1][4] = 1 ;
    						sg[5][2][1] = 1;
    						
    					}
    					else if(sg[0][0][0] == 1 && rows == 2){
    						sg[5][0][0] = 1;
    						sg[5][1][4] = 1;
    					} else if(sg[0][0][0] == 1 && rows == 1){
	    					sg[5][0][0] = 1;
    					}
    					    				
    			//	}
	    	//	}
	    		
	    		
	    	//	for(int u2 = 0 ; u2 < rows ; u2++){
    		//		for(int v2 = 0 ;v2 < krks ; v2++){
    					if( sg[0][0][0] == 1 && rows > 2){
    						sg[6][0][0] = 1;
    						sg[6][1][5] = 1 ;
    						sg[6][2][1] = 1;
    						
    					}
    					else if( sg[0][0][0] == 1 && rows == 2){
    						sg[6][0][0] = 1;
    						sg[6][1][5] = 1;
    					}
    					else if( sg[0][0][0] == 1 && rows == 1){
    					
    					}
    					
    					if( sg[0][0][0] == 1 && rows == 2){
    						sg[7][0][0] = 1;
    						sg[7][1][3] = 1 ;
    						//sg[7][2][1] = 1;
    						
    					}
    					else if(sg[0][0][0] == 1 && rows == 1){ }
    					
    					
    					
    					    				
    		//		}
	    	//	}
	    		
	    		if(rows == 1){
	    			sg[4][0][0] = 0;
	    			sg[4][0][3] = 1;
	    			sg[3][0][1] = 1;
	    		}
	    		/*for(int u3 = 0 ; u3 < rows ; u3++){
    				for(int v3 = 0 ;v3 < krks ; v3++){
    					if( rows > 2){
    						sg[3][0][1] = 1;
    						sg[3][1][1] = 1;
    						sg[3][2][1] = 0;
    						
    					}	
    				
    				}
	    		}*/
	//    	}
    		int[] degree =  new int[16];
    		for(int s1 = 0 ; s1 < nsgr ; s1++){
	    		for(int u1 = 0 ; u1 < rows ; u1++){
    				for(int v1 = 0 ;v1 < krks ; v1++){
    					if( sg[s1][u1][v1] == 1){
    						degree[s1]++;
    						System.out.println("Degree of the subgraph "+s1+" is "+degree[s1] );
    					}	
    				
    				}
	    		}
	    		Deg[s1] =degree[s1];
	    	}
    		for(int u = 0 ; u < nsgr ; u++){
	   	   	for(int l = 0 ; l < rows ; l++){
   		 		for(int m = 0 ; m < krks ; m++){
    					System.out.print("   sg["+u+"]["+l+"]["+m+"]="+sg[u][l][m]);
	    			}
    				System.out.println();
    			}
    			System.out.println();
    			System.out.println();
	    	}
	    	nKarkas = krks;
	    	return sg;
    
    }
    public int[][][] NewSubgraph(int[][][] sg, int nons, int krks, int flgk4){
    	int[][][] sg1 = new int[1][nons][krks];
    	sg1 = sg;
    	int i = 1;
//    	for(int i = 1 ; i < nons ; i++){   // Karta (K1) is excluded which can not be sampradan
	while(i<3){
    		for(int j = 0 ; j < krks ; j++){
    	//		sg1[0][i][j] = 0;
    			if(flgk4 == 1 && sg[0][i][j] == 1){
    				sg1[0][i][krks-1] = sg[0][i][j];
//    				sg1[0][i][j] = 
    //				sg1[0][i][j] = 0;
    				if(i == 2){
    					sg1[0][2][1] = sg[0][i][j];
  //  					sg1[0][i][j] = 0;
    				}
    		//		i = i +1;
//    				sg1[0][i+1][1] = sg[][][];
    			}
    		}
    		i = i + 1;
    		
    	}
    	return sg1;
//    	return sg1;
    
    }
    public int[][][] findfinalmatching(int[][][] subgr,int nsgr, int rows, int krks, int indkrta, int indkrn, String[] nn){
    	int[][][] fg = new int[nsgr][rows][krks];
    	int[][][] afg = new int[nsgr][rows][krks];
    	boolean flg = true ; //false;
    	int count = 0;
    	/*if(Deg[0] != rows){
    		System.out.println("Degree of Graph is not equal to No. of nouns. ");
    		flg = false;
    		//break;
	    	//maxedges = 1;
	}
	else{
		//maxedges = 0;
		//krks = krks - 1;
	}*/
    	System.out.println("indkrta="+indkrta+" 0nsgr="+nsgr+" rows="+rows+" krks="+krks);
    	for(int u = 0 ; u < nsgr ; u++){
	   	for(int l = 0 ; l < rows ; l++){
   	 		for(int m = 0 ; m < krks ; m++){
   	 			System.out.println("   subgr["+u+"]["+l+"]["+m+"]="+subgr[u][l][m]);
   	 			if(subgr[u][l][m] == 1){
   	 				if(l == indkrta){
   	 					fg = subgr;
		    				//System.out.print("   11subgr["+u+"]["+l+"]["+m+"]="+subgr[u][l][m]);
		    			}
		    			else{ }
		    				
	    			}
	    			else { }
			}
    			System.out.println();
    		}
    		System.out.println();
    		
    		//System.out.println();
	}
	//for(int u = 0 ; u < nsgr ; u++){
	int u1 = 0;
	System.out.println("nsgr="+nsgr);
	while(u1 < nsgr){	
//	for(int u1 = 0 ; u1 < nsgr ; u1++){
		System.out.println("before u1="+u1);
		if(Deg[u1] != rows){
		    	System.out.println("Degree of the graph Deg["+u1+"] is = "+Deg[u1]);
			flg = false;
		}
		else{
		    	System.out.println("True Degree of the graph Deg["+u1+"] is = "+Deg[u1]);
			flg = true;
		}
	   	for(int l1 = 0 ; l1 < rows ; l1++){
   	 		for(int m1 = 0 ; m1 < krks ; m1++){
   	 			fg[0][l1][m1] = subgr[u1][l1][m1];
   	 		}
   	 	}
   	 	System.out.println("oflag="+flg);
   	 	if(flg){
   		 	System.out.println("***oflag="+flg);
			flg = checkTAM(fg,nsgr,rows,krks);
			if(flg != true){
				//u1++;	
				//break;
			}
			else{
				count++;
			}
			System.out.println("**flag = "+flg);
			
		}
		/*	if(count > 1 && rows == 1){
			checkVerb(fg, krks);
		}*/
   	//}
	if(flg == true ){
		System.out.println("\t  KARTA(k1)   \tKARMA(k2)\t  KARANA(k3)\t  SAMPRADAN(K4) \tAPADAN(k5)   \tSAMBANDH(K6)");
	   	   	for(int l = 0 ; l < rows ; l++){
	   	   		System.out.print(nn[l]);
   		 		for(int m = 0 ; m < krks ; m++){
    					System.out.print("       fg["+0+"]["+l+"]["+m+"]="+fg[0][l][m]);
	    			}
    				System.out.println();
    			}
    			//TO DISPLAY FINAL OUTPUT IN THE OUTPUT FILE ////
    			displayString(filename,fg,rows,krks,nn);  	
    	}
    	else{
    		System.out.println("*****PARSING IS NOT POSSIBLE*****");
    	//	u1=3;
    	}
    	u1++;
    	System.out.println("after u1="+u1);
    }
	  
    			System.out.println();
    			System.out.println();
	   // 	}
	return fg;
    }
    public String[] RegionMatchesDemo(String findMe, String file) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
       String[] positions = new String[50000];  // Maximum number of tags to each words = 5
        int index = 0;
        int count = 0;
        int t = 0;
        String[] start = new String[50000];
        String[] g = new String[2];
       int fileLength = file.length();
        int findMeLength = findMe.length();
        String findMe1 = "hi";
        if(fileLength > 2)
	        findMe1 = file.substring(fileLength-2,fileLength);
	else
		g[0] = "ab";
        // System.out.println("file length = "+file.length);
         System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
               System.out.println("check");    
              // System.out.println(" g="+g);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
              System.out.println("g[0]="+g[0]);
              g[1] = findMe1;
          //   System.out.println("file["+(i+1)+"]="+file[i+1]);

            	
//              index = i+1;
  //            positions[count] = file[index];
    //          System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
      //        if(positions[count].equals("YF")){
        //      	if(i < (noWords-2))
	  //            	start[count] = file[index + 2]; 
	             // 	System.out.println("start["+count+"]="+start[count]);
	    //          	getStart(start[count], count);
              }
              else{
              	g[0] = "ab";
              }
//              count++;
              
              
           //   break;
         // }
          
           return g;
          // break;
        }
        
        public String[] RegionMatchesDemoLai(String findMe, String file) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
       String[] positions = new String[50000];  // Maximum number of tags to each words = 5
        int index = 0;
        int count = 0;
        int t = 0;
        String[] start = new String[50000];
        String[] g = new String[2];
       int fileLength = file.length();
        int findMeLength = findMe.length();
        String findMe1 = "hi";
        if(fileLength > 2)
	        findMe1 = file.substring(fileLength-3,fileLength);
	else
		g[0] = "ab";
        // System.out.println("file length = "+file.length);
         System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
               System.out.println("check");    
               System.out.println(" filelength="+fileLength);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
              System.out.println("g[0]="+g[0]);
              g[1] = findMe1;
          //   System.out.println("file["+(i+1)+"]="+file[i+1]);

            	
//              index = i+1;
  //            positions[count] = file[index];
    //          System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
      //        if(positions[count].equals("YF")){
        //      	if(i < (noWords-2))
	  //            	start[count] = file[index + 2]; 
	             // 	System.out.println("start["+count+"]="+start[count]);
	    //          	getStart(start[count], count);
              }
              else{
              	g[0] = "ab";
              }
//              count++;
              
              
           //   break;
         // }
          
           return g;
          // break;
        }
        
  // }
        
        // WORD GROUPS (CHUNKS)
      
  
   public void getParsedsentence(String[][] np, String[] vp, String[] Nphrase, int nnl){
   	System.out.println("SIMPLE CONCEPTUAL GRAPH : ");
   	int[][] SCG;
   	int[][] subSCG;
	int[][][] completeSCG;
   	String[] n = new String[5*3]; //total 5 noun phrases each with length 3 words
   	int karkas = 2; // what and whose
   	int count = 0;
   	
   	
   	for(int i = 0 ; i < np.length ; i++){
   		for(int j = 0 ; j < np[i].length ; j++){
   			System.out.println("np["+i+"]["+j+"]="+np[i][j]);
   			try{
	   			if(np[i][j].equals(null) || np[i][j].equals("n")){
   					System.out.println("hi nnl = "+nnl);
	   				//continue;	
   				}
   				else{
   					n[count] = np[i][j];
   					System.out.println("**n["+count+"]="+n[count]+"  vp.length ="+vp.length +" vp[0]="+vp[0]);
   					if(count < (n.length-1))
		   				count++;
   				
   				}
   			}catch(NullPointerException nullPointer){}
   		}
   	}
   	for(int k = 0 ; k < vp.length ; k++){
   		System.out.println("hi");
   		System.out.println("vp["+k+"]="+vp[k]);
   	}
   	SCG = findEdgesSCG(vp,n,count, karkas, nnl);
   	subSCG = findsubSCG1(SCG, vp, n, count, karkas, nnl);
   	completeSCG = findComplete(subSCG, count, karkas, Nphrase, nnl);                                                                         
   	
   	//return completeSCG;
   }    
   
   public int[][][] findComplete(final int[][] sg, int nounlength, int karkas, String[] noun, int nnl1){
   	nounlength = nnl1 ;
   	int[][][] finalSCG = new int[karkas][karkas*nounlength][karkas*nounlength];
   	int[][] temp ;
   	//final sg;
   //	int[][] temp1 ;
	//temp new int[karkas*nounlength][karkas*nounlength];
   	int[] count = new int[karkas];
   	int count1 = 0;
   	int count2 = 0;
   	int[] flag = new int[2];
   	//temp[0] = sg;
   	//temp[1] = sg;
   	//finalSCG[0] = sg;
   	//finalSCG[1] = sg;
   	temp = sg;
   //	temp1 = sg;
   	
   	for(int i = 0 ; i < karkas*nounlength ; i++){
   		for(int j = 0 ; j < karkas*nounlength ; j++){
   			if(i%2 != 0){   // Checking for multiple karka whose. IN THE FINAL GRAPH ALL THE KARKAS TO BE ASSIGNED EXACTLY ONCE
   				System.out.println("Count number of non zero odd columns for whose");
   				if(temp[j][i] == 1){
   					flag[0] = 1;
   					break;
   					//count[i]++;
   					
   				}
   				System.out.println("***Count number of non zero columns="+flag[0]);
   			}
   			else{
   				//flag = 0;
   				System.out.println("Count number of non zero even columns for what");
   				if(temp[j][i] == 1){
   					flag[1] = 1;
   					break;
   					//count[i]++;
   					
   				}
   				System.out.println("&&&Count number of non zero columns="+flag[1]);	
   			}
   		}
   
   	}
   	for(int i1 = 0 ; i1 < 2 ; i1++){
   		int count3 = 0;
   		int token = 0;
   		if(flag[i1] == 1){
   			for(int i = 0 ; i < (karkas*nounlength) ; i++){
   				System.out.println("i="+i);
//   				finalSCG[]
		    //		count1=0;
		    		// token = 0;
		    		if(i1 == 0){
			    		if(i%2 == 0 ){
			    			for(int m = 0 ; m < (karkas*nounlength) ; m++){
				    			for(int n = 0 ; n < (karkas*nounlength) ; n++){
				    				System.out.println("temp["+m+"]["+n+"]="+temp[m][n]);
				    				//finalSCG[0] = temp;
				    				//if(i == 0)
					    			//	finalSCG[0][m][i] = 0;
				    				/*if(i==0){
				    					if(n == i){
					    					finalSCG[0][m][n] = 0;
					    					//finalSCG[0] = temp;
					    				}
					    				
				    				}*/
				    				if(i==2){
				    					if(n == i){
					    					finalSCG[1][m][n] = 0;
					    				}
				    				}
					    			
				    			}
				    		}
				    		
				    		
	    				}
    					else{
    						//if(count3 == 1)
	    					//	i=0;
    						//break;
    					//break;
    					}
    				}
    				else{
    					if(i%2 != 0 ){
			    			for(int k = 0 ; k < (karkas*nounlength) ; k++){
					    		finalSCG[count3][k][i] = 0;
					    		System.out.print("	"+finalSCG[count3][k][i]);
					    		System.out.println("count3="+count3+"   i = "+i);
			
		    				}
	    					count3++;
    						
	    				}
    					else{
    						break;
    					//break;
    					}
    				}
	    		}
    			//count3++;
   		}
   		//count3++;
   	}
   	
   	System.out.println();
   	for(int i = 0 ; i < karkas*nounlength ; i++){
   		for(int j = 0 ; j < karkas*nounlength ; j++){
   			System.out.print("	"+sg[i][j]);
   			if(j != 0){
   				finalSCG[0][i][j] = sg[i][j];
   			}
   			if(j != 2){
   				finalSCG[1][i][j] = sg[i][j];
   			}
   			//if(j == 0)
   			//	finalSCG[0][i][j] = 0;
   			//finalSCG[0][i][j] = temp[i][j];
   		}
   		   	System.out.println();
   	}
  	System.out.println();
  	
  	int parse = -1;
  	int[] degree = new int[karkas*nounlength];
  	for(int p = 0 ; p < 2 ; p++){
	  	for(int i = 0 ; i < nounlength ; i++){
   			for(int j = 0 ; j < karkas*nounlength ; j++){
				if(finalSCG[p][i][j] != 0){
					degree[i]++;
				}
					
   			}
		}
		for(int l = 0 ; l < nounlength ; l++){
			if(degree[l] != 0){
				parse = p;
			}
			else{
				parse = -1;
				break;
			}
		}
	}
   	System.out.println("FINAL PARSED GRAPH: ");
   	//finalSCG[1] = sg;
   	System.out.println("parse="+parse);
   	for(int i = 0 ; i < karkas*nounlength ; i++){
   		for(int j = 0 ; j < karkas*nounlength ; j++){
   			System.out.print("	"+finalSCG[parse][i][j]);
   		}
   		System.out.println();
   	}
   	System.out.println();
   	System.out.println();
   	//System.out.println("noun["+0+"]["+0+"]="+noun[0][0]+"noun["+1+"]["+1+"]="+noun[0][1]);
   	displayStringSCG(filename, finalSCG, nounlength, karkas, noun , parse );
   	return finalSCG;
   	
   }
   public int[][] findEdgesSCG(String[] v, String[] n, int nounlength, int karkas, int nnlen){
   	//nnlen++;  // the noun index started from 0 
    	System.out.println("no of noun phrase = "+nnlen+" no of verbs = "+v.length + "  karkas="+karkas);
    	int counter = 0;
    	int j = 0;
    //	int karkas = 2 ;   // NO OF KARKAS 1. WHOSE 2. WHAT  
    	int[][] incedence = new int[karkas*nnlen][karkas*nnlen];
    	int[][][] incedence1 = new int[karkas*nnlen][karkas*nnlen][2];
    	int[] vertices = new int[karkas+nnlen];
    	int[][] edges = new int[karkas*nnlen][karkas*nnlen];   // For complete bipartite graph 
    	
    	for(int i = 0 ; i < nnlen ; ){
    		while(j < karkas){
    			edges[i][j] = 1;
    			System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
    			edges[i][j+nnlen] = 1;
    			System.out.println("edges["+i+"]["+(j+nnlen)+"]="+edges[i][j+nnlen]);
    			j++;
    			i++;
    		}
    	}
    	j=0;
    	for(int i = nnlen ; i <(nnlen + karkas) ;){
    		while(j < karkas){
    			edges[i][j] = 1;
    			if((j+nnlen+1) < (nnlen+karkas)){
    				edges[i][j+nnlen+1]= 1;
    				System.out.println("edges["+i+"]["+(j+nnlen+1)+"]="+edges[i][j+nnlen+1]);
    			}
    			else{
    				edges[i][j+nnlen-1] = 1;
    				System.out.println("edges["+i+"]["+(j+nnlen-1)+"]="+edges[i][j+nnlen-1]);
    			}
    			j++;
    			i++;
    		}
    	}
    	
    	
    	/*for(int i = 0 ; i < (nnlen*karkas) ;  ){
    		//k1++;
    		for(int j = 0 ; j <  karkas ; j++ ){
    			System.out.println("j="+j+"  i="+i);
    			edges[i][j]=1;
    			System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
    			edges[i][j+nnlen] = 1;
    			System.out.println("edges["+i+"]["+(j+nnlen)+"]="+edges[i][j+nnlen]);
    			i++;
    			edges[i][j]=1;
    			System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
    			if((j+nnlen+1) < (nnlen*karkas )){
    				System.out.println("hi");
	    			edges[i][j+nnlen+1] = 1;
	    			System.out.println("edges["+i+"]["+(j+nnlen+1)+"]="+edges[i][j+nnlen+1]);
	    		}
	    		else{
	    			edges[i][j+nnlen-1] = 1;
	    		}
    			i++;
    			//for(int k = j ; k <  (nnlen * karkas) ; k++){
    			/*if(i%2 != 0){
    				edges[i][j - nnlen-1]=1;
    				edges[i][j] = 1;
    				System.out.println("edges["+i+"]["+(j-nnlen-1)+"]="+edges[i][j-nnlen-1]);
    				System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
    			}
    			else{
    				edges[i][j - nnlen] = 1;
    				edges[i][j] = 1;
    				System.out.println("edges["+i+"]["+(j-nnlen)+"]="+edges[i][j-nnlen]);
    				System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
    			}
    				
	    			
	    			
    			
    			//i++;
    			//if(counter < (nnlen-1))
			//	counter++;
    			
    		}
    		//if(counter < (nnlen-1))
		//		counter++;
    	}*/
    	
    	
	/*for(int i = 0 ; i < nnlen ; i++){
		for(int j = nnlen ; j < (nnlen * karkas) ; j++){
			edges[counter][i]=1;
			System.out.println("edges["+counter+"]["+i+"]="+edges[counter][i]);
			edges[counter][j]=1;
			System.out.println("**edges["+counter+"]["+j+"]="+edges[counter][j]);
			if(counter < (nnlen-1))
				counter++;
		}
		//counter++;
    	}*/
    	
    	System.out.println("SIMPLE CONCEPTUAL GRAPH :");
    	System.out.println();
    	
    	for(int i = 0 ; i < (nnlen * karkas) ; i++){
	    	for(j = 0 ; j < (nnlen * karkas) ; j++){
	    		System.out.print("    "+edges[i][j]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
	    		//for(int k = 0 ; k < karkas ; k++)
//	    		if(j == 0){}
	    		if(j == 2){
	    			//k = 3;
	    			for(int k = 0 ; k < (nnlen*karkas) ; k++){
		    			incedence[k][1] = edges[k][j];
		    		}
	    		}
	    		if(j == 3){
	    			//k = 3;
	    			for(int k = 0 ; k < (nnlen*karkas) ; k++){
		    			incedence[k][2] = edges[k][j];
		    		}
	    		}
	    		if (j == 1){
	    			for(int k = 0 ; k < (nnlen*karkas) ; k++){
		    			incedence[k][3] = edges[k][j];
		    		}
	    		}
	    		
	    		//if(j != 1 && j != 3){
		    		incedence[i][0] = edges[i][0];
		    	//}
		    	//}
	    	}
		System.out.println();    		
	}
	
	System.out.println("SIMPLE CONCEPTUAL GRAPH A:");
	for(int i = 0 ; i < (nnlen * karkas) ; i++){
	    	for(j = 0 ; j < (nnlen * karkas) ; j++){
	    		
	    		//for(int k = 0 ; k < karkas ; k++){
		    		System.out.print("    "+incedence[i][j]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
		    		//for(int k = 0 ; k < karkas ; k++){
		    		if(j%2 == 0){
			    		incedence1[i][j][0] = incedence[i][j];
			    	}
			    	else{
			    		incedence1[i][j][1] = incedence[i][j];
			    	}
	//	    		incedence[j][i][k] = edges[i][j];
		    	//}
	    	}
		System.out.println();    		
	}
	
	System.out.println();   
	System.out.println();   
	
	for(int i = 0 ; i < counter ; i++){
	    	for( j = 0 ; j < (nnlen * karkas) ; j++){
	    		for(int k = 0 ; k < karkas ; k++){
		    		System.out.print("    "+incedence1[i][j][k]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
		    	}
		    	
		}
		System.out.println();   
	}
		    		//for(int k = 0 ; k < karkas ; k++){
	/*for(int i = 0 ; i < (v.length+n.length) ; i++){

		for(int k = 0 ; k < (v.lengt * n.length) ; k++){
			for(int j = 0 ; j < karkas ; j++){
				if(egs[][][])
					incidentMatrix[i][k+j] = 1;
					incidentMatrix[i+2][k+j] = 1;
					
				
				
			}
			
		}
	
	}*/
	System.out.println();
	nKarkas = karkas;
	System.out.println("***nKarkas="+nKarkas);
    	return incedence;
    
    }
    
    public int[][] findsubSCG1(int[][] scg, String[] v, String[] n, int nlength, int karkas, int nnl){
    	nlength = nnl;
    //	int karkas = 2 ; // whose and what
    	int[][] subKasko = new int[karkas*nlength][karkas*nlength];
    	int[][] subKe = new int[karkas*nlength][karkas*nlength];
    	int[][][] subGraph = new int[2][karkas*nlength][karkas*nlength]; // Assuming 2 columns in each subgraph for 2 karkas (whose and what)
    	int[][] incedent = new int[karkas*nlength][karkas*nlength];
    	int count = 0 ;
    	int temp = 0 ;
    	int count1 = 0;
	subKasko = checkKasko1(scg, karkas, n, nlength);   // checking grammar rules of whose (KASKO)
    	subKe = checkKe1(scg, karkas, n,nlength);	// checking grammar rules of what (KE)
    	System.out.println("MAIN SUB GRAPH OF CONCEPTURAL GRAPH : ");
    	
    	
    	for(int i = 0 ; i < (karkas*nlength) ; i++){
    		for(int j = 0 ; j < (karkas*nlength) ; j++){
    			
    			if(i%2 ==0)
	    			incedent[i][j] = subKasko[i][j];
	    		else
	    			incedent[i][j] =subKe[i][j];
    		}
    	}
    	
    	for(int i = 0 ; i < (karkas*nlength) ; i++){
    		count1=0;
       		for(int j = 0 ; j < (karkas*nlength) ; j++){
       			if(incedent[j][i] == 1){
       				count1++;
       			}
    		}
    		if(count1 == 1){
    			for(int k = 0 ; k < (karkas*nlength) ; k++){
	    			incedent[k][i] = 0;
	    			System.out.println("k="+k);
	    		}
    		}
    	}
    	
    	for(int i = 0 ; i < (karkas*nlength) ; i++){
    		for(int j = 0 ; j < (karkas*nlength) ; j++){
    			System.out.print("	"+incedent[i][j]);
    		}
    		System.out.println();
    	}
    	return incedent;
    }
    public int[][][] findsubSCG(int[][] scg, String[] v, String[] n, int nlength){
    	int[][] subKasko = new int[v.length][n.length];
    	int[][] subKe = new int[v.length][n.length];
    	int karkas = 2 ; // whose and what
    	int[][][] subGraph = new int[2][n.length][karkas]; // Assuming 2 columns in each subgraph for 2 karkas (whose and what)
    	
    	int count = 0 ;
    	int temp = 0 ;
	subKasko = checkKasko1(scg, karkas, n, nlength);   // checking grammar rules of whose (KASKO)
    	subKe = checkKe1(scg, karkas, n,nlength);	// checking grammar rules of what (KE)
    	for(int i = 0 ; i < 1 ; i++){
    		//count = 0 ;
    		while(temp < karkas){
    			count = 0;
	    		for(int j = 0 ; j < karkas; j++){
	    			for(int k = 0 ; k < n.length ; k++){
	    				if(subKasko[0][k] == 1 && temp != 1){   // subkasko and subgraph are transpose  because 0 indicates the first karka i.e whose
	    					subGraph[count][k][0] = 1;
	    					//temp++;
	    					System.out.println("subgraph["+count+"]["+k+"]["+0+"]="+subGraph[count][k][0]+"  temp="+temp);
	    					temp++;
	    					for(int l = 0 ; l < n.length ; l++){
	    						if(l != k ){
	    							subGraph[count][l][0] = 0;
	    						}
	    					}
	    				}
    			//}
    			//for(int k = 0 ; k < n.length ; k++){
    					if(subKe[1][k]  == 1 && temp <= karkas){ // 1 indicates second karka i.e. what
    						subGraph[count][k][1] = 0;
    					//	temp++;
	    					System.out.println("subgraph["+count+"]["+k+"]["+1+"]="+subGraph[count][k][1]+"   ketemp="+temp);
    						//temp++;
    						for(int l = 0 ; l < n.length ; l++){
    							if(l != k ){
    								subGraph[count][l][1] = 1;
    								
    								System.out.println("subgraph["+count+"]["+k+"]["+1+"]="+subGraph[count][k][1]+"   ketemp="+temp);
    								temp++;
	    						}
    						}
    					
    					}
    				//	count++;
    				}
    				count++;
    			}//count++;
    		}
    		//count++;
    	}
    	for(int i = 0 ; i < count ; i++){
    		for(int j = 0 ; j < n.length; j++){
    			for(int k = 0 ; k < karkas ; k++){
    				System.out.println("subGraph["+i+"]["+j+"]["+k+"]="+subGraph[i][j][k]);
    			}
    		}
    		System.out.println();
    	}
    	return subGraph;
    }
  
  public int[][] checkKasko1(int[][] scg1, int krk, String[] n1, int nounlength){
	System.out.println("It's checkKasko1");
    	int[][] nntg;
    	nntg = scg1;
    	int count = 0;
    	//int flag = 0;
       	int[][] subkasko = new int[1][nounlength];
  	for(int i = 0 ; i < (krk*nounlength) ; i++){
    		for(int j = 0 ; j < (krk*nounlength) ; j++){
    			if(i%2 == 0 ){     // even edges for kasko i.e. whose
    				System.out.println("scg1["+j+"]["+i+"]="+scg1[j][i]+" n1.length="+nounlength+"  v1.length="+krk);
    				if(scg1[j][i] == 1 ){
    					System.out.println("hi nountag["+count+"]="+nounTags[count]);
	    				if(nounTags[count].equals("NN") || nounTags[count].equals("NNP")){
    						subkasko[0][count] = 1;
    						System.out.println("subkasko["+0+"]["+count+"]="+subkasko[0][count]);
    						
	    				}
	    				else{
	    					nntg[j][i] = 0; 
	    				}
	    				if(count < (nounlength-1))
	    						count++;
	    				else{
	    					break;
	    				}
    				
	    			}
    					
    		//		}
	    		}
    		}
    	}
    	return nntg;
  
  }
  
  
  public int[][] checkKe1(int[][] scg1, int krk, String[] n1, int nounlength){
	System.out.println("It's checkKe1");
    	int[][] nntg;
    	nntg = scg1;
    	int count = 0;
       	int[][] subke = new int[1][nounlength];
  	for(int i = 0 ; i < (krk*nounlength) ; i++){
    		for(int j = 0 ; j < (krk*nounlength) ; j++){
    			if(i%2 != 0){     // even edges for ke i.e. what
    				System.out.println("scg1["+j+"]["+i+"]="+scg1[j][i]+" n1.length="+nounlength+"  v1.length="+krk);
    				if(scg1[j][i] == 1){
    					System.out.println("hi nountag["+count+"]="+nounTags[count]);
	    				if(nounTags[count].equals("NNP")){
    						subke[0][count] = 1;
    						System.out.println("subke["+0+"]["+count+"]="+subke[0][count]);
    						
    					}
    					else{
    						nntg[j][i] = 0;
    					}
    					if(count < (nounlength-1))
    							count++;
    					else{
    						//break;
    					}
    				
	    			}
    					
    		//		}
	    		}
    		}
    	}
    	return nntg;
  
  }
  
    public int[][] checkKasko(int[][][] scg1, String[] v1, String[] n1){
    	System.out.println("It's checkKasko");
    	String[] nntg;
    //	nntg = getNounTag();
    	int[][] subkasko = new int[1][n1.length];
    	for(int i = 0 ; i < (v1.length*n1.length) ; i++){
    		for(int j = 0 ; j < (v1.length*n1.length) ; j++){
    			System.out.println("scg1["+i+"]["+j+"]["+0+"]="+scg1[i][j][0]);
    			if(scg1[i][j][0] == 1 ){   // for first karka i.e whose
    			System.out.println("hi nountag["+j+"]="+nounTags[j]);
    				if(nounTags[j].equals("NN") || nounTags[j].equals("NNP")){
    					subkasko[0][j] = 1;
    					System.out.println("subkasko["+0+"]["+j+"]="+subkasko[0][j]);
    				}
    				
    			}
    		}
    	}
    	return subkasko;
    }
    
    public int[][] checkKe(int[][][] scg1, String[] v1, String[] n1){
    	int[][] subke = new int[2][n1.length];
    	for(int i = 0 ; i < v1.length ; i++){
    		for(int j = 0 ; j < n1.length ; j++){
    			if(scg1[i][j][1] == 1 ){   // 1 indicates first karka i.e what
    				if(nounTags[j].equals("NNP")){
    					subke[1][j] = 1;
    					System.out.println("subke["+1+"]["+j+"]="+subke[1][j]);
    				}
    				
    			}
    		}
    	}
    	return subke;
    }
    
    public void displayStringSCG(String outputName2, int[][][] fg1, int rows, int krks, String[] nn, int prs){
    try{
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName2, true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        int[] deletecol = new int[rows*krks];
	        int count = 0;
	        System.out.println("hi");
	        for(int l = 0 ; l < rows*krks ; l++){
	        	for(int m = 0 ; m < rows*krks ; m++){
	        		if(fg1[prs][m][l] != 0){
		        		System.out.println("hi ello rows ="+rows+" krks ="+krks+" count="+count+" l="+l+"  m="+m);
	        			deletecol[count] = l;
	        			count++;
	        			break;
	        			
	        		}
	        		else{
	        			//deletecol[count] = -1;
	        			//count++;
	        		}
	        		//count++;
	        		//break;
	        	}
	        }
	       // int t = 0;
	        fout.write("Hi its displayString FINAL OUTPUT");
	        fout.write("\n\t  KASKO(WHOSE)      \tKE(WHAT)     ");
	   	for(int l = 0 ; l < rows ; l++){
	   	   		fout.write("\n");
	   	   		fout.write(nn[l]);
	   	   		System.out.println("hi hello l = "+l);
   		 		//for(int m = 0 ; m < krks ; m++){
   		 		for(int t= 0 ; t < krks ; t++){
	   		 		System.out.println("hi hello l = "+l+"  del["+t+"]="+deletecol[t]);
   		 			//if((l+krks) == deletecol[t]){
   		 				System.out.println("Welcome");
   		 				if(l%2 == 0 ){
	    						fout.write("     fg1["+prs+"]["+l+"]["+0+"]="+fg1[prs][l][deletecol[t]]);
	    						//break;
	    					}
	    					else{
	    						fout.write("     fg1["+prs+"]["+l+"]["+1+"]="+fg1[prs][l][deletecol[t]]);
	    						//break;
	    					}
    				//	}
	    				//}
	    				//else{
	    					//t++;
	    				//}
	    			}
    				fout.write("\n");
    		}
    		fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
    
}
