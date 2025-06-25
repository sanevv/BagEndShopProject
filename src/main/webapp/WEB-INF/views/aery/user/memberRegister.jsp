임시

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
%>    

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" type="text/css" href="/aery/css/user/memberRegister.css" />

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/aery/js/user/memberRegister.js"></script>

<div class="row" id="registerRow">
   <div class="container mt-5" id="divRegisterFrm" style="max-width: 600px; width: 100%; margin: 0 auto;">
      <form name="registerFrm">
          <table id="tblMemberRegister">  
        	<colgroup>
				<col style="width:auto;">
				<col style="width:150px;">
			</colgroup>
	        <tbody>

	           <%-- 이메일 입력 --%>
	           <tr>
	               <td colspan="2">
	                  <label for="emailtext" id="emailtext">이메일<span class="star">*</span></label>
	               </td>
	           </tr>
	           
	           <tr>
	               <td colspan="2">
	                  <input type="text" name="email" id="email" maxlength="60" class="requiredInfo"/>
	                  <span class="error" style="display: block; margin-top: 5px;">이메일 형식에 맞지 않습니다.</span>
	               </td>
	           </tr>
	        
	           <tr>
	        		<td colspan="2">
	                  <button type="button" class="btn btn-dark" id="emailcheck" style="display: block; margin: 5px auto 15px;">이메일중복확인</button>
	                  <span id="emailCheckResult"></span>
	               </td>   
	        	</tr>
				
				
				<%-- 비밀번호 입력 --%>
				<tr>
	               <td colspan="2">
	                  <label for="pwdtext" id="pwdtext">비밀번호<span class="star">*</span></label>
	               </td>
	           </tr>			  
	           
	           <tr>
	               <td colspan="2">
	                  <input type="password" name="password" id="password" maxlength="15" class="requiredInfo"/>
	                  <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
	               </td>
	           </tr>
	           
	           <tr>
	        	   <td colspan="2">
	                  <span style="display: inline-flex; align-items: center; gap: 5px;">
     				     <img src="/aery/images/help.png" id="help_icon" style="width:14px; height:14px; margin-bottom: 15px;" />
      				     <span class="help" style="margin-bottom: 15px;">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16)</span>
    				  </span>
	               </td> 
	        	</tr>
	           
	           
	            <%-- 비밀번호 확인 --%>
	            <tr>
	               <td colspan="2">
	                  <label for="pwdchtext" id="pwdchtext">비밀번호 확인<span class="star">*</span></label>
	               </td>
	           </tr>			  
	           
	           <tr>
	               <td colspan="2">
	                  <input type="password" name="pwdcheck" id="pwdcheck" maxlength="15" class="requiredInfo"/>
	                  <span class="error" style="display: block; margin-top: 5px;">비밀번호가 일치하지 않습니다.</span>
	               </td>
	           </tr>
	           
	           
	           <%-- 이름 입력 --%>
	           <tr>
	               <td colspan="2">
	                  <label for="nametext" id="nametext" style="margin-top: 15px;">이름<span class="star">*</span></label>
	               </td>
	           </tr>			  
	           
	           <tr>
	               <td colspan="2">
	                  <input type="text" name="name" id="name" maxlength="30" class="requiredInfo"/>
	                  <span class="error" style="display: block; margin-top: 5px;">이름은 필수입력 사항입니다.</span>
	               </td>
	           </tr>
	            
	            
	           <%-- 휴대폰 --%>
	           <tr>
	               <td colspan="2">
	                  <label for="phonetext" id="phonetext" style="margin-top: 15px;">휴대폰<span class="star">*</span></label>
	               </td>
	           </tr>			  
	           
	           <tr>
	               <td colspan="2">
	                  <input type="text" name="hp1" id="hp1" maxlength="3" value="010" readonly style="width: 31%;" />&nbsp;-&nbsp; 
	                  <input type="text" name="hp2" id="hp2" size="6" maxlength="4" style="width: 31%;"/>&nbsp;-&nbsp;
	                  <input type="text" name="hp3" id="hp3" size="6" maxlength="4" style="width: 31%;"/>  
	                  <span class="error" style="display: block; margin-top: 5px;">휴대폰 형식이 아닙니다.</span>
	               </td>
	           </tr> 
	           
	           
	           <%-- 우편번호 --%>
	           <tr>
	               <td colspan="2">
	                  <label for="zipCodetext" id="zipCodetext" style="margin-top: 15px;">우편번호<span class="star">*</span></label>
	               </td>
	           </tr>			  
	           
	           <tr>
	               <td colspan="2">
	                  <input type="text" name="zipCode" id="zipCode" maxlength="5"/>
				      <button type="button" class="btn btn-dark" id="zipCodeSearch" style="margin: 5px 0 auto;">우편번호찾기</button>
	                  <span class="error" style="display: block; margin-top: 5px;">우편번호 형식에 맞지 않습니다.</span>
	               </td>
	           </tr> 
	           
	           
	           <%-- 주소 --%>
	           <tr>
	               <td colspan="2">
	                  <label for="addtext" id="addtext" style="margin-top: 15px;">주소<span class="star">*</span></label>
	               </td>
	           </tr>	
	          
	           <tr>
	               <td colspan="2">
	                  <input type="text" name="address" id="address" size="40" maxlength="200" placeholder="주소" style="margin-bottom: 5px;"/><br>
	                  <input type="text" name="addressDetails" id="addressDetails" size="40" maxlength="200" placeholder="상세주소"/>
	                  <span class="error" style="display: block; margin-top: 5px;">주소를 입력하세요.</span>
	               </td>
	           </tr>
	           
	        
	           <%-- 이용약관 --%>
	           <tr>
	               <td colspan="2"><hr style="border: 0; border-top: 1px solid #e9ecef; margin: 20px 0;"/>
	                  <label style="margin-top: 15px;">[필수] 이용약관 동의</label>
	                  <iframe src="/aery/iframe_agree/agree.html" width="100%" height="150px" style="border: solid 1px #e9ecef;"></iframe>
	               </td>
	           </tr>
	           
	           <tr>
				   <td colspan="2" style="padding-top: 5px;">
				      <label for="agree" style="font-size: 13px; color: #666666;">이용약관에 동의하십니까?</label>
				      <input type="checkbox" id="agree" style="margin-left: 10px; width:18px; height:18px; vertical-align: middle;"/>
				      <label for="agree" style="margin-left: 5px;">동의함</label>
				   </td>
				</tr>


	           <tr>
	               <td colspan="2">
        		      <div style="display: flex; justify-content: center; gap: 10px; margin-top: 20px;">
              	         <input type="button" class="btn btn-success btn-lg" style="background-color: black;" value="회원가입" onclick="goRegister()"/>
            		     <input type="reset" class="btn btn-danger btn-lg" style="background-color: #f05650; font-size: 14px;" value="취소하기" onclick="goReset()"/>
        		      </div>
    			   </td>
	           </tr>
	        </tbody>
     </table>
       
       <%--    
          <div>
          	 <button onclick="goGaib()">type이 없으면 submit 임</button>&nbsp; 
          	 <button type="button" onclick="goGaib()">type이 button 인것</button>&nbsp;
          	 <button type="submit">type이 submit 인 것</button>
          </div>
       --%>   
      </form>
   </div>
</div>

<jsp:include page="../../include/footer.jsp" />
