package com.github.semiprojectshop.service;

import com.github.semiprojectshop.config.cool.CoolSmsProperties;
import com.github.semiprojectshop.web.sihu.dto.sms.MessageRequest;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class MessageService {

    private final DefaultMessageService messageService;
    public MessageService(CoolSmsProperties coolSmsProperties) {

        this.messageService = NurigoApp.INSTANCE.initialize(
                coolSmsProperties.getApiKey(),
                coolSmsProperties.getApiSecret(),
                coolSmsProperties.getDomain()
        );
    }

    public void sendMessage(MessageRequest messageRequest) {
        String to = messageRequest.getPhoneNumber().replace(" ", "").replace("-", "");
        Message message = new Message();
        message.setFrom("01022258999");
        message.setTo(to);
        message.setText(messageRequest.getMessage());

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            // Java LocalDateTime, Instant 기준, Kolintx의 datetime 내 Instant 타입을 넣어도 동작합니다!
            LocalDateTime reservedDateTime = messageRequest.getReservedDateTime();
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
}
