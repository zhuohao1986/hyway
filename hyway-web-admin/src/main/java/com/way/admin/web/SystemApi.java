package com.way.admin.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.way.common.constant.ServiceConstants;

@FeignClient(name=ServiceConstants.SYSTEM_SERVICE)
public interface SystemApi {

	@RequestMapping(path = "/dict/sysDict", method = RequestMethod.GET)
    String hello();
}
