package com.way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.spring.annotation.MapperScan;

import com.way.api.system.SysDictApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.context.BaseController;
import com.way.common.stdo.Result;

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.way.dao")
@SpringBootApplication
@RestController
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

	@GetMapping("/")
	public String info() {
		initParams();
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
		String sysDictPageStr = sysDictApi.sysDict();
		result.setValue(sysDictPageStr);
		return result.toString();
	}
}
