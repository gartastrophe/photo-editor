package model.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import model.filter.option.FilterOption;
import model.layer.Layer;
import model.layer.LayerPixelImpl;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import model.utils.PPMUtil;

/**
 * Represents an abstract class for CollageModel that would hold
 * common code across implementations of CollageModel.  This
 * class will force the client to create a "newProject" or
 * load an existing project with "loadProject" before allowing
 * the client to do anything else.  This is enforced through
 * IllegalStateExceptions.  This model also does not allow the
 * client to load or open a newProject when one is already open.
 *
 * <p>This implementation also has a unique implementation of
 * quit such that the quit will exit the open project if one is
 * open and re-init all the data</p>
 */
public abstract class AbstractCollageModel implements CollageModel<Pixel> {
  //represents the layers in this project
  protected Map<String, Layer<Pixel>> layers;
  //represents the order in which the layers were added
  protected List<String> layersKeyOrder;
  protected boolean projectOpen;
  protected int height;
  protected int width;
  protected final Map<String, FilterOption<Pixel>> knownFilters;



  /**
   * Represents the constructor for an AbstractCollageModel.
   * This constructor takes in no parameters and initialises
   * the projectOpen flag and the map of layers.
   */
  protected AbstractCollageModel() {
    this.init();
    knownFilters = new HashMap<>();
    this.initFilters();
  }

  //actually initialises the data;
  //called separately from the constructor
  //to enable further use in this.quit
  private void init() {
    //initialises the projectOpen flag and the map of layers
    this.projectOpen = false;
    this.layers = new HashMap<>();
    this.layersKeyOrder = new ArrayList<>();
  }

  protected abstract void initFilters();

  @Override
  public void newProject(int height, int width)
          throws IllegalStateException,IllegalArgumentException {
    if (this.projectOpen) {
      //throws an IllegalStateException if a project is already open
      throw new IllegalStateException("newProject fail; project already open.");
    }

    //this method will add a new layer to this.layers
    //which, in the constructor, will:
    //make sure the height and width are valid
    //and throw an exception if they are not.
    this.layers.put("background",new LayerPixelImpl(height,width));

    //initialises the height and width only if they are valid
    this.height = height;
    this.width = width;
    this.projectOpen = true;
    this.layersKeyOrder.add("background");


    //sets the background colour to opaque white as per instructions
    Pixel[][] whiteGrid = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        whiteGrid[i][j] = new RGBPixel(255,255,255,255);
      }
    }

    addImageToLayerAbstraction("background", whiteGrid,0,0);
  }

  @Override
  public void loadProject(String projFile) throws IllegalStateException, IllegalArgumentException {
    if (this.projectOpen) {
      //throws an IllegalStateException if a project is already open
      throw new IllegalStateException("loadProject fail; project already open.");
    }

    try {
      //read the PPM file
      readProject(projFile);
      //only if the path is readable,
      //set the projectOpen status to true
      //this.projectOpen = true;
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("loadProject failed: projFile cannot be null.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }


  /**
   * Abstracts the cleaning process in the attempt to
   * read from a given project file and render
   * the corresponding CollageModel with
   * the same layers, filters and colours.
   */
  private String setUpRead(String projFile) {
    //ensure the path is not null
    Objects.requireNonNull(projFile);

    //create a scanner object for this projFile
    Scanner sc = new Scanner(projFile);

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string.
    //This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    return builder.toString();
  }


  /**
   * Abstracts out throwing an IllegalStateException with an informative message
   * when no project is open.
   * @param sourceMethod represents the name of the method
   *                     that threw this exception
   * @throws IllegalStateException when no project is open
   */
  protected void notOpenException(String sourceMethod) throws IllegalStateException {
    try {
      //tests for null inputs since this method doesn't
      //know what other classes will send over
      Objects.requireNonNull(sourceMethod);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Operation failed: null method name.");
    }

    if (!projectOpen) {
      throw new IllegalStateException(sourceMethod + " failed: No project opened.");
    }
  }

  /**
   * Abstracts out throwing an IllegalArgumentException with an informative message
   * when the given layerName is not found.
   * @param sourceMethod represents the name of the method
   *                     that threw this exception
   * @throws IllegalArgumentException when the layerName is not found
   */
  protected void layerNameException(String layerName, String sourceMethod)
          throws IllegalArgumentException {
    try {
      //tests for null inputs since this method doesn't
      //know what child classes will send over
      Objects.requireNonNull(sourceMethod);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Operation failed: null sourceMethod.");
    }

    //if the layer does not exist:
    if (!this.layers.containsKey(layerName)) {
      throw new IllegalArgumentException(sourceMethod + " failed: layer not found.");
    }
  }

  @Override
  public String saveProject() throws IllegalStateException {
    //throws an IllegalStateException if a project is not open
    this.notOpenException("saveProject");

    return this.toProjString();
  }

  @Override
  public void addLayer(String layerName)
          throws IllegalStateException, IllegalArgumentException {
    //throws an IllegalStateException if a project is not open
    this.notOpenException("addLayer");

    //makes sure name is OK
    try {
      Objects.requireNonNull(layerName);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Operation failed: null sourceMethod.");
    }

    if (layers.containsKey(layerName)) {
      throw new IllegalArgumentException("addLayer failed: layerName already exists.");
    } else {
      layers.put(layerName,new LayerPixelImpl(this.height,this.width));
      layersKeyOrder.add(layerName);
    }
  }

  @Override
  public void addImageToLayer(String layerName, String imageContents, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      Objects.requireNonNull(imageContents);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("imageName cannot be null.");
    }

    //design choice: both add addImageToLayer reference
    //the abstraction such that override one does not
    //necessarily override the other.
    this.addImageToLayerAbstraction(layerName, PPMUtil.fromPPMFormat(imageContents),x,y);
  }

  @Override
  public void addImageToLayer(String layerName, Pixel[][] grid, int x, int y)
          throws IllegalStateException, IllegalArgumentException {
    try {
      for (Pixel[] colors : grid) {
        for (int j = 0; j < grid[0].length; j++) {
          Objects.requireNonNull(colors[j]);
        }
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Grid cannot be null.");
    }

    //design choice: both add addImageToLayer reference
    //the abstraction such that override one does not
    //necessarily override the other.
    this.addImageToLayerAbstraction(layerName,grid,x,y);
  }

  //performs the functionality of both addImageToLayer methods.
  protected void addImageToLayerAbstraction(String layerName, Pixel[][] grid, int x, int y) {
    //throws an IllegalStateException if no project is open
    this.notOpenException("addImageToLayer");

    //throws an IllegalArgumentException if layerName doesn't exist
    this.layerNameException(layerName,"addImageToLayer");

    //delegates to the given layer
    layers.get(layerName).addImage(grid,x,y);
  }

  @Override
  public String printFilters() {
    //should NOT throw an IllegalStateException if a project
    //is not open; should be able to access info about this
    //model as long as it exists.

    //print out list of known filters
    return this.knownFilters.keySet().toString();
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    //throws an IllegalStateException if no project is open
    this.notOpenException("setFilter");

    //throws an IllegalArgumentException if layerName doesn't exist
    this.layerNameException(layerName,"setFilter");

    //setFilter in the super class AbstractCollageModel is already built to handle
    //null inputs for Filter (because of design principles).  Therefore, this method
    //can pass the null value to the AbstractCollageModel when the filterName is not
    //found in the map of knownFilters.
    this.setFilter(layerName,knownFilters.getOrDefault(filterName,null));
  }

  //represents an abstraction to actually set the filter
  protected void setFilter(String layerName, FilterOption<Pixel> option)
          throws IllegalArgumentException, IllegalStateException {
    //throws an IllegalStateException if no project is open
    this.notOpenException("setFilter");

    //throws an IllegalArgumentException if layerName doesn't exist
    this.layerNameException(layerName,"setFilter");

    try {
      this.layers.get(layerName).setFilter(option);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("setFilter failed: FilterOption not found.");
    }
  }

  @Override
  public String getFilter(String layerName)
          throws IllegalStateException,IllegalArgumentException {
    //throws an IllegalStateException if no project is open
    notOpenException("getFilter");
    layerNameException(layerName,"getFilter");

    return this.layers.get(layerName).getFilterName();
  }

  @Override
  public String saveImage() throws IllegalStateException {
    //throws an IllegalStateException if no project is open
    this.notOpenException("saveImage");

    //compresses the layers
    Pixel[][] grid = this.compressLayers();

    return PPMUtil.toPPMFormat(grid);
  }

  //method for compressing all the Layers
  //in this.layers down to a 2D array of colour
  protected Pixel[][] compressLayers() {
    //create a temporary array of Color to add to
    Pixel[][] temp = new Pixel[height][width];

    //initialise temp
    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < temp[0].length; j++) {
        temp[i][j] = new RGBPixel(0,0,0,0);
      }
    }


    //add all the layers on top of temp in order
    for (int i = 0; i < layersKeyOrder.size(); i++) {
      temp = layers.get(layersKeyOrder.get(i)).mergeDown(temp);
    }

    return temp;
  }

  @Override
  public void quit() throws IllegalStateException {
    this.notOpenException("quit");
    this.init();
  }

  @Override
  public Layer<Pixel> getLayer(String layerName) {
    //throws an IllegalStateException if no project is open
    this.notOpenException("getLayer");

    //throws an IllegalArgumentException if layerName doesn't exist
    this.layerNameException(layerName,"getLayer");
    //the method above ensures that the given layerName exists
    //and is non-null

    //gets the target layer
    Layer<Pixel> target = this.layers.get(layerName);

    //returns a clone of the target layer
    //to protect data integrity
    return new LayerPixelImpl(
            target.getHeight(),
            target.getWidth(),
            target.getGrid(),
            target.getFilter()
    );
  }

  @Override
  public List<String> getOrder() {
    //throws an IllegalStateException if no project is open
    this.notOpenException("getOrder");

    //returns a clone of the list
    //to protect data integrity
    return new ArrayList<>(this.layersKeyOrder);
  }

  @Override
  public int getWidth() throws IllegalStateException {
    this.notOpenException("getWidth");
    return this.width;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    this.notOpenException("getHeight");
    return this.height;
  }

  @Override
  public int getMax() throws IllegalStateException {
    //throws exception if no project open
    this.notOpenException("getMax");

    //this implementation has a max value of 255
    return 255;
  }

  /**
   * Converts the given project to a ProjString format.
   * @return this project in a ProjString format.
   * @throws IllegalStateException when no file is open
   */
  private String toProjString() throws IllegalStateException {
    //this method effective serves as the writeProject
    //method in the previous iteration implementation;
    //without actually writing to a project file.

    this.notOpenException("toProjString");

    //temp contains the Project's specs
    StringBuilder temp = new StringBuilder(
            String.format("C1\n%d %d\n%d\n", width, height, getMax()));

    //performs the following for each layer
    for (String layerName : layersKeyOrder) {
      temp.append(layerName)
              .append(" ")
              .append(layers.get(layerName).toProjString())
              .append("\n");
    }
    return temp.toString();
  }


  /**
   * Attempts to read from a given project file and render
   * the corresponding CollageModel with the same layers,
   * filters and colours.  If the method encounters any
   * issues with reading the file, it throws an IllegalArgumentException
   * @param projFile represents the text-based project file
   */
  private void readProject(String projFile) {
    //this method effective serves to read from a project
    //given the String-based projectFile.  Since the model
    //is so intimately involved in the project process;
    //it did not make sense to keep that in the ImageUtil
    //class

    //cleans the project file before running
    String cleanProjFile = this.setUpRead(projFile);

    //now set up the scanner to read from the string we just built
    Scanner sc = new Scanner(cleanProjFile);

    String token;

    token = sc.next();
    if (!token.equals("C1")) {
      throw new IllegalArgumentException("Invalid Project file: project file should start with C1");
    }
    int width;
    int height;
    int maxValue;

    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();

      //creates a new Project with the given specs:
      newProject(height, width);
      //if newProject fails, this should propagate the exception;
      //and the project should NOT be open

    } catch (InputMismatchException e) {
      throw new IllegalArgumentException("Project file read fail: incorrect format.");
    }

    //does the following for each layer:
    try {
      while (sc.hasNext()) {
        //extract the relevant information from the layer:
        String layerName = sc.next();
        String filterOption = sc.next();

        Pixel[][] grid;
        grid = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            //the following will get the proportion of colour component with respect to
            //the max value and multiply that by 255 to enforce an RGB255 representation
            int r = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
            int g = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
            int b = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
            int a = Math.toIntExact(Math.round((sc.nextInt() / (maxValue + 0.0)) * 255.0));
            grid[i][j] = new RGBPixel(r, g, b, a);
          }
        }

        //add the layer if it is not "background"
        if (Objects.equals(layerName, "background")) {
          //layerName should only ignore adding another layer for the "background"
        } else {
          try {
            addLayer(layerName);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("File read error: " + e.getMessage());
          }
        }

        //sets the filter of the layer
        setFilter(layerName, filterOption);

        //add the image to the layer without offsetting
        addImageToLayer(layerName, grid, 0, 0);
      }
    } catch (InputMismatchException e) {
      //if the inputs do not line up with the Layer format,
      quit();
      throw new IllegalArgumentException("Project file read fail: incorrect layer format.");
    }
  }
}
