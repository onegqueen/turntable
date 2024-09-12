package com.example.turntable.controller;

import com.example.turntable.dto.MemberInfoResponseDto;
import com.example.turntable.service.MemberService;
import com.example.turntable.service.PlayListService;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class MemberController {
	private final MemberService memberService;
	private final PlayListService playListService;

	@GetMapping("/check-username")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam("name") String username) {
		Map<String, Boolean> response = new HashMap<>();
		response.put("available", memberService.isNotUsernameExist(username));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/username")
	@ResponseBody
	public ResponseEntity<MemberInfoResponseDto> findUserById(@RequestParam("memberId") Long memberId) {
		return ResponseEntity.ok(memberService.getUserById(memberId));
	}

	@GetMapping("/users/all")
	@ResponseBody
	public ResponseEntity<Page<MemberInfoResponseDto>> findAllUsers(@RequestParam("pageNum") int page) {
		return ResponseEntity.ok(memberService.getAllUsersInfo(page));
	}

	@GetMapping("/users/nickname")
	@ResponseBody
	public ResponseEntity<Page<MemberInfoResponseDto>> findAllUsersByName(@RequestParam("pageNum") int page,
		@RequestParam("nickname") String name) {
		return ResponseEntity.ok(memberService.getAllUsersInfoByNickname(page, name));
	}

	@GetMapping("/user/playlist-count")
	@ResponseBody
	public ResponseEntity<Integer> getUserPlaylistCount(@RequestParam Long userId) {
		return ResponseEntity.ok(playListService.getPlaylistCount(userId));
	}

	@PostMapping("user/change-nickname")
	public ResponseEntity<MemberInfoResponseDto> changeNickname(@RequestBody String newNickname, HttpSession session) {
		Long userId = (Long)session.getAttribute("userId");
		return ResponseEntity.ok(memberService.changeNickName(userId, newNickname));
	}

	@PostMapping("user/change-bgimg")
	public ResponseEntity<String> changeBgImg(@RequestBody MultipartFile newBgImg, HttpSession session)
		throws IOException {
		Long userId = (Long)session.getAttribute("userId");
		return ResponseEntity.ok(memberService.changeBgImg(userId, newBgImg));
	}

	@GetMapping("/imgurl")
	public ResponseEntity<String> getUserImageUrl(@RequestParam Long pageOwnerId, HttpSession session) {
		return ResponseEntity.ok(memberService.getUserBgImg(pageOwnerId));
	}
}
