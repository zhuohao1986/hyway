package com.way.api.system;

import com.way.common.exception.BusinessException;

public interface SysDeptApi {

	String selectSysDeptPage(String string);

	String sysDept(String string);

	String insertSysDept(String string);

	String deleteSysDeptById(String string);

	String updateSysDept(String string);

	String selectListTree(String param) throws BusinessException;

	String selectSysDeptList(String param) throws BusinessException;

}
