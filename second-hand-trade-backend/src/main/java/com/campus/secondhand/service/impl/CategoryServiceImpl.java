package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.CategoryDao;
import com.campus.secondhand.model.Category;
import com.campus.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> list() {
        return categoryDao.list();
    }

    @Override
    public Category getById(Integer id) {
        return categoryDao.getById(id);
    }

    @Override
    public void save(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void updateById(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void removeById(Integer id) {
        categoryDao.delete(id);
    }
}