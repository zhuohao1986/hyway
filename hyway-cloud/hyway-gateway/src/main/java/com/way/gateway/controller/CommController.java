package com.way.gateway.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.way.common.constant.CodeConstants;
import com.way.common.stdo.Result;

@RestController
@RequestMapping("/default")
public class CommController {

	// 增加路由
		@RequestMapping("/defaultfallback")
		public String add(ServerHttpRequest request, ServerHttpResponse response) {
			Result result = new Result(CodeConstants.RESULT_FAIL);
			result.setMessage("网关路由超时");
			return result.toJSONString();
		}
}
