package com.github.semiprojectshop.web.seungho;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.semiprojectshop.repository.seungho.domain.NoticeVO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO;
import com.github.semiprojectshop.repository.seungho.model.NoticeDAO_imple;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeListController {
	private final NoticeDAO ndao;
	NoticeVO nvo = new NoticeVO();
	@GetMapping("/list.one")
	public String noticeList(HttpServletRequest request) throws SQLException {
		
		String ctxPath = request.getContextPath();
		
		String sizePerPage = "8";
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if (currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("sizePerPage", sizePerPage);
		paraMap.put("currentShowPageNo", currentShowPageNo);

		int totalPage = ndao.totalPage(paraMap);
		
		
		
		String pageBar = "";
		
		int blockSize = 8;
		
		try {

			if (Integer.parseInt(currentShowPageNo) > totalPage || Integer.parseInt(currentShowPageNo) <= 0) {
				currentShowPageNo = "1";
				paraMap.put("currentShowPageNo", currentShowPageNo);
			}

		} catch (NumberFormatException e) {
			currentShowPageNo = "1";
			paraMap.put("currentShowPageNo", currentShowPageNo);
		}
		
		
		List<NoticeVO> noticeList = ndao.noticeList(paraMap);

		request.setAttribute("noticeList", noticeList);
		request.setAttribute("sizePerPage", sizePerPage);
		int loop = 1;
		// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.

		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== //
		int pageNo = ((Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1;
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.

		// **** [맨처음][이전] 만들기 **** //
		// pageNo ==> 11
		pageBar += "<li class='page-item'><a class='page-link' href='"+ctxPath+"/notice/list.one?sizePerPage=" + sizePerPage
				+ "&currentShowPageNo=1'>[맨처음]</a></li>";

		if (pageNo != 1) {
			pageBar += "<li class='page-item'><a class='page-link' href='"+ctxPath+"/notice/list.one?sizePerPage=" + sizePerPage
					+ "&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "<li class='page-item active'><a class='page-link' href='#'>" + pageNo + "</a></li>";
			} else {
				pageBar += "<li class='page-item'><a class='page-link' href='"+ctxPath+"/notice/list.one?sizePerPage="
						+ sizePerPage + "&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
			}

			loop++; // 1 2 3 4 5 6 7 8 9 10

			pageNo++; // 1 2 3 4 5 6 7 8 9 10
						// 11 12 13 14 15 16 17 18 19 20
						// 21 22 23 24 25 26 27 28 29 30
						// 31 32 33 34 35 36 37 38 39 40
						// 41 42

		} // end of while(!(loop > blockSize || pageNo > totalPage))----------------

		// **** [다음][마지막] 만들기 **** //
		// pageNo ==> 11

		if (pageNo <= totalPage) {
			pageBar += "<li class='page-item'><a class='page-link' href='"+ctxPath+"/notice/list.one?sizePerPage=" + sizePerPage
					+ "&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
		}
		pageBar += "<li class='page-item'><a class='page-link' href='"+ctxPath+"/notice/list.one?sizePerPage=" + sizePerPage
				+ "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";

		request.setAttribute("pageBar", pageBar);
		// ========== 페이지바 만들기 끝 ============ //
		
	
		
		/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
        검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작 <<< */

		request.setAttribute("currentShowPageNo", currentShowPageNo);
		
		
		
		return "product/NoticeList";
	}
}
