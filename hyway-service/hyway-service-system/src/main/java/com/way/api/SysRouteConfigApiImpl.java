package com.way.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.feign.SysRouteConfigFeignApi;
import com.way.api.feign.SysRouteGateWayFeignApi;
import com.way.api.system.SysRouteConfigApi;
import com.way.common.constant.CodeConstants;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysRouteConfig;
import com.way.common.pojos.system.dto.GatewayRouteDefinition;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.DateUtils;
import com.way.system.service.SysRouteConfigService;

@Service
public class SysRouteConfigApiImpl implements SysRouteConfigApi{
	
	@Autowired
	private SysRouteConfigService sysRouteConfigService;
	
	@Autowired
	private SysRouteConfigFeignApi sysRouteConfigFeignApi;
	
	@Autowired
	private SysRouteGateWayFeignApi sysRouteGateWayFeignApi;
	
	Result result;

	@Override
	public String selectSysRouteConfigPage(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		PageInfo<SysRouteConfig> sysDictpage = sysRouteConfigService.selectSysRouteConfigPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysDictpage);
		return result.toJSONString();
	}

	@Override
	public String SysRouteConfig(String param) throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		SysRouteConfig sysRouteConfig = sysRouteConfigService.getSysRouteConfig(id);
		result.setValue(sysRouteConfig);
		return result.toJSONString();
	}

	@Override
	public String insert(String param) throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		sysRouteConfigService.update(sysRouteConfig);
		if (CodeConstants.RESULT_SUCCESS.equals(result.getCode())) {
			String remoteSysRouteConfigStr=sysRouteGateWayFeignApi.insertSysRouteConfig(rw.toString());
			Result remoteResult=JSONObject.parseObject(remoteSysRouteConfigStr, Result.class);
			if (CodeConstants.RESULT_SUCCESS.equals(remoteResult.getCode())) {
				return result.toString();
			}
		}
		return result.toString();
	}

	@Override
	public String deleteById(String param) throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		sysRouteConfigService.update(sysRouteConfig);
		if (CodeConstants.RESULT_SUCCESS.equals(result.getCode())) {
			String remoteSysRouteConfigStr=sysRouteGateWayFeignApi.deleteSysRouteConfigById(rw.toString());
			Result remoteResult=JSONObject.parseObject(remoteSysRouteConfigStr, Result.class);
			if (CodeConstants.RESULT_SUCCESS.equals(remoteResult.getCode())) {
				return result.toString();
			}
		}
		return result.toString();
	}

	@Override
	public String updateSysRouteConfig(String param) throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		sysRouteConfigService.update(sysRouteConfig);
		if (CodeConstants.RESULT_SUCCESS.equals(result.getCode())) {
			String remoteSysRouteConfigStr=sysRouteGateWayFeignApi.updateSysRouteConfig(rw.toString());
			Result remoteResult=JSONObject.parseObject(remoteSysRouteConfigStr, Result.class);
			if (CodeConstants.RESULT_SUCCESS.equals(remoteResult.getCode())) {
				return result.toString();
			}
		}
		return result.toJSONString();
	}
	@Override
	public String getSysRouteConfigLastVersion() throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		Long sysRouteConfigLastVersion = sysRouteConfigService.getSysRouteConfigLastVersion();
		result.setValue(sysRouteConfigLastVersion);
		return result.toJSONString();
	}

	@Override
	public String refresh(String string) throws ClientToolsException {
	    //发布路由信息的版本号
        Long versionId = 0L;
        try{
            System.out.println("拉取时间:" + DateUtils.getNowDateTime19String());
            //先拉取版本信息，如果版本号不想等则更新路由
            Long resultVersionId =sysRouteConfigService.getSysRouteConfigLastVersion();
            System.out.println("路由版本信息：本地版本号：" + versionId + "，远程版本号：" + resultVersionId);
            if(resultVersionId != null && versionId != resultVersionId){
                System.out.println("开始拉取路由信息......");
                String routes = sysRouteConfigFeignApi.routes();
                Result routesResult=JSONObject.parseObject(routes,Result.class);
                String resultRoutes =JSONObject.toJSONString(routesResult.getValue());
                System.out.println("路由信息为：" + resultRoutes);
                if(!StringUtils.isEmpty(resultRoutes)){
                    List<GatewayRouteDefinition> list = JSON.parseArray(resultRoutes , GatewayRouteDefinition.class);
                    for(GatewayRouteDefinition definition : list){
                        //更新路由
                    	RequestWrapper requestWrapper =new RequestWrapper();
                    	requestWrapper.setValue(JSONObject.toJSONString(definition));
                    		sysRouteConfigFeignApi.updateRoute(requestWrapper.toString());
                       }
                    versionId = resultVersionId;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
		return string;
	    }
}
