package com.github.semiprojectshop.web.sanhae.comment;


import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewCommentVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewCommentDAO;
import com.github.semiprojectshop.service.sanhae.exeptions.BadSanHaeException;
import com.github.semiprojectshop.service.sanhae.exeptions.InternalServerErrorSanHaeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController // 포스트맨으로 JSON값을 확인하고 싶을 때
@RequestMapping("/api/comment")
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class CommentRestController {

    private final ReviewCommentDAO rvcDAO;

    // 관리자 댓글쓴거 보여주기
    @GetMapping("/list")
    public Map<String, Object> getCommentList(@RequestParam("reviewId") int reviewId) {

        // reviewId로  댓글내용 불러오기
        String commentContents = rvcDAO.getCommentContents(reviewId);

        if(commentContents != null) {
            commentContents = commentContents.replaceAll("\n", "<br>");
        }

        boolean isComment = (commentContents != null);

        //System.out.println("isComment  : " + isComment);
        //System.out.println("commentContents : " + commentContents);

        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("isComment", isComment);
        commentMap.put("commentContents", commentContents);

        return commentMap;
    }

    // 관리자 댓글 작성하기
    // @RequestBody : HTTP 요청의 본문을 Java 객체로 변환해줌
    // @ResponseBody : 자바 객체를 JSON으로 변환해서 클라이언트에 던져줌
    // ==> @RestController 선언이 되어 있다면 생략가능
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> addReviewComment(@RequestBody ReviewCommentVO rvcVO)  {

        Map<String, Object> response = new HashMap<>();

        if (rvcVO == null) {
            throw BadSanHaeException.fromMessage("입력값이 없습니다.");
        }

        // 관리자 댓글 작성하기
        ReviewCommentVO result = rvcDAO.addReviewComment(rvcVO);

        String commentContents = rvcVO.getCommentContents();

        if(commentContents != null) {
            // 입력한 내용에서 엔터는 <br>로 변환시키기
            commentContents = commentContents.replaceAll("\n", "<br>");
            result.setCommentContents(commentContents);
            //System.out.println("commentContents = " + commentContents);
        }

        if (result == null) {
//            response.put("success", false);
//            response.put("message", "댓글 저장 실패");
//            return ResponseEntity.status(500).body(response);
            throw InternalServerErrorSanHaeException.fromMessage("댓글 저장 실패!!");
        }

        response.put("success", true);
        response.put("comment", result);


        // 문제없으면
        return ResponseEntity.ok(response);

    }

    // 관리자 댓글 수정하기
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateReviewComment(@RequestBody ReviewCommentVO rvcVO)  {

        Map<String, Object> response = new HashMap<>();

        if (rvcVO == null) {
            throw BadSanHaeException.fromMessage("입력값이 없습니다.");
        }

        int updated = rvcDAO.updateReviewComment(rvcVO);

        // 댓글
        if (updated < 1) {
            throw BadSanHaeException.fromMessage("댓글 수정 실패!");
        }

        response.put("success", true);
        response.put("comment", "댓글 수정 완료!!!");

        // 문제없으면
        return ResponseEntity.ok(response);

    }

    // 관리자 댓글 삭제하기
    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteReviewComment(@RequestBody ReviewCommentVO rvcVO)  {

        Map<String, Object> response = new HashMap<>();

        int reviewId = rvcVO.getReviewId();
        ReviewCommentVO deleteReviewCommentVO = rvcDAO.deleteReviewComment(reviewId);

        if(deleteReviewCommentVO == null) {
            throw BadSanHaeException.fromMessage("관리자 댓글을 삭제할 수 없습니다.");
        }

        response.put("success", true);
        response.put("comment", "관리자 댓글 삭제 성공!");

        return ResponseEntity.ok(response);

    }
}
