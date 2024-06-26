package com.example.turntable.controller;

import com.example.turntable.dto.PlayListDto;
import com.example.turntable.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> savePlayList(@PathVariable Long memberId, @RequestBody PlayListDto playListDto) {
        playListService.savePlayList(memberId, playListDto);
        return ResponseEntity.ok().build();
    }
}
