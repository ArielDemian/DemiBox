package org.demian.demibox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController extends AbstractController{
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome() {
		String username = getUsername();
		if (username == null || username.length() == 0)
			return "welcome";
		return "index";
	}
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value="/manage_account",method=RequestMethod.GET)
	public String showManageAccount(){
		return "manage_account";
	}
}