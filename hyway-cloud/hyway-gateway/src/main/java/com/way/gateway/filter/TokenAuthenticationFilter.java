package com.way.gateway.filter;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.way.common.auth.JWTUtil;
import com.way.common.constant.ConfigKeyConstant;
import com.way.common.utils.StringUtils;

import reactor.core.publisher.Mono;

/**
 * 可对客户端header 中的 Authorization 信息进行认证
 */
@Component
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<Object> {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	
	public static final String[] ignoreUrl= {"/auth/auth/login"};
	
	public final static String REDIS_USER_SESSION_KEY = "Bearer ";

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpRequest.Builder mutate = request.mutate();
			ServerHttpResponse response = exchange.getResponse();
			try {
				boolean flag = false;
		        for (String page:ignoreUrl) {
		            if (request.getURI().getPath().equals(page)){
		                flag = true;
		            }
		        }
		        if(flag) {
		        	ServerHttpRequest build = mutate.build();
					return chain.filter(exchange.mutate().request(build).build());
		        }
				// 获取header中的Authorization
				String headerAuthorization = request.getHeaders().getFirst("Authorization");
				logger.info("header Authorization :{} ",headerAuthorization);

				if (headerAuthorization == null || !headerAuthorization.startsWith(REDIS_USER_SESSION_KEY)) {
					throw new RuntimeException("请求头中Authorization信息为空");
				}
				// 截取Authorization Bearer
				// 可把token存到redis中，此时直接在redis中判断是否有此key，有则校验通过，否则校验失败
				if (!StringUtils.isEmpty(headerAuthorization)) {
					boolean verifyResult = JWTUtil.verify(headerAuthorization);
					if (!verifyResult) {
						response.setStatusCode(HttpStatus.OK);
						response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
						DataBuffer bodyDataBuffer = responseErrorInfo(response, HttpStatus.UNAUTHORIZED.toString(),"无效的请求");
						return response.writeWith(Mono.just(bodyDataBuffer));
					}
					String userInfo = JWTUtil.getUserInfo(headerAuthorization);
					
					// 有token，把token设置到header中，传递给后端服务
					mutate.header("userDetails", userInfo).build();
				} else {
					// token无效
					logger.info("token无效");
					DataBuffer bodyDataBuffer = responseErrorInfo(response, HttpStatus.UNAUTHORIZED.toString(),"无效的请求");
					return response.writeWith(Mono.just(bodyDataBuffer));
				}
			} catch (Exception e) {
				// 没有token
				DataBuffer bodyDataBuffer = responseErrorInfo(response, HttpStatus.UNAUTHORIZED.toString(),
						e.getMessage());
				return response.writeWith(Mono.just(bodyDataBuffer));
			}
			ServerHttpRequest build = mutate.build();
			return chain.filter(exchange.mutate().request(build).build());
		};
	}

	/**
	 * 自定义返回错误信息
	 * 
	 * @param response
	 * @param status
	 * @param message
	 * @return
	 */
	public DataBuffer responseErrorInfo(ServerHttpResponse response, String status, String message) {
		HttpHeaders httpHeaders = response.getHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		Map<String, String> map = new HashMap<>();
		map.put("status", status);
		map.put("message", message);
		DataBuffer bodyDataBuffer = response.bufferFactory().wrap(map.toString().getBytes());
		return bodyDataBuffer;
	}
}