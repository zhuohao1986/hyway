package com.way.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.way.common.constant.CommonConstant;
import com.way.common.pojos.system.SysOperRecord;
import com.way.common.utils.DateUtils;
import com.way.dao.SysOperRecordMapper;
import com.way.system.service.SysOperRecordService;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author 
 * @since 2017-11-20
 */
@Service
public class SysOperRecordServiceImpl implements SysOperRecordService {
	@Autowired
	private SysOperRecordMapper sysOperRecordMapper;

    @Override
    public int updateByLogId(Long id) {
    	SysOperRecord sysLog = new SysOperRecord();
        sysLog.setDelState(CommonConstant.STATUS_DEL);
        sysLog.setModifyTime(DateUtils.getCurrentDate());
        return sysOperRecordMapper.updateByPrimaryKeySelective(sysLog);
    }
}
