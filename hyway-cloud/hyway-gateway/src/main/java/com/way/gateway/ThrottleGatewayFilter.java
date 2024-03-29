package com.way.gateway;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Sample throttling filter.
 * See https://github.com/bbeck/token-bucket
 */
public class ThrottleGatewayFilter implements GatewayFilter {
	private static final Logger log = LoggerFactory.getLogger(ThrottleGatewayFilter.class);

    int capacity;
    int refillTokens;
    int refillPeriod;
    TimeUnit refillUnit;

	public int getCapacity() {
		return capacity;
	}

	public ThrottleGatewayFilter setCapacity(int capacity) {
		this.capacity = capacity;
		new Date();
		return this;
	}

	public int getRefillTokens() {
		return refillTokens;
	}

	public ThrottleGatewayFilter setRefillTokens(int refillTokens) {
		this.refillTokens = refillTokens;
		return this;
	}

	public int getRefillPeriod() {
		return refillPeriod;
	}

	public ThrottleGatewayFilter setRefillPeriod(int refillPeriod) {
		this.refillPeriod = refillPeriod;
		return this;
	}

	public TimeUnit getRefillUnit() {
		return refillUnit;
	}

	public ThrottleGatewayFilter setRefillUnit(TimeUnit refillUnit) {
		this.refillUnit = refillUnit;
		return this;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		TokenBucket tokenBucket = TokenBuckets.builder()
				.withCapacity(capacity)
				.withFixedIntervalRefillStrategy(refillTokens, refillPeriod, refillUnit)
				.build();

        //TODO: get a token bucket for a key
        log.debug("TokenBucket capacity: " + tokenBucket.getCapacity());
        boolean consumed = tokenBucket.tryConsume();
        if (consumed) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
	}
}