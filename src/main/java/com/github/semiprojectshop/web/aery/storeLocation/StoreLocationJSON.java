package com.github.semiprojectshop.web.aery.storeLocation;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.sihu.product.ProductJpaCustom;
import com.github.semiprojectshop.repository.sihu.product.ProductJpaCustomImpl;
import com.github.semiprojectshop.repository.sihu.product.wish.WishJpaCustom;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class StoreLocationJSON extends AbstractController {

	private ProductJpaCustom pdao;
	
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (pdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            pdao = (ProductJpaCustom) ctx.getBean("ProductJpaCustomImpl");
        }

		// tbl_map(위,경도) 테이블에 있는 정보를 가져오기(select)
		List<Map<String, String>> storeMapList = pdao.selectStoreMap();
		
		JSONArray jsonArr = new JSONArray(); // []
	      
		if(storeMapList.size() > 0) {
			for(Map<String, String> storeMap : storeMapList) {
				JSONObject jsonObj = new JSONObject(); // {}
	            
				String storeurl = storeMap.get("STOREURL");
	            String storename = storeMap.get("STORENAME");
	            String storeimg = storeMap.get("STOREIMG");
	            String storeaddress = storeMap.get("STOREADDRESS");
	            double lat = Double.parseDouble(storeMap.get("LAT"));
	            double lng = Double.parseDouble(storeMap.get("LNG"));
	            int zIndex = Integer.parseInt(storeMap.get("ZINDEX"));
	            
	            jsonObj.put("storeurl", storeurl);
	            jsonObj.put("storename", storename);
	            jsonObj.put("storeimg", storeimg);
	            jsonObj.put("storeaddress", storeaddress);
	            jsonObj.put("lat", lat);
	            jsonObj.put("lng", lng);
	            jsonObj.put("zIndex", zIndex);
	            
	            jsonArr.put(jsonObj); // [{},{},{},{},{}]
			}// end of for---------------------
		}
	      
		String json = jsonArr.toString();
		request.setAttribute("json", json);
	      
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/views/aery/jsonView.jsp");
	}

}
