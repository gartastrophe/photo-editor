package model.filter.option.brightness;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents the DarkenValue filter type that can be used on
 * colours.  This class has one method whereby it can subtract
 * the brightness value of a given colour from that colour.
 */
public class DarkenValue extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.applyBrightness(c, PixelUtil.getValue(c),-1);
  }

  @Override
  public String toString() {
    return "darken-value";
  }

}
