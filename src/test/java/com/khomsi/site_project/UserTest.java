package com.khomsi.site_project;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
