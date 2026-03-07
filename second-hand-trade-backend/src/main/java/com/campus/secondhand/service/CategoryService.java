package com.campus.secondhand.service;

import com.campus.secondhand.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();
    Category getById(Integer id);
    void save(Category category);
    void updateById(Category category);
    void removeById(Integer id);
}
