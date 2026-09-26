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

import java.util.concurrent.Callable;


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
    BFSResult searchresult = (new BFS(graph.totalNodes())).search(graph.getNodeById(startId),graph.getNodeById(targetId));
    if (searchresult == null) {
        System.out.println("Error: Node does not exist or is unreacheable.");
        return 1;
      }
    System.out.println("ID: " + searchresult.getResult().getId() + " Age: "+ searchresult.getResult().getAge()+ " Name: " + searchresult.getResult().getName());
    System.out.println("Distance travelled: " + searchresult.getTotalNodesVisited());
    for (Node node: searchresult.getShortestPath()){
      System.out.print(" -> " + node.getName());
    }
    System.out.println();
    return 0;
  }
} 
