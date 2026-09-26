package Graph;

import Graph.Node;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Graph {
  private HashMap<Integer,Node> nodesById = new HashMap<>();
  private HashMap<String,List<Integer>> nodesByName = new HashMap<>();


  public void addNode(Node node) {
    nodesById.put(node.getId(),node);
    nodesByName.computeIfAbsent(node.getName(), k -> new ArrayList<>()).add(node.getId());
  }

  public void removeNode(int id) {
    String name = nodesById.get(id).getName();
    nodesByName.get(name).remove(Integer.valueOf(id));
    nodesById.remove(id);
  }

  public Node getNodeById(int id){
    return nodesById.getOrDefault(id,null);
  }

  public List<Integer> getIdByName(String name) {
    return nodesByName.get(name);
  }

  public int totalNodes(){
    return nodesById.size();

  }

  public boolean isLoaded(){
    return (!nodesById.isEmpty());
  }
}
