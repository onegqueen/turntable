package com.example.turntable.spotify;

import com.wrapper.spotify.SpotifyApi;

import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyApiConfig {

    private String clientId = "278b6e24bdf249e7b23f635ecbc97b40";
    private String clientSecret = "32375966937a46ad925e6d381d18afd6";

    @Bean
    public SpotifyApi spotifyApi() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve access token", e);
        }

        return spotifyApi;
    }
}