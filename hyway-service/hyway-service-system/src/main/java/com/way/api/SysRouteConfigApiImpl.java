package com.way.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysRouteConfigFeignApi;
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
	
	Result result;

	@Override
	public String selectSysRouteConfigPage(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String SysRouteConfig(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectList(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteById(String param) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateSysRouteConfig(String param) throws BusinessException {
		result=new Result(CodeConstants.RESULT_SUCCESS);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		sysRouteConfigService.update(sysRouteConfig);
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
                    		sysRouteConfigFeignApi.update(requestWrapper.toString());
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
