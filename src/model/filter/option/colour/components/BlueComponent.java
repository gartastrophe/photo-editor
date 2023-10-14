package model.filter.option.colour.components;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;

/**
 * Represents the BlueComponent filter type that can be used
 * on colours.  This method has one method whereby it can
 * isolate the blue component of a given colour and return
 * a new colour with only that component (all other components
 * are set to zero).
 */
public class BlueComponent extends AbstractSingleFilter {

  protected Pixel applyToColor(Pixel c) {
    return super.filterComponents(c,false,false,true);
  }

  @Override
  public String toString() {
    return "blue-component";
  }

}
