package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.Like;
import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import java.util.*;

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

    public Post maxLike(User user){
        Query<Post> query1 = session.createQuery("from Post where userId="+user.getUserId());
        List<Post> postList = query1.list();
        if(postList.size()==0){
            throw new Exception("this user dont have any post")
        }
        postList.sort(Comparator.comparingInt(o -> o.getLikes().size()));
        return postList.get(postList.size()-1);
    }
}
