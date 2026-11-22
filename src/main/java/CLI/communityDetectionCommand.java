package CLI;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import Graph.Graph;
import Graph.Node;
import Operations.communityDetection;

import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Command(name= "community", mixinStandardHelpOptions = true, version = "ramGraph 0.1.0",
    description = "Searches for communities in a graph using louvanes algorithm.")
public class communityDetectionCommand implements Callable<Integer> {
  Graph graph;
  public communityDetectionCommand(Graph graph) {
    this.graph = graph;
  }


  @Override
  public Integer call() throws Exception {
    
    if (!this.graph.isLoaded()) {
      System.out.println("Error: No Graph Loaded, run the 'load <infoFile> <connectionsFile>' command first.");
      return 1;
    }
    
    communityDetection commdetect = new communityDetection(this.graph);
    Map<Integer,Integer> communities = commdetect.detectCommunities();
    
    ArrayList<CommunityInfo> communityInfoArr = new ArrayList<>();
    for (Map.Entry<Integer, Integer> communityNode  : communities.entrySet()) {
      Node node = this.graph.getNodeById(communityNode.getKey());
      int edges = node.getIncoming().size();
      communityInfoArr.add(new CommunityInfo(communityNode.getValue(),node.getId(),edges));
    }
    
    int noOfCommunities = 0;

    //HashMap with communityid to a list of total nodes and totaledges.
    HashMap<Integer,List<Integer>> communityToInfo = new HashMap<>(); 

    for(CommunityInfo commInfo : communityInfoArr) {
      if (!communityToInfo.containsKey(commInfo.communityId())) {
        communityToInfo.put(
          commInfo.communityId(),
          new ArrayList<>(List.of(1,commInfo.noOfEdges()))

        );
      }
      else {
        List<Integer> info = communityToInfo.get(commInfo.communityId());
        info.set(0, info.get(0) + 1);
        info.set(1, info.get(1) + commInfo.noOfEdges());
      }
    }

    System.out.println("-------Communities------");
    System.out.println("Total Communities: "+ communityToInfo.size() + "\n");

    for (Map.Entry<Integer, List<Integer>> community  : communityToInfo.entrySet()) {
      System.out.println("Community: "+ community.getKey());
      System.out.println("    Nodes: "+ community.getValue().get(0));
      System.out.println("    Edges: "+ community.getValue().get(1));
      System.out.println();
    }

    return 0;
  }
} 


record CommunityInfo(
   int communityId,
   int nodeId,
   int noOfEdges
){}
