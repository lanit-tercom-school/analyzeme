package com.analyzeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping(value = "/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");

		String msg = "Running IndexController.index() method";

		mav.addObject("msg", msg);
		return mav;
	}

	@RequestMapping(value = "/action")
	public String moveToActionPage() {
		return "action";
	}

	@RequestMapping(value = "/projects")
	public String moveToProjectPage() { return "projects"; }

}
