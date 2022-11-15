import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Tyler Teufel (tdt0025@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  1/27/22
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
      if ( a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0];
      for (int num : a) {
         if (num < min) {
            min = num;
         }
      }
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
      if ( a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0];
      for (int num : a) {
         if (num > max) {
            max = num;
         }
      }
      return max; 
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      if (k == 1) {
         return Selector.min(a);
      }
      int[] b = new int[a.length];
      for (int j = 0; j < a.length; j++) {
         b[j] = a[j];
      }
      
      
      Arrays.sort(b);
      int distinct = 1;
      int kmin = 0;
      
      for (int i = 1; i < a.length; i++) {
         if (b[i] != b[i-1]) {
            distinct++;
            if (distinct == k) {
               kmin = b[i];
            }
         }
      }
      
      
      if (k > distinct) {
         throw new IllegalArgumentException();
      }
      else if (k == a.length) {
         return Selector.max(a);
      }
   
      return kmin;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      if (k == 1) {
         return Selector.max(a);
      }
      int[] b = new int[a.length];
      for (int j = 0; j < a.length; j++) {
         b[j] = a[j];
      }
      
      
      Arrays.sort(b);
      int distinct = 1;
      int tempV = b[a.length - 1];
      int kmax = 0;
      
      for (int i = a.length - 1; i >= 0; i--) {
         if (b[i] != tempV) {
            distinct++;
            if (distinct == k) {
               kmax = b[i];
            }
         }
         tempV = b[i];
      }
      
      
      if (k > distinct) {
         throw new IllegalArgumentException();
      }
      else if (k == a.length) {
         return Selector.min(a);
      }
   
      return kmax;
   
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int size = 0;
      
      for (int val : a) {
         if (val >= low && val <= high) {
            size++;
         }
      }
      
      int[] range = new int[size];
      if (size == 0) {
         return range;
      }
      
      int j = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            range[j] = a[i];
            
            j++;
         }
      }
      
      return range;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      boolean qualifyingVal = false;
      int size = 0;
      for (int val : a) {
         if (val >= key) {
            qualifyingVal = true;
            size++;
         }
      }
      
      if (qualifyingVal == false) {
         throw new IllegalArgumentException();
      }
      
      int[] qualVals = new int[size];
      int j = 0;
      
      for (int i = 0; i <a.length; i++) {
         if (a[i] >= key) {
            qualVals[j] = a[i];
            j++;
         }
      }
      
      Arrays.sort(qualVals);
      return Selector.min(qualVals);
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      boolean qualifyingVal = false;
      int size = 0;
      for (int val : a) {
         if (val <= key) {
            qualifyingVal = true;
            size++;
         }
      }
      
      if (qualifyingVal == false) {
         throw new IllegalArgumentException();
      }
      
      int[] qualVals = new int[size];
      int j = 0;
      
      for (int i = 0; i <a.length; i++) {
         if (a[i] <= key) {
            qualVals[j] = a[i];
            j++;
         }
      }
      
      Arrays.sort(qualVals);
      return Selector.max(qualVals);
   }

}
