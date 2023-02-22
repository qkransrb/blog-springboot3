package com.example.blog.service.impl;

import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.PageableDto;
import com.example.blog.payload.PostDto;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostDto create(PostDto postDto) {
        Optional<Post> optional = postRepository.findByTitle(postDto.getTitle());

        if (optional.isPresent()) {
            throw new IllegalArgumentException("Post title is already in use");
        }

        Post post = Post.of(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
        Post entity = postRepository.save(post);

        return modelMapper.map(entity, PostDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PageableDto<PostDto> findAll(int page, int skip, String sort, String direction) {
        PageRequest pageRequest = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? PageRequest.of(page, skip, Sort.by(sort).ascending())
                : PageRequest.of(page, skip, Sort.by(sort).descending());

        Page<Post> posts = postRepository.findAll(pageRequest);

        return new PageableDto<>(
                page,
                skip,
                posts.getTotalPages(),
                posts.getTotalElements(),
                posts.isFirst(),
                posts.isLast(),
                posts.getContent()
                        .stream()
                        .map(post -> modelMapper.map(post, PostDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        return modelMapper.map(post, PostDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostDto update(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        Post updated = post.update(postDto);

        return modelMapper.map(updated, PostDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostDto delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        PostDto postDto = modelMapper.map(post, PostDto.class);

        postRepository.delete(post);

        return postDto;
    }
}
