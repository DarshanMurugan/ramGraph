package Importer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import Graph.Edge;



public class importSnap{
  private File EdgesFile;
  private File featNamesFile;
  private File featuresFile;
  private File circlesFile;
  private File OutputDir;
  private String relation;

  public importSnap(
    File EdgesFile,
    File featuresFile,
    File featNamesFile,
    File circlesFile,
    File OutputDir,
    String relation
    ) {
    this.EdgesFile = EdgesFile;
    this.featuresFile = featuresFile;
    this.featNamesFile = featNamesFile;
    this.circlesFile = circlesFile;
    this.OutputDir = OutputDir;
    this.relation = relation;
  }


  private record edgeAllNodes(Set<String> allNodes, List<CSVFormatEdge> edges) {};

  public void importSnapFiles() throws IOException {
    Map<String,String> nodeProperties = loadNodes();
    edgeAllNodes res = loadEdges();
    writeToCsv(nodeProperties,res.allNodes(), res.edges());

  }

  private  void writeToCsv(Map<String,String> nodeProperties, Set<String> allNodes, List<CSVFormatEdge> edges) throws IOException{
    File nodesOut = new File(this.OutputDir, "nodes.csv");
    try (PrintWriter pw = new PrintWriter(new FileWriter(nodesOut));
         CSVWriter writer = new CSVWriter(pw)) {
        writer.writeNext(new String[]{"id","properties"});
        for (String nodeId : allNodes) {
          String props = nodeProperties.getOrDefault(nodeId,"");
          writer.writeNext(new String[]{nodeId,props});
      }
    }

    File edgesOut = new File(this.OutputDir, "edges.csv");
    try (PrintWriter pw = new PrintWriter(new FileWriter(edgesOut));
         CSVWriter writer = new CSVWriter(pw)) {
      writer.writeNext(new String[]{"sourceId","relation","targetId","properties"});
      for (CSVFormatEdge edge : edges) {
        writer.writeNext(new String[]{edge.sourceId,edge.relation,edge.targetId,edge.properties});
    }
  }
  }


  private edgeAllNodes loadEdges() throws IOException{
    List<CSVFormatEdge> edges = new ArrayList<>();
    Set<String> allNodes = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(this.EdgesFile))) {
      String line;
      while((line = reader.readLine()) != null) {
        if (line.isEmpty() || line.startsWith("#") || line.startsWith("%")) continue;

        String[] parts = line.split("\\s+");
        if(parts.length >= 2) {
          String sourceId = parts[0];
          String targetId = parts[1];
          allNodes.add(sourceId);
          allNodes.add(targetId);
          edges.add(new CSVFormatEdge(sourceId,this.relation,targetId,""));
        }
      }
    }
    return (new edgeAllNodes(allNodes,edges));
  }

  private Map<String, String> loadNodes() throws IOException{
    Map<String, String> nodeProperties = new HashMap<>();
    Map<Integer,String> featureNames = loadPropertyNames();

    if (this.featuresFile != null && this.featuresFile.exists()){
      try (BufferedReader reader = new BufferedReader(new FileReader(this.featuresFile))){
        String line;
        while((line = reader.readLine()) != null) {
          if (line.trim().isEmpty() || line.startsWith("#")) continue;
          String[] parts = line.trim().split("\\s+");
          String nodeId = parts[0];

          StringBuilder propBuilder = new StringBuilder();
          for(int i =1; i < parts.length; i++) {
            if ("1".equals(parts[i]) && featureNames.containsKey(i-1)) {
                propBuilder.append(featureNames.get(i-1)).append("=\"true\";");
            }
          }
          nodeProperties.put(nodeId,propBuilder.toString());
        }
      }

    }

    if (this.circlesFile != null && this.circlesFile.exists()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(this.circlesFile))) {
        String line;
        while((line = reader.readLine()) != null) {
          if (line.trim().isEmpty() || line.startsWith("#")) continue;
          String[] parts = line.trim().split("\\t");

          if (parts.length < 2) continue;

          String circleName = parts[0].split("\\s+")[0];

          for(int i = 1; i < parts.length; i++){
            String nodeId = parts[i];
            String existingProps = nodeProperties.getOrDefault(nodeId,"");
            nodeProperties.put(nodeId,existingProps + "community=" + circleName + ";");
          }

        }
      }
    }
    return nodeProperties;
  }

  private Map<Integer, String> loadPropertyNames() throws IOException {
    Map<Integer, String> propertyNames =  new HashMap<>();
    if (this.featNamesFile == null || !this.featNamesFile.exists()) return propertyNames;

    try (BufferedReader reader = new BufferedReader(new FileReader(this.featNamesFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty() || line.startsWith("#")) continue;
        String[] parts = line.trim().split(" ",2);
        if (parts.length == 2) {
          int index = Integer.parseInt(parts[0]);

          String cleanFeatureName = parts[1].replace(";anonymized features ","_")
                                  .replace(";", "_").replaceAll("[^a-zA-Z0-9_]", "_");
          propertyNames.put(index,cleanFeatureName);
        }
      }
    }
    return propertyNames;
  }
}

class CSVFormatEdge {
  String sourceId;
  String relation;
  String targetId;
  String properties;
  CSVFormatEdge(String sourceId, String relation, String targetId, String properties) {
    this.sourceId = sourceId;
    this.relation = relation;
    this.targetId = targetId;
    this.properties = properties;
  }
}
