package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.entities.Comment;


public class CommentRepository extends CrudDAO<Comment, Long> {
    private static CommentRepository commentRepository;

    private CommentRepository() {
    }

    public static CommentRepository getCommentRepository() {
        if (commentRepository == null) {
            commentRepository = new CommentRepository();
            return commentRepository;
        }
        return commentRepository;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
