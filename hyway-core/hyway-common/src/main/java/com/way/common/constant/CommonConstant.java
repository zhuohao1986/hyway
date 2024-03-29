package com.way.common.constant;

/**
 * 全局公共常量
 *
 * @author 
 * @date 2017/10/29
 */
public interface CommonConstant {
    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * 标签 header key
     */
    String HEADER_LABEL = "x-label";

    /**
     * 标签 header 分隔符
     */
    String HEADER_LABEL_SPLIT = ",";

    /**
     * 标签或 名称
     */
    String LABEL_OR = "labelOr";

    /**
     * 标签且 名称
     */
    String LABEL_AND = "labelAnd";

    /**
     * 权重key
     */
    String WEIGHT_KEY = "weight";

    /**
     * 删除
     */
    Integer STATUS_DEL = 1;

    /**
     * 正常
     */
    Integer STATUS_NORMAL = 0;

    /**
     * 锁定
     */
    Integer STATUS_LOCK = 9;

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 按钮
     */
    String BUTTON = "1";

    /**
     * 删除标记
     */
    String DEL_FLAG = "del_state";
    
    int DEL_FLAG_TRUE =1;
    
    int DEL_FLAG_FALSE = 0;

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 路由信息Redis保存的key
     */
    String ROUTE_KEY = SecurityConstants.TAROCO_PREFIX + "ROUTE_LIST";

    /**
         * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "admin";

    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * default LINE_SEPARATOR
     */
    String LINE_SEPARATOR = "/";
}
