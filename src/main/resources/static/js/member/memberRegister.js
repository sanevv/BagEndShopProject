$(() => {
})
$('span.error').hide();


$('input#name').focus().bind('blur', (e) => {
    //이름이 비어있으면 에러메시지 표시
    // $(e.target).siblings()
    //siblings()는 현재 요소의 형제 요소를 찾는 메서드
    inputValueCheck($(e.target), $(e.target).val() === '');
})

inputValueCheck = (targetElement, isError) => {
    if (isError) {
        targetElement.parent().find('span.error').show();
        $('table#tblMemberRegister :input').prop('disabled', true);//  모든 요소를 비활성화
        targetElement.prop('disabled', false);// 현재 요소는 비활성화하지 않음
        targetElement.val('').focus()
    } else {
        targetElement.parent().find('span.error').hide();
        $('table#tblMemberRegister :input').prop('disabled', false);
        if (idCheck)
            $('input#userid').prop('disabled', true); //아이디 중복 체크가 완료된 경우 아이디 입력 필드를 비활성화
        if (emailCheck)
            $('input#email').prop('disabled', true); //이메일 중복 체크가 완료된 경우 이메일 입력 필드를 비활성화
    }
}
$('input#userid').focus().bind('blur', (e) => {
    inputValueCheck($(e.target), $(e.target).val() === '');
})


$('input#pwd').focus().bind('blur', (e) => {
    //정규표현식 사용 암호 검증
    const pwdPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
    //이 정규표현식은 최소 8자 이상, 영문자, 숫자, 특수문자를 포함해야 함을 의미합니다.
    inputValueCheck($(e.target), !pwdPattern.test($(e.target).val().trim()))
    // 정규표현식 테스트 결과가 false 이면 에러 메시지를 표시
})
$('input#pwdcheck').focus().bind('blur', (e) => {
    //비밀번호 확인
    const pwdElement = $('input#pwd');
    const pwd = pwdElement.val().trim();
    const pwdCheck = $(e.target).val().trim();
    if (pwdCheck === '') return;
    const isError = pwd !== pwdCheck;
    inputValueCheck($(e.target), isError);
    if (isError) {
        pwdElement.prop('disabled', false);
    }
})

$('input#email').focus().bind('blur', (e) => {
    //이메일 형식 검사
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    // 이 정규표현식은 일반적인 이메일 형식을 검사합니다.
    inputValueCheck($(e.target), !emailPattern.test($(e.target).val().trim()));
})

$('input#hp2').focus().bind('blur', (e) => {
    const regExp = /^[1-9][0-9]{3,4}$/;
    //휴대폰 번호 형식 검사 연락처 국번( 숫자 4자리인데 첫번째 숫자는 1-9 이고 나머지는 0-9) 정규표현식 객체 생성
    inputValueCheck($(e.target), !regExp.test($(e.target).val().trim()));
})
$('input#hp3').focus().bind('blur', (e) => {
    const regExp = /^[0-9]{4}$/;
    inputValueCheck($(e.target), !regExp.test($(e.target).val().trim()));
})

$('input#postcode').focus().bind('blur', e => {
    const regExp = /^[0-9]{5}$/;
    inputValueCheck($(e.target), !regExp.test($(e.target).val().trim()));
}).attr('readonly', true);

/*
            >>>> .prop() 와 .attr() 의 차이 <<<<
                 .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함.
                 .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
      */
$('input#address').attr('readonly', true);
$('input#extraAddress').attr('readonly', true);

$('img#zipcodeSearch').click(function () {

    new daum.Postcode({

        oncomplete: function (data) {

            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.


            // 각 주소의 노출 규칙에 따라 주소를 조합한다.

            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.

            let addr = ''; // 주소 변수

            let extraAddr = ''; // 참고항목 변수


            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우

                addr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)

                addr = data.jibunAddress;

            }


            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.

            // if (data.userSelectedType === 'R') {

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)

            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.

            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {

                extraAddr += data.bname;

            }

            // 건물명이 있고, 공동주택일 경우 추가한다.

            if (data.buildingName !== '' && data.apartment === 'Y') {

                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);

            }

            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.

            if (extraAddr !== '') {

                extraAddr = ' (' + extraAddr + ')';

            } else
                extraAddr = '(참고항목없음)'; // 참고항목이 없을 경우 기본값으로 설정한다.

            // 조합된 참고항목을 해당 필드에 넣는다.

            document.getElementById("extraAddress").value = extraAddr;
            //} else {

            // document.getElementById("extraAddress").value = '';

            // }


            // 우편번호와 주소 정보를 해당 필드에 넣는다.

            document.getElementById('postcode').value = data.zonecode;

            document.getElementById("address").value = addr;

            // 커서를 상세주소 필드로 이동한다.

            document.getElementById("detailAddress").focus();

        }

    }).open();

});


// === jQuery UI 의 datepicker === //
$("input#datepicker").datepicker({
    dateFormat: 'yy-mm-dd'  //Input Display Format 변경
    , showOtherMonths: true   //빈 공간에 현재월의 앞뒤월의 날짜를 표시
    , showMonthAfterYear: true //년도 먼저 나오고, 뒤에 월 표시
    , changeYear: true        //콤보박스에서 년 선택 가능
    , changeMonth: true       //콤보박스에서 월 선택 가능
    //  ,showOn: "both"          //button:버튼을 표시하고,버튼을 눌러야만 달력 표시됨. both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시됨.
    //  ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
    //  ,buttonImageOnly: true   //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
    //  ,buttonText: "선택"       //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
    , yearSuffix: "년"         //달력의 년도 부분 뒤에 붙는 텍스트
    , monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'] //달력의 월 부분 텍스트
    , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] //달력의 월 부분 Tooltip 텍스트
    , dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'] //달력의 요일 부분 텍스트
    , dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'] //달력의 요일 부분 Tooltip 텍스트
    //  ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
    //  ,maxDate: "+1M" //최대 선택일자(+1D:하루후, +1M:한달후, +1Y:일년후)
}).bind('change', e => {
    //날짜 입력시 에러 메시지 표시
    const datePattern = /^\d{4}-\d{2}-\d{2}$/; //yyyy-mm-dd 형식의 정규표현식
    const birthInput = $(e.target);
    // 날짜가 실존하는 날짜인지 확인
    // 예: 2023-02-30은 존재하지 않는 날짜이므로 false
    // 2023-02-29은 존재하지 않는 날짜이므로 false
    // 2023-02-28은 존재하는 날짜이므로 true
    try {
        new Date(birthInput.val().trim());
    } catch (error) {
        inputValueCheck(birthInput, true);
        return;
    }
    inputValueCheck(birthInput, !datePattern.test(birthInput.val().trim()));
}).bind('keyup', e => {
    //날짜 입력시 에러 메시지 표시
    inputValueCheck($(e.target), true);
});

// 초기값을 오늘 날짜로 설정
// $('input#datepicker').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)


// === 전체 datepicker 옵션 일괄 설정하기 ===
//     한번의 설정으로 $("input#fromDate"), $('input#toDate')의 옵션을 모두 설정할 수 있다.
$(function () {
    //모든 datepicker에 대한 공통 옵션 설정
    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd' //Input Display Format 변경
        , showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
        , showMonthAfterYear: true //년도 먼저 나오고, 뒤에 월 표시
        , changeYear: true //콤보박스에서 년 선택 가능
        , changeMonth: true //콤보박스에서 월 선택 가능
        // ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시됨. both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시됨.
        // ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
        // ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
        // ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
        , yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
        , monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'] //달력의 월 부분 텍스트
        , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] //달력의 월 부분 Tooltip 텍스트
        , dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'] //달력의 요일 부분 텍스트
        , dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'] //달력의 요일 부분 Tooltip 텍스트
        // ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
        // ,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
    });

    // input을 datepicker로 선언
    $("input#fromDate").datepicker();
    $("input#toDate").datepicker();

    // From의 초기값을 오늘 날짜로 설정
    $('input#fromDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)

    // To의 초기값을 3일후로 설정
    $('input#toDate').datepicker('setDate', '+3D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
});
//회원가입성공후 로그인처리시키기
function loginProcess(id, pwd) {
    LoginModule.login(id, pwd);
}



//유효성 검사
function goRegister() {
    let isError = false;
    $('.requiredInfo').each((i, e) => {
        const data = $(e).val().trim()
        if (data === '') {
            alert($(e).data('kor') + '을(를) 입력하세요.');
            $(e).focus();
            isError = true;
            return false; // 반복문 중단
        }
    });
    if (isError) return;


    //address 체크
    const addressArr = $('.address').map(function () {
        return $(this).val().trim();
    }).get();
    // addressArr 는 주소 입력 필드의 값들을 배열로 가져옵니다.
    for (const addValue of addressArr) {
        if (addValue === '') {
            alert('주소를 입력하세요.');
            isError = true;
            return false; // 반복문 중단
        }
    }


    //성별 체크
    const checkedGender = $('input[name="gender"]:checked').val();
    if (checkedGender === 0 || checkedGender === undefined) {
        alert('성별을 선택하세요.');
        return;
    }
    //생년월일
    const birthDate = $('input#datepicker').val().trim();
    if (birthDate === '') {
        alert('생년월일을 입력하세요.');
        return;
    }

    const isAgreement = $('input#agree').is(':checked');
    if (!isAgreement) {
        alert('개인정보 수집 및 이용에 동의해주세요.');
        return;
    }
    if (!idCheck) {
        alert('아이디 중복 체크를 해주세요.');
        $('input#userid').focus();
        return;
    }
    if (!emailCheck) {
        alert('이메일 중복 체크를 해주세요.');
        $('input#email').focus();
        return;
    }


    // disabled 상태의 필드들을 임시로 활성화
    const disabledFields = $('form[name="registerFrm"] :disabled').prop('disabled', false);

    // form 데이터 수집
    const form = document.registerFrm;
    const formData = new FormData(form);
    const jsonData = {};

    // FormData를 JSON 객체로 변환
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });
// 필드들을 다시 disabled 상태로 되돌림
    disabledFields.prop('disabled', true);
    console.log(jsonData)
    // fetch API로 JSON 형식으로 전송
    fetch('/api/member/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (response.ok) {
                return response.text();
                // window.location.href = '/index.up'; // 성공 시 리다이렉션
            } else {
                alert('회원가입 중 오류가 발생했습니다.');
                history.back(); // 오류 발생 시 이전 페이지로 돌아가기
            }
        })
        .then(name => {
            alert(`${name}님 회원가입이 완료되었습니다.`);
            // 로그인 처리
            loginProcess(jsonData.userid, jsonData.pwd); // 회원가입 후 로그인 처리
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버 통신 중 오류가 발생했습니다.');
        });

}

let idCheck = false; //아이디 중복 체크 여부
let emailCheck = false; //이메일 중복 체크 여부


$('#idcheck').on('click', e => {
    if (idCheck) return; //이미 아이디 중복 체크가 완료된 경우
    const userid = $('input#userid').val().trim();
    if (userid === '') {
        alert('아이디를 입력하세요.');
        return;
    }
    //아이디 중복 체크
    $.ajax({
        url: '/api/member/check-id',
        type: 'GET',
        data: {userId: userid},
        async: true, //비동기 방식으로 요청 이게 기본값임 false로 설정하면 동기 방식으로 요청
        success: (data) => {
            if (!data) {
                alert('사용 가능한 아이디입니다.');
                idCheck = true; //아이디 중복 체크 완료
                $('input#userid').prop('disabled', true);
                $('#idcheckResult').text('사용 가능한 아이디입니다.').css('color', 'green');
            } else {
                alert('이미 사용 중인 아이디입니다.');
                $('input#userid').focus();
                $('#idcheckResult').text('이미 사용 중인 아이디입니다.').css('color', 'red');
            }
        },
        error: (xhr, status, error) => {
            console.error('아이디 중복 체크 실패:', error);
        }
    });
})

$('#emailcheck').on('click', e => {
    if (emailCheck) return; //이미 이메일 중복 체크가 완료된 경우
    const email = $('input#email').val().trim();
    if (email === '') {
        alert('이메일을 입력하세요.');
        return;
    }
    //중복 체크 제이쿼리 ajax 방식
    $.ajax({
        url: '/api/member/check-email',
        type: 'GET',
        data: {email: email},
        success: (data) => {
            if (!data) {
                alert('사용 가능한 이메일입니다.');
                emailCheck = true; //이메일 중복 체크 완료
                $('input#email').prop('disabled', true);
                $('#emailCheckResult').text('사용 가능한 이메일입니다.').css('color', 'green');
            } else {
                alert('이미 사용 중인 이메일입니다.');
                $('input#email').focus();
                $('#emailCheckResult').text('이미 사용 중인 이메일입니다.').css('color', 'red');
            }
        },
        error: (xhr, status, error) => {
            console.error('이메일 중복 체크 실패:', error);
        }
    });
})