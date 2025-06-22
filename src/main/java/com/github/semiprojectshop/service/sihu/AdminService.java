//package com.github.semiprojectshop.service;
//
//import com.github.semiprojectshop.config.encryption.AES256;
//import com.github.semiprojectshop.repository.sihu.user.MyUserRepository;
//import com.github.semiprojectshop.web.sihu.dto.PaginationDto;
//import com.github.semiprojectshop.web.sihu.dto.member.MemberDetailResponse;
//import com.github.semiprojectshop.web.sihu.dto.member.MemberListResponse;
//import com.github.semiprojectshop.web.sihu.dto.member.SearchConditions;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AdminService {
//    private final MyUserRepository myUserRepository;
//    private final AES256 aes256;
//
//
//    @Transactional(readOnly = true)
//    public Optional<PaginationDto<MemberListResponse>> searchMemberList(SearchConditions searchConditions, long page, long size) {
//        PaginationDto<MemberListResponse> responses =
//                myUserRepository.findUserInfoBySearchConditions(searchConditions, page, size);
//        for(int i = 0; i < responses.getItems().size(); i++){
//            MemberListResponse member = responses.getItems().get(i);
//            member.emailDecryption();
//            member.setSearchCount(responses.getTotalItems()-(size*page) - i);
//        }
////        responses.getItems().forEach(MemberListResponse::emailDecryption);//이메일복호화
//        return Optional.of(responses);
//    }
//
//
//
//    // AES256을 사용하여 이메일을 복호화하는 메서드
//    private String decryption(String encryptedText) {
//        try {
//            return aes256.decrypt(encryptedText);
//        } catch (Exception e) {
//            throw new RuntimeException("Decryption failed", e);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<MemberDetailResponse> getMemberInfo(String userId) {
//        MemberDetailResponse memberDetailResponse = myUserRepository.findUserDetailsById(userId);
//        if(memberDetailResponse == null)
//            return Optional.empty();
//        memberDetailResponse.decryptionAndRegisterAtConvert();
//        memberDetailResponse.settingAge();
//        return Optional.of(memberDetailResponse);
//    }
//}
