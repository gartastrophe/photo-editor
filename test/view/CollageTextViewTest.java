package view;

import org.junit.Test;

import java.io.IOException;

import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Represents tests for the class CollageTextView.
 * Tests the one method in the class renderMessage.
 */
public class CollageTextViewTest {

  /**
   * tests valid/invalid constructors for renderMessage.
   */
  @Test
  public void constructorTest() {
    //test for convenience constructor:

    //test if the following throws exceptions (it shouldn't)
    CollageView view = new CollageTextView();

    try {
      //view send to System.out
      //there should be no issues
      view.renderMessage("a");
    } catch (IOException e) {
      fail("should not catch any exceptions");
    }


    //test for full constructor:

    //tests for full constructor with null appendable:

    try {
      //this should throw an exception
      view = new CollageTextView(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable cannot be null.",e.getMessage());
    }


    //tests for full constructor with appendable that only throws IOExceptions:


    //make anonymous appendable that only throws IOExceptions:
    Appendable app = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }
    };

    try {
      //view should not have any issues taking in the appendable
      //that only throws IOExceptions:
      view = new CollageTextView(app);
    } catch (IllegalArgumentException e) {
      fail("should not throw an exception here");
    }

    try {
      //view should not have any issues taking in the appendable
      view.renderMessage("ahh");
    } catch (IOException e) {
      assertEquals("Error: Appendable IOException",e.getMessage());
    }


    //tests for full constructor with functioning appendable:

    //override app to a new StringBuilder
    app = new StringBuilder();
    assertEquals("",app.toString());
    view = new CollageTextView(app);
    try {
      view.renderMessage("a");
      assertEquals("a",app.toString());
    } catch (IOException ignore) {
      fail("should not throw IOException here");
    }
  }



  /**
   * Tests the method renderMessage in the class
   * CollageTextView for examples that throw an
   * IOException.
   */
  @Test
  public void renderMessageIOTest() {
    //make anonymous appendable that only throws IOExceptions:
    Appendable app = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Error: Appendable IOException");
      }
    };


    //view should not have any issues taking in the appendable:
    CollageView view = new CollageTextView(app);

    try {
      //view should only throw the exception when calling renderMessage
      view.renderMessage("a");
      fail("view should propagate this exception");
    } catch (IOException e) {
      assertEquals("Error: Appendable IOException",e.getMessage());
    }

    try {
      //view should still throw the exception when calling renderMessage
      view.renderMessage("abc 123");
      fail("view should propagate this exception");
    } catch (IOException e) {
      assertEquals("Error: Appendable IOException",e.getMessage());
    }
  }


  /**
   * Tests the method renderMessage in the class
   * CollageTextView for examples that do not
   * throw an IOException.
   */
  @Test
  public void renderMessageTest() {
    Appendable app = new StringBuilder();
    CollageView view = new CollageTextView(app);
    //try adding "abc" to the appendable
    try {
      view.renderMessage("abc");
    } catch (IOException e) {
      fail("this should throw an exception here");
    }
    assertEquals("abc",app.toString());

    //try adding "d" to the appendable
    try {
      view.renderMessage("d");
    } catch (IOException e) {
      fail("this should throw an exception here");
    }
    //should add to the END of the appendable
    assertEquals("abcd",app.toString());
  }
}