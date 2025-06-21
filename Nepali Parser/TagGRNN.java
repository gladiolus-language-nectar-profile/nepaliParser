//PARSER FOR NEPALI LANGUAGE IS BUILT UNDER THE DST FUNDED PROJECT SR/CSRI/28/2015
// DEVELOPED BY PROF. (DR) ARCHIT YAJNIK, PRINCIPLE INVESTIGATOR
// DATE OF SUBMISSION : 05-11-2020
// TECHNIQUES : 1. AUGMENTING PATH
// 2. MAXIMUM BIPARTITE GRAPH
// 3. GENERAL REGRESSION NEURAL NETWORKS
// 4. RULE BASED INTRA-CHUNK DEPENDENCY  PARSER
// 5. INPUT: NEPALI SENTENCE
// 6. OUTPUT: LOGO.PNG (PARSED TREE)
// 7. BATCH FILE: FASTRUN2.BAT
// UTILITY FILE TO SUPPORT GRNN5.JAVA
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

public class TagGRNN
{
    public static int totaltag = 39;
    public static int maxwords = 500000;
    public static int maxobswords = 300000;
    public static double[][] transitionMatrix = new double[totaltag][totaltag];
    public static double[][] observationMatrix = new double[totaltag][300000];
    public static String[] Words = new String[maxwords];
    public static int[] confusedword ;
    public static int noWords = 0;
    public static int noTags = 0;
    public static int alaram = 0;
    public static String thread;
    public static int sentnoWords = 0;
    public static String[] sentWords = new String[maxobswords];
    public static String[] Obs = new String[maxobswords];
    public static String[] sentTotalwords = new String[2*maxobswords];
    public static String[] OriOutput = new String[300000];
    public String[] Finalstart  = new String[50000];
    public static double[][] ExtractedFeatures;
    public BufferedWriter annFile;
    public BufferedWriter tmFile;
    public static double[][] Features;
    public static int[][] Outputs;
    public static String[] TGSET;
    public static int inputNeurons;
    public static int outputNeurons;
    public static int hiddenNeurons;
    public static int numberPatterns;
    public static int numbertestPatterns;
    public static String[] testSent;
    public static String[] oritestSent;
    public static String[] trainTags;
    public static int totalWords;
    public static double[][] testFeatures;
    public static double[][] testFeatures1;
    public static double[][] defaulter;
    public static String[][] defaulterstring;
    public static double[][] trainFeatures;
    public static int leindex, laiindex;
    public static double[][] observationMatrix1;
    public static double[][] rareFreq;
    public static String[] freqpos = new String[50];
    public static int[] rareFreqpos = new int[100];
    public static int[] rareFreqTrainpos = new int[100];
    public static int frq = 0;    // Number of test  words having frequency rare
    public static String[] specialverbs;



    public int[] allunknownpositions;
    public  TagGRNN(String s, String[] f){
       // String RegionMatchesDemo = RegionMatchesDemo(s,f);
    }
   public  TagGRNN(){}
   public TagGRNN(String a, String c, String d,  String e, int s, String l) throws IOException{
   	String glyph = "\u0926\u0947\u0916\u093F";
   	String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
      // String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM"};
       TGSET = tagset;
      /* for(int i = 0 ; i < totaltag ; i++)
             //System.out.println("tagset["+i+"]="+tagset[i]);*/
       TagGRNN t = new TagGRNN();
       t.readFile(a,c,d, e, glyph, tagset);
       
       transitionMatrix = t.generateTM(Words, noWords, tagset);
       observationMatrix = t.generateOM(sentWords, sentnoWords , tagset, Words);
       testFeatures = new double[numbertestPatterns][totaltag*3];
       testFeatures1 = new double[numbertestPatterns][totaltag*3];
       defaulter = new double[numbertestPatterns][4];
       defaulterstring = new String[numbertestPatterns][2];
       trainFeatures = t.gettrainFeatures();
       //System.out.println("TRAIN FEATURES");
       //System.out.println("sentnoWords="+sentnoWords);
      // for(int i = 0 ; i < sentnoWords ; i++){
       	//	for(int j = 0 ; j < 117 ; j++){
       		//	//System.out.print("  "+trainFeatures[i][j]);
       		//}
       		//System.out.println();
       		//System.out.println();
     //  }
       OriOutput =  t.getOriginalOutput(sentTotalwords, 2*sentnoWords, tagset, trainFeatures, s);
    //   t.getTestfeature(testSent,numbertestPatterns, tagset, Words);
     //  annFile.close();
      // confusedword = new int[50]; //maximum multiple tagged words
   }
   
   public TagGRNN(String a, String b, String c, String d,  int s) throws IOException{
   	String glyph = "\u0926\u0947\u0916\u093F";
   	GRNN5 gr = new GRNN5();
   	String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
      // String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM"};
       TGSET = tagset;
       /*for(int i = 0 ; i < totaltag ; i++)
             //System.out.println("tagset["+i+"]="+tagset[i]);*/
       TagGRNN t = new TagGRNN();
       
       t.readFile1(a, b,  glyph, tagset);
       
    //   transitionMatrix = t.generateTM(Words, noWords, tagset);
      // observationMatrix = t.generateOM(sentWords, sentnoWords , tagset, Words);
       testFeatures = new double[numbertestPatterns][totaltag*3];
       testFeatures1 = new double[numbertestPatterns][totaltag*3];
       defaulter = new double[numbertestPatterns][4];
       defaulterstring = new String[numbertestPatterns][2];
      /* t.getTrainFeatures(gr);
       //trainFeatures = t.gettrainFeatures();
       //System.out.println("TRAIN FEATURES");
       //System.out.println("sentnoWords="+sentnoWords);
       for(int i = 0 ; i < sentnoWords ; i++){
       		for(int j = 0 ; j < 117 ; j++){
       			//System.out.print("  "+trainFeatures[i][j]);
       		}
       		//System.out.println();
       		//System.out.println();
       }*/
       //System.out.println("train pat");
     //  t.getTrainFeatures(gr);
       //trainFeatures = t.gettrainFeatures();
       OriOutput =  t.getOriginalOutput(sentTotalwords, 2*sentnoWords, tagset, trainFeatures, s);
      //t.getTestfeature(testSent,numbertestPatterns, tagset, Words);
      // confusedword = new int[50]; //maximum multiple tagged words
   }
   
   
   public static void main(String args[])throws IOException 
   {
       String glyph = "\u0926\u0947\u0916\u093F";
       String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
       //String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM"};
     /*  for(int i = 0 ; i < totaltag ; i++)
             //System.out.println("tagset["+i+"]="+tagset[i]);*/
       TagGRNN t = new TagGRNN();
       
       t.readFile(args[0], args[2], args[3], args[4], glyph, tagset);
       
       transitionMatrix = t.generateTM(Words, noWords, tagset);
       observationMatrix = t.generateOM(sentWords, sentnoWords , tagset, Words);
       trainFeatures = t.gettrainFeatures();
       OriOutput =  t.getOriginalOutput(sentTotalwords, 2*sentnoWords, tagset, trainFeatures, 1);
//       annFile.close();
       //transitionMatrix = t.generateTM();
     //  thread = t.RegionMatchesDemo(glyph, Words);
   }
   public void readFile(String fileName,  String outputName, String anninput, String trMatrix, String gl, String[] tgs1) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
	//BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(obsName), "UTF-8"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(anninput), "UTF-8");
        BufferedWriter fout1 = new BufferedWriter(writer1);
	OutputStreamWriter writer2 = new OutputStreamWriter(new FileOutputStream(trMatrix), "UTF-8");
        BufferedWriter fout2 = new BufferedWriter(writer2);
        tmFile = fout2;
       // String is = br.readLine();
        String vibhakti1 = "hi";
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
          //  String sent = br1.readLine();
            //fout.write(line);
           // String ln = line;
            //// //System.out.println(ln);
           // while (line != null) {
               // //System.out.println("hi");
            
            //sb.append(line);
            //sb.append("\n");
           
           // //System.out.println("line length = "+line.length());
           //if(line==null)
             //   break;
            String[] words = new String[300000];// = line.split(" ");//those are your words
            String[] sentwords = new String[300000];
            String[] obs = new String[500000];
            String[] sentwords1 = new String[300000];
            String ws;
            String obws;
            int nowords = 0;
            int obsnowords = 0, j = 0;
           // String[] big = (String [])join(words,nwords);
            //appendWords.add(words);
           // StringTokenizer st = new StringTokenizer(is);
            while (line != null ) {
	           //fout.write(line);
			StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            ws = st.nextToken();
                            words[obsnowords]=ws;
                           // //System.out.println("***words["+nowords+"]="+words[nowords]);
                            //nowords++;
                             if((obsnowords%2) == 0){
	                            obws = ws;
        	                    sentwords1[obsnowords]=obws;
        	                    sentwords[j]=obws;
        	                 //   //System.out.println("////sentwords["+j+"]="+sentwords[j]);
                	            fout.write(" ");
                        	    fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // //System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	    j++;
                            	    
                            }
			    else{
				    obws = ws;
	                            sentwords1[obsnowords]=obws;
	                          //   //System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }
	                    obsnowords++;
                        }
                line = br.readLine();
        
            
            
            for(int i = 0 ; i < nowords; i++){
                 // //System.out.println("words["+i+"]="+words[i]);
                // fout.write(words[i]);
                 //fout.newLine();
                 }
             //line = br.readLine();//vibhakti1 = line;
             // //System.out.println("line="+line+"    words="+Words+"    noWords ="+nowords+"    noTags = "+noTags);
             Words = words;
             noWords = obsnowords;
             noTags = noWords/2;
             trainTags = sentwords1;
             //System.out.println("no of words = "+noWords);
             
              sentWords = sentwords;
                sentTotalwords = sentwords1;
                sentnoWords = j;
                totalWords = obsnowords;
   
            break;
        }
        String[] testsent = new String[noWords];
        int obsnowords1=0;
       /* while (sent != null) {
	           //fout.write(line);
			StringTokenizer st1 = new StringTokenizer(sent);
                        while (st1.hasMoreTokens() && obsnowords1 < (noWords-1)) {
                        	testsent[obsnowords1] = st1.nextToken();
                        	////System.out.println("obsnowords="+obsnowords1+"testsent["+obsnowords1+"]="+testsent[obsnowords1]);
                        	//obs[obsnowords] = st1.nextToken();
                          /* if((obsnowords%2) == 0){
	                         //   obws = st1.nextToken();
        	                   // sentwords1[obsnowords]=obws;
        	                   // sentwords[j]=obws;
        	                  //  //System.out.println("////sentwords["+j+"]="+sentwords[j]);
                	          //  fout.write("\n");
                        	  //  fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // //System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	   // j++;
                            	    
                            }
			    else{
				    obws = st1.nextToken();
	                            sentwords1[obsnowords]=obws;
	                            // //System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }*/
	           //         obsnowords1++;
                           /* else{
                                    obws = st1.nextToken();
        	                    sentwords1[obsnowords]=obws;
        	                    // //System.out.println("sentwords1["+obsnowords+"]="+sentwords1[obsnowords]);
                	            fout.write("\n");
                        	    fout.write(sentwords1[obsnowords]);
                            	  //  obsnowords++;
                            }*/
                            //obsnowords++;
                 //       }
               // sent = br1.readLine();
               
             //   Obs = obs;
             //   numbertestPatterns = obsnowords1;
           //     testSent = testsent;
    
               //	 observationMatrix = generateOM1(sentWords, sentnoWords , tgs1, Words);
          //  }
       // return sb.toString();
         //}
         annFile = fout1;
//         generateANNdatabase(fout1);
        // sentwords1 = getOriginalOutput(sentwords,obsnowords);
        // fout.write("\n");
         //fout.write(sentwords1[obsnowords]);
         for(int i = 0 ; i < j; i++){
         	//// //System.out.println("sentwords1["+i+"]="+sentwords1[i]);
		// //System.out.println("sentwords["+i+"]="+sentwords[i]);         	
         }
         for(int i = 0 ; i < 2*j; i++){
         	// //System.out.println("sentwords["+i+"]="+sentwords[i]);
		// //System.out.println("sentwords1["+i+"]="+sentwords1[i]);         	
         }
        } finally {
        br.close();
     //   br1.close();
        fout.close();
       // fout1.close();
        }
    }
    
    public void readFile1(String fileName, String obsName,  String gl, String[] tgs1) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
	BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(obsName), "UTF-8"));
       // OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(trMatrix), "UTF-8");
        //BufferedWriter fout = new BufferedWriter(writer);
     //   OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(anninput), "UTF-8");
       // BufferedWriter fout1 = new BufferedWriter(writer1);
       // String is = br.readLine();
        String vibhakti1 = "hi";
       // tmFile = fout;
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            String sent = br1.readLine();
            //fout.write(line);
           // String ln = line;
            //// //System.out.println(ln);
           // while (line != null) {
               // //System.out.println("hi");
            
            //sb.append(line);
            //sb.append("\n");
           
           // // //System.out.println("line length = "+line.length());
           //if(line==null)
             //   break;
            String[] words = new String[300000];// = line.split(" ");//those are your words
            String[] sentwords = new String[300000];
            String[] obs = new String[500000];
            String[] sentwords1 = new String[300000];
            String ws;
            String obws;
            int nowords = 0;
            int obsnowords = 0, j = 0;
           // String[] big = (String [])join(words,nwords);
            //appendWords.add(words);
           // StringTokenizer st = new StringTokenizer(is);
            while (line != null ) {
	           //fout.write(line);
			StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            ws = st.nextToken();
                            words[obsnowords]=ws;
                          //  //System.out.println("***words["+nowords+"]="+words[nowords]);
                            //nowords++;
                             if((obsnowords%2) == 0){
	                            obws = ws;
        	                    sentwords1[obsnowords]=obws;
        	                    sentwords[j]=obws;
        	                    //System.out.println("////sentwords["+j+"]="+sentwords[j]);
                	           // fout.write(" ");
                        	   // fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // //System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	    j++;
                            	    
                            }
			    else{
				    obws = ws;
	                            sentwords1[obsnowords]=obws;
	                          //   //System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }
	                    obsnowords++;
                        }
                line = br.readLine();
        
            
            
            for(int i = 0 ; i < nowords; i++){
                 // //System.out.println("words["+i+"]="+words[i]);
                // fout.write(words[i]);
                 //fout.newLine();
                 }
             //line = br.readLine();//vibhakti1 = line;
             // //System.out.println("line="+line+"    words="+Words+"    noWords ="+nowords+"    noTags = "+noTags);
             Words = words;
             noWords = obsnowords;
             noTags = noWords/2;
             trainTags = sentwords1;
             //System.out.println("no of words = "+noWords);
             
             
              sentWords = sentwords;
                sentTotalwords = sentwords1;
                sentnoWords = j;
                totalWords = obsnowords;
   
            break;
        }
        numberPatterns = noWords;
        System.out.println("nowords="+noWords);
        String[] testsent = new String[noWords];
        String[] testsent1 = new String[noWords];
        String[] specialverbs1 = new String[noWords];
        String[] posle = new String[10]; // considering  max 10 words
       // String[] words ;
        //words = testsent;
        int obsnowords1=0;
        String ws1;
        int flag = 0, flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0 ;
        int alaram = 0;
        int counter = 0 ;
        int sp = 0;
        String[] sws = new String[2];
       // testsent[0] = "1";
        while (sent != null) {
	           //fout.write(line);
			StringTokenizer st1 = new StringTokenizer(sent);
                        while (st1.hasMoreTokens() && obsnowords1 < (noWords-1)) {
                        	
	                      //  testsent[obsnowords1] = "1";
                        	testsent[obsnowords1]  = st1.nextToken();
                  		ws1 = testsent[obsnowords1];      	
                        	//System.out.println("CHECK FOR NEW WORD *** ");
                            System.out.println("beforeWS = "+testsent[obsnowords1]+" flag = "+flag +"  counter ="+counter+"  obsnowords1="+obsnowords1);
                            if(flag == 1){
                            		testsent[obsnowords1] = ws1;
		                   // 	System.out.println("wd["+(obsnowords1)+"]="+testsent[obsnowords1]);
                            }
                            if(ws1.equals("\u092B\u093E\u0932\u0947") || ws1.equals("\u0915\u093e\u0932\u0947"))
                            	flag = 0;
                            else
                                sws = RegionMatchesDemo1("\u0932\u0947",ws1);
	                          //  System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                            if(sws[0].equals("ab")) 
		                            flag = 0;
		                    else{
		                            flag = 1;
		                          //  leindex = 2;
		                    }
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   
	                   if(flag == 1 ){
	                    	testsent[obsnowords1++] = sws[0];
	                    	System.out.println("PSPLE= "+sws[0]);
	                    	//posle = RegionMatchesDemoTest(sws[0],sentWords,obsnowords);
	                    	/*for(int i = 0 ; i < 5 ; i++ )
		                    	System.out.println(" posle ["+i+"]="+posle[i]);*/
	                    	testsent1[obsnowords1] = ws1;
	                    	//System.out.println("wd["+obsnowords1+"]="+testsent[obsnowords1]);
	                    	//System.out.println("beforenowods="+obsnowords1);
	                  //  	if(obsnowords1%2 != 0){
		            //        	testsent[obsnowords1+1] = ws1;
		              //      	System.out.println("wd["+(obsnowords1+1)+"]="+words[obsnowords1+1]);
		           //	}
	                    	testsent[obsnowords1++] = sws[1];
	                    //	System.out.println("wd["+(obsnowords1)+"]="+testsent[obsnowords1]);
	                    	alaram = 1;
	                    	//leindex = obsnowords1;
	                    //	obttags[obsnowords1] = "PSPLE";
	                    	
	                   }
	                   
	                   
	                  // System.out.println("CHECK FOR PLAI ");
	                   if(flag1 == 2 ){
                            		testsent[obsnowords1] = ws1;
		                 //   	System.out.println("***wd["+(obsnowords1-3)+"]="+testsent[obsnowords1-3]);
                            }
	                   sws = RegionMatchesDemoLai("\u0932\u093E\u0908", ws1);

                     
	                   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                   if(sws[0].equals("ab")){
		                   flag1 = 0;
                      /* sws = RegionMatchesDemoLai("\u0939\u0930\u0941", ws1);
                       if(sws[0].equals("ab"))
                          sws = RegionMatchesDemoLai("\u0939\u0930\u0942", ws1);*/
                     }
		           else
		                    flag1 = 2;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   if(flag1 == 2){
	                    	testsent[obsnowords1++] = sws[0];
	                    	testsent1[obsnowords1] = ws1;


			 /*  sws = RegionMatchesDemoLai("\u0939\u0930\u0941", ws1);
	                   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                   if(sws[0].equals("ab"))
		                   flag3 = 0;
		           else
		                    flag3 = 2;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   if(flag3 == 2){
	                    	testsent[obsnowords1++] = sws[0];
	                    	testsent1[obsnowords1] = ws1;*/

	                    	//System.out.println("beforenowods="+obsnowords1);
	                    //	if(obsnowords1%2 != 0)
		              //      	words[obsnowords1+1] = ws1;
	                    	testsent[obsnowords1++] = sws[1];
	                    	alaram = 1;
	                   }
	                  
	                   
	                   
	                  // System.out.println("CHECK FOR PKO ");
	                   if(flag2 == 2 ){
                            		testsent[obsnowords1] = ws1;
		                    //	System.out.println("wd["+(obsnowords1-3)+"]="+testsent[obsnowords1-2]);
                            }
	                   sws = RegionMatchesDemo1("\u0915\u094B", ws1);
	                   //sws[0] = "ab";
	                //   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
	                   if(sws[0].equals("ab"))
		                   flag2 = 0;
		           else
		                    flag2 = 2;
	                    //}
	                   // for(int i = 0 ; i < 1 && flag == 1 ; i++){
	                   if(flag2 == 2){
	                   	specialverbs1[sp] = ws1;
	                    	//testsent[obsnowords1++] = sws[0];
	                    	//testsent1[obsnowords1] = ws1;
	                    	
	                    	System.out.println("spvb["+sp+"]="+specialverbs1[sp]);
	                    //	if(obsnowords1%2 != 0)
		              //      	testsent[obsnowords1+1] = ws1;
	                    	testsent[obsnowords1++] = ws1;
	                    //	alaram = 1;
	                    sp++;
	                   }

                     // System.out.println("CHECK FOR PKO ");
                     if(flag4 == 2 ){
                                testsent[obsnowords1] = ws1;
                        //  System.out.println("wd["+(obsnowords1-3)+"]="+testsent[obsnowords1-2]);
                            }
                    // sws = RegionMatchesDemo1("\u0915\u094B", ws1);
                     sws = RegionMatchesDemoLai("\u0939\u0930\u0941", ws1);
                            
                            if(sws[0].equals("ab"))
                              sws = RegionMatchesDemoLai("\u0939\u0930\u0942", ws1);
                     //sws[0] = "ab";
                  //   System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab"))
                       flag4 = 0;
               else
                        flag4 = 2;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if(flag4 == 2){
                      testsent[obsnowords1++] = sws[0];
                        testsent1[obsnowords1] = ws1;
                        testsent[obsnowords1++] = sws[1];
                        alaram = 1;
                      //sp++;
                     }
	                   
	                   System.out.println("flag="+flag+"  flag1 = "+flag1+"   flag2 ="+flag2+"  flag4="+flag4+"  obsnowords1="+obsnowords1+"  alaram = "+alaram);
	                   
	                    if(flag == 0 && flag1 == 0 && flag2 == 0 && flag4 == 0 && alaram == 0){
	                            testsent[obsnowords1]=ws1;
	                   //         System.out.println("wd["+(obsnowords1)+"]="+testsent[obsnowords1]);
        	                    obsnowords1++;
        	            }
        	            else if(flag == 0 && flag1 == 0 && flag2 == 0 && flag4 == 0) {
        	            		System.out.println("hi alaram");
        	                    alaram = 0;
        	                    obsnowords1++;
        	            }
        	           /* if(flag == 0 && flag1 != 0 && flag2 != 0 && alaram != 0)
        	            	obsnowords++;*/
                        	//System.out.println("obsnowords="+obsnowords1+"testsent["+obsnowords1+"]="+testsent[obsnowords1]);
                        	//obs[obsnowords] = st1.nextToken();
                          /* if((obsnowords%2) == 0){
	                         //   obws = st1.nextToken();
        	                   // sentwords1[obsnowords]=obws;
        	                   // sentwords[j]=obws;
        	                  //  //System.out.println("////sentwords["+j+"]="+sentwords[j]);
                	          //  fout.write("\n");
                        	  //  fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // //System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	   // j++;
                            	    
                            }
			    else{
				    obws = st1.nextToken();
	                            sentwords1[obsnowords]=obws;
	                            // //System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }*/
	                  //  obsnowords1++;
                           /* else{
                                    obws = st1.nextToken();
        	                    sentwords1[obsnowords]=obws;
        	                    // //System.out.println("sentwords1["+obsnowords+"]="+sentwords1[obsnowords]);
                	            fout.write("\n");
                        	    fout.write(sentwords1[obsnowords]);
                            	  //  obsnowords++;
                            }*/
                            //obsnowords++;
                }
                sent = br1.readLine();
               
             //   Obs = obs;
                numbertestPatterns = obsnowords1;
                //System.out.println("number of test patterns ="+numbertestPatterns);
               // oritestSent = "nll";
               
                testSent = testsent;
                //if(testSent[])
          /*      for(int i = 0 ; i < obsnowords1 ; i++){
                	oritestSent[i] = "nll";
                }*/
                oritestSent = testsent1;
                for(int i = 0 ; i < obsnowords1 ; i++){
                	System.out.println("oritestSent["+i+"]="+testsent1[i]);
                }
    
               //	 observationMatrix = generateOM1(sentWords, sentnoWords , tgs1, Words);
          //  }
       // return sb.toString();
         }
         specialverbs = specialverbs1;
      //   annFile = fout1;
//         generateANNdatabase(fout1);
        // sentwords1 = getOriginalOutput(sentwords,obsnowords);
        // fout.write("\n");
         //fout.write(sentwords1[obsnowords]);
      /*   for(int i = 0 ; i < j; i++){
         	//// //System.out.println("sentwords1["+i+"]="+sentwords1[i]);
		// //System.out.println("sentwords["+i+"]="+sentwords[i]);         	
         }
         for(int i = 0 ; i < 2*j; i++){
         	// //System.out.println("sentwords["+i+"]="+sentwords[i]);
		// //System.out.println("sentwords1["+i+"]="+sentwords1[i]);         	
         }*/
        } finally {
        br.close();
        br1.close();
     //   fout.close();
    //    fout1.close();
        }
        
    }
     public String[] getspecialverbs(){
    	return specialverbs;
    }
    public String[] getoritestSent(){
    	return oritestSent;
    }
    public int getleindex(){
    	return leindex;
    }
    
    public String[] gettrainTags(){
    	//for(int i = 0 ; i < trainTags.length ; i++)
	    	//System.out.println("traintags["+i+"]="+trainTags[i]);
    	return trainTags;
    }
    public String[] RegionMatchesDemo1(String findMe, String file) {
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
       //  System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
       //        System.out.println("check");    
              // System.out.println(" g="+g);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 2);
              
        //      System.out.println("g[0]="+g[0]);
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
    
   public String[] RegionMatchesDemo2(String findMe, String file) {
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
       //  System.out.println("file length = "+file.length);
         //System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
       //        System.out.println("check");    
              // System.out.println(" g="+g);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
        //      System.out.println("g[0]="+g[0]);
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
        if(fileLength > 3)
	        findMe1 = file.substring(fileLength-3,fileLength);
	else
		g[0] = "ab";
        // System.out.println("file length = "+file.length);
       //  System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
       //        System.out.println("check");    
         //      System.out.println(" filelength="+fileLength);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
      //        System.out.println("g[0]="+g[0]);
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
    //public void generateANNdatabase(String annfile){
    //	annfile.write
    //}
    public String[] getOriginalOutput(String[] sts, int nosts, String[] tg, double[][] efeatures, int s) throws IOException {
    	int ind = 0;
    	int[] output = new int[nosts];
    	int[][] annoutput = new int[nosts/2][totaltag];
   // 	OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(outputName1), "UTF-8");
	//.out.println("  EFEATURES : "); 
	  	////System.out.println("nosts="+nosts);
  //      BufferedWriter fout1 = new BufferedWriter(writer1);
	for(int i = 0 ; i < nosts; i++){
		////System.out.println("i="+i);
		for(int j = 0 ;j < 3*totaltag ; j++){
			//if(i%2 != 0)
			//	//System.out.print("  "+efeatures[i][j]);
		}
                // //System.out.println();
               //  //System.out.println();
         }
    	String[] Originaloutput = new String[nosts/2];
	// //System.out.println("nosts="+nosts);
         int j=0;
         for(int count=0  ; count <= nosts ; count++){
           if((count%2) != 0){
                Originaloutput[j] = sts[count];
             //   //System.out.println("Originaloutput["+(j)+"]="+Originaloutput[j]);
            //    fout1.write("\n");
            //    fout1.write(Originaloutput[j]);
                j++;
           }
           //else wordTag[i]= "SYM";
       }
       ////System.out.println("j="+j);
       for(int i = 0 ; i < j/*nosts/2*/ ; i++){
       	   for(int it = 0 ; it < totaltag ; it++){
	       		if(Originaloutput[i].equals(tg[it])){
	       			output[i] = it+1;
	       			//// //System.out.println("output["+i+"]="+output[i]);
	       			// //System.out.println(output[i] +"            "+tg[it]);
	       			 //annoutput[i][it] = output[i]; 
	       			 annoutput[i][it] = 1; 
	       			////System.out.println("i = "+i+"    it="+it);
	       			//// //System.out.print("\t"+(i+1));	       			
	       		}
	       		else{
	       		   	// //System.out.println("tg["+it+"]="+tg[it]);
	       		}
	   	}
	}
	//System.out.println("FEATURE EXTRACTION:"+nosts);
	//annFile.write("\n INPUT DATABASE: ");
	if(s != 0){
		annFile.write(""+nosts/2);
		annFile.write("\n");
	}
	int x1=0, m1 = 0;
	for(int x = 0 ; x < nosts/2  ; x++){
		
		for(int m = 0 ; m < 3*totaltag ; m++){
			
			//if(x%2 != 0){
				//annFile.write(x+"       ");
				//annFile.write("\n");
				if(s != 0)
					annFile.write(efeatures[x][m]+" ");
				//x1++;
			//}
//			//System.out.println("  "+Features[x][m]);
			//Features[x][m] = efeatures[x][m];
		}
		for(int y = 0 ; y < totaltag ; y++){
				////System.out.println("annoutput["+x+"]["+y+"]="+annoutput[x][y]);
			//	Outputs[x][y] = annoutput[x][y]; 
			//	annFile.write("output");
		//	if(x%2 != 0)
				//x1++;
				//if(x1 == 0)
				//	x1 = 1;
					
				//if( x%2 != 0)
				if(s != 0)
					annFile.write(annoutput[x][y]+" ");
				//else 
				//	x1 = x/2;
			//	annFile.write("\n");
		}
		//annFile.write("\n");
		if(s != 0)
			annFile.write("\n");	
		//x1 = x/2;	
		
	}
	inputNeurons = 3*totaltag;
	outputNeurons = totaltag;
	hiddenNeurons = nosts/2 ;
	numberPatterns = nosts/2 ;
	//numbertestPatterns = 
	Features = efeatures;
	Outputs = annoutput;
	if(s != 0)
		annFile.close();
	return Originaloutput;
    }
    public double[][] getFeatures(){
    	return Features;
    }
    public int[][] getOutputs(){
    	return Outputs;
    }
    
    public int getnoPatterns(){
    	return sentnoWords;
    }
    public int getinputNeurons(){
    	return inputNeurons;
    }
    public String[] getTagset(){
    	return TGSET;
    }
    public String[] getWords(){
    	//for(int i = 0 ; i < numberPatterns; i++)
	    	//System.out.println("sentWords["+i+"]="+sentWords[i]);
    	return sentWords;
    }
    public int getoutputNeurons(){
    	return outputNeurons;
    }
    public int gethiddenNeurons(){
    	return hiddenNeurons;
    }
    public int getnumberPatterns(){
    	return numberPatterns;
    }
    public int getnumbertestPatterns(){
    	return numbertestPatterns;
    }

    public String[] gettestSentence(){
    	return testSent;
    }    	
    public double[][] gettestFeatures(){
    	return testFeatures;
    }    
    
   public double[][] generateTM(String[] wds, int nowds, String[] tgs) throws IOException {
   	//tmFile.write("hi");
            int[][] freqDist = new int[totaltag][totaltag];
       double[][] probDist = new double[totaltag][totaltag];
       //String[] wordTag = "SYM";
       String[] wordTag = new String[nowds];
       for(int p=0; p<nowds;p++)
            wordTag[p] = " NULL ";
       // //System.out.println("nowds="+nowds+"totaltag="+totaltag);
       int i=0, index=0;
      // for(int j = 0 ; j < nowds; j++)
                 // //System.out.println("words["+j+"]="+wordTag[j]);
       for(int count=0  ; count <= nowds ; count++){
           if((count%2) != 0){
                wordTag[i] = wds[count];
                 //System.out.println("wordTag["+(i)+"]="+wordTag[i]);
                i++;
           }
           //else wordTag[i]= "SYM";
       }
     /*  int t0 =0, t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0, t9 = 0, t10 = 0, t11 = 0, t12 = 0, t13 = 0, t14 = 0, t15 = 0;
       int t16 =0, t17 = 0, t18 = 0, t19 = 0, t20 = 0, t21 = 0, t22 = 0, t23 = 0, t24 = 0, t25 = 0, t26 = 0, t27 = 0, t28 = 0, t29 = 0, t30 = 0, t31 = 0;
       int t32 =0, t33 = 0, t34 = 0, t35 = 0, t36 = 0, t37 = 0, t38 = 0, t39 = 0, t40 = 0, t41 = 0;      */
     //  for(int s=0  ; s < nowds/2 ; s++){
         //  if((count%2) != 0){
            //    wordTag[i] = wds[count];
                // //System.out.println("wordTag["+(s)+"]="+wordTag[s]);
             //   i++;
        //   }
           //else wordTag[i]= "SYM";
       //}
        
       for(int j = 0 ; j < (nowds/2 - 1); j++){
           int item = j+1;
           int cindex = 0;
        for(int it = 0 ; it < totaltag; it++){
           System.out.println("Tagset["+it+"]="+tgs[it]+"            wordTag["+j+"]="+wordTag[j]);
           if(wordTag[j].equals(tgs[it])){
               index = it;
               //System.out.println("Index="+index+"   j="+j+"  item="+item);
           }
            if(wordTag[item].equals(tgs[it])){
               cindex = it;
               // //System.out.println("Index="+index+"   j="+j+"  item="+item+" cindex="+cindex);
           }
        }
        // //System.out.println("hi freq");
        System.out.println("Index="+index+"freqDist["+index+"]["+0+"]="+freqDist[index][12]+"wirdTag["+(j)+"]="+wordTag[j]);
               freqDist[index][cindex]++;
              
       }
       // //System.out.println("Frequency Distribution");
       int sum = 0;
       for(int m = 0 ; m < totaltag ; m++){
           for(int n = 0 ; n < totaltag ; n++){
               // //System.out.print("\t"+freqDist[m][n]);
               sum = sum + freqDist[m][n];
           }
           // //System.out.println();
       }
       // //System.out.println("Total Frequency = "+sum);
       
       // Generating Transition Probability table
       
       for(int m = 0 ; m < totaltag ; m++){
           for(int n = 0 ; n < totaltag ; n++){
              // // //System.out.println("prob dist["+m+"]["+n+"] = "+probDist[m][n]);
               probDist[m][n] = (double)freqDist[m][n]/sum;
           }
       }
       for(int m=0;m<totaltag;m++){
   		for(int n=0;n<totaltag;n++){
   			if(probDist[m][n] == 0)
   				probDist[m][n] = 0.00000000001;
   		}
   	}
       // Displaying Transition Probability table
        //System.out.println("Transition Probability Table : ");
       // try{
        	tmFile.write(""+totaltag);
        	tmFile.write("\n");
	       for(int m = 0 ; m < totaltag ; m++){
        	   for(int n = 0 ; n < totaltag ; n++){
                	////System.out.print("\t"+probDist[m][n]);
	               	tmFile.write(probDist[m][n]+" ");	
        	   }
	            //System.out.println();
	            tmFile.write("\n");
	       }
	//}
	//catch(IOException ie){}
	tmFile.close();
       return probDist;
   }


 
   
   public double[][] generateOM(String[] owds, int sentnowds, String[] tgs, String[] word){
   	double[][] obsfreMatrix = new double[tgs.length][owds.length];
   	double[][] obsMatrix = new double[tgs.length][owds.length];
   	double[][] newm = new double[tgs.length][owds.length+1];
   	String[] fullinx = new String[5]; // Maximum Tags assigned to each word is 5
   	String[] pos = new String[5];
   	String[] pos1 = new String[1]; // Starting tag of the document
   	int[] psn = new int[5]; //position of the Tag
   	
   	int sum = 0;
   	int sum1 = 0;
   	int sum2 = 0;
   	String ind = "hi"; 
   	int indx = 0;
   	int count = 0;
       // //System.out.println("Totaltags="+totaltag);
   	for(int i = 0 ; i < sentnowds ; i++){
   		//System.out.println();
   		count = 0;
   		//indx = 0;
	   	// //System.out.println("wds length = "+sentnowds+"owds["+i+"]="+owds[i]);
	   //	 do{
	 		
	 		//for(int l = 0 ; l < fullinx.length ; l++){
	 			//if(i != 0){
		 		pos = RegionMatchesDemo(owds[i], word); 
		 		/*for(int m = 0 ; m < pos.length ; m++){
		 			//System.out.println("pos["+m+"] for "+i+"word is "+pos[m]);
		 		}*/
		 		
		 		//}
	 			for(int p = 0 ; p < 5; p++){
	 				if( pos[p] != null){
	 					//System.out.println("pos["+p+"]="+pos[p]);
	 					//indx = 0;
		 				for(int q = 0 ; q < tgs.length; q++){
		 					if(pos[p].equals(tgs[q])){
		 						psn[p] = q;
		 						obsfreMatrix[q][i]++;
		 					//	//System.out.println("obsfreMatrix["+q+"]["+i+"]="+obsfreMatrix[q][i]);
		 						if(indx == 1 ){
		 							newm[q][0]=1;
		 							//sum1++;
		 							////System.out.println("newm["+q+"]["+0+"]="+newm[q][0]);
		 						}
				 				////System.out.println("psn["+p+"]="+psn[p]);
				 			}
			 			}
			 		}
			 		else{
			 			        	//obsfreMatrix[26][i]++;
			 		}
			 		if(psn[p] == 36)  // Checking for YM (full stop)
			 		{
			 			indx = 1;
			 			//newm[][]
		 				////System.out.println("ENTRY "+i+"after YM ="+indx);
		 				//break;
			 		}
			 	}
			 	
   		sum++;
   		
   		////System.out.println("obsfreMatrix["+m+"]["+n+"]");
   	}
   	int[] var = new int[sentnowds];
   	for(int l = 0 ; l < sentnowds ; l++){
	   	pos = RegionMatchesDemo(owds[l], word); 
	   	for(int r = 0 ; r < 5 ; r++)
		   	//System.out.println("***pos["+r+"]="+pos[r]);
		for(int q = 0 ; q < 500 ; q++){
			if(pos[q] != null){
				var[l] = q+1;
		 	}
		 	else{
		 		break;
		 	}
		 	
		}
		//System.out.println("Frequency of word "+l+" is "+var[l]);
	}
   	for(int m = 0 ; m < totaltag ; m++ ){
   		for(int n = 1 ; n < sentnowds+1 ; n++){
   			newm[m][n] = obsfreMatrix[m][n-1];
   		}
   		// //System.out.println();
   	}
   	
   	for(int m = 0 ; m < totaltag ; m++ ){
   		for(int n = 1 ; n < sentnowds+1 ; n++){
   			newm[m][n] = newm[m][n]/(sum);
   			//obsMatrix[m][n] = obsfreMatrix[m][n]/sum;
   		}
   		// //System.out.println();
   	}
   	
   	for(int m=0;m<totaltag;m++){
   		newm[m][0] = 0;
   		for(int n=0;n<sentnowds+1;n++){
   			if(newm[m][n] == 0)
   				newm[m][n] = 0.000000000001;
   			//	obsMatrix[m][n] = 0.0000000001;
   		}
   	}
   	
   	// STARTING PROBABILITY
   	
 	for(int s = 0 ; s < 5 ; s++){
 		if(Finalstart[s] != null){
 			for(int q1 = 0 ; q1 < tgs.length; q1++){
 				//newm[q1][0] = 0;
				if(Finalstart[s].equals(tgs[q1])){
					//psn[s] = q1;
					newm[q1][0]++;
					sum1++;
				}
			}
 		}
 	}
 	
 	pos1 = RegionMatchesDemo(owds[0], word); 
 	for(int q2 = 0 ; q2 < tgs.length; q2++){
 			////System.out.println("***pos1["+q2+"]="+tgs[q2]+"   word[1]="+Words[1]);
		if(Words[1].equals(tgs[q2])){
			//psn[s] = q1;
			////System.out.println("***pos1["+q2+"]="+tgs[q2]);
			newm[q2][0]++;
			sum1++;
		}
	}
   	//for(int m = 0 ; m < g ; m++ ){
   		for(int n = 0 ; n < tgs.length ; n++){
   			newm[n][0] = newm[n][0]/(sum1);
   			
   		}
   	// //System.out.println();
   	//}
   	/*for(int j = 0 ; j < wds.length; j++){
   	for(int it = 0 ; it < totaltag; it++){
           // // //System.out.println("Tagset["+it+"]="+tgs[it]+"            wordTag["+j+"]="+wordTag[j]);
           if(wds[j].equals(tgs[it])){
               index = it;
               // //System.out.println("Index="+index+"   j="+j+"  item="+item);
           }
            if(wordTag[item].equals(tgs[it])){
               cindex = it;
               // //System.out.println("Index="+index+"   j="+j+"  item="+item+" cindex="+cindex);
           }
        }*/
        double value;
        double[] temp = new double[3*totaltag];
      //  ExtractedFeatures = new double[sentnowds][3*totaltag];
        for(int p = 1 ; p < sentnowds ; p++ ){
   		for(int q = 0 ; q < totaltag ; q++){
   			if(newm[q][p] > 0.0000005){
				// //System.out.print("\t newm["+q+"]["+p+"] = "+newm[q][p] +"sentnowds="+sentnowds);
				 value = newm[q][p];			 
				 
   			}	
			
   			 ////System.out.print("\t"+obsMatrix[m][n]);
   		}
   		////System.out.println();
   		////System.out.println();   		
   	}
   	//System.out.println("sentnowds="+sentnowds);
        //System.out.println("\n OBSERVATION PROBABILITY TABLE : ");
       // try{
	   	//for(int m = 0 ; m < totaltag ; m++ ){
   			//for(int n = 0 ; n < (sentnowds+1) ; n++){
				//	 //System.out.print("\t"+newm[m][n]);
				 	//annFile.write("  "+newm[m][n]);	
	   			 ////System.out.print("\t"+obsMatrix[m][n]);
   		//	}
   			//System.out.println();
	   //	}
	//}
	//catch(IOException ie){}
   	observationMatrix1 = newm;
   	//annFile.close() ;
   	return newm;
   }
   public double[][] gettrainFeatures(){
   	double[] temp = new double[3*TGSET.length];
   	String[] positiontr;
   	int pos = 0 ;
   	int p;
   	int p1=0;
   	int count = 0;
   	numberPatterns = sentnoWords*2;
   	//System.out.println("no of train pat = "+numberPatterns+"totaltag="+totaltag);
   	ExtractedFeatures = new double[numberPatterns][3*totaltag];
   	for(p=0;p < numberPatterns;p++){
   		////System.out.println("p="+p+" Word["+p+"]="+Words[p]);
	   //	positiontr = RegionMatchesDemoTest(sentWords[p], sentWords); 
	   	for(int n = 0 ; n < totaltag ; n++){	
	   		if(Words[p].equals(TGSET[n]))
	   		{
	   			//p1++;
	   			pos = n;	
	   			////System.out.println("pos = "+pos);	
	//	   	//System.out.println("positiontr["+p+"] = "+positiontr[p]+"       TGset["+n+"]="+TGSET[n]);		   		
	  // 		if(positiontr[p].equals(TGSET[n])){
	//			pos[count] = n;
				////System.out.println("word no: "+p);
				temp = getFeature1(numberPatterns, totaltag, p, pos, observationMatrix);
				
			//}     
				p1 = (p-1)/2;
				for(int m = 0 ; m < 3*totaltag ; m++){
	//			temp = getFeature1(sentnowds, totaltag, p, m, observationMatrix1);
			 	////System.out.println("temp["+m+"]="+temp[m]);
			 		//if(p%2 != 0){
			 			
			 			ExtractedFeatures[p1][m] = temp[m];
			 		//	p1++;
				 	//}
				}
				////System.out.println("p1: "+p1);
				//p1 = (p-1)/2;
			}
		}
		
		//count++;
	}
   	return ExtractedFeatures;
   }
   
   public double[][] getTransitionMatrix(){
   	return transitionMatrix;
   }
   
   public void getTransitionMatrix(double[][] tm){
   	transitionMatrix = tm;
   }
   
   public double[][] gettrainInput(){
   	return trainFeatures;
   }

   public double[][] gettestInput(){
   	return testFeatures;
   }
   public double[][] getobservationMatrix(){
   	 //System.out.println("\n OBSERVATION PROBABILITY TABLE 1: ");
   //	 //System.out.println("totaltag="+totaltag+"   totalwords="+totalWords);
   	//for(int m = 0 ; m < totaltag ; m++ ){
   	//	for(int n = 0 ; n < totalWords/2 ; n++){
	//			 //System.out.print("\t"+observationMatrix[m][n]);
//   			 //System.out.print("\t"+obsMatrix[m][n]);
   		//}
   		//System.out.println();
   		//System.out.println();   		
   	//}
   	return observationMatrix;
   }
   public double[][] getTestFeatures(double[][] train){
   	getTestfeature(testSent,numbertestPatterns, TGSET, Words, train);
   	return testFeatures;
   }
   
   public int[] getrareFreqpos(){
   	for(int i = 0 ; i < rareFreqpos.length ; i++)
   		System.out.println("rareFreqpos["+i+"]="+rareFreqpos[i]);
   	return rareFreqpos;
   }
   
   public int[] getrareFreTrainpos(){
   	//for(int i = 0 ; i < rareFreqpos.length ; i++)
   		//System.out.println("rareFreqpos["+i+"]="+rareFreqpos[i]);
   	return rareFreqTrainpos;
   }
   public void getTestfeature(String[] sent, int notest, String[] testtag, String[] testword, double[][] trinF){
   	//System.out.println("Test sentence is :");
   	double[] temp1 = new double[3*testtag.length];
   	double[][] temp2 = new double[notest][3*testtag.length];
   	int[] tconfusedword = new int[notest];
   	trainFeatures = trinF;
   	allunknownpositions = new int[notest];
   	//temp2 = getobservationMatrix();
   	String position[] = new String[notest];
   	String position1[] = new String[notest];
   	int count1 = 0;
   	int posindex[] = new int[notest];
   	int trr;
   	int count=0;
   	int n = 0;
   	System.out.println("no of test patterns = "+notest+" testtag.len="+testtag.length);
   	/*for(int l = 0 ; l < numberPatterns ; l++){
   		for(int m = 0 ; m < 117 ; m++){
		   	//System.out.print("  trainFeatures["+l+"]["+m+"] = "+trainFeatures[l][m]);
		}
		//System.out.println();
	}*/
	int count2 = 0;
   	for(int i = 0 ; i < notest ; i++){
   	   		//System.out.print("sent["+i+"]=  "+sent[i]);
   		try{
   	   		/*if(!oritestSent[i].equals("null")){
  		 		position1 = RegionMatchesDemoTest(oritestSent[i], testword, i);    	   			
   	   		}*/
   	   		
		   		position = RegionMatchesDemoTest(sent[i], testword, i); 
		   	
   			if(position[0].equals("nll")){
	   			allunknownpositions[count2] = i;
	   			System.out.println("Unknown positions :"+allunknownpositions[i]);
	   		}
	   		count2++;
	   	}catch(NullPointerException e ){}
   		if(alaram != 0){
   			tconfusedword[n] = i;
   			//System.out.println("confusedword["+n+"]="+i);
   			n++;
   		}
   	//	//System.out.println("position["+0+"]=  "+position[0]);
   		//for(int j=0 ; j < 5 ; j++){
	   		////System.out.println("i= "+i+"  test position["+j+"]="+position[j]);
	   	//	if( position[j] != null){
	 					////System.out.println("test position["+j+"]="+position[j]);
	 					//indx = 0;
		 				//for(int q = 0 ; q < testtag.length; q++){
		 				//	if(position[j].equals(testtag[q])){
		 				//		posindex[count] = q;
		 				//		count1 = i;
		 						//trr = (int)position[j];
		 //						//System.out.println("test index["+count+"]="+posindex[count]+" i="+i);
		 				//		temp1 = getFeatureTest(notest, testtag.length, count1, q, observationMatrix );
		 //						for(int k = 0 ; k < 3*testtag.length ; k++){
		 						//	//System.out.print("temp1["+k+"]=  "+temp1[k] +"  i="+i+" k = "+k);
			 					//	temp2[i][k]=trainFeatures[trr][k];
		//	 						temp2[i][k] = temp1[k];
		//	 						//System.out.println("extractedTestfeaturesfinal["+i+"]["+k+"]="+temp2[i][k]);
		//	 					}
		 				//	}
		 				//}
		 //	}
	   		
	   	//}
	   	count++;
   	}
   	   	//System.out.println();
   	   	//System.out.println("HI");
   	//for(int i = 0 ; i < testtag.length ; i++){
   	//	//System.out.print("  "+testtag[i]);
   	//}
   	//System.out.println();
   	//for(int i = 0 ; i < totalWords ; i++){
   	//	//System.out.print("  "+testword[i]);
   //	}
   	
   	//return temp2;
   	confusedword = tconfusedword;
   	//System.out.println("** confusedword[0]="+confusedword[0]);
   }

   public int[] getPositions(){
   	return allunknownpositions;
   }
   
   
   public static double Max(double[] b) 
	{
		double a[] = new double[b.length] ;
		a = b;

		double min = a[0]; //  assume first elements as smallest number
		double max = a[0]; //  assume first elements as largest number

		for (int i = 1; i < a.length; i++)  // iterate for loop from arrays 1st index (second element)
		{
			if (a[i] > max) 
			{
				max = a[i];
			}
			if (a[i] < min) 
			{
				min = a[i];
			}
		}

		//System.out.println("Largest Number in a given array is : " + max);
		//System.out.println("Smallest Number in a given array is : " + min);
		return max;
	}
   public String[] getPossibleTags(int[] ind, String[] obttags, double[][] ttltrain){
        String[] ttg = new String[numbertestPatterns];    // maximum 5 tags for unknown words
        double[] transtag = new double[39];
        int possibletagindex = 0;
        double tempmax;
        String prevTag;
        String nextTag = "hi1";
        int prevTagindex;
   	for(int i = 0 ; i < numbertestPatterns ; i++){
   		if(ind[i] != 0){
        System.out.println("obttags["+(obttags[ind[i]+1])+"]="+obttags[ind[i]+1]);
   			prevTag = obttags[ind[i]-1];
   			try{
	   			nextTag = obttags[ind[i]+1];
	   		}
	   		catch(ArrayIndexOutOfBoundsException exception){};
   			//System.out.println("prevTag = "+prevTag+" nextTag = "+nextTag +" test pattern ="+i);	
   			for(int k = 0 ; k < 39 ; k++){			
	   			if(TGSET[k].equals(prevTag)){
	   				prevTagindex = k;
	   				//System.out.println("prevTag="+prevTag+"  prevtgindex="+prevTagindex);
	   				for(int l = 0 ; l < numbertestPatterns ; l++){
	   					if(prevTagindex == l){
	   						
	   						for(int n = 39 ; n < 2*39 ; n++){
		   						transtag[n-39] = ttltrain[l][n];
		   						//System.out.println("  "+transtag[n-39]);
		   					}
		   						tempmax = Max(transtag);
		   						for(int r = 0 ; r < 39 ; r++){
		   							if(tempmax == transtag[r]){
		   								possibletagindex = r;
		   							}
		   						}
	   							//System.out.println("maximum is "+tempmax +" possibletagindex = "+possibletagindex);
		   						ttg[ind[i]] = TGSET[possibletagindex];
		   						obttags[ind[i]] = TGSET[possibletagindex];
		   						//System.out.println("possibletag["+i+"] = "+ttg[ind[i]]);
		   						////System.out.println("hi1 = "+"\u0964");
		   						//if(nextTag.equals("PUNC")) && testwd[ind[i]].equals("?")){
		   						//	obttags[ind[i]] = "VAUX";
		   						//}
	   					}
	   				}
	   			}
	   		}
   		}
   	}
   	return ttg;
   }


   public String[] getPossibleTags1(int[] ind, String[] obttags, double[][] ttltrain, String[] testwd){      // to tackle unknown word just before full stop
        String[] ttg = new String[numbertestPatterns];    // maximum 5 tags for unknown words
        double[] transtag = new double[39];
        int possibletagindex = 0;
        double tempmax;
        String prevTag;
        String nextTag="hi";
        String[] str;
        int flag2 = 0;
        int prevTagindex;
        System.out.println("Test patterns = "+numbertestPatterns);
   	for(int i = 0 ; i < numbertestPatterns ; i++){
   		System.out.println("test word["+i+"]="+testwd[i]);
   		if(ind[i] != 0){
   			prevTag = obttags[ind[i]-1];
   			try{
	   			//nextTag = obttags[ind[i]+1];
	   			nextTag = "NN";
	   		}
	   		catch(ArrayIndexOutOfBoundsException exception){};
   			System.out.println("prevTag = "+prevTag+" nextTag = "+nextTag +" test pattern ="+i);	
   			for(int k = 0 ; k < 39 ; k++){			
	   			if(TGSET[k].equals(prevTag)){
	   				prevTagindex = k;
	   				//System.out.println("prevTag="+prevTag+"  prevtgindex="+prevTagindex);
	   				for(int l = 0 ; l < numbertestPatterns ; l++){
	   					if(prevTagindex == l){
	   						
	   						for(int n = 39 ; n < 2*39 ; n++){
		   						transtag[n-39] = ttltrain[l][n];
		   						//System.out.println("  "+transtag[n-39]);
		   					}
		   						tempmax = Max(transtag);
		   						for(int r = 0 ; r < 39 ; r++){
		   							if(tempmax == transtag[r]){
		   								possibletagindex = r;
		   							}
		   						}
	   							//System.out.println("maximum is "+tempmax +" possibletagindex = "+possibletagindex);
		   						ttg[ind[i]] = TGSET[possibletagindex];
		   						obttags[ind[i]] = TGSET[possibletagindex];
		   						System.out.println("possibletag["+i+"] = "+ttg[ind[i]]);
		   						System.out.println("123 testwd["+ind[i-1]+"] = "+testwd[ind[i-1]]);
		   						if(nextTag.equals("PUNC") && testwd[ind[i]].equals("\u0964")){
		   							obttags[ind[i]] = "VAUX";
		   						}
		   						/*System.out.println("ind["+i+"]="+testwd[ind[i]]+"word ="+"\u0918\u0923\u094D\u091F\u093E");
		   						str = RegionMatchesDemo1("\u0918\u0923\u094D\u091F\u093E",testwd[ind[i]]);
		   						System.out.println("Hi QTO1 str="+str[0]);
		   						if(str[0].equals("ab"))
		                   					flag2 = 0;
					         	        else
							                    flag2 = 2;
							        System.out.println("Hi QTO flag ="+flag2);
							        if(flag2 == 2){
							        	System.out.println("Hi QTO");
							        	obttags[ind[i]] = "QTO";
							        }*/
		   						/*if(testwd[i].equals("\u0918\u0923\u094D\u091F\u093E")){
		   							System.out.println("hi123");
		   							obttags[ind[i-1]] = "QTO";
		   						}*/
		   					//	if(nextTag.equals("PUNC") && testwd[ind[i]].equals("\u0964")){
		   					//		obttags[ind[i]] = "VAUX";
		   					//	}
		   						
	   					}
	   				}
	   			}
	   		}
   		}
   	}
   	return ttg;
   }
   public void getTestfeature(String[] sent, int notest, String[] testtag, String[] testword){
   	//System.out.println("Test sentence is :");
   	double[] temp1 = new double[3*testtag.length];
   	double[][] temp2 = new double[notest][3*testtag.length];
   	int[] tconfusedword = new int[notest];
   	//temp2 = getobservationMatrix();
   	String position[] ;
   	int count1 = 0;
   	int posindex[] = new int[notest];
   	int trr;
   	int count=0;
   	int n = 0;
   	////System.out.println("no of test patterns = "+notest+" testtag.len="+testtag.length);
   	/*for(int l = 0 ; l < numberPatterns ; l++){
   		for(int m = 0 ; m < 117 ; m++){
		   	//System.out.print("  trainFeatures["+l+"]["+m+"] = "+trainFeatures[l][m]);
		}
		//System.out.println();
	}*/
   	for(int i = 0 ; i < notest ; i++){
   	   		////System.out.print("sent["+i+"]=  "+sent[i]);
   		position = RegionMatchesDemoTest(sent[i], testword, i); 
   		if(alaram != 0){
   			tconfusedword[n] = i;
   			//System.out.println("confusedword["+n+"]="+i);
   			n++;
   		}
   	//	//System.out.println("position["+0+"]=  "+position[0]);
   		//for(int j=0 ; j < 5 ; j++){
	   		////System.out.println("i= "+i+"  test position["+j+"]="+position[j]);
	   	//	if( position[j] != null){
	 					////System.out.println("test position["+j+"]="+position[j]);
	 					//indx = 0;
		 				//for(int q = 0 ; q < testtag.length; q++){
		 				//	if(position[j].equals(testtag[q])){
		 				//		posindex[count] = q;
		 				//		count1 = i;
		 						//trr = (int)position[j];
		 //						//System.out.println("test index["+count+"]="+posindex[count]+" i="+i);
		 				//		temp1 = getFeatureTest(notest, testtag.length, count1, q, observationMatrix );
		 //						for(int k = 0 ; k < 3*testtag.length ; k++){
		 						//	//System.out.print("temp1["+k+"]=  "+temp1[k] +"  i="+i+" k = "+k);
			 					//	temp2[i][k]=trainFeatures[trr][k];
		//	 						temp2[i][k] = temp1[k];
		//	 						//System.out.println("extractedTestfeaturesfinal["+i+"]["+k+"]="+temp2[i][k]);
		//	 					}
		 				//	}
		 				//}
		 //	}
	   		
	   	//}
	   	count++;
   	}
   	   	//System.out.println();
   	   	//System.out.println("HI");
   	//for(int i = 0 ; i < testtag.length ; i++){
   		////System.out.print("  "+testtag[i]);
   	//}
   	//System.out.println();
   	//for(int i = 0 ; i < totalWords ; i++){
   	//	//System.out.print("  "+testword[i]);
   	//}
   	
   	//return temp2;
   	confusedword = tconfusedword;
   	//System.out.println("** confusedword[0]="+confusedword[0]);
   }
   public int getalaram(){
   	return alaram;
   }
   public double[][] getTestF(){
   	//boolean flag = false;
   	//if(alaram != 0)
	//{
		/*for(int i = 0 ; i < testFeatures1.length ; i++){
   			if(testFeatures1[0][i] != 0)
   				flag = true;
	   	}
   		if(!flag)
	   		return testFeatures;
		else
			return testFeatures;*/
	//}
	return testFeatures;
   }

   public String[] getfreqpos(){
    for(int i = 0 ; i < 50 ; i++){
                  //for(int j=0; j < freqposition[i].length ; j++ ){
                    System.out.println("taggrnn freqposition["+i+"]="+freqpos[i]);
                  //}
                }
    return freqpos;
   }

   public double[][] getTest2F(){
   	return testFeatures1;
   }
   
   public double[] getFeatureTest(int stwd, int ttag, int row, int col, double[][] obsfeature ){
   	////System.out.println("\n FEATURES PROBABILITY TABLE : ");
   	double[][] trans = new double[ttag][ttag];	
	double[][] feature = new double[totalWords][ttag];
	//feature[row][col] = obsfeature;
	double[][] featuretrans = new double[totalWords][ttag*2];
	double[][] extractedFeatures = new double[stwd][ttag*3];
	double[] tem = new double[ttag*3];
	//ExtractedFeatures = new double[stwd][ttag*3];
	////System.out.println("Test stwd = "+stwd+" ttag="+ttag+" row="+row+" col "+col+ "obsfeature="+obsfeature);
	trans = getTransitionMatrix();
	for(int m = 0 ; m < 2*ttag ; m++){
		if(m < ttag)
			featuretrans[row][m] = transitionMatrix[col][m];
		else
			featuretrans[row][m] = transitionMatrix[m-ttag][col];
	}
	
	//for(int p = 0 ; p < 2*ttag ; p++){
		////System.out.println("featuretrans["+col+"]["+p+"]="+featuretrans[col][p]);
	//}
   	//for(int m = 0 ; m < stwd ; m++ ){
   	//System.out.println("Observation matrix Test : ");
   	//for(int m = 0 ; m < stwd ; m++){
   		for(int n=0 ; n < ttag; n++){
		// //System.out.print("\t"+feature[row][n]);
	  		//if(row == 1){
		   		////System.out.print("\t"+obsfeature[n][row+1]);
				feature[row][n] = obsfeature[n][row+1];
			//}
			//else{
			//	//System.out.print("\t"+obsfeature[n][(row+1)/2]);
			//	feature[row][n] = obsfeature[n][(row+1)/2];
			//}
	  	}
   	//}
   	//System.out.println();
   	//}
   	// FEATURES 1. EMISSION COLUMN 2. TRANSITION ROW 3. TRANSITION COL
   	//for(int x = 0; x < stwd ; x++){
   	for(int y = 0 ; y < 3*ttag ; y++){
   		if(y < ttag)
			extractedFeatures[row][y] = feature[row][y];
		else
			extractedFeatures[row][y] = featuretrans[row][y-ttag];
   	}
   //	ExtractedFeatures = extractedFeatures;
   	/*try{
	   	annFile.write("\n");
	}
	catch(IOException ie){}*/
   	for(int x = 0 ; x < 3*ttag ; x++){
   	//	//System.out.println("**extractedTestfeatures["+row+"]["+x+"]="+extractedFeatures[row][x]);
   		tem[x] = extractedFeatures[row][x];
   	//	ExtractedFeatures[row][x] = extractedFeatures[row][x];
   		//System.out.println("tem["+x+"]="+tem[x]);
   		/*try{
   			
	   		annFile.write(" "+extractedFeatures[row][x]);
	   	}
	   	catch(IOException ie){}*/
   	}
   	//}
//   	double[][] feature = new double[ttag][stwd+1];
	return tem;
   }
   public double[] getFeature(int stwd, int ttag, int row, int col, double obsfeature ){
   	////System.out.println("\n FEATURES PROBABILITY TABLE : ");
   	double[][] trans = new double[ttag][ttag];	
	double[][] feature = new double[stwd][ttag];
	double[][] temp2;
   	//temp2 = getobservationMatrix();
	//feature[row][col] = obsfeature;
	double[][] featuretrans = new double[stwd][ttag*2];
	double[][] extractedFeatures = new double[stwd][ttag*3];
	double[] tem = new double[ttag*3];
	//ExtractedFeatures = new double[stwd][ttag*3];
	////System.out.println("Train stwd = "+stwd+" ttag="+ttag+" row="+row+" col "+col);
	trans = getTransitionMatrix();
	for(int m = 0 ; m < 2*ttag ; m++){
		if(m < ttag)
			featuretrans[col][m] = trans[col][m];
		else
			featuretrans[col][m] = trans[m-ttag][col];
	}
	
	for(int p = 0 ; p < 2*ttag ; p++){
		////System.out.println("featuretrans["+col+"]["+p+"]="+featuretrans[col][p]);
	}
   	//for(int m = 0 ; m < stwd ; m++ ){
   	for(int n = 0 ; n < ttag ; n++){
   		////System.out.print("\t"+observationMatrix[col][n]);
	  			 feature[col][n] = observationMatrix[col][n];
		// //System.out.print("\t"+feature[row][n]);
	  			 ////System.out.print("\t"+obsMatrix[m][n]);
   	}
   	//System.out.println();
   	//}
   	//feature = getobservationMatrix();
   	// FEATURES 1. EMISSION COLUMN 2. TRANSITION ROW 3. TRANSITION COL
   	//for(int x = 0; x < stwd ; x++){
   	for(int y = 0 ; y < 3*ttag ; y++){
   		if(y < ttag)
			extractedFeatures[row][y] = feature[col][y];
		else
			extractedFeatures[row][y] = featuretrans[col][y-ttag];
   	}
   //	ExtractedFeatures = extractedFeatures;
   	/*try{
	   	annFile.write("\n");
	}
	catch(IOException ie){}*/
   	for(int x = 0 ; x < 3*ttag ; x++){
   		////System.out.println("extractedfeatures["+row+"]["+x+"]="+extractedFeatures[row][x]);
   		tem[x] = extractedFeatures[row][x];
   	//	ExtractedFeatures[row][x] = extractedFeatures[row][x];
   //		System.out.println("tem["+x+"]="+tem[x]);
   		/*try{
   			
	   		annFile.write(" "+extractedFeatures[row][x]);
	   	}
	   	catch(IOException ie){}*/
   	}
   	//}
//   	double[][] feature = new double[ttag][stwd+1];
	return tem;
   }
   
   public double[] getFeature1(int stwd, int ttag, int row, int col, double[][] obsfeature ){
   	////System.out.println("\n FEATURES PROBABILITY TABLE : ");
   	double[][] trans = new double[ttag][ttag];	
	double[][] feature = new double[stwd][ttag];
	double[][] temp2;
   	//temp2 = getobservationMatrix();
	//feature[row][col] = obsfeature;
	double[][] featuretrans = new double[stwd][ttag*2];
	double[][] extractedFeatures = new double[stwd][ttag*3];
	double[] tem = new double[ttag*3];
	//ExtractedFeatures = new double[stwd][ttag*3];
	////System.out.println("Train stwd = "+stwd+" ttag="+ttag+" row="+row+" col "+col);
	trans = getTransitionMatrix();
	for(int m = 0 ; m < 2*ttag ; m++){
		if(m < ttag)
			featuretrans[row][m] = trans[col][m];
		else
			featuretrans[row][m] = trans[m-ttag][col];
	}
	
	for(int p = 0 ; p < 2*ttag ; p++){
		////System.out.println("featuretrans["+col+"]["+p+"]="+featuretrans[col][p]);
	}
   	//for(int m = 0 ; m < stwd ; m++ ){
   	for(int n = 0 ; n < ttag ; n++){
   		if(row == 1){
	   		////System.out.print("\t"+obsfeature[n][row]);
			feature[row][n] = obsfeature[n][row];
		}
		else{
			////System.out.print("\t"+obsfeature[n][(row+1)/2]);
			feature[row][n] = obsfeature[n][(row+1)/2];
		}
		// //System.out.print("\t"+feature[row][n]);
	  			 ////System.out.print("\t"+obsMatrix[m][n]);
   	}
   	////System.out.println();
   	//}
   	//feature = getobservationMatrix();
   	// FEATURES 1. EMISSION COLUMN 2. TRANSITION ROW 3. TRANSITION COL
   	//for(int x = 0; x < stwd ; x++){
   	for(int y = 0 ; y < 3*ttag ; y++){
   		if(y < ttag)
			extractedFeatures[row][y] = feature[row][y];
		else
			extractedFeatures[row][y] = featuretrans[row][y-ttag];
   	}
   //	ExtractedFeatures = extractedFeatures;
   	/*try{
	   	annFile.write("\n");
	}
	catch(IOException ie){}*/
   	for(int x = 0 ; x < 3*ttag ; x++){
   		////System.out.println("extractedfeatures["+row+"]["+x+"]="+extractedFeatures[row][x]);
   		tem[x] = extractedFeatures[row][x];
   	//	ExtractedFeatures[row][x] = extractedFeatures[row][x];
   		////System.out.println("tem["+x+"]="+tem[x]);
   		/*try{
   			
	   		annFile.write(" "+extractedFeatures[row][x]);
	   	}
	   	catch(IOException ie){}*/
   	}
   	//}
//   	double[][] feature = new double[ttag][stwd+1];
	return tem;
   }
   public void getStart(String stt, int ct){
   	Finalstart[ct] = stt;
   	////System.out.println("Finalstart["+ct+"]="+Finalstart[ct]);
   }
   public double[][] getDefaulter(){
   	return defaulter;
   	////System.out.println("Finalstart["+ct+"]="+Finalstart[ct]);
   }
   
   public int[] getconfusedword(){
	//System.out.println("     confusedword [0]= "+confusedword[0]);
   	return confusedword;
   }
   public String[][] getDefaulterstring(){
   	return defaulterstring;
   	////System.out.println("Finalstart["+ct+"]="+Finalstart[ct]);
   }
   public String getdinfo(String s1, String p1, String p2){
   	String tg;
   	int indexs1 = 0 , indexp1 = 0, indexp2 = 0;
   	//System.out.println("s1="+s1+" p1="+p1+"  p2="+p2);
   	for(int i = 0 ; i < TGSET.length ; i ++){
	   	if(s1.equals(TGSET[i]))
	   	{
	   	
			indexs1 = i ;   	
	   	}
	}
	for(int i = 0 ; i < TGSET.length ; i ++){
	   	if(p1.equals(TGSET[i]))
	   	{
			indexp1 = i ;   	
	   	}
	}
	for(int i = 0 ; i < TGSET.length ; i ++){
	   	if(p2.equals(TGSET[i]))
	   	{
			indexp2 = i ;   	
	   	}
	}
	double[][] tm = new double[totaltag][totaltag];
	tm = getTransitionMatrix();
	System.out.println(" 1tf["+indexs1+"]["+indexp1+"]="+tm[indexs1][indexp1]+"   "+" 2tf["+indexs1+"]["+indexp2+"]="+tm[indexs1][indexp2]);
	if(tm[indexs1][indexp1] > tm[indexs1][indexp2])
	{
		//System.out.println(" 1tf["+indexs1+"]["+indexp1+"]="+tm[indexs1][indexp1]);
		tg = TGSET[indexp1];
	}
	else{
		//System.out.println(" 2tf["+indexs1+"]["+indexp2+"]="+tm[indexs1][indexp2]);	
		tg = TGSET[indexp2];
	}
   	//System.out.println("tg="+tg);
   	return tg;
   }
    public String[] RegionMatchesDemo(String findMe, String[] file) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
       String[] positions = new String[50000];  // Maximum number of tags to each words = 5
        int index = 0;
        int count = 0;
        int t = 0;
        String[] start = new String[50000];
        String g = findMe;
    //    int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
     //   System.out.println("file length = "+file.length);
      //   System.out.println("Find Me = "+findMe+"no of Tags="+noWords);
        
        boolean foundIt = false;
        for(int i = 0; i < noWords; i++) {
           try{
	         //   System.out.println("i ="+i+"file["+i+"]="+file[i]);
	    	if(file[i].equals(null)){
	    		i--;
	    		//throw new NullPointerException();
	    		break;
	    		
	    	}
	    	else{
        	   if(file[i].equals(findMe)) {
           	
             //  //System.out.println("check"+findMe);    
              // //System.out.println(" g="+g);   
	              foundIt = true;
            // //System.out.println("file["+(i+1)+"]="+file[i+1]);
            	
        	      index = i+1;
	              positions[count] = file[index];
        	     // //System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
	              if(positions[count].equals("PUNC")){
	              	if(i < (noWords-2))
		              	start[count] = file[index + 2]; 
	             // 	//System.out.println("start["+count+"]="+start[count]);
	        	      	getStart(start[count], count);
	              }
        	      count++;
              
              //g = searchMe.substring(i, i + findMeLength);
             // break;
          	}
              }
         }
           catch(NullPointerException e) 
        { 
          //  //System.out.print("NullPointerException Caught"); 
        }
          
           //return searchMe.substring(i, i + findMeLength);
          // break;
        }
        if (!foundIt){
            // //System.out.println("No match found.");
           // return "NULL";
           return positions;
        }
        // //System.out.println("INDEX="+index);
       // return file[index];
       return positions;
    }
    
    
    
    public void getTrainFeatures(GRNN5 g){
	trainFeatures = g.getTrainFeatures();
	/*for(int i = 0 ; i < noWords ; i++){
		for(int j = 0 ; j < 117 ; j++){
			//System.out.print("  trainfeatures["+i+"]["+j+"]="+trainFeatures[i][j]);
		}
		//System.out.println();
	}*/
    }
    public String[] RegionMatchesDemoTest(String findMe, String[] file, int testwdindex) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
       confusedword = new int[500];
       int trainindex = 0;
       //System.out.println();
      // //System.out.println("HI IT IS REGIONMATCHESDEMO TEST: ");
       String[] positions = new String[50000];  // Maximum number of tags to each words = 5
       int[] freqpos1 = new int[50];   // Maximum words in a sentence is 50
        int index = 0;
        int previndex = 0;
        int count = 0;
        int t = 0;
        int l = 0;
        alaram = 0;
       // int n ;
        
       // int confused = 0;
        String[] start = new String[50000];
        
        String g = findMe;
        int frequency = 0;   // Frequency of one word having rare appearance in train database
        rareFreq = new double[500][117];    // Assuming max of 50 words in test sentence which have rare frequencies
  //      rareFreqpos = new int[50];
    //    rareFreqTrainpos = new int[50];   // Training set position of the matched word
        
    //    int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
        System.out.println("file length = "+file.length);
         System.out.println("**Find Me = "+findMe+"no of Tags="+totalWords+"   testwdindex="+testwdindex);
       // System.out.println(" file["+8926+"]="+file[8926]);
        boolean foundIt = false;
        for(int i = 0; i < totalWords; i++) {
          //System.out.println("i ="+i+"file["+i+"]="+file[i]+" fineMe="+findMe);
           if(findMe.equals(file[i])) {
             foundIt = true;
            // alaram = 0;
             System.out.println("file["+(i)+"]="+file[i]);
             trainindex = i;
             frequency++;
              if(count == 1){
              	previndex = index;
              }
              index = i+1;
              positions[count] = file[index];
              System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
              try{
              for(int k = 0 ;k < 117 ; k++){
                    //        System.out.println(" trainFeatures ["+(index - 1)/2 +"]["+k+"]="+trainFeatures[(index-1)/2][k]+" testfeature ["+testwdindex+"]["+k+"]="+testFeatures[testwdindex][k]);              

                   testFeatures[testwdindex][k] = trainFeatures[(index-1)/2][k];
                   
                //   System.out.println("  "+testFeatures[testwdindex][k]);
                   if (count != 0)
		   {
		   	if(positions[count].equals(positions[count - 1])){
              			testFeatures[testwdindex][k] = trainFeatures[(index-1)/2][k];
              			////System.out.println(" bad alaram = "+alaram); 
              			
              		}
              		else{ 
		              	testFeatures1[testwdindex][k] = trainFeatures[(previndex-1)/2][k];
		              	alaram=1;
//		              	cp[n] = testwdindex; 
		         //     	//System.out.println("l="+l+" testwdindex="+testwdindex+"   defter["+l+"][0]="+defaulter[l][0]);
		              	defaulter[testwdindex][0] = testwdindex;
		              	defaulter[testwdindex][1] = (index-1)/2 ;
		              	defaulter[testwdindex][2] = (previndex-1)/2 ;
		              	defaulterstring[testwdindex][0] = positions[count];
		              	defaulterstring[testwdindex][1] = positions[count-1];	
		              //	if(testwdindex != 0){
				//      confused = testwdindex;
			     	       // //System.out.println(" **n="+n);
			     	  //      n++;
			     //	}
		              		              	
		             // 	defaulter[l][3] = position;
		              	
	              		//System.out.println(" alaram = "+alaram+" dflter["+l+"]["+0+"]="+defaulter[l][0]+"  dflter["+l+"]["+1+"]="+defaulter[l][1]);              
	              		//l++;
	                }
	           }
	         //  l++;
	      }
      }
      catch(ArrayIndexOutOfBoundsException ae){};
	      
	      l++;
              if(positions[count].equals("PUNC") ){
              	if(i < (noWords-2))
	              	start[count] = file[index + 2]; 
	             // 	//System.out.println("start["+count+"]="+start[count]);
	              	getStart(start[count], count);
              }
              count++;
            //  confusedword[n] = confused ;
             // n++;
              //g = searchMe.substring(i, i + findMeLength);
             // break;
          }
         
//n++;
          
           //return searchMe.substring(i, i + findMeLength);
          // break;
        }
        
         System.out.println(" frq="+frequency+"  index="+trainindex);
	      freqpos1[testwdindex] = frequency;
        freqpos[testwdindex] = "nl";
        
	     // n++; 
	      if(frequency >0 && frequency < 3){
			System.out.println("hi");	      		
	      		//rareFreq = testFeatures;
	      		rareFreqpos[frq] = testwdindex;
	      		System.out.println(" rare frequency  words = "+frq+"   rarefreqpos["+frq+"]="+rareFreqpos[frq] +"    index = "+trainindex);
	      		/*if(frequency == 0){
	      			allunknownpositions[frq] = testwdindex;
	      		}*/
	      		rareFreqTrainpos[frq] = trainindex;
	      		System.out.println(" rare frequency  words = "+frq+"   rarefreqpos["+frq+"]="+rareFreqpos[frq] +"    index = "+trainindex);
	      		frq++; 
	      }
        else if(frequency  >=  3){
          freqpos[testwdindex] = positions[count -1];
        System.out.println("freqpos["+testwdindex+"]="+freqpos[testwdindex]);
        }
        else{
          freqpos[testwdindex] = "nl";
        }
        if (!foundIt){
             System.out.println("No match found."+findMe);
             if(findMe.equals("|") || findMe.equals("\u0964"))
             	positions[0] = "ok";
             else
             	positions[0] = "nll";
             	
             System.out.println(" 123 positions["+0+"]="+positions[0]);
            // freqpos = freqpos1;
           // return "NULL";
           return positions;
        }
        // //System.out.println("INDEX="+index);
       // return file[index];
       // freqpos = freqpos1;
       return positions;
    }
    
    public String RegionMatchesDemoTestNew(String findMe, String[] file, String[] tfile, int testwdindex) {
    //public static void main(String[] args) {
       // String searchMe = "Green Eggs and Ham";
       confusedword = new int[50];
       //System.out.println();
       
      System.out.println("HI IT IS REGIONMATCHESDEMO TEST: ");
       String[] positions = new String[50000];  // Maximum number of tags to each words = 5
        int index = 0;
        int previndex = 0;
        int count = 0;
        int t = 0;
        int l = 0;
        alaram = 0;
        String obttags = "hi";
       // int n ;
        totalWords = file.length;
       // int confused = 0;
        String[] start = new String[50000];
        
        String g = findMe;
    //    int searchMeLength = searchMe.length();
        int findMeLength = findMe.length();
        // //System.out.println("file length = "+file.length);
         //System.out.println("**Find Me = "+findMe+"no of Tags="+totalWords+"   testwdindex="+testwdindex);
        
        boolean foundIt = false;
        for(int i = 0; i < totalWords; i++) {
          //  //System.out.println("i ="+i+"file["+i+"]="+file[i]+" fineMe="+findMe);
           if(findMe.equals(file[i])) {
             foundIt = true;
            // alaram = 0;
             //System.out.println("file["+(i)+"]="+file[i]);
              if(count == 1){
              	previndex = index;
              }
              index = i;
              positions[count] = tfile[index];
              obttags = positions[count]; 
              //System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
             // //System.out.println("trainFeatures["+(index - 1)/2 +"]="+trainFeatures[(index-1)/2][0]+"testfeature["+testwdindex+"]["+0+"]="+testFeatures[testwdindex][0]);              
              
              
	      //System.out.println("old alaram = "+alaram+" testwdindex = "+testwdindex);
	      
	     // n++; 
	      
	      l++;
              if(positions[count].equals("PUNC") ){
              	if(i < (noWords-2))
	              	start[count] = file[index + 2]; 
	             // 	//System.out.println("start["+count+"]="+start[count]);
	              	getStart(start[count], count);
              }
              count++;
            //  confusedword[n] = confused ;
             // n++;
              //g = searchMe.substring(i, i + findMeLength);
             // break;
          }
//n++;
          
           //return searchMe.substring(i, i + findMeLength);
          // break;
        }
        if (!foundIt){
             //System.out.println("No match found.");
             positions[0] = "nll";
             //System.out.println("positions["+0+"]="+positions[0]);
           // return "NULL";
          // return positions;
        }
        // //System.out.println("INDEX="+index);
       // return file[index];
       return obttags;
    }
}
