package com.way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.common.cache.RedisUtil;
import com.way.common.constant.CodeConstants;
import com.way.common.pojos.system.SysDict;
import com.way.common.stdo.Result;
import com.way.system.api.SysDictService;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@RestController
@MapperScan(basePackages = "com.way.dao")
@ComponentScan(basePackages = {"com.way.system.service"})
@EnableCaching
public class SystemServiceSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SystemServiceSpringApplication.class, args);
	}
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
    private SysDictService sysDictService;
	 /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/dict/{id}")
    public String dict(@PathVariable Integer id) {
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	SysDict sysDict = sysDictService.selectById(id);
    	redisUtil.set("id", sysDict);
    	result.setValue(JSONObject.toJSON(sysDict));
        return result.toJSONString();
    }
}
