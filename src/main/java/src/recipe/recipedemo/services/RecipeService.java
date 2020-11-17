package src.recipe.recipedemo.services;

import src.recipe.recipedemo.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
}
