package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.way.api.feign.SysDeptFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.DefaultError;
import com.way.common.stdo.Result;

import feign.hystrix.FallbackFactory;

public class SysDeptApiHystrixFeignFallbackFactory implements FallbackFactory<SysDeptFeignApi> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysDictApiHystrixFeignFallbackFactory.class);

	@Override
	public SysDeptFeignApi create(Throwable cause) {
		Result result=new Result(CodeConstants.RESULT_FAIL, DefaultError.SERVER_EXCEPTION);
		SysDeptApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return new SysDeptFeignApi()
        {

			@Override
			public String selectSysDeptPage(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String sysDept(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String selectList(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String insert(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String deleteById(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String updateSysDept(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String selectListTree(String string) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

        };
	}
}
