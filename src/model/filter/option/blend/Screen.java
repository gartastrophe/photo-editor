package model.filter.option.blend;

import model.filter.option.AbstractBlendFilter;
import model.pixel.Pixel;

/**
 * Represents a type of filter that blends the top and bottom
 * colours in some way.  This filter does the following:
 * - brightens the top Pixel without overexposing it
 */
public class Screen extends AbstractBlendFilter {

  @Override
  public Pixel applyToColor(Pixel top, Pixel bottom) {
    //abstract to the superclass
    return super.overWriteLightness(top,bottom,
            //the following is the Screen formula
            (1 - ((1 - top.getLightness()) * (1 - bottom.getLightness()))));
  }

  @Override
  public String toString() {
    return "screen";
  }

}
