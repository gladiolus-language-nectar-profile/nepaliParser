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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
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
//Charset UTF8_CHARSET = Charset.forName("UTF-8");
import java.nio.charset.Charset;



public class ReadXMLFile {
  static String rootverb;
  String[] verbs = {"VF","VINF","VAUX","VMNE","VMKO","VMO"};
 // static String[] rootverbs = {"दियो"};
  static String[] rtverbs = {"दिन्छ","दियो", "दिए", "जम्मा","देख्यो", "पठाउँछ", "पठाउँछ", "खान्छ","खान्छ", "खाइरह","खानेछु", "खानेछौ", "खान्छे","लिएर","FAULT KARAKA CHART"};
  static int NoVerbs = rtverbs.length;
  ////System.out.println("number of verbs :"+NoVerbs);
  static String[] rootverbs = new String[NoVerbs];
  static String prs;
  static int totalkarakas = 9;
//  rootverbs[0]="दियो";
  static String[][] prslist = new String[totalkarakas*(NoVerbs+1)][3];
  static String vbhkt = " ले";

  //static int NoVerbs = rtverbs.length;
  static String[][] vbhkts = new String[totalkarakas*(NoVerbs+1)][3];
  static String krk;
  static int vbindex;
  static String[][] krks = new String[totalkarakas*(NoVerbs+1)][3];
  static int index = 0;
  static int nvb = 0;
  static int id = 0;
  static String[] words;
  static String[] wordnet;
  public static String Genericmarker = "abc";
  public static int k7tindex = 100;
  public static int k2pindex = 100;
//  Charset UTF8_CHARSET = Charset.forName("UTF-8");

public static void main(String argv[]) {
    try {
    	File fXmlFile = new File("E:\\Nepali Parser-19May2025\\Nepali Parser\\cars.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    ReadXMLFile rxf = new ReadXMLFile();
	    rxf.readFile1(argv[0]);
	   // rxf.readFile1("D:\\Archit Folder\\NLP\\Newset\\wordnet.txt");
	    index = 0;

	
	     NoVerbs = argv.length-1;
   // Charset UTF8_CHARSET = Charset.forName("UTF-8");
  //  rootverb = rxf.getRootverb();
    rootverb =  doc.getDocumentElement().getNodeName();
    //System.out.println("NoVErbs="+NoVerbs+" id="+id+"  nvb="+nvb);

    rootverbs[nvb]=rootverb;
   // rxf.displayString(vbhkt);
    //rxf.displayString(rootverbs[nvb]);

    //System.out.println("Root element :" + rootverb);
    NodeList nList = doc.getElementsByTagName("vibhakti");
    //System.out.println("----------------------------");
    //System.out.println("nList.getLength: "+nList.getLength());

    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
       
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            //System.out.println("Presence : "
                            //   + eElement.getAttribute("presence"));
            prs = eElement.getAttribute("presence");
            //System.out.println("****************************************************"+temp);
            //System.out.println("****************************************************");
            prslist[index][0] = prs;
            //System.out.println("prslist["+index+"][0]= "+prslist[index][0]);
            //System.out.println("Karaka : "
                             //  + eElement.getAttribute("karaka"));
            krk = eElement.getAttribute("karaka");
            krks[index][1] = krk;
                               //  .item(0).getTextContent());
            //System.out.println("Form : "
                        //       + eElement.getAttribute("Form"));
            //System.out.println("Vibhakti : "
                           //    + eElement.getElementsByTagName("vibhakti"));
//                                 .item(0).getTextContent());
            vbhkt =  eElement.getTextContent();       
            vbhkts[index][2] = vbhkt;
        }
       // if(index < 4)
	        index++;
	//else
	//	break;
    }
//    index++;
	    } catch (Exception e) {
    		e.printStackTrace();
	}
  }
  public static void main1(String argv[], String[] prenn, String wordnetfile) {
    try {
    File fXmlFile = new File("E:\\Nepali Parser-19May2025\\Nepali Parser\\cars.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    doc.getDocumentElement().normalize();
    int flag = 0;
 //   //System.out.println("hi GM");
    ReadXMLFile rxf = new ReadXMLFile();
    rxf.readFile1(wordnetfile);
    index = 0;
    
    //System.out.println("number of verbs "+NoVerbs);
    String genericmarker = "abc";
    
    vbindex = getVerb(argv[0]);
    System.out.println("verb index ="+vbindex);
    
    if (vbindex == 0){
    	id = 0; //main0(rtverbs[vbindex]);
    }
    else if(vbindex == 1 || vbindex == 4){
    	id = 1; //main11(rtverbs[vbindex]);
    }
    else if(vbindex == 2){
    	id = 2; //main2(rtverbs[vbindex]);
    }
    else if(vbindex == 3){
    //	id = main100(rtverbs[vbindex]);
    	id = 3;
    } 
    else if(vbindex == 5 || vbindex == 6 || vbindex == 11 || vbindex == 14){
         //System.out.println("place hi GM prenn= "+prenn[0]);
         try{
	         for(int l=0;l<wordnet.length;l++){
	         	for(int m = 0 ; m < prenn.length ; m++){
			         //System.out.println("hi GM word["+l+"]= "+words[l]+"  prenn["+m+"]="+prenn[m]+"   flag="+flag);         
		        	 if(words[l].equals(prenn[m]) ) {  
		        	 	//System.out.println("generic marker ="+genericmarker);
			         	genericmarker = wordnet[l];
			         	//System.out.println("generic marker ="+genericmarker);
			         	if(genericmarker.equals("TIME")){
			         		k2pindex = 0;
			         		k7tindex = m;
			         	} 
			         	//else{
			         	//	continue;
			         	//}
			         	System.out.println("generic marker for time ="+genericmarker+"   k2pindex="+k2pindex);			         	
		        	 	if(genericmarker.equals("PLACE") && flag == 0){
			        	 	
			        	 	k2pindex = m;
			        	 	System.out.println("hi its genericmarker place k2pindex="+k2pindex);
		         			id = 5;
		         			flag = 1;
			         		break;
		        	 	} 
	        	 		else{
	        	 			if(flag ==0 && (vbindex == 6 || vbindex == 5))
			        	 		id = 6;
			        	 		//break;
	        	 		}
			         }
			}
		 }
	}
	catch(NullPointerException e){
		////System.out.print("NullPointerException Caught");
		if(flag == 0)
			id = 10;
	}
    	//id = 6;
    }
    else if(vbindex == 7 || vbindex == 8 || vbindex == 9 || vbindex == 13 ||  vbindex == 14){
    //System.out.println("time hi GM prenn= "+prenn[1]);
         try{
	         for(int l=0;l<wordnet.length;l++){
	                 System.out.println("hi GM word["+l+"]= "+words[l]);         
	                 for(int m = 0 ; m < prenn.length ; m++){
		        	 if(words[l].equals(prenn[m]) ) {  
			         	genericmarker = wordnet[l];
			         	if(genericmarker.equals("PLACE")){
			         		k2pindex = 1;
			         		k7tindex = 0;
			         	} 
			         	System.out.println("generic marker ="+genericmarker+"    k7tindex="+k7tindex);
			         	if(genericmarker.equals("TIME") && flag == 0){
			         		k7tindex = m;
				         	System.out.println("hi its genericmarker k7tindex="+k7tindex);
		         			id = 8;
		         			flag = 1;
		         			break;
		        	 	} 
		         		else{
		         			if(flag == 0)
				         		id = 7;
	        		 	}
			         }
			 }
		 }
	}
	catch(NullPointerException e){
		
		////System.out.print("NullPointerException Caught");
		if(flag == 0)
			id = 7;
	}
    //	id = main100(rtverbs[vbindex]);
    	//id = 7;
    } 
    else if(vbindex == 10 || vbindex == 11 || vbindex == 12)
    {
    	id = 9;
    }
    else if(vbindex == 13){
        id = 8;
    }
    else{
    	id = 10;
    }
    Genericmarker = genericmarker;
    //int id;
    //id = main2(vbindex);
    NoVerbs = argv.length-1;
   // Charset UTF8_CHARSET = Charset.forName("UTF-8");
  //  rootverb = rxf.getRootverb();
    rootverb =  doc.getDocumentElement().getNodeName();
    //System.out.println("NoVErbs="+NoVerbs+" id="+id+"  nvb="+nvb);

    rootverbs[nvb]=rootverb;
   // rxf.displayString(vbhkt);
    //rxf.displayString(rootverbs[nvb]);

    //System.out.println("Root element :" + rootverb);
    NodeList nList = doc.getElementsByTagName("vibhakti");
    //System.out.println("----------------------------");
    //System.out.println("nList.getLength: "+nList.getLength());

    for (int temp = 0; temp <nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
       
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            //System.out.println("Presence : "
                            //   + eElement.getAttribute("presence"));
            prs = eElement.getAttribute("presence");
            //System.out.println("****************************************************"+temp);
            //System.out.println("****************************************************");
            prslist[index][0] = prs;
            //System.out.println("prslist["+index+"][0]= "+prslist[index][0]);
            //System.out.println("Karaka : "
                            //   + eElement.getAttribute("karaka"));
            krk = eElement.getAttribute("karaka");
            krks[index][1] = krk;
                               //  .item(0).getTextContent());
            //System.out.println("Form : "
                           //    + eElement.getAttribute("Form"));
            //System.out.println("Vibhakti : "
                           //    + eElement.getElementsByTagName("vibhakti"));
//                                 .item(0).getTextContent());
            vbhkt =  eElement.getTextContent();       
            vbhkts[index][2] = vbhkt;
        }
       // if(index < 4)
	        index++;
	//else
	//	break;
    }
//    index++;
    } catch (Exception e) {
    e.printStackTrace();
    }
  }
  public static String getGM(){
  	return Genericmarker;
  }
  public static int getk2pindex(){
  	return k2pindex;
  }
  public static int getk7tindex(){
  	return k7tindex;
  }
  
  public static void displayString(String vb){
    try{
	    	File outputfile = new File("E:/bndwimage.txt"); 
	    	OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputfile,true), "UTF-8");
	        BufferedWriter fout = new BufferedWriter(writer);
	        fout.write("Hi its displayString in ReadXMLFile");
	        fout.write("\n\t  vb="+vb);
	   	fout.write("\n");
    	   	fout.close();
    } 
    catch(IOException ie){}
    finally {	
	       // fout.close();
	}
    }
 // public class Main {
  public static int getVerb(String st) throws Exception{
  	int t = 0;
  	//System.out.println(" rtverbs.len="+(rtverbs.length-1));
  	String stng ;
  	for(int i = 0 ; i < rtverbs.length ; i++){
  		byte[] utf1 = rtverbs[i].getBytes("UTF-8");
  		stng = new String(utf1,"UTF-8");
  		try{
  			byte[] utf2 = st.getBytes("UTF-8");
  			st = new String(utf2,"UTF-8");
  			//System.out.println("getVerb st = "+st+"    verb="+stng);
  			if(st.equals(stng)){
  				//System.out.println("get verb Good Job");
			    	t=i;
			    	break;
  			}
  			else{
  				//System.out.println("get verb Bad Job");
			    	t = rtverbs.length-1;
  			}
  		}
		catch(Exception e){}
		finally {	
		       // fout.close();
		}
		
  	}
  	//System.out.println("get verb t = "+t);
  	return t;
  	//return rtverbs[t];
  }
  
  
  // verb "दिन्छ"
    public static int main0(String string) throws Exception {
//    String string = "abc\u5639";
	int t = 0;
	String stng = "दिन्छ";
        displayString(string);
        byte[] utf9 = rtverbs[0].getBytes("UTF-8");
        stng = new String(utf9,"UTF-8");
        displayString(stng);


	try{
	    byte[] utf8 = string.getBytes("UTF-8");
    	    string = new String(utf8, "UTF-8");
	    //System.out.println(string);
	  //  displayString(string);
	    //byte[] utf9 = stng.getBytes("UTF-8");
	    //stng = new String(utf9, "UTF-8");
	    //displayString(stng);
	    //System.out.println("string = "+string+"  str="+stng);
	    if(string.equals(stng)){
	    	//System.out.println("Good Job");
	    	t=0;
	    }
	    else{
	    	//System.out.println("Bad Job");
	    	t = rtverbs.length-1;
	    }
	}
	catch(Exception e){}
	finally {	
	       // fout.close();
	}
	return t;
  }
  
  
  
  // verb "दियो"  
  public static int main11(String string) throws Exception {
//    String string = "abc\u5639";
	int t = 0;
	String stng = "दियो";
        displayString(string);
        byte[] utf9 = rtverbs[1].getBytes("UTF-8");
        stng = new String(utf9,"UTF-8");
        displayString(stng);


	try{
	    byte[] utf8 = string.getBytes("UTF-8");
    	    string = new String(utf8, "UTF-8");
	    //System.out.println(string);
	  //  displayString(string);
	    //byte[] utf9 = stng.getBytes("UTF-8");
	    //stng = new String(utf9, "UTF-8");
	    //displayString(stng);
	    //System.out.println("string = "+string+"  str="+stng);
	    if(string.equals(stng)){
	    	//System.out.println("Good Job");
	    	t=1;
	    }
	    else{
	    	//System.out.println("Bad Job");
	    	t = rtverbs.length-1;
	    }
	}
	catch(Exception e){}
	finally {	
	       // fout.close();
	}
	return t;
  }
  
  
  public static int main12(String string) throws Exception {
//    String string = "abc\u5639";
	int t = 0;
	String stng = "देख्यो";
        displayString(string);
        byte[] utf9 = rtverbs[1].getBytes("UTF-8");
        stng = new String(utf9,"UTF-8");
        displayString(stng);


	try{
	    byte[] utf8 = string.getBytes("UTF-8");
    	    string = new String(utf8, "UTF-8");
	    //System.out.println(string);
	  //  displayString(string);
	    //byte[] utf9 = stng.getBytes("UTF-8");
	    //stng = new String(utf9, "UTF-8");
	    //displayString(stng);
	    //System.out.println("string = "+string+"  str="+stng);
	    if(string.equals(stng)){
	    	//System.out.println("Good Job");
	    	t=1;
	    }
	    else{
	    	//System.out.println("Bad Job");
	    	t = rtverbs.length-1;
	    }
	}
	catch(Exception e){}
	finally {	
	       // fout.close();
	}
	return t;
  }
  
  
  // verb "दिए" 
  public static int main2(String string) throws Exception {
//    String string = "abc\u5639";
	int t = 0;
	String stng = "दिए";
        displayString(string);
        byte[] utf9 = rtverbs[2].getBytes("UTF-8");
        stng = new String(utf9,"UTF-8");
        displayString(stng);


	try{
	    byte[] utf8 = string.getBytes("UTF-8");
    	    string = new String(utf8, "UTF-8");
	    //System.out.println(string);
	  //  displayString(string);
	    //byte[] utf9 = stng.getBytes("UTF-8");
	    //stng = new String(utf9, "UTF-8");
	    //displayString(stng);
	    //System.out.println("string = "+string+"  str="+stng);
	    if(string.equals(stng)){
	    	//System.out.println("Good Job");
	    	t=2;
	    }
	    else{
	    	//System.out.println("Bad Job");
	    	t = rtverbs.length-1;
	    }
	}
	catch(Exception e){}
	finally {	
	       // fout.close();
	}
	return t;
  }
  
    
  public int getFrameIndex(){
  	return id;
  }
//}
  public int getRootverb(String nvb){
	int ind = 0;
	//main2(nvb);
	//NoVerbs = novb;
	//String bb1;
   //   try{	
  	
  	int i1 = 0;
  	boolean flag = true;
   	//System.out.println("Hi its ReadXMLFile and getRootverb method: no of verbs = "+NoVerbs	);
  // 	main2(rootverbs[0]);
  	for( i1=0;i1<NoVerbs;i1++){
  		if(rootverbs[i1] != nvb){	
  			flag = true;
  		}
  		else{
			//System.out.println("flag = "+flag);
			flag = true;
  			break;
  		}
  	
  	}

	/*while(rootverbs[i] != nvb ){
  		//System.out.println("RootVerb["+i+"]= "+rootverbs[i]);
  		//System.out.println("hi its a get Rootverb"+i);
  		flag = true;
  		if(flag == true){
 			break;
  		}
  		if(i<(NoVerbs-1))
			i++;
		else{
			
			break;
		}
  		//System.out.println("i="+i);
  	}*/
//  	flag = true;
  	if(flag == false){
	    	//System.out.println("hi its not a Rootverb  "+i1);
	    	ind = -1;
	}
	else{
		//System.out.println("hi its a Rootverb  "+i1);
		ind = i1;
	}
//  	return ind;
   //  }
     //    catch(IOException ie){}
    //finally {	
	       
//	}

     return ind;

  }
  public String[][] getPresence(int indVerb){
  	String[][] tpr = new String[totalkarakas][3];
  	int nv = indVerb + 1;
  	//nv = 5;
  	int tl = (totalkarakas*nv)-totalkarakas;
 //	tl = tl - 2; // Pathayo has only 3 karkas instead of 4
  	for(int i = tl ; i < tl+totalkarakas ; i++){
  		for(int j = 0 ; j < 3 ; j++){
  			//System.out.println("prslist["+i+"]["+j+"]= "+prslist[i][j]);
  			tpr[i -tl ][j] = prslist[i][j];
  		}
  	}
  	return tpr;
  }
  
  public String[][] getKaraka(int indVerb){
	String[][] tkrk = new String[totalkarakas][3];
  	int nv = indVerb+1;
  	int tl = (totalkarakas*nv)-totalkarakas;
  //	tl = tl - 2; // Pathayo has only 3 karkas instead of 4
  	for(int i = tl; i < tl+totalkarakas  ; i++){
		for(int j = 0 ; j < 3 ; j++){
  			//System.out.println("krks["+i+"]["+j+"]= "+krks[i][j]);
  			tkrk[i-tl][j] = krks[i][j];
  		}
  	}
  	return tkrk;
  }

  public String[][] getVbhkt(int indVerb){
  	String[][] tv = new String[totalkarakas][3];
  	int nv = indVerb+1;
  	int tl = (totalkarakas*nv)-totalkarakas;
  //	tl = tl - 2; // Pathayo has only 3 karkas instead of 4
  	for(int i = tl ; i < tl+totalkarakas  ; i++){
	  	for(int j = 0 ; j < 3 ; j++){
  			//System.out.println("vbhkts["+i+"]["+j+"]= "+vbhkts[i][j]);
  			tv[i-tl][j]=vbhkts[i][j];
  		}
  	}
  	return tv;
  }

//}

	public void readFile1(String fileName) throws IOException {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8")); // Read wordnet.txt
	        //System.out.println("hi");
        	String vibhakti1 = "hi";
	        try {

	            String line =  br.readLine();
	            String ws;
        	    int count = 0;
	            int oddcount=0;
        	    int evencount=0;
	            //System.out.println("hi");
	            words = new String[1000];// = line.split(" ");//those are your words
	//            wordsentwords = new String[1000];
        	    wordnet = new String[1000];
	            while (line != null) {
		 	StringTokenizer st = new StringTokenizer(line);
	                while (st.hasMoreTokens()) {
		                ws = st.nextToken();
		                if((count%2) == 0){
				        //System.out.println("count1 ="+count);		                
	        	        	words[evencount] = ws;
	        	        	evencount++;
		                }
		                else{
		                	wordnet[oddcount] = ws;
		                	oddcount++;
	        	        }
//		                evencount++;
//		                oddcount++;
	        	        count++;
	                }
	                line = br.readLine();
	            }
	        }
	        finally {
	        br.close();
	}
}

 public String[] getwordnet(){
  return wordnet;
 } 
 public String[] getwordnetword(){
  return words;
 } 
}
