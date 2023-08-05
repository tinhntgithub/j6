package j6.asm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import j6.asm.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Bật phân quyền cho các phương thức trong controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

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
//		http.csrf().ignoringAntMatchers("/signin.html");
		// CSRF, CORS
		http.csrf().disable().cors().disable();

		// Phân quyền sử dụng DIRE (admin), CUST (Thường dân), STAF (Nhân viên)
		http.authorizeRequests()
				.antMatchers("/index.html", "/signin.html", "/register.html", "/logout.html", "/forgot-password.html",
						"/compareEmail.html")
				.permitAll()

				.antMatchers("/admin/**").hasAnyRole("DIRE", "STAF").antMatchers("/cart.html", "/changepass.html",
						"/update_account.html", "/update_account.html/**", "/checkout.html", "/orders.html")
				.authenticated();

		// Điều khiển lỗi truy cập không đúng vai trò.
		http.exceptionHandling().accessDeniedPage("/error1");

		// Giao diện đăng nhập
		http.formLogin().loginPage("/signin.html").loginProcessingUrl("/signin.html")
				.defaultSuccessUrl("/index.html", false).failureUrl("/signin.html?err=123")
				.usernameParameter("username").passwordParameter("password");

		// Đăng xuất
		http.logout().logoutUrl("/logout.html").logoutSuccessUrl("/index.html")
				.addLogoutHandler(new SecurityContextLogoutHandler());

		// Đăng nhập bằng OAuth2
//		http.oauth2Login(oauth -> oauth.defaultSuccessUrl("/compareEmail.html"));
		http.oauth2Login().loginPage("/compareEmail.html").defaultSuccessUrl("/compareEmail.html", false)
				.authorizationEndpoint().baseUri("/oauth2/authorization")
				.authorizationRequestRepository(getRepository()).and().tokenEndpoint()
				.accessTokenResponseClient(getToken());

	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}
}
