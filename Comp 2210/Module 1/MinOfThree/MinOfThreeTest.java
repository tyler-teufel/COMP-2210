import static org.junit.Assert.assertEquals;

import org.junit.Test;

 /**
  * MinOfThreeTest.java
  * Demonstrates the test cases for MinOfThree methods.
  */
public class MinOfThreeTest {

   /** Test method for min1. **/
   @Test 
   public void Min1Test1() {
   
      int a = 3;
      int b = 2;
      int c = 1;
      int expected = c;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min1. **/
   @Test 
   public void Min1Test2() {
   
      int a = 6;
      int b = 2;
      int c = 37;
      int expected = b;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min1. **/
   @Test 
   public void Min1Test3() {
   
      int a = 1;
      int b = 22;
      int c = 37;
      int expected = a;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min2. **/
   @Test 
   public void Min1Test4() {
   
      int a = 34;
      int b = 34;
      int c = 5555;
      int expected = b;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   
   /** Test method for min2. **/
   @Test 
   public void Min2Test1() {
   
      int a = 34;
      int b = 245;
      int c = 3;
      int expected = c;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min2. **/
   @Test 
   public void Min2Test2() {
   
      int a = 34;
      int b = 5;
      int c = 323;
      int expected = b;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min2. **/
   @Test 
   public void Min2Test3() {
   
      int a = 3;
      int b = 245;
      int c = 34;
      int expected = a;
      int actual = MinOfThree.min1(a, b, c);
      assertEquals(expected, actual);
   }
   
   /** Test method for min2. **/
   @Test 
   public void Min2Test4() {
   
      int a = 3;
      int b = 3;
      int c = 355;
      int expected = a;
      int actual = MinOfThree.min2(a, b, c);
      assertEquals(expected, actual);
   }
   
}
