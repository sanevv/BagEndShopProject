package com.github.semiprojectshop.web.sihu.dto.member;

import com.github.semiprojectshop.config.BeanProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MemberDetailResponse {
    private String userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String address;
    private String gender;
    private LocalDate birthDay;
    private int age;
    private long coin;
    private long point;
    private LocalDate registerAt;

    @Getter(AccessLevel.NONE)
    private LocalDateTime registerDateTime;

    public void decryptionAndRegisterAtConvert(){
        if (this.email != null) {
            this.email = BeanProperties.decryptNotException(this.email);
        }
        if( this.phoneNumber != null) {
            this.phoneNumber = BeanProperties.decryptNotException(this.phoneNumber);
        }
        if (this.registerDateTime != null) {
            this.registerAt = this.registerDateTime.toLocalDate();
        }
    }
    public void settingAge(){
        if (this.birthDay == null)
            return;
        LocalDate today = LocalDate.now();
        int age = today.getYear() - this.birthDay.getYear();
        if (today.isBefore(this.birthDay.withYear(today.getYear())))
            age--;

        this.age = age;
    }
}
