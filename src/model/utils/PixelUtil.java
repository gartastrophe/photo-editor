package model.utils;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * Represents extra utility to be added to the Pixel class.  Namely,
 * this class enables other classes to get different metrics of brightness:
 * the value, the intensity, and the luma of a given colour through static
 * methods.
 *
 * <p>It also enables the client and other classes to convert
 * between RGB and RGBA and vice versa.</p>
 */
public class PixelUtil {

  /**
   * Represents a measure of "brightness" or "intensity".
   * Returns the maximum value of the three components for each pixel.
   */
  public static int getValue(Pixel color) {
    return Math.max(color.getRed(),Math.max(color.getGreen(),color.getBlue()));
  }

  /**
   * Represents a measure of "brightness" or "intensity".
   * Returns the average of the three components for each pixel.
   */
  public static int getIntensity(Pixel color) {
    return Math.toIntExact(Math.round((
            color.getRed()
                    + color.getGreen()
                    + color.getBlue()) / 3.0));
  }

  /**
   * Represents a measure of "brightness" or "intensity".
   * Returns the weighted sum of 0.2126r + 0.7152g + 0.0722b
   * where r, g, & b represent the colours red, green, and blue,
   * respectively.
   */
  public static int getLuma(Pixel color) {
    return Math.toIntExact(Math.round(
            (0.2126) * color.getRed()
                    + (0.7152) * color.getGreen()
                    + (0.0722) * color.getBlue()));
  }

  /**
   * Computes the resultant colour after overlaying
   * the given top Colour over the given bottom Colour.
   */
  public static Pixel computeColour(Pixel top, Pixel bottom) {
    double alphaPrime =
            (top.getAlpha() / 255.0)
                    + (bottom.getAlpha() / 255.0)
                    * (1 - (top.getAlpha() / 255.0));
    double redPrime = ((top.getAlpha() / 255.0)
            * top.getRed() + bottom.getRed()
            * (bottom.getAlpha() / 255.0)
            * (1 - (top.getAlpha() / 255.0)))
            * (1 / alphaPrime);
    double greenPrime = ((top.getAlpha() / 255.0)
            * top.getGreen() + bottom.getGreen()
            * (bottom.getAlpha() / 255.0)
            * (1 - (top.getAlpha() / 255.0)))
            * (1 / alphaPrime);
    double bluePrime = ((top.getAlpha() / 255.0)
            * top.getBlue() + bottom.getBlue()
            * (bottom.getAlpha() / 255.0)
            * (1 - (top.getAlpha() / 255.0)))
            * (1 / alphaPrime);
    return new RGBPixel(
            Math.toIntExact(Math.round(redPrime)),
            Math.toIntExact(Math.round(greenPrime)),
            Math.toIntExact(Math.round(bluePrime)),
            Math.toIntExact(Math.round(alphaPrime * 255)));
  }

  /**
   * Converts a given four-component colour to a three-component
   * colour.
   */
  public static Pixel toThreeComponent(Pixel color) {
    return new RGBPixel(
            Math.toIntExact(Math.round(color.getRed() * (color.getAlpha() / 255.0))),
            Math.toIntExact(Math.round(color.getGreen() * (color.getAlpha() / 255.0))),
            Math.toIntExact(Math.round(color.getBlue() * (color.getAlpha() / 255.0))));
  }


  /**
   * Converts a given three-component RGB colour to a four-component
   * RGBA colour.  This method does NOT throw an exception when the
   * given Pixel colour has its own alpha value; it ignores that Pixel's
   * alpha and proceeds with the given alpha.
   * @param color represents the RGB colour to convert to RGBA
   * @param alpha represents the RGBA alpha to use for the conversion
   * @throws IllegalArgumentException when the given alpha is not
   *     in between the values 0 and 255 inclusive
   */
  public static Pixel toFourComponent(Pixel color, int alpha)
          throws IllegalArgumentException {
    if (alpha > 255 || alpha < 0) {
      throw new IllegalArgumentException("alpha must be between 0 and 255, inclusive");
    } else if (alpha == 0) {
      //if the alpha is zero, the RGB fundamentally does not matter
      //also it would be impossible to reconstruct the RGB
      //so set the RGB to [0,0,0,0], the default
      return new RGBPixel(0,0,0,0);
    } else {
      //from the previous if-else,
      //we can guarantee that alpha is not zero
      //therefore we can divide by zero here
      return new RGBPixel(
              cap(Math.toIntExact(Math.round(color.getRed() / (alpha / 255.0))),0,255),
              cap(Math.toIntExact(Math.round(color.getGreen() / (alpha / 255.0))),0,255),
              cap(Math.toIntExact(Math.round(color.getBlue() / (alpha / 255.0))),0,255),
              alpha);
    }
  }

  //caps the minimum and maximum values for the target
  //number such that if the target is under the min,
  //it is replaced by the min; if the target is greater
  //than the max, it is replaced by the max
  private static int cap(int target, int min, int max) {
    int temp;
    temp = Math.max(target,min);
    temp = Math.min(temp,max);
    return temp;
  }


}
