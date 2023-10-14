package model.filter.option.brightness;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents the BrightenValue filter type that can be used on
 * colours.  This class has one method whereby it can add the
 * brightness value of a given colour to that colour.
 */
public class BrightenValue extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.applyBrightness(c, PixelUtil.getValue(c),1);
  }

  @Override
  public String toString() {
    return "brighten-value";
  }

}
