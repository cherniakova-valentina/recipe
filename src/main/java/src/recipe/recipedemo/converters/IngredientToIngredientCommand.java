package src.recipe.recipedemo.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null)
            return null;

        final IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            command.setRecipeId(ingredient.getRecipe().getId());
        }
        command.setDescription(ingredient.getDescription());
        command.setAmount(ingredient.getAmount());
        command.setUom(uomConverter.convert(ingredient.getUom()));
        return command;
    }
}
