package model.pixel;

/**
 * Represents a pixel of a certain colour with a certain format.
 */
public interface Pixel {

  /**
   * Returns the rgb red value.
   * @return the rgb red representation.
   */
  int getRed();

  /**
   * Returns the rgb green value.
   * @return the rgb green representation.
   */
  int getGreen();


  /**
   * Returns the rgb blue value.
   * @return the rgb blue representation.
   */
  int getBlue();

  /**
   * Returns the rgb alpha value.
   * @return the rgb alpha representation.
   */
  int getAlpha();

  /**
   * Returns the HSL hue value.
   * @return the HSL hue representation.
   */
  double getHue();

  /**
   * Returns the HSL saturation value.
   * @return the HSL saturation representation.
   */
  double getSaturation();

  /**
   * Returns the HSL lightness value.
   * @return the HSL lightness representation.
   */
  double getLightness();

  /**
   * The toProjString method to produce
   * a String representation of the Pixel.
   * @return the String representation of the
   *     given pixel.
   */
  String toProjString();
}
