package src.recipe.recipedemo.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.commands.RecipeCommand;
import src.recipe.recipedemo.domain.Category;
import src.recipe.recipedemo.domain.Ingredient;
import src.recipe.recipedemo.domain.Recipe;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private CategoryCommandToCategory categoryConverter;
    private NotesCommandToNotes notesConverter;
    private IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, NotesCommandToNotes notesConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setNotes(notesConverter.convert(recipeCommand.getNotes()));
        recipe.setDirections(recipeCommand.getDirections());
        Set<Ingredient> ingredients = Optional.ofNullable(recipeCommand.getIngredients())
                .orElse(new HashSet<>()).stream()
                .map((s) -> ingredientConverter.convert(s))
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setSource(recipeCommand.getSource());
        recipe.setServings(recipeCommand.getServings());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        Set<Category> categories = Optional.ofNullable(recipeCommand.getCategories())
                .orElse(new HashSet<>()).stream()
                .map((s)-> categoryConverter.convert(s)).collect(Collectors.toSet());
        recipe.setCategories(categories);
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setImage(recipeCommand.getImage());
        return recipe;
    }
}
