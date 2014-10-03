package org.demian.demibox.dao;

import java.util.Date;

public class File implements Comparable<File> {
	private long id;
	private String name;
	private Date uploadDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	@Override
	public int compareTo(File f) {
		if (uploadDateTime.compareTo(f.getUploadDateTime()) == 0) {
			return name.compareTo(f.getName());
		}
		return uploadDateTime.compareTo(f.getUploadDateTime());
	}
}