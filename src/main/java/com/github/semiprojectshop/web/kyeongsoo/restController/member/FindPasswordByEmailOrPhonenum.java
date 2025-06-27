package com.github.semiprojectshop.web.kyeongsoo.restController.member;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import com.github.semiprojectshop.web.kyeongsoo.dto.FindPasswordByEmailResponse;
import com.github.semiprojectshop.web.kyeongsoo.dto.FindPasswordByPhonenumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/find")
@RequiredArgsConstructor
public class FindPasswordByEmailOrPhonenum {
    private final DaoCustom daoCustom;
    private final MemberDAO memberDAO;
    private AES256 aes256;
    //
    @GetMapping("/email")
    public FindPasswordByEmailResponse requestfindPasswordByEmail(@RequestParam String email,
                                                                  @RequestParam String username,
                                                                  @RequestParam String userid) throws SQLException {



        boolean isExistEmail = memberDAO.findAPasswordByEmail(email, username, userid);


        //TODO: 이메일로 비밀번호 찾기 로직 구현


        FindPasswordByEmailResponse response = new FindPasswordByEmailResponse(email, username, userid, isExistEmail);

        return response;
    }
    @GetMapping("/phone")
    public FindPasswordByPhonenumResponse findPasswordByPhone(@RequestParam String username,
                                                              @RequestParam String phoneNumber,
                                                              @RequestParam String userid) throws SQLException {

        System.out.println(phoneNumber);

        boolean existPhoneNum = memberDAO.judgmentCalledMobilePhoneNumber(phoneNumber);


        FindPasswordByPhonenumResponse response = new FindPasswordByPhonenumResponse(phoneNumber, username, userid, existPhoneNum);
        //TODO: 핸드폰 인중이 완료된후 유저에게
        return response;

    }

}
