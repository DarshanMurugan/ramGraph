
package Operations;

import Operations.BFSResult;
import Graph.Graph;
import Graph.Node;
import Graph.Edge;

import Options.BFSOptions;
import Options.BFSOptions.Direction;


import java.util.*;


public class BFS {
  Graph graph;
  int totalNodes;
  BFSOptions modifier;

  public BFS(Graph graph,int totalNodes, BFSOptions modifier){
    this.graph = graph;
    this.totalNodes = totalNodes;
    this.modifier = modifier;
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

    while(!(queue.isEmpty())) {
      int edgesAtCurrentLevel = queue.size();

      for(int i = 0; i < edgesAtCurrentLevel; i++){
        if (i > modifier.getDegree()) {
          return null;
        }
        Node current = queue.poll();
        for(Edge edge: current.getOutgoing()){
          if(modifier.getRelationType().contains(edge.getRelation()) || modifier.getRelationType().contains("")){
            if(!visited.contains(edge.getTargetId())){
              pathMap.put(this.graph.getNodeById(edge.getTargetId()),current);
              visited.add(edge.getTargetId());
            }

            if(edge.getTargetId() == target.getId()){
              List<Node> shortestPath = constructShortestPath(pathMap,start,target);
              return new BFSResult(this.graph.getNodeById(edge.getTargetId()),visited.size() - 1, shortestPath);
            }
            queue.add(this.graph.getNodeById(edge.getTargetId()));
          }
        }
      }
    }

    return null;
  }

  private LinkedList<Node> constructShortestPath(HashMap<Node,Node> pathMap,Node start,Node target){
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



