package com.way.api.open;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("open-service")
public interface OpenApi {

}
