package shopYd.com.MyProjectYD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"image","enabled","category"})
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150, unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String image;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product(String name, Double price, Integer quantity, String image, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        if (image == null || image.isEmpty()) {
            this.image = "default_image.jpg";
        } else {
            this.image = image;
        }
        this.category = category;
    }
}
