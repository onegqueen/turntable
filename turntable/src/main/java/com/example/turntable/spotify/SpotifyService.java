package com.example.turntable.spotify;

import com.example.turntable.spotify.dto.TrackResponseDto;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    @Autowired
    public SpotifyService(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    public List<TrackResponseDto> searchTracks(String keyword){
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(keyword).build();

        try {
            Paging<Track> trackPaging = searchTracksRequest.execute();
            Track[] tracks = trackPaging.getItems();

            return Arrays.stream(tracks).map(track -> {
                TrackResponseDto trackResponseDto = new TrackResponseDto();
                trackResponseDto.setId(track.getId());
                trackResponseDto.setName(track.getName());

                trackResponseDto.setArtists(Arrays.stream(track.getArtists()).map(artist->{
                    return artist.getName();
                    }).collect(Collectors.toList()));

                trackResponseDto.setAlbumName(track.getAlbum().getName());
                return trackResponseDto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to search tracks", e);
        }
    }
}