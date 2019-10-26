package com.way.common.pojos;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_auth")
public class SysAuth {
    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "auth_time")
    private Date authTime;

    @Column(name = "auth_ip")
    private String authIp;

    @Column(name = "auth_address")
    private String authAddress;

    /**
     * 0等待验证，1验证成功，2正在验证，3验证失败（过期）
     */
    @Column(name = "auth_state")
    private Boolean authState;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * @return auth_token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken == null ? null : authToken.trim();
    }

    /**
     * @return auth_time
     */
    public Date getAuthTime() {
        return authTime;
    }

    /**
     * @param authTime
     */
    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    /**
     * @return auth_ip
     */
    public String getAuthIp() {
        return authIp;
    }

    /**
     * @param authIp
     */
    public void setAuthIp(String authIp) {
        this.authIp = authIp == null ? null : authIp.trim();
    }

    /**
     * @return auth_address
     */
    public String getAuthAddress() {
        return authAddress;
    }

    /**
     * @param authAddress
     */
    public void setAuthAddress(String authAddress) {
        this.authAddress = authAddress == null ? null : authAddress.trim();
    }

    /**
     * 获取0等待验证，1验证成功，2正在验证，3验证失败（过期）
     *
     * @return auth_state - 0等待验证，1验证成功，2正在验证，3验证失败（过期）
     */
    public Boolean getAuthState() {
        return authState;
    }

    /**
     * 设置0等待验证，1验证成功，2正在验证，3验证失败（过期）
     *
     * @param authState 0等待验证，1验证成功，2正在验证，3验证失败（过期）
     */
    public void setAuthState(Boolean authState) {
        this.authState = authState;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}