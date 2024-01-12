package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(String name, Integer age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers () {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }

    public boolean isUserNotExist (long id) {
        String readsql = "SELECT * FROM user WHERE id=?";
        return (this.jdbcTemplate.query(readsql, (rs, rowNum) -> 0, id).isEmpty());
    }

    public void updateUserName(UserUpdateRequest request) {
        String sql = "UPDATE user SET name=? WHERE id=?";
        this.jdbcTemplate.update(sql, request.getName(), request.getId());
    }

    public boolean isUserNotExist(String name) {
        String readsql = "SELECT * FROM user WHERE name=?";
        return (this.jdbcTemplate.query(readsql, (rs, rowNum) -> 0, name).isEmpty());
    }

    public void deleteUser (String name) {
        String sql = "DELETE FROM user WHERE name=?";
        jdbcTemplate.update(sql, name);
    }
}
