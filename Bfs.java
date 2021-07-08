import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Bfs {

    
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

class BreadthFirstPaths{
	public int[] edgeTo;
    public boolean[] marked;
    public int[] dist; 
    public BreadthFirstPaths (Graph G,int s){
        marked = new boolean[G.v];
        dist=new int[G.v];
        edgeTo= new int[G.v];
        for (int i=0; i<G.v ; i++){
            marked[i]=false;
            dist[i]=-1;
            edgeTo[i]=-1;
        }
        
        bfs(G,s);
    }
    public void print_path(int w) {
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
    public int[] arr(Iterable<Integer> a) {
        int n=0;
        for(int i:a) {
            n++;
        }
        
        int[] rev= new int[n];
        int j=0;
        for(int i:a) {
            rev[j]=i;
            j++;
        }
        return rev;
        
    }
    public void shuffleArray(int[] ar)
    {
      // If running on Java 6 or older, use `new Random()` on RHS here
      Random rnd = ThreadLocalRandom.current();
      for (int i = ar.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        // Simple swap
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
      }
    }
    public boolean goaltest(int w) {
    	if(w==99)return true;
    	else return false;
    }
    public void bfs(Graph G ,int s){
        LinkedList<Integer> q =new LinkedList<Integer>();
        q.add(s);
        marked[s]=true;
        dist[s]=0;
        int visit=0;

        while(q.size()!=0){
            int v=q.poll();
            visit++;
            if(goaltest(v)) {
                System.out.println("Number of states visited "+visit);

            	return;
            }
            int[] b=arr(G.movegen(v));
           // shuffleArray(b);   // shuffles the order in which vertices are entered
            for (int i=0;i<b.length;i++){
                int w=b[i];
                if(!marked[w]){
                	
                    q.add(w);
                    marked[w]=true;
                    dist[w]=dist[v]+1;
                    edgeTo[w]=v;
                    
                }
            }
            
        }
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

        BreadthFirstPaths bfs= new BreadthFirstPaths(G,0);
        bfs.print_path(99);
        return bfs.dist[99];
        
        
        


    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
Bfs sol = new Bfs();
       

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

            System.out.println( "Distance travelled by bfs is " +result);
        


        scanner.close();
    }
}
