package com.github.mymvcspring.repository.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String birthDay;
    private long coin;
    private long point;
    private LocalDate registerAt;
    private LocalDate lastChangeAt;
    private Boolean status;
    private Boolean dormancy;


}
