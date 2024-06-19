package com.example.turntable.spotify;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService {
    createToken createToken = new createToken();
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setAccessToken(createToken.accessToken())
        .build();

}
