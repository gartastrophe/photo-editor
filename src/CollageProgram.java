import java.io.InputStreamReader;
import java.io.StringReader;

import controller.CollageController;
import controller.CollageControllerImpl;
import controller.features.Features;
import controller.features.FeaturesController;
import controller.utils.IOUtil;
import controller.utils.IOUtil2;
import controller.utils.IOUtilInterface;
import model.model.CollageModel;
import model.model.CollageModelImpl3;
import model.pixel.Pixel;
import view.CollageGUIFrame;
import view.CollageTextView;
import view.CollageView;
import view.ImageCollageView;

/**
 * Represents the main class for the model.
 */
public class CollageProgram {
  /**
   * Runs a text-based CollageProgram when run.
   * @param args represents the String argument.
   */
  public static void main(String[] args) {
    CollageView view;
    CollageController controller;
    //always a CollageModelImpl3 (newest version)
    CollageModel<Pixel> model = new CollageModelImpl3();
    IOUtilInterface ioUtil = new IOUtil2();

    //java -jar Program.jar -file path-of-script-file
    if (args.length == 2
            && args[0].equals("-file")) {
      System.out.println(args[1]);
      //gets the next item (the path to the file)
      String commands = IOUtil.readFile(args[1]);
      Appendable a = new StringBuilder();
      view = new CollageTextView(a);
      controller = new CollageControllerImpl(model,view,new StringReader(commands),ioUtil);
      controller.runCollage();
      System.out.println(a);
    }
    //java -jar Program.jar -text
    else if (args.length == 1
            && args[0].equals("-text")) {
      view = new CollageTextView();
      controller = new CollageControllerImpl(model,view,new InputStreamReader(System.in),ioUtil);
      controller.runCollage();
    }
    //java -jar Program.jar
    else if (args.length == 0) {
      view = new CollageGUIFrame();
      Features fc = new FeaturesController(model,(ImageCollageView) view,ioUtil);
    }
    //when none of the above cases match
    else {
      //close program if not supported
      System.exit(1);
    }
  }
}

