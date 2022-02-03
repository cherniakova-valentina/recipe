package src.recipe.recipedemo.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.recipe.recipedemo.commands.CategoryCommand;
import src.recipe.recipedemo.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final Long ID = 1l;
    private static final String DESCRIPTION = "description";

    private CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {
        // given
        CategoryCommand command = new CategoryCommand();
        command.setId(ID);
        command.setDescription(DESCRIPTION);

        // when
        Category category = converter.convert(command);

        //then
        assertNotNull(category);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}