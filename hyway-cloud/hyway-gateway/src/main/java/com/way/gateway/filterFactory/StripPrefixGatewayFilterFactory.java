package com.way.gateway.filterFactory;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * This filter removes the first part of the path, known as the prefix, from the request
 * before sending it downstream
 * @author Ryan Baxter
 */
@Component
public class StripPrefixGatewayFilterFactory extends AbstractGatewayFilterFactory<StripPrefixGatewayFilterFactory.Config> {

    public static final String PARTS_KEY = "api";

    public StripPrefixGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARTS_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->  {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getRawPath();
            String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(path, "/"))
                    .skip(config.parts).collect(Collectors.joining("/"));
            ServerHttpRequest newRequest = request.mutate()
                    .path(newPath)
                    .build();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    public static class Config {
        private int parts;

        public int getParts() {
            return parts;
        }

        public void setParts(int parts) {
            this.parts = parts;
        }
    }
}