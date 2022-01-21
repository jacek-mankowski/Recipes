package recipes.domain.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.direction.Direction;
import recipes.domain.ingredient.Ingredient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    List<Recipe> findByNameContainsIgnoreCase(String name);

    Optional<Recipe> findByNameEquals(String name);

    List<Recipe> findByCategory_NameLikeIgnoreCase(String name);

    default Recipe update(Recipe recipe, Recipe newRecipe) {
        recipe.setName(newRecipe.getName());
        recipe.setDescription(newRecipe.getDescription());
        recipe.setDate(LocalDateTime.now());
        recipe.setCategory(newRecipe.getCategory());

        newRecipe.getIngredients().sort(Comparator.comparingInt(Ingredient::getPosition));
        newRecipe.getIngredients().forEach(x -> x.setRecipe(recipe));
        recipe.getIngredients().sort(Comparator.comparingInt(Ingredient::getPosition));

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = i < newRecipe.getIngredients().size() ? newRecipe.getIngredients().get(i) : null;

            if (ingredient != null && recipe.getIngredients().get(i).getPosition() == ingredient.getPosition()) {
                if (!recipe.getIngredients().get(i).getDescription().equals(ingredient.getDescription())) {
                    recipe.getIngredients().get(i).setDescription(ingredient.getDescription());
                }
                newRecipe.getIngredients().get(i).setPosition(0);
            } else {
                recipe.getIngredients().get(i).setPosition(0);
            }
        }

        recipe.getIngredients().removeIf(x -> x.getPosition() == 0);
        newRecipe.getIngredients().removeIf(x -> x.getPosition() == 0);
        this.flush();
        recipe.getIngredients().addAll(newRecipe.getIngredients());

        newRecipe.getDirections().sort(Comparator.comparingInt(Direction::getPosition));
        newRecipe.getDirections().forEach(x -> x.setRecipe(recipe));
        recipe.getDirections().sort(Comparator.comparingInt(Direction::getPosition));


        for (int i = 0; i < recipe.getDirections().size(); i++) {
            Direction direction = i < newRecipe.getDirections().size() ? newRecipe.getDirections().get(i) : null;

            if (direction != null && recipe.getDirections().get(i).getPosition() == direction.getPosition()) {
                if (!recipe.getDirections().get(i).getDescription().equals(direction.getDescription())) {
                    recipe.getDirections().get(i).setDescription(direction.getDescription());
                }
                newRecipe.getDirections().get(i).setPosition(0);
            } else {
                recipe.getDirections().get(i).setPosition(0);
            }
        }

        recipe.getDirections().removeIf(x -> x.getPosition() == 0);
        newRecipe.getDirections().removeIf(x -> x.getPosition() == 0);
        this.flush();
        recipe.getDirections().addAll(newRecipe.getDirections());
        this.flush();

        return this.save(recipe);
    }
}
