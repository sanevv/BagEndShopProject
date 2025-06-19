package com.github.semiprojectshop.web.sihu.viewcontroller;

import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
// abstract 추가해서 미완성 클래스라고 정의가 되면 에러는 나지 않는다.
// 하지만 AbstractController 를 부모로 가진 자식 클래스에서 @Override 로 재정의 해야한다!
public abstract class AbstractController implements InterCommand {

    /*
     * === 다음의 나오는 것은 우리끼리한 약속이다. ===
     *
     * ※ view 단 페이지(.jsp)로 이동시 forward 방법(dispatcher)으로 이동시키고자 한다라면
     * 자식클래스(/webapp/WEB-INF/Command.properties 파일에 기록된 클래스명들)에서는 부모클래스에서 생성해둔 메소드
     * 호출시 아래와 같이 하면 되게끔 한다.
     *
     * super.setRedirect(false);
     * super.setViewPage("/WEB-INF/index.jsp");
     *
     *
     * ※ URL 주소를 변경하여 페이지 이동시키고자 한다라면 즉,
     * 	 sendRedirect 를 하고자 한다라면 자식클래스에서는 부모클래스에서
     *   생성해둔 메소드 호출시 아래와 같이 하면 되게끔 한다.
     *
     * super.setRedirect(true);
     * super.setViewPage("registerMember.up");
     */

    private boolean isRedirect = false;
// isRedirect 변수의 값이 false 이라면 view단 페이지(.jsp)로 forward 방법(dispatcher)으로 이동시키겠다.
// isRedirect 변수의 값이 true 이라면 sendRedirect 로 페이지이동을 시키겠다.

    private String viewPage;
// viewPage 는 isRedirect 값이 false 이라면 view단 페이지(.jsp)의 경로명 이고,
// isRedirect 값이 true 이라면 이동해야할 페이지 URL 주소 이다.

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }

    public String getViewPage() {
        return viewPage;
    }

    public void setViewPage(String viewPage) {
        this.viewPage = viewPage;
    }

    //////////////////////////////////////////////////////////////////////////
    // 로그인 유무를 검사해서 로그인 했으면 true 를 리턴해주고
    // 로그인을 하지 않았으면 false를 리턴해주도록 할게야

    public boolean checkLogin(HttpServletRequest request) {

        HttpSession session = request.getSession();
        MyUser loginUser = (MyUser) session.getAttribute("loginUser");

        // 로그인한 경우
        if(loginUser != null) {
            return true;
        }
        else {
            return false;
        }

    }// end of public boolean checkLogin()

}
