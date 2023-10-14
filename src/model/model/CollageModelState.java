package model.model;

/**
 * Represents an interface to look at--but not mutate
 * the state of this CollageModel.  Specifically this
 * interface allows for returning the height and width
 * of the Collage.
 */
public interface CollageModelState {
  /**
   * Return the width of the project for this CollageModel.
   * @return the width of the project
   * @throws IllegalStateException if the project has not yet started
   */
  int getWidth() throws IllegalStateException;

  /**
   * Return the height of the project for this CollageModel.
   * @return the height of the project
   * @throws IllegalStateException if the project has not yet started
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns the maximum colour component value of the project for
   *     this REPRESENTATION of CollageModel.
   * @return the maximum colour component value of the project
   */
  int getMax();

}
