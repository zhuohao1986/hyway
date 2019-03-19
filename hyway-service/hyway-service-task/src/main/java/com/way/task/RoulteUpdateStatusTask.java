package com.way.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.way.api.feign.SysRouteConfigFeignApi;

/**
 * 定时任务工作类
 *
 */
@DisallowConcurrentExecution
public class RoulteUpdateStatusTask implements Job {
	
	@Autowired
	private SysRouteConfigFeignApi  sysRouteConfigFeignApi;
	
	private static final Logger logger = LoggerFactory.getLogger(RoulteUpdateStatusTask.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug(">>>>>>>>>RoulteRefreshUpdateStatusTask:");
		 sysRouteConfigFeignApi.refreshRoute();
	}
}
