package com.ibm.eib.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.eib.service.WebexService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/hi")
public class RestController {

	@Autowired
	private WebexService webexService;
	
	public WebexService getWebexService() {
		return webexService;
	}
	public void setWebexService(WebexService webexService) {
		this.webexService = webexService;
	}
	 
	@RequestMapping(method = RequestMethod.GET, value = "/sayhi")
	public String sayHi(@RequestParam("code") String code) {
		
		return webexService.generateWidget(code);
	}
}
