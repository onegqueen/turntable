package com.example.turntable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequestDto {
    private String name;
    private String nickname;
    private String password;
    private MultipartFile bgImg;
}
