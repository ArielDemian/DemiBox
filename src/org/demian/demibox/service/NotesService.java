package org.demian.demibox.service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.demian.demibox.dao.Note;
import org.demian.demibox.dao.NotesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

@Service
public class NotesService implements MessageSourceAware {
	private NotesDAO notesDAO;
	private MessageSource messageSource;

	@Autowired
	@Required
	public void setNotesDAO(NotesDAO notesDAO) {
		this.notesDAO = notesDAO;
	}

	public List<Note> getUsersNotes(String username) {
		List<Note> notes = notesDAO.getUsersNotes(username);
		Collections.sort(notes);
		return notes;
	}

	public Note getNote(long id, String username) {
		return notesDAO.getNote(id, username);
	}

	public boolean modifyNote(Note note, String username) {
		String format = messageSource.getMessage("date.databaseFormat", null, Locale.ROOT);
		String timeZone = messageSource.getMessage("date.timeZone", null, Locale.ROOT);
		return notesDAO.modifyNote(note, username, format, timeZone);
	}

	public boolean noteExists(long id, String username) {
		return notesDAO.noteExists(id, username);
	}

	public boolean createNote(Note note, String username) {
		String format = messageSource.getMessage("date.databaseFormat", null, Locale.ROOT);
		String timeZone = messageSource.getMessage("date.timeZone", null, Locale.ROOT);
		return notesDAO.createNote(note, username, format, timeZone);
	}

	public boolean deleteNote(String id, String username) {
		return notesDAO.deleteNote(id, username);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}