package controller.utils;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * Tests the adapter from the PPM-string to a BufferedImage.
 * Specifically this tests the constructor and the initialisation
 * of the image.
 */
public class PPMToBufferedImageTest {

  /**
   * Test constructor for the class PPMToBufferedImageTest.
   */
  @Test
  public void testConstructor() {
    BufferedImage image;
    String content;

    //test for null content
    try {
      new PPMToBufferedImage(null);
    } catch (IllegalArgumentException e) {
      assertEquals("ppmText cannot be null.",e.getMessage());
    }

    //test for failing dimensions:
    content = "P3 1 ";
    try {
      new PPMToBufferedImage(content);

    } catch (IllegalArgumentException e) {
      assertEquals("Error: incomplete PPM header, dimensions " +
              "could not be extracted.",e.getMessage());
    }

    //test for passing dimensions failing grid init
    content = "P3 1 2 255 ";
    try {
      new PPMToBufferedImage(content);
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incorrect PPM format, " +
              "Pixels could not be extracted.",e.getMessage());
    }

    //test for passing dimensions failing grid init
    content = "P3 1 2 255 " +
            "255 255 255 "; //should have 2 rows; has 1
    try {
      new PPMToBufferedImage(content);
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incorrect PPM format, " +
              "Pixels could not be extracted.",e.getMessage());
    }

    //test for passing dimensions failing grid init
    content = "P3 1 2 255 " +
            "255 255 255 " +
            "255 255"; //should have 2 FULL rows
    try {
      new PPMToBufferedImage(content);
    } catch (IllegalArgumentException e) {
      assertEquals("Error: incorrect PPM format, " +
              "Pixels could not be extracted.",e.getMessage());
    }

    //test for passing dimensions failing grid init
    content = "P3 1 2 255 " +
            "255 255 255 " + //white
            "255 0 0 "; //red
    image = new PPMToBufferedImage(content);
    assertEquals(1,image.getWidth());
    assertEquals(2,image.getHeight());
    assertEquals(Color.WHITE.getRGB(),image.getRGB(0,0));
    assertEquals(Color.RED.getRGB(),image.getRGB(0,1));
  }
}