package src.recipe.recipedemo.repositories;

import org.springframework.data.repository.CrudRepository;
import src.recipe.recipedemo.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
