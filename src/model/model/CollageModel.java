package model.model;

import java.util.List;

import model.layer.Layer;

/**
 * Represents a model for the Collage program that enables
 * users to create new projects, load old projects, add layers,
 * add images, add filters, save projects, save images, and
 * quit the project.
 */
public interface CollageModel<C> extends CollageModelState {
  /**
   * Creates a new project with the given name and given dimensions.
   * Every project has a white background layer by default.
   * @param height represents the canvas height
   * @param width represents the canvas width
   * @throws IllegalStateException when a project is already open
   * @throws IllegalArgumentException when height or width are invalid
   *     (ie: negative or zero height/width)
   */
  void newProject(int height, int width) throws IllegalStateException,IllegalArgumentException;

  /**
   * Loads a project into the program.
   * @param path represents the path to the project
   * @throws IllegalStateException when a project is already open
   * @throws IllegalArgumentException when path not found or file is invalid
   */
  void loadProject(String path) throws IllegalStateException,IllegalArgumentException;

  /**
   * Saves the current project as is.
   * @throws IllegalStateException when project not open yet
   */
  String saveProject() throws IllegalStateException;

  /**
   * Adds a new layer with the given name to the top of the
   * whole project. This layer always has a fully transparent
   * white image and the Normal filter on by default.  Any
   * attempt at creating another layer with the same name
   * reports an error to the user, but continues the program.
   *
   * @param layerName represents the name of the layer to add
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException when trying to create
   *     a new layer of the same name
   */
  void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException;

  /**
   * Places an image on the layer such that the top left corner of the
   * image is at (x-pos, y-pos).  The given x and y coordinates are
   * NOT invalid when negative.
   *
   * @param layerName represents the layerName for which the image
   *     will be added
   * @param imageName represents the image to add to the given layer
   * @param x represents the x coordinate with zero at the top-left.
   *      As x increases it moves right.
   * @param y represents the y coordinate with zero at the top-left.
   *      As y increases it moves down.
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException when layerName is invalid or the
   *     imageName is invalid.
   */
  void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalStateException, IllegalArgumentException;


  /**
   * Places an image on the layer such that the top left corner of the
   * image is at (x-pos, y-pos).  The given x and y coordinates are
   * NOT invalid when negative.
   *
   * @param layerName represents the layerName for which the image
   *     will be added
   * @param grid represents the image to add to the given layer
   * @param x represents the x coordinate with zero at the top-left.
   *      As x increases it moves right.
   * @param y represents the y coordinate with zero at the top-left.
   *      As y increases it moves down.
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException when layerName is invalid or the
   *     imageName is invalid.
   */
  void addImageToLayer(String layerName, C[][] grid, int x, int y)
          throws IllegalStateException, IllegalArgumentException;

  /**
   * Sets the filter of the given layer.
   * @param layerName represents the name of the layer to be filtered
   * @param filterName represents the type of filter to be applied on the layer
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException layerName is invalid or filterName is
   *     not in the implementation specific list of accepted filters.
   */
  void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException;

  /** Sets the filter of the given layer.
   * @param layerName represents the name of the layer to be filtered
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException layerName is invalid or filterName is
   *     not in the implementation specific list of accepted filters.
   */
  String getFilter(String layerName) throws IllegalStateException,IllegalArgumentException;

  /**
   * Prints out an implementation specific list of accepted filters.
   * @return the string counterpart of a list of accepted filters
   */
  String printFilters();

  /**
   * Saves the current image at the given fileName.  Overwrites the
   * old file if the given fileName already exists.
   * @throws IllegalStateException when project not open yet
   */
  String saveImage() throws IllegalStateException;

  /**
   * Quits the project and loses all unsaved work.
   * @throws IllegalStateException if no project is open
   */
  void quit() throws IllegalStateException;

  /**
   * Produces a copy of the Layer with the given name.
   * @return a copy of the Layer with the given name
   * @throws IllegalStateException when project not open yet
   * @throws IllegalArgumentException when layerName is invalid
   */
  Layer<C> getLayer(String layerName) throws IllegalStateException,IllegalArgumentException;

  /**
   * Returns the order in which the Layers in this project were created.
   * @throws IllegalStateException when project not open yet
   */
  List<String> getOrder();
}
