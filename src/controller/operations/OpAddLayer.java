package controller.operations;

import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that adds a layer
 * of a specified name to the model upon call.
 */
public class OpAddLayer implements ControllerOperations {
  private final String layerName;

  /**
   * Represents a controller that takes in a layerName
   * to pass to the CollageModel.  This does NOT
   * check for "valid" params beyond what java innately
   * does (ensuring that layerName is in fact a String
   * or a null).  This is because the model itself should
   * handle these issues and the controller should NOT
   * know what the model does.
   * @param layerName represents the input-provided
   *                  layerName
   */
  public OpAddLayer(String layerName) {
    this.layerName = layerName;
  }

  @Override
  public void execute(CollageModel model) {
    model.addLayer(layerName);
  }

}
