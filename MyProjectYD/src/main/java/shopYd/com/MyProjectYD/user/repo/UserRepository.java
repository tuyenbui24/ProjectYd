package shopYd.com.MyProjectYD.user.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shopYd.com.MyProjectYD.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("update User u set u.enabled = ?2 where u.id = ?1")
    @Modifying
    @Transactional
    public void updateEnabled(int id, boolean enabled);

    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query("select u from User u where concat(u.id, ' ',u.email, ' ',u.firstName, ' ',u.lastName) like %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);

}
