package shopYd.com.MyProjectYD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import shopYd.com.MyProjectYD.entity.Role;
import shopYd.com.MyProjectYD.entity.User;
import shopYd.com.MyProjectYD.user.repo.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWith1Role() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user1 = new User("tuyenbeo2k3@gmail.com", "buituyen10x", "Bùi", "Tuyến");
        user1.addRole(roleAdmin);

        User savedUser = repo.save(user1);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWith2Role() {
        User user2 = new User("vanhung@gmail.com", "123456", "Phạm", "Hùng");
        Role roleEditor = new Role(2);
        Role roleAssistant = new Role(3);

        user2.addRole(roleEditor);
        user2.addRole(roleAssistant);

        User savedUser = repo.save(user2);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void listAllUsers() {
        List<User> users =  repo.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
