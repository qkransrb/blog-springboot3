package com.example.blog.service.impl;

import com.example.blog.entity.Category;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CategoryDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Optional<Category> optional = categoryRepository.findByName(categoryDto.getName());

        if (optional.isPresent()) {
            throw new IllegalArgumentException("Category name already in use");
        }

        Category category = categoryRepository.save(Category.of(categoryDto.getName(), categoryDto.getDescription()));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(id)));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(id)));

        category.update(category.getName(), categoryDto.getDescription());

        return modelMapper.map(category, CategoryDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(id)));

        categoryRepository.delete(category);
    }
}
