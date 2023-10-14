package model.model;

import java.util.Objects;

import model.filter.option.NormalFilter;
import model.filter.option.blend.Difference;
import model.filter.option.blend.Multiply;
import model.filter.option.blend.Screen;
import model.filter.option.brightness.BrightenIntensity;
import model.filter.option.brightness.BrightenLuma;
import model.filter.option.brightness.BrightenValue;
import model.filter.option.brightness.DarkenIntensity;
import model.filter.option.brightness.DarkenLuma;
import model.filter.option.brightness.DarkenValue;
import model.filter.option.colour.components.BlueComponent;
import model.filter.option.colour.components.GreenComponent;
import model.filter.option.colour.components.RedComponent;
import model.pixel.Pixel;

/**
 * Represents a new implementation of CollageModelImpl
 * whereby new filters are added.  These new filters
 * bring the total list of filters to the following:
 *   - normal
 *   - red-component
 *   - green-component
 *   - blue-component
 *   - brighten-value
 *   - brighten-intensity
 *   - brighten-luma
 *   - darken-value
 *   - darken-intensity
 *   - darken-luma
 *   - difference
 *   - multiply
 *   - screen
 * This model implementation is a more up-to-date version
 * than CollageModelImpl in that it also allows for
 * BlendFilters.
 *
 * <p>This version of CollageModel also supports writing
 * with transparency when calling save.</p>
 */
public class CollageModelImpl3 extends AbstractCollageModel {

  //initialises knownFilters by adding all known filters to the HashMap.
  protected void initFilters() {
    //adds a string representation of each of the
    //FilterOptions to the map of knownFilters
    knownFilters.put("normal",new NormalFilter());
    knownFilters.put("red-component",new RedComponent());
    knownFilters.put("green-component",new GreenComponent());
    knownFilters.put("blue-component",new BlueComponent());
    knownFilters.put("brighten-value",new BrightenValue());
    knownFilters.put("brighten-intensity",new BrightenIntensity());
    knownFilters.put("brighten-luma",new BrightenLuma());
    knownFilters.put("darken-value",new DarkenValue());
    knownFilters.put("darken-intensity",new DarkenIntensity());
    knownFilters.put("darken-luma",new DarkenLuma());
    knownFilters.put("difference",new Difference());
    knownFilters.put("multiply",new Multiply());
    knownFilters.put("screen",new Screen());
  }


  @Override
  public String saveImage() throws IllegalStateException {
    //throws an IllegalStateException if no project is open
    this.notOpenException("saveImage");

    //compresses the layers
    Pixel[][] grid = this.compressLayers();

    return this.toIntermediaryString(grid);
  }

  /**
   * Creates a new "intermediary string" format that preserves
   * the transparency of the underlying images.
   */
  private String toIntermediaryString(Pixel[][] grid) {
    StringBuilder temp = new StringBuilder(
            String.format("T1\n%d %d\n%d\n", width, height, getMax()));
    try {
      Objects.requireNonNull(grid);
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
          Objects.requireNonNull(grid[i][j]);
          temp.append(grid[i][j].toProjString());
        }
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Grid cannot be null");
    }

    return temp.toString();
  }

}
