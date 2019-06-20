package com.way.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.feign.GateWayRouteFeignApi;
import com.way.api.system.SysRouteConfigApi;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysRouteConfig;
import com.way.common.route.GatewayRouteDefinition;
import com.way.common.route.GatewayRouteDefinitionUtils;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.common.utils.DateUtils;
import com.way.common.utils.StringUtils;
import com.way.service.SysRouteConfigService;
/**
 * 
 * @author way
 *  网管路由配置
 */
@Service
public class SysRouteConfigApiImpl implements SysRouteConfigApi{
	
	private static final Logger logger = LoggerFactory.getLogger(SysRouteConfigApiImpl.class);
	
	@Autowired
	private SysRouteConfigService sysRouteConfigService;
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private GateWayRouteFeignApi gateWayRouteFeignApi;
	
	@Override
	public String selectSysRouteConfigPage(String param) throws BusinessException {
		Result result = new Result();
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
	public String sysRouteConfigList(String param) throws BusinessException {
		Result result = new Result();
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Object>>() {});
		List<SysRouteConfig> sysRouteConfigList = sysRouteConfigService.selectSysRouteConfigList(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysRouteConfigList);
		return result.toJSONString();
	}
	@Override
	public String sysRouteConfig(String param) throws BusinessException {
		Result result = new Result();
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
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"动态路由配置表add success",true);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setCreateTime(DateUtils.getCurrentDate());
		int flag = sysRouteConfigService.insert(sysRouteConfig);
		if(flag<=0) {
			result.setMessage("动态路由配置表添加错误");
			result.setCode(CodeConstants.RESULT_FAIL);
			result.setValue(false);
		}
		return result.toString();
	}

	@Override
	public String deleteById(String param) throws BusinessException {
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"动态路由配置表delete success",true);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj=JSONObject.parseObject(rw.getValue());
		Integer id = obj.getInteger("id");
		SysRouteConfig sysRouteConfig = sysRouteConfigService.getSysRouteConfig(id);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		sysRouteConfig.setDeleteState(CommonConstant.STATUS_DEL);
		int updateflag = sysRouteConfigService.update(sysRouteConfig);
		if(updateflag<=0) {
			result.setMessage("动态路由配置表添加错误");
			result.setCode(CodeConstants.RESULT_FAIL);
			result.setValue(false);
		}
		return result.toString();
	}

	@Override
	public String updateSysRouteConfig(String param) throws BusinessException {
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"动态路由配置表update success",true);
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysRouteConfig sysRouteConfig=JSONObject.parseObject(rw.getValue(), SysRouteConfig.class);
		sysRouteConfig.setModifyTime(DateUtils.getCurrentDate());
		int updateflag = sysRouteConfigService.update(sysRouteConfig);
		if(updateflag<=0) {
			result.setMessage("动态路由配置update错误");
			result.setCode(CodeConstants.RESULT_FAIL);
			result.setValue(false);
		}
		return result.toJSONString();
	}
	@Override
	public String getSysRouteConfigLastVersion() throws BusinessException {
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"路由配置最新版本");
		Long sysRouteConfigLastVersion = sysRouteConfigService.getSysRouteConfigLastVersion();
		result.setValue(sysRouteConfigLastVersion);
		return result.toJSONString();
	}

	@Override
	public String refreshCacheGatewayRoutes() throws ClientToolsException, NumberFormatException, IOException {
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"路由配置刷新路由緩存成功");
	    //发布路由信息的版本号
		Long versionId=0l;
		if(jedisClient.exists(ConfigKeyConstant.GATEWAY_ROUTES_VERSION)) {
			versionId =Long.valueOf(jedisClient.get(ConfigKeyConstant.GATEWAY_ROUTES_VERSION)) ;
		}
        try{
        	 logger.info("拉取时间:" + DateUtils.getNowDateTime19String());
            //先拉取版本信息，如果版本号不想等则更新路由
            Long resultVersionId =sysRouteConfigService.getSysRouteConfigLastVersion();
            logger.info("路由版本信息：本地版本号：" + versionId + "，远程版本号：" + resultVersionId);
            if(resultVersionId != null && versionId != resultVersionId){
                System.out.println("开始拉取路由信息......");
                String resultRoutes = null;
                if(jedisClient.exists(ConfigKeyConstant.GATEWAY_ROUTES)) {
                	resultRoutes =jedisClient.get(ConfigKeyConstant.GATEWAY_ROUTES);
        		}
                //数据库获取配置
                Map<String,Object> paramMap =new HashMap<>();
                List<SysRouteConfig> resultRoutesList = sysRouteConfigService.selectSysRouteConfigList(paramMap);
                List<GatewayRouteDefinition> gatewayRouteDefinitionlist=GatewayRouteDefinitionUtils.assembleSysRouteConfigToRouteDefinition(resultRoutesList);
                resultRoutes=JSONObject.toJSONString(gatewayRouteDefinitionlist);
                logger.info("路由信息为：" + JSONObject.toJSONString(resultRoutes));
                if(!StringUtils.isEmpty(resultRoutes)){
                	gateWayRouteFeignApi.refreshRoute();
                    versionId = resultVersionId;
                    jedisClient.set(ConfigKeyConstant.GATEWAY_ROUTES_VERSION,String.valueOf(versionId));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
		return result.toJSONString();
	    }
	
	@Override
	public String refresh() throws ClientToolsException, NumberFormatException, IOException{
		Result result=new Result(CodeConstants.RESULT_SUCCESS,"路由配置刷新路由緩存成功");
        try{
            String resultRoutes = null;
            if(jedisClient.exists(ConfigKeyConstant.GATEWAY_ROUTES)) {
            	resultRoutes =jedisClient.get(ConfigKeyConstant.GATEWAY_ROUTES);
    		}else {
    			Map<String,Object> paramMap =new HashMap<>();
                List<SysRouteConfig> resultRoutesList = sysRouteConfigService.selectSysRouteConfigList(paramMap);
                List<GatewayRouteDefinition> gatewayRouteDefinitionlist=GatewayRouteDefinitionUtils.assembleSysRouteConfigToRouteDefinition(resultRoutesList);
                resultRoutes=JSONObject.toJSONString(gatewayRouteDefinitionlist);
                resultRoutes.replaceAll("\\\\", "");
    		}
            jedisClient.set(ConfigKeyConstant.GATEWAY_ROUTES, resultRoutes);
        }catch (Exception e){
        	result.setCode(CodeConstants.RESULT_FAIL);
        	result.setMessage("路由信息缓存刷新失败"+e.getMessage());
        	logger.error("路由信息缓存刷新失败", e.getMessage());
        	return result.toJSONString();
        }
		return result.toJSONString();
	    }
}
