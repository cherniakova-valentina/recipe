package src.recipe.recipedemo.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.recipe.recipedemo.commands.RecipeCommand;
import src.recipe.recipedemo.domain.Difficulty;
import src.recipe.recipedemo.domain.Recipe;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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

    private RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setCookTime(COOK_TIME);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setImage(IMAGE);
        recipe.setPrepTime(PREP_TIME);
        recipe.setServings(SERVING);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        // when
        RecipeCommand command = converter.convert(recipe);

        // then
        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(IMAGE, command.getImage());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(SERVING, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
    }
}