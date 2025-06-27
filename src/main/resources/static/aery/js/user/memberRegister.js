
let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지, 클릭을 안했는지 여부를 알아오기 위한 용도

$(function(){
	
	$('span.error').hide();
	$('input#email').focus();
	
 //	$('input#email').bind('blur', function(){ alert('email 에 있던 포커스를 잃어버렸습니다-1.'); });
 // 또는
 // $('input#email').blur(function(){ alert('email 에 있던 포커스를 잃어버렸습니다-2.'); });
 
	$('input#email').blur((e) => { 
		
	    const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
		// 이메일 정규표현식 객체 생성
		
		const bool = regExp_email.test($(e.target).val());
		
		if(!bool) {
			// 이메일이 정규표현식에 위배된 경우
				
		    $(e.target).parent().find('span.error').show();
		}
		else {
			// 이메일이 정규표현식에 맞는 경우

			$(e.target).parent().find('span.error').hide();
		}
				
	});	// end of $('input#email').blur((e) => {})-------------------	
	
	
	$('input#password').blur((e) => { 
			   
	 // alert($(e.target).val());
		
	    const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
		// 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
		
		const bool = regExp_pwd.test($(e.target).val());
		
		if(!bool) {
			// 암호가 정규표현식에 위배된 경우
				
		    $(e.target).parent().find('span.error').show();
		}
		else {
			// 암호가 정규표현식에 맞는 경우
		
			$(e.target).parent().find('span.error').hide();
		}
				
	});	// end of $('input#password').blur((e) => {})-------------------	


	$('input#pwdcheck').blur((e) => { 
	 	
		if( $('input#password').val() != $(e.target).val() ) {
			// 암호와 암호확인값이 틀린 경우
				
		    $(e.target).parent().find('span.error').show();
		}
		else {
			// 암호와 암호확인값이 같은 경우
			
			$(e.target).parent().find('span.error').hide();
		}
				
	});	// end of $('input#pwdcheck').blur((e) => {})-------------------	

	
	$('input#name').blur((e) => { 
		   
		 // alert($(e.target).val());
			
			const name = $(e.target).val().trim();
			if(name == "") {
				// 입력하지 않거나 공백만 입력했을 경우
				
			    $(e.target).parent().find('span.error').show();
			}
			else {
				// 공백이 아닌 글자를 입력했을 경우
			
				$(e.target).parent().find('span.error').hide();
			}
				
		});	// end of $('input#name').blur((e) => {})-------------------
		
		
	$('input#hp2').blur((e) => { 
			
	    const regExp_hp2 = /^[1-9][0-9]{3}$/; 
		// 연락처 국번( 숫자 4자리인데 첫번째 숫자는 1-9 이고 나머지는 0-9) 정규표현식 객체 생성
		
		const bool = regExp_hp2.test($(e.target).val());
		
		if(!bool) {
			// 연락처 국번이 정규표현식에 위배된 경우
				
		    $(e.target).closest('td').find('span.error').show();
		}
		else {
			// 연락처 국번이 정규표현식에 맞는 경우
		
			$(e.target).closest('td').find('span.error').hide();
		}
				
	});	// end of $('input#hp2').blur((e) => {})-------------------	
	
	
	$('input#hp3').blur((e) => { 
				
	 // const regExp_hp3 = /^[0-9]{4}$/;
	 // 또는
		const regExp_hp3 = /^\d{4}$/;
		// 연락처 마지막 4자리( 숫자만 되어야 함) 정규표현식 객체 생성
		
		const bool = regExp_hp3.test($(e.target).val());
		
		if(!bool) {
			// 연락처 마지막 4자리가 정규표현식에 위배된 경우
			
		    $(e.target).closest('td').find('span.error').show();
		}
		else {
			// 연락처 마지막 4자리가 정규표현식에 맞는 경우
		
			$(e.target).closest('td').find('span.error').hide();
		}
				
	});	// end of $('input#hp3').blur((e) => {})-------------------	
	
	
	$('input#zipCode').blur((e) => { 
					
	 // const regExp_hp3 = /^[0-9]{4}$/;
	 // 또는
		const regExp_zipCode = /^\d{5}$/;
		// 숫자 5자리만 들어오도록 정규표현식 객체 생성
		
		const bool = regExp_zipCode.test($(e.target).val());
		
		if(!bool) {
			// 우편번호가 정규표현식에 위배된 경우
		
		    $(e.target).parent().find('span.error').show();
		}
		else {
			
			$(e.target).parent().find('span.error').hide();
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


	 /////////////////////////////////////////////////////////////////////
	 
	 
	 // "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 시작 // 
	 $('button#emailcheck').click(function(){
		b_emailcheck_click = true;
		// "이메일중복확인" 을 클릭했는지, 클릭을 안했는지 여부를 알아오기 위한 용도
		
		// 입력하고자 하는 이메일이 데이터베이스 테이블에 존재하는지, 존재하지 않는지 알아와야 한다. 
        /*
      		Ajax (Asynchronous JavaScript and XML)란?                         
 		    ==> 이름만 보면 알 수 있듯이 '비동기 방식의 자바스크립트와 XML' 로서     
 		        Asynchronous JavaScript + XML 인 것이다.
 		        한마디로 말하면, Ajax 란? Client 와 Server 간에 XML 데이터를 JavaScript 를 사용하여 비동기 통신으로 주고 받는 기술이다.
 		        하지만 요즘에는 데이터 전송을 위한 데이터 포맷방법으로 XML 을 사용하기 보다는 JSON(Javascript Standard Object Notation) 을 더 많이 사용한다. 
 		        참고로 HTML은 데이터 표현을 위한 포맷방법이다.                             
 		        그리고, 비동기식이란 어떤 하나의 웹페이지에서 여러가지 서로 다른 다양한 일처리가 개별적으로 발생한다는 뜻으로서, 
 		        어떤 하나의 웹페이지에서 서버와 통신하는 그 일처리가 발생하는 동안 일처리가 마무리 되기전에 또 다른 작업을 할 수 있다는 의미이다.
      	*/
		
		// === 두번째 방법(jquery Ajax) === //
		$.ajax({
			url:"emailDuplicateCheck.team1",
			data:{"email":$('input#email').val()},
			type:"post",
		//	async:true, 
			dataType:"json",  // Javascript Standard Object Notation.  dataType은 /MyMVC/member/emailDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다. 
		                      // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
		                      // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다.
			
			success:function(json){
			//	console.log(json);
				// {"isExists":false}
				// {"isExists":true}
				// json 은 emailDuplicateCheck.up 을 통해 가져온 결과물인 {"isExists":true} 또는 {"isExists":false} 로 되어지는 object 타입의 결과물이다. 
				
			//	console.log("~~~~ json 의 데이터타입 : ", typeof json);
				// ~~~~ json 의 데이터타입 :  object
				
				if(json.isExists) {
					// 입력한 email 이 이미 사용중이라면 
					$('span#emailCheckResult')
					.html($('input#email').val() + "은 이미 사용중 이므로 다른 이메일을 입력하세요")
					.css({"color":"#999999", "font-size": "14px"});
										
					$('input#email').val("");
				}
				else {
					// 입력한 email 이 존재하지 않는 경우라면 
					$('span#emailCheckResult')
					.html($('input#email').val() + "은 사용가능 합니다.")
					.css({"color":"#999999", "font-size": "14px"});
				}
				
			},
			
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }				  
		});
			
	 });
	 // "이메일중복확인" 을 클릭했을 때 이벤트 처리하기 끝 //	 
	 
	
	 // 이메일값이 변경되면 가입하기 버튼 클릭시 "이메일중복확인" 을 클릭했는지 클릭안했는지 알아보기 위한 용도 초기화 시키기 
 	 $('input#email').bind('change', function(){
		b_emailcheck_click = false;
 	 });
	 	
});// end of $(function(){})--------------------------------


// Function Declaration

// "가입하기" 버튼 클릭시 호출되는 함수
function goRegister() {
	
	// **** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 **** //
	let b_requiredInfo = true;
	
	$('input.requiredInfo').each(function(index, elmt){
		const data = $(elmt).val().trim();
		if(data == "") {
			alert("*표시된 필수입력사항은 모두 입력하셔야 합니다.");
			b_requiredInfo = false;
			return false; // break; 라는 뜻이다.
		}
	});
	
	if(!b_requiredInfo) {
		return; // goRegister() 함수를 종료한다.
	}
	// **** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 **** //
	
	
	// **** "이메일중복확인" 을 클릭했는지 검사하기 시작 **** //
	if(!b_emailcheck_click) {
		// "이메일중복확인" 을 클릭 안 했을 경우
		
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; // goRegister() 함수를 종료한다.
	}	
    // **** "이메일중복확인" 을 클릭했는지 검사하기 끝 **** //
	
	
	// **** 우편번호 및 주소에 값을 입력했는지 검사하기 시작 **** //
	let b_addressInfo = true;
	
	const arr_addressInfo = [];
	arr_addressInfo.push($('input#zipCode').val());
	arr_addressInfo.push($('input#address').val());
	
	for(let addressInfo of arr_addressInfo) {
		if(addressInfo.trim() == "") {
			alert("우편번호 및 주소를 입력하셔야 합니다.");
			b_addressInfo = false;
			break;
		}
	}// end of for---------------------
	
	if(!b_addressInfo) {
		return; // goRegister() 함수를 종료한다.
	}
	// **** 우편번호 및 주소에 값을 입력했는지 검사하기 시작 **** //
	
	
	// **** 약관에 동의를 했는지 검사하기 시작 **** //
	const checkbox_checked_length = $('input:checkbox[id="agree"]:checked').length;
	
	if(checkbox_checked_length == 0) {
		alert("이용약관에 동의하셔야 합니다.");
		return; // goRegister() 함수를 종료한다.
	}
	// **** 약관에 동의를 했는지 검사하기 끝 **** //
	
	const frm = document.registerFrm;
	frm.method = "post";
 //	frm.action = "memberRegister.up";
	frm.submit();
	
}// end of function goRegister()-----------------------------


 // "취소하기" 버튼 클릭시 호출되는 함수
 function goReset() {

	document.registerFrm.reset();

    $('span.error').hide();
    $('#emailCheckResult').html('');
    $('#email').focus();

    b_emailcheck_click = false;
}// end of  function goReset(){}-----------
