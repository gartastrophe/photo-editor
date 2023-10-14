package model.model;

import java.util.List;

import model.layer.Layer;

/**
 * Represents a CollageModel that will throw an IllegalArgumentException
 * at every applicable method.
 */
public class CollageModelThrowIllegalArg implements CollageModel {

  @Override
  public void newProject(int height, int width) throws IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public void loadProject(String path) throws IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public String saveProject() throws IllegalStateException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public void addLayer(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");

  }

  @Override
  public void addImageToLayer(String layerName, Object[][] grid, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public String getFilter(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public String printFilters() {
    //this method should never throw an IllegalArgumentException
    return null;
  }

  @Override
  public String saveImage() throws IllegalStateException {
    throw new IllegalArgumentException("IllegalArgumentException");

  }

  @Override
  public void quit() {
    //this method should never throw an IllegalArgumentException
  }

  @Override
  public Layer getLayer(String layerName) {
    throw new IllegalArgumentException("IllegalArgumentException");
  }

  @Override
  public List<String> getOrder() {
    //this method should never throw an IllegalArgumentException
    return null;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    //this method should never throw an IllegalArgumentException
    return 0;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    //this method should never throw an IllegalArgumentException
    return 0;
  }

  @Override
  public int getMax() {
    //this method should never throw an IllegalArgumentException
    return 0;
  }
}