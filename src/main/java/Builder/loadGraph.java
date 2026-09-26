
package Builder;

import Graph.Node;
import Graph.Graph;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;
import java.util.HashMap;

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
    List<Node> nodeArray = readNodes();
    createGraph(graph,nodeArray);
    buildConnections(graph);
    return graph;
  }

  private List<Node> readNodes() throws Exception {
    try (FileReader infoReader = new FileReader(this.infoFileName)) {
      
      List<Node> nodeArray =  new CsvToBeanBuilder<Node>(infoReader)
              .withType(Node.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build()
              .parse();
      return nodeArray;
    }
  }

  private void createGraph(Graph graph,List<Node> nodeArray) {
      for (Node node:nodeArray) {
        graph.addNode(node);
      }
  }

  private void buildConnections(Graph graph) throws Exception {
      // the connections readNodeser stores the id of the connections for every node and 
      // the code below attaches the nodes to the connections array
      try (CSVReader connectionsReader = new CSVReader(new FileReader(this.connectionsFileName))) {
        String[] row;
        while((row = connectionsReader.readNext()) != null){
          // row[0] = id of a node and row[1]  = id of node that row[0] must connect to
          graph.getNodeById(Integer.parseInt(row[0]))
          .addConnection(graph.getNodeById(Integer.parseInt(row[1])));
        }
      }
  }
}


