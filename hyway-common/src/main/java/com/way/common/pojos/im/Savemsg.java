package com.way.common.pojos.im;

import javax.persistence.*;

public class Savemsg {
    private Integer sendid;

    private Integer getid;

    private String msg;

    private Integer trantype;

    private String time;

    private Integer resulttype;

    private Integer messagetype;

    private String sendname;

	public Integer getSendid() {
		return sendid;
	}

	public void setSendid(Integer sendid) {
		this.sendid = sendid;
	}

	public Integer getGetid() {
		return getid;
	}

	public void setGetid(Integer getid) {
		this.getid = getid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getTrantype() {
		return trantype;
	}

	public void setTrantype(Integer trantype) {
		this.trantype = trantype;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getResulttype() {
		return resulttype;
	}

	public void setResulttype(Integer resulttype) {
		this.resulttype = resulttype;
	}

	public Integer getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(Integer messagetype) {
		this.messagetype = messagetype;
	}

	public String getSendname() {
		return sendname;
	}

	public void setSendname(String sendname) {
		this.sendname = sendname;
	}

    
}