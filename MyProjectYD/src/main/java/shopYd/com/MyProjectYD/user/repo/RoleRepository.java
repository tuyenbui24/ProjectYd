package shopYd.com.MyProjectYD.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopYd.com.MyProjectYD.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
