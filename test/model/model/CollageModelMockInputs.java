package model.model;

import java.io.IOException;
import java.util.List;

import model.layer.Layer;


/**
 * Represents a mock-up class of a CollageModel that returns the inputs
 * that methods take in an appendable log.  This class is used to check
 * if the Controller correctly passes params to the model.
 */
public class CollageModelMockInputs implements CollageModel {
  private final Appendable log;

  public CollageModelMockInputs(Appendable log) {
    this.log = log;
  }



  @Override
  public void newProject(int height, int width) throws IllegalArgumentException {
    try {
      this.log.append("height=" + height
              + " " + "width=" + width
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public void loadProject(String path) throws IllegalArgumentException {
    try {
      this.log.append("path=" + path
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public String saveProject() throws IllegalStateException {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return "C1\n project";
  }

  @Override
  public void addLayer(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("layerName=" + layerName
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
  }


  @Override
  public void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("layerName=" + layerName
              + " " + "imageName=" + imageName
              + " " + "x=" + x
              + " " + "y=" + y
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public void addImageToLayer(String layerName, Object[][] grid, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("layerName=" + layerName
              + " " + "imageName=" + grid
              + " " + "x=" + x
              + " " + "y=" + y
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    try {
      this.log.append("layerName=" + layerName
              + " " + "filterName=" + filterName
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }

  }

  @Override
  public String getFilter(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("layerName=" + layerName + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }

    return null;
  }

  @Override
  public String printFilters() {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return null;
  }

  @Override
  public String saveImage() throws IllegalStateException {
    try {
      this.log.append(//"fileName=" + fileName+
              "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
    return "P3\n image";
  }

  @Override
  public void quit() {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public Layer getLayer(String layerName) {
    try {
      this.log.append("layerName=" + layerName
              + "\n");
    } catch (IOException ignore) {
      //do nothing since it would be a higher-level problem
    }
    return null;
  }

  @Override
  public List<String> getOrder() {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return null;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return 0;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return 0;
  }

  @Override
  public int getMax() {
    try {
      //add nothing else since there are no inputs
      this.log.append("\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return 0;
  }
}