package shopYd.com.MyProjectYD.product.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopYd.com.MyProjectYD.FileUploadUntil;
import shopYd.com.MyProjectYD.entity.Category;
import shopYd.com.MyProjectYD.entity.Product;
import shopYd.com.MyProjectYD.export.ProductExportCsv;
import shopYd.com.MyProjectYD.export.UserExportCsv;
import shopYd.com.MyProjectYD.product.repo.CategoryRepository;
import shopYd.com.MyProjectYD.product.service.ProductService;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewListProduct(Model model) {
        List<Product> productList = productService.findAllProduct();

        model.addAttribute("listProduct", productList);
        return "products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        Product product = new Product();
        List<Category> categoryList = productService.findAllCategory();
        product.setEnabled(true);

        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("pageTitle", "Create New Product");
        return "product_form";
    }

    @PostMapping("/products/save")
    public String saveProductForm(Product product, RedirectAttributes redirectAttributes,
                                  @RequestParam("image") MultipartFile image)throws IOException {
        System.out.println("Product ID: " + product.getId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Category ID: " + (product.getCategory() != null ? product.getCategory().getId() : "null"));

        if (!productService.isProductNameUnique(product.getId(), product.getName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product name already exist");

            if (product.getId() == null)
                return "redirect:/products/new";
            return "redirect:/products/edit/" + product.getId();
        }
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            product.setImage(fileName);
            Product savedProduct = productService.save(product);

            String uploadDir = "product-image/" + savedProduct.getId();
            FileUploadUntil.cleanDir(uploadDir);
            FileUploadUntil.saveFile(uploadDir, fileName, image);
        } else {
            if (product.getImage().isEmpty())
                product.setImage(null);
            productService.save(product);
        }
        redirectAttributes.addFlashAttribute("message", "Product saved successfully");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.getId(id);
        List<Category> listCategory = productService.findAllCategory();

        model.addAttribute("product", product);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("pageTitle", "Edit Product");
        redirectAttributes.addFlashAttribute("message", "Edit product successfully");
        return "product_form";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProductForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("The product ID " + id + " has been deleted successfully");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String disableProduct(@PathVariable("id") Integer id,
                                 @PathVariable("status") boolean enabled,
                                 RedirectAttributes redirectAttributes) {
        productService.updateStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The product " + id + " has been" + status;

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products";
    }

    @GetMapping("/export/product/csv")
    public void exportCSV(HttpServletResponse httpServletResponse) throws IOException {
        List<Product> productList = productService.findAllProduct();
        ProductExportCsv exporter = new ProductExportCsv();
        exporter.export(productList, httpServletResponse);
    }
}
