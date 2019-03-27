package com.way.task;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseTask extends QuartzJobBean implements Serializable {
		/**
	     * <p>Discription:[字段功能描述]</p>
	     */
	    private static final long serialVersionUID = 2567514317313869830L;

	    protected ApplicationContext applicationContext;

	    /**
	     * 从SchedulerFactoryBean注入的applicationContext.
	     */
	    public void setApplicationContext(ApplicationContext applicationContext) {
	        this.applicationContext = applicationContext;
	    }
}
