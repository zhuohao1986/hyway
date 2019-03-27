package com.way.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.way.service.ScheduleJob;

/**
 * 定时任务工作类
 *
 */
@DisallowConcurrentExecution
public class SimpleExampleStatusTask extends BaseTask {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9104284264815874761L;
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleExampleStatusTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug(">>>>>>>>>SimpleExampleStatusTask:");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		System.out.println("任务名称 = [" + scheduleJob.getName() + "]" + " 在 " + dateFormat.format(new Date()) + " 时运行");
	}
}
