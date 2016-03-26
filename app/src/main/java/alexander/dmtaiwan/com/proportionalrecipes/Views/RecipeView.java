package alexander.dmtaiwan.com.proportionalrecipes.Views;

import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;

/**
 * Created by Alexander on 3/26/2016.
 */

    public interface RecipeView {
        public void onDataReturned(List<Ingredient> ingredientList);
    }
