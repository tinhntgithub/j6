package dtn.asm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import dtn.asm.dao.AccountDAO;
import dtn.asm.entity.Accounts;
import dtn.asm.entity.Accounts;
import dtn.asm.service.AccountsService;
import dtn.asm.service.SessionService;

@Service
public class DeleteSessionLogin implements HandlerInterceptor {

	@Autowired
	SessionService session;

	@Autowired
	AccountDAO accountDAO;
	@Autowired
	AccountsService accountsService1;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getRemoteUser() == null) {
			session.remove("account");
			System.out.println("[==========| Notification: No session login. |==========]");
		} else {
			String username = request.getUserPrincipal().getName();
			Accounts account = accountsService1.findById(username);
			if (account != null) {
				session.set("account", account);
				System.out.println("[==========| Notification: Login by " + username + ". |==========]");
			} else {
				System.out.println("[==========| Notification: Account not found. |==========]");
			}
		}

		return true;
	}
}
