package com.way.api.system;

import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
public interface SysDictApi{
	/**
	 * 分页查询字典
	 * @param param limit  page
	 * @return
	 */
	public String selectSysDictPage(String param) throws BusinessException;
	/**
	 * 字典Id查询
	 * @param param dictId
	 * @return
	 */
	public String sysDict(String param)  throws BusinessException;
	/**
	 * 查询字典List
	 * @param param dictType
	 * @return
	 */
	public String selectList(String param)  throws BusinessException;
    /**
     * 查询字典
     * @param param
     * @return
     */
	public String insert(String param)  throws BusinessException;
    /**
     * 删除字典
     * @param param
     * @return
     */
	public String deleteById(String param) throws BusinessException;
    /**
     * 更新字典
     * @param param
     * @return
     */
	public String updateSysDict(String param) throws BusinessException;
	 /**
	  * 刷新字典緩存
     * @param param
     * @return
     */
	public String refresh(String string) throws ClientToolsException;

}
