
package Operations;

import Operations.BFSResult;
import Graph.Graph;
import Graph.Node;

import java.util.*;


public class BFS {
  int totalNodes;

  public BFS(int totalNodes){
    this.totalNodes = totalNodes;
  }

  public BFSResult search(Node start,Node target){
    
    if (start.equals(target)) {
      List<Node> shortestPath = List.of(start,start);
      return new BFSResult(start,0,shortestPath);
    }

    Queue<Node> queue = new LinkedList<>();
    HashSet<Integer> visited = new HashSet<>();
    HashMap<Node,Node> pathMap = new HashMap<>();

    queue.add(start);
    visited.add(start.getId());

    while(!(queue.isEmpty())){
      int nodesAtCurrentLevel = queue.size();

      for (int i = 0; i < nodesAtCurrentLevel; i++){
        Node current = queue.poll();
        for(Node node: current.getConnections()){
          if (!visited.contains(node.getId())){
            pathMap.put(node,current);
            visited.add(node.getId());

            if (node.equals(target)) {
              List<Node> shortestPath = constructShortestPath(pathMap,start,target);
              return new BFSResult(node,visited.size() - 1,shortestPath);
            }
            queue.add(node);
          }
        }
      }
    }
    return null;
  }

  private List<Node> constructShortestPath(HashMap<Node,Node> pathMap,Node start,Node target){
    LinkedList<Node> shortestPath = new LinkedList<>();
    Node curr = target;
    while(curr != null){
      shortestPath.addFirst(curr);
      if (curr.equals(start))
        break;
      curr = pathMap.get(curr);
    }
    return shortestPath;
  }
}



