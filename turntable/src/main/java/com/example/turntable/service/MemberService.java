package com.example.turntable.service;

import com.example.turntable.domain.Member;
import com.example.turntable.repository.MemberRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final NcpService ncpService;

    @Transactional
    public void create(String memberId,String nickname, String password, MultipartFile bgImg) throws IOException {
        final Member member = Member.builder()
            .name(memberId)
            .nickname(nickname)
            .password(password)
            .backGroundImage(ncpService.uploadFile(bgImg))
            .build();
        memberRepository.save(member);
    }



}
