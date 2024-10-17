package shopYd.com.MyProjectYD;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
}
