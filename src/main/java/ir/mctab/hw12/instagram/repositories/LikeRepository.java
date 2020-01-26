package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.entities.Like;
import ir.mctab.hw12.instagram.entities.Post;

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
}
