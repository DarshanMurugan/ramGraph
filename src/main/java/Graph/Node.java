package Graph;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import Graph.Edge;

import Builder.KeyValueBuilder;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Node {
  @CsvBindByName
  @JsonSerialize(using = ToStringSerializer.class)
  private int id;

  @CsvCustomBindByName(column = "properties", converter = KeyValueBuilder.class)
  private Map<String, Object> properties;
  
  private List<Edge> incoming = new ArrayList<Edge>();

  private List<Edge> outgoing = new ArrayList<Edge>();

  public int getId() {
    return id;
  }

  public List<Edge> getOutgoing() {
    return  Collections.unmodifiableList(outgoing);
  }

  public List<Edge> getIncoming() {
    return Collections.unmodifiableList(incoming);
  }


  public void addOutgoingEdge(Edge edge) {
    outgoing.add(edge);
  }

  public void addIncomingEdge(Edge edge) {
    incoming.add(edge);
  }

  public void setProperty(String key, Object value) {
    properties.put(key,value);
  }

  public Object getProperty(String key){
    return properties.getOrDefault(key,null);
  }

  public Map<String,Object> getProperties() {
    return properties;
  }
}
