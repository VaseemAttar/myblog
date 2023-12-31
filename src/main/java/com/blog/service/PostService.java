package com.blog.service;


import com.blog.payloaddto.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto PostDto);

    void deletePost(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder);

    PostDto updatePost(Long postId, PostDto postDto);
}
