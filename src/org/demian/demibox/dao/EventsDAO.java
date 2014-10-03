package org.demian.demibox.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventsDAO {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Event> rowMapper = new RowMapper<Event>() {
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Event(rs.getString("id"), rs.getTimestamp("date_time"), rs.getString("details"), rs.getString("username"), rs.getInt("email_sent") > 0);
		}
	};

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public boolean insert(Event event, String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		params.addValue("details", event.getDetails());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.addValue("date_time", dateFormat.format(event.getDate()));
		String query = "INSERT INTO event(username,details,date_time) VALUES(:username,:details,:date_time)";
		return jdbc.update(query, params) == 1;
	}

	public List<Event> getEvents(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		try {
			return jdbc.query("SELECT * FROM event WHERE username = :username", params, rowMapper);
		} catch (DataAccessException e) {
			return null;
		}
	}

	public boolean delete(String id, String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		params.addValue("id", id);
		try {
			return jdbc.update("DELETE FROM event WHERE username = :username AND id = :id", params) > 0;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Event> getRecentEvents() {
		String query = "SELECT * FROM `event` WHERE `date_time` <= TIMESTAMPADD(DAY, 1, CURRENT_TIMESTAMP) AND `date_time` >= CURRENT_TIMESTAMP AND `email_sent` = 0";
		return jdbc.query(query, rowMapper);
	}

	public boolean setEmailSent(String id, String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		params.addValue("id", id);
		try {
			return jdbc.update("UPDATE event SET email_sent = 1 WHERE id = :id AND username = :username", params) == 1;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
}