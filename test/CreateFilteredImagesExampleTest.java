import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.CollageController;
import controller.CollageControllerImpl;
import controller.utils.IOUtil;
import model.model.CollageModel;
import model.model.CollageModelImpl2;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;
import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;


/**
 * Creates the relevant images into the res/ folder.  This
 * test class also checks to see if the original controller
 * view and model function as intended.
 */
public class CreateFilteredImagesExampleTest {

  /**
   * This method will attempt to read res/threeByThree.ppm.
   * If res/threeByThree.ppm already exists AND it exists
   * as intended, then use the existing file for future
   * reference.  If res/threeByThree.ppm does not already
   * exist OR it does not exist as this class expects it
   * to, overwrite res/threeByThree.ppm with the version
   * expected in this class.
   */
  @Before
  public void createImage() {
    try {
      //attempt to read the file: res/threeByThree.ppm
      IOUtil.readFile("res/threeByThree.ppm");
      //if reading fails for any reason, create a
      //new functional "res/threeByThree.ppm".

      //tests to see if the locations are right;
      //if they're not right, throw an assertion error
      threeByThreeLoc();

    } catch (IllegalArgumentException | AssertionError e) {
      //if any errors occur, reinitialise res/threeByThree.ppm
      //this method creates a PPM
      //with the following colours
      //in a 3 by 3 square of squares:
      //    white, red, orange,
      //    yellow, green, blue
      //    purple, black, pink
      //each inner square will be 50 by 50 pixels
      //and the total PPM image will be 150 by 150
      Pixel[][] threeByThree = new Pixel[150][150];

      //define an array in this order:
      List<Pixel> order = new ArrayList<>(Arrays.asList(
              //white red orange
              new RGBPixel(255, 255, 255, 255),
              new RGBPixel(255, 0, 0, 255),
              new RGBPixel(255, 165, 0, 255),
              //yellow green blue
              new RGBPixel(255, 255, 0, 255),
              new RGBPixel(0, 255, 0, 255),
              new RGBPixel(0, 0, 255, 255),
              //purple violet black
              new RGBPixel(255, 0, 255, 255),
              new RGBPixel(127, 0, 255, 255),
              new RGBPixel(0, 0, 0, 255)));

      //create a loop to make these 150 pixels
      for (int i = 0; i < 150; i++) {
        //integer division (floored)
        int tempI = i / 50;
        for (int j = 0; j < 150; j++) {

          //integer division (floored)
          int tempJ = j / 50;

          //uses the i and j values to get which
          //colour in 'order' to use.
          int tempIndex = tempJ + (tempI * 3);
          //the three is hard coded cause this only
          //writes this one given image:

          //sets the Pixel in threeByThree to the
          //designated index value.
          threeByThree[i][j] = order.get(tempIndex);
        }
      }

      IOUtil.writeFile("res/threeByThree.ppm",
              PPMUtil.toPPMFormat(threeByThree));
    }
  }

  /**
   * Tests to see if the RGBPixel values in res/threeByThree.ppm exist
   * as intended.  This method throws an AssertionError if the colour
   * values are not as intended (by default).
   */
  @Test
  public void threeByThreeLoc() {
    String ppm = IOUtil.readFile("res/threeByThree.ppm");
    Pixel[][] grid = PPMUtil.fromPPMFormat(ppm);
    //test whites
    assertEquals(new RGBPixel(255, 255, 255, 255),grid[0][0]);
    assertEquals(new RGBPixel(255, 255, 255, 255),grid[49][49]);
    //test reds
    assertEquals(new RGBPixel(255, 0, 0, 255),grid[0][50]);
    assertEquals(new RGBPixel(255, 0, 0, 255),grid[49][99]);
    //test oranges
    assertEquals(new RGBPixel(255, 165, 0, 255),grid[0][100]);
    assertEquals(new RGBPixel(255, 165, 0, 255),grid[49][149]);
    //test yellows
    assertEquals(new RGBPixel(255, 255, 0, 255),grid[50][0]);
    assertEquals(new RGBPixel(255, 255, 0, 255),grid[99][49]);
    //test greens
    assertEquals(new RGBPixel(0,255, 0, 255),grid[50][50]);
    assertEquals(new RGBPixel(0,255, 0, 255),grid[99][99]);
    //test blues
    assertEquals(new RGBPixel(0,0, 255, 255),grid[50][100]);
    assertEquals(new RGBPixel(0,0, 255, 255),grid[99][149]);

    //test purples
    assertEquals(new RGBPixel(255,0,255, 255),grid[100][0]);
    assertEquals(new RGBPixel(255,0,255, 255),grid[149][49]);

    //test violets
    assertEquals(new RGBPixel(127,0, 255, 255),grid[100][50]);
    assertEquals(new RGBPixel(127,0, 255, 255),grid[149][99]);

    //test blacks
    assertEquals(new RGBPixel(0,0, 0, 255),grid[100][100]);
    assertEquals(new RGBPixel(0,0, 0, 255),grid[149][149]);
  }


  /**
   * This method tests if any errors are thrown when creating
   * a difference filtered image through the controller.
   * This method does NOT check for the functionality of
   * the underlying filters.  This method also creates
   * a rendering of the image to the src folder.
   */
  @Test
  public void writeDifference() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("difference",
            "garrett_eddy_chillin_difference.ppm",
            "add-layer mid " +
                    "add-image-to-layer mid res/threeByThree.ppm 50 50 ");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, " +
            "quit, save-image, load-project, " +
            "save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",
            appendable.toString());
  }


  /**
   * This method tests if any errors are thrown when creating
   * a screen filtered image through the controller.
   * This method does NOT check for the functionality of
   * the underlying filters.  This method also creates
   * a rendering of the image to the src folder.
   */
  @Test
  public void writeScreen() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("screen",
            "garrett_eddy_chillin_screen.ppm",
            "add-layer mid " +
                    "add-image-to-layer mid res/threeByThree.ppm 50 50 ");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, " +
            "quit, save-image, load-project, save-project, " +
            "new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }

  /**
   * This method tests if any errors are thrown when creating
   * a multiply filtered image through the controller.
   * This method does NOT check for the functionality of
   * the underlying filters.  This method also creates
   * a rendering of the image to the src folder.
   */
  @Test
  public void writeMultiply() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("multiply",
            "garrett_eddy_chillin_multiply.ppm",
            "add-layer mid " +
                    "add-image-to-layer mid res/threeByThree.ppm 50 50 ");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }

  /**
   * Tests the BrightenValue filter and writes to a new ppm file.
   */
  @Test
  public void writeBrightenValue() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("brighten-value",
            "garrett_eddy_chillin_brighten_value.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }

  /**
   * Tests the BrightenIntensity filter and writes to a new ppm file.
   */
  @Test
  public void writeBrightenIntensity() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("brighten-intensity",
            "garrett_eddy_chillin_brighten_intensity.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }


  /**
   * Tests the BrightenLuma filter and writes to a new ppm file.
   */
  @Test
  public void writeBrightenLuma() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("brighten-luma",
            "garrett_eddy_chillin_brighten_luma.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }

  /**
   * Tests the DarkenValue filter and writes to a new ppm file.
   */
  @Test
  public void writeDarkenValue() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("darken-value",
            "garrett_eddy_chillin_darken_value.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }

  /**
   * Tests the DarkenIntensity filter and writes to a new ppm file.
   */
  @Test
  public void writeDarkenIntensity() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("darken-intensity",
            "garrett_eddy_chillin_darken_intensity.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }


  /**
   * Tests the DarkenLuma filter and writes to a new ppm file.
   */
  @Test
  public void writeDarkenLuma() {
    //these represent repeated commands
    CollageModel<Pixel> model = new CollageModelImpl2();
    Appendable appendable = new StringBuilder();
    CollageView view  = new CollageTextView(appendable);
    StringReader readable = readableHelper("darken-luma",
            "garrett_eddy_chillin_darken_luma.ppm","");
    CollageController controller = new CollageControllerImpl(model,view,readable);
    controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, quit, save-image, " +
            "load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",appendable.toString());
  }


  //represents a helper test for the basic functionality of the writing.
  //this has a general framework that creates a new project, does
  //some function(s), adds the image res/garrett_eddy_chillin.ppm,
  //sets a filter of on that image, saves the image as a specified
  //string-based file path, quits the project and then the program.
  private StringReader readableHelper(String filterName,String saveAs,String extra) {
    return new StringReader("new-project 250 250 " +
            extra +
            "add-layer image-layer " +
            "add-image-to-layer image-layer res/garrett_eddy_chillin.ppm 0 0 " +
            "set-filter image-layer " + filterName + " " +
            "save-image " + saveAs + " " +
            "quit " +
            "quit");
  }

}
