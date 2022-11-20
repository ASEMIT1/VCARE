package com.vcare.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.util.WebUtils;

import com.vcare.service.UserService;

@Component
public class AuthFilter implements Filter {

	@Autowired
	JwtUtils util;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me,token");
		if (CorsUtils.isPreFlightRequest(req)) {
			resp.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, resp);
			return;
		}
		Cookie cookie = WebUtils.getCookie(req, "token");
		String value ="";
		if (cookie != null) {
			 value = cookie.getValue();
		}else{
              value = req.getHeader("token");
		}
		System.out.println(req.getHeaderNames());
		if (req.getRequestURI().contains("logout")) {
			UserService.tokenList.remove(value);
			chain.doFilter(request, resp);
			return;
		}
		if (req.getRequestURI().contains("sign")) {

			chain.doFilter(request, resp);
		} else {

			if (!UserService.tokenList.contains(value)) {
				resp.sendError(401, "Authorization Failed");
				return;
			}

//        LOG.info(
//          "Starting a transaction for req : {}", 
//          req.getRequestURI());
//			Cookie cookie = WebUtils.getCookie(req, "token");
			if (StringUtils.isEmpty(value) || !util.validateJwtToken(value)) {
				resp.sendError(401, "Authorization Failed");
//				resp.setStatus(200,"Authorization Faile");
//				resp.sendError(sc);
				return;
			}
			String role = util.getRoleFromJwtToken(value);
//			if (role.equals("manager") && !RoleAuthMapping.ManagerMap.contains(req.getRequestURI())) {
//				resp.sendError(401, "Authorization Failed");
//				return;
//			}

			chain.doFilter(request, resp);
		}
	}

	// other methods
}