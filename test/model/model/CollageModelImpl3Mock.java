package model.model;


import model.layer.LayerPixelImpl;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * This is a mock for a CollageModelImpl3.  This specifically
 * overrides the newProject method such that the background
 * is not always just white but instead transparent.
 */
public class CollageModelImpl3Mock extends CollageModelImpl3 {
  //extends CollageModelImpl3 to reuse code:

  /**
   * This method overrides the original in CollageModelAbstraction
   * to show a transparent background layer so transparency with
   * pngs can actually work.  This method changes nothing else.
   */
  @Override
  public void newProject(int height, int width)
          throws IllegalStateException,IllegalArgumentException {
    if (this.projectOpen) {
      //throws an IllegalStateException if a project is already open
      throw new IllegalStateException("newProject fail; project already open.");
    }

    //this method will add a new layer to this.layers
    //which, in the constructor, will:
    //make sure the height and width are valid
    //and throw an exception if they are not.
    this.layers.put("background",new LayerPixelImpl(height,width));

    //initialises the height and width only if they are valid
    this.height = height;
    this.width = width;
    this.projectOpen = true;
    this.layersKeyOrder.add("background");


    //sets the background colour to TRANSPARENT
    Pixel[][] invisibleGrid = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        invisibleGrid[i][j] = new RGBPixel(0,0,0,0); //TRANSPARENT!!!
      }
    }

    addImageToLayerAbstraction("background", invisibleGrid,0,0);
  }


}