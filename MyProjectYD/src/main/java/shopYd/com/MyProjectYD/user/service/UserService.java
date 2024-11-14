package shopYd.com.MyProjectYD.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopYd.com.MyProjectYD.entity.Role;
import shopYd.com.MyProjectYD.entity.User;
import shopYd.com.MyProjectYD.user.exc.UserNotFoundExp;
import shopYd.com.MyProjectYD.user.repo.RoleRepository;
import shopYd.com.MyProjectYD.user.repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> findAllUser() {
        Sort sort= Sort.by("lastName").ascending();
        return userRepo.findAll(sort);
    }

    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }

    public static final int users_in_page = 4;
    public Page<User> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, users_in_page);

        if (keyword != null && !keyword.isEmpty()) {
            return userRepo.findAll(keyword, pageable);
        }

        return userRepo.findAll(pageable);
    }


    public void encryptPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    public User save(User user) {
        boolean userUpdate = user.getId() != null;

        if (userUpdate) {
            User exitUser = userRepo.findById(user.getId()).orElse(null);

            if (exitUser != null) {
                if (user.getPassword().isEmpty()) {
                    user.setPassword(encoder.encode(user.getPassword()));
                } else {
                    encryptPassword(user);
                }
            }else {
                throw new IllegalArgumentException("User not found with ID: " + user.getId());
            }
        }else {
            encryptPassword(user);
        }
        return userRepo.save(user);
    }

    public User getId(Integer id) throws UserNotFoundExp {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundExp("Could not find any user with id: " + id));
    }

    public void delete(Integer id) throws UserNotFoundExp {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundExp("Could not find any user with ID " + id));
        userRepo.delete(user);
    }

    public void updateStatus(Integer id, boolean enabled) {
        userRepo.updateEnabled(id, enabled);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;
        return userByEmail.getId().equals(id) || id == null;
    }
}
