/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

import java.util.LinkedList;

/**
 *
 * @author Daan
 */
public class Playlist {
    
    LinkedList<Song> songs = new LinkedList<>();

    Song playing;
    
    public Playlist() {
        
    }

    public Song getPlaying() {
        return playing;
    }
    
    
    
    public void addSong(Song song){
        songs.add(song);
        if(playing == null){
            playing = songs.get(0);
        }
    }
    
}
