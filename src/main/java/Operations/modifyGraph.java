package Operations;
import Graph.Edge;
import Graph.Graph;
import Graph.Node;

import java.util.Map;
import java.util.List;

public class modifyGraph {
  Graph graph;
  public modifyGraph(Graph graph) {
    this.graph = graph;
  }

  public void removeNode(int nodeId){
    Node node = graph.getNodeById(nodeId);
    List<Edge> incomingEdge = node.getIncoming();
    for(Edge edge: incomingEdge){
      graph.getNodeById(edge.getSourceId()).removeOutgoingEdge(edge);
    }
    List<Edge> outgoingEdge = node.getOutgoing();
    for(Edge edge: outgoingEdge){
      graph.getNodeById(edge.getSourceId()).removeIncomingEdge(edge);
    }

    graph.removeNode(nodeId);

  }

  public void addConnection(int sourceNodeId,String relation,int targetNodeId,Map<String,Object> properties) {
    Edge edge = new  Edge(sourceNodeId,relation,targetNodeId,properties);
    graph.getNodeById(sourceNodeId).addOutgoingEdge(edge);
    graph.getNodeById(targetNodeId).addIncomingEdge(edge);
  }

}
