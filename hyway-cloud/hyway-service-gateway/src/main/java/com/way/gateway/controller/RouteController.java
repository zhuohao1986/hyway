package com.way.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.alibaba.fastjson.JSONObject;
import com.way.common.constant.CodeConstants;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.gateway.service.DynamicRouteService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/route")
public class RouteController extends BaseController{

	@Autowired
	private DynamicRouteService dynamicRouteService;

	Result result;

	// 增加路由
	@RequestMapping("/add")
	public String add(ServerHttpRequest request, ServerHttpResponse response) {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toJSONString());
			String addStr = this.dynamicRouteService.add(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.toJSONString();
		}
		return result.toJSONString();
	}

	// 删除路由
	@RequestMapping("/delete")
	public String delete(ServerRequest request) {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toJSONString());
			String addStr = this.dynamicRouteService.delete(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.toJSONString();
		}
		return result.toJSONString();
	}

	// 更新路由
	@RequestMapping("/update")
	public String update(ServerRequest request) {
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB,jsonData.toJSONString());
			String addStr = this.dynamicRouteService.update(rw.toString());
			result = JSONObject.parseObject(addStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.toJSONString();
		}
		return result.toJSONString();
	}
	//获取网关所有的路由信息
	
	 @Autowired
	 private RouteDefinitionLocator routeDefinitionLocator;
	 
    @RequestMapping("/routes")
    public String getRouteDefinitions(){
		result = new Result(CodeConstants.RESULT_FAIL);
		try {
			String routeDefinitionsStr = this.dynamicRouteService.getRouteDefinitions();
			result = JSONObject.parseObject(routeDefinitionsStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.toJSONString();
		}
		return result.toJSONString();
    }
    //获取网关所有的路由信息
    @RequestMapping("/routes/")
    public Flux<RouteDefinition> getRouteDefinitionss(){
    	
		return routeDefinitionLocator.getRouteDefinitions();
    }
    /**
     * 手动刷新路由配置
     * @return
     */
    @RequestMapping(value="/routes/refresh", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshRoute() {
    	result = new Result(CodeConstants.RESULT_FAIL);
		try {
			String routeDefinitionsStr = this.dynamicRouteService.refresh();
			result = JSONObject.parseObject(routeDefinitionsStr, Result.class);
			result.setCode(CodeConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.toJSONString();
		}
		return result.toJSONString();
    };
}
