package com.decagon.xpress.controller;

import com.decagon.xpress.DTO.CommentDTO;
import com.decagon.xpress.DTO.LikeDTO;
import com.decagon.xpress.DTO.PostDTO;
import com.decagon.xpress.DTO.UserDTO;
import com.decagon.xpress.entity.Post;
import com.decagon.xpress.response.*;
import com.decagon.xpress.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping(value = "/api")
public class UserController {

        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping(value = "/register")
        public ResponseEntity<RegisterResponse> registration(@RequestBody UserDTO userDTO){
                log.info("Successfully registered {} " , userDTO.getEmail());
                //return  new ResponseEntity<>(userService.register(userDTO), HttpStatus.CREATED);
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());
                return ResponseEntity.created(uri).body(userService.register(userDTO)) ;
        }

        @PostMapping(value = "/create")
        public ResponseEntity<PostCreationResponse> create(@RequestBody PostDTO postDTO){
                log.info("Post successfully created with title: {} " , postDTO.getTitle());
                //return new ResponseEntity<>(userService.createPost(postDTO), HttpStatus.CREATED);
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/create").toUriString());
                return ResponseEntity.created(uri).body(userService.createPost(postDTO)) ;
        }
        @PutMapping(value = "/edit/{post_id}")
        public ResponseEntity<EditPostResponse> editPost(@RequestBody PostDTO post, @PathVariable("post_id") Integer post_id){
                //post.setId(post_id);
                userService.editPost(post, post_id);
                return new ResponseEntity<>(userService.editPost(post, post_id), HttpStatus.CREATED);
        }

        @DeleteMapping(value = "/delete/{post_id}")
        public ResponseEntity<DeletePostResponse> deletePost(@PathVariable("post_id") Integer post_id){

                log.info("Post successfully deleted with title: {} "  );
                //return new ResponseEntity<>(userService.deletePost(post_id), HttpStatus.OK);
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/delete/{post_id}").toUriString());
                return ResponseEntity.created(uri).body(userService.deletePost(post_id));
        }

        @PostMapping(value = "/comment/{user_id}/{post_id}")
        public ResponseEntity<CommentResponse> toComment(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody CommentDTO commentDTO){

                log.info("Commented created : {} " , commentDTO.getComment());
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/comment/{user_id}/{post_id}").toUriString());
                return ResponseEntity.created(uri).body(userService.comment(user_id, post_id, commentDTO)) ;
        }

        @PostMapping(value = "/like/{user_id}/{post_id}")
        public ResponseEntity<LikeResponse> toLike(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody LikeDTO likeDTO){

                log.info("Like successful : {} " , likeDTO.isLiked());
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like/{user_id}/{post_id}").toUriString());
                return ResponseEntity.created(uri).body(userService.like(user_id, post_id, likeDTO)) ;
        }

        @GetMapping(value = "/searchPost/{keyword}")
        public ResponseEntity<SearchPostResponse> searchPost(@PathVariable(value = "keyword") String keyword){
                return new ResponseEntity<>(userService.searchPost(keyword), HttpStatus.OK);
                //return ResponseEntity.ok().body(userService.searchPost(keyword));
        }

        @GetMapping(value = "/searchComment/{keyword}")
        public ResponseEntity<SearchCommentResponse> searchComment(@PathVariable(value = "keyword") String keyword){
                return  new ResponseEntity<>(userService.searchComment(keyword), HttpStatus.OK);
                //return ResponseEntity.ok().body(userService.searchComment(keyword));
        }




}
