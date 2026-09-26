package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import Graph.Graph;
import Builder.loadGraph;

import java.io.FileNotFoundException;

import java.util.concurrent.Callable;

@Command(name= "load", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "Loads the nodes onto RAM from the specified file directory")
public class loadCommand implements Callable<Integer> {
  Graph graph;
  public loadCommand(Graph graph) {
    this.graph = graph;
  }

  @Parameters(index = "0", description="The file name of the csv file holding the information")
  private String infoFileName;

  @Parameters(index = "1", description = "The file name of the csv file holding the connections")
  private String connectionsFileName;

  @Override
  public Integer call() throws Exception {
    try {
      loadGraph loader = new loadGraph(
      graph,
      infoFileName,
      connectionsFileName
      );
      loader.load();

    } catch(FileNotFoundException e) {
      System.out.println("Error:  File Directory does not exist." + e.getMessage());
      return 1;

    }

    return 0;
  }
} 
