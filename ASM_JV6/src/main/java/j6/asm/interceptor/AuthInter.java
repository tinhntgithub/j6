package j6.asm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import j6.asm.entity.Accounts;
import j6.asm.service.SessionService;

@Service
public class AuthInter implements HandlerInterceptor {
    @Autowired
    SessionService session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        Accounts user = (Accounts) session.get("account");
        String error = "";
        if (user == null) {
            session.remove("account");
            System.out.println("Không có tài khoản đăng nhập nào");
            error = "Please login!";
            response.sendRedirect("/error1?error=" + error);
            return false;
        } else
         if (!user.getAuthorities().equals("DIRE")
                || !user.getAuthorities().equals("STAF")
                 && uri.startsWith("/admin/index.html")) {
            error = "You do not have access!";
            response.sendRedirect("/error1?error=" + error);
            return false;
        }
        return true;
    }
}
