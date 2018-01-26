package com.example.demo.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.feign.FeginServiceClient;
import com.example.demo.util.RedisUtils;
@RestController
@RequestMapping("/html")
public class testController {
	
	@Autowired
	public FeginServiceClient feginServiceClient;
	@Autowired  
    protected RedisTemplate redisTemplate;
	 @Autowired
	private AmqpTemplate rabbitTemplate;
	@RequestMapping("/")
	public String html(){
		System.out.println("测试");
		//feginServiceClient.html("调用服务端");
		String userid=RedisUtils.getKV("userid");
		if(userid!=null && !userid.equals("")){
			System.out.println(userid);
		}else{
			//redisTemplate.opsForValue().set("userid","sunshuoyang");
			RedisUtils.setKV("userid", 60 * 60 * 2, "sunshuoyang");
		}
		
		rabbitTemplate.convertAndSend("ssy","sunshuoyang");
		System.out.println();
		//redisUtil.set("userid", "sunshuoyang");
		return "ok";
	}
}
