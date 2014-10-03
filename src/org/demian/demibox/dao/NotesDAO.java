package org.demian.demibox.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotesDAO {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Note> rowMapper = new RowMapper<Note>() {
		public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
			Note note = new Note();
			note.setId(rs.getLong("id"));
			note.setTitle(rs.getString("title"));
			note.setCreationDate(rs.getTimestamp("creation_date_time"));
			note.setModifyDate(rs.getTimestamp("last_modification_date_time"));
			try {
				note.setText(rs.getString("text"));
			} catch (SQLException e) {
				note.setText("");
			}
			return note;
		}
	};

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<Note> getUsersNotes(String username) {
		return jdbc.query("SELECT id,title,creation_date_time,last_modification_date_time FROM note WHERE username = :username", new MapSqlParameterSource("username", username), rowMapper);
	}

	public Note getNote(long id, String username) {
		Note note = null;
		String query = "SELECT id,title,username,creation_date_time,last_modification_date_time,text FROM note WHERE id = :id AND username = :username";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("username", username);
		try {
			note = jdbc.queryForObject(query, params, rowMapper);
		} catch (DataAccessException e) {
			return null;
		}
		return note;
	}

	public boolean modifyNote(Note note, String username, String dateFormat, String timeZone) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", note.getTitle());
		params.addValue("username", username);
		params.addValue("text", note.getText());
		params.addValue("id", note.getId());
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date now = new Date();
		params.addValue("last_modification_date_time", format.format(now));
		String query = "UPDATE note SET title = :title, text = :text, last_modification_date_time = :last_modification_date_time WHERE id = :id AND username = :username";
		try {
			return jdbc.update(query, params) == 1;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public boolean noteExists(long id, String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("username", username);
		String query = "SELECT COUNT(*) FROM note WHERE id = :id AND username = :username";
		try {
			return jdbc.queryForObject(query, params, Integer.class) == 1;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public boolean createNote(Note note, String username, String dateFormat, String timeZone) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", note.getTitle());
		params.addValue("username", username);
		params.addValue("text", note.getText());
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date now = new Date();
		params.addValue("creation_date_time", format.format(now));
		params.addValue("last_modification_date_time", format.format(now));
		String query = "INSERT INTO note(title,username,text,creation_date_time,last_modification_date_time) VALUES(:title,:username,:text,:creation_date_time,:last_modification_date_time)";
		try {
			return jdbc.update(query, params) == 1;
		} catch (DataAccessException e) {
			return false;
		}
	}

	public boolean deleteNote(String id, String username) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("username", username);
		String query = "DELETE FROM note WHERE id = :id AND username = :username";
		try {
			return jdbc.update(query, params) == 1;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
}