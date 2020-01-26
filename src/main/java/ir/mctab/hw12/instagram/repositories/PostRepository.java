package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PostRepository extends CrudDAO<Post, Long> {
    private static PostRepository postRepository;

    private PostRepository() {
    }

    public static PostRepository getPostRepository() {
        if (postRepository == null) {
            postRepository = new PostRepository();
            return postRepository;
        }
        return postRepository;
    }

    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    public List<Post> showUserPost(User user) {
        Session session = HibernateUtil.getSession();
        Query<Post> query = session.createQuery("from Post where userId ='" + user.getUserId() + "'");
        return query.list();

    }
}
