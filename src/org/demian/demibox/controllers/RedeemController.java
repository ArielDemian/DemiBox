package org.demian.demibox.controllers;

import java.security.SecureRandom;
import java.util.Locale;

import javax.annotation.Resource;

import org.demian.demibox.dao.RedeemCodesDAO;
import org.demian.demibox.dao.User;
import org.demian.demibox.service.UsersService;
import org.demian.demibox.validation.ValidEmailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RedeemController extends AbstractController {
	private PasswordEncoder passwordEncoder;

	private String emailText;
	private MailSender mailSender;
	private ValidEmailImpl emailValidator;
	private RedeemCodesDAO codesDAO;
	private UsersService usersService;
	private SecureRandom random;
	private char[] symbols;

	public RedeemController() {
		emailValidator = new ValidEmailImpl();
		emailValidator.initialize(null);
		random = new SecureRandom();
		symbols = new char[62];
		int i = 0;
		char c;
		for (c = 'a'; c <= 'z'; c++)
			symbols[i++] = c;
		for (c = 'A'; c <= 'Z'; c++)
			symbols[i++] = c;
		for (c = '0'; c <= '9'; c++)
			symbols[i++] = c;
	}

	private String generatePassword(int n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n; i++) {
			s.append(symbols[random.nextInt(symbols.length)]);
		}
		return s.toString();
	}

	private String generatePassword() {
		return this.generatePassword(20);
	}

	@RequestMapping(value = "/redeem_code", method = RequestMethod.GET)
	public String showRedeemPage() {
		return "redeem_code";
	}

	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	public String redeem(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "code", required = true) String code, Model model) {
		if (emailValidator.isValid(email, null)) {
			String emailDemiBox = messageSource.getMessage("demibox.email", null, Locale.ROOT);
			if (usersService.exists(email)) {
				model.addAttribute("message", messageSource.getMessage("error.redeem.emailAlreadyUsed", new String[] { emailDemiBox }, Locale.ROOT));
				return "error";
			}
			if (codesDAO.exists(code)) {
				String password = generatePassword();
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setFrom(emailDemiBox);
				mail.setTo(email);
				mail.setSubject(messageSource.getMessage("redeem.emailTitle", null, Locale.ROOT));
				mail.setText(String.format(emailText, email, password));
				try {
					mailSender.send(mail);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", messageSource.getMessage("error.redeem.couldNotSendEmail", null, Locale.ROOT));
					return "error";
				}
				User user = new User(email, passwordEncoder.encode(password), true, "ROLE_USER");
				usersService.createUser(user);
				codesDAO.deleteCode(code);
				return "redeem_successful";
			} else {
				model.addAttribute("message", messageSource.getMessage("error.redeem.insertedCodeNotValid", null, Locale.ROOT));
			}
		} else {
			model.addAttribute("message", messageSource.getMessage("error.redeem.insertedEmailNotValid", null, Locale.ROOT));
		}
		return "error";
	}

	@Autowired
	@Required
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Resource(name = "contents")
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	@Autowired
	@Required
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	@Required
	public void setCodesDAO(RedeemCodesDAO codesDAO) {
		this.codesDAO = codesDAO;
	}

	@Autowired
	@Required
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
}