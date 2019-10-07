package com.ibm.eib.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/hi")
public class RestController {

	@RequestMapping(method = RequestMethod.GET, value = "/sayhi")
	public String sayHi() {
		return " Heloooooooooooooooooooooooo!!!";
	}
}
