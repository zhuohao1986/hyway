package com.way.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysDictFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.context.BaseController;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author
 * @since 2017-11-19
 */
@RestController
@RequestMapping("system/dict")
public class DictController extends BaseController {

	@Autowired
	private SysDictFeignApi sysDictApi;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
	@ApiOperation(value = "通过ID查询字典信息", notes = "通过ID查询字典信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "path") })
	@GetMapping("/{id}")
	public String dict(@PathVariable Integer id) {
		Result result = null;
		try {
			initParams();
			jsonData.put("dictId", id);
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String sysDictStr = sysDictApi.sysDict(rw.toString());
			result = JSONObject.parseObject(sysDictStr, Result.class);
		} catch (Exception e) {

		}
		return result.toJSONString();
	}

	/**
	 * 分页查询字典信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "limit", value = "条数", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query") })
	@GetMapping("/dictPage")
	public String dictPage() {
		Result result = null;
		try {
			initParams();
			jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
			String pageStr = sysDictApi.selectSysDictPage(jsonData.toString());
			result = JSONObject.parseObject(pageStr, Result.class);
		} catch (Exception e) {
		}
		return result.toJSONString();
	}

	/**
	 * 通过字典类型查找字典
	 *
	 * @param type 类型
	 * @return 同类型字典
	 */
	@ApiOperation(value = "通过字典类型查找字典", notes = "通过字典类型查找字典")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "path") })
	@GetMapping("/type/{type}")
	public String findDictByType(@PathVariable String type) {
		Result result = null;
		try {
			initParams();
			jsonData.put("dictType", type);
			jsonData.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String listStr = sysDictApi.selectList(rw.toString());
			result = JSONObject.parseObject(listStr, Result.class);
		} catch (Exception e) {
		}
		return result.toJSONString();
	}

	/**
	 * 添加字典
	 *
	 * @param sysDict 字典信息
	 * @return success、false
	 */
	@ApiOperation(value = "添加字典", notes = "添加字典")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query") })
	@PostMapping("/insert")
	public String dict() {
		Result result = null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String insertStr = sysDictApi.insert(rw.toString());
			result = JSONObject.parseObject(insertStr, Result.class);
		} catch (Exception e) {
		}
		return result.toJSONString();
	}

	/**
	 * 删除字典，并且清除字典缓存
	 *
	 * @param id   ID
	 * @param type 类型
	 * @return R
	 */
	@ApiOperation(value = "删除字典", notes = "删除字典")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "key", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "query") })
	@DeleteMapping("/{id}/{type}")
	public String deleteDict(@PathVariable Integer id, @PathVariable String type) {
		Result result = null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String delStr = sysDictApi.deleteById(rw.toString());
			result = JSONObject.parseObject(delStr, Result.class);
		} catch (Exception e) {
		}
		return result.toJSONString();
	}

	/**
	 * 修改字典
	 *
	 * @param sysDict 字典信息
	 * @return success/false
	 */
	@ApiOperation(value = "修改字典", notes = "修改字典")
	@ApiImplicitParams({ @ApiImplicitParam(name = "dictType", value = "dictType", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictCode", value = "dictCode", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictType", value = "dictType", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictCodeDesc", value = "dictCodeDesc", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictTypeDesc", value = "dictTypeDesc", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictLang", value = "dictLang", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dictsort", value = "dictsort", dataType = "String", paramType = "query"),})
	@PutMapping
	public String editDict() {
		Result result = null;
		try {
			initParams();
			RequestWrapper rw = new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String updateStr = sysDictApi.updateSysDict(rw.toString());
			result = JSONObject.parseObject(updateStr, Result.class);
		} catch (Exception e) {
		}
		return result.toJSONString();
	}

}
