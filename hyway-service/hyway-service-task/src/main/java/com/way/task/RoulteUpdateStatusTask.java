package com.way.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.way.common.utils.SpringUtil;
import com.way.job.ExcuteJobService;

/**
 * 定时任务工作类
 *
 */
@DisallowConcurrentExecution
public class RoulteUpdateStatusTask extends BaseTask {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6167294611087696967L;
	
	private static final Logger logger = LoggerFactory.getLogger(RoulteUpdateStatusTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug(">>>>>>>>>RoulteRefreshUpdateStatusTask:");
		ExcuteJobService excuteJobService =SpringUtil.getBean(ExcuteJobService.class);
		logger.info(excuteJobService.excuteRefresh());
	}
}
