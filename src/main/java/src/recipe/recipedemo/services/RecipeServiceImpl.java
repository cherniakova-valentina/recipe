package src.recipe.recipedemo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import src.recipe.recipedemo.domain.Recipe;
import src.recipe.recipedemo.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in get recipe service");
        HashSet<Recipe> setOfRecipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(setOfRecipes::add);
        return setOfRecipes;
    }

    @Override
    public Recipe findById(Long l) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if(!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found!");
        }

        return recipeOptional.get();
    }
}
