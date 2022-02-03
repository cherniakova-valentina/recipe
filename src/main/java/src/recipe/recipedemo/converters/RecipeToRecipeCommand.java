package src.recipe.recipedemo.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import src.recipe.recipedemo.commands.CategoryCommand;
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
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private NotesToNotesCommand notesConverter;
    private IngredientToIngredientCommand ingredientConverter;
    private CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter, IngredientToIngredientCommand ingredientConverter,
                                 CategoryToCategoryCommand categoryConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null)
            return null;

        final RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setNotes(notesConverter.convert(recipe.getNotes()));
        command.setDirections(recipe.getDirections());
        Set<IngredientCommand> ingredients = Optional.ofNullable(recipe.getIngredients())
                .orElse(new HashSet<>()).stream()
                .map((s) -> ingredientConverter.convert(s))
                .collect(Collectors.toSet());
        command.setIngredients(ingredients);
        command.setDifficulty(recipe.getDifficulty());
        command.setUrl(recipe.getUrl());
        command.setSource(recipe.getSource());
        command.setServings(recipe.getServings());
        command.setCookTime(recipe.getCookTime());
        command.setPrepTime(recipe.getPrepTime());
        Set<CategoryCommand> categories = Optional.ofNullable(recipe.getCategories())
                .orElse(new HashSet<>()).stream()
                .map((s)-> categoryConverter.convert(s)).collect(Collectors.toSet());
        command.setCategories(categories);
        command.setDescription(recipe.getDescription());
        command.setImage(recipe.getImage());
        return command;
    }
}
