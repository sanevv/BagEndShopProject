package com.github.mymvcspring.repository.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class MyUser {
    @Id
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String addressReference;
    private String gender;
    private LocalDate birthDay;
    private long coin;
    private long point;
    private LocalDateTime registerAt;
    private LocalDateTime lastChangeAt;
    private Boolean status;
    private Boolean dormancy;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<LoginHistory> loginHistories = new ArrayList<>();

    public void dormantProcessing(){
        this.dormancy = true;
    }
}
