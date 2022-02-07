package src.recipe.recipedemo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.converters.IngredientCommandToIngredient;
import src.recipe.recipedemo.converters.IngredientToIngredientCommand;
import src.recipe.recipedemo.converters.UnitOfMeasureCommandToUnitOfMeasure;
import src.recipe.recipedemo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import src.recipe.recipedemo.domain.Ingredient;
import src.recipe.recipedemo.domain.Recipe;
import src.recipe.recipedemo.repositories.RecipeRepository;
import src.recipe.recipedemo.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository,
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        // given
        Long recipeId = Long.valueOf(1l);
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Long ingredientId1 = Long.valueOf(1l);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ingredientId1);
        recipe.addIngredient(ingredient1);

        Long ingredientId2 = Long.valueOf(2l);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredientId2);
        recipe.addIngredient(ingredient2);

        Long ingredientId3 = Long.valueOf(3l);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(ingredientId3);
        recipe.addIngredient(ingredient3);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand result = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId3);

        // then
        assertEquals(Long.valueOf(ingredientId3), result.getId());
        assertEquals(Long.valueOf(recipeId), result.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientCommand() {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3l);
        command.setRecipeId(2l);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3l);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
        assertEquals(Long.valueOf(3l), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        //
        Long recipeId = 1l;
        Long ingredientId = 2l;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        // when
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        ingredientService.deleteById(recipeId, ingredientId);

        // then
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).save(any());
    }
}