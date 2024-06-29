package com.example.turntable.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GuestComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private DailyComment dailyComment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member visitorMember;

    @Column(nullable = false)
    private String comment;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
