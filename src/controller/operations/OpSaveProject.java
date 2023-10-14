package controller.operations;

import controller.utils.IOUtil;
import model.model.CollageModel;

/**
 * Represents a type of ControllerOperation that saves
 * the project in the model upon call.
 */
public class OpSaveProject implements ControllerOperations {
  private final String fileName;

  public OpSaveProject(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void execute(CollageModel model) {
    IOUtil.writeFile(fileName,model.saveProject());
  }
}
