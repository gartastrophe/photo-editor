//package model.model;
//
//import org.junit.Test;
//
//import java.awt.Color;
//
//import controller.operations.ControllerOperations;
//import controller.operations.OpAddImageToLayer;
//import controller.operations.OpAddLayer;
//import controller.operations.OpNewProject;
//import controller.operations.OpSaveImage;
//import controller.operations.OpSetFilter;
//import model.ImageUtil;
//import model.filter.option.NormalFilter;
//import model.filter.option.colour.components.BlueComponent;
//import model.layer.LayerImpl;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Tests the AbstractCollageModel class.
// */
//public class AbstractCollageModelTest {
//
//
//  /**
//   * Tests for writing to a ppm file.
//   */
//  @Test
//  public void PPMTest1() {
//    //test for
//    Color[][] grid = new Color[2][1];
//
//    //test for when grid not init
//    try {
//      ImageUtil.writePPM("saveImageTestInAbstractCollageModel", grid);
//    } catch (IllegalArgumentException e) {
//      assertEquals("fileName and grid cannot be null.",e.getMessage());
//    }
//
//    grid[1][0] = Color.BLUE;
//    try {
//      //should still throw the exceptions since [0][0] is null
//      ImageUtil.writePPM("saveImageTestInAbstractCollageModel", grid);
//    } catch (IllegalArgumentException e) {
//      assertEquals("fileName and grid cannot be null.",e.getMessage());
//    }
//
//    //now it should be good now:
//    grid[0][0] = Color.RED;
//    ImageUtil.writePPM("saveImageTestInAbstractCollageModel", grid);
//  }
//
//  /**
//   * Tests for compressing layers.
//   */
//  @Test
//  public void compressLayersTest() {
//    CollageModel model = new CollageModelImpl();
//    ControllerOperations op = new OpNewProject(2, 2);
//    op.execute(model);
//    op = new OpAddLayer("layer1");
//    op.execute(model);
//    op = new OpAddImageToLayer("layer1", "res/wrgb2x2.ppm", 0, 0);
//    op.execute(model);
//    op = new OpSaveImage("saved");
//    op.execute(model);
//
//    Color [][] imgGrid = new Color[2][2];
//    imgGrid[0][0] = Color.WHITE;
//    imgGrid[0][1] = Color.RED;
//    imgGrid[1][0] = Color.GREEN;
//    imgGrid[1][1] = Color.BLUE;
//
//    assertEquals(ImageUtil.readPPM("res/saved.ppm"), imgGrid);
//
//  }
//
//  /**
//   * Tests for saveImage method in AbstractCollageModel.
//   */
//  @Test
//  public void saveImageTest() {
//    //init
//    CollageModel model = new CollageModelImpl();
//
//
//    //tests for IllegalStateException
//    try {
//      model.saveImage("a");
//    } catch (IllegalStateException e) {
//      assertEquals("saveImage failed: No project opened.",e.getMessage());
//    }
//
//    //make a new project
//    model.newProject(2,1);
//    model.saveImage("saveImageTest");
//
//    Color [][] imgGrid = new Color[2][1];
//    imgGrid[0][0] = Color.WHITE;
//    imgGrid[1][0] = Color.WHITE;
//
//    assertEquals(ImageUtil.readPPM("res/saveImageTest.ppm"), imgGrid);
//
//  }
//
//  @Test
//  public void getDimensionsTest() {
//    CollageModel model = new CollageModelImpl();
//    ControllerOperations op = new OpNewProject(3, 4);
//    try {
//      assertEquals(model.getHeight(), 3);
//    } catch (IllegalStateException e) {
//      assertEquals("getHeight failed: No project opened.", e.getMessage());
//    }
//    try {
//      assertEquals(model.getWidth(), 4);
//    } catch (IllegalStateException e) {
//      assertEquals("getWidth failed: No project opened.", e.getMessage());
//    }
//    op.execute(model);
//
//    assertEquals(model.getHeight(), 3);
//    assertEquals(model.getWidth(), 4);
//  }
//
//  @Test
//  public void getLayerTest() {
//    CollageModel model = new CollageModelImpl();
//    try {
//      assertEquals(model.getLayer("whatever"), new LayerImpl(3, 4));
//    } catch (IllegalStateException e) {
//      assertEquals("getLayer failed: No project opened.", e.getMessage());
//    }
//    ControllerOperations op = new OpNewProject(3, 4);
//    op.execute(model);
//    try {
//      assertEquals(model.getLayer("whatever"), new LayerImpl(3, 4));
//    } catch (IllegalArgumentException e) {
//      assertEquals("getLayer failed: layer not found.", e.getMessage());
//    }
//    op = new OpAddLayer("layerName");
//    op.execute(model);
//    Color[][] grid = new Color[3][4];
//
//    for (int i = 0; i < 3; i++) {
//      for (int j = 0; j < 4; j++) {
//        grid[i][j] = new Color(255, 255, 255, 0);
//      }
//    }
//    assertEquals(model.getLayer("layerName").getGrid(), grid);
//  }
//
//
//  @Test
//  public void getFilterTest() {
//    CollageModel model = new CollageModelImpl();
//    ControllerOperations op = new OpNewProject(3, 4);
//    try {
//      assertEquals(model.getFilter("anyLayer"), "whatever-filter");
//    } catch (IllegalStateException e) {
//      assertEquals("getFilter failed: No project opened.", e.getMessage());
//    }
//    op.execute(model);
//    try {
//      assertEquals(model.getFilter("anyLayer"), "whatever-filter");
//    } catch (IllegalArgumentException e) {
//      assertEquals("getFilter failed: layer not found.", e.getMessage());
//    }
//    op = new OpAddLayer("layer");
//    op.execute(model);
//    assertEquals(model.getFilter("layer"), new NormalFilter());
//
//    op = new OpSetFilter("layer", "blue-component");
//    op.execute(model);
//    assertEquals(model.getFilter("layer"), new BlueComponent());
//
//  }
//
//  @Test
//  public void addImageToLayerNullGridTest() {
//    CollageModel model = new CollageModelImpl();
//    model.newProject(2,3);
//
//    //test for when nullGrid contains null values
//    Color[][] nullGrid = new Color[2][3];
//
//    try {
//      model.addImageToLayer("background", nullGrid, 1, 2);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Grid cannot be null.",e.getMessage());
//    }
//
//    //test for when null grid itself is null
//    nullGrid = null;
//
//    try {
//      model.addImageToLayer("background", nullGrid, 1, 2);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Grid cannot be null.",e.getMessage());
//    }
//  }
//
//
//  @Test
//  public void addLayerExceptions() {
//    CollageModel model = new CollageModelImpl();
//    model.newProject(2,3);
//    //gives model a null layer
//    try {
//      model.addLayer(null);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Operation failed: null sourceMethod.",e.getMessage());
//    }
//
//    try {
//      model.addLayer("background");
//    } catch (IllegalArgumentException e) {
//      assertEquals("addLayer failed: layerName already exists.",e.getMessage());
//    }
//  }
//
//  @Test
//  public void loadProjTest() {
//    CollageModel model = new CollageModelImpl();
//    try {
//      model.loadProject("AHHHHH");
//    } catch (IllegalArgumentException e) {
//      assertEquals("File AHHHHH not found!",e.getMessage());
//    }
//
//    model.newProject(1,2);
//    model.addLayer("a");
//    model.setFilter("a","red-component");
//    model.saveProject("res/test1.txt");
//
//    try {
//      model.loadProject("res/test1.txt");
//    } catch (IllegalStateException e) {
//      assertEquals("loadProject fail; project already open.",e.getMessage());
//    }
//    model.quit();
//    model.loadProject("res/test1.txt");
//  }
//
//  @Test
//  public void newProjectIllegalState() {
//    CollageModel model = new CollageModelImpl();
//    model.newProject(1,1);
//    try {
//      model.newProject(1,1);
//    } catch (IllegalStateException e) {
//      assertEquals("newProject fail; project already open.",e.getMessage());
//    }
//  }
//
//
//}