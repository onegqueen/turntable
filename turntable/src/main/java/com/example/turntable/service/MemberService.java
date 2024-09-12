package com.example.turntable.service;

import com.example.turntable.domain.Member;
import com.example.turntable.dto.MemberInfoResponseDto;
import com.example.turntable.dto.SignupRequestDto;
import com.example.turntable.exception.CustomErrorCode;
import com.example.turntable.exception.DuplicatedUsernameException;
import com.example.turntable.repository.MemberRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;
    private final PlayListService playListService;
    private final CommentService commentService;

    @Transactional
    public Member create(SignupRequestDto signupRequestDto) throws IOException {
        Optional<Member> existingUser = memberRepository.findByName(signupRequestDto.getName());
        if (existingUser.isPresent()) {
            return null;
        }

        final Member member = Member.builder()
            .name(signupRequestDto.getName())
            .nickname(signupRequestDto.getNickname())
            .password(passwordEncoder.encode(signupRequestDto.getPassword()))
            .backGroundImage(s3Uploader.upload(signupRequestDto.getBgImg()))
            .build();

        return memberRepository.save(member);
    }

    public boolean deleteUserInfo(Long userId) throws Exception {
        playListService.deleteAllPlaylistByMemberId(userId);
        commentService.deleteDailyCommentByMemberId(userId);
        commentService.deleteGuestCommentByMemberId(userId);
        deleteUser(userId);
        return true;
    }

    @Transactional
    protected boolean deleteUser(Long userId){
        Optional<Member> member = memberRepository.findById(userId);
        memberRepository.delete(member.get());
        return true;
    }

    public Page<MemberInfoResponseDto> getAllUsersInfo(int page){
        int pageSize = 9;
        PageRequest pageRequest = PageRequest.of(page,pageSize);
        Page<Member> members = memberRepository.findAll(pageRequest);

        return members.map(member -> {
            int playlistCount = playListService.getPlaylistCount(member.getId());
            return MemberInfoResponseDto.of(member,playlistCount);
        }
        );
    }
    public Page<MemberInfoResponseDto> getAllUsersInfoByNickname(int page, String Nickname){
        int pageSize = 9;
        PageRequest pageRequest = PageRequest.of(page,pageSize);
        Page<Member> members = memberRepository.findByNicknameContaining(Nickname,pageRequest);

        return members.map(member -> {
            int playlistCount = playListService.getPlaylistCount(member.getId());
            return MemberInfoResponseDto.of(member,playlistCount);
        });
    }

    public MemberInfoResponseDto getUserById(Long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
           Member member = optionalMember.get();
           int playlistCount = playListService.getPlaylistCount(member.getId());
            return MemberInfoResponseDto.of(member,playlistCount);
        }
        else{
            return null;
        }
    }
    public Long getUserIdByName(String username){
        return memberRepository.findByName(username).map(Member::getId).orElse(null);
    }

    public boolean isNotUsernameExist(String username) {
        return memberRepository.findByName(username).isEmpty();
    }

    public String getUserBgImg(Long username) {
        Optional<Member> member = memberRepository.findById(username);
        return member.get().getBackGroundImage();
    }

    @Transactional
    public MemberInfoResponseDto changeNickName(Long userId, String newNickName) {
        Member member = memberRepository.findById(userId).orElse(null);
        if (isNotUsernameExist(newNickName)) {
            member.changeNickname(newNickName);
            memberRepository.save(member);
            return getUserById(userId);
        }
        else{
            throw new DuplicatedUsernameException(CustomErrorCode.ALREADY_EXIST_USERNAME);
        }
    }


    @Transactional
    public String changeBgImg(Long userId, MultipartFile newBgImg) throws IOException {
        Member member = memberRepository.findById(userId).orElse(null);
        String bgImgUrl = s3Uploader.upload(newBgImg);
        member.changeBackGroundImage(bgImgUrl);
        return bgImgUrl;
    }
}
