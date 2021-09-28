package com.production.v1.util;

import javax.servlet.http.HttpServletRequest;

public class BaseUrlUtility {
	 public static String getSiteURL(HttpServletRequest request) {
	        String siteURL = request.getRequestURL().toString();
	        return siteURL.replace(request.getServletPath(), "");
	    }
}
