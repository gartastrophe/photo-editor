package controller.operations;

import controller.utils.IOUtil;
import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that opens
 * a specified project to the model upon call.
 */
public class OpLoadProject implements ControllerOperations {
  private final String path;


  /**
   * Represents a controller that takes in a path
   * to pass to the CollageModel.  This does NOT
   * check for "valid" params beyond what java innately
   * does (ensuring that path is in fact a String
   * or a null).  This is because the model itself should
   * handle these issues and the controller should NOT
   * know what the model does.
   * @param path represents the input-provided
   *             path to the project file
   */
  public OpLoadProject(String path) {
    this.path = path;
  }

  @Override
  public void execute(CollageModel model) {
    model.loadProject(IOUtil.readFile(path));
  }

}
