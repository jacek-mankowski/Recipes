package recipes.domain.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import recipes.domain.user.User;
import recipes.domain.user.UserService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final RecipeDtoValidator recipeDtoValidator;

    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper, RecipeDtoValidator recipeDtoValidator, UserService userService) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
        this.recipeDtoValidator = recipeDtoValidator;
        this.userService = userService;
    }

    @GetMapping("/api/recipe/")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        return new ResponseEntity<>(recipeMapper.mapToRecipeDtoList(recipeService.getRecipes()), HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);

        if (recipe != null)
            return new ResponseEntity<>(recipeMapper.mapToRecipeDto(recipe), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity createRecipe(@RequestBody RecipeDto recipeDto, Authentication auth) {
        if (recipeDtoValidator.isValid(recipeDto)) {
            Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
            User user = userService.findUserByEmail(auth.getName());
            recipe.setUser(user);
            recipe = recipeService.saveRecipe(recipe);
            return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable long id, Authentication auth) {
        if (recipeService.getRecipe(id) != null) {
            if (recipeService.getRecipe(id).getUser().getEmail().toUpperCase().matches(auth.getName().toUpperCase()) &&
                    recipeService.deleteRecipe(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/api/recipe/search", params = {"category"})
    public ResponseEntity<List<RecipeDto>> searchRecipesByCategory(@RequestParam String category) {
        List<Recipe> recipeList = recipeService.getRecipesByCategoryName(category);
        List<RecipeDto> recipeDtoList = List.of();

        if (recipeList != null) {
            recipeDtoList = recipeMapper.mapToRecipeDtoList(recipeList);
            recipeDtoList.sort(Comparator.<RecipeDto, LocalDateTime>comparing(x -> x.getDate().orElse(null)).reversed());
        }
        return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/api/recipe/search", params = {"name"})
    public ResponseEntity<List<RecipeDto>> searchRecipesByName(@RequestParam String name) {
        List<Recipe> recipeList = recipeService.getRecipesContainingName(name);
        List<RecipeDto> recipeDtoList = List.of();

        if (recipeList != null) {
            recipeDtoList = recipeMapper.mapToRecipeDtoList(recipeList);
            recipeDtoList.sort(Comparator.<RecipeDto, LocalDateTime>comparing(x -> x.getDate().orElse(null)).reversed());
        }
        return new ResponseEntity<>(recipeDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/api/recipe/{id}")
    public ResponseEntity updateRecipe(@PathVariable long id, @RequestBody RecipeDto recipeDto, Authentication auth) {
        Recipe recipe = recipeService.getRecipe(id);

        if (recipe != null) {
            if (recipeDtoValidator.isValid(recipeDto)) {
                if (recipe.getUser().getEmail().toUpperCase().matches(auth.getName().toUpperCase())) {
                    Recipe newRecipe = recipeMapper.mapToRecipe(recipeDto);
                    recipeService.updateRecipe(recipe, newRecipe);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
