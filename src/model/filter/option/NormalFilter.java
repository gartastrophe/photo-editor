package model.filter.option;

import model.pixel.Pixel;
import model.utils.PixelUtil;

/**
 *   Represents a filter that does nothing to a colour.
 */
public class NormalFilter extends AbstractFilterOption {

  /**
   * This method circumvents the filter application.  Specifically,
   * it calls PixelUtil to compute the rasterized colour as there
   * are no special operations.
   * @param top represents the colour on top that should be filtered
   * @param bottom represents the colour on the bottom of the filter
   * @return the new combined Colour for the top
   */
  @Override
  public Pixel applyToColor(Pixel top, Pixel bottom) {
    return PixelUtil.computeColour(top,bottom);
  }

  @Override
  public String toString() {
    return "normal";
  }

}
