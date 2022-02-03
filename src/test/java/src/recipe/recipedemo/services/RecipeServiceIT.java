package src.recipe.recipedemo.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import src.recipe.recipedemo.commands.RecipeCommand;
import src.recipe.recipedemo.converters.RecipeCommandToRecipe;
import src.recipe.recipedemo.converters.RecipeToRecipeCommand;
import src.recipe.recipedemo.domain.Recipe;
import src.recipe.recipedemo.repositories.RecipeRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceIT {

    public static final String DESCROPTION = "description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        // given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // when
        testRecipeCommand.setDescription(DESCROPTION);
        RecipeCommand savedRecipeCommad = recipeService.saveRecipeCommand(testRecipeCommand);

        // then
        assertEquals(DESCROPTION, savedRecipeCommad.getDescription());
        assertEquals(testRecipeCommand.getId(), savedRecipeCommad.getId());
        assertEquals(testRecipeCommand.getCategories().size(), savedRecipeCommad.getCategories().size());
        assertEquals(testRecipeCommand.getIngredients().size(), savedRecipeCommad.getIngredients().size());
    }
}