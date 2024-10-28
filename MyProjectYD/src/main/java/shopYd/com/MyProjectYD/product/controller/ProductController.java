package shopYd.com.MyProjectYD.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopYd.com.MyProjectYD.entity.Product;
import shopYd.com.MyProjectYD.product.service.ProductService;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewListProduct(Model model) {
//        List<Product> productList = productService.findAllProduct();
//
//        model.addAttribute("listProduct", productList);

        return "products";
    }
}
