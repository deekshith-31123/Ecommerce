package com.project.product.service;

import com.project.product.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto dto);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(int id);
}
