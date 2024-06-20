package com.example.turntable.spotify;

import com.example.turntable.spotify.dto.ArtistResponseDto;
import com.example.turntable.spotify.dto.RecommendRequestDto;
import com.example.turntable.spotify.dto.TrackResponseDto;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import com.wrapper.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ArtistResponseDto>searchArtist(String keyword){
        SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(keyword).build();

        try{
             Paging<Artist> artistPaging = searchArtistsRequest.execute();
             Artist[] artists = artistPaging.getItems();

             return Arrays.stream(artists).map(artist -> {
                 ArtistResponseDto artistResponseDto = new ArtistResponseDto();
                 artistResponseDto.setId(artist.getId());
                 artistResponseDto.setName(artist.getName());
                 return artistResponseDto;
             }).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to search artist", e);
        }
    }

    public List<String> searchGenre(){
        GetAvailableGenreSeedsRequest getAvailableGenreSeedsRequest = spotifyApi.getAvailableGenreSeeds().build();
        try{
            String[] genres = getAvailableGenreSeedsRequest.execute();
            return Arrays.asList(genres);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to search genres", e);
        }
    }

    public List<TrackResponseDto> getRecommends(RecommendRequestDto recommendRequestDto){
        if (recommendRequestDto.getSeedArtists() == null || recommendRequestDto.getSeedGenres() == null || recommendRequestDto.getSeedTracks() == null) {
            throw new IllegalArgumentException("Seed artists, genres, and tracks must not be null");
        }

        String seedArtistsStr = String.join(",", recommendRequestDto.getSeedArtists());
        String seedGenresStr = String.join(",", recommendRequestDto.getSeedGenres());
        String seedTracksStr = String.join(",", recommendRequestDto.getSeedTracks());

        GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
            .seed_artists(seedArtistsStr)
            .seed_genres(seedGenresStr)
            .seed_tracks(seedTracksStr)
            .build();

        System.out.println("Seed Artists: " + recommendRequestDto.getSeedArtists().toString());

        try{
            Recommendations recommendations = getRecommendationsRequest.execute();
            TrackSimplified[] tracks = recommendations.getTracks();
            return getTrackList(tracks);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to search genres", e);
        }

    }

    public List<TrackResponseDto> getTrackList(TrackSimplified[] tracks){
        return Arrays.stream(tracks).map(track -> {
            TrackResponseDto trackResponseDto = new TrackResponseDto();
            trackResponseDto.setId(track.getId());
            trackResponseDto.setName(track.getName());

            trackResponseDto.setArtists(Arrays.stream(track.getArtists()).map(artist -> {
                return artist.getName();
            }).collect(Collectors.toList()));
            return trackResponseDto;
        }).collect(Collectors.toList());
    }
}
