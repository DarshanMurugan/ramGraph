
package CLI;

import Graph.Graph;
import Graph.Node;

import Operations.popularNode;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;



@Command(name= "popularNodes", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "returns the most popular nodes in a graph.")
public class popularNodesCommand implements Callable<Integer> {
  Graph graph;

  public popularNodesCommand(Graph graph){
      this.graph = graph;
    }
  @Option(names={"--direction","-d"}, description="define direction, available values: INCOMING,OUTGOING ", defaultValue="INCOMING")
  private String direction;


  @Option(names={"--limit","-l"}, description="define how many popular vertices to show. from most to least popular", defaultValue="10")
  private int limit;


  @Override
  public Integer call() throws Exception {
    if (!graph.isLoaded()) {
        System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
        return 1;
    }

    direction = direction.strip().toUpperCase();

    popularNode popNodes = new popularNode(graph, direction);
    int count = 1;
    for (Node node: popNodes.getPopularity()){

      if (limit <= 0) 
        break;
      
      System.out.println("Popularity position: " + count);
      System.out.println();
      node.getProperties().forEach((key, value) -> System.out.println(key + ": " + value));
      System.out.println();
      if (direction.equals("INCOMING"))
        System.out.println("Number of edges: " + node.getNoOfIncoming() );
      else 
        System.out.println("Number of edges: " + node.getNoOfOutgoing() );

      System.out.println();
      System.out.println("----------------------------");

      count++;
      limit--;

    }
    
  

    return 0;
  }
} 
