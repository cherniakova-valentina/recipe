package src.recipe.recipedemo.repositories;

import org.springframework.data.repository.CrudRepository;
import src.recipe.recipedemo.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
