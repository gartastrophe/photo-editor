package model.utils;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * Represents extra utilities helpful for the reading and writing of PPM image files.
 * This includes the curation of the rgb data of the PPM into a format that the rest
 * of the program can utilize as well as creating a PPM from similar formats.
 */
public class PPMUtil {

  /**
   * Converts the given Array of Pixel to a String-based
   * PPM format.
   * @param grid represents the pixel array
   * @return the PPM format of the given Pixel array
   */
  public static String toPPMFormat(Pixel[][] grid) {
    try {
      //ensure the grid is not null
      Objects.requireNonNull(grid);
      //ensure the grid does not contain null values
      for (Pixel[] pixels : grid) {
        for (int j = 0; j < grid[0].length; j++) {
          Objects.requireNonNull(pixels[j]);
        }
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("The given pixel array " +
              "cannot be null" +
              " or contain null values.");
    }

    int max = 255;

    //add all the basic formatting to a PPM file
    StringBuilder build = new StringBuilder(String.format("P3\n%d %d\n%d\n",
            grid[0].length, grid.length, max));

    //converts each Pixel to the RGB String notation;
    //removing the alpha value
    for (Pixel[] pixels : grid) {
      for (int j = 0; j < grid[0].length; j++) {
        //Converts the pixel at the given coordinate from RGBA to RGB.
        Pixel tempPixel = PixelUtil.toThreeComponent(pixels[j]);
        String temp = tempPixel
                .toProjString()
                .substring(0,
                        tempPixel.toProjString().lastIndexOf(max + " "));
        build.append(temp);
      }
    }

    return build.toString();
  }


  /**
   * Read in a String in the ppm format and extract the
   * relevant Colour data.  This colour data is extracted
   * into a 2D array of pixels for further the Program's
   * internal use.
   *
   * @param ppmText is the String-based text of the file.
   * @return the 2D Pixel representation stored in the String
   * @throws IllegalArgumentException when the give filename
   *     cannot be found or when the token does not start with
   *     "P3".
   */
  public static Pixel[][] fromPPMFormat(String ppmText) {
    try {
      Objects.requireNonNull(ppmText);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("ppmText cannot be null.");
    }

    //create a scanner for ppmText
    Scanner sc = new Scanner(ppmText);
    Pixel[][] grid;

    try {
      String token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: plain" +
                " RAW file should begin with P3.");
      }
      int width = sc.nextInt();
      int height = sc.nextInt();
      grid = new Pixel[height][width];
      int maxValue = sc.nextInt();

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          //the following will get the proportion of colour component with respect to
          //the max value and multiply that by 255 to enforce an RGB255 representation
          int r = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
          int g = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
          int b = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
          grid[i][j] = new RGBPixel(r,g,b);
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: incorrect PPM format, " +
              "Pixels could not be extracted.");
    }

    return grid;
  }


  /**
   * This method extracts the dimensions of the given ppmText.
   * This method is distinct from the fromPPMFormat method in
   * that it only extracts the width, height, and maxValue
   * parts of the PPM file.
   *
   * <p>This functionality is separate from the fromPPMFormat
   * method as to avoid conflating the two arrays and the values
   * contained within them.  This also makes it clearer as to
   * what values are contained at which index of the array.</p>
   *
   * <p>This method can potentially pass on "illegal" values
   * if the given ppmText is corrupted.  For example, if the
   * max value in the ppmText is negative, this method will
   * pass on the negative value.  This was done to preserve
   * functionality; there should not be a "minimum" or
   * "maximum" values hard coded into the method but rather
   * whatever uses this method can decide how to deal with
   * these "illegal" values.</p>
   *
   * @param ppmText is the String-based text of the file.
   * @return the relevant dimension information in an array, namely:
   *         - width in position 0
   *         - height in position 1
   *         - maxValue in position 2
   * @throws IllegalArgumentException when the give filename
   *     cannot be found or when the token does not start with
   *     "P3".
   */
  public static int[] extractPPMDimensions(String ppmText) {
    try {
      Objects.requireNonNull(ppmText);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("ppmText cannot be null.");
    }

    //create a scanner for ppmText
    Scanner sc = new Scanner(ppmText);

    try {
      String token = sc.next();
      if (!token.equals("P3") & !token.equals("T1")) {
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file " +
                "should begin with P3.");
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: token not found.");
    }

    try {
      //WARNING: PASSES ON NEGATIVE VALUES!!!
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      int[] dims = new int[3];
      dims[0] = width;
      dims[1] = height;
      dims[2] = maxValue;
      return dims;
    } catch (InputMismatchException e) {
      throw new IllegalArgumentException("Error: InputMismatchException thrown in width, " +
              "height, max.  These values should be integers.");
    }
    catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Error: incomplete PPM header, " +
              "dimensions could not be extracted.");
    }
  }
}
