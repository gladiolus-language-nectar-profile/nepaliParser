import java.io.*;
public class DWT4{
     double[] u;
     double[] v;
     double[] du;
     double[] dv;
     double[] f;
     double[] g;
     double[] con;
     double[] WavTran;
     double[] series1;
     double[] series2;
     double[] series3;
     double[] series4;
     double max = 0;
     int n,p;


     public DWT4(int l , int m){
     	if(l%2 != 0){
     		n = l+1;
     	}
     	else{
		  n = l;
	}
	  p = m;
	  u = new double[n];
          v = new double[n];
          du = new double[n];
          dv = new double[n];
          f = new double[n];
          g = new double[n];
          WavTran = new double[n/2];
	      series1 = new double[p*n];
	      series2 = new double[p*n];
	      series3 = new double[p*n];
	      series4 = new double[p*n];
     
     }
     public DWT4(int l,int m, double[] f1){
    /* for(int i = 0 ; i < f1.length ; i++){
	     	System.out.println("f1["+i+"]="+f1[i]);
	}*/
	Function(f1);
	/*if(l%2 != 0){
     		n = l+1;
     	}
     	else{
		  n = l;
	}*/
	  n = l;
	  p = m;
	  u = new double[n];
          v = new double[n];
          du = new double[n];
          dv = new double[n];
          f = new double[n];
          g = new double[n];
        //  System.out.println("n/2 ="+(n/2));
          WavTran = new double[n/2];
	      series1 = new double[p*n];
	      series2 = new double[p*n];
	      series3 = new double[p*n];
	      series4 = new double[p*n];
	      getdwt(l);
	      WaveletTransform(f1);
	
     }
     
     public void getdwt(int p){
     	ScalingSeq(p);
       	WaveletSeq(p);
       	DualScalingSeq(p);
       	DualWaveletSeq(p);
       //	Function(q);
       	//WaveletTransform();
      // 	for(int i = 0 ; i < WavTran.length ; i++){
	     //	System.out.println("q["+i+"]="+q[i]);
	//}
     	
     }
     public void ScalingSeq(int k){
          double fact = 1/Math.sqrt(2);
          u[0]=fact*(1+Math.sqrt(3))/4;
          u[1]=fact*(3+Math.sqrt(3))/4;
          u[2]=fact*(3-Math.sqrt(3))/4;
          u[3]=fact*(1-Math.sqrt(3))/4;
         // System.out.println("Scaling Sequence :");
          /*for(int i=0;i<k;i++)
               System.out.println(" "+u[i]);
		  System.out.println("Average="+(u[0]+u[1]+u[2]+u[3])/4);*/
     }
     public void WaveletSeq(int k){
          double fact = 1/Math.sqrt(2);
          v[0]=fact*(-3-Math.sqrt(3))/4;
          v[1]=fact*(1+Math.sqrt(3))/4;
          v[k-2]=fact*(-1+Math.sqrt(3))/4;
          v[k-1]=fact*(3-Math.sqrt(3))/4;
          //System.out.println("Wavelet Sequence :"+k);
         /* for(int i=0;i<k;i++)
               System.out.println(" v["+i+"]"+v[i]);*/
    }
    public double WaveletFun(int x){
		if(x<0) return v[n-x];
			else if(x < 2) return v[x];
			else if(x == (n-2)) return v[2];
			else if(x == (n-1)) return v[3];
			else return v[x-n];
   	    }

    public double ScalingFun(int x){
		if(x<0) return u[n-x];
			else if(x < 4) return u[x];
			else return u[x-n];
     }

    public void DualScalingSeq(int k){
	  du[0] = u[0];
	  for(int i=1;i<k;i++)
		du[i]=u[k-i];
          //System.out.println("Dual Scaling Sequence :");

     }
     public void DualWaveletSeq(int k){
	  dv[0]=v[0];
	  for(int i=1;i<k;i++)
		dv[i]=v[k-i];

     }
     public void Function(double[] function){
		 f = function;
		/* for(int i = 0 ; i < n ; i++){
		     System.out.println("f["+i+"]="+f[i]);
		}*/

     }
     public double Convolution(double[] a, double[] b,int x){

          double sum = 0;
          for(int i=0;i<a.length;i++){
	        if((x-i)>=0)// && (x-i)<4)
                    sum = sum + a[x - i]*b[i];
		else
		    sum = sum + a[a.length + x - i]*b[i];

          }
          return sum;
     }
     public void  WaveletTransform(double[] f2){
          
          con = new double[2*n];
          for(int i=0;i<n;i++){
               con[i] = Convolution(f2,dv,i);
               con[i+n] = Convolution(f2,du,i);
          }
        //  System.out.println("n="+n);
          double[] tempwt = new double[n/2];
          //WavTran = new double[n/2];
         tempwt = DownSampling(con);
       //  WavTran = tempwt;
          for(int i = 0 ; i < (n/2) ; i++){
	     //	System.out.println("wavTran["+i+"]="+tempwt[i]);
	     	WavTran[i] = tempwt[i];
	}
     }
     public double[] getwaveletTransform(){
     	//for(int i = 0 ; i < WavTran.length ; i++){
	     //	System.out.println("wavTran["+i+"]="+WavTran[i]);
	//}
     	return this.WavTran;
     }
     public void Reconstruction(){
          double[] upsa = new double[n];
          double[] upsb = new double[n];
          double[] a = new double[n/2];
          double[] b = new double[n/2];

          for(int i=0;i<n/2;i++){

               a[i]=WavTran[i];

               b[i]=WavTran[i+n/2];

          }
          upsa = UpSampling(a);
          upsb = UpSampling(b);
          for(int i=0;i<n;i++){

               con[i] = Convolution(upsa,v,i);

               con[i+n] = Convolution(upsb,u,i);
               g[i] = con[i]+con[i+n];
          }

     }
     public double[] DownSampling(double[] a){
	  /*for(int i=0;i<a.length;i++)
		System.out.print(" hi its downsampling");*/
	  double[] b = new double[n];
          for(int i=0;i<n/2;i++){
		b[i]=a[2*i];
		b[i+n/2]=a[n+(2*i)];
          }
          return b;
     }
     public double[] UpSampling(double[] a){
	  double[] c = new double[2*a.length];
	  double[] d = new double[2*a.length];
	  c[0]=a[0];
	  int j=1;
	  for(int i=0;i< 2*a.length-1;i++){
	  	if(i%2 != 0){
			c[i+1]=a[j];
			j++;
		  }
	  }
	  for(int k=0;k<2*a.length-1;k++){
		double temp = c[k];
		d[k+1] = temp;
	  }


          return c;
     }

    public void Expansion(){
		//System.out.println("hi its expansion");
		double[] x = new double[2*n];
		double[] y1 = new double[2*n];
		double[] y2 = new double[2*n];
		double[] y = new double[4*n];
		x = con;
		y = UpSampling(x);

		for(int i=0;i<y.length;i++){
			if(i<y.length/2){
			  y1[i]=y[i];

			}
			else{ y2[i - 2*n] = y[i];
		//		System.out.println("y2["+(i-2*n)+"]="+y2[i - 2*n]);
			}
		}
                double[] ans = new double[y1.length];
                int[] ans1 = new int[y1.length];
		for(int i=0;i<1;i++){
		        v = new double[y1.length];
				WaveletSeq(y1.length);
		        u = new double[y1.length];
			ScalingSeq(y1.length);
			for(int j=0;j<y1.length;j++){
				series1[j] = Convolution(y1,v,j);
				series2[j] = Convolution(y1,u,j);
				series3[j]=Convolution(y2,v,j);
				series4[j]=Convolution(y2,u,j);
				System.out.println("y1.length="+y1.length);
                             
                               ans[j]=Math.abs(series1[j]+series3[j])*(Math.sqrt(2));
                               ans1[j]=(int)dround(ans[j],0);
			}
		}
                convert2D(ans1,64,64); 

     }
    public static void convert2D(int[] pixel,int w, int h){
		int[][] pixel2D;
		int k=0;
		pixel2D=new int[h][w];
                System.out.println("w="+w+"h="+h);
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				pixel2D[i][j]=pixel[k++];
                        }
                }
                for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				System.out.print(" "+pixel2D[i][j]);
                        }
                        System.out.println();
                }
		//return(pixel2D);
    }
        
     private void Display(){
        /* for(int i=0;i<n;i++)
               System.out.println(" "+f[i]);
          System.out.println("Convolution of a functin with dv and du resp");
          for(int i = 0 ; i < n ; i++)
               System.out.println("  "+con[i]+"  "+con[i + n]);
          System.out.println("Wavelets Transforms :");*/
          for(int i=0;i<n/2;i++){
              System.out.println(WavTran[i]);
    
               //System.out.println(" "+WavTran[i]+" "+WavTran[i+n/2]);
          }
       /*   System.out.println("Reconstruction : ");
          for(int i=0;i<n;i++)
               System.out.println(" "+g[i]);*/
         /* System.out.println("Series Expansion up to "+p+" level");

   		double MaxWtran = 0;
		for(int i=0;i<n/2;i++){
			  if(WavTran[i+n/2] > MaxWtran)
				MaxWtran = WavTran[i + n/2];
				System.out.println("Wtransform max = "+MaxWtran);
		 }
		double MaxLL = 0;
   		for(int i=0;i<n;i++){
			 if(f[i] > MaxLL)
				MaxLL = f[i];
			System.out.println("MaxLL max = "+MaxLL);
		}

		System.out.println("ratio = "+MaxWtran/MaxLL);*/
	 // double value = 0.0;*/
      int q = 0;
      for(int j=0;j<p*n;j++){
		//q=q+1;
	//	 value = (series1[j]+series3[j])*(Math.sqrt(2));

		//System.out.println(" "+(series1[j]+series3[j])*(Math.sqrt(2)));
                double ans=Math.abs(series1[j]+series3[j])*(Math.sqrt(2));
                int ans1=(int)dround(ans,0);
               // double a[][] = new double[64][64];
              //  System.out.println(" "+ans1);
                //for(int l=0;l<64;l++){
                  //  for(int m=0;m<64;m++){
                   //     a[q][j]=ans1;
                    //}
                //}
                // for(int l=0;l<64;l++){
                  //  for(int m=0;m<64;m++){
                    //    System.out.println(" "+a[l][m]);
              //      }
                //}
		//  System.out.println(" "+series4[j]*1.45);//(MaxWtran/MaxLL));
			//System.out.println("q= "+q);
			//if(q%2 == 0)	
			  // System.out.println(" "+(series1[j]+series3[j])*(-Math.sqrt(2)));
			//else
			  // System.out.println(" "+(series1[j]+series3[j])*(Math.sqrt(2)));
            //System.out.println(" "+(series1[j]+series2[j]+series3[j]+series4[j])*Math.sqrt(2));	
		   q++;
	  }
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
	 /*private double Gaussian(double s){
	   
     	return (Math.exp(-(s*s)/2));

	 }*/
     public static void main(String[] args) {
		 final int SAMPLES;
		 try{
			FileReader fr = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(fr);
			SAMPLES = Integer.parseInt(br.readLine());
		 	double[] function = new double[SAMPLES];
		 	for(int i=0;i<SAMPLES;i++)
		 		function[i] = Double.parseDouble(br.readLine());

          	DWT4 d = new DWT4(SAMPLES,2);
          	d.ScalingSeq(SAMPLES);
          	d.WaveletSeq(SAMPLES);
          	d.DualScalingSeq(SAMPLES);
          	d.DualWaveletSeq(SAMPLES);
          	d.Function(function);
          	//d.WaveletTransform();
          	//d.Reconstruction();
          	d.Display();
	    //  	d.Expansion();
          	//d.Display();
                }
		  catch(IOException ie){};

     }

}
