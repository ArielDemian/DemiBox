package org.demian.demibox.service;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.demian.demibox.controllers.IDNotCreatedException;
import org.demian.demibox.dao.File;
import org.demian.demibox.dao.FilesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class FilesService implements MessageSourceAware {
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 2; // 3MB
	private static final long MAX_FILE_SIZE = Long.MAX_VALUE; // 200MB
	private static final long MAX_REQUEST_SIZE = Long.MAX_VALUE; // 400MB
	private static final String UPLOAD_DIRECTORY = "uploaded_files";
	private static final int BUFFER_SIZE = 4096;
	private FilesDAO filesDAO;
	private MessageSource messageSource;

	@Autowired
	@Required
	public void setFilesDAO(FilesDAO filesDAO) {
		this.filesDAO = filesDAO;
	}

	public List<File> getUsersFiles(String username) {
		List<File> files = filesDAO.getUsersFiles(username);
		Collections.sort(files);
		return files;
	}

	public long insert(String fileName, String username) {
		String format = messageSource.getMessage("date.databaseFormat", null, Locale.ROOT);
		String timeZone = messageSource.getMessage("date.timeZone", null, Locale.ROOT);
		return filesDAO.insert(fileName, username, format, timeZone);
	}

	public boolean delete(String id) {
		return filesDAO.delete(id);
	}

	public File getFile(String id) {
		return filesDAO.getFile(id);
	}

	@Transactional
	public boolean fileUpload(HttpServletRequest request, Model model, String username) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			model.addAttribute("message", "Form must have 'enctype=multipart/form-data'.");
			return false;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new java.io.File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		String uploadPath = request.getSession().getServletContext().getRealPath("") + java.io.File.separator + UPLOAD_DIRECTORY;
		long id = -1;
		boolean uploadedFileCompleted = false;
		try {
			List<FileItem> formItems = upload.parseRequest(request);
			if (formItems != null && formItems.size() > 0) {
				for (FileItem item : formItems) {
					if (!item.isFormField()) {
						String fileName = new java.io.File(item.getName()).getName();
						// Retrieve ID from the FilesService, after insertion of the
						// row in the database
						id = this.insert(fileName, username);
						if (id < 0)
							throw new IDNotCreatedException();
						java.io.File uploadDir = new java.io.File(uploadPath + java.io.File.separator + id);
						if (!uploadDir.exists())
							uploadDir.mkdirs();
						String filePath = uploadPath + java.io.File.separator + id + java.io.File.separator + fileName;
						java.io.File storeFile = new java.io.File(filePath);
						uploadedFileCompleted = false;
						item.write(storeFile);
						uploadedFileCompleted = true;
					}
				}
			}
		} catch (Exception e) {
			if (id > 0 && !uploadedFileCompleted)
				this.delete("" + id);
			model.addAttribute("message", messageSource.getMessage("error.uploadFile.generic", new String[] { e.getMessage() }, Locale.ROOT));
			return false;
		}
		return true;
	}

	public void fileDownload(String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		String downloadPath = request.getSession().getServletContext().getRealPath("") + java.io.File.separator + UPLOAD_DIRECTORY + java.io.File.separator + id;
		org.demian.demibox.dao.File file = this.getFile(id);
		String fullPath = downloadPath + java.io.File.separator + file.getName();
		java.io.File downloadFile = new java.io.File(fullPath);
		String mimeType = request.getSession().getServletContext().getMimeType(fullPath);
		if (mimeType == null)
			mimeType = "application/octet-stream";
		response.setHeader("Content-Length", String.valueOf(downloadFile.length()));
		response.setContentType(mimeType);
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);
		try {
			FileInputStream inputStream = new FileInputStream(downloadFile);
			OutputStream outputStream = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
		}
	}

	public void deleteFile(String id, HttpServletRequest request, String username) {
		String folderPath = request.getSession().getServletContext().getRealPath("") + java.io.File.separator + UPLOAD_DIRECTORY + java.io.File.separator + id;
		java.io.File folder = new java.io.File(folderPath);
		if (!folder.exists()) {
			this.delete(id);
		}
		String[] entries = folder.list();
		if (entries != null) {
			for (String s : entries) {
				java.io.File currentFile = new java.io.File(folder.getPath(), s);
				currentFile.delete();
			}
			folder.delete();
			this.delete(id);
		}
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}