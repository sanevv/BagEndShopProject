<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"></jsp:include>

<!-- jQuery UI CSS -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<!-- jQuery UI JS -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<style type="text/css">

#formContainer {
    width: 80%;
    margin: 80px auto;         /* 위아래 여백 & 중앙정렬 */
    padding: 40px 30px;
    background-color: #fff;
    box-shadow: 0 4px 16px rgba(0,0,0,0.1);
    border-radius: 12px;
    box-sizing: border-box;
}


#tblProdInput {
    width: 80%;
    border-collapse: collapse;
}


.prodInputName {
    font-weight: bold;
    padding: 15px 10px;
    vertical-align: top;
    background-color: #f5f5f5;
    border-bottom: 1px solid #ddd;
}


#tblProdInput td {
    padding: 15px 10px;
    border-bottom: 1px solid #eee;
}


input[type="text"],
input[type="file"],
select,
textarea {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
}


textarea {
    resize: vertical;
    height: 120px;
}


#fileDrop {
    width: 100%;
    height: 100px;
    background-color: #f8f9fa;
    border: 2px dashed #ccc;
    border-radius: 8px;
    margin-top: 10px;
    padding: 20px;
    text-align: center;
    color: #777;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

#fileDrop:hover {
    background-color: #eef3f7;
    border-color: #999;
    color: #444;
}


.btn-wrapper {
    text-align: center;
    margin-top: 40px;
}
#fileDrop {
    width: 100%;
    height: 200px;
    background-color: #f8f9fa;
    border: 2px dashed #ccc;
    border-radius: 8px;
    margin-top: 10px;
    padding: 20px;
    text-align: center;
    color: #777;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

#fileDrop:hover {
    background-color: #eef3f7;
    border-color: #999;
    color: #444;
}
.delete:hover{
	cursor: pointer;
}
</style>

<script type="text/javascript">


$(function() {
    $('span.error').hide();
	let file_arr = [];
    // 수량 스피너
    $("input#spinnerPqty").spinner({
        spin: function(event, ui) {
            if (ui.value > 100) {
                $(this).spinner("value", 100);
                return false;
            } else if (ui.value < 1) {
                $(this).spinner("value", 1);
                return false;
            }
        }
    });

    // 드래그앤드롭 이벤트 등록
    $('div#fileDrop')
        .on("dragenter", function(e) {
            e.preventDefault();
            e.stopPropagation();
        })
        .on("dragover", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#ffd8d8");
        })
        .on("dragleave", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#fff");
        })
        .on("drop", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#fff");

            const files = e.originalEvent.dataTransfer.files;


            
            if (files && files.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    const f = files[i];
                    const fileType = f.type;
                    let fileSize = f.size / 1024 / 1024; // MB 단위

                    if (!(fileType === 'image/jpeg' || fileType === 'image/png')) {
                        alert("JPEG 또는 PNG 형식만 업로드 가능합니다.");
                        return;
                    } else if (fileSize >= 10) {
                        alert("10MB 미만 파일만 업로드 가능합니다.");
                        return;
                    } else {
                        file_arr.push(f);
                    }
	
                    $('div#fileDrop').empty();
                    
                    for(let imgfile of file_arr){
                    	const fileName = imgfile.name;
                    	
	                  	const v_html = 
	                	   `<div class='fileList'>
	                	     <span class='delete'>&times;</span>
	                	     <span class='fileName'>\${fileName}</span>
	                	     <span class='clear'></span>
	                	   </div>`;
	                	
	                 	$(this).append(v_html); 
	                    
                    } // end of  for(let imgfile of file_arr){} --------------
                    
                }

            }
            file_arr_copy = file_arr;
        });
    
    	$(document).on('click','span.delete',function(e){
    		
        	const idx = $('span.delete').index($(e.target));
        	      	
            file_arr_copy.splice(idx, 1);	        
	        $(e.target).parent().remove();
    	});
    	
    	$(document).on("click","input:button[id='btnRegister']",function(e){
    		console.log(file_arr_copy);
    		let is_infoData_OK = true;
	    	   
	    	  $('span.error').hide();
	    	  
	    	   $('.infoData').each(function(index, elmt){
	    		   const val = $(elmt).val().trim();
	    		   if(val == "") {
	    			   $(elmt).next().show();
	    			   is_infoData_OK = false;
	    			   return false; // forEach 는 break(중단) 기능이 없으나, each 는 있다.
	    			   
	    		   }
	    		   
	    	   });    		
    		
	    	  	  if(is_infoData_OK) {
	    		   

	    	  		var formData = new FormData($("form[name='prodInputFrm']").get(0));
	    		
	    	  		
	    	  		for(let i = 0; i < file_arr_copy.length; i++) {
	    	  			formData.append("files", file_arr_copy[i]);
	    	  		}
	    	  		
	    	  		
	    	  		$.ajax({
				    	  url:"${pageContext.request.contextPath}/api/product",
				    	  type:"post",
				    	  data:formData,
				    	  processData:false,  // 파일 전송시 설정 
		                  contentType:false,  // 파일 전송시 설정
		                  dataType:"json",
		                  success:function(response){
		                	  alert("들어왔어");
		                		  location.href="${pageContext.request.contextPath}/product/detail/"+response;  
		                	  
		                		  
		                	  
		                  },
		                  error: function(request, status, error){
		                	   //alert("메롱");
		                       alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		                  }
				       });
	    	   } 

    	});
    	
    
    
});

</script>


<div align="center" style="margin: 150px 0;">

    <div style="border: solid green 2px; width: 250px; margin-top: 20px; padding: 10px 0; border-left: hidden; border-right: hidden;">
        <span style="font-size: 15pt; font-weight: bold;">제품등록&nbsp;[관리자전용]</span>
    </div>
    <br/>

    <%-- ===== 폼에서 파일 업로드 시 반드시 method="POST" + enctype 설정 ===== --%>
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
                        <input type="text" style="width: 300px;" name="product_name" class="box infoData" />
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
                    	<input type="file" name="product_contents" class="infoData img_contents_file" accept="image/*" />
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