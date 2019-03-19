package com.way.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.way.datasource.DataSourceConfig;

@Configuration
public class QuartzConfig {
	@Autowired
	private SpringJobFactory springJobFactory;

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobFactory(springJobFactory);
		schedulerFactoryBean.setDataSource(dataSourceConfig.dataSource());
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		// 延迟10s启动quartz
	    schedulerFactoryBean.setStartupDelay(10);
		return schedulerFactoryBean;
	}

	@Bean
	public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws IOException, SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.start();
		return scheduler;
	}

	/**
	 * 设置quartz属性
	 */
	public Properties quartzProperties() throws IOException {
		Properties prop = new Properties();
		ClassPathResource resource=new ClassPathResource("quartz.properties");
		prop.load(resource.getInputStream());
		return prop;
	}

}
