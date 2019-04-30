package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.way.api.feign.GateWayRouteFeignApi;
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
public class GateWayRouteApiHystrixFeignFallbackFactory implements FallbackFactory<GateWayRouteFeignApi> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GateWayRouteApiHystrixFeignFallbackFactory.class);

	@Override
	public GateWayRouteFeignApi create(Throwable cause) {
		Result result=new Result(CodeConstants.RESULT_FAIL, DefaultError.SERVER_EXCEPTION);
		GateWayRouteApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return new GateWayRouteFeignApi()
        {

			@Override
			public String addRoute(String param) {
				return result.toJSONString();
			}

			@Override
			public String deleteRoute(String param) {
				return result.toJSONString();
			}

			@Override
			public String updateRoute(String param) {
				return result.toJSONString();
			}

			@Override
			public String refreshRoute() {
				return result.toJSONString();
			}
        };
	}
}
