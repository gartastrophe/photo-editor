package model.filter.option.colour.components;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;

/**
 * Represents the RedComponent filter type that can be used
 * on colours.  This method has one method whereby it can
 * isolate the red component of a given colour and return
 * a new colour with only that component (all other components
 * are set to zero).
 */
public class RedComponent extends AbstractSingleFilter {

  @Override
  protected Pixel applyToColor(Pixel c) {
    return super.filterComponents(c,true,false,false);
  }

  @Override
  public String toString() {
    return "red-component";
  }

}
