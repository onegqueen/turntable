package com.example.turntable.controller;

import com.example.turntable.service.NcpService;
import java.io.IOException;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ncpController {

    @Autowired
    private NcpService ncpService;

    @GetMapping("image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            byte[] imageData = ncpService.getImgObject(imageName);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+imageName+"\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
        }catch (IOException e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/imgurl")
    public ResponseEntity<String> getUserImageUrl(String imageUrl) {
        return ResponseEntity.ok(imageUrl);
    }
}


