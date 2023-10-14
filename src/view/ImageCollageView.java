package view;


import controller.features.Features;

/**
 * Represents a CollageView that also enables the GUI
 * to function.
 */
public interface ImageCollageView extends CollageView {

  /**
   * Adds the functional features to the backend
   * of the GUI.
   */
  void addFeatures(Features f);

  /**
   * refreshBetter is a method that will
   * refresh the given imageIcon when called
   * based on the given ppmString.
   * @param ppm represents the given String-based
   *            ppm image to render
   */
  void refreshBetter(String ppm);

  /**
   * Initialises the given text-based filters.
   */
  void initFilters(String str);

}
