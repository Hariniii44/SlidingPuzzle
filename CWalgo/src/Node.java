import java.util.ArrayList;

public class Node {
    private int rowNum;
    private int colNum;
    private boolean isWall;
    private String type; //types of tile: S, F, 0, .
    private Node prevNode; //previous cell in the path
    private ArrayList<Node> neighbors; //List of neighbors for the cell

    // constructor

    public Node() {
    }
    public Node(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.neighbors = new ArrayList<>();
        this.type = ".";
        this.isWall = false;
    }

    // Getters and setters

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public String getLabel() {
        return type;
    }

    public void setLabel(String label) {
        this.type = label;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevCell(Node prevCell) {
        this.prevNode = prevCell;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<ArrayList<Node>> maze) {
        if (rowNum < maze.size() - 1)
            neighbors.add(maze.get(rowNum + 1).get(colNum));
        if (rowNum > 0)
            neighbors.add(maze.get(rowNum - 1).get(colNum));
        if (colNum < maze.get(0).size() - 1)
            neighbors.add(maze.get(rowNum).get(colNum + 1));
        if (colNum > 0)
            neighbors.add(maze.get(rowNum).get(colNum - 1));
    }

    @Override
    public String toString() {
        return "(" + getRowNum() + ", " + getColNum() + ")";
    }
}
