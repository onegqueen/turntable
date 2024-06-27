package com.example.turntable.service;

import com.example.turntable.domain.DailyComment;
import com.example.turntable.domain.Member;
import com.example.turntable.dto.WriteDailyCommentDto;
import com.example.turntable.repository.DailyCommentRepository;
import com.example.turntable.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private DailyCommentRepository dailycommentRepository;
    private MemberRepository memberRepository;

    public void create(WriteDailyCommentDto writeDailyCommentDto, Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        final DailyComment dailyComment = DailyComment.builder()
            .comment(writeDailyCommentDto.getComment())
            .createdAt(LocalDateTime.parse(writeDailyCommentDto.getDate()))
            .spotifySongId(writeDailyCommentDto.getSpotifySongId())
            .member(memberOptional.get())
            .build();
        dailycommentRepository.save(dailyComment);
    }


}
