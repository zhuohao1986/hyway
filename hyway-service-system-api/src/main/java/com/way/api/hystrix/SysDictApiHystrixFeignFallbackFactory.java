package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.way.api.system.SysDictApi;

import feign.hystrix.FallbackFactory;
/**
 * 此类表示FallBack执行的时候，打印相应的日志
 * 如果需要访问产生回退触发器的原因，可以使用@ feignclient中的fallbackFactory属性。
 * @author
 *
 */
@Component
public class SysDictApiHystrixFeignFallbackFactory implements FallbackFactory<SysDictApi> {

private static final Logger LOGGER = LoggerFactory.getLogger(SysDictApiHystrixFeignFallbackFactory.class);

	@Override
	public SysDictApi create(Throwable cause) {
		SysDictApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return null;
	}
}
