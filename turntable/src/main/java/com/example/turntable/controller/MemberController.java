package com.example.turntable.controller;

import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.service.MemberService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto signupRequestDto, RedirectAttributes redirectAttributes) throws IOException {
        if (memberService.create(signupRequestDto)){
            return "redirect:/login";
        }
        else{
            return "회원가입 실패";
        }
    }

    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Map<String,Boolean>> checkUsername(@RequestParam("username") String username){
        boolean available = memberService.isUsernameExist(username);
        Map<String,Boolean> response = new HashMap<>();
        response.put("available", available);
        return ResponseEntity.ok(response);
    }
}
