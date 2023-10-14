package controller.features;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import controller.operations.ControllerOperations;
import controller.operations.OpAddImageToLayer;
import controller.operations.OpAddLayer;
import controller.operations.OpNewProject;
import controller.operations.OpSetFilter;
import controller.utils.IOUtilInterface;
import model.model.CollageModel;
import view.ImageCollageView;

/**
 * Represents a controller for the GUI.  This helps the GUI
 * communicate with the model while maintaining the MVC design
 * pattern.
 */
public class FeaturesController implements Features {

  private final CollageModel model;
  private final ImageCollageView view;
  private final IOUtilInterface util;


  /**
   * Creates a new controller with the given model and
   * view.  Model and view cannot be null.
   * @param model model for the current program
   * @param view graphical view for this controller
   */
  public FeaturesController(CollageModel model, ImageCollageView view,
                            IOUtilInterface util) {
    try {
      this.model = Objects.requireNonNull(model);
      this.view = Objects.requireNonNull(view);
      this.view.addFeatures(this);
      this.util = Objects.requireNonNull(util);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("model & view cannot be null.");
    }
  }

  @Override
  public void newProject(int height, int width) {
    ControllerOperations op = new OpNewProject(height,width);
    this.executioner(op);
    this.view.initFilters(this.model.printFilters());
    //refresh the view
    refresh();
  }

  @Override
  public void saveImage(String path) {
    try {
      util.writeFile(path,model.saveImage());
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage(e.getMessage());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    //refresh the view
    refresh();
  }

  @Override
  public void saveProject(String path) {
    try {
      util.writeFile(path,model.saveProject());
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage(e.getMessage());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    //refresh the view
    refresh();
  }

  @Override
  public void loadProject(String path) {
    try {
      util.readFile(path);
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage(e.getMessage());
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    this.view.initFilters(this.model.printFilters());
    //refresh the view
    refresh();
  }

  @Override
  public void addLayer(String name) {
    ControllerOperations add = new OpAddLayer(name);
    this.executioner(add);
    //refresh the view
    refresh();
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String x, String y) {

    int xVal = 0;
    int yVal = 0;
    try {
      //ensure the given coords are integers
      xVal = Integer.parseInt(x);
      yVal = Integer.parseInt(y);
    } catch (IllegalArgumentException ex) {
      this.printMsg("x and y coordinates must be integers.");
    }

    ControllerOperations add = new OpAddImageToLayer(layerName, filePath, xVal, yVal,util);
    this.executioner(add);
    //refresh the view
    refresh();
  }

  @Override
  public void setFilter(String layerName, String filterName) {
    ControllerOperations set = new OpSetFilter(layerName, filterName);
    this.executioner(set);
    //refresh the view
    refresh();
  }

  //refreshes the view:
  private void refresh() {
    //model.saveImage returns a ppm string
    //which can be used to update the model's view
    this.view.refreshBetter(model.saveImage());
  }

  @Override
  public List<String> retrieveLayers() {
    return this.model.getOrder();
  }

  //represents abstractions for the controller operations in
  //this class.  this method executes the given operation and
  //then renders the exception to the view.  If the render
  //message ever throws an IOException, this class will throw
  //an IllegalStateException telling the client that the out
  //put to the view failed.
  private void executioner(ControllerOperations op) throws IllegalStateException {
    try {
      op.execute(model);
    } catch (IllegalArgumentException ex) {
      this.printMsg(ex.getMessage());
    }
  }

  //prints the designated message to the view
  private void printMsg(String msg) {
    try {
      this.view.renderMessage(msg);
    } catch (IOException e) {
      throw new IllegalStateException("Output to the view failed.");
    }
  }
}
