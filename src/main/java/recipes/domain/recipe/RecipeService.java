package recipes.domain.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public synchronized Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public synchronized Recipe updateRecipe(Recipe recipe, Recipe newRecipe) {
        return recipeRepository.update(recipe, newRecipe);
    }

    public synchronized List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public synchronized Recipe getRecipe(long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public synchronized Recipe getRecipe(String name) {
        return recipeRepository.findByNameEquals(name).orElse(null);
    }

    public synchronized List<Recipe> getRecipesByCategoryName(String categoryName) {
        return recipeRepository.findByCategory_NameLikeIgnoreCase(categoryName);
    }

    public synchronized List<Recipe> getRecipesContainingName(String name) {
        return recipeRepository.findByNameContainsIgnoreCase(name);
    }

    @Transactional
    public synchronized boolean deleteRecipe(long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
