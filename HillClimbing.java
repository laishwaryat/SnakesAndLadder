import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class HillClimbing {

    
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

class HillClimbPath{
	public int[] edgeTo;
    public boolean[] marked;
    public int[] dist; 
    public HillClimbPath (Graph G,int s,int[][] ladders, int[][] snakes){
        marked = new boolean[G.v];
        dist=new int[G.v];
        edgeTo= new int[G.v];
        for (int i=0; i<G.v ; i++){
            marked[i]=false;
            dist[i]=-1;
            edgeTo[i]=-1;
        }
        
        hill_climb(G,s,ladders,snakes);
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
    public int head_sort_h(Iterable<Integer> movegen_node, int[][] ladders, int[][] snakes) {
    	int min=Integer.MAX_VALUE-1;
    	int present_v=-1;
    	for(int v: movegen_node) {
    		int k=heuristic(ladders, snakes, v);
    		if(k<min) {
    			min=k;
    			present_v=v;
    		}
    	}
    	return present_v;
    }
    
    public void hill_climb(Graph G ,int s, int[][] ladders, int[][] snakes){
        
        dist[s]=0;
        int visit=1;
        int present_node=s;
        int new_node=head_sort_h (G.movegen(present_node), ladders, snakes);
        while(heuristic(ladders,snakes,new_node)<heuristic(ladders,snakes,present_node)){
            visit++;
        	edgeTo[new_node]=present_node;
            dist[new_node]=dist[present_node]+1;
            present_node =new_node;
            new_node=head_sort_h (G.movegen(present_node), ladders, snakes);
            
        }
        System.out.println("Number of states visited "+visit);
    }
}   
    public int ladder_or_snake(int[][] ladders, int[][] snakes, int i) {
        for(int k=0; k<ladders.length ; k++){
            if(i==ladders[k][0]) {
                //System.out.println("tru");
                return ladders[k][1];
            }
            
        }
        for(int k=0; k<snakes.length ; k++){
            if(i==snakes[k][0]) {
                //System.out.println("tru");

                return snakes[k][1];
            }
        }
        return -1;
        
    }
    

    
    // Complete the quickestWayUp function below.
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

        HillClimbPath hill_climb= new HillClimbPath(G,0,ladders,snakes);
        hill_climb.print_path(99);
        return hill_climb.dist[99];
        
        
        


    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
HillClimbing sol = new HillClimbing();
       

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

            System.out.println( "Distance travelled by hill_climb is " +result);
        


        scanner.close();
    }
}
