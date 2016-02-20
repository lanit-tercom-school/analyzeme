package com.analyzeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActionController {
	@RequestMapping(value = "/index")
	public String moveToIndexPage() {
		return "index";
	}
}
