package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import controller.features.Features;
import controller.utils.PixelGridToBufferedImage;

/**
 * Represents the GUI for the Collage Model
 * this is able to render images in real time.
 */
public class CollageGUIFrame extends JFrame implements ActionListener,
        ImageCollageView {
  private final JLabel imageDisplay;
  private final JToolBar toolBar;
  private final JButton newProject;
  private final JButton loadProject;
  private final JButton saveImage;
  private final JButton saveProject;
  private final JButton addLayer;
  private final JButton addImage;
  private JComboBox<String> setFilter;
  private JToolBar layerListDisplay;
  private Features features;
  JRadioButton radioButtons;
  ButtonGroup buttonGroup;
  private final JFrame mainPanel;


  /**
   * This is the constructor fo this CollageGUIFrame.
   * It initialises the toolbar, display, & buttons.
   */
  public CollageGUIFrame() {
    //create a new frame
    this.mainPanel = new JFrame("Collage Program");
    mainPanel.setSize(new Dimension(500, 500));
    mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //increase the default size to like 500 500 or something
    mainPanel.setPreferredSize(new Dimension(500, 500));

    //Creates the toolbar holding all the functionality for the program
    this.toolBar = new JToolBar();
    this.toolBar.setSize(new Dimension(500, 100));
    this.toolBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    mainPanel.add(toolBar, BorderLayout.PAGE_START);

    //Creates a new project in the program to call the rest of
    //the toolbar's functionality on.
    this.newProject = new JButton("New Project");
    this.newProject.setSize(new Dimension(150, 100));
    this.newProject.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(newProject);

    //Creates a new project in the program to call the rest of
    //the toolbar's functionality on.
    this.loadProject = new JButton("Load Project");
    this.loadProject.setSize(new Dimension(150, 100));
    this.loadProject.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(loadProject);

    //Saves the current project image to a chosen file destination
    this.saveImage = new JButton("Save Image");
    this.saveImage.setSize(new Dimension(150, 100));
    this.saveImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(saveImage);

    //Saves the current project to a chosen file destination
    this.saveProject = new JButton("Save Project");
    this.saveProject.setSize(new Dimension(150, 100));
    this.saveProject.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(saveProject);

    //Adds a layer to the current project which allows for the
    //application of images and filters
    this.addLayer = new JButton("Add Layer");
    this.addLayer.setSize(new Dimension(150, 100));
    this.addLayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    this.toolBar.add(addLayer);

    //Adds an image to the selected layer
    this.addImage = new JButton("Add Image");
    this.addImage.setSize(new Dimension(150, 100));
    this.addImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(addImage);

    //Add the image display area with a scrollable
    this.imageDisplay = new JLabel();
    this.imageDisplay.setSize(new Dimension(400, 400));
    this.imageDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.imageDisplay.setVerticalAlignment(SwingConstants.TOP);
    JScrollPane imageScrollPane = new JScrollPane(this.imageDisplay);
    imageScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    mainPanel.add(imageScrollPane, BorderLayout.CENTER);

    //Add the text display area for the list of layers
    this.layerListDisplay = new JToolBar(SwingConstants.VERTICAL);
    this.layerListDisplay.setSize(new Dimension(200, 400));
    this.layerListDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    mainPanel.add(layerListDisplay, BorderLayout.EAST);
    JLabel title = new JLabel("Layer list:    ");
    this.layerListDisplay.add(title);

    mainPanel.pack();
    mainPanel.setVisible(true);
  }

  /**
   * This method helps the GUI initialise the filters into this GUI.
   * It makes sense to initialise them here as the view should not
   * be predisposed to a certain set of filters; the controller should
   * be directing the view on what to make the filters and how.
   * @param filtersString represent the filters passed along from the
   *                      feature controller.
   */
  public void initFilters(String filtersString) {
    try {
      //ensure filtersString is not null
      Objects.requireNonNull(filtersString);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("filterList cannot be null.");
    }

    //remove the '[]' characters from the front and end
    //of the given filtersString
    String temp = filtersString.substring(1,filtersString.length() - 1);
    String[] filters = temp.split(", ");

    this.setFilter = new JComboBox<>(filters);
    this.setFilter.setSize(new Dimension(150, 100));
    this.setFilter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.toolBar.add(setFilter);

    //adds the button now that the filters has been initialised
    this.setFilter.addActionListener(this);
    this.setFilter.setActionCommand("set filter");
  }

  @Override
  public void refreshBetter(String ppm) {
    Image image = this.renderImage(ppm);
    ImageIcon icon = new ImageIcon(image);
    imageDisplay.setIcon(icon);
  }

  /**
   * This method adds features and the corresponding
   * buttons to the GUI.  Specifically, it adds the
   * following buttons:
   * - newProject
   * - saveImage
   * - addLayer
   * - addImage
   * This method also sets the visibility of these
   * buttons such that the client can see them.
   * @param f represents the features controller
   */
  @Override
  public void addFeatures(Features f) {
    this.features = f;
    this.newProject.addActionListener(this);
    this.newProject.setActionCommand("new project");
    this.loadProject.addActionListener(this);
    this.loadProject.setActionCommand("load project");
    this.saveImage.addActionListener(this);
    this.saveImage.setActionCommand("save image");
    this.saveProject.addActionListener(this);
    this.saveProject.setActionCommand("save project");
    this.addLayer.addActionListener(this);
    this.addLayer.setActionCommand("add layer");
    this.addImage.addActionListener(this);
    this.addImage.setActionCommand("add image");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int retValue;
    JFileChooser fChooser;
    String layerName;


    switch (e.getActionCommand()) {

      //creates a new project of specified dimensions
      //adds one layer to layer list: "background" and selects it
      case "new project":
        int height = Integer.parseInt(JOptionPane.showInputDialog("Height in pixels: "));
        int width = Integer.parseInt(JOptionPane.showInputDialog("Width in pixels: "));
        this.features.newProject(height, width);
        JRadioButton background = new JRadioButton(this.features.retrieveLayers().get(0));

        background.setActionCommand(this.features.retrieveLayers().get(0));
        this.buttonGroup = new ButtonGroup();
        this.buttonGroup.add(background);
        this.buttonGroup.setSelected(background.getModel(), true);

        this.layerListDisplay.add(background);
        break;

      //saves the current project as one image file as determined
      //by the type provided by the user
      case "save image":
        fChooser = new JFileChooser(".");
        retValue = fChooser.showSaveDialog(this);
        if (retValue == 0) {
          File f = fChooser.getSelectedFile();
          this.imageDisplay.setText(f.getAbsolutePath());
          this.features.saveImage(f.getAbsolutePath());
        }
        break;

      //saves the current project as a .txt file
      case "save project":
        fChooser = new JFileChooser(".");
        retValue = fChooser.showSaveDialog(this);
        if (retValue == 0) {
          File f = fChooser.getSelectedFile();
          this.imageDisplay.setText(f.getAbsolutePath());
          this.features.saveProject(f.getAbsolutePath());
        }
        break;

      //adds a layer on the model side and adds text representation
      //to the list of layers on the right side.
      //adding a new layer causes background to be selected by default
      case "add layer":
        //prompts for new layer name
        layerName = JOptionPane.showInputDialog("Layer name: ");
        this.features.addLayer(layerName); //throws relevant errors

        //replace current list of layers and add new blank
        mainPanel.remove(this.layerListDisplay);
        this.layerListDisplay = new JToolBar(SwingConstants.VERTICAL);
        this.layerListDisplay.setSize(new Dimension(200, 400));
        this.layerListDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(layerListDisplay, BorderLayout.EAST);

        JLabel title = new JLabel("Layer List:    ");
        this.layerListDisplay.add(title);

        this.buttonGroup = new ButtonGroup();

        background = new JRadioButton(this.features.retrieveLayers().get(0));
        background.setActionCommand(this.features.retrieveLayers().get(0));
        background.setSize(200, 100);
        this.buttonGroup.add(background);
        this.layerListDisplay.add(background);

        this.radioButtons = new JRadioButton();
        for (int i = 1; i < this.features.retrieveLayers().size(); i++) {
          this.radioButtons = new JRadioButton(this.features.retrieveLayers().get(i));
          this.radioButtons.setActionCommand(this.features.retrieveLayers().get(i));
          this.radioButtons.setSize(200, 100);
          this.buttonGroup.add(radioButtons);
          this.layerListDisplay.add(radioButtons);
        }
        this.buttonGroup.setSelected(background.getModel(), true);
        break;


      //adds an image to the selected layer, user specifying the x and y
      //coordinates at which to place the image
      case "add image":
        fChooser = new JFileChooser(".");
        retValue = fChooser.showOpenDialog(this);
        if (retValue == 0) {
          File f = fChooser.getSelectedFile();

          String x = JOptionPane.showInputDialog("x coordinate of image: ");
          String y = JOptionPane.showInputDialog("y coordinate of image: ");
          //the model should handle all other exceptions
          //xVal and yVal must be integers at this point
          this.features.addImageToLayer(this.buttonGroup.getSelection().getActionCommand()
                  , f.getAbsolutePath(), x, y);
        }
        break;

      //loads a previous project file saved by the user
      case "load project":
        fChooser = new JFileChooser(".");
        retValue = fChooser.showOpenDialog(this);
        if (retValue == 0) {
          File f = fChooser.getSelectedFile();
          this.features.loadProject(f.getAbsolutePath());
        }
        break;

      //applies a selected filter to the selected layer
      //defaults to background upon project creation and adding new layers
      case "set filter":
        this.features.setFilter(this.buttonGroup.getSelection().getActionCommand(),
                String.valueOf(setFilter.getSelectedItem()));
        break;
      default:
        throw new IllegalArgumentException("No button functionality yet!");
    }
  }


  @Override
  public void renderMessage(String message) {
    //this renders the message in a new window (JFrame)
    JFrame frame = new JFrame("Error Message");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.add(new JLabel(message)); //displays the error message

    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setSize(500, 100);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  //renders the given String as a BufferedImage.
  private Image renderImage(String grid) {
    return new PixelGridToBufferedImage(grid);
  }
}

