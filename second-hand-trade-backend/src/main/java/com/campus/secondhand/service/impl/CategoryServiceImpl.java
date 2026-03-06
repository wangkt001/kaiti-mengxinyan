package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.CategoryDao;
import com.campus.secondhand.model.Category;
import com.campus.secondhand.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
}
