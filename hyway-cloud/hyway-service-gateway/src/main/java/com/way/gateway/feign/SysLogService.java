/*package cn.taroco.gateway.feign;

import cn.taroco.common.constants.ServiceNameConstants;
import cn.taroco.common.entity.SysLog;
import cn.taroco.gateway.feign.fallback.SysLogServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

*//**
 * 系统日志Service
 *
 * @author 
 * @date 2018/9/13 17:06
 *//*
@FeignClient(name = ServiceNameConstants.RBAC_SERVICE, fallback = SysLogServiceFallbackImpl.class)
public interface SysLogService {

    *//**
     * 添加日志
     *
     * @param log 日志实体
     *//*
    @PostMapping("/log")
    void add(@RequestBody SysLog log);
}
*/