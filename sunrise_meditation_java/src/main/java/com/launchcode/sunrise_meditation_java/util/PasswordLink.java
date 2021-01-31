package com.launchcode.sunrise_meditation_java.config;
import javax.servlet.http.HttpServletRequest;

public class PasswordLink {
    public static String getSiteAddress(HttpServletRequest request) {
        String siteAddress = request.getRequestURL().toString();
        return siteAddress.replace(request.getServletPath(),"");
    }
}
