$(function (){});

$('button#btnSubmit').click((e)=>{
    e.preventDefault();
    login();
});

$('input#loginPwd').on('keyup', (e)=>{
    if(e.keyCode === 13) //엔터쳤을경우
        $('button#btnSubmit').click();
});
$('input#loginUserid').on('keyup', (e)=>{
    if(e.keyCode === 13) //엔터쳤을경우
        $('button#btnSubmit').click();
})

//로그인처리함수디이
login=()=>{
    let loginId = $('input#loginUserid').val();
    let loginPwd = $('input#loginPwd').val();

    if(loginId === '' || loginPwd === ''){
        alert('아이디와 비밀번호를 입력해주세요.');
        return;
    }

    $.ajax({
        url: '/api/member/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            userId: loginId,
            password: loginPwd
        }),
        success: (response) => {
            console.log(response);
            if(response.success){//응답 제이슨에 success 키값이 true일경우
                window.location.href = '/index.up';
            } else {
                alert('로그인 실패: ' + response.message);
            }
        },
        error: (xhr, status, error) => {
            alert('로그인 중 오류 발생: ' + error);
        }
    });
}