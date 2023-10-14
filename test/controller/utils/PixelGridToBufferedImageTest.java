package controller.utils;


import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import model.layer.Layer;
import model.layer.LayerPixelImpl;
import model.model.CollageModel;
import model.model.CollageModelImpl2;
import model.model.CollageModelImpl3;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the conversion from PixelGrid to a BufferedImage.
 */
public class PixelGridToBufferedImageTest {

  @Test
  public void constTest() {
    //tests the constructor:
    try {
      new PixelGridToBufferedImage((String)null);
    } catch (IllegalArgumentException e) {
      assertEquals("content cannot be null",e.getMessage());
    }

    try {
      new PixelGridToBufferedImage((Pixel[][])null);
    } catch (IllegalArgumentException e) {
      assertEquals("content cannot be null",e.getMessage());
    }

    try {
      new PixelGridToBufferedImage(new Pixel[1][1]);
    } catch (IllegalArgumentException e) {
      assertEquals("Contents contain null values.",e.getMessage());
    }

    //test for string input

    String numbers = "1 2 3 4 " +
            //6 "pixels" below
            "5 6 7 8 " +
            "9 10 11 12 " +
            "13 14 15 16 " +
            "17 18 19 20 " +
            "21 22 23 24 " +
            "25 26 27 28 ";
    BufferedImage customBI = new PixelGridToBufferedImage(numbers);
    assertEquals(new Color(5,6,7,8).getRGB(),customBI.getRGB(0,0));
    assertEquals(new Color(9,10,11,12).getRGB(),customBI.getRGB(1,0));
    assertEquals(new Color(13,14,15,16).getRGB(),customBI.getRGB(0,1));
    assertEquals(new Color(17,18,19,20).getRGB(),customBI.getRGB(1,1));
    assertEquals(new Color(21,22,23,24).getRGB(),customBI.getRGB(0,2));
    assertEquals(new Color(25,26,27,28).getRGB(),customBI.getRGB(1,2));

    //test for pixel input

    Pixel[][] gridTemp = new Pixel[2][2];
    gridTemp[0][0] = new RGBPixel(255,255,255,255);
    gridTemp[0][1] = new RGBPixel(255,0,0,200);
    gridTemp[1][0] = new RGBPixel(0,255,0,150);
    gridTemp[1][1] = new RGBPixel(0,0,255,100);
    BufferedImage customBI2 = new PixelGridToBufferedImage(gridTemp);
    assertEquals(new Color(255,255,255,255).getRGB(),customBI2.getRGB(0,0));
    assertEquals(new Color(255,0,0,200).getRGB(),customBI2.getRGB(1,0));
    assertEquals(new Color(0,255,0,150).getRGB(),customBI2.getRGB(0,1));
    assertEquals(new Color(0,0,255,100).getRGB(),customBI2.getRGB(1,1));

    //test for getGridContents

    Pixel[][] gridTempCopy = new PixelGridToBufferedImage(gridTemp).getGridContent();
    assertArrayEquals(gridTempCopy,gridTemp); //same contents
    assertNotEquals(gridTempCopy,gridTemp); //pointing to different objects
  }











  //represents tests about functionality while implementing the class
  @Test
  public void testPrelim() {

    String temp = "a b\nc d";
    String[] tempArray = new String[4];
    tempArray[0] = "a";
    tempArray[1] = "b";
    tempArray[2] = "c";
    tempArray[3] = "d";

    assertArrayEquals(tempArray,temp.split("\n| "));

    CollageModel<Pixel> model = new CollageModelImpl2();

    model.newProject(2,1);
    assertEquals("P3\n" +
                    "1 2\n" +
                    "255\n" +
                    "255 255 255 " +
                    "255 255 255 ",
            model.saveImage());



    model = new CollageModelImpl3();

    model.newProject(2,1);
    assertEquals("T1\n" +
            "1 2\n" +
            "255\n" +
            "255 255 255 255 " +
            "255 255 255 255 ",
            model.saveImage());

    String[] components = model.saveImage().split("\n| ");

    assertEquals("T1",components[0]);
    assertEquals("1",components[1]);
    assertEquals("2",components[2]);
    assertEquals("255",components[3]);
    assertEquals("255",components[4]);

    Pixel[][] grid = new Pixel[2][1];

    for (int i = 0; i < 2; i++) { //height
      for (int j = 0; j < 1; j++) { //width
        //convert height and width to the pixel #
        int pixelNum = (i + j);

        //rIndex represents the index of this
        //pixel's red value in the array of
        //components
        int rIndex = pixelNum + 4; //0,0 should be 4 so +4

        int r = Integer.parseInt(components[rIndex]);
        int g = Integer.parseInt(components[rIndex + 1]);
        int b = Integer.parseInt(components[rIndex + 2]);
        int a = Integer.parseInt(components[rIndex + 3]);

        grid[i][j] = new RGBPixel(r,g,b,a);
      }


      //tests for if layers can display 0 alpha pixels
      Layer<Pixel> l = new LayerPixelImpl(2,1);
      Pixel[][] g2 = new Pixel[2][1];
      g2[0][0] = new RGBPixel(0,0,0,0);
      g2[1][0] = new RGBPixel(255,255,255,255);
      l.addImage(g2,0,0); //add image is OK
      assertArrayEquals(g2,l.getGrid());

      Pixel[][] g3 = new Pixel[2][1];
      g3[0][0] = new RGBPixel(255,0,0,255);
      g3[1][0] = new RGBPixel(255,0,0,255);

      Pixel[][] g4 = new Pixel[2][1];
      g4[0][0] = new RGBPixel(255,0,0,255);
      g4[1][0] = new RGBPixel(255,255,255,255);

      l.addImage(g3,0,0);
    }

    //new collage model
    model = new CollageModelImpl3();
    model.newProject(1,1);
    //set background to red
    model.setFilter("background","red-component");
    assertEquals("C1\n" +
            "1 1\n" +
            "255\n" +
            "background red-component\n" +
            "255 255 255 255 \n",model.saveProject());
    assertEquals("T1\n" +
            "1 1\n" +
            "255\n" +
            "255 0 0 255 ",model.saveImage());

  }

}