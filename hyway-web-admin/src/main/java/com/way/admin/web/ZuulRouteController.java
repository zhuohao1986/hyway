/*package cn.taroco.rbac.admin.controller;

import java.util.Date;
import java.util.Map;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.way.admin.BaseController;
import com.way.common.constant.CommonConstant;
import com.way.system.api.SysZuulRouteService;

import cn.taroco.common.entity.SysZuulRoute;

*//**
 * <p>
 * 动态路由配置表 前端控制器
 * </p>
 *
 * @author 
 * @since 2018-05-15
 *//*
@RestController
@RequestMapping("/route")
public class ZuulRouteController extends BaseController {
    @Autowired
    private SysZuulRouteService sysZuulRouteService;
    *//**
     * 通过ID查询
     *
     * @param id ID
     * @return SysZuulRoute
     *//*
    @GetMapping("/{id}")
    public SysZuulRoute get(@PathVariable Integer id) {
        return sysZuulRouteService.selectById(id);
    }

    *//**
     * 分页查询信息
     *
     * @param params 分页对象
     * @return 分页对象
     *//*
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return sysZuulRouteService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    *//**
     * 添加
     *
     * @param sysZuulRoute 实体
     * @return success/false
     *//*
    @PostMapping
    public Response add(@RequestBody SysZuulRoute sysZuulRoute) {
        return Response.success(sysZuulRouteService.insert(sysZuulRoute));
    }

    *//**
     * 删除
     *
     * @param id ID
     * @return success/false
     *//*
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Integer id) {
        SysZuulRoute sysZuulRoute = new SysZuulRoute();
        sysZuulRoute.setId(id);
        sysZuulRoute.setUpdateTime(new Date());
        sysZuulRoute.setDelFlag(CommonConstant.STATUS_DEL);
        return Response.success(sysZuulRouteService.updateById(sysZuulRoute));
    }

    *//**
     * 编辑
     *
     * @param sysZuulRoute 实体
     * @return success/false
     *//*
    @PutMapping
    public Response edit(@RequestBody SysZuulRoute sysZuulRoute) {
        sysZuulRoute.setUpdateTime(new Date());
        return Response.success(sysZuulRouteService.updateById(sysZuulRoute));
    }

    *//**
     * 刷新配置
     *
     * @return success/fasle
     *//*
    @GetMapping("/apply")
    public Response apply() {
        return Response.success(sysZuulRouteService.applyZuulRoute());
    }
}
*/