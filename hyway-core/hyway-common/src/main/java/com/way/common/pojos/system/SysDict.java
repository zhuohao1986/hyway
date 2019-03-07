package com.way.common.pojos.system;

import javax.persistence.*;

@Table(name = "t_sys_dict")
public class SysDict {
    @Id
    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "dict_type")
    private String dictType;

    @Column(name = "dict_code")
    private String dictCode;

    @Column(name = "dict_code_desc")
    private String dictCodeDesc;

    @Column(name = "dict_type_desc")
    private String dictTypeDesc;

    @Column(name = "dict_lang")
    private String dictLang;

    @Column(name = "dictSort")
    private String dictsort;

    /**
     * @return dict_id
     */
    public Integer getDictId() {
        return dictId;
    }

    /**
     * @param dictId
     */
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    /**
     * @return dict_type
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * @param dictType
     */
    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    /**
     * @return dict_code
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * @param dictCode
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    /**
     * @return dict_code_desc
     */
    public String getDictCodeDesc() {
        return dictCodeDesc;
    }

    /**
     * @param dictCodeDesc
     */
    public void setDictCodeDesc(String dictCodeDesc) {
        this.dictCodeDesc = dictCodeDesc == null ? null : dictCodeDesc.trim();
    }

    /**
     * @return dict_type_desc
     */
    public String getDictTypeDesc() {
        return dictTypeDesc;
    }

    /**
     * @param dictTypeDesc
     */
    public void setDictTypeDesc(String dictTypeDesc) {
        this.dictTypeDesc = dictTypeDesc == null ? null : dictTypeDesc.trim();
    }

    /**
     * @return dict_lang
     */
    public String getDictLang() {
        return dictLang;
    }

    /**
     * @param dictLang
     */
    public void setDictLang(String dictLang) {
        this.dictLang = dictLang == null ? null : dictLang.trim();
    }

    /**
     * @return dictSort
     */
    public String getDictsort() {
        return dictsort;
    }

    /**
     * @param dictsort
     */
    public void setDictsort(String dictsort) {
        this.dictsort = dictsort == null ? null : dictsort.trim();
    }
}