import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.CollageController;
import controller.CollageControllerImpl;
import model.model.CollageModel;
import model.model.CollageModelImpl3;
import model.pixel.Pixel;
import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;

/**
 * Creates the relevant images into the res/ folder.  This
 * test class also checks to see if the original controller
 * view and model function as intended.  Specifically, this
 * class will make a png, a jpg, and a ppm, normally, and
 * then with a red filter as well, of our image.  The tests
 * in this class only test to see if the controller runs as
 * intended and DO NOT test the functionality of the image
 * processing.
 */
public class MakeImageFormatsTest {
  private Appendable appendable;
  private Readable input;
  private CollageView view;
  private CollageModel<Pixel> model;
  private CollageController controller;


  @Before
  public void init() {
    this.appendable = new StringBuilder();
    this.view = new CollageTextView(appendable);
    this.model = new CollageModelImpl3();
  }

  @Test
  public void makePNG() {
    this.input = this.makeRed("png");
    this.controller = new CollageControllerImpl(model,view,input);
    this.controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, " +
            "quit, save-image, load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "save-image run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",this.appendable.toString());
  }

  @Test
  public void makePPM() {
    this.input = this.makeRed("ppm");
    this.controller = new CollageControllerImpl(model,view,input);
    this.controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, " +
            "quit, save-image, load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "save-image run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",this.appendable.toString());
  }


  @Test
  public void makeJPG() {
    this.input = this.makeRed("jpg");
    this.controller = new CollageControllerImpl(model,view,input);
    this.controller.runCollage();
    assertEquals("The following is a list of known commands:\n" +
            "[add-image-to-layer, set-filter, add-layer, " +
            "quit, save-image, load-project, save-project, new-project]\n" +
            "new-project run successfully!\n" +
            "add-layer run successfully!\n" +
            "add-image-to-layer run successfully!\n" +
            "save-image run successfully!\n" +
            "set-filter run successfully!\n" +
            "save-image run successfully!\n" +
            "quit run successfully!\n" +
            "No project open when quit; quitting program.",this.appendable.toString());
  }



  //takes our image, makes it red and then saves it
  private Readable makeRed(String suffix) {
    return new StringReader(
            "new-project 250 250 " +
                    "add-layer layer " +
                    "add-image-to-layer layer res/garrett_eddy_chillin.ppm  0 0 " +
                    "save-image res/garrett_eddy_chillin_" + suffix + "." + suffix + " " +
                    "set-filter layer red-component " +
                    "save-image res/garrett_eddy_chillin_" + suffix + "_red." + suffix + " " +
                    "quit " +
                    "quit ");
  }



}
