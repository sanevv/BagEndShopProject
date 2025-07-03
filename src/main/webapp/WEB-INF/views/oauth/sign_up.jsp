<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 7. 3.
  Time: 오전 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../include/header.jsp"/>
<%--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">--%>


<style>
    body {
        background-color: #f8f9fa;
    }

    .signup-box {
        max-width: 400px;
        margin: 60px auto;
        background-color: white;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
    }

    .social-icons img {
        width: 38px;
        height: 38px;
        margin: 0 6px;
        cursor: pointer;
        transition: transform 0.2s ease;
    }

    .social-icons img:hover {
        transform: scale(1.1);
    }
</style>


<div class="signup-box text-center">
    <h4 class="mb-3 fw-bold">소셜 회원가입</h4>
    <p class="text-muted">추가 정보 입력 후 가입을 완료하세요<br><span style="font-size: 13px">페이지를 벗어나면 가입이 취소 됩니다.</span></p>

    <!--  <div class="social-icons mb-4 d-flex justify-content-center">-->
    <!--    <a href="/oauth2/authorization/kakao"><img src="/images/social/kakao.png" alt="Kakao"></a>-->
    <!--    <a href="/oauth2/authorization/naver"><img src="/images/social/naver.png" alt="Naver"></a>-->
    <!--    <a href="/oauth2/authorization/github"><img src="/images/social/github.png" alt="GitHub"></a>-->
    <!--    <a href="/oauth2/authorization/google"><img src="/images/social/google.png" alt="Google"></a>-->
    <!--    <a href="/oauth2/authorization/microsoft"><img src="/images/social/microsoft.png" alt="Microsoft"></a>-->
    <!--    <a href="/oauth2/authorization/twitter"><img src="/images/social/twitter.png" alt="Twitter"></a>-->
    <!--    <a href="/oauth2/authorization/facebook"><img src="/images/social/facebook.png" alt="Facebook"></a>-->
    <!--  </div>-->

    <!--  <p class="text-muted mb-3">또는</p>-->

    <form>
        <div class="mb-3 mt-5 text-start">
            <label for="email" class="form-label">이메일</label>
            <div class="input-group">
                <input type="email" class="form-control" id="email" placeholder="이메일 입력">
                <button type="button" class="btn btn-outline-secondary" id="checkEmailBtn">중복확인</button>
            </div>
            <div style="display: none" id="verifyNumberBox">
                <label for="email" class="form-label">인증번호</label>
                <div class="input-group">
                    <input type="email" class="form-control" id="verifyNumber" placeholder="인증번호 입력">
                    <button type="button" class="btn btn-outline-secondary" id="verifyNumberBtn">인증받기</button>
                </div>
            </div>
        </div>

        <div class="mb-3 text-start">
            <label for="username" class="form-label">이름</label>
            <input type="text" class="form-control" id="username" placeholder="이름 입력">
        </div>
        <div class="mb-3 text-start">
            <label for="phone" class="form-label">전화번호</label>
            <input type="tel" class="form-control" id="phone" placeholder="전화번호 입력">
        </div>
        <div style="margin: 50px 0;">
            <div class="mb-1 text-start">
                <label for="zipcode" class="form-label">우편번호</label>
                <input type="text" class="form-control address" style="width: 21%; cursor: default" id="zipcode"
                       readonly>
            </div>
            <div class="mb-1 text-start">
                <label for="address" class="form-label">주소</label>
                <input type="text" class="form-control address" id="address" style="cursor: default" readonly>
            </div>
            <div class="mb-1 text-start">
                <label for="addressDetails" class="form-label">상세 주소</label>
                <input type="text" class="form-control" id="addressDetails" placeholder="상세 주소 입력">
            </div>

        </div>
        <!-- 프로필 사진 업로드 -->
        <div class="mb-5 text-start">
            <label class="form-label">프로필 사진</label>
            <div class="d-flex align-items-center">
                <div id="profilePreviewBox" class="me-3"
                     style="width: 60px; height: 60px; border-radius: 50%; overflow: hidden; background: #f0f0f0; display: flex; align-items: center; justify-content: center;">
                    <img id="profilePreview" src="https://www.svgrepo.com/show/343494/profile-user-account.svg"
                         alt="미리보기" style="width: 100%; height: 100%; object-fit: cover;">
                </div>
                <input class="form-control form-control-sm" type="file" id="profileImage" accept="image/*"
                       style="max-width: 200px;">
            </div>
            <div class="form-text">이미지 파일(jpg, png 등)을 선택하세요.</div>
        </div>
        <button type="submit" class="btn btn-dark w-100">회원가입</button>
    </form>

    <div class="mt-3">
        <a href="/login" class="text-decoration-none">이미 계정이 있으신가요? 로그인</a>
    </div>
</div>

<%--<!-- Bootstrap JS (optional) -->--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>--%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%--스왈--%>
<link rel="stylesheet" type="text/css"
      href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script>
    const signUpDto = JSON.parse('${signUpDto}'.replace(/&quot;/g,'"'));
    const providerConstant = '${provider}';
    const socialId = signUpDto.socialId;
    const email = signUpDto.email;
    const name = signUpDto.name;
    const profileImageUrl = signUpDto.profileImageUrl;


    console.log(signUpDto);
    console.log(signUpDto.email);
    console.log(profileImageUrl);


    const emailInput = document.getElementById('email');
    const nameInput = document.getElementById('username');
    const verifyNumberBox = document.getElementById('verifyNumberBox');


    function emailValidation(email) {
        if (!email) {
            //sweetAlert
            swal({
                title: "이메일을 입력해주세요",
                text: "이메일 주소를 입력해야 합니다.",
                icon: "warning",
                button: "확인"
            });

            return false;
        }
        // 이메일 형식 간단한 정규식 검사
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            //sweetAlert
            swal({
                title: "이메일 형식이 올바르지 않습니다",
                text: "올바른 이메일 주소를 입력해주세요.",
                icon: "error",
                button: "확인"
            });
            return false;
        }
        return true;
    }

    const checkEmailBtn = document.getElementById('checkEmailBtn');
    checkEmailBtn.addEventListener('click', async function () {
        const email = emailInput.value.trim();
        if (!emailValidation(email))
            return;

        // 실제로는 axios로 서버에 중복확인 요청
        const isNotExist = await axios.get(`/api/member/exist-email?email=\${encodeURIComponent(email)}`)
            .then(response => {
                if (response.data) {
                    // 이메일이 사용 가능
                    swal({
                        title: "사용 가능한 이메일입니다",
                        text: "이 이메일을 사용해 회원가입을 진행하세요.",
                        icon: "success",
                        button: "확인"
                    });
                } else {
                    // 이메일이 이미 사용 중
                    swal({
                        title: "이미 사용 중인 이메일입니다",
                        text: "다른 이메일을 입력해주시거나 비밀번호 찾기를 이용해주세요.",
                        icon: "error",
                        button: "확인"
                    });
                }
                return response.data; // true or false
            })
            .catch(error => {
                console.error('중복확인 요청 실패:', error);
                swal({
                    title: "오류 발생",
                    text: "중복확인 요청에 실패했습니다. 나중에 다시 시도해주세요.",
                    icon: "error",
                    button: "확인"
                });
                return false;
            });
        if(!isNotExist)
            return;

        // 이메일 인증 요청
        const emailSend = await axios.post('/api/mail/send?\${email}')
            .then(response =>{
                if (response.data){
                    swal({
                        title: "인증 메일이 발송되었습니다",
                        text: "이메일을 확인하고 인증을 완료해주세요.",
                        icon: "success",
                        button: "확인"
                    });
                } else {
                    swal({
                        title: "인증 메일 발송 실패",
                        text: "이메일 주소를 확인해주세요.",
                        icon: "error",
                        button: "확인"
                    });
                }
                return response.data;
            }).catch(error=>{
                console.error('인증 메일 발송 실패:', error);
                swal({
                    title: "오류 발생",
                    text: "인증 메일 발송에 실패했습니다. 나중에 다시 시도해주세요.",
                    icon: "error",
                    button: "확인"
                });
                return false;
            })
        if (!emailSend)
            return;

        //성공시
        emailInput.disabled = true;
        checkEmailBtn.disabled = true;
        //버튼 색 푸른색으로 변경
        checkEmailBtn.classList.remove('btn-outline-secondary');
        checkEmailBtn.classList.add('btn-primary');
        checkEmailBtn.textContent = '사용가능';
        verifyNumberBox.style.display = 'block'; // 인증번호 입력 박스 보이기

    });


    phoneInput.addEventListener('input', function (e) {
        let value = e.target.value.replace(/\D/g, ''); // 숫자만 남김
        if (value.length > 11) value = value.slice(0, 11);

        let formatted = value;
        if (value.length > 7) {
            formatted = value.replace(/(\d{3})(\d{4})(\d{0,4})/, '$1 - $2 - $3');
        } else if (value.length > 3) {
            formatted = value.replace(/(\d{3})(\d{0,4})/, '$1 - $2');
        }
        e.target.value = formatted.replace(/ - $/, ''); // 마지막 하이픈 제거

    })

    const addressInputs = document.querySelectorAll('.address');
    addressInputs.forEach(input => {
        input.addEventListener('click', function () {
            new daum.Postcode({

                oncomplete: function (data) {
                    console.log(data);


                    let addr = ''; // 주소 변수

                    const extraAddr = data.buildingName; // 참고항목 변수


                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.

                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우

                        addr = data.roadAddress

                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress
                    }


                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.

                    // if (data.userSelectedType === 'R') {

                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)

                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    //
                    // if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    //
                    //     extraAddr += data.bname;
                    //
                    // // }
                    // // 건물명이 있고, 공동주택일 경우 추가한다.
                    // if (data.buildingName !== '' && data.apartment === 'Y') {
                    //
                    //     extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    //
                    // }

                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.


                    // 조합된 참고항목을 해당 필드에 넣는다.


                    //} else {

                    // document.getElementById("extraAddress").value = '';

                    // }


                    // 우편번호와 주소 정보를 해당 필드에 넣는다.

                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById("address").value = addr;
                    const detailInput = document.getElementById("addressDetails")
                    if (detailInput.value !== extraAddr)
                        detailInput.value = extraAddr ?
                            (detailInput.value ? detailInput.value + ' ' + extraAddr : extraAddr) :
                            detailInput.value;


                    // 커서를 상세주소 필드로 이동한다.
                    detailInput.focus();
                }

            }).open();
        });
    });


    document.getElementById('profileImage').addEventListener('change', function (e) {
        const file = e.target.files[0];
        const preview = document.getElementById('profilePreview');
        const defaultImg = 'https://www.svgrepo.com/show/343494/profile-user-account.svg';

        if (file) {
            if (!file.type.startsWith('image/')) {
                preview.src = defaultImg;
                alert('이미지 파일만 선택할 수 있습니다.');
                e.target.value = ''; // 파일 입력 초기화
                return;
            }
            const reader = new FileReader();
            reader.onload = function (evt) {
                preview.src = evt.target.result;
            };
            reader.readAsDataURL(file);
        } else {
            preview.src = defaultImg;
        }
    });
</script>


<jsp:include page="../include/footer.jsp"/>
