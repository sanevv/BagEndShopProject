package com.github.semiprojectshop.web.sanhae.review;

import com.github.semiprojectshop.repository.kyeongsoo.memberDomain.MemberVO;
import com.github.semiprojectshop.repository.sanhae.reviewDomain.ReviewVO;
import com.github.semiprojectshop.repository.sanhae.reviewModel.ReviewDAO;
import com.github.semiprojectshop.service.sihu.StorageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // 포스트맨으로 JSON값을 확인하고 싶을 때
@RequestMapping("/api/review")
@RequiredArgsConstructor // final이 붙은 필드를 생성자(Constructor)를 자동으로 생성해줌
public class ReviewRestController {

    private final ReviewDAO rvDAO;
    private final StorageService storageService;

    // 리뷰 조회하기
    @GetMapping("/list")
    public Map<String, Object> getReviewList(@RequestParam("productId") int productId,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "sizePerPage", defaultValue = "5") int sizePerPage,
                                        HttpSession session) throws SQLException {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        int loginUserId = (loginUser != null) ? loginUser.getUserId() : -1;


        //System.out.println("loginUserId: " + loginUserId);
        List<ReviewVO> reviewList = rvDAO.reviewList(productId, page, sizePerPage);
        int totalPages = rvDAO.getTotalPage(productId, sizePerPage);

        // 처음에 보여줄 리스트 넣어주기
        Map<String, Object> reviewMap = new HashMap<>();
        reviewMap.put("reviewList", reviewList);
        reviewMap.put("currentPage", page);
        reviewMap.put("totalPages", totalPages);


        for (ReviewVO review : reviewList) {
            // 리뷰 작성자가 현재 로그인한 사람인지 확인하고 맞으면 true 아니면 false
            review.setLoginReviewUser(review.getUserId() == loginUserId);
        }

        return reviewMap;
    }

    // 리뷰 작성하기
    // @RequestBody : HTTP 요청의 본문을 Java 객체로 변환해줌
    // @ResponseBody : 자바 객체를 JSON으로 변환해서 클라이언트에 던져줌
    // ==> @RestController 선언이 되어 있다면 생략가능
//    @PostMapping("/write")
//    public ReviewVO addReview(@RequestBody ReviewVO reviewVO, @RequestPart MultipartFile file) throws SQLException {
//
//        // addReview 메소드에서 처리한 결과 값을 ReviewVO insertReview 담아줌
//        ReviewVO insertReview = rvDAO.addReview(reviewVO);
//
//        Path uploadDir = storageService.createFileDirectory("review", String.valueOf(reviewVO.getUserId()), String.valueOf(insertReview.getReviewId()));
//
//        System.out.println("uploadDir : " + uploadDir);
//        System.out.println("file : " + file.isEmpty());
//
//        if (!file.isEmpty()) {
//            String path = storageService.returnTheFilePathAfterTransfer(file, uploadDir);
//            int reviewId = insertReview.getReviewId();
//
//            insertReview = rvDAO.addReviewImage(reviewId, path);
//            //reviewVO.setReviewImagePath(path);
//        }
//
//
//        return insertReview;
//    }

    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addReviewForm(
            @ModelAttribute ReviewVO reviewVO,
            @RequestPart(value = "file", required = false) MultipartFile file)  {

        try {

            String path = "";
            Path uploadDir = storageService.createFileDirectory("review",
                    String.valueOf(reviewVO.getUserId()),
                    String.valueOf(reviewVO.getProductId()));

            if (file != null && !file.isEmpty()) {

                path = storageService.returnTheFilePathAfterTransfer(file, uploadDir);
            }

            ReviewVO insertReview = rvDAO.addReview(reviewVO, path);


            return ResponseEntity.ok(insertReview);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("리뷰 저장 중 오류 발생");
        }

    }


    // 리뷰 삭제하기
    @PostMapping("/delete")
    // ResponseEntity<?> Spring에서 클라이언트에게 응답할 때 사용하는 객체
    // 상태 코드(200 OK, 404 Not Found 등), 헤더, 본문(JSON 데이터 등)을 직접 제어할 수 있음!!!
    // ResponseEntity.status(403) : 실패 응답 Forbidden - 클라이언트가 해당 요청에 대한 권한이 없다고 알려주는 것
    // ResponseEntity.status(401) : 실패 응답 Unauthorized (승인 관련) - 클라이언트가 인증되지 않았기 때문에 요청을 정상적으로 처리할 수 없다고 알려주는 것
    // ResponseEntity.ok : 상태코드 200~299일 때 true
    public ResponseEntity<?> deleteReview(@RequestBody ReviewVO reviewVO, HttpSession session) throws SQLException {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if(loginUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String loginUserId = String.valueOf(loginUser.getUserId());
        String reviewWriteUserId = rvDAO.getReviewWriteUserid(reviewVO.getReviewId());

        if (!loginUserId.equals(reviewWriteUserId)) {
            return ResponseEntity.status(403).body("본인만 삭제할 수 있습니다.");
        }

        ReviewVO deleted = rvDAO.deleteReview(reviewVO);

        if (deleted == null) {
            return ResponseEntity.status(500).body("리뷰 삭제에 실패했습니다.");
        }

        return ResponseEntity.ok(deleted);

    }

    // 리뷰 수정하기
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateReviewForm(
            @ModelAttribute ReviewVO reviewVO,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpSession session) throws SQLException {

        // 로그인 확인
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String loginUserId = String.valueOf(loginUser.getUserId());

        // ReviewId 로 작성자 userId 조회하기
        String reviewWriteUserId = rvDAO.getReviewWriteUserid(reviewVO.getReviewId());

        System.out.println("reviewVO.getReviewId() : " + reviewVO.getReviewId());

        System.out.println("loginUserId : " + loginUserId);
        System.out.println("reviewWriteUserId : " + reviewWriteUserId);

        if (!loginUserId.equals(reviewWriteUserId)) {
            return ResponseEntity.status(403).body("본인만 수정할 수 있습니다.");
        }

        String reviewImgPath = null;

        // 파일이 존재하지 않는 경우
        if (file != null && !file.isEmpty()) {
            Path uploadDir = storageService.createFileDirectory("review",
                    String.valueOf(reviewVO.getUserId()),
                    String.valueOf(reviewVO.getProductId()));

            reviewImgPath = storageService.returnTheFilePathAfterTransfer(file, uploadDir);
        }
        // 파일이 기존에 존재한 경우
        else {
            // 기존 이미지 경로 조회
            reviewImgPath = rvDAO.getReviewImagePath(reviewVO.getReviewId());
        }

        System.out.println("reviewImgPath : " + reviewImgPath);

        // 리뷰 정보 및 이미지 경로 함께 업데이트
        int updated = rvDAO.updateReview(reviewVO, reviewImgPath);

        if (updated < 1) {
            return ResponseEntity.status(500).body("리뷰 수정 실패");
        }

        return ResponseEntity.ok(updated);
    }


    @GetMapping("/pagination")
    public Map<String, Object> getReviewListPagination(@RequestParam("productId") int productId,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("sizePerPage") int sizePerPage,
                                                       @RequestParam("currentShowPageNo") int currentShowPageNo,
                                                       HttpSession session) throws SQLException {


        List<ReviewVO> reviewList = rvDAO.reviewList(productId, page, sizePerPage);

        // 1페이지에 보여줄 개수
        sizePerPage = 5;
        int totalPages = rvDAO.getTotalPage(productId, sizePerPage);
        int currentPage = currentShowPageNo;



        // 페이지네이션 정보만 전달
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", currentPage);
        pagination.put("totalPages", totalPages);
        pagination.put("hasNext", currentPage < totalPages);
        pagination.put("hasPrevious", currentPage > 1);
        pagination.put("startPage", Math.max(1, currentPage - 5));
        pagination.put("endPage", Math.min(totalPages, currentPage + 5));

        pagination.put("pagination", pagination);

        return pagination;
    }


}