package controller.operations;

import model.model.CollageModel;

/**
 * Represents an operation for a CollageController
 * following the Command Design Pattern.
 * This class calls one method execute which
 * takes in a model and executes the setFilter
 * method in that model.
 */
public class OpSetFilter implements ControllerOperations {
  private final String layerName;
  private final String filterName;


  public OpSetFilter(String layerName, String filterName) {
    this.layerName = layerName;
    this.filterName = filterName;
  }

  @Override
  public void execute(CollageModel model) {
    model.setFilter(layerName,filterName);
  }

}
