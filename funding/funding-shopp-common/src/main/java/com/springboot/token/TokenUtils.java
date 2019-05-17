
package com.springboot.token;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtils {

	public String getToken(){

		return UUID.randomUUID().toString();
	   }
	/**
	 * 获取支付token
	 * @return
	 */
	public String getPayToken() {
		return "pay-" + UUID.randomUUID().toString();
	}
	
}
