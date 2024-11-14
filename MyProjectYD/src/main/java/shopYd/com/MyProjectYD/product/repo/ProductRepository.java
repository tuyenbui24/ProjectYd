package shopYd.com.MyProjectYD.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shopYd.com.MyProjectYD.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.name = :name")
    public Product getProductByName(@Param("name") String name);

    @Query("update Product p set p.enabled = ?2 where p.id = ?1")
    @Modifying
    @Transactional
    public void updateEnabled(Integer id, boolean enabled);
}
