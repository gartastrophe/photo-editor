package model.pixel;

import java.util.Objects;

/**
 * This AbstractPixel represents a Pixel with default RGBA
 * values of red, green, blue, and alpha.  This class also
 * allows for the conversion to HSL (hue, saturation, and
 * lightness) and converting from HSL to RGB and RGB to HSL.
 *
 * <p>The class specifically has observers to the
 * red, green, blue, alpha values along with the
 * corresponding hue, saturation, and lightness.</p>
 *
 * <p>The class further returns a String representation
 * of itself for the project as a whole with the toProjString
 * method and overwrites equality such that two Abstract
 * Pixels are the same if their corresponding RGBA values
 * are identical.</p>
 */
public abstract class AbstractPixel implements Pixel {
  //internally, RGBPixel is stored as doubles
  //to preserve data integrity
  protected final double red;
  protected final double green;
  protected final double blue;
  protected final double alpha;

  /**
   * This constructor is used to initialise the final RGBA values
   * from the given RGBA values.
   * @param red represents the red values of an AbstractPixel
   * @param green represents the green values of an AbstractPixel
   * @param blue represents the blue values of an AbstractPixel
   * @param alpha represents the alpha values of an AbstractPixel
   */
  protected AbstractPixel(int red,int green,int blue,int alpha) {
    //ensures the data is within the bounds of 0 and 255,
    //inclusive and stores the data as a percentage of 255
    //to preserve data integrity.
    this.red = this.enforceInBounds(red,0,255,"red") / 255.0;
    this.green = this.enforceInBounds(green,0,255,"green") / 255.0;
    this.blue = this.enforceInBounds(blue,0,255,"blue") / 255.0;
    this.alpha = this.enforceInBounds(alpha,0,255,"alpha") / 255.0;
  }

  /**
   * This constructor is used to initialise the final RGBA values
   * from the given HSL values.
   * @param hue represents the hue from the HSL
   * @param saturation represents the saturation from the HSL
   * @param lightness represents the lightness from the HSL
   */
  protected AbstractPixel(double hue, double saturation, double lightness) {
    double tempHue = this.enforceInBounds(hue,0,360,"hue");
    double tempSaturation = this.enforceInBounds(saturation,0,1,"saturation");
    double tempLightness = this.enforceInBounds(lightness,0,1,"lightness");

    //a hue of 360 is the same as a hue of 0
    if (tempHue == 360) {
      tempHue = 0;
    }

    //send through the converter
    double[] rgb = this.convertHSLtoRGB(
            tempHue,
            tempSaturation,
            tempLightness);

    //init data
    this.red = rgb[0];
    this.green = rgb[1];
    this.blue = rgb[2];
    this.alpha = 1.0; //an alpha of 1.0 represents an 100% alpha
  }

  /**
   * enforceInBounds enforces the given colour constraints
   * by overriding any values less than min with min
   * and any values greater than max with max.
   * For example, enforceInBounds ensures a floor of 0
   * and a ceiling of 255 for a 0-to-255 representation
   * of RGB.
   *
   * <p>This also helps eliminate the need for this class
   * and other classes that implement this one to throw
   * exceptions when given a colour outside of its range.</p>
   *
   * @param target represents the value to test
   * @param min represents the minimum allowed value
   * @param max represents the maximum allowed value
   * @param name represents the name of the target value
   * @return the target value if it is in bounds
   * @throws IllegalArgumentException when not in bounds
   */
  protected double enforceInBounds(double target, double min, double max,String name)
          throws IllegalArgumentException {

    if (target < min || target > max) {
      throw new IllegalArgumentException("The " + name + " value cannot be less than "
              + min + " or greater than " + max + ".");
    }
    return target;
  }

  @Override
  public int getRed() {
    return Math.toIntExact(Math.round(red * 255));
  }

  @Override
  public int getGreen() {
    return Math.toIntExact(Math.round(green * 255));
  }

  @Override
  public int getBlue() {
    return Math.toIntExact(Math.round(blue * 255));
  }

  @Override
  public int getAlpha() {
    return Math.toIntExact(Math.round(alpha * 255));
  }

  @Override
  public double getHue() {
    //converts this Pixel from RGBA to RGB
    double[] asRGB = this.rgbaToRGB();

    //use the RGB (instead of RGBA) to calculate the HSL
    double[] hsl = this.convertRGBtoHSL(
            asRGB[0],
            asRGB[1],
            asRGB[2]);

    //return HSL
    return hsl[0];
  }

  @Override
  public double getSaturation() {
    //converts this Pixel from RGBA to RGB
    double[] asRGB = this.rgbaToRGB();

    //use the RGB (instead of RGBA) to calculate the HSL
    double[] hsl = this.convertRGBtoHSL(
            asRGB[0],
            asRGB[1],
            asRGB[2]);

    //return HSL
    return hsl[1];
  }

  @Override
  public double getLightness() {
    //converts this Pixel from RGBA to RGB
    double[] asRGB = this.rgbaToRGB();

    //use the RGB (instead of RGBA) to calculate the HSL
    double[] hsl = this.convertRGBtoHSL(
            asRGB[0],
            asRGB[1],
            asRGB[2]);

    //return HSL
    return hsl[2];
  }

  //this method converts the RGBA to RGB.
  //while this is similar to toThreeComponent
  //in PixelUtil, it has enhanced accuracy
  //by accessing the private (more accurate)
  //colour components of Pixel.
  private double[] rgbaToRGB() {
    //size of three
    double[] rgb = new double[3];

    rgb[0] = red * alpha;
    rgb[1] = green * alpha;
    rgb[2] = blue * alpha;

    return rgb;
  }



  /**
   * Returns RGBA representation of this
   * AbstractPixel in Project format.  This
   * format is a space-separated 255-max RGBA.
   * @return the string representation of
   *     this AbstractPixel in Project format
   */
  @Override
  public String toProjString() {
    //if the alpha is zero, return
    //a completely transparent black
    //pixel for consistency (it would
    //look the same no matter what).
    if (this.alpha == 0) {
      return "0 0 0 0 ";
    }

    //otherwise return the normal format
    return String.format("%d %d %d %d ",
            this.getRed(),
            this.getGreen(),
            this.getBlue(),
            this.getAlpha());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Pixel)) {
      //if the given Object is not a Pixel, it is not the same as this.
      return false;
    } else {
      //if the RGBA values are the same, this should be the same colour
      return this.toProjString().equals(((Pixel) obj).toProjString());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.toProjString());
  }


  /**
   * Converts an RGB representation in the range 0-1 into an HSL
   * representation.  Specifically, where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * @param r red value of the RGB between 0 and 1
   * @param g green value of the RGB between 0 and 1
   * @param b blue value of the RGB between 0 and 1
   */
  protected double[] convertRGBtoHSL(double r, double g, double b) {
    double componentMax = Math.max(r, Math.max(g, b));
    double componentMin = Math.min(r, Math.min(g, b));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue;
    double saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == r) {
        hue = (g - b) / delta;
        while (hue < 0) {
          hue += 6; //hue must be positive to find the appropriate modulus
        }
        hue = hue % 6;
      } else if (componentMax == g) {
        hue = (b - r) / delta;
        hue += 2;
      } else if (componentMax == b) {
        hue = (r - g) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }

    double[] hsl = new double[3];
    hsl[0] = squeezeInBounds(hue,0,360,true);
    hsl[1] = squeezeInBounds(saturation,0,1,false);
    hsl[2] = squeezeInBounds(lightness,0,1,false);

    return hsl;
  }

  //this method should address a potential bug in convertRGBtoHSL
  //that could allow non-conforming hsl values to be exposed through
  //rounding or the like. this method will ensure that that bug will
  //never occur by "squeezing" the hsl values into conformity.
  private double squeezeInBounds(double target,double min,double max,boolean maxMinSame) {
    //define a temporary variable
    double temp;
    //if the temp is below the min, set it to the min
    temp = Math.max(target,min);
    //if the temp is greater than the max, set it to the max
    temp = Math.min(temp,max);
    //if the max is functionally identical to the min, use the min value
    temp = maxMinSame ? (temp == max ? 360 : temp) : temp;
    return temp;
  }


  /**
   * Converts RGB to an HSL representation.  Specifically, where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-1
   * @param hue hue of the HSL representation
   * @param saturation saturation of the HSL representation
   * @param lightness lightness of the HSL representation
   */
  protected double[] convertHSLtoRGB(double hue, double saturation, double lightness) {
    double r = convertFn(hue, saturation, lightness, 0);
    double g = convertFn(hue, saturation, lightness, 8);
    double b = convertFn(hue, saturation, lightness, 4);

    double[] rgb = new double[3];
    rgb[0] = r;
    rgb[1] = g;
    rgb[2] = b;

    return rgb;
  }

  /*
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a  = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

}
