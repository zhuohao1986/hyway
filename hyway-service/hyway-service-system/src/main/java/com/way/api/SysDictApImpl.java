package com.way.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.way.api.system.SysDictApi;
import com.way.common.cache.JedisClient;
import com.way.common.constant.CodeConstants;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.exception.BusinessException;
import com.way.common.exception.ClientToolsException;
import com.way.common.pojos.system.SysDict;
import com.way.common.stdo.RequestWrapper;
import com.way.common.stdo.Result;
import com.way.system.service.SysDictService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 * 
 */
@Service
public class SysDictApImpl implements SysDictApi {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private SysDictService sysDictService;

	Result result = new Result(CodeConstants.RESULT_FAIL);

	@Override
	public String selectSysDictPage(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),
				new TypeReference<HashMap<String, Object>>() {
				});
		PageInfo<SysDict> sysDictpage = sysDictService.selectPage(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(sysDictpage);
		return result.toJSONString();
	}

	@Override
	public String sysDict(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer dictId = obj.getInteger("dictId");
		SysDict sysDict = sysDictService.selectById(dictId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(sysDict));
		return result.toJSONString();
	}

	@Override
	public String selectList(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Map<String, Object> paramMap = JSONObject.parseObject(obj.toString(),
				new TypeReference<HashMap<String, Object>>() {
				});
		List<SysDict> sysDictList = sysDictService.selectList(paramMap);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(sysDictList));
		return result.toJSONString();
	}

	@Override
	public String insert(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysDict sysDict = JSONObject.parseObject(rw.getValue(), SysDict.class);
		int insert = sysDictService.insert(sysDict);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(insert));
		return result.toJSONString();
	}

	@Override
	public String deleteById(String param) throws BusinessException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		JSONObject obj = JSONObject.parseObject(rw.getValue());
		Integer dictId = obj.getInteger("dictId");
		int deleteById = sysDictService.deleteById(dictId);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(deleteById));
		return result.toJSONString();
	}

	@Override
	public String updateSysDict(String param) throws ClientToolsException {
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		SysDict sysDict = JSONObject.parseObject(rw.getValue(), SysDict.class);
		int updateSysDict = sysDictService.updateSysDict(sysDict);
		result.setCode(CodeConstants.RESULT_SUCCESS);
		result.setValue(JSONObject.toJSONString(updateSysDict));
		return result.toJSONString();
	}

	@Override
	public String refresh(String param) throws ClientToolsException{
		RequestWrapper rw = JSONObject.parseObject(param, RequestWrapper.class);
		Map<String, Object> paramMap = JSONObject.parseObject(rw.getValue(),new TypeReference<HashMap<String, Object>>() {});
		List<SysDict> sysDictList=sysDictService.selectList(paramMap);
		Map<String, Map<String, String>> dictMap = convertListToMap(sysDictList);
		jedisClient.set(ConfigKeyConstant.REDIS_SYS_DICT_KEY, JSONObject.toJSONString(dictMap));
		result.setCode(CodeConstants.RESULT_SUCCESS);
		return result.toJSONString();
	}

	/**
	 * 将字典list转换为Map
	 *
	 * @param dictList
	 * @return
	 */
	private Map<String, Map<String, String>> convertListToMap(List<SysDict> dictList) {
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		for (SysDict dict : dictList) {
			String groupId = dict.getDictType();
			Map<String, String> dictMap = resultMap.get(groupId);
			if (dictMap != null) {
				dictMap.put(dict.getDictCode(), dict.getDictCode());
			} else {
				dictMap = new HashMap<>();
				dictMap.put(dict.getDictCode(), dict.getDictCode());
				resultMap.put(groupId, dictMap);
			}
		}
		return resultMap;
	}
}
