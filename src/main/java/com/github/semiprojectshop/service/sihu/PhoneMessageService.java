package com.github.semiprojectshop.service.sihu;

import com.github.semiprojectshop.config.cool.CoolSmsProperties;
import com.github.semiprojectshop.web.sihu.dto.sms.MessageRequest;
import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class PhoneMessageService {
    private final EmailVerifyService emailVerifyService;
    private final DefaultMessageService messageService;
    private final String phoneNumber;
    public PhoneMessageService(CoolSmsProperties coolSmsProperties, EmailVerifyService emailVerifyService) {
        this.phoneNumber = coolSmsProperties.getPhoneNumber();
        this.emailVerifyService = emailVerifyService;
        this.messageService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain()
        );
    }

    public void sendMessage(MessageRequest messageRequest) {
        HttpSession session = getSession();
        String to = messageRequest.getPhoneNumber().replace(" ", "").replace("-", "");
        Message message = new Message();
        message.setFrom(phoneNumber);
        message.setTo(to);
        String verifyCode = emailVerifyService.generateRandomCode();
        message.setText(verifyCode + " 뿌잉뿌잉쀼?❤️");

        // 세션에 인증 코드 저장
        emailVerifyService.saveSessionCode(session, to, verifyCode);
        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            // Java LocalDateTime, Instant 기준, Kolintx의 datetime 내 Instant 타입을 넣어도 동작합니다!
            LocalDateTime reservedDateTime = LocalDateTime.now();
            ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(reservedDateTime);
            Instant instant = reservedDateTime.toInstant(zoneOffset);

            messageService.send(message, instant);
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }
    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(attr).getRequest().getSession();
    }

    public boolean verifyMessage(String phoneNumber, String code) {
        HttpSession session = getSession();
        String sessionCode = (String) session.getAttribute(phoneNumber + "_code");
        Long expireAt = (Long) session.getAttribute(phoneNumber + "_expireAt");

        if (sessionCode == null || expireAt == null || !sessionCode.equals(code)) {
            return false; // 세션에 인증 코드가 없거나 만료 시간 정보가 없거나 인증 코드가 일치하지 않음
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(expireAt), ZoneId.systemDefault());

        if (now.isAfter(expirationTime)) {//만료 됏으면 세션에서 제거
            session.removeAttribute(phoneNumber + "_code");
            session.removeAttribute(phoneNumber + "_expireAt");
            return false; // 인증 코드가 만료됨
        }

        session.removeAttribute(phoneNumber + "_code");
        session.removeAttribute(phoneNumber + "_expireAt");
        return true; // 인증 코드 일치 여부 확인
    }
}
