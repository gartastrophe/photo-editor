package model.filter.option.blend;

import org.junit.Test;

import model.filter.option.FilterOption;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PixelUtil;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the applyToColour method in
 * Difference as well as the toString override of the Filter.
 */
public class DifferenceTest {

  /**
   * Tests the applyToColourTest in this filter.
   */
  @Test
  public void applyToColourTest() {
    //test for solid colours:
    FilterOption<Pixel> filter = new Difference();
    Pixel temp;
    Pixel black = new RGBPixel(0, 0, 0, 255);
    Pixel white = new RGBPixel(255, 255, 255, 255);

    //tries applying black on white
    temp = filter.applyToColor(black, white);
    assertEquals(white, temp);

    //tries applying white on black
    temp = filter.applyToColor(white, black);
    assertEquals(white, temp);

    //variables used later
    Pixel p1;
    Pixel p2;
    Pixel p3;

    //tries two different colours where the top's components are
    //greater than the bottom's
    p1 = new RGBPixel(200, 100, 50, 255);
    p2 = new RGBPixel(150, 50, 0, 255);
    p3 = new RGBPixel(50, 50, 50, 255);
    temp = filter.applyToColor(p1, p2);
    assertEquals(p3, temp);

    //test for other way around; ie: bottom > top
    temp = filter.applyToColor(p2, p1);
    assertEquals(p3, temp);

    //tests for non-solid alphas:
    p1 = new RGBPixel(200, 100, 50, 51);
    assertEquals("40 20 10 255 ", PixelUtil.toThreeComponent(p1).toProjString());
    p2 = new RGBPixel(150, 50, 0, 255);
    p3 = new RGBPixel(110, 30, 10, 255); //difference (RGB)
    p3 = PixelUtil.toFourComponent(p3, 51); //convert the difference back into RGBA
    temp = filter.applyToColor(p1, p2);
    assertEquals(PixelUtil.computeColour(p3, p2), temp); //pixel util already tested

    //tests for non-solid alphas:
    p1 = new RGBPixel(200, 100, 50, 255);
    p2 = new RGBPixel(150, 50, 0, 51);
    assertEquals("30 10 0 255 ", PixelUtil.toThreeComponent(p2).toProjString());
    p3 = new RGBPixel(170, 90, 50, 255); //difference (RGB)
    //since the top has an alpha of 255, p3 doesn't change when merged down
    temp = filter.applyToColor(p1, p2);
    assertEquals(p3.toProjString(),
            temp.toProjString());

    //tests for non-solid alphas:
    p1 = new RGBPixel(200, 100, 50, 51);
    assertEquals("40 20 10 255 ", PixelUtil.toThreeComponent(p1).toProjString());
    p2 = new RGBPixel(150, 50, 0, 51);
    assertEquals("30 10 0 255 ", PixelUtil.toThreeComponent(p2).toProjString());
    p3 = new RGBPixel(10, 10, 10, 255); //difference (RGB)
    p3 = PixelUtil.toFourComponent(p3,51); //back to rgba
    //since they're both 51, this should be identical to the filtered top
    temp = filter.applyToColor(p1, p2);
    assertEquals(PixelUtil.computeColour(p3,p2).toProjString(), //top over bottom
            temp.toProjString()); //pixel util already tested
  }



  @Test
  public void testToString() {
    //test to see if toString matches
    FilterOption<Pixel> filter = new Difference();
    assertEquals("difference",filter.toString());
  }
}