package j6.asm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import j6.asm.entity.Accounts;
import j6.asm.service.SessionService;

@Service
public class BlockLogin implements HandlerInterceptor {
	@Autowired
	SessionService session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Accounts accounts = session.get("account");
		if (accounts != null) {
			response.sendRedirect("/index.html");
		}
		return true;
	}
}
