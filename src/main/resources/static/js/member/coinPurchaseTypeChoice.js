const errorMsg = $('td#error').hide();
$('td#purchase').hover((event) =>{
    $(event.target).addClass('purchase');
}, (event) => {
    $(event.target).removeClass('purchase');
})

goCoinPayment = (ctxPath, userId) => {
    const selectedAmount = $('input:radio[name="coinmoney"]:checked');

    if (!selectedAmount.val()) {
        alert('충전할 금액을 선택해주세요.');
        errorMsg.show()
        return;
    }
    errorMsg.hide();

    //결제 금액을 서버로 전송
    const chargingDataIndex = $('input:radio[name="coinmoney"]').index(selectedAmount);
    const point = $('td>span').eq(chargingDataIndex).text().trim();
    // alert('선택된 금액: ' + point + '금액 '+selectedAmount.val());
    //자기 자신 팝업창 닫기
    /*
    1-1. 일반적인 방법
         opener.location.href = "javascript:부모창스크립트 함수명();";

         1-2. 일반적인 방법
         window.opener.부모창스크립트 함수명();

         2. jQuery를 이용한 방법
         $(opener.location).attr("href", "javascript:부모창스크립트 함수명();");
 */


     opener.location.href = `javascript:goPaymentStart('${ctxPath}', '${userId}', '${selectedAmount.val()}', '${point}')`;
    // const pointValue = point.replace(/,/g, ''); //콤마 제거

    self.close();

}

$('input:radio[name="coinmoney"]').on('click', (e)=>{
    // alert('선택된 금액: ' + $(e.target).val());
    // 선택된 라디오 버튼의 인덱스를 가져온다. 암기 .index에 이벤트 타겟 파라미터로 넣음
    const index = $('input:radio[name="coinmoney"]').index($(e.target));
    // console.log('확인용 '+index);
    $('td>span').removeClass('stylePoint').eq(index).addClass('stylePoint')

    // $("td>span").eq(index); ==> $("td>span")중에 index 번째의 요소인 엘리먼트를 선택자로 보는 것이다.
    //                             $("td>span")은 마치 배열로 보면 된다. $("td>span").eq(index) 은 배열중에서 특정 요소를 끄집어 오는 것으로 보면 된다. 예를 들면 arr[i] 와 비슷한 뜻이다.
})