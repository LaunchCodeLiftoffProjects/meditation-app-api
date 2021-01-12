package com.launchcode.sunrise_meditation_java.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.launchcode.sunrise_meditation_java.config.JwtTokenUtil;
import com.launchcode.sunrise_meditation_java.model.JwtRequest;
import com.launchcode.sunrise_meditation_java.model.JwtResponse;
import com.launchcode.sunrise_meditation_java.model.JwtUserDetails;
import com.launchcode.sunrise_meditation_java.service.JwtUserDetailsService;
import com.launchcode.sunrise_meditation_java.util.CommonUtils;

/**
 * 
 * This file contains authentication rest API that receives the username and password for authentication and it returns the JWT token on successful response.
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
public class JwtAuthenticationController {
	
	@Value("${jwt.http.request.header}")
	 private String tokenHeader;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private CommonUtils commonUtils;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception 
	{
		
		if(!commonUtils.isLoggedInUserValid(authenticationRequest.getUsername(), authenticationRequest.getPassword())) { 
			System.out.println("Invalid Credentials");
			return ResponseEntity.badRequest().body(null);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());	
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	  public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
	    String authToken = request.getHeader(tokenHeader);
	    final String token = authToken.substring(7);
	    String username = jwtTokenUtil.getUsernameFromToken(token);
	    JwtUserDetails user = (JwtUserDetails) userDetailsService.loadUserByUsername(username);

	    if (jwtTokenUtil.canTokenBeRefreshed(token)) {
	      String refreshedToken = jwtTokenUtil.refreshToken(token);
	      return ResponseEntity.ok(new JwtResponse(refreshedToken));
	    } else {
	      return ResponseEntity.badRequest().body(null);
	    }
	  }	
}
