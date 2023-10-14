import org.junit.Test;

import model.pixel.HSLPixel;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PixelUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the AbstractPixel class for its constructors and methods.
 */
public class AbstractPixelTest {

  /**
   * Tests the valid constructors in RGBPixel.
   */
  @Test
  public void testValidConstructors() {
    Pixel temp = new RGBPixel(1,2,3);
    assertEquals(1,temp.getRed());
    assertEquals(2,temp.getGreen());
    assertEquals(3,temp.getBlue());
    assertEquals(255,temp.getAlpha());

    temp = new RGBPixel(1,2,3,4);
    assertEquals(1,temp.getRed());
    assertEquals(2,temp.getGreen());
    assertEquals(3,temp.getBlue());
    assertEquals(4,temp.getAlpha());
  }

  /**
   * Tests the invalid constructors for an RGBPixel.
   */
  @Test
  public void testRGBInvalidConstructors() {
    //tests for when values too large
    try {
      //invalid red value
      new RGBPixel(999999, 2, 3, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The red value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid green value
      new RGBPixel(1, 693, 3, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The green value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid blue value
      new RGBPixel(1, 2, 2999, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The blue value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid alpha value
      new RGBPixel(1, 2, 0, 32222);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The alpha value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }

    //tests for when values too small
    try {
      //invalid red value
      new RGBPixel(-260, 2, 3, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The red value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid green value
      new RGBPixel(1, -693, 3, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The green value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid blue value
      new RGBPixel(1, 2, -2999, 4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The blue value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
    try {
      //invalid alpha value
      new RGBPixel(1, 2, 0, -32222);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The alpha value cannot be less than 0.0 or greater than 255.0.",
              e.getMessage());
    }
  }


  /**
   * Tests the invalid constructors for an HSLPixel.
   */
  @Test
  public void testHSLInvalidConstructors() {
    try {
      //invalid hue value
      new HSLPixel(-1,1,1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The hue value cannot be less than 0.0 or greater than 360.0.",
              e.getMessage());
    }

    try {
      //invalid hue value
      new HSLPixel(361,1,1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The hue value cannot be less than 0.0 or greater than 360.0.",
              e.getMessage());
    }

    try {
      //invalid saturation value
      new HSLPixel(200,-1,1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The saturation value cannot be less than 0.0 or greater than 1.0.",
              e.getMessage());
    }

    try {
      //invalid saturation value
      new HSLPixel(200,1.4,1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The saturation value cannot be less than 0.0 or greater than 1.0.",
              e.getMessage());
    }

    try {
      //invalid lightness value
      new HSLPixel(200,1,-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The lightness value cannot be less than 0.0 or greater than 1.0.",
              e.getMessage());
    }

    try {
      //invalid lightness value
      new HSLPixel(200,1,1.4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The lightness value cannot be less than 0.0 or greater than 1.0.",
              e.getMessage());
    }
  }

  @Test
  public void testHSLConstructorEdge() {
    //makes a red pixel:
    Pixel hsl = new HSLPixel(360,1,0.5);

    //according to my documentation, a hue of 360 IS
    //accepted but since the representation is exactly
    //the same as 0, it should be reverted to zero
    //for consistency's sake
    assertEquals(0.0,hsl.getHue(),0.01);

    //tests for hue of 360
    assertEquals(255,hsl.getRed());
    //tests for hue of 0
    assertEquals(255,new HSLPixel(0,1,1).getRed());
    assertEquals("255 0 0 255 ",hsl.toProjString());

    hsl = new HSLPixel(120,1,0.5);
    assertEquals("0 255 0 255 ",hsl.toProjString());

    hsl = new HSLPixel(240,1,0.5);
    assertEquals("0 0 255 255 ",hsl.toProjString());
  }


  /**
   * Tests the observers in RGBPixel.
   */
  @Test
  public void getComponentTest() {

    //tests for getRed
    for (int i = 0; i < 255; i++) {
      Pixel p = new RGBPixel(i,2,3,4);
      assertEquals(i,p.getRed());
      assertEquals(2,p.getGreen());
      assertEquals(3,p.getBlue());
      assertEquals(4,p.getAlpha());
    }

    //tests for getGreen
    for (int i = 0; i < 255; i++) {
      Pixel p = new RGBPixel(1,i,3,4);
      assertEquals(1,p.getRed());
      assertEquals(i,p.getGreen());
      assertEquals(3,p.getBlue());
      assertEquals(4,p.getAlpha());
    }

    //tests for getBlue
    for (int i = 0; i < 255; i++) {
      Pixel p = new RGBPixel(1,2,i,4);
      assertEquals(1,p.getRed());
      assertEquals(2,p.getGreen());
      assertEquals(i,p.getBlue());
      assertEquals(4,p.getAlpha());
    }

    //tests for getAlpha
    for (int i = 0; i < 255; i++) {
      Pixel p = new RGBPixel(1,2,3,i);
      assertEquals(1,p.getRed());
      assertEquals(2,p.getGreen());
      assertEquals(3,p.getBlue());
      assertEquals(i,p.getAlpha());
    }
  }

  /**
   * Tests if the HSL Constructor preserves RGB and RGBA.
   */
  @Test
  public void testHSL() {
    int red = 0;
    int green = 100;
    int blue = 150;
    int alpha = 200;
    Pixel p = new RGBPixel(red,green,blue,alpha);
    assertEquals(red,p.getRed());
    assertEquals(green,p.getGreen());
    assertEquals(blue,p.getBlue());
    assertEquals(alpha,p.getAlpha());

    //test for getting the hues
    assertEquals(200,p.getHue(),0.1);
    assertEquals(1.0,p.getSaturation(),0.01);
    assertEquals(0.23,p.getLightness(),0.01);

    //create a new HSLPixel w/ those hues
    Pixel p2 = new HSLPixel(p.getHue(),p.getSaturation(),p.getLightness());

    //test to see if hues are the same still
    assertEquals(200,p2.getHue(),0.01);
    assertEquals(1.0,Math.abs(p2.getSaturation()),0.01);
    assertEquals(0.23,p2.getLightness(),0.01);

    //test for equality w/ the RGB toThreeComponent method in PixelUtil
    //in other words, test to see if the RGB values are preserved
    assertEquals(PixelUtil.toThreeComponent(p).toProjString(),p2.toProjString());

    //test for conversion back to RGBA-255 with 1 unit leniency
    Pixel p3 = PixelUtil.toFourComponent(p2,alpha);
    assertEquals(p.getRed(),p3.getRed(),1);
    assertEquals(p.getGreen(),p3.getGreen(),1);
    assertEquals(p.getBlue(),p3.getBlue(),1);
    assertEquals(p.getAlpha(),p3.getAlpha(),1);


    //test using different non-zero values
    red = 173;
    green = 102;
    blue = 84;
    alpha = 150;
    p = new RGBPixel(red,green,blue,alpha);
    assertEquals(red,p.getRed());
    assertEquals(green,p.getGreen());
    assertEquals(blue,p.getBlue());
    assertEquals(alpha,p.getAlpha());

    //test for getting the hues
    assertEquals(12.1,p.getHue(),0.1);
    assertEquals(0.34,p.getSaturation(),0.01);
    assertEquals(0.30,p.getLightness(),0.01);

    //create a new HSLPixel w/ those hues
    p2 = new HSLPixel(p.getHue(),p.getSaturation(),p.getLightness());

    //test to see if hues are the same still
    assertEquals(12.1,p2.getHue(),0.1);
    assertEquals(0.34,Math.abs(p2.getSaturation()),0.01);
    assertEquals(0.30,p2.getLightness(),0.01);

    //test for equality w/ the RGB toThreeComponent method in PixelUtil
    //in other words, test to see if the RGB values are preserved
    assertEquals(PixelUtil.toThreeComponent(p).toProjString(),p2.toProjString());

    //test for conversion back to RGBA-255 with 1 unit leniency
    p3 = PixelUtil.toFourComponent(p2,alpha);
    assertEquals(p.getRed(),p3.getRed(),1);
    assertEquals(p.getGreen(),p3.getGreen(),1);
    assertEquals(p.getBlue(),p3.getBlue(),1);
    assertEquals(p.getAlpha(),p3.getAlpha(),1);
  }


  /**
   * Tests the toString method in RGBPixel.
   */
  @Test
  public void testToProjString() {
    //tests for when alpha not 0
    Pixel p = new RGBPixel(1,2,3,4);
    assertEquals("1 2 3 4 ", p.toProjString());

    p = new RGBPixel(1,100,150,1);
    assertEquals("1 100 150 1 ", p.toProjString());

    p = new RGBPixel(50,100,200,255);
    assertEquals("50 100 200 255 ", p.toProjString());

    //tests for when the alpha is 0
    p = new RGBPixel(1,2,3,0);
    assertEquals("0 0 0 0 ", p.toProjString());

    p = new RGBPixel(255,255,255,0);
    assertEquals("0 0 0 0 ", p.toProjString());

    p = new RGBPixel(0,0,0,0);
    assertEquals("0 0 0 0 ", p.toProjString());
  }

}