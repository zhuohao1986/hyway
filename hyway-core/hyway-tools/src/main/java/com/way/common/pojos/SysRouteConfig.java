package com.way.common.pojos;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_route_config")
public class SysRouteConfig {
    private Integer id;

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "forward_uri")
    private String forwardUri;

    /**
     * 顺序
     */
    @Column(name = "route_order")
    private Integer routeOrder;

    /**
     * 断言字符窜
     */
    @Column(name = "route_predicates")
    private String routePredicates;

    @Column(name = "route_filter")
    private String routeFilter;

    /**
     * 启用
     */
    @Column(name = "route_enable")
    private Byte routeEnable;

    @Column(name = "delete_state")
    private Byte deleteState;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return route_id
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * @param routeId
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    /**
     * @return forward_uri
     */
    public String getForwardUri() {
        return forwardUri;
    }

    /**
     * @param forwardUri
     */
    public void setForwardUri(String forwardUri) {
        this.forwardUri = forwardUri == null ? null : forwardUri.trim();
    }

    /**
     * 获取顺序
     *
     * @return route_order - 顺序
     */
    public Integer getRouteOrder() {
        return routeOrder;
    }

    /**
     * 设置顺序
     *
     * @param routeOrder 顺序
     */
    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    /**
     * 获取断言字符窜
     *
     * @return route_predicates - 断言字符窜
     */
    public String getRoutePredicates() {
        return routePredicates;
    }

    /**
     * 设置断言字符窜
     *
     * @param routePredicates 断言字符窜
     */
    public void setRoutePredicates(String routePredicates) {
        this.routePredicates = routePredicates == null ? null : routePredicates.trim();
    }

    /**
     * @return route_filter
     */
    public String getRouteFilter() {
        return routeFilter;
    }

    /**
     * @param routeFilter
     */
    public void setRouteFilter(String routeFilter) {
        this.routeFilter = routeFilter == null ? null : routeFilter.trim();
    }

    /**
     * 获取启用
     *
     * @return route_enable - 启用
     */
    public Byte getRouteEnable() {
        return routeEnable;
    }

    /**
     * 设置启用
     *
     * @param routeEnable 启用
     */
    public void setRouteEnable(Byte routeEnable) {
        this.routeEnable = routeEnable;
    }

    /**
     * @return delete_state
     */
    public Byte getDeleteState() {
        return deleteState;
    }

    /**
     * @param deleteState
     */
    public void setDeleteState(Byte deleteState) {
        this.deleteState = deleteState;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}