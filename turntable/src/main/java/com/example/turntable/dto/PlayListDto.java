package com.example.turntable.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayListDto {
    private String name;
    private List<PlayListSongDto> tracks;
}
