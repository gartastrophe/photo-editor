package model.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests the methods unique to CollageModelImpl.
 * Specifically, this means testing:
 *   - the constructor
 *   - the setFilter method that takes in two Strings
 *   - the printFilters method
 * See AbstractCollageModel test for abstracted testing.
 */
public class CollageModelImplTest {

  /**
   * Tests the unique initialisation of the known
   * filters for this CollageModelImpl.
   */
  @Test
  public void constructorTest() {
    //construct new model impl
    CollageModel model = new CollageModelImpl();

    //test inheritance from parent class, specifically the line
    //    "projectOpen = false"
    try {
      //getHeight throws an exception when projectOpen == false
      model.getHeight();
      fail("getHeight should fail");
    } catch (IllegalStateException e) {
      assertEquals("getHeight failed: No project opened.",e.getMessage());
    }

    //tests to see if the list of filters is as intended
    assertEquals("[brighten-intensity, " +
            "normal, green-component, " +
            "darken-luma, brighten-value, " +
            "darken-value, darken-intensity, " +
            "blue-component, red-component, " +
            "brighten-luma]",model.printFilters());
  }


  /**
   * Tests for the exceptions that setFilter(String,String)
   * should throw and/or propagate.
   */
  @Test
  public void setFilterTestExceptions() {
    //construct new model impl
    CollageModel model = new CollageModelImpl();

    //test for when no project is open in the model
    try {
      model.setFilter(null, null);
      fail("should fail");
    } catch (IllegalStateException e) {
      assertEquals("setFilter failed: No project opened.",
              e.getMessage());
    }

    //test for when project is open in the model
    //but layerName is null
    model.newProject(1,1);
    try {
      model.setFilter(null, null);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("setFilter failed: layer not found.",e.getMessage());
    }

    //test for when project is open in the model
    //but no layerName is found
    model.quit();
    model.newProject(1,1);
    try {
      model.setFilter("null", null);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("setFilter failed: layer not found.",e.getMessage());
    }

    //test for when project is open in the model
    //layerName is found but filter is null
    model.quit();
    model.newProject(1,1);
    model.addLayer("name");
    try {
      model.setFilter("name", null);
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("setFilter failed: FilterOption not found.",e.getMessage());
    }

    //test for when project is open in the model
    //layerName is found but filter is non-null
    //but not known filter
    model.quit();
    model.newProject(1,1);
    model.addLayer("name");
    try {
      model.setFilter("name", "null");
      fail("should fail");
    } catch (IllegalArgumentException e) {
      assertEquals("setFilter failed: FilterOption not found.",e.getMessage());
    }
  }

  /**
   * Tests for valid names for filters and layers on one layer.
   */
  @Test
  public void setFilterTestValid() {
    //construct new model impl
    CollageModel model = new CollageModelImpl();
    model.newProject(1,1);
    model.addLayer("name");
    //tests for base case (normal filter)
    assertEquals("normal",model.getFilter("name"));

    //tests for various FilterOptions:

    //tests for red-component
    model.setFilter("name","red-component");
    assertEquals("red-component",model.getFilter("name"));

    //tests for green-component
    model.setFilter("name","green-component");
    assertEquals("green-component",model.getFilter("name"));

    //tests for blue-component
    model.setFilter("name","blue-component");
    assertEquals("blue-component",model.getFilter("name"));

    //tests for brighten-value
    model.setFilter("name","brighten-value");
    assertEquals("brighten-value",model.getFilter("name"));

    //tests for brighten-intensity
    model.setFilter("name","brighten-intensity");
    assertEquals("brighten-intensity",model.getFilter("name"));

    //tests for brighten-luma
    model.setFilter("name","brighten-luma");
    assertEquals("brighten-luma",model.getFilter("name"));

    //tests for darken-value
    model.setFilter("name","darken-value");
    assertEquals("darken-value",model.getFilter("name"));

    //tests for darken-intensity
    model.setFilter("name","darken-intensity");
    assertEquals("darken-intensity",model.getFilter("name"));

    //tests for darken-luma
    model.setFilter("name","darken-luma");
    assertEquals("darken-luma",model.getFilter("name"));

    //tests for normal
    model.setFilter("name","normal");
    assertEquals("normal",model.getFilter("name"));
  }

  /**
   * Tests for valid names for filters and layers, making sure filters
   * are unique to that layer and not the Project as a whole.
   */
  @Test
  public void setFilterTestValid2() {
    //construct new model impl
    CollageModel model = new CollageModelImpl();
    model.newProject(1,1);
    model.addLayer("name");
    model.addLayer("name2");
    model.setFilter("background","red-component");

    //tests for base case (normal filter)
    assertEquals("normal",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));


    //tests for various FilterOptions:

    //tests for red-component
    model.setFilter("name","red-component");
    assertEquals("red-component",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for green-component
    model.setFilter("name","green-component");
    assertEquals("green-component",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for blue-component
    model.setFilter("name","blue-component");
    assertEquals("blue-component",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for brighten-value
    model.setFilter("name","brighten-value");
    assertEquals("brighten-value",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for brighten-intensity
    model.setFilter("name","brighten-intensity");
    assertEquals("brighten-intensity",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for brighten-luma
    model.setFilter("name","brighten-luma");
    assertEquals("brighten-luma",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for darken-value
    model.setFilter("name","darken-value");
    assertEquals("darken-value",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for darken-intensity
    model.setFilter("name","darken-intensity");
    assertEquals("darken-intensity",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for darken-luma
    model.setFilter("name","darken-luma");
    assertEquals("darken-luma",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));

    //tests for normal
    model.setFilter("name","normal");
    assertEquals("normal",model.getFilter("name"));
    assertEquals("normal",model.getFilter("name2"));
    assertEquals("red-component",model.getFilter("background"));
  }


  @Test
  public void getFiltersTest() {
    CollageModel model = new CollageModelImpl();
    assertEquals("[brighten-intensity, normal, " +
            "green-component, darken-luma, brighten-value, " +
            "darken-value, darken-intensity, blue-component, " +
            "red-component, brighten-luma]",model.printFilters());
  }

}