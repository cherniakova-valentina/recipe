package src.recipe.recipedemo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.converters.IngredientCommandToIngredient;
import src.recipe.recipedemo.converters.IngredientToIngredientCommand;
import src.recipe.recipedemo.converters.UnitOfMeasureCommandToUnitOfMeasure;
import src.recipe.recipedemo.domain.Ingredient;
import src.recipe.recipedemo.domain.Recipe;
import src.recipe.recipedemo.repositories.RecipeRepository;
import src.recipe.recipedemo.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            // todo implement error
            log.error("Recipe with id " + recipeId + " does not exits");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            // todo implement error
            log.error("Ingredient with id " + ingredientId + " for recipe with id " + recipeId + " does not exits");
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> findRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!findRecipe.isPresent()) {
            // todo toss error if not found!
            log.error("Recipe with id " + ingredientCommand.getRecipeId() + " not found");
            return new IngredientCommand();
        } else {
            Recipe recipe = findRecipe.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found"))); // todo address this
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst().get());
        }
    }
}
