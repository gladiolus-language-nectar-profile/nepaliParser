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
	public Chunker(int now, String[] words, String[] tags, boolean k2pexist, boolean k7texist){
		TN = new String[15][3] ; // Maximum noun chunks assumed = 5 and maximum length(words) of each chunk = 3
		TV = new String[15][3] ; // Maximum noun chunks assumed = 5 and maximum length(words) of each chunk = 3	
		
		TN = getNounChunks(now, words, tags, k2pexist, k7texist);
		
		TV = getVerbChunks(now, words, tags);
	}
	public String[][] getNounChunks(int nwords , String[] swords, String[] stags, boolean k2p , boolean k7t){
		int count = 0;
		int flag = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		int flag5 = 0;
		int flag6 = 0;
		int flag7 = 0;
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
		try{
			for(int i = 0 ; i < nwords ; i++){
				if(!stags[i].equals(null)){
				  pt++;
				}
				else{
					break;
				}
			}
		}
		catch(NullPointerException nullPointer){}
		nwords = pt;
		int[] index = new int[nwords];
		//int[] pos = new int[5];
		String[][] ttn = new String[15][nwords];
		tagttn = new String[15][nwords];
		try{
			if(count == 0){
				count = 0;
			//	ttn = new String[5][nwords];
				for(int q = 0 ; q < 5 ; q++){        // Assuming maximum 5 noun phases
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
							System.out.println("	ttnle["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
							index[ind1] = i;
							ind1++;
							System.out.println("chunker nwords="+nwords+"   count="+count);
							//lag1++;
							nounPhrase++;
							flag6 = 1;
							count++;
							if(stags[i+1].equals("PSPKO") && stags[i+2].equals("PSPO")){
								ttn[ind1-1][lag1+2] = swords[i+2];
								tagttn[ind1-1][lag1+2] = stags[i+2];
								System.out.println("	Hi it is NN PSPKO PSPO");
								System.out.println("	ttnle["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
								flag6 =1;o
								//count++;
								
							}
						}
						else{
							System.out.println("chunker nwords="+nwords+"   count="+count+" NO CHUNKS");
							//ttn[ind1][lag1] = swords[i];
							//tagttn[ind1][lag1] = stags[i];
							//ind1++;
						}
						/*else if(stags[i+1].equals("PSPLAI")  ){
							ttn[ind1][lag1] = swords[i];
							System.out.println("	ttnlai["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
							index[ind1] = i;
							ind1++;
							//lag1++;
						}*/
				//	}
					
			//	}
				System.out.println("chunker nwords="+nwords+"   count="+count);
			}
		
		count = 0;
		//lag11 = 0;
		System.out.println("chunker nwords="+nwords+"   out count="+count);
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
			System.out.println("chunker nwords="+nwords+"  index["+ind+"]="+index[ind]);
			//if(flag == 0){
				int scope = 0;
				for(int i = 0 ; i < nwords  ; i=i+scope+1){
					scope = 0;
					System.out.println("i="+i+"   scope="+scope);
					/*if(stop == 0 && index[ind] != i ){
						System.out.println(" danger i="+i+"   flag7="+flag7+"   count="+count);
						if(flag7 != 1){
							System.out.println("if danger i="+i);
							flag6 = 0;
						}
						else if(i <= count){
								System.out.println("else if danger i="+i);
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
					System.out.println("lag ="+lag+"   ind="+ind1+"   flag ="+flag+"     flag1 = "+flag1+"   flag2 ="+flag2+"  flag3="+flag3+" new word index ="+i+"  stags["+i+"]="+stags[i]);
					if(stags[i].equals("VMKO")){
						if(stags[i+1].equals("NN")){
							noentry = i+1;
						}
					
					}
					System.out.println("noentry ="+noentry);
					if(noentry == 0 && stags[i].equals("QW")){
						System.out.println("Welcome QW world");
						ttn[ind][lag] = swords[lag];
						tagttn[ind][lag] = stags[lag];
						System.out.println("tagttn["+ind+"]["+lag+"]="+tagttn[ind][lag]);
						ind++;
						lag++;
						onlyqwflag = 1;
						scope = lag;
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
				//	System.out.println("stags["+i+"]="+stags[i]+"     stags["+(i+1)+"]="+stags[i+1]);
					if( !k2p || !k7t) {
						scope = 100;

					}
					int mytrial = 0;
						if(scope == 0 && noentry == -1 && (stags[i].equals("PRF") || stags[i].equals("PRP") || stags[i].equals("PRL") || stags[i].equals("NN"))){
							//if(stags[i+1].equals("PSPKO")){
							System.out.println("my trial");
								mytrial = 1;
								if(stags[i+1].equals("NN") || stags[i+1].equals("NNP") || stags[i+1].equals("PSPKO") || stags[i+1].equals("QTO") ){
									mytrial = 0;
									if(stags[i+2].equals("JJ") || stags[i+2].equals("PSPO") || /*stags[i+2].equals("NN") || */ stags[i+2].equals("PSPLAI") || stags[i+2].equals("PSPLE")){//&& mustjj == 1){
										System.out.println(" PRP NN JJ chalo	");
										nounPhrase++;
										mytrial = 0;
										System.out.println("nwords="+nwords+"   index["+0+"]="+index[0]+"  i="+i);
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+2)){
											System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}*/
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];
									System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count);
									//ttn[ind][lag+2] = swords[lag+2];								
									count=count+1;
								//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									System.out.println();
									ind++;
									flag = 1;
									scope = 2;
									System.out.println("scope="+scope);
									//ind++;
								}
								//}
								else if( scope == 0 && flag1 != 1 && flag != 1 && !stags[i+2].equals("PSPKO") && !stags[i+2].equals("PSPO") && !stags[i+2].equals("JJ") && mustjj == 0){
										System.out.println(" chalo	");
										nounPhrase++;
										mytrial = 0;
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[lag+1];
										tagttn[ind][lag+1] = stags[lag + 1];
										System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
										//ttn[ind][lag+2] = swords[lag+2];								
										count++;
									//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
										System.out.println();
										ind++;
										flag = 1;
										scope = lag+1;
										
									System.out.println("scope="+scope);
										
								}
								}

								System.out.println("my trial = "+mytrial);	
								//}
							}
							else if(mytrial == 1 || (scope == 0 && stags[i].equals("QW"))   || stags[i].equals("NNP") || (flag1 == 2 && flag2 == 2 && flag3 == 2)){
								System.out.println("my trial one");
								if(!stags[i+1].equals("PSPLE") && !stags[i+1].equals("PSPLAI") && !stags[i+1].equals("JJ") && !stags[i+1].equals("PSPKO") && !stags[i+1].equals("QF") && !stags[i+1].equals("QTC") && !stags[i+1].equals("JJ") ){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	only NN  ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									lag++;
									onlynnflag = 1;
									scope = lag;
									System.out.println("scope="+scope);
								}
								else if(stags[lag+1].equals("JJ")){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag]+1;
									System.out.println("	only NN JJ ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									ind++;
									//lag++;
									onlynnflag = 0;
									scope = lag + 1;
									System.out.println("scope="+scope);
								}
								if(scope == 0 && noentry == 0 && onlyqwflag == 0 && flag3 == 0 && jjnnflag == 0 && (stags[lag+1].equals("VF") || stags[lag+1].equals("VAUX"))){
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	new ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag]);
									count++;
									nounPhrase++;
									flag = 1;
									scope = lag;
									System.out.println("scope="+scope);
								}
						        if(flag == 1 && jjnnflag == 0){
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
									
									System.out.println("	1ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i+" stags["+(lag+1)+"]="+stags[lag+1]);
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
						if(scope == 0 && flag != 1 && noentry == -1 &&  (stags[i].equals("PRP") || stags[i].equals("QW") || stags[i].equals("PRL") || stags[i].equals("PRF") || stags[i].equals("NN") || stags[i].equals("NNP") || stags[i].equals("DMI"))){
							if(stags[i+1].equals("PSPLE") || stags[i+1].equals("PSPLAI") ||  stags[i+1].equals("PSPO") || stags[i+1].equals("JJ") || stags[i+1].equals("RP")){
								if(!stags[i+2].equals("NN") && !stags[i+2].equals("NNP")){
								//if(stags[i+2].equals("NN") || stags[i+2].equals("NNP")){
									System.out.println(" Lai or Le chalo hi	");
									nounPhrase++;
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+2)){
											System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}*/
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									//ttn[ind][lag+2] = swords[lag+2];
									//tagttn[ind][lag+2] = stags[lag+2];
									System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"  count="+count+"  i="+i);
									//ttn[ind][lag+2] = swords[lag+2];								
									count=count+1;
								//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									System.out.println();
									ind++;
									scope = lag+1;
									System.out.println("scope="+scope);
									flag1 = 1;
									//ind++;
									/*if(i < (i+lag+1)){
										flag1 = 1;
										System.out.print("causion (i+lag+1)="+(i+lag+1));
									}
									else
										flag1 = 0;*/
									}
									
									else {
										System.out.println( "DMI RP NN");
										ttn[ind][lag] = swords[lag];
										tagttn[ind][lag] = stags[lag];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[lag+1];
										tagttn[ind][lag+1] = stags[lag+1];
										if(!stags[i].equals("PRP") && !stags[i].equals("PRL") && !stags[i].equals("NN") && !stags[i].equals("NNP") ){
											ttn[ind][lag+2] = swords[lag+2];
											tagttn[ind][lag+2] = stags[lag+2];
											scope = lag+2;
										}
										count = count + 1;
										ind=ind+1;;
										flag1 = 1;
										scope = lag+1;
									System.out.println("scope="+scope);
									
									}
									
								//}
							}
							else if(scope == 0 && flag1 != 1 && flag == 0 && onlyqwflag == 0 && onlynnflag == 0){
									System.out.println("flag="+flag);
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									ind++;
									count++;
									nounPhrase++;
									onlynnflag = 1;
									//if(flag != 1 && flag1 != 1){
										flag5 = 1;
										scope = lag;
									System.out.println("scope="+scope);
										System.out.print("****single	ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"  count="+count+"  tagttn="+tagttn[ind][lag]);
								//	}
								}
						}
						else if((mytrial == 1 || scope == 0 && (stags[i].equals("NN") || stags[i].equals("NNP") || (flag1 ==2 && flag2 == 2 && flag3 == 2))) ){
							System.out.println("third stags["+(i+1)+"]="+stags[i+1]);
							if(mytrial == 1){
									System.out.println("Only NN");
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									nounPhrase++;
									ind++;
									count++;
									scope = 0;
								}
						        if(flag1 == 1 && onlynnflag != 1 && onlyqwflag == 0){
						        	flag1 = 2;
						        	if(!stags[lag+1].equals("VF")  && !stags[lag+1].equals("VAUX") && !stags[lag+1].equals("VMKO") && !stags[lag+1].equals("VMNE")){
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									scope = lag+1;
									System.out.println("scope="+scope);
								}
								System.out.println("	***ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								ind++;
								ct = 1;
							}else if(flag6 == 0 && flag == 0 && flag2 == 0 && flag3 == 0 && flag1 != 2){
								flag1 = 0;
								try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									scope = lag;
									System.out.println("scope="+scope);
									System.out.println("	2ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i+"  swords["+lag+"]="+swords[lag]);
								}
								catch(ArrayIndexOutOfBoundsException exception) {
 									   
								}
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
						if( stags[i].equals("QC") || stags[i].equals("QF") || stags[i].equals("QTC") ){
							//if(stags[i+1].equals("PSPKO")){
								if(stags[i+1].equals("NN") || stags[i+1].equals("NNP") ){
									System.out.println(" QC FAST chalo	");
									nounPhrase++;
									for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+1)){
											System.out.print("index["+k+"]="+index[k]);
											ttn[k][0] = "n";
											ind = k;
										}
									}
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
									//ttn[ind][lag+2] = swords[lag+2];								
									count++;
								//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									System.out.println();
									ind++;
									flag3 = 1;
									scope = lag+1;
									System.out.println("scope="+scope);
									
								}
								if(flag3 == 0 && (stags[i-1].equals("NN") || stags[i-1].equals("NNP") )){
									System.out.println(" QW FAST chalo back	");
									nounPhrase++;
									/*for(int k = 0 ; k < nwords ; k++){
										if(index[k] == (i+1)){
											System.out.print("index["+k+"]="+index[k]);
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
								//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									System.out.println();
									ind++;
									flag3 = 1;
									scope = lag+1;
									System.out.println("scope="+scope);
								
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
									scope = lag;
									System.out.println("scope="+scope);
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
						
						
						// CHECKING NOUN FOLLOWED BY Adjective
						if(noentry == -1 && stags[i].equals("JJ") ){
							//if(stags[i+1].equals("PSPKO")){
								if(stags[i+1].equals("NN") || stags[i+1].equals("NNP")){
									if(stags[i+2].equals("PSPLE") || stags[i+2].equals("PSPLAI") || stags[i+2].equals("PSPO")){
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
										scope = lag+2;
									System.out.println("scope="+scope);
										//index[ind]
									}
									else {
										
										System.out.println(" FAST chalo	");
										System.out.println("ind ="+ind+" lag="+lag+"   stags["+lag+"]="+stags[lag]);
										nounPhrase++;
										for(int k = 0 ; k < nwords ; k++){
											if(index[k] == (i+1)){
												System.out.print("index["+k+"]="+index[k]);
												ttn[k][0] = "n";
												ind = k;
											}
										}
										ttn[ind][lag] = swords[i];
										tagttn[ind][lag] = stags[i];
										System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
										ttn[ind][lag+1] = swords[i+1];
										tagttn[ind][lag+1] = stags[i+1];
										System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"  count="+count);
										//ttn[ind][lag+2] = swords[lag+2];								
										count++;
									//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
										System.out.println();
										ind++;
										flag2 = 1;
										jjnnflag =1;
										scope = lag+1;
									System.out.println("scope="+scope);
									}
								}
								else if(flag == 0 && flag1 == 0){
									ttn[ind][lag] = swords[i];
									tagttn[ind][lag] = stags[i];
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"  count="+count);
									ind++;
									scope = lag;
									System.out.println("scope="+scope);
								}
							}
							else if(stags[i].equals("NN") || stags[i].equals("NNP") || (flag == 2 && flag1 == 2)){
						        	if( flag2 == 1){
						        		flag2 = 2;
						        		System.out.println("Hi its else JJ NN ");
								//ttn[ind][lag] = swords[lag];
							//	System.out.println("	*ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
								//ind++;
							}else if(onlynnflag == 0 && flag6 == 0 && flag1 == 0 && flag == 0 && flag3 == 0 && flag2 != 2){
								flag = 3;
								flag = 0;
								try{
									ttn[ind][lag] = swords[lag];
									tagttn[ind][lag] = stags[lag];
									System.out.println("	3ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count + "  i ="+i);
									scope = lag;
									System.out.println("scope="+scope);
								}
								catch(ArrayIndexOutOfBoundsException exception) {
 									   
								}
								
								ind++;
							}
							
							
						//count = 0;
						   //   }
						 //     flag = 0;
						//break;
						
						}
				//} 
			
				}
			//}
		}
		
			/*if(stags[i].equals("PP$") ){
				System.out.println("hi");
				ttn[count][i] = swords[i];
				System.out.println("///FLAG ="+flag+" ttn["+count+"]["+i+"]="+ttn[count][i]);
				count++; 
			}
			
			
			else if(count%2 !=  0 && count > 0){
				if(stags[i].equals("NN") || stags[i].equals("NNP")){
					//pos[i] = 0;
					ttn[count-1][i] = swords[i];
					flag = i;
					System.out.println("***FLAG ="+flag+" ttn["+(count-1)+"]["+i+"]="+ttn[count-1][i]);
					count++;
				}
			}
			
			else if(count == 0){
				ttn[0][0] = "null1";
			}
			if(flag == 0){
				
				ttn[count][i] = swords[i];
				System.out.println("FLAG ="+flag+" ttn["+count+"]["+i+"]="+ttn[count][i]);
			}
			
			
			
		}
		System.out.println("count ="+count);
		for(int i = 0 ; i < nwords;i++){
			for(int j = 0 ; j < 3 ; j++){
				if(stags[i].equals("NN") || stags[i].equals("NNP")){
						if(i > 1 && count != 0){
							ttn[i-(count-1)][j] = swords[i];
							System.out.print("	1ttn["+(i-(count-1))+"]["+j+"]= "+ttn[i-(count-1)][j]);
						}
										
				}
			}
			System.out.println();
		}*/
		
		System.out.println("1count="+count+" ind="+ind);
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
							System.out.println(" double	else 4ttn["+i+"]["+(lag5)+"]= "+ttn[i][lag5] +"count="+count);
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
					System.out.println(" NN PSPKO PSPO lag ="+lag);
					//for(int j = 0 ; j < 3 ; j++){
						/*if(stags[0].equals("NN") || stags[0].equals("NNP")){
							ind++;
						}*/
						if(noentry == -1 && (stags[i].equals("JJ")|| stags[i].equals("NN"))){
							if(stags[i+1].equals("PSPKO")){
								if(stags[i+2].equals("NN") || stags[i+2].equals("NNP") || stags[i+2].equals("PSPO")){
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
									System.out.print("	ttn["+ind+"]["+lag+"]= "+ttn[ind][lag] +"count="+count);
									
									ttn[ind][lag+1] = swords[lag+1];
									tagttn[ind][lag+1] = stags[lag+1];
									System.out.print("	ttn["+ind+"]["+(lag+1)+"]= "+ttn[ind][lag+1] +"count="+count);
									ttn[ind][lag+2] = swords[lag+2];
									tagttn[ind][lag+2] = stags[lag+2];								
									count++;
									System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
									System.out.println();
									ind++;
									
								}
							}
						}
						else{
							if(stags[ind].equals("NN") || stags[ind].equals("NNP")){
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
									System.out.println(" double	else 4ttn["+ind+"]["+(lag)+"]= "+ttn[ind][lag] +"count="+count);
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
			System.out.println("new i = "+i);
			if(stags[i].equals("JJ")){
				if(stags[i+1].equals("PKO")){
					if(stags[i+2].equals("NN") || stags[i+2].equals("NNP")){
						ttn[count][i] = swords[i];
						System.out.print("	PKO ttn["+count+"]["+i+"]= "+ttn[count][i]);						
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
				if(noentry == -1 && (stags[l].equals("NN") || stags[l].equals("NNP") || stags[l].equals("PRP") || stags[l].equals("PRL"))){
//					temp = ttn[ind][lag]
					System.out.println("hki");
					ttn[tind ][l] = swords[l];
					tagttn[tind][l] = stags[l];
					tind++;
					nounPhrase++;
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
		String[] tag = {"NN","NNP","NST","PRP","PRL","JJ"};
		String[] tag1 = {"PRP","PRL"};
		int temp = 0;
		System.out.println("come again");
		for(int i = 0 ; i < 6 ; i++){
			System.out.println(" Final");
			for(int j = 0 ; j < nwords ; j++){
				System.out.println("	final ttn["+i+"]["+j+"]= "+ttn[i][j]+"     tagttn["+i+"]["+j+"]="+tagttn[i][j]);
				
				
			}
//			
		}
		}
		catch(NullPointerException nullPointer){}
		if(nounPhrase == 0){
			ttn[0][0] = "null1";
		}
			noNP = ind ;
			System.out.println("noNP="+noNP);
			
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
	
	public String[][] getVerbChunks(int nwords, String[] swords, String[] stags){
		String[][] ttv = new String[15][10];
		String[][] tagttv = new String[15][10];
		int[] index = new int[nwords];
		int verbPhrase = 0;
		int ind = 0;
		int lag = 0;
		int count = 0;
		int bflag = 0;
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
		//	int bflag = 0;
			//if(flag == 0){
				for(int i = 0 ; i < nwords ; i++){
					System.out.println("initial Welcome verb chunk bigflag= "+bigflag+"  i="+i);
					if(stags[i].equals("VF") ){
						System.out.println("1 Verb initial Welcome verb chunk bigflag= "+bigflag+"  i="+i);
						//if(!stags[i+1].equals("VAUX")){
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
				//	ttv[ind][lag+1] = swords[i+1];
				//	tagttv[ind][lag+1] = stags[i+1];
				//	System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
							verbPhrase++;
							ind++;
							bflag = 1;
							if(stags[i+1].equals("VAUX")){
								verbPhrase--;
								ind--;
								bflag = 0;
							}
							System.out.println("Verb Phrase = "+verbPhrase);
						//}
				//}
				      }
					//lag = 0;
					System.out.println("Welcome verb chunk bigflag= "+bigflag+"  i="+i);
					System.out.println("1 verb");
					if(bflag == 0 && stags[i].equals("VF") ){
						System.out.println("2 verb");		
							bflag = 1;			
							System.out.println("Welcome verb chunk b= "+bflag+"  i="+i);
								if(stags[i+1].equals("VAUX")  ){
									bflag = 5 ;
									System.out.println("3 verb");								
									if(stags[i+2].equals("PSPKO")){
										System.out.println("4 verb");									
										if(stags[i+3].equals("VF")){
											System.out.println("5 verb");		
											System.out.println("Four chunks swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"  stags["+i+"]="+stags[i]);
											ttv[ind][lag] = swords[i];
											tagttv[ind][lag] = stags[i];
											ttv[ind][lag+1] = swords[i+1];
											tagttv[ind][lag+1] = stags[i+1];
											ttv[ind][lag+2] = swords[i+2];
											tagttv[ind][lag+2] = stags[i+2];
											ttv[ind][lag+3] = swords[i+3];
											tagttv[ind][lag+3] = stags[i+3];
											
											System.out.println("	ttsle["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count1="+count+"    word index ="+i);
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
								System.out.println("Verb Phrase = "+verbPhrase);
					}
					System.out.println("again Welcome verb chunk bigflag= "+bigflag+"  i="+i+"   bflag="+bflag+"  stags["+i+"]="+stags[i]);
					
				//	else
					if(bflag == 5){
							System.out.println("VF VAUX swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag);
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							ttv[ind][lag+1] = swords[i+1];
							tagttv[ind][lag+1] = stags[i+1];
							System.out.println("	ttsle["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count1="+count+"    word index ="+i);
							index[ind] = i;
							ind++;
							verbPhrase++;
							bflag = 2;
							System.out.println("Verb Phrase = "+verbPhrase);

					}
					if(bflag == 1 && bigflag == 0 && (stags[i].equals("VMKO") || stags[i].equals("VF") || stags[i].equals("VINF") || stags[i].equals("VAUX") || stags[i].equals("VMNE") || stags[i].equals("VMO"))){
						System.out.println("Welcome verb chunk bigflag= "+bigflag+"   bflag="+bflag);
						if(stags[i+1].equals("VAUX")  || stags[i+1].equals("VMKO") || stags[i+1].equals("PSPKO")){
							System.out.println("swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag);
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
						else if( !stags[i-1].equals("VF")){
							ttv[ind][lag] = swords[i];
							tagttv[ind][lag] = stags[i];
							System.out.println(" waiting swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"   word index ="+i);
							System.out.println("welcome else verb chunk");
							System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
							lag++;
							index[ind] = i;
							ind++;
							verbPhrase++;
							bflag = 0;
							System.out.println("Verb Phrase = "+verbPhrase);
						}
						
						/*else if(stags[i+1].equals("PSPLAI")  ){
							ttn[ind1][lag1] = swords[i];
							System.out.println("	ttnlai["+ind1+"]["+lag1+"]= "+ttn[ind1][lag1] +"  count1="+count+"    word index ="+i);
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
				System.out.println("stags["+i+"]="+stags[i]+"   stags["+(i-1)+"]="+stags[i-1]);
									if(!stags[i-1].equals("VF")){
										bflag = 1;
										ttv[ind][lag] = swords[i];
										tagttv[ind][lag] = stags[i];
										System.out.println("swords["+i+"]="+swords[i]+"   ind="+ind+"   lag="+lag+"   word index ="+i);
										System.out.println("welcome else verb chunk");
										System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
										lag++;
										index[ind] = i;
										ind++;
										verbPhrase++;
									}
			}*/
			if(stags[i].equals("VMKO")){
				if(stags[i+1].equals("NN")){
					ttv[ind][lag] = swords[i];
					tagttv[ind][lag] = stags[i];
					System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
					ttv[ind][lag+1] = swords[i+1];
					tagttv[ind][lag+1] = stags[i+1];
					System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
					verbPhrase++;
					ind++;
					System.out.println("Verb Phrase = "+verbPhrase);
				}
			}
			
			if(stags[i].equals("RB") ){
				//if(stags[i+1].equals("PSPKO")){
				lag = i;
				if(stags[i+1].equals("VF") || stags[i+1].equals("VINF") || stags[i+1].equals("VAUX") || stags[i+1].equals("VMNE") || stags[i+1].equals("VMO") || stags[i+1].equals("VMKO")){
						System.out.println(" FAST chalo	Verb");
						verbPhrase++;
						for(int k = 0 ; k < nwords ; k++){
							if(index[k] == (i+1)){
								System.out.println("index["+k+"]="+index[k]);
								ttv[k][0] = "n";
								ind = k;
							}
						}
						System.out.println("	ttv["+ind+"]["+lag+"]="+ttv[ind][lag]+"    stags["+lag+"]="+stags[lag]);
						ttv[ind][lag] = swords[lag];
						tagttv[ind][lag] = stags[lag];
						System.out.println("	ttv["+ind+"]["+lag+"]= "+ttv[ind][lag] +"  count="+count+"   tagttv["+ind+"]["+lag+"]="+tagttv[ind][lag]);
						ttv[ind][lag+1] = swords[lag+1];
						tagttv[ind][lag+1] = stags[lag+1];
						System.out.println("	ttv["+ind+"]["+(lag+1)+"]= "+ttv[ind][lag+1] +" verb chunk count="+count+"  tagttv["+ind+"]["+(lag+1)+"]="+tagttv[ind][lag+1]);
						//ttn[ind][lag+2] = swords[lag+2];								
						count++;
						//	System.out.print("	ttn["+ind+"]["+(lag+2)+"]= "+ttn[ind][lag+2] +"count="+count);
						System.out.println();
						ind++;
						System.out.println("Verb Phrase = "+verbPhrase);
						//verbPhrase++;
						//flag2 = 1;
				}
			}
		}
		}
		catch(NullPointerException nullPointer){}
		if(verbPhrase == 0){
			ttv[0][0] = "null1";
		}
			noVP = verbPhrase ;
			System.out.println("noVP="+noVP);
			tagttv1 = tagttv;
		try{
		for(int a = 0 ; a < noVP ; a++){
			for(int b = 0 ; b < 10 ; b++){
				System.out.println("  ttv["+a+"]["+b+"]="+ttv[a][b]+"    tagttv["+a+"]["+b+"]="+tagttv[a][b]);
			}
		}
		}
		catch(ArrayIndexOutOfBoundsException ae){
			System.out.println("Its Verb Chunker");
		}
		return ttv;
	}
	
	public String[][] getNounChunks(){
		System.out.println("TN[0][0]"+TN[0][0]);
		return TN;
	}
	
	public String[][] getVerbChunks(){
		return TV;
	}
	public int getverbphrase(){
		return noVP;
	}
	

}