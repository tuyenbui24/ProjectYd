package shopYd.com.MyProjectYD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import shopYd.com.MyProjectYD.entity.Category;
import shopYd.com.MyProjectYD.entity.Product;
import shopYd.com.MyProjectYD.product.repo.CategoryRepository;
import shopYd.com.MyProjectYD.product.repo.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepoTest {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepo;

    @Test
    public void testCreateProduct() {
        Category menCategory = categoryRepo.findById(1).get();

        Product menProduct = new Product("Áo sơ mi nam", 350.000,100, "",menCategory);

        Product saveProduct = productRepo.save(menProduct);

        assertThat(saveProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void findAllProducts() {
        List<Product> products = productRepo.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
