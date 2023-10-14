package model.filter.option;

/**
 * Represents the interface for various filter options.
 * This interface has one method, applyToColor in which
 * the classes that implement FilterOption will apply
 * the filter type to a given colour.
 *
 * <p>For example, a GreenComponent FilterOption's
 * functionality is to isolate the green component of
 * a given colour.  Thus, the applyToColor method in
 * that situation will isolate the green value from
 * the given colour and produce a new colour with
 * only the green component.</p>
 *
 * @param <C> is the colour representation;
 */
public interface FilterOption<C> {
  /**
   * Applies the filter type on the given colour AND will rasterize
   * the two colours together.
   * @param top represents the colour on top that should be filtered
   * @param bottom represents the colour on the bottom of the filter
   * @return the filtered colour
   */
  C applyToColor(C top, C bottom);


  /**
   * Override the toString method such that getting the FilterOption
   * will produce the name of the FilterOption.
   */
  @Override
  String toString();
}
