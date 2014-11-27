/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

/**
 *
 * @author Daan
 */
public class Song {
    String artist;
    String title;
    MusicGenreEnum genre;

    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Artist: " + artist + " - " + "Title: " + title; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
