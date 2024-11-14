package shopYd.com.MyProjectYD.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopYd.com.MyProjectYD.FileUploadUntil;
import shopYd.com.MyProjectYD.entity.Role;
import shopYd.com.MyProjectYD.entity.User;
import shopYd.com.MyProjectYD.export.UserExportCsv;
import shopYd.com.MyProjectYD.user.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    private String listFirstPage(Model model) {
        return listAllUsersByPage(1, model, "");
    }

    @GetMapping("/users/page/{pageNum}")
    public String listAllUsersByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                                     @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<User> page = userService.listByPage(pageNum, keyword);
        List<User> listUsers = page.getContent();

        int startUser = (pageNum - 1) * UserService.users_in_page + 1;
        int endUser = startUser + UserService.users_in_page - 1;
        if (endUser > page.getTotalElements()) {
            endUser = (int) page.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startUser", startUser);
        model.addAttribute("endUser", endUser);
        model.addAttribute("totalUser", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "users";
    }


    @GetMapping("/users/new")
    private String newUser(Model model) {
        User user = new User();
        List<Role> listRoles = userService.findAllRoles();
        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    private String saveUser(User user, RedirectAttributes redirectAttributes,
                            @RequestParam("image") MultipartFile image) throws IOException {

        if (!userService.isEmailUnique(user.getId(), user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email đã tồn tại! Vui lòng sử dụng email khác.");

            if (user.getId() == null)
                return "redirect:/users/new";
            return "redirect:/users/edit/" + user.getId();
        }

        if(!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            user.setPhotos(fileName);
            User saveUser = userService.save(user);

            String uploadDir = "user-photo/" + saveUser.getId();
            FileUploadUntil.cleanDir(uploadDir);
            FileUploadUntil.saveFile(uploadDir, fileName, image);
        } else {
            if (user.getPhotos().isEmpty())
                user.setPhotos(null);
            userService.save(user);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    private String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getId(id);
        List<Role> listRoles = userService.findAllRoles();

        model.addAttribute("listRoles", listRoles);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
        redirectAttributes.addFlashAttribute("message", "The user has been edited successfully.");

        return "user_form";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message",
                "The user ID " + id + " has been deleted successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String disableUser(@PathVariable(name = "id") Integer id,
                              @PathVariable(name = "status") boolean enabled,
                              RedirectAttributes redirectAttributes) {
        userService.updateStatus(id, enabled);
        String status = enabled ? "enabled" : "disable";
        String message = "The user ID " + id + " has been " + status;

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }

    @GetMapping("/export/users/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.findAllUser();
        UserExportCsv exporter = new UserExportCsv();
        exporter.export(listUsers, response);
    }
}
