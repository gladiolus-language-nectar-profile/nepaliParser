import java.io.*;
import javax.swing.*;
public class GRNN4{
	java.io.PrintStream p = System.out;
	protected int inNeuron ;
	protected int hidNeuron ;
	protected int outNeuron ;
	private int patCount ;
    	private int testCount;
    	private int finalalaram;
    	private String[] tgset;
    	private String[] words;
	private double[][] computedOutput;
	private double[][] patterns;
	private double[][] Testpatterns;
	private double[][] inputPatterns;
	private double[][] outputPatterns;
	private double[][] Defaulters;
	private String[][] Defaultersstring;
	private double[][] testoutputPatterns;
	private String[] testSentence;
	public BufferedWriter annTagedFile;
	private int[] cenIndex;
	double[][] gaussian;
	double[][] Testgaussian;
	double[] denominator;
	double[][] Feat;
	int[][] Output;
	int[] tags;
	String[] obtTags;
	double spread = 0.001;


	public void assignOutput(int patCount,int outNeuron){
			 computedOutput = new double[patCount][outNeuron];
	}
	public void assigntestOutput(int patCount,int outNeuron){
				 computedOutput = new double[testCount][outNeuron];
	}
	
	

	public void getGaussianMatrix(String a) throws IOException{
		OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(a), "UTF-8");
	        BufferedWriter fout1 = new BufferedWriter(writer1);
		annTagedFile = fout1;
		//p.println("inNeuron = " +inNeuron+"   patterns[0][0]="+patterns[0][0]+"patCount="+patCount);
		//annTagedFile.write("\n GAUSSIN MATRIX: ");
		//annTagedFile.write("\n");
		annTagedFile.write(""+patCount);
		annTagedFile.write("\n");
//		annTagedFile.write(patCount);		
		 gaussian = new double[patCount][patCount];
		for(int pat=0;pat<patCount;pat++){
			for(int n=0;n<patCount;n++){
				gaussian[pat][n]=getGaussianOutput(pat,n);
				annTagedFile.write(""+gaussian[pat][n]+" ");
			}
			annTagedFile.write("\n");
		}
		fout1.close();
		annTagedFile.close();
	}

	public void getTestGaussianMatrix(){
			//p.println("inNeuron = " +inNeuron+"   patterns[0][0]="+patterns[0][0]);
			
			 Testgaussian = new double[testCount][patCount];
			for(int pat=0;pat<testCount;pat++){
				for(int n=0;n<patCount;n++){
					Testgaussian[pat][n]=getTestGaussianOutput(pat,n);
					
				}
			}
			
			
			
	}
	public double getGaussianOutput(int npat,int cen){
			
			
			double out;
			double edist = 0;
			int feat = inNeuron;
			feat = 39;
			//DWT4 d1 = new DWT4(feat,2);
			//double[] localpat = new double[feat];
			//double[] smallpat = new double[(feat+1)/2];
			//double[][] dsmallpat = new double[patCount][(feat+1)/2];			
			//localpat = patterns[npat];
			//smallpat = d1.getwaveletTransform();
			//for(int i = 0 ; i < smallpat.length ; i ++){
			//	dsmallpat[npat][i] = smallpat[i];
				//p.println("dsmallpat["+npat+"]["+i+"]="+dsmallpat[npat][i]);
			//}
			//p.println("inNeuron="+inNeuron+"    patcount="+patCount );
			//p.println("inNeuron = " +inNeuron+"   patterns[0][0]="+patterns[npat][0]);
			//getInputpatterns();
			for(int d=feat ; d < 2*feat ; d++){
				//p.println("dsmallpatterns["+npat+"]["+d+"]="+dsmallpat[npat][d]);
				edist += Math.pow((patterns[npat][d] - patterns[cen][d])/spread,2.0);
			}
			edist = Math.sqrt(edist);
			out = transfer(edist);
			return out;
	}

	public double getTestGaussianOutput(int npat,int cen){
				//Testpatterns = patterns;
				double out;
				double edist = 0;
				int feat = inNeuron;
				feat = 39;
				//DWT4 d1 = new DWT4(feat,2);
				//double[] localpat = new double[feat];
				//double[] smallpat = new double[(feat+1)/2];
				//double[][] dsmallpat = new double[testCount][(feat+1)/2];			
				//localpat = Testpatterns[npat];
				//smallpat = d1.getwaveletTransform();
				//for(int i = 0 ; i < smallpat.length ; i ++){
				//	dsmallpat[npat][i] = smallpat[i];
				//}
				//p.println("inNeurons="+inNeuron+"   testcount="+testCount);
				for(int d=feat ; d < 2*feat ; d++){
					edist += Math.pow((Testpatterns[npat][d] - patterns[cen][d])/spread,2.0);
					//p.println("Testpatterns["+npat+"]["+d+"]="+Testpatterns[npat][d]+"        patterns["+cen+"]["+d+"]="+patterns[cen][d]);
				}
				edist = Math.sqrt(edist);
				//p.println("dist["+npat+"]="+edist);
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

	public double[] TestDenominator(int npat){
			denominator = new double[patCount];
			for(int i=0;i<patCount;i++){
				denominator[i] = Testgaussian[npat][i];
			}
			return denominator;
	}

	public void Output(int neuron){
		double[] denominator = new double[gaussian.length];
		computedOutput = new double[patCount][outNeuron];
		for(int i=0;i<patCount;i++){
			double sum1=0,sum=0;
			denominator = Denominator(i);
			for(int j=0;j<patCount;j++){
				sum1 += outputPatterns[j][neuron] * denominator[j];
				sum += denominator[j];
			}
			//p.println("sum1="+sum1+"sum="+sum+"patCount="+patCount+"neuron="+neuron);
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
		obtTags = new String[testCount];
		//p.println();
		//double max=0;
		//int position =0;
		double[][] temp = new double[outNeuron][2];
		//System.out.println("Test count="+testCount);
		for(int i = 0 ; i < testCount ; i++){
			for(int j = 0 ; j < outNeuron ; j++){
				temp[j][0] = 100*computedOutput[i][j];
				temp[j][1] = j;
				std[j] = new Student(j, Math.round((float)temp[j][0]));
			}
		//	p.println("Test Pattern = "+ i);
			newstd = m.getsorted(std);
			
		}
		//p.println();
		double max=0;
		int position =0;
		//System.out.println("Test count="+testCount);
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
				////System.out.println("Maximum value:"+max+"  position="+position);
				//if(computedOutput[pat][neu] > 0.50){
                            	//  p.println("     computed output["+pat+"]["+neu+"]= "+computedOutput[pat][neu] );
                                    tags[pat] = position ;
                                 //   p.println("tag["+pat+"]="+tags[pat]+"  Obtained Tag["+pat+"]="+tgset[position]);
                                    obtTags[pat] = tgset[position];
                                /*    if(neu>6)
                                        p.print(" "+1);
                                    else
                                        p.print(1);*/
					//p.println("computed output["+pat+"]["+neu+"]="+1);
                                 //  a[neu]=1
				//}
                                //else{
//					p.println("computed output["+pat+"]["+neu+"]="+0);
                            	  //p.println("       computed output["+pat+"]["+neu+"]= "+computedOutput[pat][neu] );
                                     
                                 /* if(neu>6)
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
			
			//p.print(words[i]+"  "+obtTags[i]);
			//annTagedFile.write("\n INPUT DATABASE: ");
			//annTagedFile.write("\n");
			annTagedFile.write("  "+words[i]+"  "+obtTags[i]);
		}
		annTagedFile.close();
	}
	
	public void finaldisplay(String file) throws IOException {
		OutputStreamWriter writer1 = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        	BufferedWriter annTagedFile = new BufferedWriter(writer1);
               // p.println();
		for(int i = 0 ; i < testCount ; i++){
			
			//p.print(words[i]+"  "+obtTags[i]);
			//annTagedFile.write("\n INPUT DATABASE: ");
			//annTagedFile.write("\n");
			annTagedFile.write("  "+words[i]+"  "+obtTags[i]);
		}
		annTagedFile.close();
	}
	
	
	
/*public void BtoD(long num){
    long rem;
              while(num > 0){
                                        rem = num % 10;
                                        num = num / 10;
                                        if(rem != 0 && rem != 1){
                                            //System.out.println("This is not a binary number.");
                                            //System.out.println("Please try once again.");
                                            System.exit(0);
                                        }
                                }
                                int i= Integer.parseInt(str,2);
                                //System.out.println("Decimal:="+ i);
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
	                    //System.out.println(textLine);
	                    String[] tokens = textLine.split("\\s|,");
	                   // //System.out.println("  "+(inputs+outlns));
	                    for (int j=0; j<(inputs+outlns); j++) {
	                        double patij = Double.parseDouble(tokens[j]);
	                        patterns[i][j] = patij;
	                      //  //System.out.println("pat["+i+"]["+j+"]="+patterns[i][j]);
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
	                	    ////System.out.println("cenIndex["+i+"]="+cenIndex[i]);
	                	}
					}
	               	br.close();
	            	fr.close();


	}

	public void readTestNet(String s1) throws IOException{

				/*String s1 = "0";
				s1 = JOptionPane.showInputDialog(" Please Enter the name of data file");*/
				////System.out.println("hi");
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
		                   // //System.out.println(textLine);

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
			//p.println("outputneurons="+outNeuron);
			Output(i);
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
			//p.println("CO["+pat+"]["+n+"]="+computedOutput[pat][n]+"outpatterns["+pat+"]["+n+"]="+outputPatterns[pat][n]);
			if(dround(computedOutput[pat][n],0) != dround(outputPatterns[pat][n],0)){
				//p.println("CO["+pat+"]["+n+"]="+computedOutput[pat][n]+"outpatterns["+pat+"]["+n+"]="+outputPatterns[pat][n]);
   			//if(dround(computedOutput[pat][n],0) != dround(testoutputPatterns[pat][n],0)){
				ch++;
				//p.println("ch="+ch);
				//if(ch == 2)
					//p.println("correct pattern is "+n);
				//if(ch == 1)
					//p.println("Identified Pattern is "+n);
			}
			else{
				//p.println("Computed Output["+pat+"]["+n+"]="+computedOutput[pat][n]);
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
		p.println("Accuracy="+(accuracy)*100+"%");
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
		Feat = t1.gettrainInput();
		patterns = Feat;
		int k = 0;
		//p.println("INPUT PATTERNS");
	//	p.println("number of input patterns = "+patterns.length);
		//for(int i = 0 ; i< patterns.length ; i++){
			//p.println();
			////System.out.println("OL[i]= "+Output[i].length);		
		//	for(int j = 0 ; j <patterns[0].length ; j++){
				////System.out.println("testoutpat["+i+"]["+j+"]= "+Output[i][j]);	
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
		//	p.println();
		//}
	}
	public double[][] getInputpatterns(){
		return patterns;
	}
	public void getOutput(TagGRNN t1){
		Output = t1.getOutputs();
		//System.out.println("OL= "+Output[0].length);
		double[][] testot = new double[Output.length][Output[0].length];
		
	//	testot = Output;
//		testoutputPatterns = (double) testot;

		for(int i = 0 ; i< patCount ; i++){
			p.println();
			////System.out.println("OL[i]= "+Output[i].length);		
			for(int j = 0 ; j < Output[0].length; j++){
				////System.out.println("testoutpat["+i+"]["+j+"]= "+Output[i][j]);	
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
	
	public void defaultercase(TagGRNN tt1, int[][] ld, String[][] lds){
		int testvalue = 0, flag = 0;
		String possibletag1, possibletag2, ptag ;
		String finaltag = "hi";
		for(int i = 1 ; i < 5 ; i++)   // Assuming maximum of 5 confusion words are there in test sentence
		{
			if(testvalue != ld[i][0] && flag == 0){
				testvalue = ld[i][0];
			//	p.println(" testvalue="+testvalue);
				ptag = obtTags[testvalue-1];
				possibletag1 = lds[i][0];
				possibletag2 = lds[i][1];
				finaltag = tt1.getdinfo(ptag, possibletag1, possibletag2);
			//	p.println("final tag = "+finaltag);
				flag = 1 ;
			}
		}
		obtTags[testvalue] = finaltag ;
		
		p.println("ot["+testvalue+"]="+obtTags[testvalue]);
		/*String[] dtag = new String[testCount];
		
		for(int i = 0 ; i < testCount ; i++)
			dtag[i] = tt1.getdinfo(id[][],ids);*/
	}
	public void getInfo(TagGRNN t1){
		int testCount1;
		double[][] testinputs;
		
				
		//inNeuron = t1.getinputNeurons();
		inNeuron = 39;
		tgset = t1.getTagset();
		words = t1.getWords();
		outNeuron = t1.getoutputNeurons();
		hidNeuron = t1.gethiddenNeurons();
		patCount = t1.getnumberPatterns();
		//testCount = patCount;
		testCount1 = t1.getnumbertestPatterns();
		testCount = testCount1;
		testSentence = t1.gettestSentence();
		words = testSentence;
		testinputs = t1.getTestF();
		//testinputs1 = testinputs;
		
		//p.println();
		//p.println(" GRNN3 test patterns: ");
		//p.println("**Test count = "+testCount1+"words count="+patCount);
		testCount = testCount1;
		/*for(int i = 0 ; i < testCount1 ; i++){
			for(int j = 0 ; j < 117 ; j++){
				p.print("   "+testinputs[i][j]);
			}
			p.println();
		}*/
		Testpatterns = testinputs;
		
		//testCount = t1.getnumbertestPatterns();
		//p.println("inneurons="+inNeuron+" outNeurons="+outNeuron+" hidNeurons="+hidNeuron+"  patcount="+patCount);
	}
	public void getDefaultersTag(TagGRNN t1){
		finalalaram = t1.getalaram();
		double[][] testinputs1;
		int localdefaulter[][] = new int[testCount][3];
		String localdefaulterstring[][] = new String[testCount][2];
		//p.println(" finalalaram ="+finalalaram);
		if(finalalaram != 0){
			testinputs1 = t1.getTest2F();
			Defaulters = t1.getDefaulter();
			Defaultersstring = t1.getDefaulterstring();
			//defaultercase();
			
		}
		for(int i = 0 ; i < testCount ; i++){
			if(i != 0){
				localdefaulter[i][0] = (int)Defaulters[i][0];
				localdefaulter[i][1] = (int)Defaulters[i][1];
				localdefaulter[i][2] = (int)Defaulters[i][2];
				localdefaulterstring[i][0] = Defaultersstring[i][0];
				localdefaulterstring[i][1] = Defaultersstring[i][1];				
			}
			/*for(int j = 0 ; j < 4 ; j++){
				p.print("   "+(int)Defaulters[i][j]);
				
			}*/
			//p.println();
			/*for(int k = 0 ; k < 2 ; k++){
				p.print("   "+Defaultersstring[i][k]);
			}
			p.println();*/
		}
		if(finalalaram != 0){
			
			defaultercase(t1,localdefaulter,localdefaulterstring);
			
		}
	}
	public static void main(String[] args) throws IOException{
		GRNN4 gr = new GRNN4();
		TagGRNN tg = new TagGRNN(args[0],  args[2], args[3], args[4], 1, "hi");
		gr.getInfo(tg);
		gr.getFeatures(tg);
		gr.getOutput(tg);
		//System.out.println("WELCOME TO GRNN4: ");
		//gr.readNet(args[0]);
		gr.getGaussianMatrix(args[5]);
//		gr.getOutput();
		//gr.readTestNet(args[1]);
		//gr.getTestGaussianMatrix();
//		gr.getTestOutput();
//		gr.checkOutput();
		
//		gr.display(args[4]);
//		gr.getDefaultersTag(tg);
//		gr.finaldisplay(args[4]);
	}

}
