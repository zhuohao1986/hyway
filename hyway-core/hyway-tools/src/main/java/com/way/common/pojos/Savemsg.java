package com.way.common.pojos;

import javax.persistence.*;

public class Savemsg {
	
    private Integer sendid;

    private Integer getid;

    private String msg;

    private Byte trantype;

    private String time;

    private Byte resulttype;

    private Byte messagetype;

    private String sendname;

    /**
     * @return sendid
     */
    public Integer getSendid() {
        return sendid;
    }

    /**
     * @param sendid
     */
    public void setSendid(Integer sendid) {
        this.sendid = sendid;
    }

    /**
     * @return getid
     */
    public Integer getGetid() {
        return getid;
    }

    /**
     * @param getid
     */
    public void setGetid(Integer getid) {
        this.getid = getid;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * @return trantype
     */
    public Byte getTrantype() {
        return trantype;
    }

    /**
     * @param trantype
     */
    public void setTrantype(Byte trantype) {
        this.trantype = trantype;
    }

    /**
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    /**
     * @return resulttype
     */
    public Byte getResulttype() {
        return resulttype;
    }

    /**
     * @param resulttype
     */
    public void setResulttype(Byte resulttype) {
        this.resulttype = resulttype;
    }

    /**
     * @return messagetype
     */
    public Byte getMessagetype() {
        return messagetype;
    }

    /**
     * @param messagetype
     */
    public void setMessagetype(Byte messagetype) {
        this.messagetype = messagetype;
    }

    /**
     * @return sendname
     */
    public String getSendname() {
        return sendname;
    }

    /**
     * @param sendname
     */
    public void setSendname(String sendname) {
        this.sendname = sendname == null ? null : sendname.trim();
    }
}