package com.way.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.system.SysRouteConfigApi;
import com.way.common.constant.CodeConstants;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.LogUtils;

/**
 * <p>
 * 动态路由配置表 前端控制器
 * </p>
 *
 * @author
 * 
 */

@RestController
@RequestMapping("/route")
public class SysRouteConfigController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysRouteConfigController.class);

	@Autowired
	private SysRouteConfigApi sysRouteConfigApi;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysRoute
	 */
	@RequestMapping(value="/route",produces = MediaType.APPLICATION_JSON_VALUE)
	public String get() {
		Result result=null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysRouteConfigStr = sysRouteConfigApi.sysRouteConfig(rw.toString());
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表获取错误");
		}
		return result.toString();
	}

	@RequestMapping(value="/add",produces = MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST)
	public String add() {
		Result result=null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysRouteConfigStr = sysRouteConfigApi.insert(rw.toString());
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update() {
		Result result=null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysRouteConfigStr = sysRouteConfigApi.updateSysRouteConfig(rw.toString());
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}

	@RequestMapping("/delete")
	public String delete() {
		Result result=null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysRouteConfigStr = sysRouteConfigApi.deleteById(rw.toString());
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}
	
	@RequestMapping("/refresh")
	public String refresh() {
		Result result=null;
		try {
			initParams();
			String sysRouteConfigStr = sysRouteConfigApi.refresh();
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}
	
	@RequestMapping("/refreshGatewayRoutesCache")
	public String refreshCache() {
		Result result=null;
		try {
			initParams();
			String sysRouteConfigStr = sysRouteConfigApi.refreshCacheGatewayRoutes();
			result = JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}

	/**
	 * 分页查询信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/routePage")
	public String routeConfigPage() {
		Result result=null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysRouteConfigPageStr = sysRouteConfigApi.selectSysRouteConfigPage(rw.toString());
			result = JSONObject.parseObject(sysRouteConfigPageStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
		return result.toString();
	}
}
