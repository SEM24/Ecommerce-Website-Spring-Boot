package com.khomsi.site_project;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    @Autowired
    UserRepository userRepository;
    @Test
    public void testFindByLogin(){
        String login = "admin";
        User user = userRepository.findByLogin(login);

        assertThat(user).isNotNull();
    }
}
