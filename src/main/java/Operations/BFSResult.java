package Operations;

import Graph.Graph;
import Graph.Node;

import java.util.List;
import java.util.HashMap;

public class BFSResult {
  private Node result;
  private int totalNodesVisited;
  private List<Node> shortestPath;

  public BFSResult(Node result, int totalNodesVisited, List<Node> shortestPath) {
    this.result = result;
    this.totalNodesVisited = totalNodesVisited;
    this.shortestPath = shortestPath;
  }

  public Node getResult(){
    return this.result;
  }

  public int getTotalNodesVisited(){
    return this.totalNodesVisited;
  }
  public List<Node> getShortestPath() {
    return this.shortestPath;
  }
}
