package com.campus.secondhand.controller;

import com.campus.secondhand.model.Category;
import com.campus.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<Category> list() {
        return categoryService.list();
    }

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        categoryService.save(category);
        return category;
    }

    @PutMapping("/update")
    public Category update(@RequestBody Category category) {
        categoryService.updateById(category);
        return category;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        categoryService.removeById(id);
    }
}
