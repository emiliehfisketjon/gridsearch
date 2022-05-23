package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GridSearch3 {
	
  private int dimensions;
  private int minimum;
  private String filename;
  private Scanner scanner;
  private String newFile;

	
  public GridSearch3(int dimensions, int minimum, String filename, String newFile) {
    this.dimensions = dimensions;
    this.minimum = minimum;
    this.filename = filename;
    this.newFile = newFile;
    useThreads();
  }

  /**
 * method that creates GridSearchThread objects and then uses threads 
 * lastly writes the result to file
 */
  public void useThreads() {
    char[][] grid = gridArray(filename, dimensions);
    List<String> words = dictionary("words.txt", minimum);
    int size = words.size();

    GridSearchThread g1 = new GridSearchThread(grid, words.subList(0, (size + 1)/2), dimensions);
    GridSearchThread g2 = new GridSearchThread(grid, words.subList((size + 1)/2, size), dimensions);
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

  public void writeToFile(List<String> list, String filename) {
    if(!list.isEmpty()) {
      File file = new File(filename);
	  try {
	    if(file.createNewFile()) {
	      FileWriter writer = new FileWriter(file.getName());
	      list.forEach(e -> {
	      try {
	        writer.write(e + "\n");
	      } catch (IOException e1) {
			e1.printStackTrace();
	      }
	      });
	      writer.close();
	      System.out.println("Successfully written to file: " + newFile + "\n");
		} else {
	      System.out.println("Filename already exists!");
		}
	  } catch (IOException e) {
	    e.printStackTrace();
	  }
	}	
  }
		
  public List<String> dictionary(String dictionary, int min) {
    List<String> sol = new ArrayList<>();
    try {
      File file = new File(dictionary);
	  scanner = new Scanner(file);
	  
	  while(scanner.hasNextLine()) {
	    String data = scanner.nextLine();
	    if(data.length() >= min) {
	      sol.add(data);
		}
	  }
	  scanner.close();
	} catch (FileNotFoundException e) {
      e.printStackTrace();
	}
	return sol;
  }
		
	
  public List<String> sort(List<String> list){
    List<String> sortedList = list.stream().sorted((a,b) -> b.length() - a.length()).collect(Collectors.toList());
	return sortedList;
  }
	
  public char[][] gridArray(String filename, int n){
    char[][] grid = new char[n][n];
    try {
      File file = new File(filename);
      scanner = new Scanner(file);
      int k = 0;
      
      while(scanner.hasNextLine() && k <= n) {
        String data = scanner.nextLine();
        
        for(int i = 0; i < n; i++) {
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
