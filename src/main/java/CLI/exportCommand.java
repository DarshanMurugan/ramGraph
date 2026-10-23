
package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import Graph.Graph;
import Exporter.exportToJsonLD;


import java.util.concurrent.Callable;
import picocli.CommandLine.ParentCommand;

@Command(name= "export", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "export to popular graph formats like jsonLD.",
    subcommands = {
        exportCommand.jsonLD.class,
    }
  )
public class exportCommand implements Callable {
    Graph graph;
    public exportCommand(Graph graph){
      this.graph = graph;
    }
    
    public Integer call() {
      System.out.println("Specify a subcommand, use export --help for help.");
      return 1;
    }
   @Command(name="jsonld", mixinStandardHelpOptions=true,description="export to jasonLD.")
   static class jsonLD implements Callable {

      @ParentCommand
      private exportCommand parent; //picocli feature to get outer class constructor variables
    
      public Integer call() {

        if (!parent.graph.isLoaded()) {
          System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
          return 1;
        }
        
        exportToJsonLD exporter = new exportToJsonLD(parent.graph);
        exporter.export();
        
        
        return 0;

      }
  }

}
