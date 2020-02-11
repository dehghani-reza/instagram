package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.entities.Like;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeRepositoryTest {

    LikeRepository likeRepository = LikeRepository.getLikeRepository();


    @Test
    void loadByPostIdandUserId() {
        Like like = likeRepository.loadByPostIdandUserId(2L, 4L);
        assertEquals(23L,like.getId());
    }
}