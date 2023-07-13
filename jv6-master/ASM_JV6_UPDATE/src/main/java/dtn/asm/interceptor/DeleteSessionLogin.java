package dtn.asm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import dtn.asm.dao.AccountDAO;
import dtn.asm.service.SessionService;

@Service
public class DeleteSessionLogin implements HandlerInterceptor {
	@Autowired
	SessionService session;

	@Autowired
	AccountDAO accountDAO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getRemoteUser() == null) {
			session.remove("acount");
			System.out.println("[==========| Notification: No session login. |==========]");
		} else {
			session.set("account", accountDAO.findById(request.getUserPrincipal().getName()).get());
			System.out.println("[==========| Notification: Login by " + request.getUserPrincipal().getName() + ". |==========]");
		}
		return true;
	}
}
