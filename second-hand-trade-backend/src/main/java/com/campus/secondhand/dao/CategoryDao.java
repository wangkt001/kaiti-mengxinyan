package com.campus.secondhand.dao;

import com.campus.secondhand.model.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Select("SELECT * FROM categories")
    List<Category> list();

    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category getById(Integer id);

    @Insert("INSERT INTO categories (name, description, created_at, updated_at) VALUES (#{name}, #{description}, #{createdAt}, #{updatedAt})")
    void insert(Category category);

    @Update("UPDATE categories SET name = #{name}, description = #{description}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Category category);

    @Delete("DELETE FROM categories WHERE id = #{id}")
    void delete(Integer id);
}
