package com.example.blog.service;

import com.example.blog.payload.CreatePostDto;
import com.example.blog.payload.PageableDto;
import com.example.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto create(CreatePostDto createPostDto);

    PageableDto<PostDto> findAll(int page, int skip, String sort, String direction);

    PostDto findById(Long id);

    PostDto update(Long id, PostDto postDto);

    PostDto delete(Long id);
}
