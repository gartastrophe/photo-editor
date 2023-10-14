package controller.features;

import org.junit.Test;

import java.io.File;

import controller.utils.IOUtil;
import controller.utils.IOUtil2;
import controller.utils.IOUtilInterface;
import model.model.CollageModel;
import model.model.CollageModelImpl2;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;
import view.ImageCollageView;
import view.ImageCollageViewMock;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the Features controller's methods using
 * a CollageModelImpl2 and a mock of the view.
 */
public class FeaturesControllerTest {

  @Test
  public void saveImageTest() {
    //tests the save image call to the controller:
    CollageModel<Pixel> model = new CollageModelImpl2();
    ImageCollageView view = new ImageCollageViewMock();
    IOUtilInterface io = new IOUtil2();
    FeaturesController controller = new FeaturesController(model,view,io);
    //create a 1 by 1 model
    model.newProject(1,1);
    //set background to red
    model.setFilter("background","red-component");
    assertEquals("P3\n" + //ppm file
                    "1 1\n" + //size = 1 by 1
                    "255\n" + //max = 255
                    "255 0 0 ", //red
            model.saveImage());

    //tries saving an image w/ the controller
    controller.saveImage("res/fromFeatures.ppm");
    String fromFeatures = IOUtil.readFile("res/fromFeatures.ppm");
    assertEquals(model.saveImage() + "\n",
            //new line separator added when reading
            fromFeatures);

    //test to see if grid is as intended
    Pixel[][] grid = PPMUtil.fromPPMFormat(fromFeatures);
    assertEquals(new RGBPixel(255,0,0,255),grid[0][0]);


    //set it to blue to make sure testing works
    model.setFilter("background","blue-component");
    assertEquals("P3\n" + //ppm file
                    "1 1\n" + //size = 1 by 1
                    "255\n" + //max = 255
                    "0 0 255 ", //blue
            model.saveImage());

    //test what the view uses (uses the relative path)
    String relPath = new File("test.ppm").getPath();
    controller.saveImage(relPath);

    assertEquals("P3\n" + //ppm file
                    "1 1\n" + //size = 1 by 1
                    "255\n" + //max = 255
                    "0 0 255 " + //blue
                    "\n", //added from reading
            IOUtil.readFile("res/test.ppm"));

  }

}