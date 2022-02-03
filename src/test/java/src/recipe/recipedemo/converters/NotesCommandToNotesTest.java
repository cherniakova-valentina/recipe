package src.recipe.recipedemo.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.recipe.recipedemo.commands.NotesCommand;
import src.recipe.recipedemo.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private static final Long ID = 1l;
    private static final String RECIPE_NOTES = "recipeNotes";

    private NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {
        // given
        NotesCommand command = new NotesCommand();
        command.setId(ID);
        command.setRecipeNotes(RECIPE_NOTES);

        // when
        Notes notes = converter.convert(command);

        // then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}