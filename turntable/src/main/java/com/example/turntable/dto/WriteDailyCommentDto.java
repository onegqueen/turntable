package com.example.turntable.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WriteDailyCommentDto {
    private String comment;
    private String date;
    private String spotifySongId;
}
