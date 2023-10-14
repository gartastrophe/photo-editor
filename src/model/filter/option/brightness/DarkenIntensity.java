package model.filter.option.brightness;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents the DarkenIntensity filter type that can be used on
 * colours.  This class has one method whereby it can subtract the
 * intensity of a given colour from that colour.
 */
public class DarkenIntensity extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.applyBrightness(c, PixelUtil.getIntensity(c),-1);
  }

  @Override
  public String toString() {
    return "darken-intensity";
  }
}
