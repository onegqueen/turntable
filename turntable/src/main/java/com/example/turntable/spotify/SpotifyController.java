package com.example.turntable.spotify;

import com.example.turntable.spotify.dto.TrackResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {
    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search")
    public List<TrackResponseDto> searchTracks(@RequestParam String query) {
        return spotifyService.searchTracks(query);
    }
}
