
import java.util.LinkedList; 
import java.util.Queue; 
/******************************************************************************
 *  Compilation:  javac BipartiteMatching.java
 *  Execution:    java BipartiteMatching V1 V2 E
 *  Dependencies: BipartiteX.java
 *
 *  Find a maximum cardinality matching (and minimum cardinality vertex cover)
 *  in a bipartite graph using the alternating path algorithm.
 *
 ******************************************************************************/

/**
 *  The {@code BipartiteMatching} class represents a data type for computing a
 *  <em>maximum (cardinality) matching</em> and a
 *  <em>minimum (cardinality) vertex cover</em> in a bipartite graph.
 *  A <em>bipartite graph</em> in a graph whose vertices can be partitioned
 *  into two disjoint sets such that every edge has one endpoint in either set.
 *  A <em>matching</em> in a graph is a subset of its edges with no common
 *  vertices. A <em>maximum matching</em> is a matching with the maximum number
 *  of edges.
 *  A <em>perfect matching</em> is a matching which matches all vertices in the graph.
 *  A <em>vertex cover</em> in a graph is a subset of its vertices such that
 *  every edge is incident to at least one vertex. A <em>minimum vertex cover</em>
 *  is a vertex cover with the minimum number of vertices.
 *  By Konig's theorem, in any biparite
 *  graph, the maximum number of edges in matching equals the minimum number
 *  of vertices in a vertex cover.
 *  The maximum matching problem in <em>nonbipartite</em> graphs is
 *  also important, but all known algorithms for this more general problem
 *  are substantially more complicated.
 *  <p>
 *  This implementation uses the <em>alternating-path algorithm</em>.
 *  It is equivalent to reducing to the maximum-flow problem and running
 *  the augmenting-path algorithm on the resulting flow network, but it
 *  does so with less overhead.
 *  The order of growth of the running time in the worst case is
 *  (<em>E</em> + <em>V</em>) <em>V</em>,
 *  where <em>E</em> is the number of edges and <em>V</em> is the number
 *  of vertices in the graph. It uses extra space (not including the graph)
 *  proportional to <em>V</em>.
 *  <p>
 *  See also {@link HopcroftKarp}, which solves the problem in  O(<em>E</em> sqrt(<em>V</em>))
 *  using the Hopcroft-Karp algorithm and
 *  <a href = "https://algs4.cs.princeton.edu/65reductions/BipartiteMatchingToMaxflow.java.html">BipartiteMatchingToMaxflow</a>,
 *  which solves the problem in O(<em>E V</em>) time via a reduction to maxflow.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/65reductions">Section 6.5</a>
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
 import java.util.*;
 public class BipartiteMatching {

    private static int[] mate;
    /**
     * Determines a maximum matching (and a minimum vertex cover)
     * in a bipartite graph.
     *
     * @param  G the bipartite graph
     * @throws IllegalArgumentException if {@code G} is not bipartite
     *

     * @param args the command-line arguments
     */
     
    public int[][] getAdjecencyMatrix(int x, int y){
    	int[][] adjMat = new int[x+y][x+y];
    	for(int i = 0 ; i < (x+y) ; i++){
    		for(int j = 0 ; j < (x+y) ; j++){
    			adjMat[i][j] = 1 ;	
    		}
    	}
    	return adjMat;
    }
    
    public int[][] getIncidenceMatrix(int x, int y){
    	int[][] incMat = new int[x][y];
    	for(int i = 0 ; i < x ; i++){
    		for(int j = 0 ; j < y ; j++){
    			incMat[i][j] = 1 ;	
    		}
    	}
    	return incMat;
    }
    
    public int[][] getInctoAdj(int[][] incMat, int x, int y){
    	int[][] adMat = new int[x+y][x+y];
    	for(int i1 = 0 ; i1 < (x+y) ; i1++){
    		for(int j1 = 0 ; j1 < (x+y) ; j1++){
    			adMat[i1][j1] = 0;
    		}
    	}
    	for(int i = 0 ; i < x ; i++){
    		for(int j = 0 ; j < y ; j++){
    			if(incMat[i][j] == 1){
	    			adMat[i][x+j] = 1 ;	
	    			adMat[x+j][i] = 1;
	    			//System.out.println("admat["+i+"]["+(x+j)+"]="+adMat[i][x+j]);
	    			//System.out.println("*admat["+(x+j)+"]["+i+"]="+adMat[x+j][i]);
	    		}
	    		/*else{
	    			adMat[i][j] = 0 ;	
	    		}*/
    		}
    	}
    	return adMat;
    }    
    

    
    
    
    
    
    public static void main(String[] args) {
        int V1 = Integer.parseInt(args[0]);
        int V2 = Integer.parseInt(args[1]);
        int E  = Integer.parseInt(args[2]);
        int pathindex;
        int augpath;
        
       // boolean augpath ;
        int v = V1 + V2 ;
        mate = new int[v];
        System.out.println("hi");
        Graph1 g1 = new Graph1(v);
        
       // GFG.Graph g1 = new GFG.Graph(v);
        //Graph g1 = new Graph(v); 
        //GFG g2 = new GFG();
        System.out.println("bye");
//	augpath =  g1.hasAugmentingPath(V1+V2);
	//g1.BipMatching(g1);
        
        int adjMatrix[][] = new int[V1+V2][V1+V2];
        int incMatrix[][] = new int[V1][V2];
        int i2aMatrix[][] = new int[V1+V2][V1+V2];
                        
        BipartiteMatching bm = new BipartiteMatching();
        adjMatrix = bm.getAdjecencyMatrix(V1, V2);
        incMatrix = bm.getIncidenceMatrix(V1, V2);
        i2aMatrix = bm.getInctoAdj(incMatrix,V1, V2);
        
        for(int i = 0 ; i < V1 ; i++){
    		for(int j = 0 ; j < V2 ; j++){
    			g1.addEdge(i,V1+j);
    			g1.addEdge(V1+j,i);
    		}
    	}
      //  System.out.println("V1="+V1+"   V2="+V2+"  i2aMatrix[0][0]="+i2aMatrix[0][0]);
        System.out.println("Incedence to Adjecency Matrix is : ");
        for(int i = 0 ; i < (V1+V2) ; i++){
    		for(int j = 0 ; j < (V1+V2) ; j++){
    			//g1.addEdge(i,j);
    			System.out.print("\t"+i2aMatrix[i][j]);
    		
    		}
    		System.out.println();
    	}
    	
    	System.out.println("Incedence Matrix is : ");
        for(int i = 0 ; i < V1 ; i++){
    		for(int j = 0 ; j < V2 ; j++){
    			//g1.addEdge(i,i+V1);
    			System.out.print("\t"+incMatrix[i][j]);
    		
    		}
    		System.out.println();
    	}
    	g1.DFS(0);
    	pathindex = g1.pathexist(0,1);
    	int cardinality = Math.min(V1,V2);
    //	while(augpath != 0){
//	    	augpath = g1.augpathexist(0, mate);
  //  		System.out.println("Augpath exist ="+augpath);
  	for(int i = 0 ; i < v ; i++){
    		mate[i] = -1;
    		System.out.println("mate["+i+"]="+mate[i]);
    	}
    	System.out.println("hi it will be matching");
	g1.getMatching(mate, cardinality);
    	System.out.println("hi it is my expidition matching");
	g1.getMatching1();
    //	}
        // print maximum matching
       // StdOut.printf("Number of edges in max matching        = %d\n", matching.size());
        //StdOut.printf("Number of vertices in min vertex cover = %d\n", matching.size());
        //StdOut.printf("Graph has a perfect matching           = %b\n", matching.isPerfect());
        //StdOut.println();

    
    }

}

