package task;

import java.util.ArrayList;
import java.util.List;

public class GridSearchThread extends Thread {
  private char[][] grid;
  private List<String> words;
  private int n;
  private List<String> result;

  public GridSearchThread(char[][] grid, List<String> words, int n) {
    this.grid = grid;
	this.words = words;
	this.n = n;
  }

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

  public List<String> solverThread(char[][] grid, List<String> words, int n) {
    List<String> res = new ArrayList<>();
    for (String w : words) {
	  if (checkWord(grid, w, n)) {
	    res.add(w);
	  }
	}
	return res;
  }

  public static boolean wordSearch(char[][] grid, String word, int compared, int rpos, int cpos, int size) {
    if (word.length() == compared) { // word found
      return true;
	}

	if (rpos < 0 || cpos < 0 || rpos >= size || cpos >= size) { 
	  return false;
	}

	if (grid[rpos][cpos] == ' ') { 
	  return false;
	}

	char visited = grid[rpos][cpos];
	
	if (grid[rpos][cpos] == word.charAt(compared)) {
	  grid[rpos][cpos] = ' ';
	  
	  boolean match = wordSearch(grid, word, compared + 1, rpos - 1, cpos, size)
	             || wordSearch(grid, word, compared + 1, rpos, cpos + 1, size)
	             || wordSearch(grid, word, compared + 1, rpos + 1, cpos, size)
	             || wordSearch(grid, word, compared + 1, rpos, cpos - 1, size);

	  grid[rpos][cpos] = visited;
	  return match;
	}
	return false;
  }

  @Override
  public void run() {
    result = solverThread(grid, words, n);
  }

  public List<String> getList() {
    return result;
  }
}
