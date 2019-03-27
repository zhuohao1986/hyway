package com.way.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.way.api.feign.SysDeptFeignApi;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.CommonConstant;
import com.way.common.context.BaseController;
import com.way.common.pojos.system.SysDept;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author
 * @since
 */

@RestController

@RequestMapping("/dept")
public class DeptController extends BaseController {

	@Autowired
	private SysDeptFeignApi sysDeptApi;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysDept
	 */

	@GetMapping("/{id}")
	public String get(@PathVariable Integer id) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.sysDept(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public String getTree() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			jsonData.put("selState", CommonConstant.STATUS_NORMAL);
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.selectListTree(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}

	/**
	 * 添加
	 *
	 * @param sysDept 实体
	 * @return success/false
	 */

	@PostMapping
	public String add() {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.insert(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Integer id) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.deleteById(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}

	/**
	 * 编辑
	 *
	 * @param sysDept 实体
	 * @return success/false
	 */
	@PutMapping
	public String edit(@RequestBody SysDept sysDept) {
		Result result = new Result(CodeConstants.RESULT_SUCCESS);
		try {
			initParams();
			RequestWrapper rw=new RequestWrapper(CodeConstants.ALL_REQUEST_CHANNEL_WEB, jsonData.toString());
			String SysDeptPageStr = sysDeptApi.updateSysDept(rw.toString());
			result = JSONObject.parseObject(SysDeptPageStr, Result.class);
		} catch (Exception e) {
			
		}
		return result.toString();
	}
}
