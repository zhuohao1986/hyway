package com.way.admin.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysRouteConfigFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
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
	
	private static final Logger logger=LoggerFactory.getLogger(SysRouteConfigController.class);
	
    @Autowired
    private SysRouteConfigFeignApi sysRouteConfigApi;
    
    Result result;
    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysZuulRoute
     */
    @RequestMapping("/{id}")
    public String get(@PathVariable Integer id) {
    	initParams();
    	jsonData.put("id", id);
    	RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
        return sysRouteConfigApi.getRoute(rw.toString());
    }
    
    @RequestMapping("/add")
    public String add() {
    	try {
    		initParams();
    		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
    		String sysRouteConfigStr = sysRouteConfigApi.addRoute(rw.toString());
    		result=JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
    	return result.toString();
    }
    @RequestMapping("/update")
    public String update() {
    	try {
    		initParams();
    		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
    		String sysRouteConfigStr = sysRouteConfigApi.updateRoute(rw.toString());
    		result=JSONObject.parseObject(sysRouteConfigStr, Result.class);
		} catch (Exception e) {
			LogUtils.error(logger, "动态路由配置表添加错误");
		}
    	return result.toString();
    }
    
    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
    	try {
    		initParams();
    		jsonData.put("id", id);
    		RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
    		String sysRouteConfigStr = sysRouteConfigApi.deleteRoute(rw.toString());
    		result=JSONObject.parseObject(sysRouteConfigStr, Result.class);
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
    @GetMapping("/routeConfigPage")
    public String routeConfigPage() {
        jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
    	RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
        return sysRouteConfigApi.getRoute(rw.toString());
    }
}