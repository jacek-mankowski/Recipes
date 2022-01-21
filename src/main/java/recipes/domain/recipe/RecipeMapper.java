package recipes.domain.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recipes.domain.category.Category;
import recipes.domain.category.CategoryRepository;
import recipes.domain.direction.Direction;
import recipes.domain.ingredient.Ingredient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    private final CategoryRepository categoryRepository;

    @Autowired
    public RecipeMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public RecipeDto mapToRecipeDto(Recipe recipe) {
        return new RecipeDto(recipe);
    }

    public List<RecipeDto> mapToRecipeDtoList(List<Recipe> recipeList) {
        return recipeList.stream().map(this::mapToRecipeDto).collect(Collectors.toList());
    }

    public Recipe mapToRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setDate(LocalDateTime.now());

        Category category = categoryRepository.findFirstByName(recipeDto.getCategory());
        if (category == null) {
            category = new Category(0, recipeDto.getCategory());
        }

        AtomicInteger position = new AtomicInteger(0);
        List<Ingredient> ingredientList = recipeDto.getIngredients()
                .stream()
                .map(x -> new Ingredient(0, position.incrementAndGet(), x, recipe))
                .collect(Collectors.toList());

        position.set(0);
        List<Direction> directionList = recipeDto.getDirections()
                .stream()
                .map(x -> new Direction(0, position.incrementAndGet(), x, recipe))
                .collect(Collectors.toList());

        recipe.setCategory(category);
        recipe.setIngredients(ingredientList);
        recipe.setDirections(directionList);

        return recipe;
    }

    public List<Recipe> mapToRecipeList(List<RecipeDto> recipeDtoList) {
        return recipeDtoList.stream().map(this::mapToRecipe).collect(Collectors.toList());
    }

}
