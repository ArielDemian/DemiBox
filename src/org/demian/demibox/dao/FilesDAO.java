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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class FilesDAO {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<File> rowMapper = new RowMapper<File>() {
		public File mapRow(ResultSet rs, int rowNum) throws SQLException {
			File file = new File();
			file.setId(rs.getLong("id"));
			file.setName(rs.getString("file_name"));
			file.setUploadDateTime(rs.getTimestamp("upload_date_time"));
			return file;
		}
	};

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<File> getUsersFiles(String username) {
		String query = "SELECT * FROM file WHERE username = :username";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", username);
		return jdbc.query(query, params, rowMapper);
	}

	public long insert(String fileName, String username, String dateFormat, String timeZone) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("file_name", fileName);
		params.addValue("username", username);
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date now = new Date();
		params.addValue("upload_date_time", format.format(now));
		String query = "INSERT INTO file(file_name,username,upload_date_time) VALUES(:file_name,:username,:upload_date_time)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			if (jdbc.update(query, params, keyHolder) == 1) {
				return keyHolder.getKey().longValue();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean delete(String id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		String query = "DELETE FROM file WHERE id = :id";
		try {
			return jdbc.update(query, params) == 1;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	public File getFile(String id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		String query = "SELECT * FROM file WHERE id = :id";
		try {
			return jdbc.queryForObject(query, params, rowMapper);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}