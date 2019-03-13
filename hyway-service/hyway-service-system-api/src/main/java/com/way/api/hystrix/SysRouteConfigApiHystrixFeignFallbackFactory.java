package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.way.api.feign.SysRouteConfigFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.DefaultError;
import com.way.common.stdo.Result;

import feign.hystrix.FallbackFactory;
/**
 * 此类表示FallBack执行的时候，打印相应的日志
 * 如果需要访问产生回退触发器的原因，可以使用@ feignclient中的fallbackFactory属性。
 * @author
 *
 */
@Component
public class SysRouteConfigApiHystrixFeignFallbackFactory implements FallbackFactory<SysRouteConfigFeignApi> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysRouteConfigApiHystrixFeignFallbackFactory.class);

	@Override
	public SysRouteConfigFeignApi create(Throwable cause) {
		Result result=new Result(CodeConstants.RESULT_FAIL, DefaultError.SERVER_EXCEPTION);
		SysRouteConfigApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return new SysRouteConfigFeignApi()
        {

			@Override
			public String addRoute(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String delete(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String update(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String routes() {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}
        };
	}
}
