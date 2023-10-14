package model.layer;

import model.filter.option.FilterOption;
import model.filter.option.NormalFilter;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PixelUtil;

/**
 * Represents a Layer with a height, width, base image, and filter.
 * This implementation also explicitly uses Pixel.  This method
 * also allows the AbstractLayer to create arrays of the colour
 * representation, the projString representation of the colour
 * representation and an explicit example of a transparent colour
 * representation.
 */
public class LayerPixelImpl extends AbstractLayer<Pixel> {

  /**
   * Represents a convenience constructor for the most basic Layer.
   * This constructor will test if the height and width are valid,
   * set the grid of this layer to a height-by-width white-transparent
   * array of colour, and set the filter of this grid to NormalFilter.
   *
   * @param height represents the height of this Layer
   * @param width represents the width of this Layer
   * @throws IllegalArgumentException when the height or width of
   *     the Layer is negative.
   */
  public LayerPixelImpl(int height,int width) throws IllegalArgumentException {
    //Layer uses null here to delay the initialisation of
    //the grid until after verifying the height and width
    //are OK.  The full constructor will catch null values
    //and autofill the 'initial' grid state if a null grid
    //is provided.
    this(height,width,null,new NormalFilter());
  }

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
  public LayerPixelImpl(int height,int width, Pixel[][] grid,FilterOption<Pixel> filter)
          throws IllegalArgumentException {
    super(height,width,grid,filter,new RGBPixel(255,255,255,0));
  }

  @Override
  protected Pixel[][] typeArray(int height, int width) {
    return new Pixel[height][width];
  }

  @Override
  protected String getProjStringType(Pixel p) {
    return p.toProjString();
  }

  @Override
  protected Pixel computeOverlay(Pixel top,Pixel bottom) {
    return PixelUtil.computeColour(top,bottom);
  }

}
