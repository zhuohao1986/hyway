package com.way.api.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.way.api.feign.ScheduleJobFeignApi;
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
public class ScheduleJobApiHystrixFeignFallbackFactory implements FallbackFactory<ScheduleJobFeignApi> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobApiHystrixFeignFallbackFactory.class);

	@Override
	public ScheduleJobFeignApi create(Throwable cause) {
		
		Result result=new Result(CodeConstants.RESULT_FAIL, DefaultError.SERVER_EXCEPTION);
		ScheduleJobApiHystrixFeignFallbackFactory.LOGGER.info("服务异常-fallback; reason was: {}", cause.getMessage());
		return new ScheduleJobFeignApi()
        {

			@Override
			public String allJson(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String running(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String add(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String stop(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String delete(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String updateCron(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String startNow(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}

			@Override
			public String restartJob(String param) {
				// TODO Auto-generated method stub
				return result.toJSONString();
			}
           
        };
	}
}
