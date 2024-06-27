package com.example.turntable.repository;

import com.example.turntable.domain.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
}
