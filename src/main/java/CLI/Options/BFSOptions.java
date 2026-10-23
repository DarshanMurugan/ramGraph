package Options;

import java.util.Set;
import java.util.Collections;
public class BFSOptions {
  private Set<String> relationType;
  private int degree;
  private Direction direction;


  public enum Direction {
    INCOMING,
    OUTGOING,
    BOTH
  }

  public BFSOptions(Set<String> relationType,int degree,Direction direction) {
    this.relationType = relationType;
    this.degree = degree;
    this.direction = direction;
  }


  public Set<String> getRelationType() {
    return Collections.unmodifiableSet(this.relationType);
  }

  public int getDegree() {
    return this.degree;
  }

  public Direction getDirection() {
    return this.direction;
  }

}
