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
// CHUNKER WHICH TAKES TAGGED NEPALI SENTENCE FROM LWG4.JAVA AND FORMS NOUN, ADJECTIVE AND VERB CHUNKS 
// AND SUPPLIES BACK TO LWG4.JAVA
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

public class Chunker
{
	String[][] TN ;
	String[][] TV ;
	String[][] tagttn ;//= new String[5][words.length];
	String[][] tagttv1 ;//= new String[5][words.length];
	int noNP;
	int noVP;
	Lwg7 lg = new Lwg7();
	public Chunker(int now, String[] words, String[] tags, boolean k2pexist, boolean k7texist, int eko, int eka, int ika, int batexist){
		TN = new String[15][3] ; // Maximum noun chunks assumed = 5 and maximum length(words) of each chunk = 3
		TV = new String[15][3] ; // Maximum noun chunks assumed = 5 and maximum length(words) of each chunk = 3	
		
		TN = getNounChunks(now, words, tags, k2pexist, k7texist, eko, eka, ika, batexist);
		
		TV = getVerbChunks(now, words, tags, eko, eka);
	}
	public String[][] getNounChunks(int nwords , String[] swords, String[] stags, boolean k2p , boolean k7t, int eko, int eka, int ika, int batexist){
		int count = 0;
		int flag = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag5 = 0;
		int flag6 = 0;
		int flag7 = 0;
		int verbindex = 100;
		int flagRP = 0;
		int jjnnflag = 0;
		int onlynnflag = 0;
		int onlyqwflag = 0;
		int noentry = -1;
		int ind1 = 0;
		int ind = 0;
		int lag = 0;
		int lag5= 0;
		int nounPhrase = 0;
		int ct = 0;
		int pt = 0;
		int mustjj = 0;
		int regionexist = 0;
		int regionpos = 100;
		int countnoun=0;
		int sign=0;
		//int batexist = 0;

		System.out.println("no of words ="+nwords+"  k2p="+k2p+"  k7t="+k7t+"  eko="+eko+"  eka = "+eka+" ika ="+ika+" bat exist ="+batexist);
		try{
			for(int i = 0 ; i < nwords ; i++){
				if(sign == 0)
					sign = lg.getNegation2("\u0915\u094b",swords[i]);
				System.out.println("  stags["+i+"]="+stags[i]+"  sign of ko "+sign);
				if(stags[i].equals("BAT")){
					batexist = i;
				}
				System.out.println("  batexist ="+batexist);
				if(stags[i].equals("QTC") || stags[i].equals("PRP") || stags[i].equals("PRL") || stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("JJ") || stags[i].equals("RB"))
					countnoun++;
				System.out.println("countnoun = "+countnoun);
				if(!stags[i].equals(null)){
				  pt++;
				}
				else{
					break;
				}
			}
		}
		catch(NullPointerException nullPointer){}
		try{
		for(int i = 0 ; i < nwords ; i++){
				if(swords[i].equals("\u092a\u094d\u0930\u0926\u0947\u0936"))
				{
					 System.out.println("pradesh");
					regionexist = 1;
					regionpos = i;
					 System.out.println("pradesh regionexist ="+regionexist+"   region position ="+regionpos);
				}
			}
		}
		catch(NullPointerException nullPointer){}
		nwords = pt;
		int[] index = new int[nwords];
		//int[] pos = new int[5];
		String[][] ttn = new String[25][nwords];
		tagttn = new String[25][nwords];
		//try{
			if(count == 0){
				count = 0;
			//	ttn = new String[5][nwords];
				for(int q = 0 ; q < 25 ; q++){        // Assuming maximum 5 noun phases
					for(int p = 0 ; p < nwords ; p++){
						ttn[q][p] = "n";
					}
				}
				int lag1 = 0;
			
			//if(flag == 0){
			
				/*for(int i = 0 ; i < nwords ; i++){
					if(stags[i].equals("NN") || stags[i].equals("NNP") ){
						if(stags[i+1].equals("PSPLAI") || stags[i+1].equals("PSPLE") || stags[i+1].equals("PSPO") || stags[i+1].equals("PSPKO") ) {
							ttn[ind1][lag1] = swords[i];
							tagttn[ind1][lag1] = stags[i];
							ttn[ind1][lag1+1] = swords[i+1];
							tagttn[ind1][lag1+1] = stags[i+1];
							//System.out.println("	ttnle["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
							index[ind1] = i;
							ind1++;
							//System.out.println("chunker nwords="+nwords+"   count="+count);
							//lag1++;
							nounPhrase++;
							flag6 = 1;
							count++;
							if(stags[i+1].equals("PSPKO") && stags[i+2].equals("PSPO")){
								ttn[ind1-1][lag1+2] = swords[i+2];
								tagttn[ind1-1][lag1+2] = stags[i+2];
								//System.out.println("	Hi it is NN PSPKO PSPO");
								//System.out.println("	ttnle["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
								flag6 =1;o
								//count++;
								
							}
						}
						else{
							//System.out.println("chunker nwords="+nwords+"   count="+count+" NO CHUNKS");
							//ttn[ind1][lag1] = swords[i];
							//tagttn[ind1][lag1] = stags[i];
							//ind1++;
						}
						/*else if(stags[i+1].equals("PSPLAI")  ){
							ttn[ind1][lag1] = swords[i];
							//System.out.println("	ttnlai["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
							index[ind1] = i;
							ind1++;
							//lag1++;
						}*/
				//	}
					
			//	}
				//System.out.println("chunker nwords="+nwords+"   count="+count);
			}
		
		count = 0;
		//lag11 = 0;
		//System.out.println("chunker nwords="+nwords+"   out count="+count);
		if(count == 0 ){
			count = 1;
			//ttn = new String[5][nwords];
			/*for(int q = 0 ; q < 5 ; q++){        // Assuming maximum 5 noun phases
				for(int p = 0 ; p < nwords ; p++){
					ttn[q][p] = "n";
				}
			}*/
			lag = 0;
			ind = ind1;
			int stop = 1;
			int soch = 0;
			int sochind = 0;
			int mytrial = 0;
			int vfindex = 0;
			
			System.out.println("chunker nwords="+nwords+"  index["+ind+"]="+index[ind]);
			//if(flag == 0){
				int scope = 0;
				if(eka != 0){
					vfindex = eka;
				}
				for(int i = 0 ; i < nwords  ; i=i+scope+1){
					System.out.println("INSIDE FOR chunker nwords="+nwords+"  index["+ind+"]="+index[ind]);
						if(eka != 100 ){
						System.out.println("again noentry ="+noentry+"  eka ="+eka+"  stages["+(i+1)+"]"+stags[i+1]);
						if(stags[i].equals("VF") && stags[i+1].equals("NN") && !stags[i+2].equals("NN")){
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
									//	ttn[ind][lag+2] = swords[i+2];
									//	tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count = count + 2;
										verbindex = ind;
										ind++;
										scope = 1;
									//	vfindex = 0;
										
						}
						
					/*	if(stags[i].equals("NN") && stags[i+1].equals("NN") && !stags[i+2].equals("NN") && ika != 100){
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
									//	ttn[ind][lag+2] = swords[i+2];
									//	tagttn[ind][lag+2] = stags[i+2];
										System.out.println("IKA NN NN soch="+ika);
										nounPhrase++;
										count = count + 2;
										ind++;
										scope = 1;
									//	vfindex = 0;
						}*/
						if((stags[i].equals("VF") || stags[i].equals("VAUX")) && stags[i+1].equals("NN")  && stags[i+2].equals("NN")){
										System.out.println("VF NN NN soch="+soch);
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count++;
										verbindex = ind;
										ind++;
										scope = 2;
									//	vfindex = 0;
										

						}
						if(sign == 1 && stags[i].equals("VAUX") && stags[i+1].equals("NN")  && stags[i+2].equals("PSPLE")){
										System.out.println("VAUX NN PSPLE soch="+soch);
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count++;
										verbindex = ind;
										ind++;
										scope = 2;
									//	vfindex = 0;
										

						}
					}
					System.out.println("verbindex ="+verbindex);
						
				}
				for(int i = 0 ; i < nwords && (!stags[i].equals("VF") && !stags[i].equals("VAUX") ) ; i=i+scope+1){
					scope = 0;
					System.out.println("i="+i+"   scope="+scope);
					flag1 = 0;
					int danger = 0;
					/*if(stop == 0 && index[ind] != i ){
						//System.out.println(" danger i="+i+"   flag7="+flag7+"   count="+count);
						if(flag7 != 1){
							//System.out.println("if danger i="+i);
							flag6 = 0;
						}
						else if(i <= count){
								//System.out.println("else if danger i="+i);
								flag6 = 1;
						     }
						     else{
						     	flag6 = 0;
						     }
					}
					else{
					
						flag6 = 1;
						i= i + 2;
					}*/

					
					lag = i;
					
					//System.out.println("lag ="+lag+"   ind="+ind1+"   flag ="+flag+"     flag1 = "+flag1+"   flag2 ="+flag2+"  flag3="+flag3+" new word index ="+i+"  stags["+i+"]="+stags[i]);
					if(stags[i].equals("VMKO") || stags[i].equals("QW") ){
						if(stags[i+1].equals("NN")){
							noentry = i+1;
						}
					
					}
					System.out.println("noentry ="+noentry+"   scope="+scope);
					if(noentry == 0 && stags[i].equals("QW")){
						//System.out.println("Welcome QW world");
						ttn[ind][lag] = swords[lag];
						tagttn[ind][lag] = stags[lag];
						//System.out.println("tagttn["+ind+"]["+lag+"]="+tagttn[ind][lag]);
						ind++;
						 lag++;
						onlyqwflag = 1;
						scope = 0;
					}
					/*if(stags[i].equals("NN") || stags[i].equals("NNP")){
						if(stags[i+1].equals("JJ") ){
						}
					}*/
					/*if(stags[i].equals("PSPLE")){
						if(stags[i-1].equals("PRP") || stags[i-1].equals("PRL")){
							//ttn[ind][lag] = swords[lag];
							flag = 0;
						}
					}*/
					//for(int j = 0 ; j < 3 ; j++){
					// CHECKING NOUN FOLLOWED BY PRONOUN
				//	//System.out.println("stags["+i+"]="+stags[i]+"     stags["+(i+1)+"]="+stags[i+1]);
					//System.out.println("noentry ="+noentry+" 1  scope="+scope+"  k2pexist="+k2p+" k7texist="+k7t);
					/*if(eko == 0  && eka == 0){
						scope = 0;
					}
					else{
						scope = 100;
					}*/
					if( k2p ) {
						//scope = 10;
						//System.out.println("k2p malyu");
					}
					if(k7t){
						scope = 10;
					}
					if(scope != 10)
					{
						scope = 0;
					}
					System.out.println("stags["+i+"]="+stags[i]+" stags["+(i+1)+"]="+stags[i+1]+"  scope="+scope);
					if( scope != 10 && stags[i].equals("NN") && stags[i+1].equals("NN") ){
						if(!stags[i+2].equals("PSPLE") ){
							scope = 100;
							System.out.println("malyu scope ="+scope);
						}
						else{
							scope = 50;
						}
					}
					else{
						//scope = 0;
						//mytrial = 1;
						//System.out.println("noentry ="+noentry+"   scope="+scope+"  regionexist="+regionexist+" mytrial="+mytrial);
					}

					//System.out.println("before soch="+soch);
					if(stags[i].equals("NN") || stags[i].equals("NNP")){
						if(stags[i+1].equals("CCD") ){
								if((soch+1) == i || soch == 0){
									soch = soch+ i+1;
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.println("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.println("  soch="+soch);
									//scope =  1;
									nounPhrase++;
									count++;
									ind++;
									scope = 1;
								}
								else{
									soch = 100;
									//System.out.println("else  soch="+soch);
								}
									
									//lag++;
						}
						else if(soch > 0){
							ttn[ind][lag] = swords[lag];
							tagttn[ind][lag] = stags[lag];
							System.out.println("Last soch="+soch);
						}
					}
					System.out.println("scope ="+scope+" stags["+i+"]="+stags[i]+"  stags["+(i+1)+"]="+stags[i+1]);
					if(scope == 10 && stags[i].equals("JJ") && stags[i+1].equals("NN")){
											ttn[ind][lag] = swords[lag];
											tagttn[ind][lag] = stags[lag];
											ttn[ind][lag+1] = swords[lag+1];
											tagttn[ind][lag+1] = stags[lag+1];
											System.out.println("	only special k7t exist SCOPE = 10 JJ NN  ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
											ind++;
											lag++;
											scope = 1;
											flag = 1;
								}
					System.out.println("flag ="+flag+"  danger ="+danger);
					if(scope == 100 && (stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("PRL") || stags[i].equals("PRP"))){
						if(stags[i+1].equals("PSPLE") ||stags[i+1].equals("PSPLAI") || stags[i+1].equals("PSPO")   ){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									nounPhrase++;
									count++;
									ind++;
									flag = 1;
									scope = 1;
						}
					}
					if(scope == 10 && (stags[i].equals("DMD") || stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("PRL") || stags[i].equals("PRP"))){
						if(stags[i+1].equals("NN") || stags[i+1].equals("PSPLE") || stags[i+1].equals("PSPLAI") || stags[i+1].equals("PSPO")   ){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	DMD NN ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									nounPhrase++;
									count++;
									ind++;
									danger = 1;
									flag = 1;
									scope = 1;
									System.out.println("dange = "+danger+"  flag="+flag);
						}
					}
					System.out.println("outside dange = "+danger+"  flag="+flag);
					if(stags[i].equals("NN") && stags[i+1].equals("NN") && stags[i+2].equals("PSPO") && ika != 100){
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										System.out.println("IKA NN NN soch="+ika);
										nounPhrase++;
										count = count + 3;
										ind++;
										scope = 2;
										flag = 1;
									//	vfindex = 0;
						}
						if(stags[i].equals("NN") && stags[i+1].equals("NN") && stags[i+2].equals("PSPO") && ika == 100 && scope == 10){
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										System.out.println("IKA NN NN soch="+ika);
										nounPhrase++;
										count = count + 3;
										ind++;
										scope = 2;
										flag = 1;
									//	vfindex = 0;
						}
						if(stags[i].equals("NN") && stags[i+1].equals("PSPKO") && stags[i+2].equals("NN") && eko != 0){
										//ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										System.out.println("NN eko NN soch="+eko);
										nounPhrase++;
										if(stags[lag+3].equals("NN") || stags[lag+3].equals("PSPLE") || stags[lag+3].equals("PSPLAI") ){
											System.out.println("2 NN eko NN");
											ttn[ind][lag+3] = swords[lag+3];
											tagttn[ind][lag+3] = stags[lag+3];
											scope = 3 ;
										}
										count = count + 3;
										ind++;
										scope = 2;
										flag = 1;
									//	vfindex = 0;
						}
					if(soch == 100){
						nounPhrase++;
						ind++;
						scope =sochind+1;
						//break;
						//System.out.println("Last scope="+scope);
					}
				/*	if(regionexist == 1 ){
						//System.out.println("1 noentry ="+noentry+"   scope="+scope+"  regionexist="+regionexist);
						if(stags[regionpos-1].equals("NN") || stags[regionpos-1].equals("NNP")){
							//System.out.println("2 noentry ="+noentry+"   scope="+scope+"  regionexist="+regionexist);
							if(stags[regionpos+1].equals("PSPO") || stags[regionpos+1].equals("PSPKO")){
								//System.out.println("3 noentry ="+noentry+"   scope="+scope+"  regionexist="+regionexist);
								//System.out.println("WELL DONE");
								ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];
									//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count);
									scope = 3;
									nounPhrase++;
									count++;
									ind++;
									noentry = 0;
									regionpos= 100;
									regionexist = 0;
								//	mytrial = 0;
									//ttn[ind][lag+2] = swords
							}
						}


					}*/
					System.out.println("again noentry ="+noentry+"   scope="+scope+"   stags["+i+"]="+stags[i]+" eko="+eko+"  eka ="+eka);
					
					if(stags[i].equals("RB") ){
								System.out.print("RB JJ NN");
								if(stags[i+1].equals("JJ")) {
									if(stags[i+2].equals("NN")) {
										System.out.println(" VF NN NN soch="+soch);
										ind++;
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count = count + 3;
										ind++;
										scope = 2;
									}
								}
							}
							if(stags[i].equals("PRP") || stags[i].equals("PRL")){
						if(stags[i+1].equals("PSPO") ){
							if(stags[i+2].equals("NN") || stags[i+2].equals("NNP")){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.println("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.println("  soch="+soch);
									scope =  1;
									nounPhrase++;
									count = count++;
									ind++;
								}
						}
					}
						if(stags[i].equals("NNP") && stags[i+1].equals("NN") && (stags[i+2].equals("PSPLAI") || stags[i+2].equals("PSPLE"))){
							System.out.println("  Common case NNP NN PSPLE");				
							onlynnflag = 1;
							nounPhrase++;
							ttn[ind][lag] = swords[lag];
							tagttn[ind][lag] = stags[lag];
							ind++;
						}


						if((soch == 0 || soch== 100 ) && scope == 0 && noentry == -1 && ( stags[i].equals("PRF") || stags[i].equals("PRP") || stags[i].equals("PRL") || stags[i].equals("NN") || stags[i].equals("DMD") || stags[i].equals("DM") || stags[i].equals("DMI") || stags[i].equals("DMR"))){
							//if(stags[i+1].equals("PSPKO")){
							System.out.println("  my trial");
							System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count+"  stags["+i+"]="+stags[i]);
								mytrial = 1;

								if( batexist == 0 && !k2p && stags[i+1].equals("NN") || stags[i+1].equals("NNP") || stags[i+1].equals("PSPKO") || stags[i+1].equals("QTO") || stags[i+1].equals("DM") || stags[i+1].equals("DMR") || stags[i+1].equals("DMI") || stags[i+1].equals("DMD")){
									if(!stags[i+2].equals("VF") && !stags[i+2].equals("VAUX"))
										mytrial = 2;
									System.out.println("my trial="+mytrial+"  stags ="+stags[i+2]+"  stags3[i+3]="+stags[i+3]);
									if((!stags[i+3].equals("VF") && !stags[i+3].equals("VAUX")) && (stags[i+2].equals("NN") || stags[i+2].equals("NNP") || stags[i+2].equals("PRP") || stags[i+2].equals("PRL"))){
										System.out.println(" PRP NN JJ chalo ARE	");
										mytrial = 0;
										ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];
									//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count);
									//ttn[ind][lag+2] = swords[lag+2];								
									count=count+1;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									flag = 1;
									scope = 2;
									lag++;
									}
								//	scope = 1;
									if(scope == 0 && !stags[i+3].equals("VAUX") && (stags[i+2].equals("JJ") || /*stags[i+2].equals("PSPO") || /*stags[i+2].equals("NN") || */ stags[i+2].equals("PSPLAI") || stags[i+2].equals("PSPLE") || (stags[i+2].equals("NN") && sign == 1))){//&& mustjj == 1){
										System.out.println(" PRP NN JJ chalo	");
										nounPhrase++;
										mytrial = 0;
										//System.out.println("nwords="+nwords+"   index["+0+"]="+index[0]+"  i="+i);
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+2)){
											//System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}*/
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];
									//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count);
									//ttn[ind][lag+2] = swords[lag+2];								
									count=count+1;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									flag = 1;
									scope = 2;
									lag++;
									//System.out.println("scope="+scope);
									//ind++;
								}
								//}
								else if( !k2p && scope == 0 && flag1 != 1 && flag != 1 &&  !stags[i+2].equals("PSPKO") && !stags[i+2].equals("PSPO") && !stags[i+2].equals("JJ") && !stags[i+2].equals("HRU") && mustjj == 0 && !stags[i+2].equals("VAUX") && !stags[i+2].equals("VF")){
										System.out.println(" chalo	");
										nounPhrase++;
										mytrial = 0;
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[lag+1];
										tagttn[ind][lag+1] = stags[lag + 1];
										System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
									/*	if(stags[i+2].equals("PSPKO") || stags[i+2].equals("PSPO") || (stags[i+2].equals("JJ") && !stags[i+3].equals("VAUX") ) || stags[i+2].equals("HRU") || stags[i+2].equals("DM") || stags[i+2].equals("DMR") || (stags[i+2].equals("NN") && sign == 1)){
										 	System.out.println("what happen");
										 	ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag + 2];
											scope = 2;
									    }
									    else{*/
									    	scope = 1;
									 //   }
										//ttn[ind][lag+2] = swords[lag+2];								
										count++;
									//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
										//System.out.println();
										ind++;
										flag = 1;
										//scope = 1;
										
									//System.out.println("scope="+scope);
										
								}
								else if(mytrial == 2  && mustjj == 0 && !k2p && !stags[i+2].equals("VF") && !stags[i+2].equals("VAUX") && batexist != 0){

									System.out.println(" chalo NN NN PSPO	");
										nounPhrase++;
										mytrial = 0;
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[lag+1];
										tagttn[ind][lag+1] = stags[lag + 1];
										System.out.println("stags ="+stags[i+2]+" next stags ="+stags[i+3]);
										if(stags[i+2].equals("PSPKO") || stags[i+2].equals("PSPO") || (stags[i+2].equals("JJ") && !stags[i+3].equals("VAUX") ) || stags[i+2].equals("HRU") || stags[i+2].equals("DM") || stags[i+2].equals("DMR") || (stags[i+2].equals("NN") && sign == 1)){
										 	System.out.println("what happen");
										 	ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag + 2];
											scope = 2;
									    }
									    else{
									    	scope = 1;
									    }
									    ind++;
									    count++;
										//System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
										//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count);
										//scope = 1;
										flag = 1;
								}
								//System.out.println("extra my trial = "+mytrial);
								}
								

								//System.out.println("my trial = "+mytrial);	
								if(mytrial == 1 ){
									System.out.println(" my trial PRP RB");
								if(batexist == 0 && stags[i].equals("PRP") || stags[i].equals("PRL")) {
									if(!k7t && !k2p && (stags[i+1].equals("RB") || stags[i+1].equals("PSPLAI") || stags[i+1].equals("PSPLE") || stags[i+1].equals("HRU") || stags[i+1].equals("NN"))){
										if(!stags[i+2].equals("NN") && !stags[i+2].equals("NNP")){
								//if(!stags[i+1].equals("PSPLE") && !stags[i+1].equals("PSPLAI") && !stags[i+1].equals("JJ") && !stags[i+1].equals("PSPKO") && !stags[i+1].equals("QF") && !stags[i+1].equals("QTC") && !stags[i+1].equals("JJ") ){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.println("	only PRP RB ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									//onlynnflag = 1;
									scope = 1;
									mytrial = 0;
									flag3=1;
									//System.out.println("scope="+scope);
									}
								    }
								    else if(!stags[i+2].equals("VF") && !stags[i+2].equals("VAUX")){
								    	ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.println("	only PRP ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
										count++;
										nounPhrase++;
										ind++;
										lag++;
									//onlynnflag = 1;
										scope = 0;
										mytrial = 0;
								    }
								}
							}
								//}
							}
							else if(mytrial == 1 || ( stags[i].equals("NN")) || ( stags[i].equals("RB"))|| (scope == 0 && stags[i].equals("QW"))   || stags[i].equals("NNP") || stags[i].equals("PRP")|| stags[i].equals("DMD") || (flag1 == 2 && flag2 == 2 && flag3 == 2)){
								System.out.println("my trial one mytrial="+mytrial);
								System.out.println("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count+"  stags["+i+"]="+stags[i]);
								System.out.println("   soch="+soch+"  scope="+scope);
								

								if(!stags[i].equals("JJ")  && (stags[i+1].equals("NN") && stags[i+2].equals("VF")) || (stags[i+1].equals("NN") && stags[i+2].equals("VAUX"))){
									//if(!stags[i].equals("VF") ){
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
									//}
									System.out.println("	only special NN  ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									onlynnflag = 1;
									scope = 0;

									//System.out.println("scope="+scope);
									mytrial = 0;
								}
								if((soch == 0 || soch == 100) && scope != 100 && !stags[i+1].equals("PSPLE") && !stags[i+1].equals("PSPLAI") && !stags[i+1].equals("JJ") && !stags[i+1].equals("PSPKO") && !stags[i+1].equals("QF") && !stags[i+1].equals("QTC")  && !stags[i+1].equals("PSPO") && !stags[i+1].equals("HRU") && !stags[i+1].equals("DM") && !stags[i+1].equals("NN")){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	only NN  ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									onlynnflag = 1;
									scope = 0;

									//System.out.println("scope="+scope);
									mytrial = 0;
								}
								else if(stags[lag+1].equals("JJ") || stags[lag+1].equals("PSPKO") || stags[lag+1].equals("PSPO")|| stags[lag+1].equals("DM") || stags[lag+1].equals("DMI") || stags[lag+1].equals("DMD") || stags[lag+1].equals("DMR")|| stags[lag+1].equals("NN") || scope == 100){
										System.out.println("stags["+(lag+2)+"]="+stags[lag+2]);
										if(scope == 0 && !stags[lag+2].equals("PSPLAI") && !stags[lag+2].equals("PSPLE") && !stags[lag+2].equals("PSPKO") && !stags[lag+2].equals("PSPO") && !stags[lag+2].equals("HRU") && !stags[lag+2].equals("VF") && !stags[lag+2].equals("VAUX")){
											
											//scope =0;
											System.out.println("before scope="+scope);
											ttn[ind][lag] = swords[lag];
											tagttn[ind][lag] = stags[lag];
											if(scope != 10  && nwords > 8){
												
												System.out.println("long sentence");
												ttn[ind][lag+1] = swords[lag+1];
												tagttn[ind][lag+1] = stags[lag+1];
												scope = 1;
												//ind++;
												//count++;
												//nounPhrase++;
											}
											else if(soch == 0){
												scope = 0;
											}
											System.out.println("	only NN JJ ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
											count++;
											nounPhrase++;
											ind++;
									//lag++;
											onlynnflag = 0;
											//scope = 1;
											//System.out.println("scope="+scope);
										}
										else if(scope == 0 && !stags[lag].equals("NNP") && !stags[lag+2].equals("PSPLE") && !stags[lag+2].equals("VF")){
											ttn[ind][lag] = swords[lag];
											tagttn[ind][lag] = stags[lag];
											ttn[ind][lag+1] = swords[lag+1];
											tagttn[ind][lag+1] = stags[lag+1];
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											System.out.println("	only NN JJ PSPO ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
											count++;
											nounPhrase++;
											ind++;

									//lag++;
											onlynnflag = 0;
											scope = 2;
											//System.out.println("scope="+scope);
										}
								}
								else
									onlynnflag = 1;
								if(mytrial == 0 && scope == 0 && noentry == 0 && onlyqwflag == 0 && flag3 == 0 && jjnnflag == 0 && (stags[lag+1].equals("VF") || stags[lag+1].equals("VAUX"))){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	new ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									flag = 1;
									scope = 0;
									//System.out.println("scope="+scope);
								}
						        if(mytrial == 0 && flag == 1 && jjnnflag == 0){
						        	flag = 2;
								//ttn[ind][lag] = swords[lag];
								System.out.println("	*ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								
								
								//ind++;
							}else if((flag6 == 0 && flag1 == 0 && flag2 ==0 && flag3 == 0 && flag != 2)  ){
								flag = 3;
								//flag = 0;
								/*try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									count++;
									nounPhrase++;
									
									//System.out.println("	1ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i+" stags["+(lag+1)+"]="+stags[lag+1]);
								}
								catch(ArrayIndexOutOfBoundsException exception) {
 									   
								}
								
								ind++;*/
							}
							
						//count = 0;
						   //   }
						 //     flag = 0;
						//break;
						
						}
						//int flag1 = 0;
						
						
						// CHECKING NOUN FOLLOWED BY PSPLE or PSPLAI and then PRONOUN
						if(regionexist != 1 && (scope == 0 || scope == 10) && flag != 1 && noentry == -1 &&  ( stags[i].equals("PRP") || stags[i].equals("QW") || stags[i].equals("PRL") || stags[i].equals("PRF") || stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("DMI"))){
							if(stags[i+1].equals("PSPKO") || stags[i+1].equals("PSPLE") || stags[i+1].equals("PSPLAI") ||  stags[i+1].equals("PSPO") || stags[i+1].equals("JJ") || stags[i+1].equals("RP") || stags[i+1].equals("HRU")){
								if(!stags[i+2].equals("NN") && !stags[i+2].equals("NNP") && !stags[i+2].equals("PSPO") && !stags[i+2].equals("RB")){
								//if(stags[i+2].equals("NN") || stags[i+2].equals("NNP")){
									System.out.println(" Lai or Le chalo hi	");
									nounPhrase++;
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+2)){
											//System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}*/
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									//ttn[ind][lag+2] = swords[lag+2];
									//tagttn[ind][lag+2] = stags[lag+2];
									//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count+"  i="+i);
									//ttn[ind][lag+2] = swords[lag+2];								
									count=count+1;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									scope = 1;
									//System.out.println("scope="+scope);
									flag1 = 1;
									//ind++;
									/*if(i < (i+lag+1)){
										flag1 = 1;
										//System.out.print("causion (i+lag+1)="+(i+lag+1));
									}
									else
										flag1 = 0;*/
									}
									
									else if(!stags[i+1].equals("JJ") && danger == 0 ){
										System.out.println( "DMI RP NN flag ="+flag+" danger ="+danger);
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[lag+1];
										tagttn[ind][lag+1] = stags[lag+1];
										System.out.println("stags[ind]="+stags[ind]+"  stags[ind+1]="+stags[ind+1]);
										if(!stags[i].equals("PRP") && !stags[i].equals("PRL") && !stags[i].equals("NN") && !stags[i].equals("NNP") ){
											System.out.print("NN PSPO NN");
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											scope = 2 ;
										}
										if(stags[lag+2].equals("PSPO") ){
											//System.out.println("1 NN PSPO NN");
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											scope = 2 ;
										}
										if(stags[lag+2].equals("RB") ){
											//System.out.println("2 NN PSPO RB");
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											scope = 2 ;
										}
										//System.out.println("2 NN PSPO NN");
										count = count + 1;
										ind=ind+1;;
										flag1 = 1;
										if(scope != 2)
											scope = 1;
									//System.out.println("scope="+scope);
									
									}
									
								//}
							}
							else if(mytrial == 1 && scope == 0 && flag1 != 1 && flag == 0 && onlyqwflag == 0 && onlynnflag == 0){
									System.out.println("flag="+flag);
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									
									//if(flag != 1 && flag1 != 1){
										flag5 = 1;
										scope = 0;
									//System.out.println("scope="+scope);
										System.out.print("****single	ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"  count="+count+"  tagttn="+tagttn[ind][lag]);
										ind++;
									count++;
									nounPhrase++;
									onlynnflag = 1;
									mytrial = 0;
								//	}
								}
						}
						else if((mytrial == 1 || scope == 0 && (stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("PRP") || stags[i].equals("PRL")|| (flag1 ==2 && flag2 == 2 && flag3 == 2))) ){
							System.out.println("third stags["+(i+1)+"]="+stags[i+1]);
							if(mytrial == 2 ){
									System.out.println("Only NN");
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									nounPhrase++;
									ind++; 
									count++;
									scope = 0;
									mytrial = 0;
								}
						        if(flag1 == 1 && onlynnflag != 1 && onlyqwflag == 0){
						        	flag1 = 2;
						        	if(!stags[lag+1].equals("VF")  && !stags[lag+1].equals("VAUX") && !stags[lag+1].equals("VMKO") && !stags[lag+1].equals("VMNE")){
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									scope = 1;
									//System.out.println("scope="+scope);
								}
								System.out.println("	***ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								ind++;
								ct = 1;
							}else if(flag6 == 0 && flag == 0 && flag2 == 0 && flag3 == 0 && flag1 != 2){
								flag1 = 0;
							//	try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									scope = 0;
									//System.out.println("scope="+scope);
									System.out.println("	2ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i+"  swords["+lag+"]="+swords[lag]);
								//}
								//catch(ArrayIndexOutOfBoundsException exception) {
 									   
								//}
								ind++;
								ct = 1;
							}
							
						//count = 0;
						   //   }
						 //     flag = 0;
						//break;
						
						}
						
						//flag = 0;
						// CHECKING NOUN FOLLOWED BY Quantity
						if(regionexist != 1 && stags[i].equals("QC") || stags[i].equals("QF") || stags[i].equals("QTC") || stags[i].equals("QTO") ){
							//if(stags[i+1].equals("PSPKO")){
								if(stags[i+1].equals("NN") || stags[i+1].equals("NNP") ){
									if(stags[i+2].equals("PSPKO")){

										if(stags[i+3].equals("NN") || stags[i+3].equals("NNP")){
											ttn[ind][lag] = swords[lag];
											tagttn[ind][lag] = stags[lag];
											//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
											ttn[ind][lag+1] = swords[lag+1];
											tagttn[ind][lag+1] = stags[lag+1];
											System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											ttn[ind][lag+3] = swords[lag+3];
											tagttn[ind][lag+3] = stags[lag+3];
									//ttn[ind][lag+2] = swords[lag+2];								
											nounPhrase++;
										    count++;
										    scope = 3;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
											//System.out.println();
											ind++;
											//flag3 = 1;
											//scope = 1;
											//noentry = 0;
											//System.out.println(" QC NN PSPKO NN scope="+scope);

										}
										else{
											ttn[ind][lag] = swords[lag];
											tagttn[ind][lag] = stags[lag];
											//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
											ttn[ind][lag+1] = swords[lag+1];
											tagttn[ind][lag+1] = stags[lag+1];
											//System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											nounPhrase++;
										    count++;
										    scope = 2;
											System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
											//System.out.println();
											ind++;
											//flag3 = 1;
											//noentry = 0;

										//	scope = 1;
											//System.out.println(" QC NN PSPKO scope="+scope);
										}
									}
									else{
									System.out.println(" QC FAST chalo	");
									nounPhrase++;
									for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+1)){
											//System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
									if(stags[lag+2].equals("NN") || stags[lag+2].equals("PSPLE") || stags[lag+2].equals("PSPLAI") ){
											System.out.println("2 QTO NN NN");
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											scope = 2 ;
										}
									//ttn[ind][lag+2] = swords[lag+2];								
									count++;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									flag3 = 1;
									if(scope != 2)
										scope = 1;
									//System.out.println("scope="+scope);
									}
									
								}
								if(i> 0 && regionexist != 1 && flag3 == 0 && (stags[i-1].equals("NN") || stags[i-1].equals("NNP") )){
									System.out.println(" QW FAST chalo back	");
									nounPhrase++;
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+1)){
											//System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}*/
									lag = lag - 1;
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
									//ttn[ind][lag+2] = swords[lag+2];								
									count++;
								//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									flag3 = 1;
									scope = 1;
									//System.out.println("scope="+scope);
								
								}
							//}
						}
						else if(stags[lag].equals("NN") || stags[lag].equals("NNP") || (flag == 2 && flag1 == 2)){
						        if(flag3 == 1){
						        	flag3 = 2;
								//ttn[ind][lag] = swords[lag];
								System.out.println("	*ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								//ind++;
							}else if(onlynnflag == 0 && flag6 == 0 &&  flag1 == 0 && flag == 0 && flag2 == 0 && flag3 != 2){
								flag = 3;
								//flag = 0;
								try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									onlynnflag = 1;
									ind++;
									count++;
									System.out.println("	4ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
									scope = 0;
									//System.out.println("scope="+scope);
									nounPhrase++;
								}
								catch(ArrayIndexOutOfBoundsException exception) {
 									   
								}
								
								//ind++;
							}
							
						//count = 0;
						   //   }
						 //     flag = 0;
						//break;
						
						}
						if(regionexist != 1 && noentry == -1 && stags[i].equals("RP") ){
							if(stags[i+1].equals("NN") || stags[i+1].equals("JJ")){
								if(stags[i+2].equals("NN")){
									if(stags[i+3].equals("PSPO") || stags[i+3].equals("PSPKO") || stags[i+3].equals("PSLAI") || stags[i+3].equals("PSPLE"))
										System.out.println(" FAST chalo RP JJ NN PSPO	nicely");
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										ttn[ind][lag+3] = swords[i+3];
										tagttn[ind][lag+3] = stags[i+3];
										nounPhrase++;
										count = count + 4;
										ind++;
										flagRP = 1;
										scope = 3;
								}
							}


						}
						
						// CHECKING NOUN FOLLOWED BY Adjective
						if(flag == 0 && regionexist != 1 && noentry == -1 && stags[i].equals("JJ")  && onlynnflag != 0){
							//if(stags[i+1].equals("PSPKO")){
								if(stags[i+1].equals("NN") || stags[i+1].equals("NNP")){
									if(stags[i+2].equals("PSPLE") || stags[i+2].equals("PSPLAI") || stags[i+2].equals("PSPO") || stags[i+2].equals("HRU")){
										System.out.println(" FAST chalo	nicely");
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count = count + 3;
										ind++;
										flag7 = 1;
										scope = 2;
									//System.out.println("scope="+scope);
										//index[ind]
									}
									else {
										
										System.out.println(" FAST chalo	");
										System.out.println("ind ="+ind+" lag="+lag+"   stags["+lag+"]="+stags[lag]);
										nounPhrase++;
										for(int k = 0 ; k < nwords ; k++){
											if(index[k] == (i+1)){
												//System.out.print("index["+k+"]="+index[k]);
												ttn[k][0] = "n";
												ind = k;
											}
										}
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										//System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
										//ttn[ind][lag+2] = swords[lag+2];								
										count++;
									//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
										//System.out.println();
										ind++;
										flag2 = 1;
										jjnnflag =1;
										scope = 1;
									//System.out.println("scope="+scope);
									}
								}
								else if(flag == 0 && flag1 == 0){
									ttn[ind][lag] = swords[i];
									tagttn[ind][lag] = stags[i];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ind++;
									scope = 0;
									System.out.println(" 123 scope="+scope);
								}
							}
							else if(stags[i].equals("NN") || stags[i].equals("NNP") || (flag == 2 && flag1 == 2)){
						        	if( flag2 == 1){
						        		flag2 = 2;
						        		System.out.println("Hi its else JJ NN ");
								//ttn[ind][lag] = swords[lag];
							//	//System.out.println("	*ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								//ind++;
							}else if(onlynnflag == 0 && flag6 == 0 && flag1 == 0 && flag == 0 && flag3 == 0 && flag2 != 2){
								flag = 3;
								flag = 0;
								//try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.println("	3ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
									scope = 0;
									//System.out.println("scope="+scope);
								//}
								//catch(ArrayIndexOutOfBoundsException exception) {
 									   
								//}
								
								ind++;
							}
							if(mytrial == 1 && flag1 != 1  ){
		System.out.println("   my trial two mytrial="+mytrial);
								if(!stags[i+1].equals("PSPLE") && !stags[i+1].equals("PSPLAI") && !stags[i+1].equals("JJ") && !stags[i+1].equals("PSPKO") && !stags[i+1].equals("QF") && !stags[i+1].equals("QTC") && !stags[i+1].equals("JJ") ){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	only NN  ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									onlynnflag = 1;
									scope = 0;
									mytrial = 0;
									System.out.println(" 234 scope="+scope);
								}
								if(stags[i+1].equals("PSPLE") || stags[i+1].equals("PSPLAI")|| stags[i+1].equals("PSPO")){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.println("	only NN NN ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									onlynnflag = 0;
									scope = 1;
									mytrial = 0;
									//System.out.println("scope="+scope);
								}
								

							}
							System.out.println("new my trial ="+mytrial);
							System.out.println("scope = "+scope+"  soch = "+soch);
							if(soch > 0 && soch != 100){
								//System.out.println("scope = "+scope+"  soch = "+soch);
								scope = 1;
							}
							if(stags[i].equals("RB") ){
								//System.out.print("RB JJ NN");
								if(stags[i+1].equals("JJ")) {
									if(stags[i+2].equals("NN")) {
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										ttn[ind][lag+2] = swords[i+2];
										tagttn[ind][lag+2] = stags[i+2];
										nounPhrase++;
										count = count + 3;
										ind++;
										scope = 2;
									}
								}
							}
							
							
						//count = 0;
						   //   }
						 //     flag = 0;
						//break;
						
						}
						System.out.println("new my trial ="+mytrial+" i="+i);
						System.out.println("scope = "+scope+"  soch = "+soch);
						if(scope == 10 || scope == 100){
							scope = 0;
						}
				//} 
			
				}
				//System.out.println("larger new my trial ="+mytrial+" i="+i);
			//}
		}
		
			/*if(stags[i].equals("PP$") ){
				//System.out.println("hi");
				ttn[count][i] = swords[i];
				//System.out.println("///FLAG ="+flag+" ttn["+count+"]["+i+"]="+ttn[count][i]);
				count++; 
			}
			
			
			else if(count%2 !=  0 && count > 0){
				if(stags[i].equals("NN") || stags[i].equals("NNP")){
					//pos[i] = 0;
					ttn[count-1][i] = swords[i];
					flag = i;
					//System.out.println("***FLAG ="+flag+" ttn["+(count-1)+"]["+i+"]="+ttn[count-1][i]);
					count++;
				}
			}
			
			else if(count == 0){
				ttn[0][0] = "null1";
			}
			if(flag == 0){
				
				ttn[count][i] = swords[i];
				//System.out.println("FLAG ="+flag+" ttn["+count+"]["+i+"]="+ttn[count][i]);
			}
			
			
			
		}
		//System.out.println("count ="+count);
		for(int i = 0 ; i < nwords;i++){
			for(int j = 0 ; j < 3 ; j++){
				if(stags[i].equals("NN") || stags[i].equals("NNP")){
						if(i > 1 && count != 0){
							ttn[i-(count-1)][j] = swords[i];
							//System.out.print("	1ttn["+(i-(count-1))+"]["+j+"]= "+ttn[i-(count-1)][j]);
						}
										
				}
			}
			//System.out.println();
		}*/
		
		//System.out.println("1count="+count+" ind="+ind);
		int wdchunk=0;
		for(int i = 0 ; i < 5 ; i++){
			//int lag5 = 0;
			for(int j = 0 ; j < nwords ; j++){
			
				System.out.println("	semi final ttn["+i+"]["+j+"]= "+ttn[i][j]+" ");
				if(!ttn[i][j].equals("n")){
					wdchunk++;
				}
				
				//for(int i = 0 ; i < nwords ; i++){
				/*if(ttn[i][j].equals("n")){
			   		if(stags[j].equals("PRP") || stags[j].equals("PRL")){
						if(stags[j+1].equals("PSPLE") && stags[j+2].equals){
							ttn[i][lag5] = swords[j];
							tagttn[i][lag5] = stags[j];
							ttn[i][lag5+1] = swords[j+1];
							tagttn[i][lag5+1] = stags[j+1];
							//System.out.println(" double	else 4ttn["+i+"]["+(lag5)+"]= "+ttn[i][lag5] +"count="+count);
							//ind++;
						}
					}
				}*/
			//}
		//}
			}
		}
		if(count == 0){
			count = 0;
			//ttn = new String[5][nwords];
			/*for(int q = 0 ; q < 5 ; q++){        // Assuming maximum 5 noun phases
				for(int p = 0 ; p < nwords ; p++){
					ttn[q][p] = "n";
				}
			}*/
			lag = 0;
			ind = ind1;
			//if(flag == 0){
				for(int i = 0 ; i < nwords ; i++){
					lag = i;
					//System.out.println(" NN PSPKO PSPO lag ="+lag);
					//for(int j = 0 ; j < 3 ; j++){
						/*if(stags[0].equals("NN") || stags[0].equals("NNP")){
							ind++;
						}*/
						if(noentry == -1 && (stags[i].equals("JJ")|| stags[i].equals("NN"))){
							if(stags[i+1].equals("PSPKO")){
								if(stags[i+2].equals("NN") || stags[i+2].equals("NNP") || stags[i+2].equals("PSPO") || stags[i+2].equals("HRU")){
									nounPhrase++;								
									for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+2)){
											System.out.println("index JJ["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"count="+count);
									
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									//System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"count="+count);
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];								
									count++;
									//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									//System.out.println();
									ind++;
									
								}
							}
							
						}
						else{
							if(stags[ind].equals("NN") || stags[ind].equals("NNP") || stags[ind].equals("RB")){
								if(count > 0){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	else 4ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count);
									ind++;
								}
							}
						//count = 0;
						//break;
						
						}
						if(stags[ind].equals("PRP") || stags[ind].equals("PRL") || stags[ind].equals("PRF")){
								if(stags[i+1].equals("PSPLE")){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									//System.out.println(" double	else 4ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count);
									ind++;
								}
							}
						
						//}
				//} 
			
				//}
			//}

		   	}

		   }
		   
		/*count = 0;
		for(int i = 0 ; i < nwords ; i++){
			//System.out.println("new i = "+i);
			if(stags[i].equals("JJ")){
				if(stags[i+1].equals("PKO")){
					if(stags[i+2].equals("NN") || stags[i+2].equals("NNP")){
						ttn[count][i] = swords[i];
						//System.out.print("	PKO ttn["+count+"]["+i+"]= "+ttn[count][i]);						
						ttn[count+1][i+1] = swords[i+1];
						ttn[count+2][i+2] = swords[i+2];
						break;
					}
				}
			}
		}*/
		//if(count == 0){
		//	return null1;
		//}
		//else{	
		int come = 0;
		int trynext = 0;
		// IF THERE IS NO NOUN PHRASE
		System.out.println("ind = "+ind+"    wdchunk="+wdchunk+"    nwords="+nwords+"  noentry="+noentry);
		int tind = 0;
		for(int l = 0 ; l < nwords && wdchunk < nwords/2 ; l++){
			System.out.println("l="+l+"    stags["+l+"]="+stags[l]);
			if(ind == 0 ){//|| l == 0){
				if(noentry == -1 && (stags[l].equals("QTC") || stags[l].equals("NN") || stags[l].equals("NNP") || stags[l].equals("PRP") || stags[l].equals("PRL"))){
					if(!stags[l+1].equals("PSPLE")){
//					temp = ttn[ind][lag]
					System.out.println("hki");
					ttn[tind ][l] = swords[l];
					tagttn[tind][l] = stags[l];
					tind++;
					nounPhrase++;
					}
					else{
						//if(scope == 50){
							System.out.println("scope 50 hki");
							ttn[tind ][l] = swords[l];
							tagttn[tind][l] = stags[l];
							ttn[tind ][l+1] = swords[l+1];
							tagttn[tind][l+1] = stags[l+1];
							tind++;
							nounPhrase++;
						//}
					}
				}
				if(stags[l].equals("JJ")){
							System.out.println(" JJ hki");
							ttn[tind][l] = swords[l];
							tagttn[tind][l] = stags[l];
							nounPhrase++;
							tind++;
						}
				
			}
			else if(l > (wdchunk-1)){
				if(noentry == -1 && stags[l].equals("PRF") && stags[l+1].equals("NN") || stags[l+1].equals("NNP") || stags[l+1].equals("PRP") || stags[l+1].equals("PRL")){
					System.out.println("hki early l = "+l);
					ttn[ind ][l] = swords[l];
					tagttn[ind][l] = stags[l];
					ttn[ind ][l+1] = swords[l+1];
					tagttn[ind][l+1] = stags[l+1];
					ind++;
					nounPhrase++;
					trynext = 1;
				
				}
				else if(trynext == 0 &&noentry == -1 && (stags[l].equals("NN") || stags[l].equals("NNP") || stags[l].equals("PRP") || stags[l].equals("PRL"))){
					System.out.println("hki l = "+l);
					ttn[ind ][l] = swords[l];
					tagttn[ind][l] = stags[l];
					ind++;
					nounPhrase++;
				
				}
			}
			else{
				System.out.println("come ");
				//continue;
				come = 1;
				break;
			}
			
		}



		if(ind == 0){
			ind = tind;
		}
		if(ind > countnoun){
			for(int s = countnoun ; s < 20; s++ ){
				for(int r=0;r< nwords;r++){
					ttn[s][r] ="n";
					System.out.println("countnoun="+countnoun+"  ind="+ind);
				}
			}
		}
		String[] tag = {"NN","NNP","NST","PRP","PRL","JJ","QTC"};
		String[] tag1 = {"PRP","PRL"};
		int temp = 0;
		String[][] tempttn = new String[ind][nwords];
		String[][] temptagttn = new String[ind][nwords];
		System.out.println("onlynnflag="+onlynnflag);
		//System.out.println("come again");
		if(verbindex != 100){
			System.out.println("verb index replacement="+verbindex);
					tempttn = ttn;
					temptagttn = tagttn;
					for(int j = 0 ; j < nwords ; j++){
						ttn[ind][j] = tempttn[0][j];
						tagttn[ind][j] = temptagttn[0][j];
					}
		}
		for(int i = 0 ; i < 7 ; i++){
			System.out.println(" Final");
			for(int j = 0 ; j < nwords ; j++){
				if(verbindex != 100){
					System.out.println("verb index final replacement="+i+"  ind="+ind);
					ttn[i][j] = ttn[i+1][j];
					tagttn[i][j] = tagttn[i+1][j];
					//ttn[ind-1][j] = tempttn[0][j];
					//tagttn[ind-1][j] = temptagttn[0][j];
					
				}
				System.out.println("	final ttn["+i+"]["+j+"]= "+ttn[i][j]+"     tagttn["+i+"]["+j+"]="+tagttn[i][j]);
				if(nounPhrase == 0){
					if(!ttn[i][j].equals("n")){
						nounPhrase = i+1;
					}
				}
				
				
			}
//			
		}
		//}
		//catch(NullPointerException nullPointer){}
		if(nounPhrase == 0){
			ttn[0][0] = "null1";
		}
			noNP = ind ;
			//System.out.println("noNP="+noNP);
			
			return ttn;
		//}
	}
	public int getnonounphase(){
		return noNP;
	}
	
	public String[][] getnountag(){
		return tagttn;
	}
	public String[][] getverbtag(){
		return tagttv1;
	}
	
	public String[][] getVerbChunks(int nwords, String[] swords, String[] stags, int eko, int eka){
		String[][] ttv = new String[15][10];
		String[][] tagttv = new String[15][10];
		int[] index = new int[nwords];
		int verbPhrase = 0;
		int ind = 0;
		int lag = 0;
		int count = 0;
		int bflag = 0;
		int vbext = 0;
		System.out.println("Hi its verb chunker ");
		try{
		if(count == 0){
			//count = 0;
			ttv = new String[15][nwords];
			for(int q = 0 ; q < 5 ; q++){        // Assuming maximum 5 noun phases
				for(int p = 0 ; p < nwords ; p++){
					ttv[q][p] = "n";
					System.out.println("swords["+p+"]="+swords[p]);
				}
			}
			int lag1 = 0;
			int bigflag = 0;
		//	int vbext = 0;
		//	int bflag = 0;
			//if(flag == 0){
				for(int i = 0 ; i < nwords ; i++){
					System.out.println("initial Welcome verb chunk bigflag= "+bigflag+"  i="+i);
					if(stags[i].equals("VF") || stags[i].equals("VAUX") || stags[i].equals("VMNE")){
						System.out.println("1 Verb initial Welcome verb chunk bigflag= "+bigflag+"  i="+i);
						 vbext= 1;
						 ttv[ind][lag] = swords[i];   // danger
							tagttv[ind][lag] = stags[i];   // danger
						if(!stags[i+1].equals("VAUX") && !stags[i+1].equals("VMKO") && !stags[i+1].equals("PSP") && !stags[i-1].equals("PSP")){
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							System.out.println("	new ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
				//	ttv[ind][lag+1] = swords[i+1];
				//	tagttv[ind][lag+1] = stags[i+1];
				//	//System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
							verbPhrase++;
							ind++;
							bflag = 1;
							if(stags[i+1].equals("VAUX")){
								
								
								
								verbPhrase--;
								ind--;
								bflag = 0;
							}
							//System.out.println("Verb Phrase = "+verbPhrase);
						}
				//}
				      }
					//lag = 0;
					//System.out.println("Welcome verb chunk bigflag= "+bigflag+"  i="+i);
					//System.out.println("1 verb");
					if(bflag == 0 && stags[i].equals("VF") ){
						//System.out.println("2 verb");		
							bflag = 1;			
							//System.out.println("Welcome verb chunk b= "+bflag+"  i="+i);
								if(stags[i+1].equals("VAUX")  ){
									bflag = 5 ;
									//System.out.println("3 verb");								
									if(stags[i+2].equals("PSPKO")){
										//System.out.println("4 verb");									
										if(stags[i+3].equals("VF")){
											//System.out.println("5 verb");		
											//System.out.println("Four chunks swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"  stags["+i+"]="+stags[i]);
											ttv[ind][lag] = swords[i];
											tagttv[ind][lag] = stags[i];
											ttv[ind][lag+1] = swords[i+1];
											tagttv[ind][lag+1] = stags[i+1];
											ttv[ind][lag+2] = swords[i+2];
											tagttv[ind][lag+2] = stags[i+2];
											ttv[ind][lag+3] = swords[i+3];
											tagttv[ind][lag+3] = stags[i+3];
											
											//System.out.println("	ttsle["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count1="+count+"    word index ="+i);
											index[ind] = i;
											ind++;
											verbPhrase++;
											bigflag = 1;
										}
									}
								}
								else{
									
								//	}
								}
								//System.out.println("Verb Phrase = "+verbPhrase);
					}
					//System.out.println("again Welcome verb chunk bigflag= "+bigflag+"  i="+i+"   bflag="+bflag+"  stags["+i+"]="+stags[i]);
					
				//	else
					if(bflag == 5){
							//System.out.println("VF VAUX swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag);
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							ttv[ind][lag+1] = swords[i+1];
							tagttv[ind][lag+1] = stags[i+1];
							//System.out.println("	ttsle["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count1="+count+"    word index ="+i);
							index[ind] = i;
							ind++;
							verbPhrase++;
							bflag = 2;
							//System.out.println("Verb Phrase = "+verbPhrase);

					}
					if(bflag == 1 && bigflag == 0 && (stags[i].equals("VMKO") || stags[i].equals("VF") || stags[i].equals("VINF") || stags[i].equals("VAUX") || stags[i].equals("VMNE") || stags[i].equals("VMO"))){
						System.out.println("Welcome verb chunk bigflag= "+bigflag+"   bflag="+bflag);
						if(stags[i+1].equals("VAUX")  || stags[i+1].equals("VMKO") || stags[i+1].equals("PSPKO")){
							//System.out.println("swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag);
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							ttv[ind][lag+1] = swords[i+1];
							tagttv[ind][lag+1] = stags[i+1];
							System.out.println("	ttsle["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count1="+count+"    word index ="+i);
							index[ind] = i;
							ind++;
							verbPhrase++;
							bflag = 0;
							System.out.println("Verb Phrase = "+verbPhrase);
							//flag = 1;
							//lag++;
							//lag1++;
						
						}
						else if( !stags[i-1].equals("VF") && bflag == 0){
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							System.out.println(" waiting swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"   word index ="+i);
							//System.out.println("welcome else verb chunk");
							//System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
							lag++;
							index[ind] = i;
							ind++;
							verbPhrase++;
							bflag = 0;
							//System.out.println("Verb Phrase = "+verbPhrase);
						}
						
						/*else if(stags[i+1].equals("PSPLAI")  ){
							ttn[ind1][lag1] = swords[i];
							//System.out.println("	ttnlai["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
							index[ind1] = i;
							ind1++;
							//lag1++;
						}*/
					}
					//ind++;
					
				}
		}
		for(int i = 0 ; i < nwords;i++){
			/*if(stags[i].equals("VF") ){
				//if(stags[i+1].equals("PSPKO")){
				//lag = i;
				if(stags[i+1].equals("VMKO")) {
					stags[i] = "RB"; 
				}
			}*/
			/*if(bflag == 1 && i !=0){
				//System.out.println("stags["+i+"]="+stags[i]+"   stags["+(i-1)+"]="+stags[i-1]);
									if(!stags[i-1].equals("VF")){
										bflag = 1;
										ttv[ind][lag] = swords[i];
										tagttv[ind][lag] = stags[i];
										//System.out.println("swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"   word index ="+i);
										//System.out.println("welcome else verb chunk");
										//System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
										lag++;
										index[ind] = i;
										ind++;
										verbPhrase++;
									}
			}*/
			System.out.println("hi verb chunk stags["+i+"]="+stags[i]+"  stags["+(i+1)+"]"+stags[i+1]+"  bflag="+bflag);
			if((bflag == 1 && stags[i].equals("VMKO")) || (bflag == 1 && stags[i].equals("VF"))){
				if(stags[i+1].equals("NN")  || stags[i+1].equals("VAUX") || stags[i+1].equals("VMKO") ){
					ttv[ind][lag] = swords[i];
					tagttv[ind][lag] = stags[i];
					System.out.println("	456 ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
					ttv[ind][lag+1] = swords[i+1];
					tagttv[ind][lag+1] = stags[i+1];
					System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
					verbPhrase++;
					ind++;
					//System.out.println("Verb Phrase = "+verbPhrase);
				}
			}

			System.out.println("hi verb chunk with PSP (post position)stags["+i+"]="+stags[i]+"  stags["+(i+1)+"]"+stags[i+1]+"  bflag="+bflag);
			if( stags[i].equals("VF")){
				if(stags[i+1].equals("PSP")  && stags[i+2].equals("VAUX") ){
					ttv[ind][lag] = swords[i];
					tagttv[ind][lag] = stags[i];
					System.out.println("	789 ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
					ttv[ind][lag+1] = swords[i+1];
					tagttv[ind][lag+1] = stags[i+1];
					System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
					ttv[ind][lag+2] = swords[i+2];
					tagttv[ind][lag+2] = stags[i+2];
					verbPhrase++;
					ind++;
					//System.out.println("Verb Phrase = "+verbPhrase);
				}
			}
			
			if(stags[i].equals("RB") ){
				//if(stags[i+1].equals("PSPKO")){
				lag = i;
				if(stags[i+1].equals("VF") || stags[i+1].equals("VINF") || stags[i+1].equals("VAUX") || stags[i+1].equals("VMNE") || stags[i+1].equals("VMO") || stags[i+1].equals("VMKO")){
						//System.out.println(" FAST chalo	Verb");
						verbPhrase++;
						for(int k = 0 ; k < nwords ; k++){
							if(index[k] == (i+1)){
								//System.out.println("index["+k+"]="+index[k]);
								ttv[k][0] = "n";
								ind = k;
							}
						}
						//System.out.println("	ttv["+ind+"]["+lag+"]="+ttv[ind][lag]+"    stags["+lag+"]="+stags[lag]);
						ttv[ind][lag] = swords[lag];
						tagttv[ind][lag] = stags[lag];
						//System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
						ttv[ind][lag+1] = swords[lag+1];
						tagttv[ind][lag+1] = stags[lag+1];
						//System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
						//ttn[ind][lag+2] = swords[lag+2];								
						count++;
						//	//System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
						//System.out.println();
						ind++;
						//System.out.println("Verb Phrase = "+verbPhrase);
						//verbPhrase++;
						//flag2 = 1;
				}
			}
		}
		}
		catch(NullPointerException nullPointer){}
		if(vbext == 1) verbPhrase = 1;
		if(verbPhrase == 0){
			ttv[0][0] = "null1";
		}
			noVP = verbPhrase ;
			//System.out.println("noVP="+noVP);
			tagttv1 = tagttv;
		try{
		for(int a = 0 ; a < noVP ; a++){
			for(int b = 0 ; b < 10 ; b++){
				System.out.println("  ttv["+a+"]["+b+"]="+ttv[a][b]+"    tagttv["+a+"]["+b+"]="+tagttv[a][b]);
			}
		}
		}
		catch(ArrayIndexOutOfBoundsException ae){
			//System.out.println("Its Verb Chunker");
		}
		return ttv;
	}
	
	public String[][] getNounChunks(){
		//System.out.println("TN[0][0]"+TN[0][0]);
		return TN;
	}
	
	public String[][] getVerbChunks(){
		return TV;
	}
	public int getverbphrase(){
		return noVP;
	}
	

}