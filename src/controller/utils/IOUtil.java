package controller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents Input-Output Utility Functions--more generally
 * for reading and writing files for the controller.
 */
public class IOUtil {

  /**
   * Attempts to read from the given file path.
   * This method will convert the contents of
   * the file path to a String and drop all
   * comments.
   *
   * <p>If the attempt to read fails, this method
   * will throw an IllegalArgumentException with
   * a relevant message.</p>
   *
   * @param path represents the path to the given file
   * @return the contents of the file in a String format
   * @throws IllegalArgumentException when the file is not found.
   */
  public static String readFile(String path) throws IllegalArgumentException {
    Scanner sc;

    try {
      Objects.requireNonNull(path);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Given path cannot be null.");
    }

    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      // sc will remain null if and only if the line above throws
      // a FileNotFoundException. Therefore, it would be redundant
      // to test for a NullPointerException
      throw new IllegalArgumentException("File " + path + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();

      if (s.length() > 0 && s.charAt(0) != '#') {
        builder.append(s + "\n");
      }
    }

    return builder.toString();
  }

  /**
   * Attempts to write the given contents to a file
   * of the given fileName.  If no directory is specified,
   * this method will write to the res directory.  At
   * the moment, this method will only write to files for
   * accepted tokens, "C1" for project files, and "P3"
   * for PPM files.  All other cases will throw an
   * IllegalArgumentException at this time.
   *
   * <p>NOTE: this method does NOT accept the absolute path
   * at this time; it only accepts the relative path.  Please
   * use File(name).getPath() in the fileName param.</p>
   *
   * @param fileName represents the name of the file
   *                 to write to;
   * @param contents represents the contents of
   *                 the file
   */
  public static void writeFile(String fileName,String contents) {
    //declare the variable filePath
    //which represents the path to
    //the PPM file using the given fileName.
    String filePath;
    String tempContents;

    //test for null values
    try {
      filePath = Objects.requireNonNull(fileName);
      tempContents = Objects.requireNonNull(contents);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("fileName & contents cannot be null.");
    }

    // gets the first two characters of the given
    // contents; at the moment, all accepted
    // tokens use the two-character tokens;
    // all other tokens will be rejected
    String token = tempContents.substring(0,3);
    String suffix;
    if (token.equals("P3\n")) {
      suffix = ".ppm";
    } else if (token.equals("C1\n")) {
      suffix = ".txt";
    } else {
      throw new IllegalArgumentException("File token not supported at this time.");
    }

    //tests to see if a specific directory is mentioned;
    //if not, add the filePath to the res/ directory
    if (!filePath.contains(File.separator)
            && !filePath.contains("/")
            && !filePath.contains("\\")) {
      filePath = "res" + File.separator + filePath;
    }

    //appends fileType at the end of the filepath
    //if it is not already there, and it has no suffix already
    if (!filePath.contains(suffix) && filePath.indexOf('.') == -1) {
      filePath += suffix;
    } else {
      String existingSub = filePath.substring(filePath.indexOf('.'));
      if (!existingSub.equals(suffix)) {
        throw new IllegalArgumentException("fileName's suffix and content's token " +
                "do not match; aborting writeFile.");
      }
    }

    //declare the FileWriter
    FileWriter file;

    try {
      file = new FileWriter(filePath);

      file.append(tempContents);

      //close the file afterwards
      file.flush();
      file.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
