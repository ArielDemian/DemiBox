package org.demian.demibox.service;

import java.util.Collections;
import java.util.List;

import org.demian.demibox.dao.Event;
import org.demian.demibox.dao.EventsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {
	private EventsDAO eventsDAO;

	public boolean insert(Event event, String username) {
		return eventsDAO.insert(event, username);
	}

	public List<Event> getEvents(String username) {
		List<Event> events = eventsDAO.getEvents(username);
		Collections.sort(events);
		return events;
	}

	public boolean delete(String id, String username) {
		return eventsDAO.delete(id, username);
	}

	public List<Event> getRecentEvents() {
		return eventsDAO.getRecentEvents();
	}

	public boolean setEmailSent(String id, String username) {
		return eventsDAO.setEmailSent(id, username);
	}

	@Autowired
	public void setEventsDAO(EventsDAO eventsDAO) {
		this.eventsDAO = eventsDAO;
	}
}