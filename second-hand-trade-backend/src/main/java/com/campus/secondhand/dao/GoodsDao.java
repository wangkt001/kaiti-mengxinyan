package com.campus.secondhand.dao;

import com.campus.secondhand.model.Goods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    @Select("SELECT id, name, description, price, stock, category_id as categoryId, user_id as userId, status, created_at as createdAt, updated_at as updatedAt FROM goods WHERE status = 'active'")
    List<Goods> list();

    @Select("SELECT id, name, description, price, stock, category_id as categoryId, user_id as userId, status, created_at as createdAt, updated_at as updatedAt FROM goods WHERE category_id = #{categoryId} AND status = 'active'")
    List<Goods> listByCategory(Integer categoryId);

    @Select("SELECT id, name, description, price, stock, category_id as categoryId, user_id as userId, status, created_at as createdAt, updated_at as updatedAt FROM goods WHERE user_id = #{userId}")
    List<Goods> listByUser(Integer userId);

    @Select("SELECT id, name, description, price, stock, category_id as categoryId, user_id as userId, status, created_at as createdAt, updated_at as updatedAt FROM goods WHERE name LIKE CONCAT('%', #{keyword}, '%') AND status = 'active'")
    List<Goods> search(String keyword);

    @Select("SELECT id, name, description, price, stock, category_id as categoryId, user_id as userId, status, created_at as createdAt, updated_at as updatedAt FROM goods WHERE id = #{id}")
    Goods getById(Integer id);

    @Insert("INSERT INTO goods (name, description, price, stock, category_id, user_id, status, created_at, updated_at) VALUES (#{name}, #{description}, #{price}, #{stock}, #{categoryId}, #{userId}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Goods goods);

    @Update("UPDATE goods SET name = #{name}, description = #{description}, price = #{price}, stock = #{stock}, category_id = #{categoryId}, status = #{status}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Goods goods);

    @Delete("DELETE FROM goods WHERE id = #{id}")
    void delete(Integer id);
}
