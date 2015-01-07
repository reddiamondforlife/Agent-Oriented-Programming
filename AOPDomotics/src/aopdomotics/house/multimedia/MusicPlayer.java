/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aopdomotics.house.multimedia;

import java.util.ArrayList;

/**
 *
 * @author Daan
 */
public class MusicPlayer {
    
    MusicGenre classicalMusic;
    MusicGenre favoriteMusic;
    MusicGenre ambianceMusic;
    
    MusicGenre currentMusic;
    boolean play;
    static ArrayList<Song> songs = new ArrayList<>();

    public MusicPlayer(boolean play) {
        this.play = play;
        initSongs();
        currentMusic = favoriteMusic;
        setMusicGenre(MusicGenreEnum.favorite);
    }
    
    private void initSongs(){
        classicalMusic = new MusicGenre(MusicGenreEnum.classical);
        favoriteMusic = new MusicGenre(MusicGenreEnum.favorite);
        ambianceMusic = new MusicGenre(MusicGenreEnum.ambiance);
    }
    
    public void setMood(int mood){
        if(mood > 75){
            setMusicGenre(MusicGenreEnum.classical);
        } else if(mood > 25){
            setMusicGenre(MusicGenreEnum.favorite);
        } else if(mood > 0){
            setMusicGenre(MusicGenreEnum.ambiance);
        }
    }
    
    public void setMusicGenre(MusicGenreEnum genre){
        if(currentMusic.genre == genre){
            
        } else {
            currentMusic = getNewPlaylist(genre);
            if(this.play){
                System.out.println("Now Playing " + currentMusic.genre.name()+ " " + currentMusic.getPlaylist().getPlaying());
            }
        }
        
    }
    
    private MusicGenre getNewPlaylist(MusicGenreEnum genre){
        if(genre == MusicGenreEnum.ambiance){
            return ambianceMusic;
        } else if(genre == MusicGenreEnum.classical){
            return classicalMusic;
        } else if(genre == MusicGenreEnum.favorite){
            return favoriteMusic;
        }
        return currentMusic;
    }
    
    public void turnOn(){
        this.play = true;
        System.out.println("Now Playing " + currentMusic.genre.name()+ " " + currentMusic.getPlaylist().getPlaying());
    }
    
    public void turnOff(){
        this.play = false;
        System.out.println("Stopped playing music");
    }
}
