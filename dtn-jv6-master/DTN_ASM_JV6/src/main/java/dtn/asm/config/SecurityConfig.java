package dtn.asm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import dtn.asm.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Bật phân quyền cho các phương thức trong controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	// Mã hóa mật khẩu
	@Bean
	BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Quản lý dữ liệu người sử dụng
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	// Phân quyền sử dụng & hình thức đăng nhập
	@Override
	public void configure(HttpSecurity http) throws Exception {

		// CSRF, CORS
		http.csrf().disable().cors().disable();

		// Phân quyền sử dụng DIRE (admin), CUST (Thường dân), STAF (Nhân viên)
//		http.authorizeRequests().antMatchers("/DTNsBike/index.html", "/DTNsBike/login.html").permitAll().antMatchers("")
//				.hasRole("DIRE").antMatchers("").hasAnyRole("DIRE", "STAF").antMatchers("").authenticated();
//		http.authorizeRequests().antMatchers("/login.html").permitAll().antMatchers("/index.html").authenticated();
		http.authorizeRequests()
				.antMatchers("/index.html", "/login.html", "/register.html", "/logout.html", "/forgot-password.html")
				.permitAll().antMatchers("/admin/**").hasAnyRole("DIRE", "STAF")
				.antMatchers("/cart.html", "/changepass.html", "/update_account.html", "/update_account.html/**",
						"/checkout.html", "/orders.html")
				.authenticated();
		// Điều khiển lỗi truy cập không đúng vai trò.
		http.exceptionHandling().accessDeniedPage("/login.html");

		// Giao diện đăng nhập
		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login.html")
				.defaultSuccessUrl("/index.html", false).failureUrl("/login.html?err=123").usernameParameter("username")
				.passwordParameter("pass");

		// Đăng xuất
		http.logout().logoutUrl("/logout.html").logoutSuccessUrl("/index.html")
				.addLogoutHandler(new SecurityContextLogoutHandler());
	}
}
