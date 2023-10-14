package model.filter.option.colour.components;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;

/**
 * Represents the GreenComponent filter type that can be used
 * on colours.  This method has one method whereby it can
 * isolate the green component of a given colour and return
 * a new colour with only that component (all other components
 * are set to zero).
 */
public class GreenComponent extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.filterComponents(c,false,true,false);
  }

  @Override
  public String toString() {
    return "green-component";
  }

}
