package view;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents a text-based representation fo a CollageView.
 * This view only displays text-based status of the program
 * and does NOT show what an image looks like.  Therefore,
 * the View has no business knowing what the model is doing
 * and does not take in a CollageModel.
 *
 * <p>This also makes sure that the view will always be on
 * the same page as the Controller.  Without its own model,
 * as a parameter, it is now impossible to give the controller
 * and the view different models.</p>
 */
public class CollageTextView implements CollageView {
  private final Appendable appendable;

  /**
   * Convenience Constructor for CollageTextView with default
   * appendable as System.out.
   */
  public CollageTextView() {
    this(System.out);
  }

  /**
   * Represents a text-based implementation of a CollageView.
   * @param appendable represents the output
   * @throws IllegalArgumentException when the given appendable
   *     is null
   */
  public CollageTextView(Appendable appendable)
          throws IllegalArgumentException {
    try {
      this.appendable = Objects.requireNonNull(appendable);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    appendable.append(message);
  }
}
