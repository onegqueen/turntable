package com.example.turntable.spotify.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackResponseDto {
    private String id;
    private String name;
    private List<String> artists;
    private String albumName;
}
