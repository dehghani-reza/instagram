package ir.mctab.hw12.instagram.view;

import ir.mctab.hw12.instagram.entities.Comment;
import ir.mctab.hw12.instagram.entities.Like;
import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import ir.mctab.hw12.instagram.repositories.CrudDAO;
import ir.mctab.hw12.instagram.repositories.LikeRepository;
import ir.mctab.hw12.instagram.repositories.PostRepository;
import ir.mctab.hw12.instagram.repositories.UserRepository;
import ir.mctab.hw12.instagram.share.AuthenticationService;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class RemoteController {

    private static User user;

    private static UserRepository userRepository = UserRepository.getUserRepository();
    private static PostRepository postRepository = PostRepository.getPostRepository();
    private static LikeRepository likeRepository = LikeRepository.getLikeRepository();

    //******************************class method******************************//

    public void signUp(String username , String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(userRepository.loadByUsername(username)!=null){
            throw new Exception("this username exist");
        }
        User user1 = userRepository.save(user);
        if (user1 == null) {
            throw new Exception("some thing goes wrong please try later");
        }
        System.out.println("Welcome to instagram");
    }

    public User showUser(){
        return userRepository.loadByUsername(user.getUsername());
    }

    public void deleteUser(String password) throws Exception {
        if(!user.getPassword().equals(password)){
            throw new Exception("your password is incorrect");
        }
        userRepository.delete(user);
    }

    public void signIn(String username , String password) throws Exception {
        user = userRepository.loadByUsernamePassword(username,password);
        if(user==null){
            throw new Exception("wrong username or password");
        }
        AuthenticationService.getInstance().setLoginUser(user);
    }

    public void editPassword(String oldPassword , String newPassword) throws Exception {
        if(!oldPassword.equals(user.getPassword())){
            throw new Exception("wrong password");
        }
        user.setPassword(newPassword);
        user = userRepository.update(user);
    }

    public List<Post> showPosts(User user){
        return postRepository.showUserPost(user);
    }

    public List<Post> showFollowingPost(){
        List<Post> postList = new LinkedList<>();
        Set<User> following = user.getFollowing();
        for (User user:following) {
            postList.addAll(showPosts(user));
        }
        return postList;
    }

    public Post savePost(String content){
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post editPost(String content , Long postId) throws Exception {
        Post post = postRepository.loadById(postId);
        if(!post.getUser().getUserId().equals(user.getUserId())){
            throw new Exception("its not your post to edit");
        }
        post.setContent(content);
        post = postRepository.update(post);
        return post;
    }

    public void deletePost(Long id) throws Exception {
        Post post = postRepository.loadById(id);
        if(!user.getUserId().equals(post.getUser().getUserId())){
            throw new Exception("its not your post to delete");
        }
        postRepository.deleteById(id);
    }

    public User searchUserByUsername(String username){
        return userRepository.loadByUsername(username);
    }

    public Set<User> follow(String username) throws Exception {
        User user = searchUserByUsername(username);
        if(user==null){
          throw new Exception("this username does not exist");
        }
        User mainUser = AuthenticationService.getInstance().getLoginUser();
        user.getFollowers().add(mainUser);
        userRepository.update(user);
        return mainUser.getFollowing();
    }

    public Set<User> unFollow(String username) throws Exception {
        User user = searchUserByUsername(username);
        if(user==null){
            throw new Exception("this username does not exist");
        }
        User mainUser = AuthenticationService.getInstance().getLoginUser();
        user.getFollowers().remove(mainUser);
        userRepository.update(user);
        return mainUser.getFollowing();
    }

    public void likePost(Long postId) throws Exception {
        Like like = new Like();
        like.setUser(user);
        Post post = postRepository.loadById(postId);
        if(post==null){
            throw new Exception("this post not exist");
        }
        like.setPost(post);
        if(likeRepository.loadByPostIdandUserId(user.getUserId(),postId)!=null){
            likeRepository.delete(likeRepository.loadByPostIdandUserId(user.getUserId(),postId));
        }else {
            likeRepository.save(like);
        }
    }

    public Post addComment(String commentContent , Long postId) throws Exception {
        Comment comment = new Comment();
        comment.setCommentContent(commentContent);
        comment.setUser(user);
        Post post = postRepository.loadById(postId);
        comment.setPost(post);
        if(post==null){
            throw new Exception("this post does not exist");
        }
        post.getComment().add(comment);
        postRepository.update(post);
        return post;
    }

    public Post findByMostLike(String username) throws Exception {
        User user = userRepository.loadByUsername(username);
        if (user==null){
            throw new Exception("this user name does not exist");
        }
        return postRepository.maxLike(user);
    }

}
