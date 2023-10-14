package model.layer;

import java.util.Objects;

import model.filter.option.FilterOption;

/**
 * Represents an abstract non-Color specific implementation of Layer.
 * @param <C> is the colour representation of this Layer
 */
public abstract class AbstractLayer<C> implements Layer<C> {
  protected final int height;
  protected final int width;
  protected C[][] grid;
  protected FilterOption<C> filter;
  protected final C transparent;

  /**
   * This constructor will actually test the height and width
   * constraints, making sure the height and width are never
   * less than or equal to zero (a zero by five grid does not
   * make sense).  Unorthodox grid shapes such as 1000-by-1
   * will not be excluded.
   *
   * <p>However, this constructor will NOT throw exceptions
   * when the given grid of filter are null.  It will treat
   * null value scenarios as if it was provided the "default"
   * conditions.  Specifically, the constructor will convert
   * a null grid input into the 'base' state of the Layer--
   * a completely transparent white rectangle of height-by-width.
   * Likewise, a null filter input will default to a NormalFilter.</p>
   *
   * @param height represents the height of this Layer
   * @param width represents the width of this Layer
   * @param grid represents the base grid of Colors on this Layer
   * @param filter represents the filter applied on this Layer
   * @throws IllegalArgumentException when the height or width of
   *     the Layer is negative.
   */
  public AbstractLayer(int height,int width,
                       C[][] grid,FilterOption<C> filter,
                       C transparent)
          throws IllegalArgumentException {
    //makes sure the height and width are valid
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException(
              "Height or width cannot be less than or equal to zero.");
    }
    //initialises the height and width only if they are valid
    this.height = height;
    this.width = width;

    //tests for the given transparency value
    try {
      //this try catch is used to prevent child classes
      //from passing in a null transparency value
      this.transparent = Objects.requireNonNull(transparent);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("transparency value cannot be null.");
    }

    //attempts to initialise the grid
    try {
      this.grid = Objects.requireNonNull(grid);
    } catch (NullPointerException e) {
      this.grid = this.initGrid(this.typeArray(height,width));
    }

    //however, if the grid CONTAINS null values...
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        //it should fail:
        try {
          Objects.requireNonNull(this.grid[i][j]);
        } catch (NullPointerException e) {
          throw new IllegalArgumentException("Given grid cannot contain null values.");
        } catch (IndexOutOfBoundsException e) {
          //if the sizes do not match up, it should also
          //throw an illegal argument exception
          throw new IllegalArgumentException("Given grid and sizes do not match.");
        }
      }
    }

    //attempt to initialise the given filter
    try {
      this.filter = Objects.requireNonNull(filter);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Filter cannot be null.");
    }
  }



  /**
   * Initialises the grid such that the layer always has a
   * fully transparent white image.
   */
  private C[][] initGrid(C[][] given) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        given[i][j] = transparent;
      }
    }
    return given;
  }

  /**
   * Creates an array of type C with a given height and width.
   * @param height represents the height of the array
   * @param width represents the width of the array
   * @return returns the given array type.
   * @throws IllegalArgumentException when height or width
   *     are invalid
   */
  protected abstract C[][] typeArray(int height,int width);

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }


  @Override
  public C[][] getGrid() {

    return this.mergeDownPrivate(
            //a completely transparent grid overlaid over this grid
            //produces this grid
            this.initGrid(this.typeArray(this.height,this.width)),
            this.grid,false);
    //return this.deepCopyGrid(this.grid,false);
  }

  @Override
  public FilterOption<C> getFilter() {
    //This method will be especially useful for differentiating
    //BrightenValue, BrightenIntensity, and BrightenLuma.
    return this.filter;
  }

  @Override
  public String getFilterName() {
    return this.filter.toString();
  }


  @Override
  public void setFilter(FilterOption<C> filter) {
    try {
      this.filter = Objects.requireNonNull(filter);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("filter cannot be null");
    }
  }

  @Override
  public C[][] mergeDown(C[][] top, C[][] bgGrid,
                         boolean applyFilter) {
    return this.mergeDownPrivate(top,bgGrid,applyFilter);
  }

  @Override
  public C[][] mergeDown(C[][] bg) {
    return this.mergeDownPrivate(this.grid,bg,true);
  }

  //represents a private method of mergeDown that can also
  //apply this filter to the top or the bottom or both
  //depending on parameters
  private C[][] mergeDownPrivate(C[][] top,C[][] bgGrid,
                                     boolean applyFilter) {

    //if either the top of bg are null;
    //create a new fully transparent grid
    //with this layer's height and width
    C[][] modTop;
    try {
      modTop = Objects.requireNonNull(top);
    } catch (NullPointerException e) {
      //initialises modTop
      modTop = this.typeArray(height,width);
    }

    C[][] modBottom;
    try {
      modBottom = Objects.requireNonNull(bgGrid);
    } catch (NullPointerException e) {
      //initialises modBottom
      modBottom = this.typeArray(height,width);
    }

    //tests for null values in top
    for (C[] colors : modTop) {
      for (int j = 0; j < modTop[0].length; j++) {
        try {
          Objects.requireNonNull(colors[j]);
        } catch (NullPointerException e) {
          //replaces null values with
          //transparent values
          colors[j] = transparent;
        }
      }
    }
    //tests for null values in bgGrid
    for (C[] colors : modBottom) {
      for (int j = 0; j < modBottom[0].length; j++) {
        try {
          Objects.requireNonNull(colors[j]);
        } catch (NullPointerException e) {
          //replaces null values with
          //transparent values
          colors[j] = transparent;
        }
      }
    }

    //creates a temporary grid representing
    //this grid overlaid over the bgGrid
    //that takes the dimensions of the background grid
    C[][] temp = this.typeArray(modBottom.length,modBottom[0].length);

    //applies the computations for overlaying colours
    for (int i = 0; i < modBottom.length; i++) {
      for (int j = 0; j < modBottom[0].length; j++) {
        try {
          if (!applyFilter) {
            //adds the top with transparency over the bottom using
            //the formulas in ColourUtil.  Functionally, this acts
            //as the now-deleted method deepCopyGrid.

            //deepCopyGrid is a private method for making deep copies of grid.
            //There are two "modes" to this method, one where it applies this
            //layer's filter, and one where it does not.  These two modes are
            //differentiated through the boolean applyFilter.
            //This method exists to abstract out code from other methods.
            temp[i][j] = computeOverlay(modTop[i][j],modBottom[i][j]);
          } else {
            //temp[i][j] = ColourUtil.computeColour(modTop[i][j],modBottom[i][j]);
            temp[i][j] = filter.applyToColor(
                    (modTop[i][j]),
                    (modBottom[i][j]));
          }

        } catch (IndexOutOfBoundsException e) {
          //if the image does not extend that far, replace it
          //with transparent white
          temp[i][j] = filter.applyToColor(transparent,modBottom[i][j]);
        }
      }
    }
    return temp;
  }

  /**
   * Computes the overlay of the top colour representation
   * over the bottom colour representation.
   * @param top represents the top colour
   * @param bottom represents the bottom colour
   * @return the merged colour
   */
  protected abstract C computeOverlay(C top, C bottom);

  @Override
  public void addImage(C[][] image, int x, int y) {
    //placing the image within the XY coordinates
    //of this Layer:
    C[][] imageXY = this.typeArray(height,width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        try {
          //offsets the image
          imageXY[i][j] = image[i - y][j - x];
        } catch (IndexOutOfBoundsException e) {
          //replaces colours off the grid with transparent white
          imageXY[i][j] = transparent;
        }
      }
    }

    //mutates the grid such that it now contains the
    //image with the top left corner of the image at
    //coordinates (x,y)
    this.grid = this.mergeDownPrivate(imageXY,this.grid,false);
  }

  /**
   * Gets the project string representation of C.
   * @return the project String
   */
  protected abstract String getProjStringType(C colour);

  /**
   * The toProjString method in this case represents the layer's
   * project format as a String.
   */
  @Override
  public String toProjString() {
    //creates a temporary StringBuilder to build
    //the String representation
    StringBuilder temp = new StringBuilder();

    //appends the filterName to the StringBuilder
    temp.append(filter.toString()).append("\n");

    //appends each coordinates' String representation
    //from left to right, top to bottom
    for (int i = 0; i < this.grid.length; i++) {
      for (int j = 0; j < this.grid[0].length; j++) {
        temp.append(getProjStringType(this.grid[i][j]));
      }
    }

    //returns the final
    return temp.toString();
  }
}
