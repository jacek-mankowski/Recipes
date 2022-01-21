package recipes.domain.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_category_name", columnNames = {"name"})
        })
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
    @SequenceGenerator(name = "categorySequence", sequenceName = "category_seq", allocationSize = 10)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;
}
