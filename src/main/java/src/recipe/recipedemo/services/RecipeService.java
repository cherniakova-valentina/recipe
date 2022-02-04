package src.recipe.recipedemo.services;

import src.recipe.recipedemo.commands.RecipeCommand;
import src.recipe.recipedemo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
