package shopYd.com.MyProjectYD.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopYd.com.MyProjectYD.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
