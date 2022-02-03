package src.recipe.recipedemo.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import src.recipe.recipedemo.commands.UnitOfMeasureCommand;
import src.recipe.recipedemo.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null)
            return null;

        final UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(unitOfMeasure.getId());
        command.setDescription(unitOfMeasure.getDescription());
        return command;
    }
}
