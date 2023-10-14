package model.layer;

import model.filter.option.FilterOption;

/**
 * Represents a layer to be used in a CollageModel.  The layer
 * has a colour representation; a height; a width; a filter; a
 * grid-based representation of the rendering on this layer.
 * @param <C> represents the colour representation
 *           to be used on this layer
 */
public interface Layer<C> {


  /**
   * Returns the height of this layer.
   * @return the height
   */
  int getHeight();

  /**
   * Returns the width of this layer.
   * @return the width
   */
  int getWidth();

  /**
   * Produces a COPY of the grid in this Layer.
   * @return the width of this Layer
   */
  C[][] getGrid();

  /**
   * Returns the name of the filter currently being applied to this Layer.
   * @return the name of the filter currently being applied to this Layer
   */
  String getFilterName();

  /**
   * Sets the filter of this layer to the given filter.
   * @param filter is the new filter of this image
   * @throws IllegalArgumentException when the given filter
   *     is null
   */
  void setFilter(FilterOption<C> filter);

  /**
   * Overlays this Layer on top of the given background Grid.  This method
   * allows the user to pass in a "top" grid of colour representation
   * and a "bottom" grid of colour representation and specify if they want
   * to apply the filter of this layer to the top, the bottom, or both
   * grids.
   *
   * <p>When the size of this layer and the background do not match,
   * the resultant grid will take on the dimensions of the background
   * grid.  If this Layer is larger than the background grid, then this
   * method will start in the top right corner and only overlay the area
   * included in the background grid and nothing more.  If the background
   * grid is larger than this grid, this grid will only overlay its area in
   * the top right corner.  The remaining area will be filled in with a
   * transparent colouring.<p/>
   *
   * <p>This method will also apply this Layer's filter to the image before
   * merging it to the the background grid.</p>
   *
   * @param top represents the top grid
   * @param bgGrid represents the background grid
   * @param applyFilter represents if the method should apply this
   *                    layer's filter to the given top colour rep
   * @return a grid representation of this grid
   *     overlaid over the given background grid
   * @throws IllegalArgumentException when the given background grid
   *     is null.
   */
  C[][] mergeDown(C[][] top,C[][] bgGrid,boolean applyFilter);

  /**
   * Represents a convenience method for the full mergeDown.  This
   * will apply this layer's filter to the top (this grid) and not
   * to the bottom.
   *
   * <p>This method will also apply this Layer's filter to the image before
   * merging it to the the background grid.</p>
   *
   * @param bg represents the background grid
   * @return a grid representation of this grid
   *     overlaid over the given background grid
   * @throws IllegalArgumentException when the given background grid
   *     is null.
   */
  C[][] mergeDown(C[][] bg);


  /**
   * Coordinates (x,y) represents the top left corner of the image.
   *   - As x increases, the image moves rightwards.
   *   - As y increases, the image move downwards.
   * This method with overlay the given image OVER the pre-existing
   * grid.  This leaves room for extension as it does not REPLACE
   * the colours below; if the given image is translucent, this
   * method will preserve the translucency and colour mixing.
   *
   * @param image represents the image to render on the Layer
   * @param x represents the x coordinate; as x increases
   *          the image moves to the right
   * @param y represents the y coordinate; as y increases
   *          the image moves downwards
   */
  void addImage(C[][] image, int x, int y);

  FilterOption<C> getFilter();

  String toProjString();
}
