package com.open.library.mapper;

import com.open.library.POJO.Category;
import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.response.CategoryResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setCode(dto.getCode());
        return category;
    }
    public CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setCode(category.getCode());
        dto.setName(category.getName());
        dto.set_activated(category.is_activated());
        dto.set_deleted(category.is_deleted());
        return dto;
    }

    public Category toEntity(CategoryDTO categoryDTO, Category categoryOld) {
        categoryOld.setName(categoryDTO.getName());
        categoryOld.setCode(categoryDTO.getCode());
        return categoryOld;
    }
}
