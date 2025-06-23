//package com.github.semiprojectshop.service;
//
//import com.github.semiprojectshop.repository.sihu.Image;
//import com.github.semiprojectshop.repository.sihu.ImageRepository;
//import com.github.semiprojectshop.repository.sihu.product.ProductRepository;
//import com.github.semiprojectshop.repository.sihu.product.ProductSpec;
//import com.github.semiprojectshop.repository.sihu.user.MyUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ViewService {
//    private final ImageRepository imageRepository;
//    private final ProductRepository productRepository;
//
//    public List<Image> getAllImages() {
//        return imageRepository.findAll();
//    }
//
//    public boolean loginValidation(String userId, MyUser loginUser) {
//        return loginUser != null && loginUser.getUserId().equals(userId);
//        // 결제 확인 로직을 여기에 추가
//        // 예: 결제 상태 업데이트, 알림 전송 등
//    }
//
//    public long getProductCountBySpec(long specId) {
//        ProductSpec productSpec = ProductSpec.fromId(specId);
//
//        imageRepository.deleteAll();
//        return productRepository.countByProductSpec(productSpec);
//    }
//}
