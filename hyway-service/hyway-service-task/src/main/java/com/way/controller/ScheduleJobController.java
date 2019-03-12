package com.way.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.ScheduleJobApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;

/**
 * 定时任务 controller
 * 
 * @author
 * @date
 */
@RestController
public class ScheduleJobController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);

	@Autowired
	private ScheduleJobApi scheduleJobApi;

	Result result = new Result(CodeConstants.RESULT_SUCCESS);

	/**
	 * 获取定时任务 json
	 */
	@RequestMapping("json")
	public String getAllJobs(Integer page,Integer limit){
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String allJsonStr = scheduleJobApi.allJson(rw.toString());
			result=JSONObject.parseObject(allJsonStr, Result.class);
		} catch (Exception e) {
			logger.error("getAllJobs error", e.getMessage());
		}
		return result.toJSONString();
	}
	
	/**
	 * 获取正在运行的定时任务
	 */
	@RequestMapping("running/json")
	public String getAllJobsRun(){
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.running(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}
	/**
	 * 添加
	 * @param user
	 * @param model
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String create() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.add(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}
	
	/**
	 * 暂停任务
	 */
	@RequestMapping("/stop")
	public String stop() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.stop(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}

	/**
	 * 删除任务
	 */
	@RequestMapping("/delete")
	public String delete() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.delete(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}

	/**
	 * 修改表达式
	 */
	@RequestMapping("/update")
	public String update() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.updateCron(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}

	/**
	 * 立即运行一次
	 */
	@RequestMapping("/startNow")
	public String stratNow() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.startNow(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}

	/**
	 * 恢复
	 */
	@RequestMapping("/resume")
	public String resume() {
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toString());
			String runningJsonStr = scheduleJobApi.restartJob(rw.toString());
			result=JSONObject.parseObject(runningJsonStr, Result.class);
		} catch (Exception e) {
			result=new Result(CodeConstants.RESULT_FAIL, "服务内部异常");
			logger.error("getAllJobsRun error", e.getMessage());
		}
		return result.toJSONString();
	}
}
