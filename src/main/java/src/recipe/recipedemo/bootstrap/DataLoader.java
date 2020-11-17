package src.recipe.recipedemo.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import src.recipe.recipedemo.domain.*;
import src.recipe.recipedemo.repositories.CategoryRepository;
import src.recipe.recipedemo.repositories.RecipeRepository;
import src.recipe.recipedemo.repositories.UnitOfMeasureRepository;
import src.recipe.recipedemo.services.RecipeService;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeService recipeService, RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        recipeRepository.saveAll(getRecipes());

        System.out.println("recipes # " + recipeRepository.count());
    }

    private List<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        // get UOMs
        Optional<UnitOfMeasure> teaspoonUOMOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (! teaspoonUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure teasponUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> tablespoonUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (! tablespoonUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure tablespoonUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> cupUOMOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (! cupUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure cupUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> ounceUOMOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if (! ounceUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure ounceUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> dashUOMOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (! dashUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure dashUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> poundUOMOptional = unitOfMeasureRepository.findByDescription("Pound");
        if (! poundUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure poundUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> pintUOMOptional = unitOfMeasureRepository.findByDescription("Pint");
        if (! pintUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure pintdUOM = teaspoonUOMOptional.get();

        Optional<UnitOfMeasure> peaceUOMOptional = unitOfMeasureRepository.findByDescription("Piece");
        if (! peaceUOMOptional.isPresent())
            throw new RuntimeException("Unexpected unit of measure.");
        UnitOfMeasure pieceUOM = teaspoonUOMOptional.get();

        // get Categories

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (! americanCategoryOptional.isPresent())
            throw new RuntimeException("Unexpected category");
        Category americanCategory = americanCategoryOptional.get();

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (! mexicanCategoryOptional.isPresent())
            throw new RuntimeException("Unexpected category");
        Category mexicanCategory = mexicanCategoryOptional.get();

        // guacamole recipe

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(5);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("https://www.simplyrecipes.com");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setDifficulty(Difficulty.EASY);

        guacamoleRecipe.addCategory(americanCategory);
        guacamoleRecipe.addCategory(mexicanCategory);

        guacamoleRecipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), pieceUOM));
        guacamoleRecipe.addIngredient(new Ingredient("of salt, more to taste", new BigDecimal(0.25), teasponUOM));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), teasponUOM));
        guacamoleRecipe.addIngredient(new Ingredient("of minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoonUOM));
        guacamoleRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), pieceUOM));
        guacamoleRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUOM));
        guacamoleRecipe.addIngredient(new Ingredient("of freshly grated black pepper", new BigDecimal(1), dashUOM));
        guacamoleRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(0.5), pieceUOM));
        guacamoleRecipe.addIngredient(new Ingredient("Red radishes or jicama, to garnish", new BigDecimal(1), pieceUOM));
        guacamoleRecipe.addIngredient(new Ingredient("Tortilla chips, to serve", new BigDecimal(1), pieceUOM));

        recipes.add(guacamoleRecipe);

        // Spicy Grilled Chicken Tacos Recipe

        Recipe grilledChickenTacosRecipe = new Recipe();
        grilledChickenTacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        grilledChickenTacosRecipe.setPrepTime(20);
        grilledChickenTacosRecipe.setCookTime(15);
        grilledChickenTacosRecipe.setServings(4);
        grilledChickenTacosRecipe.setSource("https://www.simplyrecipes.com");
        grilledChickenTacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        grilledChickenTacosRecipe.setDifficulty(Difficulty.EASY);

        grilledChickenTacosRecipe.addCategory(mexicanCategory);
        grilledChickenTacosRecipe.addCategory(americanCategory);

        grilledChickenTacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");

        Notes grilledChickenTacosNotes = new Notes();
        grilledChickenTacosNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        grilledChickenTacosRecipe.setNotes(grilledChickenTacosNotes);

        grilledChickenTacosRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tablespoonUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teasponUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teasponUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("sugar", new BigDecimal(1), teasponUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("salt", new BigDecimal(0.5), teasponUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tablespoonUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tablespoonUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("skinless, boneless chicken thighs", new BigDecimal(1.25), poundUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), ounceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(0.5), pintdUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(0.25), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(1), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(1), pieceUOM));
        grilledChickenTacosRecipe.addIngredient(new Ingredient("sour cream", new BigDecimal(0.5), cupUOM));

        recipes.add(grilledChickenTacosRecipe);

        return recipes;
    }
}
