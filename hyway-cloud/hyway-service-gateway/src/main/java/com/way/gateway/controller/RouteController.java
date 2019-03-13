package com.way.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.pojos.system.dto.GatewayRouteDefinition;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.gateway.service.DynamicRouteService;

@RestController
@RequestMapping("/route")
public class RouteController extends BaseController {

	@Autowired
	private DynamicRouteService dynamicRouteService;

	Result result;

	// 增加路由
	@RequestMapping("/addRoute")
	public String add() {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper();
			String addStr = this.dynamicRouteService.add(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.toJSONString();
		}
		return result.toJSONString();
	}

	// 删除路由
	@RequestMapping("/routes/delete")
	public String delete() {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper();
			String addStr = this.dynamicRouteService.delete(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.toJSONString();
		}
		return result.toJSONString();
	}

	// 更新路由
	@RequestMapping("/update")
	public String update(@RequestBody GatewayRouteDefinition gwdefinition) {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper();
			String addStr = this.dynamicRouteService.update(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.toJSONString();
		}
		return result.toJSONString();
	}

	// 获取网关所有的路由信息
	@RequestMapping("/routes")
	public List<RouteDefinition> getRouteDefinitions() {
		return dynamicRouteService.getRouteDefinitions();
	}
}
