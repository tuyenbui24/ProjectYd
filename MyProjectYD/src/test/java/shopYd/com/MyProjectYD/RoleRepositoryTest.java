package shopYd.com.MyProjectYD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Rollback;
import shopYd.com.MyProjectYD.entity.Role;
import shopYd.com.MyProjectYD.user.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateRole() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role roleSalesPerson = new Role("Salesperson", "manage product price," +
                " customers, shipping, orders and sales report");

        Role roleEditor = new Role("Editor", "manage categories," +
                " brands, products, articles and menus");

        Role roleShipper = new Role("Shipper", "view products," +
                " view orders and update order status");

        Role roleAssistant = new Role("Assistant", "manage questions and review");

        repo.save(roleAdmin);
        repo.save(roleSalesPerson);
        repo.save(roleEditor);
        repo.save(roleShipper);
        repo.save(roleAssistant);
    }
}
