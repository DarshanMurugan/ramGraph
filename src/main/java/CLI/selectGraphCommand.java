
package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;


import Graph.Graph;
import Operations.modifyGraph; 
import Operations.selectGraph;

import java.util.concurrent.Callable;
import picocli.CommandLine.ParentCommand;

@Command(name= "selectGraph", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "select information from the grpah.",
    subcommands = {
        selectGraphCommand.selectNode.class,
        selectGraphCommand.getEdges.class,
    }
  )
public class selectGraphCommand implements Callable {
    Graph graph;
    selectGraph selectgraph;
    public selectGraphCommand(Graph graph){
      this.graph = graph;
      this.selectgraph = new selectGraph(this.graph);
    }

    
    public Integer call() {
      System.out.println("Specify a subcommand, use selectGraph --help for help.");
      return 1;
    }
   @Command(name="selectNode", mixinStandardHelpOptions=true,description="get node data based on an id")
   static class selectNode implements Callable {
      @Parameters(index="0",description="Id of the node you want to select.")
      private int nodeId;
      
      @ParentCommand
      private selectGraphCommand parent; //picocli feature to get outer class constructor variables

      public Integer call() {

        if (!parent.graph.isLoaded()) {
          System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
          return 1;
        }
        if (parent.graph.getNodeById(nodeId) == null){
          System.out.println("Error: Specified node does not exist.");
          return 1;
        }

        System.out.println(parent.graph.getNodeById(nodeId).getProperties());
        return 0;

      }
  }

   @Command(name="getEdges", mixinStandardHelpOptions=true,description="get all the edges connecting to a node using node id.")
   static class getEdges implements Callable {
      @Parameters(index="0",description="Id of the node whose edges you want to select.")
      private int nodeId;

      @Parameters(index="1",description="specify direction Incoming/Outgoing")
      private String direction;
      
      @ParentCommand
      private selectGraphCommand parent; //picocli feature to get outer class constructor variables

      public Integer call() {

        if (!parent.graph.isLoaded()) {
          System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
          return 1;
        }
        if (parent.graph.getNodeById(nodeId) == null){
          System.out.println("Error: Specified node does not exist.");
          return 1;
        }
        System.out.println(parent.selectgraph.getEdgesByNodeId(nodeId,direction));
        return 0;

      }
  }
}
