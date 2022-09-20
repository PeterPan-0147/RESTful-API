package com.decagon.xpress.serviceImplementation;

import com.decagon.xpress.DTO.*;
import com.decagon.xpress.entity.Comment;
import com.decagon.xpress.entity.Like;
import com.decagon.xpress.entity.Post;
import com.decagon.xpress.entity.User;
import com.decagon.xpress.exception.PostIsLikedException;
import com.decagon.xpress.exception.ResourceNotFoundException;
import com.decagon.xpress.repository.CommentRepository;
import com.decagon.xpress.repository.LikeRepository;
import com.decagon.xpress.repository.PostRepository;
import com.decagon.xpress.repository.UserRepository;
import com.decagon.xpress.response.*;
import com.decagon.xpress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


@Service
public class UserServiceImplementation implements UserService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Autowired
    public UserServiceImplementation(LikeRepository likeRepository, UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public RegisterResponse register(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        return new RegisterResponse("success", LocalDateTime.now(), user);
    }
    @Override
    public PostCreationResponse createPost(PostDTO postDTO) {

        Post post = new Post();
        User user = findUserById(postDTO.getUser_id());
        post.setTitle(postDTO.getTitle());
        post.setStory(postDTO.getStory());
        post.setSlug(toSlug(postDTO.getTitle()));
        post.setDesignImage(postDTO.getDesignImage());
        post.setUser(user);
        postRepository.save(post);
        return new PostCreationResponse("success", LocalDateTime.now(), post);
    }

    @Override
    public EditPostResponse editPost(PostDTO post, int post_id) {
        Post newPost = postRepository.findById(post_id).orElseThrow(()
                -> new ResourceNotFoundException("Post not found"));
        if(post.getTitle().length()>0){
            newPost.setTitle(post.getTitle());
            newPost.setSlug(toSlug(post.getTitle()));
        }
        if(post.getStory().length()>0){
            newPost.setStory(post.getStory());
        }
        if(post.getDesignImage().length()>0){
            newPost.setDesignImage(post.getDesignImage());
        }
        postRepository.save(newPost);
        return new EditPostResponse("success", LocalDateTime.now(), newPost);
    }

    @Override
    public DeletePostResponse deletePost(int post_id) {
        Post post = findPostById(post_id);
        postRepository.delete(post);
        return new DeletePostResponse("success", LocalDateTime.now(), post);
    }

    @Override
    public LoginResponse userLogin(LoginDTO loginDTO) {
        User visitor = findUserByEmail(loginDTO.getEmail());
        LoginResponse loginResponse = null;
        if(visitor != null){
            if(visitor.getPassword().equals(loginDTO.getPassword())){
                loginResponse = new LoginResponse("Success", LocalDateTime.now());
            }
        }else{
            loginResponse = new LoginResponse("Invalid password or email", LocalDateTime.now());
        }
        return loginResponse;
    }
    @Override
    public CommentResponse comment(int user_id, int post_id, CommentDTO commentDTO) {
        User  user = findUserById(user_id);
        Post post = findPostById(post_id);
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return  new CommentResponse("success", LocalDateTime.now(), comment, post);
    }
    @Override
    public LikeResponse like(int user_id, int post_id, LikeDTO likeDTO) {

        Like like = new Like();
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        LikeResponse likeResponse = null;
        Like duplicate = likeRepository.findLikeByUserIdAndPostId(user_id, post_id);
        if(duplicate == null){
            like.setLiked(likeDTO.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Like> likeList = likeRepository.likeList(post_id);
            likeResponse = new LikeResponse("success", LocalDateTime.now(), like, likeList.size());
        }else{
            likeRepository.delete(duplicate);
            throw  new PostIsLikedException("Post has been unliked");
        }
        return likeResponse;
    }
    @Override
    public SearchCommentResponse searchComment(String keyword) {
        List<Comment> comments = commentRepository.findByCommentContainingIgnoreCase(keyword);
        return new SearchCommentResponse("success", LocalDateTime.now(), comments);
    }
    @Override
    public SearchPostResponse searchPost(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);
        return new SearchPostResponse("success", LocalDateTime.now(), posts);
    }
    public User findUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found with id: " + id));
    }
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not Found with Email: " + email));
    }
    public Post findPostById(int id){
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not Found with id: " + id));
    }
    public static String toSlug(String input) {
        String removeSpace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(removeSpace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
