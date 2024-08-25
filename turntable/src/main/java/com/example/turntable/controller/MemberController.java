package com.example.turntable.controller;

import com.example.turntable.dto.MemberInfoResponseDto;
import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.service.MemberService;
import com.example.turntable.service.PlayListService;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PlayListService playListService;

    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Map<String,Boolean>> checkUsername(@RequestParam("name") String username){
        boolean available = memberService.isNotUsernameExist(username);
        Map<String,Boolean> response = new HashMap<>();
        response.put("available", available);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username")
    @ResponseBody
    public MemberInfoResponseDto findUserById(@RequestParam Long memberId){
        return memberService.getUserById(memberId);
    }

    @GetMapping("/users/all")
    @ResponseBody
    public Page<MemberInfoResponseDto> findAllUsers(@RequestParam int page){
        return memberService.getAllUsersInfo(page);
    }

    @GetMapping("/users/nickname")
    @ResponseBody
    public Page<MemberInfoResponseDto> findAllUsersByName(@RequestParam int page, @RequestParam String name){
        return memberService.getAllUsersInfoByNickname(page, name);
    }

    @GetMapping("/user/playlist-count")
    @ResponseBody
    public ResponseEntity<Integer> findAllUsersByName(@RequestParam Long userId){
        int playlistCount = playListService.getPlaylistCount(userId);
        return ResponseEntity.ok(playlistCount);
    }

    @PostMapping("user/change-nickname")
    public String changeNickname(@RequestBody String newNickname, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        memberService.changeNickName(userId, newNickname);
        return "redirect:/main?pageOwnerId=" + userId;
    }

    @PostMapping("user/change-bgimg")
    public String changeBgImg(@RequestBody MultipartFile newBgImg, HttpSession session)
        throws IOException {
        Long userId = (Long) session.getAttribute("userId");
        memberService.changeBgImg(userId,newBgImg);
        return "redirect:/main?pageOwnerId="+userId;
    }

    @GetMapping("/imgurl")
    public ResponseEntity<String> getUserImageUrl(@RequestParam Long pageOwnerId, HttpSession session) {
        String userBgimg = memberService.getUserBgImg(pageOwnerId);
        return ResponseEntity.ok(userBgimg);
    }
}
