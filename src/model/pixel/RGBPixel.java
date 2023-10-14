package model.pixel;


/**
 * Represents an implementation of a RGBPixel with
 * a maximum value of 255.
 */
public class RGBPixel extends AbstractPixel {

  /**
   * Represents the full constructor for RGBPixel that
   * enforces maximum and minimum values of 0 and 255,
   * respectively.  Any values given over or under 255
   * will be automatically reverted to the closest valid
   * RGB value.
   * @param red represents the red value of the model.pixel.RGBPixel
   * @param green represents the green value of the model.pixel.RGBPixel
   * @param blue represents the blue value of the model.pixel.RGBPixel
   */
  public RGBPixel(int red, int green, int blue, int alpha) {
    //cannot be null cause primitive
    super(red,green,blue,alpha);
  }

  /**
   * Represents a convenience constructor for RGBPixel that
   * automatically assumes that the alpha is at 255.
   * @param red represents the red value of the model.pixel.RGBPixel
   * @param green represents the green value of the model.pixel.RGBPixel
   * @param blue represents the blue value of the model.pixel.RGBPixel
   */
  public RGBPixel(int red, int green, int blue) {
    this(red,green,blue,255);
  }
}
