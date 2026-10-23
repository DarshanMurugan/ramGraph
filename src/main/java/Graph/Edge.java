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
  

  public int getSourceId() {
    return sourceId;  
  }

  public int getTargetId() {
    return targetId;
  }

  public String getRelation() {
    return relation;
  }
  
  public Map<String,Object> getproperties() {
    return properties;
  }

  public Object getProperty(String key) {
    return properties.getOrDefault(key,null);
  }
}
