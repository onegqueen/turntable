package com.example.turntable.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;


public class createToken {

    private static final String clientId = "278b6e24bdf249e7b23f635ecbc97b40";
    private static final String clientSecret = "32375966937a46ad925e6d381d18afd6";

    private static SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
        .setClientSecret(clientSecret).build();

    public static String accesstoken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "error";
        }
    }
}