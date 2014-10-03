package org.demian.demibox.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedeemCodesDAO {
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public boolean getCode(String code) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("code", code);
		return jdbc.update("DELETE FROM redeem_code WHERE BINARY code = :code", params) == 1;
	}

	public boolean exists(String code) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("code", code);
		return jdbc.queryForObject("SELECT COUNT(*) FROM redeem_code WHERE BINARY code = :code", params, Integer.class) > 0;
	}

	public boolean deleteCode(String code) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("code", code);
		return jdbc.update("DELETE FROM redeem_code WHERE code = :code", params) == 1;
	}
}