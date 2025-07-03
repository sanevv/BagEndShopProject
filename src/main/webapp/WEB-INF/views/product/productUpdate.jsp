<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../include/header.jsp"></jsp:include>

<div align="center" style="margin-bottom: 20px;">

    <div style="border: solid green 2px; width: 250px; margin-top: 20px; padding: 10px 0; border-left: hidden; border-right: hidden;">
        <span style="font-size: 15pt; font-weight: bold;">제품수정&nbsp;[관리자전용]</span>
    </div>
    <br/>

 
    <form name="prodInputFrm" enctype="multipart/form-data">

        <table id="tblProdInput" style="width: 80%;">
            <tbody>
                <tr>
                    <td width="25%" class="prodInputName" style="padding-top: 10px;">카테고리</td>
                    <td width="75%" align="left" style="padding-top: 10px;">
                        <select name="fk_cnum" class="infoData">
                            <option value="">:::선택하세요:::</option>
                            <c:forEach var="cvo" items="${cateList}">
                                <option value="${cvo.category_id}">${cvo.category_name}</option>
                            </c:forEach>
                        </select>
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품명</td>
                    <td align="left">
                        <input type="text" style="width: 300px;" name="product_name" class="box infoData" value="${pvo.product_id}" />
                        <span class="error">필수입력</span>
                    </td>
                </tr>
                
				<tr>
				    <td class="prodInputName">소재 / 사이즈</td>
				    <td>
				        <div style="display: flex; gap: 10px;">
				            <div style="flex: 1;">
				                <input type="text" style="width: 100%;" name="matter" class="box infoData" placeholder="소재 입력" />
				                <span class="error">필수입력</span>
				            </div>
				            <div style="flex: 1;">
				                <input type="text" style="width: 100%;" name="productSize" class="box infoData" placeholder="사이즈 입력" />
				                <span class="error">필수입력</span>
				            </div>
				        </div>
				    </td>
				</tr>               
                

                <tr>
                    <td class="prodInputName">제품이미지</td>
                    <td align="left">
                        <input type="file" name="pimage1" class="infoData img_file" accept="image/*" />
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName" style="padding-bottom: 10px; text-align: left;">추가이미지파일(선택)</td>
                    <td>
                        <span style="font-size: 10pt;">파일을 1개씩 마우스로 끌어 오세요</span>
                        <div id="fileDrop" class="fileDrop border border-secondary">
                        </div>
                       
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품수량</td>
                    <td align="left">
                        <input id="spinnerPqty" name="stock" value="1" style="width: 50px; height: 30px;" /> 개
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품정가</td>
                    <td align="left">
                        <input type="text" style="width: 100px;" name="price" class="box infoData" /> 원
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품설명</td>
                    <td align="left">
                        <input type="text" name="productInfo" class="box infoData">
                        <span class="error">필수입력</span>
                    </td>
                </tr>



                <tr>
                    <td class="prodInputName">제품상세설명</td>
                    <td align="left">
                        <textarea name="product_contents" rows="5" cols="60"></textarea>
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr style="height: 70px;">
                    <td colspan="2" align="center" style="border: none; padding: 50px 0;">
                        <input type="button" value="제품등록" id="btnRegister" style="width: 120px;" class="btn btn-info btn-lg mr-5" />
                        <input type="reset" value="취소" style="width: 120px;" class="btn btn-danger btn-lg" />
                    </td>
                </tr>
            </tbody>
        </table>

    </form>
</div>

<jsp:include page="../include/footer.jsp"></jsp:include>