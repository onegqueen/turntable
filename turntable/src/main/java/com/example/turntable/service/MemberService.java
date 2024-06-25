package com.example.turntable.service;

import com.example.turntable.domain.Member;
import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.repository.MemberRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final NcpService ncpService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(SignupRequestDto signupRequestDto) throws IOException {
        final Member member = Member.builder()
            .name(signupRequestDto.getName())
            .nickname(signupRequestDto.getNickname())
            .password(passwordEncoder.encode(signupRequestDto.getPassword()))
            .backGroundImage(ncpService.uploadFile(signupRequestDto.getBgImg()))
            .build();
        memberRepository.save(member);
    }
}
