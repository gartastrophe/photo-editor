package model.filter.option.blend;


import model.filter.option.AbstractFilterOption;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PixelUtil;

/**
 * Represents a type of filter that blends the top and bottom
 * colours in some way.  This filter does the following:
 * - inverts the colors of an image on the current layer
 */
public class Difference extends AbstractFilterOption {

  @Override
  public Pixel applyToColor(Pixel top, Pixel bottom) {

    //get top components in RGB
    Pixel topTemp = PixelUtil.toThreeComponent(top);
    int redRGB = topTemp.getRed();
    int greenRGB = topTemp.getGreen();
    int blueRGB = topTemp.getBlue();

    //get bottom components in RGB
    Pixel bottomTemp = PixelUtil.toThreeComponent(bottom);
    int redRGBBg = bottomTemp.getRed();
    int greenRGBBg = bottomTemp.getGreen();
    int blueRGBBg = bottomTemp.getBlue();

    //compute the difference of the RGB values
    int redPrime = Math.abs(redRGB - redRGBBg);
    int greenPrime = Math.abs(greenRGB - greenRGBBg);
    int bluePrime = Math.abs(blueRGB - blueRGBBg);

    //create a new topPrime colour with the RGB values
    Pixel topPrimeRGB = new RGBPixel(redPrime,greenPrime,bluePrime);

    //convert the topPrimeRGB to an RGBA using teh original alpha value
    Pixel topPrimeRGBA = PixelUtil.toFourComponent(topPrimeRGB,top.getAlpha());

    //now overlay new RGBA top over the original bottom Pixel
    return PixelUtil.computeColour(topPrimeRGBA,bottom);
  }

  @Override
  public String toString() {
    return "difference";
  }
}
