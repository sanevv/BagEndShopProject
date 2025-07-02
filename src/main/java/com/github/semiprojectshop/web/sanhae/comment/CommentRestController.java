package com.github.semiprojectshop.web.sanhae.comment;


import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewCommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // 포스트맨으로 JSON값을 확인하고 싶을 때
@RequestMapping("/api/comment")
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class CommentRestController {

    private final ReviewCommentDAO rvDAO;

    // 관리자 댓글쓴거 보여주기
    @GetMapping("/list")
    public Map<String, Object> getCommentList(@RequestParam("reviewId") int reviewId) {

        // reviewId로  댓글내용 불러오기
        String commentContents = rvDAO.getCommentContents(reviewId);
        boolean isComment = (commentContents != null);

        System.out.println("isComment  : " + isComment);
        System.out.println("commentContents : " + commentContents);

        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("isComment", isComment);
        commentMap.put("commentContents", commentContents);

        return commentMap;
    }

//    @PostMapping("/write")
//    public boolean addCommentForm(@RequestBody ReviewCommentVO reviewCommentVO) {
//
//    }
}
