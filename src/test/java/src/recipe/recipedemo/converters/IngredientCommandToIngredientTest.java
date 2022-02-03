package src.recipe.recipedemo.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import src.recipe.recipedemo.commands.IngredientCommand;
import src.recipe.recipedemo.domain.Ingredient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    private static final Long ID = 1l;
    private static final String DESCRIPTION = "description";
    private static final BigDecimal AMOUNT = new BigDecimal(10.0);

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);

        // when
        Ingredient ingredient = converter.convert(command);

        // then
        assertNotNull(ingredient);
        assertEquals(ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}