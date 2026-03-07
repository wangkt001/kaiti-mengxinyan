package com.campus.secondhand.dao;

import com.campus.secondhand.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User getByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT * FROM users WHERE id_number = #{idNumber}")
    User getByIdNumber(String idNumber);

    @Insert("INSERT INTO users (username, password, real_name, id_number, phone, email, role, created_at, updated_at) VALUES (#{username}, #{password}, #{realName}, #{idNumber}, #{phone}, #{email}, #{role}, #{createdAt}, #{updatedAt})")
    void insert(User user);

    @Update("UPDATE users SET password = #{password}, real_name = #{realName}, id_number = #{idNumber}, phone = #{phone}, email = #{email}, role = #{role}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(User user);
}
