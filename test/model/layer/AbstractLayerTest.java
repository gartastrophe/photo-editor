package model.layer;

import org.junit.Test;

import model.filter.option.NormalFilter;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

/**
 * Tests the methods in AbstractLayer.
 */
public class AbstractLayerTest {

  @Test
  public void testInitPass() {
    Pixel[][] temp = new RGBPixel[2][1];
    temp[0][0] = new RGBPixel(0,0,0,0);
    temp[1][0] = new RGBPixel(0,0,0,0);

    //should not throw an exception
    Layer<Pixel> layer = new LayerPixelImpl(2,1);

    assertEquals(2,layer.getHeight());
    assertEquals(1,layer.getWidth());
    assertEquals("normal",layer.getFilterName());
    assertEquals("0 0 0 0 ",layer.getGrid()[0][0].toProjString());
    assertArrayEquals(temp,layer.getGrid());
    assertEquals("normal\n0 0 0 0 0 0 0 0 ",layer.toProjString());

    //makes a copy with the same values
    Layer<Pixel> layer2 = new LayerPixelImpl(layer.getHeight(),
            layer.getWidth(), layer.getGrid(), layer.getFilter());
    assertEquals(2,layer2.getHeight());
    assertEquals(1,layer2.getWidth());
    assertEquals("normal",layer2.getFilterName());
    assertEquals(temp[0][0],layer2.getGrid()[0][0]);
    assertArrayEquals(temp,layer2.getGrid());
    assertEquals("normal\n0 0 0 0 0 0 0 0 ",layer2.toProjString());

    //use a null grid (okay)
    Layer<Pixel> layer3 = new LayerPixelImpl(layer.getHeight(),
            layer.getWidth(), null, layer.getFilter());
    assertEquals(2,layer3.getHeight());
    assertEquals(1,layer3.getWidth());
    assertEquals("normal",layer3.getFilterName());
    assertEquals(temp[0][0],layer3.getGrid()[0][0]);
    assertArrayEquals(temp,layer3.getGrid());
    assertEquals("normal\n0 0 0 0 0 0 0 0 ",layer2.toProjString());


  }

  @Test
  public void testInitFail() {
    try {
      //test negative height
      new LayerPixelImpl(-1, 1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",e.getMessage());
    }

    try {
      //test negative width
      new LayerPixelImpl(1, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",e.getMessage());
    }

    try {
      //test negative height & width
      new LayerPixelImpl(-1, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Height or width cannot be less than or equal to zero.",e.getMessage());
    }

    try {
      //null filter
      new LayerPixelImpl(1, 2,null,null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Filter cannot be null.",e.getMessage());
    }

    //temp has a different size than the layer
    Pixel[][] temp = new Pixel[1][1];
    temp[0][0] = new RGBPixel(1,2,3,4);

    try {
      //invalid grid
      new LayerPixelImpl(2, 2,temp,null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Given grid and sizes do not match.",e.getMessage());
    }

    //temp2 contains null values: (0,1), (1,0), (1,1)
    Pixel[][] temp2 = new Pixel[2][2];
    temp2[0][0] = new RGBPixel(1,2,3,4);

    try {
      //invalid grid
      new LayerPixelImpl(2, 2,temp2,null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Given grid cannot contain null values.",e.getMessage());
    }

    //still has 1 null
    temp2[0][1] = new RGBPixel(1,2,3,4);
    temp2[1][0] = new RGBPixel(1,2,3,4);

    try {
      //invalid grid
      new LayerPixelImpl(2, 2,temp2,null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Given grid cannot contain null values.",e.getMessage());
    }
  }


  /**
   * This method tests the observers for the
   * AbstractLayer class. Specifically, tests
   * the following observer methods:
   * - getHeight()
   * - getWidth()
   * - getFilterName()
   * - getFilter()
   * - getGrid()
   */
  @Test
  public void testObservers() {
    Layer<Pixel> layer = new LayerPixelImpl(4,10);
    assertEquals(4,layer.getHeight());
    assertEquals(10,layer.getWidth());
    assertEquals("normal",layer.getFilterName());
    assertEquals(new NormalFilter(),layer.getFilter());

    //creates a temporary grid of fully transparent pixels
    Pixel[][] tempGrid = new Pixel[4][10];

    //initialises the grid
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 10; j++) {
        tempGrid[i][j] = new RGBPixel(0,0,0,0);
      }
    }

    //test to see if the grids contents are equal
    assertArrayEquals(tempGrid,layer.getGrid());

    //test to see if the grids have reference equality;
    //ie: are the grids literally the same grid?
    //layer.getGrid SHOULD produce a copy of the grid,
    //therefore they should not be the same object
    assertNotSame(tempGrid, layer.getGrid());

    //attempts to mutate the grid
    Pixel[][] mutateAttempt = layer.getGrid();
    //changes the Pixel at (0,0)
    mutateAttempt[0][0] = new RGBPixel(25,125,225,255);
    assertNotSame(mutateAttempt[0][0], layer.getGrid()[0][0]);

    //re-initialise the grid as a red rectangle
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 10; j++) {
        tempGrid[i][j] = new RGBPixel(255,0,0,255);
      }
    }
    //test to see if the grids contain the same contents
    layer.addImage(tempGrid,0,0);
    assertArrayEquals(tempGrid,layer.getGrid());

    //test to see if the grids have reference equality;
    //ie: are the grids literally the same grid?
    //layer.getGrid SHOULD produce a copy of the grid,
    //therefore they should not be the same object
    assertNotSame(tempGrid, layer.getGrid());

    //attempts to mutate the grid
    mutateAttempt = layer.getGrid();
    //changes the Pixel at (0,0)
    mutateAttempt[0][0] = new RGBPixel(25,125,225,255);
    assertNotSame(mutateAttempt[0][0], layer.getGrid()[0][0]);
  }
}
