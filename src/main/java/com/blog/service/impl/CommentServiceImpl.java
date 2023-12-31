package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResorceNotFoundException;
import com.blog.payloaddto.CommentDto;
import com.blog.payloaddto.PostDto;
import com.blog.repositry.CommentRepository;
import com.blog.repositry.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResorceNotFoundException("Post not found with id: " + postId)
        );

        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        //converting saved comment to dto
        CommentDto dto=new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());


        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResorceNotFoundException("Comment not Found with id:" + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentDto> commentDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtos;
    }



    CommentDto mapToDto(Comment comments){
        CommentDto dto=new CommentDto();
        dto.setId(comments.getId());
        dto.setName(comments.getName());
        dto.setEmail(comments.getEmail());
        dto.setBody(comments.getBody());

        return dto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    //    updating comments
    @Override
    public CommentDto updateComments(Long commentId, CommentDto commentDto) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResorceNotFoundException("Comment not found with id:"+commentId)
        );
//        coping dto data to entity
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment savedComments = commentRepository.save(comment);
//        converting savedPost to dto
        CommentDto dto = mapToDto(savedComments);
        return dto;
    }

}
