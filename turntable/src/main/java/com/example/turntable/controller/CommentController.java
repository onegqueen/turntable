package com.example.turntable.controller;

import com.example.turntable.dto.WriteDailyCommentDto;
import com.example.turntable.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public String writeComment(@RequestBody WriteDailyCommentDto writeDailyCommentDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("userId");
        commentService.create(writeDailyCommentDto,memberId);
        return "redirect:/comment";
    }

}
