package com.example.blog.service.impl;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CommentDto;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentDto create(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        Comment comment = Comment.of(commentDto.getName(), commentDto.getEmail(), commentDto.getBody(), post);

        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAll(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentDto update(Long postId, Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        Comment updated = comment.update(commentDto.getName(), commentDto.getEmail(), commentDto.getBody());

        return modelMapper.map(updated, CommentDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentDto delete(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        commentRepository.delete(comment);

        return commentDto;
    }
}
