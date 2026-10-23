package CLI;

import Operations.BFSResult;
import Operations.BFS;
import Graph.Graph;
import Graph.Node;
import Builder.loadGraph;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


import java.util.concurrent.Callable;
import java.util.Set;

import Options.BFSOptions;
import Options.BFSOptions.Direction;


@Command(name= "bfs", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "Searches the graph specified using bfs and returns the shortest path")
public class BFSCommand implements Callable<Integer> {
  Graph graph;

  public BFSCommand(Graph graph){
      this.graph = graph;
    }

  @Parameters(index = "0", description="The name/id of the node to start with.")
  private int startId;

  @Parameters(index = "1", description = " The name/id of the node to find")
  private int targetId;

  @Option(names={"--relation","-r"}, description= "define a specific relation type to search with",
          defaultValue="", split=",")
  private Set<String> relationType;

  @Option(names={"--direction","-d"}, description="define direction, available values: ${COMPLETION-CANDIDATES} ", defaultValue="BOTH")
  private Direction direction;

  @Option(names={"--degree","-dg"}, description= "define a specific relation type to search with",defaultValue="2147483647")
  private int degree;

  @Override
  public Integer call() throws Exception {
    if (!graph.isLoaded()) {
        System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
        return 1;
    }

    if (graph.getNodeById(startId) == null ) {
      System.out.println("Error: starting Node does not exist.");
      return 1;
    }

    if(graph.getNodeById(targetId) == null) {
      System.out.println("Error: target Node does not exist.");
      return 1;
    }

    BFSOptions modifier = new BFSOptions(relationType,degree,direction);
    BFSResult searchresult = (new BFS(this.graph,this.graph.totalNodes(),modifier))
                              .search(this.graph.getNodeById(startId),this.graph.getNodeById(targetId));
    if (searchresult == null) {
        System.out.println("Error: Node does not exist or is unreacheable.");
        return 1;
      }
    Node resultNode = searchresult.getResult();
    resultNode.getProperties().forEach((key, value) -> System.out.println(key + ": " + value));
    System.out.println("Total Nodes travelled: " + searchresult.getTotalNodesVisited());
    System.out.println("Shortest Distance: " + (searchresult.getShortestPath().size() - 1));
    for (Node node: searchresult.getShortestPath()){
      System.out.print(" -> " + node.getId());
    }
    System.out.println();
    return 0;
  }
} 
