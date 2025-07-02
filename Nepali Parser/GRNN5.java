//PARSER FOR NEPALI LANGUAGE IS BUILT UNDER THE DST FUNDED PROJECT SR/CSRI/28/2015
// DEVELOPED BY PROF. (DR) ARCHIT YAJNIK, PRINCIPLE INVESTIGATOR
// DATE OF SUBMISSION : 05-11-2020
// TECHNIQUES : 1. AUGMENTING PATH
// 2. MAXIMUM BIPARTITE GRAPH
// 3. GENERAL REGRESSION NEURAL NETWORKS
// 4. RULE BASED INTRA-CHUNK DEPENDENCY  PARSER
// 5. INPUT: INPUT.TXT (NEPALI SENTENCE)
// 6. OUTPUT: LOGO.PNG (PARSED TREE)
// 7. BATCH FILE: FASTRUN2.BAT
// PART OF SPEECH TAGGER BASED ON GRNN. IT TAKES NEPALI SENTENCE AND PRODUCES THE ANNOTATED TAGGED SENTENCE.
// THE TAGGED SENTENCE IS STORED IN ANNOUTPUT.TXT
import java.io.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String.*;
import java.util.StringTokenizer;
public class GRNN5{
	java.io.PrintStream p = System.out;
	protected int inNeuron ;
	protected int hidNeuron ;
	protected int outNeuron ;
	private int patCount ;
    	private int testCount;
    	private int finalalaram;
	private int nonewWords;
	private int nonewTags;
    	private String[] tgset;
    	private String[] words;
	private double[][] computedOutput;
	private double[][] totalpats;
	private double[][] totaloutpats;
	private double[][] patterns;
	private double[][] Testpatterns;
	private static double[][] trmatrix;
	private double[][] inputPatterns;
	private double[][] outputPatterns;
	private double[][] Defaulters;
	private String[][] Defaultersstring;
	private double[][] testoutputPatterns;
	public static String[] newWords = new String[500000];
	public static String[] newtrainTags;
	public int[]  unknownPositions;
	private String[] testSentence;
	public BufferedWriter annTagedFile;
	private int[] cenIndex;
	double[][] gaussian;
	double[][] Testgaussian;
	double[] denominator;
	double[][] Feat;
	String[] tagset = {"NN","NNP","NST","PRP","PRL","PRF","JJ","VF","VINF","VAUX","VMNE","VMKO","VMO","DM","DMR","DMD","DMI","RB","PSPLE","PSPLAI","PSPKO","PSPO","CCD","CCS","HRU","QW","CL","INJ","INTF","QTC","QTO","RDF","RP","DT","UNK","PUNC","AB","ALPH","SYM"};
	int[][] Output;
	int[] tags;
	String[] obtTags;
	public static int[] rareFreqposition;
	public static int[] rareFreqTrainposition;
	
	double spread = 0.001;

	public GRNN5(){
		   	AutoCorrector ac = new AutoCorrector();

	}
	public void assignOutput(int patCount,int outNeuron){
			 computedOutput = new double[patCount][outNeuron];
	}
	public void assigntestOutput(int patCount,int outNeuron){
				 computedOutput = new double[testCount][outNeuron];
	}
	
	

	public void getGaussianMatrix(){
		////p.println("inNeuron = " +inNeuron+"   patterns[0][0]="+patterns[0][0]+"patCount="+patCount);
		 gaussian = new double[patCount][patCount];
		for(int pat=0;pat<patCount;pat++){
			for(int n=0;n<patCount;n++){
				gaussian[pat][n]=getGaussianOutput(pat,n);
			}
		}
	}

	public void getTestGaussianMatrix(int[] rarepos, int[] rareTrain){
			int count = 0;
			int flag = 0;
			//int[] rarpos = new int[];
			/*p.println("rarepos.len="+rarepos.length);
			for(int i = 0 ; i < rarepos.length ; i++){
				if(rarepos[i]!=0)
					p.println("rarepos["+i+"]="+ rarepos[i]+"    raretrin["+i+"]="+rareTrain[i]   +"   count = "+count);
					count++;
					break;
					//rarpos[count] = rarepos[i]
			}*/
			 Testgaussian = new double[testCount][patCount];
			for(int pat=0;pat<testCount;pat++){
				//p.println("pat="+pat+"   test count = "+testCount  +"patcount ="+patCount +"   raretrain/2 ="+rareTrain[pat]/2 +"   count="+count);
				//int i = 0;
				//flag = 0;
				for(int n=0;n<patCount;n++){
						Testgaussian[pat][n]=getTestGaussianOutput(pat,n, 1);
				
					/*for(int i = 0 ; i < count ; i++){
						if(flag == 0 && rarepos[i] != 0){
							if(pat == rarepos[i]){
								p.println("*pat ="+pat);
								flag = 1;
								break;
							}
						}
						if(flag ==1)
						     break;
							
					}
					if(flag != 0)
						Testgaussian[pat][n]=getTestGaussianOutput(pat,n, 1);
					else
						Testgaussian[pat][n]=getTestGaussianOutput(pat,n, 1);*/
				
				
					//for(int i = 0 ; i < count ;i++){
					
				}
			}
	}
	public double getGaussianOutput(int npat,int cen){
			double out;
			double edist = 0;
			
			////p.println("inNeuron = " +inNeuron+"   patterns[0][0]="+patterns[npat][0]);
			//getInputpatterns();
			for(int d=inNeuron ; d < 2*inNeuron ; d++){
				////p.println("patterns["+npat+"]["+d+"]="+patterns[npat][d]);
				edist += Math.pow((patterns[npat][d] - patterns[cen][d])/spread,2.0);
			}
			edist = Math.sqrt(edist);
			out = transfer(edist);
			return out;
	}

	public double getTestGaussianOutput(int npat,int cen, int rareindex){
				//Testpatterns = patterns;
				double out;
				double edist = 0;
				if(rareindex == 0){
					//p.println("inNeurons="+inNeuron + "         npat="+npat);

					/*for(int d=0 ; d < inNeuron ; d++){
						edist += Math.pow((Testpatterns[npat][d] - totalpats[cen][d])/spread,2.0);
					//p.println("Testpatterns["+npat+"]["+d+"]="+Testpatterns[npat][d]+"        totalpatterns["+cen+"]["+(d)+"]="+totalpats[cen][d]);
					}*/
				}
				else{
					for(int d=inNeuron ; d < 2*inNeuron ; d++){
						edist += Math.pow((Testpatterns[npat][d] - patterns[cen][d-inNeuron])/spread,2.0);
					////p.println("Testpatterns["+npat+"]["+d+"]="+Testpatterns[npat][d]+"        patterns["+cen+"]["+(d-inNeuron)+"]="+patterns[cen][d-inNeuron]);
					}
				}
				edist = Math.sqrt(edist);
				////p.println("dist["+npat+"]="+edist);
				out = transfer(edist);
				return out;
	}

	public double transfer(double x){
			double gauss = Math.exp(-Math.pow(x,2.0));
			return gauss;
		}


	public double[] Denominator(int npat){
		denominator = new double[patCount];
		for(int i=0;i<patCount;i++){
			denominator[i] = gaussian[npat][i];
		}
		return denominator;
	}
	
	public double[] Denominator1(int npat, double[][] gas){
		denominator = new double[patCount];
		for(int i=0;i<patCount;i++){
			denominator[i] = gas[npat][i];
		}
		return denominator;
	}

	public double[] TestDenominator(int npat){
			denominator = new double[patCount];
			for(int i=0;i<patCount;i++){
				denominator[i] = Testgaussian[npat][i];
			}
			return denominator;
	}

	public void Output(int neuron){
		double[] denominator = new double[patCount];
		computedOutput = new double[patCount][outNeuron];
		for(int i=0;i<patCount;i++){
			double sum1=0,sum=0;
			denominator = Denominator(i);
			for(int j=0;j<patCount;j++){
				sum1 += outputPatterns[j][neuron] * denominator[j];
				sum += denominator[j];
			}
			////p.println("sum1="+sum1+"sum="+sum+"patCount="+patCount+"neuron="+neuron);
			computedOutput[i][neuron] = sum1/sum;
			

		}
	}
	
	public void Output1(int neuron, double[][] gs){
		double[] denominator = new double[patCount];
		computedOutput = new double[patCount][outNeuron];
		for(int i=0;i<patCount;i++){
			double sum1=0,sum=0;
			denominator = Denominator1(i, gs);
			for(int j=0;j<patCount;j++){
				sum1 += outputPatterns[j][neuron] * denominator[j];
				sum += denominator[j];
			}
			////p.println("training sum1="+sum1+"sum="+sum+"patCount="+patCount+"neuron="+neuron);
			computedOutput[i][neuron] = sum1/sum;
			

		}
	}

	public void TestOutput(int neuron){
			double[] denominator = new double[patCount];
			for(int i=0;i<testCount;i++){
				double sum1=0,sum=0;
				denominator = TestDenominator(i);
				for(int j=0;j<patCount;j++){
					sum1 += outputPatterns[j][neuron] * denominator[j];
					sum += denominator[j];
				}

				computedOutput[i][neuron] = sum1/sum;
				////p.println("sum1="+sum1+"   sum="+sum+"  testCount="+testCount+"  neuron="+neuron+"  computedoutput["+i+"]["+neuron+"]="+computedOutput[i][neuron]);				

			}
	}

	public void display(String file) throws IOException {
		OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        	BufferedWriter annTagedFile = new BufferedWriter(writer1);
                int a[]=new int[outNeuron];
		//p.print(testCount);
		tags = new int[testCount];	
		obtTags = new String[testCount];
		Student [] std = new Student[outNeuron];
        	Student [] newstd = new Student[outNeuron];
        	Main m = new Main();
             //   int a[]=new int[outNeuron];
		//p.print(testCount);
		tags = new int[testCount];	
		//obtTags = new String[testCount];
		//p.println();
		//double max=0;
		//int position =0;
		double[][] temp = new double[outNeuron][2];
		////System.out.println("Test count="+testCount);
		for(int i = 0 ; i < testCount ; i++){
			for(int j = 0 ; j < outNeuron ; j++){
				temp[j][0] = 100*computedOutput[i][j];
				temp[j][1] = j;
				std[j] = new Student(j, Math.round((float)temp[j][0]));
			}
			//p.println("Test Pattern = "+ i);
			newstd = m.getsorted(std);
			
		}
		//p.println();
		double max=0;
		int position =0;
		////System.out.println("Test count="+testCount);
		for(int pat=0;pat<testCount;pat++){
			//p.println();
			//p.println("outputneurons = "+outNeuron);
			for(int neu=0;neu<outNeuron;neu++){
				//obtOutput = printTwoMaxNumbers(computedOutput);
				 max = computedOutput[pat][neu];
			        for(int i = 0; i < outNeuron; i++)
			        {
			            if(max < computedOutput[pat][i])
			            {
			                max = computedOutput[pat][i];
			                position = i;
			            }

			        }
				//////System.out.println("Maximum value:"+max+"  position="+position);
				//if(computedOutput[pat][neu] > 0.50){
                            	//p.println("     computed output["+pat+"]["+neu+"]= "+computedOutput[pat][neu] );
                                    tags[pat] = position ;
                                 // p.println("tag["+pat+"]="+tags[pat]+"  Obtained Tag["+pat+"]="+tgset[position]);
                                    obtTags[pat] = tgset[position];
                                  //  if(neu>6)
                                 //       p.print(" "+1);
                                //    else
                                   //     p.print(1);
					////p.println("computed output["+pat+"]["+neu+"]="+1);
                                 //  a[neu]=1
				//}
                                //else{
//					//p.println("computed output["+pat+"]["+neu+"]="+0);
                            //	  p.println("       computed output["+pat+"]["+neu+"]= "+computedOutput[pat][neu] );
                                     
                                 /*  if(neu>6)
                                      p.print(" "+0);
                                   else
                                       p.print(0);*/
                                        
                                       // a[neu]=0;
                                //}
                                
                                
				//long a;
                                //a=computedOutput[pat][neu];
                                //BtoD(a);
			
                           //     for(int i=0;i<outNeuron;i++){
                             //       String b = a[i];
                                    
                               // }
                        }
		}
		//p.println();
		for(int i = 0 ; i < testCount ; i++){
			
		//	p.print(words[i]+"  "+obtTags[i]);
			//annTagedFile.write("\n INPUT DATABASE: ");
			//annTagedFile.write("\n");
			annTagedFile.write("  "+words[i]+"  "+obtTags[i]);
		}
		annTagedFile.close();
	}
	
	
	public void finaldisplay(String file, TagGRNN t3, int[] rarepos, int[] raretrainpos) throws IOException {
		OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        	BufferedWriter annTagedFile = new BufferedWriter(writer1);
                //p.println();
        		int unknwn = 0;
                String[] spvb = t3.getspecialverbs();
                String[] freqposition = t3.getfreqpos();
                double m = 0, m1 = 0;
               // Math.max(1,5);
                //Math.max(trmatrix[0][0], trmatrix[0][1]);
                int poble1=0;
                int poble2 = 0;

                for(int r = 0 ; r < obtTags.length; r++){
                	p.println("obttag["+r+"]="+obtTags[r]);
                }
                for(int s=0;s<unknownPositions.length;s++){
					p.println("unknown positions["+s+"]="+unknownPositions[s]);
					//Math.max(trmatrix[unknownPositions[s]-1]);

					for(int s11=0;s11<trmatrix[0].length;s11++){
						
						if((unknownPositions[s] != 0)){
							if(trmatrix[unknownPositions[s]-1][s11] > m){
								m  = trmatrix[unknownPositions[s]-1][s11];
								poble1 = s11;
							}
							if(trmatrix[s11][unknownPositions[s]+1] > m1){
								m1  = trmatrix[s11][unknownPositions[s]+1];
								poble2 = s11;
							}
							
							p.println("Transition before ="+m+"  position:"+poble1+"  s="+unknownPositions[s]+"  obttag="+obtTags[unknownPositions[s]]);
							//if(obtTags[unknownPositions[s]].equals("PUNC")){
							//	obtTags[unknownPositions[s]+1] = "nl";
								p.println("Transition after ="+m1+"  position:"+poble2+"  s11 ="+s11+"  obttag="+obtTags[unknownPositions[s]+1]);
							//}
							//catch(ArrayIndexOutOfBoundsException ae){};	
							if((obtTags[unknownPositions[s]+1].equals("VF")) || (obtTags[unknownPositions[s]+1].equals("VAUX"))){
									if(obtTags[unknownPositions[s]-1].equals("PRP") || obtTags[unknownPositions[s]-1].equals("PRL") || obtTags[unknownPositions[s]-1].equals("NN") || obtTags[unknownPositions[s]-1].equals("NNP"))
									{
										p.println("why Transition"+s);
									}
									else{
										//obtTags[unknownPositions[s]] ="VF";
										//obtTags[unknownPositions[s]+1] ="VAUX";
										//unknwn= 1;
										p.println("why Transition else ="+unknownPositions[s]+"  obttag="+obtTags[unknownPositions[s]-1]);
									}
								}
								//}
				//catch(ArrayIndexOutOfBoundsException ae){}

								/*else
								{
									obtTags[unknownPositions[s]] = tagset[poble1];
									unknwn = 1;
								}*/
						}
					}
				
					//obtTags[unknownPositions[s]] = freqposition[poble1 + 1];
				}

				
				//traintags[raretrainpos[j]+1];
                //System.out.println("Frequency distribution GRNN5 testCount ="+testCount);
                for(int i = 0 ; i < (testCount-1) ; i++){
                	//for(int j=0; j < freqposition[i].length ; j++ ){
                		//System.out.println("freqposition["+i+"]="+freqposition[i]);
                		if(!freqposition[i].equals("nl") && unknwn == 0){
                			obtTags[i] = freqposition[i];
                		}
                		System.out.println("freqposition["+i+"]="+freqposition[i]+"  obttags["+i+"]="+obtTags[i]);
                	//}
                }
                String[] str;
                int flag2 = 0;
                String[] traintags = new String[testCount] ;
                String t = "ले";
                String t1 = "\u0932\u0947";    // online utf8 to string converter http://itpro.cz/juniconv/
               
                String t4 = "\u0918\u0923\u094D\u091F\u093E"; // GHANTA
                String t6 = "\u0918\u0923\u094D\u091F\u093E\u0915\u094B"; // GHANTAKO
                String t8 = "\u092D\u093E\u0917\u094D\u0926\u0948\u0917\u0930\u0947" ;//BHAGDAIGARE
                String t10 = "\u0930\u0941\u092A\u0948\u092F\u093E\u0901"; // rupeeya
                String t11 = "\u0932\u093f\u091f\u0930";// LITER
                String t12 = "\u092e\u0940\u091f\u0930"; // meter
                String t13 = "\u092b\u0940\u091f"; // feet
                String t14 = "\u0917\u094d\u0930\u093e\u092e" ; // Gram
                String t15 = "\u0915\u093f\u0932\u094b\u0917\u094d\u0930\u093e\u092e"; // kilogram
                String t16 = "\u0938\u0947\u0928\u094d\u091f\u0940\u092e\u0940\u091f\u0930"; // centimeter
                String t17 = "\u092b\u093f\u091f"; // fit

                int ind;
                
				obtTags[testCount-1] = "PUNC";	
		for(int i = 1 ; i < (testCount-1) ; i++){
			
			p.print(testSentence[i]+" ("+i+")"+"  "+obtTags[i]);
			p.println();
			//annTagedFile.write("\n INPUT DATABASE: ");
			//annTagedFile.write("\n");
			if(t1.equals(testSentence[i])){
				obtTags[i] = "PSPLE";
			}
			//rarepos = getrareTesttagposition();
			traintags = t3.gettrainTags();
			/*for(int q=0;q<tagset.length;q++){
				p.println("traintags["+q+"]="+tagset[q]);
			}*/
			p.println("tagset["+(poble1)+"]="+tagset[poble1]);
			p.println("tagset["+(poble2)+"]="+tagset[poble2]);
			//obtTags[unknownPositions[4]] = traintags[poble1 + 1];
			for(int j=0;j<rarepos.length ;j++){
				if(rarepos[j] != 0){
					p.println("rarepos["+j+"]="+rarepos[j] +"           traintags["+(raretrainpos[j] + 1)+"]="+traintags[raretrainpos[j]+1]);
					obtTags[rarepos[j]] = traintags[raretrainpos[j]+1];
				}
			}
			if(i < (testCount-1))
				p.println("obtTags["+i+"]="+obtTags[i]+"   obttags["+(i+1)+"]="+obtTags[i+1]);
			//for( int l = 0 ; l < obtTags.length ; l++){
				if(obtTags[i].equals("hi")){
					obtTags[i] = "NN";
				}

				if(obtTags[i].equals("NN") && obtTags[i+1].equals("PUNC")){
					p.println("Are");
					obtTags[i] = "VAUX";
				}
				if((obtTags[i].equals("QW") && obtTags[i+1].equals("PSPLE")) || (obtTags[i].equals("QW") && obtTags[i+1].equals("PSPLAI")) || (obtTags[i].equals("QW") && obtTags[i+1].equals("PSPO")) ){
					obtTags[i] = "NN";
				}
				if(obtTags[1].equals("PSPO")){
					obtTags[1] = "QW";
				}
			//System.out.println("ind["+i+"]="+testSentence[i]+"word ="+t4+"   obttags["+i+"]="+obtTags[i]);
		   /*	str = t3.RegionMatchesDemo1(t4,testSentence[i]);
		   	//System.out.println("Hi QTO1 str="+str[0]);
		   	if(str[0].equals("ab"))
		        	flag2 = 0;
			else
				flag2 = 2;
			//System.out.println("Hi QTO flag ="+flag2);*/
			if(t4.equals(testSentence[i]) || t6.equals(testSentence[i]) || t10.equals(testSentence[i]) || t11.equals(testSentence[i]) || t12.equals(testSentence[i]) || t13.equals(testSentence[i]) || t14.equals(testSentence[i]) || t15.equals(testSentence[i]) || t16.equals(testSentence[i]) || t17.equals(testSentence[i])){
				//System.out.println("Hi QTO");
				//System.out.println("before obttags["+(i-1)+"]="+obtTags[i-1]);
				obtTags[i-1] = "QTO";
				//System.out.println("obttags["+(i-1)+"]="+obtTags[i-1]);
			}
			//}
			if(t8.equals(testSentence[i]) && obtTags[i+1].equals("PSPKO")){
				//System.out.println("Hi JJ");
				//System.out.println("before obttags["+(i)+"]="+obtTags[i]);
				obtTags[i] = "JJ";
				//System.out.println("obttags["+(i)+"]="+obtTags[i]);
			}
			for(int r = 0 ; r < spvb.length ; r++){
				if(testSentence[i].equals(spvb[r]) && i < (testCount-2) ){
					p.println("spvb["+r+"]="+spvb[r]+"  testcout ="+testCount+"  i="+i);
					if(i < testCount/2)
						obtTags[i] = "NN";
					else if(!obtTags[i+1].equals("RB") && !obtTags[i+1].equals("NN") )
						obtTags[i] = "VF";
					p.println("obtTags["+i+"]="+obtTags[i]);
				}
			}
			//System.out.println(" metoo  obttag["+i+"] = "+obtTags[i] +"obttag["+(i-1)+"] = "+obtTags[i-1]);
			if((obtTags[i].equals("VMKO") && obtTags[i+1].equals("PSPO")) || (obtTags[i].equals("VMKO") && obtTags[i+1].equals("PSPO"))){
			//System.out.println(" me obttag["+i+"] = "+obtTags[i]);
				obtTags[i] = "NN";
			}
			/*if(obtTags[i].equals("QW") && (obtTags[i+1].equals("VF") || obtTags[i+1].equals("VAUX"))){
				obtTags[i] = "NN";
			}*/
			if(obtTags[i].equals("CCS")  && obtTags[i+1].equals("PUNC")){
				obtTags[i] = "VAUX";
			}
			if(obtTags[i].equals("VAUX")  && obtTags[i-1].equals("CCS") && obtTags[i+1].equals("PUNC")){
				obtTags[i] = "NN";
			}
			if((obtTags[i].equals("CCS")  && obtTags[i+1].equals("VF")) || obtTags[i].equals("CCS")  && obtTags[i+1].equals("VAUX")){
				obtTags[i] = "NN";
			}
			if(obtTags[i].equals("RB") && (obtTags[i+1].equals("DMI") || obtTags[i+1].equals("DM") || obtTags[i+1].equals("DMR") || obtTags[i+1].equals("DMD")) ){
				obtTags[i] = "PSPO";
			}
			if((obtTags[i].equals("PSPLE") && obtTags[i-1].equals("CCS")) || (obtTags[i].equals("PSPO") && obtTags[i-1].equals("CCS")) || (obtTags[i].equals("PSPLAI") && obtTags[i-1].equals("CCS"))){
				obtTags[i-1] = "NN";
			}
			/*if(obtTags[i].equals("VMNE") || obtTags[i].equals("VMO") || obtTags[i].equals("VMKO") ){
				obtTags[i] = "VF";
			}*/
			//ind = t3.getleindex();
			//obtTags[ind] = "PSPLE";
			//p.println("index="+ind);
			//annTagedFile.write("  "+testSentence[i]+"  "+obtTags[i]);
		}

		for(int s=0;s<unknownPositions.length;s++){
					p.println("unknown positions["+s+"]="+unknownPositions[s]);
					//Math.max(trmatrix[unknownPositions[s]-1]);

					for(int s11=0;s11<trmatrix[0].length;s11++){
						
						if((unknownPositions[s] != 0)){
							if(trmatrix[unknownPositions[s]-1][s11] > m){
								m  = trmatrix[unknownPositions[s]-1][s11];
								poble1 = s11;
							}
							if(trmatrix[s11][unknownPositions[s]+1] > m1){
								m1  = trmatrix[s11][unknownPositions[s]+1];
								poble2 = s11;
							}
							
							p.println("Transition before ="+m+"  position:"+poble1+"  s="+unknownPositions[s]+"  obttag="+obtTags[unknownPositions[s]]);
							//if(obtTags[unknownPositions[s]].equals("PUNC")){
							//	obtTags[unknownPositions[s]+1] = "nl";
							p.println("Transition after ="+m1+"  position:"+poble2+"  s11 ="+s11+"  obttag="+obtTags[unknownPositions[s]+1]);
							if((obtTags[unknownPositions[s]+1].equals("VF")) || (obtTags[unknownPositions[s]+1].equals("VAUX"))){
									if(obtTags[unknownPositions[s]-1].equals("PRP") || obtTags[unknownPositions[s]-1].equals("PRL"))
									{
										p.println("why Transition"+s);
										//obtTags[unknownPositions[s]] ="VF";
										if(obtTags[unknownPositions[s]+1].equals("VF") )
											obtTags[unknownPositions[s]+1] = "VAUX";
									}
									else if(!obtTags[unknownPositions[s]-1].equals("NNP") && !obtTags[unknownPositions[s]-1].equals("NN") && !obtTags[unknownPositions[s]-1].equals("PSPO") && !obtTags[unknownPositions[s]-1].equals("PSPKO") && !obtTags[unknownPositions[s]-1].equals("PSPLE") && !obtTags[unknownPositions[s]-1].equals("PSPLAI")){
										obtTags[unknownPositions[s]] ="VF";
										obtTags[unknownPositions[s]+1] ="VAUX";
										unknwn= 1;
										p.println("why Transition else"+unknownPositions[s]+"  obttag="+obtTags[unknownPositions[s]-1]);
									}
								}
							//}
								/*else
								{
									obtTags[unknownPositions[s]] = tagset[poble1];
									unknwn = 1;
								}*/
						}
					}

					//obtTags[unknownPositions[s]] = freqposition[poble1 + 1];
				}

				if(obtTags[1].equals("VF") && testCount >1 && unknwn == 0){
					obtTags[1] = "NN";
				}

		System.out.println("Part Of Speech Tagged Output");
		for(int i1 = 1 ; i1 < testCount ; i1++){
			annTagedFile.write("  "+testSentence[i1]+"  "+obtTags[i1]);
			System.out.print("  "+testSentence[i1]+"  "+obtTags[i1]);
		}
		//SpellCheck()

		annTagedFile.close();
	}
	
	
	
/*public void BtoD(long num){
    long rem;
              while(num > 0){
                                        rem = num % 10;
                                        num = num / 10;
                                        if(rem != 0 && rem != 1){
                                            ////System.out.println("This is not a binary number.");
                                            ////System.out.println("Please try once again.");
                                            System.exit(0);
                                        }
                                }
                                int i= Integer.parseInt(str,2);
                                ////System.out.println("Decimal:="+ i);
}*/

	public void readNet(String s1) throws IOException{

			/*String s1 = "0";
			s1 = JOptionPane.showInputDialog(" Please Enter the name of data file");*/
			String dataFile = s1;
			int inputs, nodes,pats,outlns;
			FileReader fr = new FileReader(dataFile);
			BufferedReader br = new BufferedReader(fr);
			String textLine = br.readLine();
			inputs = Integer.parseInt(textLine);
			textLine = br.readLine();
			outlns = Integer.parseInt(textLine);
			textLine = br.readLine();
			nodes = Integer.parseInt(textLine);
			textLine = br.readLine();
			pats = Integer.parseInt(textLine);

			hidNeuron = nodes;
			patCount = pats;
			inNeuron = inputs;
			outNeuron = outlns;

	        patterns = new double[pats][inputs+outNeuron];
	        double [][] inpats = new double[patCount][inputs];
			outputPatterns = new double[patCount][outNeuron];
			assignOutput(patCount,outNeuron);
			for ( int i =0; i < pats; i++ ) {
	                    textLine = br.readLine();
	                    ////System.out.println(textLine);
	                    String[] tokens = textLine.split("\\s|,");
	                   // ////System.out.println("  "+(inputs+outlns));
	                    for (int j=0; j<(inputs+outlns); j++) {
	                        double patij = Double.parseDouble(tokens[j]);
	                        patterns[i][j] = patij;
	                      //  ////System.out.println("pat["+i+"]["+j+"]="+patterns[i][j]);
	                	if( j < inputs){
	                        	inpats[i][j] = patij;
		                }
						else
		                    outputPatterns[i][j-inputs] = patij;
	                    }
	                }
	                cenIndex = new int[hidNeuron];
	                if(hidNeuron == patCount){
						for(int i=0;i<hidNeuron;i++)
						      cenIndex[i] = i;
					}
					else{
		                textLine = br.readLine();
		                String[] tokens = textLine.split("\\s|,");
	                	for(int i=0;i<hidNeuron;i++){
	                	    cenIndex[i] = Integer.parseInt(tokens[i]);
	                	    //////System.out.println("cenIndex["+i+"]="+cenIndex[i]);
	                	}
					}
	               	br.close();
	            	fr.close();


	}
	public double[][] readGaussianMatrix(String s1) throws IOException{
		int ip ;
		FileReader fr = new FileReader(s1);
		BufferedReader br = new BufferedReader(fr);
		String textLine = br.readLine();
		ip = Integer.parseInt(textLine);
		gaussian = new double[ip][ip];
		//p.println("patcount="+ip);
		for ( int i =0; i < ip; i++ ) {
                    textLine = br.readLine();
                    String[] tokens = textLine.split("\\s|,");
                    for (int j=0; j< ip; j++) {
	                double patij = Double.parseDouble(tokens[j]);
	              //  //p.println(patij);
                    	gaussian[i][j] = patij;
                    	////p.println("gaussian["+i+"]["+j+"]="+gaussian[i][j]);
                    }
                } 
                br.close();
            	fr.close();
            	return gaussian;
	}
	
	
	
	public double[][] readTransitionMatrix(String s1) throws IOException{
		int ip ;
		FileReader fr = new FileReader(s1);
		BufferedReader br = new BufferedReader(fr);
		String textLine = br.readLine();
		ip = Integer.parseInt(textLine);
		double[][] tm = new double[ip][ip];
		//p.println("Tagcount="+ip);
		for ( int i =0; i < ip; i++ ) {
                    textLine = br.readLine();
                    String[] tokens = textLine.split("\\s|,");
                    for (int j=0; j< ip; j++) {
	                double patij = Double.parseDouble(tokens[j]);
	              //  //p.println(patij);
                    	tm[i][j] = patij;
                    	////p.println("gaussian["+i+"]["+j+"]="+gaussian[i][j]);
                    }
                } 
                br.close();
            	fr.close();
            	return tm;
	}
	public double[][] getTrainFeatures(){
		return patterns;
	}
	
	public double[][] readTrainingPatterns(String s2) throws IOException{
		int ip ;
                
		FileReader fr = new FileReader(s2);
		BufferedReader br = new BufferedReader(fr);

		String textLine = br.readLine();
		ip = Integer.parseInt(textLine);
		p.println("IP="+ip+"   inNeurons = "+inNeuron+"   outNeurons= "+outNeuron);
		patterns = new double[ip][inNeuron];
		patCount = ip;
		double[][] outpats = new double[patCount][outNeuron];
		//p.println("patcount="+ip +" inNeuron="+inNeuron+" outNeuron="+outNeuron);
		totalpats = new double[patCount][3*inNeuron];
		totaloutpats = new double[patCount][inNeuron];				
		for ( int i =0; i < ip; i++ ) {
                    textLine = br.readLine();
                    String[] tokens = textLine.split("\\s|,");
                    for (int j=0; j< 3*inNeuron+outNeuron; j++) {
	                double patij = Double.parseDouble(tokens[j]);
	                if(j < 3*inNeuron)
		                totalpats[i][j] = patij;
		        else
		        	totaloutpats[i][j-3*inNeuron] = patij;
	               // //p.println(patij);
	                if(j >= inNeuron && j < 2*inNeuron){
	                    	patterns[i][j-inNeuron] = patij;
        	            	//p.println("patterns["+i+"]["+(j-inNeuron)+"]="+patterns[i][j-inNeuron]);
        	        }
        	        else if(j >= 3*inNeuron){
        	        	//p.println(patij);
        	        	outpats[i][j-3*inNeuron] = patij;
                    		////p.println("output patterns["+i+"]["+(j-3*inNeuron)+"]="+outpats[i][j-3*inNeuron]);
        	        }
                    	
                    	
                    }
               //     //p.println("outneurons="+outNeuron);

                   /* for(int k=inNeuron;k<inNeuron+outNeuron;k++){
                    	double patij1 = Double.parseDouble(tokens[k]);
	                //p.println(patij1);
                    	outpats[i][k-inNeuron] = patij1;
                    	//p.println("output patterns["+i+"]["+(k-inNeuron)+"]="+patterns[i][k-inNeuron]);
                    }*/
                } 
                outputPatterns = outpats;
                br.close();
            	fr.close();
            	return patterns;
	}
	
	public void readTestNet(String s1) throws IOException{

				/*String s1 = "0";
				s1 = JOptionPane.showInputDialog(" Please Enter the name of data file");*/
				//////System.out.println("hi");
				String dataFile = s1;
				int inputs, nodes,pats,outlns;
				FileReader fr = new FileReader(dataFile);
				BufferedReader br = new BufferedReader(fr);
				String textLine = br.readLine();
				inputs = Integer.parseInt(textLine);
				textLine = br.readLine();
				outlns = Integer.parseInt(textLine);
				textLine = br.readLine();
				nodes = Integer.parseInt(textLine);
				textLine = br.readLine();
				pats = Integer.parseInt(textLine);

				hidNeuron = nodes;
				testCount = pats;
				inNeuron = inputs;
				outNeuron = outlns;

		        Testpatterns = new double[pats][inputs];
		        double [][] inpats = new double[testCount][inputs];
				testoutputPatterns = new double[testCount][outNeuron];
				assigntestOutput(testCount,outNeuron);
				for ( int i =0; i < pats; i++ ) {
		                    textLine = br.readLine();
		                   // ////System.out.println(textLine);

		                    String[] tokens = textLine.split("\\s|,");
		                    for (int j=0; j<(inputs+outNeuron); j++) {
		                        double patij = Double.parseDouble(tokens[j]);
						   		if( j < inputs){
		                        	Testpatterns[i][j] = patij;
			               		 }
								else{
			                   		 testoutputPatterns[i][j-inputs] = patij;
								 }
		                    }
		                }
		                /*cenIndex = new int[hidNeuron];
		                if(hidNeuron == patCount){
							for(int i=0;i<hidNeuron;i++)
							      cenIndex[i] = i;
						}
						else{
			                textLine = br.readLine();
			                String[] tokens = textLine.split("\\s|,");
		                	for(int i=0;i<hidNeuron;i++){
		                	    cenIndex[i] = Integer.parseInt(tokens[i]);
		                	}
						}*/
		               // createLayer( inputs, nodes,outlns, pats,pa,cenIndex,0);
		               	br.close();
		            	fr.close();


	}

	public void getOutput(){
		for(int i=0;i<outNeuron;i++){
			////p.println("outputneurons="+outNeuron);
			Output(i);
		}

	}
	
	public void getOutput1(double[][] gaus){
		for(int i=0;i<outNeuron;i++){
			////p.println("outputneurons="+outNeuron);
			Output1(i, gaus);
		}

	}

	public void getTestOutput(){
		for(int i=0;i<outNeuron;i++){
			TestOutput(i);
		}

	}
	/*public void getTestOutput(){
		return testCount;
	}*/

	public int checkPatternwiseOutput(int pat){
		int ch = 0;
		//p.println("pat="+pat);
		for(int n=0;n<outNeuron;n++){
			////p.println("CO["+pat+"]["+n+"]="+computedOutput[pat][n]+"outpatterns["+pat+"]["+n+"]="+outputPatterns[pat][n]);
			if(dround(computedOutput[pat][n],0) != dround(outputPatterns[pat][n],0)){
				//p.println("CO["+pat+"]["+n+"]="+computedOutput[pat][n]+"outpatterns["+pat+"]["+n+"]="+outputPatterns[pat][n]);
   			//if(dround(computedOutput[pat][n],0) != dround(testoutputPatterns[pat][n],0)){
				ch++;
				////p.println("ch="+ch);
				//if(ch == 2)
					////p.println("correct pattern is "+n);
				//if(ch == 1)
					////p.println("Identified Pattern is "+n);
			}
			else{
				////p.println("Computed Output["+pat+"]["+n+"]="+computedOutput[pat][n]);
				continue;
				
			}
		}
		if(ch == 0)
			return 1;
		else{
			
			return 0;
		}
	}

	public void checkOutput(){
		int cnt=0;
		//testCount = patCount;
		for(int i=0;i<testCount;i++){
			int count = checkPatternwiseOutput(i);
			
			cnt=cnt + count;
			
		}

		double accuracy = (double)cnt/testCount;
		//p.println("Accuracy="+(accuracy)*100+"%");
	}

	 public static double dround ( double x, int n ) {
			long copy = 0l;
			if ( n <= 0 ) {
				copy = Math.round(x);
				return (double)copy;
			}
			long multiplier = 1;
			for ( int i=0; i<n; i++)
				multiplier *= 10;
			double y = x * multiplier ;
			copy = Math.round(y);
			return (double)copy/multiplier;
	}
	public void getFeatures(TagGRNN t1){
		//Feat = t1.gettrainInput();
		//patterns = Feat;
		int k = 0;
		//p.println("INPUT PATTERNS");
		//p.println("number of input patterns = "+patterns.length);
		//for(int i = 0 ; i< patterns.length ; i++){
			////p.println();
			//////System.out.println("OL[i]= "+Output[i].length);		
		//	for(int j = 0 ; j <patterns[0].length ; j++){
				//////System.out.println("testoutpat["+i+"]["+j+"]= "+Output[i][j]);	
				//p.print(" "+patterns[i][j]);	
		//		if(i%2 != 0){
					//p.print(" "+Feat[i][j]);	
		//			patterns[k][j] = Feat[i][j];		
		//		}
		//		else{
		//			k = i/2;
		//		}
//				testot[i][j] = Output[i][j]*1.00;
//				testoutputPatterns[i][j] =  (double)testot[i][j];

		//	}
		//	//p.println();
		//}
	}
	public double[][] getInputpatterns(){
		return patterns;
	}
	public void getOutput(TagGRNN t1){
		Output = t1.getOutputs();
		////System.out.println("OL= "+Output[0].length);
		double[][] testot = new double[Output.length][Output[0].length];
		
	//	testot = Output;
//		testoutputPatterns = (double) testot;
		//p.println("patCount = "+patCount);
		for(int i = 0 ; i< this.patCount ; i++){
			//p.println();
			//////System.out.println("OL[i]= "+Output[i].length);		
			for(int j = 0 ; j < Output[0].length; j++){
				//////System.out.println("testoutpat["+i+"]["+j+"]= "+Output[i][j]);	
				//p.print(" "+Output[i][j]);			
				testot[i][j] = Output[i][j]*1.00;
//				testoutputPatterns[i][j] =  (double)testot[i][j];

			}
		}
		outputPatterns = testot;
	}
	public double[][] getOriginaloutput(){
		return outputPatterns;
	}
	
	public void defaultercase(TagGRNN tt1, int[] ld, String[] lds){
		int testvalue = 0, flag = 0;
		String possibletag1, possibletag2, ptag ;
		String finaltag = "hi";
		//String[] qtstr = new String[1];  Only one quantifier 
		////System.out.println("Hi its defaultcase");
		for(int i = 0 ; i < 5 ; i++)   // Assuming maximum of 5 confusion words are there in test sentence
		{
			if(testvalue != ld[0] && flag == 0){
				testvalue = ld[0];
				p.println(" testvalue="+testvalue);
				ptag = obtTags[testvalue-1];
				possibletag1 = lds[0];
				possibletag2 = lds[1];
				finaltag = tt1.getdinfo(ptag, possibletag1, possibletag2);
			//	qtstr = tt1.RegionMatchesDemo(lds[testvalue],qtotag);
			System.out.println("possibletag1="+possibletag1+"  possibletag2="+possibletag2);
			
			//	RegionMatchesDemo1("ले",ws1)
				
				/*if(finaltag.equals("PSPLE") || finaltag.equals("PSPO")){
					//if(obtTags[testvalue - 1].equals("VAUX"))
					finaltag = "PUNC";
				}*/
				if(finaltag.equals("PUNC") || finaltag.equals("JJ")){
					//if(obtTags[testvalue - 1].equals("VAUX"))
					finaltag = "PUNC";
				}
				if(finaltag.equals("QTC") || finaltag.equals("QTO")){
					if(obtTags[testvalue+1].equals("PUNC")){
						finaltag = "VAUX";
					}
				}
				if(finaltag.equals("NN") || finaltag.equals("NNP")){
					if(obtTags[testvalue+1].equals("PUNC")){
						finaltag = "VAUX";
					}
				}
				if(finaltag.equals("QTC") || finaltag.equals("QTO") || finaltag.equals("QW")){
					if(obtTags[testvalue+1].equals("JJ")){
						finaltag = "RP";
					}
				}
				
				if(finaltag.equals("QTC") || finaltag.equals("QTO") || finaltag.equals("QW")){
					if(obtTags[testvalue+1].equals("VAUX") || obtTags[testvalue+1].equals("VF")) {
						finaltag = "NN";
					}
				}
				
				if(finaltag.equals("PSPKO") || finaltag.equals("QTO")){
					if(obtTags[testvalue+1].equals("VF") || obtTags[testvalue+1].equals("VM")){
						finaltag = "PRL";
					}
				}
				p.println("final tag = "+finaltag);
				flag = 1 ;
				//obtTags[testvalue] = finaltag ;
			}
		}
		if(testvalue != 0){
			obtTags[testvalue] = finaltag ;
		}
		
		//p.println("ot["+testvalue+"]="+obtTags[testvalue]);
		/*String[] dtag = new String[testCount];
		
		for(int i = 0 ; i < testCount ; i++)
			dtag[i] = tt1.getdinfo(id[][],ids);*/
	}
	public void getInfo(TagGRNN t1){
		int testCount1;
		double[][] testinputs;
		
				
//		inNeuron = t1.getinputNeurons();
		inNeuron = 39;
		tgset = t1.getTagset();
		words = t1.getWords();
		//for(int i = 0 ; i < 447 ; i++){
			//p.println(" test word["+i+"]= "+words[i]);
		//}
		outNeuron = t1.getoutputNeurons();
		hidNeuron = t1.gethiddenNeurons();
		patCount = t1.getnumberPatterns();
		//testCount = patCount;
		testCount1 = t1.getnumbertestPatterns();
		testCount = testCount1;
		testSentence = t1.gettestSentence();
		/*for(int i = 0 ; i < testSentence.length ; i++){
			//p.println(" testword["+i+"]= "+testSentence[i]);
		}*/
		words = testSentence;
		//testinputs = t1.getTestF();
		//testinputs1 = testinputs;
		
		//p.println();
		//p.println(" GRNN5 test patterns: ");
		//p.println("**Test count = "+testCount1+"words count="+patCount+"  patCout="+patCount+"  hidneuron="+hidNeuron+"  outneuron="+outNeuron);
		testCount = testCount1;
		/*for(int i = 0 ; i < testCount1 ; i++){
			for(int j = 0 ; j < 117 ; j++){
				p.print("   "+testinputs[i][j]);
			}
			//p.println();
		}*/
		//Testpatterns = testinputs;
		
		//testCount = t1.getnumbertestPatterns();
		////p.println("inneurons="+inNeuron+" outNeurons="+outNeuron+" hidNeurons="+hidNeuron+"  patcount="+patCount);
	}
	
	
	
	public void getUnkonwnTags(TagGRNN t2){
		int[] positions = new int[testCount];
		String[] tg = new String[5];     // Maximum possible tags for unknown words
		//p.println(" It is getUnkonwnTags metod: ");
		positions=t2.getPositions();
		unknownPositions = new int[testCount];

		for(int l  = 0 ; l < testCount ; l++){
			System.out.println("  obtTags["+l+"]="+obtTags[l]+"  trmatrix[0][0]"+trmatrix[0][0]);
			if(positions[l] != 0){
				unknownPositions[l] = positions[l] ;
				System.out.println("*unknownpositions["+l+"]="+positions[l]);
			}
		}
		tg = t2.getPossibleTags1(positions, obtTags, totalpats, words);
		for(int i = 0 ; i < tg.length ; i++){
			System.out.println(" tg["+i+"]= "+tg[i]);
		}
	}
	public void getDefaultersTag(TagGRNN t1){
		int[] confusedwordindex = new int[testCount];
		//finalalaram = t1.getalaram();
		int[] finalalaram = new int[testCount];
		int count = 0;
		confusedwordindex = t1.getconfusedword();
		for(int l  = 0 ; l < testCount ; l++){
			for(int m = 0 ; m < testCount ; m++){
				if(l == confusedwordindex[m] ){
					finalalaram[l] = 1;
					if(count == 0){
						////System.out.println("   final alaram["+l+"]="+finalalaram[l]);
						count++;
					}
				}	
				else{
					//finalalaram[l] = 0; 
				}		
			}
		}
		double[][] testinputs1;
		int localdefaulter[][] = new int[testCount][3];
		String localdefaulterstring[][] = new String[testCount][2];
		int i = 0 ;
		for(int m = 0 ; m < testCount ; m++){
			//////System.out.println("output for the word "+i+" is");		
			////p.println(" finalalaram["+m+"] ="+finalalaram[m]);
			i = m ;
			if(finalalaram[m] != 0){
				testinputs1 = t1.getTest2F();
				Defaulters = t1.getDefaulter();
				Defaultersstring = t1.getDefaulterstring();
				//defaultercase();
			
			//}
			//for(int i = 0 ; i < testCount && finalalaram[m] != 0 ; i++){
				if(i != 0){
					localdefaulter[i][0] = (int)Defaulters[i][0];
					localdefaulter[i][1] = (int)Defaulters[i][1];
					localdefaulter[i][2] = (int)Defaulters[i][2];
					localdefaulterstring[i][0] = Defaultersstring[i][0];
					localdefaulterstring[i][1] = Defaultersstring[i][1];				
				}
				
				for(int j = 0 ; j < 4 ; j++){
					p.print("   "+(int)Defaulters[i][j]);
				
				}
				p.println();
				for(int k = 0 ; k < 2 ; k++){
					p.print("  defultstring["+i+"]["+k+"] ="+Defaultersstring[i][k]+"  localdefaulter["+i+"][0]="+localdefaulter[i][0]);
				}
				p.println();
			//}
			//if(finalalaram[i] != 0){

				defaultercase(t1,localdefaulter[i],localdefaulterstring[i]);
			
			}
		}

		
		
		
		
		
		
		
		
		////p.println(" finalalaram ="+finalalaram);
		//if(finalalaram != 0){
		//	testinputs1 = t1.getTest2F();
		//	Defaulters = t1.getDefaulter();
		//	Defaultersstring = t1.getDefaulterstring();
		//	//p.println("testcount="+testCount);
			//defaultercase();
			
		//}
//	   	//p.println(" pre confusedwordindex="+confusedwordindex[0]);

//		for(int i = 0 ; i < testCount ; i++){
//		      for(int m = 0 ; m < testCount ; m++){
//			   if(i != 0 && i == confusedwordindex[m]){
//			   	//p.println("confusedwordindex="+confusedwordindex[m]);
//				localdefaulter[i][0] = (int)Defaulters[i][0];
//				localdefaulter[i][1] = (int)Defaulters[i][1];
//				localdefaulter[i][2] = (int)Defaulters[i][2];
//				localdefaulterstring[i][0] = Defaultersstring[i][0];
//				localdefaulterstring[i][1] = Defaultersstring[i][1];	
//				finalalaram[i] = 1;
//				defaultercase(t1,localdefaulter,localdefaulterstring);			
//			    }
//			    finalalaram[i] = 1 ;
//			}
			
//			for(int j = 0 ; j < 4 ; j++){
//				p.print("   "+(int)Defaulters[i][j]);
//				
//			}
//			//p.println();
//			for(int k = 0 ; k < 2 ; k++){
//				p.print("   "+Defaultersstring[i][k]);
//			}
//			//p.println();
			//if(finalalaram[i] != 0){

			//	defaultercase(t1,localdefaulter,localdefaulterstring);
			
			//}
//		}
		/*for(int l=0;l< testCount; l++){
			if(finalalaram[l] != 0){
					defaultercase(t1,localdefaulter,localdefaulterstring);
			}
		}*/
	}
	public void getPatterns(double[][] pt){
		patterns = pt;
		
	}
	
	public double[][] gettotalpats(){
		return totalpats;
	}
	
	public void getTestpatterns(double[][] tp){
				Testpatterns = tp;
	}
	
	public void readFile1(String fileName, TagGRNN tg, int[] unknpos) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
	//BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(obsName), "UTF-8"));
//        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName), "UTF-8");
  //      BufferedWriter fout = new BufferedWriter(writer);
     //   OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(anninput), "UTF-8");
       // BufferedWriter fout1 = new BufferedWriter(writer1);
       // String is = br.readLine();
        String vibhakti1 = "hi";
        try {
            StringBuilder sb = new StringBuilder();
            String line =  br.readLine();
          //  String sent = br1.readLine();
            //fout.write(line);
           // String ln = line;
            //// ////System.out.println(ln);
           // while (line != null) {
               // ////System.out.println("hi");
            
            //sb.append(line);
            //sb.append("\n");
           
           // // ////System.out.println("line length = "+line.length());
           //if(line==null)
             //   break;
            String[] newwords = new String[3000000];// = line.split(" ");//those are your words
            String[] sentwords = new String[3000000];
            String[] obs = new String[5000000];
            String[] sentwords1 = new String[3000000];
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
                            newwords[obsnowords]=ws;
                          //  ////System.out.println("***words["+nowords+"]="+words[nowords]);
                            //nowords++;
                             if((obsnowords%2) == 0){
	                            obws = ws;
        	                  //  sentwords1[obsnowords]=obws;
        	                    sentwords[j]=obws;
        	                    ////System.out.println("////sentwords["+j+"]="+sentwords[j]);
                	           // fout.write(" ");
                        	   // fout.write(sentwords[j]);
                            	 //   obsnowords++;
                            	    // ////System.out.println("obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
                            	    j++;
                            	    
                            }
			    else{
				    obws = ws;
	                            sentwords1[obsnowords]=obws;
	                          //   ////System.out.println("**obsnowords="+obsnowords+"j="+j+"sentwords1["+j+"]="+sentwords1[j]);
	                          //  j++;
	                    }
	                    obsnowords++;
                        }
                line = br.readLine();
        
            String[] ntags = new String[j];
            int count = 0;
            for(int i = 0 ; i < 2*j; i++){
            	if(i%2 != 0){
            		ntags[count] = sentwords1[i];
	                ////System.out.println("words["+count+"]="+ntags[count]+"   sentwords["+count+"]="+sentwords[count]);
	                count++;
	        }
                  
                // fout.write(words[i]);
                 //fout.newLine();
             }
             int[] newunknownPositions = new int[unknpos.length];
             int ind = 0;
             for(int l = 0 ; l < unknpos.length; l++){
                     if(unknpos[l] != 0){
		             //System.out.println("words[unknopos["+l+"]]="+words[unknpos[l]]);	
		             obtTags[unknpos[l]] = tg.RegionMatchesDemoTestNew(words[unknpos[l]], sentwords, ntags, unknpos[l]) ;
		             if(obtTags[unknpos[l]].equals("hi")){
		             	newunknownPositions[ind] = l;
		             	////System.out.println("**words[unknopos["+l+"]]="+newunknownPositions[ind]);	
		             	ind++;
		             }
		             else{
		             	unknownPositions[ind] = 0;
		             }
		             
		     }
	     }
	     getStillUnkonwnTags(tg, newunknownPositions);
             //line = br.readLine();//vibhakti1 = line;
             // ////System.out.println("line="+line+"    words="+Words+"    noWords ="+nowords+"    noTags = "+noTags);
             newWords = newwords;
             nonewWords = obsnowords;
           //  nonewTags = noWords/2;
             newtrainTags = sentwords1;
             ////System.out.println("no of words = "+nonewWords);
             
             
              //sentWords = sentwords;
             // sentTotalwords = sentwords1;
             // sentnoWords = j;
           //   totalWords = obsnowords;
   
            break;
        }
        //numberPatterns = noWords;
        
      //   annFile = fout1;
//         generateANNdatabase(fout1);
        // sentwords1 = getOriginalOutput(sentwords,obsnowords);
        // fout.write("\n");
         //fout.write(sentwords1[obsnowords]);
      /*   for(int i = 0 ; i < j; i++){
         	//// ////System.out.println("sentwords1["+i+"]="+sentwords1[i]);
		// ////System.out.println("sentwords["+i+"]="+sentwords[i]);         	
         }
         for(int i = 0 ; i < 2*j; i++){
         	// ////System.out.println("sentwords["+i+"]="+sentwords[i]);
		// ////System.out.println("sentwords1["+i+"]="+sentwords1[i]);         	
         }*/
        } finally {
        br.close();
     //   br1.close();
     //   fout.close();
    //    fout1.close();
        }
        
    }
    
    public void getStillUnkonwnTags(TagGRNN t2, int[] newunpos){
		int[] positions = new int[testCount];
		String[] tg = new String[5];     // Maximum possible tags for unknown words
		//p.println(" It is getStillUnkonwnTags metod: ");
		//positions=t2.getPositions();
		//unknownPositions = new int[testCount];
		for(int l  = 0 ; l < testCount ; l++){
			if(newunpos[l] != 0){
//				unknownPositions[l] = positions[l] ;
				////System.out.println("*newpositions["+l+"]="+newunpos[l]);
			}
		}
		tg = t2.getPossibleTags(newunpos, obtTags, totalpats);
	}
	public void getorisentTest(TagGRNN t1){
		String[] oritestSent;
		oritestSent = t1.getoritestSent();
	}
	public void getUnknownTagsfromNewfile(String newfile, TagGRNN tg1, String file){
		//p.println();
		//p.println("hi its new unknown words file :");
		try{
			readFile1(newfile, tg1, unknownPositions);
		}
		catch(IOException ie){
		
		}
	}
	public void SpellCheck(String f1, String f2){

	}
	
	public static void main(String[] args) throws IOException{
		GRNN5 gr = new GRNN5();
		//AutoCorrector ac = new AutoCorrector();
		double[][] testinputs;
	//	TagGRNN tg = new TagGRNN(args[0], args[1], args[2], args[3]);
	//	String file1 = "traintemp.txt";
		String file1 = "smallmaindata11.txt";
		String file2 = "newt1.txt";
		String file3 = "newt2.txt";
		String file4 = "newt3.txt";
		String file5 = "newt4.txt";
		String file6 = "annoutput.txt";
		//String file7 = "smallmaindata10.txt";
		String file7 = "TDIL_Main_3.txt";

//		TagGRNN tg = new TagGRNN(args[0], args[1], args[2], args[3], 0);
		TagGRNN tg = new TagGRNN(file1, args[0], file2, file3, 0);
		
		gr.getInfo(tg);
		
		//gr.readNet(args[0]);
		//gr.getGaussianMatrix();
		
		//gr.readTestNet(args[1]);
		
//		patterns = pats;
		int patCount = tg.getnoPatterns();
		////System.out.println("patcount="+patCount);
		double[][] gs =  new double[patCount][patCount];
		gs = gr.readGaussianMatrix(file5);
		double[][] pats;		
		pats = gr.readTrainingPatterns(file3);
		trmatrix = gr.readTransitionMatrix(file4);
		tg.getTransitionMatrix(trmatrix);
		gr.getPatterns(pats);
		
		gr.getFeatures(tg);
		gr.getOutput(tg);
		////System.out.println("WELCOME TO GRNN5: ");
		
		double[][] ttlpts;
		ttlpts = gr.gettotalpats();
	//	for(int l = 0 ; l < 447 ; l++){
   		/*for(int m = 0 ; m < 117 ; m++){
		   	////System.out.print("   "+ttlpts[l][m]);
		}*/
		////System.out.println();
	//}
		gr.getOutput1(gs);
		testinputs = tg.getTestF();
		gr.getTestpatterns(testinputs);
		tg.getTestFeatures(ttlpts);
		rareFreqposition = tg.getrareFreqpos();
		rareFreqTrainposition = tg.getrareFreTrainpos();
		gr.getTestGaussianMatrix(rareFreqposition, rareFreqTrainposition);
		gr.getTestOutput();
		gr.checkOutput();
		
		gr.display(file6);
		gr.getDefaultersTag(tg);
		gr.getUnkonwnTags(tg);
		gr.getorisentTest(tg);
		gr.getUnknownTagsfromNewfile(file7,tg,"जानकार");
		
		gr.getDefaultersTag(tg);
		//gr.getStillUnkonwnTags(tg);
		gr.finaldisplay(file6, tg, rareFreqposition, rareFreqTrainposition);
		gr.getUnkonwnTags(tg);
		gr.finaldisplay(file6, tg, rareFreqposition, rareFreqTrainposition);
		//gr.SpellCheck(file6,file1);
		//ac.main1(file6, args[1]);
	}

}
