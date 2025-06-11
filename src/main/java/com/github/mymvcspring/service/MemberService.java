package com.github.mymvcspring.service;

import com.github.mymvcspring.config.encryption.AES256;
import com.github.mymvcspring.config.encryption.SecretMyKey;
import com.github.mymvcspring.config.encryption.Sha256;
import com.github.mymvcspring.repository.user.LoginHistory;
import com.github.mymvcspring.repository.user.LoginHistoryRepository;
import com.github.mymvcspring.repository.user.MyUser;
import com.github.mymvcspring.repository.user.MyUserRepository;
import com.github.mymvcspring.service.exceptions.CustomMyException;
import com.github.mymvcspring.web.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MyUserRepository myUserRepository;
    private final LoginHistoryRepository loginHistoryRepository;

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

    // !!!! session(세션) 이라는 저장소에 로그인 되어진 loginuser 을 저장시켜두어야 한다.!!!! //
    // session(세션) 이란 ? WAS 컴퓨터의 메모리(RAM)의 일부분을 사용하는 것으로 접속한 클라이언트 컴퓨터에서 보내온 정보를 저장하는 용도로 쓰인다.
    // 클라이언트 컴퓨터가 WAS 컴퓨터에 웹으로 접속을 하기만 하면 무조건 자동적으로 WAS 컴퓨터의 메모리(RAM)의 일부분에 session 이 생성되어진다.
    // session 은 클라이언트 컴퓨터 웹브라우저당 1개씩 생성되어진다.
    // 예를 들면 클라이언트 컴퓨터가 크롬웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 session이 하나 생성되어지고 ,
    // 또 이어서 동일한 클라이언트 컴퓨터가 엣지웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 또 하나의 새로운 session이 생성되어진다.
      /*
            -------------
            | 클라이언트   |             ---------------------
            | A 웹브라우저 | ----------- |   WAS 서버         |
            -------------             |                   |
                                      |  RAM (A session)  |
            --------------            |      (B session)  |
            | 클라이언트     |           |                   |
            | B 웹브라우저   | ----------|                   |
            ---------------           --------------------

        !!!! 세션(session)이라는 저장 영역에 loginuser 를 저장시켜두면
             Command.properties 파일에 기술된 모든 클래스 및 모든 JSP 페이지(파일)에서
             세션(session)에 저장되어진 loginuser 정보를 사용할 수 있게 된다. !!!!
             그러므로 어떤 정보를 여러 클래스 또는 여러 jsp 페이지에서 공통적으로 사용하고자 한다라면
             세션(session)에 저장해야 한다.!!!!
       */
    @Transactional
    public LoginResponse loginLogic(LoginRequest loginRequest, HttpSession session, String loginIp) {
        String userId = loginRequest.getUserId();
        String password = Sha256.encrypt(loginRequest.getPassword());
        MyUser myUser = myUserRepository.findById(userId).orElse(null);

        if (myUser == null)
            return LoginResponse.of(userId, "존재하지 않는 아이디입니다.", false);
        else if (!myUser.getPassword().equals(password))
            return LoginResponse.of(userId, "비밀번호가 일치하지 않습니다.", false);
        boolean isChangePw = false;
        try {
            validateUser(myUser);
        } catch (IllegalAccessException ex) {
            isChangePw = true;
        } catch (Exception e) {
            return LoginResponse.of(userId, e.getMessage(), false);
        }


        //성공
        // 세션에 로그인된 사용자 정보를 저장
        // session(세션)에 로그인 되어진 사용자 정보인 myUser 를 키이름을 "loginUser" 으로 저장시켜두는 것이다.

        LoginHistory loginHistory = LoginHistory.of(myUser, loginIp);
        myUser.getLoginHistories().add(loginHistory);
        session.setAttribute("loginUser", myUser);
        return isChangePw ?
                LoginResponse.of(userId, myUser.getUserName() + "님 환영합니다.", true).needToChangePassword() :
                LoginResponse.of(userId, myUser.getUserName() + "님 환영합니다.", true);
    }

    private void validateUser(MyUser myUser) throws IllegalAccessException {
        LocalDateTime now = LocalDateTime.now();
        if (myUser.getStatus())
            throw new RuntimeException("이미 탈퇴한 회원입니다.");
        if (myUser.getDormancy())
            throw new RuntimeException("휴면회원입니다. 관리자에게 문의하세요.");
        LoginHistory loginHistory = loginHistoryRepository.findByLastLogin(myUser.getUserId());
        if (loginHistory != null && loginHistory.getLoginDate().isBefore(now.minusMonths(6))) {
            myUser.dormantProcessing();
            throw new RuntimeException("6개월 이상 로그인하지 않아 휴면처리 됩니다. 관리자에게 문의하세요.");
        }
        if (myUser.getLastChangeAt() != null && myUser.getLastChangeAt().isBefore(now.minusMonths(3)))
            throw new IllegalAccessException("3개월 이상 비밀번호 변경을 하지 않은 회원입니다. 비밀번호 찾기를 통해 비밀번호를 변경 후 이용해주세요.");

    }


    @Transactional(readOnly = true)
    public LoginHistory getLastLoginHistory(String userId) {
        return loginHistoryRepository.findByLastLogin(userId);
    }

    @Transactional(readOnly = true)
    public String findUserInfoLogic(FindUserRequest emailAndName) {
        String encryptionEmail = emailEncrypt(emailAndName.getEmail());
        return myUserRepository.findUserIdByEmailAndName(
                encryptionEmail, emailAndName.getName()
        );
    }

    private String emailEncrypt(String email) {
        try {
            return aes256.encrypt(email);
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            throw new RuntimeException("AES256 인코딩 실패", e);
        }
    }

    @Transactional(readOnly = true)
    public boolean searchUserExists(FindUserRequest emailAndName) {
        String encryptedEmail = emailEncrypt(emailAndName.getEmail());
        return myUserRepository.existsByEmailAndUserId(encryptedEmail, emailAndName.getUserId());
    }

    @Transactional
    public void changePassword(PwChangeRequest pwChangeRequest) {
        long result = myUserRepository.updatePasswordByUserId(
                pwChangeRequest.getUserId(),
                Sha256.encrypt(pwChangeRequest.getPassword())
        );
        if(result == 1) return;
        throw CustomMyException.fromMessage("비밀번호 변경에 실패했습니다. 아이디와 비밀번호를 확인해주세요. 관리자에게 문의해주세요.");
    }
}
