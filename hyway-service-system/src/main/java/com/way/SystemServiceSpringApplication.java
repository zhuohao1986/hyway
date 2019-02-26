package com.way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.api.system.SysDictApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.context.BaseController;
import com.way.common.stdo.Result;

import tk.mybatis.spring.annotation.MapperScan;
@RestController
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.way.dao")
@ComponentScan(basePackages = {"com.way"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class SystemServiceSpringApplication extends BaseController {
	
	public static void main(String[] args) {
		SpringApplication.run(SystemServiceSpringApplication.class, args);
	}
	@Autowired
	private SysDictApi sysDictApi;
	
	@GetMapping("/dictPage")
	public String dictPage() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
		String sysDictPageStr = sysDictApi.selectSysDictPage(jsonData.toString());
		result.setValue(sysDictPageStr);
		return result.toString();
	}
	@GetMapping("/info")
	public String info() {
		initParams();
 		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
 		String sysDictPageStr = sysDictApi.sysDict();
		result.setValue(sysDictPageStr);
		return result.toString();
	}
}
