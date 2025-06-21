// Java program to print DFS traversal from a given given graph 
import java.io.*; 
import java.util.*; 
  
// This class represents a directed graph using adjacency list 
// representation 
class Graph1 
{ 
    public int V;   // No. of vertices 
    private int[] marked;
    public int[] edgeTo;
    private int cardinality;
    // Array  of lists for Adjacency List Representation 
    private LinkedList<Integer> adj[]; 
    private Queue<Integer> augpathseq  = new LinkedList<Integer>();
  
    // Constructor 
    Graph1(int v) 
    { 
        V = v; 
        adj = new LinkedList[v]; 
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList(); 
    } 
  
    //Function to add an edge into the graph 
    void addEdge(int v, int w) 
    { 
        adj[v].add(w);  // Add w to v's list. 
    } 
  
    // A function used by DFS 
    void DFSUtil(int v,boolean visited[]) 
    { 
        // Mark the current node as visited and print it 
//        augpathseq = new LinkedList<Integer>();
        visited[v] = true; 
        System.out.print(v+" "); 
       //augpathseq.offer(0);
        augpathseq.add(v);
  	
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
           // System.out.println("n1="+n);
            if (!visited[n]) {
           	System.out.println("n="+n);
            //	System.out.println();
            //	augpathseq.add(0);
            	//augpathseq.add(n);
            	//System.out.println(augpathseq);
                DFSUtil(n, visited); 
            }
        }
        System.out.println(augpathseq); 
       // System.out.println(" elements "+augpathseq.element());         
    } 
  
    // The function to do DFS traversal. It uses recursive DFSUtil() 
    void DFS(int v) 
    { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        boolean visited[] = new boolean[V]; 
  
        // Call the recursive helper function to print DFS traversal 
        DFSUtil(v, visited); 
    } 
    
    int DFStil(int v,boolean visited[], int u, int source) 
    { 
        // Mark the current node as visited and print it 
        System.out.println();
        System.out.println("Reachable :");
        visited[v] = true; 
        int ind = 0;
        System.out.print(v+" "); 
  	Queue<Integer> p = new LinkedList<Integer>();
  	p.add(source);
  	p.add(v);
  	
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
           // System.out.println("n = "+n);
            if(n == u){
            	ind = 1;
            	p.add(n);
            	System.out.print(n+" "); 
            	
            	//return 0;
            	break;
            	//return 0;
            }
            else
           // System.out.println("n1="+n);
            if (!visited[n]) {
            //	System.out.println("n="+n);
            //	System.out.println();
            	p.add(n);
            	//System.out.println("p = "+p);
                DFStil(n, visited, u, source); 
            }
            ind = 0;
        } 
        if(ind == 1){
        	System.out.println("p = "+p);
        }
        return ind;
    } 
    int pathexist(int a, int b){
    	boolean visited[] = new boolean[V]; 
  	int index;
        // Call the recursive helper function to print DFS traversal 
        index = DFStil(a, visited, b, a); 
        return index;
    }
    
    public boolean isMarked(int a, int[] mrk){
    	for(int i = 0 ; i < mrk.length ; i++){
    		if(mrk[i] != a || mrk[a] != i) return true;
    	}
    	return false;
    }
    public  void getMatching1(){
    	int vertex = V;
    	int index = 1;
    	int ind = 0;
    	int v = 0;
    	int flag = 1;
    	int[] mat = new int[vertex];
    	int[] l1 = new int[vertex];    	
    	int[] marked = new int[vertex];
    	for(int i = 0 ; i < vertex ; i++){
    		mat[i] = -1;
    		marked[i] = -1;
    	}
    	for(int j = 0 ; j < vertex ; j++){
    		ind = 0;
    		for (int j1 = 0; j1 < adj[j].size(); j1++) {
	                	l1[ind] = adj[j].get(j1);
        		       System.out.print("   "+adj[j].get(j1));
        		       ind++;
	         }
	         System.out.println();
	         System.out.println();
    		//DFS(j);
    		System.out.println("DFS["+j+"]="+augpathseq);
    		int u = augpathseq.remove();
    		if(augpathseq.size() > 1)
		  		v = augpathseq.remove();
    		for(int k = 0 ; k < vertex ; k++){
    			if(u != mat[k] ){
    				if(isMarked(u,marked)){
    					if(isMarked(v,marked)){
	    					System.out.println("new match ="+u);
						index = 0;
					}
				}
    			}
    			else{
    				index = 1;
    				break;
    			}
    			
    		}
    		if(index == 0 ){
    			//flag = augpathexist(u,mat);
    			System.out.println("flag = "+flag +"   mat["+j+"]="+mat[j]);
    			//if(flag == 0){
	    			mat[v] = u;
	    			mat[u] = v;
	    			System.out.println(" ***mat["+v+"]=   "+u+"   mat["+u+"]="+v);
    			//}
    		}
    		else if(u != j){
    			marked[j]=u;
    			marked[u]=j;
    			System.out.println(" marked["+j+"]=   "+marked[j]);    			
    		}
    	}
    	for(int l = 0 ; l < vertex ; l++){
    		System.out.print (" mat["+l+"]=   "+mat[l]);
    	}
    }
    public  void getMatching(int[] mate, int card){
    	int vertex = V;
    //	int augpatexist = aug;
         cardinality = 0;
    	int index = 1;
    	int count = 0;
    	//mate = new int[vertex];
    	//cardinality = card ;
    	// find one endpoint t in alternating path
    	System.out.println("card="+card);
    	while ( cardinality <= card && index != 0) {
    		index = augpathexist(0,mate);
	        int t = -1;
        	for (int v = 0; v < vertex ; v++) {
	            if (!isMatched(v,mate) && edgeTo[v] != -1) {
        	          t = v;
        	          System.out.println("inside index ="+index);
                	  break;
	             }
        	}
        	
        	  // update the matching according to alternating path in edgeTo[] array
            for (int v = t; v != -1 || count < 10; v = edgeTo[edgeTo[v]]) {
                int w = edgeTo[v];
                mate[v] = w;
                mate[w] = v;
                System.out.println("mate["+w+"]="+mate[w]);
                if(count < 8)
	                count++;	
	        else
	        	break;
            }
            cardinality++;
            System.out.println("cardinality = "+cardinality+"  index ="+index);
        
        }
    	//augpatexist = g.augpathexist(0, mate);
    	//System.out.println("Aug path exist :"+augpatexist);
    }
    
    boolean isMatched(int b, int[] mt){
    	return mt[b] != -1;
    }
    
    
    int augpathexist(int a, int[] mat){
    	marked = new int[V];
    	edgeTo = new int[V];
    	int[] l = new 	int[V];
    	int index=0;
    	Queue<Integer> matching = new LinkedList<Integer>();
    	System.out.println("Matching : ");
    	System.out.println(matching);
    	System.out.println(augpathseq);
    	for(int i = 0; i < V ;i++){
    		if(adj[i].size() > 0){
    			index = 0;
    			System.out.print("Vertex " + i + " is connected to: ");
	                for (int j = 0; j < adj[i].size(); j++) {
	                	l[index] = adj[i].get(j);
        		       System.out.print(adj[i].get(j) + " l["+index+"]="+l[index]);
        		       index++;
	                }
        	        System.out.println();
    		}
	}    	
    	for (int v = 0; v < V; v++) {
            if (!isMatched(v,mat)) {
                matching.add(v);
                System.out.println("match vertex ="+v);
                marked[v] = 1;
            }
        }
        // run DFS or BFS
        while (!matching.isEmpty()) {
            int v = matching.remove();
            System.out.println("v="+v+"  adj["+v+"].size="+adj[v].size());
            for (int w =0 ; w < adj[v].size() ; w++) {
		System.out.println("w="+w+"  marked["+w+"]="+marked[w]);
                // either (1) forward edge not in matching or (2) backward edge in matching
                if (marked[w] != 0) {
                    edgeTo[w] = v;
                    marked[w] = 1;
                    System.out.println("marked["+w+"]="+marked[w]);
                    System.out.println("interim Matching : ");
		    System.out.println(matching);
                    if (!isMatched(w, mat)) return 1;
                    matching.add(w);
                    System.out.println("match next vertex ="+w);
                }
            }
        }
        System.out.println("iteration Matching : ");
    	System.out.println(matching);
    	
    	return 0;
    }
  
    public static void main(String args[]) 
    { 
        Graph1 g1 = new Graph1(5); 
  
        g1.addEdge(0, 2); 
	g1.addEdge(0, 3); 
	g1.addEdge(0, 4); 
	g1.addEdge(1, 2); 
	g1.addEdge(1, 3); 
	g1.addEdge(1, 4);
	g1.addEdge(2,0); 
	g1.addEdge(3,0); 
	g1.addEdge(4,0); 
	g1.addEdge(2,1); 
	g1.addEdge(3,1); 
	g1.addEdge(4,1);
  
        System.out.println("Following is Depth First Traversal "+ 
                           "(starting from vertex 2)"); 
  
        g1.DFS(0); 
    } 
} 