package com.launchcode.sunrise_meditation_java.util;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class PasswordLink {

    public static String getSiteAddress(HttpServletRequest request) {
        String siteAddress = request.getRequestURL().toString();
        return siteAddress.replace(request.getServletPath(),"");
    }
}
