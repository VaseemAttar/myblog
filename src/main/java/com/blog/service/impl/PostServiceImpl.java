package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResorceNotFoundException;
import com.blog.payloaddto.PostDto;
import com.blog.repositry.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepo;

    //this will work like @autowired
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTittle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savedPost = postRepo.save(post);

        PostDto dto = new PostDto();
        dto.setId(savedPost.getId());
        dto.setTitle(savedPost.getTittle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());

//        dto.setMessage("Post is created");

        return dto;

    }

    @Override
    public void deletePost(long id) {
//        this is one way
//        Optional<Post> byId = postRepo.findById(id);
//        if (byId.isPresent()) {
//            postRepo.deleteById(id);
//        }else{
//            throw new ResorceNotFoundException("Post not Fond with id"+id);
//        }
//
//    }

//        another easy way using java 8 future lambdas expression

        Post post = postRepo.findById(id).orElseThrow(//it taking functional interface
                () -> new ResorceNotFoundException("Post not found with id:" + id)
        );
        //while deleting a resorce if resorce is not there i wanted to thow exception for that we use lambdas expression and we created a custom expression
    //lets delete the exceptioin
        postRepo.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();//it will converts the page to list
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }
    PostDto mapToDto(Post post){
       PostDto dto = new PostDto();
       dto.setId(post.getId());
       dto.setTitle(post.getTittle());
       dto.setDescription(post.getDescription());
       dto.setContent(post.getContent());
       return dto;
   }



    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post=postRepo.findById(postId).orElseThrow(
                ()->new ResorceNotFoundException("Post not found with id:"+postId)
        );
//        coping dto data to entity
            post.setTittle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setDescription(postDto.getDescription());

        Post savedPost = postRepo.save(post);
//        converting savedPost to dto
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

}
