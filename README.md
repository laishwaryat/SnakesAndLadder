Domain: Snakes And Ladder Game


We have a snake and ladder board, find the minimum number of dice throws required and path taken to reach the destination or last cell from source or 1st cell.
 Basically, the player has total control over outcome of dice throw. 
If the player reaches a cell which is base of a ladder, the player has to climb up that ladder and if reaches a cell is mouth of the snake, has to go down to the tail of snake without a dice throw.


Start state : square 1
Goal state : square 100
Neighbouring states : All the states that are reachable in a single dice throw. In case of ladder or snake we will consider the state after travelling the ladder/ snake as neighbouring
Distance : number of rolls of dice to reach square 100
Path : the squares traversed between and including 1 and 100


Input Format:
- The first line contains n, the number of ladders. 
- Each of the next n lines contains two space-separated integers, the start and end of a ladder. 
- The next line contains the integer m , the number of snakes. 
- Each of the next  m lines contains two space-separated integers, the start and end of a snake.


Solution :
The idea is to consider the given snake and ladder board as a directed graph with number of vertices equal to the number of cells in the board. Every vertex of the graph has an edge to next six vertices if next 6 vertices do not have a snake or ladder. If any of the next six vertices has a snake or ladder, then the edge from current vertex goes to the top of the ladder or tail of the snake.

Implemented using different algorithms:
1) BFS
2) DFS
3) Hill Climbing
4) Best First Search
5) Tabu Search

