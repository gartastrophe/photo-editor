package controller.operations;

import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that creates
 * a new project in the model upon call.
 */
public class OpNewProject implements ControllerOperations {
  private final int height;
  private final int width;

  /**
   * Represents the constructor of a NewProject ControllerOperation.
   * @param height represents the height of the grid
   * @param width represents the width of the grid
   */
  public OpNewProject(int height, int width) {
    //this does NOT throw exceptions since
    //controller should NEVER know what is valid or invalid
    this.height = height;
    this.width = width;
  }

  @Override
  public void execute(CollageModel model) {
    //propagates IllegalState and IllegalArg exceptions
    model.newProject(height,width);
  }
}
