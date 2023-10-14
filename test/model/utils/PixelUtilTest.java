package model.utils;

import org.junit.Test;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests the PixelUtil class' methods.
 */
public class PixelUtilTest {



  @Test
  public void toFourTest() {
    Pixel original = new RGBPixel(100,150,200,51);

    //test for when given alpha out of range:
    try {
      PixelUtil.toFourComponent(original,-10);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("alpha must be between 0 and 255, inclusive",e.getMessage());
    }

    //test for when given alpha out of range:
    try {
      PixelUtil.toFourComponent(original,256);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("alpha must be between 0 and 255, inclusive",e.getMessage());
    }

    //test for when given alpha is zero:
    original = new RGBPixel(100,150,200,250);
    assertEquals(new RGBPixel(0,0,0,0),
            PixelUtil.toFourComponent(original,0));

    //baseline test to see if it inverts toThreeComponent
    original = new RGBPixel(100,150,200,51);
    Pixel temp = PixelUtil.toThreeComponent(original);
    assertEquals(original,PixelUtil.toFourComponent(temp,51));
  }

}
