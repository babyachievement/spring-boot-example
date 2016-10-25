/**
 * FileName:UserServiceImpl
 * Created by HaoQiang
 * Date:16-10-25
 * Time:下午5:05
 */
package com.oneapm.si.service;

import com.oneapm.si.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String name, Integer age) {
        jdbcTemplate.update("insert into USER(NAME, AGE) values(?, ?)", name, age);
    }
    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("delete from USER where NAME = ?", name);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select id, age, name from USER order by id", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setAge(resultSet.getInt(2));
                user.setName(resultSet.getString(3));
                return user;
            }
        });
    }

    @Override
    public User getUserByID(long id) {
        return jdbcTemplate.queryForObject("Select id, age, name from User where id="+id, User.class);
    }

    @Override
    public Integer getAllUsersCount() {
        return jdbcTemplate.queryForObject("select count(1) from USER", Integer.class);
    }
    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from USER");
    }
}
