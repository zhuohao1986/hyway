package com.way.system.api;

/**
 * <p>
 * 动态路由配置表 服务类
 * </p>
 *
 * @author 
 * @since 2018-05-15
 */
public interface SysZuulRouteService {

    /**
     * 立即生效配置
     * @return
     */
    Boolean applyZuulRoute();
}
