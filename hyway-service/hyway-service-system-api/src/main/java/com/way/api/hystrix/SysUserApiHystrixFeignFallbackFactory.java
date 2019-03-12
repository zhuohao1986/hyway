package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.way.api.feign.SysDictFeignApi;
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
public class SysUserApiHystrixFeignFallbackFactory implements FallbackFactory<SysDictFeignApi> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserApiHystrixFeignFallbackFactory.class);

	@Override
	public SysDictFeignApi create(Throwable cause) {
		Result result=new Result(CodeConstants.RESULT_FAIL, DefaultError.SERVER_EXCEPTION);
		SysUserApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return new SysDictFeignApi()
        {
			@Override
			public String selectSysDictPage(String param) {
				return result.toString();
			}

			@Override
			public String sysDict(String param) {
				return result.toString();
			}

			@Override
			public String selectList(String param) {
				return result.toString();
			}

			@Override
			public String insert(String param) {
				return result.toString();
			}

			@Override
			public String deleteById(String param) {
				return result.toString();
			}

			@Override
			public String updateSysDict(String param) {
				return result.toString();
			}

			@Override
			public String refreshSysDict(String param) {
				return result.toString();
			}
           
        };
	}
}
