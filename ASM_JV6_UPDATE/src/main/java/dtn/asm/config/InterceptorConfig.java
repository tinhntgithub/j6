package dtn.asm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dtn.asm.interceptor.BlockLogin;
import dtn.asm.interceptor.DeleteSessionLogin;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	BlockLogin blockLogin;

	@Autowired
	DeleteSessionLogin deleteSessionLogin;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(blockLogin).addPathPatterns("/login.html", "/register.html").excludePathPatterns("");

		registry.addInterceptor(deleteSessionLogin).addPathPatterns("/*").excludePathPatterns("");
	}

}
