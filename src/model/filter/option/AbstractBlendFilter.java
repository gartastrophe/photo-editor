package model.filter.option;

import model.pixel.HSLPixel;
import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 * Represents a filter for blending multiple Pixel colours.
 */
public abstract class AbstractBlendFilter extends AbstractFilterOption {

  @Override
  public abstract Pixel applyToColor(Pixel top, Pixel bottom);

  /**
   * This method abstracts out altering blending filters
   * that change the lightness of the top Pixel.
   * @param top represents the top pixel
   * @param bottom represents the bottom pixel
   * @param newLightTop represents the new lightness to override with
   * @return the new Pixel representation
   */
  protected Pixel overWriteLightness(Pixel top, Pixel bottom, double newLightTop) {

    //perform the filter
    Pixel topTemp = new HSLPixel(
            top.getHue(),
            top.getSaturation(),
            newLightTop);
    //note: get-HSL methods in Pixel already convert to RGB first
    //before calculating the HSL values; therefore, it is not
    //necessary to convert here.

    //convert the HSL back to RGBA
    Pixel rgbaTop = PixelUtil.toFourComponent(topTemp,top.getAlpha());

    //computes the final colour of the filter-applied top over the bottom
    return PixelUtil.computeColour(rgbaTop,bottom);
  }


  @Override
  public abstract String toString();
}
