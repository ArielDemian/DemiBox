package org.demian.demibox.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsersDAO {
	private NamedParameterJdbcTemplate jdbc;
	private PasswordEncoder passwordEncoder;

	public UsersDAO() {
		System.out.println("Successfully loaded UsersDAO!");
	}

	public boolean delete(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		return jdbc.update("DELETE from users WHERE username = :username", params) == 1;
	}

	@Transactional
	public boolean insert(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", user.getPassword());
		params.addValue("authority", user.getAuthority());
		params.addValue("enabled", user.isEnabled());
		int res = 0;
		String query = "INSERT INTO users(username,password,enabled) VALUES(:username,:password,:enabled)";
		res += jdbc.update(query, params);
		query = "INSERT INTO authorities(username,authority) VALUES(:username,:authority)";
		res += jdbc.update(query, params);
		return res == 2;
	}

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public boolean exists(String username) {
		return jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE username = :username", new MapSqlParameterSource("username", username), Integer.class) > 0;
	}

	public boolean hasPassword(String username, String password) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		params.addValue("password", password);
		String query = "SELECT password FROM users WHERE username = :username";
		String dbPassword = jdbc.queryForObject(query, params, String.class);
		return passwordEncoder.matches(password, dbPassword);
	}

	public boolean changePassword(String username, String password) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		params.addValue("password", passwordEncoder.encode(password));
		return jdbc.update("UPDATE users SET password = :password WHERE username = :username", params) == 1;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}