
package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;
import java.io.*;
import Importer.importSnap;

import Graph.Graph;
import Operations.modifyGraph;
import java.util.concurrent.Callable;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Option;

@Command(name= "import", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "import graphs from different format like SNAP",
    subcommands = {
        importCommand.snap.class,
    }
  )
public class importCommand implements Callable {
    public importCommand(){}
    public Integer call() {
      System.out.println("Specify a subcommand, use import --help for help.");
      return 1;
    }
   @Command(name="snap", mixinStandardHelpOptions=true,description="import graphs in SNAP format.")
   static class snap implements Callable {
      @Option(names = {"-e", "--edges"}, description = "Path to SNAP edges file (.edges or .txt)", required = true)
      private File edgesFile;

      @Option(names = {"-f", "--features"}, description = "Path to SNAP feature file (.feat - optional)")
      private File featuresFile;

      @Option(names = {"-fn", "--featnames"}, description = "Path to SNAP feature names file (.featnames - optional)")
      private File featNamesFile;

      @Option(names = {"-c", "--circles"}, description = "Path to SNAP circles file (.circles - optional)")
      private File circlesFile;

      @Option(names = {"-o", "--output"}, description = "Output directory for ramGraph CSVs", defaultValue = ".")
      private File outputDir;

      public Integer call() {
        outputDir.mkdirs();
        try {
          importSnap snap = new importSnap(edgesFile,featuresFile,featNamesFile,circlesFile,outputDir,"friends");
          snap.importSnapFiles();
        } catch(IOException e) {return 0;}
      
      return 1;
      }
    }
  }

