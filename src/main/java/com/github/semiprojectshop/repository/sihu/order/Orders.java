package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MyUser myUser;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private orderStatus status;

    public enum orderStatus {
        DELIVERY, CANCEL, COMPLETED
    }
}
