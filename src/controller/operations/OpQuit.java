package controller.operations;

import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that quits
 * the project in the model upon call; this also deletes
 * all unsaved work.
 */
public class OpQuit implements ControllerOperations {

  @Override
  public void execute(CollageModel model) {
    model.quit();
  }
}
