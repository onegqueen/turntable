package com.example.turntable.service;

import com.example.turntable.domain.PlayList;
import com.example.turntable.domain.PlayListSong;
import com.example.turntable.domain.Member;
import com.example.turntable.domain.PlayListStatus;
import com.example.turntable.dto.PlayListDto;
// import com.example.turntable.dto.PlayListSongDto;
import com.example.turntable.repository.PlayListRepository;
import com.example.turntable.repository.MemberRepository;
import com.example.turntable.repository.PlayListSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final MemberRepository memberRepository;
    private final PlayListSongRepository playListSongRepository;

    @Transactional
    public PlayList savePlayList(String username, PlayListDto playListDto) {
        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        PlayList playList = PlayList.builder()
                .member(member)
                .name(playListDto.getName())
                .date(LocalDate.now())
                // 로직 추가에따라 수정해줘야 함!
                .state(PlayListStatus.DAILY)
                .build();

        playListRepository.save(playList);

        List<PlayListSong> playListSongs = playListDto.getTracks().stream()
                .map(trackDto -> PlayListSong.builder()
                        .playlist(playList)
                        .spotifySongId(trackDto.getSpotifySongId())
                        .build())
                .collect(Collectors.toList());

        playListSongRepository.saveAll(playListSongs);

        return playList;
    }
}
