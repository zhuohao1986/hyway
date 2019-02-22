package com.way.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.context.BaseController;
import com.way.service.sso.api.SsoApi;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@RestController
@MapperScan(basePackages = "com.way.dao")
@ComponentScan(basePackages = { "com.way.user.service", "com.way.user.api", "com.way.service.sso",
		"com.way.common.config", "com.way.common.cache" })
@EnableFeignClients(basePackages = "com.way.service.sso.api")
public class SsoSpringApplication extends BaseController {

	public static void main(String[] args) {
		SpringApplication.run(SsoSpringApplication.class, args);
	}

	@Autowired
	private SsoApi ssoApi;

	@RequestMapping("/login")
	public String login(String userName,String userPwd) {
		try {
			initParams();
			return ssoApi.userLogin(userName,userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
