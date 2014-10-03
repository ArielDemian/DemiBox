package org.demian.demibox.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.demian.demibox.dao.File;
import org.demian.demibox.dao.User;
import org.demian.demibox.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {
	private FilesService filesService;
	private UsersDAO usersDAO;

	@Autowired
	@Required
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Autowired
	@Required
	public void setFilesService(FilesService filesService) {
		this.filesService = filesService;
	}

	public void createUser(User user) {
		usersDAO.insert(user);
	}

	public boolean exists(String username) {
		return usersDAO.exists(username);
	}

	public boolean hasPassword(String username, String password) {
		return usersDAO.hasPassword(username, password);
	}

	public boolean changePassword(String username, String password) {
		return usersDAO.changePassword(username, password);
	}

	@Transactional
	public boolean deleteUser(HttpServletRequest request, String username) {
		List<File> files = filesService.getUsersFiles(username);
		for (File f : files) {
			filesService.deleteFile("" + f.getId(), request, username);
		}
		return usersDAO.delete(username);
	}
}