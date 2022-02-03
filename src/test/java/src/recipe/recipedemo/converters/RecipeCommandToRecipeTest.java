package src.recipe.recipedemo.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.recipe.recipedemo.commands.RecipeCommand;
import src.recipe.recipedemo.domain.Difficulty;
import src.recipe.recipedemo.domain.Recipe;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    private static final Long ID = 1l;
    private static final String DIRECTIONS = "directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final String URL = "url";
    private static final String SOURCE = "source";
    private static final Integer SERVING = 2;
    private static final Integer COOK_TIME = 30;
    private static final Integer PREP_TIME = 30;
    private static final String DESCRIPTION = "description";
    private static final Byte[] IMAGE = new Byte[]{0x00, 0x00};

    private RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(), new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        // given
        RecipeCommand command = new RecipeCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);
        command.setCookTime(COOK_TIME);
        command.setDifficulty(DIFFICULTY);
        command.setDirections(DIRECTIONS);
        command.setImage(IMAGE);
        command.setPrepTime(PREP_TIME);
        command.setServings(SERVING);
        command.setSource(SOURCE);
        command.setUrl(URL);

        // when
        Recipe recipe = converter.convert(command);

        // then
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(IMAGE, recipe.getImage());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(SERVING, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
    }
}