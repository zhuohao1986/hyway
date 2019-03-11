package com.way.common.pojos.im;

import java.util.Date;

import javax.persistence.Column;

public class ImUser {
    /**
     * 自动生成的ID
     */
    private Integer id;

    private String account;

    private Byte gender;

    private String name;

    private String location;

    private Date birthday;

    @Column(name = "isOnline")
    private Byte isonline;

    private String password;

    private byte[] photo;

    /**
     * 获取自动生成的ID
     *
     * @return id - 自动生成的ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自动生成的ID
     *
     * @param id 自动生成的ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * @return gender
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return isOnline
     */
    public Byte getIsonline() {
        return isonline;
    }

    /**
     * @param isonline
     */
    public void setIsonline(Byte isonline) {
        this.isonline = isonline;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}