package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final UserRoleRowMapper ROW_MAPPER_ROLE = new UserRoleRowMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            final int userId = user.getId();
            user.getRoles().forEach(
                    role -> {
                        jdbcTemplate.update("INSERT INTO user_roles (role, user_id) " +
                   "VALUES (?,?);",role.name(),userId);
                    }
            );



//            namedParameterJdbcTemplate.update("INSERT INTO users (name, email, password, registered, enabled, calories_per_day ) " +
//                    "VALUES (:name, :email, :password, :registered, :enabled, :calories_per_day);",)
//
//
//
//            Number newKey = insertUser.executeAndReturnKey(parameterSource);
//            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON users.id = user_roles.user_id WHERE id=?", ROW_MAPPER_ROLE, id);
        return singleResult(groupRolesByUsers(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON users.id = user_roles.user_id WHERE email=?", ROW_MAPPER_ROLE, email);
        return singleResult(groupRolesByUsers(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email", ROW_MAPPER_ROLE);
        return groupRolesByUsers(users);
    }

    private List<User> groupRolesByUsers(List<User> users){
        Map<Integer, List<User>> usersMap = users.stream().collect(Collectors.groupingBy(User::getId));
        List<User> results = new ArrayList<>();
        for (List<User> value : usersMap.values()){
            results.add(value.remove(0));
            for(User u : value){
                results.get(results.size()-1).getRoles().add(singleResult(u.getRoles()));
            }
        }
        return results;
    }

    private static class UserRoleRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            String strRole = rs.getString("role");
            Role role = (strRole != null) ? Role.valueOf(strRole) : null;
            return new User(Integer.valueOf(rs.getString("id")), rs.getString("name"),
                    rs.getString("email"), rs.getString("password"), role);
        }
    }
}
