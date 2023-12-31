package com.blog.service;

import com.blog.payloaddto.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(long postId, CommentDto commentDto);


    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPostId(long postId);

    List<CommentDto> getAllComments();

    CommentDto updateComments(Long commentId, CommentDto commentDto);
}
