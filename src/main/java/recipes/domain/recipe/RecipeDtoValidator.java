package recipes.domain.recipe;

import org.springframework.stereotype.Component;
import recipes.utils.StringValidator;


@Component
public class RecipeDtoValidator implements StringValidator {

    public boolean isValid(RecipeDto recipeDto) {
        return recipeDto != null &&
                isValid(recipeDto.getName()) &&
                isValid(recipeDto.getCategory()) &&
                isValid(recipeDto.getDescription()) &&
                isValid(recipeDto.getIngredients()) &&
                isValid(recipeDto.getDirections());
    }

}
