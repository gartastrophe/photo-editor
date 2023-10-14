package model.filter.option.brightness;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents the BrightenIntensity filter type that can be used on
 * colours.  This class has one method whereby it can add the intensity
 * of a given colour to that colour.
 */
public class BrightenIntensity extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.applyBrightness(c, PixelUtil.getIntensity(c),1);
  }

  @Override
  public String toString() {
    return "brighten-intensity";
  }
}
