package org.demian.demibox.dao;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.demian.demibox.validation.ValidEmail;

public class User {
	@ValidEmail
	private String username;
	@Size(min = 8, max = 30)
	@Pattern(regexp = "^\\S.*\\S$")
	private String password;
	private boolean enabled;
	private String authority;

	public User(String username, String password, boolean enabled, String authority) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}