package com.github.semiprojectshop.web.sanhae.viewcontroller;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//웹 요청을 처리하는 컨트롤러
@Controller
@RequiredArgsConstructor
public class MainController {

    private final NoticeDAO ndao;


    @GetMapping("/")
    public String main(HttpServletRequest request) throws SQLException {
        List<NoticeVO> nvoList = new ArrayList<>();
        nvoList = ndao.selectMainPageNotice();

        request.setAttribute("nvoList", nvoList);

        return "index";
    }
}
