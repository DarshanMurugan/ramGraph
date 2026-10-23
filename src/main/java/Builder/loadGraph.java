
package Builder;

import Graph.Node;
import Graph.Graph;
import Graph.Edge;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;
import java.util.HashMap;

import java.io.IOException;

public class loadGraph {
  Graph graph;
  String infoFileName;
  String connectionsFileName;

  public loadGraph(Graph graph,String infoFileName,String connectionsFileName) {
    this.graph = graph;
    this.infoFileName = infoFileName;
    this.connectionsFileName = connectionsFileName;

  }

  public Graph load() throws Exception {
    buildNodes();
    buildEdges();
    return this.graph;
  }

  private void buildNodes() throws Exception {
    try (FileReader nodeReader = new FileReader(this.infoFileName)) {
      CsvToBean<Node> csvToBean = new CsvToBeanBuilder<Node>(nodeReader)
            .withType(Node.class)
            .build();

      csvToBean.stream().forEach(node -> {
        this.graph.addNode(node);
      });
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  private void buildEdges() throws Exception {
      try (CSVReader edgeReader = new CSVReader(new FileReader(this.connectionsFileName))) {
        CsvToBean<Edge> csvToBean = new CsvToBeanBuilder<Edge>(edgeReader)
            .withType(Edge.class)
            .build();
        
        csvToBean.stream().forEach(edge -> {
          Node outgoingNode = this.graph.getNodeById(edge.getSourceId());
          Node incomingNode = this.graph.getNodeById(edge.getTargetId());
          
          outgoingNode.addOutgoingEdge(edge);
          incomingNode.addIncomingEdge(edge);
      });
      }
  }
}


