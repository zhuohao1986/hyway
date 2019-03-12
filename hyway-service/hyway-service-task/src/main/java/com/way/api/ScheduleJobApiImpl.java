package com.way.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.way.api.system.ScheduleJobApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.ScheduleJobExecption;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.service.ScheduleJob;
import com.way.service.ScheduleJobService;

/**
 * 定时任务 服务类
 * 
 * @author
 *
 */
@Service
public class ScheduleJobApiImpl implements ScheduleJobApi {

	@Autowired
	private ScheduleJobService scheduleJobService;

	Result result = new Result(CodeConstants.RESULT_FAIL);

	@Override
	public String allJson(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Integer> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Integer>>() {});
		Integer page = paramMap.get("page");
		Integer limit = paramMap.get("limit");
		List<ScheduleJob> scheduleJobsList = scheduleJobService.getAllScheduleJob();
		PageHelper.startPage(page, limit);
		PageInfo<ScheduleJob> pageInfo=new PageInfo<>(scheduleJobsList);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(pageInfo);
		return result.toJSONString();
	}

	@Override
	public String running(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Integer> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Integer>>() {});
		Integer page = paramMap.get("page");
		Integer limit = paramMap.get("limit");
		List<ScheduleJob> scheduleJobsList = scheduleJobService.getAllRuningScheduleJob();
		PageHelper.startPage(page, limit);
		PageInfo<ScheduleJob> pageInfo=new PageInfo<>(scheduleJobsList);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(pageInfo);
		return result.toJSONString();
	}

	@Override
	public String add(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		ScheduleJob scheduleJob = JSONObject.parseObject(rw.getValue(), ScheduleJob.class);
		//验证cron表达式
		if(CronExpression.isValidExpression(scheduleJob.getCronExpression())){
			scheduleJobService.add(scheduleJob);
		}else{
			result.setCode(CodeConstants.RESULT_FAIL);
			result.setMessage("Cron表达式不正确");
		}
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	@Override
	public String stop(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String name = obj.getString("name");
		String group = obj.getString("group");
		scheduleJobService.stopJob(name, group);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	@Override
	public String delete(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String name = obj.getString("name");
		String group = obj.getString("group");
		scheduleJobService.delJob(name, group);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	@Override
	public String updateCron(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String name = obj.getString("name");
		String group = obj.getString("group");
		String cron = obj.getString("cron");
		//验证cron表达式
		if(CronExpression.isValidExpression(cron)){
			scheduleJobService.modifyTrigger(name, group, cron);
		}else{
			result.setCode(CodeConstants.RESULT_FAIL);
			result.setMessage("Cron表达式不正确");
		}
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	@Override
	public String startNow(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String name = obj.getString("name");
		String group = obj.getString("group");
		scheduleJobService.startNowJob(name, group);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	@Override
	public String restartJob(String param) throws ScheduleJobExecption{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		String name = obj.getString("name");
		String group = obj.getString("group");
		scheduleJobService.restartJob(name, group);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	/**
	 * 获取所有trigger
	 */
	@Override
	public String getTriggers() throws ScheduleJobExecption{
		List<ScheduleJob> scheduleJobs = scheduleJobService.getTriggersInfo();
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(scheduleJobs);
		return result.toJSONString();
	}

}
