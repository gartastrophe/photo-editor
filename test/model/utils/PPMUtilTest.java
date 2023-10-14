package model.utils;

import org.junit.Test;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Tests the methods in PPMUtil.
 */
public class PPMUtilTest {

  @Test
  public void toPPMStringTest() {

    try {
      //test for null grid
      PPMUtil.toPPMFormat(null);
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel array " +
              "cannot be null or contain null values.",
              e.getMessage());
    }

    //at the moment, the array has only null values
    Pixel[][] pixels = new Pixel[1][3];
    //test that contains null values:
    assertNull(pixels[0][0]);

    try {
      //test for grid containing null values
      PPMUtil.toPPMFormat(pixels);
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel array " +
                      "cannot be null or contain null values.",
              e.getMessage());
    }

    pixels[0][0] = new RGBPixel(0,0,0,0);
    //test that pixels contains non-null values:
    assertEquals(new RGBPixel(0,0,0,0),pixels[0][0]);
    //test that pixels contains null values:
    assertNull(pixels[0][1]);

    try {
      //test for grid containing null values
      PPMUtil.toPPMFormat(pixels);
    } catch (IllegalArgumentException e) {
      assertEquals("The given pixel array " +
                      "cannot be null or contain null values.",
              e.getMessage());
    }

    pixels[0][1] = new RGBPixel(0,0,0,0);
    pixels[0][2] = new RGBPixel(0,0,0,0);

    //now pixels contains no null values:
    String temp = PPMUtil.toPPMFormat(pixels);

    //tests for the right amount of pixels
    //showing up (should be 9)
    assertEquals("P3\n3 1\n255\n" +
            "0 0 0 " +
            "0 0 0 " +
            "0 0 0 ",temp);

    pixels[0][0] = new RGBPixel(255,0,0,255);
    pixels[0][1] = new RGBPixel(0,200,0,255);
    pixels[0][2] = new RGBPixel(0,0,100,255);
    temp = PPMUtil.toPPMFormat(pixels);
    //tests for the pixel order for RGB
    assertEquals("P3\n3 1\n255\n" +
            "255 0 0 " +
            "0 200 0 " +
            "0 0 100 ",temp);

    //alpha is 1/3rd of max
    pixels[0][0] = new RGBPixel(255,0,0,85);
    pixels[0][1] = new RGBPixel(0,150,0,85);
    pixels[0][2] = new RGBPixel(0,0,99,85);
    temp = PPMUtil.toPPMFormat(pixels);
    //tests for the right RGBA to RGB conversion
    //all new RGB values should be 1/3rd of
    //the original
    assertEquals("P3\n3 1\n255\n" +
            "85 0 0 " +
            "0 50 0 " +
            "0 0 33 ",temp);
  }

  /**
   * Tests the extractPPMDimensions method in PPMUtil.
   */
  @Test
  public void getHeaderTest() {

    //test for null input
    try {
      PPMUtil.extractPPMDimensions(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("ppmText cannot be null.",e.getMessage());
    }

    String temp;
    //tests for when there is no "next"
    temp = "";

    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: token not found.",e.getMessage());
    }

    //tests for when token not right
    temp = "A4"; //token should be P3
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM file: plain RAW file should begin with P3.",e.getMessage());
    }

    //tests for when nothing after right token
    temp = "P3";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incomplete PPM header," +
              " dimensions could not be extracted.",e.getMessage());
    }

    //tests for when nothing after width
    temp = "P3 200";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incomplete PPM header," +
              " dimensions could not be extracted.",e.getMessage());
    }

    //tests for when nothing after height
    temp = "P3 200 5";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incomplete PPM header," +
              " dimensions could not be extracted.",e.getMessage());
    }

    //tests for complete header
    temp = "P3 200 5 255";
    int[] tempArray = PPMUtil.extractPPMDimensions(temp);

    //all the values should be right
    assertEquals(200,tempArray[0]);
    assertEquals(5,tempArray[1]);
    assertEquals(255,tempArray[2]);

    //tests for complete header with extra content at the end
    temp = "P3 200 5 255 2 2 34 5 6 2 2 12 3 4 2 21 ";
    tempArray = PPMUtil.extractPPMDimensions(temp);

    //all the values should be right
    assertEquals(200,tempArray[0]);
    assertEquals(5,tempArray[1]);
    assertEquals(255,tempArray[2]);

    //tests for complete header with "illegal" content at the end
    temp = "P3 200 5 255 2.0 2 34 a af t 43 few 5 foa skn saj  4 2 21 ";
    tempArray = PPMUtil.extractPPMDimensions(temp);

    //all the values should be right
    assertEquals(200,tempArray[0]);
    assertEquals(5,tempArray[1]);
    assertEquals(255,tempArray[2]);

    //tests for complete header (with "illegal" values for the model)
    temp = "P3 -200 -905 -255"; //this method will pass on these values
    tempArray = PPMUtil.extractPPMDimensions(temp);

    //all the values should be right
    assertEquals(-200,tempArray[0]);
    assertEquals(-905,tempArray[1]);
    assertEquals(-255,tempArray[2]);

    //tests for when width is a double
    temp = "P3 200.0 111 204";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(    "Error: InputMismatchException thrown in width," +
                      " height, max.  These values should be integers.",e.getMessage());
    }

    //tests for when height is a double
    temp = "P3 200 111.0 204";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(    "Error: InputMismatchException thrown in width, " +
                      "height, max.  These values should be integers.",e.getMessage());
    }

    //tests for when max is a double
    temp = "P3 200 111 204.0";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(    "Error: InputMismatchException thrown in width, " +
                      "height, max.  These values should be integers.",e.getMessage());
    }

    //tests for when max is a String
    temp = "P3 200 111 ahh";
    try {
      PPMUtil.extractPPMDimensions(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(    "Error: InputMismatchException thrown in width, " +
                      "height, max.  These values should be integers.",e.getMessage());
    }
  }


  @Test
  public void fromPPMFormatTest() {
    //test for null input
    try {
      PPMUtil.fromPPMFormat(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("ppmText cannot be null.",e.getMessage());
    }

    String temp;
    //tests for when there is no "next"
    temp = "";

    try {
      PPMUtil.fromPPMFormat(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incorrect PPM format, " +
              "Pixels could not be extracted.",e.getMessage());
    }

    //tests for when token not right
    temp = "A4"; //token should be P3
    try {
      PPMUtil.fromPPMFormat(temp);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid PPM file: plain RAW file should begin with P3.",e.getMessage());
    }
  }


}