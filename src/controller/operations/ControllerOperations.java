package controller.operations;

import model.model.CollageModel;

/**
 * Represents an operation for a CollageController
 * following the Command Design Pattern.
 */
public interface ControllerOperations {

  /**
   * This method will execute the function on the given model.
   * Execute will NOT catch exceptions but instead pass them
   * to the controller that implements this function's
   * methods
   */
  void execute(CollageModel model);

}
