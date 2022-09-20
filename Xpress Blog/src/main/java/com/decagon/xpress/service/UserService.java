package com.decagon.xpress.service;

import com.decagon.xpress.DTO.*;
import com.decagon.xpress.entity.Post;
import com.decagon.xpress.response.*;


public interface UserService {

    RegisterResponse register(UserDTO userDTO);
    PostCreationResponse createPost(PostDTO postDTO);

    EditPostResponse editPost(PostDTO postDTO, int post_id);

    DeletePostResponse deletePost(int post_id);
    LoginResponse userLogin(LoginDTO loginDTO);
    CommentResponse comment(int user_id, int post_id, CommentDTO commentDTO);
    LikeResponse like(int user_id, int post_id, LikeDTO likeDTO);
    SearchCommentResponse searchComment(String keyword);
    SearchPostResponse searchPost(String keyword);

}
