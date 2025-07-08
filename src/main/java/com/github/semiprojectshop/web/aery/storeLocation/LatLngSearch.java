package com.github.semiprojectshop.web.aery.storeLocation;

import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LatLngSearch extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.setRedirect(false);
	    super.setViewPage("/WEB-INF/views/aery/storeLocation/latLngSearch.jsp");
	    
	}

}
