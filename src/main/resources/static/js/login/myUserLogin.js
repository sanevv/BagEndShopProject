$(function(){

    $('button#btnSubmit').on('click', function(){
        looglogInLocalStorage();
        loginCheck();
    })

    $('input#loginPwd').on('keyup', function(e){
        if(e.key === 'Enter'){
            looglogInLocalStorage();
            loginCheck();
        }
    })


})

// Function Declaration

function looglogInLocalStorage() {

    if($('input#userEmail').val().trim() === ""){
        alert("아이디를 입력하세요.");
        $('input#userEmail').val('').focus();
        return;
    }

    if($('input#loginPwd').val().trim() === ""){
        alert("비밀번호를 입력하세요.");
        $('input#loginPwd').val('').focus();
        return;
    }

    if($('input#checkSaveId').is(':checked')) {
        localStorage.setItem('checkSaveId', $('input#userEmail').val());
    }
    else {
        localStorage.removeItem('checkSaveId');
    }

    const frm = document.getElementById('memberLoginFrm');
    if (frm) {
        frm.submit();
    } else {
        console.error('폼을 찾을 수 없습니다.');
    }



} // end of function looglogInLocalStorage() {}

function loginCheck() {

    const writeEmail = $('input#userEmail').val();
    const writePwd = $('input#loginPwd').val();
    $.ajax({
        type: 'POST',
        url: 'api/member/login',
        data: {
            userEmail: writeEmail,
            loginPwd: writePwd
        },
        success: function(response) {
            if(response) {
                alert('로그인 성공');
                window.location.href = '/';
            } else {
                alert('로그인 실패: ');
            }
        },
        error: function(xhr, status, error) {
            console.error('로그인 요청 실패:', error);
            alert('로그인 요청에 실패했습니다. 다시 시도해주세요.');
        }
    })
}