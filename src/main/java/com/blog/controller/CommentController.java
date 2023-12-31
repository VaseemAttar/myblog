package com.blog.controller;

import com.blog.payloaddto.CommentDto;
import com.blog.payloaddto.PostDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentserve;

    public CommentController(CommentService commentserve) {

        this.commentserve = commentserve;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment (@RequestParam("postId") long postId,@RequestBody CommentDto commentDto){
        CommentDto dto = commentserve.createComment(postId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }


//    deleting records

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentserve.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted!!",HttpStatus.OK);
    }


//    getting all the comments by using post id

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsById(@PathVariable long postId){
        List<CommentDto> commentDto = commentserve.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

//    getting All the comments

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos=commentserve.getAllComments();
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }


    //http://localhost:8080/api/comments?CommentId=1
//    updating comments
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(
            @RequestParam("CommentId") Long commentId,
            @RequestBody CommentDto commentDto) {

        CommentDto dtos = commentserve.updateComments(commentId, commentDto);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

}
