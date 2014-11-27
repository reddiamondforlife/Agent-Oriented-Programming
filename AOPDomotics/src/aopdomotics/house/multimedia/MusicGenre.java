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
public class MusicGenre {
    
    MusicGenreEnum genre;
    
    Playlist playlist;

    public MusicGenre(MusicGenreEnum genre) {
        this.genre = genre;
        playlist = new Playlist();
        if(genre == MusicGenreEnum.classical){
            playlist.addSong(new Song("Ludwig van Beethoven","Sumphony No. 5"));
            playlist.addSong(new Song("Antonio Vivaldi","The Four Seasons"));
            playlist.addSong(new Song("Samuel Barber","Adagio for Strings"));
        } else if(genre == MusicGenreEnum.favorite){
            playlist.addSong(new Song("Tove Lo","Habits (Stay High)"));
            playlist.addSong(new Song("Foo Fighters","The Pretenders"));
            playlist.addSong(new Song("Disclosure","January"));
        } else if(genre == MusicGenreEnum.ambiance){
            playlist.addSong(new Song("Rameses B","Drift away"));
            playlist.addSong(new Song("Netsky","No beginning"));
            playlist.addSong(new Song("Stan SB","Anyone out there"));
        }
    }

    public Playlist getPlaylist() {
        return playlist;
    }
    
    
    
    
}
