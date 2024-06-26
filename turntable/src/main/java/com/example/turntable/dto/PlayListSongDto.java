package com.example.turntable.dto;

import com.example.turntable.spotify.dto.TrackResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayListSongDto {
    private String spotifySongId;

    public static PlayListSongDto fromTrackResponseDto(TrackResponseDto trackResponseDto) {
        PlayListSongDto playListSongDto = new PlayListSongDto();
        playListSongDto.setSpotifySongId(trackResponseDto.getId());
        return playListSongDto;
    }
}
