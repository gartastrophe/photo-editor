package controller.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;

/**
 * Represents a version of an IOUtil that can handle at
 * least four types of files.  These files are:
 * - png
 * - ppm
 * - jpg
 * This class can read these files and turn them into
 * a two-dimensional array of Pixel.  Similarly, it
 * can also write to a file of:
 * - png
 * - ppm
 * - jpg
 * - txt
 * when given a String in the "intermediaryString" format.
 * Notably, this method hinges on the class IOUtil for
 * ppm and text based functionality.
 */
public class IOUtil2 implements IOUtilInterface {

  @Override
  public Pixel[][] readFile(String path) {
    try {
      Objects.requireNonNull(path);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Given path cannot be null.");
    }

    //parse the suffix
    String suffix = this.parseSuffix(path);

    Pixel[][] grid;
    if (suffix.equals("ppm")) {
      //if the suffix is a PPM or a text file
      //then it should do the same thing as the
      //original IOUtil
      return PPMUtil.fromPPMFormat(IOUtil.readFile(path));
    } else {
      try {
        //get the file from the path
        File file = new File(path);
        //convert the file into a BufferedImage
        BufferedImage img = ImageIO.read(file);

        //get the alpha raster, if possible
        Raster alphaRaster = img.getAlphaRaster();

        //initialise the Pixel-based representation
        //of this grid.
        grid = new Pixel[img.getHeight()][img.getWidth()];

        //gets all the information from the image
        //to the grid
        for (int i = 0; i < img.getHeight(); i++) {
          for (int j = 0; j < img.getWidth(); j++) {
            //use Color method since it already
            //has a built-in getRGB
            Color c = new Color(img.getRGB(j, i));

            //base case for the alpha
            //(helps the program work w/
            //jpegs and such)
            int alpha = c.getAlpha();

            //this is for images where
            //there is an alpha (eg: png)
            if (alphaRaster != null) {
              //using alpha raster here to get the alpha to show up
              int[] oneCell = new int[1];
              alpha = alphaRaster.getPixel(j, i, oneCell)[0];
            }
            //if there is no alphaRaster, uses the
            //predefined alpha of the colour (255)

            //sets the pixel in the grid to the
            //given pixel; accounts for the
            //original image's alpha value.
            grid[i][j] = new RGBPixel(
                    c.getRed(),
                    c.getGreen(),
                    c.getBlue(),
                    alpha);
          }
        }

      } catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    return grid;
  }

  @Override
  public void writeFile(String fileName, String contents) {
    //declare the variable filePath
    //which represents the path to
    //the PPM file using the given fileName.
    String filePath;
    String tempContents;

    //test for null values
    try {
      filePath = Objects.requireNonNull(fileName);
      tempContents = Objects.requireNonNull(contents);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("fileName & contents cannot be null.");
    }

    //parse the suffix
    String suffix = this.parseSuffix(filePath);

    //declare img (should never remain null)
    BufferedImage img = null;

    //switch statement based on what type
    //of suffix there is; at the moment
    //this only supports ppm, txt, jpg,
    //and png
    switch (suffix) {
      case "ppm":
      case "txt":
        String token = tempContents.substring(0,3);
        if (token.equals("T1\n")) {
          //converts the temporary string
          //--through a Pixel[][]--
          //to an alpha-less ppm format
          tempContents = PPMUtil.toPPMFormat(
                  new PixelGridToBufferedImage(tempContents).getGridContent());
        }

        IOUtil.writeFile(filePath,tempContents);
        break;
      case "jpg":
        //the jpg format is effectively the same as the ppm format (3 components)
        img = new PPMToBufferedImage(PPMUtil.toPPMFormat(
                new PixelGridToBufferedImage(tempContents).getGridContent()));
        break;
      case "png":
        img = new PixelGridToBufferedImage(tempContents);
        break;
      default:
        throw new IllegalArgumentException("File type not supported at this time.");
    }

    try {
      if (img == null) {
        throw new IllegalArgumentException("skip");
      }

      //try opening a fileWriter
      FileWriter writer = new FileWriter(fileName);
      writer.write("");
      writer.flush();
      writer.close();

      //file now exists
      if (new File(fileName).exists()) {
        //try writing
        ImageIO.write(img, suffix, new File(fileName));
      } else {
        throw new IOException("File does not exist.");
      }

    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (IllegalArgumentException ignore) {
      //should be ignored because
      //this only happens when the
      //img type is a ppm or a txt
    }

  }

  /**
   * parseString represents a method which extracts
   * the suffix of the file type WITHOUT the period.
   * for example, if you provided "abc.def", the
   * parseSuffix would return "def".
   * @param filePath represents the filePath to read/write to.
   * @return the suffix withOUT the "." attached.
   * @throws IllegalArgumentException when no suffix is in the filePath.
   */
  private String parseSuffix(String filePath) {
    if (filePath.indexOf('.') == -1) {
      throw new IllegalArgumentException("Error: no suffix found.");
    } else {
      return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
  }
}
