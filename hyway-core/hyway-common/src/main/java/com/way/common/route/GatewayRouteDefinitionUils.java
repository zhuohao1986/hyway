package com.way.common.route;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.way.common.pojos.system.SysRouteConfig;
/**
 * 路由工具类
 * @author way
 *
 */
public class GatewayRouteDefinitionUils {
	/**
	 * 把传递进来的参数转换成路由对象
	 * @param gwdefinition
	 * @return
	 */
	public  static List<GatewayRouteDefinition> assembleSysRouteConfigToRouteDefinition(List<SysRouteConfig> sysRouteConfigList) {
		List<GatewayRouteDefinition> gatewayRouteDefinitionList=new ArrayList<GatewayRouteDefinition>();
		for (SysRouteConfig sysRouteConfig : sysRouteConfigList) {
			GatewayRouteDefinition gatewayRouteDefinition=new GatewayRouteDefinition();
			gatewayRouteDefinition.setId(sysRouteConfig.getRouteId());
			gatewayRouteDefinition.setOrder(sysRouteConfig.getRouteOrder());
			List<GatewayFilterDefinition> filters=JSONObject.parseArray(sysRouteConfig.getRouteFilter().replaceAll("\\\\", ""), GatewayFilterDefinition.class);
			gatewayRouteDefinition.setFilters(filters);
			List<GatewayPredicateDefinition> predicates=JSONObject.parseArray(sysRouteConfig.getRoutePredicates().replaceAll("\\\\", ""), GatewayPredicateDefinition.class);
			gatewayRouteDefinition.setPredicates(predicates);
			gatewayRouteDefinition.setUri(sysRouteConfig.getForwardUri());
			gatewayRouteDefinitionList.add(gatewayRouteDefinition);
		}
		return gatewayRouteDefinitionList;
    }
}
