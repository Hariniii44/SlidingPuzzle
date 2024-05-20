import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SlidingPuzzle {
    private static Node startNode;
    private static Node endNode;
    private static final ArrayList<ArrayList<Node>> puzzleGrid = new ArrayList<>();   //for the whole puzzle grid
    private static final ArrayList<Node> exploreNodes = new ArrayList<>();

    /*task parser*/

    //Parser to read the puzzle grid from a file
    private static void loadMazeFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            int row = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().trim().split("");
                ArrayList<Node> tempArrayList = new ArrayList<>();  //for rows in the puzzle grid

                for (int i = 0; i < line.length; i++) {
                    Node node = new Node(row, i);
                    switch (line[i]) {
                        case "0" -> node.setWall(true);
                        case "S" -> node.setLabel("S");
                        case "F" -> node.setLabel("F");
                        case "." -> node.setLabel(".");
                        default -> System.out.println("Invalid Input!");
                    }
                    tempArrayList.add(node);
                }
                puzzleGrid.add(tempArrayList);
                row++;
            }

            //setting neighbors for each cell
            for (int i = 0; i < puzzleGrid.size(); i++) {
                for (int j = 0; j < puzzleGrid.get(0).size(); j++) {
                    puzzleGrid.get(i).get(j).setNeighbors(puzzleGrid);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    private static boolean pathFound = false;
    private static final ArrayList<Node> visitedList = new ArrayList<>();

    /*task path finder*/
    private static void findShortestPath() {
        exploreNodes.add(startNode); //starting node to explore

        while (!exploreNodes.isEmpty()) {    //iterate until exploreNodes list become empty. new node is added to the list in each iteration
            Node currentCell = exploreNodes.remove(0);

            if (currentCell.equals(endNode)) {
                pathFound = true;
                reconstructPath(currentCell);
                break;
            }

            exploreNeighbors(currentCell);
        }

        if (!pathFound) {
            System.out.println("No path found to the destination.");
        }
    }

    private static void exploreNeighbors(Node currentNode) {
        if (!visitedList.contains(currentNode)) {
            visitedList.add(currentNode);

            ArrayList<Node> nextNodes = getNextCells(currentNode);
            for (Node nextNode : nextNodes) {
                if (!visitedList.contains(nextNode) && !exploreNodes.contains(nextNode) && !nextNode.isWall()) {
                    nextNode.setPrevCell(currentNode);
                    exploreNodes.add(nextNode);
                }
            }
        }
    }

    private static ArrayList<Node> getNextCells(Node currentCell) {
        ArrayList<Node> nextNodes = new ArrayList<>();
        nextNodes.add(slide(currentCell, 0, -1)); // Slide left
        nextNodes.add(slide(currentCell, 0, 1)); // Slide right
        nextNodes.add(slide(currentCell, -1, 0)); // Slide up
        nextNodes.add(slide(currentCell, 1, 0)); // Slide down
        return nextNodes;
    }

    private static final ArrayList<Node> path = new ArrayList<>();

    //reconstructs the path from end cell to start cell to give the output steps
    private static void reconstructPath(Node currentCell) {
        path.clear();
        while (currentCell != null) {
            path.add(0, currentCell); //reverse the path because path is added from last to start
            currentCell = currentCell.getPrevNode();
        }
    }

    //sliding in a direction until hitting a wall or Rock
    private static Node slide(Node currentCell, int dx, int dy) {
        Node nextCell = currentCell;
        while (true) {
            int newRow = nextCell.getRowNum() + dx;
            int newCol = nextCell.getColNum() + dy;

            //check for any obstacles. (boundaries or rocks)
            if (newRow >= 0 && newRow < puzzleGrid.size() && newCol >= 0 && newCol < puzzleGrid.get(0).size() && !puzzleGrid.get(newRow).get(newCol).isWall()) {
                nextCell = puzzleGrid.get(newRow).get(newCol);
                //stop sliding if the next position is the goal
                if (nextCell.equals(endNode)) {
                    break;
                }
            } else {    //if an obstacle is met, sliding stops
                break;
            }
        }
        return nextCell;
    }

    private static void printOutput() {   //gives the step by step output
        if (pathFound) {
            System.out.println("1. Start at (" + (path.get(0).getColNum() + 1) + ", " + (path.get(0).getRowNum() + 1) + ")");
            int slideNumber = 2;
            for (int i = 1; i < path.size(); i++) {
                Node node = path.get(i);
                System.out.println(slideNumber + ". Move " + getDirection(path.get(i - 1), node) + " to (" + (node.getColNum() + 1) + ", " + (node.getRowNum() + 1) + ")");
                slideNumber++;
            }
            System.out.println(slideNumber + ". Done!");
        } else {
            System.out.println("No path found.");
        }
    }

    private static String getDirection(Node from, Node to) {
        if (from.getRowNum() < to.getRowNum()) {
            return "down";
        } else if (from.getRowNum() > to.getRowNum()) {
            return "up";
        } else if (from.getColNum() < to.getColNum()) {
            return "right";
        } else {
            return "left";
        }
    }

    //Find the starting node
    private static Node getStartNode() {
        Node start = new Node();
        for (ArrayList<Node> nodes : puzzleGrid) {
            for (int j = 0; j < puzzleGrid.get(0).size(); j++) {
                if (nodes.get(j).getLabel() != null && nodes.get(j).getLabel().equals("S")) {
                    start = nodes.get(j);
                    break;
                }
            }
        }
        return start;
    }

    //Find the ending node
    private static Node getEndNode() {
        Node end = new Node();
        for (ArrayList<Node> nodes : puzzleGrid) {
            for (int j = 0; j < puzzleGrid.get(0).size(); j++) {
                if (nodes.get(j).getLabel().equals("F")) {
                    end = nodes.get(j);
                    break;
                }
            }
        }
        return end;
    }

    /*main method*/
    public static void main(String[] args) {
        try {
            loadMazeFromFile("CWalgo/examples/maze10_1.txt");
        } catch (Exception e) {
            System.out.println("File not found!");
            System.exit(0);
        }

        startNode = getStartNode();   //calling the method to get the S position
        endNode = getEndNode();       //calling the method to get the F position
        long startTime = System.nanoTime();
        exploreNodes.add(startNode);
        findShortestPath();
        long endTime = System.nanoTime();

        printOutput();
        long duration = (endTime - startTime);
        double milliseconds = duration / 1_000_000.0; // Convert nanoseconds to milliseconds
        System.out.println("Time taken to find the shortest path: " + milliseconds + " miliseconds");
        System.out.println("Number of moves: " + path.size());
    }
}
