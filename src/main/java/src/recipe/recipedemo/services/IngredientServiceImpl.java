package src.recipe.recipedemo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.converters.IngredientCommandToIngredient;
import src.recipe.recipedemo.converters.IngredientToIngredientCommand;
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
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
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
                // add new ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            // check by decription
            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

//    @Override
//    public void deleteById(Long recipeId, Long ingredientId) {
//        IngredientCommand ingredientCommand = findByRecipeIdAndIngredientId(recipeId, ingredientId);
//
//        ingredientRepository.deleteById(ingredientCommand.getId());
//    }

    @Override
    @Transactional
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("Delete ingredient " + ingredientId + " in recipe " + recipeId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe " + recipe.getId());

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();
                log.debug("Ingredient is found" + ingredientToDelete.getId());
                ingredientToDelete.setRecipe(null);  // hibernate will delete it from database by relationship
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);

            } else {
                // todo implement error
                log.error("Ingredient with id " + ingredientId + " for recipe with id " + recipeId + " does not exits");
            }
        } else {
            // todo implement error
            log.error("Recipe with id " + recipeId + " does not exits");
        }
    }
}
