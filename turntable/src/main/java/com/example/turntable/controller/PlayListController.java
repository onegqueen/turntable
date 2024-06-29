package com.example.turntable.controller;

import com.example.turntable.domain.PlayListStatus;
import com.example.turntable.dto.PlayListDto;
import com.example.turntable.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    // 상태 맵을 정의하여 경로와 PlayListStatus를 매핑
    private static final Map<String, PlayListStatus> statusMap = new HashMap<>();

    static {
        statusMap.put("Daily", PlayListStatus.DAILY);
        statusMap.put("My", PlayListStatus.MY);

    }

    @PostMapping("/{status}")
    public ResponseEntity<Void> savePlayList(@SessionAttribute(name="userId", required = false) Long userId,
                                             @RequestBody PlayListDto playListDto,
                                             @PathVariable String status) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PlayListStatus playListStatus = statusMap.get(status);
        if (playListStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        playListService.savePlayList(userId, playListDto, playListStatus);
        return ResponseEntity.ok().build();
    }
}



