package com.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.way.common.context.BaseController;

import tk.mybatis.spring.annotation.MapperScan;
@EnableDiscoveryClient
@EnableFeignClients//(basePackages={"com.way.api.system"})
@ComponentScan(basePackages = {"com.way"})
@MapperScan(basePackages = "com.way.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class SystemServiceSpringApplication extends BaseController {
	
	public static void main(String[] args) {
		SpringApplication.run(SystemServiceSpringApplication.class, args);
	}
	/*
	 * @Autowired private SysDictApi sysDictApi;
	 * 
	 * @Autowired EurekaClient eurekaClient;
	 * 
	 * @GetMapping("/dictPage") public String dictPage() { initParams(); Result
	 * result = new Result(CodeConstants.RESULT_SUCCESS);
	 * jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL); String
	 * sysDictPageStr = sysDictApi.selectSysDictPage(jsonData.toString());
	 * result.setValue(sysDictPageStr); return result.toString(); }
	 * 
	 * @GetMapping("/info") public String info() { initParams(); Result result = new
	 * Result(CodeConstants.RESULT_SUCCESS); jsonData.put(CommonConstant.DEL_FLAG,
	 * CommonConstant.STATUS_NORMAL); String sysDictPageStr = sysDictApi.sysDict();
	 * result.setValue(sysDictPageStr); return result.toString(); }
	 * 
	 * @RequestMapping(value="/info2",produces="application/json") public String
	 * info2() { initParams(); Result result = new
	 * Result(CodeConstants.RESULT_SUCCESS); jsonData.put(CommonConstant.DEL_FLAG,
	 * CommonConstant.STATUS_NORMAL); Applications applications =
	 * eurekaClient.getApplications("SYSTEM-SERVICE"); String sysDictPageStr =
	 * JSONObject.toJSONString(applications); result.setValue(sysDictPageStr);
	 * return result.toJSONString(); }
	 */
}
