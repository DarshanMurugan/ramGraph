package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

@Command(name= "ramGraph", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "An RAM based graph DB."
    )
public class ramGraphCommand implements Runnable {
  @Override
  public void run() {
    System.out.println("Use --help to see available commands.");
  }
}
