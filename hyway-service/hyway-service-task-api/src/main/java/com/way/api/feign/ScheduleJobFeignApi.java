package com.way.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.way.api.hystrix.ScheduleJobApiHystrixFeignFallbackFactory;
import com.way.common.constant.ServiceConstants;

/**
 * 定时任务 服务类Feign
 * @author 
 *
 */
@FeignClient(name=ServiceConstants.TASK_SERVICE,fallbackFactory=ScheduleJobApiHystrixFeignFallbackFactory.class)
public interface ScheduleJobFeignApi {
	/**
	 * 获取定时任务 json
	 * @param param
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="json",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String allJson(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="running/json", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String running(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="add", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String add(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="stop", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String stop(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="delete", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String delete(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="update", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateCron(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="startNow", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String startNow(String param);
	
	@RequestMapping(method=RequestMethod.POST,value="resume", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String restartJob(String param);

}
