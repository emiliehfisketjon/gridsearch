package task;

import java.util.List;
import java.util.Scanner;
import java.lang.Object;

public class GridSearchMain {
  private static int dimensions;
  private static int minimum;
  private static String filename;
  private static String newFile;
  
  public static void main(String[] args) {
    boolean con = true;
   
    
    
    //List<String> bigList = grid.dictionary("words.txt", 0);
    		//List<List<String>> smallerLists = Lists.partition(bigList, 10);
    
    while(con) {
      Scanner sc = new Scanner(System.in);
      System.out.println("1: Search for words in grid file: \n" + "2: exit");
      String ans = sc.nextLine();
      
      if(ans.equals("1") || ans.equals("2")) {
    	  
        switch(Integer.parseInt(ans)) {
        
          case 1:
            System.out.println("Enter filename-dimensions 3,10,25,100,250,500,1000(int):");
            if (!sc.hasNextInt()) break;
            dimensions = Integer.parseInt(sc.nextLine());
            System.out.println("Enter minimum length of characters(int):");
            if (!sc.hasNextInt()) break;
            minimum = Integer.parseInt(sc.nextLine());
            System.out.println("Enter new filename(string):");
            newFile = sc.nextLine() + ".txt";
            filename = "grid" + dimensions + "x" + dimensions + ".txt";
            System.out.println("\n Searching... \n");
            GridSearch3 grid = new GridSearch3(dimensions, minimum, filename, newFile);
            break;
				
		  case 2:
		    con = false;
		    sc.close();
		  break;
			
		  default:
		    System.out.println("Invalid input");
		  break;
		}
						
	  } else {
	    System.out.println("Invalid input");
	  }
    }
  }
}
