package com.example.blog.service;

import com.example.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(Long postId, CommentDto commentDto);

    List<CommentDto> findAll(Long postId);

    CommentDto update(Long postId, Long commentId, CommentDto commentDto);

    CommentDto delete(Long postId, Long commentId);
}
