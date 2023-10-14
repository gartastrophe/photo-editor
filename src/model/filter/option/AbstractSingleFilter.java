package model.filter.option;

import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PixelUtil;

/**
 * A 'SingleFilter' represents a filter that is only applied to the
 * top pixel.  This was the primary filter type was used in Assignment
 * Four.
 */
public abstract class AbstractSingleFilter extends AbstractFilterOption {

  @Override
  public Pixel applyToColor(Pixel top, Pixel bottom) {
    //applies the 'SingleFilter' to only the top
    //pixel colour.
    Pixel modTop = this.applyToColor(top);
    //computes the final colour after combining
    //the modified top and overlaying it on the bottom.
    return PixelUtil.computeColour(modTop,bottom);
  }


  /**
   * This method provides backwards compatibility with the original
   * colour component and brightness filters.
   * @param color represents the top colour to modify
   * @return the new colour after applying the filter
   */
  protected abstract Pixel applyToColor(Pixel color);

  @Override
  public abstract String toString();


  /**
   * Represents an abstraction of applyToColour that can filter
   * by red, green, and blue such that the final colour only
   * keeps the red, green, or blue value.
   *
   * <p>The method also is also open to expansion in that it can
   * filter out specified component colours as well.  For example,
   * by setting the param includeGreen to false, the filter will
   * keep both the red and blue values--which would not be possible
   * in the original RGB arrangement.</p>
   *
   * @param c represents the colour that the
   *          filter should be applied to
   * @param includeRed represents if the method should
   *     keep the red values in the given colour
   * @param includeGreen represents if the method should
   *     keep the green values in the given colour
   * @param includeBlue represents if the method should
   *     keep the blue values in the given colour
   * @return the new RGB colour after applying the filter
   *     with the given colour's original alpha value.
   */
  protected Pixel filterComponents(Pixel c,
                                   boolean includeRed,
                                   boolean includeGreen,
                                   boolean includeBlue) {
    int red = includeRed ? c.getRed() : 0;
    int green = includeGreen ? c.getGreen() : 0;
    int blue = includeBlue ? c.getBlue() : 0;
    return new RGBPixel(red, green, blue, c.getAlpha());
  }

  /**
   * This protected method represents an abstraction of
   * brightness based filters.  This method augments the
   * brightness of the given colour by a given integer
   * brightness value, and a multiplier.
   *
   * @param c represents the colour that the
   *          filter should be applied to
   * @param brightness represents the type of
   *     brightness that should be added to all pixels
   * @param multiplier represents the number of times
   *     the brightness should be added to the colour
   * @return the augmented colour with the added or
   *     subtracted brightness values.
   */
  protected Pixel applyBrightness(Pixel c, int brightness, int multiplier) {
    //Ensures that red, green, and blue fall in the range 0-to-255 inclusive
    //even after applying the brightness filter.
    int red = this.inBounds(c.getRed() + (brightness * multiplier));
    int green = this.inBounds(c.getGreen() + (brightness * multiplier));
    int blue = this.inBounds(c.getBlue() + (brightness * multiplier));
    return new RGBPixel(red, green, blue, c.getAlpha());
  }


  //inBounds enforces the 0-to-255 colour constraints
  //by overriding any values less than zero with zero
  //and any values greater than 255 with 255.
  //In other words, inBounds ensures a floor of 0
  //and a ceiling of 255.
  //This also helps eliminate the need for this class
  //and other parts of the model to test for invalid
  //colours.
  private int inBounds(int value) {
    //declare a new int called temp
    int temp;
    //create a floor of zero such that:
    //if the given value is less than zero,
    //    set the temporary value to zero
    //otherwise, set temp to the given value.
    temp = Math.max(value, 0);
    //create a ceiling of 255 such that:
    //if the given value is greater than 255,
    //    set the temporary value to 255
    //otherwise, set temp to the given value.
    temp = Math.min(temp, 255);
    //return the new temp value:
    return temp;
  }
}
