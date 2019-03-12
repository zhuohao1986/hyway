package com.way.system.service;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author 
 * @since 2017-11-20
 */
public interface SysLogService {

    /**
     * 通过ID删除日志（逻辑删除）
     *
     * @param id 日志ID
     * @return true/false
     */
    Boolean updateByLogId(Long id);
}
