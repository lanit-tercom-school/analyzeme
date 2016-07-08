package com.analyzeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ������ on 11.04.2016.
 */
@Controller
public class AppController {
	@RequestMapping(value = "/app/**")
	public ModelAndView moveToAppPage() {
		return new ModelAndView("app");
	}
}
