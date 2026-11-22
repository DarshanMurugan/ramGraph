package Operations;

import Graph.Graph;
import Graph.Node;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class popularNode {

  private Graph graph;
  private String direction;

  public popularNode(Graph graph, String direction){
    this.graph = graph;
    this.direction = direction;
  }
  
  public List<Node> getPopularity() {
    List<Node> nodes = new ArrayList<>(graph.getGraphNodes()) ;
    if (direction.equals("INCOMING"))
      nodes.sort(Comparator.comparing(Node::getNoOfIncoming).reversed());
    if (direction.equals("OUTGOING"))        
      nodes.sort(Comparator.comparing(Node::getNoOfOutgoing).reversed());

    return nodes;
  }




}
