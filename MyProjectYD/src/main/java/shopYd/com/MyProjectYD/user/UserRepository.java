package shopYd.com.MyProjectYD.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopYd.com.MyProjectYD.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
