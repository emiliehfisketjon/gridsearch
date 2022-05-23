package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author
 *
 *     GridSearch program 
 *
 */
public class GridSearch {

  private static int n = 100;
  private static String filename = "grid100x100.txt";
  private static Scanner scanner;

  public static void main(String[] args) {
	  
    List<String> letters = solver(filename, "words.txt", 0, n);
    letters.stream().sorted((a, b) -> b.length() - a.length()).forEach(System.out::println);
    
  }

  /**
 * @param filename grid file 
 * @param dictionary list of words (words.txt)
 * @param min minimum length of characters 
 * @param num dimensions 
 * @return a list with found words
 */
  public static List<String> solver(String filename, String dictionary, int min, int num) {
    List<String> sol = new ArrayList<>();
    char[][] grid = gridArray(filename, n);
    
    try {
      File file = new File(dictionary);
      scanner = new Scanner(file);
      
      while (scanner.hasNextLine()) {
        String data = scanner.nextLine();
        
        if (data.length() >= min && checkWord(grid, data, n)) {
          sol.add(data);
        }
      }
      
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return sol;
  }


  /**
 * @param grid 2D array
 * @param word search for in grid
 * @param size dimensions of the grid
 * @return true if the word was found
 */
  public static boolean checkWord(char[][] grid, String word, int size) {
    if (word.length() > size * size) {
      return false;
    }
    for (int r = 0; r < size; r++) {
    	
      for (int c = 0; c < size; c++) {

        if (grid[r][c] == word.charAt(0)) {
        	
          if (wordSearch(grid, word, 0, r, c, size)) {

            return true;
          }
        }
      }
    }
    return false;
  }

/** 
 * searching through the grid, keeping track of already visited characters 
 * (by temporarily changing the character to an empty char ' ') and out of bound cases. 
 * @param grid 2D array
 * @param word search for in grid
 * @param comp number of character matches done
 * @param rpos row position
 * @param cpos column position
 * @param size dimensions
 * @return true if word was found
 */
  public static boolean wordSearch(char[][] grid, String word, int comp, int rpos, int cpos, int size) {
    if (word.length() == comp) {
      return true;
    }
    
    if (rpos < 0 || cpos < 0 || rpos >= size || cpos >= size) {
      return false;
    }

    if (grid[rpos][cpos] == ' ') { 
      return false;
    }

    char visited = grid[rpos][cpos];


    if (grid[rpos][cpos] == word.charAt(comp)) {

      grid[rpos][cpos] = ' ';

      boolean match = wordSearch(grid, word, comp + 1, rpos - 1, cpos, size)
             || wordSearch(grid, word, comp + 1, rpos, cpos + 1, size)
             || wordSearch(grid, word, comp + 1, rpos + 1, cpos, size)
             || wordSearch(grid, word, comp + 1, rpos, cpos - 1, size);

      grid[rpos][cpos] = visited;

      return match;
    }

    return false;
  }


  /**
   * constructing the grid 
   * 
 * @param filename name
 * @param n dimensions
 * @return 2D char array 
 */
  public static char[][] gridArray(String filename, int n) {
    char[][] grid = new char[n][n];

    try {
      File file = new File(filename);
      scanner = new Scanner(file);
      int k = 0;
      
      while (scanner.hasNextLine() && k <= n) {
        String data = scanner.nextLine();
        
        for (int i = 0; i < n; i++) {
          grid[k][i] = data.charAt(i);
        }
        k++;
      }
      scanner.close();

    } catch (FileNotFoundException e) {

      e.printStackTrace();
    }

    return grid;
  }

}
