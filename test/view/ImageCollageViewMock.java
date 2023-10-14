package view;

import java.io.IOException;

import controller.features.Features;

/**
 * This mock is a stub.  It is used in FeaturesControllerTest
 * to avoid using the actual CollageGUIFrame.
 */
public class ImageCollageViewMock implements ImageCollageView {

  @Override
  public void renderMessage(String message) throws IOException {
    //ignore
  }

  @Override
  public void addFeatures(Features f) {
    //ignore
  }

  @Override
  public void refreshBetter(String ppm) {
    //ignore
  }

  @Override
  public void initFilters(String str) {
    //ignore
  }
}
