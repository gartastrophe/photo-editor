package controller.utils;

import model.pixel.Pixel;

/**
 * This represents the interface of IO utilities
 * going forwards.  This interface allows for
 * the user to convert standard file types such
 * as png or jpg or ppm into an array of pixels.
 *
 */
public interface IOUtilInterface {

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
   * <p>Ideally this method would pass along an
   * encoded String or set of bytes making it
   * harder to decrypt.</p>
   *
   * @param path represents the path to the given file
   * @return the contents of the file in a String format
   * @throws IllegalArgumentException when the file is not found.
   */
  Pixel[][] readFile(String path);

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
  void writeFile(String fileName,String contents);
}
