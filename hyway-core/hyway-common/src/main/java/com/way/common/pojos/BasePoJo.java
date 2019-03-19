/**   
 *Copyright (c) 2014-2020, HIAAS and/or its affiliates. All rights reserved.
 */
package com.way.common.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础po，所有po继承该po
 * 
 * @author
 * @date  
 * 
 */
public class BasePoJo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 创建人ID */
	private Long createBy;
	/** 创建时间 */
    private Date createTime;
    /** 更新人ID */
    private Long updateBy;
    /** 更新时间 */
    private Date updateTime;
    /** 数据版本 */
    private Integer dataVersion;
    /**删除状态*/
    private String delStatus;
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDataVersion() {
		return dataVersion;
	}
	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	


}