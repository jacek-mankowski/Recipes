package recipes.domain.direction;

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
@Table(name = "direction",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_direction_position_recipe_id", columnNames = {"position", "recipe_id"})
        })
public class Direction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directionSequence")
    @SequenceGenerator(name = "directionSequence", sequenceName = "direction_seq", allocationSize = 10)
    private long id;

    @Column(nullable = false)
    private int position;

    @Column(length = 300, nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)
    private Recipe recipe;
}
