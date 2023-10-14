package model.pixel;

/**
 * Represents a Hue-Saturation-Lightness representation of a Pixel.
 * This class allows the client to create a Pixel using this representation
 * through the HSLPixel Constructor.
 */
public class HSLPixel extends AbstractPixel {

  /**
   * Represents a constructor for an HSL Pixel.
   * @param hue represents the hue of the Pixel
   * @param saturation represents the saturation of the Pixel
   * @param lightness represents the lightness of a Pixel
   */
  public HSLPixel(double hue,double saturation,double lightness) {
    //cannot be null cause primitives
    super(hue,saturation,lightness);
  }
}
