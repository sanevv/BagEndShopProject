<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
%>    

<jsp:include page="../../include/header.jsp" />

<link rel="stylesheet" type="text/css" href="/aery/css/user/memberRegister.css" />

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/aery/js/user/memberRegister.js"></script>

	<div class="container mt-5" style="border: solid 0px red;">
	   <div class="row" id="registerRow">
	      <div class="col-12 col-md-5 mx-auto" style="max-width: 600px;">
		      <form name="registerFrm">
		          <table id="tblMemberRegister">  
			        <tbody>
		
			           <%-- 이메일 입력 --%>
			           <tr>
			               <td>
			                  <label for="emailtext" id="emailtext">이메일<span class="star">*</span></label>
			               </td>
			           </tr>
			           
			           <tr>
			               <td>
			                  <input type="text" name="email" id="email" maxlength="60" class="requiredInfo" style="padding-left: 10px;"/>
			                  <span class="error" style="display: block; margin-top: 5px;">이메일 형식에 맞지 않습니다.</span>
			               </td>
			           </tr>
			        
			           <tr>
			        		<td>
			                  <button type="button" class="btn btn-dark" id="emailcheck" style="display: block; margin: 5px auto 15px;">이메일중복확인</button>
			                  <span id="emailCheckResult"></span>
			               </td>   
			        	</tr>
						
						
						<%-- 비밀번호 입력 --%>
						<tr>
			               <td>
			                  <label for="pwdtext" id="pwdtext">비밀번호<span class="star">*</span></label>
			               </td>
			           </tr>			  
			           
			           <tr>
			               <td>
			                  <input type="password" name="password" id="password" maxlength="15" class="requiredInfo" style="padding-left: 10px;"/>
			                  <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
			               </td>
			           </tr>
			           
			           <tr>
			        	   <td>
			                  <div style="display: flex; align-items: center; justify-content: flex-start; gap: 6px; margin-top: 5px;">
      						     <img src="/aery/images/help.png" id="help_icon" style="width: 16px; height: 16px;" />
      							 <span style="font-size: 11px; color: #999999; line-height: 1;">(영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~16)</span>
    					 	  </div>
			               </td> 
			        	</tr>
			           
			           
			            <%-- 비밀번호 확인 --%>
			            <tr>
			               <td>
			                  <label for="pwdchtext" id="pwdchtext" style="margin-top: 15px;">비밀번호 확인<span class="star">*</span></label>
			               </td>
			           </tr>			  
			           
			           <tr>
			               <td>
			                  <input type="password" name="pwdcheck" id="pwdcheck" maxlength="15" class="requiredInfo" style="padding-left: 10px;"/>
			                  <span class="error" style="display: block; margin-top: 5px;">비밀번호가 일치하지 않습니다.</span>
			               </td>
			           </tr>
			           
			           
			           <%-- 이름 입력 --%>
			           <tr>
			               <td>
			                  <label for="nametext" id="nametext" style="margin-top: 15px;">이름<span class="star">*</span></label>
			               </td>
			           </tr>			  
			           
			           <tr>
			               <td>
			                  <input type="text" name="name" id="name" maxlength="30" class="requiredInfo" style="padding-left: 10px;"/>
			                  <span class="error" style="display: block; margin-top: 5px;">이름은 필수입력 사항입니다.</span>
			               </td>
			           </tr>
			            
			            
			           <%-- 휴대폰 --%>
			           <tr>
			               <td>
			                  <label for="phonetext" id="phonetext" style="margin-top: 15px;">휴대폰<span class="star">*</span></label>
			               </td>
			           </tr>			  
			           
			           <tr>
			               <td>
			                  <div class="phoneGroup">
				                  <input type="text" name="hp1" id="hp1" maxlength="3" value="010" readonly style="padding-left: 10px;"/>
								  <span>-</span>
				                  <input type="text" name="hp2" id="hp2" size="6" maxlength="4">
				                  <span>-</span>
				                  <input type="text" name="hp3" id="hp3" size="6" maxlength="4"/>
				              </div>   
				                  <span class="error" style="display: block; margin-top: 5px;">휴대폰 형식이 아닙니다.</span>
			               </td>
			           </tr> 
			           
			           
			           <%-- 추후 회원가입시 휴대전화 인증 필요할 시, js 구현 필요
			           <tr>
			        		<td>
			                  <button type="button" class="btn btn-dark" id="phonecheck" style="display: block; margin: 5px auto 15px;">인증번호 받기</button>
			                  <span id="phoneCheckResult"></span>
			               </td>   
			           </tr>
			           
			           
			           // 인증번호
			           <tr>
			               <td>
			                  <label for="veriCodetext" id="veriCodetext">인증번호<span class="star">*</span></label>
			               </td>
			           </tr>		
			           
			           <tr>
			               <td>
			                  <div class="veriCodeGroup">
			                  	<input type="text" name="veriCode" id="veriCode" maxlength="5" style="padding-left: 10px;"/>
						      	<button type="button" class="btn btn-dark" id="veriCodeCheck">확인</button>
			                  </div>
			                  <span class="error" style="display: block; margin-top: 5px;">인증번호가 일치하지 않습니다.</span>
			               </td>
			           </tr> 
			           --%>
			           
			           
			           <%-- 우편번호 --%>
			           <tr>
			               <td>
			                  <label for="zipCodetext" id="zipCodetext" style="margin-top: 15px;">우편번호<span class="star">*</span></label>
			               </td>
			           </tr>			  
			           
			           <tr>
			               <td>
			                  <div class="zipCodeGroup">
			                  	<input type="text" name="zipCode" id="zipCode" maxlength="5" style="padding-left: 10px;"/>
						      	<button type="button" class="btn btn-dark" id="zipCodeSearch">우편번호찾기</button>
			                  </div>
			                  <span class="error" style="display: block; margin-top: 5px;">우편번호 형식에 맞지 않습니다.</span>
			               </td>
			           </tr> 
			           
			           
			           <%-- 주소 --%>
			           <tr>
			               <td>
			                  <label for="addtext" id="addtext" style="margin-top: 15px;">주소<span class="star">*</span></label>
			               </td>
			           </tr>	
			          
			           <tr>
			               <td>
			                  <input type="text" name="address" id="address" size="40" maxlength="200" placeholder="주소" style="margin-bottom: 5px; padding-left: 10px;"/><br>
			                  <input type="text" name="addressDetails" id="addressDetails" size="40" maxlength="200" placeholder="상세주소" style="padding-left: 10px;"/>
			                  <span class="error" style="display: block; margin-top: 5px;">주소를 입력하세요.</span>
			               </td>
			           </tr>
			           
			        
			           <%-- 이용약관 --%>
			           <tr>
			               <td>
			                  <hr style="border: 0; border-top: 1px solid #e9ecef; margin: 20px 0;" />
    						  <label style="margin-top: 15px;">[필수] 이용약관 동의</label>
			           
			          		  <div style="border: 1px solid #e9ecef; height: 150px; overflow-y: auto; overflow-x: hidden; margin-top: 5px;">
      							<iframe src="/aery/iframe_agree/agree.html" width="100%" height="100%" style="border: none;"></iframe>
    						  </div>
			          	   </td> 
			          	   
					   <tr>   
						   <td>
						      <label for="agree" style="font-size: 13px; color: #666666;">이용약관에 동의하십니까?</label>
						      <input type="checkbox" id="agree" style="margin-left: 10px; width:18px; height:18px; vertical-align: middle;"/>
						      <label for="agree" style="margin-left: 5px;">동의함</label>
						   </td>
						</tr>
		
		
			           <tr>
			               <td>
		        		      <div style="display: flex; justify-content: center; margin-top: 20px;">
		              	         <input type="button" class="btn btn-success btn-lg" style="background-color: black;" value="회원가입" onclick="goRegister()"/>
		            		     <input type="button" class="btn btn-danger btn-lg" id="cancel" style="background-color: #f05650; font-size: 14px;" value="취소하기" onclick="goReset()"/>
		        		      </div>
		    			   </td>
			           </tr>
			        </tbody>
		      	  </table>
		      </form>
		   </div>
	   </div>
	</div>

<jsp:include page="../../include/footer.jsp" />
