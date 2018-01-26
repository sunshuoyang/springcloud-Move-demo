package com.example.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(value = "SPRINGCLOUD-SERVICE-SSY")
public interface FeginServiceClient {

	@RequestMapping("/html/")
    String html(@RequestParam("par") String par);
}
