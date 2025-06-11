package com.github.mymvcspring.repository.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser user;

    private LocalDateTime loginDate;
    private String clientIp;

    // historyId를 제외한 정적 팩토리 메서드
    public static LoginHistory of(MyUser user, String clientIp) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.user = user;
        loginHistory.clientIp = clientIp;
        return loginHistory;
    }



}
