package Operations;

import Graph.Graph;
import Graph.Node;
import Graph.Edge;

import java.util.Set;
import java.util.Map;

import java.util.*;


public class communityDetection {
  private Graph graph;

  public communityDetection(Graph graph) {
    this.graph = graph;
  }

  public Map<Integer,Integer> detectCommunities() {
    Map<Integer,Integer> communities = createCommunityIds();

    boolean changed = true;

    while (changed) {
      changed = false;

      for (Node node: this.graph.getGraphNodes()) {
        int currentCommunityId = communities.get(node.getId());

        double currentModularity = calculateModularity(communities);

        int bestCommunity = currentCommunityId;
        double bestModularity = currentModularity;
        
        Set<Integer> candidates = new HashSet<>();

        List<Edge> edges = new ArrayList<>(node.getIncoming());
        edges.addAll(node.getOutgoing());
        for(Edge edge: edges ) {
          int neighbour;
          if (edge.getTargetId() == node.getId()) 
            neighbour = edge.getSourceId();
          else 
            neighbour = edge.getTargetId();

          candidates.add(communities.get(neighbour));
        }
        
        for (int candidate : candidates) {
          if (candidate == currentCommunityId) 
            continue;

          communities.put(node.getId(), candidate);

          double newModularity = calculateModularity(communities);

          if (newModularity > bestModularity + 1e-10) {
            bestModularity = newModularity;
            bestCommunity = candidate;
          }

          communities.put(node.getId(),currentCommunityId); 
        }

        if (bestCommunity != currentCommunityId) {
          communities.put(node.getId(), bestCommunity);
          changed = true;
        }

      }
    }
    return communities;
  }
  
  private Map<Integer,Integer> createCommunityIds() {
    Map<Integer,Integer> communities = new HashMap<>();
    int communityId = 0;

    for(int nodeId: this.graph.getGraphIds()) {
      communities.put(nodeId, communityId);
      communityId++;
    }
    return communities;
  }


  private double calculateModularity(Map<Integer,Integer> communities) {
    int totalDegree = 0;
    for (Node node: this.graph.getGraphNodes()) {
      totalDegree += node.getIncoming().size() + node.getOutgoing().size();
    }

    double m = totalDegree / 2.0;

    if (m == 0) return 0;

    Map<Integer,Integer>  degreeSum = new HashMap<>();
    Map<Integer,Integer>  internalEdges = new HashMap<>();

    for (Node node: this.graph.getGraphNodes()) {
      int communityId = communities.get(node.getId());

      degreeSum.merge(
        communityId,
        node.getIncoming().size() + node.getOutgoing().size(),
        Integer::sum
        );
      List<Edge> edges = new ArrayList<>(node.getIncoming());
      edges.addAll(node.getOutgoing());
      for(Edge edge: edges ) {
        int neighbour;
        if (edge.getTargetId() == node.getId()) 
           neighbour = edge.getSourceId();
        else 
           neighbour = edge.getTargetId();

        if (communities.get(node.getId()).equals(
              communities.get(neighbour)
        )) {
          internalEdges.merge(
            communityId, 1, Integer::sum
          );
        }
      }
    }

    double modularity = 0;

    for (int communityId : degreeSum.keySet()) {
      double edgeNo = internalEdges.getOrDefault(
            communityId, 0
      ) / 2.0;

      double degree = degreeSum.get(communityId);

      modularity += edgeNo / m - Math.pow(degree /(2*m),2);

    }
    return modularity;
  }
}
