package recipes.domain.recipe;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.domain.direction.Direction;
import recipes.domain.ingredient.Ingredient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto implements Serializable {
    private String name;
    private String category;
    private Optional<LocalDateTime> date;
    private String description;
    private List<String> ingredients;
    private List<String> directions;

    public RecipeDto(Recipe recipe) {
        this.name = recipe.getName();
        this.category = recipe.getCategory().getName();
        this.date = Optional.of(recipe.getDate());
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients()
                .stream()
                .sorted(Comparator.comparingInt(Ingredient::getPosition))
                .map(Ingredient::getDescription)
                .collect(Collectors.toList());
        this.directions = recipe.getDirections()
                .stream()
                .sorted(Comparator.comparingInt(Direction::getPosition))
                .map(Direction::getDescription)
                .collect(Collectors.toList());
    }
}
