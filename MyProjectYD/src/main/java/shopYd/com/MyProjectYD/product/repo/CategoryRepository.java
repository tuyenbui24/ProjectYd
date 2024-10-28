package shopYd.com.MyProjectYD.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopYd.com.MyProjectYD.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
