import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Parser {
  private String inputFile;
  private ArrayList<Song> songArrayList;
  
  public Parser(String inputFile) {
    this.inputFile = inputFile;
    this.songArrayList = new ArrayList<>();
  }
  
  public void parseFile() {
    Scanner file = openFile(this.inputFile);
    
    // Information used in Song constructor
    StringBuilder songLyrics = new StringBuilder("");
    String albumTitle = "";
    String songTitle = "";
    
    // File pointer information
    String line = file.nextLine();
    String nextLine = "";
    
    while(file.hasNextLine()) {
      
      // Skip over empty lines
      if(line.isBlank()) {
        line = file.nextLine();
  
        // Checks if current line is the album title
      } else if(line.startsWith("<") && line.endsWith(">")) {
        albumTitle = line.substring(1, line.length() - 1);
        line = file.nextLine();
        
        // Checks if current line is the album title
      } else if(line.startsWith("\"") && line.endsWith("\"")) {
        
        songTitle = line.substring(1, line.length() - 1);
        line = file.nextLine();
        
        // If not the album or song name, then it's a lyric
      } else {
        
        // Adds lyrics until the next line is a song or album title
        while(!((nextLine.startsWith("\"") && nextLine.endsWith("\"")) || (nextLine.startsWith("<") && nextLine.endsWith(">")))) {
  
          if(line.contains("(")) {
            line = removeParenthesisAndTextInside(line);
          }
          
          // Add line to lyrics and shift line pointers one position
          songLyrics.append(line).append("\n");
          line = nextLine;
          
          try {
            nextLine = file.nextLine();
            
            // Runs when getting to the last line in the file. Adds last line
          } catch(NoSuchElementException e) {
            songLyrics.append(line).append("\n");
            break;
          }
        }
        
        // Shift line pointers for next iteration
        line = nextLine;
        try {
          nextLine = file.nextLine();
        } catch(NoSuchElementException ignore) {
        }
        
        // Add Song object to ArrayList after all information for current song has been gathered
        this.songArrayList.add(new Song(songTitle, albumTitle, songLyrics.toString()));
        
        // Clear songLyrics StringBuilder
        songLyrics.delete(0, songLyrics.length());
      }
    }
    
  }
  
  private String removeParenthesisAndTextInside(String line) {
    // Finds indexes of first instance of parenthesis
    int indexStart = line.indexOf('(');
    int indexEnd = line.indexOf(')');
    
    ArrayList<Character> characters = new ArrayList<>();
    StringBuilder result = new StringBuilder();
    
    // Adds characters not within parenthesis in Character ArrayList
    for(int i = 0; i < line.length(); i++) {
      if(i >= indexStart && i <= indexEnd) {
        continue;
      }
      characters.add(line.charAt(i));
    }
    
    // Move Character ArrayList to StringBuilder to be able to return as String
    for(char c : characters) {
      result.append(c);
    }
    return result.toString().trim();
  }
  
  public ArrayList<Song> getSongArrayList() {
    return this.songArrayList;
  }
  
  /**
   * Creates a Scanner object given a file name. Used to be
   * able to read from it.
   * @param fileName The name of the file to be scanned
   * @return A Scanner object of the given file
   */
  private Scanner openFile(String fileName) {
    try {
      return new Scanner(new File(fileName));
    } catch(FileNotFoundException e) {
      System.out.println("The file cannot be found");
      System.exit(0);
    }
    return null;
  }
}
