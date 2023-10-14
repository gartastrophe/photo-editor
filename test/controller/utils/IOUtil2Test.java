package controller.utils;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.model.CollageModel;
import model.model.CollageModelImpl3;
import model.model.CollageModelImpl3Mock;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the IOUtil2 class.  This specifically tests that
 * reading and writing images works as intended.
 */
public class IOUtil2Test {

  @Before
  public void init() {
    String path;
    Pixel[][] grid;
    IOUtilInterface util = new IOUtil2();
    CollageModel<Pixel> model = new CollageModelImpl3();
    String inter;
    String proj;

    //try writing a jpg still works:
    path = "res/wrgb2x2.jpg";
    //read from ppm to ensure same results;
    grid = util.readFile("res/wrgb2x2.ppm");

    //convert the grid into an intermediary string:
    model.newProject(2,2);
    //add the image to the layer
    model.addImageToLayer("background",grid,0,0);
    inter = model.saveImage();

    proj = model.saveProject();
    assertEquals("C1\n" +
            "2 2\n" +
            "255\n" +
            "background normal\n" +
            "255 255 255 255 " +
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 \n",proj);

    assertEquals("T1\n" +
            "2 2\n" +
            "255\n" +
            "255 255 255 255 " +
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 ",inter);

    BufferedImage img = new PPMToBufferedImage(PPMUtil.toPPMFormat(
            new PixelGridToBufferedImage(inter).getGridContent()));

    assertArrayEquals(grid,new PixelGridToBufferedImage(inter).getGridContent());

    //the new buffered image
    assertEquals(new Color(255,255,255,255).getRGB(),
            img.getRGB(0,0));
    assertEquals(new Color(255,0,0,255).getRGB(),
            img.getRGB(1,0));
    assertEquals(new Color(0,255,0,255).getRGB(),
            img.getRGB(0,1));
    assertEquals(new Color(0,0,255,255).getRGB(),
            img.getRGB(1,1));


    util.writeFile(path,inter);


    //ensure argb2x2.png also works:

    String argb = "T1\n" +
            "2 2\n" +
            "255\n" +
            "0 0 0 0 " +
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 ";

    util.writeFile("res/argb2x2.png",argb);

  }


  @Test
  public void testErrors() {
    IOUtilInterface util = new IOUtil2();

    try {
      util.readFile(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Given path cannot be null.",e.getMessage());
    }

    try {
      util.readFile("res/test");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Error: no suffix found.",e.getMessage());
    }

    try {
      util.readFile("non-existent_file.png");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Can't read input file!",e.getMessage());
    }

    try {
      util.readFile("non-existent_file.rgb");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Can't read input file!",e.getMessage());
    }




    try {
      util.writeFile(null,"abc");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName & contents cannot be null.",e.getMessage());
    }

    try {
      util.writeFile("abc",null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName & contents cannot be null.",e.getMessage());
    }


    try {
      util.writeFile("abc.123","content"); //illegal suffix
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File type not supported at this time.",e.getMessage());
    }

    try {
      util.writeFile("abc.txt","content");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File token not supported at this time.",e.getMessage());
      //this is the same as IOUtil; see the tests there for more details.
    }

    //this should run because right token
    util.writeFile("deleteMe.txt","C1\ncontent");
    //this is the same as IOUtil; see the tests there for more details.


    String argb = "T1\n" +
            "2 2\n" +
            "255\n" +
            "0 0 0 0 " +
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 ";

    try {
      //test for illegal name
      util.writeFile("..#%#$<<<.png",argb); //illegal syntax
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("..#%#$<<<.png (The " +
              "filename, directory name, or volume " +
              "label syntax is incorrect)",e.getMessage());
      //this is the same as IOUtil; see the tests there for more details.
    }

  }






  /**
   * Tests the methods that read in an image file.
   */
  @Test
  public void testRead() {
    String path;
    Pixel[][] grid;

    //ensure reading from a ppm still works:
    path = "res/wrgb2x2.ppm";
    grid = new IOUtil2().readFile(path);

    Pixel[][] gridTemp = new Pixel[2][2];
    gridTemp[0][0] = new RGBPixel(255,255,255,255);
    gridTemp[0][1] = new RGBPixel(255,0,0,255);
    gridTemp[1][0] = new RGBPixel(0,255,0,255);
    gridTemp[1][1] = new RGBPixel(0,0,255,255);

    assertArrayEquals(gridTemp,grid);


    //test for reading from a jpg
    path = "res/wrgb2x2.jpg";
    grid = new IOUtil2().readFile(path);
    gridTemp = new Pixel[2][2];
    //jpegs compress kinda weird:
    gridTemp[0][0] = new RGBPixel(239, 245, 243, 255);
    gridTemp[0][1] = new RGBPixel(87, 93, 91, 255);
    gridTemp[1][0] = new RGBPixel(162, 168, 166,255);
    gridTemp[1][1] = new RGBPixel(24, 30, 28,255);

    assertEquals(gridTemp[0][0].toProjString(),grid[0][0].toProjString());
    assertEquals(gridTemp[0][1].toProjString(),grid[0][1].toProjString());
    assertEquals(gridTemp[1][0].toProjString(),grid[1][0].toProjString());
    assertEquals(gridTemp[1][1].toProjString(),grid[1][1].toProjString());

    assertArrayEquals(gridTemp,grid);


    //test for reading from a png
    path = "res/wrgb2x2.png";
    grid = new IOUtil2().readFile(path);
    gridTemp = new Pixel[2][2];
    gridTemp[0][0] = new RGBPixel(255,255,255,255);
    gridTemp[0][1] = new RGBPixel(255,0,0,255);
    gridTemp[1][0] = new RGBPixel(0,255,0,255);
    gridTemp[1][1] = new RGBPixel(0,0,255,255);

    assertArrayEquals(gridTemp,grid);


    //test for reading from a png with transparency
    path = "res/argb2x2.png";
    grid = new IOUtil2().readFile(path);
    gridTemp = new Pixel[2][2];
    gridTemp[0][0] = new RGBPixel(255,255,255,0);
    gridTemp[0][1] = new RGBPixel(255,0,0,255);
    gridTemp[1][0] = new RGBPixel(0,255,0,255);
    gridTemp[1][1] = new RGBPixel(0,0,255,255);

    assertEquals(gridTemp[0][0].toProjString(),grid[0][0].toProjString());

    assertArrayEquals(gridTemp,grid);
  }

  /**
   * Tests the methods that write to an image.
   */
  @Test
  public void testWrite() {
    IOUtilInterface util = new IOUtil2();
    String path;
    Pixel[][] grid;
    Pixel[][] newGrid;
    CollageModel<Pixel> model;
    String inter;
    String proj;


    //ensure writing a ppm still works:
    path = "res/wrgb2x2.ppm";
    grid = util.readFile(path);

    //convert the grid into an intermediary string:
    model = new CollageModelImpl3();

    //add the image to the layer
    model.newProject(2,2);
    model.addImageToLayer("background",grid,0,0);
    inter = model.saveImage();

    util.writeFile(path,inter);
    newGrid = util.readFile(path);

    assertArrayEquals(newGrid,grid);






    //try writing a jpg still works:
    path = "res/wrgb2x2.jpg";
    //read from ppm to ensure same results;
    grid = util.readFile("res/wrgb2x2.ppm");

    //convert the grid into an intermediary string:
    model = new CollageModelImpl3();

    //add the image to the layer
    model.newProject(2,2);
    model.addImageToLayer("background",grid,0,0);
    inter = model.saveImage();

    BufferedImage img = new PPMToBufferedImage(PPMUtil.toPPMFormat(
            new PixelGridToBufferedImage(inter).getGridContent()));

    assertArrayEquals(grid,new PixelGridToBufferedImage(inter).getGridContent());

    //the new buffered image
    assertEquals(new Color(255,255,255,255).getRGB(),
            img.getRGB(0,0));
    assertEquals(new Color(255,0,0,255).getRGB(),
            img.getRGB(1,0));
    assertEquals(new Color(0,255,0,255).getRGB(),
            img.getRGB(0,1));
    assertEquals(new Color(0,0,255,255).getRGB(),
            img.getRGB(1,1));

    /*
     * BECAUSE JPEGS WILL COMPRESS WEIRDLY,
     * I AM COMPARING THIS TO JAVA's IMAGEIO's
     * OUTPUT AS A BASE LINE!
     */

    //get the data for ioUtil2's write
    util.writeFile(path,inter);
    Pixel[][] ioUtil2write = util.readFile(path);

    Pixel[][] imageIOWrite;
    try {
      ImageIO.write(img, "jpg", new File(path));
      imageIOWrite = util.readFile(path);
      assertArrayEquals(imageIOWrite,ioUtil2write);
    } catch (IOException e) {
      fail();
    }



    //ensure writing a pngs works:
    path = "res/wrgb2x2.png";
    grid = util.readFile(path);

    //write file
    util.writeFile(path,inter);
    newGrid = util.readFile(path);

    assertArrayEquals(newGrid,grid);



    //ensure writing a pngs works (feat transparency):
    path = "res/argb2x2.png";
    grid = util.readFile(path);

    //convert the grid into an intermediary string:
    model = new CollageModelImpl3Mock(); //uses a mock with a transparent background layer

    //add the image to the layer
    model.newProject(2,2);
    model.addImageToLayer("background",grid,0,0);
    //add the grid to the bg; should be identical
    inter = model.saveImage();

    proj = model.saveProject();

    assertEquals("C1\n" +
            "2 2\n" +
            "255\n" +
            "background normal\n" +
            "0 0 0 0 " + //(0,0) has transparency here
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 \n",proj);

    assertEquals("T1\n" +
            "2 2\n" +
            "255\n" +
            "0 0 0 0 " +
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 ",inter);

    util.writeFile(path,inter);
    newGrid = util.readFile(path);

    assertArrayEquals(newGrid,grid); //shows that it preserves transparency



    //test for saving png (with transparency) to jpg:

    //shows inter still same
    assertEquals("T1\n" +
            "2 2\n" +
            "255\n" +
            "0 0 0 0 " + //invisible
            "255 0 0 255 " +
            "0 255 0 255 " +
            "0 0 255 255 ",inter);

    //write to a jpg:
    util.writeFile("res/test.jpg",inter);

    newGrid = util.readFile("res/test.jpg");

    assertEquals(new RGBPixel(6,12,10,255).toProjString(),
            newGrid[0][0].toProjString()); //saving to a jpg; basically black






  }

}