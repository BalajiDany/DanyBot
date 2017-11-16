package com.source.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping(value = "/")
	private String initialLoad() {
		return "dashboard";
	}
}
