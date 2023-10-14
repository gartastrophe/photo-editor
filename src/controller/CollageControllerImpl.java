package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import controller.operations.ControllerOperations;
import controller.operations.OpAddImageToLayer;
import controller.operations.OpAddLayer;
import controller.operations.OpLoadProject;
import controller.operations.OpNewProject;
import controller.operations.OpQuit;
import controller.operations.OpSaveImage;
import controller.operations.OpSaveProject;
import controller.operations.OpSetFilter;
import controller.utils.IOUtil2;
import controller.utils.IOUtilInterface;
import model.model.CollageModel;
import view.CollageView;

/**
 * Represents an implementation of a CollageController that
 * uses ControllerOperations.
 */
public class CollageControllerImpl extends AbstractCollageController {
  private Map<String, Function<Scanner, ControllerOperations>> knownCommands;
  private final IOUtilInterface ioUtil;


  /**
   * Represents a constructor for this CollageControllerCommandDesignPatternImpl.
   * This controller initialises the model, view, and input.  It also abstracts
   * out common code for throwing IllegalArgumentExceptions.
   *
   * @param model represents the model in the MVC design pattern
   * @param view  represents the view in the MVC design pattern
   * @param input represents the input given to the controller
   * @throws IllegalArgumentException when any values are null
   */
  public CollageControllerImpl(CollageModel model, CollageView view, Readable input,
                               IOUtilInterface ioUtil)
          throws IllegalArgumentException {
    super(model, view, input);

    //uses the given ioUtil if non-null
    try {
      this.ioUtil = Objects.requireNonNull(ioUtil);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("IOUtil cannot be null.");
    }

    //init the commands
    this.initCommands();
  }



  /**
   * Represents a constructor for this CollageControllerCommandDesignPatternImpl.
   * This controller initialises the model, view, and input.  It also abstracts
   * out common code for throwing IllegalArgumentExceptions.
   *
   * @param model represents the model in the MVC design pattern
   * @param view  represents the view in the MVC design pattern
   * @param input represents the input given to the controller
   * @throws IllegalArgumentException when any values are null
   */
  public CollageControllerImpl(CollageModel model, CollageView view, Readable input)
          throws IllegalArgumentException {
    //IOUtil2 is the default IOUtil
    this(model,view,input,new IOUtil2());
  }

  //initialises the list of knownCommands
  //for this implementation of CollageController
  private void initCommands() {
    //initialises the known commands
    knownCommands = new HashMap<>();

    //adds to the list of known commands
    knownCommands.put("new-project",s -> new OpNewProject(s.nextInt(),s.nextInt()));
    knownCommands.put("load-project",(s -> new OpLoadProject(s.next())));
    knownCommands.put("save-project",s -> new OpSaveProject(s.next()));
    knownCommands.put("add-layer",s -> new OpAddLayer(s.next()));
    knownCommands.put("add-image-to-layer",s -> new OpAddImageToLayer(
            s.next(),s.next(), s.nextInt(),s.nextInt(),ioUtil));
    knownCommands.put("set-filter",s -> new OpSetFilter(s.next(),s.next()));
    knownCommands.put("save-image",s -> new OpSaveImage(s.next(),ioUtil));
    knownCommands.put("quit", s -> new OpQuit());

    //the ioUtil is added to saving and loading images because
    //it is relevant to have more than 3 types of images to read/
    //write from; it is not the same relevant to do the same for
    //the load/save project ControllerOperations; text is fine
  }


  @Override
  public void runCollage() {
    boolean quit = false;

    //prints the list of commands to the view
    renderMessage(this.printCommands());

    while (!quit) {
      try {
        //this could throw a NoSuchElementException
        String next = sc.next();

        //null is OK in this instance as it is handled
        //immediately in a try catch block that checks
        //for a NullPointerException


        //ControllerOperations op = knownCommands.getOrDefault(next,null).apply(sc);
        ControllerOperations op;

        Function<Scanner,ControllerOperations> fun = knownCommands.getOrDefault(next,null);
        op = fun.apply(sc);

        //tries to run the model
        op.execute(model);

        //if the model has not thrown an exception,
        //it has run successfully, and the controller
        //sends the following message to the view:
        renderMessage(next + " run successfully!\n");
      } catch (NullPointerException e) {
        //null is the "default" value for when a String
        //command is not found.  In this case, it should
        //render the default message:
        renderMessage(printDefault());
      } catch (IllegalArgumentException | IllegalStateException e) {
        if (e.getMessage().contains("quit")) {
          //if the error message propagated contains quit
          //that means that the user tried to quit the
          //model without a project open.
          //for this instance, this implementation of
          //controller defines this as quitting the
          //program as a whole--not just the model
          renderMessage("No project open when quit; quitting program.");
          quit = true;
        } else {
          //exception e is used here to catch
          //other exceptions propagated by
          //the model and displaying these
          //errors on the view.
          renderMessage(e.getMessage() + "\n");
        }
      } catch (InputMismatchException e) {
        renderMessage(e.getMessage() + "\n");
      } catch (NoSuchElementException e) {
        //accounts for the case where there is no "next"
        //in the readable:
        break;
      }
    }


  }


  //prints the list of knownCommands
  private String printCommands() {
    return "The following is a list of known commands:\n"
            + this.knownCommands.keySet()
            + "\n";
  }

  //prints the default message when the command is not found
  private String printDefault() {
    String defaultMessage = "Command not found.\n";
    defaultMessage += printCommands();
    return defaultMessage;
  }

  //renders a message to the view and throws an informative IllegalStateException
  //@throws IllegalStateException when output to the view fails
  private void renderMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      //Throws an IllegalStateException because there
      //was an issue with initialising the view
      //also allows for the exception to provide a useful message
      throw new IllegalStateException("renderMessage from controller to view failed.");
    }
  }
}
