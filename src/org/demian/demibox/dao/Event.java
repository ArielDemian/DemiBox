package org.demian.demibox.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Comparable<Event> {
	private String id;
	private Date date;
	private String details;
	private String username;
	private boolean emailSent;

	public Event(String id, Date date, String details, String username, boolean emailSent) {
		this.id = id;
		this.date = date;
		this.details = details;
		this.username = username;
		this.emailSent = emailSent;
	}

	public Event(Date date, String details) {
		this.id = null;
		this.date = date;
		this.details = details;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", date=" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date) + ", details=" + details + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(Event e) {
		if (date.compareTo(e.getDate()) == 0) {
			return details.compareTo(e.getDetails());
		}
		return date.compareTo(e.getDate());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEmailSent() {
		return emailSent;
	}

	public void setEmailSent(boolean emailSent) {
		this.emailSent = emailSent;
	}
}