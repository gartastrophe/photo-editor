import org.junit.Before;
import org.junit.Test;

import model.filter.option.NormalFilter;
import model.filter.option.blend.Difference;
import model.filter.option.blend.Multiply;
import model.filter.option.blend.Screen;
import model.filter.option.brightness.BrightenValue;
import model.filter.option.colour.components.BlueComponent;
import model.filter.option.colour.components.GreenComponent;
import model.filter.option.colour.components.RedComponent;
import model.layer.Layer;
import model.layer.LayerPixelImpl;
import model.model.CollageModel;
import model.model.CollageModelImpl;
import model.model.CollageModelImpl2;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;


/**
 * Tests the new functionality of the AbstractCollageModel.
 * This specifically tests the new set filter, the
 * printFilters, and saveImage functionality
 */
public class AbstractCollageModelNewTest {
  CollageModel<Pixel> model;

  @Before
  public void init() {
    model = new CollageModelImpl2();
  }

  @Test
  public void setFilterTest() {
    model.newProject(2,1);
    //test for base case normal filter name
    assertEquals("normal",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new NormalFilter(),
            model.getLayer("background")
                    .getFilter());

    //try explicitly setting background to "normal"
    model.setFilter("background","normal");
    assertEquals("normal",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new NormalFilter(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to red-component
    model.setFilter("background","red-component");
    assertEquals("red-component",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new RedComponent(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to green-component
    model.setFilter("background","green-component");
    assertEquals("green-component",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new GreenComponent(),
            model.getLayer("background")
                    .getFilter());


    //try setting background filter to blue-component
    model.setFilter("background","blue-component");
    assertEquals("blue-component",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new BlueComponent(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to brighten-value
    model.setFilter("background","brighten-value");
    assertEquals("brighten-value",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new BrightenValue(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to difference
    model.setFilter("background","difference");
    assertEquals("difference",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new Difference(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to multiply
    model.setFilter("background","multiply");
    assertEquals("multiply",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new Multiply(),
            model.getLayer("background")
                    .getFilter());

    //try setting background filter to Screen
    model.setFilter("background","screen");
    assertEquals("screen",
            model.getLayer("background")
                    .getFilterName());

    //tests for the object
    assertEquals(new Screen(),
            model.getLayer("background")
                    .getFilter());

  }


  /**
   * Tests the saveProject method with the
   * String-based output.
   */
  @Test
  public void saveProjectTest() {
    try {
      model.saveProject();
    } catch (IllegalStateException e) {
      //should not be trying to make a model
      //with no project open
      assertEquals("saveProject failed: No project opened.",e.getMessage());
    }

    model.newProject(1,2);

    Layer<Pixel> bg = new LayerPixelImpl(1,2);
    assertEquals(new RGBPixel(0,0,0,0), bg.getGrid()[0][0]);
    assertEquals(new RGBPixel(0,0,0,0), bg.getGrid()[0][1]);
    assertEquals(2,bg.getWidth());
    assertEquals(1,bg.getHeight());
    assertEquals("normal",bg.getFilterName());
    assertEquals("normal\n" +
            "0 0 0 0 0 0 0 0 ",bg.toProjString());

    model.addLayer("a");
    assertEquals("normal\n" +
            "0 0 0 0 0 0 0 0 ",model.getLayer("a").toProjString());

    assertEquals("C1\n2 1\n255\nbackground normal\n" +
            "255 255 255 255 255 255 255 255 \n" +
            "a normal\n" +
            "0 0 0 0 0 0 0 0 \n",model.saveProject());
  }


  /**
   * Tests the printFilters method for Different Collage models.
   * This specifically tests to see if the new filters for
   * CollageModelImpl2 are added to the list of filters.
   */
  @Test
  public void printFiltersTest() {
    CollageModelImpl m1 = new CollageModelImpl();
    CollageModelImpl2 m2 = new CollageModelImpl2();
    //does not contain
    assertEquals("[brighten-intensity, normal, green-component, darken-luma, " +
            "brighten-value, darken-value, darken-intensity, " +
            "blue-component, red-component, brighten-luma]",m1.printFilters());
    assertEquals("[normal, green-component, darken-intensity, " +
            "screen, " + //in m2 but not m1
            "brighten-luma, brighten-intensity, darken-luma, " +
            "brighten-value, darken-value, " +
            "difference, " + //in m2 but not m1
            "blue-component, " +
            "multiply, " + //in m2 but not m1
            "red-component]",m2.printFilters());
  }



}