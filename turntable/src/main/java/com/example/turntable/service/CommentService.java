package com.example.turntable.service;

import com.example.turntable.domain.DailyComment;
import com.example.turntable.domain.Member;
import com.example.turntable.dto.WriteDailyCommentDto;
import com.example.turntable.repository.DailyCommentRepository;
import com.example.turntable.repository.MemberRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final DailyCommentRepository dailycommentRepository;
    private final MemberRepository memberRepository;

    public void create(WriteDailyCommentDto writeDailyCommentDto, Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        final DailyComment dailyComment = DailyComment.builder()
            .comment(writeDailyCommentDto.getComment())
            .createdAt(LocalDateTime.parse(writeDailyCommentDto.getDate(),formatter))
            .spotifySongId(writeDailyCommentDto.getSpotifySongId())
            .member(memberOptional.get())
            .build();
        dailycommentRepository.save(dailyComment);
    }


}
