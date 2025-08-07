package com.project.product.controller;

import com.project.product.dto.CategoryDto;
import com.project.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto) {
        CategoryDto createdCategory = categoryService.createCategory(dto);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<CategoryDto> getCategoryById(@RequestParam int id) {
        CategoryDto category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.ok(null);
        }
    }
}
