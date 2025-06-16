package com.github.mymvcspring.repository.user;

import com.github.mymvcspring.web.dto.auth.UserInfoEditRequest;
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
    public void updateCoinAndPoint(long coin, long point) {
        this.coin += coin;
        this.point += point;
    }
    public void updateUserInfo(UserInfoEditRequest request){
        this.userName = request.getUserName();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
        this.password = request.getPassword();
        this.zipCode = request.getZipCode();
        this.address = request.getAddress();
        this.addressDetail = request.getAddressDetail();
        this.addressReference = request.getAddressReference();
    }
}
