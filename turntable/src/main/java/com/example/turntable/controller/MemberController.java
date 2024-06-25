package com.example.turntable.controller;

import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.service.MemberService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) throws IOException {
        memberService.create(signupRequestDto);
        return "redirect:/main";
    }

}
