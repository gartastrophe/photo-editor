package model.layer;

import org.junit.Before;
import org.junit.Test;

import model.filter.option.NormalFilter;
import model.filter.option.colour.components.BlueComponent;
import model.filter.option.colour.components.GreenComponent;
import model.filter.option.colour.components.RedComponent;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;
import model.utils.PixelUtil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents tests for methods and constructors in the Layers class.
 */
public class LayerImplTest {
  Pixel defaultColour;
  Layer<Pixel> default1by1;
  Layer<Pixel> red;
  Layer<Pixel> green;
  Layer<Pixel> blue;
  Layer<Pixel> black;
  Layer<Pixel> white;
  Layer<Pixel> rgb;

  @Before
  public void init() {
    defaultColour = new RGBPixel(0,0,0,0);

    default1by1 = new LayerPixelImpl(1,1);

    Pixel[][] redGrid = new Pixel[1][1];
    redGrid[0][0] = new RGBPixel(255,0,0);
    red = new LayerPixelImpl(1,1,redGrid,new NormalFilter());

    Pixel[][] greenGrid = new Pixel[1][1];
    greenGrid[0][0] = new RGBPixel(0,255,0);
    green = new LayerPixelImpl(1,1,greenGrid,new NormalFilter());

    Pixel[][] blueGrid = new Pixel[1][1];
    blueGrid[0][0] = new RGBPixel(0,0,255);
    blue = new LayerPixelImpl(1,1,blueGrid,new NormalFilter());

    Pixel[][] blackGrid = new Pixel[1][1];
    blackGrid[0][0] = new RGBPixel(0,0,0);
    black = new LayerPixelImpl(1,1,blackGrid,new NormalFilter());

    Pixel[][] whiteGrid = new Pixel[1][1];
    whiteGrid[0][0] = new RGBPixel(255,255,255);
    white = new LayerPixelImpl(1,1,whiteGrid,new NormalFilter());

    Pixel[][] rgbGrid = new Pixel[1][3];
    rgbGrid[0][0] = new RGBPixel(255,0,0);
    rgbGrid[0][1] = new RGBPixel(0,255,0);
    rgbGrid[0][2] = new RGBPixel(0,0,255);
    rgb = new LayerPixelImpl(1,3,rgbGrid,new NormalFilter());
  }

  /**
   * Tests the convenience constructor exceptions for Layer.
   */
  @Test
  public void convenienceConstructorTest() {
    //the height cannot be less than zero
    try {
      new LayerPixelImpl(-1,3);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height cannot be less than zero
    try {
      new LayerPixelImpl(-100,3);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be less than zero
    try {
      new LayerPixelImpl(100,-3);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be less than zero
    try {
      new LayerPixelImpl(100,-300);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height and width cannot both be less than zero
    try {
      new LayerPixelImpl(-100,-300);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height and width cannot both be equal to zero
    try {
      new LayerPixelImpl(0,0);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height cannot be equal to zero
    try {
      new LayerPixelImpl(0,20);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be equal to zero
    try {
      new LayerPixelImpl(20,0);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }
  }

  /**
   * Tests the full constructor exceptions for Layer.
   */
  @Test
  public void fullConstructorTest() {
    //the height cannot be less than zero
    try {
      new LayerPixelImpl(-1,3,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height cannot be less than zero
    try {
      new LayerPixelImpl(-100,3,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be less than zero
    try {
      new LayerPixelImpl(100,-3,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be less than zero
    try {
      new LayerPixelImpl(100,-300,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height and width cannot both be less than zero
    try {
      new LayerPixelImpl(-100,-300,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height and width cannot both be equal to zero
    try {
      new LayerPixelImpl(0,0,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the height cannot be equal to zero
    try {
      new LayerPixelImpl(0,20,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //the width cannot be equal to zero
    try {
      new LayerPixelImpl(20,0,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",
              e.getMessage());
    }

    //when null filter
    try {
      new LayerPixelImpl(20,1,null,null);
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Filter cannot be null.",
              e.getMessage());
    }

    try {
      //when given grid contains null values and sizes don't match
      Pixel[][] temp = new Pixel[1][2];
      temp[0][0] = new RGBPixel(4,4,4);
      new LayerPixelImpl(20,1,temp,new NormalFilter());
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given grid and sizes do not match.",
              e.getMessage());
    }

    try {
      //when given grid contains null values
      Pixel[][] temp = new Pixel[1][2];
      temp[0][0] = new RGBPixel(4,4,4);
      new LayerPixelImpl(1,2,temp,new NormalFilter());
      fail("should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given grid cannot contain null values.",
              e.getMessage());
    }

    //test for when no nulls and size match
    Pixel[][] temp = new Pixel[1][1];
    temp[0][0] = new RGBPixel(4,4,4);
    new LayerPixelImpl(1,1,temp,new NormalFilter());
  }

  /**
   * Tests valid values for the Convenience Constructor for Layer.
   */
  @Test
  public void validConvConstructor() {
    //tests if height and width are applied
    Layer<Pixel> temp = new LayerPixelImpl(1,1);
    assertEquals(1,temp.getHeight());
    assertEquals(1,temp.getWidth());
    //tests if grid and filter are as expected
    Pixel[][] tempGrid = new Pixel[1][1];
    tempGrid[0][0] = defaultColour;
    assertArrayEquals(tempGrid, temp.getGrid());
    assertEquals(new NormalFilter(),temp.getFilter());

    //tests if height and width are applied independently
    temp = new LayerPixelImpl(1,2);
    assertEquals(1,temp.getHeight());
    assertEquals(2,temp.getWidth());
    //tests if grid and filter are as expected
    tempGrid = new Pixel[1][2];
    tempGrid[0][0] = defaultColour;
    tempGrid[0][1] = defaultColour;
    assertArrayEquals(tempGrid, temp.getGrid());
    assertEquals(new NormalFilter(),temp.getFilter());

    //tests if height and width for larger inputs
    temp = new LayerPixelImpl(1000,102);
    assertEquals(1000,temp.getHeight());
    assertEquals(102,temp.getWidth());
    //tests if grid and filter are as expected
    tempGrid = new Pixel[1000][102];
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 102; j++) {
        tempGrid[i][j] = defaultColour;
      }
    }
    assertArrayEquals(tempGrid, temp.getGrid());
    assertEquals(new NormalFilter(),temp.getFilter());
  }

  @Test
  public void getGridTest() {
    Pixel[][] tempGrid = new Pixel[1][1];
    tempGrid[0][0] = new RGBPixel(0,0,0,0);
    assertEquals(tempGrid,default1by1.getGrid());
  }

  @Test
  public void mergeDownLayerTest() {
    //test for null layer:
    try {
      rgb.mergeDown(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Given Layer cannot be null.",e.getMessage());
    }
    NormalFilter normal = new NormalFilter();
    Pixel[][] whiteLayer = new Pixel[1][1];
    whiteLayer[0][0] = new RGBPixel(255,255,255);
    Layer<Pixel> layer = new LayerPixelImpl(1, 1, whiteLayer, normal);
    assertEquals(whiteLayer, layer.mergeDown(whiteLayer));
  }


  @Test
  public void getFilterTest() {
    this.init();
    //tests if by default the filter is NormalFilter
    assertEquals(new NormalFilter(),default1by1.getFilter());
    assertEquals(new NormalFilter(),red.getFilter());
    assertEquals(new NormalFilter(),green.getFilter());
    assertEquals(new NormalFilter(),blue.getFilter());

    //test for when the filter is swapped
    this.init();
    //sets the filter to RedComponent
    default1by1.setFilter(new RedComponent());
    assertEquals(new RedComponent(),default1by1.getFilter());
    //sets the filter to BlueComponent
    default1by1.setFilter(new BlueComponent());
    assertEquals(new BlueComponent(),default1by1.getFilter());
    //sets the filter to GreenComponent
    default1by1.setFilter(new GreenComponent());
    assertEquals(new GreenComponent(),default1by1.getFilter());
  }

  @Test
  public void mergeDownGridTest() {
    Pixel[][] gridTemp = new Pixel[1][1];
    gridTemp[0][0] = new RGBPixel(255,255,255);

    Layer<Pixel> layer = new LayerPixelImpl(1,1,gridTemp,new GreenComponent());
    assertEquals(new RGBPixel(255,255,255),layer.getGrid()[0][0]);

    Pixel[][] newGrid = layer.mergeDown(gridTemp,gridTemp,false);
    assertEquals(new RGBPixel(255,255,255)
            .toProjString(),newGrid[0][0].toProjString());

    assertEquals(new RGBPixel(0,255,0),
            PixelUtil.computeColour(new RGBPixel(0,255,0),
                    new RGBPixel(255,255,255)));

    assertEquals(new RGBPixel(0,255,0),
            new GreenComponent().applyToColor(new RGBPixel(255,255,255)));

    newGrid = layer.mergeDown(gridTemp,gridTemp,true);
    assertEquals(new RGBPixel(0,255,0),newGrid[0][0]);
  }

  @Test
  public void getDimensionsTest() {
    NormalFilter normal = new NormalFilter();

    Layer layer2x2 = new LayerPixelImpl(2, 2, null, normal);
    assertEquals(2, layer2x2.getHeight());
    assertEquals(2, layer2x2.getWidth());

    Layer layer7x12 = new LayerPixelImpl(7, 12, null, normal);
    assertEquals(7, layer7x12.getHeight());
    assertEquals(12, layer7x12.getWidth());
  }

  @Test
  public void addImageTest() {
    Pixel bgWhite = new RGBPixel(0, 0, 0, 0);

    Pixel[][] image = PPMUtil.fromPPMFormat("P3\n2 2\n255\n" +
            "255 255 255 " +
            "255 0 0 " +
            "0 255 0 " +
            "0 0 255 ");

    Pixel[][] centered = new Pixel[2][2];
    centered[0][0] = new RGBPixel(255,255,255);
    centered[0][1] = new RGBPixel(255,0,0);
    centered[1][0] = new RGBPixel(0,255,0);
    centered[1][1] = new RGBPixel(0,0,255);

    Pixel[][] vertShift = new Pixel[2][2];
    vertShift[0][0] = bgWhite;
    vertShift[0][1] = bgWhite;
    vertShift[1][0] = new RGBPixel(255,255,255);
    vertShift[1][1] = new RGBPixel(255,0,0);

    Pixel[][] horizShift = new Pixel[2][2];
    horizShift[0][0] = bgWhite;
    horizShift[0][1] = new RGBPixel(255,255,255);
    horizShift[1][0] = bgWhite;
    horizShift[1][1] = new RGBPixel(0,255,0);

    NormalFilter normal = new NormalFilter();

    Layer<Pixel> layerCentered = new LayerPixelImpl(2, 2, null, normal);
    layerCentered.addImage(image, 0, 0);
    assertArrayEquals(centered, layerCentered.getGrid());

    Layer<Pixel> layerVertShift = new LayerPixelImpl(2, 2, null, normal);
    layerVertShift.addImage(image, 0, 1);
    assertEquals(vertShift[0][0].toProjString(),
            layerVertShift.getGrid()[0][0].toProjString());
    assertArrayEquals(vertShift, layerVertShift.getGrid());


    Layer<Pixel> layerHorizShift = new LayerPixelImpl(2, 2, null, normal);
    layerHorizShift.addImage(image, 1, 0);
    assertArrayEquals(horizShift, layerHorizShift.getGrid());

  }


  @Test
  public void testMergeDownDifferentGrids() {
    Pixel[][] grid1 = new Pixel[1][1];
    Pixel[][] grid2 = new Pixel[2][2];
    Layer<Pixel> layer = new LayerPixelImpl(2,2);


    try {
      layer.mergeDown(grid1,grid2,true);
    } catch (IllegalArgumentException e) {
      assertEquals("Top and background grid cannot" +
              " be null or contain null values.",e.getMessage());
    }


    //init the grids
    grid1[0][0] = new RGBPixel(255,0,0,255);
    grid2[0][0] = new RGBPixel(0,0,0,255);
    grid2[0][1] = new RGBPixel(0,0,0,255);
    grid2[1][0] = new RGBPixel(0,0,0,255);
    grid2[1][1] = new RGBPixel(0,0,0,255);


    //grid 2 is the background here
    Pixel[][] grid3 = layer.mergeDown(grid1,grid2,false);
    assertEquals(2,grid3.length);
    assertEquals(2,grid3[0].length);

    //grid 1 is the background here
    grid3 = layer.mergeDown(grid2,grid1,false);
    assertEquals(1,grid3.length);
    assertEquals(1,grid3[0].length);


  }

}