package org.demian.demibox.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.demian.demibox.dao.Event;
import org.demian.demibox.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventsController extends AbstractController {
	private EventsService eventService;

	@RequestMapping(value = "/create_event", method = RequestMethod.GET)
	public String showCreateEvent() {
		return "create_event";
	}

	@RequestMapping(value = "/event_created", method = RequestMethod.POST)
	public String eventCreated(@RequestParam(value = "date_time", required = true) String dateTime, @RequestParam(value = "details", required = true) String details, Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0)
			return "redirect:/view_events";
		try {
			Date date = new SimpleDateFormat(messageSource.getMessage("date.format", null, Locale.ROOT)).parse(dateTime);
			Event event = new Event(date, details);
			eventService.insert(event, username);
		} catch (ParseException e) {
			model.addAttribute("message", messageSource.getMessage("error.date.wrongFormat", null, Locale.ROOT));
			return "error";
		} catch (DataAccessException e) {
			model.addAttribute("message", messageSource.getMessage("error.event.couldNotCreate", null, Locale.ROOT));
			return "error";
		}
		return "redirect:/view_events";
	}

	@RequestMapping(value = "/view_events", method = RequestMethod.GET)
	public String showViewEvents(Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0) {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
		model.addAttribute("events", eventService.getEvents(username));
		return "view_events";
	}

	@RequestMapping(value = "/delete_event", method = RequestMethod.GET)
	public String deleteEvent(@RequestParam(value = "id", required = true) String id, Model model) {
		String username = getUsername();
		if (username == null || username.length() == 0) {
			model.addAttribute("message", messageSource.getMessage("error.auth.username", null, Locale.ROOT));
			return "error";
		}
		if (eventService.delete(id, username)) {
			return "redirect:/view_events";
		} else {
			model.addAttribute("message", messageSource.getMessage("error.event.couldNotDelete", null, Locale.ROOT));
			return "error";
		}
	}

	@Autowired
	@Required
	public void setEventService(EventsService eventService) {
		this.eventService = eventService;
	}
}