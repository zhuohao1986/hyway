package com.way.api.system;

import com.way.common.exception.ScheduleJobExecption;

/**
 * 定时任务 服务类Feign
 * @author 
 *
 */
public interface ScheduleJobApi {
	/**
	 * 获取定时任务 json
	 * @param param
	 * @return
	 */
	public String allJson(String param) throws ScheduleJobExecption;
	
	public String running(String param) throws ScheduleJobExecption;
	
	public String add(String param) throws ScheduleJobExecption;
	
	public String stop(String param) throws ScheduleJobExecption;
	
	public String delete(String param) throws ScheduleJobExecption;
	
	public String updateCron(String param) throws ScheduleJobExecption;
	
	public String startNow(String param) throws ScheduleJobExecption;
	
	public String restartJob(String param) throws ScheduleJobExecption;

	String getTriggers() throws ScheduleJobExecption;

}
