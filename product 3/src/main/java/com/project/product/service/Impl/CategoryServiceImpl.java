package com.project.product.service.Impl;

import com.project.product.dto.CategoryDto;
import com.project.product.entity.ProductCategory;
import com.project.product.repository.ProductCategoryRepository;
import com.project.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        ProductCategory category = new ProductCategory();
        BeanUtils.copyProperties(categoryDto, category,"id");
        ProductCategory saved = productCategoryRepository.save(category);
        log.info("Category created with ID {}", saved.getId());
        CategoryDto responseDto = new CategoryDto();
        BeanUtils.copyProperties(saved, responseDto);
        return responseDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<ProductCategory> list = productCategoryRepository.findAll();
        List<CategoryDto> result = new ArrayList<>();
        for (ProductCategory category : list) {
            CategoryDto dto = new CategoryDto();
            BeanUtils.copyProperties(category, dto);
            result.add(dto);
        }
        return result;
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category ID {} not found", id);
                    return new RuntimeException("Category not found");
                });
        CategoryDto dto = new CategoryDto();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }
}
