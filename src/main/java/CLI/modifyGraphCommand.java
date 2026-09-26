package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import Graph.Graph;
import Operations.modifyGraph;


import java.util.concurrent.Callable;
import picocli.CommandLine.ParentCommand;

@Command(name= "modifyGraph", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "Make changes to the existing graph like removing and adding nodes.",
    subcommands = {
        modifyGraphCommand.removeNode.class,
        modifyGraphCommand.connectNode.class,
    }
  )
public class modifyGraphCommand implements Callable {
    Graph graph;
    modifyGraph modifygraph;
    public modifyGraphCommand(Graph graph){
      this.graph = graph;
      this.modifygraph = new modifyGraph(this.graph);
    }
    
    public Integer call() {
      System.out.println("Specify a subcommand, use modifyGraph --help for help.");
      return 1;
    }
   @Command(name="remove", mixinStandardHelpOptions=true,description="remove a Node from the Graph, specify the Node Id to remove.")
   static class removeNode implements Callable {
      @Parameters(index="0",description="Id of the node you want to remove.")
      private int nodeId;
      
      @ParentCommand
      private modifyGraphCommand parent; //picocli feature to get outer class constructor variables

      public Integer call() {

        if (!parent.graph.isLoaded()) {
          System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
          return 1;
        }
        if (parent.graph.getNodeById(nodeId) == null){
          System.out.println("Error: Specified node does not exist.");
          return 1;
        }
        parent.modifygraph.removeNode(nodeId);
        return 0;

      }
  }

  @Command(name="connect", mixinStandardHelpOptions=true, description="add a connection(vertex) between two nodes.")
  static class connectNode implements Callable {
    @Parameters(index="0",description="Id of the node to connect with.")
    private int sourceNodeId;

    @Parameters(index="1",description="Id of the node to connect to.")
    private int connectNodeId;

    @ParentCommand
    private modifyGraphCommand parent; //picocli feature to get outer class constructor variables

    public Integer call() {
      if (parent.graph.getNodeById(sourceNodeId) == null) {
        System.out.println("Error: Specified source node does not exist");
        return 1;
      }
      
      if (parent.graph.getNodeById(connectNodeId) == null) {
        System.out.println("Error: Specified connect Node does not exist");
      }

      parent.modifygraph.addConnection(sourceNodeId,connectNodeId);

      return 0;

    }


  }
}
