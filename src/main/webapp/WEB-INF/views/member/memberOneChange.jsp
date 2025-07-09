<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../include/header.jsp"/>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="/css/member/memberEdit.css" />

<script type="text/javascript">

    $(function (){

        $('.error').hide(); // 에러 메시지 숨김

        $('input#password').blur((e) => {

            // alert($(e.target).val());

            const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
            // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성

            const bool = regExp_pwd.test($(e.target).val());

            if(!bool) {
                // 암호가 정규표현식에 위배된 경우

                $(e.target).parent().find('.error').show();
            }
            else {
                // 암호가 정규표현식에 맞는 경우

                $(e.target).parent().find('.error').hide();
            }

        });	// end of $('input#password').blur((e) => {})-------------------


        $('input#pwdcheck').blur((e) => {

            if( $('input#password').val() != $(e.target).val() ) {
                // 암호와 암호확인값이 틀린 경우

                $(e.target).parent().find('.error').show();
            }
            else {
                // 암호와 암호확인값이 같은 경우

                $(e.target).parent().find('.error').hide();
            }

        });	// end of $('input#pwdcheck').blur((e) => {})-------------------


        $('input#name').blur((e) => {

            // alert($(e.target).val());

            const name = $(e.target).val().trim();
            if(name == "") {
                // 입력하지 않거나 공백만 입력했을 경우

                $(e.target).parent().find('.error').show();
            }
            else {
                // 공백이 아닌 글자를 입력했을 경우

                $(e.target).parent().find('.error').hide();
            }

        });	// end of $('input#name').blur((e) => {})-------------------


        $('input#hp2').blur((e) => {

            const regExp_hp2 = /^[1-9][0-9]{3}$/;
            // 연락처 국번( 숫자 4자리인데 첫번째 숫자는 1-9 이고 나머지는 0-9) 정규표현식 객체 생성

            const bool = regExp_hp2.test($(e.target).val());

            if(!bool){
                // 연락처 국번이 정규표현식에 위배된 경우

                $('form#memberOneChangeFrm :input').prop('disabled', true); // table 태그내의 모든 input 태그를 잡을 때는 공백 :input 으로 표시한다.
                $(e.target).prop('disabled', false); // ('disabled', true) 는 input 태그 내의 기능을 정지, $(e.target).prop('disabled', false)는 $('input#name') 만 기능을 활성화
                $(e.target).val('').focus();

                // $(e.target).next().show(); // .next() 는 형제 태크에서 다음 태그를 말하는 것이고, .show() 는 .hide() 했던 것을 다시 보여라 라는 말이다.
                // 또는
                $(e.target).parent().find('.error').show();
            }
            else {
                // 연락처 국번이 정규표현식에 맞는 경우

                $('form#memberOneChangeFrm :input').prop('disabled', false);

                // $(e.target).next().hide(); // .next() 는 형제 태크에서 다음 태그를 말하는 것이고, .show() 는 .hide() 했던 것을 다시 보여라 라는 말이다.
                // 또는
                $(e.target).parent().find('.error').hide(); // .parent() 로 상위 태그인 td 태그로 올라가고 .find() 로 자식 태그를 다시 불러온다.
            }
        }); // end of $('input#hp2').blur((e) =>{})

        // 연락처 유효성 검사
        $('input#hp3').blur((e) => {

            // const regExp_hp3 = /^[0-9]{4}$/;
            // 또는
            const regExp_hp3 = /^\d{4}$/;
            // 연락처 마지막 4자리(숫자만 되어야 함) 정규표현식 객체 생성

            const bool = regExp_hp3.test($(e.target).val());

            if(!bool){
                // 연락처 마지막 4자리가 정규표현식에 위배된 경우

                $('form#memberOneChangeFrm :input').prop('disabled', true); // table 태그내의 모든 input 태그를 잡을 때는 공백 :input 으로 표시한다.
                $(e.target).prop('disabled', false); // ('disabled', true) 는 input 태그 내의 기능을 정지, $(e.target).prop('disabled', false)는 $('input#name') 만 기능을 활성화
                $(e.target).val('').focus();

                // $(e.target).next().show(); // .next() 는 형제 태크에서 다음 태그를 말하는 것이고, .show() 는 .hide() 했던 것을 다시 보여라 라는 말이다.
                // 또는
                $(e.target).parent().find('.error').show();
            }
            else {
                // 연락처 마지막 4자리가 정규표현식에 맞는 경우

                $('form#memberOneChangeFrm :input').prop('disabled', false);
                
                // $(e.target).next().hide(); // .next() 는 형제 태크에서 다음 태그를 말하는 것이고, .show() 는 .hide() 했던 것을 다시 보여라 라는 말이다.
                // 또는
                $(e.target).parent().find('.error').hide(); // .parent() 로 상위 태그인 td 태그로 올라가고 .find() 로 자식 태그를 다시 불러온다.
            }
        }); // end of $('input#hp3').blur((e) =>{})


        $('input#zipCode').blur((e) => {

            // const regExp_hp3 = /^[0-9]{4}$/;
            // 또는
            const regExp_zipCode = /^\d{5}$/;
            // 숫자 5자리만 들어오도록 정규표현식 객체 생성

            const bool = regExp_zipCode.test($(e.target).val());

            if(!bool) {
                // 우편번호가 정규표현식에 위배된 경우

                $(e.target).parent().find('.error').show();
            }
            else {

                $(e.target).parent().find('.error').hide();
            }

        });	// end of $('input#zipCode').blur((e) => {})-------------------


        //////////////////////////////////////////////////////////////////////

        /*
            >>>> .prop() 와 .attr() 의 차이 <<<<
                 .prop() ==> form 태그내에 사용되어지는 엘리먼트의 disabled, selected, checked 의 속성값 확인 또는 변경하는 경우에 사용함.
                 .attr() ==> 그 나머지 엘리먼트의 속성값 확인 또는 변경하는 경우에 사용함.
        */

        // 우편번호를 읽기전용(readonly)로 만들기
        $('input#zipCode').attr('readonly', true);

        // 주소를 읽기전용(readonly)로 만들기
        $('input#address').attr('readonly', true);

        // ==== "우편번호찾기"를 클릭했을때 이벤트 처리하기 ==== //
        $('button#zipCodeSearch').click(function(){
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    let addr = ''; // 기본 주소
                    let extraAddr = ''; // 참고 항목 (법정동명, 건물명 등)

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;

                        // 법정동명이 있을 경우
                        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                            extraAddr += data.bname;
                        }

                        // 공동주택 건물명이 있을 경우
                        if (data.buildingName !== '' && data.apartment === 'Y') {
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }

                        // 참고항목이 있으면 괄호로 묶어서 주소 뒤에 붙임
                        if (extraAddr !== '') {
                            addr += ' (' + extraAddr + ')';
                        }
                    } else {
                        addr = data.jibunAddress;
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById("zipCode").value = data.zonecode;
                    document.getElementById("address").value = addr;
                    //   document.getElementById("addressDetails").value = "";

                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("addressDetails").focus();
                }

            }).open();

        });// end of $('button#zipCodeSearch').click(function(){})---------------


        $('input#email').val("${requestScope.loginUser.email}");
        $('input#name').val("${requestScope.loginUser.name}");
        $('input#hp1').val("${requestScope.hp1}");
        $('input#hp2').val("${requestScope.hp2}");
        $('input#hp3').val("${requestScope.hp3}");

        $('button#update').on('click', function(){
            goChange();
        }); // end of $('button#update').on('click', function(){})-------------------

        // 전화번호 중복확인 버튼 제어
        const originalHp2 = "${requestScope.hp2}";
        const originalHp3 = "${requestScope.hp3}";

        const hp2Input = $('input#hp2');
        const hp3Input = $('input#hp3');
        const phoneCheckBtn = $('button#checkThePhoneNumberDuplicate');

        //  초기 로딩 시 비교해서 버튼 숨기기 또는 보이기
        if (hp2Input.val() === originalHp2 && hp3Input.val() === originalHp3) {
            phoneCheckBtn.hide();
        } else {
            phoneCheckBtn.show();
        }

        // 입력 변경 시 버튼 상태 업데이트
        hp2Input.on('input', togglePhoneCheckButton);
        hp3Input.on('input', togglePhoneCheckButton);

        function togglePhoneCheckButton() {
            const currentHp2 = hp2Input.val();
            const currentHp3 = hp3Input.val();

            if (currentHp2 === originalHp2 && currentHp3 === originalHp3) {
                phoneCheckBtn.hide();
            } else {
                phoneCheckBtn.show();
            }
        }

        //  중복확인 버튼 클릭 시 로직
        phoneCheckBtn.on('click', function() {
            const phoneNumber = $('input#hp1').val() + hp2Input.val() + hp3Input.val();

            $.ajax({
                url: "/api/member/exist-phone",
                type: "GET",
                data: { phoneNumber: phoneNumber },
                success: function(response) {
                    if (response) {
                        alert('사용 가능한 휴대폰 번호입니다.');
                        phoneCheckBtn.hide(); // 확인 완료 시 숨김
                    } else {
                        alert('이미 사용중인 휴대폰 번호입니다.');
                        hp2Input.val("").focus();
                        hp3Input.val("");
                    }
                },
                error: function() {
                    alert('휴대폰 번호 중복 확인에 실패했습니다.');
                }
            });
        });


    }); // end of $(function(){})

    function goChange(){

        // AJAX 요청
        $.ajax({
            type: 'POST',
            url: '/api/member/memberOneChange',
            data: {
                password: $('#password').val(),
                name: $('#name').val(),
                hp1: $('#hp1').val(),
                hp2: $('#hp2').val(),
                hp3: $('#hp3').val(),
                zipCode: $('#zipCode').val(),
                address: $('#address').val(),
                addressDetails: $('#addressDetails').val()
            },
            success: function(response) {
                alert('회원 정보가 변경되었습니다.');
                location.href = '/'; // 변경이 성공하면 다시 홈으로 이동
            },
            error: function(xhr, status, error) {
                alert('회원 정보 변경에 실패했습니다. 다시 시도해주세요.');
            }
        });

    }


</script>

<div class="container mt-5">
    <div class="row">

        <!-- 좌측 마이페이지 메뉴 -->
        <div class="mypageMenu col-lg-3 col-md-4">
            <jsp:include page="../include/mypageMenu.jsp"/>
        </div>

        <!-- 내 정보 수정 -->
		<div class="memberOneChange-content col-lg-9 col-md-8" style="margin-top: 50px;">
	        <div class="memberOneChange-header">
	            <h4 class="memberOneChange-title">내 정보 수정</h4>
	        </div>		
				
            <form name="memberOneChangeFrm" id="memberOneChangeFrm">
            
	            <%-- 이메일 입력 --%>
	            <div class="Change-row">
	                <label for="email">이메일<span class="star">*</span></label>
	                <div class="input-box">
	                    <input type="text" id="email" readonly onfocus="this.blur()" onkeydown="return false;" />
	                </div>
	            </div>
	
	            <%-- 비밀번호 입력 --%>
	            <div class="Change-row">
	                <label for="password">비밀번호<span class="star">*</span></label>
	                <div class="input-box">
	                    <input type="password" id="password" maxlength="15" />
	                    <div class="error">암호는 영문자, 숫자, 특수기호 포함 8~15자로 입력하세요.</div>
	                    <div class="form-info">
	                        <img src="/aery/images/help.png" alt="도움말" />
	                        <span>(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8~15자)</span>
	                    </div>
	                </div>
	            </div>
	
	            <%-- 비밀번호 확인 --%>
	            <div class="Change-row">
	                <label for="pwdcheck">비밀번호 확인<span class="star">*</span></label>
	                <div class="input-box">
	                    <input type="password" name="pwdcheck" id="pwdcheck" maxlength="15"/>
	                    <div class="error">비밀번호가 일치하지 않습니다.</div>
	                </div>
	            </div>
	
	            <%-- 이름 입력 --%>
	            <div class="Change-row">
	                <label for="name">이름<span class="star">*</span></label>
	                <div class="input-box">
	                    <input type="text" name="name" id="name" maxlength="30"/>
	                    <div class="error">이름은 필수입력 사항입니다.</div>
	                </div>
	            </div>
	
	            <%-- 휴대폰 --%>
	            <div class="Change-row">
	                <label for="phonetext">휴대폰<span class="star">*</span></label>
	                <div class="input-box">
	                    <div class="phoneGroup">
	                        <input type="text" name="hp1" id="hp1" maxlength="3" value="010" readonly />
	                        <span>-</span>
	                        <input type="text" name="hp2" id="hp2" maxlength="4" />
	                        <span>-</span>
	                        <input type="text" name="hp3" id="hp3" maxlength="4" />
	                    </div>
	                    <div class="error">휴대폰 형식이 아닙니다.</div>
	                </div>
	            </div>
	
	            <%-- 우편번호 --%>
	            <div class="Change-row">
	                <label for="zipCode">우편번호<span class="star">*</span></label>
	                <div class="input-box">
	                    <div class="zipCodeGroup">
	                        <input type="text" name="zipCode" id="zipCode" maxlength="5" />
	                        <button type="button" class="btn btn-dark" id="zipCodeSearch">주소검색</button>
	                    </div>
	                    <div class="error">주소 형식에 맞지 않습니다.</div>
	                </div>
	            </div>
	
	            <%-- 주소 --%>
	            <div class="Change-row">
	                <label for="address">주소<span class="star">*</span></label>
	                <div class="input-box">
	                    <input type="text" name="address" id="address" maxlength="200" placeholder="주소" style="margin-bottom: 5px;" />
	                    <input type="text" name="addressDetails" id="addressDetails" maxlength="200" placeholder="상세주소" />
	                    <div class="error">주소를 입력하세요.</div>
	                </div>
	            </div>
	
	            <div class="hr-line-wrapper">
	                <hr class="hr-line" />
	            </div>
	
	            <%-- 이용약관 --%>
	            <div class="Change-row">
	                <label>[필수] 이용약관 동의</label>
					<div class="input-box">
	    				<div class="agree-box">
	        				<iframe src="/aery/iframe_agree/agree.html" width="100%" height="100%" style="border: none;"></iframe>
	    				</div>
	
		    			<div class="agree-check">
		        			<label for="agree">이용약관에 동의하십니까?</label>
		        			<input type="checkbox" id="agree" style="margin-left: 5px;"/>
		        			<label for="agree" style="margin-left: 5px; font-size: 14px;">동의함</label>
		    			</div>
					</div>
				</div>
	
	
            	<div class="Change-row button-row">
				    <div class="button-box">
				        <input type="button" id="update" class="btn btn-success btn-lg" style="background-color: black;" value="수정하기" onclick="goChange()" />
				        <input type="reset" class="btn btn-danger btn-lg" id="cancel" style="background-color: #fff; color: black; border:solid 1px #e9ecef; font-size: 14px;" value="취소하기" onclick="goReset()"/>
				    </div>
				</div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>