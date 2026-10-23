package Graph;

import Graph.Node;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Graph {
  private HashMap<Integer,Node> nodesById = new HashMap<>();


  public void addNode(Node node) {
    nodesById.put(node.getId(),node);
  }

  public void removeNode(int id) {
    nodesById.remove(id);
  }

  public Node getNodeById(int id){
    return nodesById.getOrDefault(id,null);
  }


  public int totalNodes(){
    return nodesById.size();

  }

  public boolean isLoaded(){
    return (!nodesById.isEmpty());
  }
  
  public List<Node> getGraphNodes() {
    return new ArrayList<>(nodesById.values());
  }

  
}
