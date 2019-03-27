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

	/**
	 * 获取定时任务 json
	 */
	@RequestMapping("json")
	public String getAllJobs(Integer page,Integer limit){
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String create() {
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
		Result result = new Result(CodeConstants.RESULT_FAIL);
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
