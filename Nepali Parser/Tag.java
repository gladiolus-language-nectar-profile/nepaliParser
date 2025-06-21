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

public class Tag
{
    public static int totaltag = 41;
    public static int maxwords = 500000;
    public static int maxobswords = 300000;
    public static double[][] transitionMatrix = new double[totaltag][totaltag];
    public static double[][] observationMatrix = new double[totaltag][300000];
    public static String[] Words = new String[maxwords];
    public static int noWords = 0;
    public static int noTags = 0;
    public static String thread;
    public static int sentnoWords = 0;
    public static String[] sentWords = new String[maxobswords];
    public static String[] sentTotalwords = new String[2*maxobswords];
    public static String[] OriOutput = new String[300000];
    public String[] Finalstart  = new String[50000];
    public static double[][] ExtractedFeatures;
    public BufferedWriter annFile;
    public  Tag(String s, String[] f){
       // String RegionMatchesDemo = RegionMatchesDemo(s,f);
    }
   public  Tag(){}
   public static void main(String args[])throws IOException 
   {
       String glyph = "\u0926\u0947\u0916\u093F";
      // String[] tagset = {"CCD","CCS","DMD","DMR","DMS","INFV","INJ","INTF","INVF","JJ","NEG","NN","NNP","NSN","NST","NVG","PRD","PRI","PRL","PRP","PRQ","PSP","PUNC","QTC","QTF","QTO","RB","RPD", "VAUX","VINF","VM","VN","VNF","VNG"};       
       String[] tagset = {"NN","NNP","PP","PP$","PPR","DM","DUM","VBF","VBX","VBI","VBNE","VBKO","VBO","JJ","JJM","JJD","RBM","RBO","INTF","PLE","PLAI","PKO","POP","CC","CS","UH","CD","OD","HRU","QW","CL","RP","DT","UNW","FW","YF","YM","YQ","YB","FB","SYM"};
       for(int i = 0 ; i < totaltag ; i++)
             System.out.println("tagset["+i+"]="+tagset[i]);
       Tag t = new Tag();
       
       t.readFile(args[0], args[1], args[2], args[3], glyph, tagset);
       
       transitionMatrix = t.generateTM(Words, noWords, tagset);
       observationMatrix = t.generateOM(sentWords, sentnoWords , tagset, Words);
       OriOutput =  t.getOriginalOutput(sentTotalwords, 2*sentnoWords, tagset, ExtractedFeatures);
//       annFile.close();
       //transitionMatrix = t.generateTM();
     //  thread = t.RegionMatchesDemo(glyph, Words);
   }
   public void readFile(String fileName, String obsName, String outputName, String anninput, String gl, String[] tgs1) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
	BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(obsName), "UTF-8"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");
        BufferedWriter fout = new BufferedWriter(writer);
        OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(anninput), "UTF-8");
        BufferedWriter fout1 = new BufferedWriter(writer1);
       // String is = br.readLine();
        String vibhakti1 = "hi";
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
            String sent = br1.readLine();
            //fout.write(line);
           // String ln = line;
            //// System.out.println(ln);
           // while (line != null) {
               // System.out.println("hi");
            
            //sb.append(line);
            //sb.append("\n");
           
           // // System.out.println("line length = "+line.length());
           //if(line==null)
             //   break;
            String[] words = new String[300000];// = line.split(" ");//those are your words
            String[] sentwords = new String[300000];
            String[] sentwords1 = new String[300000];
            String ws;
            String obws;
            int nowords = 0;
            int obsnowords = 0;
           // String[] big = (String [])join(words,nwords);
            //appendWords.add(words);
           // StringTokenizer st = new StringTokenizer(is);
            while (line != null) {
	           //fout.write(line);
			StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            ws = st.nextToken();
                            words[nowords]=ws;
                            nowords++;
                        }
                line = br.readLine();
          //  }
            /*char ch[]= new char[line.length()];      //in string especially we have to mention the () after length
            for(int i=0;i<line.length();i++)
            {
                ch[i]= line.charAt(i);
                if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                    nowords = nowords;
            }*/
            
            
            for(int i = 0 ; i < nowords; i++){
                 // System.out.println("words["+i+"]="+words[i]);
                // fout.write(words[i]);
                 //fout.newLine();
                 }
             //line = br.readLine();//vibhakti1 = line;
             // System.out.println("line="+line+"    words="+Words+"    noWords ="+nowords+"    noTags = "+noTags);
             Words = words;
             noWords = nowords;
             noTags = nowords/2;
            //if (line != null)
            //    RegionMatchesDemo(gl,words);
            //// System.out.println(words);
            //fout.write(gl);
            break;
        }
        int j=0;
        while (sent != null) {
	           //fout.write(line);
			StringTokenizer st1 = new StringTokenizer(sent);
                        while (st1.hasMoreTokens()) {
                           if((obsnowords%2) == 0){
	                            obws = st1.nextToken();
        	                    sentwords1[obsnowords]=obws;
        	                    sentwords[j]=obws;
                	            fout.write("\n");
                        	    fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	    j++;
                            	    
                            }
			    else{
				    obws = st1.nextToken();
	                            sentwords1[obsnowords]=obws;
	                            // System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }
	                    obsnowords++;
                           /* else{
                                    obws = st1.nextToken();
        	                    sentwords1[obsnowords]=obws;
        	                    // System.out.println("sentwords1["+obsnowords+"]="+sentwords1[obsnowords]);
                	            fout.write("\n");
                        	    fout.write(sentwords1[obsnowords]);
                            	  //  obsnowords++;
                            }*/
                            //obsnowords++;
                        }
                sent = br1.readLine();
                sentWords = sentwords;
                sentTotalwords = sentwords1;
                sentnoWords = j;
               //	 observationMatrix = generateOM1(sentWords, sentnoWords , tgs1, Words);
          //  }
       // return sb.toString();
         }
         annFile = fout1;
//         generateANNdatabase(fout1);
        // sentwords1 = getOriginalOutput(sentwords,obsnowords);
        // fout.write("\n");
         //fout.write(sentwords1[obsnowords]);
         for(int i = 0 ; i < j; i++){
         	//// System.out.println("sentwords1["+i+"]="+sentwords1[i]);
		// System.out.println("sentwords["+i+"]="+sentwords[i]);         	
         }
         for(int i = 0 ; i < 2*j; i++){
         	// System.out.println("sentwords["+i+"]="+sentwords[i]);
		// System.out.println("sentwords1["+i+"]="+sentwords1[i]);         	
         }
        } finally {
        br.close();
        fout.close();
       // fout1.close();
        }
    }
    //public void generateANNdatabase(String annfile){
    //	annfile.write
    //}
    public String[] getOriginalOutput(String[] sts, int nosts, String[] tg, double[][] efeatures) throws IOException {
    	int ind = 0;
    	int[] output = new int[nosts];
    	int[][] annoutput = new int[nosts][totaltag];
   // 	OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(outputName1), "UTF-8");
   
  //      BufferedWriter fout1 = new BufferedWriter(writer1);
	for(int i = 0 ; i < nosts; i++){
                  System.out.println("sts["+i+"]="+sts[i]);
         }
    	String[] Originaloutput = new String[nosts/2];
	// System.out.println("nosts="+nosts);
         int j=0;
         for(int count=0  ; count <= nosts ; count++){
           if((count%2) != 0){
                Originaloutput[j] = sts[count];
                System.out.println("Originaloutput["+(j)+"]="+Originaloutput[j]);
            //    fout1.write("\n");
            //    fout1.write(Originaloutput[j]);
                j++;
           }
           //else wordTag[i]= "SYM";
       }
       //System.out.println("j="+j);
       for(int i = 0 ; i < j/*nosts/2*/ ; i++){
       	   for(int it = 0 ; it < totaltag ; it++){
	       		if(Originaloutput[i].equals(tg[it])){
	       			output[i] = it+1;
	       			//// System.out.println("output["+i+"]="+output[i]);
	       			 System.out.println(output[i] +"            "+tg[it]);
	       			 //annoutput[i][it] = output[i]; 
	       			 annoutput[i][it] = 1; 
//	       			System.out.println();
	       			//// System.out.print("\t"+(i+1));	       			
	       		}
	       		
	   	//// System.out.println("tg["+it+"]="+tg[it]);
	   	}
	}
	System.out.println("FEATURE EXTRACTION:"+nosts);
	annFile.write("\n INPUT DATABASE: ");
	annFile.write("\n");
	for(int x = 1 ; x < nosts/2 ; x++){
		for(int m = 0 ; m < 3*totaltag ; m++)
			annFile.write(efeatures[x][m]+" ");
		for(int y = 0 ; y < totaltag ; y++){
			System.out.println("annoutput["+x+"]["+y+"]="+annoutput[x][y]);
		//	annFile.write("output");
			annFile.write(annoutput[x-1][y]+" ");
			//annFile.write("\n");
		}
		//annFile.write("\n");
		annFile.write("\n");		
	}
	annFile.close();
	return Originaloutput;
    }
   public double[][] generateTM(String[] wds, int nowds, String[] tgs){
              int[][] freqDist = new int[totaltag][totaltag];
       double[][] probDist = new double[totaltag][totaltag];
       //String[] wordTag = "SYM";
       String[] wordTag = new String[nowds];
       for(int p=0; p<nowds;p++)
            wordTag[p] = " NULL ";
       // System.out.println("nowds="+nowds+"totaltag="+totaltag);
       int i=0, index=0;
      // for(int j = 0 ; j < nowds; j++)
                 // System.out.println("words["+j+"]="+wordTag[j]);
       for(int count=0  ; count <= nowds ; count++){
           if((count%2) != 0){
                wordTag[i] = wds[count];
                 System.out.println("wordTag["+(i)+"]="+wordTag[i]);
                i++;
           }
           //else wordTag[i]= "SYM";
       }
       int t0 =0, t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0, t9 = 0, t10 = 0, t11 = 0, t12 = 0, t13 = 0, t14 = 0, t15 = 0;
       int t16 =0, t17 = 0, t18 = 0, t19 = 0, t20 = 0, t21 = 0, t22 = 0, t23 = 0, t24 = 0, t25 = 0, t26 = 0, t27 = 0, t28 = 0, t29 = 0, t30 = 0, t31 = 0;
       int t32 =0, t33 = 0, t34 = 0, t35 = 0, t36 = 0, t37 = 0, t38 = 0, t39 = 0, t40 = 0, t41 = 0;      
       for(int s=0  ; s < nowds/2 ; s++){
         //  if((count%2) != 0){
            //    wordTag[i] = wds[count];
                // System.out.println("wordTag["+(s)+"]="+wordTag[s]);
             //   i++;
        //   }
           //else wordTag[i]= "SYM";
       }
        
       for(int j = 0 ; j < (nowds/2 - 1); j++){
           int item = j+1;
           int cindex = 0;
        for(int it = 0 ; it < totaltag; it++){
           // // System.out.println("Tagset["+it+"]="+tgs[it]+"            wordTag["+j+"]="+wordTag[j]);
           if(wordTag[j].equals(tgs[it])){
               index = it;
               // System.out.println("Index="+index+"   j="+j+"  item="+item);
           }
            if(wordTag[item].equals(tgs[it])){
               cindex = it;
               // System.out.println("Index="+index+"   j="+j+"  item="+item+" cindex="+cindex);
           }
        }
        // System.out.println("hi freq");
        //// System.out.println("Index="+index+"freqDist["+index+"]["+0+"]="+freqDist[index][12]+"wirdTag["+(j)+"]="+wordTag[j]);
               freqDist[index][cindex]++;
              
       }
       // System.out.println("Frequency Distribution");
       int sum = 0;
       for(int m = 0 ; m < totaltag ; m++){
           for(int n = 0 ; n < totaltag ; n++){
               // System.out.print("\t"+freqDist[m][n]);
               sum = sum + freqDist[m][n];
           }
           // System.out.println();
       }
       // System.out.println("Total Frequency = "+sum);
       
       // Generating Transition Probability table
       
       for(int m = 0 ; m < totaltag ; m++){
           for(int n = 0 ; n < totaltag ; n++){
              // // System.out.println("prob dist["+m+"]["+n+"] = "+probDist[m][n]);
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
        System.out.println("Transition Probability Table : ");
       for(int m = 0 ; m < totaltag ; m++){
           for(int n = 0 ; n < totaltag ; n++){
                System.out.print("\t"+probDist[m][n]);
           }
            System.out.println();
       }
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
       // System.out.println("Totaltags="+totaltag);
   	for(int i = 0 ; i < sentnowds ; i++){
   		count = 0;
   		//indx = 0;
	   	// System.out.println("wds length = "+sentnowds+"owds["+i+"]="+owds[i]);
	   //	 do{
	 		
	 		//for(int l = 0 ; l < fullinx.length ; l++){
	 			//if(i != 0){
		 		pos = RegionMatchesDemo(owds[i], word); 
		 		//}
	 			for(int p = 0 ; p < 5; p++){
	 				if( pos[p] != null){
	 					//System.out.println("pos["+p+"]="+pos[p]);
	 					//indx = 0;
		 				for(int q = 0 ; q < tgs.length; q++){
		 					if(pos[p].equals(tgs[q])){
		 						psn[p] = q;
		 						obsfreMatrix[q][i]++;
		 						if(indx == 1 ){
		 							newm[q][0]=1;
		 							//sum1++;
		 							//System.out.println("newm["+q+"]["+0+"]="+newm[q][0]);
		 						}
				 				//System.out.println("psn["+p+"]="+psn[p]);
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
		 				//System.out.println("ENTRY "+i+"after YM ="+indx);
		 				//break;
			 		}
			 	}
			 	
   		sum++;
   	}
   	for(int m = 0 ; m < totaltag ; m++ ){
   		for(int n = 1 ; n < sentnowds+1 ; n++){
   			newm[m][n] = obsfreMatrix[m][n-1];
   		}
   		// System.out.println();
   	}
   	
   	for(int m = 0 ; m < totaltag ; m++ ){
   		for(int n = 1 ; n < sentnowds+1 ; n++){
   			newm[m][n] = newm[m][n]/(sum);
   			//obsMatrix[m][n] = obsfreMatrix[m][n]/sum;
   		}
   		// System.out.println();
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
 			//System.out.println("***pos1["+q2+"]="+tgs[q2]+"   word[1]="+Words[1]);
		if(Words[1].equals(tgs[q2])){
			//psn[s] = q1;
			//System.out.println("***pos1["+q2+"]="+tgs[q2]);
			newm[q2][0]++;
			sum1++;
		}
	}
   	//for(int m = 0 ; m < g ; m++ ){
   		for(int n = 0 ; n < tgs.length ; n++){
   			newm[n][0] = newm[n][0]/(sum1);
   			
   		}
   	// System.out.println();
   	//}
   	/*for(int j = 0 ; j < wds.length; j++){
   	for(int it = 0 ; it < totaltag; it++){
           // // System.out.println("Tagset["+it+"]="+tgs[it]+"            wordTag["+j+"]="+wordTag[j]);
           if(wds[j].equals(tgs[it])){
               index = it;
               // System.out.println("Index="+index+"   j="+j+"  item="+item);
           }
            if(wordTag[item].equals(tgs[it])){
               cindex = it;
               // System.out.println("Index="+index+"   j="+j+"  item="+item+" cindex="+cindex);
           }
        }*/
        double value;
        double[] temp = new double[3*totaltag];
        ExtractedFeatures = new double[sentnowds][3*totaltag];
        for(int p = 1 ; p < sentnowds ; p++ ){
   		for(int q = 0 ; q < totaltag ; q++){
   			if(newm[q][p] > 0.0000005){
				 System.out.print("\t newm["+q+"]["+p+"] = "+newm[q][p]);
				 value = newm[q][p];			 
				 temp = getFeature(sentnowds, totaltag, p, q, value);
				 for(int m = 0 ; m < 3*totaltag ; m++){
				 	System.out.println("temp["+m+"]="+temp[m]);
				 	ExtractedFeatures[p][m] = temp[m];
				 }
   			}	
			
   			 //System.out.print("\t"+obsMatrix[m][n]);
   		}
   		System.out.println();
   		System.out.println();   		
   	}
   	
        System.out.println("\n OBSERVATION PROBABILITY TABLE : ");
   	for(int m = 0 ; m < totaltag ; m++ ){
   		for(int n = 0 ; n < sentnowds ; n++){
				 System.out.print("\t"+newm[m][n]);
   			 //System.out.print("\t"+obsMatrix[m][n]);
   		}
   		System.out.println();
   	}
   	return obsMatrix;
   }
   
   public double[][] getTransitionMatrix(){
   	return transitionMatrix;
   }
   
   public double[] getFeature(int stwd, int ttag, int row, int col, double obsfeature ){
   	System.out.println("\n FEATURES PROBABILITY TABLE : ");
   	double[][] trans = new double[ttag][ttag];	
	double[][] feature = new double[stwd][ttag];
	feature[row][col] = obsfeature;
	double[][] featuretrans = new double[stwd][ttag*2];
	double[][] extractedFeatures = new double[stwd][ttag*3];
	double[] tem = new double[ttag*3];
	//ExtractedFeatures = new double[stwd][ttag*3];
	System.out.println("stwd = "+stwd);
	trans = getTransitionMatrix();
	for(int m = 0 ; m < 2*ttag ; m++){
		if(m < ttag)
			featuretrans[col][m] = trans[col][m];
		else
			featuretrans[col][m] = trans[m-ttag][col];
	}
	
	for(int p = 0 ; p < 2*ttag ; p++){
		//System.out.println("featuretrans["+col+"]["+p+"]="+featuretrans[col][p]);
	}
   	//for(int m = 0 ; m < stwd ; m++ ){
   	for(int n = 0 ; n < ttag ; n++){
		// System.out.print("\t"+feature[row][n]);
	  			 //System.out.print("\t"+obsMatrix[m][n]);
   	}
   	System.out.println();
   	//}
   	// FEATURES 1. EMISSION COLUMN 2. TRANSITION ROW 3. TRANSITION COL
   	//for(int x = 0; x < stwd ; x++){
   	for(int y = 0 ; y < 3*ttag ; y++){
   		if(y < ttag)
			extractedFeatures[row][y] = feature[row][y];
		else
			extractedFeatures[row][y] = featuretrans[col][y-ttag];
   	}
   //	ExtractedFeatures = extractedFeatures;
   	/*try{
	   	annFile.write("\n");
	}
	catch(IOException ie){}*/
   	for(int x = 0 ; x < 3*ttag ; x++){
   		System.out.println("extractedfeatures["+row+"]["+x+"]="+extractedFeatures[row][x]);
   		tem[x] = extractedFeatures[row][x];
   	//	ExtractedFeatures[row][x] = extractedFeatures[row][x];
   		System.out.println("tem["+x+"]="+tem[x]);
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
   	System.out.println("Finalstart["+ct+"]="+Finalstart[ct]);
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
        // System.out.println("file length = "+file.length);
     //    System.out.println("Find Me = "+findMe+"no of Tags="+noWords);
        
        boolean foundIt = false;
        for(int i = 0; i < noWords; i++) {
          //  System.out.println("i ="+i+"file["+i+"]="+file[i]);
           if(file[i].equals(findMe)) {
              // System.out.println("check");    
              // System.out.println(" g="+g);   
              foundIt = true;
          //   System.out.println("file["+(i+1)+"]="+file[i+1]);

            	
              index = i+1;
              positions[count] = file[index];
              System.out.println("positions["+count+"]="+positions[count]+" Index = "+index);
              if(positions[count].equals("YF")){
              	if(i < (noWords-2))
	              	start[count] = file[index + 2]; 
	             // 	System.out.println("start["+count+"]="+start[count]);
	              	getStart(start[count], count);
              }
              count++;
              
              //g = searchMe.substring(i, i + findMeLength);
             // break;
          }
          
           //return searchMe.substring(i, i + findMeLength);
          // break;
        }
        if (!foundIt){
            // System.out.println("No match found.");
           // return "NULL";
           return positions;
        }
        // System.out.println("INDEX="+index);
       // return file[index];
       return positions;
    }
}
