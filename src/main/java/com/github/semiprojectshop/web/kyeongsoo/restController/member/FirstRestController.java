package com.github.semiprojectshop.web.kyeongsoo.restController.member;

import com.github.semiprojectshop.config.encryption.AES256;
import com.github.semiprojectshop.repository.kyeongsoo.memberModel.MemberDAO;
import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import com.github.semiprojectshop.web.kyeongsoo.dto.FindEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class FirstRestController {
    private final DaoCustom daoCustom;
    private final MemberDAO memberDAO;
    private AES256 aes256;

    @PostMapping("/find")
    public FindEmailResponse requestFindUser(@RequestParam String check,
                                             @RequestParam String name,
                                             @RequestParam String phoneNum) throws SQLException {

        Map<String, String> paramap = new HashMap<>();
        paramap.put("check", check);
        paramap.put("name", name);
        paramap.put("phoneNum", phoneNum);


        boolean isExist = memberDAO.findPhoneNum(paramap);
        //TODO: 파라미터로 넘어온 유저이름과 이메일로 해당유저 존재하는지 먼저확인
        FindEmailResponse response = new FindEmailResponse(name,phoneNum,isExist);



        return response;

    }
}
