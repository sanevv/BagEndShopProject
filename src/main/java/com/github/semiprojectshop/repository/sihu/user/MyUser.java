package com.github.semiprojectshop.repository.sihu.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@DynamicInsert
@Getter
@Table(name = "my_user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Long zipCode;
    private String address;
    private String addressDetails;
    private LocalDateTime registerAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles roles;


    public static MyUser onlyId(Long userId) {
        MyUser myUser = new MyUser();
        myUser.userId = userId;
        return myUser;
    }
}
