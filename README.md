Sliding Puzzle Solver
This project implements a sliding puzzle solver using Breadth-First Search (BFS) to find the shortest path from a start node (S) to a finish node (F) on a 2D grid. The grid can contain walls (0), open spaces (.), and the start and finish nodes.

Prerequisites
Java Development Kit (JDK) 8 or higher
A text editor or Integrated Development Environment (IDE) for Java development

Installing
Clone the repository:
git clone https://github.com/yourusername/SlidingPuzzle.git
Navigate to the project directory:
cd SlidingPuzzle
Ensure you have a puzzle file ready. The format should be a text file where:

S represents the start node
F represents the finish node
0 represents a wall
. represents an open space

How It Works
The program reads a grid from a file and uses the BFS algorithm to find the shortest path from the start node (S) to the finish node (F). It then outputs the path taken and the number of moves required.

Data Structure
A 2D grid is used to represent the puzzle layout. Each cell in the grid is represented by a Node object, which contains information about its position, type (start, finish, wall, or open), and links to neighboring nodes.

Pathfinding Algorithm
Breadth-First Search (BFS) is used to explore all possible paths from the start node. BFS ensures that the shortest path is found in an unweighted grid.
Algorithm Details
Reading the Grid: The grid is read from a text file, where each character represents a different type of cell.
Setting Neighbors: For each node, neighbors are identified (up, down, left, right) and stored.
Pathfinding: Starting from the S node, BFS explores each node's neighbors until the F node is found.
Reconstructing the Path: Once the F node is reached, the path is reconstructed from F back to S using parent references.
Output: The steps to reach the finish node are printed, along with the total number of moves and the time taken.

Usage
Running the Program
Compile the Java program:
javac SlidingPuzzle.java
Run the compiled Java program:
java SlidingPuzzle path/to/your/puzzlefile.txt