
package Operations;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;

import java.util.List;

public class selectGraph{
  Graph graph;
  public selectGraph(Graph graph) {
    this.graph = graph;
  }


  public String getEdgesByNodeId(int nodeId, String direction){
    Node node = this.graph.getNodeById(nodeId);
    direction = direction.toLowerCase().strip();
    List<Edge> edgeList; 
    String output = "";
    if ((direction).equals("incoming")){
      edgeList = node.getIncoming();
    }
    else {
      edgeList = node.getOutgoing();
    }

    for(Edge edge: edgeList){
      output += edge.getSourceId() + "----->" +  edge.getTargetId() + "\n" +
                "Relation:" + edge.getRelation() + "\n" +
                "Properties:" + "\n" +
                edge.getProperties() + "\n";

    }
    return output;
  }


}
