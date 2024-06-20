package com.example.turntable.spotify.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendRequestDto {
    private List<String> seedArtists;
    private List<String> seedTracks;
    private List<String> seedGenres;
}
