package com.example.turntable.repository;

import com.example.turntable.domain.DailyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyCommentRepository  extends JpaRepository<DailyComment, Long> {

}
