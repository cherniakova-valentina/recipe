package src.recipe.recipedemo.repositories;

import org.springframework.data.repository.CrudRepository;
import src.recipe.recipedemo.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
