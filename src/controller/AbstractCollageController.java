package controller;

import java.util.Objects;
import java.util.Scanner;

import model.model.CollageModel;
import view.CollageView;

/**
 * Represents an abstract class of a collage controller that abstracts
 * out the constructor.  To maximise maintainability of future
 * controllers, the all controllers should follow a model-view-input
 * design model.  All controllers should also prevent input of null
 * values.  Therefore, AbstractCollageController was made to abstract
 * out common code from the construction process.  This abstract class
 * can also hold the common fields of model, view, and scanner.
 */
public abstract class AbstractCollageController implements CollageController {
  protected final CollageModel model;
  protected final CollageView view;
  protected final Scanner sc;

  /**
   * Represents a constructor for AbstractCollageController.  This
   * controller abstracts out common code for initialising the model,
   * view, and input.  It also abstracts out common code for throwing
   * IllegalArgumentExceptions.
   *
   * @param model represents the model in the MVC design pattern
   * @param view represents the view in the MVC design pattern
   * @param input represents the input given to the controller
   * @throws IllegalArgumentException when any values are null
   */
  protected AbstractCollageController(CollageModel model,
                                      CollageView view,
                                      Readable input)
          throws IllegalArgumentException {
    try {
      this.model = Objects.requireNonNull(model);
      this.view = Objects.requireNonNull(view);
      // the input does not necessarily need to be a field
      // in the CollageControllerImpl; the scanner is a more
      // useful field in this case.
      this.sc = new Scanner(Objects.requireNonNull(input));
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Model, view, and inputs cannot be null.");
    }
  }

  @Override
  public abstract void runCollage();
}
