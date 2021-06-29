package com.xsushirollx.sushibyte.user.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xsushirollx.sushibyte.user.dao.UserDAO;
import com.xsushirollx.sushibyte.user.entities.User;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil util;

	@Autowired
	UserDAO cdao;

	Logger log = Logger.getLogger("JWTRequestFilter");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// assume authentication so valid jwt which starts w/ "Bearer "
		try {
			String token = request.getHeader("Authorization").substring(7);

			long userId = Integer.parseInt(util.extractUserId(token));

			User customer = null;
			if (util.validateToken(token)) {
				log.info("token is valid");
				customer = cdao.findById((int) userId).get();
				log.info(customer.toString());
			} else {
				log.info("token is not valid");
				customer = new User(0, 0);
			}

			log.info(customer.toString());
			UserAuthenticationToken customerAuthentication = new UserAuthenticationToken(customer, token);
			SecurityContextHolder.getContext().setAuthentication(customerAuthentication);
		} catch (Exception e) {
			log.info("customer error thrown");
			User customer = new User(0, 0);
			UserAuthenticationToken customerAuthentication = new UserAuthenticationToken(customer, "BLANK");
			SecurityContextHolder.getContext().setAuthentication(customerAuthentication);
		} finally {
			filterChain.doFilter(request, response);
		}
	}

}