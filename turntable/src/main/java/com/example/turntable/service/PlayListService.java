package com.example.turntable.service;

import com.example.turntable.domain.PlayList;
import com.example.turntable.domain.PlayListSong;
import com.example.turntable.domain.Member;
import com.example.turntable.domain.PlayListStatus;
import com.example.turntable.dto.PlayListDto;
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
    public PlayList savePlayList(Long userId, PlayListDto playListDto, PlayListStatus playListStatus) {
        Member member = getMemberById(userId);
        PlayList playList = createPlayList(member, playListDto.getName(), playListStatus);
        List<PlayListSong> playListSongs = createPlayListSongs(playList, playListDto);

        savePlayListAndSongs(playList, playListSongs);

        return playList;
    }

    private Member getMemberById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
    }

    private PlayList createPlayList(Member member, String name, PlayListStatus playListStatus) {
        return PlayList.builder()
                .member(member)
                .name(name)
                .date(LocalDate.now())
                .state(playListStatus)
                .build();
    }

    private List<PlayListSong> createPlayListSongs(PlayList playList, PlayListDto playListDto) {
        return playListDto.getTracks().stream()
                .map(trackDto -> PlayListSong.builder()
                        .playlist(playList)
                        .spotifySongId(trackDto.getSpotifySongId())
                        .build())
                .collect(Collectors.toList());
    }

    private void savePlayListAndSongs(PlayList playList, List<PlayListSong> playListSongs) {
        playListRepository.save(playList);
        playListSongRepository.saveAll(playListSongs);
    }

}
