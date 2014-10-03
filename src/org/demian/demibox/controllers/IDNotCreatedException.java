package org.demian.demibox.controllers;

public class IDNotCreatedException extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "Could not create new ID for uploaded file.";
	}
}