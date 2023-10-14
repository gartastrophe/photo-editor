package model.filter.option.blend;

import org.junit.Test;

import model.filter.option.FilterOption;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the applyToColour method in
 * Multiply as well as the toString override of the Filter.
 */
public class MultiplyTest {

  /**
   * Tests the applyToColourTest in this filter.
   */
  @Test
  public void applyToColourTest() {
    FilterOption<Pixel> filter = new Multiply();
    Pixel top;
    Pixel bottom;
    Pixel mix;

    //should be zero when the background is black
    top = new RGBPixel(50,100,150,200);
    bottom = new RGBPixel(0,0,0,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(0,mix.getRed(),1);
    assertEquals(0,mix.getGreen(),1);
    assertEquals(0,mix.getBlue(),1);
    assertEquals(255,mix.getAlpha(),1);

    //should stay the same if the background is white
    top = new RGBPixel(50,100,150,255);
    bottom = new RGBPixel(255,255,255,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(top.getRed(),mix.getRed(),1);
    assertEquals(top.getGreen(),mix.getGreen(),1);
    assertEquals(top.getBlue(),mix.getBlue(),1);
    //the alpha is 255 because the background is 255
    assertEquals(top.getAlpha(),mix.getAlpha(),1);

    //background is now purple:
    top = new RGBPixel(50,100,150,200);
    bottom = new RGBPixel(255,0,255,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(75,mix.getRed());
    assertEquals(39,mix.getGreen());
    assertEquals(114,mix.getBlue());
    //the alpha is 255 because the background is 255
    assertEquals(255,mix.getAlpha(),1);
  }

  @Test
  public void testToString() {
    //test to see if toString matches
    FilterOption<Pixel> filter = new Multiply();
    assertEquals("multiply",filter.toString());
  }
}