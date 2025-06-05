package com.github.mymvcspring.service;

import com.github.mymvcspring.config.encryption.AES256;
import com.github.mymvcspring.config.encryption.SecretMyKey;
import com.github.mymvcspring.config.encryption.Sha256;
import com.github.mymvcspring.repository.user.MyUser;
import com.github.mymvcspring.repository.user.MyUserRepository;
import com.github.mymvcspring.web.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MyUserRepository myUserRepository;

    private final AES256 aes256;

    @Transactional(readOnly = true)
    public boolean duplicateCheckId(String userId) {
        return myUserRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public boolean duplicateCheckEmail(String email) {
        try {
            String encryptEmail = aes256.encrypt(email);
            return myUserRepository.existsByEmail(encryptEmail);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional(readOnly = true)
    public MyUser getUserById(String userId) {
        return myUserRepository.findById(userId).orElse(null);
    }

    public boolean checkId(String userId) {
        return myUserRepository.existsById(userId);
    }
    @Transactional
    public void registerLogic(SignUpRequest signUpRequest) {
        String pw = signUpRequest.getPassword();
        String encryptionPw = Sha256.encrypt(pw);//단방향 암호화
        try {
            String encryptionEmail = aes256.encrypt(signUpRequest.getEmail());
            String encryptionPhone = aes256.encrypt(signUpRequest.getPhoneNumber());

            MyUser myUser = MyUser.builder()
                    .userId(signUpRequest.getUserId())
                    .password(encryptionPw)
                    .email(encryptionEmail)
                    .phoneNumber(encryptionPhone)
                    .userName(signUpRequest.getName())
                    .zipCode(signUpRequest.getZipCode())
                    .address(signUpRequest.getAddress())
                    .addressDetail(signUpRequest.getAddressDetail())
                    .addressReference(signUpRequest.getAddressReference())
                    .gender(signUpRequest.getGender())
                    .birthDay(signUpRequest.getBirthDay())
                    .build();
            myUserRepository.save(myUser);

        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            throw new RuntimeException("AES256 인코딩 실패", e);
        }


    }
}
