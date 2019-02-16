package com.way.admin.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.way.admin.BaseController;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.pojos.system.SysDict;
import com.way.common.stdo.Result;
import com.way.system.api.SysDictService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author 
 * @since 2017-11-19
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/{id}")
    public String dict(@PathVariable Integer id) {
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	SysDict sysDict = sysDictService.selectById(id);
    	result.setValue(JSONObject.toJSON(sysDict));
        return result.toJSONString();
    }

    /**
     * 分页查询字典信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/dictPage")
    public String dictPage() {
    	initParams();
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
    	PageInfo<SysDict> selectPage = sysDictService.selectPage(jsonData);
    	result.setValue(JSONObject.toJSON(selectPage));
        return result.toString();
    }

    /**
     * 通过字典类型查找字典
     *
     * @param type 类型
     * @return 同类型字典
     */
    @GetMapping("/type/{type}")
    public String findDictByType(@PathVariable String type) {
    	initParams();
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
    	List<SysDict> list=sysDictService.selectList(jsonData);
    	result.setValue(list);
        return result.toJSONString();
    }

    /**
     * 添加字典
     *
     * @param sysDict 字典信息
     * @return success、false
     */
    @PostMapping
    public String dict() {
    	initParams();
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	int insert = sysDictService.insert(jsonData);
    	result.setValue(insert);
        return result.toString();
    }

    /**
     * 删除字典，并且清除字典缓存
     *
     * @param id   ID
     * @param type 类型
     * @return R
     */
    @DeleteMapping("/{id}/{type}")
    public String deleteDict(@PathVariable Integer id, @PathVariable String type) {
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	int del = sysDictService.deleteById(id);
    	result.setValue(del);
        return result.toJSONString();
    }

    /**
     * 修改字典
     *
     * @param sysDict 字典信息
     * @return success/false
     */
    @PutMapping
    @SuppressWarnings("unchecked")
    public String editDict(@RequestBody SysDict sysDict) {
		Map<String,Object> requestParamters = getRequestParamters();
    	Result result=new Result(CodeConstants.RESULT_SUCCESS);
    	int del = sysDictService.updateSysDict(requestParamters);
    	result.setValue(del);
        return result.toJSONString();
    }
}
