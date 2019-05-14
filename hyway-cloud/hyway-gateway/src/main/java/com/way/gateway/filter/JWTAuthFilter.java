package com.way.gateway.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;
import com.way.common.auth.JWTUtil;
import com.way.common.constant.CodeConstants;
import com.way.common.stdo.Result;
import com.way.common.utils.StringUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return -100;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String url = exchange.getRequest().getURI().getPath();

		// 忽略以下url请求
		if (url.indexOf("/auth/auth/login") >= 0) {
			return chain.filter(exchange);
		}
		// 从请求头中取得token
		String token = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (StringUtils.isEmpty(token)) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.OK);
			response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
			Result res = new Result(CodeConstants.RESULT_FAIL, "invalid token");
			byte[] responseByte = JSONObject.toJSONBytes(res);

			DataBuffer buffer = response.bufferFactory().wrap(responseByte);
			return response.writeWith(Flux.just(buffer));
		}
		// 请求中的token是否在redis中存在
		boolean verifyResult = JWTUtil.verify(token);
		if (!verifyResult) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.OK);
			response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

			Result res = new Result(CodeConstants.RESULT_FAIL, "invalid token");
			byte[] responseByte = JSONObject.toJSONBytes(res).toString().getBytes(StandardCharsets.UTF_8);

			DataBuffer buffer = response.bufferFactory().wrap(responseByte);
			return response.writeWith(Flux.just(buffer));
		}

		return chain.filter(exchange);
	}
}
