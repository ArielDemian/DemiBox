package org.demian.demibox.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.demian.demibox.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilesController extends AbstractController {
	private FilesService filesService;

	@Transactional
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0) {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
		if (filesService.fileUpload(request, model, username))
			return "redirect:/view_files";
		else
			return "error";
	}

	@RequestMapping(value = "/upload_file", method = RequestMethod.GET)
	public String showUploadFile() {
		return "upload_file";
	}

	@RequestMapping(value = "/view_files", method = RequestMethod.GET)
	public String showFiles(Model model) {
		String username = getUsername();
		if (username != null && username.length() > 0) {
			model.addAttribute("files", filesService.getUsersFiles(username));
			return "view_files";
		} else {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void handleFileDownload(@RequestParam(value = "id", required = true) String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		filesService.fileDownload(id, request, response, model);
	}

	@RequestMapping(value = "/delete_file", method = RequestMethod.GET)
	public String handleDeleteFile(@RequestParam(value = "id", required = true) String id, HttpServletRequest request, Model model) {
		String username = getUsername();
		if (username != null && username.length() > 0) {
			filesService.deleteFile(id, request, username);
			return "redirect:/view_files";
		} else {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
	}

	@Autowired
	@Required
	public void setFilesService(FilesService filesService) {
		this.filesService = filesService;
	}
}