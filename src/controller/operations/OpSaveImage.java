package controller.operations;

import controller.utils.IOUtilInterface;
import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that saves
 * the project as a compressed image in the model upon call.
 * It calls the saveImage method in model when calling execute.
 */
public class OpSaveImage implements ControllerOperations {
  private final String fileName;
  private final IOUtilInterface util;


  /**
   * Represents a controller that takes in a fileName
   * to pass to the CollageModel.  This does NOT
   * check for "valid" params beyond what java innately
   * does (ensuring that fileName is in fact a String
   * or a null).  This is because the model itself should
   * handle these issues and the controller should NOT
   * know what the model does.
   * @param fileName represents the input-provided
   *                 fileName
   */
  public OpSaveImage(String fileName,IOUtilInterface util) {
    this.fileName = fileName;
    this.util = util;
  }

  @Override
  public void execute(CollageModel model) {
    util.writeFile(fileName,model.saveImage());
  }
}
