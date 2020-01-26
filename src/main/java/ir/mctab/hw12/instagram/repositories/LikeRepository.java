package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.Like;
import ir.mctab.hw12.instagram.entities.Post;
import ir.mctab.hw12.instagram.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class LikeRepository extends CrudDAO<Like, Long> {

    @Override
    protected Class<Like> getEntityClass() {
        return Like.class;
    }

    private static LikeRepository likeRepository;

    private LikeRepository() {
    }

    public static LikeRepository getLikeRepository() {
        if (likeRepository == null) {
            likeRepository = new LikeRepository();
            return likeRepository;
        }
        return likeRepository;
    }

    public Like loadByPostIdandUserId(Long userId, Long postId) {
        Like like;
        Session session = HibernateUtil.getSession();
        Query<Like> query = session.createQuery("from Like where postId ='" + postId + "' and userId ='" + userId + "'");
        like = query.uniqueResult();
        return like;
    }
}
