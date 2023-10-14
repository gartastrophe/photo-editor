package controller.operations;

import controller.utils.IOUtilInterface;
import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that adds an image
 * to a specified layer in the model upon call.
 */
public class OpAddImageToLayer implements ControllerOperations {
  private final String layerName;
  private final String filePath;
  private final int x;
  private final int y;
  private final IOUtilInterface util;

  /**
   * Represents a constructor for this command.
   *
   * @param layerName represents the name of the layer
   * @param filePath represents the filepath
   * @param x represents the x coordinate
   * @param y represents the y coordinate
   */
  public OpAddImageToLayer(String layerName, String filePath, int x, int y,
                           IOUtilInterface util) {
    this.layerName = layerName;
    this.filePath = filePath;
    this.x = x;
    this.y = y;
    this.util = util;
  }

  @Override
  public void execute(CollageModel model) {
    //delegates command to the model
    model.addImageToLayer(layerName,util.readFile(filePath),x,y);
  }

}
