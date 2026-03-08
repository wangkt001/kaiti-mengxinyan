package com.campus.secondhand.dao;

import com.campus.secondhand.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("SELECT id, username, password, role, id_number as idNumber, email, phone, avatar, created_at as createdAt, updated_at as updatedAt FROM users WHERE username = #{username}")
    User getByUsername(String username);

    @Select("SELECT id, username, password, role, id_number as idNumber, email, phone, avatar, created_at as createdAt, updated_at as updatedAt FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT id, username, password, role, id_number as idNumber, email, phone, avatar, created_at as createdAt, updated_at as updatedAt FROM users WHERE id_number = #{idNumber}")
    User getByIdNumber(String idNumber);

    @Select("SELECT id, username, password, role, id_number as idNumber, email, phone, avatar, created_at as createdAt, updated_at as updatedAt FROM users")
    List<User> listAll();

    @Insert("INSERT INTO users (username, password, id_number, phone, email, role, created_at, updated_at) VALUES (#{username}, #{password}, #{idNumber}, #{phone}, #{email}, #{role}, #{createdAt}, #{updatedAt})")
    void insert(User user);

    @Update("UPDATE users SET password = #{password}, id_number = #{idNumber}, phone = #{phone}, email = #{email}, role = #{role}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Integer id);
}
