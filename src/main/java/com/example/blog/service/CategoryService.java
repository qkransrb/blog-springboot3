package com.example.blog.service;

import com.example.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    List<CategoryDto> findAll();

    CategoryDto findById(Long id);

    CategoryDto update(Long id, CategoryDto categoryDto);

    void delete(Long id);
}
