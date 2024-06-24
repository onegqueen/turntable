package com.example.turntable.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "playlist_song")
public class PlayListSong {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_song_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlayList playlist;

    @ManyToOne
    @JoinColumn(name = "spotify_song_id")
    private SpotifySong spotifySong;


}
