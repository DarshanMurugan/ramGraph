import Graph.Graph;
import CLI.*;
import java.util.Scanner;

import picocli.CommandLine;



public class Main {
  public static void main(String[] args) throws Exception {
    Graph graph = new Graph();

    CommandLine cmd =  new CommandLine(
      new ramGraphCommand()
    );

    cmd.addSubcommand("load", new loadCommand(graph));
    cmd.addSubcommand("bfs", new BFSCommand(graph));
    cmd.addSubcommand("modifyGraph", new modifyGraphCommand(graph));
    cmd.addSubcommand("selectGraph", new selectGraphCommand(graph));
    cmd.addSubcommand("export", new exportCommand(graph));
    cmd.addSubcommand("import", new importCommand());
    cmd.addSubcommand("community", new communityDetectionCommand(graph));
    Scanner scanner = new Scanner(System.in);

    while(true){
      System.out.print("ramGraph>");
      
      String input = scanner.nextLine().trim();

      if (input.equals("exit")){
        break;
      }

      if (input.isEmpty()) {
        continue;
      }

      String[] commandArgs = input.split("\\s+");
      cmd.execute(commandArgs);
    }
  }
} 
