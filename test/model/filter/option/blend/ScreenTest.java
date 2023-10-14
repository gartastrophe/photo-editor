package model.filter.option.blend;

import org.junit.Test;

import model.filter.option.FilterOption;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the applyToColour method in
 * Screen as well as the toString override of the Filter.
 */
public class ScreenTest {

  /**
   * Tests the applyToColourTest in this filter.
   */
  @Test
  public void applyToColourTest() {
    FilterOption<Pixel> filter = new Screen();
    Pixel top;
    Pixel bottom;
    Pixel mix;

    //should do nothing when the background is black
    top = new RGBPixel(50,100,150,255);
    bottom = new RGBPixel(0,0,0,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(top.getRed(),mix.getRed(),1);
    assertEquals(top.getGreen(),mix.getGreen(),1);
    assertEquals(top.getBlue(),mix.getBlue(),1);
    assertEquals(top.getAlpha(),mix.getAlpha(),1);

    //should become white if the background is white
    top = new RGBPixel(50,100,150,200);
    bottom = new RGBPixel(255,255,255,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(255,mix.getRed(),1);
    assertEquals(255,mix.getGreen(),1);
    assertEquals(255,mix.getBlue(),1);
    //the alpha is 255 because the background is 255
    assertEquals(255,mix.getAlpha(),1);

    //should become white-ish because the background is white
    top = new RGBPixel(50,100,150,200);
    bottom = new RGBPixel(255,255,255,200);
    mix = filter.applyToColor(top,bottom);
    assertEquals(253,mix.getRed());
    assertEquals(255,mix.getGreen());
    assertEquals(255,mix.getBlue());
    //the alpha is greater than 200 because alpha compositing
    assertEquals(243,mix.getAlpha(),1);

    //background is now purple:
    top = new RGBPixel(50,100,150,200);
    bottom = new RGBPixel(255,0,255,255);
    mix = filter.applyToColor(top,bottom);
    assertEquals(178,mix.getRed());
    assertEquals(167,mix.getGreen());
    assertEquals(255,mix.getBlue());
    //the alpha is greater than 200 because alpha compositing
    assertEquals(255,mix.getAlpha(),1);
  }

  @Test
  public void testToString() {
    //test to see if toString matches
    FilterOption<Pixel> filter = new Screen();
    assertEquals("screen",filter.toString());
  }
}