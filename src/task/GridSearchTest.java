package task;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridSearchTest {
  private char[][] grid;
  
  @BeforeEach
  void init() {
    grid = GridSearch.gridArray("grid3x3.txt", 3);
  }
  
  @Test
  void testCheckWord() {
    assertFalse(GridSearch.checkWord(grid, "johnfjfdef", 3));
    assertTrue(GridSearch.checkWord(grid, "jo", 3));
    assertFalse(GridSearch.checkWord(grid, "x", 3));
  }
  
  @Test
  void testWordSearch() {
	assertFalse(GridSearch.wordSearch(grid, "joh", 1, -1, 0, 3));
	assertFalse(GridSearch.wordSearch(grid, "joh", 1, 0, -3, 3));
	assertFalse(GridSearch.wordSearch(grid, "joh", 1, 4, 0, 3));
	assertTrue(GridSearch.wordSearch(grid, "joh", 3, 0, 0, 3));
  }
  
  @Test
  void testGridArray() {
	assertTrue(grid[1][1] == 'h');
	assertTrue(grid[1][2] == 'n');
  }
}
