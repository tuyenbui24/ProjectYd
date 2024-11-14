package shopYd.com.MyProjectYD.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import shopYd.com.MyProjectYD.entity.Category;
import shopYd.com.MyProjectYD.entity.Product;
import shopYd.com.MyProjectYD.product.exc.ProductNotFoundExp;
import shopYd.com.MyProjectYD.product.repo.CategoryRepository;
import shopYd.com.MyProjectYD.product.repo.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAllProduct() {
        Sort sort = Sort.by("name").ascending();
        return productRepository.findAll(sort);
    }

    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public Product save(Product product) {
        boolean isUpdate = product.getId() != null;

        if (isUpdate) {
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);

            if (existingProduct != null) {
                if (product.getName().isEmpty()) {
                    product.setName(existingProduct.getName());
                }
            } else {
                throw new IllegalArgumentException("Product not found with ID: " + product.getId());
            }
        }
        return productRepository.save(product);
    }


    public Product getId(Integer id) throws ProductNotFoundExp {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundExp("Could not find any product with id: " + id));
    }

    public void delete(Integer id) throws ProductNotFoundExp {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundExp("Could not find any product with id: " + id));
        productRepository.delete(product);
    }

    public void updateStatus(Integer id, boolean enabled) {
        productRepository.updateEnabled(id, enabled);
    }

    public boolean isProductNameUnique(Integer id, String name) {
        Product existingProduct = productRepository.getProductByName(name);

        if (existingProduct == null) return true;
        return existingProduct.getId().equals(id) || id == null;
    }
}
