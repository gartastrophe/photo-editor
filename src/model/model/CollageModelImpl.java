package model.model;

import model.filter.option.NormalFilter;
import model.filter.option.brightness.BrightenIntensity;
import model.filter.option.brightness.BrightenLuma;
import model.filter.option.brightness.BrightenValue;
import model.filter.option.brightness.DarkenIntensity;
import model.filter.option.brightness.DarkenLuma;
import model.filter.option.brightness.DarkenValue;
import model.filter.option.colour.components.BlueComponent;
import model.filter.option.colour.components.GreenComponent;
import model.filter.option.colour.components.RedComponent;

/**
 * Represents an implementation of CollageModel that has a limited
 * amount of known filters.  These filters include only the following.
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
 * This implementation knows the String representation of the filters
 * described above and is able to convert from the String representation
 * to the Java class representation.  It also has one method setFilter
 * and this class' method checks if the filterName for this setFilter
 * is valid before delegating back up to its parent class for the actual
 * functionality.
 */
public class CollageModelImpl extends AbstractCollageModel {

  /**
   * Represents the constructor for a CollageModelImpl.
   * This constructor sets the projectOpen status to
   * false AND initialises the known filters.
   */
  public CollageModelImpl() {
    super();
  }

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
  }
}
