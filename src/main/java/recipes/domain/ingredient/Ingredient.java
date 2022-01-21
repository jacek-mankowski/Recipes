package recipes.domain.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.domain.recipe.Recipe;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_ingredient_position_recipe_id", columnNames = {"position", "recipe_id"})
        })
public class Ingredient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredientSequence")
    @SequenceGenerator(name = "ingredientSequence", sequenceName = "ingredient_seq", allocationSize = 10)
    private long id;

    @Column(nullable = false)
    private int position;

    @Column(length = 50, nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    private Recipe recipe;
}
