<%--
  Created by IntelliJ IDEA.
  User: sihu
  Date: 25. 6. 29.
  Time: 오후 6:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>BagEnd 결제창</title>
  <meta charset="UTF-8">

  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
  <script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>

  <script type="text/javascript">
    const paymentRequestJson = JSON.parse('${paymentRequestJson}');
    // 자식창에서 실행
    let originalHref = window.opener.location.href;
    let isSuccess = false;
    let blockParentNavigation = true; // 부모창 이동 방지 플래그
    //부모창 닫기 방지
    function monitorParent() {
      if (!blockParentNavigation) return;
      try {
        if (window.opener && !window.opener.closed) {
          if (window.opener.location.href !== originalHref) {
            alert('다시 시도해 주세요.');
            self.close();
          }
        }
      } catch (e) {
        // cross-origin 등 예외 무시
      }
      setTimeout(monitorParent, 500);
    }
    monitorParent(); // 부모창 모니터링 시작



    $(document).ready(function() {

      //	여기 링크를 꼭 참고하세용 http://www.iamport.kr/getstarted
      var IMP = window.IMP;     // 생략가능
      IMP.init('${portOnePrimary}');  // 중요!!  아임포트에 가입시 부여받은 "가맹점 식별코드".
      //jsp 에서 값을넣을땐 문자열이라면 따옴표로 감싸줘야된다.
      // 결제요청하기
      IMP.request_pay({

        pg : 'html5_inicis', // 결제방식 PG사 구분
        pay_method : 'card',	// 결제 수단
        merchant_uid : paymentRequestJson.ordersId + new Date().getTime(), // 가맹점에서 생성/관리하는 고유 주문번호
        name : paymentRequestJson.ordersId + '_'+ paymentRequestJson.refProductName +'등 '+ paymentRequestJson.totalStock +'개 상품',	 // 코인충전 또는 order 테이블에 들어갈 주문명 혹은 주문 번호. (선택항목)원활한 결제정보 확인을 위해 입력 권장(PG사 마다 차이가 있지만) 16자 이내로 작성하기를 권장
        amount : paymentRequestJson.totalPrice, 	  // ''  결제 금액 number 타입. 필수항목.
        buyer_email : '${email}',  // 구매자 email
        buyer_name : '${name}',	  // 구매자 이름
        buyer_tel : '${phoneNumber}',    // 구매자 전화번호 (필수항목)
        buyer_addr : '',//안할래
        buyer_postcode : '',//안해
        m_redirect_url : ''  // 휴대폰 사용시 결제 완료 후 action : 컨트롤러로 보내서 자체 db에 입력시킬것!
      }, function(rsp) {
        /*
            if ( rsp.success ) {
                var msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + rsp.imp_uid;
                msg += '상점 거래ID : ' + rsp.merchant_uid;
                msg += '결제 금액 : ' + rsp.paid_amount;
                msg += '카드 승인번호 : ' + rsp.apply_num;
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }
            alert(msg);
        */
        if ( rsp.success ) { // PC 데스크탑용
          /* === 팝업창에서 부모창 함수 호출 방법 3가지 ===
              1-1. 일반적인 방법
              opener.location.href = "javascript:부모창스크립트 함수명();";
              opener.location.href = "http://www.aaa.com";

              1-2. 일반적인 방법
              window.opener.부모창스크립트 함수명();

              2. jQuery를 이용한 방법
              $(opener.location).attr("href", "javascript:부모창스크립트 함수명();");
          */

          <%--   opener.location.href = "javascript:goCoinUpdate('<%= ctxPath%>', '${requestScope.userid}','${requestScope.coinmoney}');"; --%>
          <%--window.opener.goCoinUpdate('<%= ctxPath%>', '${requestScope.userId}','${requestScope.realCoin}', '${requestScope.point}');--%>
          <%--   $(opener.location).attr("href", "javascript:goCoinUpdate('<%= ctxPath%>', '${requestScope.userid}','${requestScope.coinmoney}');");  --%>
          // self.close();
          (async function() {
            //결제 완료 로직
            isSuccess = true;
            await window.opener.paymentSuccess(paymentRequestJson.ordersId, paymentRequestJson.productCartIds);
            blockParentNavigation = false; // 부모창 이동 방지 플래그 해제
            self.close();
          })();

        } else {
          console.log(rsp);
          // 결제 실패 로직
          const 좆됐음 = true;
          (async function(){
            blockParentNavigation = false; // 부모창 이동 방지 플래그 해제
            self.close();
          })()
          // location.href="/MyMVC";

        }

      }); // end of IMP.request_pay()----------------------------


    }); // end of $(document).ready()-----------------------------
    window.onbeforeunload = function() {
      blockParentNavigation = false; // 부모창 이동 방지 플래그 해제
      if(isSuccess) return;
      if (window.opener && !window.opener.closed) {
        // window.opener.부모함수명();
        (async function(){
          await window.opener.paymentFail(paymentRequestJson.ordersId);
        })();

      }
    };

  </script>
</head>

<body>
</body>
</html>
