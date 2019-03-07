/*
 * package com.way.admin.web;
 * 
 * import java.util.Date; import java.util.List; import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.way.admin.BaseController; import
 * com.way.common.constant.CommonConstant; import
 * com.way.common.pojos.system.SysDept; import
 * com.way.common.pojos.system.dto.DeptTree; import
 * com.way.system.api.SysDeptService;
 * 
 *//**
	 * <p>
	 * 部门管理 前端控制器
	 * </p>
	 *
	 * @author
	 * @since
	 */
/*
 * @RestController
 * 
 * @RequestMapping("/dept") public class DeptController extends BaseController {
 * 
 * @Autowired private SysDeptService sysDeptService;
 * 
 *//**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysDept
	 */
/*
 * @GetMapping("/{id}") public SysDept get(@PathVariable Integer id) { return
 * sysDeptService.selectById(id); }
 * 
 * 
 *//**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
/*
 * @SuppressWarnings("unchecked")
 * 
 * @GetMapping(value = "/tree") public List<DeptTree> getTree() {
 * Map<String,Object> requestParamters = getRequestParamters();
 * requestParamters.put("selState", CommonConstant.STATUS_NORMAL); return
 * sysDeptService.selectListTree(requestParamters); }
 * 
 *//**
	 * 添加
	 *
	 * @param sysDept 实体
	 * @return success/false
	 */
/*
 * @PostMapping public Boolean add(@RequestBody SysDept sysDept) { return
 * sysDeptService.insertDept(sysDept); }
 * 
 *//**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
/*
 * @DeleteMapping("/{id}") public Boolean delete(@PathVariable Integer id) {
 * return sysDeptService.deleteDeptById(id); }
 * 
 *//**
	 * 编辑
	 *
	 * @param sysDept 实体
	 * @return success/false
	 *//*
		 * @PutMapping public Boolean edit(@RequestBody SysDept sysDept) {
		 * sysDept.setModifyTime(new Date()); return
		 * sysDeptService.updateDeptById(sysDept); } }
		 */