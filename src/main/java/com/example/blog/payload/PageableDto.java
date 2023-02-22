package com.example.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableDto<T> {

    int page;
    int skip;
    int totalPage;
    long totalElements;
    boolean first;
    boolean last;
    List<T> content = new ArrayList<>();
}
