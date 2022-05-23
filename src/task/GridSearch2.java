package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GridSearch2 {
  private static int n = 25;
  private static String filename = "grid25x25.txt";
  private static Scanner scanner;
  private static String newFile = "filename.txt";
  
  public static void main(String[] args) {
	  
	char[][] grid = gridArray(filename, n);
	List<String> words = dictionary("words.txt", 0);
	int size = words.size();

	GridSearchThread g1 = new GridSearchThread(grid, words.subList(0, (size + 1) / 2), n);
	GridSearchThread g2 = new GridSearchThread(grid, words.subList((size + 1) / 2, size), n);
	Thread thread1 = new Thread(g1);
	Thread thread2 = new Thread(g2);
	thread1.start();
	thread2.start();

	try {
	  thread1.join();
	  thread2.join();

	} catch (InterruptedException e) {

	  e.printStackTrace();
	}

	List<String> first = g1.getList();
	List<String> second = g2.getList();
	first.addAll(second);

	writeToFile(sort(first), newFile);

	}

  /**
   * write result to file
 * @param list with result
 * @param filename of file
 */
  public static void writeToFile(List<String> list, String filename) {

    if (!list.isEmpty()) {
	  File file = new File(filename);
	  
	  try {
	    if (file.createNewFile()) {
	      FileWriter writer = new FileWriter(file.getName());
	      list.forEach(e -> {
	      try {
	        writer.write(e + "\n");
	      } catch (IOException e1) {

	        e1.printStackTrace();
						}
	      });
	      
		  writer.close();
	      System.out.println("Successfully written to file");
	      
		} else {
			System.out.println("Filename already exists!");
		}
	  } catch (IOException e) {

	    e.printStackTrace();
		}
	}

  }

  /**
   * write words from file to list 
 * @param dictionary name of file
 * @param min minimum length of characters 
 * @return list with words (with length > min) 
 */
  public static List<String> dictionary(String dictionary, int min) {
    List<String> sol = new ArrayList<>();
    try {
      File file = new File(dictionary);
      scanner = new Scanner(file);
      
      while (scanner.hasNextLine()) {
	    String data = scanner.nextLine();
	    if (data.length() >= min) {
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
   * method that sort list from longest to smallest char length 
 * @param list 
 * @return sorted list 
 */
  public static List<String> sort(List<String> list) {
    List<String> sortedList = list.stream().sorted((a, b) -> b.length() - a.length()).collect(Collectors.toList());
	return sortedList;
  }


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
