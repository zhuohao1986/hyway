package com.way.enreka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
//@EnableAdminServer
@SpringBootApplication
public class EnrekaServerSpringApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(EnrekaServerSpringApplication.class, args);
		}
		/*@Profile("insecure")
	    @Configuration
	    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
	        private final String adminContextPath;

	        public SecurityPermitAllConfig(AdminServerProperties adminServerProperties) {
	            this.adminContextPath = adminServerProperties.getContextPath();
	        }

	        @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            http.authorizeRequests()
	                .anyRequest()
	                .permitAll()
	                .and()
	                .csrf()
	                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	                .ignoringAntMatchers(adminContextPath + "/instances", adminContextPath + "/actuator/**");
	        }
	    }

	    @Profile("secure")
	    @Configuration
	    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
	        private final String adminContextPath;

	        public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
	            this.adminContextPath = adminServerProperties.getContextPath();
	        }

	        @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            // @formatter:off
	            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
	            successHandler.setTargetUrlParameter("redirectTo");
	            successHandler.setDefaultTargetUrl(adminContextPath + "/");

	            http.authorizeRequests()
	                .antMatchers(adminContextPath + "/assets/**").permitAll()
	                .antMatchers(adminContextPath + "/login").permitAll()
	                .anyRequest().authenticated()
	                .and()
	            .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
	            .logout().logoutUrl(adminContextPath + "/logout").and()
	            .httpBasic().and()
	            .csrf()
	                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	                .ignoringAntMatchers(adminContextPath + "/instances", adminContextPath + "/actuator/**");
	            // @formatter:on
	        }
	    }*/

}
