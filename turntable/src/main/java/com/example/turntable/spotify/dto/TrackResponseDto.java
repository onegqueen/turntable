package com.example.turntable.spotify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackResponseDto {
    private String id;
    private String name;
    private String artist;
    private String albumName;
}
