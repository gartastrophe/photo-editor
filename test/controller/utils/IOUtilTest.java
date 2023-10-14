package controller.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import model.model.CollageModel;
import model.model.CollageModelImpl2;
import model.pixel.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Tests the IOUtil class' methods, specifically the
 * reading and writing of the files.
 */
public class IOUtilTest {

  @Before
  public void writeTestFiles() {
    CollageModel<Pixel> model = new CollageModelImpl2();
    //create a 1 by 1 model
    model.newProject(1,1);
    //set background to red
    model.setFilter("background","red-component");
    assertEquals("P3\n" + //ppm file
                    "1 1\n" + //size = 1 by 1
                    "255\n" + //max = 255
                    "255 0 0 ", //red
            model.saveImage());
    IOUtil.writeFile("test.ppm",model.saveImage());
    IOUtil.writeFile("test.txt",model.saveProject());
  }


  /**
   * Tests the reading functionality of IOUtil.
   * Specifically tests reading from absolute
   * vs relative paths as well as invalid/null
   * inputs for IOUtil.readFile(...).
   */
  @Test
  public void readTest() {
    String ppm;

    //tests for reading from the newly made ppm file
    ppm = IOUtil.readFile("res/test.ppm");
    assertEquals("P3\n" +
            "1 1\n" +
            "255\n" +
            "255 0 0 \n",ppm);

    //tests for reading from the newly made txt file
    ppm = IOUtil.readFile("res/test.txt");
    assertEquals("C1\n" +
            "1 1\n" +
            "255\n" +
            "background red-component\n" +
            "255 255 255 255 \n",ppm);

    try {
      //tests for null path
      IOUtil.readFile(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Given path cannot be null.",e.getMessage());
    }

    try {
      //tests for invalid path
      IOUtil.readFile("null");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File null not found!",e.getMessage());
    }

    try {
      //tests for invalid path
      IOUtil.readFile("aaa");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File aaa not found!",e.getMessage());
    }

    //try passing in the full path instead of the relative path
    File file = new File("res/test.txt");
    String fullPath = file.getAbsolutePath();
    assertEquals("C1\n" +
            "1 1\n" +
            "255\n" +
            "background red-component\n" +
            "255 255 255 255 \n",IOUtil.readFile(fullPath));
  }

  /**
   * Tests the writing functionality of IOUtil.
   * Specifically tests reading from absolute
   * vs relative paths as well as invalid/null
   * inputs for IOUtil.writeFile(...).
   */
  @Test
  public void writeTest() {

    try {
      //test writing with null fileName
      IOUtil.writeFile(null, "P3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName & contents cannot be null.",e.getMessage());
    }

    try {
      //test writing with null contents
      IOUtil.writeFile("res/test.txt", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName & contents cannot be null.",e.getMessage());
    }

    try {
      //test writing with null contents
      IOUtil.writeFile(null, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName & contents cannot be null.",e.getMessage());
    }

    try {
      //test writing with token "aaa"
      IOUtil.writeFile("res/test.txt", "aaa");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File token not supported at this time.",e.getMessage());
    }

    //test for when file suffix not specified
    IOUtil.writeFile("res/test", "C1\nwriteTest1");
    assertEquals("C1\nwriteTest1\n",IOUtil.readFile("res/test.txt"));

    //test for when file suffix not specified
    IOUtil.writeFile("res/test", "P3\nwriteTest2");
    assertEquals("P3\nwriteTest2\n",IOUtil.readFile("res/test.ppm"));

    //test for when file suffix mismatched
    try {
      IOUtil.writeFile("res/test.ppm", "C1\nwriteTest1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName's suffix and content's token do not match; " +
              "aborting writeFile.",e.getMessage());
    }

    //test for when file suffix mismatched
    try {
      IOUtil.writeFile("res/test.txt", "P3\nwriteTest1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("fileName's suffix and content's token do not match; " +
              "aborting writeFile.",e.getMessage());
    }


    //test for when res not specified
    //test for "res/" not specified
    IOUtil.writeFile("test", "C1\nwriteTest3");
    assertEquals("C1\nwriteTest3\n",IOUtil.readFile("res/test.txt"));

    //test for when "res/" not specified
    IOUtil.writeFile("test", "P3\nwriteTest4");
    assertEquals("P3\nwriteTest4\n",IOUtil.readFile("res/test.ppm"));

    //test for absolute path
    //try passing in the full path instead of the relative path
    File file = new File("test.txt");
    String path = file.getPath();
    String fullPath = new File("res/test.txt").getAbsolutePath();

    IOUtil.writeFile(path,"C1\n" +
                    "1 1\n" +
                    "255\n" +
                    "background red-component\n" +
                    "255 255 255 255 ");
    assertEquals("C1\n" +
            "1 1\n" +
            "255\n" +
            "background red-component\n" +
            "255 255 255 255 \n",IOUtil.readFile(fullPath));


    //test for when the directory is wrong
    try {
      IOUtil.writeFile("re/res/test.txt", "C1\nwriteTest1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("re\\res\\test.txt (The system cannot find the path specified)",
              e.getMessage());
    }

  }



}