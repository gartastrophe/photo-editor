package view;

import java.io.IOException;

/**
 * Represents the view of a Collage project in the MVC design pattern.
 */
public interface CollageView {

  /**
   * Renders a given message to the data output in the implementation.
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;
}
