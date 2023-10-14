package controller.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Objects;

import model.pixel.Pixel;
import model.utils.PPMUtil;

/**
 * Represents an adapter to convert a PPMString to a
 * java-accepted BufferedImage.
 */
public class PPMToBufferedImage extends BufferedImage {
  private final String PPMString;


  /**
   * Constructor to convert a PPMString to a BufferedImage.
   * Performs this action by extracting the height and width
   * from the content string, creating the buffered image
   * and then initialising all the cells from the PPMString
   * to this image.
   *
   * @param content represents the PPMString contents
   *                to convert to a BufferedImage
   * @throws IllegalArgumentException when the given
   *     String content is null.
   */
  public PPMToBufferedImage(String content) {
    //since PPMs are always an RGB representation
    //WITHOUT an alpha value, this converter should
    //also reflect this.
    super(PPMUtil.extractPPMDimensions(content)[0],
            PPMUtil.extractPPMDimensions(content)[1],
            BufferedImage.TYPE_INT_RGB);

    //ensures that the content String is not null
    this.PPMString = content;

    //initialise the PPMString into an Image
    this.init();
  }

  /**
   * This method will initialise the BufferedImage
   * to display the PPM representation from the given
   * String.
   */
  private void init() {
    //INVARIANT: PPMString at this point is never null at this point.

    //create a grid from the PPMString
    //this SHOULD propagate all exceptions thrown from
    //   ImageUtil.fromPPMFormat(PPMString)
    //so there is no try-catch
    Pixel[][] grid = PPMUtil.fromPPMFormat(PPMString);

    try {
      //make sure the grid is not null
      Objects.requireNonNull(grid);
      for (int i = 0; i < getHeight(); i++) {
        for (int j = 0; j < getWidth(); j++) {
          //make sure the grid does not contain nulls
          Objects.requireNonNull(grid[i][j]);
        }
      }
      //the following exceptions should never be thrown if
      //PPMUtil.fromPPMFormat works as intended.
      //These methods are still here to help the
      //debugging process if PPMUtil is changed
      //at any time.
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Grid and its contents cannot be null.");
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Given height and width must match" +
              "the dimensions of the PPMString.");
    }

    //for each coordinate on the grid from the PPMString
    //apply that RGB value to this BufferedImage
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {

        //get the rgb values
        int r = grid[i][j].getRed();
        int g = grid[i][j].getGreen();
        int b = grid[i][j].getBlue();

        //use Color class as an intermediary since
        //it has a built-in getRGB method in the
        //same format as BufferedImage's RGB
        this.setRGB(j, i, new Color(r, g, b).getRGB());
        //width and height are input as (width, height)
        //for BufferedImage.setRGB(...)
      }
    }
  }
}
