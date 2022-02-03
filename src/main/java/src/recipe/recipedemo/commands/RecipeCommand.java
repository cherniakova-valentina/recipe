package src.recipe.recipedemo.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import src.recipe.recipedemo.domain.Difficulty;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients;
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories;

}
