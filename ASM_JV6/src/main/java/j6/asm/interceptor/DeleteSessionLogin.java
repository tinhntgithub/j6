package j6.asm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import j6.asm.dao.AccountDAO;
import j6.asm.entity.Accounts;
import j6.asm.service.AccountsService;
import j6.asm.service.SessionService;

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
			// Accounts account1 = (Accounts) session.get("account")


			String username = request.getUserPrincipal().getName();
			System.out.println(username+ " =======");
			Accounts account = accountsService1.findById(username);
			if (account != null) {
				session.set("account", account);
				System.out.println("[==========| Notification: Login by " + account.getFullname() + ". |==========]");
			} 
			// else if (account1 != null) {
			// 	session.set("account", account1);
			// 	System.out.println("[==========| Notification: Login by " + account1.getFullname() + ". |==========]");
			// }
			 else {
				System.out.println("[==========| Notification: Account not found. |==========]");
			}
		}
		return true;
	}
}
