package controller.features;

import java.util.List;

/**
 * This interface allows for the view to communicate to the model
 * by way of controller operations. The methods within this interface
 * consist mostly of functionality implemented within the GUI as
 * well as some helper methods to provide important information
 * such as the current layers in the project.
 */
public interface Features {

  /**
   * Tells the model to create a new project using the given
   * height and width.
   */
  void newProject(int height, int width);

  /**
   * Tells the model to save an image using the given string
   * as a file path.
   */
  void saveImage(String path);

  /**
   * Tells the model to save an image using the given string
   * as a file path.
   */
  void saveProject(String path);

  /**
   * Loads a project from a path by telling the model &
   * view what to do.
   */
  void loadProject(String path);

  /**
   * Tells the model to add a layer to the project using the
   * given string as a name.
   */
  void addLayer(String name);

  /**
   * Tells the model to add an image to the project, adding to
   * the layer with the given string as a name, adding the image
   * with the given string as a file path, and adding to the given
   * x and y coordinates in pixels.
   */
  void addImageToLayer(String layerName, String filePath, String x, String y);

  /**
   * Tells the model to apply a filter to the layer with the given
   * string as a name, and applies the filter with the given string
   * as a name.
   */
  void setFilter(String layerName, String filterName);

  /**
   * Tells the model to provide its current list of layers.
   * @return a list of strings representing the names of the
   *         added layers.
   */
  List<String> retrieveLayers();

}
