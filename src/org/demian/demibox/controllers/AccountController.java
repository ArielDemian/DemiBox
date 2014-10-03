package org.demian.demibox.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.demian.demibox.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController extends AbstractController {
	private UsersService usersService;

	@RequestMapping(value = "/delete_account", method = RequestMethod.GET)
	public String deleteAccount(HttpServletRequest request) {
		String username = getUsername();
		if (username == null || username.length() < 0)
			return "redirect:/";
		if (usersService.deleteUser(request, username)) {
			return "redirect:/j_spring_security_logout";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public String changePassword(@RequestParam(value = "new_password", required = true) String password, @RequestParam(value = "old_password", required = true) String oldPassword, Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0)
			return "redirect:/";
		if (usersService.hasPassword(username, oldPassword)) {
			if (usersService.changePassword(username, password)) {
				return "password_changed";
			} else {
				model.addAttribute("message", messageSource.getMessage("error.changePassword.notPossible", null, Locale.ROOT));
			}
		} else {
			model.addAttribute("message", messageSource.getMessage("error.changePassword.incorrectPassword", null, Locale.ROOT));
		}
		return "error";
	}

	@Autowired
	@Required
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
}