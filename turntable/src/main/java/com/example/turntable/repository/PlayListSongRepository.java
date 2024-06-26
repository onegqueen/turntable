package com.example.turntable.repository;

import com.example.turntable.domain.PlayListSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListSongRepository extends JpaRepository<PlayListSong, Long> {
}
