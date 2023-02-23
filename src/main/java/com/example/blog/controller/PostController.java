package com.example.blog.controller;

import com.example.blog.payload.CreatePostDto;
import com.example.blog.payload.PageableDto;
import com.example.blog.payload.PostDto;
import com.example.blog.service.PostService;
import com.example.blog.util.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> create(@Valid @RequestBody CreatePostDto createPostDto) {
        return new ResponseEntity<>(postService.create(createPostDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageableDto<PostDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = Constant.DEFAULT_PAGE) int page,
            @RequestParam(value = "skip", required = false, defaultValue = Constant.DEFAULT_SKIP) int skip,
            @RequestParam(value = "sort", required = false, defaultValue = Constant.DEFAULT_SORT) String sort,
            @RequestParam(value = "direction", required = false, defaultValue = Constant.DEFAULT_DIRECTION) String direction
    ) {
        return new ResponseEntity<>(postService.findAll(page, skip, sort, direction), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> update(@Valid @PathVariable("id") Long id, @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.update(id, postDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.delete(id), HttpStatus.OK);
    }
}
