import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.lang.Math;
import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class WordSearchGameEngine implements WordSearchGame {
   
   private TreeSet<String> lexicon;
   private String[][] board;
   private int width;
   private int height;
   private SortedSet<String> allWords;
   private boolean[][] visited;
   private final int MAX_NEIGHBORS = 8;
   private String partialWord;
   private ArrayList<Integer> path;
   private ArrayList<Position> posPath;
   
   
   public WordSearchGameEngine() {
      
      //a = ["E", "E", "C", "A", "A", "L", "E", "P", "H", "N", "B", "O", "Q", "T", "T", "Y"]
      board = new String[4][4];
      
      board[0][0] = "E";
      board[0][1] = "E";
      board[0][2] = "C";
      board[0][3] = "A";
      board[1][0] = "A";
      board[1][1] = "L";
      board[1][2] = "E";
      board[1][3] = "P";
      board[2][0] = "H";
      board[2][1] = "N";
      board[2][2] = "B";
      board[2][3] = "O";
      board[3][0] = "Q";
      board[3][1] = "T";
      board[3][2] = "T";
      board[3][3] = "Y";
      
      width = board.length;
      height = board[0].length;
      
   }
   
   public String getBoard() {
   
      String bstr = "";
      for (String[] arr : board) {
         bstr += Arrays.toString(arr) + "\n";
         
      }
      
      return bstr;
   
   }
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      lexicon = new TreeSet<String>();
      
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         Scanner fileScan = new Scanner(new BufferedReader(new FileReader(fileName)));
      
         while (fileScan.hasNextLine()) {
            String line = fileScan.next().toUpperCase();
            lexicon.add(line);
            fileScan.nextLine();
         }
      } 
      
      catch (java.io.FileNotFoundException e) {
         throw new IllegalArgumentException();
         
      }
   }
   
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      
      int n = (int) Math.sqrt(letterArray.length);
      if ( (n * n) != letterArray.length) {
         throw new IllegalArgumentException();
      }
      
      width = n;
      height = n;
      board = new String[width][height];
      
      //int MAxlen = letterArray.length;
      int len = 0;
      for (int i = 0; i < height; i++) {
         
         for (int j = 0; j < width; j++) {
            board[i][j] = letterArray[len];
            len++;
            
         }
      }
      
      
   
   }
   
   /**
    * Retrieves all scorable words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      posPath = new ArrayList<Position>();
      allWords = new TreeSet<String>();
      partialWord = "";
      
      for (int i = 0; i < height; i++) {
         
         for( int j = 0; j < width; j++) {
            partialWord = board[i][j];
            
            if (isValidWord(partialWord) && partialWord.length() >= minimumWordLength) {
               allWords.add(partialWord);
            }
            
            if (isValidPrefix(partialWord)) {
               Position temp = new Position(i,j);
               posPath.add(temp);
               dfs2(i, j, minimumWordLength);
               posPath.remove(temp);
               
            }
         }
      }
      
      
      
      return allWords;
      
      
   }
   
  /**
      * Depth-First Search.
      * @param x x-value
      * @param y y-value
      * @param wordToCheck word to check for.
      */
   private void dfs2(int x, int y, int min) {
      Position start = new Position(x,y);
      clearVisited();
      pathVisited();
         
      for (Position p : start.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
               
            if (isValidPrefix(partialWord + board[p.x][p.y])) {
               partialWord += board[p.x][p.y];
               posPath.add(p);
                  
               if (isValidWord(partialWord) && partialWord.length() >= min) {
                  allWords.add(partialWord);
               }
                  
               dfs2(p.x, p.y, min);
               posPath.remove(p);
               int endIndex = partialWord.length() - board[p.x][p.y].length();
               partialWord = partialWord.substring(0, endIndex);
                  
                  
            }
         }
      }
         
      clearVisited();
      pathVisited();
         
   } 
   
   
   /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      int score = 0;
      
      for (String word : words) {
         if (isValidWord(word) && isOnBoard(word) != null) {
            if (word.length() == minimumWordLength) {
               score += 1;
            }
            else if (word.length() > minimumWordLength) {
               score += ( (word.length() - minimumWordLength) + 1);
            }
            
         }
      }
      
      return score;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) {
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      return lexicon.contains(wordToCheck);
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (lexicon == null) {
         throw new IllegalStateException();
         
      }
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      prefixToCheck = prefixToCheck.toUpperCase();
      String lexWord = lexicon.ceiling(prefixToCheck);
      
      if (lexWord != null) {
         return lexWord.startsWith(prefixToCheck);
      }
      return false;         
      
     
      
      
   }
   
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      
      if (lexicon == null) {
         throw new IllegalStateException(); //testing to see if the lexicon has been loaded.
      }
      
      if (wordToCheck == null) {
         throw new IllegalArgumentException(); //making sure a word was entered.
      }
      
      partialWord = "";
      path = new ArrayList<Integer>();
      posPath = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      
      //iterating through the board to find a starting position, or a valid single letter word.
      for (int i = 0; i < height; i++) {
         
         for (int j = 0; j < width; j++) {
            
            if (wordToCheck.equals(board[i][j])) {
               path.add((i * width) + j);
               return path;
            }
            
            if (wordToCheck.startsWith(board[i][j])) {
               Position startP = new Position(i, j);
               posPath.add(startP);
               partialWord = board[i][j];
               dfs(i, j, wordToCheck);
               
               /*
                * If after the search the word is not equal to the resulting
                * partial, than you exit the loop, and continue iteration
                * until the next starting char is located to search again.
                */
               if (!wordToCheck.equals(partialWord)) {
                  posPath.remove(startP);
               }
               else {
                  /*
                   * If the word is indeed the word being looked for,
                   * The integer Array path is filled with the corresponding
                   * integers that represent the path to the complete word.
                   */
                  for (Position p : posPath) {
                     path.add((p.x * width) + p.y);
                  }
                  
                  return path;
                     
               }
               
            }
         }
         
         
      }
      return path;
      
      
   }
   
   /**
    * Clears all value on the board in terms of
    * if they have been visited yet.
    */
   private void clearVisited() {
      visited = new boolean[width][height];
      
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   /**
    * Iterates through the visited Position values
    * listed in posPath, marking them as visited on 
    * the visited[][] array of the board. 
    */
   private void pathVisited() {
      for (Position v : posPath) {
         visit(v);
      }
      
   
   }
   
   /**
    * Depth first search algorithm, that takes in a starting
    * value on the board, and searches it to the deepest value,
    * determining if it fits the criteria or not. This algorithm
    * contains backtracking, and does not take into account 
    * minimum values; purely to see if such a word is on the board
    * in reference to the lexicon.
    *
    * @param int x is the row.
    * @param int y is the column.
    * @param String wordToCheck is the word being searched for.
    */
   private void dfs(int x, int y, String wordToCheck) {
      if (partialWord.equals(wordToCheck)) {
         return;
      }
      clearVisited();
      pathVisited();
      
      
      Position position = new Position(x, y);
      
      
      
      for (Position p : position.neighbors()) {
         
         if (!isVisited(p)) {
            visit(p);
            
            if (wordToCheck.startsWith(partialWord + board[p.x][p.y])) {
               partialWord += board[p.x][p.y];
               posPath.add(p);
               dfs(p.x, p.y, wordToCheck);
               
               if (wordToCheck.equals(partialWord)) {
                  return;
               }
               
               
               else {
               
                  posPath.remove(p);
               
                  int endIndex = partialWord.length() - board[p.x][p.y].length();
                  partialWord = partialWord.substring(0, endIndex);
               }
               
            }
            
            
            
            
            
            
         }
      }
      
      clearVisited();
      pathVisited();
   }
   
   
   
   
   ///////////////////////////////////////////
   // Position class and associated methods //
   ///////////////////////////////////////////

   /**
    * Models an (x,y) position in the grid.
    */
   private class Position {
      int x;
      int y;
   
      /** Constructs a Position with coordinates (x,y). */
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      /** Returns a string representation of this Position. */
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      /** Returns all the neighbors of this Position. */
      public Position[] neighbors() {
         Position[] nbrs = new Position[MAX_NEIGHBORS];
         int count = 0;
         Position p;
         // generate all eight neighbor positions
         // add to return value if valid
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     nbrs[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(nbrs, count);
      }
   }

   /**
    * Is this position valid in the search area?
    */
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < width) && 
            (p.y >= 0) && (p.y < height);
   }

   /**
    * Has this valid position been visited?
    */
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   /**
    * Mark this valid position as having been visited.
    */
   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }

   
}


   

