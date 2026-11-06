package Graph;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import java.util.HashMap;
import java.util.Map;

import Builder.KeyValueBuilder;




public class Edge {

  @CsvBindByName(column="sourceId")
  private int sourceId;
  
  @CsvBindByName(column="relation")
  private String relation;

  @CsvBindByName(column="targetId")
  private int targetId;

  @CsvCustomBindByName(column="properties", converter=KeyValueBuilder.class)
  private Map<String,Object> properties;
  

  //opencsv needs a no argument constructor therefore this, not meant to be used for creating an edge object.
  public Edge(){};

  public Edge(int sourceId,String relation, int targetId,Map<String,Object> properties){
    this.sourceId = sourceId;
    this.relation = relation;
    this.targetId = targetId;
    this.properties = properties;
  }
  

  public int getSourceId() {
    return this.sourceId;  
  }

  public int getTargetId() {
    return this.targetId;
  }

  public String getRelation() {
    return this.relation;
  }
  
  public Map<String,Object> getProperties() {
    return this.properties;
  }

  public Object getProperty(String key) {
    return this.properties.getOrDefault(key,null);
  }
}
