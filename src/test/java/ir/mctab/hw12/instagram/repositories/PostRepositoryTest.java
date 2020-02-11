package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {
    PostRepository postRepository = PostRepository.getPostRepository();
    User user = new User();

    @Test
    void showUserPost() {
        user.setUserId(1L);
        List<Post> postList = postRepository.showUserPost(user);
        assertEquals(7,postList.size());
    }

    @Test
    void maxLike() throws Exception {
        user.setUserId(1L);
        Post post = postRepository.maxLike(user);
        assertEquals(11,post.getPostId());
    }
}