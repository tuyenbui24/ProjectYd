package shopYd.com.MyProjectYD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import shopYd.com.MyProjectYD.entity.Category;
import shopYd.com.MyProjectYD.product.repo.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepoTest {
    @Autowired
    private CategoryRepository categoryRepo;

    @Test
    public void testCreateCategories() {
        Category menClothing = new Category("Quần áo nam");
        Category womenClothing = new Category("Quần áo nữ");
        Category kidsClothing = new Category("Quần áo trẻ em");

        categoryRepo.save(menClothing);
        categoryRepo.save(womenClothing);
        categoryRepo.save(kidsClothing);
    }
}
