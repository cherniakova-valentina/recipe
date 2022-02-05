package src.recipe.recipedemo.services;

import src.recipe.recipedemo.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUoms();
}
