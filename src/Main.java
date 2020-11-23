import java.sql.*;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    
    Parser parser = new Parser("lyrics.txt");
    parser.parseFile();
    ArrayList<Song> songs = parser.getSongArrayList();
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beatles", "root", "");
      
      
      String query = "INSERT INTO songs (song_title, album_title, song_lyrics) VALUES(?,?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(query);
      for(Song s : songs) {
        preparedStatement.setString(1, s.getTitle());
        preparedStatement.setString(2, s.getAlbumTitle());
        preparedStatement.setString(3, s.getLyrics());
        preparedStatement.executeUpdate();
      }
  
      System.out.println("Database created successfully");
      con.close();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
