import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
public class AutoCorrector{
	public static String[] dictionary = new String[3000000];
	public static String[] sentence = new String[300];
	public static String[] absentwords = new String[300];
	public static String[] propernn = {"म","हामी","तपाईं","तँ","तिमी","तपाईँ","ऊ","उनी","उहाँ","तिमीहरू","मैले","उसले","हामीले","तिनले","यसको","यसलाई","तिनीहरू","तिनीहरूले","उस्लाई","त्यसलाई","तिनीहरूलाई","यसलाई","तिनीहरू","मसँग","आफू","आफैँ","मेरो","हाम्रो","तेरो","तिम्रो","तपाईँको","उसको","उनको","उहाँको",};
	public static String[] reflpron = {"आफू","आफैँ"};
	public static String[] posspron = {"मेरो","हाम्रो","तेरो","तिम्रो","तपाईँको","उसको","उनको","उहाँको"};
	public static String[] dempron = {"यो","यी","त्यो","ती","यस","कुनै","हालै","अगाडि","अझै","पनि","समान्यतया","झन्डै","हिजो"};
	public static String[] Questionpron = {"को","के","कुन","कुनै","कहि"};
	public static String[] verb ={"जान्छु","खानु","हुनु","बोल्नु","रुनु","खानु","लिनु","जानु","छुनु","आउनु","पकाउनु","पिउनु","बिर्सनु","सम्झनु","फर्कनु","दौडनु","चिप्लनु","उभिनु","चलाउनु","पर्खनु","लगाउनु","किन्नु","काट्नु","मर्नु","गर्नु","सुन्नु","हाँस्नु","बस्नु","हार्नु","भेट्नु","खोल्नु","खेल्नु","पढ्नु","भन्नु","देख्नु","बेच्नु","सुत्नु","बोल्नु","रोक्नु","लग्नु","उठ्नु","हिँड्नु","हेर्नु","जित्नु","लेख्नु","मान्नु","बाल्नु","उठ्नु","ब्यूँझनु","हुनु","सहनु","कुट्नु","शुरु","गर्नु","बङ्ग्याउनु","बाजी","राख्नु","बाँध्नु","टोक्नु","आउनु","फुक्नु","भाँच्नु","ल्याउनु","बाल्नु","किन्नु","फ्याँक्नु","समात्नु","छान्नु","पिट्नु","लगाउनु","आउनु","पर्नु","घस्रनु","काट्नु","खन्नु","फाल्नु","खिच्नु","देख्नु","पिउनु","हाँक्नु","बस्नु","खानु","झर्नु","खुवाउनु","महशुस","झगडा","लगाउनु","पत्ता","सुहाउनु","भाग्नु","फ्याँक्नु","उड्नु","बिर्सनु","अनुमान","निषेध","माफ","त्याग्नु","जम्नु","पाउनु","दिनु","जानु","पिँध्नु","बढ्नु","बढाउनु","लेख्नु","झुन्ड्याउनु","हुनु","सँग","पाउनु","सुन्नु","लुक्नु","थाम्नु","समात्नु","दुखाउनु","टेक्नु","बुन्नु","जान्नु","लाग्नु","उफ्रनु","सिक्नु","छोड्नु","दिनु","गर्न","ढाँट्नु","बाल्नु","हराउनु","बनाउनु","भेट्नु","पग्लनु","झिक्नु","फिर्ता","सुन्नु","तिर्नु","चढ्नु","बजाउनु","उदाउनु","दगुर्नु","काट्नु","खोज्नु","पठाउनु","अस्ताउनु","सिउनु","हल्लाउनु","निकाल्नु","चम्कनु","हान्नु","देखाउनु","खुम्चनु","बन्द","गाउनु","डुब्नु","मार्नु","चिप्लनु","हिड्नु","दिनेछन्","गर्छौ","गर्छु","बनायौ","भेट्यो","रुचाउँछिन्","गर्छौ","दिनेछन्","सक्छु","पढ्नेछिन्","गर्‍यौ","गर्छन्","र्‍याउँछिन्","देख्छौ","गर्छन्","बिक्री","पठाएँ","खौरियो","खुम्चियो","छौ","छ","गाउने","बसे","बोल्न","खर्च","गर्छिन्","दुख","पाउँछौ","सल्लाह","दिन्छन्","अचम्ममा","पारेँ","लगिन्","पढाउने","भने","धन्यवाद","दिइन्","सोच्न","सक्छु","फालिदिइन्","बुझ्छौ","चाहन्छन्","लगाउन","सक्छौ","लेख्छिन्","कुरा","हेरेँ","गर्नेछौ","किन्यो","हिज","किन्यो","सक्यौ","लिन्छौ","मुस्कुराउँछौ","दिन्छौ","दिन्छ","कुदाउँछौ","लेख्छौ","बोल्छौ","लिन्छ","मुस्कुराउँछ","माया","गर्छ","कुदाउँछ","लेख्छ","बोल्छ","लिन्छु","मुस्कुराउँछु","दिन्छु","कुदाउँछु","लेख्छु","बोल्छु","लिने","लिनेछौ","छौ","मुस्कुराउने","दिनेछौ","कुदाउने","कुदाउनेछौ","लेख्नेछौ","बोल्नेछौ","लिनेछ","मुस्कुराउनेछ","दिनेछ","गर्नेछ","लेख्नेछ","बोल्नेछ","मुस्कुराउनेछौ","गर्नेछु","लेख्यौ"," कुदायौ","गर्यौ","दियौ","मुस्कुरायौ","लियौ","लियो","मुस्कुरायो","दियो","गर्यो","कुदायो","लेख्यो","बोल्यो","लिएँ","मुस्कुराएँ","दिएँ","गरेँ","कुदाएँ","लेखेँ","बोलेँ","थिएन","थिए"};// = new String[200];
	public static String[] adjective = {"कालो","फूस्रो","सेतो","निलो","हरियो","पंहेलो","रातो","खैरो","ठूलो","सानो","लामो","छोटो","अग्लो","बाक्लो","पातलो","फराकिलो"};
	public static String[] adverb ={"हिजो","पछाडि","बिस्तारै","कहिल्यै","राम्ररी","धेरै","अचेल","पनि","माथि","वरपर","पछाडि","मुनि","वारि","तल","यहाँ","भित्र","अगाडि","बाहिर","त्यहाँ","पारि","सधैँ","अब","पर्सि","अस्ति","पछि","कहिल्यै","अहिले","अचेल","छिटो","बिस्तारै","कहिलेकाहीँ","उहिले","आज","भोलि","अलिकति","धेरै","झन","बेसि","थोरै","अलिअलि","कसरी","त्यसरी","यसरी","राम्ररी"};
	public static String[] conjunct = {"तथा","र","एन्ड","अर्थात्","वा","तर"};
	//public static String[] particle = {"","र","एन्ड","अर्थात्","वा"};
	public static byte[][] utfverb = new byte[verb.length][20];
	public static byte[][] utfprnn = new byte[propernn.length][20];
	public static byte[][] utfdm = new byte[dempron.length][20];
	public static byte[][] utfadj = new byte[adjective.length][20];
	public static byte[][] utfadv = new byte[adverb.length][20];
	public static byte[][] utfqm = new byte[Questionpron.length][20];
	public static int[] dist1 = new int[20]; 
	public static int  wordlength = 0;
	public static int notavailablecount = 0;
	public static String[] spellCorrection = new String[5];

	public int nowords= 1;
	public AutoCorrector(){}
	public static void main(String[] args) throws IOException{
		
		AutoCorrector t = new AutoCorrector();
		
		t.readFile(args[0], args[1]);
		System.out.println("dict["+0+"]="+dictionary[0]);
		t.dictionaryCheck(dictionary, sentence);
	}
	public static void main1(String ar0, String ar1) throws IOException{
		
		AutoCorrector t = new AutoCorrector();

		
		t.readFile(ar0, ar1);
		System.out.println("Auto dict["+0+"]="+dictionary[0]);
		absentwords = t.dictionaryCheck(dictionary, sentence);
		spellCorrection = t.spellOptions(dist1);
	}
	public String[] spellOptions(int[] distance){
		String[] correct = new String[5];
		for(int c=0;c<distance.length;c++){
			System.out.println("distance["+c+"]="+distance[c]);
		}
		return correct;
	}
	public void readFile(String fileName, String sentenceFile) throws IOException{
		//public void readFile(String fileName, String outputName, String dispfile,  String gl) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(sentenceFile), "UTF-8"));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(sentenceFile), "UTF-16"));
       // OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName, true), "UTF-8");
        //BufferedWriter fout = new BufferedWriter(writer);
        String vibhakti1 = "hi";
        String[] words = new String[300];// = line.split(" ");//those are your words
            String[] sentwords = new String[3000000];
            String[] sentwords1 = new String[300];
        try {

            StringBuilder sb = new StringBuilder();
            
           // String linec =  br1.readLine();
			String linec =  br1.readLine();
			String line =  br.readLine();
			String line16 = br2.readLine();
			System.out.println("line="+line);
            int counterc=0;
            int count=0;
            //System.out.println("hi");
            
            String ws="sent";
            String ws1 ="dict";
            
            //System.out.println("verb vibhakti"+linec);
            while(linec != null){
              StringTokenizer stc = new StringTokenizer(linec);
              //System.out.println("check verb vibhakti");
                while (stc.hasMoreTokens()) {
                  
                  //System.out.println("counterc="+counterc);
                  ws = stc.nextToken();
                  sentwords[counterc] = ws;
                  dictionary[counterc] = ws;
                 // byte[][] dictbytes = new byte[500000][ws1.length()];
                  //for(int i = 0 ; i < ws1.length() ; i++){
                  //	dictbytes[counterc][i]=(byte) ws1.charAt(i);
                  //	System.out.println("UTF-16 of "+ws+"="+ws1.charAt(i));
                  	//System.out.println("UTF-16 of "+ws+" at "+counterc+"="+dictbytes[count][i]);
                  //	System.out.println("UTF-16 of "+ws1+"="+(Char) ws1);
                  //}
                  counterc++;
                 // System.out.println("Sentence:");
                  //System.out.println("sentwords["+counterc+"]="+sentwords[counterc]);
                  
                }
                linec =  br1.readLine();
               // line16 = br2.readLine();
                System.out.println("new what line ="+line);
                //notavailablecount= line;
			}

			while(line16 != null){
              StringTokenizer stc = new StringTokenizer(line16);
              //System.out.println("check verb vibhakti");
                while (stc.hasMoreTokens()) {
                  
                  //System.out.println("counterc="+counterc);
                  ws = stc.nextToken();
                  sentwords[counterc] = ws;
                  dictionary[counterc] = ws;
                  counterc++;
                 // System.out.println("Sentence:");
                  //System.out.println("sentwords["+counterc+"]="+sentwords[counterc]);
                  
                }
                //linec =  br1.readLine();
                line16 = br2.readLine();
                System.out.println("new what line 16 ="+line16);
			}
			//br1.close();
			System.out.println("new line ="+line);
			try{
				while(line != null){
              StringTokenizer stc1 = new StringTokenizer(line);
              System.out.println("check verb vibhakti");
                while (stc1.hasMoreTokens()) {
                  
                  System.out.println("Auto count="+count);
                  ws1 = stc1.nextToken();
                  words[count] = ws1;
                  sentence[count] = ws1;
                  byte[][] bytes = new byte[500000][ws1.length()];
                  for(int i = 0 ; i < ws1.length() ; i++){
                  	bytes[count][i]=(byte) ws1.charAt(i);
                  //	System.out.println("UTF-16 of "+ws1+"="+ws1.charAt(i));
                  	//System.out.println("UTF-16 of "+ws1+" at "+count+"="+bytes[count][i]);
                  //	System.out.println("UTF-16 of "+ws1+"="+(Char) ws1);
                  }
                 // String str = new String(bytes, Char.forName("UTF-16"));
                  //System.out.println("UTF-16 of "+ws1+"="+bytes[i]);
                  count++;
                  nowords++;
                  System.out.println("Dictionary:");
                  System.out.println("dict["+count+"]="+words[count]+"  sentence["+count+"]="+sentence[count]);
                  
                }
                line =  br.readLine();
			}
			}
			catch(ArrayIndexOutOfBoundsException ae){};
		}
		finally {
			br.close();
			br1.close();
		}
		dictionary = sentwords;
		for(int i = 0; i < 2; i++){
			System.out.println("dictionary["+i+"]="+dictionary[i]);
		}
		sentence = words;
		//catch(ArrayIndexOutOfBoundsException e){};
	}
	public byte[] getUTF(String wd){
		System.out.println("wd = "+wd+"  wd.length="+wd.length());
		wordlength = wd.length();
		byte[] bytes = new byte[wd.length()];
		int count = 0;
		for(int i = 0 ; i < wd.length() ; i++){
          	bytes[i]=(byte) wd.charAt(i);
          	System.out.println("UTF-16 of "+wd+"="+wd.charAt(i));
            System.out.println("UTF-16 of "+wd+" at "+count+"="+bytes[i]);
          //  System.out.println("UTF-16 of "+wd+"="+(Char) wd);
        }
        return bytes;
	}
public void spellCheck(String[] dict, String[] findMe){
	try{
		boolean available = false;
	for(int i = 0 ; i < findMe.length ; i++){
		System.out.println("dict["+i+"]="+dict[i]+"    findMe["+i+"]="+findMe[i]);
		if(findMe[i].equals("PUNC"))
		 nowords=i;
		System.out.println("no of words="+nowords);
		for(int j = 0 ; j < dict.length ; j++){

			if(findMe[i].equals(dict[j])){
				available = true;
				break;
				//System.out.println("Available");
			}
			else{
				available =false;
				//System.out.println(" Not Available");
			}
		}
		System.out.println("Availability ="+available);
	}
	}
	catch(NullPointerException ne){};
}

public String[] dictionaryCheck(String[] dict, String[] findMe){
	String[] nonavailable = new String[findMe.length];
	int[] ava = new int[findMe.length];
	for(int j = 0 ; j < nonavailable.length ; j++){
		nonavailable[j] = "nl";
		ava[j]=100;
	}
	try{
		boolean available = false;
		String s = "hi";
		int count = 0;
		int temp= 0;
		boolean noentry = false;
	for(int i = 0 ; i < findMe.length ; i++){
		System.out.println("dict["+i+"]="+dict[i]+"    findMe["+i+"]="+findMe[i]);
		if(findMe[i+2].equals("ले") || findMe[i+2].equals("लाई")){
			 s = findMe[i].concat(findMe[i+2]);
			System.out.println("concat = "+s);
			noentry = true;
			temp = i;
		}
		if(i > (temp+2)){
			noentry = false;
		}
		for(int j = 0 ; j < dict.length ; j++){

			if(findMe[i].equals(dict[j])){
				available = true;
				nonavailable[count] = findMe[i];
				ava[count] = i;
				break;
				//System.out.println("Available");
			}
			else{
				available =false;
				//System.out.println("concat ="+s);
				if(s.equals(dict[j])){
					available = true;
					nonavailable[count] = findMe[i];
					ava[count] = i;
					System.out.println("Available concat ="+s);
				}
				
				//count++;
				//System.out.println(" Not Available");
			}
			if(!available ){

			}
		}


		count++;
		System.out.println("Availability ="+available);
	}

	}
	catch(NullPointerException ne){};
	int[] nva = new int[ava.length];
	for(int t=0;t<ava.length;t++){
		nva[t] = 100;
	}
	int ct=0;
	for(int k=0;k<ava.length;k++){
		System.out.println("ava["+k+"]="+ava[k]);
		if(ava[k]!= k){
			nva[ct]=k;
			
			System.out.println("nva["+ct+"]="+nva[ct]);
			ct++;
		}
		
	}
	notavailablecount = nowords;
	System.out.println("not available count ="+notavailablecount);
	int counter = 0;
	int counterdm = 0;
	int counterprnn = 0;
	int countervb = 0;
	int counterqm = 0;
	int counteradj = 0;
	int counteradverb = 0;
	String[][] collection = new String[nva.length][dict.length];
	//try{
	for(int a = 0 ; a < 10 ; a++){
		for(int b = 0 ; b < 10 ; b++){
			collection[a][b] = "nl"; 
		}
	}
	
	byte[] utfme = new byte[20];
		for(int r=0;r<propernn.length;r++){
				utfprnn[r] = getUTF(propernn[r]);}
		for(int r=0;r<verb.length;r++){
				utfverb[r] = getUTF(verb[r]);}
		//byte[] utfdm = new byte[20];
		for(int r=0;r<dempron.length;r++){
				utfdm[r] = getUTF(dempron[r]);}
		//byte[] utfqm = new byte[20];
		for(int r=0;r<Questionpron.length;r++){
				utfqm[r] = getUTF(Questionpron[r]);}
		/*for(int r=0;r<verb.length;r++){
				utfverb[r] = getUTF(verb[r]);}*/
		//byte[] utfadj = new byte[20];
		for(int r=0;r<adjective.length;r++){
				utfadj[r] = getUTF(adjective[r]);}
		//byte[] utfadv = new byte[20];
		for(int r=0;r<adverb.length;r++){
				utfadv[r] = getUTF(adverb[r]);
			/*	for(int r1=0;r1<utfverb[r].length;r1++)
					System.out.println("utfverb["+r+"]["+r1+"]"+utfverb[r][r1]);*/
			}

	for(int k1=0;k1<nva.length;k1++){
		//collection[]
		if(nva[k1] < (notavailablecount-2)){
				System.out.println("index = "+findMe[nva[k1]+1]+"  nva["+k1+"]="+nva[k1]+"  nowords="+nowords+"  unknown word="+findMe[nva[k1]]+"  counterprnn="+counterprnn+"  propernn="+propernn.length);

		// PRONOUN CORRECTION
		if(findMe[nva[k1]+1].equals("NN") || findMe[nva[k1]+1].equals("PRP") || findMe[nva[k1]+1].equals("PRL")){
				counterprnn = 1;			
		for(int j1=0;j1<propernn.length && counterprnn < propernn.length ;j1++){
			if(findMe[nva[k1]].equals(propernn[j1])){
				collection[k1][counterprnn] = propernn[j1] ;
				System.out.println("Spell correction collection["+k1+"]["+counterprnn+"]="+collection[k1][counterprnn]);
				
				counterprnn++; 
			}
			else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);
							
							//dist1 = findDist(utfme, utfverb[r]);
						}
						
					}
			}
		}
		else{
			counterprnn = 0;
		}
		int tempmatchpron = 0;
		int ct1prn = 0;
		for(int r2 = 0 ; r2 < propernn.length && counterprnn > 0 ; r2++){
			System.out.println("verb number ="+r2+"  PRONOUN["+r2+"]="+propernn[r2]);
			dist1 = findDist(utfme, utfprnn[r2]);
			dist1[18] = r2;
			if(r2 == 0)
				tempmatchpron = dist1[19];      // dist1[19] = number of matches
			if(ct1prn < 5 && dist1[19] > 1 && dist1[17] <= 5*(wordlength+1) ){
				if(tempmatchpron < dist1[19]){
					System.out.println();
					spellCorrection[ct1prn] = propernn[r2];

					System.out.println("spell correction ["+ct1prn+"]="+spellCorrection[ct1prn]+" for the word :"+findMe[nva[k1]]);
					tempmatchpron = dist1[19];
					System.out.println("temp max match for propernn ="+tempmatchpron);
					ct1prn++;
				}
			}
			System.out.println("temp max match number for propernn ="+tempmatchpron);
		}

		

		// DEMONSTRATIVE MODIFIER CORRECTION
		 if(findMe[nva[k1]+1].equals("NN") || findMe[nva[k1]+1].equals("DM") || findMe[nva[k1]+1].equals("DMR") || findMe[nva[k1]+1].equals("DMI") || findMe[nva[k1]+1].equals("RBO") || findMe[nva[k1]+1].equals("RP")){
				//counterdm = 0;
				counterdm = 1;
				for(int j1=0;j1<dempron.length && counterdm < propernn.length ;j1++){
					if(findMe[nva[k1]].equals(dempron[j1])){
						collection[k1][counterdm] = dempron[j1] ;
						System.out.println("collection["+k1+"]["+counterdm+"]="+collection[k1][counterdm]);
				
						counterdm++; 
					}
					else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);

							//dist1 = findDist(utfme, utfverb[r]);
						}
						
					}
				}
		}
		else{
			counterdm = 0;
		}
		int tempmatchdm = 0;
		int ct1dm = 0;
		for(int r3 = 0 ; r3 < dempron.length && counterdm > 0 ; r3++){
			System.out.println("verb number ="+r3+"  dempron["+r3+"]="+dempron[r3]);
			dist1 = findDist(utfme, utfdm[r3]);
			dist1[18] = r3;
			if(r3 == 0)
				tempmatchdm = dist1[19];      // dist1[19] = number of matches
			if(ct1dm < 5 && dist1[19] > 1 && dist1[17] <= 5*(wordlength+1)){
				if(tempmatchdm < dist1[19]){
					System.out.println();
					spellCorrection[ct1dm] = dempron[r3];

					System.out.println("spell correction ["+ct1dm+"]="+spellCorrection[ct1dm]+" for the word :"+findMe[nva[k1]]);
					tempmatchdm = dist1[19];
					System.out.println("temp max match for dm ="+tempmatchdm);
					ct1dm++;
				}
			}
			System.out.println("temp max match number for DEMONSTRATIVE ="+tempmatchdm);
		}

		
			/*   VERB CORRECTION */
		System.out.println("Tag="+findMe[nva[k1]+1]);
		 if(findMe[nva[k1]+1].equals("VM") || findMe[nva[k1]+1].equals("VF") || findMe[nva[k1]+1].equals("VMKO") || findMe[nva[k1]+1].equals("VAUX") ){
				countervb = 0;
				System.out.println("Tag 2 ="+findMe[nva[k1]+1]);
				for(int j1=0;j1<verb.length && countervb < verb.length ;j1++){
					countervb = 1;
					if(findMe[nva[k1]].equals(verb[j1])){
						collection[k1][countervb] = verb[j1] ;
						System.out.println("collection["+k1+"]["+countervb+"]="+collection[k1][countervb]);
				
						countervb++; 
					}
					else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);
							//dist1 = findDist(utfme, utfverb[r]);
						}
						
					}
				}
		}
		else{
			countervb = 0;
		}
		int tempmatch = 0;
		int ct1 = 0;
		for(int r1 = 0 ; r1 < verb.length && countervb >0 ; r1++){
			System.out.println("verb number ="+r1+"  verb["+r1+"]="+verb[r1]);
			dist1 = findDist(utfme, utfverb[r1]);
			dist1[18] = r1;
			if(r1 == 0)
				tempmatch = dist1[19];      // dist1[19] = number of matches
			if(ct1 < 5 && dist1[19] > 1  && dist1[17] <= 7*(wordlength+1)){
				if(tempmatch <= dist1[19]){
					System.out.println();
					spellCorrection[ct1] = verb[r1];

					System.out.println("spell correction ["+ct1+"]="+spellCorrection[ct1]+" for the word :"+findMe[nva[k1]]);
					tempmatch = dist1[19];
					System.out.println("temp max match ="+tempmatch);
					ct1++;
				}
			}
			System.out.println("temp max match number ="+tempmatch);
		}

		// ADJECTIVE CORRECTION
		if(findMe[nva[k1]+1].equals("NN") || findMe[nva[k1]+1].equals("JJ") ){
				counteradj = 1;			
		for(int j1=0;j1<adjective.length && counteradj < adjective.length ;j1++){
			if(findMe[nva[k1]].equals(adjective[j1])){
				collection[k1][counteradj] = adjective[j1] ;
				System.out.println("Spell correction collection["+k1+"]["+counteradj+"]="+collection[k1][counteradj]);
				
				counteradj++; 
			}
			else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);
							
							//dist1 = findDist(utfme, utfverb[r]);
						}
						
					}
			}
		}
		else{
			counteradj = 0;
		}
		int tempmatchadj = 0;
		int ct1adj = 0;
		for(int r2 = 0 ; r2 < adjective.length && counteradj > 0 ; r2++){
			System.out.println("adjective number ="+r2+"  ADJECTIVE["+r2+"]="+adjective[r2]);
			dist1 = findDist(utfme, utfadj[r2]);
			dist1[18] = r2;
			if(r2 == 0)
				tempmatchadj = dist1[19];      // dist1[19] = number of matches
			if(ct1adj < 5 && dist1[19] > 1 && dist1[17] <= 5*(wordlength+1) ){
				if(tempmatchadj < dist1[19]){
					System.out.println();
					spellCorrection[ct1adj] = adjective[r2];

					System.out.println("spell correction ["+ct1adj+"]="+spellCorrection[ct1adj]+" for the word :"+findMe[nva[k1]]);
					tempmatchadj = dist1[19];
					System.out.println("temp max match for ADJECTIVE ="+tempmatchadj);
					ct1adj++;
				}
			}
			System.out.println("temp max match number for ADJECTIVE ="+tempmatchadj);
		}

		// ADVERB CORRECTION
		if(findMe[nva[k1]+1].equals("NN") || findMe[nva[k1]+1].equals("RB") || findMe[nva[k1]+1].equals("RBO") ){
				counteradverb = 1;			
		for(int j1=0;j1<adverb.length && counteradverb < adverb.length ;j1++){
			if(findMe[nva[k1]].equals(adverb[j1])){
				collection[k1][counteradverb] = adverb[j1] ;
				System.out.println("Spell correction collection["+k1+"]["+counteradj+"]="+collection[k1][counteradj]);
				
				counteradverb++; 
			}
			else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);
							
							//dist1 = findDist(utfme, utfverb[r]);
						}
						
					}
			}
		}
		else{
			counteradverb = 0;
		}
		int tempmatchadverb = 0;
		int ct1adverb = 0;
		for(int r2 = 0 ; r2 < adverb.length && counteradverb > 0 ; r2++){
			System.out.println("adverb number ="+r2+"  ADVERB["+r2+"]="+adverb[r2]);
			dist1 = findDist(utfme, utfadv[r2]);
			dist1[18] = r2;
			if(r2 == 0)
				tempmatchadverb = dist1[19];      // dist1[19] = number of matches
			if(ct1adverb < 5 && dist1[19] > 1 && dist1[17] <= 5*(wordlength+1) ){
				if(tempmatchadverb < dist1[19]){
					System.out.println();
					spellCorrection[ct1adverb] = adverb[r2];

					System.out.println("spell correction ["+ct1adverb+"]="+spellCorrection[ct1adverb]+" for the word :"+findMe[nva[k1]]);
					tempmatchadverb = dist1[19];
					System.out.println("temp max match for ADVERB ="+tempmatchadverb);
					ct1adverb++;
				}
			}
			System.out.println("temp max match number for ADVERB ="+tempmatchadverb);
		}

		// QUESTION CORRECTION
		//dist1 = findDist(utfme, utfverb);
		 if( findMe[nva[k1]+1].equals("NN") || findMe[nva[k1]+1].equals("QW")  ){
				//counterqm = 0;
		 			counterqm=1;
				for(int j1=0;j1<Questionpron.length && counterqm < Questionpron.length ;j1++){
					if(findMe[nva[k1]].equals(Questionpron[j1])){
						collection[k1][counterqm] = verb[j1] ;
						System.out.println("collection["+k1+"]["+counterqm+"]="+collection[k1][counterqm]);
				
						counterqm++; 
					}
					else{
						utfme = getUTF(findMe[nva[k1]]);
						for(int r=0;r<utfme.length;r++){
							System.out.println("distance utfme["+r+"]"+utfme[r]);
							//dist1 = findDist(utfme, utfverb[r]);
						}
						
				}
		}
		}
		else{
			counterqm = 0;
		}

		// Question correction

		int tempmatchqm = 0;
		int ct1qm = 0;
		for(int r4 = 0 ; r4 < Questionpron.length && counterqm >0 ; r4++){
			System.out.println("question number ="+r4+"  QM["+r4+"]="+Questionpron[r4]);
			dist1 = findDist(utfme, utfqm[r4]);
			dist1[18] = r4;
			if(r4 == 0)
				tempmatchqm = dist1[19];      // dist1[19] = number of matches
			if(ct1qm < 5 && dist1[19] >= 1 && dist1[17] <= 5*(wordlength+1)){
				if(tempmatchqm < dist1[19]){
					System.out.println();
					spellCorrection[ct1qm] = Questionpron[r4];

					System.out.println("spell correction ["+ct1qm+"]="+spellCorrection[ct1qm]+" for the word :"+findMe[nva[k1]]);
					tempmatchqm = dist1[19];
					System.out.println("temp max match for dm ="+tempmatchqm);
					ct1qm++;
				}
			}
			System.out.println("temp max match number for Question mark ="+tempmatchqm);
		}


	//}
	/*else{
			break;
		}*/
	}
	//}
	//catch(ArrayIndexOutOfBoundsException ne2){};
	/*for(int k2=0;k2<10;k2++){
		for(int j2=0;j2<10;j2++){
			System.out.println("Collection["+k2+"]["+j2+"]="+collection[k2][j2]);
		}
	}*/

	/*try{
	for(int i = 0 ; i < findMe.length;i++){
				System.out.println("before nonavailable["+i+"]="+nonavailable[i]);

		if(nonavailable[i].equals(findMe[i])){
			
		}
		else{
			nonavailable[i] = findMe[i];
			System.out.println("i="+i);
		}
		System.out.println("nonavailable["+i+"]="+nonavailable[i]);
	}
	}
	catch(NullPointerException ne1){};*/
	}
	return nonavailable;
	}

	public int[] findDist(byte[] w, byte[] v){
		int count = 0, c = 0,maximum = 0, minimum = 0, match = 0;
		maximum = Math.max(v.length,w.length);
		minimum = Math.min(v.length,w.length);
		maximum = 20;
		int[] dist11 = new int[maximum];
		int[] dist10 = new int[maximum];
		//for(int a = 0 ; a < v.length; a++){
			//for(int b = 0 ; b < v[a].length ; b++){
			//System.out.println("w["+c+"]="+w[c]+"   v["+a+"]="+v[a]);
				//c=0;
				System.out.println("maximum ="+maximum+"  minimum ="+minimum+"  wd.length="+wordlength);
				count = Math.abs(v.length - w.length);
				while(c < wordlength){
					for(int a = 0 ; a < v.length; a++){
							System.out.println("initial count = "+count+"  w["+c+"]="+w[c]+"  v["+a+"]="+v[a]);
							if(w[c] == v[a] ){
								dist11[c]=0;
								match++;
								System.out.println(" equal count match ="+match);
							}
							else{
								System.out.println("not equal count="+count);
								dist11[c]=1;
								count++;
								//break;
							}
					}
					c++;
				//}
				}
			//c++;
			dist11[19] = match;
			dist11[17] = count;    //distance
			System.out.println("dist ="+count+"   match ="+match+"  distance dist11["+17+"]="+dist11[17]);
			if(match > 1)
				return dist11;
			else {
				return dist10;
			}
		}
	}

	
	//}