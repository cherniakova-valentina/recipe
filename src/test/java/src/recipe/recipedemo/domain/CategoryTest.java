package src.recipe.recipedemo.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    static Category category;

    @BeforeAll
    public static void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4l;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}