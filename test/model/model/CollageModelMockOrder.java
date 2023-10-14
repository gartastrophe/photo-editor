package model.model;

import java.io.IOException;
import java.util.List;

import model.layer.Layer;

/**
 * Represents a mock-up class of a CollageModel that returns the methods
 * called in an appendable log.  This class is used to check if the Controller
 * correctly calls the right methods at the right time.
 */
public class CollageModelMockOrder implements CollageModel {
  private final Appendable log;

  public CollageModelMockOrder(Appendable log) {
    this.log = log;
  }


  @Override
  public void newProject(int height, int width) throws IllegalArgumentException {
    try {
      this.log.append("newProject\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public void loadProject(String path) throws IllegalArgumentException {
    try {
      this.log.append("loadProject\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

  }

  @Override
  public String saveProject() throws IllegalStateException {
    try {
      this.log.append("saveProject\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return "C1\n project";
  }

  @Override
  public void addLayer(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("addLayer\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

  }

  @Override
  public void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("addImageToLayer\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

  }

  @Override
  public void addImageToLayer(String layerName, Object[][] grid, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("addImageToLayer\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    try {
      this.log.append("setFilter\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

  }

  @Override
  public String getFilter(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append("getFilter\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

    return null;
  }

  @Override
  public String printFilters() {
    try {
      this.log.append("printFilters\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

    return null;
  }

  @Override
  public String saveImage() throws IllegalStateException {
    try {
      this.log.append("saveImage\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return "P3\n image";
  }

  @Override
  public void quit() {
    try {
      this.log.append("quit\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
  }

  @Override
  public Layer getLayer(String layerName) {
    try {
      this.log.append("getLayer\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

    return null;
  }

  @Override
  public List<String> getOrder() {
    try {
      this.log.append("getOrder\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

    return null;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    try {
      this.log.append("getWidth\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }

    return 0;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    try {
      this.log.append("getHeight\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return 0;
  }

  @Override
  public int getMax() {
    try {
      this.log.append("getMax\n");
    } catch (IOException e) {
      //do nothing since it would be a higher-level problem
    }
    return 0;
  }
}