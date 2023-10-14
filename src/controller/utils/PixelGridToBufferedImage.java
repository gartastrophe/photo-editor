package controller.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Objects;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * This method is an adapter to convert a given String in
 * intermediaryString format or a 2D array of Pixels into
 * a BufferedImage.
 */
public class PixelGridToBufferedImage extends BufferedImage {
  private Pixel[][] gridContent;
  private final int height;
  private final int width;


  /**
   * Represents a constructor that converts a 2D array of
   * Pixel into a BufferedImage.  It initialises the following:
   * - width
   * - height
   * - grid contents
   * - this Image
   * @param content is the 2D array of Pixel
   */
  public PixelGridToBufferedImage(Pixel[][] content) {
    super(ensureGridOK(content)[0].length,
            //if the content is null, give it an illegal value
            //so BufferedImage cannot init the board
            content.length,
            //here no test is needed because it's
            //already been accounted for
            BufferedImage.TYPE_INT_ARGB);
    this.width = content[0].length;
    this.height = content.length;

    //init the grid content
    this.gridContent = content;

    this.initV2(content);
  }


  /**
   * Represents a PixelGridToBufferedImage that converts
   * a given string-based pixel grid with the token
   * "T1\n" into a BufferedImage.  It initialises
   * the following:
   * - width
   * - height
   * - grid contents
   * - this Image
   * from the String.
   * @param content is the rasterised pixel grid
   *                in a String format
   */
  public PixelGridToBufferedImage(String content) {
    super(Integer.parseInt(parseCont(content)[1]),
            Integer.parseInt(parseCont(content)[2]),
            BufferedImage.TYPE_INT_ARGB);

    //init the base data
    this.width = Integer.parseInt(parseCont(content)[1]);
    this.height = Integer.parseInt(parseCont(content)[2]);

    //init the grid
    this.gridContent = new Pixel[height][width];

    //initialise this BufferedImage
    this.init(content);
  }

  //represents the init for the 2D array of pixels
  //inits the pixels one at a time after making
  //sure the pixel does not contain any null values
  //throws an IllegalArgumentException when null values
  //exist in the content
  private void initV2(Pixel[][] content) throws IllegalArgumentException {
    //for each coordinate on the grid from the PPMString
    //apply that RGB value to this BufferedImage
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {

        //INVARIANT: CANNOT BE NULL AT THIS POINT

        //get the rgb values
        int r = content[i][j].getRed();
        int g = content[i][j].getGreen();
        int b = content[i][j].getBlue();
        int a = content[i][j].getAlpha();

        //use Color class as an intermediary since
        //it has a built-in getRGB method in the
        //same format as BufferedImage's RGB
        this.setRGB(j, i, new Color(r,g,b,a).getRGB());
        //width and height are input as (width, height)
        //for BufferedImage.setRGB(...)
      }
    }
  }

  /**
   * This method will initialise the BufferedImage
   * to display the 4 component pixel representation
   * from the given String.
   */
  private void init(String content) {
    //INVARIANT: content should never be null at this point.

    //split based on "\n" or " "
    String[] components = content.split("\n| ");

    /*
     * for an array of len n:
     * c1=
     * 4 5 6 7
     * c2=
     * 8 9 10 11
     * c3=
     * 12 13 14 15
     * ...
     * c?=
     * n-3, n-2, n-1, n
     *
     */

    //loop for initialising the grid
    for (int i = 0; i < height; i++) { //height
      for (int j = 0; j < width; j++) { //width
        //convert height and width to the pixel #
        int pixelNum = (i * width + j);

        //rIndex represents the index of this
        //pixel's red value in the array of
        //components
        int rIndex = pixelNum * 4 + 4; //0,0 should be 4 so +4

        int r = Integer.parseInt(components[rIndex]);
        int g = Integer.parseInt(components[rIndex + 1]);
        int b = Integer.parseInt(components[rIndex + 2]);
        int a = Integer.parseInt(components[rIndex + 3]);

        gridContent[i][j] = new RGBPixel(r,g,b,a);
      }
    }

    try {
      //make sure the grid is not null
      Objects.requireNonNull(gridContent);
      for (int i = 0; i < getHeight(); i++) {
        for (int j = 0; j < getWidth(); j++) {
          //make sure the grid does not contain nulls
          Objects.requireNonNull(gridContent[i][j]);
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
        int r = gridContent[i][j].getRed();
        int g = gridContent[i][j].getGreen();
        int b = gridContent[i][j].getBlue();
        int a = gridContent[i][j].getAlpha();

        //use Color class as an intermediary since
        //it has a built-in getRGB method in the
        //same format as BufferedImage's RGB
        this.setRGB(j, i, new Color(r,g,b,a).getRGB());
        //width and height are input as (width, height)
        //for BufferedImage.setRGB(...)
      }
    }
  }

  /**
   * This method creates a clone of the given 2D array of pixels (in
   * String or pixel format) and then returns this grid.  While this
   * method technically leaks implementation details, the client
   * effectively already knew the implementation details, so it's
   * not a big deal.  Also, this method returns a clone to preserve
   * data integrity.
   * @return a clone of the 2D Pixel representation of this BufferedImage.
   */
  public Pixel[][] getGridContent() {
    return this.gridContent.clone();
  }

  //parses the first four elements of the content if possible
  private static String[] parseCont(String content) {
    try {
      Objects.requireNonNull(content);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("content cannot be null");
    }
    return content.split("\n| ");
  }

  //ensures the given grid is OK
  private static Pixel[][] ensureGridOK(Pixel[][] grid) {
    //tests for null grid
    try {
      Objects.requireNonNull(grid);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("content cannot be null");
    }

    //for each coordinate on the grid from the PPMString
    //apply that RGB value to this BufferedImage
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {

        try {
          Objects.requireNonNull(grid[i][j]);
        } catch (NullPointerException e) {
          throw new IllegalArgumentException("Contents contain null values.");
        }
      }
    }
    return grid;
  }
}
