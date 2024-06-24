package com.example.turntable.spotify;

import com.example.turntable.spotify.dto.ArtistResponseDto;
import com.example.turntable.spotify.dto.GenreResponsDto;
import com.example.turntable.spotify.dto.RecommendRequestDto;
import com.example.turntable.spotify.dto.TrackResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search/track")
    public List<TrackResponseDto> searchTracks(@RequestParam String keyword) {
        return spotifyService.searchTracks(keyword);
    }

    @GetMapping("/search/artist")
    public List<ArtistResponseDto> searchTracksByArtist(@RequestParam String keyword) {
        return spotifyService.searchArtist(keyword);
    }

    @GetMapping("/search/genre")
    public List<GenreResponsDto> searchGenres() {
        return spotifyService.searchGenre();
    }

    @PostMapping("/recommendations")
    public List<TrackResponseDto> getRecommendations(@RequestBody RecommendRequestDto recommendationsRequestDto) {
        return spotifyService.getRecommends(recommendationsRequestDto);
    }
}
