package com.example.turntable.auth;


import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class AuthController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto signupRequestDto, RedirectAttributes redirectAttributes) throws IOException {
        if(memberService.create(signupRequestDto)!=null){
            return "redirect:/login";
        }
        else{
            return "회원가입 실패";
        }
    }

    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = memberService.getUserIdByName(username);
        session.setAttribute("username", username);
        session.setAttribute("userId",userId);
        return "redirect:/main?page" +
                "OwnerId="+userId;
    }

    @PostMapping("/withdraw")
    public String deleteUser(HttpSession session) throws Exception {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // 세션에 사용자 ID가 없는 경우 로그인 페이지로 리다이렉트
        }

        boolean res = memberService.deleteUserInfo(userId);

        if (res) {
            session.invalidate(); // 탈퇴 성공 시 세션 무효화
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        } else {
            return "redirect:/error"; // 탈퇴 실패 시 에러 페이지로 리다이렉트
        }
    }
}
