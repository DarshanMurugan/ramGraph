package Operations;

import Graph.Graph;
import Graph.Node;

public class modifyGraph {
  Graph graph;
  public modifyGraph(Graph graph) {
    this.graph = graph;
  }

  public void removeNode(int nodeId){
    graph.removeNode(nodeId);
  }

  public void addConnection(int sourceNodeId,int connectNodeId) {
    Node sourceNode = graph.getNodeById(sourceNodeId);
    Node connectNode = graph.getNodeById(connectNodeId);
    sourceNode.addConnection(connectNode);
  }

}
