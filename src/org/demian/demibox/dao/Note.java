package org.demian.demibox.dao;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Note implements Comparable<Note> {
	private long id;
	@Size(min = 1, max = 255)
	@Pattern(regexp = "^\\S.*\\S$")
	private String title;
	private Date creationDate;
	private Date modifyDate;
	@Size
	private String text;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Note n) {
		if (modifyDate.compareTo(n.getModifyDate()) == 0) {
			if (creationDate.compareTo(n.getCreationDate()) == 0) {
				return title.compareTo(n.getTitle());
			}
			return creationDate.compareTo(n.getCreationDate());
		}
		return modifyDate.compareTo(n.getModifyDate());
	}
}