/*package cn.taroco.rbac.admin.controller;


import java.util.Map;

import javax.management.Query;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.way.admin.BaseController;
import com.way.common.constant.CommonConstant;
import com.way.system.api.SysLogService;

import cn.taroco.common.entity.SysLog;

*//**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author 
 * @since 2017-11-20
 *//*
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    *//**
     * 分页查询日志信息
     *
     * @param params 分页对象
     * @return 分页对象
     *//*
    @GetMapping("/logPage")
    public Page logPage(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return sysLogService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    *//**
     * 根据ID
     *
     * @param id ID
     * @return success/false
     *//*
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success(sysLogService.updateByLogId(id));
    }

    *//**
     * 添加日志
     *
     * @param log 日志实体
     * @param result 错误信息
     *//*
    @PostMapping
    public void add(@Valid @RequestBody SysLog log, BindingResult result) {
        if (result.hasErrors()) {
            throw new ClientException(result.getAllErrors().get(0).getDefaultMessage());
        }
        sysLogService.insert(log);
    }
}
*/