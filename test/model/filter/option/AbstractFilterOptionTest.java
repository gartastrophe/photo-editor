package model.filter.option;

import org.junit.Test;

import model.filter.option.brightness.BrightenLuma;
import model.filter.option.colour.components.GreenComponent;
import model.filter.option.colour.components.RedComponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the methods for comparing equality in the
 * AbstractFilterOption.
 */
public class AbstractFilterOptionTest {

  /**
   * Tests for equality metrics, namely the .equals and hash-code.
   */
  @Test
  public void equality() {
    FilterOption op1 = new GreenComponent();
    FilterOption op2 = new RedComponent();
    FilterOption op3 = new RedComponent();
    FilterOption op4 = new BrightenLuma();

    //assert that ops 2 and 3 are equal
    assertEquals(op2, op3);
    assertEquals(op3, op2);

    //assert hash-codes are the same too
    assertEquals(op2.hashCode(), op3.hashCode());
    assertEquals(op3.hashCode(), op2.hashCode());

    //the following should not be equal:
    //op1 and op2
    assertNotEquals(op2, op1);
    assertNotEquals(op1, op2);
    //op1 and op4
    assertNotEquals(op4, op1);
    assertNotEquals(op1, op4);
    //op2 and op4
    assertNotEquals(op4, op2);
    assertNotEquals(op2, op4);


    //the following should not have the same hashcode:
    //op1 and op2
    assertNotEquals(op2.hashCode(), op1.hashCode());
    assertNotEquals(op1.hashCode(), op2.hashCode());
    //op1 and op4
    assertNotEquals(op4.hashCode(), op1.hashCode());
    assertNotEquals(op1.hashCode(), op4.hashCode());
    //op2 and op4
    assertNotEquals(op4.hashCode(), op2.hashCode());
    assertNotEquals(op2.hashCode(), op4.hashCode());

    //operation and null value
    assertNotEquals(null, op1);
    assertNotEquals(op1, null);
  }
}