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
	    //schedulerFactoryBean.setStartupDelay(10);
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
		
		prop.put("org.quartz.scheduler.instanceName", "timingTasks");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
		prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
		prop.put("org.quartz.jobStore.tablePrefix", "qrtz_");
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", "5");

		prop.put("org.quartz.dataSource.quartzDataSource.driver", prop.get("org.quartz.dataSource.myDS.driver"));
		prop.put("org.quartz.dataSource.quartzDataSource.URL", prop.get("org.quartz.dataSource.myDS.URL"));
		prop.put("org.quartz.dataSource.quartzDataSource.user", prop.get("org.quartz.dataSource.myDS.user"));
		prop.put("org.quartz.dataSource.quartzDataSource.password",prop.get("org.quartz.dataSource.myDS.password"));
		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", prop.get("org.quartz.dataSource.myDS.maxConnections"));
		return prop;
	}

}
