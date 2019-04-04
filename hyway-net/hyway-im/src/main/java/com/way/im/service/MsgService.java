package com.way.im.service;

import java.util.List;

import com.way.im.net.bean.TranObject;

public interface MsgService {
	
	void insertSaveMsg(int myid,TranObject tran);
	
	void  deleteSaveMsg(int getid);
	
	List<TranObject> selectMsg(int id);

}
