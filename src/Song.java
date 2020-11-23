public class Song {
  private int id;
  private String title;
  private String albumTitle;
  private String lyrics;
  
  public Song(String title, String albumTitle, String lyrics) {
    this.title = title;
    this.albumTitle = albumTitle;
    this.lyrics = lyrics;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getLyrics() {
    return this.lyrics;
  }
  
  public String getAlbumTitle() {
    return albumTitle;
  }
  
  @Override
  public String toString() {
    return this.title + "\t" + this.albumTitle;
  }
  
}
