package com.way.im.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.way.common.pojos.im.Savemsg;
import com.way.common.utils.DateUtils;
import com.way.dao.SavemsgMapper;
import com.way.im.net.bean.TranObject;
import com.way.im.net.bean.TranObjectType;
import com.way.im.net.bean.User;
import com.way.im.net.client.ChatEntity;
import com.way.im.net.global.Result;
import com.way.im.service.MsgService;
import com.way.im.service.UserService;
@Service
public class MsgServiceImpl implements MsgService{
	
	private static HashMap<Integer,Result> idResult = new HashMap<Integer, Result>();
	private static HashMap<Result,Integer> resultId = new HashMap<Result, Integer>();
	private static HashMap<Integer,TranObjectType> idTrantype = new HashMap<Integer, TranObjectType>();
	private static HashMap<TranObjectType,Integer> trantypeId = new HashMap<TranObjectType, Integer>(); 
	
	static{
		//为Result枚举添加映射
		idResult.put(0, null);
		resultId.put(null, 0);
		idResult.put(1, Result.FRIEND_REQUEST_RESPONSE_ACCEPT);
		resultId.put(Result.FRIEND_REQUEST_RESPONSE_ACCEPT, 1);
		idResult.put(2, Result.FRIEND_REQUEST_RESPONSE_REJECT);
		resultId.put(Result.FRIEND_REQUEST_RESPONSE_REJECT, 2);
		idResult.put(3, Result.MAKE_FRIEND_REQUEST);
		resultId.put(Result.MAKE_FRIEND_REQUEST, 3);
		//为TranObjectType枚举添加映射
		idTrantype.put(0, null);
		trantypeId.put(null, 0);
		idTrantype.put(1, TranObjectType.FRIEND_REQUEST);
		trantypeId.put(TranObjectType.FRIEND_REQUEST, 1);
		idTrantype.put(2, TranObjectType.MESSAGE);
		trantypeId.put(TranObjectType.MESSAGE, 2);
	}
	
	@Autowired
	private SavemsgMapper savemsgMapper;
	@Autowired
	private UserService userService;

	@Override
	public void insertSaveMsg(int myid, TranObject tran) {
		
		int messageType = ChatEntity.RECEIVE;
		String msg = "";
		Savemsg savemsg=new Savemsg();
		savemsg.setGetid(tran.getReceiveId());
		savemsg.setSendid(myid);
		
		//message信息，时间来自ChatEntity对象，否则在tran中
		if(tran.getTranType() == TranObjectType.MESSAGE){
			ChatEntity chatEntity = (ChatEntity)tran.getObject();
			msg = chatEntity.getContent();
			messageType = chatEntity.getMessageType();
			savemsg.setTime(DateUtils.getCurDateTimeStr19());
		}
		savemsg.setTime(tran.getSendTime());
		savemsg.setTrantype(trantypeId.get(tran.getTranType()));
		savemsg.setMsg(msg);
		savemsg.setResulttype(resultId.get(tran.getResult()));
		savemsg.setMessagetype(messageType);
		savemsg.setSendname(tran.getSendName());
		
		savemsgMapper.insert(savemsg);
	}

	@Override
	public void deleteSaveMsg(int getid) {
		savemsgMapper.deleteByPrimaryKey(getid);
	}

	@Override
	public List<TranObject> selectMsg(int id) {
		
		List<TranObject> mlist=new ArrayList<TranObject>();
		Example example=new Example(Savemsg.class);
		example.createCriteria().andEqualTo("getid", id);
		List<Savemsg> msgList = savemsgMapper.selectByExample(example);
		for (Savemsg savemsg : msgList) {
			TranObject tran = new TranObject();
			tran.setSendId(savemsg.getSendid());
			tran.setTranType(idTrantype.get(savemsg.getTrantype()));
			tran.setSendName(savemsg.getSendname());
			tran.setResult(idResult.get(savemsg.getResulttype()));
			if(idTrantype.get(savemsg.getTrantype())==TranObjectType.MESSAGE){
				ChatEntity chatEntity = new ChatEntity();
				chatEntity.setContent(savemsg.getMsg());
				chatEntity.setMessageType(savemsg.getMessagetype());
				chatEntity.setReceiverId(tran.getReceiveId());
				chatEntity.setSenderId(tran.getSendId());
				chatEntity.setSendTime(savemsg.getTime());
				tran.setObject(chatEntity);
			}else if(idResult.get(savemsg.getResulttype())==Result.FRIEND_REQUEST_RESPONSE_ACCEPT){
				List<User> list = userService.selectFriendByAccountOrID(tran.getSendId());
				tran.setObject(list.get(0));
				tran.setSendTime(savemsg.getTime());
			}else{
				tran.setSendTime(savemsg.getTime());
			}
			mlist.add(tran);
		}
		return null;
	}
}
