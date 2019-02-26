
package com.way.common.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

@Configuration
public class FeignConfig {

	@Bean
	public Retryer feignRetryer() {

		return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 5);
	}
}
