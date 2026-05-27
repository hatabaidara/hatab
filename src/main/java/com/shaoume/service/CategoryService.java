package com.shaoume.service;

import com.shaoume.dto.CategoryDTO;
import com.shaoume.entity.Category;
import com.shaoume.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(this::toDTO);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        return toDTO(categoryRepository.save(toEntity(dto)));
    }

    public Optional<CategoryDTO> updateCategory(Long id, CategoryDTO dto) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setIcon(dto.getIcon());
            existing.setColor(dto.getColor());
            existing.setActive(dto.isActive());
            existing.setImageUrl(dto.getImageUrl());
            existing.setParentId(dto.getParentId());
            return toDTO(categoryRepository.save(existing));
        });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CategoryDTO toDTO(Category c) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        dto.setIcon(c.getIcon());
        dto.setColor(c.getColor());
        dto.setActive(c.isActive());
        dto.setProductCount(c.getProductCount() != null ? c.getProductCount() : 0);
        dto.setImageUrl(c.getImageUrl());
        dto.setParentId(c.getParentId());
        return dto;
    }

    private Category toEntity(CategoryDTO dto) {
        Category c = new Category();
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        c.setIcon(dto.getIcon() != null ? dto.getIcon() : "tag");
        c.setColor(dto.getColor() != null ? dto.getColor() : "#dbeafe");
        c.setActive(dto.isActive());
        c.setImageUrl(dto.getImageUrl());
        c.setParentId(dto.getParentId());
        return c;
    }
}
