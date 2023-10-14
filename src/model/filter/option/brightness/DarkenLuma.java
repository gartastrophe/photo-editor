package model.filter.option.brightness;

import model.filter.option.AbstractSingleFilter;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents the DarkenLuma filter type that can be used on
 * colours.  This class has one method whereby it can subtract
 * the luminance of a given colour from that colour.
 */
public class DarkenLuma extends AbstractSingleFilter {
  @Override
  public Pixel applyToColor(Pixel c) {
    return super.applyBrightness(c, PixelUtil.getLuma(c),-1);
  }

  @Override
  public String toString() {
    return "darken-luma";
  }

}
