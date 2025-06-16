package com.github.mymvcspring.service;

import com.github.mymvcspring.repository.Image;
import com.github.mymvcspring.repository.ImageRepository;
import com.github.mymvcspring.repository.user.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public boolean loginValidation(String userId, MyUser loginUser) {
        return loginUser != null && loginUser.getUserId().equals(userId);
        // 결제 확인 로직을 여기에 추가
        // 예: 결제 상태 업데이트, 알림 전송 등
    }
}
