//An Iterative Java program to do DFS traversal from 
//a given source vertex. DFS(int s) traverses vertices 
//reachable from s. 

import java.util.*; 

public class GFG 
{ 
	// This class represents a directed graph using adjacency 
	// list representation 	
	    private static final int UNMATCHED = -1;

	    private static final int V = 1;                 // number of vertices in the graph
	   // private BipartiteX bipartition;      // the bipartition
	    private static int cardinality;             // cardinality of current matching
	    private static int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
		                                        //         = -1 if v is not in current matching
	    private static boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
	    private static boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
	    private static int[] edgeTo;                // edgeTo[v] = last edge on alternating path to v
	    static class Graph 
	    { 
		int V; //Number of Vertices 
		boolean augpat;
		LinkedList<Integer>[] adj; // adjacency lists 
		
		//Constructor 
		Graph(int V) 
		{ 
			this.V = V; 
			System.out.println("V="+V);
			adj = new LinkedList[V]; 
			
			
			for (int i = 0; i < adj.length; i++) 
				adj[i] = new LinkedList<Integer>(); 
			/*for(int i = 0 ; i < V1 ; i++){
		    		for(int j = 0 ; j < V2 ; j++){
		    			g1.addEdge(i,V1+j);
		    		}
		    	}*/
			System.out.println("Its  Graph constructor: ");
			augpat=hasAugmentingPath(V);
			BipMatching(augpat);
			
		} 
		
		//To add an edge to graph 
		void addEdge(int v, int w) 
		{ 
			adj[v].add(w); // Add w to v’s list. 
		} 
		
		// prints all not yet visited vertices reachable from s 
		
		private boolean hasAugmentingPath(int V) {
		        marked = new boolean[V];
		        mate = new int[V];
			System.out.println("marked="+marked);
		        edgeTo = new int[V];
		        for (int v = 0; v < V; v++){
		           edgeTo[v] = -1;
		           mate[v] = -1;
		        }

	        // breadth-first search (starting from all unmatched vertices on one side of bipartition)
	        	Queue<Integer> queue = new LinkedList<Integer>();
		        for (int v = 0; v < V; v++) {
		            System.out.println("v="+v+"    mate["+v+"]="+mate[v]);	
		            if ( !isMatched(v)) {
		                queue.add(v);
	        	        marked[v] = true;
	        	        System.out.println(""+v+" is not Matched: ");	
		            }
		        }
		        System.out.println("queue="+queue);

        	// run BFS, stopping as soon as an alternating path is found
		        while (!queue.isEmpty()) {
		        	System.out.println("I am in a queue :");
			        int v = queue.remove();
			        System.out.println(""+v+" is removed "+"adj["+v+"]="+adj[v]);
			        for (int w = 0 ; w <  V ;w++) {
					System.out.println("adj["+v+"]="+adj[v]);
	        	        // either (1) forward edge not in matching or (2) backward edge in matching
        			        if ( !marked[w]) {
			                    edgeTo[w] = v;
		        	            marked[w] = true;
		        	            System.out.println("w="+w);
	        	        	    if (!isMatched(w)) return true;
				            queue.add(w);
        		        	}
		            	}
		        }

		        return false;
		}
		
		public boolean isMatched(int v) {
		        validate(v);
		        System.out.println("mate["+v+"]="+mate[v]);
		        return mate[v] != UNMATCHED;
		}
    
	       private void validate(int v) {
		        if (v < 0 || v >= V)
			            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
		}
		
		
		
		// A function used by DFS 
    void DFSUtil(int v,boolean visited[]) 
    { 
        // Mark the current node as visited and print it 
        visited[v] = true; 
        System.out.print(v+" "); 
  
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
            if (!visited[n]) 
                DFSUtil(n, visited); 
        } 
    } 
  
    // The function to do DFS traversal. It uses recursive DFSUtil() 
    void DFS(int v) 
    { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        System.out.println("Hi its DFS");
        boolean visited[] = new boolean[V]; 
  
        // Call the recursive helper function to print DFS traversal 
        DFSUtil(v, visited); 
    } 
		
		void BipMatching(Boolean augpt){
			System.out.println("Hi its BipMatching");
			System.out.println("augmented path exist or not :"+augpt);
			mate = new int[V];
		        for (int v = 0; v < V; v++)
		            mate[v] = UNMATCHED;

		        // alternating path algorithm
		        while (augpt) {

		            // find one endpoint t in alternating path
		            int t = -1;
		            for (int v = 0; v < V; v++) {
		                if (!isMatched(v) && edgeTo[v] != -1) {
		                    t = v;
		                    break;
		                }
		            }

		            // update the matching according to alternating path in edgeTo[] array
		            for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
		                int w = edgeTo[v];
		                mate[v] = w;
		                mate[w] = v;
		            }
		            cardinality++;
		        }
		        System.out.println("MATCHING: ");
			for(int i = 0 ; i < V; i++){
				System.out.println("   "+mate[i]);
			}
		}
	} 
	
	// Driver program to test methods of graph class 
	public static void main(String[] args) 
	{ 
		// Total 5 vertices in graph 
		Graph g = new Graph(5); 
		
		g.addEdge(0, 2); 
		g.addEdge(0, 3); 
		g.addEdge(0, 4); 
		g.addEdge(1, 2); 
		g.addEdge(1, 3); 
		g.addEdge(1, 4);
			
		System.out.println("Following is the Depth First Traversal"); 
		g.DFS(0); 
	} 
} 
