package org.demian.demibox.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.demian.demibox.dao.Note;
import org.demian.demibox.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController extends AbstractController {
	private NotesService notesService;

	@RequestMapping(value = "/view_notes", method = RequestMethod.GET)
	public String showViewNotes(Model model) {
		String username = getUsername();
		if (username != null && username.length() > 0) {
			model.addAttribute("notes", notesService.getUsersNotes(username));
			return "view_notes";
		}
		model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
		return "error";
	}

	@RequestMapping(value = "/modify_note", method = RequestMethod.GET)
	public String showModifyNote(@RequestParam("id") long id, Model model) {
		if (id < 0)
			return showViewNotes(model);
		String username = getUsername();
		if (username != null && username.length() > 0) {
			Note note = notesService.getNote(id, username);
			model.addAttribute("note", note);
			return "modify_note";
		}
		model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
		return "error";
	}

	@RequestMapping(value = "/note_modified", method = RequestMethod.POST)
	public String showNoteModified(Model model, @Valid Note note, @RequestParam("id") long id, BindingResult result) {
		String username = getUsername();
		if (username != null && username.length() > 0) {
			Note originalNote = notesService.getNote(id, username);
			originalNote.setTitle(note.getTitle());
			originalNote.setText(note.getText());
			if (result.hasErrors() || !notesService.modifyNote(originalNote, username)) {
				model.addAttribute("note", originalNote);
				return "modify_note";
			} else {
				return showViewNotes(model);
			}
		} else {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
	}

	@RequestMapping(value = "/create_note", method = RequestMethod.GET)
	public String showCreateNote(Model model) {
		model.addAttribute("note", new Note());
		return "create_note";
	}

	@RequestMapping(value = "/note_created", method = RequestMethod.POST)
	public String showNoteCreated(Model model, @Valid Note note, BindingResult result) {
		if (result.hasErrors())
			return "create_note";
		String username = getUsername();
		if (username != null && username.length() > 0) {
			if (notesService.createNote(note, username)) {
				return showViewNotes(model);
			} else {
				model.addAttribute("message", messageSource.getMessage("error.createNote.notPossible", new String[] { note.getTitle() }, Locale.ROOT));
				return "error";
			}
		} else {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
	}

	@RequestMapping(value = "/delete_note", method = RequestMethod.GET)
	public String handleDeleteNote(@RequestParam(value = "id", required = true) String id, Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0)
			return showViewNotes(model);
		notesService.deleteNote(id, username);
		return "redirect:/view_notes";
	}

	@Autowired
	@Required
	public void setNotesService(NotesService notesService) {
		this.notesService = notesService;
	}
}