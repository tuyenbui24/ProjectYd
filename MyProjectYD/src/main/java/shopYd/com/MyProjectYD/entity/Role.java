package shopYd.com.MyProjectYD.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")  // Sử dụng 'id' cho equals() và hashCode()
@ToString(of = "name")  // Sử dụng 'name' cho toString()
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role(int id) {
        this.id = id;
    }
}
