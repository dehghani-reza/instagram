package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    UserRepository userRepository = UserRepository.getUserRepository();

    @Test
    void loadByUsername() {
        User user = userRepository.loadByUsername("reza");
        assertEquals(1L,user.getUserId());
    }

    @Test
    void loadByUsernamePassword() {
        User user = userRepository.loadByUsernamePassword("reza", "reza");
        assertEquals(1L,user.getUserId());
    }
}