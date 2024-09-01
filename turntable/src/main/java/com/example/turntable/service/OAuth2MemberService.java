package com.example.turntable.service;

import com.example.turntable.auth.CustomOAuthDetails;
import com.example.turntable.auth.KakaoMemberInfo;
import com.example.turntable.auth.OAuth2MemberInfo;
import com.example.turntable.domain.Member;
import com.example.turntable.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2MemberInfo memberInfo = null;
        System.out.println(oAuth2User.getAttributes());
        System.out.println(userRequest.getClientRegistration().getRegistrationId());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId = " + registrationId);
        if (registrationId.equals("kakao")) {
            memberInfo = new KakaoMemberInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("로그인 실패");
        }
        String provider = memberInfo.getProvider();
        String providerId = memberInfo.getProviderId();
        String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String nickname = memberInfo.getNickname();
        String profile_image = memberInfo.getProfileImageUrl();
        System.out.println(oAuth2User.getAttributes());
        Optional<Member> findMember = memberRepository.findByName(username);
        Member member = null;

        if (findMember.isEmpty()) { //찾지 못했다면
            member = Member.builder()
                    .name(username)
                    .nickname(nickname)
                    .backGroundImage(profile_image)
                    .build();
        memberRepository.save(member);
        }
        else{
            member=findMember.get();
        }
        return new CustomOAuthDetails(member, oAuth2User.getAttributes());
    }
}
