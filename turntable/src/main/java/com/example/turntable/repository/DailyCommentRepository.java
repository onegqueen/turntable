package com.example.turntable.repository;

import com.example.turntable.domain.DailyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyCommentRepository  extends JpaRepository<DailyComment, Long> {

}
