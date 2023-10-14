package model.filter.option;

import java.util.Objects;

import model.pixel.Pixel;

/**
 * Represents an abstract filter to consolidate common code.
 * This class overrides equality, hashcode, and toString.
 */
public abstract class AbstractFilterOption implements FilterOption<Pixel> {

  @Override
  public abstract Pixel applyToColor(Pixel top, Pixel bottom);

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FilterOption)) {
      //if the given Object is not a FilterOption, it is not the same FilterOption as this.
      return false;
    } else {
      return this.toString().equals(obj.toString());
    }
  }

  @Override
  public int hashCode() {
    //the same three colours are used to override the hashCode
    return Objects.hash(this.toString());
  }

  @Override
  public abstract String toString();
}
