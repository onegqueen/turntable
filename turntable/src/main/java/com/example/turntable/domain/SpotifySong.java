package com.example.turntable.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class SpotifySong {
    @Id @GeneratedValue
    @Column(name = "spotify_song_id")
    private long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "artist" ,nullable = false)
    private String artist;


}
