package snakeLadder;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class TabuSearchAlgo {

    
    class Graph{
    public int v;
    private LinkedList<Integer>[] adj1 ;
       
    
    public Graph (int v){
        this.v = v;
        adj1 = (LinkedList<Integer>[])new LinkedList[v];
      
        for (int i=0; i<v ; i++){
            adj1[i]=new LinkedList<Integer>();
        }
    }
    public void addEdge1(int v, int w){
        adj1[v].add(w);
        
    }
      
    public Iterable<Integer> movegen(int v){
        return adj1[v];
    }
       
    
}

class TabuSearch{
	public int[] edgeTo;
    public boolean[] marked;
    public int[] dist;
    int best_move=-1;
    LinkedList<Integer> not_allowed;
    public TabuSearch (Graph G,int s,int[][] ladders, int[][] snakes){
        marked = new boolean[G.v];
        dist=new int[G.v];
        edgeTo= new int[G.v];
        
        not_allowed = new LinkedList<>();
        for (int i=0; i<G.v ; i++){
            marked[i]=false;
            dist[i]=-1;
            edgeTo[i]=-1;
        }
        
        tabu(G,s,ladders,snakes);
    }
    public void print_path(int w) {
    	System.out.print("Path in terms of squares of board  " );
    	LinkedList<Integer> l = new  LinkedList();
    	while(w!=-1) {
    		l.add(w);
    		w=edgeTo[w];
    	}
    	while(!l.isEmpty()) {
    		System.out.print(l.pollLast()+1 +" ");
    	}
    	System.out.println();

    }
    
    public boolean goaltest(int w) {
    	if(w==99)return true;
    	else return false;
    }
    
    public int heuristic (int[][] ladders, int[][] snakes,int v) {
    	for(int k=0; k<ladders.length ; k++){
            if(v==ladders[k][0]) {
                v=v+ ladders[k][1]-ladders[k][0];
            }
            
        }
        for(int k=0; k<snakes.length ; k++){
            if(v==snakes[k][0]) {
            	v=v + snakes[k][1]- snakes[k][0];
            }
        }
    	return 100-v;
    }
    public int best_allowed(Iterable<Integer> movegen_node, int[][] ladders, int[][] snakes, int present_node) {
    	int min=Integer.MAX_VALUE-1;
    	int present_v=-1;
    	
    	for(int v: movegen_node) {
    		int k=heuristic(ladders, snakes, v);
    		
    		
    		if(allowed(v)) {
    		if(k<min ) {
    			min=k;
    			present_v=v;
    		}
    		}
    		
    	}
    	//aspiration criteria check
    	
    	if(heuristic(ladders, snakes, present_node)<min) {

    		//all bad
    		int min_tabu=Integer.MAX_VALUE-1;
    		int best_tabu=-1;
    		for(int v: movegen_node) {
        		int k=heuristic(ladders, snakes, v);
        		if(k<min_tabu && !allowed(v)) {
        			min_tabu=k;
        			best_tabu=v;
        		}
        	}
    		if(min_tabu<heuristic(ladders, snakes, best_move)) {
    			present_v=best_tabu;
    		}
    	}
    	return present_v;
    }
    
    public boolean allowed(int v) {
    	for(int not_allow: not_allowed) {
    		if(v==not_allow) {
    		
    		return false;
    		}
    	}
    	return true;
    }
    public int ladder_tail(int new_node, int[][] ladders) {
    	for(int k=0; k<ladders.length ; k++){
            if(new_node==ladders[k][1]) {
                return ladders[k][0];
            }
            
        }
    	return -1;
    }
    public void tabu(Graph G ,int s, int[][] ladders, int[][] snakes){
        best_move=s;
        dist[s]=0;
        int visit=1;
        int present_node=s;
        int new_node=best_allowed (G.movegen(present_node), ladders, snakes,present_node);
        not_allowed.add(present_node);
        while(!goaltest(present_node)) {
        	
        	visit++;
        	edgeTo[new_node]=present_node;
            dist[new_node]=dist[present_node]+1;
            
            if(heuristic(ladders, snakes, present_node)>heuristic(ladders, snakes, new_node)) {
            	best_move=new_node;
            }
            
            if(not_allowed.size()<4) {
            	if(new_node-present_node>6) {
            		int tail_ladder=ladder_tail(new_node,ladders);
            		not_allowed.add(tail_ladder);
                	//System.out.println(tail_ladder+"added tail_ladder to no allow");
            	}
            	else {
            		not_allowed.add(new_node);
                	//System.out.println(new_node+"added to no allow");
            	}
            	
            
            }
            else {
            	not_allowed.remove();
            	if(new_node-present_node>6) {
            		int tail_ladder=ladder_tail(new_node,ladders);
            		not_allowed.add(tail_ladder);
                	//System.out.println(tail_ladder+"added tail_ladder to no allow");
            	}
            	else {
            		not_allowed.add(new_node);
                	//System.out.println(new_node+"added to no allow");
            	}
            }
            present_node =new_node;
            new_node=best_allowed(G.movegen(present_node), ladders, snakes,present_node);
            
        }
        System.out.println("Number of states visited "+visit);
        int node=present_node+1;
        System.out.println("present_node " + node);
    }
}   
    public int ladder_or_snake(int[][] ladders, int[][] snakes, int i) {
        for(int k=0; k<ladders.length ; k++){
            if(i==ladders[k][0]) {
                return ladders[k][1];
            }
            
        }
        for(int k=0; k<snakes.length ; k++){
            if(i==snakes[k][0]) {

                return snakes[k][1];
            }
        }
        return -1;
        
    }
    

    
    public int wayUp(int[][] ladders, int[][] snakes) {
        int n= ladders.length;
        int m= snakes.length;
        
        Graph G= new Graph(100);
        boolean k=false;
        int p=0;
        
        for (int i=0; i<100;i++){
            for (int j=1;j<=6;j++){
                if(ladder_or_snake(ladders,snakes,i)==-1){
                int w=i+j;
                
                     if(i+j<=99) { 
                         int v=ladder_or_snake(ladders,snakes,i+j);
                     if(v==-1){
                     G.addEdge1(i,i+j);
                     //System.out.println(i + " "+ w);
                     
                     }
                     else {
                         G.addEdge1(i,v);
                     }
                     }
                }
            }
            
        }
        //graph is made

        TabuSearch tabu= new TabuSearch(G,0,ladders,snakes);
        tabu.print_path(99);
        return tabu.dist[99];
        
        
        


    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
TabuSearchAlgo sol = new TabuSearchAlgo();
       

            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] ladders = new int[n][2];

            for (int i = 0; i < n; i++) {
                String[] laddersRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int laddersItem = Integer.parseInt(laddersRowItems[j]);
                    ladders[i][j] = laddersItem -1;
                }
            }

            int m = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] snakes = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] snakesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int snakesItem = Integer.parseInt(snakesRowItems[j]);
                    snakes[i][j] = snakesItem -1;
                }
            }

            int result =sol.wayUp(ladders, snakes);

            System.out.println( "Distance travelled by tabu search is " +result);
        


        scanner.close();
    }
}
