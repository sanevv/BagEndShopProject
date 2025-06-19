package com.github.semiprojectshop.repository.sihu.notice;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser myUser;
    private String title;
    private String contents;
    private String thumbnail;
    private LocalDateTime createdAt;
}
