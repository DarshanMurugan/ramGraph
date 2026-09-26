package Graph;
import com.opencsv.bean.CsvBindByName;
import java.util.List;
import java.util.ArrayList;
public class Node {
  
  @CsvBindByName
  private int id;
  
  @CsvBindByName
  private String name;

  @CsvBindByName
  private int age;
  
  private List<Node> connections = new ArrayList<>();

  public Node() {}

  public void addConnection(Node node) {
    connections.add(node);
  }

  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }

  public int getAge(){
    return age;
  }
  public List<Node> getConnections() {
    return connections;
  }
}
