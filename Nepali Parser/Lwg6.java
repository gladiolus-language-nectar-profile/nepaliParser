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
// PROGRAM TAKES PART OF SPEECH TAGGED INPUT NEPALI SENTENCE FROM ANNOUTPUT.TXT AND 
// PRODUCES THE PARSED TREE IN LOGO.PNG

import java.applet.Applet;
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

public class Lwg4
{
    public static int totaltag = 39;
    public static int maxwords = 500;
    public static int maxobswords = 300;
    public static String[] vbs;
    public static int finalend = 0;
    public static int ekoexist = 0;
   // public static double[][] transitionMatrix = new double[totaltag][totaltag];
   // public static double[][] observationMatrix = new double[totaltag][300];
    public static String[] Words = new String[maxwords];
    public static int noWords = 0;
    public static int noTags = 0;
    public static int MaxSG = 5040;
    public static  int nKarkas = 8;
    public static int ntKarkas = 7;
    public static int maxKarana = 4;
    public static String filename ;
    public static String thread;
    public static int sentnoWords = 0;
    public static int indexkarana = 0;
    public static int indexkarta = 0;
    public static int noVbkt = 1;
    public static int sangexist = 0;
    public static String output;
    public static String[] sentWords = new String[maxobswords];
    public static String[] sentTotalwords = new String[2*maxobswords];
    public static String[] OriOutput = new String[3000];
    public static String[] sentwords;
    public static String[] sentwords1;
    public static String[] karana;
    public static String[] karta;
    public static String[][] Presence = new String[8][3]; 
    public static String[][] Karaka = new String[8][3];
    public static String[][] vibh = new String[8][3];
//    public static String[][] chunks = {"N","PR","DM","V","JJ","RB","PSP","CC","RP","QT","RD"};
    public static String[][] N ;    // Maximum 5 noun groups and each group has 3 words are assumed
    public static String[][] TTN = new String[15][3];    // Maximum 5 noun groups and each group has 3 words are assumed
     public static String[][] Vph = new String[15][3];    // Maximum 5 Verb groups and each group has 3 words are assumed
    public static String[][] TN = new String[15][3];    // Maximum 5 noun groups and each group has 3 words are assumed
     public static String[] NP = new String[15]; // Noun Phrase
     public static String[] TNP = new String[15]; // Tag Noun Phrase
    public static String[] VP = new String[5]; // Verb Phrase
    public static String[] TVP = new String[5]; // Tag Verb Phrase
    public static String[][][] Actualvibhakti;
    public static String[] Actualvibhaktilist;
    public static String Actualvbt;    
    public static int[][][] EDGES;
    public static int danger = 0;
    public static int[] Deg =  new int[MaxSG];
    public static String[] nounTags;
    public static String preNN;
    public static String wordnetfile;
    public static int finalcountParse = 0;
    public static int totalverbs = 10;
    public static int semicount = 0;
    public static int iteration = 0;
 //   public static int[][] Presence = new int[4][3];        
    public static int frameInd;
    public static int[][][] semifinalmatching;
    public static int[][][] semisecondsfm;
    public static int[][][] finalsemisecondsfm;
    public static int [][][] finalsecondmatching;
    public static int[][][] golden;
    public static int[][][] egolden;
    public static int[][][] goldenfinal;
    public static int g = 0;
    public static int directkrk = 0;
    public static String k1snoun = "null1"; 
    public static boolean k1sexist = false;
    public static boolean k1texist = false;
    public static boolean k2pexist = false;
    public static boolean addone = false;
    public static int k2exist = 0;
    public static boolean k7texist = false;
    public static int k1sposition = 0;
    public static int k2pindex;
    public static int k7tindex;
    public static int[][] duplicate;
    public static int[][] intramatrix;
    public static int[] verbPosition = new int[3];
    public static int[]  seperatepos = new int[3];
    public static String temptag;
    public static String temptag1;  
    public static int npcount = 0;
    public static int nllength = 0;
    public static int nlrows = 0;    
    public static int QWexist = 100;
    //public static int noVerbs = 1;
    public static int nlcols = 0;    
    public static int[][] npfinal = new int[15][16]; 
    public static String[][] npwords = new String[5][60]; // maximum words assumed to be 30 in a sentence
    public static String[][] nptags = new String[5][60]; // maxinum 5 verbs in a sentence
    public static int ct1 = 0;
    public static int ct2 = 0;
    public String[] oldparserelation = new String[10];
    public String[][] oldparserelation1 = new String[5][30];    
    public int[][] npextduplicate = new int[15][16]; // Assuming max 4 verbs in a sentences
    public int[][] extduplicate = new int[15][16];
      String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
    String[] postagset = {"Noun","Proper noun","Noun","Pronoun","Pronoun","Pronoun","Adjective","Verb","Verb","Verb","Verb","Verb","Verb","Demonstrative","Demonstrative","Demonstrative","Demonstrative","Adverb","Vibhakti","Vibhakti","Vibhakti","Vibhakti","Conjuction","Conjuction","Vibhakti","Question","Classifier","Interjection","Intensifier","Quantity","Quantity","Foreign Word","Particle","Determinar","Unknown symbol","PUNC","AB","ALPH","Symbol"};

    //public final String[] tpst;
    
    
    Tag ftag;
    public  Lwg4(String s, String[] f){
    }
   public  Lwg4(){}
   public static void main(String args[])throws IOException 
   {
      String output = args[0];
       String glyph = "\u0926\u0947\u0916\u093F";
       int nonouns = 0;
       //String[] presence = new String[];
    String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
    String[] postagset = {"Noun","Proper noun","Noun","Pronoun","Pronoun","Pronoun","Adjective","Verb","Verb","Verv","Verb","Verb","Verb","Proposition","Proposition","Proposition","Proposition","Adverb","Vibhakti","Vibhakti","Vibhakti","Vibhakti","Conjuction","Conjuction","Vibhakti","Question","Conjuction","Interjection","Interjection","Quantity","Quantity","Adverb","Adverb","Determinar","Unknown symbol","PUNC","AB","ALPH","Symbol"};
       //String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM","STOP"};
     //  for(int i = 0 ; i < totaltag ; i++)
            //System.out.println("tagset["+i+"]="+tagset[i]);
       for(int i = 0 ; i < 5 ; i++){
          TNP[i] = "null2";
          //System.out.println("$****TNP["+i+"]="+TNP[i]);
       }
       Lwg4 t = new Lwg4();
       
       final String[] tags;
       String[] morphs;
      // int finalcountParse = 0;
       String[] verbs = {"VF","VINF","VAUX","VMNE","VMO","VMKO"};
       String[] demo = {"DM","DMR","DMD","DMI","CCD","CCS"};
       String[] Verbs ;
       String[] V;
       String[] nouns = {"NN","NNP","NST","PRP","PRL","PRF"};
       String[] seperator = new String[5];   //maximum seperator words are assumed 5 and the length of the sentence is 25
     //  int semicount = 0;
       
       
       int npcount = 0;
       String vframe;
       String[] Nouns;
       String[] Vibhakti = {"\u0932\u0947","\u0932\u093E\u0908", "\u092E\u093E", "\u0915\u093E", "\u0915\u094B", "\u0926\u0947\u0916\u093F", "\u092C\u093E\u091F","\u0938\u092E\u094D\u092E", "\u0938\u0902\u0917"};
      // String wordnetfile;
//       String[] Vibhakti = {}
       String[][][] vibhakti ;
     //  String[][] N = new String[5][3];
       karana = new String[maxKarana];
       ReadXMLFile xm = new ReadXMLFile();       
      // Chunker ch ;
//Actualvibhaktilist = new String[nKarkas];
      // t.readFile(args[0], args[1],args[2], glyph);
       t.readFile("annoutput.txt", "display.txt" ,"display.txt", glyph);
       wordnetfile = "wordnet.txt";
       String filename = "display.txt";
       String filename1 = "abc.jpg";
       nounTags =  new String[noWords/2];
       int[] nPhaseInd = new int[noWords/2];
       String[] nPhase = new String[noWords/2];
       int[] vPhaseInd = new int[noWords/2];
       String[] vPhase = new String[noWords/2];
       String[][] nlsentence1final = new String[seperator.length][20];   // length of the projective sentence is assumed 20
     //  int frameInd;
      // tags = t.getTags(sentwords1);
       //Words = new String[tags.length];
       //sentwords = new String[tags.length];
      // sentwords = new String[noWords/2];
   //   //System.out.println("*****sentwords1["+0+"]="+sentwords1[0
    //noWords = 14;
       //System.out.println("No of Morphs = "+(noWords/2));
       morphs = new String[noWords/2];
       Actualvibhakti= new String[noWords/2][noWords/2][2];     
       Actualvibhaktilist = new String[noWords/2];
       for(int s = 0 ; s < noWords/2 ; s++){
          Actualvibhaktilist[s] = "null";
          //System.out.println("Actualvibhaktilist["+s+"]="+Actualvibhaktilist[s]);
       }

       morphs = t.getMorphs(sentwords);
       tags = t.getTags(sentwords1);
       int cnt1 = 0;
       try{
       for(int i = 0 ; i < tags.length ; i++){
        if(tags[i].equals("PRL") || tags[i].equals("PRP") ||tags[i].equals("PRF")){
          if(tags[i+1].equals("NN")){
            if(tags[i+2].equals("NNP")){
              //System.out.println(" Welcome k1sexist");
              k1sexist = true;
              break;
            }

          }
        }
       }
     }
     catch(NullPointerException nullPointer){}

     try{
       for(int i = 0 ; i < tags.length ; i++){
        if(tags[i].equals("NN") || tags[i].equals("NNP")){
          if(tags[i+1].equals("PSPO")){
            if(morphs[i+1].equals("\u092E\u093E")){
              //System.out.println(" Welcome k2pexist");
              k2pexist = true;
              break;
            }

          }
        }
       }
     }
     catch(NullPointerException nullPointer){}

    System.out.println(" outside Welcome  k2pexist ");
    xm.readFile1(wordnetfile);
    String[] wordnettag = new String[100];
    String[] wordnetdata = new String[100];
    wordnettag = xm.getwordnet();
    wordnetdata = xm.getwordnetword();
    int k2pcount = 0;
      try{
      	//int k2pcount1=0;
       for(int i = 0 ; i < tags.length ; i++){
        if((tags[i].equals("NN") && tags[i+1].equals("NN")) || (tags[i].equals("NNP") && tags[i+1].equals("NN"))){
        	k2pcount++;
      //    if(tags[i+1].equals("PSPO")){
           for(int s = 0 ; s < wordnetdata.length;s++){
            //System.out.println("wordnetdata["+s+"] ="+wordnetdata[s]);
            if(morphs[i+1].equals(wordnetdata[s]) && wordnettag[s].equals("PLACE")){

              System.out.println(" Welcome k2pexist");
              k2pindex = k2pcount-1;
              k2pexist = true;
              break;
            }
          }

        //  }
        }
        
        System.out.println("*** tags["+i+"]="+tags[i]);
        if(tags[i].equals("NN") ){
        	k2pcount++;
        	System.out.println("k2pcount="+k2pcount);
      //    if(tags[i+1].equals("PSPO")){
           for(int s = 0 ; s < wordnetdata.length;s++){
           // System.out.println("morphs["+i+"] ="+morphs[i]+"   wordnetdata["+s+"]="+wordnetdata[s]+"  wordnettag["+s+"]="+wordnettag[s]);
            if(morphs[i].equals(wordnetdata[s]) ){
            	System.out.println("why");
            	if( wordnettag[s].equals("PLACE")) {
              		System.out.println(" Welcome again k2pexist");
              		k2pindex = k2pcount-1;
              		k2pexist = true;
              		break;
              	}
            }
          }

        //  }
        }

       }
     }
     catch(NullPointerException nullPointer){}

     
     //System.out.println(" outside Welcome CCS k1sexist ");

  try{
  for(int i = 0 ; i < tags.length ; i++){
    //System.out.println(" outside Welcome CCS k1sexist morphs["+(i+1)+"]="+morphs[i+1]+"  tags["+i+"]="+tags[i]);
        if(tags[i].equals("CCS") || tags[i].equals("SYM") || morphs[i+1].equals("\u002d") || morphs[i+1].equals("\u003b") || morphs[i+1].equals("\u003a")){
          if(tags[i+1].equals("NN") || tags[i+1].equals("NNP")){
          //  if(morphs[i+1].equals("\u002d") || morphs[i+1].equals("\u003b") || morphs[i+1].equals("\u003a")){
              //System.out.println(" Welcome CCS k1sexist");
              k1sexist = true;
              k1sposition = i+1;
              //System.out.println("k1sposition = "+k1sposition);
              danger = 1;
              break;
           // }

          }
        }
       }
     }
     catch(NullPointerException nullPointer){}

    /* try{
       for(int i = 0 ; i < tags.length ; i++){
        if(tags[i].equals("NN") ){
          if(tags[i+1].equals("PSPO")  ){
            if(tags[i+2].equals("NNP")){
              //System.out.println(" Welcome k1sexist");
              k1sexist = true;
              break;
            }

          }
        }
       }
     }
     catch(NullPointerException nullPointer){}*/
       try{
         for(int i = 0 ; i < tags.length ; i++){
            //System.out.println("$****sentwords1["+i+"]="+tags[i]+"  sentwords1 length="+sentwords1.length);
            if(!tags[i].equals("PUNC")){
              if(( tags[i].equals("NN") || tags[i].equals("NNP"))){
                TNP[cnt1]=tags[i];
                //System.out.println("$****TNP["+cnt1+"]="+tags[cnt1]);
                cnt1++;
              //break;
              }
            }
            else
              break;
         }
  }
  catch(NullPointerException nullPointer){}
       int in = 0, ln = 0;
       //System.out.println("IT IS CHUNKER");
       for(int i = 0 ; i < sentwords1.length ; i++){
          //System.out.println("*****sentwords1["+i+"]="+tags[i]);
          try{
            if(tags[i].equals("PRP") || tags[i].equals("PRL")){
              if(tags[i+1].equals("PSPLE") ){
                TTN[in][ln] = morphs[i];
                TTN[in][ln+1] = morphs[i+1];
                in = 1;
              }
            }
          }
          catch(NullPointerException nullPointer){}
          
          //in++;
       }
       
       int nnlength = 0;
       int temp = 0;
       int len = 0;
       int vblength = 0;
       int lenverbphrase = 0;
       String[] ntstart = new String[5];   // max 5 chunks
       int nttg = 0;
       int[] len1 = new int[5] ; // assuming maximum noun chunks is 5
       String[] temptag = new String[sentwords1.length];
       String[] tempmorphs = new String[sentwords1.length];       
       /*if(in == 1){
         for(int i = 0 ; i <  (sentwords1.length-2) ; i++ ){
            temptag[i] = tags[i+2];
            tempmorphs[i] = morphs[i+2];
         }
  }*/
  Chunker ch =  new Chunker(noTags, morphs, tags, k2pexist, k7texist);
        N = ch.getNounChunks();
        TN = ch.getnountag();
        int oldi = 100;
        //nnlength = 1;
        try{
       for(int i = 0 ; i < 15 ; i++){
          //int len2 = 0;
          for(int j = 0 ; j < N[0].length ; j++){
            //System.out.println("N["+i+"]["+j+"]="+N[i][j]+"   oldi="+oldi+"   TN["+i+"]["+j+"]= "+TN[i][j]);
            if(!N[i][j].equals("n") && i != oldi){
              oldi = i;
              
              //len2++;
              len = i+1;
              //len1[i] = len2;
              //System.out.println("len1["+i+"]="+len1[i]);
            }
          }
        }
         
         //System.out.println("in="+in);
        if(in == 1){
          for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 3 ; j++){
              //N[i+1][j] = N[i][j];
              //TNP[i+1][j] = TNP[i][j];
              //if(!N[0][j].equals("n"))
                //N[0][j] = TTN[0][j];
              //TNP[0][j] = TNP[0][j];
            
            }
          }
        }
        }
        catch(ArrayIndexOutOfBoundsException ae){
        
        }
       
       /* if(in == 1){
          len = len + 1;
        }*/
        
        int tg = 0;
        //System.out.println("N.len="+len+"  N["+0+"]["+1+"]="+N[0][1]+"  n[0][0]="+N[0][0]);
      //  //System.out.println("V.len="+lenverbphrase+"  V["+0+"]["+2+"]="+Vph[0][2]+"  V[0][0]="+Vph[0][0]);
       /* for(int p=0;p<len;p++){
          for(int q = 0 ; q < N[p].length;q++){
          
              //System.out.println("N["+p+"]["+q+"]="+N[p][q]);
          }
        }*/
        int covid = 0;
        int countn = 0;
       for(int i1= 0 ; i1 < len ; i1++){
      countn = 0;
      for(int j = 0 ; j < N[0].length ; j++){
        if(N[i1][j].equals("n")){
          countn++;
          if(countn == N[i1].length){
            covid = i1;
            //System.out.println("Covid =" +covid);
          }
        }
        
      }
  }
  if(covid !=0){
    
    for(int i1= covid ; i1 < len ; i1++){
      //countn = 0;
      for(int j = 0 ; j < N[i1].length ; j++){
        N[i1][j] = N[i1+1][j];
        TN[i1][j] = TN[i1+1][j];
        
        
      }
    }
    len--;
  }
        
        try{
            try{
          if(N[0][0].equals("null1")){
            len = 0;
          }
          int con=0;
          int tri = 0;
          String[] Alist = new String[noWords/2];
          
          for(int i = 0 ; i < len ; i++){
            //TNP[i]="n";
           /* for(int s=0; s< noWords/2 ; s++){

            Alist[s] = "hi";
            //System.out.println("Are");
          }*/
            int index = 0;
            int fg = 0;
            String sum = " ";
            String tsum = " ";
            String sum1 = " ";
            String tsum1 = " ";
            int corona = 0;
            String tp1 = "hi";
            k1snoun = "null1";
            len1[i] = N[i].length;
          //  int tri = 0 ;
            //System.out.println("len1["+i+"]="+len1[i]+"  Alist["+i+"]=  "+Alist[i]+"  k2pexist ="+k2pexist);
            int stop = 0;
            for(int j1 = 0 ; j1 < len1[i] ; j1++){
              if(TN[i][j1] != null){
                  stop++;
              }
            }
            
            for(int j = 0 ; j < len1[i] ; j++){
              
              //System.out.println("N["+i+"]["+j+"]="+N[i][j]+"    TN["+i+"]["+j+"]="+TN[i][j]+" stop="+stop);

              

              //if(tags[j+tg-1] =="NN" && tags[j+tg] == "NN") fg =1;
              if(! N[i][j].equals("n")){
                corona = 1;
               // con++;
                if(stop == 1){

                tp1 = "null1";
                //System.out.println(" full stop = "+tp1);
              }
                sum = sum + N[i][j]+" "; 
                sum1 = sum1 + TN[i][j]+" ";
                NP[i] = sum;
                TNP[i] = sum1;
                nPhase[i] = sum1;
               // int tri = 0;
              //  String tp1 ="hi";
                //System.out.println("trial sum1="+sum1+"  tri="+tri);
                String[] ptag = {"PSPLE","PSPLAI","PSPKO","PSPO"};
                for(int p = 0 ; p < ptag.length ; p++){
                    if( TN[i][j].equals(ptag[p])){
                      //System.out.println("ptag["+p+"]="+ptag[p]+"  stop="+stop);
                    //  try{

                         
                        
                      if(stop == 3 ){
                          //System.out.println("bahu");
                         // tp1 = ptag[p];
                          tp1 = N[i][j];
                        //  Alist[con] = ptag[p];
                          //System.out.println(" 0 Alist["+con+"]"+Alist[con]);
                         // con++;
                        }
                        if(stop == 2 ){
                          //System.out.println("bahu");
                         // tp1 = ptag[p];
                          tp1 = N[i][j];
                          //System.out.println(" 1 tp1="+tp1);
                          //Alist[con] = tp1;
                        //  con++;                        
                        }
                        if(stop == 1)
                        {
                          //System.out.println("hi trial"+len1[i]);
                        //  tp1 = null;
                       //   Alist[con] = null;
                        //  con++;
                        }
                        
                    
                    }
                    
                 //con++;
                }
              //  Actualvbt[i] = 
                //System.out.println("projective sum="+sum+"    sum1="+sum1);
                //tsum = tsum + TN[i][j]"   ";
                if(i > 0 && i != (i-1)){
                  if(index == 0 ){//|| fg == 1){
                    
                    //System.out.println("if **sum1="+sum1+"   index="+index+"   tags["+(j+tg-1)+"]="+tags[j+tg-1]+"  tags["+(j+tg)+"]="+tags[j+tg]+"  j="+j);
                    if(tags[j+tg-1].equals("NN") && tags[j+tg].equals("NN")) {
                      //System.out.println("chalo");
                      tags[j+tg]="NNP";
                    }
                    //sum1 = sum1 + tags[j+tg]+" ";
                  }
                  else{
                    //sum1 = sum1 + tags[j+tg]+" ";
                    //System.out.println("if sum1="+sum1);
                  }
                  //tsum1 = tsum1 +
                  if(j >1)
                    k1snoun = tags[j+tg-2];
                  else
                    k1snoun = tags[j+tg-1];
                  //System.out.println("if j="+j+"    tg="+tg);
                  //System.out.println("if tags["+(j+tg-2)+"]="+tags[j+tg-2]);
                }
                else{
                  //sum1 = sum1 + tags[j]+" ";
                  //System.out.println("else tags["+j+"]="+tags[j]);
                  //System.out.println("else j="+j+"    tg="+tg+"   index="+index);
                  if(index == 0){
                    ntstart[nttg] = sum1;
                    //nttg++;
                  }
                  nttg++;
                  
                }
                
                tg++;
                index++;
                fg++;
              //  //System.out.println("tags["+(j+tg-1)+"]="+tags[j+tg-1]+"   tags["+(j+tg-2)+"]="+tags[j+tg-2]+"   fg="+fg);
              //if(tags[j+tg-2] =="NN" && tags[j+tg-1] == "NN" && fg == 1) tags[j+tg] ="NNP";
                
              }
              else{
                //sum = "null2";
                //sum1 = "null2";
              }
              //System.out.println("sum="+sum+"    sum1="+sum1);
//              NP[i] =  N[i][j]  
            }
            //if(corona == 1){
              
           //}
            //System.out.println("*NP["+i+"]="+NP[i]+"   TNP["+i+"]="+TNP[i]+"   tp1="+tp1);
            nPhaseInd[i] = tg;
            //nPhase[i] = sum1;
            //System.out.println("*NPhase["+i+"]="+nPhase[i]+"  Alist["+i+"]="+Alist[i]);
            Alist[i] = tp1;
            if(tp1.equals("hi")) tp1 = "null";
            if(stop == 1)
             Actualvibhaktilist[i] = "null";
           else
            Actualvibhaktilist[i] = tp1;
            //System.out.println("567 Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]);
          }
          
    
         
            }catch(NullPointerException nullPointer){}
  }catch(ArrayIndexOutOfBoundsException ae){}
  
  try{
    if(in == 1){
      for(int i = 0 ; i < (len-1) ; i++){
            /*  NP[i+1] = NP[i];
              NP[0] = morphs[0];
              TNP[i+1] = TNP[i];
              TNP[0] = tags[0];*/
          
            }
    }
  }
  catch(ArrayIndexOutOfBoundsException ae){}
  int vpindex = 0 ;
  // verv chunks
  //int vpindex = 0 ;
  // verv chunks
  String[][] vphtag;
  Vph= ch.getVerbChunks();
       // TN = ch.getnountag();
       vphtag = ch.getverbtag();
        lenverbphrase = ch.getverbphrase();
        int wildcard = 0;

        //tgv = 0;
  //System.out.println("verb chunk Lwg4 Vph["+0+"]["+0+"]="+Vph[0][0]+"    vphtag["+0+"]["+0+"]"+vphtag[0][0]);
  
  if(Vph[0][0].equals("null1")){
    lenverbphrase = 0;
  }
  for(int i = 0 ; i < lenverbphrase ; i++){
    String summ = " ";
    String summ1 = " ";
    for(int j =0 ; j < Vph[i].length ; j++){
      if(!Vph[i][j].equals("n")){
        summ = summ + Vph[i][j]+" ";
        summ1 = summ1 + vphtag[i][j]+" ";
        if(vphtag[i][j].equals("VMKO")){
          //System.out.println("welcome to wildcard j ="+j);
          if(vphtag[i][j+1].equals("NN")){
            wildcard = 1;
            //System.out.println("welcome to wildcard again j = "+j);
          }
        }
        //System.out.println("    summ = "+summ+"  summ1= "+summ1+"  wildcard="+wildcard);
      }
    }
    VP[i] = summ;
          TVP[i] = summ1;
          //System.out.println("*VP["+i+"]="+VP[i]+"   TVP["+i+"]="+TVP[i]);
  }
  
  /*try{
            try{
          if(Vph[0][0].equals("null1")){
            lenverbphrase = 0;
          }
          for(int i = 0 ; i < lenverbphrase ; i++){
            int index = 0;
            int fg = 0;
            String sum = " ";
            String tsum = " ";
            String sum1 = " ";
            String tsum1 = " ";
            len1[i] = Vph[i].length;
            //System.out.println("len1["+i+"]="+len1[i]);
            for(int j = 0 ; j < len1[i] ; j++){
              //System.out.println("Vph["+i+"]["+j+"]="+Vph[i][j]);
              //if(tags[j+tg-1] =="NN" && tags[j+tg] == "NN") fg =1;
              if(! Vph[i][j].equals("n")){
                sum = sum + Vph[i][j]+" "; 
                //System.out.println("sum="+sum);
                //tsum = tsum + TN[i][j]"   ";
                if(i != (i-1)){
                  if(i>0 && index == 0 ){//|| fg == 1){
                    
                    //System.out.println("if **sum1="+sum1+"   index="+index+"   tags["+(j+tg-1)+"]="+tags[j+tg-1]+"  tags["+(j+tg)+"]="+tags[j+tg]+"  j="+j);
                    /*if(tags[j+tg-1].equals("VM") && tags[j+tg].equals("VAUX")) {
                      //System.out.println("chalo");
                      tags[j+tg]="NNP";
                    }*/
                  /*  sum1 = sum1 + tags[j+tg]+" ";
                  }
                  else{
                    sum1 = sum1 + tags[j+tg]+" ";
                    //System.out.println("if sum1="+sum1);
                  }
                  //tsum1 = tsum1 +
                  //k1snoun = tags[j+tg];
                  //System.out.println("if j="+j+"    tg="+tg);
                  //System.out.println("if tags["+(j+tg)+"]="+tags[j+tg]);
                }
                else{
                  sum1 = sum1 + tags[j]+" ";
                  //System.out.println("else tags["+j+"]="+tags[j]);
                  //System.out.println("else j="+j+"    tg="+tg+"   index="+index);
                  
                }
                tg++;
                index++;
                fg++;
              //  //System.out.println("tags["+(j+tg-1)+"]="+tags[j+tg-1]+"   tags["+(j+tg-2)+"]="+tags[j+tg-2]+"   fg="+fg);
              //if(tags[j+tg-2] =="NN" && tags[j+tg-1] == "NN" && fg == 1) tags[j+tg] ="NNP";
                
              }
              //System.out.println("sum="+sum+"    sum1="+sum1);
//              NP[i] =  N[i][j]  
      vpindex++;
            }
            VP[i] = sum;
            TVP[i] = sum1;
            //System.out.println("*VP["+i+"]="+VP[i]+"   TVP["+i+"]="+TVP[i]);
            vPhaseInd[i] = tg;
            vPhase[i] = sum1;
            
            //System.out.println("*VPhase["+i+"]="+vPhase[i]);
          }
          
    
         
            }catch(NullPointerException nullPointer){}
  }catch(ArrayIndexOutOfBoundsException ae){}*/
  
       // nnlength = len;
        vblength = lenverbphrase;
        //System.out.println("nnlength = "+nnlength+"  tg="+tg+"    vblength="+vblength+"  k2pexist ="+k2pexist);
    //    //System.out.println("nnlength = "+nnlength+"  tg="+tg);
       /*   //System.out.println("N.length="+len+"  N[0][0]="+N[0][0]+"  n["+0+"].LEN="+N[0].length);
        for(int i = 0 ; i < 1 ; i++){
          for(int j = 0 ; j < N.length ; j++){
            //System.out.println(" N["+i+"]["+j+"]="+N[i][j]);
            try{
              if(! N[i][j].equals("n") ){
                nnlength = i;
                temp = j;
                if(len == 3){
                  //System.out.println("hi len = "+len);
                  for(int k = 0 ; k < len ; k++){
                    NP[i] = N[i][k]+" "+N[i][k+1]+" "+N[i][k+2];
                    }
                  break;
                }
                else if(len == 2){
                  NP[i] = N[i][len-1] + "   "+ N[i][len] ;
                  break;
                }
                else
                  NP[i] = N[i][j] ;
                //System.out.println("nnlength="+nnlength);
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
          
          try{
            nnlength = len;
            //System.out.println("  NP["+i+"]="+NP[i]+"   noun phase["+i+"]="+nPhase[i]+"   tg="+tg+"   p in="+in+"   nnlength="+nnlength+"   ntstart["+i+"]="+ntstart[i]);
            if(tg > 1)
              nPhase[i]=NP[i];
          }
          catch(ArrayIndexOutOfBoundsException ae){}
        }
       // Nouns = NP;
       Nouns = t.findNouns(tags, sentwords, nouns, Vibhakti, len, ntstart, in);
       // t.findNouns(tags, sentwords, nouns, Vibhakti, len, ntstart, in);
  
//  Final tags;
       //System.out.println("*****sentwords1["+0+"]="+tags[0]);
       //System.out.println("No of Morphs = "+(noWords/2));
       
      // noVerbs= Vph.length;
       //noVerbs = Verbs.length;
       int decesion = 1;
       
       //System.out.println("WELCOME tags.len = "+tags.length+"   TAGS["+0+"]"+tags[0]);
       for(int l = 0 ; l < tags.length/2 && tg <= 1; l++){
         ////System.out.println("hi");
         //System.out.println("TAGS["+l+"]"+tags[l]);
          if((tags[l].equals("PSPLE")) || (tags[l].equals("PSPKO")) || (tags[l].equals("PSPLAI")) || tg == 0){
      //System.out.println("hi");
            decesion = 1;
            //break;
          }
          else{
            //decesion = 0;
            //System.out.println("hi**");
          }
       }
       Verbs = t.findVerbs(sentwords1, sentwords, verbs, xm);
      // t.findVerbs(sentwords1, sentwords, verbs, xm);
      // Verbs = VP;
       V = Verbs;
       vbs = Verbs;
       //System.out.println("123 verb length = "+ Verbs.length);
      // int verblength = 1;
       if(vpindex == 0)
         VP = Verbs;
        int verblength=1;
       boolean projective = true;
       int vblen1 = 0;
       try{
       for(int p = 0 ; p < vblength; p++){
           vblen1= verbPosition[p]+1;
          //System.out.println("proj verbs["+p+"]="+verbPosition[p]+"   verbpos["+(p+1)+"]="+verbPosition[p+1]+"  verb length "+Verbs.length+"   vblen1="+vblen1);
          for(int q = 0 ; q < Verbs.length ; q++){
            //System.out.println("Check projectivity sentwords1["+vblen1+"]="+sentwords1[vblen1]+"   verbs["+q+"]="+verbs[q]);
            if(sentwords1[vblen1].equals(verbs[q])){
              //System.out.println("Extra inning="+verblength);
              verbPosition[p] = verbPosition[p]+1;
              verblength++;
            }
          }
          if((verbPosition[p]+1) != (verbPosition[p+1]) && Verbs.length >1 ){
            if(verbPosition[p] < verbPosition[p+1]){
              //System.out.println("initial Non projective sentence:");
              projective = false;
              verblength = verblength + p+1;
              break;
            }
          }
       }
     }
       catch(ArrayIndexOutOfBoundsException ae){};
      // verblength=noVerbs;
       for(int s = 0 ; s < tags.length ; s++){
          //System.out.println(" after verb sss tags["+s+"]="+tags[s]);
       }
       if(!projective){
        
          /*for(int i=0;i<Words.length;i++){
            //System.out.println("words1["+i+"]="+Words[i]);
          }*/
          String[] Words1;
          Words1 = Words;
          //System.out.println("Welcome to NL disaster management :");
          seperator = t.getDMposition(Words, demo, noWords/2,verbPosition,verblength);
          String sum1 = "  ";
          int start = 0;
          int end, count = 0;
          int flag = 0;
          
          String[] nlsentence = new String[seperator.length];
          String[][] nlsentence1 = new String[seperator.length][20];   // length of the projective sentence is assumed 20
          //int bus = 0;
          /*try{
          if(wildcard==1){
            //nlsentence1[0] = Words1;
            end = tags.length;
            for(int i = 0 ; i < tags.length && bus == 0 ; i++){
              //System.out.println("Words1["+i+"]="+Words1[i]);
              nlsentence1[0][i]= Words[i];
              if(Words1[i].equals("VF")){
                bus = 1;
                for(int j = (i+3) ; j < tags.length ; j++){
                  if(!Words1[j+1].equals("VF")){
                    //System.out.println("   Welcome not VF"+Words[j]);
                    nlsentence1[1][j-1] = Words[j] ;
                  }
                  else{
                    //System.out.println("i="+i+"   j = "+j);
                    nlsentence1[1][j] = Words1[j];
                    nlsentence1[1][j+1] = Words1[j+1];
                    nlsentence1[1][j+2] = Words1[j+2];
                    nlsentence1[1][j+3] = Words1[j+3];
                    
                  }
                }
              }
            }
          }
          }
          catch(NullPointerException nullPointer){
          
          }*/
          /*for(int i = 0 ; i <2 ; i++){
            for(int j = 0 ; j < tags.length ; j++){
              //System.out.println("nlsentence1["+i+"]["+j+"]="+nlsentence1[i][j]);
            }
          }*/
          //System.out.println("seperatepos.len="+seperatepos.length+"    seperatorlen="+seperator.length);
          nllength = (seperatepos.length-2);
          for(int i = 0 ; i < (seperatepos.length-1) && wildcard == 0 ; i++){
            if(i!=0){
              count = 2;
            }
            else{
              count = 0;
            }
            flag = 0;
            sum1 = "  ";
            //System.out.println("seperatepos["+i+"]="+seperatepos[i]);
            end = (seperatepos[i]*2)+1;
            for(int j = start ; j <= end ; j++){
              //System.out.println("Words1["+j+"]="+Words1[j]);
              sum1 = sum1 + Words1[j]+" ";
              if(i != 0 && flag == 0){
                nlsentence1[i][0] = "dummy";
                nlsentence1[i][1] = "NN";
                //nlsentence1[i][end+1] = "|";
                //nlsentence1[i][end+2] = "PUNC";
//                count = count + 2;
                flag = 1;
                //System.out.println("NL count="+count);
              }
              nlsentence1[i][count] = Words1[j];
        count++;
            }
            nlsentence[i] = sum1;           
            //System.out.println("nlsentence["+i+"]="+nlsentence[i]+"   seperatepos["+i+"]="+seperatepos[i]+"start ="+start+"  end="+end);
            if(Words1[end].equals("VMKO") && Words[end+2].equals("PSPKO") )
              start = (seperatepos[i]*2)+4  ;
            else
              start = (seperatepos[i]*2)+2  ;
            nlsentence1final = nlsentence1;
            //nllength = i;
            t.NPmain(nlsentence1[i], "ann.txt" ,"display.txt", wordnetfile, i, filename1);
           // t.NPmain1(nlsentence1[i], "ann.txt" ,"display.txt", wordnetfile, i);
            t.oldparserelation = t.oldparserelation1[i];
            for(int i1 = 0 ; i1 < t.oldparserelation.length ; i1++){
              //System.out.println("have oldparserelation["+i1+"]="+t.oldparserelation[i1]);
            }
          }
          
       }
       
       xm.displayString("vbs="+NP[0]);
       Mymatching1 mm = new Mymatching1(nnlength+nKarkas, nnlength, nKarkas, danger);
       //t.getFrame(Verbs);
       //System.out.println("decesion = "+decesion+" projective ="+projective);
        try{
      if( decesion == 1 && projective)  {
        //System.out.println("projective WELCOME1");
      nonouns = nnlength;
           //System.out.println("Length of Verbs = "+Verbs.length+"  Length of Nouns="+nnlength+"  nkarkas="+nKarkas);
           int[][][] edges = new int[Verbs.length][nnlength][nKarkas];
           edges = t.findEdges(Verbs,Nouns);
           EDGES = edges;
           nKarkas = 8;
          // vibhakti = t.findVibhakti(sentwords1,morphs, Vibhakti, nonouns,nouns);
           int[][][] subgraphs = new int[MaxSG][nnlength][Verbs.length];
           int[][][] tsubgraphs = new int[5040][nnlength][ntKarkas];     // 7! = 5040 there are 7 karkas permutation considered
           int[][][] ntsubgraphs = new int[5040][nnlength][nnlength+ntKarkas];
           
           int[][][] finalmaxMatchings = new int[5040][nnlength][nKarkas];
     
           int[][][] subgraphs3 = new int[MaxSG][nnlength][Verbs.length];
           int nsgrph = 0;
           int [][][] finalmatching = new int[MaxSG][nnlength][Verbs.length];
          
           int [][][] finalmatching1 = new int[MaxSG][nnlength][Verbs.length];
           semifinalmatching = new int[100][nnlength][nKarkas];
           //System.out.println("N.len="+nnlength+"    nkarkas="+nKarkas);
           //nKarkas = 7;
    //       nnlength = 2;
    // OUT OF 8 KARKAS 7 ARE CONSIDERED FOR PERMUTATION SAMBANDH (K6) IS REMOVED 
           int[] krkseq = new int[nKarkas];
           for(int i = 0 ; i < ntKarkas ; i++){
              krkseq[i]=nnlength + i;
              //System.out.println("  krkseq["+i+"]="+krkseq[i]);
           }
            
           tsubgraphs = mm.main1(nnlength+ntKarkas, ntKarkas, krkseq, danger);
           
           int fac = mm.getFactorial();
           //System.out.println("factorial = "+fac+"  ntkarkas="+ntKarkas+"   nkarkas="+nKarkas);
           
           /*for(int i = 0 ; i < fac ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < ntKarkas ; k++){
                  ntsubgraphs[i][j][k] = tsubgraphs[i][j][k+nnlength];
                }
              }
      }*/
      //System.out.println("go ahead");
      for(int i = 0 ; i < fac ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
                  try{
                    if(k==5){
                      finalmaxMatchings[i][j][k] = 0;
                    }
                    else if(k < 5){
                      finalmaxMatchings[i][j][k] = tsubgraphs[i][j][k] ;
                    }
                    else if(k > 5){
                      finalmaxMatchings[i][j][k] = tsubgraphs[i][j][k-1];
                    }
                  }
                  catch(ArrayIndexOutOfBoundsException ae){}
                }
              }
      }
      
      
          /* for(int i = 0 ; i < MaxSG ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
            ////System.out.print("  "+tsubgraphs[i][j][k]);
            if(k == 4 || k == 6){
              finalmaxMatchings[i][j][k] = 0;
            }
            else{
              if(k < 7)
                finalmaxMatchings[i][j][k] = tsubgraphs[i][j][nnlength+k];
            }
            //System.out.print("   "+finalmaxMatchings[i][j][k] );
                }
                ////System.out.println();
              }
                              ////System.out.println();
           }*/
            //System.out.println();
           System.out.println("**final maximum subgraphs :");
            for(int i = 0 ; i < fac ; i++){
              System.out.println("permutation ="+i);
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
                  ////System.out.print("   "+ntsubgraphs[i][j][k]);
                  System.out.print("   "+finalmaxMatchings[i][j][k] );
                }
                System.out.println();
              }
              System.out.println();             
              
      }
      //System.out.println("verbs length = "+Verbs.length);
      int parsedGraphindex;
      
      golden = new int[150][nnlength][nKarkas];
      egolden = new int[150][nnlength][nKarkas+1];
           for(int l = 0 ; l < Verbs.length ; l++){
                subgraphs = finalmaxMatchings;
               //subgraphs = t.findsubGraphs(edges, nKarkas, nnlength, l, indexkarana);
             System.out.println("k1snoun="+k1snoun);
          finalmatching = t.findfinalmatching(subgraphs, 5040, nnlength, nKarkas, indexkarta, indexkarana, NP, k1snoun, false);
          //semifinalmatching = semifinal;
          if(k1sexist) {
            finalcountParse = 0;
            break;
          }
          //System.out.println("generic marker k2p="+k2pexist+"    gereric marker k7t="+k7texist+"  number of parsed graphs g= "+g);
          System.out.println("Semi parsed tree ");
          try{
          for(int m = 0 ; m < iteration ;m++){
            for(int j = 0 ; j < nnlength ; j++){
                    for(int k = 0 ; k < nKarkas ; k++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      System.out.print("   "+semifinalmatching[m][j][k] );
                    }
                    System.out.println();
                  }
                  System.out.println(); 
          }
        }
        catch(ArrayIndexOutOfBoundsException ae){};
          if(g == 0 ){
            //if(!k2pexist && !k7texist){
            golden = semifinalmatching;
            g=iteration;
            if(k7texist){
              //System.out.println("generic marker k7t exists");
              finalcountParse = 0;
              
            }
          }
          else{
            //golden = finalmatching;
            
            if(k7texist){
              //System.out.println("generic marker k7t exists");
              semifinalmatching = semifinalmatching;
              finalcountParse = 0;
            }
          }
          //else 
            //g = 0;
          //}
          System.out.println("Golden");
          try{
          for(int m1 = 0 ; m1 < (g)  && !k7texist ;m1++){
            for(int j1 = 0 ; j1 < nnlength ; j1++){
                    for(int k1 = 0 ; k1 < nKarkas ; k1++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      System.out.print("   "+golden[m1][j1][k1] );
                    }
                    System.out.println();
                  }
                  System.out.println(); 
          }
          System.out.println("eGolden");
          for(int m1 = 0 ; m1 < (g) && !k7texist ;m1++){
            for(int j1 = 0 ; j1 < nnlength ; j1++){
                    for(int k1 = 0 ; k1 < nKarkas+1 ; k1++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      if(k1 < nKarkas)
                        egolden[m1][j1][k1] = golden[m1][j1][k1];
                      else
                        egolden[m1][j1][k1] = 0;
                      //System.out.print("   "+egolden[m1][j1][k1] );
                    }
                    //System.out.println();
                  }
                  //System.out.println(); 
          }
          }
          catch(ArrayIndexOutOfBoundsException Ae){};
          //if(egolden[0][0].length < golden[0][0].length){
            goldenfinal = new int[500][nnlength][nKarkas];
            if(g != 1){
              goldenfinal = getGoldenunique(golden, g-1, nnlength, nKarkas);
            }
            else{
              goldenfinal = golden;
            }
        //  }
        //  else{
        //    //System.out.println("Hi it is egolden");
        //    nKarkas = nKarkas + 1;
        //    goldenfinal = new int[500][nnlength][nKarkas];
        //    goldenfinal = getGoldenunique(egolden, g, nnlength, nKarkas);

        //  }
          System.out.println("Golden final");
          //if(!k1texist){
            for(int m1 = 0 ; m1 < (g-1) ;m1++){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        ////System.out.print("   "+ntsubgraphs[i][j][k]);
                        System.out.print("   "+goldenfinal[m1][j1][k1] );
                      }
                      System.out.println();
                    }
                    System.out.println(); 
            }
          //}
          //if(!k7texist){
            parsedGraphindex = getparsedGraph1(goldenfinal, Actualvibhaktilist, g-1, nnlength, nKarkas, getk1s(),k7texist, k2pexist,k7tindex, k2pindex);
          //}
          //else{
          //  parsedGraphindex = 100;
          //}
          //System.out.println("Parsed Golden final");
          //if(!k1texist){
            if(parsedGraphindex  != 100){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        //System.out.print("  parsedindex=  "+parsedGraphindex);
                        //System.out.print("   "+goldenfinal[parsedGraphindex][j1][k1] );
                      }
                      //System.out.println();
                    }
                    if(!k7texist && finalend == 0)
                      displayString(filename,goldenfinal,parsedGraphindex,nnlength,nKarkas,NP, false, getk1s());
            }
            
            else{
          //  if(!k1texist)
              //System.out.print("  parsedindex=  "+0);
              if(!k2pexist && !k7texist  && finalend == 0){
                displayString(filename,golden,0,nnlength,nKarkas,NP, false, getk1s());
              }
            }
          //}
          //else{
            //displayString(filename,egolden,0,nnlength,nKarkas,NP, false, getk1s());
          //}
      }
         
    }
    else if(projective){
      //System.out.println("Chunk exists ");
      t.getParsedsentence(N, V, NP, nnlength);
    }
    //displayfullparser(filename, goldenfinal, );
    finalsecondmatching = new int[iteration][nnlength][Verbs.length];
    finalsemisecondsfm = new int[3*iteration][nnlength][nKarkas+1];
    int cnt = 0;
    //int[][][] finalsemisecondsfm = new int[][][];
    int fl =0;
    if(finalcountParse == 0 && projective){
      //System.out.println("CAP OF GOOD HOPE");
      semisecondsfm = getaddcolumn(semifinalmatching);
      //System.out.println("second Semi parsed tree ");
      for(int m = 0 ; m < 3*iteration ;m++){
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                      
                    ////System.out.print("   "+semisecondsfm[m][j][k] );
                    fl = mm.getUniqueIncMatrix(m,nnlength,nKarkas+1,semisecondsfm);
                    //System.out.println(" fl=  "+fl+"  m="+m);
                    if(fl != -1){
              //  for(int i = 0 ; i < (nnlength-(nKarkas+1)) ; i++){
                  //for(int j1= 0 ; j1 < (nKarkas+1) ; j1++){
                    //System.out.println("Hi it is unique");
                    try{
                    if(cnt< (iteration)){

                      finalsemisecondsfm[cnt][j][k] = semisecondsfm[m][j][k];
                      //System.out.println("  finalsemi["+cnt+"]["+j+"]["+k+"]  "+finalsemisecondsfm[cnt][j][k]);
                      //cnt++;
                    }
                  }
                  catch(ArrayIndexOutOfBoundsException ae){};
                    //cnt++;
                  
                  //}
              //  }
              }
              else{
                
              }
          }
          //System.out.println();
              }
              //System.out.println(); 
              cnt++;
      }
      System.out.println("Extended second :");
      try{
      for(int m = 0 ; m < 3*iteration ;m++){
        //System.out.println("Iteration = "+m);
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                //System.out.print("     "+semisecondsfm[m][j][k]);
              }
              //System.out.println();
            }
            //System.out.println();
            //System.out.println();
      }
      }
      catch(ArrayIndexOutOfBoundsException ae){};
      finalsecondmatching = t.findfinalmatching(semisecondsfm, 3*iteration, nnlength, nKarkas+1, indexkarta, indexkarana, NP, k1snoun, true);
      for(int m = 0 ; m < finalsecondmatching.length ;m++){
        //System.out.println("final Iteration = "+m);
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                //System.out.print("     "+finalsecondmatching[m][j][k]);
              }
              //System.out.println();
            }
            //System.out.println();
            //System.out.println();
      }
      //System.out.println("Extended parsing iteration="+iteration);
      int eparsedGraphindex;
      //if(!k7texist ){
        eparsedGraphindex = getparsedGraph1(finalsecondmatching , Actualvibhaktilist, iteration, nnlength, nKarkas+1, getk1s(),k7texist, k2pexist,k7tindex,k2pindex);
      //}
      //else{
      //  eparsedGraphindex = 100;
      //}
        //System.out.println("Extended Parsed Golden final");
        //System.out.println("eparsed graph index="+eparsedGraphindex);
          //if(!k1texist){
            if(eparsedGraphindex  != 100){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas+1; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        //System.out.print("  eparsedindex=  "+eparsedGraphindex);
                        //System.out.print("   "+finalsecondmatching[eparsedGraphindex][j1][k1] );
                      }
                      //System.out.println();
                    }
                    if(finalsecondmatching[eparsedGraphindex][0][0] == 1  && finalend == 0)
                    displayString(filename,finalsecondmatching,eparsedGraphindex,nnlength,nKarkas+1,NP, true, getk1s());
            }
            
            else{
          //  if(!k1texist)
              //System.out.print("  parsedindex=  "+0);
              if(!k2pexist && !k7texist  && finalend == 0){
                displayString(filename,finalsecondmatching,0,nnlength,nKarkas,NP, true, getk1s());
              }
            }
      //displayString(filename,egolden,g-1,nnlength,nKarkas+1,NP, true, getk1s());
    }
  } catch(NullPointerException ne) { };
     //  for(int l1 = 0 ; l1 < Verbs.length ; l1++){
       //   finalmatching = t.findfinalmatching(subgraphs, 2, nnlength, nKarkas, indexkarta, indexkarana, Nouns);
       //}
       int nlen=0;
       int lol = 0;
       String[] NNP = new String[nnlength];
       String[] TTNP = new String[nnlength];   
      /* for(int b=0;b<duplicate.length;b++){
          for(int c = 0 ; c < duplicate[0].length;c++){
            if(duplicate[b][c] == 1){
              lol = 0;
              break;
            }
            else{
              lol = 1;
            }
          }

       }    */
       if(projective){
          //System.out.println("check check check projective noun ");
          for(int i = 0 ; i < nnlength ; i++){
            //System.out.println("NP["+i+"]="+NP[i]+"  TNP["+i+"]="+TNP[i]);
            try{
            if(!TNP[i].equals("null2") && !NP[i].equals(null) ){
              NNP[nlen] = NP[i];
              TTNP[nlen] = TNP[i];
              nlen++;
            }
            }
            catch(NullPointerException nullPointer){}
          }
          //System.out.println("nlen="+nlen);
        //  if(lol ==0)
         intramatrix = t.intrachunk(NNP,TTNP,duplicate,nlen, Verbs, filename, filename1, output, Words);
  }
  
  //else{
  //  intramatrix = t.intrachunk(NP,TNP,duplicate,nnlength, Verbs);
  //}
   }

   public static void NPmain1(String[] np1, String args, String args1, String args2, int index1)throws IOException {


   }
   
    public static void NPmain(String[] np1, String args, String args1, String args2, int index1, String filename1)throws IOException 
   {
       String glyph = "\u0926\u0947\u0916\u093F";
       int nonouns = 0;

       //System.out.println("Welcome to NPmain");
       //String[] presence = new String[];
    String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
       //String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM","STOP"};
      // for(int i = 0 ; i < totaltag ; i++)
            //System.out.println("tagset["+i+"]="+tagset[i]);
       for(int i = 0 ; i < 5 ; i++){
          TNP[i] = "null2";
          //System.out.println("$****TNP["+i+"]="+TNP[i]);
       }
       Lwg4 t = new Lwg4();
       int ctm = 0;
       final String[] tags;
       String[] morphs;
      // int finalcountParse = 0;
       String[] verbs = {"VF","VINF","VAUX","VMNE","VMKO","VMO"};
       String[] demo = {"DM","DMR","DMD","DMI","CCD","CCS"};
       String[] Verbs ;
       String[] V;
       String[] nouns = {"NN","NNP","NST","PRP","PRL","PRF"};
       String[] seperator = new String[5];   //maximum seperator words are assumed 5 and the length of the sentence is 25
     //  int semicount = 0;
       
       
       
       String vframe;
       String[] Nouns;
       String[] Vibhakti = {"\u0932\u0947","\u0932\u093E\u0908", "\u092E\u093E", "\u0915\u093E", "\u0915\u094B", "\u0926\u0947\u0916\u093F", "\u092C\u093E\u091F","\u0938\u092E\u094D\u092E", "\u0938\u0902\u0917"};
      // String wordnetfile;
//       String[] Vibhakti = {}
       String[][][] vibhakti ;
     //  String[][] N = new String[5][3];
       karana = new String[maxKarana];
       ReadXMLFile xm = new ReadXMLFile();       
      // Chunker ch ;
Actualvibhaktilist = new String[nKarkas];
 for(int s = 0 ; s < nKarkas ; s++){
          Actualvibhaktilist[s] = "null";
          //System.out.println("Actualvibhaktilist["+s+"]="+Actualvibhaktilist[s]);
       }
       t.NPreadFile(np1, args , args1, glyph);
       wordnetfile = args2;
       nounTags =  new String[noWords/2];
       int[] nPhaseInd = new int[noWords/2];
       String[] nPhase = new String[noWords/2];
       int[] vPhaseInd = new int[noWords/2];
       String[] vPhase = new String[noWords/2];
     //  int frameInd;
      // tags = t.getTags(sentwords1);
       //Words = new String[tags.length];
       //sentwords = new String[tags.length];
      // sentwords = new String[noWords/2];
   //   //System.out.println("*****sentwords1["+0+"]="+sentwords1[0
    //noWords = 14;
       //System.out.println("No of Morphs = "+(noWords/2));
       morphs = new String[noWords/2];
       Actualvibhakti= new String[noWords/2][noWords/2][2];     
//       Actualvibhaktilist = new String[10];

       morphs = t.getMorphs(sentwords);
       tags = t.getTags(sentwords1);
       int cnt1 = 0;
       for(int i = 0 ; i < tags.length ; i++){
          //System.out.println("$****sentwords1["+i+"]="+tags[i]+"  sentwords1 length="+sentwords1.length);
          try{
            if(!tags[i].equals("PUNC")){
              if(( tags[i].equals("NN") || tags[i].equals("NNP"))){
                TNP[cnt1]=tags[i];
                //System.out.println("$****TNP["+cnt1+"]="+tags[cnt1]);
                cnt1++;
                //break;
              }
            }
            else
              break;
    }
    catch(NullPointerException nullPointer){}
       }
       
       //System.out.println("IT IS CHUNKER");
       int in = 0;
       int ln = 0;
       for(int i = 0 ; i < sentwords1.length ; i++){
          //System.out.println("*****sentwords1["+i+"]="+tags[i]);
          try{
            if(tags[i].equals("PRP") || tags[i].equals("PRL")){
              if(tags[i+1].equals("PSPLE") ){
                TTN[in][ln] = morphs[i];
                TTN[in][ln+1] = morphs[i+1];
                in = 1;
              }
            }
          }
          catch(NullPointerException nullPointer){}
          
          //in++;
       }
       for(int i = 0 ; i < sentwords1.length ; i++){
          //System.out.println("non projective *****sentwords1["+i+"]="+tags[i]);
       }
       Chunker ch =  new Chunker(noTags, morphs, tags, k2pexist, k7texist);
       int nnlength = 0;
       int temp = 0;
       int nttg = 0;
       int len = 0;
       int vblength = 0;
       int lenverbphrase = 0;
       String[] ntstart = new String[5];// = "no";
       int[] len1 = new int[5] ; // assuming maximum noun chunks is 5
       
        N = ch.getNounChunks();
        Vph= ch.getVerbChunks();
        TN = ch.getnountag();
        len = ch.getnonounphase();
        lenverbphrase = ch.getverbphrase();
        int tg = 0;
        //System.out.println("N.len="+len+"  N["+0+"]["+2+"]="+N[0][2]+"  n[0][0]="+N[0][0]);
      //  //System.out.println("V.len="+lenverbphrase+"  V["+0+"]["+2+"]="+Vph[0][2]+"  V[0][0]="+Vph[0][0]);
       /* for(int p=0;p<len;p++){
          for(int q = 0 ; q < N[p].length;q++){
          
              //System.out.println("N["+p+"]["+q+"]="+N[p][q]);
          }
        }*/
        int ind = 0;
        try{
            try{
          if(N[0][0].equals("null1")){
            len = 0;
          }
          int con=0;
          int tri = 0;
          String[] Alist = new String[noWords/2];
          
          for(int i = 0 ; i < len ; i++){
            //TNP[i]="n";
           /* for(int s=0; s< noWords/2 ; s++){

            Alist[s] = "hi";
            //System.out.println("Are");
          }*/
            int index = 0;
            int fg = 0;
            String sum = " ";
            String tsum = " ";
            String sum1 = " ";
            String tsum1 = " ";
            int corona = 0;
            String tp1 = "hi";
            k1snoun = "null1";
            len1[i] = N[i].length;
          //  int tri = 0 ;
            //System.out.println("len1["+i+"]="+len1[i]+"  Alist["+i+"]=  "+Alist[i]);
            int stop = 0;
            for(int j1 = 0 ; j1 < len1[i] ; j1++){
              if(TN[i][j1] != null){
                  stop++;
              }
            }
            
            for(int j = 0 ; j < len1[i] ; j++){
              
              //System.out.println("N["+i+"]["+j+"]="+N[i][j]+"    TN["+i+"]["+j+"]="+TN[i][j]+" stop="+stop);

              

              //if(tags[j+tg-1] =="NN" && tags[j+tg] == "NN") fg =1;
              if(! N[i][j].equals("n")){
                corona = 1;
               // con++;
                if(stop == 1){

                tp1 = "null1";
                //System.out.println(" full stop = "+tp1);
              }
                sum = sum + N[i][j]+" "; 
                sum1 = sum1 + TN[i][j]+" ";
                NP[i] = sum;
                TNP[i] = sum1;
                nPhase[i] = sum1;
               // int tri = 0;
              //  String tp1 ="hi";
                //System.out.println("trial sum1="+sum1+"  tri="+tri);
                String[] ptag = {"PSPLE","PSPLAI","PSPKO","PSPO"};
                for(int p = 0 ; p < ptag.length ; p++){
                    if( TN[i][j].equals(ptag[p])){
                      //System.out.println("ptag["+p+"]="+ptag[p]+"  stop="+stop);
                    //  try{

                         
                        
                      if(stop == 3 ){
                          //System.out.println("bahu");
                         // tp1 = ptag[p];
                          tp1 = N[i][j];
                        //  Alist[con] = ptag[p];
                          //System.out.println(" 0 Alist["+con+"]"+Alist[con]);
                         // con++;
                        }
                        if(stop == 2 ){
                          //System.out.println("bahu");
                         // tp1 = ptag[p];
                          tp1 = N[i][j];
                          //System.out.println(" 1 tp1="+tp1);
                          //Alist[con] = tp1;
                        //  con++;                        
                        }
                        if(stop == 1)
                        {
                          //System.out.println("hi trial"+len1[i]);
                        //  tp1 = null;
                       //   Alist[con] = null;
                        //  con++;
                        }
                        
                    
                    }
                    
                 //con++;
                }
              //  Actualvbt[i] = 
                //System.out.println("projective sum="+sum+"    sum1="+sum1);
                //tsum = tsum + TN[i][j]"   ";
                if(i > 0 && i != (i-1)){
                  if(index == 0 ){//|| fg == 1){
                    
                    //System.out.println("if **sum1="+sum1+"   index="+index+"   tags["+(j+tg-1)+"]="+tags[j+tg-1]+"  tags["+(j+tg)+"]="+tags[j+tg]+"  j="+j);
                    if(tags[j+tg-1].equals("NN") && tags[j+tg].equals("NN")) {
                      //System.out.println("chalo");
                      tags[j+tg]="NNP";
                    }
                    //sum1 = sum1 + tags[j+tg]+" ";
                  }
                  else{
                    //sum1 = sum1 + tags[j+tg]+" ";
                    //System.out.println("if sum1="+sum1);
                  }
                  //tsum1 = tsum1 +
                  if(j >1)
                    k1snoun = tags[j+tg-2];
                  else
                    k1snoun = tags[j+tg-1];
                  //System.out.println("if j="+j+"    tg="+tg);
                  //System.out.println("if tags["+(j+tg-2)+"]="+tags[j+tg-2]);
                }
                else{
                  //sum1 = sum1 + tags[j]+" ";
                  //System.out.println("else tags["+j+"]="+tags[j]);
                  //System.out.println("else j="+j+"    tg="+tg+"   index="+index);
                  if(index == 0){
                    ntstart[nttg] = sum1;
                    //nttg++;
                  }
                  nttg++;
                  
                }
                
                tg++;
                index++;
                fg++;
              //  //System.out.println("tags["+(j+tg-1)+"]="+tags[j+tg-1]+"   tags["+(j+tg-2)+"]="+tags[j+tg-2]+"   fg="+fg);
              //if(tags[j+tg-2] =="NN" && tags[j+tg-1] == "NN" && fg == 1) tags[j+tg] ="NNP";
                
              }
              else{
                //sum = "null2";
                //sum1 = "null2";
              }
              //System.out.println("sum="+sum+"    sum1="+sum1);
//              NP[i] =  N[i][j]  
            }
            //if(corona == 1){
              
           //}
            //System.out.println("*NP["+i+"]="+NP[i]+"   TNP["+i+"]="+TNP[i]+"   tp1="+tp1);
            nPhaseInd[i] = tg;
            //nPhase[i] = sum1;
            //System.out.println("*NPhase["+i+"]="+nPhase[i]+"  Alist["+i+"]="+Alist[i]);
            Alist[i] = tp1;
            if(tp1.equals("hi")) tp1 = "null";
            if(stop == 1)
             Actualvibhaktilist[i] = "null";
           else
            Actualvibhaktilist[i] = tp1;
            //System.out.println("567 nonprojective Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]);
          }
          
    
         
            }catch(NullPointerException nullPointer){}
  }catch(ArrayIndexOutOfBoundsException ae){}

  int vpindex = 0 ;
  // verv chunks
  //int vpindex = 0 ;
  // verv chunks
  String[][] vphtag;
  Vph= ch.getVerbChunks();
       // TN = ch.getnountag();
       vphtag = ch.getverbtag();
        lenverbphrase = ch.getverbphrase();
        int wildcard = 0;
        //tgv = 0;
  //System.out.println("verb chunk Lwg4 Vph["+0+"]["+0+"]="+Vph[0][0]+"    vphtag["+0+"]["+0+"]"+vphtag[0][0]);
  
  if(Vph[0][0].equals("null1")){
    lenverbphrase = 0;
  }
  for(int i = 0 ; i < lenverbphrase ; i++){
    String summ = " ";
    String summ1 = " ";
    for(int j =0 ; j < Vph[i].length ; j++){
      if(!Vph[i][j].equals("n")){
        summ = summ + Vph[i][j]+" ";
        summ1 = summ1 + vphtag[i][j]+" ";
        if(vphtag[i][j].equals("VMKO")){
          //System.out.println("welcome to wildcard j ="+j);
          if(vphtag[i][j+1].equals("NN")){
            wildcard = 1;
            //System.out.println("welcome to wildcard again j = "+j);
          }
        }
        //System.out.println("    summ = "+summ+"  summ1= "+summ1+"  wildcard="+wildcard);
      }
    }
    VP[i] = summ;
          TVP[i] = summ1;
          //System.out.println("*VP["+i+"]="+VP[i]+"   TVP["+i+"]="+TVP[i]);
  }
  
  /* Nouns = new String[NP.length];
 // Nouns = NP;
  for(int i = 0 ; i < NP.length;i++){
          //System.out.println("  4321 Nouns["+i+"]="+Nouns[i]);
          if(i< ind){
              Nouns[i] = NP[i];
          }
          else{
          Nouns[i] = "null";
        }

       }
//Nouns = NP;
      for(int i = 0 ; i < Nouns.length;i++){
          //System.out.println(" 5 4321 Nouns["+i+"]="+Nouns[i]);
       //   Nouns[i] = "null";

       }*/
  /*try{
    if(in == 1){
      for(int i = 0 ; i < (len-1) ; i++){
              NP[i+1] = NP[i];
              NP[0] = morphs[0];
              TNP[i+1] = TNP[i];
              TNP[0] = tags[0];
          
            }
    }
  }

  catch(ArrayIndexOutOfBoundsException ae){}*/
  //int vpindex = 0 ;
  //Nouns = NP;
  // verv chunks
  //int vpindex = 0 ;
  // verv chunks
  
  
  
     /*   nnlength = len;
        vblength = lenverbphrase;
        //System.out.println("nnlength = "+nnlength+"  tg="+tg+"    vblength="+vblength);*/
     /*   for(int i = 0 ; i < Nouns.length;i++){
          //System.out.println("  6321 Nouns["+i+"]="+Nouns[i]);

       }*/
    //    //System.out.println("nnlength = "+nnlength+"  tg="+tg);
       /*   //System.out.println("N.length="+len+"  N[0][0]="+N[0][0]+"  n["+0+"].LEN="+N[0].length);
        for(int i = 0 ; i < 1 ; i++){
          for(int j = 0 ; j < N.length ; j++){
            //System.out.println(" N["+i+"]["+j+"]="+N[i][j]);
            try{
              if(! N[i][j].equals("n") ){
                nnlength = i;
                temp = j;
                if(len == 3){
                  //System.out.println("hi len = "+len);
                  for(int k = 0 ; k < len ; k++){
                    NP[i] = N[i][k]+" "+N[i][k+1]+" "+N[i][k+2];
                    }
                  break;
                }
                else if(len == 2){
                  NP[i] = N[i][len-1] + "   "+ N[i][len] ;
                  break;
                }
                else
                  NP[i] = N[i][j] ;
                //System.out.println("nnlength="+nnlength);
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
          try{
            nnlength = len;
            //System.out.println("  NP["+i+"]="+NP[i]+"   noun phase["+i+"]="+nPhase[i]+"   tg="+tg+"   in ="+in);
            if(tg > 1)
              nPhase[i]=NP[i];
          }
          catch(ArrayIndexOutOfBoundsException ae){}
        }
       // Nouns = NP;
       Nouns = t.findNouns(tags, sentwords, nouns, Vibhakti, len, ntstart, in);
      // Nouns = NP;
  //t.findNouns(tags, sentwords, nouns, Vibhakti, len, ntstart, in);
//  Final tags;
       //System.out.println("*****sentwords1["+0+"]="+tags[0]);
       //System.out.println("No of Morphs = "+(noWords/2));
       
      // noVerbs= Vph.length;
       //noVerbs = Verbs.length;
       int decesion = 1;
       try{
       //System.out.println("WELCOME tags.len = "+tags.length+"   TAGS["+0+"]"+tags[0]);
       for(int l = 0 ; l < tags.length/2 && tg <= 1; l++){
         ////System.out.println("hi");
         //System.out.println("TAGS["+l+"]"+tags[l]);
          if((tags[l].equals("PSPLE")) || (tags[l].equals("PSPKO")) || (tags[l].equals("PSPLAI")) || tg == 0){
      //System.out.println("hi");
            decesion = 1;
            //break;
          }
          else{
            //decesion = 0;
            //System.out.println("hi**");
          }
       }
       }
  catch(NullPointerException nullPointer){}
   /*for(int i = 0 ; i < Nouns.length;i++){
          //System.out.println("  321 Nouns["+i+"]="+Nouns[i]);

       }*/
       Verbs = t.findVerbs(sentwords1, sentwords, verbs, xm);
    //  t.findVerbs(sentwords1, sentwords, verbs, xm);
     // Verbs = VP;
       V = Verbs;
       vbs = Verbs;
       
       if(vpindex == 0)
         VP = Verbs;
       int verblength=1;
       boolean projective = true;
       for(int p = 0 ; p < Verbs.length; p++){
          //System.out.println("proj verbs["+p+"]="+verbPosition[p]+"   verbpos["+(p+1)+"]="+verbPosition[p+1]+"  verb length "+Verbs.length);
          
          if((verbPosition[p]+1) != (verbPosition[p+1]) && Verbs.length >1){
            if(verbPosition[p] < verbPosition[p+1]){
              //System.out.println("projective sentence:");
              projective = false;
              verblength = p+1;
              break;
            }
          }
       }
       verblength=Verbs.length;
       for(int s = 0 ; s < tags.length ; s++){
          //System.out.println("NP  after verb sss tags["+s+"]="+tags[s]);
       }
        for(int i = 0 ; i < Nouns.length;i++){
          //System.out.println("Nouns["+i+"]="+Nouns[i]+"   NP["+i+"]="+NP[i]);

       }
     /*  if(!projective){
        
          /*for(int i=0;i<Words.length;i++){
            //System.out.println("words1["+i+"]="+Words[i]);
          }*/
          /*seperator = t.getDMposition(Words, demo, noWords/2,verbPosition,verblength);
          String sum1 = "  ";
          int start = 0;
          int end;
          String[] nlsentence = new String[seperator.length];
          for(int i = 0 ; i < (seperatepos.length-1) ; i++){
            sum1 = "  ";
            end = (seperatepos[i]*2)+1;
            for(int j = start ; j <= end ; j++){
              sum1 = sum1 + Words[j]+" ";

            }
            nlsentence[i] = sum1;           
            //System.out.println("nlsentence["+i+"]="+nlsentence[i]+"   seperatepos["+i+"]="+seperatepos[i]+"start ="+start+"  end="+end);
            start = (seperatepos[i]*2)+2  ;
            //t.main1(String[] args);
          }
       }*/
       //System.out.println("123 nnlength="+nnlength);
       xm.displayString("vbs="+NP[0]);
       Mymatching1 mm = new Mymatching1(nnlength+nKarkas, nnlength, nKarkas, danger);
       //t.getFrame(Verbs);
       //System.out.println("decesion = "+decesion+" projective ="+projective);
       for(int i = 0 ; i < Nouns.length;i++){
          //System.out.println("Nouns["+i+"]="+Nouns[i]);

       }
        try{
      if( decesion == 1 )   {
        //System.out.println("NONPROJECTIVE WELCOME1");
      nonouns = nnlength;
           //System.out.println("Length of Verbs = "+Verbs.length+"  Length of Nouns="+nnlength+"  nkarkas="+nKarkas);
           int[][][] edges = new int[Verbs.length][nnlength][nKarkas];
           edges = t.findEdges(Verbs,Nouns);
           EDGES = edges;
           nKarkas = 8;
          // vibhakti = t.findVibhakti(sentwords1,morphs, Vibhakti, nonouns,nouns);
           int[][][] subgraphs = new int[MaxSG][nnlength][Verbs.length];
           int[][][] tsubgraphs = new int[5040][nnlength][ntKarkas];     // 7! = 5040 there are 7 karkas permutation considered
           int[][][] ntsubgraphs = new int[5040][nnlength][nnlength+ntKarkas];
           
           int[][][] finalmaxMatchings = new int[5040][nnlength][nKarkas];
     
           int[][][] subgraphs3 = new int[MaxSG][nnlength][Verbs.length];
           int nsgrph = 0;
           int [][][] finalmatching = new int[MaxSG][nnlength][Verbs.length];
          
           int [][][] finalmatching1 = new int[MaxSG][nnlength][Verbs.length];
           semifinalmatching = new int[100][nnlength][nKarkas];
           //System.out.println("N.len="+nnlength+"    nkarkas="+nKarkas+"   nnlength="+nnlength);
           //nKarkas = 7;
    //       nnlength = 2;
    // OUT OF 8 KARKAS 7 ARE CONSIDERED FOR PERMUTATION SAMBANDH (K6) IS REMOVED 
           //int[] krkseq = new int[nKarkas];
         //  for(int i = 0 ; i < ntKarkas ; i++){
           // if(ctm%2 == 0)
            //  krkseq[i]= Nouns.length + i;
           // else
            //  if(Nouns.length == 1){
            //    krkseq[i] = i;
            //  }
            //  else{
        //        krkseq[i] = Nouns.length + i;
          //    }
           //   //System.out.println("  krkseq["+i+"]="+krkseq[i]);
          // }
           int[] krkseq = new int[nKarkas];
           for(int i = 0 ; i < ntKarkas ; i++){
              krkseq[i]=nnlength + i;
              //System.out.println("  krkseq["+i+"]="+krkseq[i]);
           }
           //System.out.println("Non projective : Nouns.length="+Nouns.length+"   ntKarkas="+ntKarkas+"  krkseq["+0+"]"+krkseq[0]);
         //  //System.out.println("  NP["+i+"]="+NP[i]+"   noun phase["+i+"]="+nPhase[i]+"   tg="+tg+"   p in="+in+"   nnlength="+nnlength+"   ntstart["+i+"]="+ntstart[i]);
          // //System.out.println("factorial = "+fac+"  ntkarkas="+ntKarkas+"   nkarkas="+nKarkas);
           tsubgraphs = mm.main1(nnlength+ntKarkas, ntKarkas, krkseq, danger);
           //ct++;
           int fac = mm.getFactorial();
           //System.out.println("factorial = "+fac+"  ntkarkas="+ntKarkas+"   nkarkas="+nKarkas);
           
         /*  for(int i = 0 ; i < fac ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < ntKarkas ; k++){
                  //ntsubgraphs[i][j][k] = tsubgraphs[i][j][k+nnlength];
                  //System.out.print("NP subgraphs["+i+"]["+j+"]["+k+"]= "+tsubgraphs[i][j][k]);
                }
                //System.out.println();
              }
      }*/
      
      for(int i = 0 ; i < fac ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
                  try{
                    if(k==5){
                      finalmaxMatchings[i][j][k] = 0;
                    }
                    else if(k < 5){
                      finalmaxMatchings[i][j][k] = tsubgraphs[i][j][k] ;
                    }
                    else if(k > 5){
                      finalmaxMatchings[i][j][k] = tsubgraphs[i][j][k-1];
                    }
                  }
                  catch(ArrayIndexOutOfBoundsException ae){}
                }
              }
      }
      
      
          /* for(int i = 0 ; i < MaxSG ; i++){
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
            ////System.out.print("  "+tsubgraphs[i][j][k]);
            if(k == 4 || k == 6){
              finalmaxMatchings[i][j][k] = 0;
            }
            else{
              if(k < 7)
                finalmaxMatchings[i][j][k] = tsubgraphs[i][j][nnlength+k];
            }
            //System.out.print("   "+finalmaxMatchings[i][j][k] );
                }
                ////System.out.println();
              }
                              ////System.out.println();
           }*/
            //System.out.println();
           //System.out.println("**final maximum subgraphs :");
            for(int i = 0 ; i < fac ; i++){
              //System.out.println("permutation ="+i);
              for(int j = 0 ; j < nnlength ; j++){
                for(int k = 0 ; k < nKarkas ; k++){
                  ////System.out.print("   "+ntsubgraphs[i][j][k]);
                  //System.out.print("   "+finalmaxMatchings[i][j][k] );
                }
                //System.out.println();
              }
              //System.out.println();             
              
      }
      //System.out.println("verbs length = "+Verbs.length);
      int parsedGraphindex;
      
      golden = new int[150][nnlength][nKarkas];
      egolden = new int[150][nnlength][nKarkas+1];
           for(int l = 0 ; l < Verbs.length ; l++){
                subgraphs = finalmaxMatchings;
               //subgraphs = t.findsubGraphs(edges, nKarkas, nnlength, l, indexkarana);
          finalmatching = t.findfinalmatching(subgraphs, 5040, nnlength, nKarkas, indexkarta, indexkarana, NP, k1snoun, false);
          //semifinalmatching = semifinal;
          if(k1sexist) {
            finalcountParse = 0;
            break;
          }
          //System.out.println("generic marker k2p="+k2pexist+"    gereric marker k7t="+k7texist+"  number of parsed graphs g= "+g+" iteration="+iteration);
          //System.out.println("NP Semi parsed tree ");
          try{
          for(int m = 0 ; m < iteration ;m++){
            for(int j = 0 ; j < nnlength ; j++){
                    for(int k = 0 ; k < nKarkas ; k++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      //System.out.print("   "+semifinalmatching[m][j][k] );
                    }
                    //System.out.println();
                  }
                  //System.out.println(); 
          }
          }catch(ArrayIndexOutOfBoundsException ae){}
          
          if(g == 0 ){
            //if(!k2pexist && !k7texist){
            golden = semifinalmatching;
            g=iteration;
            if(k7texist){
              //System.out.println("generic marker k7t exists");
              finalcountParse = 0;
              
            }
          }
          else{
            //golden = finalmatching;
            System.out.println("g="+g);
            
            if(k7texist){
              //System.out.println("generic marker k7t exists");
              semifinalmatching = semifinalmatching;
              finalcountParse = 0;
            }
          }
          //else 
            //g = 0;
          //}
          System.out.println("new g="+g);
          System.out.println("Golden");
          for(int m1 = 0 ; m1 < (g)  && !k7texist ;m1++){
            for(int j1 = 0 ; j1 < nnlength ; j1++){
                    for(int k1 = 0 ; k1 < nKarkas ; k1++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      System.out.print("   "+golden[m1][j1][k1] );
                    }
                    System.out.println();
                  }
                  System.out.println(); 
          }
          //System.out.println("eGolden");
          for(int m1 = 0 ; m1 < (g) && !k7texist ;m1++){
            for(int j1 = 0 ; j1 < nnlength ; j1++){
                    for(int k1 = 0 ; k1 < nKarkas+1 ; k1++){
                      ////System.out.print("   "+ntsubgraphs[i][j][k]);
                      if(k1 < nKarkas)
                        egolden[m1][j1][k1] = golden[m1][j1][k1];
                      else
                        egolden[m1][j1][k1] = 0;
                      //System.out.print("   "+egolden[m1][j1][k1] );
                    }
                    //System.out.println();
                  }
                  //System.out.println(); 
          }
          
          //if(egolden[0][0].length < golden[0][0].length){
            goldenfinal = new int[500][nnlength][nKarkas];
            if(g != 1){
              goldenfinal = getGoldenunique(golden, g-1, nnlength, nKarkas);
            }
            else{
              goldenfinal = golden;
            }
        //  }
        //  else{
        //    //System.out.println("Hi it is egolden");
        //    nKarkas = nKarkas + 1;
        //    goldenfinal = new int[500][nnlength][nKarkas];
        //    goldenfinal = getGoldenunique(egolden, g, nnlength, nKarkas);

        //  }
          //System.out.println("Golden final");
          //if(!k1texist){
            for(int m1 = 0 ; m1 < (g-1) ;m1++){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        ////System.out.print("   "+ntsubgraphs[i][j][k]);
                        //System.out.print("   "+goldenfinal[m1][j1][k1] );
                      }
                      //System.out.println();
                    }
                    //System.out.println(); 
            }
          //}
          //if(!k7texist){
            parsedGraphindex = getparsedGraph1(goldenfinal, Actualvibhaktilist, g-1, nnlength, nKarkas, getk1s(),k7texist, k2pexist,k7tindex, k2pindex);
          //}
          //else{
          //  parsedGraphindex = 100;
          //}
          //System.out.println("Parsed Golden final");
          //if(!k1texist){
            if(parsedGraphindex  != 100){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        //System.out.print("  parsedindex=  "+parsedGraphindex);
                        //System.out.print("   "+goldenfinal[parsedGraphindex][j1][k1] );
                      }
                      //System.out.println();
                    }
                    if(!k7texist  && finalend == 0)
                      displayString(filename,goldenfinal,parsedGraphindex,nnlength,nKarkas,NP, false, getk1s());
            }
            
            else{
          //  if(!k1texist)
              //System.out.print("  parsedindex=  "+0);
              if(!k2pexist && !k7texist  && finalend == 0){
                displayString(filename,golden,0,nnlength,nKarkas,NP, false, getk1s());
              }
            }
          //}
          //else{
            //displayString(filename,egolden,0,nnlength,nKarkas,NP, false, getk1s());
          //}
      }
         
    }
    else if(projective){
      //System.out.println("Chunk exists ");
      t.getParsedsentence(N, V, NP, nnlength);
    }
    //displayfullparser(filename, goldenfinal, );
    finalsecondmatching = new int[iteration][nnlength][Verbs.length];
    finalsemisecondsfm = new int[3*iteration][nnlength][nKarkas+1];
    int cnt = 0;
    //int[][][] finalsemisecondsfm = new int[][][];
    int fl =0;
    if(finalcountParse == 0 && projective){
      //System.out.println("CAP OF GOOD HOPE");
      semisecondsfm = getaddcolumn(semifinalmatching);
      //System.out.println("second Semi parsed tree ");
      for(int m = 0 ; m < 3*iteration ;m++){
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                      
                    ////System.out.print("   "+semisecondsfm[m][j][k] );
                    fl = mm.getUniqueIncMatrix(m,nnlength,nKarkas+1,semisecondsfm);
                    //System.out.println(" fl=  "+fl+"  m="+m);
                    if(fl != -1){
              //  for(int i = 0 ; i < (nnlength-(nKarkas+1)) ; i++){
                  //for(int j1= 0 ; j1 < (nKarkas+1) ; j1++){
                    //System.out.println("Hi it is unique");
                    if(cnt< (iteration)){
                      finalsemisecondsfm[cnt][j][k] = semisecondsfm[m][j][k];
                      //System.out.println("  finalsemi["+cnt+"]["+j+"]["+k+"]  "+finalsemisecondsfm[cnt][j][k]);
                      //cnt++;
                    }
                    //cnt++;
                  //}
              //  }
              }
              else{
                
              }
          }
          //System.out.println();
              }
              //System.out.println(); 
              cnt++;
      }
      //System.out.println("Extended second :");
      for(int m = 0 ; m < 3*iteration ;m++){
        //System.out.println("Iteration = "+m);
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                //System.out.print("     "+semisecondsfm[m][j][k]);
              }
              //System.out.println();
            }
            //System.out.println();
            //System.out.println();
      }
      
      finalsecondmatching = t.findfinalmatching(semisecondsfm, 3*iteration, nnlength, nKarkas+1, indexkarta, indexkarana, NP, k1snoun, true);
      for(int m = 0 ; m < finalsecondmatching.length ;m++){
        //System.out.println("final Iteration = "+m);
        for(int j = 0 ; j < nnlength ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
                //System.out.print("     "+finalsecondmatching[m][j][k]);
              }
              //System.out.println();
            }
            //System.out.println();
            //System.out.println();
      }
      //System.out.println("Extended parsing iteration="+iteration);
      int eparsedGraphindex;
      //if(!k7texist ){
        eparsedGraphindex = getparsedGraph1(finalsecondmatching , Actualvibhaktilist, iteration, nnlength, nKarkas+1, getk1s(),k7texist, k2pexist,k7tindex,k2pindex);
      //}
      //else{
      //  eparsedGraphindex = 100;
      //}
        //System.out.println("Extended Parsed Golden final");
        //System.out.println("eparsed graph index="+eparsedGraphindex);
          //if(!k1texist){
            if(eparsedGraphindex  != 100){
              for(int j1 = 0 ; j1 < nnlength ; j1++){
                      for(int k1 = 0 ; k1 < nKarkas+1; k1++){
                        //if(goldenfinal[m1][j1][k1])
                        //System.out.print("  eparsedindex=  "+eparsedGraphindex);
                        //System.out.print("   "+finalsecondmatching[eparsedGraphindex][j1][k1] );
                      }
                      //System.out.println();
                    }
                    if(finalend == 0)
                    displayString(filename,finalsecondmatching,eparsedGraphindex,nnlength,nKarkas+1,NP, true, getk1s());
            }
            
            else{
          //  if(!k1texist)
              //System.out.print("  parsedindex=  "+0);
              if(!k2pexist && !k7texist  && finalend == 0){
                displayString(filename,finalsecondmatching,0,nnlength,nKarkas,NP, true, getk1s());
              }
            }
      //displayString(filename,egolden,g-1,nnlength,nKarkas+1,NP, true, getk1s());
    }
  } catch(NullPointerException ne) { };
     //  for(int l1 = 0 ; l1 < Verbs.length ; l1++){
       //   finalmatching = t.findfinalmatching(subgraphs, 2, nnlength, nKarkas, indexkarta, indexkarana, Nouns);
       //}
       if(projective){
         //intramatrix = t.intrachunk(NP,TNP,duplicate,nnlength, Verbs);
         //System.out.println("Non projective sentence :");
         for(int i = 0 ; i < nnlength ; i++){
            //System.out.println("NP["+i+"]="+NP[i]+"   TNP["+i+"]="+TNP[i]);
         }
         if(index1 ==0){
            /*if(!TNP[i].equals("null2")){
            
          }*/
            intramatrix = t.intrachunk(NP,TNP,duplicate,nnlength, Verbs, filename, filename1, output, Words);
         }
         else{
           intramatrix = t.intrachunk1(NP,TNP,duplicate,nnlength, Verbs, intramatrix, filename, output, Words);
         }
  }
  t.npextduplicate = intramatrix;
  //System.out.println("too much");
  /*for(int a = 0 ; a < 15 ; a++){
          for(int b = 0 ; b < 16 ; b++){
            //npextduplicate[0][a][b] = extduplicate[a][b];
            //System.out.print("   "+t.npextduplicate[a][b]);
          }
          //System.out.println();
  }*/
  //else{
  //  intramatrix = t.intrachunk(NP,TNP,duplicate,nnlength, Verbs);
  //}
   }
   
   public String[] getDMposition(String[] tgs, String[] dem, int now, int[] vp,int noverbs){
    String[] seperate = new String[5];
    for(int l = 0 ; l < seperate.length ; l++){
      seperate[l] = "null1";
    }
    int count = 0;
    //System.out.println("Hi its getDMposition noverbs="+noverbs);
    for(int i = 0 ; i < 2*now ; i++){
      //System.out.println("tgs["+i+"]="+tgs[i]);
    }
    for(int i = 0 ; i < 2*now ; i++){
      for(int j = 0 ; j < dem.length ; j++){
        //System.out.println("dem["+j+"]="+dem[j]);
        if(i%2 != 0 && tgs[i].equals(dem[j])){
          seperate[count] = dem[j]; 
          seperatepos[count] = i;
          //System.out.println("seperate["+count+"]="+seperate[count]+"  position["+count+"]=  "+seperatepos[count]);
          count++;
          
        }
       // try{
        if(i%2 != 0 ) {
          for(int k = 0 ; k <= (noverbs-1);k++){
            //System.out.println("vplength="+noverbs+"   count="+count);
            //    if(tgs[i].equals)
            if(count < noverbs){
              seperatepos[count] = vp[k];
              seperate[count] = tgs[2*vp[k]];
              //System.out.println("**seperate["+count+"]="+seperate[count]+"  position["+count+"]=  "+seperatepos[count]+"  vp["+k+"]="+vp[k]);
              count++;
            }
          }
        }
    //  }
     // catch(ArrayIndexOutOfBoundsException ae){};
      }
      
      //count++;
    }
    return seperate;
   }
   public static int getparsedGraph1(int[][][] gu, String[] vb, int a, int b, int c, boolean k1s, boolean k7t, boolean k2p, int k7tind, int k2pind){
    int[][][] temp = new int[a][b][c];
    temp = gu;
    int ct = 0;
    int[] m = new int[10];
  //System.out.println("get paresed graph1");

  //System.out.println("g="+g+"  a="+a+"    b="+b+"    c="+c+"   k1s="+k1s+"   k7t="+k7t+"    k2p="+k2p+"     k7tind="+k7tind+"   k2pind="+k2pind);
    for(int m1 = 0 ; m1 < temp.length ;m1++){
      for(int j1 = 0 ; j1 < b ;j1++){
        for(int n1 = 0 ; n1 < c ;n1++){
          //System.out.println("m1="+m1+"   j1="+j1+"   n1="+n1);

          if(temp[m1][j1][n1] == 1){
            if(c >8 ){
              if(k1s){
                if(gu[m1][0][0] == 1)
                  return m1;                
              }
            }
            //if(temp[m1][j1][0] == 1){
              if(n1 == 8 && temp[m1][j1][n1] == 1){
                if(k7t){
                  try{
                  if(temp[m1][k7tind][n1] == 1 && temp[m1][2][2] != 1){
                    //System.out.println("m1 for k7t is"+m1);
                    return m1;
                  }
                  }
                  catch(ArrayIndexOutOfBoundsException ae){
                  //System.out.println("It is not a chunker");
                }
                  
                }
                else
                  continue;
              }
              //else
                //continue;
            //}
             try{
            if(n1==7 && temp[m1][j1][n1]  == 1){
              if(k2p ){
                if(temp[m1][k2pind][n1] == 1 && temp[m1][0][0] == 1 ){
                  System.out.println("m1 for k2p is"+m1+"   k2pindex="+k2pind);
                  //if(b == 2){
                    //System.out.println("m1 for k2p is"+m1+"   b="+b);
                   // if(temp[m1][0][0] == 1){
                      //System.out.println(" k2p");
                      return m1;
                   // }
                     // continue;
                  //}
                  //m[ct] = m1;
                  //if(temp[m1][2][2] != 0) {
                  //  //System.out.println("good m1="+m1);
                  //}
                  //ct++;
                 // return m1;
                }
                
              }
              else
                continue;
            }
          }
          catch(ArrayIndexOutOfBoundsException ae){};
          System.out.println("Karana K3 j1="+j1);
          System.out.println("sentwords1["+j1+"]="+sentwords1[j1]);
         // for(int k=2;k<sentwords1.length;k++){
         	 if( !sentwords1[4].equals("NNP")){
          		if(temp[m1][1][2] == 1){
          			System.out.println("m1="+m1);
                  return m1;
          		}
      	  	}
      	  //}
            //else
            //  continue;
            System.out.println("sentwords1["+j1+"]="+sentwords1[j1]);
            //for(int j=0;j<sentwords1.length;j++){
              //if(sentwords1[j1].equals("PSPLE") || sentwords1[j1].equals("PSPLAI") || sentwords1[j1].equals("PSPO") || sentwords1[j1].equals("PSPKO")){
              if(!k1s && !k7t && sentwords1[j1].equals("PSPLAI")){
                //System.out.println("Hi psplai exist ");
                if(temp[m1][1][3] == 1){
                  return m1;
                } 
              }
              else if(!k1s && !k7t){
                //System.out.println("Hi psplai does not exist ");
                /*if(b==1){
                  if(temp[m1][0][1] == 1){
                    return m1;
                  }
                }*/
                if(b == 2 && !k2p){
                  //System.out.println("temp="+temp[m1][1][1]);
                  if(temp[m1][1][1] == 1 && !sentwords1[1].equals("PSPO"))
                    return m1;
                  else if(sentwords1[j1].equals("PSPO") && temp[m1][j1-1][3] == 0){   // j1 is always 1 because of the existence of PSPO followed by noun
                    //System.out.println("Hi psplai does not exist but PSPO exist ");
                    return m1;
                  }
                }
                  
              }
              
              
            //}
            /*if(b==2){
              if(temp[m1][j1][1] == 1){
                return m1;
              }
            }*/
              
            
            //}
            
          }
        }
      }
    }
    return 0;
   }
   public static int getparsedGraph(int[][][] gu, String[] vb, int a, int b, int c, boolean k1s){
    int[][][] temp = new int[a][b][c];
    int pos1;
    int pos2;
    int pos3 = 0;
    int flag = 0;
    int nullct = 1;
    int sampradancount = 0;
    for(int m1 = 0 ; m1 < g ;m1++){
      if(flag == 10 || m1 == g) break;
      if(g == 1) return m1;
      //System.out.println(" parsed Golden Unique m1="+m1+"  g="+g);
      //index = m1; 
    for(int j1 = 0 ; j1 < b && (flag == 0 || flag == 2); j1++){
            for(int n1 = 0 ; n1 < c ; n1++){
              if(m1 < (g-2) && (flag == 0 ||flag == 2) && gu[m1][j1][n1] != gu[m1+1][j1][n1]){
                pos1 = j1;
                pos2 = n1;
                //System.out.println("pos1="+pos1+"   pos2="+pos2+"  k1s="+k1s);
                //System.out.println("m1="+m1+"   j1="+j1+"   n1="+n1+"    c="+c);              

                if(b == 1) {
                  //System.out.println("b="+b);
                  if(gu[m1][0][0] == 1)
                    return m1;
                  else if(gu[m1][0][1] == 1)
                    return m1;
                  else if(gu[m1][0][3] == 1)
                    flag = 2;
                  else flag = 5;
                  
                  
                  
                //}
                }
                if(b == 2){
                  //System.out.println("b2="+b);
                  if(gu[m1][0][0] ==1 && gu[m1][1][1] == 1 && !k1s){
                    return m1;
                  }
                  if(gu[m1][0][6] == 1 && gu[m1][1][1] == 1 && !k1s){
                    return m1;
                  }
                  if(gu[m1][0][0] == 1 && gu[m1][1][8] == 1 && k1s){
                    return m1;
                  }
                  else
                    m1++;
                  /*else if(j1 == (b-1) && n1 == (c-1) ){
                    m1++;
                    //System.out.println("m1="+m1+"   j1="+j1+"   n1="+n1);
                  }
                  else if(n1 == (c-1)){
                      j1++;
                    }
                    else if(j1 == (b-1)){
                      n1++;
                    }*/
                    
                  //m1++;
                    
                  
                          //}                 
                                      
                }
                if(b>2){
                  //System.out.println("b2="+b+"   M1"+m1);
                  if(gu[m1][0][0] ==1 && gu[m1][1][8] == 1 && gu[m1][2][1] == 1 && !k1s){
                    return m1;
                  }
                  else
                    flag = 2;
                }
                if(flag == 2 ){
                  //System.out.println("sampradancount="+sampradancount);
                  
                  if(b  > 2 || sampradancount == 1 && pos2 == 3){
                    
                    pos3 = m1+1;
                    //System.out.println("new pos3="+pos3);
                    flag = 10;
                    break;
                  }
                  else{
                    n1++;
                    //if(n1 == c){
                    sampradancount++;
                    //System.out.println("add sampradancount="+sampradancount);
                    //}
                  }
                  //break;
                }
                else{
                  if(j1 == (b-1) && n1 == (c-1) ){
                    m1++;
                    //System.out.println("m1="+m1+"   j1="+j1+"   n1="+n1);
                  }
                  else if(n1 == (c-1)){
                      j1++;
                    }
                    else if(m1 == 0){
                      n1++;
                    }
                    else{
                      continue;
                    }
                  
                }
                if(k1s){
                  if(pos2 == 7){
                    //System.out.println("k1s pos3="+pos3);
                    pos3 = m1+1;
                    flag = 0;
                    break;
                  }
                }
                try{
                  if(vb[pos2].equals(null)){
                    nullct = 0;
                  }
                  else{
                    nullct = 1;
                  }
                }
                catch(ArrayIndexOutOfBoundsException ae){ }
                
                //try{
                ////System.out.println("vb["+pos2+"]="+vb[pos2]+"   flag="+flag+"   b="+b+"   nullct="+nullct);
                  //if(fl)
                  if(b > 2 && !k1s && (nullct == 1 || flag == 2 )){
                    if(pos2 == 6){
                      flag = 0;
                    }
                    else{
                      flag  = 2;
                    }
                    pos3 = m1;
                    //System.out.println("pos3="+pos3);
                    //m1++;
                    //break;
                  }
                  else{
                    if(b == 0){
                      pos3 = m1;
                      flag = 10;
                    }
                    flag = 0;
                    pos3 = m1;
                    //System.out.println("else pos3="+pos3);
                    if(flag == 5) continue;
                              }
                  
                //}
                //catch(ArrayIndexOutOfBoundsException ae){}
                //break;
              } 
              //pos3 = m1;  
              //break;
            }
            //break;
          }
        }
        //System.out.println("**pos3="+pos3);
        if(flag == 1 || flag == 0 || flag == 10){
          return pos3 ;
        }
        else{
                return 100;
        }
   }
   
   public static int[][][] getGoldenunique(int[][][] gu,int a, int b, int c){
  //System.out.println("a="+a+"   b="+b+"   c="+c);
    int[][][] temp = new int[a][b][c];
    //System.out.println("a="+a+"   b="+b+"   c="+c);
    temp[0] = gu[0];
    //temp = gu;
    boolean flag = false;
    int index = 1;
    int count = 0;
    for(int m1 = 1 ; m1 < g ;m1++){
      //System.out.println("Golden Unique m1="+m1);
      index = m1; 
    for(int j1 = 0 ; j1 < b ; j1++){
            for(int k1 = 0 ; k1 < c ; k1++){
              for(int t = 0 ; t < index && flag; t++){
                if(gu[m1][j1][k1] == gu[t][j1][k1]){
                  flag = true;  
                  //System.out.println("m1="+m1);     
                }
                else{
                  flag = false;
                  //temp[count][j1][k1] = gu[m1][j1][k1];
                  //System.out.println("count = "+count+"    flag="+flag);
                  //m1++;
                  //count++;
                  break;
                }
              }
            }
          }
          try{
          if(!flag ){
            for(int j2 = 0 ; j2 < b ; j2++){
              for(int k2 = 0 ; k2 < c ; k2++){
                //System.out.println("*m1="+m1+"  count="+count);
                temp[count][j2][k2] = gu[m1][j2][k2]; 
              }
            }
            count++;
          }
         }
          catch(ArrayIndexOutOfBoundsException ae){};
          
            
        }
        return temp;
   }
   public static boolean isunique(int[][][] seconds, int index, int n){
    boolean flg = false;
    int count = 0;
    int count1 = 0;
    for(int m = 0 ; m < index ;m++){
        for(int j = 0 ; j < n ; j++){
              for(int k = 0 ; k < (nKarkas+1) ; k++){
            if(seconds[m][j][k] != seconds[index][j][k]){         
              if(count <((nKarkas+1)*n - 1 )){
                count++;  
                //flg = true;
              }
              else{
                flg = true;
              }
            }
            else{
              if(count1 <((nKarkas+1)*n - 1 )){
                count1++; 
                //flg = true;
              }
              else{
                flg = false;
                break;
              }
            }
              }
            }
  }
  return flg;
   }
   public static int[][][] getaddcolumn(int[][][] sfm){
    int[][][] secondsfm = new int[3*sfm.length][sfm[0].length][sfm[0][0].length+1];
    try{
    for(int p = 0 ; p < (iteration) ; p++){
      for(int q = 0 ; q < sfm[0].length; q++){
        for(int r = 0 ; r < (sfm[0][0].length+1);r++){
          if(r < sfm[0][0].length){
            secondsfm[3*p][q][r] = sfm[p][q][r];
            //if(p!=0){
              secondsfm[3*p+1][q][r] = sfm[p][q][r];
              secondsfm[3*p+2][q][r] = sfm[p][q][r];            
            //}
          }
          else{
            secondsfm[p][q][r] = 0; 
          }
        }
      }
    }

    //secondsfm = sfm;
    
    int count = 0;
    for(int i = 0 ; i < (iteration) ; ){
      //count = 0;
      //System.out.println("i="+i);
      for(int j = 0 ; j < sfm[0].length ; j++){
        for(int k = 0 ; k < (sfm[0][0].length+1) && i< (iteration) ; k++ ){
          if(k < sfm[i][j].length ){
            ////System.out.println("count="+count);
            //secondsfm[count][j][k] = sfm[i][j][k];
            
          }
          else{
            for(int l = 0 ;l < k ; l++){
              if(secondsfm[count][j][l] ==  1)
                secondsfm[count][j][l] =  0;
            }         
              secondsfm[count][j][k] =  1;  
              //System.out.println("SECONDS: "+i+"  count="+count+"   k="+k);
              //System.out.println("seconds["+(count)+"]["+j+"]["+k+"]="+secondsfm[count][j][k]);
              count++;
              if(count%3 == 0){
                //count = 0;
                i++;
                
              }
              //i++;
              break;
              
            }
          }
        }
        //count++;
      }
       }
   catch(ArrayIndexOutOfBoundsException ae){};
      return secondsfm;
    }
   
   //}
   public String[] getVerbs(){
    return vbs;
   }
   
  
   
  /* public int[][] checkVerb(int[][][] fg, int karkas){
      for(int i = 0 ; i < 1 ; i++){
        for(int j = 0 ; j < karkas ; j++){
          //System.out.print("  fg[0][0]["+j+"]="+fg[0][0][j]);
          if(fg[0][0][0] )
        }
        //System.out.println();
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
  //System.out.println("t1="+t1);
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
   
   public void readFile(String fileName, String outputName, String dispfile,  String gl) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName, true), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        filename = dispfile;
//        wordnetfile = wordnet;
        String vibhakti1 = "hi";
        try {

            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            String linec =  br1.readLine();
            //System.out.println("hi");
            String[] words = new String[300];// = line.split(" ");//those are your words
            sentwords = new String[300];
            sentwords1 = new String[300];
            int flag = 0, flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
            int alaram = 0;
            int dgindex = 0 ;
            String ws,ws1;
            int dontgo = 0;
            int vbexist = 0;
            int counter = 0 ;
            int counterc = 0;
            String[] sws = new String[2];
            int nowords = 0;
            String wsc;
            int VFexist = 0;
            int ekaexist = 0;
            String tempVF = "hari";
            String[] twd = new String[40];   // check verb vibhakti
            //fout.write("ITS A READFILE IN LWG.JAVA");
            //fout.write("\n");
            //System.out.println("verb vibhakti"+linec);
            while(linec != null){
              StringTokenizer stc = new StringTokenizer(linec);
              //System.out.println("check verb vibhakti");
                while (stc.hasMoreTokens()) {
                  counterc++;
                  //System.out.println("counterc="+counterc);
                  wsc = stc.nextToken();
                  twd[counterc] = wsc;
                  //System.out.println("twd["+counterc+"]="+twd[counterc]);
                  if(twd[counterc].equals("QW")){
                     QWexist = counterc-1;
                     
                      //System.out.println("twd["+counterc+"]="+twd[counterc]+"   QWexist="+QWexist);
                  }
                  if(twd[counterc].equals("VF")){
                     VFexist = counterc-1;
                     tempVF = twd[VFexist];
                      //System.out.println("twd["+counterc+"]="+twd[counterc]+"   VFexist="+VFexist);
                  }

                  //if()
                }
                
                for(int i = 1 ; i < counterc  ; i++){
                  //for(int j = 0 ; j < verbs.length ; j++){
                    if(i%2 == 0 ){
                      //System.out.println("chalo twd["+i+"]"+twd[i]);
                      if(twd[i].equals("VF") && !twd[i+2].equals("VAUX")){
                   dgindex = i-1;
                   //System.out.println("dgindex="+dgindex);
                   break;    
                } 
                else if(twd[i].equals("VF") && twd[i+2].equals("VAUX")) {
                    //System.out.println("chalo pachha twd["+(i+2)+"]"+twd[i+2]);
                      dgindex = i+2;
                   //System.out.println("dgindex="+dgindex);
                   break;    
                }
                else if(twd[i].equals("VAUX") || twd[i].equals("VF")){
                  vbexist = i-1;
                  break;
                }
                    }
                  //}
          }
          linec = br.readLine();
            }
            while (line != null) {
      StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                            counter++;
                          //  alaram = 0;
                     // flag = 0;
                            ws = st.nextToken();
                            System.out.println("ws["+counter+"]="+ws);
                            if(ekaexist == 1){

                            	ws = "VF";
                            	ekaexist = 0;
                            }
                        //    ws1 = st.nextToken();
                            if(ws.equals("\u092B\u093E\u0932\u0947") || ws.equals("\u0915\u093e\u0932\u0947")){
                              dontgo = 1;
                              dgindex = counter ;
                            }

                            if(counter == vbexist){
                              dgindex = counter ;
                            }
                            if(flag7 == 2){
                              if(!ws.equals("NN")){
                                  ws = "NN";
                              }
                            }
                            if(flag8 == 2){
                              if(!ws.equals("NN")){
                                  ws = "NN";
                              }
                            }


                            //System.out.println("CHECK FOR NEW WORD *** ");
                            //System.out.println("beforeWS = "+ws+" flag = "+flag +"  counter ="+counter+"  nowords="+nowords);
                            
                            if(flag == 1 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                              if(dgindex != counter)
                                    sws = RegionMatchesDemo("\u0932\u0947", ws);
                                else
                                  sws[0] = "ab";
                                    
                              //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                              if(sws[0].equals("ab"))
                                flag = 0;
                        else
                                flag = 1;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     
                     if( flag == 1 ){
                        words[nowords] = sws[0];
                        //System.out.println("wd["+nowords+"]="+words[nowords]);
                        //System.out.println("beforenowods="+nowords);
                    //    if(nowords%2 != 0){
                //          words[nowords+1] = ws;
                  //        //System.out.println("wd["+(nowords+1)+"]="+words[nowords+1]);
               // }
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPLE";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                        
                     }
                     
                     //System.out.println("CHECK FOR PLAI ");
                     if( flag1 == 2 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("***wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                       sws = RegionMatchesDemoLai("\u0932\u093E\u0908", ws);
                else
                  sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if( sws[0].equals("ab"))
                       flag1 = 0;
               else
                        flag1 = 2;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag1 == 2){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPLAI";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     

                       //System.out.println("Projective CHECK FOR eko ");
                     //System.out.println("words["+(nowords-3)+"]="+ws);  
                    System.out.println("dgindex="+dgindex);
                     if( flag6 == 2 && counter%2 == 0 ){
                               // words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                              sws = RegionMatchesDemoLai("\u0947\u0915\u094b", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag6 = 0;
               else
                        flag6 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag6 == 2 ){
                        
                        System.out.println("semi imp eko words["+(nowords)+"]="+words[nowords]);
                        //words[nowords + 1] ="VF";
                      ekoexist = 1;
                      if(dgindex <2){
                      	words[nowords] = ws;
                      	alaram = 1;
                        flag6 = 2;
                      	System.out.println("danger eko");
                      }
                        //System.out.println("beforenowods HI** ="+nowords+"   ekoexist="+ekoexist);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                       // words[nowords+2] = sws[1];
                        //words[nowords+3] = "PSPKO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        //nowords = nowords + 4;
                        alaram = 0;
                        flag6 = 0;
                     }
                    

                     //System.out.println("Projective CHECK FOR eka ");
                     System.out.println("not imp words["+(nowords-3)+"]="+ws);  
                    
                     if( flag9 == 2 && counter%2 == 0 ){
                                //words[nowords-3] = ws;
                          System.out.println("imp wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                              sws = RegionMatchesDemoLai("\u090f\u0915\u093e", ws);
                      else
                        sws[0] = "ab";
                     System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag9 = 0;
               else
                        flag9 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag9 == 2 ){
                        //words[nowords-3] = ws;
                        System.out.println("semi imp words["+(nowords-3)+"]="+ws);
                       // words[nowords -2] ="VF";
                      ekaexist = 1;
                        System.out.println("beforenowods HI** ="+nowords+"   ekaexist="+ekaexist);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                       // words[nowords+2] = sws[1];
                        //words[nowords+3] = "PSPKO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                       // nowords = nowords + 1;
                        alaram = 0;
                        flag9 = 0;
                     }

                     System.out.println("Ekoexist="+ekoexist+"   ekaexist="+ekaexist);
                     //System.out.println("Projective CHECK FOR PKO ");
                     //System.out.println("words["+(nowords-3)+"]="+ws);  
                    
                     if( flag2 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter && flag6 != 2 && flag9 != 2)
                              sws = RegionMatchesDemo("\u0915\u094B", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag2 = 0;
               else
                        flag2 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag2 == 2 && ekoexist == 0 && dgindex < 2){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPKO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     else{
                     	alaram = 0;
                     	flag2=0;
                     }
                     
                     //System.out.println("CHECK FOR MA ");
                     //System.out.println("words["+(nowords)+"]="+ws);  
                    ////System.out.println("words["+(nowords+1)+"]="+sws[1]);
               //     if(ws == "VF"){
                 //     words[]
                   // }
                   int flagsampradan = 0;
                     if( flag3 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter)
                              sws = RegionMatchesDemo("\u092E\u093E", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag3 = 0;
               else
                        flag3 = 2;
                        
                        
                         if(flag3 == 2 && QWexist == 100){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPO";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                      //System.out.println("CHECK FOR PSPO "+flag4);
                     if( flag4 == 2 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("***wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                       sws = RegionMatchesDemoLagi("\u0932\u093E\u0917\u093F", ws);
                else
                  sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if( sws[0].equals("ab"))
                       flag4 = 0;
               else
                        flag4 = 2;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag4 == 2){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
  /*                   String temp = "ab";
                     if(flag3 == 2){
                 if(st.hasMoreTokens()){
                        if(st.nextToken() == "VF"){
                    flag3 = 0;
                  }
                }
               }*/
                     
                /*     if(dgindex == 0 && flag3 == 2 && flagsampradan != 0){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPO";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }*/


                     //System.out.println("Projective CHECK FOR saang ");
                     //System.out.println("words["+(nowords-3)+"]="+ws);  
                    
                     if( flag5 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter ){
                              sws = RegionMatchesDemoLai("\u0938\u0901\u0917", ws);
                            
                            if(sws[0].equals("ab"));
                              sws = RegionMatchesDemoLai("\u0938\u0902\u0917", ws);
                            }
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag5 = 0;
               else
                        flag5 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag5 == 2 ){
                       // sangexist = 1;
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }

                     //System.out.println("Projective CHECK FOR bat ");
                     //System.out.println("words["+(nowords-3)+"]="+ws);  
                    
                     if( flag7 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex == counter )
                              sws = RegionMatchesDemoLai("\u092c\u093e\u091f", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag7 = 0;
               else
                        flag7 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag7 == 2 ){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                     //System.out.println("Projective CHECK FOR Haru ");
                     //System.out.println("words["+(nowords-3)+"]="+ws);  
                    
                     if( flag8 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex == counter )
                              sws = RegionMatchesDemoLai("\u0939\u0930\u0942", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag8 = 0;
               else
                        flag8 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag8 == 2 ){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "HRU";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                            //System.out.println("k2exist ="+k2exist);
                            if(k2exist == 0 )
                              sws = RegionMatchesDemoLagi("\u0907\u0928\u094d\u091b", ws);
                             else
                               sws[0] = "ab";
                             //System.out.println("Inchha sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                           if(sws[0].equals("ab") && k2exist == 0 )
                               k2exist = 0;
                        else
                                k2exist = 1;
                        
                       if(k2exist == 0 )
                              sws = RegionMatchesDemoLai("\u090f\u0915\u093e", ws);
                             else
                               sws[0] = "ab";
                             //System.out.println(" eka sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                           if(sws[0].equals("ab") && k2exist == 0)
                               k2exist = 0;
                        else
                                k2exist = 1;

                              if(k2exist == 0 )
                              sws = RegionMatchesDemoLai("\u092f\u094b\u0938", ws);
                             else
                               sws[0] = "ab";
                             //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                           if(sws[0].equals("ab") && k2exist == 0)
                               k2exist = 0;
                        else
                                k2exist = 1;
               
               if(k2exist == 0 )
                              sws = RegionMatchesDemoLai("\u0907\u092f\u094b", ws);
                             else
                               sws[0] = "ab";
                             //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                           if(sws[0].equals("ab") && k2exist == 0 )
                               k2exist = 0;
                        else
                                k2exist = 1;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     
                     
                     //System.out.println("k2exist="+k2exist);
                     
                     //System.out.println("flag="+flag+"  flag1 = "+flag1+"   flag2 ="+flag2+"    flag3="+flag3+"   flag4="+flag4+"  nowords="+nowords+"  alaram = "+alaram);
                     
                      if(flag == 0 && flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0 && flag8 == 0 && flag9 == 0 && alaram == 0){
                              words[nowords]=ws;
                              System.out.println("**wd["+(nowords)+"]="+words[nowords]);
                              nowords++;
                      }
                      else if(flag == 0 && flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0 && flag8 == 0 && flag9 == 0) {
                              alaram = 0;
                      }
                 }
                 line = br.readLine();
       //     }
          int bus = 0;
          String[] tem = new String[nowords-2];
          //System.out.println("nowods="+nowords);
             for(int i = 0 ; i < nowords; i++){
                  if(VFexist  != 0 && ekoexist == 1){
                    words[VFexist-1] = tempVF;
                  //  words[VFexist] = VF;
                   // words[VFexist+1] = words[VFexist+3];
                    //words[VFexist+2] = words[VFexist+4];
                    try{
                    if(i == (VFexist+1)){
                      for(int j = i ; j < nowords ; j++){
                         tem[j] =words[j+2];
                         System.out.println("tem["+j+"]="+tem[j]);
                    }


                    }
                  }
                  catch(ArrayIndexOutOfBoundsException e){};
                  //  nowords = nowords - 2;
                   /* for(int j = VFexist+1 ; bus != 1 && j < (nowords-2) ; j++ ){

                      words[j] = words[j+2];
                      //System.out.println("newj = "+j);
                      bus = 1;
                    }*/
                   // words[]
                  }
                  //System.out.println("nowords="+nowords);
                }
                 if(VFexist  != 0 && ekoexist == 1){
                nowords = nowords - 2;
              }
                for(int i = 0 ; i < nowords; i++){
                  if(i > VFexist && ekoexist == 1){
                    words[i] = tem[i];
                  }
                   //System.out.println("words["+i+"]="+words[i]);
                   fout.write(words[i]);
                   fout.newLine();
              }
          fout.newLine();
          int j = 0;
          int k = 0;
          sentwords = new String[nowords];
          sentwords1 = new String[nowords];
          Actualvibhaktilist = new String[nowords/2];
        //  int crt = 0;
          // sentwords = words and sentwords1 = tags
          for(int i = 0 ; i < nowords ; i++){
                if(i %2 == 0){
                  sentwords[j] = words[i];
                  System.out.println("sentwords["+j+"]="+sentwords[j]);
                //  fout.write("sentwords["+j+"]="+sentwords[j]);
                  //fout.newLine();
                  j++;
                }
                else{
                  sentwords1[k] = words[i];
                  
        System.out.println("sentwords1["+k+"]="+sentwords1[k]);
        /*if(TNP[crt].equals("null2")){
          TNP[crt] = sentwords1[k];
          crt++;
        }*/
                  //fout.write("sentwords1["+k+"]="+sentwords1[k]);
                 // fout.newLine();
                  k++;
            }
              }
              try{
              for(int j1 = 0 ; j1 < nowords/2 ; j1++){
                if(sentwords1[j1].equals("VF") && !sentwords1[j1-1].equals("NN") && !sentwords1[j1-1].equals("NNP") ){
                  //if(ws.equals("VF") && ftg == 0){
                            //System.out.println("CHECK FOR PKO ");
                        //nowords--;
                        //ws = words[nowords - 3];
                         //ftg = 1;
                        //ws = words[nowords-2] ;
                        //System.out.println("sentwords1["+(j1)+"]="+sentwords1 [j1]+"  flag2 ="+flag2+"   counter="+counter+"  dgindex="+dgindex); 
                    
                     //   if( flag2 == 2 && counter%2 == 0 ){
                                   ws = sentwords[j1] ;
                         //   //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                              //  }
                               // if(dgindex != counter ){
                                //System.out.println("welcome to PKO again");
                                sws = RegionMatchesDemo("\u0915\u094B", ws);
                       //   }
                       //   else
               //             sws[0] = "ab";
                      //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                        if(sws[0].equals("ab") )
                           flag2 = 0;
                  else
                            flag2 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                      if( flag2 == 2 ){
                        //    words[nowords] = sws[0];
                          //System.out.println("beforenowods HI** ="+nowords);
                          sentwords1[j1] = "VMKO";
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        //  words[nowords+2] = sws[1];
                        //  words[nowords+3] = "PSPKO";
                          ////System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        //  nowords = nowords + 4;
                      //    alaram = 1;
                       }
          
                     
                     //}
                }
              }
              }
              catch(NullPointerException nullPointer){
              
              }
              
          //System.out.println("line= "+line+"  noWords ="+nowords+"    noTags = "+noTags);
          int cnt = 0;
          int t=0;
          String temp1 = "hi";
          int flg = 0;
          while(cnt < (nowords/2-1)){
//          int cnt = 0;
      //System.out.println("sentwords["+cnt+"]="+sentwords[cnt]+"    sentwords1 tags["+cnt+"]="+sentwords1[cnt]);
//      if(sentwords1[cnt-1])
      /*Actualvbt = getVbt(sentwords[cnt],sentwords1[cnt],cnt);
      //Actualvbt = getVibhakti(sentwords1[cnt], nPhase);
      //System.out.println("Actualvbt = "+Actualvbt+"   flg="+flg);
      if(cnt == 0){
        temp1 = Actualvbt;
      }
      else {
        if(temp1.equals(Actualvbt)){
          Actualvibhaktilist[t] = "null";
          flg = 1;
        }
        else if(! Actualvbt.equals("null1")){
          Actualvibhaktilist[t] = Actualvbt;
          flg = 1;
        }
        else{
          flg = 0;
        }
      }
            */
            //System.out.println("Actualvbt="+Actualvbt+"   cnt="+cnt+"   t= "+t);
            //if(cnt%2 != 0){
//              Actualvibhaktilist[t] = Actualvbt;
        if(flg == 1)
                //System.out.println("**Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]);
             // t++;
            //  cnt++;
      //}
            fout.newLine();
            if(flg == 1){
             // fout.write("  Actualvbt ="+Actualvbt+"  Actualvbtlist["+t+"]="+Actualvibhaktilist[t]);
              t++;
            }
                  fout.newLine();
                  temp1 = Actualvbt;
                  cnt++;
                //  t++;
    }

          Words = words;
          noWords = nowords;
          noTags = nowords/2;
        //  fout.write(gl);
          maxobswords = noTags;
//          Verbs = findVerbs(sentwords1, sentwords, verbs, xm);
          break;
           }
       } finally {
           br.close();
         fout.close();
        }
    }
    /*public String[] getVibhakti(String[] pos, String[] nph){


    }*/
    public void NPreadFile(String[] fileName, String outputName, String dispfile,  String gl) throws IOException {
       // BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
     //   BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName, true), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        filename = dispfile;
//        wordnetfile = wordnet;
        String vibhakti1 = "hi";
        int count = 0;
        int count1 = 0;
        try {

            StringBuilder sb = new StringBuilder();
            String line =  fileName[count];//br.readLine();
            String linec =  fileName[count1];//br1.readLine();
            for(int i = 0 ; i < fileName.length ; i++){
              //System.out.println("NP line["+i+"]="+fileName[i]+"    NP linec ="+linec);
      }
            //System.out.println("hi");
            String[] words = new String[300];// = line.split(" ");//those are your words
            words = fileName;
            sentwords = new String[300];
            sentwords1 = new String[300];
            int flag = 0, flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;
            int alaram = 0;
            int dgindex = 0 ;
            String ws,ws1;
            int ftg = 0;
            int counter = 0 ;
            int counterc = 0;
            String[] sws = new String[2];
            int nowords = 0;
            String wsc;
            String[] twd = new String[40];   // check verb vibhakti
           // fout.write("ITS A READFILE IN LWG.JAVA");
            fout.write("\n");
            //System.out.println("verb vibhakti"+linec);
            while(linec != null){
              /*StringTokenizer stc = new StringTokenizer(linec);
              //System.out.println("check verb vibhakti");
                while (stc.hasMoreTokens()) {
                  counterc++;
                  //System.out.println("counterc="+counterc);
                  wsc = stc.nextToken();
                  twd[counterc] = wsc;
                  //System.out.println("twd["+counterc+"]="+twd[counterc]);
                  //if()
                }*/
                for(int i = 1 ; i < counterc ; i++){
                  //for(int j = 0 ; j < verbs.length ; j++){
                    if(i%2 == 0 ){
                      //System.out.println("chalo twd["+i+"]"+twd[i]);
                      if(twd[i].equals("VF")){
                   dgindex = i-1;
                   //System.out.println("dgindex="+dgindex);
                   break;    
                } 
                    }
                  //}
          }
        //  linec = br.readLine();
        
        count1++;
        linec = fileName[count1];
            }
            while (line != null) {
      //StringTokenizer st = new StringTokenizer(line);
               // while (st.hasMoreTokens()) {
                            counter++;
                          //  alaram = 0;
                     // flag = 0;
                     ws = line;
                            //ws = st.nextToken();
                        //    ws1 = st.nextToken();
                            //System.out.println("CHECK FOR NEW WORD *** ");
                            //System.out.println("beforeWS = "+ws+" flag = "+flag +"  counter ="+counter+"  nowords="+nowords);
                            
                            if(flag == 1 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                              if(dgindex != counter)
                                    sws = RegionMatchesDemo("\u0932\u0947", ws);
                                else
                                  sws[0] = "ab";
                                    
                              //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                              if(sws[0].equals("ab"))
                                flag = 0;
                        else
                                flag = 1;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     
                     if( flag == 1 ){
                        words[nowords] = sws[0];
                        //System.out.println("wd["+nowords+"]="+words[nowords]);
                        //System.out.println("beforenowods="+nowords);
                    //    if(nowords%2 != 0){
                //          words[nowords+1] = ws;
                  //        //System.out.println("wd["+(nowords+1)+"]="+words[nowords+1]);
               // }
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPLE";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                        
                     }
                     
                     //System.out.println("CHECK FOR PLAI ");
                     if( flag1 == 2 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("***wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                       sws = RegionMatchesDemoLai("\u0932\u093E\u0908", ws);
                else
                  sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if( sws[0].equals("ab"))
                       flag1 = 0;
               else
                        flag1 = 2;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag1 == 2){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPLAI";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                     
                     //System.out.println("non projective CHECK FOR PKO ");
                     //System.out.println("words["+(nowords-3)+"]="+ws+"  flag2 ="+flag2+"   counter="+counter+"  dgindex="+dgindex); 
                    
                     if( flag2 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter ){
                              //System.out.println("welcome to PKO");
                              sws = RegionMatchesDemo("\u0915\u094B", ws);
                      }
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag2 = 0;
               else
                        flag2 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag2 == 2 ){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPKO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                     
                     
                     //System.out.println("CHECK FOR MA ");
                     //System.out.println("words["+(nowords)+"]="+ws+"  sentwords1 ");  
                    ////System.out.println("words["+(nowords+1)+"]="+sws[1]);
               //     if(ws == "VF"){
                 //     words[]
                   // }
                   int flagsampradan = 0;
                     if( flag3 == 2 && counter%2 == 0 ){
                                words[nowords-3] = ws;
                          //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter)
                              sws = RegionMatchesDemo("\u092E\u093E", ws);
                      else
                        sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if(sws[0].equals("ab") )
                       flag3 = 0;
               else
                        flag3 = 2;
                        
                        
                         if(flag3 == 2 ){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPO";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
                     
                      //System.out.println("CHECK FOR PSPO "+flag4);
                     if( flag4 == 2 && counter%2 == 0){
                                words[nowords-3] = ws;
                          //System.out.println("***wd["+(nowords-3)+"]="+words[nowords-3]);
                            }
                            if(dgindex != counter )
                       sws = RegionMatchesDemoLagi("\u0932\u093E\u0917\u093F", ws);
                else
                  sws[0] = "ab";
                     //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                     if( sws[0].equals("ab"))
                       flag4 = 0;
               else
                        flag4 = 2;
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                     if( flag4 == 2){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        words[nowords+3] = "PSPO";
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }
  /*                   String temp = "ab";
                     if(flag3 == 2){
                 if(st.hasMoreTokens()){
                        if(st.nextToken() == "VF"){
                    flag3 = 0;
                  }
                }
               }*/
                     
                /*     if(dgindex == 0 && flag3 == 2 && flagsampradan != 0){
                        words[nowords] = sws[0];
                        //System.out.println("beforenowods HI** ="+nowords);
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        words[nowords+2] = sws[1];
                        //System.out.println("wd["+(nowords+2)+"]="+words[nowords+2]);
                        words[nowords+3] = "PSPO";
                        //System.out.println("wd["+(nowords+3)+"]="+words[nowords+3]);
                        //System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        nowords = nowords + 4;
                        alaram = 1;
                     }*/
                     
                     
                     //System.out.println("flag="+flag+"  flag1 = "+flag1+"   flag2 ="+flag2+"    flag3="+flag3+"   flag4="+flag4+"  nowords="+nowords+"  alaram = "+alaram);
                     
                      if(flag == 0 && flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && alaram == 0){
                              words[nowords]=ws;
                              //System.out.println("wd["+(nowords)+"]="+words[nowords]);
                              nowords++;
                      }
                      else if(flag == 0 && flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0) {
                              alaram = 0;
                      }
                // }
                // line = br.readLine();
                count++;
                line = fileName[count];
                //System.out.println("new line="+line);
       //     }
          //System.out.println("nowods="+nowords);
             for(int i = 0 ; i < nowords; i++){
                   //System.out.println("words["+i+"]="+words[i]);
                   fout.write(words[i]);
                   fout.newLine();
              }
          fout.newLine();
          int j = 0;
          int k = 0;
          nowords = fileName.length;
          sentwords = new String[nowords];
          sentwords1 = new String[nowords];
          Actualvibhaktilist = new String[nowords/2];
        //  int crt = 0;
          // sentwords = words and sentwords1 = tags
          for(int i = 0 ; i < nowords ; i++){
                if(i %2 == 0){
                  sentwords[j] = words[i];
                  //System.out.println("sentwords["+j+"]="+sentwords[j]);
                 // fout.write("sentwords["+j+"]="+sentwords[j]);
                  //fout.newLine();
                  j++;
                }
                else{
                  sentwords1[k] = words[i];
                  
        //System.out.println("sentwords1["+k+"]="+sentwords1[k]);
        /*if(TNP[crt].equals("null2")){
          TNP[crt] = sentwords1[k];
          crt++;
        }*/
                //  fout.write("sentwords1["+k+"]="+sentwords1[k]);
                  //fout.newLine();
                  k++;
            }
              }
              try{
              for(int j1 = 0 ; j1 < nowords/2 ; j1++){
                if(sentwords1[j1].equals("VF") && !sentwords1[j1-1].equals("NN") && !sentwords1[j1-1].equals("NNP")){
                  //if(ws.equals("VF") && ftg == 0){
                            //System.out.println("CHECK FOR PKO ");
                        //nowords--;
                        //ws = words[nowords - 3];
                         //ftg = 1;
                        //ws = words[nowords-2] ;
                        //System.out.println("sentwords1["+(j1)+"]="+sentwords1[j1]+"  flag2 ="+flag2+"   counter="+counter+"  dgindex="+dgindex);  
                    
                     //   if( flag2 == 2 && counter%2 == 0 ){
                                   ws = sentwords[j1] ;
                         //   //System.out.println("wd["+(nowords-3)+"]="+words[nowords-3]);
                              //  }
                               // if(dgindex != counter ){
                                //System.out.println("welcome to PKO again");
                                sws = RegionMatchesDemo("\u0915\u094B", ws);
                       //   }
                       //   else
               //             sws[0] = "ab";
                      //System.out.println("sws["+0+"]="+sws[0]+"  sws[1]="+sws[1]);
                        if(sws[0].equals("ab") )
                           flag2 = 0;
                  else
                            flag2 = 2;
                        
               
               
               
                      //}
                     // for(int i = 0 ; i < 1 && flag == 1 ; i++){
                      if( flag2 == 2 ){
                        //    words[nowords] = sws[0];
                          //System.out.println("beforenowods HI** ="+nowords);
                          sentwords1[j1] = "VMKO";
                      //  if(nowords%2 != 0)
                  //        words[nowords+1] = ws;
                        //  words[nowords+2] = sws[1];
                        //  words[nowords+3] = "PSPKO";
                          ////System.out.println("beforewords["+nowords+"]="+words[nowords+2]);
                        //  nowords = nowords + 4;
                      //    alaram = 1;
                       }
          
                    
                     //}
                }
              }
              }
              catch(NullPointerException nullPointer){
              
              }
          //System.out.println("line= "+line+"  noWords ="+nowords+"    noTags = "+noTags);
          int cnt = 0;
          int t=0;
          String temp1 = "hi";
          int flg = 0;
          try{
            while(cnt < (nowords/2-1)){
//            int cnt = 0;
        //System.out.println("sentwords["+cnt+"]="+sentwords[cnt]+"    sentwords1 tags["+cnt+"]="+sentwords1[cnt]);
//        if(sentwords1[cnt-1])
      /*  Actualvbt = getVbt(sentwords[cnt],sentwords1[cnt],cnt);
        //System.out.println("Actualvbt = "+Actualvbt+"   flg="+flg);
        if(cnt == 0){
          temp1 = Actualvbt;
        }
        else {
          if(temp1.equals(Actualvbt)){
          //  Actualvibhaktilist[t] = "null";
            flg = 1;
          }
          else if(! Actualvbt.equals("null1")){
        //    Actualvibhaktilist[t] = Actualvbt;
            flg = 1;
          }
          else{
            flg = 0;
          }
        }*/
            
              //System.out.println("Actualvbt="+Actualvbt+"   cnt="+cnt+"   t= "+t);
              //if(cnt%2 != 0){
//              Actualvibhaktilist[t] = Actualvbt;
        if(flg == 1)
                //System.out.println("**Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]);
             // t++;
            //  cnt++;
      //}
              fout.newLine();
              if(flg == 1){
                //fout.write("  Actualvbt ="+Actualvbt+"  Actualvbtlist["+t+"]="+Actualvibhaktilist[t]);
                t++;
              }
                 //   fout.newLine();
                    temp1 = Actualvbt;
                    cnt++;
                  //  t++;
      }
    }
    catch (NullPointerException nullPointer){}
    

          Words = words;
          noWords = nowords;
          noTags = nowords/2;
         // fout.write(gl);
          maxobswords = noTags;
//          Verbs = findVerbs(sentwords1, sentwords, verbs, xm);
          break;
           }
       } finally {
          // br.close();
         fout.close();
        }
    }
    
    
    public String getVbt(String v, String tagv, int cnt){
      String temp = "null1";
      int flag = 0;
  //String[] temp1 = new temp1[]
      String[] ptag = {"PSPLE","PSPLAI","PSPKO","PSPO"};
      //System.out.println("tagv.length="+tagv+"nowords="+noWords);
      //for(int i = cnt ; i < tagv.length/2 ; i++){
      //  //System.out.println("tagv["+i+"]="+tagv[i]);
        for(int j=0 ; j < 4; j++){
          if(tagv.equals(ptag[j]) && flag == 0){
            //System.out.println("tagv="+tagv+"  ptag["+j+"]="+ptag[j]+"   v="+v);
            temp = v;
            flag = 1;
            break;
          }
          else{
            temp = "null1";
          }
        }
        
        
      //}
      //System.out.println("VIBHAKTI="+temp);
      return temp;
    
    }
    public String[] getTags(String[] s){
         // for(int i = 0 ; i < noWords/2 ; i++)
            //System.out.println("Tags["+i+"]="+s[i]);
          return s;
    }
    public String[] getMorphs(String[] s1){
         // for(int i = 0 ; i < noWords/2 ; i++)
            //System.out.println("Morphs["+i+"]="+s1[i]);
            //displayString1(filename,s1,noWords/2);
          return s1;
    }
    public int getTAM(String vbr, ReadXMLFile f2 ){
      int ind;
      String  gm = "abc";
      f2.displayString(vbr);
//      int psnverb;
      String[] vb = new String[2];
  String[][]  prsce = new String[totalverbs][3];
  String[][] ktk = new String[totalverbs][3];
  String[][] vbkt = new String[totalverbs][3];
  vb[0] = vbr;
  //  ReadXMLFile xm = new ReadXMLFile();
      //System.out.println("getTAM");
      //System.out.println("verb="+vbr);
   //   f2.readFile1(wordnetfile);
        f2.main1(vb,sentwords,wordnetfile);
        /*for(int i = 0 ; i < vb.length ; i++){
          f2.displayString(vb[i]);
        }*/
        
        ind = f2.getFrameIndex();
        gm = f2.getGM();
        k2pindex = f2.getk2pindex();
        System.out.println("k2pindex="+k2pindex);
        k7tindex = f2.getk7tindex();
        if(gm.equals("TIME")){
          k7texist = true;
        }
        if(gm.equals("PLACE")){
          k2pexist = true;
        }
        if(ind == 5){
          directkrk = 7;
        }
        if(ind == 8){
          directkrk = 8;
        }
        frameInd = ind;
      //  //System.out.println("");
       // ind = 2;  // IN THE CASE OF FRAME DOES NOT EXIST
      //ind = f2.getRootverb(vbr);
      System.out.println("index = "+ind+"  direct krk"+directkrk+"   k7texist"+k7texist+"     k2pexist="+k2pexist);
      if(ind != -1){
        prsce = f2.getPresence(ind);
        ktk = f2.getKaraka(ind);
        vbkt = f2.getVbhkt(ind);
      /*  for(int i = 0 ;i < 8; i++){
          for(int j = 0 ; j < 3 ; j++){
            //System.out.println("i="+i);
            //System.out.println("index = "+ind+" Presence["+i+"]["+j+"] = "+prsce[i][j]+" karaka["+i+"]["+j+"] = "+ ktk[i][j] +" vbkt["+i+"]["+j+"] = "+vbkt[i][j]);
      }
      //System.out.println();
    }*/
    Presence = prsce;
    Karaka = ktk;
    vibh = vbkt;
    //Actualvibhakti = vibh;
  }
    return ind;
    }
    public boolean checkTAM(int[][][] tg, int tsg, int trows, int tcol, boolean ext) throws ArrayIndexOutOfBoundsException {
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
      String prsk2p;
      String krrk2p;
      String vbtk2p;
      String prsk7t;
      String krrk7t;
      int flagk1 = 1;
      int flagk2 = 1;     
        int flagk3 = 1;
        int flagk4 = 1;
        int flagk5 = 1;
        int flagk6 = 1;  
        int flagk7 = 1; 
        int flagk2p = 1;
        int flagk7t = 1;
        int ct1 = 0;
        int ct2 = 0; 
        int ct3 = 0;     
        int ct4 = 0;
        int ct5 = 0;
        int ct6 = 0;  
        int ct7 = 0;
        int ct8 = 0;
        int ct9 = 0;
        int ct10 = 0;
        int[] flgk = new int[9];      
      String prsk3;
      String krrk3;
      String vbtk3;
  String prsk7;
      String krrk7;
      String vbtk7;     
      AsciiString as = new AsciiString();
      String temp;
      int[][][] newfg = new int[1][trows][tcol];
      newfg = tg;
      int t = 0;
      
      String[] tvbt = new String[8]; // Maximum number of vibhakties associated with a verb is 5
      System.out.println("HI ITS CHECKTAM....");
      //prsk1 = Presence;
      for(int i=0;i<1;i++){
        for(int j=0;j<trows;j++){
          for(int k = 0 ; k < tcol ; k++){
            System.out.print("\t "+tg[i][j][k]);
          }
          System.out.println();
        }
      }
      int nxt = 0;
      // Check Karta (K1) Subjective or Nominative or Ergative Case
            prsk1 = Presence[0][0];
            //System.out.println("prsk1="+prsk1);
            if(prsk1.equals("Mendatory") ){
              //System.out.println("Mendatory");
              krrk1 = Karaka[0][1];
              //System.out.println("krrk1="+krrk1);
              if(krrk1.equals("karta(K1)")){
                //System.out.println("karta(k1)");
                vbtk1 = vibh[0][2];
                  tvbt = separateVbktTAM(vbtk1);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    t=0;
                    temp = as.decode(tvbt[i]);
                    //System.out.println("Actualvibhakti[0][0][1]="+Actualvibhaktilist[t]+"     tvbt["+i+"]="+tvbt[i]+"   temp="+temp+"  t="+t+"   trows="+trows);
                    for(int l = 0 ; l < trows ; l++){
                      System.out.println("tvbt["+i+"]="+tvbt[i]);
                      System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhaktilist[l] + "     tvbt["+i+"]="+tvbt[i]);
                      System.out.println("Actualvibhakti["+t+"]="+Actualvbt);
                    }
                            try{
                              while(t < trows && ct1 == 0){
                                //System.out.println("*Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"    tvbt["+i+"]="+tvbt[i]);
                          if(Actualvibhaktilist[t].equals(tvbt[i]) || Actualvibhaktilist[t].equals("\u092C\u093E\u091F")){
                            if(! Actualvibhaktilist[t].equals("null")){
                              ct1 = 1;
                              //System.out.println("hi vibhakti is not null");  
                            }
                            else{
                              ct1 = 0;
                              //System.out.println("hi its Karta with null vibhakti");  
                            }
                            System.out.println("**********karta(k1)");
                            for(int l = 0 ; l < trows ; l++){
                                    System.out.println("tg[0]["+t+"]["+0+"]="+tg[0][t][0]);
                            if(tg[0][l][0] == 1 ){    // 0th col for karta K1 of the final subgraph
                              if(Actualvibhaktilist[t].equals(tvbt[i]))
                                count++;
                                              
                            }
                            else{
                              count = count;
                            
                            }
                          }
                              System.out.println("karta count ="+ count);
                              if(count == 1){
                                       System.out.println("KARTA(K1) velidated...");
                                       flagk1 = 0;
                                       break;
                              }
                              else if (flagk1 != 0){
                                  //tg[0][0][0] = 0;
                            //System.out.println("KARTA(K1) is Not velidated...");
                            //System.out.println("karta count i="+ i);
                            flagk1 = 1;
                            break;
                              }
                        }
                        else{
                                  if(flagk1 == 0 && i == (tvbt.length-1))
                                  break;  
                              }
                              t++;
                      }
                          }
                          catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                         }   
                        
                        
                    //}
                    //}
//                    flagk1 = 
                    /*else{
                    //System.out.println("FRAME DOES NOT EXIST...");
                          System.exit(0);
                }*/
                }
                  
              }
            } 
          //}
        //}
      
      // Check Karma (K2) Accusative Case
            count = 0;
            t=0;
                prsk2 = Presence[1][0];
            System.out.println("prsk2="+prsk2 +"    no of ROWs="+trows +" vibhakti length = "+tvbt.length);
            if(prsk2.equals("Mendatory") || prsk2.equals("Optional")){
              //System.out.println("Mendatory");
              krrk2 = Karaka[1][1];
              //System.out.println("krrk2="+krrk2);
              if(krrk2.equals("karma(K2)")){
                //System.out.println("karma(k2)");
                vbtk2 = vibh[1][2];
                System.out.println("karma(k2) vbtk2="+vbtk2);
                  tvbt = separateVbktTAM(vbtk2);
             // try{
                  System.out.println("tvbtlength karma ="+tvbt.length+"   count="+count);
                  for(int i = 0 ; i < tvbt.length && count == 0 ; i++ ){
                    t=0;
                  //  try{
                    System.out.println("Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]);
                  System.out.println("   tvbt["+i+"]="+tvbt[i]+"  trows="+trows+"  ct1="+ct1);
                
                    try{
                              while(t < trows && ct2 == 0){
                                System.out.println("*Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"    tvbt["+i+"]="+tvbt[i]);
                          if(Actualvibhaktilist[t].equals(tvbt[i]) || Actualvibhaktilist[t].equals("\u092C\u093E\u091F")){
                            if(! Actualvibhaktilist[t].equals("null")){
                              ct2 = 1;
                              //System.out.println("hi vibhakti is not null");  
                            }
                            else{
                              ct2 = 0;
                              //System.out.println("hi its Karta with null vibhakti");  
                            }
                            System.out.println("**********karma(k2)");
                            for(int l = 0 ; l < trows ; l++){
                                    //System.out.println("tg[0]["+t+"]["+0+"]="+tg[0][t][0]);
                            if(tg[0][l][1] == 1 ){    // 0th col for karta K1 of the final subgraph
                              if(Actualvibhaktilist[t].equals(tvbt[i]))
                                count++;
                                              
                            }
                            else{
                              count = count;
                            
                            }
                          }
                              //System.out.println("karta count ="+ count);
                              if(count == 1){
                                       System.out.println("KARMA(K2) velidated...");
                                       flagk2 = 0;
                                       break;
                              }
                              else if (flagk2 != 0){
                                  //tg[0][0][0] = 0;
                            //System.out.println("KARMA(K2) is Not velidated...");
                            //System.out.println("karma count i="+ i);
                            flagk2 = 1;
                            break;
                              }
                        }
                        else{
                                  if(flagk2 == 0 && i == (tvbt.length-1))
                                  break;  
                              }
                              t++;
                      }
                          }
                          catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                         }  
              //  }

                  /*  for(int l = 0 ; l < trows ; l++){
                        //System.out.println("tvbt["+i+"]="+tvbt[i]);
                        //System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhakti[l][2*l][1] + "   ***tvbt["+i+"]="+tvbt[i]);
                        try{
                          //System.out.println("**********Akarma(k2)");
                          ////System.out.println("Actualvibhakti["+t+"]="+Actualvibhaktilist[t] + "  ***tvbt["+i+"]="+tvbt[i]);
//                          continue;
                //System.out.println("flagk2="+flagk2+"    count="+count +"  Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]);
                      if( count == 1 || Actualvibhakti[l][2*l][1].equals(tvbt[i]) || Actualvibhaktilist[i].equals(tvbt[i])){
                        //System.out.println("**********karma(k2)");
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                    //    for(int r = 0 ; r < trows ; r++){
                      //System.out.println("tg[0]["+l+"]["+1+"]="+tg[0][l][1]);
                      if(tg[0][l][1] == 1){     // 1st row of the final subgraph
                        count++;
                        
                      }
                      else{
                        count = count;
                        
                      }
                     //     }
                          //System.out.println("karma count ="+ count);
                          if(count == 1){
                                   //System.out.println("KARMA(K2) velidated...");
                                   flagk2 = 0;
                                   break;
                          }
                          else if (flagk2 != 0){
                              //tg[0][1][1] = 0;
                        //System.out.println("KARMA(K2) is Not velidated...");
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
                        //System.out.println("HI  and i = "+i+"     flagk2="+flagk2);
                        
                              if(flagk2 == 0 && i == (tvbt.length-1)){
                          //System.out.println("hi");
                                break;  
                              }
                    }
                       }
                       catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                   }  
            //             t++;  
                  }
                  //t++;
                   
                }*/
              }
               // t++;  
              }
            } 

      // Check Karana (K3) Instrumental Case
      
                              /*count = 0;
            t=0;
                prsk3 = Presence[2][0];
            //System.out.println("prsk3="+prsk3 +"    no of ROWs="+trows +" vibhakti length = "+tvbt.length);
            if(prsk3.equals("Mendatory") || prsk3.equals("Optional")){
              //System.out.println("Mendatory");
              krrk3 = Karaka[2][1];
              //System.out.println("krrk3="+krrk3);
              if(krrk3.equals("karana(K3)")){
                //System.out.println("karana(K3)");
                vbtk3 = vibh[2][2];
                  tvbt = separateVbktTAM(vbtk3);
              try{
                  for(int i = 0 ; i < tvbt.length ; i++ ){
  //                  t=0;
                    //System.out.println("Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]+"   tvbt["+i+"]="+tvbt[i]);
                  
                
                    //for(int l = 0 ; l < tvbt.length ; l++){
                      if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct3 == 0){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct3 = 1;
                              //System.out.println("hi karana vibhakti is not null"); 
                            }
                            else{
                              ct3 = 0;
                        }
                  //System.out.println("hi karana t="+t);
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+1+"]="+tg[0][k][1] +"   count="+count+"  i="+i);                         
                          if((tg[0][k][1]) == 1){
                            //System.out.println("*Actualvibhaktilist["+k+"]="+Actualvibhaktilist[k]+"   tvbt["+i+"]="+tvbt[i]);
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count = 1;
                          }
                        }
                      }
                      //System.out.println();
                    //}
                    
                    t++;
                    }
                  }
                  //}
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("karana count ="+ count);
                if(count == 1){
                        //System.out.println("KARANA(K3) velidated...");
                        flagk3 = 0;
              //          break;
                }else{
                  //System.out.println("KARANA(K3) not velidated...");
                        flagk3 = 1;
                }
              
                t++;  
              }
            } */
      
      
      
      
      
      
      
      
                count = 0;
                t=0;
                    prsk3 = Presence[2][0];
            //System.out.println("prsk3="+prsk3);
            
            if(prsk3.equals("Optional") ){
              //System.out.println("Optional");
              krrk3 = Karaka[2][1];
              //System.out.println("krrk3="+krrk3);
              if(krrk3.equals("karana(K3)")){
                System.out.println("karana(K3)");
                vbtk3 = vibh[2][2];
                  tvbt = separateVbktTAM(vbtk3);
                  for(int i = 0 ; i < tvbt.length && count == 0 ; i++ ){
                    System.out.println("Actualvibhakti[2][4][1]="+Actualvbt+"  tvbt["+i+"]="+tvbt[i]);
                   // for(int l = 0 ; l < trows ; l++){
                      t = 0;
                     // System.out.println("Actualvibhakti("+l+")="+Actualvibhaktilist[l]);
                       try{
                              while(t < trows && ct3 == 0){
                                System.out.println("*Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"    tvbt["+i+"]="+tvbt[i]);
                          if(Actualvibhaktilist[t].equals(tvbt[i]) ){
                            if(! Actualvibhaktilist[t].equals("null")){
                              ct3 = 1;
                              //System.out.println("hi vibhakti is not null");  
                            }
                            else{
                              ct3 = 0;
                              //System.out.println("hi its Karta with null vibhakti");  
                            }
                            System.out.println("**********karana(k3)");
                            for(int l = 0 ; l < trows ; l++){
                                    //System.out.println("tg[0]["+t+"]["+0+"]="+tg[0][t][0]);
                            if(tg[0][l][2] == 1 ){    // 0th col for karta K1 of the final subgraph
                              if(Actualvibhaktilist[t].equals(tvbt[i]))
                                count++;
                                              
                            }
                            else{
                              count = count;
                            
                            }
                          }
                              //System.out.println("karta count ="+ count);
                              if(count == 1){
                                       System.out.println("KARANA(K3) velidated...");
                                       flagk3 = 0;
                                       break;
                              }
                              else if (flagk3 != 0){
                                  //tg[0][0][0] = 0;
                            //System.out.println("KARMA(K2) is Not velidated...");
                            //System.out.println("karma count i="+ i);
                            flagk3 = 1;
                            break;
                              }
                        }
                        else{
                                  if(flagk3 == 0 && i == (tvbt.length-1))
                                  break;  
                              }
                              t++;
                      }
                          }
                          catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                         }  
                         //t++;  
                         // }
                      //    t++;
                  }
                 // t++;
                }
            } 

      
      // Check Sampradan (K4) Dative Case
            count = 0;
            t = 0;
            
                    prsk4 = Presence[3][0];
            //System.out.println("prsk4="+prsk4);
            if( prsk2.equals("Optional") || t == 0){
              System.out.println("K4 Optional");
              krrk4 = Karaka[3][1];
              //System.out.println("krrk4="+krrk4);
              if(krrk4.equals("Sampradan(K4)")){
                //System.out.println("Sampradan(K4)");
                vbtk4 = vibh[3][2];
                  tvbt = separateVbktTAM(vbtk4);
                  
                  for(int i = 0 ; i < tvbt.length && t < tvbt.length; i++ ){
                    System.out.println("Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length  ; l++){
                      //t = 0;
                      //System.out.println("hi it is sampradan");
                      //System.out.println("**Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct4 == 0){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct4 = 1;
                              //System.out.println("hi sampradan vibhakti is not null");  
                            }
                            else{
                              ct4 = 0;
                        }
                    //System.out.println("hi it is sampradan ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+3+"]="+tg[0][k][3]);                         
                          if((tg[0][k][3]) == 1){
                            System.out.println("k="+k+"    i="+i);
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            
                          }
                        }
                      }
                      //System.out.println();
                      t++;
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("sampradan count ="+ count);
                if(count == 1){
                        System.out.println("SAMPRADAN(K4) velidated...");
                        flagk4 = 0;
                        break;
                }else{
                  System.out.println("SAMPRADAN(K4) not velidated...");
                        flagk4 = 1;
                }
                }
                  
                  
                  
                /*  for(int i = 0 ; i < tvbt.length ; i++ ){
                    ////System.out.println("Actualvibhakti[3][6][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[i]+" tvbt["+l+"]="+tvbt[i]);
                      try{
                        if(Actualvibhaktilist[i].equals(tvbt[i])){
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                  
                      //  for(int r = 0 ; r < trows ; r++){
                      //System.out.println("tg["+0+"]["+l+"]["+3+"]="+tg[0][l][3]); 
                          if(tg[0][l][3] == 1){    // 3nd row of the final subgraph
                            count++;
                            
                          }
                          else{
                            count = count;
                            
                          }
                       //     }
                            //System.out.println("Sampradan count ="+ count);
                            if(count == 1){
                                     //System.out.println("Sampradan(K4) velidated...");
                                     flagk4 = 0;
                                     break;
                            }
                            else{
                                //tg[0][2][3] = 0;
                          //System.out.println("Sampradan(K4) is Not velidated...");
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
                  }*/
                }
                
            } 
            
      // Check Apadan(K5) Ablative Case
            count = 0;
            t = 0;
                    prsk5 = Presence[4][0];
            //System.out.println("prsk5="+prsk5);
            if(prsk5 != null){
            if(prsk5.equals("Optional") && t < 4 ) {
              //System.out.println("Optional");
              krrk5 = Karaka[4][1];
              //System.out.println("krrk5="+krrk5);
              if(krrk5.equals("Apadan(K5)")){
                //System.out.println("Apadan(K5)");
                vbtk5 = vibh[4][2];
                  tvbt = separateVbktTAM(vbtk5);
                  for(int i = 0 ; i < tvbt.length && t < trows; i++ ){
                    //System.out.println("Actualvibhakti[4][8][1]="+Actualvbt+"tvbt["+i+"]="+tvbt[i]);
                    try{
                    for(int l = 0 ; l < Actualvibhaktilist.length  ; l++){
                      //System.out.println("**Actualvibhakti["+l+"]["+(2*l)+"][l]="+Actualvibhaktilist[t] +"   t="+t);
                    //  try{
                        if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct5 == 0){
                          if(! Actualvibhaktilist[t].equals("null")){
                              ct5 = 1;
                              //System.out.println("hi Apadan vibhakti is not null"); 
                            }
                            else{
                              ct5 = 0;
                          }
                          //System.out.println("Apadan count ="+ count);
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                  for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+4+"]="+tg[0][k][4]);                         
                          if((tg[0][k][4]) == 1){
                            //System.out.println("k="+k+"    i="+i);
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            
                          }
                        }
                      }
                      t++;
                    }
                  }
                      catch (NullPointerException nullPointer){}
                      if(count == 1){
                        //System.out.println("Apadan(K5) velidated...");
                        flagk5 = 0;
                        break;
                }else{
                  //System.out.println("Apadan(K5) not velidated...");
                        flagk5 = 1;
                }
              }
                      //  for(int r = 0 ; r < trows ; r++){
                          //if(tg[0][l][4] == 1){    // 3nd row of the final subgraph
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                          //    count++;
                          //  //System.out.println("hi");
                            
                        //  }
                          //else{
                            //count = count;
                            
                          //}
                       //     }
                            //System.out.println("Apadan count ="+ count);
                      //      if(count == 1){
                        //             //System.out.println("Apadan(K5) velidated...");
                          //           flagk5 = 0;
                            //         break;
                            //}
                            //else{
//                                tg[0][2][3] = 0;
                          ////System.out.println("Apadan(K5) is Not velidated...");
                          //flagk5 = 1;
                            //}
                      //}
                        //    else{
                        //if(flagk5 == 0 && i == (tvbt.length-1))
                          //      break;  
                              
                      //}
                        //}
                          //    catch (NullPointerException nullPointer)
                   //{
                    //log(causeStr, nullPointer, System.out);
                     //    }   
                       //  t++;   
                       
            }
            }
            } 
            ////System.out.println("flagk1= "+flagk1);


      // Check Sambandh(K6)
            count = 0;
            t = 0 ;
                    prsk6 = Presence[5][0];
            //System.out.println("prsk6="+prsk6);
            if(prsk6 != null){
            if(prsk6.equals("Optional")  ){
              //System.out.println("Optional");
              krrk6 = Karaka[5][1];
              //System.out.println("krrk6="+krrk6);
              if(krrk6.equals("Sambandh(K6)")){
                //System.out.println("Sambandh(K6)");
                vbtk6 = vibh[5][2];
                  tvbt = separateVbktTAM(vbtk6);
                  for(int i = 0 ; i < tvbt.length  &&  t < trows; i++ ){
                    ////System.out.println("Actualvibhakti[5][10][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
                      try{
                        if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct6 == 0){
                          if(! Actualvibhaktilist[t].equals("null")){
                              ct6 = 1;
                              //System.out.println("hi Sambhandh vibhakti is not null");  
                            }
                            else{
                              ct6 = 0;
                          }
                          //System.out.println("hi Sambhandh : ");
                          for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+5+"]="+tg[0][k][5]);                         
                          if((tg[0][k][5]) == 1){
                            //System.out.println("k="+k+"    i="+i);
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            
                          }
                        }
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                  
                      //  for(int r = 0 ; r < trows ; r++){
                      //    if(tg[0][l][5] == 1){    // 4nd row of the final subgraph
                        //    count++;
                            
                          //}
                          //else{
                            //count = count;
                            
                          //}
                       //     }
                            //System.out.println("Sambandh count ="+ count);
                            if(count == 1){
                                     //System.out.println("Sambandh(K6) velidated...");
                                     flagk6 = 0;
                                     break;
                            }
                            else{
//                                tg[0][2][3] = 0;
                          //System.out.println("Sambandh(K6) is Not velidated...");
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
      }
      
  // Check Adhikaran (K7) 
      
      
                count = 0;
                t=0;
                    prsk7 = Presence[6][0];
            //System.out.println(" 1234 prsk7="+prsk7+"    tvbt.len="+tvbt.length);
            
            if(prsk7.equals("Optional") ){
              //System.out.println("Optional");
              krrk7 = Karaka[6][1];
              //System.out.println("krrk7="+krrk7);
              if(krrk7.equals("Adhikaran(K7)")){
                //System.out.println("Adhikaran(K7)");
                vbtk7 = vibh[6][2];
                  tvbt = separateVbktTAM(vbtk7);
                  for(int i = 0 ; i < tvbt.length && t < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"  tvbt["+i+"]="+tvbt[i]+"  trows="+trows+"  t="+t);
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
                 //     try{
                  /*    try{
                      if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct7 == 0) {
                          if(! Actualvibhaktilist[t].equals("null")){
                              ct7 = 1;
                              //System.out.println("hi Adhikaran vibhakti is not null");  
                            }
                            else{
                              ct7 = 0;
                          }

                          //System.out.println("hi Adhikaran vibhakti");

                          for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+6+"]="+tg[0][k][6]);                         
                          if((tg[0][k][6]) == 1){
                            //System.out.println("k="+k+"    i="+i);
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            
                          }
                        }
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                       // int karn ;              // Karana does not exist
//                       r = l;
                       // for(int r = 0 ; r < trows ; r++){
                      //    //System.out.println("l="+l);
                        //  if(tg[0][l][6] == 1 ){//&& karn != 0){     // 2nd row of the final subgraph
                          //  //System.out.println("**l="+l);
                            //count++;
                            
                          //}
                          //else{
                            //count = count;
                            
                        //  }
//                            }
                            //System.out.println("Adhikaran count ="+ count);
                            if(count == 1){
                                     //System.out.println("Adhikaran(K7) velidated...");
                                     flagk7 = 0;
                                     break;
                            }
                            else{
                                //tg[0][2][3] = 0;
                          //System.out.println("Adhikaran(K7) is Not velidated...");
                          flagk7 = 1;
                            }
                      }
                            else{
                        if(flagk7 == 0 && i == (tvbt.length-1))
                                break;  
                      }
                     }
                              catch ( ArrayIndexOutOfBoundsException ae)
                   {
                      //System.out.println("123456");
                    //log(causeStr, nullPointer, System.out);
                         }  */
                         t++;  
                          }

                  }
                }
            }       
            
            
      // Check Location (K2P) Optional
            count = 0;
            t = 0;
            
                    prsk2p = Presence[7][0];
            //System.out.println("prsk2p="+prsk2p);
            if(prsk2p != null){
            if( prsk2p.equals("Optional")){
              //System.out.println("K2p Optional");
              krrk2p = Karaka[7][1];
              //System.out.println("krrk2p= "+krrk2p);
              if(krrk2p.equals("Locative(K2p)")){
                //System.out.println("Locative(K2p)");
                vbtk2p = vibh[7][2];
                  tvbt = separateVbktTAM(vbtk2p);
                  
                  for(int i = 0 ; i < tvbt.length && t < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is Locative K2p");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[l].equals(tvbt[i])){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct8 = 1;
                              //System.out.println("hi Location k7p optional vibhakti is not null");  
                            }
                            else{
                              ct8 = 0;
                          }
                      
                    //System.out.println("hi it is Location (K2p) ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+7+"]="+tg[0][k][7]);                         
                          if((tg[0][k][7]) == 1){
                            //System.out.println("k="+k+"    i="+i);
                            if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            //count =1;
                            else
                              count = 0;
                            
                          }
                        }
                      }
                      else count = 0;
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("Location K2p count ="+ count);
                if(count == 1){
                        //System.out.println("LOCATION (K2P) velidated...");
                        flagk2p = 0;
                        flagk2 = 1;
                        break;
                }else{
                  //System.out.println("LOCATION (K2P) not velidated...");
                        flagk2p = 1;
                }
              }
            }
            }   
            } 
                    
            
            
            
       // Check Location (K2P) Mendatory
            count = 0;
            t = 0;
            
                    prsk2p = Presence[7][0];
            //System.out.println("prsk2p="+prsk2p);
            if(prsk2p != null){
            if( prsk2p.equals("Mendatory")){
              //System.out.println("K2p Mendatory");
              krrk2p = Karaka[7][1];
              //System.out.println("krrk2p= "+krrk2p);
              if(krrk2p.equals("Locative(K2p)")){
                //System.out.println("Locative(K2p)");
                vbtk2p = vibh[7][2];
                  tvbt = separateVbktTAM(vbtk2p);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is Locative K2p");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[l].equals(tvbt[i])){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct9 = 1;
                              //System.out.println("hi Location k2p Mendatory vibhakti is not null"); 
                            }
                            else{
                              ct9 = 0;
                          }
                    //System.out.println("hi it is Location (K2p) ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+7+"]="+tg[0][k][7]);                         
                          if((tg[0][k][7]) == 1){
                            if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            //count =1;
                            
                          }
                        }
                      }
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("Location K2p count ="+ count);
                if(count == 1){
                        //System.out.println("LOCATION (K2P) velidated...");
                        flagk2p = 0;
                        flagk4 = 1;
                        break;
                }else{
                  //System.out.println("LOCATION (K2P) not velidated...");
                        flagk2p = 1;
                }
              }
            }
            }   
            }         
            
            
      // Check Location (K7t) Optional
          if(ext){
            count = 0;
            t = 0;
            
                    prsk7t = Presence[8][0];
            //System.out.println("prsk7t="+prsk7t);
            if(prsk7t != null){
            if( prsk2p.equals("Optional")){
              //System.out.println("K7t Optional");
              krrk7t = Karaka[8][1];
              //System.out.println("krrk7t= "+krrk7t);
              if(krrk7t.equals("Locative(K7t)")){
                //System.out.println("Locative in time (K2p)");
                vbtk2p = vibh[8][2];
                  tvbt = separateVbktTAM(vbtk2p);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is Locative K7t");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[l].equals(tvbt[i])){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct10 = 1;
                              //System.out.println("hi Location k7t Mendatory vibhakti is not null"); 
                            }
                            else{
                              ct10 = 0;
                          }
                    //System.out.println("hi it is Location (K7t) ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+8+"]="+tg[0][k][8]);                         
                          if((tg[0][k][8]) == 1){
                            if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            //count =1;
                            
                          }
                        }
                      }
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("Location K7t count ="+ count);
                if(count == 1){
                        //System.out.println("LOCATION (K7t) velidated...");
                        flagk7t = 0;
                        flagk4 = 1;
                        break;
                }else{
                  //System.out.println("LOCATION in TIME (K7t) not velidated...");
                        flagk7t = 1;
                }
              }
            }
            }   
            } 
          }
            
            //System.out.println("flagk1= "+flagk1+"   flagk2="+flagk2+"    flagk3= "+flagk3+"  flagk4= "+flagk4 +"      flagk5=  "+flagk5+  "     flagk6="+flagk6+  "      flagk7="+flagk7+  "     flagk2p="+flagk2p+   "  flagk7t="+flagk7t+    "    trows="+trows);
            flgk[0] = flagk1;
            flgk[1] = flagk2;
            flgk[2] = flagk3;
            flgk[3] = flagk4;
            flgk[4] = flagk5;
            flgk[5] = flagk6;
            flgk[6] = flagk7;
            flgk[7] = flagk2p;
            flgk[8] = flagk7t;
            if(trows < 3){
              flagk3 = 0;
              flagk4 = 0;
            }
            
            
            /*int checkmorekarkas(tg, tsg, trows, tcol, ext){
              if(vibhakti.equals("ma"))
                if(tg[][][])
              return flagk;
            
            }*/
            
            
            
            
            /*if(flagk1 == 0 && flagk2 == 0 && flagk3 == 0 || flagk4 == 0 || flagk5 == 0 || flagk6 == 0 ){
              flg1 = true;
            }
            else{
              flg1 = false;
            }*/
            int ct=0;
            int semicount1 = 0;
            int karkas = 8;
            //int iteration = 0;
            int[][][] semifinal;
            int semicountnull = ct1 + ct2 + ct3 + ct4 + ct5 + ct6 + ct7 + ct8 ;
            boolean semiflg1 = false ;
            if(ext){
              karkas = 9;
            }
            else{
              karkas = 8;
            }
            for(int m = 0 ; m < trows ; m++){
              for(int i=0;i<karkas;i++){
              
                if(tg[0][m][i] == 1){
                    //System.out.println(" ct0="+ct+"   trows="+trows+" semicountnull= "+semicountnull);                
                //while(ct < trows){
                  if(flgk[i] == 0 ){
                    ct++;
                    flg1 = true;
                    //System.out.println(" ct="+ct+"   trows="+trows);
                    if(ct == trows)
                      break;
                    else{
                      semicount1++;
                      flg1 = false;
                      if(ct == (trows - 1))
                        semiflg1 = true;
                    }
                  //break;
                  }
                  else{
                    flg1 = false;
                  } 
                }
                else 
                  flg1 = false;
        //      }
              }
            }
            
            //System.out.println(" ct="+ct+"   trows="+trows+" semicount1="+semicount1);
            if(semicount1 > semicount)
              semicount = semicount1;
            //System.out.println("semicount* = "+semicount+"  semiflg1="+semiflg1);
            semifinal = tg ;
            if(semiflg1){
              //System.out.println("semicount** = "+semicount+"  iteration="+iteration);
              //semifinalmatching = semifinal;
              for(int j = 0 ; j < trows ; j++){
                  for(int k = 0 ; k < nKarkas ; k++){
                    ////System.out.print("   "+ntsubgraphs[i][j][k]);
                    try{
                      //System.out.print("   "+semifinal[iteration][j][k] );
                      semifinalmatching[iteration][j][k] = tg[0][j][k];
                    }
                    catch(ArrayIndexOutOfBoundsException ae){
                    
                    }
                  }
                  //System.out.println();
                }
                iteration++;
        }
        //semifinalmatching = semifinal;
            //  break;
            
            //}
            //while(ct <= trows);
//            break;
            
  /*      newfg = NewSubgraph(tg,trows,tcol,flagk4);
            //System.out.println("\t  KARTA(k1)\tKARMA(k2)\tKARANA(k3)\tGOL(K2P)");
            for(int l = 0 ; l < trows ; l++){
              //System.out.println();
            for(int m = 0 ; m < tcol ; m++){
                //System.out.print("  newfg["+0+"]["+l+"]["+m+"]="+newfg[0][l][m]);
              }
              //System.out.println();
            }*/
      
      return flg1;
    
    }


  // Returns Mendatory or optional
    public String checkTAM1(int[][][] tg, int tsg, int trows, int tcol) throws ArrayIndexOutOfBoundsException {
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
      String prsk2p;
      String krrk2p;
      String vbtk2p;
      int flagk1 = 1;
      int flagk2 = 1;     
        int flagk3 = 1;
        int flagk4 = 1;
        int flagk5 = 1;
        int flagk6 = 1;  
        int flagk7 = 1; 
        int flagk2p = 1;
        int ct1 = 0;
        int ct2 = 0; 
        int ct3 = 0;     
        int ct4 = 0;
        int ct5 = 0;
        int ct6 = 0;  
        int[] flgk = new int[8];      
      String prsk3;
      String krrk3;
      String vbtk3;
  String prsk7;
      String krrk7;
      String vbtk7;     
      AsciiString as = new AsciiString();
      String temp;
      int[][][] newfg = new int[1][trows][tcol];
      newfg = tg;
      int t = 0;
      
      String[] tvbt = new String[4]; // Maximum number of vibhakties associated with a verb is 4
      //System.out.println("HI ITS CHECKTAM....");
      //prsk1 = Presence;
      for(int i=0;i<1;i++){
        for(int j=0;j<trows;j++){
          for(int k = 0 ; k < tcol ; k++){
            //System.out.print("\t "+tg[i][j][k]);
          }
          //System.out.println();
        }
      }
      int nxt = 0;
      // Check Karta (K1) Subjective or Nominative or Ergative Case
            prsk1 = Presence[0][0];
            //System.out.println("prsk1="+prsk1);
            if(prsk1.equals("Mendatory") ){
              //System.out.println("Mendatory");
              krrk1 = Karaka[0][1];
              //System.out.println("krrk1="+krrk1);
              if(krrk1.equals("karta(K1)")){
                //System.out.println("karta(k1)");
                vbtk1 = vibh[0][2];
                  tvbt = separateVbktTAM(vbtk1);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    t=0;
                    temp = as.decode(tvbt[i]);
                    //System.out.println("Actualvibhakti[0][0][1]="+Actualvibhaktilist[t]+"tvbt["+i+"]="+tvbt[i]+"   temp="+temp+"  t="+t);
                    for(int l = 0 ; l < trows ; l++){
                      //System.out.println("tvbt["+i+"]="+tvbt[i]);
                      //System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhaktilist[t] + "tvbt["+i+"]="+tvbt[i]);
                      //System.out.println("Actualvibhakti["+t+"]="+Actualvbt);
                    }
                            try{
                              while(t < trows && ct1 == 0){
                                //System.out.println("*Actualvibhakti["+t+"]="+Actualvibhaktilist[t]+"    tvbt["+i+"]="+tvbt[i]);
                          if(Actualvibhaktilist[t].equals(tvbt[i]) || Actualvbt.equals("\u092C\u093E\u091F")){
                            if(! Actualvibhaktilist[t].equals("null")){
                              ct1 = 1;
                              //System.out.println("hi vibhakti is not null");  
                            }
                            else{
                              ct1 = 0;
                            }
                            //System.out.println("**********karta(k1)");
                            //for(int l = 0 ; l < trows ; l++){
                                    //System.out.println("tg[0]["+t+"]["+0+"]="+tg[0][t][0]);
                            if(tg[0][t][0] == 1 ){    // 0th col for karta K1 of the final subgraph
                              if(Actualvibhaktilist[t].equals(tvbt[i]))
                                count++;
                                              
                            }
                            else{
                              count = count;
                            
                            }
                          //}
                              //System.out.println("karta count ="+ count);
                              if(count == 1){
                                       //System.out.println("KARTA(K1) velidated...");
                                       flagk1 = 0;
                                       break;
                              }
                              else if (flagk1 != 0){
                                  //tg[0][0][0] = 0;
                            //System.out.println("KARTA(K1) is Not velidated...");
                            //System.out.println("karta count i="+ i);
                            flagk1 = 1;
                            break;
                              }
                        }
                        else{
                                  if(flagk1 == 0 && i == (tvbt.length-1))
                                  break;  
                              }
                              t++;
                      }
                          }
                          catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                         }   
                        
                        
                    //}
                    //}
//                    flagk1 = 
                    /*else{
                    //System.out.println("FRAME DOES NOT EXIST...");
                          System.exit(0);
                }*/
                }
                  
              }
            } 
          //}
        //}
      
      // Check Karma (K2) Accusative Case
            count = 0;
            t=0;
                prsk2 = Presence[1][0];
            //System.out.println("prsk2="+prsk2 +"    no of ROWs="+trows +" vibhakti length = "+tvbt.length);
            if(prsk2.equals("Mendatory") || prsk2.equals("Optional")){
              //System.out.println("Mendatory");
              krrk2 = Karaka[1][1];
              //System.out.println("krrk2="+krrk2);
              if(krrk2.equals("karma(K2)")){
                //System.out.println("karma(k2)");
                vbtk2 = vibh[1][2];
                  tvbt = separateVbktTAM(vbtk2);
              try{
                  for(int i = 0 ; i < tvbt.length ; i++ ){
  //                  t=0;
                    //System.out.println("Actualvibhaktilist["+t+"]="+Actualvibhaktilist[t]+"   tvbt["+i+"]="+tvbt[i]);
                  
                
                    //for(int l = 0 ; l < tvbt.length ; l++){
                      if(count == 0 || Actualvibhaktilist[t].equals(tvbt[i]) && ct2 == 0){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct2 = 1;
                              //System.out.println("hi karma vibhakti is not null");  
                            }
                            else{
                              ct2 = 0;
                        }
                  //System.out.println("hi karma t="+t);
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+1+"]="+tg[0][k][1] +"   count="+count+"  i="+i);                         
                          if((tg[0][k][1]) == 1){
                            //System.out.println("*Actualvibhaktilist["+k+"]="+Actualvibhaktilist[k]+"   tvbt["+i+"]="+tvbt[i]);
                            if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count = 1;
                          }
                        }
                      }
                      //System.out.println();
                    //}
                    
                    t++;
                    }
                  }
                  //}
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("karma count ="+ count);
                if(count == 1){
                        //System.out.println("KARMA(K2) velidated...");
                        flagk2 = 0;
              //          break;
                }else{
                  //System.out.println("KARMA(K2) not velidated...");
                        flagk2 = 1;
                }
              //  }

                  /*  for(int l = 0 ; l < trows ; l++){
                        //System.out.println("tvbt["+i+"]="+tvbt[i]);
                        //System.out.println("Actualvibhakti["+l+"][2*"+l+"][1]="+Actualvibhakti[l][2*l][1] + "   ***tvbt["+i+"]="+tvbt[i]);
                        try{
                          //System.out.println("**********Akarma(k2)");
                          ////System.out.println("Actualvibhakti["+t+"]="+Actualvibhaktilist[t] + "  ***tvbt["+i+"]="+tvbt[i]);
//                          continue;
                //System.out.println("flagk2="+flagk2+"    count="+count +"  Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]);
                      if( count == 1 || Actualvibhakti[l][2*l][1].equals(tvbt[i]) || Actualvibhaktilist[i].equals(tvbt[i])){
                        //System.out.println("**********karma(k2)");
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                    //    for(int r = 0 ; r < trows ; r++){
                      //System.out.println("tg[0]["+l+"]["+1+"]="+tg[0][l][1]);
                      if(tg[0][l][1] == 1){     // 1st row of the final subgraph
                        count++;
                        
                      }
                      else{
                        count = count;
                        
                      }
                     //     }
                          //System.out.println("karma count ="+ count);
                          if(count == 1){
                                   //System.out.println("KARMA(K2) velidated...");
                                   flagk2 = 0;
                                   break;
                          }
                          else if (flagk2 != 0){
                              //tg[0][1][1] = 0;
                        //System.out.println("KARMA(K2) is Not velidated...");
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
                        //System.out.println("HI  and i = "+i+"     flagk2="+flagk2);
                        
                              if(flagk2 == 0 && i == (tvbt.length-1)){
                          //System.out.println("hi");
                                break;  
                              }
                    }
                       }
                       catch (NullPointerException nullPointer)
                   {
                    //log(causeStr, nullPointer, System.out);
                   }  
            //             t++;  
                  }
                  //t++;
                   
                }*/
                t++;  
              }
            } 

      // Check Karana (K3) Instrumental Case
      
      
                count = 0;
                t=0;
                    prsk3 = Presence[2][0];
            //System.out.println("prsk3="+prsk3);
            
            if(prsk3.equals("Optional") ){
              //System.out.println("Optional");
              krrk3 = Karaka[2][1];
              //System.out.println("krrk3="+krrk3);
              if(krrk3.equals("karana(K3)")){
                //System.out.println("karana(K3)");
                vbtk3 = vibh[2][2];
                  tvbt = separateVbktTAM(vbtk3);
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhakti[2][4][1]="+Actualvbt+"  tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
                      try{
                      if(Actualvibhaktilist[t].equals(tvbt[i]) && ct3 == 0) { 
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct3 = 1;
                              //System.out.println("hi karana vibhakti is not null"); 
                            }
                            else{
                              ct3 = 0;
                        }
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                       // int karn ;              // Karana does not exist
//                       r = l;
                       // for(int r = 0 ; r < trows ; r++){
                          //System.out.println("l="+l);
                          if(tg[0][l][2] == 1 ){//&& karn != 0){     // 2nd row of the final subgraph
                            //System.out.println("**l="+l);
                            if(Actualvibhaktilist[l].equals(tvbt[i]))
                              count++;
                            
                          }
                          else{
                            count = count;
                            
                          }
//                            }
                            //System.out.println("Karana count ="+ count);
                            if(count == 1){
                                     //System.out.println("Karana(K3) velidated...");
                                     flagk3 = 0;
                                     break;
                            }
                            else{
                                //tg[0][2][3] = 0;
                          //System.out.println("Karana(K3) is Not velidated...");
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

      
      // Check Sampradan (K4) Dative Case
            count = 0;
            t = 0;
            
                    prsk4 = Presence[3][0];
            //System.out.println("prsk4="+prsk4);
            if( prsk2.equals("Optional") || t == 0){
              //System.out.println("K4 Optional");
              krrk4 = Karaka[3][1];
              //System.out.println("krrk4="+krrk4);
              if(krrk4.equals("Sampradan(K4)")){
                //System.out.println("Sampradan(K4)");
                vbtk4 = vibh[3][2];
                  tvbt = separateVbktTAM(vbtk4);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                    count = 0;
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is sampradan");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if( count == 0 ||Actualvibhaktilist[l].equals(tvbt[i]) && ct4 == 0){
                        if(! Actualvibhaktilist[t].equals("null")){
                              ct4 = 1;
                              //System.out.println("hi sampradan vibhakti is not null");  
                        }
                        else{
                          //System.out.println("hi it is ct4 ***"+ct4);
                              ct4 = 0;
                        }
                    //System.out.println("hi it is sampradan count ***"+count);
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+3+"]="+tg[0][k][3]);                         
                          if((tg[0][k][3]) == 1){
                            if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count =1;
                            else
                              count = 0;
                            
                          }
                        }
                      }
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("sampradan count ="+ count);
                if(count == 1){
                        //System.out.println("SAMPRADAN(K4) velidated...");
                        flagk4 = 0;
                        break;
                }else{
                  //System.out.println("SAMPRADAN(K4) not velidated...");
                        flagk4 = 1;
                }
                }
                  
                  
                  
                /*  for(int i = 0 ; i < tvbt.length ; i++ ){
                    ////System.out.println("Actualvibhakti[3][6][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[i]+" tvbt["+l+"]="+tvbt[i]);
                      try{
                        if(Actualvibhaktilist[i].equals(tvbt[i])){
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                  
                      //  for(int r = 0 ; r < trows ; r++){
                      //System.out.println("tg["+0+"]["+l+"]["+3+"]="+tg[0][l][3]); 
                          if(tg[0][l][3] == 1){    // 3nd row of the final subgraph
                            count++;
                            
                          }
                          else{
                            count = count;
                            
                          }
                       //     }
                            //System.out.println("Sampradan count ="+ count);
                            if(count == 1){
                                     //System.out.println("Sampradan(K4) velidated...");
                                     flagk4 = 0;
                                     break;
                            }
                            else{
                                //tg[0][2][3] = 0;
                          //System.out.println("Sampradan(K4) is Not velidated...");
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
                  }*/
                }
                
            } 
            
      // Check Apadan(K5) Ablative Case
            count = 0;
            t = 0;
                    prsk5 = Presence[4][0];
            //System.out.println("prsk5="+prsk5);
            if(prsk5 != null){
            if(prsk5.equals("Optional") && t < 4 ) {
              //System.out.println("Optional");
              krrk5 = Karaka[4][1];
              //System.out.println("krrk5="+krrk5);
              if(krrk5.equals("Apadan(K5)")){
                //System.out.println("Apadan(K5)");
                vbtk5 = vibh[4][2];
                  tvbt = separateVbktTAM(vbtk5);
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhakti[4][8][1]="+Actualvbt+"tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows  && t < trows; l++){
                      //System.out.println("**Actualvibhakti["+l+"]["+(2*l)+"][l]="+Actualvibhaktilist[t] +"   t="+t);
                      try{
                        if(Actualvibhaktilist[t].equals(tvbt[i]) && ct5 == 0){
                          if(! Actualvibhaktilist[t].equals("null")){
                              ct5 = 1;
                              //System.out.println("hi Apadan vibhakti is not null"); 
                            }
                            else{
                              ct5 = 0;
                          }
                          //System.out.println("Apadan count ="+ count);
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                  
                      //  for(int r = 0 ; r < trows ; r++){
                          if(tg[0][l][4] == 1){    // 3nd row of the final subgraph
                            //if(Actualvibhaktilist[k].equals(tvbt[i]))
                              count++;
                            //System.out.println("hi");
                            
                          }
                          else{
                            count = count;
                            
                          }
                       //     }
                            //System.out.println("Apadan count ="+ count);
                            if(count == 1){
                                     //System.out.println("Apadan(K5) velidated...");
                                     flagk5 = 0;
                                     break;
                            }
                            else{
//                                tg[0][2][3] = 0;
                          //System.out.println("Apadan(K5) is Not velidated...");
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
            } 
            ////System.out.println("flagk1= "+flagk1);


      // Check Sambandh(K6)
            count = 0;
            t = 0 ;
                    prsk6 = Presence[5][0];
            //System.out.println("prsk6="+prsk6);
            if(prsk6 != null){
            if(prsk6.equals("Optional")  ){
              //System.out.println("Optional");
              krrk6 = Karaka[5][1];
              //System.out.println("krrk6="+krrk6);
              if(krrk6.equals("Sambandh(K6)")){
                //System.out.println("Sambandh(K6)");
                vbtk6 = vibh[5][2];
                  tvbt = separateVbktTAM(vbtk6);
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    ////System.out.println("Actualvibhakti[5][10][1]="+Actualvibhakti[3][6][1]+"tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows && t < trows; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
                      try{
                        if(Actualvibhaktilist[t].equals(tvbt[i]) && ct6 == 0){
                          if(! Actualvibhaktilist[t].equals("null")){
                              ct6 = 1;
                              //System.out.println("hi Sambhandh vibhakti is not null");  
                            }
                            else{
                              ct6 = 0;
                          }
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
                            //System.out.println("Sambandh count ="+ count);
                            if(count == 1){
                                     //System.out.println("Sambandh(K6) velidated...");
                                     flagk6 = 0;
                                     break;
                            }
                            else{
//                                tg[0][2][3] = 0;
                          //System.out.println("Sambandh(K6) is Not velidated...");
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
      }
      
  // Check Adhikaran (K7) 
      
      
                count = 0;
                t=0;
                    prsk7 = Presence[6][0];
            //System.out.println("prsk7="+prsk7);
            
            if(prsk7.equals("Optional") ){
              //System.out.println("Optional");
              krrk7 = Karaka[6][1];
              //System.out.println("krrk7="+krrk7);
              if(krrk7.equals("Adhikaran(K7)")){
                //System.out.println("Adhikaran(K7)");
                vbtk7 = vibh[6][2];
                  tvbt = separateVbktTAM(vbtk7);
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhakti[2][6][1]="+Actualvbt+"  tvbt["+i+"]="+tvbt[i]);
                    for(int l = 0 ; l < trows ; l++){
                      //System.out.println("Actualvibhakti["+l+"]["+(2*l)+"][1]="+Actualvibhaktilist[t]);
                      try{
                      if(Actualvibhaktilist[t].equals(tvbt[i])) {
                   // }
                  //if(vbtk1.equals("\u0932\u0947") || vbtk1.equals("\u0915\u094B") || vbtk1.equals("null")   || vbtk1.equals("\u0932\u093E\u0908")){
                       // int karn ;              // Karana does not exist
//                       r = l;
                       // for(int r = 0 ; r < trows ; r++){
                          //System.out.println("l="+l);
                          if(tg[0][l][6] == 1 ){//&& karn != 0){     // 2nd row of the final subgraph
                            //System.out.println("**l="+l);
                            count++;
                            
                          }
                          else{
                            count = count;
                            
                          }
//                            }
                            //System.out.println("Adhikaran count ="+ count);
                            if(count == 1){
                                     //System.out.println("Adhikaran(K7) velidated...");
                                     flagk7 = 0;
                                     break;
                            }
                            else{
                                //tg[0][2][3] = 0;
                          //System.out.println("Adhikaran(K7) is Not velidated...");
                          flagk7 = 1;
                            }
                      }
                            else{
                        if(flagk7 == 0 && i == (tvbt.length-1))
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
            
            
      // Check Location (K2P) Optional
            count = 0;
            t = 0;
            
                    prsk2p = Presence[7][0];
            //System.out.println("prsk2p="+prsk2p);
            if(prsk2p != null){
            if( prsk2p.equals("Optional")){
              //System.out.println("K2p Optional");
              krrk2p = Karaka[7][1];
              //System.out.println("krrk2p= "+krrk2p);
              if(krrk2p.equals("Locative(K2p)")){
                //System.out.println("Locative(K2p)");
                vbtk2p = vibh[7][2];
                  tvbt = separateVbktTAM(vbtk2p);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is Locative K2p");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[l].equals(tvbt[i])){
                    //System.out.println("hi it is Location (K2p) ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+7+"]="+tg[0][k][7]);                         
                          if((tg[0][k][7]) == 1){
                            count =1;
                            
                          }
                        }
                      }
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("Location K2p count ="+ count);
                if(count == 1){
                        //System.out.println("LOCATION (K2P) velidated...");
                        flagk2p = 0;
                        flagk2 = 1;
                        break;
                }else{
                  //System.out.println("LOCATION (K2P) not velidated...");
                        flagk2p = 1;
                }
              }
            }
            }   
            }         
            
            
            
       // Check Location (K2P) Mendatory
            count = 0;
            t = 0;
            
                    prsk2p = Presence[7][0];
            //System.out.println("prsk2p="+prsk2p);
            if(prsk2p != null){
            if( prsk2p.equals("Mendatory")){
              //System.out.println("K2p Mendatory");
              krrk2p = Karaka[7][1];
              //System.out.println("krrk2p= "+krrk2p);
              if(krrk2p.equals("Locative(K2p)")){
                //System.out.println("Locative(K2p)");
                vbtk2p = vibh[7][2];
                  tvbt = separateVbktTAM(vbtk2p);
                  
                  for(int i = 0 ; i < tvbt.length ; i++ ){
                    //System.out.println("Actualvibhaktilist["+i+"]="+Actualvibhaktilist[i]+"   frametvbt["+i+"]="+tvbt[i]);
                  
                  try{
                    for(int l = 0 ; l < Actualvibhaktilist.length ; l++){
                      //System.out.println("hi it is Locative K2p");
                      //System.out.println("Actualvibhaktilist["+l+"]="+Actualvibhaktilist[l]+"   frametvbt["+i+"]="+tvbt[i]);
                      if(count == 0 || Actualvibhaktilist[l].equals(tvbt[i])){
                    //System.out.println("hi it is Location (K2p) ***");
                        for(int k = 0; k < trows ; k++){
                          //System.out.println("tg["+0+"]["+k+"]["+7+"]="+tg[0][k][7]);                         
                          if((tg[0][k][7]) == 1){
                            count =1;
                            
                          }
                        }
                      }
                      //System.out.println();
                    }
                    
                  
                  }
                       catch (NullPointerException nullPointer)
                      {
                    //log(causeStr, nullPointer, System.out);
            }  
            //System.out.println("Location K2p count ="+ count);
                if(count == 1){
                        //System.out.println("LOCATION (K2P) velidated...");
                        flagk2p = 0;
                        flagk4 = 1;
                        break;
                }else{
                  //System.out.println("LOCATION (K2P) not velidated...");
                        flagk2p = 1;
                }
              }
            }
            }   
            }         
            
            
            System.out.println("flagk1= "+flagk1+"   flagk2="+flagk2+"    flagk3= "+flagk3+"  flagk4= "+flagk4 +"      flagk5=  "+flagk5+  "     flagk6="+flagk6+  "     flagk2p="+flagk2p+   "    trows="+trows);
            flgk[0] = flagk1;
            flgk[1] = flagk2;
            flgk[2] = flagk3;
            flgk[3] = flagk4;
            flgk[4] = flagk5;
            flgk[5] = flagk6;
            flgk[6] = flagk7;
            flgk[7] = flagk2p;
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
              for(int i=0;i<8;i++){
              
                if(tg[0][m][i] == 1){
                    //System.out.println(" ct0="+ct+"   trows="+trows);               
                //while(ct < trows){
                  if(flgk[i] == 0 ){
                    ct++;
                    flg1 = true;
                    //System.out.println(" ct="+ct+"   trows="+trows);
                    if(ct == trows)
                      break;
                    else
                      flg1 = false;
                  //break;
                  }
                  else{
                    flg1 = false;
                  } 
                }
                else 
                  flg1 = false;
        //      }
              }
            }
            
            //  break;
            
            //}
            //while(ct <= trows);
//            break;
            
  /*      newfg = NewSubgraph(tg,trows,tcol,flagk4);
            //System.out.println("\t  KARTA(k1)\tKARMA(k2)\tKARANA(k3)\tGOL(K2P)");
            for(int l = 0 ; l < trows ; l++){
              //System.out.println();
            for(int m = 0 ; m < tcol ; m++){
                //System.out.print("  newfg["+0+"]["+l+"]["+m+"]="+newfg[0][l][m]);
              }
              //System.out.println();
            }*/
      
      return prsk1;
    
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
          try{
         for(int l = 0 ; l < wdslen ; l++){
            fout.write("\n");
            fout.write(wds[l]);
          //(int m = 0 ; m < krks ; m++){
          //    fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
          //  }
            fout.write("\n");
          }
        }
        catch (NullPointerException nullPointer){}
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
          //    fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
          //  }
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
                //System.out.print("\t wds["+l+"]["+m+"]["+n+"]=" +wds[l][m][n]);
                fout.write("\t wds["+l+"]["+m+"]["+n+"]=" +wds[l][m][n]);             
              }
            }

          //(int m = 0 ; m < krks ; m++){
          //    fout.write("  fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
          //  }
            fout.write("\n");
        }
          fout.close();
    } 
    catch(IOException ie){}
    finally { 
         // fout.close();
  }
    }
    public static void displayString(String outputName2, int[][][] fg1,int ind, int rows, int krks, String[] nn, boolean e, boolean k1s){
    try{
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName2, true), "UTF-8");
          BufferedWriter fout = new BufferedWriter(writer);

          
          if(!k1s)
            duplicate = new int[rows][krks];

    else{
//      krks = krks + 1;
      duplicate = new int[rows][krks+1];
    }
    int ctrw = 0;
          System.out.println("Hi its displaystring ");
           System.out.println("k1s= "+k1s+" ind="+ind+"  rows="+rows+"  krks="+krks);
           for(int a = 0 ; a < rows ; a++){
            for(int b = 0 ; b < krks ; b++){
              if(fg1[ind][a][b] == 1){
                ctrw++;
              }
              System.out.print("   "+fg1[ind][a][b]);

            }
            System.out.println();
           }
           if(ctrw < rows){
            addone = true;
           }
         //  if(k7texist) fg1[ind][0][0] = 1;
           if(k2exist == 1 && fg1[ind][0][0] == 1){
            //System.out.println("yes karma exist");
            fg1[ind][0][1] = 1;
            fg1[ind][0][0] = 0;
           }
           if(rows==2){
            if(fg1[ind][1][3] == 1){
              fg1[ind][1][3] = 0;
              fg1[ind][1][1] = 1;
            }
            if(fg1[ind][1][7] == 1 ){
              for(int s=1;s<krks;s++){
                if(fg1[ind][0][s] == 1){
                  fg1[ind][0][s] = 0;
                  fg1[ind][0][0] = 1;
                }
              }
            }
           
           }
           if(rows > 3 ){
            if(k1s){
             // fg1[ind][1][3] = 0;
              //System.out.println("Hi its CCS k1s"+rows);
              fg1[ind][rows-1][krks-1] = 1;
              for(int s=0 ; s < (krks-1) ; s++){
                fg1[ind][rows-1][s] = 0;
              }
            }
           
           }
           if(rows == 1){
            if(fg1[ind][0][1] == 1 && ekoexist ==1){
              fg1[ind][0][0] = 1;
              fg1[ind][0][1] = 0;
            }
           }
          // if(k7texist){

           System.out.println("Hi its displaystring  k2pexist="+k2pexist);
           if(k2pexist){
                     // if(fg1[ind][0][4] == 1 ){
                      		/*if(fg1[ind][2][4] == 1){
                      			fg1[ind][2][4] = 0;
                      			fg1[ind][2][1] = 1;
                      		}*/
                        for(int i = 0 ; i < rows ; i++){
                        	if(fg1[ind][i][4] == 1 ){
                        		fg1[ind][i][4] = 0;
                        		fg1[ind][i][7] = 1;
                        		for(int j1 = 0 ; j1 < rows ; j1++){
                        			System.out.println("fg1["+ind+"]["+j1+"]["+7+"]="+fg1[ind][j1][7]+"   i="+i);
                        			if(j1 != i && fg1[ind][j1][7] == 1){
                        				fg1[ind][j1][7] = 0;
                        				fg1[ind][j1][1] = 1;
                        				System.out.println("*fg1["+ind+"]["+j1+"]["+7+"]="+fg1[ind][j1][7]+"   i="+i);
                        				System.out.println("k2p displaystring bus ");
                        			}
                        		}
                        	}
                        }
                        	for(int i1=0;i1<rows;i1++){
                          		for(int j = 0 ; j < krks ; j++){
                             		System.out.print("      "+fg1[ind][i1][j]);
                         			  duplicate[i1][j] = fg1[ind][i1][j]; 
                         			System.out.println("inside Hi its displaystring  k2pexist="+k2pexist);
                          		 }
                          		}
                          	
                            //System.out.println();
         
                        //}
                     // }
            }
            else{
                for(int i = 0 ; i < rows ; i++){
                  for(int j = 0 ; j < krks ; j++){
                      //System.out.print("      "+fg1[ind][i][j]);
                  
                      duplicate[i][j] = fg1[ind][i][j]; 
                  // }
                //}
              
                  }
                      //System.out.println();
         //  }
                }
          }
        /*  if(k2pexist ){
              if(fg1[ind][1][1] == 1 ){
                for(int i = 0 ; i < rows ; i++){
                          for(int j = 0 ; j < krks ; j++){
                            //System.out.print("      "+fg1[ind][i][j]);
                          //   fg1[ind][1][7] = 1;
                            // fg1[ind][1][1] = 0;
                             duplicate[i][j] = fg1[ind][i][j]; 
                           }
                         }
                         //System.out.println();
              }
          }*/
                        // duplicate[i][j] = fg1[ind][i][j]; 
                  // }
                   //else{
            
          // if(k1s) krks= krks + 1;
           //System.out.println("krks="+krks);
          //fout.write("Hi its displayString in");
         // fout.write("\nKARTA(k1)  KARMA(k2)  KARANA(k3)  SAMPRADAN(K4)  APADAN(K5)  SAMBANDH(K6)  ADHIKARAN(K7) LOCATION(K2P) LOCATION(K7t)   KARTA(K1S)     (K*U)");
         // do{
          for(int l = 0 ; l < rows ; l++){
           // fout.write("\n");
           // fout.write(nn[l]);
          for(int m = 0 ; m < krks ; m++){
            //if(!e){
            if(!k1s){}
                //fout.write("           "+fg1[ind][l][m]);
              else{
                fg1[ind][1][8] = 0;
                //fg1[ind][0][9] = 0;
                if(l == 0){
                  if(m<(krks-1)){
                 // fout.write("           "+fg1[ind][l][m]);
                  duplicate[l][m] = fg1[ind][l][m];
                }
                else{
                 // fout.write("           "+fg1[ind][l][m]+"           "+0);
                  duplicate[l][krks] = 0;
                }
              }
              else
                if(m<(krks-1)){
                  //fout.write("           "+fg1[ind][l][m]);
                  duplicate[l][m] = fg1[ind][l][m];
                }
                else{
                  //fout.write("           "+fg1[ind][l][m]+"           "+1);
                //  duplicate[l][m] = 1;            
                }
            //fout.write("  fg1["+ind+"]["+l+"]["+krks+"]="+1);
              }
                //fout.write("          "+fg1[ind][l][m]);
              //}
              //else{
              /*if(k1s){
              if(l == 1)
                fg1[ind][l][8] = 0;
                fout.write("  fg1["+ind+"]["+l+"]["+krks+"]="+1);
                //System.out.println("krks="+krks);
              
            }*/
                
              //  fout.write("     fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
                //fout.write("        "+0);
              //}
            }
            if(!e){
                //fout.write("        "+0);
                //fout.write("          "+0);
              }
            if(!k2pexist && !k1s && !e && rows>2){
            	if(duplicate[1][3] == 1 && duplicate[2][4] == 1 ){
            		System.out.println("Apadan removed");
            		duplicate[2][1] = 1;
            		duplicate[2][4] = 0;
            	}
                //fout.write("         "+0);
                //fout.write("          "+fg1[ind][l][m]);
              }
              else if(!e){
                if(l == 1){
                	/*try{
                  		fg1[ind][l][8] = 0;
                  	}
                  	catch(ArrayIndexOutOfBoundsException ae){};*/
                  //System.out.println("problem FG1["+ind+"]["+l+"]["+7+"]="+fg1[ind][l][7]);
                 // fout.write("         "+1);
                }
                else{
                 // fout.write("        "+0);
                }
              }
              //else{
                
              //  fout.write("     fg1["+0+"]["+l+"]["+m+"]="+fg1[0][l][m]);
                //fout.write("        "+0);
            //}
            fout.write("\n");
          }
          fout.write("\n **********************************************   ");
          fout.close();
          //System.out.println("duplicate");
          if(k1s && rows == 2){
            duplicate[1][8] = 0;
            duplicate[1][9] = 1;
          }
          if(k1s && rows > 2){
              for(int i = 0 ; i < rows ; i++){
                if(fg1[ind][i][8] == 1)
                      duplicate[1][9] = 1;
               }
          }
          System.out.println("display string duplicate matris:");
          for(int l = 0 ; l < rows ; l++){
            for(int m = 0 ; m < krks ; m++){
              System.out.print("  "+duplicate[l][m]);
            }
            System.out.println();
          }
        //}
        //while(tind < ind && )
    } 
    catch(IOException ie){}
    finally { 
         // fout.close();
  }
  finalend = 1;
    }
    
    public int[][] intrachunk(String[] np, String[] tnp, int[][] duplicateparse, int ln, String[] vb, String filename, String filename1, String out, String[] sent){
      
      
    //  int[][] npfinal = new int[15][16];
      
      PaintNodes2 pn1 = new PaintNodes2();
      PaintNodes2 pn2 = new PaintNodes2();    
      
        int superindex = 0;  
        int superindex1 = 0;
        int superindex11 = 0;  
        int superindex111 = 0;
     // Picture pc = new Picture(300,300);
      String ws = " ";
      String[] sw;
      String[] words = new String[30];
      //words[0] = "VERB";
      String[] tsw;
      String[] tags = new String[30];
      String[] tempstr = new String[30];
      for(int i = 0 ; i < tags.length;i++){
        tags[i] = "tag";
      }

      for(int i = 0 ; i < vb.length;i++){
        //System.out.println("vb["+i+"]="+vb[i]);
      }
      int novertices = vb.length; // Verb is the root
      // ORDER IS 1. VERB     2. NOUN1     3. NOUN2      4. NOUN3
      int noedges = 0;
      int flag = 0;
      int count = 0 ;
      int count1 = 1;
      int count2 = 0;
      int ct = 0;
      //System.out.println(" Intrachunk length ="+ln);
      sw = np;
      tsw = tnp;  
      int available = 0;
      int oldswlen = 0;
      int oldswvibhakti = 0;
      ct1=0;
      int oldi = 0;
      int ftk = 0;

      //int flgsw = 0;
      //int swcount = 0;
//      int fugee = 0;
      String[] relation = {"Karta(K1)","Karma(K2)", "Karana(K3)", "Sampradan(K4)", "Apadan(K5)","Sambandh(K6)","Adhikaran(K7)","Location(K2p)","Location(K7t)","Second Karta(K1s)"," Comparision(K*u)"};
      String[] parserelation = new String[30];
      for(int m = 0 ; m < 30 ; m++)
        parserelation[m] = "next";
      
      System.out.println("k2pexist = "+k2pexist);
      //sw = np;
    //  extduplicate = duplicateparse;
      for(int i=0; i < ln  ; i++){
        //if(!tnp[i].equals("null2")){
        superindex1 = 0;
        superindex111 = 0;
        int icount = 0;
        //System.out.println("previous Novertices="+novertices+"    sw.length="+sw.length);

        if(i > 0){
            oldi = i-1;
           // oldswlen = sw.length;
  //        fugee = 1;
          }
          //System.out.println("np["+i+"]="+np[i]);
          //System.out.println("tnp["+i+"]="+tnp[i]);    
       //   //System.out.println("vb["+i+"]="+vb[i]); 
          /*if(!tnp[i].equals("null2")){
            
          }*/
          
          try{
            sw = np[i].trim().split("\\s+"); 
            tsw = tnp[i].trim().split("\\s+");
          }
          catch(NullPointerException nullPointer){
            //System.out.println(" THERE IS NO CHUNK");
            //sw[0] = "null1";
        //tsw[0] = "null1";
      }
    //  swcount++;
    //}
    
    try{
        if(sw[0].equals("")){
          //System.out.println("go back");
          flag = 1;
        }
        }
        catch(NullPointerException nullPointer){
            //System.out.println(" THERE IS NO CHUNK again");
            //sw[0] = "null1";
        //tsw[0] = "null1";
    }
        if(flag == 1){
          novertices = novertices - 1;
        }
       

        
        for(int j = 0 ; j < sw.length;j++){
          ftk = 0;
          words[count1] = sw[j];
          //System.out.println("input string words separated by whitespace: " + sw[j] +"    "+tsw[j]); 
          tags[count2] = tsw[j];
          count2++;
          if(j<=0 && j >= (sw.length-1) && (tsw[j].equals("PSPLE") || tsw[j].equals("PSPLAI") || tsw[j].equals("PSPO"))){
            //System.out.println("*j="+j);
            count1 = count1;
            flag = 2;
          
          }
          else{
            count1++;
            flag = 0;
          }
        }
        novertices = novertices + sw.length;
        
        /*if(flag == 2){
          novertices = novertices - 1;
        }*/
        //novertices = novertices + 1;
        noedges = novertices+1;
        
        //System.out.println("Novertices="+novertices+"    sw.length="+sw.length+"   noedges="+noedges+"    flag="+flag);
      //  noedges = novertices - 1;
        int swvibhakti = 0;
        if(flag == 2){
          novertices = novertices - 1;
          swvibhakti = sw.length - 1;
        }
        //if(novertices > t)
      //    extduplicate = new int[noedges][novertices];
    
    //t=novertices;
  //} 
    int k = 0;
    //System.out.println();
    //System.out.println("addone ="+addone);
      if(addone){
              duplicateparse[ln-1][5] = 1;
            }
        //System.out.println("No of vertices ="+novertices+"  i="+i+"    sw.length =="+sw.length+"  duplicateparse["+0+"]="+duplicateparse[0].length);
        //for(int m = 0 ; m < novertices;m++){
          for(int n = 0 ; n < duplicateparse[0].length;n++){
            System.out.print("   "+duplicateparse[i][n]);

            if(duplicateparse[i][n] == 1 && icount == 0){
              //System.out.println("  i="+i+"  n="+n);
              icount++;
                if(superindex111==0){
                //  superindex111++;
                 // superindex111 = 
                   //System.out.println("zero superindex111="+superindex111+"   superindex11="+superindex11+"  n="+n);
                  extduplicate[superindex11][superindex111] = 1;
                  //System.out.println("oldswlen="+oldswlen);
                  superindex111 = superindex111 + oldswlen + 1;
                //  if(superindex111 > n && i >0 )  superindex111--;
                  //System.out.println("superindex111="+superindex111);
                  extduplicate[superindex11][superindex111] = 1;
                  superindex11++;
                }
                if(superindex111!=0){
                   for(int l = 1 ; l < (sw.length) ; l++){
                    //System.out.println("superindex111="+superindex111+"   superindex11="+superindex11+"  sw.length="+sw.length);
                  extduplicate[superindex11][superindex111] = 1;
                  superindex111 = superindex111 + 1;
                  extduplicate[superindex11][superindex111] = 1;
                  superindex11++;
                 // oldswlen = sw.length;
                  //System.out.println("123 superindex111="+superindex111+"   superindex11="+superindex11+"  oldswlen="+oldswlen);
                }
                }
                System.out.println("early intrachunk duplicate");
              //  noedges = noedges+1;
               // noedges1 = noedges1 +1;
                 for(int m1 = 0 ; m1 < noedges;m1++){
                    int noct = 0;
                      for(int n1 = 0 ; n1 < novertices;n1++){
                        if(extduplicate[m1][n1]  == 1 ){
                            noct++;
                            //System.out.println("123 m1="+m1+"   n1="+n1+"   noct="+noct);
                            if(noct != 2 && n1 == (novertices-1)){ 
                      //System.out.println("123456 m1="+m1+"   noct="+noct);
                        extduplicate[m1][novertices-1] = 1;
                        //System.out.println("123456");
                     }
                          }
                     //  //System.out.print("   "+extduplicate[m1][n1]);

                     }

                  // //System.out.println();
                 }
                 
      for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          System.out.print("   "+extduplicate[m1][n1]);
        }
    System.out.println();
  }

              if(flag != 2){
                if(sw.length == 1 && available == 0 ){
                  parserelation[ct] = relation[n];
                  //System.out.println("initial parse relation at n="+n+"   relation["+n+"]="+relation[n]);
                  //extduplicate[superindex][superindex1] = 1;
                  //superindex = superindex + i;
                  superindex1 = superindex1 + i + 1;
                 // if( //extduplicate[superindex][superindex1] == 0)
                    //extduplicate[superindex][superindex1] = 1;
                  superindex++;
                  //System.out.println("superindex="+superindex+"    superindex1 ="+(superindex1 + i));
                  
                  //System.out.println("Every thing is main: i = "+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  relation n="+n );
                  ct++;
                }
                else{
                  available = 1;
                  if(i==0 && !tsw[0].equals("JJ")){
                    parserelation[ct] = relation[n];
                   //System.out.println("parse relation at n="+n+"   relation["+n+"]="+relation[n]);

                    //extduplicate[superindex][superindex1] = 1;    // 0 indicates verb position
                    superindex1 = superindex1 + i + 1;
                    //ct++;
                    //extduplicate[superindex][superindex1] = 1;
                   // superindex = superindex + i;
                    superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 ="+(superindex1 + i));
                    //ct++;
                    //System.out.println("  main1: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                    ct++;
                  
                  }
                  else if(i == 0 && tsw[0].equals("JJ")){
                    parserelation[ct] = relation[n];
                  //System.out.println("parse relation at n="+n+"   relation["+n+"]="+relation[n]);

                    //extduplicate[superindex][superindex1] = 1;    // 0 indicates verb position
                    //ct++;
                    superindex1 = superindex1 + i + 1;
                    //extduplicate[superindex][superindex1] = 1;
                   // superindex = superindex + i;
                    superindex++;
                  //System.out.println("superindex="+superindex+"    superindex1"+(superindex1 + i));
                    //ct++;
                    //System.out.println("else main1: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                    ct++;
                  }
                  

                  else{
                    
                    //System.out.println("  new extdupliate["+(superindex)+"]["+0+"]="+extduplicate[superindex][0]);
                    if((tsw[0].equals("PRL") || tsw[0].equals("PRP")) && ftk == 0){
                          if(tsw[1].equals("RB")){
                            parserelation[ct] = relation[10];
                  //System.out.println("parse relation at n="+n+"   relation["+n+"]="+relation[n]);

                            //extduplicate[superindex][superindex1] = 1;    // 0 indicates verb position
                    //ct++;
                            superindex1 = superindex1 + i + 1;
                             ////extduplicate[i][10] = 1;
                            //extduplicate[i][superindex1] = 1;
                            // superindex = superindex + i;
                             superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1 ="+(superindex1 + i));
                             //System.out.println("main10: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                             ct++;
                          }

                  }

                    parserelation[ct] = relation[n];
                  //System.out.println("parse relation at n="+n+"   relation["+n+"]="+relation[n]);

                    //ct++;
                    //System.out.println("hi tsw["+0+"]="+tsw[0]+"  ftk="+ftk+"  oldswlen="+oldswlen+"   i="+i);
                    if((tsw[0].equals("JJ") && ftk == 0)) {
                      //oldswlen = oldswlen + 1;

                      //extduplicate[superindex][superindex1] = 1;
                      superindex1 = superindex1 + i + 1;
                      //extduplicate[superindex][superindex1] = 1;
                      //System.out.println("JJ extdupliate["+(superindex)+"]["+(0)+"]="+extduplicate[superindex][0]);
                      //System.out.println("JJ extdupliate["+(superindex)+"]["+(i+oldswlen-1+1)+"]="+extduplicate[superindex][i+oldswlen-1+1]);
                     // superindex = superindex + i + oldswlen-1;
                      superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1"+(superindex1 + i));
                      ftk = i+oldswlen;
                      //System.out.println("JJ ftk="+ftk);
                      ct++;
                    }
                    else if(ftk != 0 && tsw[0].equals("JJ")){
                      //extduplicate[superindex][superindex1] = 1;
                      superindex1 = superindex1 + i + 1;
                      //extduplicate[superindex][superindex1] = 1;
                      // superindex = superindex + ftk + 1;
                      
                      //System.out.println("JJ extdupliate["+(superindex)+"]["+(superindex1-1)+"]="+extduplicate[superindex][superindex1-1]);
                      //System.out.println("JJ extdupliate["+(superindex)+"]["+(superindex1)+"]="+extduplicate[superindex][superindex1]);
                      superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 ="+(superindex1 + i));
                      ct++;
                    }
                    else if((tsw[0].equals("QTC") && ftk == 0) || (tsw[0].equals("QC") && ftk == 0) || (tsw[0].equals("QF") && ftk == 0)){
                      /*if(i == 1){
                        //extduplicate[i+oldswlen-1][0] = 1;
                        //extduplicate[i+oldswlen-1][i+oldswlen-1+1+1] = 1;
                        //System.out.println("QTC extdupliate["+(i+oldswlen-1)+"]["+(0)+"]="+//extduplicate[i+oldswlen-1][0]);
                        //System.out.println("QTC extdupliate["+(i+oldswlen-1)+"]["+(i+oldswlen-1+1+1)+"]="+//extduplicate[i+oldswlen-1][i+oldswlen-1+1+1]);
                      
                        ftk = i+oldswlen;
                        //System.out.println("QTC ftk="+ftk);
                        ct++;
                      }
                      else {*/
                        k = i;
                        ftk = k+oldswlen;
                        //extduplicate[superindex][superindex1] = 1;
                        superindex1 = superindex1 + i + 1;
                        //extduplicate[superindex][superindex1] = 1;
                        //System.out.println("QTC extdupliate["+(superindex)+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                        //System.out.println("QTC extdupliate["+(superindex)+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                     //  superindex = superindex + ftk;
                        superindex++;
                  //System.out.println("superindex="+superindex);
                        //System.out.println("else QTC ftk="+ftk);
                        ct++;
                      //}
                    }
                    else{
                      //for(int k = 0; k < sw.length ; k++){
                        k=1;
                       // superindex1++;
                        //System.out.println("no JJ oldswlen="+oldswlen+"   i="+i+"    sw.len="+sw.length+"   superindex1 = "+superindex1);
                        //extduplicate[superindex][superindex1] = 1;
                        //System.out.println("no JJ extdupliate["+(superindex)+"]["+(superindex1)+"]="+extduplicate[superindex][superindex1]);
                        superindex1 = superindex1 + i+ 1;
                        //extduplicate[superindex][superindex1] = 1;
                        // superindex = superindex + k + oldswlen+2;
                        
                        
                        //System.out.println("no JJ extdupliate["+(superindex)+"]["+((superindex1))+"]="+extduplicate[superindex][(superindex1)]);
                        superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1"+(superindex1));
                        //ct++;
                        //ftk = 1;
                        //ct++;
                    //  }
                    }
                    //ct++;
                    ////extduplicate[i+oldswlen-1][i+oldswlen-1+1] = 1;
                    //System.out.println("extdupliate["+(superindex)+"]["+(i+oldswlen-1)+"]="+extduplicate[superindex][i+oldswlen-1]);
                    
                    //ct++;
                    //System.out.println("main2: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  sw.length="+sw.length+" oldswlen="+oldswlen );
                    ct++;
                  }
                  //int ftk = 0;
                  for(int l = 2 ; l <= (sw.length) ; l++){
                    //System.out.println("l="+l+"   ct="+ct+"   superindex1"+(superindex1 + i));
                    if(i==0){
                      //extduplicate[superindex][superindex1] = 1;
                      superindex1 = superindex1 + i + 1;
                      //extduplicate[superindex][superindex1] = 1;
                      //System.out.println("extdupliate["+superindex+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                      //System.out.println("extdupliate["+superindex+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                      parserelation[ct] = "Oth";
                  //System.out.println("parse relation at n="+n+"   relation["+n+"]="+relation[n]);

                     //  superindex = superindex + i + l-1;
                      superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1 ="+(superindex1 + i));
                      //System.out.println("i = 0 and other 1:"+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                      ct++;
                    }
                    else{
                      if(ftk != 0 && tsw[0].equals("JJ")){
                        //oldswlen = oldswlen + 1;
                        
                        
                        if(l == 2 ){
                          //extduplicate[superindex ][superindex1] = 1;
                          superindex1 = superindex1 + i + 1;
                          //extduplicate[superindex][superindex1] = 1;
                          //System.out.println("if previous JJ extdupliate["+(superindex)+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                          //System.out.println("if previous JJ extdupliate["+(superindex)+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                         //  superindex = superindex + i + ftk;
                          superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1 ="+(superindex1 + i));
                          //nonext = 1;
                        }
                        if(l > 2 ){
                          //extduplicate[superindex][superindex1] = 1;
                          superindex1 = superindex1 + i + 1;
                          //extduplicate[superindex][superindex1] = 1;
                          //System.out.println("if next JJ extdupliate["+superindex+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                          //System.out.println("if next JJ extdupliate["+superindex+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                         //  superindex = superindex + ftk;
                          superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 ="+(superindex1 + i));
                        }
                          
                      //  ftk = 0;
                      }
                      else if(ftk != 0){   // for QTO
                        int tp = k+oldswlen-1+l-1;
                        if(tsw[0].equals("NN") || tsw[0].equals("PRP") || tsw[0].equals("NNP") || tsw[0].equals("PRL")){
                          //extduplicate[superindex][superindex1] = 1;
                          superindex1 = superindex1 + i + 1;
                          //extduplicate[superindex][superindex1] = 1;
                          //System.out.println("else extdupliate["+superindex+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                          //System.out.println("else extdupliate["+superindex+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                         //  superindex = superindex + k+oldswlen -1 + l - 1;
                          superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1"+(superindex1 + i));
                          ftk = 0;
                        }
                        else{
                           tp = k+oldswlen-1+l-1;
                          //extduplicate[superindex][superindex1] = 1;
                          superindex1 = superindex1 + i + 1;
                          //extduplicate[superindex][superindex1] = 1;
                          //System.out.println("NN else extdupliate["+superindex+"]["+((superindex1 + i)-1)+"]="+extduplicate[superindex][(superindex1 + i)-1]);
                          //System.out.println("NN else extdupliate["+superindex+"]["+((superindex1 + i))+"]="+extduplicate[superindex][(superindex1 + i)]);
                         //  superindex = superindex + k+oldswlen-1+(l);
                          superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1"+(superindex1 + i));
                          ftk = 0;
                        }
                      }
                      else {//} if(k1sposition != (k+oldswlen+(l))){
                        int tmp = i+oldswlen-1+(l-1);
                        //k=0;
                        k=1;;
                        //System.out.println("FINAL k="+k+"   oldswlen="+oldswlen+"   l ="+l+" temp="+tmp+ " k1sposition"+k1sposition);

                        //System.out.println("final else oldswlen="+oldswlen+"   i="+i+"    sw.len="+sw.length+"   superindex1 = "+superindex1);
                        //extduplicate[superindex][superindex1] = 1;
                        //System.out.println("final else extdupliate["+(superindex)+"]["+(superindex1)+"]="+extduplicate[superindex][superindex1]);
                        superindex1 = superindex1 +  1;
                        //extduplicate[superindex][superindex1] = 1;
                        // superindex = superindex + k + oldswlen+2;
                        
                        
                        //System.out.println("final extdupliate["+(superindex)+"]["+((superindex1))+"]="+extduplicate[superindex][(superindex1)]);

                  /*      //extduplicate[superindex][superindex1] = 1;
                        superindex1 = superindex1 + i + 1;
                        //extduplicate[superindex][superindex1] = 1;
                        //System.out.println("FINAL else extdupliate["+superindex+"]["+((superindex1 + i)-1)+"]="+//extduplicate[superindex][(superindex1 + i)-1]);
                        //System.out.println("FINAL else extdupliate["+superindex+"]["+(superindex1 + i)+"]="+//extduplicate[superindex][(superindex1 + i)]);
                      //  superindex = superindex + k+oldswlen+(l);*/
                        superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1= "+(superindex1 + i));
                        ftk = 0;
                      }
                      
                      
                      
                      ////System.out.println("i != 0 and other 2:");
                      parserelation[ct] = "Oth";
                      //System.out.println("i != 0 and other 2:"+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  sw.length="+sw.length+" oldswlen="+oldswlen  );
                      ct++;
                    }
                  /*if(i==0 && l < sw.length){
                    //extduplicate[i][i+l] = 1;    // for noun1 position
                    parserelation[ct] = "Oth";
                  }
                  else{
                    //extduplicate[i+sw.length][i+sw.length+1] = 1;
                    parserelation[ct] = "Oth";                    
                  }
                  if(l != sw.length ){
                    //extduplicate[i+1][i+1] = 1;   // for noun1
                    //extduplicate[l+i][i+l+1] = 1; 
                    parserelation[ct] = "Oth";                    
                  }*/
                }
              }
                }
                else{
                  //System.out.println("danger");
                    flag = 0;
                    if(swvibhakti == 1 && available == 0 ){
                  parserelation[ct] = relation[n];
                  //extduplicate[superindex][superindex1] = 1;
                  superindex1 = superindex1 + i + 1;
                  if( extduplicate[i][i+1] == 0)
                    //extduplicate[superindex][superindex1] = 1;
                 // superindex = superindex + i;
                  superindex++;
                  //System.out.println("superindex="+superindex+"   superindex1 ="+(superindex1 + i));
                  //ct++;
                  //System.out.println("Vibhakti exist and Every thing is main: i = "+i+"  parserelation["+ct+"] ="+parserelation[ct]);
                  ct++;
                }
                else{
                  available = 1;
                  if(i==0){
                    //System.out.println("danger");
                    parserelation[ct] = relation[n];
                    //extduplicate[superindex][superindex1] = 1;    // 0 indicates verb position
                    //ct++;
                    superindex1 = superindex1 + i + 1;
                    //extduplicate[superindex][superindex1] = 1;
                   // superindex = superindex + i;
                    superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 ="+(superindex1 + i));
                    //ct++;
                    //System.out.println("Vibhakti exist  main1: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                    ct++;
                  
                  }
                  else{
                    //extduplicate[superindex][superindex1] = 1;
                    parserelation[ct] = relation[n];
                    superindex1 = superindex1 + i + 1;
                    //ct++;
                    //extduplicate[superindex][superindex1] = 1;
                    //ct++;
                    //System.out.println("Vibhakti exist  main2: and i="+i);
                   // superindex = superindex + i+swvibhakti;
                    superindex++;
                  //System.out.println("superindex="+superindex);
                    ct++;
                  }
                  for(int l = 2 ; l <= swvibhakti ; l++){
                    if(i==0){
                      //System.out.println("danger 3");
                      //extduplicate[superindex][superindex1] = 1;
                      superindex1 = superindex1 + i + 1;
                      //extduplicate[superindex][superindex1] = 1;
                      parserelation[ct] = "Oth";
                      //System.out.println("Vibhakti exist  i = 0 and other 1:");
                     // superindex = superindex + i + l-1;
                      superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 ="+(superindex1 + i));
                      ct++;
                    }
                    else{
                      //extduplicate[superindex][superindex1] = 1;
                      superindex1 = superindex1 + i + 1;
                      //extduplicate[superindex][superindex1] = 1;
                      parserelation[ct] = "Oth";
                      //System.out.println("Vibhakti exist  i != 0 and other 2:");
                     // superindex = superindex + i + swvibhakti + l - 1;
                      superindex++;
                  //System.out.println("superindex="+superindex+"  superindex1 = "+(superindex1 + i));
                      ct++;
                    }
                  /*if(i==0 && l < swvibhakti){
                    //extduplicate[i][i+l] = 1;    // for noun1 position
                    parserelation[ct] = "Oth";
                  }
                  else{
                    //extduplicate[i+swvibhakti][i+swvibhakti+1] = 1;
                    parserelation[ct] = "Oth";                    
                  }
                  if(l != swvibhakti ){
                    //extduplicate[i+1][i+1] = 1;   // for noun1
                    extduplicate[l+i][i+l+1] = 1; 
                    parserelation[ct] = "Oth";                    
                  }*/
                }
              }
                  
                
                }
            
            }
          }
        //}
        ////System.out.println("output string: " + Arrays.toString(currencies));
    
        //sw = RegionMatchesDemo(ws,np[i]);
        //System.out.println("Parsed Relation");
        ct1=0;
        try{
          for(int j=0;j<parserelation.length;j++){
            //System.out.println("  "+parserelation[j]);
            if(parserelation[j].equals(null)){
              //ct1++;
              break;
            }
            else{
              ct1++;;
            }
            //tempstr = parserelation;
          }
        } 
        catch(NullPointerException nullPointer){}
        //System.out.println("i="+i+"   oldswlen="+oldswlen+"    sw.len="+sw.length);
        if(i == 0){
      oldswlen = sw.length;
      oldswvibhakti = swvibhakti;
    }
    else{
      oldswlen = sw.length + oldswlen  ;
      //System.out.println("oldswlen="+oldswlen+"    i="+i+"    sw.len="+sw.length);
    }
        //if(i >= 0)
        //  oldswlen = oldswlen + sw.length;
        //else
        //  oldswlen = sw.length;
        oldswvibhakti = swvibhakti;
        
      }
     // novertices = novertices+vb.length
     // superindex111++;
      for(int j = 1 ; j < vb.length;j++){
          
          words[count1] = vb[j];
          //System.out.println("input string words separated by whitespace: " + vb[j] +"    "+vb[j]); 
          tags[count2] = "VAUX";
          count2++;
          count1++;
        }
      superindex11++;
            // Verb chunk
      //System.out.println("Verb intrachunk vb.length ="+vb.length);
     // for(int v = 0 ; v < vb.length; v++){
        extduplicate[0][superindex111] = 1;
        for(int l = 1 ; l < (vb.length) ; l++){
                    //System.out.println("superindex111="+superindex111+"   superindex11="+superindex11+"  sw.length="+sw.length);
                  extduplicate[superindex11][0] = 1;
                  superindex111 = superindex111 + 1;
                  extduplicate[superindex11][superindex111] = 1;
                  superindex11++;
                 // oldswlen = sw.length;
                  //System.out.println("123 superindex111="+superindex111+"   superindex11="+superindex11+"  oldswlen="+oldswlen);
                }
    //  }
      //int ct1 ;
      //System.out.println("npcount="+npcount+"   nllength="+nllength+"   oldparserelation length="+oldparserelation.length);
      //tempstr = parserelation;
      for(int k = 0 ; k < ct1 ; k++){
        //if(nllength == 0){
          oldparserelation1[nllength][k] = parserelation[k];
          
    //}
    //System.out.println(" *** old rel nlcount="+nllength+"   oldparserelation1["+nllength+"]["+k+"]="+oldparserelation1[nllength][k]);
  }
  //if(nllength == 0)
    final String[] tpst = oldparserelation1[0];
      try{
        if(nllength == 0){
          //int ct1 = 0;
          
          for(int k = 0 ; k < oldparserelation.length ; k++){
            System.out.println("  parserelation["+k+"]="+parserelation[k]+"   tpst["+k+"]="+tpst[k]);
            oldparserelation[k] = parserelation[k];
          }
          //oldparserelation = parserelation;
          for(int j = 0 ; j < oldparserelation.length ; j++){
            if(!oldparserelation[j].equals(null))
              //System.out.println("  old rel npcount="+npcount+"   oldparserelation1["+nllength+"]["+j+"]="+oldparserelation1[nllength][j]);
              ct2++;
          
      }
      oldparserelation = oldparserelation1[0];
          //ct1++;
    }
    else{
      //System.out.println("  final parse relation novertices="+novertices+"  ct1="+ct1+"   ct2="+ct2);
//      parserelation[ct1]=tempstr;
      /*for(int l = 0 ; l < novertices ; l++){
        //System.out.println(" too much hi "+l+" "+tempstr[l]);
        parserelation[l+ct1] = tempstr[l];
        //System.out.println(" hi "+(l+ct1)+" "+parserelation[l+ct1]);
      }*/
      //tempstr = getoldrelation();
      for(int i = 0 ; i < ct2 ; i++){
        //parserelation[i+ct1] = tempstr[i];
        parserelation[i+ct1] = tpst[i];
        //parserelation[i+ct1] = oldparserelation1[nllength-1][i];
        //System.out.println("  old rel npcount="+npcount+"   oldparserelation1["+(nllength - 1)+"]["+i+"]="+oldparserelation1[nllength-1][i]+"  tpst["+i+"]="+tpst[i]);
        System.out.println(" "+(i+ct1)+" "+parserelation[i+ct1]);
      }
    }
  }
  catch(NullPointerException nullPointer){}
  oldparserelation = oldparserelation1[0];
      String[] tempt = new String[tags.length];
      //tempt = tags;
      words[0] = vb[0];
      for(int i = 0 ; i < novertices ; i++){
        tempt[i+1] = tags[i];

  }
      tempt[0] = "VF";
      //System.out.println("word[0]"+words[0]);
      //npwords[npcount] = words;
  //nptags[npcount] = tags;
  //noedges = swcount+1;
      for(int s = 0 ; s < novertices;s++){
//        npwords[npcount][s] = words[s];
        //System.out.println("sss TNP["+s+"]="+tags[s]+"    words["+s+"]="+words[s]);
        
        //System.out.println("sss TNP["+s+"]="+tags[s]+"  npwd["+npcount+"]["+s+"]="+npwords[npcount][s]+"  nptgs["+npcount+"]["+s+"]="+nptags[npcount][s]);
        
        
        
      }
      //noedges = 0;
      int flg = 0;
      String[] temp = new String[30];
   int noedges1 = 0;
       for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          if(extduplicate[m1][n1] == 1){
              noedges1++;
          }
    }
  }
  
  //System.out.println("early intrachunk duplicate");
      for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          //System.out.print("   "+extduplicate[m1][n1]);
        }
    //System.out.println();
  }

  noedges1 = (noedges1 / 2)+1 ;
  //System.out.println("noedges1 ="+noedges1);
  int[][] newextdup = new int[noedges1][novertices];
  int cntr = 0;
  int index1 = 100;
  int rw1 =0;
  //System.out.println("new extduplicate");
  for(int m1 = 0 ; m1 < extduplicate.length;m1++){
    cntr = 0;
    for(int n1 = 0 ; n1 < novertices;n1++){
          if(extduplicate[m1][n1] == 0){
            cntr++;
            if(cntr == novertices){
              index1 = m1;

            }

           }
    }
    if(m1 != index1){

      newextdup[rw1] = extduplicate[m1];
      //System.out.println("newextdup rw1="+rw1+"   m1="+m1+"  index1="+index1);
      rw1++;
    }
  }

      
      //System.out.println("intrachunk duplicate");
      for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          //System.out.print("   "+extduplicate[m1][n1]);
        }
    //System.out.println();
  }

      //System.out.println("intrachunk duplicate new");
      for(int m1 = 0 ; m1 < noedges1;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          //System.out.print("   "+newextdup[m1][n1]);
        }
    //System.out.println();
  }

  /*if(npcount == 0)
    npextduplicate[npcount] = extduplicate;*/
  int norows = 0 , nocols = 0;
  //System.out.println("earlier matrix npcount="+npcount);
  //if(npcount == 0){
       // for(int a = 0 ; a < 15 ; a++){
         // for(int b = 0 ; b < 16 ; b++){
            //npextduplicate[0][a][b] = extduplicate[a][b];
            //System.out.print("   "+npextduplicate[a][b]);
         // }
          //System.out.println();
        //}
  //}
  /*for(int i = 0 ; i < npextduplicate.length ; i++){
    norows =  norows  + npextduplicate[i].length;
    nocols = nocols + npextduplicate[i][0].length;
    //System.out.println("npfinal vertices="+novertices+"    npfinal edges="+noedges);
  }*/
  
  //npcount++;
  
  //int count = npcount;
  int dummyindex = 0;
  npwords[npcount] = words;
  nptags[npcount] = tags;
  nlrows = nlrows + noedges ;
  nlcols = nlcols + novertices;
  //System.out.println("npcount="+npcount+"  nllength="+nllength+"  nlrows="+nlrows+"  nlcols="+nlcols+"    npwords["+1+"][0]="+npwords[1][0]);
  if(npcount > 0){
        for(int i = 0 ; i < (novertices-1) ; i++){
          if(flg == 0  && npwords[npcount][i].equals("dummy")){
            npwords[npcount][i]  = npwords[npcount-1][0] ;
            dummyindex = i;
            //System.out.println("dummyindex="+dummyindex);
            //nptags[npcount][i] = "VMOD";
            flg = 1;
          }
          if(flg == 1){
            //System.out.println("**sss TNP["+i+"]="+tags[i]+"  npwd["+npcount+"]["+i+"]="+npwords[npcount][i]+"  nptgs["+npcount+"]["+i+"]="+nptags[npcount][i]);
          }
        
        }
      }
      if(npcount != 0){
        for(int i = 0 ; i < (novertices-1) ; i++){
          if(dummyindex == i){
            //nptags[npcount][i+1] = nptags[npcount][i]; 
          }
          
        }
        //System.out.println("nptags["+npcount+"]["+dummyindex+"]="+nptags[npcount][dummyindex]);
        nptags[npcount][dummyindex-1] = "VMOD";
      }
      
      //System.out.println("earlier old matrix ");
      for(int a = 0 ; a < nlrows ; a++){
        for(int b = 0 ; b < nlcols ; b++){
          //System.out.print("   "+npextduplicate[a][b]);
        }
        //System.out.println();
      }
      int tpcount = 0;

     // nlcols = noedges1;
  if(npcount != 0){
    //words = npwords
    for(int k = npcount ; k >= 0 ; k--){
      ////System.out.println("  npcount"+npcount+"   npextduplength="+npextduplicate.length+"    npext["+k+"]="+npextduplicate[k].length+"  npext["+k+"][0]"+npextduplicate[k][0].length);
      for(int i = 0 ; i < nlrows ; i++){
      tpcount=0;
        for(int j = 0 ; j < nlcols ; j++){
        //while(count >= 0){
          if(k == npcount){
            npfinal[i][j] = extduplicate[i][j];
            /*if(i==0){
              words[j] = npwords[k][j];
              tags[j] = nptags[k][j];
            }*/
          }
          else{
            if(tpcount == 0){
              npfinal[i+noedges][j+dummyindex] = npextduplicate[i][j];
              tpcount = 1;
              //System.out.println("npextduplicate["+k+"]["+i+"]["+j+"]="+npextduplicate[i][j]);
            }
            else{
              //System.out.println("else npextduplicate["+i+"]["+j+"]="+npextduplicate[i][j]);
              npfinal[i+noedges][j+novertices-1] = npextduplicate[i][j];
            }
            /*if(i == 0){
              words[j+novertices-1] = npwords[k][j];
              tags[j+novertices - 1] = nptags[k][j];
            }*/
          }
        //}
        }
  
      }
    }
  }
  //System.out.println("NPFINAL matrix: ");
  for(int i = 0 ; i < nlrows;i++){
    for(int j = 0 ; j < nlcols;j++){
      //System.out.print("   "+npfinal[i][j]);
    }
    //System.out.println();
  }
  for(int i = 0 ; i < words.length;i++){
    //for(int j = 0 ; j < nlcols;j++){
      //System.out.print("   words["+i+"]="+words[i]+"   tags["+i+"]"+tags[i]+"   novertices="+novertices);
    //}
    //System.out.println();
  }
  String[] finalwords = new String[30];
  String[] finaltags = new String[30];  
  //else{
  if(npcount == 1){
    
    for(int k = npcount ; k>= 0; k--){
      for(int j = 0 ; j < nlcols;j++){
        if(k == npcount){
          finalwords[j] = npwords[k][j];
          finaltags[j+1] = nptags[k][j];
          //System.out.println("  finalwords["+j+"]="+finalwords[j]+"   finaltags["+j+"]="+finaltags[j]);
        }
        else{
          if(j != 0){
            finalwords[j+novertices-1] = npwords[k][j];
            finaltags[j+novertices-1 ] = nptags[k][j-1];
            //System.out.println(" else  finalwords["+(j+novertices-1)+"]="+finalwords[(j+novertices-1)]+"   finaltags["+(j+novertices-1)+"]="+finaltags[(j+novertices-1)]);
            //System.out.println(" npwords["+k+"]["+j+"]="+npwords[k][j]+"nptags["+k+"]["+(j-1)+"]="+nptags[k][j-1]);
          }
          
        }
      }
    }
  }
  /*String[] temptags = new String[tags.length+1];
  for(int i = 0 ; i < (tags.length-1) ; i++){
        temptags[i+1] = finaltags[i];

  }
  temptags[0] = "VF";*/
  String[] vfinaltags = new String[finaltags.length-1];
  for(int i = 0 ; i < vfinaltags.length ; i++){
    vfinaltags[i] = finaltags[i+1];
  }
  for(int i = 0 ; i <= npcount;i++){
    for(int j = 0 ; j < nptags[i].length ; j++){
      System.out.println("   nptags["+i+"]["+j+"]="+nptags[i][j]+"  nlrows="+nlrows+"   nlcols="+nlcols);
    }
  }
  
  for(int i = 0 ; i < nlcols;i++){
    //System.out.println("sangexist ="+sangexist);
    if(sangexist == 1 ){
                    //parserelation[0] = relation[1];
                 }
    System.out.println("  finalwords["+i+"]="+finalwords[i]+"   vfinaltags["+i+"]="+vfinaltags[i]+"  parserelation["+i+"]="+parserelation[i]);
  }
  int index = 0;
  int m1 = 0;
  try{
  OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true), "UTF-8");
      BufferedWriter fout = new BufferedWriter(writer);
      fout.write("\n");
      fout.write("******************   Parsing Output  ********************************");
      fout.write("\n");
      fout.write(" Sentence :");
     /* for(int s = 0 ; s < words.length; s++){
        
        fout.write("  "+words[s]);
      }*/
      fout.write("\n");
  for(int n1 = 0 ; n1 < nlcols;n1++){
       //   if(extduplicate[m1][n1] == 1){
              if(n1 == 0){
                  //System.out.println(words[n1]+"  is Verb");
                  fout.write(words[n1]+"  is Verb");
                  fout.write("\n");
              }
              else{
                  //System.out.println("Part of Speech of "+words[n1]+" is "+tags[n1-1]);
                  for(int i = 0 ; i < tagset.length ; i++){
                    if(tags[n1-1].equals(tagset[i])){
                        index = i;
                    }
                    if(tags[n1-1].equals("NN1")){
                        index = 6;    // NN1 refers to JJ
                    }
                  }
                  fout.write("Part of Speech of "+words[n1]+" is "+postagset[index]);
                  fout.write("\n");
                  try{
                    if(parserelation[n1].equals("Oth")) parserelation[n1] = "Chunk with "+words[n1];
                  }
                  catch(NullPointerException ne){};   
                  try{
                  if(!parserelation[n1-1].equals("next")){
                      System.out.println("Relation of  "+words[n1]+" with verb is "+ parserelation[n1-1]);
                      fout.write("Relation of  "+words[n1]+" with verb is "+ parserelation[n1-1]);
                      fout.write("\n");
                  }
                  else{
                      //System.out.println("Relation of  "+words[n1]+" with verb is a chunk with "+ words[n1-1]);
                      fout.write("Relation of  "+words[n1]+" with verb is a chunk with "+ words[n1-1]);
                      fout.write("\n");

                  }
                }
                  catch(NullPointerException ne1){};
              }
         
        //  }
         // m1++;
      }
      fout.close();
    }
    catch(IOException ie){}
    finally { 
         // fout.close();
  }
  //if(npcou == 0)
    
  //}
  
  if(npcount == 0 && nllength == 0){
    //final String[] tpst = oldparserelation ;
    //oldparserelation1[0] = parserelation;
    try{
    pn1.main1(newextdup,noedges1,novertices,words,parserelation, tags, filename1, out, sent);
    }
    catch(Exception e){};
    //pn1.save();
   /* try{
    pc.main1(extduplicate,noedges,novertices,words,parserelation, tags, filename);
  }
  catch(Exception e){}*/
  }
  if(npcount == 1){
    //pn1.main1(extduplicate,noedges,novertices,words,parserelation, tags);
    //pn1.main1(npfinal,nlrows,nlcols,finalwords,parserelation, vfinaltags);
    //System.out.println("  stop ");
    //pc.main1();
    try{
    pn2.main2(npfinal,nlrows,nlcols,finalwords,parserelation, finaltags, pn1, filename1, out, sent);
    }
    catch(Exception e){}; 
    //pn2.save(); 
  }
  if(npcount < nllength){
    npcount++;
  }
  //}
      
      return extduplicate;
    }
    
    
    public int[][] intrachunk1(String[] np, String[] tnp, int[][] duplicateparse, int ln, String[] vb, int[][] npextduplicate, String filename, String out, String[] sent){
      
      
    //  int[][] npfinal = new int[15][16];
      
      PaintNodes2 pn1 = new PaintNodes2();
      PaintNodes2 pn2 = new PaintNodes2();      
      String ws = " ";
      String[] sw;
      String[] words = new String[30];
      //words[0] = "VERB";
      String[] tsw;
      String[] tags = new String[30];
      String[] tempstr = new String[10];
      for(int i = 0 ; i < tags.length;i++){
        tags[i] = "tag";
      }
      int novertices = 1; // Verb is the root
      // ORDER IS 1. VERB     2. NOUN1     3. NOUN2      4. NOUN3
      int noedges = 0;
      int flag = 0;
      int count = 0 ;
      int count1 = 1;
      int count2 = 0;
      int ct = 0;
      //System.out.println("length intrachunk1 ="+ln);
      sw = np;
      tsw = tnp;  
      int available = 0;
      int oldswlen = 0;
      int oldswvibhakti = 0;
      ct1=0;
      int oldi = 0;
//      int fugee = 0;
      String[] relation = {"Karta(K1)","Kama(K2)", "Karana(K3)", "Sampradan(K4)", "Apadan(K5)","Sambandh(K6)","Adhikaran(K7)","Location(K2p)","Location(K7t)","Second Karta(K1s)"};
      String[] parserelation = new String[10];
      int ftk = 0;
      
      
      //sw = np;
    //  extduplicate = duplicateparse;
      for(int i=0; i < ln ; i++){
        if(i > 0){
          oldi = i-1;
  //        fugee = 1;
        }
        //System.out.println("np["+i+"]="+np[i]);
        //System.out.println("tnp["+i+"]="+tnp[i]);    
        try{
          sw = np[i].trim().split("\\s+"); 
          tsw = tnp[i].trim().split("\\s+");
        }
        catch(NullPointerException nullPointer){
            //System.out.println(" THERE IS NO CHUNK");
            //sw[0] = "null1";
        //tsw[0] = "null1";
    }
        if(sw[0].equals("")){
          //System.out.println("go back");
          flag = 1;
        }
        if(flag == 1){
          novertices = novertices - 1;
        }
        int idx = 0;
        for(int j = 0 ; j < sw.length;j++){
          words[count1] = sw[j];
          //System.out.println("input string words separated by whitespace: " + sw[j] +"    "+tsw[j]); 
          /*if(tsw[j].equals("PSPLAI") ){
            idx = j;
            //System.out.println("idx="+idx);
          }*/
          tags[count2] = tsw[j];
          count2++;
          if(tsw[j].equals("PSPLE") || tsw[j].equals("PSPLAI") || tsw[j].equals("PSPO") ){
            if(j==1 && j > sw.length){
              count1 = count1;
              flag = 2;
              
            }
            //System.out.println("j="+j);
            
          }
          else{
            count1++;
            flag = 0;
          }
        }
        novertices = novertices + sw.length;
        /*if(flag == 2){
          novertices = novertices - 1;
        }*/
        noedges = novertices - 1;
        int swvibhakti = 0;
        if(flag == 2){
          novertices = novertices - 1;
          swvibhakti = sw.length - 1;
        }
        //if(novertices > t)
      //    extduplicate = new int[noedges][novertices];
    if(i == 0){
      oldswlen = sw.length;
      oldswvibhakti = swvibhakti;
    }
    //t=novertices;
  //}
    //System.out.println();
        //System.out.println("No of vertices ="+novertices+"  i="+i+"    sw.length="+sw.length+"   duplicateparse[0]="+duplicateparse[0]);
        //for(int m = 0 ; m < novertices;m++){
          for(int n = 0 ; n < duplicateparse[0].length;n++){
            //System.out.print("   "+duplicateparse[i][n]);
            if(duplicateparse[i][n] == 1){
              if(flag != 2){
                if(sw.length == 1 && available == 0 ){
                  parserelation[ct] = relation[n];
                  extduplicate[i][0] = 1;
                  if( extduplicate[i][i+1] == 0)
                    extduplicate[i][i+1] = 1;
                  
                  //System.out.println("Every thing is main: i = "+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  relation n="+n );
                  ct++;
                }
                else{
                  available = 1;
                  if(i==0){
                    parserelation[ct] = relation[n];
                    extduplicate[i][0] = 1;    // 0 indicates verb position
                    //ct++;
                    extduplicate[i][i+1] = 1;
                    //ct++;
                    //System.out.println("main1: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                    ct++;
                  
                  }
                  else{
                    
                    //System.out.println("extdupliate["+(i+oldswlen-1)+"]["+0+"]="+extduplicate[i+oldswlen-1][0]);

                    parserelation[ct] = relation[n];
                    //ct++;
                    //System.out.println("tsw["+0+"]="+tsw[0]+"  ftk="+ftk+"  oldswlen="+oldswlen+"   i="+i);
                    if((tsw[0].equals("JJ") && ftk == 0)) {
                      //oldswlen = oldswlen + 1;
                      extduplicate[i+oldswlen-1][0] = 1;
                      extduplicate[i+oldswlen-1][i+oldswlen-1+1+1] = 1;
                      //System.out.println("JJ extdupliate["+(i+oldswlen-1)+"]["+(0)+"]="+extduplicate[i+oldswlen-1][0]);
                      //System.out.println("JJ extdupliate["+(i+oldswlen-1)+"]["+(i+oldswlen-1+1+1)+"]="+extduplicate[i+oldswlen-1][i+oldswlen-1+1+1]);
                      
                      ftk = i+oldswlen-1+1+1;
                      //System.out.println("JJ ftk="+ftk);
                    }
                    else if(ftk != 0){
                      extduplicate[ftk+1][0] = 1;
                      extduplicate[ftk+1][i+oldswlen-1+1+1] = 1;
                      //System.out.println("JJ extdupliate["+(ftk+1)+"]["+(0)+"]="+extduplicate[ftk+1][0]);
                      //System.out.println("JJ extdupliate["+(ftk+1)+"]["+(i+oldswlen-1+1+1)+"]="+extduplicate[ftk+1][i+oldswlen-1+1+1]);
                    }
                    else if((tsw[0].equals("QTC") && ftk == 0) || (tsw[0].equals("QC") && ftk == 0) || (tsw[0].equals("QF") && ftk == 0)){
                      if(i == 1){
                        extduplicate[i+oldswlen-1][0] = 1;
                        extduplicate[i+oldswlen-1][i+oldswlen-1+1+1] = 1;
                        //System.out.println("JJ extdupliate["+(i+oldswlen-1)+"]["+(0)+"]="+extduplicate[i+oldswlen-1][0]);
                        //System.out.println("JJ extdupliate["+(i+oldswlen-1)+"]["+(i+oldswlen-1+1+1)+"]="+extduplicate[i+oldswlen-1][i+oldswlen-1+1+1]);
                      
                        ftk = i+oldswlen-1+1+1;
                        //System.out.println("QTC ftk="+ftk);
                      }
                      else{
                        ftk = i+oldswlen-1+1+1;
                        extduplicate[ftk-1][0] = 1;
                        extduplicate[ftk-1][ftk] = 1;
                        //System.out.println("else QTC ftk="+ftk);
                      }
                    }
                    else{
                      extduplicate[i+oldswlen-1][0] = 1;
                      extduplicate[i+oldswlen-1][i+oldswlen-1+1] = 1;
                      //System.out.println("no JJ extdupliate["+(i+oldswlen-1)+"]["+(0)+"]="+extduplicate[i+oldswlen-1][0]);
                      //System.out.println("no JJ extdupliate["+(i+oldswlen-1)+"]["+(i+oldswlen-1+1)+"]="+extduplicate[i+oldswlen-1][i+oldswlen-1+1]);
                      
                      ftk = 0;
                    }
                    //extduplicate[i+oldswlen-1][i+oldswlen-1+1] = 1;
                    //System.out.println("extdupliate["+(i+oldswlen-1)+"]["+(i+oldswlen-1+1)+"]="+extduplicate[i+oldswlen-1][i+oldswlen-1+1]);
                    
                    //ct++;
                    //System.out.println("main2: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  sw.length="+sw.length+" oldswlen="+oldswlen );
                    ct++;
                  }
                  //int ftk = 0;
                  for(int l = 2 ; l <= (sw.length) ; l++){
                    //System.out.println("l="+l);
                    if(i==0){
                      extduplicate[i+(l-1)][i+l-1] = 1;
                      extduplicate[i+(l-1)][i+l] = 1;
                      //System.out.println("extdupliate["+(i+(l-1))+"]["+(i+l-1)+"]="+extduplicate[i+(l-1)][i+l-1]);
                      //System.out.println("extdupliate["+(i+(l-1))+"]["+(i+l)+"]="+extduplicate[i+(l-1)][i+l]);
                      parserelation[ct] = "Oth";
                      //System.out.println("i = 0 and other 1:"+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                      ct++;
                    }
                    else{
                      if(ftk != 0){
                        //oldswlen = oldswlen + 1;
                        
                        
                        if(l == 2 ){
                          extduplicate[ftk-1 ][ftk] = 1;
                          extduplicate[ftk-1][ftk-1] = 1;
                          //System.out.println("if JJ extdupliate["+(ftk-1)+"]["+(ftk)+"]="+extduplicate[ftk-1][ftk]);
                          //System.out.println("if JJ extdupliate["+(ftk-1)+"]["+(ftk-1)+"]="+extduplicate[ftk-1][ftk-1]);
                        }
                        if(l == 3 || sw.length == 2){
                          extduplicate[ftk][ftk] = 1;
                          extduplicate[ftk][ftk+1] = 1;
                          //System.out.println("if JJ extdupliate["+ftk+"]["+(ftk)+"]="+extduplicate[ftk][ftk]);
                          //System.out.println("if JJ extdupliate["+ftk+"]["+(ftk+1)+"]="+extduplicate[ftk][ftk+1]);
                        }
                          
                      //  ftk = 0;
                      }
                      else{
                        extduplicate[i+oldswlen-1+(l-1)][i+oldswlen-1+l-1] = 1;
                        extduplicate[i+oldswlen-1+(l-1)][i+oldswlen-1+l] = 1;
                        //System.out.println("else extdupliate["+(i+oldswlen-1+(l-1))+"]["+(i+oldswlen-1+l-1)+"]="+extduplicate[i+oldswlen-1+(l-1)][i+oldswlen-1+l-1]);
                        //System.out.println("else extdupliate["+(i+oldswlen-1+(l-1))+"]["+(i+oldswlen-1+l)+"]="+extduplicate[i+oldswlen-1+(l-1)][i+oldswlen-1+l]);
                        ftk = 0;
                      }
                      
                      
                      ////System.out.println("i != 0 and other 2:");
                      parserelation[ct] = "Oth";
                      //System.out.println("i != 0 and other 2:"+i+"  parserelation["+ct+"] ="+parserelation[ct]+"  sw.length="+sw.length+" oldswlen="+oldswlen  );
                      ct++;
                    }
                  /*if(i==0 && l < sw.length){
                    extduplicate[i][i+l] = 1;    // for noun1 position
                    parserelation[ct] = "Oth";
                  }
                  else{
                    extduplicate[i+sw.length][i+sw.length+1] = 1;
                    parserelation[ct] = "Oth";                    
                  }
                  if(l != sw.length ){
                    extduplicate[i+1][i+1] = 1;   // for noun1
                    extduplicate[l+i][i+l+1] = 1; 
                    parserelation[ct] = "Oth";                    
                  }*/
                }
              }
                }
                else{
                    flag = 0;
                    if(swvibhakti == 1 && available == 0 ){
                  parserelation[ct] = relation[n];
                  extduplicate[i][0] = 1;
                  if( extduplicate[i][i+1] == 0)
                    extduplicate[i][i+1] = 1;
                  //ct++;
                  //System.out.println("Vibhakti exist and Every thing is main: i = "+i+"  parserelation["+ct+"] ="+parserelation[ct]);
                  ct++;
                }
                else{
                  available = 1;
                  if(i==0){
                    parserelation[ct] = relation[n];
                    extduplicate[i][0] = 1;    // 0 indicates verb position
                    //ct++;
                    extduplicate[i][i+1] = 1;
                    //ct++;
                    //System.out.println("Vibhakti exist  main1: and i="+i+"  parserelation["+ct+"] ="+parserelation[ct] );
                    ct++;
                  
                  }
                  else{
                    extduplicate[i+swvibhakti][0] = 1;
                    parserelation[ct] = relation[n];
                    //ct++;
                    extduplicate[i+swvibhakti][i+swvibhakti+1] = 1;
                    //ct++;
                    //System.out.println("Vibhakti exist  main2: and i="+i);
                    ct++;
                  }
                  for(int l = 2 ; l <= swvibhakti ; l++){
                    if(i==0){
                      extduplicate[i+(l-1)][i+l-1] = 1;
                      extduplicate[i+(l-1)][i+l] = 1;
                      parserelation[ct] = "Oth";
                      //System.out.println("Vibhakti exist  i = 0 and other 1:");
                      ct++;
                    }
                    else{
                      extduplicate[i+swvibhakti+(l-1)][i+swvibhakti+l-1] = 1;
                      extduplicate[i+swvibhakti+(l-1)][i+swvibhakti+l] = 1;
                      parserelation[ct] = "Oth";
                      //System.out.println("Vibhakti exist  i != 0 and other 2:");
                      ct++;
                    }
                  /*if(i==0 && l < swvibhakti){
                    extduplicate[i][i+l] = 1;    // for noun1 position
                    parserelation[ct] = "Oth";
                  }
                  else{
                    extduplicate[i+swvibhakti][i+swvibhakti+1] = 1;
                    parserelation[ct] = "Oth";                    
                  }
                  if(l != swvibhakti ){
                    extduplicate[i+1][i+1] = 1;   // for noun1
                    extduplicate[l+i][i+l+1] = 1; 
                    parserelation[ct] = "Oth";                    
                  }*/
                }
              }
                  
                
                }
            
            }
          }
        //}
        ////System.out.println("output string: " + Arrays.toString(currencies));
    
        //sw = RegionMatchesDemo(ws,np[i]);
        //System.out.println("Parsed Relation");
        ct1=0;
        try{
          for(int j=0;j<parserelation.length;j++){
            //System.out.println("  "+parserelation[j]);
            if(parserelation[j].equals(null)){
              //ct1++;
              break;
            }
            else{
              ct1++;;
            }
            //tempstr = parserelation;
          }
        } 
        catch(NullPointerException nullPointer){}
        oldswlen = sw.length;
        oldswvibhakti = swvibhakti;
        
      }
      //int ct1 ;
      //System.out.println("npcount="+npcount+"   nllength="+nllength+"   oldparserelation length="+oldparserelation.length);
      //tempstr = parserelation;
      for(int k = 0 ; k < ct1 ; k++){
        //if(nllength == 0){
          oldparserelation1[nllength][k] = parserelation[k];
          
    //}
    //System.out.println(" *** old rel nlcount="+nllength+"   oldparserelation1["+nllength+"]["+k+"]="+oldparserelation1[nllength][k]);
  }
  //if(nllength == 0)
    final String[] tpst = oldparserelation1[0];
      try{
        if(nllength == 0){
          //int ct1 = 0;
          
          for(int k = 0 ; k < oldparserelation.length ; k++){
            //System.out.println("  parserelation["+k+"]="+parserelation[k]+"   tpst["+k+"]="+tpst[k]);
            oldparserelation[k] = parserelation[k];
          }
          //oldparserelation = parserelation;
          for(int j = 0 ; j < oldparserelation.length ; j++){
            if(!oldparserelation[j].equals(null))
              //System.out.println("  old rel npcount="+npcount+"   oldparserelation1["+nllength+"]["+j+"]="+oldparserelation1[nllength][j]);
              ct2++;
          
      }
      oldparserelation = oldparserelation1[0];
          //ct1++;
    }
    else{
      //System.out.println("  final parse relation novertices="+novertices+"  ct1="+ct1+"   ct2="+ct2);
//      parserelation[ct1]=tempstr;
      /*for(int l = 0 ; l < novertices ; l++){
        //System.out.println(" too much hi "+l+" "+tempstr[l]);
        parserelation[l+ct1] = tempstr[l];
        //System.out.println(" hi "+(l+ct1)+" "+parserelation[l+ct1]);
      }*/
      //tempstr = getoldrelation();
      for(int i = 0 ; i < ct2 ; i++){
        //parserelation[i+ct1] = tempstr[i];
        parserelation[i+ct1] = tpst[i];
        //parserelation[i+ct1] = oldparserelation1[nllength-1][i];
        //System.out.println("  old rel npcount="+npcount+"   oldparserelation1["+(nllength - 1)+"]["+i+"]="+oldparserelation1[nllength-1][i]+"  tpst["+i+"]="+tpst[i]);
        //System.out.println(" "+(i+ct1)+" "+parserelation[i+ct1]);
      }
    }
  }
  catch(NullPointerException nullPointer){}
  oldparserelation = oldparserelation1[0];
      String[] tempt = new String[tags.length];
      //tempt = tags;
      words[0] = vb[0];
      for(int i = 0 ; i < novertices ; i++){
        tempt[i+1] = tags[i];

  }
      tempt[0] = "VF";
      //System.out.println("word[0]"+words[0]);
      //npwords[npcount] = words;
  //nptags[npcount] = tags;
      for(int s = 0 ; s < novertices;s++){
//        npwords[npcount][s] = words[s];
        //System.out.println("sss TNP["+s+"]="+tags[s]+"    words["+s+"]="+words[s]);
    //    //System.out.println("noedges1 =");
        
        //System.out.println("sss TNP["+s+"]="+tags[s]+"  npwd["+npcount+"]["+s+"]="+npwords[npcount][s]+"  nptgs["+npcount+"]["+s+"]="+nptags[npcount][s]);
        
        
        
      }
    //  //System.out.println("noedges1 =");
      int flg = 0;
      String[] temp = new String[30];

       int noedges1 = 0;
       for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          if(extduplicate[m1][n1] == 1){
              noedges1++;
          }
    }
  }
  
  noedges1 = noedges1 / 2 ;
  //System.out.println("noedges1 ="+noedges1);
  int[][] newextdup = new int[noedges1][novertices];
  int cntr = 0;
  int index1 = 100;
  int rw1 =0;
  //System.out.println("new extduplicate");
  for(int m1 = 0 ; m1 < extduplicate.length;m1++){
    cntr = 0;
    for(int n1 = 0 ; n1 < novertices;n1++){
          if(extduplicate[m1][n1] == 0){
            cntr++;
            if(cntr == novertices){
              index1 = m1;

            }

           }
    }
    if(m1 != index1){

      newextdup[rw1] = extduplicate[m1];
      //System.out.println("newextdup rw1="+rw1+"   m1="+m1+"  index1="+index1);
      rw1++;
    }
  }
   
      
      //System.out.println("intrachunk duplicate");
    

   
      for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
          //System.out.print("   "+extduplicate[m1][n1]);
        }
    //System.out.println();
  }

  for(int m1 = 0 ; m1 < noedges;m1++){
    for(int n1 = 0 ; n1 < novertices;n1++){
         // if(extduplicate[m1][n1] == 1){


         // }
        }
    //System.out.println();
  }

  /*if(npcount == 0)
    npextduplicate[npcount] = extduplicate;*/
  int norows = 0 , nocols = 0;
  //System.out.println("earlier matrix npcount="+npcount);
  //if(npcount == 0){
        for(int a = 0 ; a < 15 ; a++){
          for(int b = 0 ; b < 16 ; b++){
            //npextduplicate[0][a][b] = extduplicate[a][b];
            //System.out.print("   "+npextduplicate[a][b]);
          }
          //System.out.println();
        }
  //}
  /*for(int i = 0 ; i < npextduplicate.length ; i++){
    norows =  norows  + npextduplicate[i].length;
    nocols = nocols + npextduplicate[i][0].length;
    //System.out.println("npfinal vertices="+novertices+"    npfinal edges="+noedges);
  }*/
  
  //npcount++;
  
  //int count = npcount;
  int dummyindex = 0;
  npwords[npcount] = words;
  nptags[npcount] = tags;
  nlrows = nlrows + noedges ;
  nlcols = nlcols + novertices;
  //System.out.println("npcount="+npcount+"  nllength="+nllength+"  nlrows="+nlrows+"  nlcols="+nlcols+"    npwords["+1+"][0]="+npwords[1][0]);
  if(npcount > 0){
        for(int i = 0 ; i < (novertices-1) ; i++){
          if(flg == 0  && npwords[npcount][i].equals("dummy")){
            npwords[npcount][i]  = npwords[npcount-1][0] ;
            dummyindex = i;
            //System.out.println("dummyindex="+dummyindex);
            //nptags[npcount][i] = "VMOD";
            flg = 1;
          }
          if(flg == 1){
            //System.out.println("**sss TNP["+i+"]="+tags[i]+"  npwd["+npcount+"]["+i+"]="+npwords[npcount][i]+"  nptgs["+npcount+"]["+i+"]="+nptags[npcount][i]);
          }
        
        }
      }
      if(npcount != 0){
        for(int i = 0 ; i < (novertices-1) ; i++){
          if(dummyindex == i){
            //nptags[npcount][i+1] = nptags[npcount][i]; 
          }
          
        }
        //System.out.println("nptags["+npcount+"]["+dummyindex+"]="+nptags[npcount][dummyindex]);
        nptags[npcount][dummyindex-1] = "VMOD";
      }
      
      //System.out.println("earlier old matrix ");
      for(int a = 0 ; a < nlrows ; a++){
        for(int b = 0 ; b < nlcols ; b++){
          //System.out.print("   "+npextduplicate[a][b]);
        }
        //System.out.println();
      }
      int tpcount = 0;
  if(npcount != 0){
    //words = npwords
    for(int k = npcount ; k >= 0 ; k--){
      ////System.out.println("  npcount"+npcount+"   npextduplength="+npextduplicate.length+"    npext["+k+"]="+npextduplicate[k].length+"  npext["+k+"][0]"+npextduplicate[k][0].length);
      for(int i = 0 ; i < nlrows ; i++){
      tpcount=0;
        for(int j = 0 ; j < nlcols ; j++){
        //while(count >= 0){
          if(k == npcount){
            npfinal[i][j] = extduplicate[i][j];
            /*if(i==0){
              words[j] = npwords[k][j];
              tags[j] = nptags[k][j];
            }*/
          }
          else{
            if(tpcount == 0){
              npfinal[i+noedges][j+dummyindex] = npextduplicate[i][j];
              tpcount = 1;
              //System.out.println("npextduplicate["+i+"]["+j+"]="+npextduplicate[i][j]);
            }
            else{
              //System.out.println("else npextduplicate["+k+"]["+i+"]["+j+"]="+npextduplicate[i][j]);
              npfinal[i+noedges][j+novertices-1] = npextduplicate[i][j];
            }
            /*if(i == 0){
              words[j+novertices-1] = npwords[k][j];
              tags[j+novertices - 1] = nptags[k][j];
            }*/
          }
        //}
        }
  
      }
    }
  }
  //System.out.println("NPFINAL matrix: ");
  for(int i = 0 ; i < nlrows;i++){
    for(int j = 0 ; j < nlcols;j++){
      //System.out.print("   "+npfinal[i][j]);
    }
    //System.out.println();
  }
  for(int i = 0 ; i < words.length;i++){
    //for(int j = 0 ; j < nlcols;j++){
      //System.out.print("   words["+i+"]="+words[i]+"   tags["+i+"]"+tags[i]+"   novertices="+novertices);
    //}
    //System.out.println();
  }
  String[] finalwords = new String[30];
  String[] finaltags = new String[30];  
  //else{
  if(npcount == 1){
    
    for(int k = npcount ; k>= 0; k--){
      for(int j = 0 ; j < nlcols;j++){
        if(k == npcount){
          finalwords[j] = npwords[k][j];
          finaltags[j+1] = nptags[k][j];
          //System.out.println("  finalwords["+j+"]="+finalwords[j]+"   finaltags["+j+"]="+finaltags[j]);
        }
        else{
          if(j != 0){
            finalwords[j+novertices-1] = npwords[k][j];
            finaltags[j+novertices-1 ] = nptags[k][j-1];
            //System.out.println(" else  finalwords["+(j+novertices-1)+"]="+finalwords[(j+novertices-1)]+"   finaltags["+(j+novertices-1)+"]="+finaltags[(j+novertices-1)]);
            //System.out.println(" npwords["+k+"]["+j+"]="+npwords[k][j]+"nptags["+k+"]["+(j-1)+"]="+nptags[k][j-1]);
          }
          
        }
      }
    }
  }
  /*String[] temptags = new String[tags.length+1];
  for(int i = 0 ; i < (tags.length-1) ; i++){
        temptags[i+1] = finaltags[i];

  }
  temptags[0] = "VF";*/
  String[] vfinaltags = new String[finaltags.length-1];
  for(int i = 0 ; i < vfinaltags.length ; i++){
    vfinaltags[i] = finaltags[i+1];
  }
  for(int i = 0 ; i <= npcount;i++){
    for(int j = 0 ; j < nptags[i].length ; j++){
      //System.out.println("   nptags["+i+"]["+j+"]="+nptags[i][j]+"  nlrows="+nlrows+"   nlcols="+nlcols);
    }
  }
  for(int i = 0 ; i < nlcols;i++){
    //System.out.println("  finalwords["+i+"]="+finalwords[i]+"   vfinaltags["+i+"]="+vfinaltags[i]+"  parserelation["+i+"]="+parserelation[i]);
  }
  int index= 0;
  try{
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, true), "UTF-8");
          BufferedWriter fout = new BufferedWriter(writer);
          
          fout.write("\n");
          fout.write(" ******************   Parsing Output  ********************************");
          fout.write("\n");
          fout.write(" Sentence :");
      for(int s = 0 ; s < sentwords.length /2; s++){
        
        fout.write("  "+sentwords[s]);
      }
      fout.write("\n");

  for(int n1 = 0 ; n1 < nlcols;n1++){
       //   if(extduplicate[m1][n1] == 1){
              if(n1 == 0){
                  //System.out.println(words[n1]+"  is Verb");
                  fout.write(words[n1]+"  is Verb");
                  fout.write("\n");
              }
              else{
                  //System.out.println("Part of Speech of "+words[n1]+" is "+tags[n1-1]);
                  for(int i = 0 ; i < tagset.length ; i++){
                    if(tags[n1-1].equals(tagset[i])){
                        index = i;
                    }
                    if(tags[n1-1].equals("NN1")){
                        index = 6;    // NN1 refers to JJ
                    }
                  }
                  fout.write("Part of Speech of "+words[n1]+" is "+postagset[index]);
                  fout.write("\n");
                  try{
                    if(parserelation[n1].equals("Oth")) parserelation[n1] = "Chunk with "+words[n1];
                  }
                  catch(NullPointerException ne){};   
                  try{
                  if(!parserelation[n1-1].equals("next")){
                      //System.out.println("Relation of  "+words[n1]+" with verb is "+ parserelation[n1-1]);
                      fout.write("Relation of  "+words[n1]+" with verb is "+ parserelation[n1-1]);
                      fout.write("\n");
                  }
                  else{
                      //System.out.println("Relation of  "+words[n1]+" with verb is a chunk with "+ words[n1-1]);
                      fout.write("Relation of  "+words[n1]+" with verb is a chunk with "+ words[n1-1]);
                      fout.write("\n");

                  }
                }
                  catch(NullPointerException ne1){};
              }
         
        //  }
         // m1++;
      }
      fout.close();
    }
    catch(IOException ie){}
    finally { 
         // fout.close();
  }
  //if(npcou == 0)
    
  //}
  
  if(npcount == 0){
    //final String[] tpst = oldparserelation ;
    //oldparserelation1[0] = parserelation;
    try{
    pn1.main1(extduplicate,noedges,novertices,words,parserelation, tags, filename, out, sent);
  }
  catch(Exception e){}
  }
  if(npcount == 1){
    //pn1.main1(extduplicate,noedges,novertices,words,parserelation, tags);
    //pn1.main1(npfinal,nlrows,nlcols,finalwords,parserelation, vfinaltags);
    //System.out.println(" NL stop ");
    try{
    pn2.main2(npfinal,nlrows,nlcols,finalwords,parserelation, finaltags, pn1, filename, out, sent);   
  }
  catch(Exception e){}
}
  if(npcount < nllength){
    npcount++;
  }
  //}
      
      return extduplicate;
    }
    
    public String[] getoldrelation(){
      //System.out.println("parseme");
      for(int i = 0 ; i < oldparserelation.length ; i++){
        //System.out.println("oldparserelation["+i+"]="+oldparserelation[i]);
      }
      return oldparserelation;
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
//            fout.write(nn[l]);
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
          //System.out.println("Hi its displayString");
          //System.out.println("number = "+number+"Vibhakti[0]="+vbkt[0]);
          boolean b = true;
          for(int i = 0 ; i < number ; i++){
            //System.out.println("s["+i+"]="+s[i]+"   s.length= "+s.length+"   number of nouns = "+number);
           // fout.write("s["+i+"]="+s[i]+"   s.length= "+s.length+"   number of nouns = "+number+"No of Vibhakti = "+noVbkt);
            //String a = s[i];
           // fout.write(s[i]);
            fout.newLine();
            String string1 = s[i];
          //  fout.write("String1 = "+string1);
            //b = string1.endsWith(vbkt[i]); 
            for(int l=0;l<noVbkt;l++){
              if(string1.contains(vbkt[l])){
                //System.out.println("Hi its a vibhakti section:  ");
                b = true;
                break;
              }
              else
                b = false;  
              
            }
            //String string1 = "Madam, I am Adam";
            
          /*  if( b){
              fout.newLine();
              fout.write(" ");
              //System.out.println("True");
              
              if(i != 0 ){
                //karana[i] = s[i];
                indexkarana = i;
                fout.write("karana["+i+"]="+karana[i]+" indexkrana = "+indexkarana);
                //System.out.println("karana["+i+"]="+karana[i]+" indexkrana = "+indexkarana);
                fout.newLine();
              }
              else{
                indexkarta = i;
                
                //karta[i] = s[i];
                fout.write(" indexkarta = "+indexkarta);
                //System.out.println("indexkarta="+indexkarta);
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
          //System.out.println("HI ITS A VERB SECTION");
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
        try{
        for(int i = 0; i < noWords/2 ; i++){
          //f1.displayString(ms[count]);
          for(int j=0;j<6;j++){  // No of verb tags = 5
            if(tgs[i].equals(wd[j])){
              if (count < 3){
              //if(count > 0  && wd[j].equals("VBX")){    
                //System.out.println("tgs["+i+"]="+tgs[i]+"   wd["+j+"]="+wd[j]);
                y[count] = ms[i];
                verbPosition[count] = i;
                //y[count] = vbs[i];
                //count++;
                ////System.out.println("Count="+count);
                //System.out.println("Count="+count+"   verb position["+count+"]="+verbPosition[count]);
                vbs[count] = ms[i];
                //System.out.println("Verbs["+i+"]="+vbs[i]);
                f1.displayString(ms[i]);
                //for(int l=0;l<3;l++){
                  if((tgs[i-1].equals("NN")) || (tgs[i-1].equals("NNP"))){
                    preNN = Words[2*i-2];
                    //System.out.println(" preNN = "+preNN);    
                  }
                //}
                 if(count > 0 && verbPosition[count] != verbPosition[count-1])
                      hint = getTAM(vbs[count-1], f1);
                    else
                      hint = getTAM(vbs[count], f1);
                if(hint == -1){
                  //System.out.println("FRAME DOES NOT EXIST:  ");
                  System.exit(0);
                }
                count++;
              //  tvbs[k] = vbs[i]; 
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
        }
    catch(NullPointerException nullPointer){}
    String[] z = new String[count];
        String b = "null";
        int m = 0;
        
        for(int l = 0 ; l < 10 ; l++){
          if(b.equals(y[l])) {
            break;
          }
          else if(m < count){
            //System.out.println("y["+l+"]="+y[l]+" m="+m+" Count="+count);
            z[m] = y[l];  
      
          }
    //  //System.out.println("z["+m+"]="+z[m]);
          m++; 
        }           
        return z;
    }
    
    public void getFrame(String[] verb){
      //String vibhakti-verb;
      ReadXMLFile xm = new ReadXMLFile();
      xm.main1(verb, sentwords, wordnetfile);
      
    }
    public String[] findNouns(String[] tgs1, String[] ms , String[] nns, String[] vbkts, int len, String[] ntstart , int tg){
        //System.out.println("HI ITS A NOUN SECTION");
        String[] nbs = ms;
        int itm = 0;
        //int l2 = 0;
        String ind = "NULL";
        int count = 0;
        //int pcount = 0;
        int nt = -1 ;
        //String sum = np[0];
        String[] nntg = new String[3];
        String[] y = new String[36];
        String[][][] vibhakti = new String[noWords/2][noWords/2][2];
        //System.out.println("***tgs1["+0+"]="+tgs1[0]+"  nns["+1+"]="+nns[1]+" nowords/2 = "+(noWords/2));
        try{
        for(int i = 0; i < noWords/2 ; i++){
          for(int j=0;j<5;j++){
            if((tgs1[i].equals(nns[j]) || tgs1[i].equals("JJ")) && count < len){  
              //System.out.println("nt="+nt+"   nns["+j+"]="+nns[j]+"  len ="+len+"  ntstart["+0+"]="+ntstart[0]+" tg="+tg+"   tgs1["+i+"]"+tgs1[i]); 
              /*if(len > 0){
                for(int c = i ; c < ntstart.length ; c++){
                  if(nns[j].equals(ntstart[0])){
                    //System.out.println("welcomeback i="+i);
                    i = i + tg;
                    break;
                  }
                }
              }*/
              /*if(nt >= 3)
                break;
              nntg[nt] = nns[j];
              //System.out.println("nounTags["+nt+"]="+nntg[nt]);*/
              //for(o = 0; o < 1 ; o++){
              //  if(ms[i] != optkrks[o]){
                  //nbs[i] = ms[i];
                  //System.out.println("tgs1["+i+"]="+ tgs1[i]+"   nns["+j+"]="+nns[j]+"   Nouns["+i+"]="+ms[i]);
                  /*if(ms[i] == np[l2] && pcount <= np.length){
                    pcount++;
                    if(pcount == np.length){
                      for(int k = 0 ; k < np.length ; k++){
                        sum = sum + "    " +np[k] ;
                      }
                      nbs[itm] = sum;
                    }
                  }
                  else{
                    pcount = 0;
                  }*/
              //if(pcount == 0){
                    nbs[itm] = ms[i];
                //  }
                  vibhakti[count][2*count][0]=ms[i];
                  for(int l = 0 ; l < vbkts.length ; l++){
                    if(vbkts[l].equals(ms[i+1])){
                      vibhakti[count][2*count][1] = ms[i+1];
                      //System.out.println("vibhakti["+count+"]["+i+"]["+1+"]="+vibhakti[count][i][1]);
                      break;
                    }
                    else{
                      vibhakti[count][i][1] = "null";
//                      break;
                    }
                  }
                  
                  //System.out.println("Nouns["+i+"]="+nbs[i]+"   N["+0+"]["+0+"]="+N[0][0]+"  count="+count);  
                  y[count] = nbs[i];
                  count++;
                  itm++;
                  if(N[0][0].equals("null1"))
                    NP[count] = nbs[i];
              //char ch1 = nbs[i].charAt(3);
          ////System.out.println("Character at 2 index is: "+ch1);
          //String l = Character.toString(ch1);
          //try{
          //  displayString(nbs[i], filename);
          //}catch (IOException ie){ };
          //fout.close();
                  
                  //itm++;
              //  }
              //}
            //  ind = nbs[i];
            }
            else{

            }
          }
          nt++;
          //l2++;
        }
        }
    catch(NullPointerException nullPointer){}
    //System.out.println("nncount="+count);
    
    if(tg == 1){
      count++;
    }
        try{
          //System.out.println("DISPLAY SECTION");
      displayString(nbs, count, filename, vbkts);
      //displayString1(filename,vibhakti,count);
//      Actualvibhakti  = vibhakti;
    }catch (IOException ie){ };
    Actualvibhakti = vibhakti;
        //y.length = count;
        String[] z = new String[count];
        String b = "null";
        int m = 0;
          for(int l1 = 0 ; l1 < count ; l1++){
            //System.out.println("y["+l1+"]="+y[l1]+"karana[1]="+karana[1]);
            if(b.equals(y[l1])) {
              break;
            }
            else if(m < count && y[l1] != karana[1]){
              //System.out.println("y["+l1+"]="+y[l1]+" m="+m+" Count="+count);
              z[m] = y[l1]; 
        
            }
            
        //System.out.println("z["+m+"]="+z[m]);
            m++; 
          }
          
//    nounTags = nntg;
    //nounTags = nns;
    
    /*for(int q = 0 ; q < np.length ; q++){
      //System.out.println("NP["+q+"]="+NP[q]);
    }*/
        return z;
    }
    
    public String[] getNounTag(){
      return nounTags;
    }
     public int[][][] findEdges(String[] v, String[] n){
      //System.out.println("no of nouns = "+n.length+" no of verbs = "+v.length);
      int[][][] egs = new int[v.length][n.length][8];
      int karkas = 8 ;   // NO OF KARKAS 1. KARTA 2. KARNA 3. KARMA   4 SAMPRADAN    5 APADAN    6. SAMBANDH  7. ADHIKARAN (k7)  8. K2P (LOCATION) 
      for(int i = 0 ; i < v.length ; i++){
        for(int k = 0 ; k < n.length ; k++) 
        {
          for(int j = 0 ; j < karkas ; j++){
             try{
            if(n[k].equals("null")){
               
            }
            else{
              //System.out.println("n["+k+"]="+n[k]);
              /*if(j == 2)
                egs[i][k][j] = 0; 
              else*/
                egs[i][k][j] = 1;
            }
               }catch(NullPointerException nullPointer){
            //System.out.println("***egs["+i+"]["+k+"]["+j+"]="+egs[i][k][j]);  
      /*  if(j == 2)
              egs[i][k][j] = 1;
            else
               egs[i][k][j] = 0;*/
               
                }
          }
        }
        
      }
      //System.out.println("CONSTRAINT GRAPH :");
      //System.out.println();
      for(int i = 0 ; i < v.length ; i++){
        for(int k = 0 ; k < n.length ; k++) // For karkas
        {
          for(int j = 0 ; j < karkas ; j++){
            //System.out.print("    Edges["+i+"]["+k+"]["+j+"]="+egs[i][k][j]);
          }
          //System.out.println();
        }
  }
  nKarkas = karkas;
  //System.out.println("***nKarkas="+nKarkas);
      return egs;
    
    }
    
    public int[][][] findsubGraphs3(int[][][] eg1, int krks1, int rows1, int ver, int nsg){
        int ns = 0;
        //ver = nsg;
      //System.out.println("nsg = "+nsg+" rows1="+rows1+" krks1="+krks1);
      int[][][] eg2 = new int[ver][rows1][krks1]; // ver stands for verb index
      int[][][] teg1  = new int[nsg][rows1][krks1];
      eg2 = eg1;
      //System.out.println("nsg="+nsg+"  ver="+ver);
      //System.out.println("Hi Its findsubGraph3 method:  ");
      for(int i = 0 ; i < rows1 ; i++){
        for(int j = 0 ; j < krks1 ; j++){
          //System.out.print("\t"+eg1[ver][i][j]);
        }
    //System.out.println();       
      }
      //System.out.println();
      int value = nsg;
  int p;

      for( p = nsg ; p < rows1 ; p++){
        if(p != 0)
          value = (int )(Math.random() * (krks1) + 1);
        for(int k = value ; k < krks1 ; k++){
          //System.out.println("k="+k+" eg1[0]["+p+"]["+k+"]="+eg1[0][p][k]);
          if(eg1[0][p][k] == 1){
            for(int q = 0 ; q < rows1 ; q++){
              //System.out.println("ver = "+ver+" p="+p+" q="+q+" k="+k);
              if(p == q){
                for(int l = 0 ; l < krks1 ; l++){
                  //System.out.println("l= "+l+" ver="+ver+" p="+p);
                  if(k != l)
                    eg2[ver][p][l] = 0;

                  ////System.out.println("****eg2["+ver+"]["+q+"]["+l+"]="+eg2[ver][q][l]); 
                }
              }
              
            }
            for(int m = 0; m < krks1 ; m++){
              if(k == m){
                for(int l = 0 ; l < rows1 ; l++){
                  if(l != p)
                    eg2[ver][l][k] = 0;
                  //System.out.println("@@@@eg2["+ver+"]["+l+"]["+k+"]="+eg2[ver][l][k]); 
                }
              }
                
            }
            
          }
          //System.out.println();
        }
      }
      //ns++;
      //}
      int count = 0;
      int value1 = 0;
      for(int i1 = 0 ; i1 < rows1 ; i1++){
        for(int j1 = 0 ; j1 < krks1 ; j1++){
          //System.out.print("\t"+eg2[ver][i1][j1]);
          if(eg2[ver][i1][j1] == 1 ){
            count++;
          }
        }
    //System.out.println();       
      }
      //System.out.println();    
      //System.out.println();    
      int value2 = 0;
      int value3 = 0;
      int temp = 0;
  temp = count - rows1;
      
      //System.out.println("count = "+count);
      while(temp != 0){
        //System.out.println("Temp = "+temp);
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

      
      //System.out.println("Final Subgraph :");    
      for(int i3 = 0 ; i3 < rows1 ; i3++){
    for(int j3 = 0 ; j3 < krks1 ; j3++){
      //System.out.print("\t eg2["+ver+"]["+i3+"]["+j3+"]= "+eg2[ver][i3][j3]);
        }
    //System.out.println();
      }

 
      return eg2;
    }
    
    public int[][][] findsubGraphs(int[][][] eg, int krks, int rows, int ver, int indkrn){
      int temp = 0;
      int temp1 = 0;
      int r = 0;
      int c  = 0;
      int nsgr = 5040 ;
      int maxedges;
      System.out.println("indexkarana = "+ indkrn);
      if(indkrn != 0){            // DOES KARANA PRESENT WHICH IS OPTIONAL ?
        maxedges = 1;
  }
  else{
    maxedges = 0;
  //  krks = krks - 1;
    //System.out.println("krks = "+krks);
  }
        
  
      int[][] ary = new int[rows][krks];
      //System.out.println("sub graphs ="+nsgr+"no of rows = "+rows+"no of cols = "+krks+" verb= "+ver+"nsgr="+nsgr);
      int[][][] sg = new int[nsgr][rows][krks];
      int[][][] subtract = new int[nsgr][rows][krks]; 
      for(int l = 0 ; l < rows ; l++){
        for(int m = 0 ; m < krks ; m++){
          sg[0][l][m] = eg[ver][l][m];
          System.out.print("   eg["+ver+"]["+l+"]["+m+"]="+eg[ver][l][m]);
        }
        System.out.println();
      }
   //   int i = 0;      
   //   int j =0;
    for(int i =0;i<rows;i++){
        for(int j=0;j<krks;j++){
          do{
            if(sg[0][i][j] == 1){
              //System.out.println("-sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
                      for(int n = 0 ; n < j ; n++){
                  if(sg[0][i][n] == 1){
                    
                    //System.out.println("sg["+0+"]["+i+"]["+n+"]="+sg[0][i][n]);
                    temp = 1;
                    break;
                    
                  }
                  else{
                    //System.out.println("*sg["+0+"]["+i+"]["+n+"]="+sg[0][i][n]);
                    temp = 0;
                  }
                }
                //System.out.println("new0sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]+" temp="+temp);
                
                for(int m = 0 ; m < i ; m++){
                  if(sg[0][m][j] == 1){
                    //System.out.println("2sg["+0+"]["+m+"]["+j+"]="+sg[0][m][j]);
                    temp1 = 1;
                    break;
                  }
                  else{
                    //System.out.println("*2sg["+0+"]["+m+"]["+j+"]="+sg[0][m][j]);
                    temp1 = 0;
                  }
                }
              //}
              //System.out.println("0sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]+" temp1="+temp1);
              
            }
            else{ 
              //System.out.println("00sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
            }
            if(temp == 1 || temp1 == 1){
              sg[0][i][j] = 0;
              //System.out.println("000sg["+0+"]["+i+"]["+j+"]="+sg[0][i][j]);
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
          //System.out.print("   sg["+0+"]["+l+"]["+m+"]="+sg[0][l][m]);
          
        }
        //System.out.println();
      }
      
    if(indkrn != 0)
        maxedges = 1;
  else
    maxedges = 0;     
      for(int l = 0 ; l < rows ; l++){
        for(int m = 0 ; m < krks ; m++){
          subtract[1][l][m] = eg[ver][l][m] - sg[0][l][m];
          
          //System.out.print("   subtract["+1+"]["+l+"]["+m+"]="+subtract[1][l][m]);
        }
        //System.out.println();
        //System.out.println();
      }
      
      
      do{
      
        for(r = 0 ; r < rows ; r++){
          for(c=0;c<krks;c++){
            if (temp == 0 ){
                sg[1][r][c] = subtract[1][r][c];
                //System.out.println("**sg[1]["+r+"]["+c+"]="+sg[1][r][c]+" Maxedges="+maxedges);
                temp = 1;
              }
              else {
                if(r != (r-1) && r != 0)
                  sg[1][r][c] = subtract[1][r][c];
              }
            for(int s=0;s<=r;s++){
              do{
                if(sg[1][s][c] == 0){
                  //System.out.println("111sg[0]["+s+"]["+c+"]="+sg[0][s][c]);
                    temp = 0;
                  }
                  else{
                    
                    temp = 1;
                    //System.out.println(" s = "+s+"c="+c);
                    break;
                  }
                }
                while(temp == 1);
            }
            
              if(sg[1][r][c] == 1){//{ && maxedges < krks){
                //System.out.println("maxedges="+maxedges);
                maxedges++;
              }
            
            //System.out.println("sg[1]["+r+"]["+c+"]="+sg[1][r][c]+" Maxedges="+maxedges+"temp="+temp);
        
        //System.out.println("maxedges = "+maxedges+"  r="+r+"  c="+c+" krks= "+krks);
      }
       }
            
        } while(maxedges < (krks-1));
 
        for(int v = 0 ; v < nsgr ; v++){
          if(indkrn != 0)
            sg[v][indkrn][2] = 1;
        }
        
        
//        for(int s1 = 0 ; s1 < nsgr ; s1++){
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
          //  for(int v2 = 0 ;v2 < krks ; v2++){
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
                          
          //  }
        //  }
          
          
        //  for(int u2 = 0 ; u2 < rows ; u2++){
        //    for(int v2 = 0 ;v2 < krks ; v2++){
              if( sg[0][0][0] == 1 && rows > 2){
                sg[6][0][0] = 1;
                sg[6][1][5] = 1 ;
                sg[6][2][1] = 1;
                sg[8][0][0] = 1;
                sg[8][1][1] = 1;
                sg[8][2][7] = 1;
                
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
              
              
              
                          
        //    }
        //  }
          
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
  //      }
        int[] degree =  new int[nsgr];
        for(int s1 = 0 ; s1 < nsgr ; s1++){
          for(int u1 = 0 ; u1 < rows ; u1++){
            for(int v1 = 0 ;v1 < krks ; v1++){
              if( sg[s1][u1][v1] == 1){
                degree[s1]++;
                //System.out.println("Degree of the subgraph "+s1+" is "+degree[s1] );
              } 
            
            }
          }
          Deg[s1] =degree[s1];
        }
        for(int u = 0 ; u < nsgr ; u++){
          for(int l = 0 ; l < rows ; l++){
          for(int m = 0 ; m < krks ; m++){
              //System.out.print("   sg["+u+"]["+l+"]["+m+"]="+sg[u][l][m]);
            }
            //System.out.println();
          }
          //System.out.println();
          //System.out.println();
        }
        nKarkas = krks;
        return sg;
    
    }
    public int[][][] NewSubgraph(int[][][] sg, int nons, int krks, int flgk4){
      int[][][] sg1 = new int[1][nons][krks];
      sg1 = sg;
      int i = 1;
//      for(int i = 1 ; i < nons ; i++){   // Karta (K1) is excluded which can not be sampradan
  while(i<3){
        for(int j = 0 ; j < krks ; j++){
      //    sg1[0][i][j] = 0;
          if(flgk4 == 1 && sg[0][i][j] == 1){
            sg1[0][i][krks-1] = sg[0][i][j];
//            sg1[0][i][j] = 
    //        sg1[0][i][j] = 0;
            if(i == 2){
              sg1[0][2][1] = sg[0][i][j];
  //            sg1[0][i][j] = 0;
            }
        //    i = i +1;
//            sg1[0][i+1][1] = sg[][][];
          }
        }
        i = i + 1;
        
      }
      return sg1;
//      return sg1;
    
    }
    public int[][][] findfinalmatching(int[][][] subgr,int nsgr, int rows, int krks, int indkrta, int indkrn, String[] nn, String np, boolean e){
      int[][][] fg = new int[nsgr][rows][krks];
      int[][][] afg = new int[nsgr][rows][krks];
      
      boolean flg = true ; //false;
      int countParse = 0;
      int count = 0;
      /*if(Deg[0] != rows){
        //System.out.println("Degree of Graph is not equal to No. of nouns. ");
        flg = false;
        //break;
        //maxedges = 1;
  }
  else{
    //maxedges = 0;
    //krks = krks - 1;
  }*/
      System.out.println("indkrta="+indkrta+" 0nsgr="+nsgr+" rows="+rows+" krks="+krks+"  noun ph[1]="+np);
      try{
      for(int u = 0 ; u < nsgr ; u++){
      for(int l = 0 ; l < rows ; l++){
        for(int m = 0 ; m < krks ; m++){
          System.out.println("   subgr["+u+"]["+l+"]["+m+"]="+subgr[u][l][m]);
          if(subgr[u][l][m] == 1){
            if(l == indkrta){
              fg = subgr;
                ////System.out.print("   11subgr["+u+"]["+l+"]["+m+"]="+subgr[u][l][m]);
              }
              else{ }
                
            }
            else { }
      }
          //System.out.println();
        }
        //System.out.println();
        
        ////System.out.println();
  }
}
catch(ArrayIndexOutOfBoundsException ae){};
  //for(int u = 0 ; u < nsgr ; u++){
  int u1 = 0;
  //System.out.println("nsgr="+nsgr+"   rows="+rows);
  while(u1 < nsgr){ 
//  for(int u1 = 0 ; u1 < nsgr ; u1++){
    Deg[u1] = rows;
    System.out.println("before u1="+u1);
    if(Deg[u1] != rows){
          //System.out.println("Degree of the graph Deg["+u1+"] is = "+Deg[u1]);
      flg = false;
    }
    else{
          //System.out.println("True Degree of the graph Deg["+u1+"] is = "+Deg[u1]);
      flg = true;
    }
    try{
      for(int l1 = 0 ; l1 < rows ; l1++){
        for(int m1 = 0 ; m1 < krks ; m1++){
          fg[0][l1][m1] = subgr[u1][l1][m1];
        }
      }
      }
catch(ArrayIndexOutOfBoundsException ae){};
      //System.out.println("oflag="+flg);
      if(flg){
        System.out.println("***oflag="+flg);
      flg = checkTAM(fg,nsgr,rows,krks,e);
      if(flg != true){
        //u1++; 
        //break;
      }
      else{
        count++;
      }
      //System.out.println("**flag = "+flg);
      
    }
    /*  if(count > 1 && rows == 1){
      checkVerb(fg, krks);
    }*/
    //}
    int cnt = 0;
    int row = 0;
    int column = 0;
    int presCheckIndex = 0 ;
    int displaystr = 1;
   // k1sexist = false;
    int locationindex = 0;
    String[] presence = new String[rows]; // Assuming maximum 10 parsed output
    
  if(flg == true ){
    cnt++;
    countParse++;
    System.out.println("first number of parsing :"+countParse+"   rows="+rows+"   krks="+krks+"  frame Index="+frameInd);
    for(int l = 0 ; l < rows ; l++){
            System.out.print(nn[l]);
          for(int m = 0 ; m < krks ; m++){
            //countParse++;
              System.out.print("       fg["+0+"]["+l+"]["+m+"]="+fg[0][l][m]);
            }
    }

   // golden = fg;
    //presence[cnt] = checkTAM1(fg,nsgr,rows,krks);
    ////System.out.println("presence["+cnt+"]="+presence[cnt]);
    //System.out.println("\t  KARTA(k1)   \tKARMA(k2)\t  KARANA(k3)\t  SAMPRADAN(K4) \tAPADAN(k5)   \tSAMBANDH(K6)  \tADHIKARAN(K7)    \tLOCATION(K2P)     \tLOCATION(K7t)");
          for(int l = 0 ; l < rows ; l++){
            //System.out.print(nn[l]);
            //System.out.println("trouble 1");
          for(int m = 0 ; m < krks ; m++){
            //countParse++;
              System.out.print("       fg["+0+"]["+l+"]["+m+"]="+fg[0][l][m]);
              if(frameInd == 5 ){    // Location Exist
                if(fg[0][l][7] == 1)
                  locationindex = 1;
                else
                  locationindex = 2;
              }
              //System.out.println("trouble 2");
              if(e && frameInd == 8 ){    // Location TIME Exist
                if(fg[0][l][1] == 1 && fg[0][2][8] == 1){
                  locationindex = 2;
                  //System.out.println("LOCATION TIME INDEX**");
                  //displayString(filename,fg,rows,krks,nn);
                  break; 
                }
                else if(e){
                  if(fg[0][1][8] ==1)
                  {
                    locationindex = 3;
                  }
                }
                
              }
              //System.out.println("trouble 3");
              if(fg[0][0][6]  == 1 && rows == 2){
                if(fg[0][1][7] == 1 || fg[0][1][0] == 1){
                  //if(!e && fg[0][1][8] == 1){
                  //  //System.out.println("Halt");
                    displaystr = 0;
                  //}
                }
                
              }
              //System.out.println("trouble");
            //  //System.out.println("    new -2 NP[1]="+np+"   l="+rows+"   m="+krks+"  fg["+0+"]["+0+"]["+1+"]="+fg[0][0][1]);
              //try{
              /*  if(np.equals(null)){
                  //System.out.println("hi null");
                  np = "null1";
                }*/
              //System.out.println("trouble new");
              //}
              //catch(NullPointerException nu){
              
              //}
            //  //System.out.println("    new -3 NP[1]="+np);
              //try{
            /*  if(!np.equals("null1")){
                if(fg[0][0][0] == 1 && rows == 2 && fg[0][1][1] == 1 && (np.equals("NNP") || np.equals("JJ") || np.equals("NN"))){
                  //System.out.println("k1sexist = "+k1sexist);
                  k1sexist = true;
                }
              }
              else{
                //System.out.println("    new  -1 NP[1]="+np);
                k1sexist = false;
              }*/
              //System.out.println("trouble 4");
              ////System.out.println("    new  0 NP[1]="+np);
              //}
              //catch(NullPointerException nullPointer){}
              ////System.out.println("directkrk = "+directkrk);
              if(directkrk == 7){    // EXISTENCE OF LOCATION IN PLACE k2P
                for(int d = 0 ; d < rows ;d++){
                  if(fg[0][d][7] == 1){
                    displaystr = 1;
                  }
                  else{
                    displaystr = 0;
                  }
                }
              }
              ////System.out.println("    new  1 NP[1]="+np);
              if(directkrk == 8){    // EXISTENCE OF lOCATION IN TIME K7t
                for(int d = 0 ; d < rows ;d++){
                  if(e && fg[0][d][8] == 1){
                    displaystr = 1;
                  }
                  else{
                    displaystr = 0;
                  }
                }
              }
              
              //else{
              //  locationindex = 1;  
              //}
              if(fg[0][l][m] == 1){
                row = l;
                column = m;
                presence[l] = Presence[m][0];
                
                if(presence[0].equals("Mendatory") && countParse <= 1){
                  
                  presCheckIndex = 1;
                }
                else if(countParse >1){
                  presCheckIndex = 2;
                }
                
              }
            }
            //System.out.println();
          }
          //System.out.println("Prescheckindex="+presCheckIndex+"  locationindex ="+locationindex);
          //TO DISPLAY FINAL OUTPUT IN THE OUTPUT FILE ////
          if(presCheckIndex != 2 && e==false && locationindex == 0 && displaystr ==1){
            //displayString(filename,fg,rows,krks,nn, e, k1sexist);  
            //System.out.println("first");
            for(int a=0 ; a < rows ; a++){
              for(int b=0 ; b < krks ; b++){
                golden[g][a][b] = fg[0][a][b];
                System.out.println("first golden["+g+"]["+a+"]["+b+"]="+golden[g][a][b]);
              }
            }
            g++;
          //  goldenfg[][][]
            //break;
          }
          if(locationindex == 1 ){  
            //displayString(filename,fg,rows,krks,nn, e,k1sexist);
            //System.out.println("second");           
            for(int a=0 ; a < rows ; a++){
              for(int b=0 ; b < krks ; b++){
                golden[g][a][b] = fg[0][a][b];
                System.out.println("second golden["+g+"]["+a+"]["+b+"]="+golden[g][a][b]);
              }
            }
            g++;
            break; 
          }
          else if(locationindex == 3){
            //System.out.println("LOCATION TIME INDEX");
            //System.out.println("third");
            
            //krks = krks + 1;          
            //displayString(filename,fg,0,rows,krks,nn, e, k1sexist);  
            k1texist = true;
            //try{
              for(int a=0 ; a < rows ; a++){
                for(int b=0 ; b < krks ; b++){
                  
                  egolden[g][a][b] = fg[0][a][b];
                  System.out.println("third egolden["+g+"]["+a+"]["+b+"]="+egolden[g][a][b]);
                }
              }
            //}
            //catch(ArrayIndexOutOfBoundsException ae){}
            g++;
            break;
            
          }
          else if(locationindex != 2 && displaystr ==1){
            //System.out.println("hi am I parsed ?");
            //System.out.println("fourth");           
            //displayString(filename,fg,rows,krks,nn, e, k1sexist);  
            for(int a=0 ; a < rows ; a++){
              for(int b=0 ; b < krks ; b++){
                try{
                  golden[g][a][b] = fg[0][a][b];
                  //System.out.println("fourth golden["+g+"]["+a+"]["+b+"]="+golden[g][a][b]);
                }
                catch(ArrayIndexOutOfBoundsException ae){}
              }
            }
            g++;
          }
      }
      else{
        //System.out.println("*****PARSING IS NOT POSSIBLE*****");
      //  u1=3;
      }
      finalcountParse = countParse;
      u1++;
      //System.out.println("after u1="+u1+"   count final output ="+finalcountParse);
    }
    //System.out.println("1 trouble");
          //System.out.println();
          //System.out.println();
     //   }
  return fg;
    }
    
    public static boolean getk1s(){
      //System.out.println("getk1s="+k1sexist);
      return k1sexist;
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
        // //System.out.println("file length = "+file.length);
         //System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  //System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
               //System.out.println("check");    
               //System.out.println(" findMe length ="+fileLength);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 2);
              //System.out.println("g[0]="+g[0]);
              g[1] = findMe1;
          //   //System.out.println("file["+(i+1)+"]="+file[i+1]);

              
//              index = i+1;
  //            positions[count] = file[index];
    //          //System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
      //        if(positions[count].equals("YF")){
        //        if(i < (noWords-2))
    //              start[count] = file[index + 2]; 
               //   //System.out.println("start["+count+"]="+start[count]);
      //            getStart(start[count], count);
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
        ////System.out.println("LAI filelength="+file.length+"   findme="+findMeLength);
        String findMe1 = "hi";
        if(fileLength > 3)
          findMe1 = file.substring(fileLength-3,fileLength);
  else
    g[0] = "ab";
        // //System.out.println("file length = "+file.length);
         //System.out.println("LAI Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  //System.out.println("i ="+i+"file["+i+"]="+file[i]);

           if(findMe1.equals(findMe)) {
               //System.out.println("check");    
               //System.out.println(" filelength="+fileLength);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
              //System.out.println("g[0]="+g[0]);
              g[1] = findMe1;
          //   //System.out.println("file["+(i+1)+"]="+file[i+1]);

              
//              index = i+1;
  //            positions[count] = file[index];
    //          //System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
      //        if(positions[count].equals("YF")){
        //        if(i < (noWords-2))
    //              start[count] = file[index + 2]; 
               //   //System.out.println("start["+count+"]="+start[count]);
      //            getStart(start[count], count);
              }
              else{
                g[0] = "ab";
              }
//              count++;
              
              if(file.equals(findMe)){
            //System.out.println("special sang");
            sangexist = 1;
            //g[0] = file;
           // foundIt = true;
          }
           //   break;
         // }
          
           return g;
          // break;
        }
        
        
        public String[] RegionMatchesDemoLagi(String findMe, String file) {
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
        if(fileLength > 4){
          try{
            findMe1 = file.substring(fileLength-4,fileLength);
    }
    catch(ArrayIndexOutOfBoundsException ae){}
  }
  else
    g[0] = "ab";
        // //System.out.println("file length = "+file.length);
         //System.out.println("Find Me = "+findMe+" file ="+file+" Find Me1 = "+findMe1);
        
        boolean foundIt = false;
      //  for(int i = 0; i < s; i++) {
          //  //System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(findMe1.equals(findMe)) {
               //System.out.println("check");    
               //System.out.println(" filelength="+fileLength);   
              foundIt = true;
              g[0] = file.substring(0, fileLength - 3);
              //System.out.println("g[0]="+g[0]);
              g[1] = findMe1;
          //   //System.out.println("file["+(i+1)+"]="+file[i+1]);

              
//              index = i+1;
  //            positions[count] = file[index];
    //          //System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
      //        if(positions[count].equals("YF")){
        //        if(i < (noWords-2))
    //              start[count] = file[index + 2]; 
               //   //System.out.println("start["+count+"]="+start[count]);
      //            getStart(start[count], count);
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
    //System.out.println("SIMPLE CONCEPTUAL GRAPH : ");
    int[][] SCG;
    int[][] subSCG;
  int[][][] completeSCG;
    String[] n = new String[5*3]; //total 5 noun phrases each with length 3 words
    int karkas = 2; // what and whose
    int count = 0;
    
    
    for(int i = 0 ; i < np.length ; i++){
      for(int j = 0 ; j < np[i].length ; j++){
        //System.out.println("np["+i+"]["+j+"]="+np[i][j]);
        try{
          if(np[i][j].equals(null) || np[i][j].equals("n")){
            //System.out.println("hi nnl = "+nnl);
            //continue; 
          }
          else{
            n[count] = np[i][j];
            //System.out.println("**n["+count+"]="+n[count]+"  vp.length ="+vp.length +" vp[0]="+vp[0]);
            if(count < (n.length-1))
              count++;
          
          }
        }catch(NullPointerException nullPointer){}
      }
    }
    for(int k = 0 ; k < vp.length ; k++){
      //System.out.println("hi");
      //System.out.println("vp["+k+"]="+vp[k]);
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
   // int[][] temp1 ;
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
   // temp1 = sg;
    
    for(int i = 0 ; i < karkas*nounlength ; i++){
      for(int j = 0 ; j < karkas*nounlength ; j++){
        if(i%2 != 0){   // Checking for multiple karka whose. IN THE FINAL GRAPH ALL THE KARKAS TO BE ASSIGNED EXACTLY ONCE
          //System.out.println("Count number of non zero odd columns for whose");
          if(temp[j][i] == 1){
            flag[0] = 1;
            break;
            //count[i]++;
            
          }
          //System.out.println("***Count number of non zero columns="+flag[0]);
        }
        else{
          //flag = 0;
          //System.out.println("Count number of non zero even columns for what");
          if(temp[j][i] == 1){
            flag[1] = 1;
            break;
            //count[i]++;
            
          }
          //System.out.println("&&&Count number of non zero columns="+flag[1]); 
        }
      }
   
    }
    for(int i1 = 0 ; i1 < 2 ; i1++){
      int count3 = 0;
      int token = 0;
      if(flag[i1] == 1){
        for(int i = 0 ; i < (karkas*nounlength) ; i++){
          //System.out.println("i="+i);
//          finalSCG[]
        //    count1=0;
            // token = 0;
            if(i1 == 0){
              if(i%2 == 0 ){
                for(int m = 0 ; m < (karkas*nounlength) ; m++){
                  for(int n = 0 ; n < (karkas*nounlength) ; n++){
                    //System.out.println("temp["+m+"]["+n+"]="+temp[m][n]);
                    //finalSCG[0] = temp;
                    //if(i == 0)
                    //  finalSCG[0][m][i] = 0;
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
                //  i=0;
                //break;
              //break;
              }
            }
            else{
              if(i%2 != 0 ){
                for(int k = 0 ; k < (karkas*nounlength) ; k++){
                  finalSCG[count3][k][i] = 0;
                  //System.out.print("  "+finalSCG[count3][k][i]);
                  //System.out.println("count3="+count3+"   i = "+i);
      
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
    
    //System.out.println();
    for(int i = 0 ; i < karkas*nounlength ; i++){
      for(int j = 0 ; j < karkas*nounlength ; j++){
        //System.out.print("  "+sg[i][j]);
        if(j != 0){
          finalSCG[0][i][j] = sg[i][j];
        }
        if(j != 2){
          finalSCG[1][i][j] = sg[i][j];
        }
        //if(j == 0)
        //  finalSCG[0][i][j] = 0;
        //finalSCG[0][i][j] = temp[i][j];
      }
          //System.out.println();
    }
    //System.out.println();
    
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
    //System.out.println("FINAL PARSED GRAPH: ");
    //finalSCG[1] = sg;
    //System.out.println("parse="+parse);
    for(int i = 0 ; i < karkas*nounlength ; i++){
      for(int j = 0 ; j < karkas*nounlength ; j++){
        //System.out.print("  "+finalSCG[parse][i][j]);
      }
      //System.out.println();
    }
    //System.out.println();
    //System.out.println();
    ////System.out.println("noun["+0+"]["+0+"]="+noun[0][0]+"noun["+1+"]["+1+"]="+noun[0][1]);
    displayStringSCG(filename, finalSCG, nounlength, karkas, noun , parse );
    return finalSCG;
    
   }
   public int[][] findEdgesSCG(String[] v, String[] n, int nounlength, int karkas, int nnlen){
    //nnlen++;  // the noun index started from 0 
      //System.out.println("no of noun phrase = "+nnlen+" no of verbs = "+v.length + "  karkas="+karkas);
      int counter = 0;
      int j = 0;
    //  int karkas = 2 ;   // NO OF KARKAS 1. WHOSE 2. WHAT  
      int[][] incedence = new int[karkas*nnlen][karkas*nnlen];
      int[][][] incedence1 = new int[karkas*nnlen][karkas*nnlen][2];
      int[] vertices = new int[karkas+nnlen];
      int[][] edges = new int[karkas*nnlen][karkas*nnlen];   // For complete bipartite graph 
      
      for(int i = 0 ; i < nnlen ; ){
        while(j < karkas){
          edges[i][j] = 1;
          //System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
          edges[i][j+nnlen] = 1;
          //System.out.println("edges["+i+"]["+(j+nnlen)+"]="+edges[i][j+nnlen]);
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
            //System.out.println("edges["+i+"]["+(j+nnlen+1)+"]="+edges[i][j+nnlen+1]);
          }
          else{
            edges[i][j+nnlen-1] = 1;
            //System.out.println("edges["+i+"]["+(j+nnlen-1)+"]="+edges[i][j+nnlen-1]);
          }
          j++;
          i++;
        }
      }
      
      
      /*for(int i = 0 ; i < (nnlen*karkas) ;  ){
        //k1++;
        for(int j = 0 ; j <  karkas ; j++ ){
          //System.out.println("j="+j+"  i="+i);
          edges[i][j]=1;
          //System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
          edges[i][j+nnlen] = 1;
          //System.out.println("edges["+i+"]["+(j+nnlen)+"]="+edges[i][j+nnlen]);
          i++;
          edges[i][j]=1;
          //System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
          if((j+nnlen+1) < (nnlen*karkas )){
            //System.out.println("hi");
            edges[i][j+nnlen+1] = 1;
            //System.out.println("edges["+i+"]["+(j+nnlen+1)+"]="+edges[i][j+nnlen+1]);
          }
          else{
            edges[i][j+nnlen-1] = 1;
          }
          i++;
          //for(int k = j ; k <  (nnlen * karkas) ; k++){
          /*if(i%2 != 0){
            edges[i][j - nnlen-1]=1;
            edges[i][j] = 1;
            //System.out.println("edges["+i+"]["+(j-nnlen-1)+"]="+edges[i][j-nnlen-1]);
            //System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
          }
          else{
            edges[i][j - nnlen] = 1;
            edges[i][j] = 1;
            //System.out.println("edges["+i+"]["+(j-nnlen)+"]="+edges[i][j-nnlen]);
            //System.out.println("edges["+i+"]["+j+"]="+edges[i][j]);
          }
            
            
            
          
          //i++;
          //if(counter < (nnlen-1))
      //  counter++;
          
        }
        //if(counter < (nnlen-1))
    //    counter++;
      }*/
      
      
  /*for(int i = 0 ; i < nnlen ; i++){
    for(int j = nnlen ; j < (nnlen * karkas) ; j++){
      edges[counter][i]=1;
      //System.out.println("edges["+counter+"]["+i+"]="+edges[counter][i]);
      edges[counter][j]=1;
      //System.out.println("**edges["+counter+"]["+j+"]="+edges[counter][j]);
      if(counter < (nnlen-1))
        counter++;
    }
    //counter++;
      }*/
      
      //System.out.println("SIMPLE CONCEPTUAL GRAPH :");
      //System.out.println();
      
      for(int i = 0 ; i < (nnlen * karkas) ; i++){
        for(j = 0 ; j < (nnlen * karkas) ; j++){
          //System.out.print("    "+edges[i][j]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
          //for(int k = 0 ; k < karkas ; k++)
//          if(j == 0){}
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
    //System.out.println();       
  }
  
  //System.out.println("SIMPLE CONCEPTUAL GRAPH A:");
  for(int i = 0 ; i < (nnlen * karkas) ; i++){
        for(j = 0 ; j < (nnlen * karkas) ; j++){
          
          //for(int k = 0 ; k < karkas ; k++){
            //System.out.print("    "+incedence[i][j]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
            //for(int k = 0 ; k < karkas ; k++){
            if(j%2 == 0){
              incedence1[i][j][0] = incedence[i][j];
            }
            else{
              incedence1[i][j][1] = incedence[i][j];
            }
  //          incedence[j][i][k] = edges[i][j];
          //}
        }
    //System.out.println();       
  }
  
  //System.out.println();   
  //System.out.println();   
  
  for(int i = 0 ; i < counter ; i++){
        for( j = 0 ; j < (nnlen * karkas) ; j++){
          for(int k = 0 ; k < karkas ; k++){
            //System.out.print("    "+incedence1[i][j][k]);  //scgEdges[0][0][1] = 1 indecates "what is my name"
          }
          
    }
    //System.out.println();   
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
  //System.out.println();
  nKarkas = karkas;
  //System.out.println("***nKarkas="+nKarkas);
      return incedence;
    
    }
    
    public int[][] findsubSCG1(int[][] scg, String[] v, String[] n, int nlength, int karkas, int nnl){
      nlength = nnl;
    //  int karkas = 2 ; // whose and what
      int[][] subKasko = new int[karkas*nlength][karkas*nlength];
      int[][] subKe = new int[karkas*nlength][karkas*nlength];
      int[][][] subGraph = new int[2][karkas*nlength][karkas*nlength]; // Assuming 2 columns in each subgraph for 2 karkas (whose and what)
      int[][] incedent = new int[karkas*nlength][karkas*nlength];
      int count = 0 ;
      int temp = 0 ;
      int count1 = 0;
  subKasko = checkKasko1(scg, karkas, n, nlength);   // checking grammar rules of whose (KASKO)
      subKe = checkKe1(scg, karkas, n,nlength); // checking grammar rules of what (KE)
      //System.out.println("MAIN SUB GRAPH OF CONCEPTURAL GRAPH : ");
      
      
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
            //System.out.println("k="+k);
          }
        }
      }
      
      for(int i = 0 ; i < (karkas*nlength) ; i++){
        for(int j = 0 ; j < (karkas*nlength) ; j++){
          //System.out.print("  "+incedent[i][j]);
        }
        //System.out.println();
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
      subKe = checkKe1(scg, karkas, n,nlength); // checking grammar rules of what (KE)
      for(int i = 0 ; i < 1 ; i++){
        //count = 0 ;
        while(temp < karkas){
          count = 0;
          for(int j = 0 ; j < karkas; j++){
            for(int k = 0 ; k < n.length ; k++){
              if(subKasko[0][k] == 1 && temp != 1){   // subkasko and subgraph are transpose  because 0 indicates the first karka i.e whose
                subGraph[count][k][0] = 1;
                //temp++;
                //System.out.println("subgraph["+count+"]["+k+"]["+0+"]="+subGraph[count][k][0]+"  temp="+temp);
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
              //  temp++;
                //System.out.println("subgraph["+count+"]["+k+"]["+1+"]="+subGraph[count][k][1]+"   ketemp="+temp);
                //temp++;
                for(int l = 0 ; l < n.length ; l++){
                  if(l != k ){
                    subGraph[count][l][1] = 1;
                    
                    //System.out.println("subgraph["+count+"]["+k+"]["+1+"]="+subGraph[count][k][1]+"   ketemp="+temp);
                    temp++;
                  }
                }
              
              }
            //  count++;
            }
            count++;
          }//count++;
        }
        //count++;
      }
      for(int i = 0 ; i < count ; i++){
        for(int j = 0 ; j < n.length; j++){
          for(int k = 0 ; k < karkas ; k++){
            //System.out.println("subGraph["+i+"]["+j+"]["+k+"]="+subGraph[i][j][k]);
          }
        }
        //System.out.println();
      }
      return subGraph;
    }
  
  public int[][] checkKasko1(int[][] scg1, int krk, String[] n1, int nounlength){
  //System.out.println("It's checkKasko1");
      int[][] nntg;
      nntg = scg1;
      int count = 0;
      //int flag = 0;
        int[][] subkasko = new int[1][nounlength];
    for(int i = 0 ; i < (krk*nounlength) ; i++){
        for(int j = 0 ; j < (krk*nounlength) ; j++){
          if(i%2 == 0 ){     // even edges for kasko i.e. whose
            //System.out.println("scg1["+j+"]["+i+"]="+scg1[j][i]+" n1.length="+nounlength+"  v1.length="+krk);
            if(scg1[j][i] == 1 ){
              //System.out.println("hi nountag["+count+"]="+nounTags[count]);
              if(nounTags[count].equals("NN") || nounTags[count].equals("NNP")){
                subkasko[0][count] = 1;
                //System.out.println("subkasko["+0+"]["+count+"]="+subkasko[0][count]);
                
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
              
        //    }
          }
        }
      }
      return nntg;
  
  }
  
  
  public int[][] checkKe1(int[][] scg1, int krk, String[] n1, int nounlength){
  //System.out.println("It's checkKe1");
      int[][] nntg;
      nntg = scg1;
      int count = 0;
        int[][] subke = new int[1][nounlength];
    for(int i = 0 ; i < (krk*nounlength) ; i++){
        for(int j = 0 ; j < (krk*nounlength) ; j++){
          if(i%2 != 0){     // even edges for ke i.e. what
            //System.out.println("scg1["+j+"]["+i+"]="+scg1[j][i]+" n1.length="+nounlength+"  v1.length="+krk);
            if(scg1[j][i] == 1){
              //System.out.println("hi nountag["+count+"]="+nounTags[count]);
              if(nounTags[count].equals("NNP")){
                subke[0][count] = 1;
                //System.out.println("subke["+0+"]["+count+"]="+subke[0][count]);
                
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
              
        //    }
          }
        }
      }
      return nntg;
  
  }
  
    public int[][] checkKasko(int[][][] scg1, String[] v1, String[] n1){
      //System.out.println("It's checkKasko");
      String[] nntg;
    //  nntg = getNounTag();
      int[][] subkasko = new int[1][n1.length];
      for(int i = 0 ; i < (v1.length*n1.length) ; i++){
        for(int j = 0 ; j < (v1.length*n1.length) ; j++){
          //System.out.println("scg1["+i+"]["+j+"]["+0+"]="+scg1[i][j][0]);
          if(scg1[i][j][0] == 1 ){   // for first karka i.e whose
          //System.out.println("hi nountag["+j+"]="+nounTags[j]);
            if(nounTags[j].equals("NN") || nounTags[j].equals("NNP")){
              subkasko[0][j] = 1;
              //System.out.println("subkasko["+0+"]["+j+"]="+subkasko[0][j]);
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
              //System.out.println("subke["+1+"]["+j+"]="+subke[1][j]);
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
          //System.out.println("hi");
          for(int l = 0 ; l < rows*krks ; l++){
            for(int m = 0 ; m < rows*krks ; m++){
              if(fg1[prs][m][l] != 0){
                //System.out.println("hi ello rows ="+rows+" krks ="+krks+" count="+count+" l="+l+"  m="+m);
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
            //System.out.println("hi hello l = "+l);
          //for(int m = 0 ; m < krks ; m++){
          for(int t= 0 ; t < krks ; t++){
            //System.out.println("hi hello l = "+l+"  del["+t+"]="+deletecol[t]);
            //if((l+krks) == deletecol[t]){
              //System.out.println("Welcome");
              if(l%2 == 0 ){
                  fout.write("     fg1["+prs+"]["+l+"]["+0+"]="+fg1[prs][l][deletecol[t]]);
                  //break;
                }
                else{
                  fout.write("     fg1["+prs+"]["+l+"]["+1+"]="+fg1[prs][l][deletecol[t]]);
                  //break;
                }
            //  }
              //}
              //else{
                //t++;
              //}
            }
            fout.write("\n");
        }
        fout.write("\n **********************************************   ");
        fout.close();
    } 
    catch(IOException ie){}
    finally { 
         // fout.close();
  }
    }
    
}
