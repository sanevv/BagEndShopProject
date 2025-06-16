
$('span.error').hide();


$('input#name').focus().bind('blur', (e) => {
    //이름이 비어있으면 에러메시지 표시
    // $(e.target).siblings()
    //siblings()는 현재 요소의 형제 요소를 찾는 메서드
    inputValueCheck($(e.target), $(e.target).val() === '');
})

let emailCheck = false; //이메일 중복 체크 여부

inputValueCheck = (targetElement, isError) => {
    if (isError) {
        targetElement.parent().find('span.error').show();
        $('table#tblMemberEdit :input').prop('disabled', true);//  모든 요소를 비활성화
        targetElement.prop('disabled', false);// 현재 요소는 비활성화하지 않음
        targetElement.val('').focus()
    } else {
        targetElement.parent().find('span.error').hide();
        $('table#tblMemberEdit :input').prop('disabled', false);
        if (emailCheck)
            $('input#email').prop('disabled', true); //이메일 중복 체크가 완료된 경우 이메일 입력 필드를 비활성화
    }
}


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


$('input#address').attr('readonly', true);
$('input#extraAddress').attr('readonly', true);

//이메일체크
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
goEdit = (userId)=> {
    //입력값 검증
    const name = $('input#name').val().trim();
    const pwd = $('input#pwd').val().trim();
    const pwdCheck = $('input#pwdcheck').val().trim();
    const email = $('input#email').val().trim();
    const hp1 = $('input#hp1').val().trim();
    const hp2 = $('input#hp2').val().trim();
    const hp3 = $('input#hp3').val().trim();
    const postcode = $('input#postcode').val().trim();
    const address = $('input#address').val().trim();
    let extraAddress = $('input#extraAddress').val().trim();
    const addressDetail = $('input#detailAddress').val().trim();

    if (name === '') {
        alert('이름을 입력하세요.');
        return;
    }
    if (pwd === '') {
        alert('비밀번호를 입력하세요.');
        return;
    }
    if (pwdCheck === '') {
        alert('비밀번호 확인을 입력하세요.');
        return;
    }
    if (email === '') {
        alert('이메일을 입력하세요.');
        return;
    }
    if (!emailCheck) {
        alert('이메일 중복 체크를 해주세요.');
        return;
    }
    if (hp1 === '' || hp2 === '' || hp3 === '') {
        alert('휴대폰 번호를 입력하세요.');
        return;
    }
    if (postcode === '') {
        alert('우편번호를 입력하세요.');
        return;
    }
    if (address === '') {
        alert('주소를 입력하세요.');
        return;
    }
    if(extraAddress === '') {
        extraAddress = '(참고항목없음)'; // 참고항목이 비어있으면 기본값으로 설정
    }
    if (addressDetail === '') {
        alert('상세주소를 입력하세요.');
        return;
    }
    //모든 입력값이 유효한 경우
    axios.put('/api/member', {
        userId: userId,
        userName: name,
        password: pwd,
        email: email,
        hp1: hp1,
        hp2: hp2,
        hp3: hp3,
        zipCode: postcode,
        address: address,
        addressDetail: addressDetail,
        addressReference: extraAddress

    }).then((response) => {
        if (response.data) {
            alert('회원 정보가 수정되었습니다.');
            //수정된 회원 정보를 부모 창에 반영
            opener.location.reload();
            self.close(); //팝업창 닫기
        } else {
            alert('회원 정보 수정에 실패했습니다. 다시 시도해주세요.');
        }
    }).catch((error)=>{
        console.error('회원 정보 수정 실패:', error);
        alert('회원 정보 수정에 실패했습니다. 다시 시도해주세요.');
    })
}

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
