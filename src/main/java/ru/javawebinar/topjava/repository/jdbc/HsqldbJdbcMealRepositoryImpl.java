package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile("hsqldb")
@Repository
public class HsqldbJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public HsqldbJdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }
    @Override
    protected Timestamp convert(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}
