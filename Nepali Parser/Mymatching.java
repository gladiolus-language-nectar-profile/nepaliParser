// A Java program to find maximal 
// Bipartite matching. 
import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.LinkedList; 
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math.*;

class Mymatching 
{

	 static  int M ;//= 2; 
	 static  int N ;//= 3;
	 static  int edges;// = 6;
	 private int cardinality; 
	 int[] mate;
	 int[] finalMate;
	 int V = M + N;
	 Boolean[] marked;
	 private int[] edgeTo;  
	 static int[][] permutation ;
	 static int[] per= new int[40320];
	 static int t = 0;
	 Mymatching(){};
	 Mymatching(int V1, int M1, int N1){
	 	M = M1;
	 	N = N1;
	 	V=V1;
	 }
	 class Graph{
	 	LinkedList<Integer> adjListArray[]; 
	 	int V;
	 	Graph(int V){
	 		this.V = V;
	 		//V = M +N;
	 		adjListArray = new LinkedList[V]; 
  
  		      // Create a new list for each vertex 
		        // such that adjacent nodes can be stored 
  
		        for(int i = 0; i < V ; i++){ 
		            adjListArray[i] = new LinkedList<Integer>(); 
		           // System.out.println("adj["+i+"]="+adjListArray[i]);
		        } 
	 		
	  	           /* Queue<Integer> queue = new Queue<Integer>();
	                    for (int v = 0; v < V; v++) {
		 	           if (!isMatched(v)) {
		        	        queue.add(v);
	        	        	marked[v] = true;
			            }
       			    }*/
 		}
	 	
	 }  
	 
	 private static void getshuffledadj(LinkedList<Integer> mylist[], int a){
	 	//Collections.shuffle(mylist[]);
	 	System.out.println("Shuffled graph :");
	 	
	 	for(int v = 0 ; v < a; v++){
	 		Collections.shuffle(mylist[v]);
		 	 for (int l : mylist[v]) {
		 	 	System.out.println("  "+l);
		 	 }
		}
	 }
	 void Matching(int V, Graph g){
	 	mate = new int[V];
	 	System.out.println("number of vertices are :"+V);
		for (int v = 0; v < V; v++){
		      mate[v] = -1;
	 	      System.out.println(v+"is not matched :");
		}
		int index = 0;
		while(hasaugmentingPath(g, mate, index)){
			 int t = -1;
		         for (int v = 0; v < V; v++) {
	        	         if (!isMatched(v,mate) && edgeTo[v] != -1) {
	                	    t = v;
	                	    System.out.println("t="+t);
	        	            break;
        	        	}
            		}
            		// update the matching according to alternating path in edgeTo[] array
		        for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
		                int w = edgeTo[v];
		                mate[v] = w;
		                mate[w] = v;
		           //     System.out.print(" mate["+v+"]=  "+mate[v]+"  mate["+w+"]=    "+mate[w]);
	                }
            		cardinality++;
            		index++;
		}
		finalMate = mate;
		//}
	 }
	 boolean isMatched(int v,int[] ma) throws ArrayIndexOutOfBoundsException {
	 	//System.out.println(v+" **is not matched :");
	 	//try{
		 	return ma[v] != -1; 
	 	//}catch(ArrayIndexOutOfBoundsException e) {}
	 }
	 
	 boolean hasaugmentingPath(Graph g, int[] mat, int ind){
	 	//int V  = 0;
	 	//this.V = V;
//	 	this.V = g.V;
		if(ind == 0)
			marked = new Boolean[V];
		edgeTo = new int[V];
	        for (int v = 0; v < V; v++)
        	    edgeTo[v] = -1;
        	    
        	// breadth-first search (starting from all unmatched vertices on one side of bipartition)
        	V = 11;
        	System.out.println();
		System.out.println("in augpath V ="+V);
	 	Queue<Integer> queue = new LinkedList<Integer>();
	        for (int v = 0; v < V; v++) {
		      if ( !isMatched(v,mat)) {
		     	        queue.add(v);
	                	marked[v] = false;
	                	System.out.println("Matching queue :"+queue+"   marked["+v+"]="+marked[v]);
	                	//return true;
		      }
		      else if(ind != 0)
			      marked[v] = true;
       		}
       		
		while (!queue.isEmpty()) {
	            int v = queue.remove();
	            System.out.println("Matching empty queue for "+v+" is:"+queue);
	            for (int w : g.adjListArray[v]) {
		        System.out.println("w="+w+"  marked["+w+"]="+marked[w]);
        	        // either (1) forward edge not in matching or (2) backward edge in matching
	                if (isResidualGraphEdge(v, w) && !marked[w]) {
	                    edgeTo[w] = v;
	                    marked[w] = true;
	                    System.out.println("Matching empty queue :"+queue+"    marked["+w+"]="+marked[w]);
	                    if (!isMatched(w, mat)) return true;
		                    queue.add(w);
                        }
            	    }
        	}

       		//System.out.println("Queue = "+queue);
	 	return false;
	 }
	 
	 private boolean isResidualGraphEdge(int a, int b){
	 	//System.out.println("mate["+a+"]="+mate[a]+"  b="+b);
	 	if (mate[a] != b) return true;
	 	if (mate[a] == b) return true;
	 	return false;
	 }
	 static void printGraph(Graph graph) 
    	{        
	        for(int v = 0; v < graph.V; v++) 
	        { 
	            System.out.println("Adjacency list of vertex "+ v); 
	            System.out.print("head"); 
	            for(Integer pCrawl: graph.adjListArray[v]){ 
	                System.out.print(" -> "+pCrawl); 
	            } 
        	    System.out.println("\n"); 
       		}	 
    	} 
	 
	  // Adds an edge to an undirected graph 
    static void addEdge(Graph graph, int src, int dest) 
    { 
        // Add an edge from src to dest.  
        graph.adjListArray[src].add(dest); 
          
        // Since graph is undirected, add an edge from dest 
        // to src also 
        graph.adjListArray[dest].add(src); 
    } 
    
    public int mate(int v) {
     //   validate(v);
        return mate[v];
    }
    
    public int[] getmatch(){
    	System.out.println("final matching:");
    	for(int i = 0 ; i < V; i++){
    		System.out.println("finalMate["+i+"]="+finalMate[i]);
    	}
    	return finalMate;
    }   
    
     private static void permute(int[] str, int l, int r, int fact, int index) 
    { 
    	int[] perm = new int[fact*(r+1)];
    	int[][] temp = new int[fact][r+1];  
    	//System.out.println("fact="+fact);  	
    	//int count = index;
    	//if(index == 0)
	  //       count = 0;
	  int k = 0;
	 // int t = 0;
        if (l == r) {
	    
            for(int i=0 ; i < str.length ; i++){
            	/*if(i>0){
            		System.out.println("initial index="+index);
	            	//if(str[i] != str[i-1]) index++;
	        }*/
            	//System.out.print("  "+str[i]); 
		perm[index] = str[i];
		//System.out.println("");
		//if(t < i)
		try{
//		System.out.println("per["+t+"]="+per[t]+"  str["+i+"]="+str[i]);
			per[t]=str[i];
			//System.out.print("  per["+t+"]="+per[t]);
			temp[index][k] = per[t];
		}
		catch( ArrayIndexOutOfBoundsException e) {}
		t++;
		//for(int k=0;k<str.length;k++){
			//System.out.println("t = "+t+"   index = "+index);
		/*	if(t == index)
			   index++;
			else
			   t = index;*/
			
			//temp[index][k] = perm[index];
			//System.out.print("  temp["+index+"]["+k+"]="+temp[index][k]);
			k++;
		//}
		
		//index++;
		//System.out.print("  perm["+index+"]="+perm[index]);
		//index++;
            	
            }
            //count++;
           // System.out.println();
            
        }
        else
        { 
            for (int i = l; i <= r; i++) 
            { 
                str = swap(str,l,i); 
                permute(str, l+1, r, fact, index++); 
                //count++;
//                if(i == r) index++;
                str = swap(str,l,i); 
            //    System.out.println("index="+index);
         //       count++;
            } 
            //count++;
        } 
       // permutation = temp;
       // return temp;
       
    } 
  
    /** 
     * Swap Characters at position 
     * @param a string value 
     * @param i position 1 
     * @param j position 2 
     * @return swapped string 
     */
    public static int[] swap(int[] a, int i, int j) 
    { 
        int temp; 
        int[] charArray = a; 
        temp = charArray[i]; 
        charArray[i] = charArray[j]; 
        charArray[j] = temp; 
        return charArray; 
    } 

    public static int factorial( int n ) {

        // Make sure that the input argument is positive

        // Use simple look to compute factorial....

        int factorial = 1;
        for(int i = 2; i <= n; i++) 
            factorial *= i;               

        return factorial;
    }
    
    public static void main1(int V, int N, int[] B) 
    { 
        // create the graph given in above figure 
	      // int V = 5; 
	      //  int N = 3;
	      int minindex = Math.min(V-N,N);
	        System.out.println("number of vertices are "+V);
	        int[] finmate;
	        int maxmatchEdges = 0;
	        int fac = 15000;//factorial(N);
	        int[][] permutation = new int[fac][N];
	        int[][] prm = new int[400][N];
	        int[] B1 = new int[fac];
	        //int 
	       // for()
	       // int B[] = {2,3,4};
	    //    B = [2,3,4];
	    	//int n = B.length;
	    	for(int i = 0 ; i < fac ; i++){
	    		if(i < N ){
	    			B1[i] = B[i];
	    		}
	    			B1[i] = 0;
	    		//System.out.print("  "+B1[i]);
		}
		//System.out.println();
	        permute(B, 0,N-1, fac, 0);
	        int k=0;
	        try{
		        for(int l = 0 ; l < fac ; l++){
			        for(int l1 = 0 ; l1 < N  ; l1++){	
			        
			        	permutation[l][l1] = per[k];        	        
			        	System.out.print("     My matching permutation["+l+"]["+l1+"]="+permutation[l][l1]);
			        	k++;
			        }
			        System.out.println();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		
		
		int count=0;
		int flag = 0;
		int q = 0;
		int multiplier = 0;
	        for(int p = 0 ; p < fac ; p++ ){
	        	//for(int q = 0 ; q < N ; q++){
	        		//q=0;
	        		//count = 0;
	        		//if(q < N){
//	        			System.out.println("q="+q+"      count="+count+"   permup["+p+"]["+q+"]="+permutation[p][q]+"   B["+q+"]="+B[q]);
		        		if(permutation[p][q] == B[q]){
		        			if(count < 50){
			        			count++;
			        			
			        			//q++;
			        			//System.out.println("**count = "+count+"    multi="+(50*multiplier + count)+" prm="+prm[50*multiplier + count][r]);
		        				for(int r = 0 ; r < N ; r++){
			        				prm[50*multiplier + count][r] = permutation[p][r];
			        				System.out.println("**count = "+count+"    multi="+(50*multiplier + count)+" prm="+prm[50*multiplier + count][r]);
			        			}
		        			}
	        				
	        			}
	        			if(count == 50 && q<= N){
	        				q++;
	        				count = 0;
	        				multiplier++;
	        			}
	        		//	q++;
	        		//}
	        		//else{
	        		//	q = 0;
	        		//}
	        		//q++;
	        		//count=0;
	        		//}
	        		//q = 0;
	        		
	        	//}
	        }

	        for(int l = 0 ; l < fac ; l++){
	        	if((0 <= l && l< 400) || ((fac-400)<=l && l<fac)){
	        		System.out.println(" New permutation l = "+l);
	                 	Mymatching m = new Mymatching(); 
  			        Mymatching.Graph g = m.new Graph(V);
  			        maxmatchEdges = 0;
	        		for(int m2 = 0 ; m2 < N ; m2++){
		        	  System.out.println("prmutation["+l+"]["+m2+"] : "+prm[l][m2]);
			      	  addEdge(g,0,prm[l][m2]);
			      	  addEdge(g,1,prm[l][m2]);
				}
			        m.Matching(V,g);	
			        finmate = m.getmatch();
			 	printGraph(g);	
			 	System.out.println("V="+V);
			 	 System.out.print("Max matching: ");
	        		for (int v = 0; v < V; v++) {
			        	if(finmate[v] != -1){
			        		System.out.println("maxmatedg="+maxmatchEdges);
			        		maxmatchEdges++;
	        			}
			    
	        		}
			        Mymatching.Graph g1 = m.new Graph(maxmatchEdges);
	        		for (int v = 0; v < V; v++) {
			        	if(finmate[v] != -1){
			        		System.out.println("v="+v+"   finmate["+v+"]="+finmate[v]+"  minindex= "+minindex);
			        		try{
				        		if(v < (minindex-1) )
						        	addEdge(g1,v,finmate[v]);
//					        else
						}
						catch(ArrayIndexOutOfBoundsException e){}
					        	
				        }
			        }
			        System.out.println();
			        printGraph(g1);	        
			//}
			}
	        
	        }
	        
	        
	       /* Mymatching m = new Mymatching(); 
	        Mymatching.Graph g = m.new Graph(V);
	        addEdge(g,0,2);
	 	addEdge(g,0,3);
	 	addEdge(g,0,4);
	 	addEdge(g,1,2);
	 	addEdge(g,1,3);
	 	addEdge(g,1,4);
	 	m.Matching(V, g);
	 	finmate = m.getmatch();
	 	printGraph(g);
	 	
	 	
	        System.out.print("Max matching: ");
	        for (int v = 0; v < V; v++) {
	        	if(finmate[v] != -1){
	        		maxmatchEdges++;
	        	}
	        //	System.out.print("    "+finmate[v]);  
	        }
	        Mymatching.Graph g1 = m.new Graph(maxmatchEdges);
	        for (int v = 0; v < V; v++) {
	        	if(finmate[v] != -1){
		        	addEdge(g1,v,finmate[v]);
		        }
	        }
	        System.out.println();
	        printGraph(g1);*/
	        //getshuffledadj(g.adjListArray,  V);
	        
	      /*  for (int v = 0; v < V; v++) {
	            int w = m.mate(v);
	            System.out.println("w="+w);
	            if (m.isMatched(v,mate) && v < w)  // print each edge only once
	                System.out.print(v + "-" + w + " ");
        	}*/
        }
    
    public static void main(String args[]) 
    { 
        // create the graph given in above figure 
	        int V = 5; 
	        int N = 3;
	        int[] finmate;
	        int maxmatchEdges = 0;
	        int fac = factorial(N);
	        int[][] permutation = new int[fac][N];
	        int B[] = {2,3,4};
	    //    B = [2,3,4];
	    	//int n = B.length;
	    	for(int i = 0 ; i < N ; i++)
		    	System.out.print("  "+B[i]);
		System.out.println();
	        permute(B, 0,N-1, fac, 0);
	        int k=0;
	        for(int l = 0 ; l < fac ; l++){
		        for(int l1 = 0 ; l1 < N  ; l1++){	
		        	permutation[l][l1] = per[k];        	        
		        	//System.out.print("     permutation["+l+"]["+l1+"]="+permutation[l][l1]);
		        	k++;
		        }
		        System.out.println();
		}
	        for(int l = 0 ; l < fac ; l++){
	        	if(0 <= l && l< 10 || (fac-10)<=l && l<fac){
	        		System.out.println("l="+l);
	                 	Mymatching m = new Mymatching(); 
	  		        Mymatching.Graph g = m.new Graph(V);
		        	for(int m2 = 0 ; m2 < N ; m2++){
	        	
				        addEdge(g,0,permutation[l][m2]);
				        addEdge(g,1,permutation[l][m2]);
				}
			        m.Matching(V,g);	
			        finmate = m.getmatch();
			 	printGraph(g);	
			 	 System.out.print("Max matching: ");
	        		for (int v = 0; v < V; v++) {
			        	if(finmate[v] != -1){
			        		maxmatchEdges++;
	        			}
			    
	        		}
			        Mymatching.Graph g1 = m.new Graph(maxmatchEdges);
	        		for (int v = 0; v < V; v++) {
			        	if(finmate[v] != -1){
				        	addEdge(g1,v,finmate[v]);
				        }
			        }
			        System.out.println();
			        printGraph(g1);	        
			//}
			}
	        
	        }
	        
	        
	       /* Mymatching m = new Mymatching(); 
	        Mymatching.Graph g = m.new Graph(V);
	        addEdge(g,0,2);
	 	addEdge(g,0,3);
	 	addEdge(g,0,4);
	 	addEdge(g,1,2);
	 	addEdge(g,1,3);
	 	addEdge(g,1,4);
	 	m.Matching(V, g);
	 	finmate = m.getmatch();
	 	printGraph(g);
	 	
	 	
	        System.out.print("Max matching: ");
	        for (int v = 0; v < V; v++) {
	        	if(finmate[v] != -1){
	        		maxmatchEdges++;
	        	}
	        //	System.out.print("    "+finmate[v]);  
	        }
	        Mymatching.Graph g1 = m.new Graph(maxmatchEdges);
	        for (int v = 0; v < V; v++) {
	        	if(finmate[v] != -1){
		        	addEdge(g1,v,finmate[v]);
		        }
	        }
	        System.out.println();
	        printGraph(g1);*/
	        //getshuffledadj(g.adjListArray,  V);
	        
	      /*  for (int v = 0; v < V; v++) {
	            int w = m.mate(v);
	            System.out.println("w="+w);
	            if (m.isMatched(v,mate) && v < w)  // print each edge only once
	                System.out.print(v + "-" + w + " ");
        	}*/
        }
}