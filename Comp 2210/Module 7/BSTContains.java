/**
 * Complete the contains method in the nested BinarySearchTree class below.
 *
 */
public class BSTContains {

   /** Provides an example. */
   public static void main(String[] args) {
      BinarySearchTree<Integer> iBst = new BinarySearchTree<>();
      // 38, 47, 56, 65, 74, 83, 92, 101, 110, 119, 128, 137, 146, 155, 164
      iBst.add(38);
      iBst.add(47);
      iBst.add(56);
      iBst.add(65);
      iBst.add(74);
      iBst.add(83);
      iBst.add(92);
      iBst.add(101);
      iBst.add(110);
      iBst.add(119);
      iBst.add(128);
      iBst.add(137);
      iBst.add(146);
      iBst.add(155);
      iBst.add(164);
      boolean found = iBst.contains(128);
      // The following statement should print false.
      System.out.println(found);
   
      BinarySearchTree<String> sBst = new BinarySearchTree<>();
      sBst.add("W");
      sBst.add("A");
      sBst.add("R");
      sBst.add("E");
      sBst.add("A");
      sBst.add("G");
      sBst.add("L");
      sBst.add("E");
      found = sBst.contains("A");
      // The following statement should print true.
      System.out.println(found);
   }



   /** Defines a binary search tree. */
   static class BinarySearchTree<T extends Comparable<T>> {
   
      // the root of this binary search tree
      private Node root;
   
      // the number of nodes in this binary search tree
      private int size;
   
      /** Defines the node structure for this binary search tree. */
      private class Node {
         T element;
         Node left;
         Node right;
      
         /** Constructs a node containing the given element. */
         public Node(T elem) {
            element = elem;
            left = null;
            right = null;
         }
      }
   
   
      /* >>>>>>>>>>>>>>>>>> YOUR WORK STARTS HERE <<<<<<<<<<<<<<<< */
   
   
      ///////////////////////////////////////////////////////////////////////////////
      //   I M P L E M E N T   T H E   C O N T A I N S   M E T H O D   B E L O W   //
      ///////////////////////////////////////////////////////////////////////////////
   
      /**
       * Returns true if this bst contains value, false otherwise.
       */
      public boolean contains(T value) {
         Node val =  root;
         if (size == 0) {
            return false;
         }
         //ArrayList<T> memory = new ArrayList<T>();
         if (recursive_search(val, value) == false) {
            return false;   
         } 
         return recursive_search(val, value);
         
         
         
             
         
         
      
      }
      
      private boolean recursive_search(Node n, T value) {
         if (value.compareTo(n.element) == 0) {
            return true;
         }    
         if (value.compareTo(n.element) < 0) {
            if (n.left == null) {
               
               return false;
            }
            return recursive_search(n.left, value);
            
         } 
         
         if (value.compareTo(n.element) > 0) {
            if (n.right == null) {
               
               return false;
            }
            return recursive_search(n.right, value);
            
         } 
         return false;
      }
   
   
      /* >>>>>>>>>>>>>>>>>> YOUR WORK ENDS HERE <<<<<<<<<<<<<<<< */
   
   
   
      ////////////////////////////////////////////////////////////////////
      //  D O   N O T   M O D I F Y   B E L O W   T H I S   P O I N T   //
      ////////////////////////////////////////////////////////////////////
   
   
   
      ////////////////////
      // M E T R I C S  //
      ////////////////////
   
      /**
       * Returns the number of elements in this bst.
       */
      public int size() {
         return size;
      }
   
      /**
       * Returns true if this bst is empty, false otherwise.
       */
      public boolean isEmpty() {
         return size == 0;
      }
   
      /**
       * Returns the height of this bst.
       */
      public int height() {
         return height(root);
      }
   
      /**
       * Returns the height of node n in this bst.
       */
      private int height(Node n) {
         if (n == null) {
            return 0;
         }
         int leftHeight = height(n.left);
         int rightHeight = height(n.right);
         return 1 + Math.max(leftHeight, rightHeight);
      }
   
   
      ////////////////////////////////////
      // A D D I N G   E L E M E N T S  //
      ////////////////////////////////////
   
      /**
       * Ensures this bst contains the specified element. Uses an iterative implementation.
       */
      public void add(T element) {
         // special case if empty
         if (root == null) {
            root = new Node(element);
            size++;
            return;
         }
      
         // find where this element should be in the tree
         Node n = root;
         Node parent = null;
         int cmp = 0;
         while (n != null) {
            parent = n;
            cmp = element.compareTo(parent.element);
            if (cmp == 0) {
               // don't add a duplicate
               return;
            } else if (cmp < 0) {
               n = n.left;
            } else {
               n = n.right;
            }
         }
      
         // add element to the appropriate empty subtree of parent
         if (cmp < 0) {
            parent.left = new Node(element);
         } else {
            parent.right = new Node(element);
         }
         size++;
      }
   
   }

}
