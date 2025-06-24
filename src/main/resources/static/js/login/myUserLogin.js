$(function(){

    $('button#btnSubmit').on('click', function(){
        looglogInLocalStorage();
    })

    $('input#loginPwd').on('keyup', function(e){
        if(e.key === 'Enter'){
            looglogInLocalStorage();
        }
    })


})

// Function Declaration

function looglogInLocalStorage() {

    if($('input#userEmail').val().trim() == ""){
        alert("아이디를 입력하세요.");
        $('input#userEamil').val('').focus();
        return;
    }

    if($('input#loginPwd').val().trim() == ""){
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